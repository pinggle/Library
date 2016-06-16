package com.hengtiansoft.ecommerce.library.view.viewholder;


import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.BaseViewHolder;
import com.hengtiansoft.ecommerce.library.data.entity.Image;

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
public class ArticleHeaderVH extends BaseViewHolder<Image> {
    TextView tv_article;

    public ArticleHeaderVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_article;
    }

    @Override
    public void onBindViewHolder(View view, Image obj) {
        String article = obj.article.replace("<br>", "\n").replaceAll(" ", "").replaceAll("//", "");
        if (!TextUtils.isEmpty(article)) {
            article = article.substring(article.indexOf("&gt;") + 4, article.length());
            tv_article.setText(article);
        }
    }
}
