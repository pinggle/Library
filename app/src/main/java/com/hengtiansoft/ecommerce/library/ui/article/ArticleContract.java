package com.hengtiansoft.ecommerce.library.ui.article;


import com.hengtiansoft.ecommerce.library.base.BaseModel;
import com.hengtiansoft.ecommerce.library.base.BasePresenter;
import com.hengtiansoft.ecommerce.library.base.BaseView;
import com.hengtiansoft.ecommerce.library.data.Pointer;
import com.hengtiansoft.ecommerce.library.data.entity.Image;
import com.hengtiansoft.ecommerce.library.data.entity._User;

import rx.Observable;


/**
 * Created by baixiaokang on 16/4/22.
 */
public interface ArticleContract {
    interface Model extends BaseModel {
        Observable createComment(String content, Pointer article, Pointer user);
    }


    interface View extends BaseView {
        void commentSuc();
        void commentFail();
        void showLoginAction();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void createComment(String content, Image article, _User user);
        @Override
        public void onStart() {}
    }
}

