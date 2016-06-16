package com.hengtiansoft.ecommerce.library.base;

import java.io.Serializable;
import java.util.Map;

import rx.Observable;

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
public interface BaseEntity {
    class BaseBean implements Serializable {
        public long id;
        public String objectId;
        public Map<String, String> param;
    }

    interface IListBean {
        Observable getPageAt(int page);

        void setParam(Map<String, String> param);
    }

    abstract class ListBean extends BaseBean implements IListBean {
        @Override
        public void setParam(Map<String, String> param) {
            this.param = param;
        }
    }
}
