package json.chao.com.wanandroid.presenter.main;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.ArticleDetailContract;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListResponse;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;


/**
 * @author quchao
 * @date 2018/2/13
 */

public class ArticleDetailPresenter extends BasePresenter<ArticleDetailContract.View> implements ArticleDetailContract.Presenter {

    DataManager mDataManager;

    @Inject
    ArticleDetailPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void addCollectArticle(int articleId) {
        addSubscribe(mDataManager.addCollectArticle(articleId)
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<FeedArticleListResponse>(mView) {
                            @Override
                            public void onNext(FeedArticleListResponse feedArticleListResponse) {
                                if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showCollectArticleData(feedArticleListResponse);
                                } else {
                                    mView.showCollectFail();
                                }
                            }
                        }));
    }

    @Override
    public void cancelCollectArticle(int articleId) {
        addSubscribe(mDataManager.cancelCollectArticle(articleId)
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<FeedArticleListResponse>(mView) {
                            @Override
                            public void onNext(FeedArticleListResponse feedArticleListResponse) {
                                if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showCancelCollectArticleData(feedArticleListResponse);
                                } else {
                                    mView.showCancelCollectFail();
                                }
                            }
                        }));
    }

    @Override
    public void cancelCollectPageArticle(int articleId) {
        addSubscribe(mDataManager.cancelCollectPageArticle(articleId)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<FeedArticleListResponse>(mView) {
                    @Override
                    public void onNext(FeedArticleListResponse feedArticleListResponse) {
                        if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            mView.showCancelCollectArticleData(feedArticleListResponse);
                        } else {
                            mView.showCancelCollectFail();
                        }
                    }
                }));
    }

    @Override
    public void shareEventPermissionVerify(RxPermissions rxPermissions) {
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        mView.shareEvent();
                    } else {
                        mView.shareError();
                    }
                });
    }

}
