package play.wm.ljb.com.wmiplay.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ljb on 2015/11/5.
 */
public class MorePageListenerViewPager extends ViewPager  {

    private List<OnPageChangeListener> mMorePageChangeListeners;

    public MorePageListenerViewPager(Context context) {
        super(context);
        init();
    }

    public MorePageListenerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mMorePageChangeListeners = new LinkedList<OnPageChangeListener>();
        setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for(OnPageChangeListener listener : mMorePageChangeListeners){
                    listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                for(OnPageChangeListener listener : mMorePageChangeListeners){
                    listener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                for(OnPageChangeListener listener : mMorePageChangeListeners){
                    listener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    public void setOnMorePageChangeListeners(OnPageChangeListener listener){
        mMorePageChangeListeners.add(listener);
    }

}
