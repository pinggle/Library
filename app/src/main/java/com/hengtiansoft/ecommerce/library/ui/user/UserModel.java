package com.hengtiansoft.ecommerce.library.ui.user;


import com.hengtiansoft.ecommerce.library.api.Api;
import com.hengtiansoft.ecommerce.library.base.util.helper.RxSchedulers;
import com.hengtiansoft.ecommerce.library.data.CreatedResult;
import com.hengtiansoft.ecommerce.library.data.entity._User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by baixiaokang on 16/5/5.
 */
public class UserModel implements UserContract.Model {

    public class Face {
        String face;

        public Face(String f) {
            this.face = f;
        }
    }

    @Override
    public Observable<CreatedResult> upFile(File file) {
        return Api.getInstance()
                .apiService
                .upFile(file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable upUser(_User user) {
        return Api.getInstance().apiService
                .upUser(user.sessionToken, user.objectId, new Face(user.face))
                .compose(RxSchedulers.io_main());
    }
}
