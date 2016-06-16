package com.hengtiansoft.ecommerce.library.base.util.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.data.PopBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util.helper
 * Description：ListpopWindow的适配器
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class PopWindowAdapter extends BaseAdapter {
    private Context context;
    private List<PopBean> dataList;
    private LayoutInflater inflater;
    private boolean isShowImg = false;

    public PopWindowAdapter(Context context, List<PopBean> dataList, boolean isShowImg) {
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
        this.isShowImg = isShowImg;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        View view = convertView;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.listview_popwindow_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);

        }

        holder.tv_name.setText(dataList.get(i).getTitle());


        if (dataList.size() - 1 == i) {
            holder.v_line.setVisibility(View.INVISIBLE);
            holder.tv_name.setBackground(context.getResources().getDrawable(R.drawable.selector_bottom_half));
        } else {
            holder.v_line.setVisibility(View.VISIBLE);
            holder.tv_name.setBackground(context.getResources().getDrawable(R.drawable.list_gray_item));
        }


        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tv_name;
        @Bind(R.id.v_line)
        View v_line;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
