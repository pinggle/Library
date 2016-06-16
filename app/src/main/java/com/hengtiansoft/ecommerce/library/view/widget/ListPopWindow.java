package com.hengtiansoft.ecommerce.library.view.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.util.helper.PopWindowAdapter;
import com.hengtiansoft.ecommerce.library.data.PopBean;

import java.util.List;

import butterknife.ButterKnife;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.view.widget
 * Description：条目的PopWindow
 *
 * @author liminghuang
 * @time 6/15/2016 17:26
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 17:26
 * Comment：
 */
public class ListPopWindow extends PopupWindow {

    private Context context;        //上下文
    /**
     * 父视图
     **/
    private View parentView;
    /**
     * item数据源
     **/
    private List<PopBean> dataList;
    /**
     * item点击接口
     **/
    private OnPopItemClickListener listener;
    /**
     * item列表视图
     **/
    private ListView lv;
    /**
     * title视图
     **/
    private View viewTop;
    private String topText, bottomText;  //title文字，bottom文字
    private TextView tvTop, tvBottom;    //title文本，bottom文本
    private PopWindowAdapter adapter;   //适配器
    /**
     * 底部点击接口
     **/
    private OnBottomTextviewClickListener bottomListener;


    public interface OnPopItemClickListener {
        void onPopItemClick(View view, int position);
    }

    public interface OnBottomTextviewClickListener {
        void onBottomClick(View view);
    }

    public ListPopWindow(Context context, OnPopItemClickListener listener, OnBottomTextviewClickListener bottomListener,
                         View parentView, List<PopBean> dataList, String bottomText, String topText) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.parentView = parentView;
        this.dataList = dataList;
        this.bottomListener = bottomListener;
        this.topText = topText;
        this.bottomText = bottomText;

        initViews();
    }


    private void initViews() {
        parentView = LayoutInflater.from(context).inflate(R.layout.layout_list_popwindow, null);
        setContentView(parentView);
        //设置弹出窗体的高
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置弹出窗体可点击
        this.setFocusable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.PopwindowAnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //view添加OnTouchListener监听判断获取触屏位置如果在布局外面则销毁弹出框
        parentView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = parentView.findViewById(R.id.ll_bottom).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        update();
        viewTop = ButterKnife.findById(parentView, R.id.top_divider_line);
        tvTop = ButterKnife.findById(parentView, R.id.tv_popwindow_header);
        tvBottom = ButterKnife.findById(parentView, R.id.tv_popwindow_footer);
        lv = ButterKnife.findById(parentView, R.id.lv_popwindow);
        adapter = new PopWindowAdapter(context, dataList, false);
        lv.setAdapter(adapter);

        if (!TextUtils.isEmpty(topText)) {
            tvTop.setVisibility(View.VISIBLE);
            tvTop.setText(topText);
            viewTop.setVisibility(View.VISIBLE);
        } else {
            tvTop.setVisibility(View.GONE);
            viewTop.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(bottomText)) {
            tvBottom.setVisibility(View.VISIBLE);
            tvBottom.setText(bottomText);
        } else {
            tvBottom.setVisibility(View.GONE);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onPopItemClick(view, i);
            }
        });

        tvTop.setOnClickListener(mOnClickListener);
        tvBottom.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomListener.onBottomClick(view);
        }
    };

    public TextView getTvTop() {
        return tvTop;
    }

    public TextView getTvBottom() {
        return tvBottom;
    }
}
