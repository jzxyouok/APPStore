package play.wm.ljb.com.wmiplay.holder;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class AppDetailSafeHolder extends BaseAHolder<AppInfo> implements View.OnClickListener {

    private RelativeLayout safe_layout;
    private List<ImageView> images, des_images;
    private List<LinearLayout> lines;
    private ImageView safe_arrow;
    private LinearLayout safe_content ;
    private List<TextView> des_texts;
    private  ViewGroup.LayoutParams mContentLayoutParams;

    public AppDetailSafeHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.holder_appdetail_safe, null);
        safe_layout = (RelativeLayout) view.findViewById(R.id.safe_layout);
        safe_arrow = (ImageView) view.findViewById(R.id.safe_arrow);
        safe_content = (LinearLayout) view.findViewById(R.id.safe_content);
        //隐藏内容区
        mContentLayoutParams = safe_content.getLayoutParams();
        mContentLayoutParams.height = 0;
        //默认关闭状态，tag=false
        safe_arrow.setTag(false);
        safe_layout.setOnClickListener(this);

        images = new ArrayList<>();
        images.add((ImageView) view.findViewById(R.id.iv_1));
        images.add((ImageView) view.findViewById(R.id.iv_2));
        images.add((ImageView) view.findViewById(R.id.iv_3));
        images.add((ImageView) view.findViewById(R.id.iv_4));


        lines = new ArrayList<>();
        lines.add((LinearLayout) view.findViewById(R.id.des_layout_1));
        lines.add((LinearLayout) view.findViewById(R.id.des_layout_2));
        lines.add((LinearLayout) view.findViewById(R.id.des_layout_3));
        lines.add((LinearLayout) view.findViewById(R.id.des_layout_4));


        des_images = new ArrayList<>();
        des_images.add((ImageView) view.findViewById(R.id.des_iv_1));
        des_images.add((ImageView) view.findViewById(R.id.des_iv_2));
        des_images.add((ImageView) view.findViewById(R.id.des_iv_3));
        des_images.add((ImageView) view.findViewById(R.id.des_iv_4));

        des_texts = new ArrayList<>();
        des_texts.add((TextView) view.findViewById(R.id.des_tv_1));
        des_texts.add((TextView) view.findViewById(R.id.des_tv_2));
        des_texts.add((TextView) view.findViewById(R.id.des_tv_3));
        des_texts.add((TextView) view.findViewById(R.id.des_tv_4));


        safe_content.setOnClickListener(this);

        return view;
    }


    @Override
    protected void initData(AppInfo data) {
        List<AppInfo.SafeInfo> safeInfos = data.getSafe();
        for (int i = 0; i < safeInfos.size() && i < images.size() && i < lines.size(); i++) {
            ImageView imageView = images.get(i);
            lines.get(i).setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);

            int safeDesColorType = safeInfos.get(i).getSafeDesColor();
            int color = getColorValue(safeDesColorType);

            Glide.with(mActivity.getApplicationContext()).load(HttpUrl.getImageUrl(safeInfos.get(i).getSafeUrl()))
                    .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            Glide.with(mActivity.getApplicationContext()).load(HttpUrl.getImageUrl(safeInfos.get(i).getSafeDesUrl()))
                    .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(des_images.get(i));
            des_texts.get(i).setText(safeInfos.get(i).getSafeDes());
            des_texts.get(i).setTextColor(color);
        }
    }

    private int getColorValue(int colorType) {
        int color;
        if (colorType >= 1 && colorType <= 3) {
            color = Color.rgb(255, 153, 0);
        } else if (colorType == 4) {
            color = Color.rgb(0, 177, 62);
        } else {
            color = Color.rgb(122, 122, 122);
        }
        return color;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.safe_layout:
                startValueAnim();
                break;
        }
    }

    private void startValueAnim() {
        int height ; //起始高度
        int targetHeight;//目标高度
        if(!(boolean)safe_arrow.getTag()){
            //展开
            height = 0;
            targetHeight = getTaragetHeight();
            safe_arrow.setTag(true);
            safe_arrow.setImageResource(R.drawable.arrow_up);
        }else {
            //关闭
            height = getTaragetHeight();
            targetHeight=0;
            safe_arrow.setTag(false);
            safe_arrow.setImageResource(R.drawable.arrow_down);
        }


        ValueAnimator va = ValueAnimator.ofInt(height, targetHeight);
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取当前动画的值
                mContentLayoutParams.height = (int) animation.getAnimatedValue();
                safe_content.setLayoutParams(mContentLayoutParams);
            }
        });
        va.start();
    }

    /**测量高度*/
    private int getTaragetHeight() {
        int measuredWidth = safe_content.getMeasuredWidth();
        int widthMeasureSpece=View.MeasureSpec.makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY); //宽度不变，精确值
        int heightMeasureSpec=View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST); //自动计算，最高上限1000像素
        safe_content.measure(widthMeasureSpece,heightMeasureSpec);
        return safe_content.getMeasuredHeight();
    }
}
