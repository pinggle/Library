package com.hengtiansoft.ecommerce.library.base.util;

import android.view.animation.Animation;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：动画处理工具类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class AnimationUtil {

    public static void setAnimationListener(Animation aninm, final AnimListener listener) {
        aninm.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listener.onAnimFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public interface AnimListener {
        void onAnimFinish();
    }
}
