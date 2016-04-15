package play.wm.ljb.com.wmiplay.adapter;

import android.support.v4.app.Fragment;

import java.util.List;

import play.wm.ljb.com.wmiplay.adapter.base.BaseListFAdapter;
import play.wm.ljb.com.wmiplay.holder.ClassifyItemHolder;
import play.wm.ljb.com.wmiplay.holder.ClassifyTitleHolder;
import play.wm.ljb.com.wmiplay.holder.MoreHodler;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.ClassifyInfo;

/**
 * Created by Ljb on 2015/11/13.
 */
public class ClassifyTabAdapter extends BaseListFAdapter<ClassifyInfo> {

    private static final int TYPE_TITLE = -1;

    public ClassifyTabAdapter(Fragment fragment, List<ClassifyInfo> datas) {
        super(fragment, datas);
    }

    @Override
    protected BaseFHolder getFHolder(int position) {
        if(getItemType(position)==TYPE_TITLE){
                return new ClassifyTitleHolder(mFragment);
        }
        return new ClassifyItemHolder(mFragment);
    }


    @Override
    public int getViewTypeCount() {
        //多了一种条目
        return super.getViewTypeCount()+1;
    }

    @Override
    protected int getItemType(int position) {
        ClassifyInfo classifyInfo = mDatas.get(position);
        if (classifyInfo.isTitle()){
            return TYPE_TITLE;
        }
        return TYPE_ITEM;
    }

    @Override
    protected int hasMoreData() {
        return MoreHodler.NOT_MORE;
    }
}
