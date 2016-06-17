package com.hengtiansoft.ecommerce.library.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengtiansoft.ecommerce.library.BasicApplication;
import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.base.util.TUtil;
import com.hengtiansoft.ecommerce.library.view.layout.TRecyclerView;

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
public class BaseListFragment extends Fragment {
    private TRecyclerView mXRecyclerView;

    /**
     * @param vh 传入VH的类名
     * @return
     */
    public static BaseListFragment newInstance(Class<? extends BaseViewHolder> vh, String type) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.VH_CLASS, vh.getCanonicalName());
        arguments.putString(Constants.BUNDLE_KEY_TYPE, type);
        BaseListFragment fragment = new BaseListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mXRecyclerView = new TRecyclerView(getContext())
                .setParam(Constants.BUNDLE_KEY_TYPE, getArguments().getString(Constants.BUNDLE_KEY_TYPE))
                .setView(TUtil.forName(getArguments().getString(Constants.VH_CLASS)));
        return mXRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (getActivity() instanceof BaseActivity) {
//            DialogHelper.DialogArgs dialogArgs = new DialogHelper.DialogArgs("Loading", null, false);
//            dialogArgs.setAlertDialogType(SweetAlertDialog.PROGRESS_TYPE);
//            dialogArgs.setProgressBarColor(Color.parseColor("#A5DC86"));
//            ((BaseActivity) getActivity()).showProgressDialog(dialogArgs);
//        }
        if (mXRecyclerView != null) {
            mXRecyclerView.fetch();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BasicApplication.getRefWatcher().watch(this);// 内存泄露分析
    }
}
