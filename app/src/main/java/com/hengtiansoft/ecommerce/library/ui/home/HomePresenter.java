package com.hengtiansoft.ecommerce.library.ui.home;


import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.base.util.SharedPreferencesUtil;
import com.hengtiansoft.ecommerce.library.data.entity._User;

/**
 * Created by baixiaokang on 16/4/22.
 */
public class HomePresenter extends HomeContract.Presenter {

    @Override
    public void getTabList() {
        mView.showTabList(mModel.getTabs());
    }

    @Override
    public void getUserInfo() {
        _User user = SharedPreferencesUtil.getUser();
        if (user != null)
            mView.initUserInfo(user);
    }

    @Override
    public void onStart() {
        getTabList();
        getUserInfo();
        mRxManager.on(Constants.EVENT_LOGIN, arg -> mView.initUserInfo((_User) arg));
    }
}
