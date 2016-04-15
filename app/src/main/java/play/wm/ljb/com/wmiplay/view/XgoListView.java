package play.wm.ljb.com.wmiplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import play.wm.ljb.com.wmiplay.utils.XgoLog;

/**
 * Created by Ljb on 2015/11/10.
 */
public class XgoListView extends ListView implements AdapterView.OnItemClickListener {

    private OnItemClickListener mOnItemClickListener = null;

    public XgoListView(Context context) {
        super(context);
    }

    public XgoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XgoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setOnItemClickListener(
            android.widget.AdapterView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
        super.setOnItemClickListener(this);
    }

    /**
     * 自身的子项被点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        XgoLog.e("getCount::" + getCount() + ",position::" + position + ",getHeaderViewsCount::" +getHeaderViewsCount() + ",getFooterViewsCount::"+getFooterViewsCount());
        if (getHeaderViewsCount() == 0) {
            if (mOnItemClickListener != null && position < getCount() - getFooterViewsCount() -1) {
                mOnItemClickListener.onItemClick(parent, view,
                        position - this.getHeaderViewsCount(), id);
            }
        } else {
            if (position >= getHeaderViewsCount() &&
            mOnItemClickListener != null && position < getCount() - getFooterViewsCount()-1)
            {
                mOnItemClickListener.onItemClick(parent, view,
                        position - this.getHeaderViewsCount(), id);
            }
        }
    }
}
