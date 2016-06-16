package com.hengtiansoft.ecommerce.library.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.BaseViewHolder;
import com.hengtiansoft.ecommerce.library.base.util.ImageUtil;
import com.hengtiansoft.ecommerce.library.data.entity.Image;
import com.hengtiansoft.ecommerce.library.ui.article.ArticleActivity;

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
public class ArticleItemVH extends BaseViewHolder<Image> {

    ImageView image;
    TextView tv_title, tv_des, tv_info, tv_time;

    public ArticleItemVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_card_main;
    }

    @Override
    public void onBindViewHolder(View view, final Image mSubject) {
        ImageUtil.loadImg(image, mSubject.image);
        tv_title.setText(mSubject.title);
        tv_des.setText(mSubject.author);
        tv_info.setText(mSubject.type);
        tv_time.setText(mSubject.createdAt);
        view.setOnClickListener((v) ->
                ActivityCompat.startActivity((Activity) mContext,
                        new Intent(mContext, ArticleActivity.class).putExtra(Constants.HEAD_DATA, mSubject),
                        ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, image,
                                ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }
}
