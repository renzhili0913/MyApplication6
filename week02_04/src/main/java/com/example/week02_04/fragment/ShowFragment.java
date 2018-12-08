package com.example.week02_04.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.week02_04.R;
import com.example.week02_04.adapter.MyBaseAdapter;
import com.example.week02_04.bean.NewBean;
import com.example.week02_04.presenter.IPresenterImpl;
import com.example.week02_04.view.IView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

public class ShowFragment extends Fragment implements IView {
    private Banner viewPager;
    private PullToRefreshListView listView;
    private MyBaseAdapter myBaseAdapter;
    private String url="http://i.jandan.net/?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author,title,excerpt,comment_count,comment_status,custom_fields&page=1&custom_fields=thumb_c,views&dev=1";
    private int mpage;
    private IPresenterImpl iPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_item,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresenter=new IPresenterImpl(this);
        mpage=1;
        //获取资源id
        viewPager=view.findViewById(R.id.viewpager);
        listView=view.findViewById(R.id.listview);
        //创建适配器
        myBaseAdapter = new MyBaseAdapter(getActivity());
        listView.setAdapter(myBaseAdapter);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            mpage=1;
            initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
            }
        });
        initData();
        //设置轮播图样式
        viewPager.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        viewPager.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                NewBean.PostsBean benner = (NewBean.PostsBean) path;
                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(benner.getCustom_fields().getThumb_c().get(0),imageView);

            }
            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });
    }

    private void initData() {
        iPresenter.getRequeryData(url,null,NewBean.class);
    }

    @Override
    public void onSuccess(Object o) {
        NewBean newBean  = (NewBean) o;
        if (mpage==1){
            myBaseAdapter.setList(newBean.getPosts());
        }else{
            myBaseAdapter.addList(newBean.getPosts());
        }
        mpage++;
        listView.onRefreshComplete();
        //设置图片集合
        viewPager.setImages(newBean.getPosts());
        //banner设置方法全部调用完毕时最后调用
        viewPager.start();

    }


    @Override
    public void onFail(String str) {
        Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
    }
}
