package play.wm.ljb.com.wmiplay.adapter.base;

import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import play.wm.ljb.com.wmiplay.holder.MoreHodler;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.manager.ThreadManager;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;

/**
 * Created by Ljb on 2015/11/6.
 */
public abstract class BaseListFAdapter<T> extends BaseAdapter {

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_LOAD_MORE = 1;


    public List<T> getData() {
        return mDatas;
    }

    public void setData(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    protected List<T> mDatas;
    protected Fragment mFragment;
    private MoreHodler mMoreHodler;

    public BaseListFAdapter(Fragment fragment, List<T> datas) {
        this.mFragment = fragment;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size()+1;
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public final int getItemViewType(int position) {
        if (getCount() - 1 == position) {
            return TYPE_LOAD_MORE;
        }
        return getItemType(position);
    }

    /**
     * 普通Item类型
     * */
    protected int getItemType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseFHolder mHolder;
        if (getItemViewType(position) == TYPE_LOAD_MORE) {
            mHolder = getMoreHolder();
        } else {
            if (convertView == null) {
                mHolder = getFHolder(position);
            } else {
                mHolder = (BaseFHolder) convertView.getTag();
            }
            mHolder.setData(getItem(position));
        }
        return mHolder.getView();
    }

    protected abstract BaseFHolder getFHolder(int position);

    /**
     * 加载更多的Holder
     */
    private BaseFHolder getMoreHolder() {
        if (mMoreHodler == null) {
            mMoreHodler = new MoreHodler(mFragment, this, hasMoreData());
        }
        return mMoreHodler;
    }

    /**
     * 是否支持加载更多
     * 默认支持 MoreHodler.HAS_MORE
     */
    protected int hasMoreData() {
        return MoreHodler.HAS_MORE;
    }

    private volatile boolean isLoading = false;
    /**
     * 加载更多
     */
    public final void loadMore() {
        /**子线程加载数据*/
        if(!isLoading) {
            ThreadManager.getDefaultManager().getLongThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    isLoading = true;
                    SystemClock.sleep(1500);
                    final List<T> newDatas = onLoadMore();
                    XgoUIUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (newDatas == null) {
                                //请求失败
                                mMoreHodler.setData(MoreHodler.LOAD_ERROR);
                            } else if (newDatas.size() == 0) {
                                //没有数据
                                mMoreHodler.setData(MoreHodler.NOT_MORE);
                            } else {
                                //请求成功
                                // mMoreHodler.setData(MoreHodler.HAS_MORE);
                                if (mDatas != null) {
                                    mDatas.addAll(newDatas);
                                } else {
                                    mDatas = newDatas;
                                }
                                BaseListFAdapter.this.notifyDataSetChanged();
                            }
                            //加载结束
                            isLoading = false;
                        }
                    });
                }
            });
        }
    }

    /**
     * 子类实现具体的加载更多,无加载更多无需复写
     * 返回：
     *      null : 链接服务器失败
     *      datas.size()=0 ；已无数据
     *      datas : 新数据
     */
    protected   List<T> onLoadMore(){
        return  new ArrayList<>();
    }
}
