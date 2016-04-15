package play.wm.ljb.com.wmiplay.holder.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Ljb on 2015/11/9.
 * <p/>
 * fragment  中使用的 BaseHolder
 */
public abstract class BaseFHolder<T> {
    protected Fragment mFragment;
    private View mContentView;
    private T mData;

    public BaseFHolder(Fragment fragment) {
        this.mFragment = fragment;
        mContentView = initView();
        mContentView.setTag(this);
    }

    /**子类实现具体的要显示的View*/
    protected abstract View initView();

    /**子类实现数据相关操作*/
    protected abstract void initData(T data);

    public View getView(){
        return  mContentView;
    }

    /**设置数据 并 初始化*/
    public  void  setData(T data){
        this.mData = data;
        initData(mData);
    }

    /**返回正在使用的数据*/
    public T getData(){
        return mData;
    }
}
