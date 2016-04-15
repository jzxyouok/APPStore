package play.wm.ljb.com.wmiplay.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.base.BaseActivity;
import play.wm.ljb.com.wmiplay.utils.XgoLog;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.view.ActionSheetDialog;

/**
 * Created by Lei on 2015/11/8.
 */
public class WebViewActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG_TYPE = "fromType";
    public static final String TAG_URL = "url";

    public static final String TYPE_FROM_WELCOME = "WELCOME";
    public static final String TYPE_FROM_H5 = "H5";

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_three_point;
    private ProgressBar pb_load;
    private WebView mWebView;
    private String mFromType = TYPE_FROM_H5;
    private String mUrl;
    private ViewGroup mPage_error;
    private boolean isError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        iniData();
    }

    private void initView() {
        setContentView(R.layout.activity_webview);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_three_point = (ImageView) findViewById(R.id.iv_three_point);
        pb_load = (ProgressBar) findViewById(R.id.pb_load);
        mWebView = (WebView) findViewById(R.id.webview);
        mPage_error = (ViewGroup) findViewById(R.id.page_error);

        iv_back.setOnClickListener(this);
        iv_three_point.setOnClickListener(this);
    }

    private void iniData() {
        mFromType = getIntent().getStringExtra(TAG_TYPE);
        mUrl = getIntent().getStringExtra(TAG_URL);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                XgoLog.d("WebRes：：" + url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pb_load.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pb_load.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    openCallPhone(url);
                } else {
                    view.loadUrl(mUrl = url);
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                XgoLog.e("code:" + errorCode + "\r\ndescription:" + description + "\r\nfailingUrl:" + failingUrl);
                tv_title.setText(getString(R.string.web_error));
                mPage_error.setVisibility(View.VISIBLE);
                mWebView.setVisibility(View.GONE);
                mPage_error.findViewById(R.id.page_bt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshWebView();
                    }
                });
            }

        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                pb_load.setProgress(progress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                tv_title.setText(title);
            }
        });

        if (!TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                back();
                break;
            case R.id.iv_three_point:
                showBottomMenu();
                break;
        }
    }

    private void showBottomMenu() {
        new ActionSheetDialog(this)
                .builder()
                .setTitle(XgoUIUtils.getString(R.string.please_select))
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem(XgoUIUtils.getString(R.string.refresh),
                        ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                refreshWebView();
                            }

                        })
                .addSheetItem(XgoUIUtils.getString(R.string.browser),
                        ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openBrowser();
                            }

                        }).show();
    }

    /**
     * 在浏览器中打开
     */
    private void openBrowser() {
        XgoLog.e("openBrowser");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uri = Uri.parse(mUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }).start();
    }

    /**
     * 刷新页面
     */
    private void refreshWebView() {
        if (!TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
            if (mWebView.getVisibility() != View.VISIBLE) {
                mWebView.setVisibility(View.VISIBLE);
                mPage_error.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 拨打网页中的电话
     */
    private void openCallPhone(String phoneUrl) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneUrl));
        startActivity(intent);
    }


    private void back() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            tv_title.setText(mWebView.getTitle());
        } else {
            if (TYPE_FROM_WELCOME.equals(mFromType)) {
                startActivity(new Intent(WebViewActivity.this, MainActivity.class));
            }
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }
}
