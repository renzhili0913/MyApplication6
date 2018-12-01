package com.example.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

import com.example.bean.UserBean;
import com.example.database.MySqlite;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static UserDao insanner;
    private final SQLiteDatabase sb;

    public UserDao(Context context) {
        sb = new MySqlite(context).getWritableDatabase();
    }

    public static UserDao getInsanner(Context context) {
        if (insanner==null){
            insanner=new UserDao(context);
        }
        return insanner;
    }
//添加数据到数据库
    public void add(String text,String uuid) {
        ContentValues values=new ContentValues();
        values.put("name",text);
        values.put("uuid",uuid);
        sb.insert("users",null,values);
    }
    //查询数据库中所有的数据
    @SuppressLint("Recycle")
    public List<UserBean> select() {
        List<UserBean> list = new ArrayList<>();
        Cursor users = sb.query("users", null, null, null, null, null, null);
        while (users.moveToNext()){
            String name = users.getString(users.getColumnIndex("name"));
            String uuid = users.getString(users.getColumnIndex("uuid"));
            list.add(new UserBean(name,uuid));
        }
        return list;
    }
    //通过标识删除单条数据
    public void delete(String uuid) {
        sb.delete("users","uuid=?",new String[]{uuid});
    }
}
