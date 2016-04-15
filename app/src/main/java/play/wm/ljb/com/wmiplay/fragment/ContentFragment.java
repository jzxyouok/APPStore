package play.wm.ljb.com.wmiplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.adapter.MainAdapter;
import play.wm.ljb.com.wmiplay.fragment.tab.factory.TabFragmentFactory;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.view.MorePageListenerViewPager;
import play.wm.ljb.com.wmiplay.view.PagerSlidingTabStrip;

/**
 * Created by ljb on 2015/11/2.
 */
public class ContentFragment extends Fragment{
    private MorePageListenerViewPager mViewPager;
    private PagerSlidingTabStrip mTabStrip;
    private MainAdapter mMainAdapter;
    private  String[] tabs;
    private ViewPager.SimpleOnPageChangeListener mSimpleListener;;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content , null);
        mViewPager = (MorePageListenerViewPager) view.findViewById(R.id.viewpager);
        mTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs_strip);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabs = XgoUIUtils.getStringArray(R.array.tab_names);
        mMainAdapter = new MainAdapter(getActivity().getSupportFragmentManager() , tabs);
        mViewPager.setAdapter(mMainAdapter);
        mTabStrip.setViewPager(mViewPager);
        mTabStrip.setTextSize(XgoUIUtils.sp2px(14));
        mTabStrip.setTextColor(XgoUIUtils.getColor(R.color.tab_text_black));
        mTabStrip.setIndicatorColor(XgoUIUtils.getColor(R.color.indicatorcolor));

        mSimpleListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mMainAdapter.getItem(position).show();
            }
        };
        mViewPager.setOnMorePageChangeListeners(mSimpleListener);

        //首次进入，定位到首页HomeTabFragment
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                 mViewPager.setCurrentItem(TabFragmentFactory.TAB_ID_HOME);
                mSimpleListener.onPageSelected(TabFragmentFactory.TAB_ID_HOME);
            }
        });
    }

    public void setCurrentPage(int tabId) {
        if(mViewPager!=null){
            mViewPager.setCurrentItem(tabId , false);
        }
    }
}
