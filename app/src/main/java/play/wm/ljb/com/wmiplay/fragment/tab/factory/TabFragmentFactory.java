package play.wm.ljb.com.wmiplay.fragment.tab.factory;

import java.util.HashMap;
import java.util.Map;

import play.wm.ljb.com.wmiplay.fragment.tab.AppTabFragment;
import play.wm.ljb.com.wmiplay.fragment.tab.ClassifyTabFragment;
import play.wm.ljb.com.wmiplay.fragment.tab.GameTabFragment;
import play.wm.ljb.com.wmiplay.fragment.tab.HomeTabFragment;
import play.wm.ljb.com.wmiplay.fragment.tab.HotTabFragment;
import play.wm.ljb.com.wmiplay.fragment.tab.SubjectTabFragment;
import play.wm.ljb.com.wmiplay.fragment.tab.WelfareTabFrahment;
import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;

/**
 * Created by ljb on 2015/9/25.
 */
public class TabFragmentFactory {

    public static final int TAB_ID_HOME = 0;
    public static final int TAB_ID_APP = 1;
    public static final int TAB_ID_GAME = 2;
    public static final int  TAB_ID_SUBJECT =3;
    public static final int  TAB_ID_CLASSIFT =4;
    public static final int  TAB_ID_HOT =5;
    public static final int TAB_ID_WELFARE = 6;

    private static Map<Integer, BaseTabFragment> mTabFragments = new HashMap<Integer , BaseTabFragment>();

    public static BaseTabFragment getTabFragemntInstance(int pageId) {
        BaseTabFragment mTabFragment = mTabFragments.get(pageId);
        if (mTabFragment == null) {
            switch (pageId) {
                case TAB_ID_HOME:
                    mTabFragment = new HomeTabFragment();
                    break;
                case TAB_ID_APP:
                    mTabFragment = new AppTabFragment();
                    break;
                case TAB_ID_GAME:
                    mTabFragment = new GameTabFragment();
                    break;
                case TAB_ID_SUBJECT:
                    mTabFragment = new SubjectTabFragment();
                break;
                case TAB_ID_CLASSIFT:
                    mTabFragment = new ClassifyTabFragment();
                break;
                case TAB_ID_HOT:
                    mTabFragment = new HotTabFragment();
                break;
                case TAB_ID_WELFARE:
                    mTabFragment = new WelfareTabFrahment();
                    break;
            }
            mTabFragments.put(pageId, mTabFragment);
        }

        return mTabFragment;
    }
}
