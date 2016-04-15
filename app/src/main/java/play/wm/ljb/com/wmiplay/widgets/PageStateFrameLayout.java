package play.wm.ljb.com.wmiplay.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.manager.ThreadManager;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;


public abstract class PageStateFrameLayout extends FrameLayout {
    /**
     * 未知状态
     */
    public static final int STATE_UNKNOW = 0;
    /**
     * 正在加载状态
     */
    public static final int STATE_LOADING = 1;
    /**
     * 错误状态
     */
    public static final int STATE_ERROR = 2;
    /**
     * 空数据状态
     */
    public static final int STATE_EMPTY = 3;
    /**
     * 成功状态
     */
    public static final int STATE_SUCCEED = 4;

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSucceedView;

    public void setPageState(int mState) {
        this.mState = mState;
    }

    private int mState = STATE_UNKNOW;
    private Context mContext;

    public PageStateFrameLayout(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public PageStateFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageStateFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void init() {
        /*
        *   android:clipToPadding="false"
        *   android:fitsSystemWindows="true"
        *   配合沉淀式通知栏
        */
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.ICE_CREAM_SANDWICH ) {
            setFitsSystemWindows(true);
            setClipToPadding(false);
        }

        setBackgroundColor(XgoUIUtils.getColor(R.color.bg_page));
        mLoadingView = initLoadingView();
        mEmptyView = initEmptyVuew();
        mErrorView = initErrorView();

        if (mLoadingView != null) {
            addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        if (mEmptyView != null) {
            addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        if (mErrorView != null) {
            addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        showPage();
    }

    /**
     * 更具状态显示界面
     */
    private void showPage() {
        XgoUIUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != mLoadingView) {
                    mLoadingView.setVisibility(mState == STATE_UNKNOW || mState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
                }
                if (null != mErrorView) {
                    mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
                }
                if (null != mEmptyView) {
                    mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
                }
                if (mState == STATE_SUCCEED && mSucceedView == null) {
                    mSucceedView = initContentView();
                    addView(mSucceedView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                }
                if (mSucceedView != null) {
                    mSucceedView.setVisibility(mState == STATE_SUCCEED ? View.VISIBLE : View.INVISIBLE);
                }
            }
        });
    }

    public synchronized void show() {
        //如果上次加载是错误界面或者空界面，就回到未知状态，准备重新加载
        if (mState == STATE_ERROR || mState == STATE_EMPTY) {
            mState = STATE_UNKNOW;
        }

        showPage();

        //未知状态 或者成功状态，开始刷新数据
        if (mState == STATE_UNKNOW ) {
            mState = STATE_LOADING;
            ThreadManager.getDefaultManager().getLongThreadPool().execute(new LoadingTask());
        }


    }

    /**
     * 加载数据的任务
     */
    private class LoadingTask implements Runnable {
        @Override
        public void run() {
            SystemClock.sleep(2000);
            LoadResult loadResult = load();
            mState = loadResult.getmResult();
            showPage();
        }
    }

    /**
     * 加载内容界面数据
     */
    public abstract LoadResult load();

    /**
     * 加载数据的结果的枚举
     */
    public enum LoadResult {

        error(STATE_ERROR), success(STATE_SUCCEED), empty(STATE_EMPTY);

        private int mResult;

        LoadResult(int result) {
            this.mResult = result;
        }

        public int getmResult() {
            return mResult;
        }
    }

    /**
     * 初始化主内容界面
     */
    public abstract View initContentView();

    /**
     * 初始化错误界面
     */
    private View initErrorView() {
        View errorView = View.inflate(mContext, R.layout.loading_page_error, null);
        errorView.findViewById(R.id.page_bt).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show();
                    }
                }
        );
        return errorView;
    }

    /**
     * 初始化空界面
     */
    private View initEmptyVuew() {
        return View.inflate(mContext, R.layout.loading_page_empty, null);
    }

    /**
     * 初始化加载中界面
     */
    private View initLoadingView() {
        return View.inflate(mContext, R.layout.loading_page_load, null);
    }
}
