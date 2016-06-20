package com.hengtiansoft.ecommerce.library.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVAnalytics;
import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.util.LogUtil;
import com.hengtiansoft.ecommerce.library.base.util.SharedPreferencesUtil;
import com.hengtiansoft.ecommerce.library.base.util.TUtil;
import com.hengtiansoft.ecommerce.library.base.util.helper.DialogHelper;
import com.hengtiansoft.ecommerce.library.view.layout.SwipeBackLayout;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base
 * Description：
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends RxAppCompatActivity {
    public boolean isNight;
    public T mPresenter;
    public E mModel;
    public Context mContext;

    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    private DialogHelper mDialogHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNight = SharedPreferencesUtil.isNight();
        setTheme(isNight ? R.style.AppThemeNight : R.style.AppThemeDay);
        this.setContentView(this.getLayoutId());
        AVAnalytics.trackAppOpened(getIntent());//跟踪统计应用的打开情况
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        mDialogHelper = new DialogHelper(this);
        this.initView();
        this.initData();
        this.initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNight != SharedPreferencesUtil.isNight()) {
            reload();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();//取消注册，以避免内存泄露
        }
        if (mDialogHelper != null) {
            mDialogHelper.onDestroy();
        }
        ButterKnife.unbind(this);
    }

    /**
     * dispatchTouchEvent()返回true，后续事件（ACTION_MOVE、ACTION_UP）会再传递，如果返回false，dispatchTouchEvent()就接收不到ACTION_UP、
     * ACTION_MOVE。
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i("BaseActivity dispatchTouchEvent");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View currentFocusView = getCurrentFocus();
            if (isShouldHideInput(currentFocusView, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null && imm.isActive()) {// 如果键盘已经打开
                    imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);// 这样才能被GestureDetectorCompat捕获keydown时间
    }

    /**
     * Description: 是否隐藏键盘的逻辑判断
     *
     * @param touchView
     * @param event
     * @return
     */
    protected boolean isShouldHideInput(View touchView, MotionEvent event) {
        if (touchView != null && (touchView instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            touchView.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + touchView.getHeight();
            int right = left + touchView.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 重新加载activity
     */
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    /**
     * 集成滑动切换页面
     *
     * @param layoutResID 布局LayoutId
     */
    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == R.layout.activity_main || layoutResID == R.layout.activity_flash) {
            super.setContentView(layoutResID);
        } else {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            view.setBackgroundColor(getResources().getColor(R.color.window_background));
            swipeBackLayout.addView(view);
        }
    }

    /**
     * 构建滑动切换的布局
     *
     * @return
     */
    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                .MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }

    protected void showProgressDialog(DialogHelper.DialogArgs dialogArgs) {
        mDialogHelper.showProgressDialog(dialogArgs);
    }

    protected void stopProgressDialog() {
        mDialogHelper.StopProgressDialog();
    }

    protected void showAlertDialog(DialogHelper.DialogArgs dialogArgs) {
        mDialogHelper.showAlertDialog(dialogArgs);
    }


    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public abstract void initPresenter();

}
