package com.hengtiansoft.ecommerce.library.data.entity;


import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.api.Api;
import com.hengtiansoft.ecommerce.library.base.BaseEntity;
import com.hengtiansoft.ecommerce.library.base.util.helper.RxSchedulers;

import rx.Observable;

/**
 * Created by baixiaokang on 16/4/29.
 */
public class _User extends BaseEntity.ListBean {
    public String username;
    public String password;
    public String face;
    public String sessionToken;

    public _User() {
    }

    public _User(String name, String pass) {
        this.username = name;
        this.password = pass;
    }

    @Override
    public Observable getPageAt(int page) {
        return Api.getInstance().apiService.getAllUser(Constants.PAGE_COUNT * (page - 1), Constants.PAGE_COUNT).compose(RxSchedulers.io_main());
    }
}
