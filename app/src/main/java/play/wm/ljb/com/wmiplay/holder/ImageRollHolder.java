package play.wm.ljb.com.wmiplay.holder;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.adapter.HomeHeadAdapter;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.TopPicInfo;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;

/**
 * Created by Ljb on 2015/11/10.
 */
public class ImageRollHolder extends BaseFHolder<List<TopPicInfo>> {
    private ViewPager mViewPager;
    private TextView mDesTextView;
    private LinearLayout mPointGroup;
    private HomeHeadAdapter mHomeHeadAdapter;
    private AutoLooperTask mAutoLooperTask;

    public ImageRollHolder(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mFragment.getActivity(), R.layout.holder_image_looper, null);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mDesTextView = (TextView) view.findViewById(R.id.tv_des);
        mPointGroup = (LinearLayout) view.findViewById(R.id.ll_point_group);

        view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, XgoUIUtils.dip2px(140)));
        mViewPager.setOnTouchListener(new PagerOnTouchListener());
        initViewPagerScroll();

        return view;
    }

    @Override
    protected void initData(List<TopPicInfo> data) {
        initPoint(data);
        initPageChangeListener(data);

        if (mHomeHeadAdapter == null) {
            mHomeHeadAdapter = new HomeHeadAdapter(mFragment, data);
            mViewPager.setAdapter(mHomeHeadAdapter);
        } else {
            mHomeHeadAdapter.setData(data);
            mHomeHeadAdapter.notifyDataSetChanged();
        }

        //初始化第一页
        mViewPager.setCurrentItem(0, false);
        mPointGroup.getChildAt(0).setEnabled(true);
        mDesTextView.setText(data.get(0).getPicDes());

        startAutoLooper();
    }

    private void initPageChangeListener(final List<TopPicInfo> data) {
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            private int prePostion;
            @Override
            public void onPageSelected(int position) {
                mDesTextView.setText(data.get(position).getPicDes());
                //把当前的View下对应的指示点高亮显示
                mPointGroup.getChildAt(position).setEnabled(true);
                //把上次高亮的变为不亮
                mPointGroup.getChildAt(prePostion).setEnabled(false);
                prePostion = position;
            }
        });
    }

    private void initPoint(List<TopPicInfo> data) {
        mPointGroup.removeAllViews();
        //初始化点
        for (int i = 0; i < data.size(); i++) {
            // 实例化指示点
            ImageView point = new ImageView(mFragment.getActivity());
            point.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.leftMargin = 15;
            point.setLayoutParams(params);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            //添加指示点
            mPointGroup.addView(point);
        }
    }

    /**
     * 开启自动轮训图片
     */
    private void startAutoLooper() {
        if (mAutoLooperTask == null) {
            mAutoLooperTask = new AutoLooperTask(new Handler());
        } else {
            mAutoLooperTask.stop();
        }
        mAutoLooperTask.start();
    }


    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(mViewPager.getContext());
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {
        }
    }

    /**
     * 自动轮训任务
     */
    class AutoLooperTask implements Runnable {
        private final int DELAY_TIME = 3000;
        private Handler mHandler;
        private boolean autoRun;

        public AutoLooperTask(Handler mainUiHandler) {
            this.mHandler = mainUiHandler;
        }

        public void start() {
            if (!autoRun) {
                autoRun = true;
                mHandler.postDelayed(this, DELAY_TIME);
            }
        }

        public void stop() {
            if (autoRun) {
                autoRun = false;
                mHandler.removeCallbacks(this);
            }
        }

        @Override
        public void run() {
            if (autoRun) {
                int currentItem = mViewPager.getCurrentItem();
                if (++currentItem == mHomeHeadAdapter.getCount()) {
                    mViewPager.setCurrentItem(0, false);
                } else {
                    mViewPager.setCurrentItem(currentItem, true);
                }
                mHandler.postDelayed(this, DELAY_TIME);
            }
        }
    }

    /**
     * 自定义Scroller，用于调节ViewPager滑动速度
     */
    public class ViewPagerScroller extends Scroller {
        private int mScrollDuration = 1200;// 滑动速度

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }
    }

    /**
     * ViewPager触摸事件
     */
    class PagerOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mAutoLooperTask != null) {
                        mAutoLooperTask.stop();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (mAutoLooperTask != null) {
                        mAutoLooperTask.start();
                    }
                    break;
            }
            return false;
        }
    }
}
