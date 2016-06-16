package com.hengtiansoft.ecommerce.library.data;

/**
 * popWindow的实体类
 *
 * @author liminghuang
 * @time 4/20/2016 14:39
 */
public class PopBean {
    private String title;
    private int icon_res;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon_res() {
        return icon_res;
    }

    public void setIcon_res(int icon_res) {
        this.icon_res = icon_res;
    }

    public PopBean(String title, int icon_res) {
        this.title = title;
        this.icon_res = icon_res;
    }
}
