package com.hengtiansoft.ecommerce.library.ui.main;

import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.BaseActivity;
import com.hengtiansoft.ecommerce.library.base.util.AnimationUtil;
import com.hengtiansoft.ecommerce.library.base.util.StatusBarUtil;
import com.hengtiansoft.ecommerce.library.ui.home.HomeActivity;
import com.hengtiansoft.ecommerce.library.view.widget.FireView;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/4/28.
 */
public class FlashActivity extends BaseActivity {

    @Bind(R.id.fl_main)
    FrameLayout fl_main;
    @Bind(R.id.view)
    View view;


    @Override
    public int getLayoutId() {
        return R.layout.activity_flash;
    }

    @Override
    public void initView() {
        StatusBarUtil.setTranslucentBackground(this);
        FireView mFireView = new FireView(this);
        fl_main.addView(mFireView,
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));

        AlphaAnimation anim = new AlphaAnimation(0.8f, 0.1f);
        anim.setDuration(5000);
        view.startAnimation(anim);
        AnimationUtil.setAnimationListener(anim, () -> {
            startActivity(new Intent(mContext, HomeActivity.class));
            finish();
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initPresenter() {
    }
}
