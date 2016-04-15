package play.wm.ljb.com.wmiplay.view;

import android.app.AlertDialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;


/**
 * 白块Dialog
 * Created by ljb on 2015/10/13.
 */
public class WhiteBlockDialog {

    private Context mContext;
    private AlertDialog mDialog;

    private LinearLayout mParentView;
    private int mWidth = XgoUIUtils.dip2px(150);
    private int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int mChildCount;
    private int mItemHeight = XgoUIUtils.dip2px(45);
    private int mTextColor = XgoUIUtils.getColor(R.color.indicatorcolor);
    /**
     * UNIT: SP
     * */
    private float mTextSize = 16;


    public WhiteBlockDialog(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        mDialog = new AlertDialog.Builder(mContext).create();
        mParentView = (LinearLayout) View.inflate(mContext, R.layout.dialog_longclick_menu, null);
    }


    /**
     * 添加一个子菜单
     */
    public WhiteBlockDialog addItem(String itemText, final View.OnClickListener clickListener) {

        if (mChildCount > 0) {
            View lineView = new View(mContext);
            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, XgoUIUtils.dip2px(0.5f));
            lineParams.leftMargin = XgoUIUtils.dip2px(10);
            lineParams.rightMargin = XgoUIUtils.dip2px(10);
            lineView.setLayoutParams(lineParams);
            lineView.setBackgroundColor(XgoUIUtils.getColor(R.color.line_color));
            mParentView.addView(lineView);
        }

        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,mItemHeight);
        layoutParams.setMargins(3,1,3,1);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(mTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        textView.setText(itemText);
        textView.setBackgroundResource(R.drawable.selector_menu_item);
        if(clickListener!=null) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(v);
                    dismiss();
                }
            });
        }
        mParentView.addView(textView);
        mChildCount++;
        return this;
    }


    public void show() {
        if (mDialog != null) {
            mDialog.show();
            initParams();
        }
    }

    public void dismiss(){
        if(mDialog!=null) {
            mDialog.dismiss();
        }
    }

    private void initParams() {
        android.view.WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = mWidth;
        lp.height = mHeight;
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().setContentView(mParentView);
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }

    /**
     * UNIT: SP
     * */
    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    public void setTextColor(int resId) {
        this.mTextColor = resId;
    }
}
