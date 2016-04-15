package play.wm.ljb.com.wmiplay.act;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.TreeMap;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.base.BaseActivity;
import play.wm.ljb.com.wmiplay.holder.AppDetailBottomHolder;
import play.wm.ljb.com.wmiplay.holder.AppDetailDesHolder;
import play.wm.ljb.com.wmiplay.holder.AppDetailInfoHolder;
import play.wm.ljb.com.wmiplay.holder.AppDetailSafeHolder;
import play.wm.ljb.com.wmiplay.holder.AppDetailScreenHolder;
import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.protocol.AppDetailProtocol;
import play.wm.ljb.com.wmiplay.widgets.PageStateFrameLayout;

/**
 * Created by Ljb on 2015/11/11.
 */
public class AppDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String PACKAGENAME = "packageName";

    private PageStateFrameLayout mPageState;
    private AppDetailProtocol mProtocol;
    private String mPackagename;
    private AppInfo mAppInfo;
    private ScrollView mScrollView;

    private ImageView iv_back, iv_three_point;
    private TextView tv_title;
    private FrameLayout bottom_layout, detail_info, detail_safe, detail_des;
    private HorizontalScrollView detail_screen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPackagename = getIntent().getStringExtra(PACKAGENAME);

        mPageState = new PageStateFrameLayout(this) {
            @Override
            public LoadResult load() {
                return AppDetailActivity.this.load();
            }

            @Override
            public View initContentView() {
                return AppDetailActivity.this.initContentView();
            }
        };
        setContentView(mPageState);
        mPageState.show();
    }

    /**
     * 加载成功显示的界面
     */
    private View initContentView() {
        View view = View.inflate(this, R.layout.activity_appdetail, null);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        view.findViewById(R.id.iv_three_point).setVisibility(View.GONE);

        tv_title.setText(mAppInfo.getName());
        iv_back.setOnClickListener(this);

        bottom_layout = (FrameLayout) view.findViewById(R.id.bottom_layout);
        detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
        detail_safe = (FrameLayout) view.findViewById(R.id.detail_safe);
        detail_des = (FrameLayout) view.findViewById(R.id.detail_des);
        detail_screen = (HorizontalScrollView) view.findViewById(R.id.detail_screen);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollview);

        /*
            所有Holder中Glide都是用act.getApplicationContxt()上下文 , 不同步Activity生命周期
            避免快速开启 和关闭Act时 ，产生ANR的Bug
         */
        AppDetailBottomHolder bottomHolder = new AppDetailBottomHolder(this);
        AppDetailInfoHolder infoHolder = new AppDetailInfoHolder(this);
        AppDetailSafeHolder safeHolder = new AppDetailSafeHolder(this);
        AppDetailDesHolder desHolder = new AppDetailDesHolder(this);
        AppDetailScreenHolder screenHolder = new AppDetailScreenHolder(this);
        bottomHolder.setData(mAppInfo);
        infoHolder.setData(mAppInfo);
        safeHolder.setData(mAppInfo);
        desHolder.setData(mAppInfo);
        screenHolder.setData(mAppInfo);

        bottom_layout.addView(bottomHolder.getView());
        detail_info.addView(infoHolder.getView());
        detail_safe.addView(safeHolder.getView());
        detail_des.addView(desHolder.getView());
        detail_screen.addView(screenHolder.getView());

        return view;
    }

    /**
     * 加载数据
     */
    private PageStateFrameLayout.LoadResult load() {
        TreeMap<String, Object> params = new TreeMap();
        params.put(AppDetailActivity.PACKAGENAME, mPackagename);
        mProtocol = new AppDetailProtocol();
        mProtocol.setRequestParams(params);
        mAppInfo = mProtocol.load(0);
        return mAppInfo != null ? PageStateFrameLayout.LoadResult.success : PageStateFrameLayout.LoadResult.error;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public ScrollView getScrollView() {
        return mScrollView;
    }
}
