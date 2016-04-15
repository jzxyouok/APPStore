package play.wm.ljb.com.wmiplay.fragment.tab.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import play.wm.ljb.com.wmiplay.widgets.PageStateFrameLayout;

/**
 * Created by Ljb on 2015/11/5.
 */
public abstract class BaseTabFragment<T> extends Fragment {
    private PageStateFrameLayout mStateFrameLayout;
    protected T mData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mStateFrameLayout==null) {
            mStateFrameLayout = new PageStateFrameLayout(getActivity()) {

                @Override
                public View initContentView() {
                    return BaseTabFragment.this.initContentView();
                }

                @Override
                public LoadResult load() {
                    return BaseTabFragment.this.load();
                }
            };
        }else{
            //避免引起挂在多个Parent
            ViewParent parent = mStateFrameLayout.getParent();
            if(parent instanceof  ViewGroup){
                ((ViewGroup)parent).removeView(mStateFrameLayout);
            }
        }
        return mStateFrameLayout;
    }

    /**初始化内容界面*/
    public abstract View initContentView();

    /**加载数据，需要手动将数据赋值给mData,返回加载完成状态*/
    public abstract PageStateFrameLayout.LoadResult load();

    public void show(){
        if(mStateFrameLayout != null){
            mStateFrameLayout.show();
        }
    }


    /**
     * 检查数据完整性
     *
     * @param data
     * @return
     */
    public abstract  PageStateFrameLayout.LoadResult checkData(T data);
}
