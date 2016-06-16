package com.hengtiansoft.ecommerce.library.view.viewholder;


import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.BaseViewHolder;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.view.viewholder
 * Description：
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class CommFooterVH extends BaseViewHolder<Object> {
    public ProgressBar progressbar;
    public TextView tv_state;
    public static final int LAYOUT_TYPE = R.layout.list_footer_view;

    public CommFooterVH(View view) {
        super(view);
    }

    @Override
    public int getType() {
        return LAYOUT_TYPE;
    }

    @Override
    public void onBindViewHolder(View view, Object o) {
        boolean isHasMore = (o == null ? false : true);
        progressbar.setVisibility(isHasMore ? View.VISIBLE : View.GONE);
        tv_state.setText(isHasMore ? "正在加载" : "已经到底");
    }
}