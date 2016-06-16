package com.hengtiansoft.ecommerce.library.ui.login;


import com.hengtiansoft.ecommerce.library.api.Api;
import com.hengtiansoft.ecommerce.library.base.util.helper.RxSchedulers;
import com.hengtiansoft.ecommerce.library.data.CreatedResult;
import com.hengtiansoft.ecommerce.library.data.entity._User;

import rx.Observable;

/**
 * Created by baixiaokang on 16/5/2.
 */
public class LoginModel implements LoginContract.Model {

    @Override
    public Observable<_User> login(String name, String pass) {
        return Api.getInstance().apiService
                .login(name, pass)
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<CreatedResult> sign(String name, String pass) {
        return Api.getInstance().apiService
                .createUser(new _User(name, pass))
                .compose(RxSchedulers.io_main());
    }
}
