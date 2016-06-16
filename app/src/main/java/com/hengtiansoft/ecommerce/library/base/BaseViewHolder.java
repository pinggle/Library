package com.hengtiansoft.ecommerce.library.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hengtiansoft.ecommerce.library.base.util.ViewUtil;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base
 * Description：
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public Context mContext;

    public BaseViewHolder(View v) {
        super(v);
        mContext = v.getContext();
        ViewUtil.autoFind(this, v);//id与name一致
    }

    /**
     * ViewHolder的Type，同时也是它的LayoutId
     *
     * @return
     */
    public abstract int getType();

    /**
     * 绑定ViewHolder
     *
     * @return
     */
    public abstract void onBindViewHolder(View view, T obj);

}
