package com.hengtiansoft.ecommerce.library.ui.home;


import com.hengtiansoft.ecommerce.library.base.BaseModel;
import com.hengtiansoft.ecommerce.library.base.BasePresenter;
import com.hengtiansoft.ecommerce.library.base.BaseView;
import com.hengtiansoft.ecommerce.library.data.entity._User;

/**
 * Created by baixiaokang on 16/4/22.
 */
public interface HomeContract {
    interface Model extends BaseModel {
        String[] getTabs();
    }


    interface View extends BaseView {
        void showTabList(String[] mTabs);

        void initUserInfo(_User user);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getTabList();

        public abstract void getUserInfo();
    }
}
