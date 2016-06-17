package com.hengtiansoft.ecommerce.library.base.util.helper;

import android.app.Activity;
import android.view.View;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.util.AdaptationUtil;
import com.hengtiansoft.ecommerce.library.base.util.SharedPreferencesUtil;
import com.hengtiansoft.ecommerce.library.view.widget.MyProgressDialogFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util.helper
 * Description：
 *
 * @author liminghuang
 * @time 6/17/2016 12:13
 * Modifier：liminghuang
 * ModifyTime：6/17/2016 12:13
 * Comment：
 */
public class DialogHelper {

    private Activity mContext;
    /** 自定义的进度条 **/
    private MyProgressDialogFragment mProgressDialogFragment;
    /** 对话框 **/
    private NiftyDialogBuilder mNiftyDialogBuilder;
    /** 带动画图标的对话框 **/
    private SweetAlertDialog mSweetAlertDialog;
    private boolean isUseDialogFragment;
    private boolean isUseNiftyDialog;

    public DialogHelper(Activity context) {
        this.mContext = context;
    }

    public void showProgressDialog(final DialogArgs dialogArgs) {
        this.isUseDialogFragment = dialogArgs.isUseDialogFragment;
        if (isUseDialogFragment) {
            mProgressDialogFragment = MyProgressDialogFragment.newInstance(dialogArgs.contentText, dialogArgs
                    .isCancelable);
            mProgressDialogFragment.show(mContext.getFragmentManager(), "");
        } else {
            mSweetAlertDialog = new SweetAlertDialog(mContext, dialogArgs.alertDialogType);
            mSweetAlertDialog.getProgressHelper().setBarColor(dialogArgs.progressBarColor);//Color.parseColor("#A5DC86")
            mSweetAlertDialog.setTitleText(dialogArgs.titleText);
            mSweetAlertDialog.setCancelable(dialogArgs.isCancelable);
            mSweetAlertDialog.show();
        }
    }

    public void StopProgressDialog() {
        if (isUseDialogFragment) {
            if (mProgressDialogFragment != null) {
                mProgressDialogFragment.dismiss();
            }
        } else {
            if (mSweetAlertDialog != null) {
                mSweetAlertDialog.dismiss();
            }
        }
    }

    public void showAlertDialog(final DialogArgs dialogArgs) {
        this.isUseNiftyDialog = dialogArgs.isUseNiftyDialog;
        if (isUseNiftyDialog) {
            int dialogColor;
            if (SharedPreferencesUtil.isNight()) {
                dialogColor = AdaptationUtil.getColor(mContext, R.color.colorAccent);
            } else {
                dialogColor = AdaptationUtil.getColor(mContext, R.color.colorPrimary);
            }

            mNiftyDialogBuilder = NiftyDialogBuilder.getInstance(mContext);
            mNiftyDialogBuilder
                    .withTitle(dialogArgs.titleText)                                  //.withTitle(null)  no title
                    .withTitleColor("#FFFFFF")                                  //def
                    .withDividerColor("#11000000")                              //def title的分隔线
                    .withMessage(dialogArgs.contentText)                     //.withMessage(null)  no Msg
                    .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                    .withDialogColor(/*"#FFE74C3C"*/dialogColor)                //def  | withDialogColor(int resid)
                    .withIcon(mContext.getResources().getDrawable(R.drawable.notify))
                    .withDuration(700)                                          //def
                    .withEffect(Effectstype.Newspager)                           //def Effectstype.Slidetop
                    .withButton1Text(dialogArgs.cancelText)                                      //def gone
                    .withButton2Text(dialogArgs.confirmText)                                  //def gone
                    .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                    .setCustomView(R.layout.dialog_custom_view, mContext)       //.setCustomView(View or ResId,
                    // context)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mNiftyDialogBuilder != null) {
                                mNiftyDialogBuilder.dismiss();
                            }
                            if (dialogArgs.mCancelClickCallBack != null) {
                                dialogArgs.mCancelClickCallBack.onButtonClick();
                            } else {
                                mNiftyDialogBuilder.dismiss();
                            }
                        }
                    })
                    .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mNiftyDialogBuilder != null) {
                                mNiftyDialogBuilder.dismiss();
                            }
                            if (dialogArgs.mConfirmClickCallBack != null) {
                                dialogArgs.mConfirmClickCallBack.onButtonClick();
                            } else {
                                mNiftyDialogBuilder.dismiss();
                            }
                        }
                    })
                    .show();
        } else {
            mSweetAlertDialog = new SweetAlertDialog(mContext, dialogArgs.alertDialogType);
            mSweetAlertDialog.setTitleText(dialogArgs.titleText)
                    .setContentText(dialogArgs.contentText)
                    .setConfirmText(dialogArgs.confirmText)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog
                                    .setTitleText(dialogArgs.twiceConfirmDialogArgs.titleText)
                                    .setContentText(dialogArgs.twiceConfirmDialogArgs.contentText)
                                    .setConfirmText(dialogArgs.twiceConfirmDialogArgs.confirmText)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(dialogArgs.twiceConfirmDialogArgs.alertDialogType);
                        }
                    })
                    .show();
        }
    }


    public void onDestroy() {
        if (mProgressDialogFragment != null) {
            mProgressDialogFragment.dismiss();
        }
        if (mNiftyDialogBuilder != null) {
            mNiftyDialogBuilder.dismiss();
        }
        if (mSweetAlertDialog != null) {
            mSweetAlertDialog.dismiss();
        }
    }

    public interface clickCallBack {
        void onButtonClick();
    }

    public static class DialogArgs {
        String titleText;
        String contentText;
        String cancelText;
        String confirmText;
        boolean isCancelable = false;// 进度条默认应该不可以取消的
        boolean isShowCancelBtn;
        boolean isUseNiftyDialog;
        boolean isUseDialogFragment;
        int customImg;
        int progressBarColor;
        int alertDialogType;
        clickCallBack mCancelClickCallBack;
        clickCallBack mConfirmClickCallBack;
        DialogArgs twiceConfirmDialogArgs;// 二次弹框Dialog参数

        public DialogArgs(String titleText, String contentText, boolean isCancelable) {
            this.titleText = titleText;
            this.contentText = contentText;
            this.isCancelable = isCancelable;
        }

        public int getAlertDialogType() {
            return alertDialogType;
        }

        public void setAlertDialogType(int alertDialogType) {
            this.alertDialogType = alertDialogType;
        }

        public String getCancelText() {
            return cancelText;
        }

        public void setCancelText(String cancelText) {
            this.cancelText = cancelText;
        }

        public String getConfirmText() {
            return confirmText;
        }

        public void setConfirmText(String confirmText) {
            this.confirmText = confirmText;
        }

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public int getCustomImg() {
            return customImg;
        }

        public void setCustomImg(int customImg) {
            this.customImg = customImg;
        }

        public boolean isCancelable() {
            return isCancelable;
        }

        public void setCancelable(boolean cancelable) {
            isCancelable = cancelable;
        }

        public boolean isShowCancelBtn() {
            return isShowCancelBtn;
        }

        public void setShowCancelBtn(boolean showCancelBtn) {
            isShowCancelBtn = showCancelBtn;
        }

        public int getProgressBarColor() {
            return progressBarColor;
        }

        public void setProgressBarColor(int progressBarColor) {
            this.progressBarColor = progressBarColor;
        }

        public String getTitleText() {
            return titleText;
        }

        public void setTitleText(String titleText) {
            this.titleText = titleText;
        }

        public boolean isUseDialogFragment() {
            return isUseDialogFragment;
        }

        public void setUseDialogFragment(boolean useDialogFragment) {
            isUseDialogFragment = useDialogFragment;
        }

        public boolean isUseNiftyDialog() {
            return isUseNiftyDialog;
        }

        public void setUseNiftyDialog(boolean useNiftyDialog) {
            isUseNiftyDialog = useNiftyDialog;
        }

        public clickCallBack getCancelClickCallBack() {
            return mCancelClickCallBack;
        }

        public void setCancelClickCallBack(clickCallBack cancelClickCallBack) {
            mCancelClickCallBack = cancelClickCallBack;
        }

        public clickCallBack getConfirmClickCallBack() {
            return mConfirmClickCallBack;
        }

        public void setConfirmClickCallBack(clickCallBack confirmClickCallBack) {
            mConfirmClickCallBack = confirmClickCallBack;
        }

        public DialogArgs getTwiceConfirmDialogArgs() {
            return twiceConfirmDialogArgs;
        }

        public void setTwiceConfirmDialogArgs(DialogArgs twiceConfirmDialogArgs) {
            this.twiceConfirmDialogArgs = twiceConfirmDialogArgs;
        }
    }
}
