package play.wm.ljb.com.wmiplay.holder;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.ClassifyInfo;
import play.wm.ljb.com.wmiplay.net.HttpUrl;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;

/**
 * Created by Ljb on 2015/11/13.
 */
public class ClassifyItemHolder extends BaseFHolder<ClassifyInfo> {

    private RelativeLayout rl1, rl2, rl3;
    private ImageView iv1, iv2, iv3;
    private TextView tv1, tv2, tv3;

    public ClassifyItemHolder(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mFragment.getActivity(), R.layout.holder_classify_item, null);
        rl1 = (RelativeLayout) view.findViewById(R.id.rl_1);
        rl2 = (RelativeLayout) view.findViewById(R.id.rl_2);
        rl3 = (RelativeLayout) view.findViewById(R.id.rl_3);
        iv1 = (ImageView) view.findViewById(R.id.iv_1);
        iv2 = (ImageView) view.findViewById(R.id.iv_2);
        iv3 = (ImageView) view.findViewById(R.id.iv_3);
        tv1 = (TextView) view.findViewById(R.id.tv_1);
        tv2 = (TextView) view.findViewById(R.id.tv_2);
        tv3 = (TextView) view.findViewById(R.id.tv_3);
        return view;
    }

    @Override
    protected void initData(final ClassifyInfo data) {
        if (!(TextUtils.isEmpty(data.getImageUrl1()) && TextUtils.isEmpty(data.getName1()))) {
            tv1.setText(data.getName1());
            Glide.with(mFragment).load(HttpUrl.getImageUrl(data.getImageUrl1()))
                    .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_default).into(iv1);
            rl1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XgoUIUtils.showToast(data.getName1());
                }
            });
            tv1.setVisibility(View.VISIBLE);
            iv1.setVisibility(View.VISIBLE);
        }else {
            tv1.setVisibility(View.INVISIBLE);
            iv1.setVisibility(View.INVISIBLE);
        }
        if (!(TextUtils.isEmpty(data.getImageUrl2()) && TextUtils.isEmpty(data.getName2()))) {
            tv2.setText(data.getName2());
            Glide.with(mFragment).load(HttpUrl.getImageUrl(data.getImageUrl2()))
                    .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_default).into(iv2);
            rl2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XgoUIUtils.showToast(data.getName2());
                }
            });
            tv2.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.VISIBLE);
        }else {
            tv2.setVisibility(View.INVISIBLE);
            iv2.setVisibility(View.INVISIBLE);
        }
        if (!(TextUtils.isEmpty(data.getImageUrl3()) && TextUtils.isEmpty(data.getName3()))) {
            tv3.setText(data.getName3());
            Glide.with(mFragment).load(HttpUrl.getImageUrl(data.getImageUrl3()))
                    .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_default).into(iv3);
            rl3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XgoUIUtils.showToast(data.getName3());
                }
            });
            tv3.setVisibility(View.VISIBLE);
            iv3.setVisibility(View.VISIBLE);
        }else {
            tv3.setVisibility(View.INVISIBLE);
            iv3.setVisibility(View.INVISIBLE);
        }
    }
}
