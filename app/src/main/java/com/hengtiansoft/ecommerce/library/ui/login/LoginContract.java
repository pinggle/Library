package com.hengtiansoft.ecommerce.library.ui.login;

import com.hengtiansoft.ecommerce.library.base.BaseModel;
import com.hengtiansoft.ecommerce.library.base.BasePresenter;
import com.hengtiansoft.ecommerce.library.base.BaseView;
import com.hengtiansoft.ecommerce.library.data.CreatedResult;
import com.hengtiansoft.ecommerce.library.data.entity._User;

import rx.Observable;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.ui.login
 * Description：登录契约类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public interface LoginContract {

    // 数据和业务模型
    interface Model extends BaseModel {
        Observable<_User> login(String name, String pass);
        Observable<CreatedResult> sign(String name, String pass);
    }

    // 视图反馈
    interface View extends BaseView {
        void loginSuccess();
        void signSuccess();
        void showMsg(String msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void login(String name, String pass);
        public abstract void sign(String name, String pass);
        @Override
        public void onStart() {}
    }
}
