package com.hengtiansoft.ecommerce.library.ui.user;


import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.base.util.SharedPreferencesUtil;
import com.hengtiansoft.ecommerce.library.data.entity._User;

import java.io.File;

/**
 * Created by baixiaokang on 16/5/5.
 */
public class UserPresenter extends UserContract.Presenter {


    @Override
    public void upLoadFace(File file) {
        mRxManager.add(mModel.upFile(file).subscribe(
                res -> upUserInfo(res.url),
                e -> mView.showMsg("上传失败!")));
    }

    @Override
    public void upUserInfo(String face) {
        _User user = SharedPreferencesUtil.getUser();
        user.face = face;
        mRxManager.add(mModel.upUser(user).subscribe(
                res -> {
                    SharedPreferencesUtil.setUser(user);
                    mRxManager.post(Constants.EVENT_LOGIN, user);
                    mView.showMsg("更新成功!");
                },
                e -> mView.showMsg("更新失败!")));
    }

    @Override
    public void onStart() {
        mRxManager.on(Constants.EVENT_LOGIN, user -> mView.initUser((_User) user));
    }
}
