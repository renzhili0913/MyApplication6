package com.example.week02_04.presenter;

import com.example.week02_04.bean.NewBean;
import com.example.week02_04.bean.PhoneBean;
import com.example.week02_04.model.IModelImpl;
import com.example.week02_04.model.MyCallBack;
import com.example.week02_04.view.IView;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel=new IModelImpl();
    }
    @Override
    public void getRequeryData(String url, final String params, Class clazz) {
        iModel.getRequeryData(url, params, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                if (o instanceof PhoneBean){
                    PhoneBean phoneBean= (PhoneBean) o;
                    if (phoneBean==null||!phoneBean.isSuccess()){
                        iView.onFail(phoneBean.getMsg());
                    }else{
                        iView.onSuccess(phoneBean.getMsg());
                    }
                }else if(o instanceof NewBean){
                    NewBean newBean= (NewBean) o;
                    if (newBean==null||!newBean.isSuccess()){
                        iView.onFail(newBean.getStatus());
                    }else{
                        iView.onSuccess(newBean);
                    }
                }

            }
        });

    }
}
