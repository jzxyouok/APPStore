package play.wm.ljb.com.wmiplay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.LinkedList;
import java.util.List;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.moudle.TopPicInfo;
import play.wm.ljb.com.wmiplay.net.HttpUrl;

/**
 * Created by Ljb on 2015/11/10.
 */
public class HomeHeadAdapter extends PagerAdapter {
    public List<TopPicInfo> getData() {
        return mDatas;
    }

    public void setData(List<TopPicInfo> mDatas) {
        this.mDatas = mDatas;
    }

    private List<TopPicInfo> mDatas;
    private Fragment mFragment;

    //优化Item缓存
    LinkedList<ImageView> mCacheViews = new LinkedList<>();

    public HomeHeadAdapter(Fragment fragment, List<TopPicInfo> datas) {
        this.mDatas = datas;
        this.mFragment = fragment;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
      //  position%=mDatas.size();
        String picUrl = mDatas.get(position).getPicUrl();

        ImageView iv;
        if(mCacheViews.size()>0){
            iv =mCacheViews.remove(0);
        }else{
            iv = new ImageView(mFragment.getActivity());
            iv.setLayoutParams(new ViewPager.LayoutParams());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        iv.setImageResource(R.drawable.default_image);
        Glide.with(mFragment).load(HttpUrl.getImageUrl(picUrl)).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.default_image).into(iv);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mCacheViews.add((ImageView)object);
    }
}
