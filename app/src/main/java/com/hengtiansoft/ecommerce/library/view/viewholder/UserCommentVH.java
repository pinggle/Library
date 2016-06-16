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
import com.hengtiansoft.ecommerce.library.data.entity.CommentInfo;
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
public class UserCommentVH extends BaseViewHolder<CommentInfo> {
    TextView tv_content, tv_title;
    ImageView im_article;

    public UserCommentVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_user_comment;
    }

    @Override
    public void onBindViewHolder(View view, final CommentInfo mSubject) {
        tv_content.setText(mSubject.content);
        tv_title.setText(mSubject.article.title);
        ImageUtil.loadImg(im_article, mSubject.article.image);
        view.setOnClickListener((v) ->
                ActivityCompat.startActivity((Activity) mContext,
                        new Intent(mContext, ArticleActivity.class).putExtra(Constants.HEAD_DATA, mSubject.article),
                        ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_article,
                                ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }
}
