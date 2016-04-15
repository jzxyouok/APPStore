package play.wm.ljb.com.wmiplay.act;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.base.BaseActivity;
import play.wm.ljb.com.wmiplay.db.UserTableContract;
import play.wm.ljb.com.wmiplay.db.WmiDao;
import play.wm.ljb.com.wmiplay.holder.BindHolder;
import play.wm.ljb.com.wmiplay.holder.UserInfoHolder;
import play.wm.ljb.com.wmiplay.moudle.User;
import play.wm.ljb.com.wmiplay.utils.VolleyImageUtils;
import play.wm.ljb.com.wmiplay.utils.XgoImageUtils;
import play.wm.ljb.com.wmiplay.utils.XgoLog;
import play.wm.ljb.com.wmiplay.utils.XgoSPUtils;
import play.wm.ljb.com.wmiplay.view.XgoMsgDialogFragment;

/**
 * Created by Ljb on 2015/11/23.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private static final int IMAGE_CAPTURE_RESULT = 0;
    private static final int IMAGE_PICLIB_RESULT = 1;
    private static final int IMAGE_CLIP_RESULT = 2;

    private FrameLayout mLayoutUserInfo, mLayoutBind;
    private RelativeLayout mLayoutExitUser;
    private UserInfoHolder mUserInfoHolder;
    private BindHolder mBindHolder;
    private File mImageFile;
    private String mClipImageUrl;

    private WmiDao dao;
    private User mOnLineUser;
    private ContentObserver mContentObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_user_info);
        mLayoutUserInfo = (FrameLayout) findViewById(R.id.fl_userinfo);
        mLayoutBind = (FrameLayout) findViewById(R.id.fl_bind);
        mLayoutExitUser = (RelativeLayout) findViewById(R.id.rl_exit_user);

        mUserInfoHolder = new UserInfoHolder(this);
        mBindHolder = new BindHolder(this);
        mLayoutUserInfo.addView(mUserInfoHolder.getView());
        mLayoutBind.addView(mBindHolder.getView());
        mLayoutExitUser.setOnClickListener(this);

        findViewById(R.id.iv_back).setOnClickListener(this);
        ((TextView) (findViewById(R.id.tv_title))).setText(getString(R.string.user_info));
        findViewById(R.id.iv_three_point).setVisibility(View.GONE);
    }

    private void initData() {
        dao = new WmiDao(this);
        mOnLineUser = dao.findUser(XgoSPUtils.getInt(UserTableContract.UserTable.USER_ID));
        mUserInfoHolder.setData(mOnLineUser);

        listenLoginState();
    }

    /**监听登录状态*/
    private void listenLoginState() {
        //监听数据库用户登录信息的改变
        mContentObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                XgoLog.e("login change");
                if (UserTableContract.URI_USER_ONLINE_CHANGED.equals(uri)) {
                    //发生改变
                    User user = dao.findUser(XgoSPUtils.getInt(UserTableContract.UserTable.USER_ID));
                    if(mUserInfoHolder!=null){
                        //重新设置数据，刷新UI
                        mUserInfoHolder.setData(user);
                    }
                }
            }
        };
        getContentResolver().registerContentObserver(UserTableContract.URI_USER, true
                , mContentObserver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (IMAGE_CAPTURE_RESULT == requestCode && Activity.RESULT_OK == resultCode) {
            takePocResult();
        } else if (IMAGE_PICLIB_RESULT == requestCode && data != null && Activity.RESULT_OK == resultCode) {
            picLibResult(data);
        } else if (IMAGE_CLIP_RESULT == requestCode && data != null) {
          clipPicResult(data);
        }
    }

    @Override
    protected void onDestroy() {
        getContentResolver().unregisterContentObserver(mContentObserver);
        super.onDestroy();
    }

    /** 拍照 */
    public void takePhoto() {
        mImageFile = XgoImageUtils.getImageFile();
        Uri imageFileUri = Uri.fromFile(mImageFile);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, IMAGE_CAPTURE_RESULT);
        overridePendingTransition(R.anim.tran_left_in, R.anim.tran_left_out);
    }

    /** 打开图库 */
    public void openPiclib() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_PICLIB_RESULT);
    }

    /**拍照后的回调*/
    private void takePocResult() {
        //图片是否需要旋转
        int degree = XgoImageUtils.getBitmapDegree(mImageFile.getAbsolutePath());
        if (degree != 0) {
            //旋转
            Bitmap bitmap = VolleyImageUtils.getScaledBitmap(mImageFile, 720, 1280);
            bitmap = XgoImageUtils.rotateBitmapByDegree(bitmap, degree);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(mImageFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        ContentValues localContentValues = new ContentValues();
        localContentValues.put("_data", mImageFile.getAbsolutePath());
        localContentValues.put("description", "wmi_photo");
        localContentValues.put("mime_type", "image/jpg");
        ContentResolver localContentResolver = getContentResolver();
        Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        localContentResolver.insert(localUri, localContentValues);

        // 刷新Sd卡
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + mImageFile.getAbsolutePath())));

        Intent intent = new Intent(this,
                ClipPictureActivity.class);
        intent.putExtra(ClipPictureActivity.TAG_URL, mImageFile.getAbsolutePath());
        startActivityForResult(intent, IMAGE_CLIP_RESULT);
    }


    /**打开图库后，回调回来的方法*/
    private void picLibResult(Intent data) {
        Uri selectedImage = data.getData();
        String picturePath = null;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);

        if(cursor!=null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        }else{
            picturePath = selectedImage.toString();
        }

        Intent intent = new Intent(this,ClipPictureActivity.class);
        intent.putExtra(ClipPictureActivity.TAG_URL, picturePath);
        startActivityForResult(intent, IMAGE_CLIP_RESULT);
    }

    /**剪切图片后的回调*/
    private void clipPicResult(Intent data) {
        mClipImageUrl = data.getStringExtra(ClipPictureActivity.TAG_CLIPED_URL);
        //此处只是模拟，正式项目还需将图片上传服务器
        mOnLineUser.setHeadUrl(mClipImageUrl);
        dao.updateUuser(mOnLineUser);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                back();
                break;
            case R.id.rl_exit_user:
                logout();
                break;
        }
    }

    /**
     * 注销用户
     */
    private void logout() {
        XgoMsgDialogFragment dialog_logout = new XgoMsgDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString(XgoMsgDialogFragment.TAG_CONTENT_MSG, getString(R.string.exit_login2));
        bundle.putString(XgoMsgDialogFragment.TAG_ENTER_TEXT, getString(R.string.enter));
        bundle.putString(XgoMsgDialogFragment.TAG_CANCEL_TEXT, getString(R.string.cancel));
        dialog_logout.setArguments(bundle);
        dialog_logout.setEnterCancelListener(new XgoMsgDialogFragment.EnterCancelListener() {
            @Override
            public void enter() {
                int userId = XgoSPUtils.getInt(UserTableContract.UserTable.USER_ID);
                User user = dao.findUser(userId);
                if (userId != -1 && user != null) {
                    //注销
                    user.setOnLine(User.OFF_LINE);
                    dao.updateUuser(user);
                    XgoSPUtils.putInt(UserTableContract.UserTable.USER_ID, -1);
                }
                back();
            }

            @Override
            public void cancel() {
                XgoLog.d("cancle logout user");
            }
        });
        dialog_logout.show(getSupportFragmentManager(), "dialog_logout");
    }


    @Override
    public void onBackPressed() {
        back();
    }

    public void back() {
        finish();
        overridePendingTransition(R.anim.scale_big_in, R.anim.tran_right_out);
    }
}
