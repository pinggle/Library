package com.hengtiansoft.ecommerce.library.base;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 * Created by baixiaokang on 16/4/28.
 */
public class RxManager {

    public RxBus mRxBus = RxBus.$();
    private Map<String, Observable<?>> mObservables = new HashMap<>();// 管理观察者
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();// 管理订阅者者

    /**
     * bus事件响应
     *
     * @param eventName
     * @param action1
     */
    public void on(String eventName, Action1<Object> action1) {
        Observable<?> mObservable = mRxBus.register(eventName);
        mObservables.put(eventName, mObservable);
        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, (e) -> e.printStackTrace()));
    }

    public void add(Subscription m) {
        mCompositeSubscription.add(m);
    }

    /**
     * RXjava取消注册，以避免内存泄露
     */
    public void clear() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();// 取消订阅
        }
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()) {
            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除观察
        }
    }

    /**
     * 发送bus事件
     *
     * @param tag
     * @param content
     */
    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }
}
