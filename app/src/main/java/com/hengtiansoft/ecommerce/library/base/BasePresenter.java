package com.hengtiansoft.ecommerce.library.base;

import android.content.Context;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base
 * Description：主持者的基类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public abstract class BasePresenter<E, T> {

    @Deprecated
    public Context mContext;
    /** 模型，包含业务模型和数据模型 **/
    public E mModel;
    /** 视图 **/
    public T mView;
    public RxManager mRxManager = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    // 数据加载初始化
    public abstract void onStart();

    public void onDestroy() {
        mRxManager.clear();
    }
}

