package me.jinkun.opennews.features.news.presenter;

import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import me.jinkun.common.cache.DiskLruCacheUtil;
import me.jinkun.opennews.base.MyApp;
import me.jinkun.opennews.base.presenter.AbsBasePresenter;
import me.jinkun.opennews.data.domain.NewsTopic;
import me.jinkun.opennews.data.model.NewsTopicModel;
import me.jinkun.opennews.features.news.view.INewsTopicView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description: Do one thing at a time, and do well.<br/>
 * Created by jinkun on 2015/12/21.
 */
public class NewsTopicPresenter extends AbsBasePresenter<INewsTopicView> {

    public void loadData(final String tid, final int start, final int end) {
        if (getView() == null) {
            return;
        }
        Observable//
                .create(new Observable.OnSubscribe<List<NewsTopic>>() {
                    @Override
                    public void call(Subscriber<? super List<NewsTopic>> subscriber) {
                        List<NewsTopic> newsTopicList = loadDataFromLocal(tid, start, end);
                        if (newsTopicList == null || newsTopicList.size() == 0) {
                            newsTopicList = loadDataFromNet(tid, start, end);
                            save2Loacl((ArrayList<NewsTopic>) newsTopicList, tid);
                        }
                        subscriber.onNext(newsTopicList);
                        subscriber.onCompleted();
                    }
                })//
                .subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Subscriber<List<NewsTopic>>() {
                    @Override
                    public void onCompleted() {
                        if (getView() == null) {
                            return;
                        }

                        getView().onLoadFinished();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() == null) {
                            return;
                        }

                        getView().onLoadError();
                        getView().onLoadFinished();
                    }

                    @Override
                    public void onNext(List<NewsTopic> newsTopicList) {
                        if (getView() == null) {
                            return;
                        }

                        if (newsTopicList == null) {
                            getView().onLoadError();
                        }

                        if (newsTopicList.size() == 0) {
                            getView().onLoadEmpty();
                        }

                        if (newsTopicList != null && newsTopicList.size() > 0) {
                            getView().onLoadSuccess(newsTopicList);
                        }
                    }
                });
    }

    public void refreshData(final String tid, final int start, final int end) {
        if (getView() == null) {
            return;
        }

        Observable//
                .create(new Observable.OnSubscribe<List<NewsTopic>>() {
                    @Override
                    public void call(Subscriber<? super List<NewsTopic>> subscriber) {
                        List<NewsTopic> newsTopicList = loadDataFromNet(tid, start, end);
                        subscriber.onNext(newsTopicList);
                        subscriber.onCompleted();
                    }
                })//
                .subscribeOn(Schedulers.io())//
                .map(new Func1<List<NewsTopic>, List<NewsTopic>>() {
                    @Override
                    public List<NewsTopic> call(List<NewsTopic> newsTopicList) {
                        if (newsTopicList != null && newsTopicList.size() > 0) {
                            save2Loacl((ArrayList<NewsTopic>) newsTopicList, tid);
                        } else {
                            loadDataFromLocal(tid, start, end);
                        }
                        return newsTopicList;
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Subscriber<List<NewsTopic>>() {
                    @Override
                    public void onCompleted() {
                        if (getView() == null) {
                            return;
                        }

                        getView().onLoadFinished();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() == null) {
                            return;
                        }

                        getView().onLoadError();
                        getView().onLoadFinished();
                    }

                    @Override
                    public void onNext(List<NewsTopic> newsTopicList) {
                        if (getView() == null) {
                            return;
                        }

                        if (newsTopicList == null) {
                            getView().onLoadError();
                        }

                        if (newsTopicList.size() == 0) {
                            getView().onLoadEmpty();
                        }

                        if (newsTopicList != null && newsTopicList.size() > 0) {
                            getView().onLoadSuccess(newsTopicList);
                        }
                    }
                });

    }


    public List<NewsTopic> loadDataFromNet(String tid, int start, int end) {
        return NewsTopicModel.getInstance().loadDataFromNet(tid, start, end);
    }

    public List<NewsTopic> loadDataFromLocal(String tid, int start, int end) {
        SystemClock.sleep(500);
        return NewsTopicModel.getInstance().loadDataFromLocal(tid, start, end);
    }

    public void save2Loacl(ArrayList<NewsTopic> newsTopicList, String tid) {
        DiskLruCacheUtil.saveObject(MyApp.getInstance(), newsTopicList, tid);
    }

    public void setIsRead(NewsTopic newsTopic) {
        boolean isRead = NewsTopicModel.getInstance().isRead(newsTopic);
        if (!isRead) {
            NewsTopicModel.getInstance().setIsRead(newsTopic);
        }
    }

    public boolean checkIsRead(NewsTopic newsTopic) {
        return NewsTopicModel.getInstance().isRead(newsTopic);
    }
}
