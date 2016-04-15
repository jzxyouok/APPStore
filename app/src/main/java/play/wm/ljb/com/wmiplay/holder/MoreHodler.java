package play.wm.ljb.com.wmiplay.holder;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.adapter.base.BaseListFAdapter;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;

/**
 * Created by Ljb on 2015/11/9.
 */
public class MoreHodler extends BaseFHolder<Integer> implements View.OnClickListener {

    public static final int LOAD_ERROR = -1;
    public static final int NOT_MORE = 0;
    public static final int HAS_MORE = 1;

    private RelativeLayout rl_more_error;
    private RelativeLayout rl_more_loading;
    private BaseListFAdapter mAdapter;

    public MoreHodler(Fragment fragment , BaseListFAdapter adapter , int state) {
        super(fragment);
        this.mAdapter = adapter;
        setData(state);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mFragment.getActivity(), R.layout.holder_load_more, null);
        rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
        rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
        rl_more_error.setOnClickListener(this);
        return view;
    }


    /**该方法在显示时，会被调用*/
    @Override
    public View getView() {
        if(getData() == HAS_MORE){
            //开始加载更多
            loadMore();
        }
        return super.getView();
    }

    /**
     * 加载更多
     * */
    private void loadMore() {
        mAdapter.loadMore();
    }

    @Override
    protected void initData(Integer data) {
        rl_more_loading.setVisibility(data==HAS_MORE?View.VISIBLE:View.GONE);
        rl_more_error.setVisibility(data == LOAD_ERROR ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_more_error:
                reLoadMore();
                break;
        }
    }

    /**重新加载*/
    private void reLoadMore() {
        if(getData()==LOAD_ERROR) {
            setData(HAS_MORE);
            loadMore();
        }
    }
}
