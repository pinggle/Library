package com.hengtiansoft.ecommerce.library.ui.login;


import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.base.util.SharedPreferencesUtil;

/**
 * Created by baixiaokang on 16/4/29.
 */
public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void login(String name, String pass) {
        // subscribe调用将信息流传递给订阅者
        mRxManager.add(mModel.login(name, pass).subscribe(user -> {
                    SharedPreferencesUtil.setUser(user);
                    mRxManager.post(Constants.EVENT_LOGIN, user);
                    mView.loginSuccess();
                }, e -> mView.showMsg("登录失败!")
        ));
    }

    @Override
    public void sign(String name, String pass) {
        mRxManager.add(mModel.sign(name, pass)
                .subscribe(res -> mView.signSuccess(),
                        e -> mView.showMsg("注册失败!")));
    }
}
