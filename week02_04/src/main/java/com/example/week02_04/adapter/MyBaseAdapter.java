package com.example.week02_04.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.week02_04.R;
import com.example.week02_04.bean.NewBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MyBaseAdapter extends BaseAdapter {
    private List<NewBean.PostsBean> list;
    private Context context;

    public MyBaseAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<NewBean.PostsBean> data) {
       list.clear();
       if (data!=null){
           list.addAll(data);
       }
       notifyDataSetChanged();
    }
    public void addList(List<NewBean.PostsBean> data) {
        if (data!=null){
            list.addAll(data);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NewBean.PostsBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.data,parent,false);
            holder=new ViewHolder(convertView);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.bindData(getItem(position));
        return convertView;
    }
    class ViewHolder{
        TextView title;
        ImageView imageView;

        public ViewHolder(View convertView) {
            title=convertView.findViewById(R.id.title);
            imageView=convertView.findViewById(R.id.thumb_c);
            convertView.setTag(this);
        }

        public void bindData(NewBean.PostsBean item) {
            title.setText(item.getTitle());
            ImageLoader.getInstance().displayImage(item.getCustom_fields().getThumb_c().get(0),imageView);
        }
    }
}
