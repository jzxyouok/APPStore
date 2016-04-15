package play.wm.ljb.com.wmiplay.fragment.tab;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.fragment.tab.base.BaseTabFragment;
import play.wm.ljb.com.wmiplay.moudle.HotTabBean;
import play.wm.ljb.com.wmiplay.protocol.HotTabProtocol;
import play.wm.ljb.com.wmiplay.utils.XgoColorUtils;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.widgets.HotLabelLayout;
import play.wm.ljb.com.wmiplay.widgets.PageStateFrameLayout;

/**
 * Created by ljb on 2015/11/2.
 */
public class HotTabFragment extends BaseTabFragment<HotTabBean> {

    @Override
    public View initContentView() {
        View view = View.inflate(getActivity(), R.layout.tab_fragment_hot, null);
        HotLabelLayout hotLabelLayout = new HotLabelLayout(getActivity());
        hotLabelLayout.setBackgroundResource(R.drawable.grid_item_bg_normal);
        int pading = XgoUIUtils.dip2px(13);
        hotLabelLayout.setPadding(pading,pading,pading,pading);
        //按下的shape
        Drawable pressedDrawable  = XgoUIUtils.createLableShape(0xffcecece);
        for(String lable : mData.getTabNames()){
            final TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            textView.setText(lable);
            textView.setTextColor(Color.WHITE);
            int tv_pading_w = XgoUIUtils.dip2px(12);
            int tv_pading_h = XgoUIUtils.dip2px(10);
            textView.setPadding(tv_pading_w, tv_pading_h, tv_pading_w, tv_pading_h);
            //默认显示的shape
            GradientDrawable normalDrawable = XgoUIUtils.createLableShape(XgoColorUtils.gteRandomColor());
            textView.setBackgroundDrawable(XgoUIUtils.crealeLableSelector(normalDrawable,pressedDrawable));

            textView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    XgoUIUtils.showToast(textView.getText().toString());
                }
            });
            hotLabelLayout.addView(textView);
        }
        if(view instanceof ViewGroup){
            ((ViewGroup) view).addView(hotLabelLayout);
           // ((ViewGroup) view).addView(linearLayout);
        }
        return view;
    }

    @Override
    public PageStateFrameLayout.LoadResult load() {
        HotTabProtocol mProtocol = new HotTabProtocol();
        mData = mProtocol.load(0);
        return checkData(mData);
    }

    @Override
    public PageStateFrameLayout.LoadResult checkData(HotTabBean data) {
        if (data == null) {
            return PageStateFrameLayout.LoadResult.error;
        } else if (data.getTabNames() == null || data.getTabNames().size() == 0) {
            return PageStateFrameLayout.LoadResult.empty;
        } else {
            return PageStateFrameLayout.LoadResult.success;
        }
    }

}
