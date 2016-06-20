package com.hengtiansoft.ecommerce.library.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.BaseActivity;
import com.hengtiansoft.ecommerce.library.base.util.EncryptUtil;
import com.hengtiansoft.ecommerce.library.base.util.LogUtil;
import com.hengtiansoft.ecommerce.library.base.util.helper.DialogHelper;
import com.hengtiansoft.ecommerce.library.ui.home.HomeActivity;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/1/14.
 */
public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.tl_name)
    TextInputLayout tlName;
    @Bind(R.id.tl_pass)
    TextInputLayout tlPass;
    @Bind(R.id.tv_sign)
    TextView tv_sign;
    @Bind(R.id.tv_title)
    TextView tv_title;
    boolean isLogin = true;
    String name;
    String pass;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        fab.setOnClickListener(v -> {
            name = tlName.getEditText().getText().toString();
            pass = tlPass.getEditText().getText().toString();
            String msg = TextUtils.isEmpty(name) ? "用户名不能为空!" : TextUtils.isEmpty(pass) ? "密码不能为空!" : "";
            if (!TextUtils.isEmpty(msg)) {
                showMsg(msg);
            } else if (isLogin) {
                showProgress();
                mPresenter.login(name, pass);
            } else {
                showProgress();
                mPresenter.sign(name, pass);
            }
        });
        tv_sign.setOnClickListener(v -> swich());
    }

    @Override
    public void initData() {

    }

    private void showProgress() {
        DialogHelper.DialogArgs dialogArgs = new DialogHelper.DialogArgs("Loading", null, false);
        dialogArgs.setUseDialogFragment(false);
        dialogArgs.setAlertDialogType(SweetAlertDialog.PROGRESS_TYPE);
        dialogArgs.setProgressBarColor(Color.parseColor("#A5DC86"));
        showProgressDialog(dialogArgs);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void loginSuccess() {
        AVObject userLoginRecord = new AVObject("UserLoginRecord");// 构建对象
        userLoginRecord.put("userName", name);// 用户名
        userLoginRecord.put("passWord", EncryptUtil.encryptMd5(pass));// 密码
        userLoginRecord.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 记录成功
                    LogUtil.d("login success");
                } else {
                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                    LogUtil.e(e.getMessage(), e);
                }
            }
        });// 保存到服务端
        stopProgressDialog();
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    @Override
    public void signSuccess() {
        AVObject ecommerceUser = new AVObject("EcommerceUser");// 构建对象
        ecommerceUser.put("userName", name);// 用户名
        ecommerceUser.put("passWord", EncryptUtil.encryptMd5(pass));// 密码
        ecommerceUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 存储成功
                    LogUtil.d("sign success");
                } else {
                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                    LogUtil.e(e.getMessage(), e);
                }
            }
        });// 保存到服务端
        stopProgressDialog();
        swich();
    }

    @Override
    public void showMsg(String msg) {
        stopProgressDialog();
        Snackbar.make(fab, msg, Snackbar.LENGTH_LONG).show();
    }

    /**
     * 登录注册切换
     */
    private void swich() {
        if (isLogin) {
            isLogin = false;
            tv_title.setText("注册");
            tv_sign.setText("去登录");
        } else {
            isLogin = true;
            tv_title.setText("登录");
            tv_sign.setText("去注册");
        }
    }
}
