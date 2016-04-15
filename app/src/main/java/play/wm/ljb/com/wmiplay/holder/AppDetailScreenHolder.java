package play.wm.ljb.com.wmiplay.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseAHolder;
import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.net.HttpUrl;

/**
 * Created by Ljb on 2015/11/12.
 */
public class AppDetailScreenHolder extends BaseAHolder<AppInfo> {

    private List<ImageView>  mScreenImages;

    public AppDetailScreenHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.holder_appdetail_screen, null);
        mScreenImages = new ArrayList<>();
        mScreenImages.add((ImageView) view.findViewById(R.id.screen_1));
        mScreenImages.add((ImageView) view.findViewById(R.id.screen_2));
        mScreenImages.add((ImageView) view.findViewById(R.id.screen_3));
        mScreenImages.add((ImageView) view.findViewById(R.id.screen_4));
        mScreenImages.add((ImageView) view.findViewById(R.id.screen_5));
        return view;
    }

    @Override
    protected void initData(AppInfo data) {
        List<String> screenUrls = data.getScreen();
        for(int i = 0 ; i<mScreenImages.size()&&i<screenUrls.size();i++){
            Glide.with(mActivity.getApplicationContext()).
                    load(HttpUrl.getImageUrl(screenUrls.get(i)))
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.default_image)
                    .into(mScreenImages.get(i));
            mScreenImages.get(i).setVisibility(View.VISIBLE);
        }
    }
}
