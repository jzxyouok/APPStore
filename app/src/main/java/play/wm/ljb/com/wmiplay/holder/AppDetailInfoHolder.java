package play.wm.ljb.com.wmiplay.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseAHolder;
import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.net.HttpUrl;
import play.wm.ljb.com.wmiplay.utils.XgoFileUtils;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;

/**
 * Created by Ljb on 2015/11/12.
 */
public class AppDetailInfoHolder extends BaseAHolder<AppInfo> {

    private ImageView item_icon;
    private TextView item_title , item_download ,item_version , item_date ,item_size;
    private RatingBar item_rating;

    public AppDetailInfoHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity , R.layout.holder_appdetail_info,null);
        item_icon = (ImageView) view.findViewById(R.id.item_icon);
        item_title = (TextView) view.findViewById(R.id.item_title);
        item_download = (TextView) view.findViewById(R.id.item_download);
        item_version = (TextView) view.findViewById(R.id.item_version);
        item_date = (TextView) view.findViewById(R.id.item_date);
        item_size = (TextView) view.findViewById(R.id.item_size);
        item_rating = (RatingBar) view.findViewById(R.id.item_rating);
        return view;
    }

    @Override
    protected void initData(AppInfo data) {
        Glide.with(mActivity.getApplicationContext()).load(HttpUrl.getImageUrl(data.getIconUrl()))
                .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_default).into(item_icon);
        item_title.setText(data.getName());
        item_download.setText(XgoUIUtils.getString(R.string.app_detail_download) + data.getDownloadNum());
        item_size.setText(XgoUIUtils.getString(R.string.app_detail_size)+XgoFileUtils.getFormatSize(data.getSize()));
        item_date.setText(XgoUIUtils.getString(R.string.app_detail_time)+data.getDate());
        item_version.setText(XgoUIUtils.getString(R.string.app_detail_version)+data.getVersion());
        item_rating.setRating(data.getStars());
    }
}
