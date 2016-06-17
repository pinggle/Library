package com.hengtiansoft.ecommerce.library.view.widget;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.hengtiansoft.ecommerce.library.R;

import butterknife.ButterKnife;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.view.widget
 * Description：
 *
 * @author liminghuang
 * @time 6/16/2016 20:16
 * Modifier：liminghuang
 * ModifyTime：6/16/2016 20:16
 * Comment：
 */
public class MyProgressDialogFragment extends DialogFragment {
    /** 内容文本 **/
    private TextView mTvContent;
    public static final String EXTRA_KEY_MESSAGE = "message";
    public static final String EXTRA_KEY_CANCELABLE = "cancelable";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        if (getActivity() instanceof ConfirmDialogListener ) {
//            mListener= (ConfirmDialogListener ) getActivity();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除默认的title
        View view = inflater.inflate(R.layout.dialog_progressbar_view, container);
        mTvContent = ButterKnife.findById(view, R.id.content_tv);
        mTvContent.setText(getArguments().getString(EXTRA_KEY_MESSAGE));
        setCancelable(getArguments().getBoolean(EXTRA_KEY_CANCELABLE));
        return view;
    }

    /**
     * 静态的构建方式
     *
     * @param message
     * @param cancelable
     * @return
     */
    public static MyProgressDialogFragment newInstance(String message, boolean cancelable) {
        MyProgressDialogFragment instance = new MyProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_KEY_MESSAGE, message);
        args.putBoolean(EXTRA_KEY_CANCELABLE, cancelable);
        instance.setArguments(args);
        return instance;
    }

    //    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
////        创建ConfirmDialog核心代码，可以下载源代码查看.....
//        return null;
//    }

}
