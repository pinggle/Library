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
import com.hengtiansoft.ecommerce.library.ui.user.UserActivity;

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
public class CommentItemVH extends BaseViewHolder<CommentInfo> {
    TextView tv_content;
    ImageView im_user;

    public CommentItemVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_comment;
    }

    @Override
    public void onBindViewHolder(View view, final CommentInfo mSubject) {
        tv_content.setText(mSubject.content);
        ImageUtil.loadRoundImg(im_user, mSubject.creater.face);
        im_user.setOnClickListener(v ->
                ActivityCompat.startActivity((Activity) mContext,
                        new Intent(mContext, UserActivity.class).putExtra(Constants.HEAD_DATA, mSubject.creater),
                        ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_user,
                                ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }
}
