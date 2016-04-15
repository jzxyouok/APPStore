package com.ljb.game.torn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.ljb.game.torn.view.ShowGirlView;

import play.wm.ljb.com.wmiplay.act.base.BaseActivity;

public class ShowGirlActivity extends BaseActivity {

	Bitmap bitmap;//声明位图类
	ShowGirlView sgv;//声明ShowGirlView


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();//实例化intent 接受值 也就是上一个页面中的位置值
		int num = intent.getIntExtra("num", 0);//实例化 int类型的num 来接受值

		int width = getWindowManager().getDefaultDisplay().getWidth();//得到屏幕的宽度
		int height = getWindowManager().getDefaultDisplay().getHeight();//得到屏幕的长度
		sgv = new ShowGirlView(ShowGirlActivity.this, width, height, num);//实例化ShowActivity类

		setContentView(sgv);//使用ShowActivity类作为布局
	}

	@Override
	protected void onDestroy() {//生命周期 onDestroy()
		if(null != sgv)
			sgv.realease();//realease()是用来回收内存的 可以到ShowGirlView中找到其方法
		super.onDestroy();
	}

}
