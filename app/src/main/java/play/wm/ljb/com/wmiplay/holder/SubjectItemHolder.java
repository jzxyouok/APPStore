package play.wm.ljb.com.wmiplay.holder;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.SubjectInfo;
import play.wm.ljb.com.wmiplay.net.HttpUrl;

/**
 * Created by Ljb on 2015/11/9.
 */
public class SubjectItemHolder extends BaseFHolder<SubjectInfo> {
    ImageView item_icon;
    TextView item_text;

    public SubjectItemHolder(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected View initView() {
       View  view = View.inflate(mFragment.getActivity(), R.layout.holder_subject_item,null);
        item_icon = (ImageView) view.findViewById(R.id.item_icon);
        item_text = (TextView) view.findViewById(R.id.item_txt);
        return view;
    }

    @Override
    protected void initData(SubjectInfo data) {
        Glide.with(mFragment).load(HttpUrl.getImageUrl(data.getUrl()))
                .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.btn_pressed).into(item_icon);
        item_text.setText(data.getDes());
    }
}
