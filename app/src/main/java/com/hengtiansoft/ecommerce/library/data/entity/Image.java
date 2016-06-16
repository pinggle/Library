package com.hengtiansoft.ecommerce.library.data.entity;


import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.api.Api;
import com.hengtiansoft.ecommerce.library.base.BaseEntity;
import com.hengtiansoft.ecommerce.library.base.util.ApiUtil;
import com.hengtiansoft.ecommerce.library.base.util.helper.RxSchedulers;
import com.hengtiansoft.ecommerce.library.data.Data;

import rx.Observable;

/**
 * Created by Administrator on 2016/4/7.
 */
public class Image extends BaseEntity.ListBean {
    public String image;
    public String createdAt;
    public String article;
    public String author;
    public String title;
    public String type;

    @Override
    public Observable<Data<Image>> getPageAt(final int page) {
        return Api.getInstance().apiService
                .getAllImages(ApiUtil.getWhere(param),"-createdAt", Constants.PAGE_COUNT * (page - 1), Constants.PAGE_COUNT)
                .compose(RxSchedulers.io_main());
    }
}
