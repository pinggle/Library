package com.hengtiansoft.ecommerce.library.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.BaseActivity;
import com.hengtiansoft.ecommerce.library.base.BaseListFragment;
import com.hengtiansoft.ecommerce.library.base.util.AdaptationUtil;
import com.hengtiansoft.ecommerce.library.base.util.ImageUtil;
import com.hengtiansoft.ecommerce.library.base.util.SharedPreferencesUtil;
import com.hengtiansoft.ecommerce.library.base.util.ToastUtil;
import com.hengtiansoft.ecommerce.library.base.util.helper.FragmentAdapter;
import com.hengtiansoft.ecommerce.library.data.entity._User;
import com.hengtiansoft.ecommerce.library.ui.article.ArticleActivity;
import com.hengtiansoft.ecommerce.library.ui.login.LoginActivity;
import com.hengtiansoft.ecommerce.library.ui.main.AboutActivity;
import com.hengtiansoft.ecommerce.library.ui.main.SettingsActivity;
import com.hengtiansoft.ecommerce.library.ui.user.UserActivity;
import com.hengtiansoft.ecommerce.library.view.viewholder.ArticleItemVH;
import com.hengtiansoft.ecommerce.library.view.widget.MeiZhuNotification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.nv_main_navigation)
    NavigationView nvMainNavigation;
    @Bind(R.id.dl_main_drawer)
    DrawerLayout dlMainDrawer;
    ImageView im_face;
    TextView tv_name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overaction, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (dlMainDrawer.isDrawerOpen(Gravity.LEFT)) {
            dlMainDrawer.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings)
            startActivity(new Intent(mContext, AboutActivity.class));
        else if (item.getItemId() == android.R.id.home)
            dlMainDrawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, dlMainDrawer, R.string.drawer_open, R
                .string.drawer_close);
        mDrawerToggle.syncState();
        dlMainDrawer.setDrawerListener(mDrawerToggle);

        fab.setOnClickListener(v -> Snackbar.make(v, "Snackbar comes out", Snackbar.LENGTH_LONG).setAction("action",
                vi -> ToastUtil.show("ok")).show());
        View headerView = nvMainNavigation.inflateHeaderView(R.layout.nav_header_main);
        im_face = (ImageView) headerView.findViewById(R.id.im_face);
        tv_name = (TextView) headerView.findViewById(R.id.tv_name);

        nvMainNavigation.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            dlMainDrawer.closeDrawers();
            switch (item.getItemId()) {
                case R.id.nav_manage:
                    startActivity(new Intent(mContext, SettingsActivity.class));
                    break;
                case R.id.nav_share:
                    startActivity(new Intent(mContext, LoginActivity.class));
                    break;
                case R.id.nav_send:
                    SharedPreferencesUtil.setNight(mContext, !SharedPreferencesUtil.isNight());
                    break;
            }
            return true;
        });

        showNotification();
//        showDialog();
        showSweetDialog();
    }

    /**
     * MeiZhuNotification的执行是同步的
     */
    private void showNotification() {
        final MeiZhuNotification notification =
                new MeiZhuNotification.Builder().setContext(this)
                        .setTime(System.currentTimeMillis())
                        .setImgRes(R.drawable.notify)
                        .setTitle("超15城现\"毒跑道\"检测结果合查")
                        .setContent("北京市教委凌晨要求各校未完工操场停工；统一接受检查").build();
        notification.show();
    }

    private void showDialog() {
        int dialogColor;
        if (SharedPreferencesUtil.isNight()) {
            dialogColor = AdaptationUtil.getColor(this, R.color.colorAccent);
        } else {
            dialogColor = AdaptationUtil.getColor(this, R.color.colorPrimary);
        }

        NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("Modal Dialog")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def title的分隔线
                .withMessage("This is a modal Dialog.")                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor(/*"#FFE74C3C"*/dialogColor)                //def  | withDialogColor(int resid)
                .withIcon(getResources().getDrawable(R.drawable.notify))
                .withDuration(700)                                          //def
                .withEffect(Effectstype.Newspager)                           //def Effectstype.Slidetop
                .withButton1Text("OK")                                      //def gone
                .withButton2Text("Cancel")                                  //def gone
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .setCustomView(R.layout.dialog_custom_view, this)         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show("i'm btn1", Toast.LENGTH_SHORT);
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show("i'm btn2f", Toast.LENGTH_SHORT);
                    }
                })
                .show();
    }

    private void showSweetDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void showTabList(String[] mTabs) {
        List<Fragment> fragments = new ArrayList<>();
        Observable.from(mTabs).subscribe(tab -> fragments.add(BaseListFragment.newInstance(ArticleItemVH.class, tab)));
        viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments, Arrays.asList(mTabs)));
        tabs.setupWithViewPager(viewpager);
//        tabs.setTabsFromPagerAdapter(viewpager.getAdapter());
    }

    @Override
    public void initUserInfo(_User user) {
        ImageUtil.loadRoundImg(im_face, user.face);
        tv_name.setText(user.username);
        im_face.setOnClickListener(v ->
                ActivityCompat.startActivity((Activity) mContext, new Intent(mContext, UserActivity.class).putExtra
                                (Constants.HEAD_DATA, user)
                        , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_face,
                                ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }
}
