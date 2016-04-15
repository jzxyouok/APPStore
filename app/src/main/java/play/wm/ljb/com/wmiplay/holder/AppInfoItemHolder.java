package play.wm.ljb.com.wmiplay.holder;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.net.HttpUrl;
import play.wm.ljb.com.wmiplay.utils.XgoFileUtils;

/**
 * Created by Ljb on 2015/11/9.
 */
public class AppInfoItemHolder extends BaseFHolder<AppInfo> {

    ImageView item_icon;
    TextView item_title;
    RatingBar item_rating;
    TextView item_size;
    TextView item_bottom;

    public AppInfoItemHolder(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected View initView() {
        View view  = View.inflate(mFragment.getActivity(), R.layout.holder_list_item, null);
        item_icon = (ImageView) view.findViewById(R.id.item_icon);
        item_title = (TextView) view.findViewById(R.id.item_title);
        item_size = (TextView) view.findViewById(R.id.item_size);
        item_bottom = (TextView) view.findViewById(R.id.item_bottom);
        item_rating = (RatingBar) view.findViewById(R.id.item_rating);
        view.setTag(this);
        return view;
    }

    @Override
    protected void initData(AppInfo data) {
        Glide.with(mFragment).load(HttpUrl.getImageUrl(data.getIconUrl())).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_default).into(item_icon);
        item_title.setText(data.getName());
        item_size.setText(XgoFileUtils.getFormatSize(data.getSize()));
        item_bottom.setText(data.getDes());
        item_rating.setRating(data.getStars());
    }
}
