package com.example.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

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

    public void add(String text) {
        ContentValues values=new ContentValues();
        values.put("name",text);
        sb.insert("users",null,values);
    }

    @SuppressLint("Recycle")
    public List<String> select() {
        List<String> list = new ArrayList<>();
        Cursor users = sb.query("users", null, null, null, null, null, null);
        while (users.moveToNext()){
            String name = users.getString(users.getColumnIndex("name"));
            list.add(name);
        }
        return list;
    }

    public void delete(String s) {
        sb.delete("users","name=?",new String[]{s});
    }
}
