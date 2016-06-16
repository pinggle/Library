package com.hengtiansoft.ecommerce.library.ui.user;


import com.hengtiansoft.ecommerce.library.base.BaseModel;
import com.hengtiansoft.ecommerce.library.base.BasePresenter;
import com.hengtiansoft.ecommerce.library.base.BaseView;
import com.hengtiansoft.ecommerce.library.data.CreatedResult;
import com.hengtiansoft.ecommerce.library.data.entity._User;

import java.io.File;

import rx.Observable;

/**
 * Created by baixiaokang on 16/5/5.
 */
public interface UserContract {
    interface Model extends BaseModel {
        Observable<CreatedResult> upFile(File file);

        Observable upUser(_User user);
    }


    interface View extends BaseView {

        void showMsg(String msg);
       void  initUser(_User user);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void upLoadFace(File f);

        public abstract void upUserInfo(String face);
    }
}
