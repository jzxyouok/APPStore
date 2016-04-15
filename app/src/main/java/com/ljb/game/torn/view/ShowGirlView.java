package com.ljb.game.torn.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ljb.game.torn.tool.ImageResource;

public class ShowGirlView extends View {

	Bitmap fore_bitmap;//声明Bitmap类
	Bitmap back_bitmap;//声明Bitmap类
	Bitmap temp_bitmap;//声明Bitmap类
	Canvas canvasTemp;//声明画布类
	Paint paint;//声明画笔类
	Path path;//画图类

	int startX;//声明startX变量
	int startY;//声明startY变量

	int width;//声明width变量
	int height;//声明height变量
	int num;//声明num变量
	Matrix matrix;//使用矩阵控制图片移动、缩放、旋转  
	ImageResource ir;//声明ImageResource类

	public ShowGirlView(Context context, int width, int height, int num) {//构造方法
		super(context);
		this.width = width;
		this.height = height;
		this.num = num;
		init();
	}

	public ShowGirlView(Context context, AttributeSet attrs, int width, int height, int num) {//构造方法
		super(context, attrs);
		this.width = width;
		this.height = height;
		this.num = num;
		init();
	}

	void init() {
		/*
        *   android:clipToPadding="false"
        *   android:fitsSystemWindows="true"
        *   配合沉淀式通知栏
        */
		if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.ICE_CREAM_SANDWICH ) {
			setFitsSystemWindows(true);
		}

		ir = ImageResource.getImageResource();//实例化 ImageResource类

		temp_bitmap = ir.getPreBitmap(getResources(), num);//实例化Bitmap类 temp_bitmap
		back_bitmap = ir.getAfterBitmap(getResources(), num);//实例化Bitmap类 back_bitmap
		fore_bitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);//创建一个位图 ARGB_4444 代表16位ARGB位图

		paint = new Paint();//实例化paint类 画笔类
		paint.setAntiAlias(true);//设置画笔的锯齿效果
		paint.setDither(true);// 设置递色 不是太清楚

		canvasTemp = new Canvas(fore_bitmap);//实例化 画布 fore_bitmap类当参数
		canvasTemp.drawColor(Color.TRANSPARENT);//设置背景颜色为透明的
		matrix = new Matrix();//实例化Matrix类
		matrix.setScale(width*1.0F/temp_bitmap.getWidth(),
				height*1.0F/temp_bitmap.getHeight());//缩放  它采用两个浮点数作为参数，分别表示在每个轴上所产生的缩放量。第一个参数是x轴的缩放比例，而第二个参数是y轴的缩放比例。
		canvasTemp.drawBitmap(temp_bitmap, matrix, paint);//绘制图像
		temp_bitmap.recycle();//回收

		paint.setColor(Color.RED);//设置画笔颜色
		paint.setStrokeWidth(20);//设置描边宽度
		BlurMaskFilter bmf = new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL);//指定了一个模糊的样式和半径来处理Paint的边缘。
		paint. setMaskFilter(bmf);//为Paint分配边缘效果。
		paint.setStyle(Paint.Style.STROKE);//让画出的图形是空心的
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));//它的作用是用此画笔后，画笔划过的痕迹就变成透明色了。画笔设置好了后，就可以调用该画笔进行橡皮痕迹的绘制了
		paint.setStrokeJoin(Paint.Join.ROUND);//设置结合处的样子 Miter:结合处为锐角， Round:结合处为圆弧：BEVEL：结合处为直线。
		paint.setStrokeCap(Paint.Cap.SQUARE);//画笔笔刷类型   方形形状

		path = new Path();//实例化画图类
	}


	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawBitmap(back_bitmap, matrix, null);//绘制图像
		canvas.drawBitmap(fore_bitmap, 0, 0, null);//绘制图像
	}


	public boolean onTouchEvent(MotionEvent event) {

		int x = (int) event.getX();//获得触摸的X轴位置
		int y = (int) event.getY();//获得触摸的Y轴位置
		int position = event.getAction();//获得的返回值 获取触控动作比如ACTION_DOWN

		int endX = 0;//声明变量endX
		int endY = 0;//声明变量endY

		switch (position)
		{
			case MotionEvent.ACTION_DOWN://当触摸时按下时
				startX = x;
				startY = y;
				endX = x;
				endY = y;
				break;
			case MotionEvent.ACTION_MOVE://当触摸移动时
				endX = x;
				endY = y;
				break;
			case MotionEvent.ACTION_UP://当触摸结束时
				endX = x;
				endY = y;
				break;
		}

		path.moveTo(startX, startY);//起始点  
		path.lineTo(endX, endY);//终点
		canvasTemp.drawPath(path, paint);//绘制图像

		postInvalidate();//刷新界面

		startX = endX;//将 endX值 也就是停止触摸时X轴的位置 付给 startX当中
		startY = endY;//将 endY值 也就是停止触摸时X轴的位置 付给 startY当中

		return true;
	}

	public void realease(){//用来回收内存
		if(null != back_bitmap && !fore_bitmap.isRecycled())
			back_bitmap.recycle();
		if(null != fore_bitmap && !fore_bitmap.isRecycled())
			fore_bitmap.recycle();
	}
}
