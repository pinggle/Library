package com.hengtiansoft.ecommerce.library.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.BaseActivity;
import com.hengtiansoft.ecommerce.library.base.util.ImageUtil;
import com.hengtiansoft.ecommerce.library.base.util.SharedPreferencesUtil;
import com.hengtiansoft.ecommerce.library.base.util.ToastUtil;
import com.hengtiansoft.ecommerce.library.data.Pointer;
import com.hengtiansoft.ecommerce.library.data.entity._User;
import com.hengtiansoft.ecommerce.library.view.layout.TRecyclerView;
import com.hengtiansoft.ecommerce.library.view.viewholder.UserCommentVH;

import java.io.File;

import butterknife.Bind;

public class UserActivity extends BaseActivity<UserPresenter, UserModel> implements UserContract.View {
    public static final String TRANSLATE_VIEW = "share_img";
    public static final String TXT_INCLUDE = "include";
    public static final String TXT_CREATER = "creater";
    private static final int IMAGE_REQUEST_CODE = 100;
    private static final int RESULT_REQUEST_CODE = 102;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lv_comment)
    TRecyclerView lv_comment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    public void initView() {
        _User user = (_User) getIntent().getSerializableExtra(Constants.HEAD_DATA);
        initUser(user);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ViewCompat.setTransitionName(image, TRANSLATE_VIEW);
        String creater = new Gson().toJson(new Pointer(_User.class.getSimpleName(), user.objectId));
        lv_comment.setView(UserCommentVH.class)
                .setParam(TXT_INCLUDE, "article")
                .setParam(TXT_CREATER, creater)
                .setIsRefreshable(false)
                .fetch();

        if (SharedPreferencesUtil.getUser() != null && TextUtils.equals(user.objectId, SharedPreferencesUtil.getUser
                ().objectId)) {
            fab.setImageResource(R.drawable.ic_menu_camera);
            fab.setOnClickListener(v -> getPhoto());
        } else fab.setOnClickListener(v -> ToastUtil.show("ok"));
    }

    private void getPhoto() {
        Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null)
            if (requestCode == IMAGE_REQUEST_CODE) startPhotoZoom(data.getData());
            else if (requestCode == RESULT_REQUEST_CODE) getImageToView(data);
    }

    public static final String INTENT_ACTION_CROP = "com.android.camera.action.CROP";

    private void startPhotoZoom(Uri data) {
        Intent intent = new Intent(INTENT_ACTION_CROP);
        intent.setDataAndType(data, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void getImageToView(Intent data) {
        String urlpath = "";
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            if (photo != null)
                urlpath = ImageUtil.saveFile(photo, SystemClock.currentThreadTimeMillis() + "userface.png");
        }
        if (!TextUtils.isEmpty(urlpath)) {
            File file = new File(urlpath);
            if (file.exists()) {
                mPresenter.upLoadFace(file);
            } else {
                ToastUtil.show("照片无法打开");
            }
        } else {
            ToastUtil.show("照片无法打开");
        }
    }


    @Override
    public void showMsg(String msg) {
        Snackbar.make(fab, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void initUser(_User user) {
        ImageUtil.loadRoundImg(image, user.face);
        setTitle(user.username);
    }
}
