package com.hengtiansoft.ecommerce.library.ui.article;


import com.hengtiansoft.ecommerce.library.api.Api;
import com.hengtiansoft.ecommerce.library.base.util.helper.RxSchedulers;
import com.hengtiansoft.ecommerce.library.data.CreatedResult;
import com.hengtiansoft.ecommerce.library.data.Pointer;
import com.hengtiansoft.ecommerce.library.data.entity.Comment;

import rx.Observable;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class ArticleModel implements ArticleContract.Model {

    @Override
    public Observable<CreatedResult> createComment(String content, Pointer article, Pointer user) {
        return Api.getInstance().apiService
                .createComment(new Comment(article, content, user))
                .compose(RxSchedulers.io_main());
    }
}