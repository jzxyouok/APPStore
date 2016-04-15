package play.wm.ljb.com.wmiplay.fragment;

import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.LoginActivity;
import play.wm.ljb.com.wmiplay.act.MainActivity;
import play.wm.ljb.com.wmiplay.act.UserInfoActivity;
import play.wm.ljb.com.wmiplay.adapter.LeftMenuAdapter;
import play.wm.ljb.com.wmiplay.db.UserTableContract;
import play.wm.ljb.com.wmiplay.db.WmiDao;
import play.wm.ljb.com.wmiplay.fragment.tab.factory.TabFragmentFactory;
import play.wm.ljb.com.wmiplay.moudle.MenuBean;
import play.wm.ljb.com.wmiplay.moudle.User;
import play.wm.ljb.com.wmiplay.utils.XgoLog;
import play.wm.ljb.com.wmiplay.utils.XgoSPUtils;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;
import play.wm.ljb.com.wmiplay.view.CircleImageView;
import play.wm.ljb.com.wmiplay.view.XgoMsgDialogFragment;

/**
 * Created by ljb on 2015/11/2.
 */
public class LeftDrawerFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final int ITEM_HOME = 0;
    public static final int ITEM_SETTING = 1;
    public static final int ITEM_THEME = 2;
    private static final int ITME_SCANS = 3;
    private static final int ITEM_FEEDBACK = 4;
    private static final int ITEM_UPDATE = 5;
    private static final int ITEM_ABOUT = 6;
    private static final int ITEM_EXIT = 7;

    private RelativeLayout rl_user;
    private CircleImageView iv_userHead;
    private TextView tv_username, tv_usertype;
    private ListView mListView;
    private List<MenuBean> mMenus;
    private ContentObserver mContentObserver;
    private WmiDao dao ;
    private BitmapUtils bu;


    private  int mUserId;
    private User mLoginedUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new WmiDao(getActivity());
        bu =  new BitmapUtils(getActivity());
        bu.configDefaultLoadingImage(R.drawable.default_image);

        mMenus = new ArrayList<>();
        String[] item_texts = XgoUIUtils.getStringArray(R.array.menu_items_text);
        mMenus.add(ITEM_HOME, new MenuBean(R.drawable.ic_home, item_texts[0]));
        mMenus.add(ITEM_SETTING, new MenuBean(R.drawable.ic_setting, item_texts[1]));
        mMenus.add(ITEM_THEME, new MenuBean(R.drawable.ic_theme, item_texts[2]));
        mMenus.add(ITME_SCANS, new MenuBean(R.drawable.ic_scans, item_texts[3]));
        mMenus.add(ITEM_FEEDBACK, new MenuBean(R.drawable.ic_feedback, item_texts[4]));
        mMenus.add(ITEM_UPDATE, new MenuBean(R.drawable.ic_updates, item_texts[5]));
        mMenus.add(ITEM_ABOUT, new MenuBean(R.drawable.ic_about, item_texts[6]));
        mMenus.add(ITEM_EXIT, new MenuBean(R.drawable.ic_exit, item_texts[7]));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_drawer, null);
        rl_user = (RelativeLayout) view.findViewById(R.id.rl_user);
        iv_userHead = (CircleImageView) view.findViewById(R.id.user_head);
        tv_username = (TextView) view.findViewById(R.id.user_name);
        tv_usertype = (TextView) view.findViewById(R.id.user_type);
        mListView = (ListView) view.findViewById(R.id.lv_menu);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setAdapter(new LeftMenuAdapter(this, mMenus));
        mListView.setOnItemClickListener(this);
        rl_user.setOnClickListener(new LoginOrUserInfoClick());

        listenLogin();
    }

    /**监听登录状态*/
    private void listenLogin() {
        //检测当前是否有用户登录信息
        checkDB();

        //监听数据库用户登录信息的改变
        mContentObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                XgoLog.e("login change");
                if (UserTableContract.URI_USER_ONLINE_CHANGED.equals(uri)) {
                    //发生改变
                    checkDB();
                }
            }
        };
        getActivity().getContentResolver().registerContentObserver(UserTableContract.URI_USER, true
                , mContentObserver);
    }

    private void checkDB() {
        //判断当前是否已有登录用户
        if(isLogined()){
            String headUrl = mLoginedUser.getHeadUrl();
            if(TextUtils.isEmpty(headUrl)){ //没有头像使用默认图片
                iv_userHead.setImageResource(R.mipmap.icon);
            }else{
                bu.display(iv_userHead, headUrl);
            }
            tv_username.setText(mLoginedUser.getNickname());
            tv_usertype.setText(User.getTypeText(mLoginedUser.getUserType()));
        }else{
            iv_userHead.setImageResource(R.drawable.user);
            tv_username.setText(R.string.login);
            tv_usertype.setText(R.string.unknow_user);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case ITEM_HOME:
                menu_goHome();
                break;
            case ITEM_EXIT:
                menu_exit();
                break;
            default:
                XgoUIUtils.showToast(mMenus.get(position).getMenuText());
                break;
        }
    }

    /**菜单-首页*/
    private void menu_goHome() {
        if (getActivity() instanceof MainActivity) {
            MainActivity mainAct = (MainActivity) getActivity();
            mainAct.setCurrentPage(TabFragmentFactory.TAB_ID_HOME);
        }
    }

    /**菜单-退出*/
    private void menu_exit() {
        XgoMsgDialogFragment msgDialogFragment = new XgoMsgDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(XgoMsgDialogFragment.TAG_CONTENT_MSG, getString(R.string.exit));
        bundle.putString(XgoMsgDialogFragment.TAG_ENTER_TEXT, getString(R.string.enter));
        bundle.putString(XgoMsgDialogFragment.TAG_CANCEL_TEXT, getString(R.string.cancel));
        msgDialogFragment.setArguments(bundle);

        msgDialogFragment.setEnterCancelListener(new XgoMsgDialogFragment.EnterCancelListener() {
            @Override
            public void enter() {
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainAct = (MainActivity) getActivity();
                    mainAct.exit();
                } else {
                    getActivity().finish();
                }
            }

            @Override
            public void cancel() {
                XgoLog.d("cancel exit");
            }
        });
        msgDialogFragment.show(getFragmentManager(), "exit_dialog");
    }

    /**用户头像点击事件*/
    private class LoginOrUserInfoClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(isLogined()){
               startActivity(new Intent(getActivity() , UserInfoActivity.class));
            }else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
            getActivity().overridePendingTransition(R.anim.tran_left_in, R.anim.scale_smaall_out);
        }
    }

    /**是否登录*/
    private boolean isLogined() {
        mUserId = XgoSPUtils.getInt(UserTableContract.UserTable.USER_ID);
        mLoginedUser = dao.findUser(mUserId);
        //用户ID 以及数据库中用户信息存在 则为已登录
        if(mUserId !=-1 && mLoginedUser !=null){
            return true;
        }
        return false;
    }
}
