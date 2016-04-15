package play.wm.ljb.com.wmiplay.view;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import play.wm.ljb.com.wmiplay.R;

/**
 * Created by Ljb on 2015/11/18.
 */
public class ShareDialog implements View.OnClickListener {

    private Activity mActivity;
    private Dialog mDialog;
    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");

    private SocializeListeners.SnsPostListener mSnsShareListener;
    private String mTitle;
    private String mContentMsg;
    private int mIconId;
    private String mIconUrl;
    private String mWebUrl;

    public ShareDialog(Activity act) {
        this.mActivity = act;
        initDialog();
        initUMengInfo();
    }

    /**
     * 初始化Dialog
     */
    private void initDialog() {
        mDialog = new Dialog(mActivity, R.style.mask_dialog);
        mDialog.setCanceledOnTouchOutside(true);

        View dialgView = View.inflate(mActivity, R.layout.dialog_share, null);
        // 获取对话框布局中的控件，并设置事件监听
        dialgView.findViewById(R.id.share_ll_sina).setOnClickListener(this);
        dialgView.findViewById(R.id.share_ll_friend).setOnClickListener(this);
        dialgView.findViewById(R.id.share_ll_weixin).setOnClickListener(this);
        dialgView.findViewById(R.id.share_ll_qq).setOnClickListener(this);
        dialgView.findViewById(R.id.share_ll_qz).setOnClickListener(this);
        dialgView.findViewById(R.id.share_cancle).setOnClickListener(this);

        mDialog.setContentView(dialgView, new RelativeLayout.LayoutParams(mActivity
                .getWindowManager().getDefaultDisplay().getWidth(),
                WindowManager.LayoutParams.MATCH_PARENT));
        mDialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);

        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL);
        dialogWindow.setWindowAnimations(R.style.bottom_out_style);
    }

    /**
     * 初始化友盟分享组件
     */
    private void initUMengInfo() {
        //添加QQ平台
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity, "yourAppId",
                "yourAppKey");
        qqSsoHandler.addToSocialSDK();

        // 添加QQ空间平台
        QZoneSsoHandler qzoneHandler = new QZoneSsoHandler(mActivity, "yourAppId",
                "yourAppKey");
        qzoneHandler.addToSocialSDK();

        // 微信
        UMWXHandler wxHandler = new UMWXHandler(mActivity, "yourAppId",
                "yourSrcret");
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(mActivity, "yourAppId",
                "yourSrcret");
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    public ShareDialog setShareData(String title, String contentMsg, int iconId, String webUrl) {
        this.mTitle = title;
        this.mContentMsg = contentMsg;
        this.mIconId = iconId;
        this.mWebUrl = webUrl;
        return this;
    }

    public ShareDialog setShareData(String title, String contentMsg, String iconUrl, String webUrl) {
        this.mTitle = title;
        this.mContentMsg = contentMsg;
        this.mIconUrl = iconUrl;
        this.mWebUrl = webUrl;
        return this;
    }

    public void show() {
        if (mDialog == null) {
            throw new RuntimeException("Do not create ShareDialog!");
        }
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


    private SocializeListeners.SnsPostListener getSnsPostListener() {
        if (mSnsShareListener == null) {
            mSnsShareListener = new SocializeListeners.SnsPostListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onComplete(SHARE_MEDIA platform, int stCode,
                                       SocializeEntity entity) {
                    if (stCode == 200) {
                        Log.d("share", "share success");
                    } else {
                        Log.d("share", "share failure");
                    }
                }
            };
        }
        return mSnsShareListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_ll_sina:
                shareSina();
                break;
            case R.id.share_ll_weixin:
                shareWX_GoodFreand();
                break;
            case R.id.share_ll_friend:
                shareWX_FreandsCircle();
                break;
            case R.id.share_ll_qq:
                shareQQ();
                break;
            case R.id.share_ll_qz:
                shareQQzone();
                break;
            case R.id.share_cancle:
                break;
        }
        dismiss();
    }

    /**
     * 分享QQ空间
     */
    private void shareQQzone() {
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(mContentMsg);
        qzone.setTargetUrl(mWebUrl);
        qzone.setTitle(mTitle);
        if (TextUtils.isEmpty(mIconUrl)) {
            qzone.setShareImage(new UMImage(mActivity, mIconId));
        } else {
            qzone.setShareImage(new UMImage(mActivity, mIconUrl));
        }
        mController.setShareMedia(qzone);
        mController.postShare(mActivity, SHARE_MEDIA.QZONE, getSnsPostListener());
    }


    /**
     * 分享QQ
     */
    private void shareQQ() {
        QQShareContent qq = new QQShareContent();
        qq.setShareContent(mContentMsg);
        qq.setTargetUrl(mWebUrl);
        qq.setTitle(mTitle);
        if (TextUtils.isEmpty(mIconUrl)) {
            qq.setShareImage(new UMImage(mActivity, mIconId));
        } else {
            qq.setShareImage(new UMImage(mActivity, mIconUrl));
        }
        mController.setShareMedia(qq);
        mController.postShare(mActivity, SHARE_MEDIA.QQ, getSnsPostListener());
    }

    /**
     * 分享微信朋友圈
     */
    private void shareWX_FreandsCircle() {
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(mContentMsg);
        circleMedia.setTitle(mTitle);
        circleMedia.setTargetUrl(mWebUrl);
        if (TextUtils.isEmpty(mIconUrl)) {
            circleMedia.setShareImage(new UMImage(mActivity, mIconId));
        } else {
            circleMedia.setShareImage(new UMImage(mActivity, mIconUrl));
        }
        mController.setShareMedia(circleMedia);
        mController.postShare(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE, getSnsPostListener());
    }

    /**
     * 分享微信好友
     */
    private void shareWX_GoodFreand() {
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(mContentMsg);
        weixinContent.setTitle(mTitle);
        weixinContent.setTargetUrl(mWebUrl);
        if (TextUtils.isEmpty(mIconUrl)) {
            weixinContent.setShareImage(new UMImage(mActivity, mIconId));
        } else {
            weixinContent.setShareImage(new UMImage(mActivity, mIconUrl));
        }
        mController.setShareMedia(weixinContent);
        mController.postShare(mActivity, SHARE_MEDIA.WEIXIN, getSnsPostListener());
    }

    /**
     * 分享到新浪
     */
    private void shareSina() {
        // 设置文字分享内容
        mController.setShareContent(mContentMsg);
        // 图片分享内容
        if (TextUtils.isEmpty(mIconUrl)) {
            mController.setShareImage(new UMImage(mActivity, mIconId));
        } else {
            mController.setShareImage(new UMImage(mActivity, mIconUrl));
        }
        //链接
        mController.setAppWebSite(mWebUrl);
        // 调用直接分享, 但是在分享前用户可以编辑要分享的内容
        mController.postShare(mActivity, SHARE_MEDIA.SINA, getSnsPostListener());
    }
}
