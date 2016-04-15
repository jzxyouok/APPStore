package play.wm.ljb.com.wmiplay.holder;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.AppDetailActivity;
import play.wm.ljb.com.wmiplay.holder.base.BaseAHolder;
import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;

/**
 * Created by Ljb on 2015/11/12.
 */
public class AppDetailDesHolder extends BaseAHolder<AppInfo> implements View.OnClickListener {

    private static final int DEFAULE_LINE = 7;
    private RelativeLayout des_bottom;
    private TextView des_content,des_author;
    private ImageView des_arrow;

    public AppDetailDesHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        View view  = View.inflate(mActivity, R.layout.holder_appdetail_des , null);
        des_bottom = (RelativeLayout) view.findViewById(R.id.des_bottom);
        des_content = (TextView) view.findViewById(R.id.des_content);
        des_author = (TextView) view.findViewById(R.id.des_author);
        des_arrow = (ImageView) view.findViewById(R.id.des_arrow);

        //隐藏多余高度
        des_content.getLayoutParams().height = getDefaultHeight();
        des_bottom.setOnClickListener(this);
        des_arrow.setTag(false);
        des_arrow.setVisibility(View.VISIBLE);

        return view;
    }

    /**返回一个默认行数的高度*/
    private int getDefaultHeight() {
        TextView copy_content  = new TextView(mActivity);
        copy_content.setLayoutParams(des_content.getLayoutParams());
        copy_content.setTextSize(TypedValue.COMPLEX_UNIT_DIP , 15);
        copy_content.setMaxLines(DEFAULE_LINE);
        copy_content.setLines(DEFAULE_LINE);
        int measuredWidth = des_content.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000 , View.MeasureSpec.AT_MOST);
        copy_content.measure(widthMeasureSpec, heightMeasureSpec);
        return copy_content.getMeasuredHeight();
    }

    @Override
    protected void initData(AppInfo data) {
        des_content.setText(data.getDes());
        des_author.setText(XgoUIUtils.getString(R.string.des_author) + data.getAuthor());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.des_bottom:
                if(des_content.getLineCount()>DEFAULE_LINE) {
                    startValueAnim();
                }
                break;
        }
    }

    private void startValueAnim() {
        int height ; //起始高度
        int targetHeight;//目标高度
        if(!(boolean)des_arrow.getTag()){
            //展开
            height = getDefaultHeight();
            targetHeight = getTaragetHeight();
            des_arrow.setTag(true);
            des_arrow.setImageResource(R.drawable.arrow_up);
        }else {
            //关闭
            height = getTaragetHeight();
            targetHeight=getDefaultHeight();
            des_arrow.setTag(false);
            des_arrow.setImageResource(R.drawable.arrow_down);
        }

        final ViewGroup.LayoutParams layoutParams = des_content.getLayoutParams();
        ValueAnimator va  = ValueAnimator.ofInt(height,targetHeight);
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                layoutParams.height = (int) animation.getAnimatedValue();
                des_content.setLayoutParams(layoutParams);

                ScrollView scrollView = getScrollView();
                if(scrollView!=null){
                    scrollView.scrollTo(0, des_content.getBottom());
                }
            }
        });
        va.start();
    }


    private ScrollView getScrollView() {
        if(mActivity instanceof AppDetailActivity){
            return ((AppDetailActivity)mActivity).getScrollView();
        }
        return  null;
    }

    /**计算目标高度*/
    private int getTaragetHeight() {
        int measuredWidth = des_content.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000 , View.MeasureSpec.AT_MOST);
        des_content.measure(widthMeasureSpec, heightMeasureSpec);
        return des_content.getMeasuredHeight();
    }
}
