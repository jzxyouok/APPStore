package play.wm.ljb.com.wmiplay.holder;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.holder.base.BaseFHolder;
import play.wm.ljb.com.wmiplay.moudle.MenuBean;

/**
 * Created by Ljb on 2015/11/10.
 */
public class LeftMenuItemHolder extends BaseFHolder<MenuBean> {

    private ImageView iv_image;
    private TextView tv_text;

    public LeftMenuItemHolder(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected View initView() {
       View view = View.inflate(mFragment.getActivity() , R.layout.menu_item , null);
        iv_image = (ImageView) view.findViewById(R.id.iv_image);
        tv_text = (TextView) view.findViewById(R.id.tv_text);
        return view;
    }

    @Override
    protected void initData(MenuBean data) {
        iv_image.setImageResource(data.getIamgeResId());
        tv_text.setText(data.getMenuText());
    }
}
