package play.wm.ljb.com.wmiplay.act;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import play.wm.ljb.com.wmiplay.R;
import play.wm.ljb.com.wmiplay.act.base.BaseActivity;


/**
 * Created by Ljb on 2015/11/16.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private EditText mSearch;
    private ImageView iv_back,iv_del;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearch = (EditText) findViewById(R.id.et_search);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_del = (ImageView) findViewById(R.id.iv_del);
        mSearch.setFocusable(true);
        mSearch.requestFocus();

        initListener();
    }

    private void initListener() {
        iv_back.setOnClickListener(this);
        iv_del.setOnClickListener(this);
        mSearch.addTextChangedListener(new SearchTextChangeListener());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                back();
                break;
            case R.id.iv_del:
                clearEditText();
                break;
        }
    }

    private void back() {
        finish();
        overridePendingTransition(R.anim.not_thing_100,R.anim.not_thing_100);
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void clearEditText() {
        if(!TextUtils.isEmpty(mSearch.getText())){
            mSearch.setText("");
        }
    }

    /**
     * 搜素内容监听器
     * */
    private class SearchTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           changUI();
        }


        @Override
        public void afterTextChanged(Editable s) {
        }

        private void changUI() {
            if(!TextUtils.isEmpty(mSearch.getText())){
                if(iv_del.getVisibility()!=View.VISIBLE){
                    iv_del.setVisibility(View.VISIBLE);
                }
            }else {
                iv_del.setVisibility(View.GONE);
            }
        }
    }
}
