package play.wm.ljb.com.wmiplay;


import android.app.Application;
import android.os.Handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import play.wm.ljb.com.wmiplay.utils.XgoFileUtils;
import play.wm.ljb.com.wmiplay.utils.XgoLog;

public class XgoApplication extends Application {
	private static XgoApplication application;
	private static int myTid;
	private static Handler handler;
	
	@Override
	public void onCreate() {
		super.onCreate();
		application=this;
		myTid = android.os.Process.myTid();
		handler=new Handler();

		//exceptionCapture();
	}

	/**
	 * 错误异常捕获
	 * */
	private void exceptionCapture() {
		//获取当前正在运行的线程
		Thread me_th = Thread.currentThread();

		//设置一个未捕获到的异常处理器
		me_th.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				XgoLog.e("捕获未处理异常::" + ex.toString());
				try {
					//创建日志打印流
					PrintStream ps = new PrintStream(new File(XgoFileUtils.getLogDir(),"wmi_error.log"));
					//写入打印流中
					ex.printStackTrace(ps);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}finally {
					//此处处理的异常并不能真正处理,为此一旦抛出未处理异常,让程序闪退,以免ANR
					//通过一个进程的pid干掉当前进程
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}
		});
	}

	public static XgoApplication getApplication() {
		return application;
	}
	
	public static int getMyTid() {
		return myTid;
	}
	
	public static Handler getHandler() {
		return handler;
	}
	
}
