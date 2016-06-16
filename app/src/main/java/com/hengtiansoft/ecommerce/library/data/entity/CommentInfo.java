package com.hengtiansoft.ecommerce.library.data.entity;


import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.api.Api;
import com.hengtiansoft.ecommerce.library.base.BaseEntity;
import com.hengtiansoft.ecommerce.library.base.util.ApiUtil;
import com.hengtiansoft.ecommerce.library.base.util.helper.RxSchedulers;
import com.hengtiansoft.ecommerce.library.data.Data;

import rx.Observable;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class CommentInfo extends BaseEntity.ListBean {
    public Image article;
    public String content;
    public _User creater;

    @Override
    public Observable<Data<CommentInfo>> getPageAt(int page) {
        return Api.getInstance().apiService
                .getCommentList(ApiUtil.getInclude(param), ApiUtil.getWhere(param), Constants.PAGE_COUNT * (page - 1), Constants.PAGE_COUNT)
                .compose(RxSchedulers.io_main());
    }
}
