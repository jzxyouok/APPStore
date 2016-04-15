package play.wm.ljb.com.wmiplay.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;
import play.wm.ljb.com.wmiplay.fragment.tab.factory.TabFragmentFactory;

/**
 * Created by ljb on 2015/11/2.
 */
public class MainAdapter extends FragmentPagerAdapter{

    private String[] mDatas;

    public MainAdapter(FragmentManager fm, String[] datas) {
        super(fm);
        this.mDatas = datas;
    }

    @Override
    public BaseTabFragment getItem(int position) {
        return TabFragmentFactory.getTabFragemntInstance(position);
    }

    @Override
    public int getCount() {
        return mDatas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDatas[position];
    }
}
