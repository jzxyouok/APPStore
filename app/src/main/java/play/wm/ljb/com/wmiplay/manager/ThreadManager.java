package play.wm.ljb.com.wmiplay.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ljb on 2015/11/5.
 */
public class ThreadManager {

    private static final ThreadManager mThreadManager= new ThreadManager();

    private ThreadManager(){}

    public static ThreadManager getDefaultManager(){
        return mThreadManager;
    }

    private ThreadProxy mProxy;

    public synchronized  ThreadProxy getLongThreadPool(){
        if(mProxy==null){
            mProxy = new ThreadProxy(3,3,5000);
        }
        return mProxy;
    }

    public synchronized  ThreadProxy getShortThreadPool(){
        if(mProxy==null){
            mProxy = new ThreadProxy(3,3,2000);
        }
        return mProxy;
    }

    public class  ThreadProxy{
        private ThreadPoolExecutor mPool;

        private int mCoreSize;
        private int mMaxSize;
        private int mKeepTime;

        public ThreadProxy(int mCoreSize, int mMaxSize, int mKeepTime) {
            this.mCoreSize = mCoreSize;
            this.mMaxSize = mMaxSize;
            this.mKeepTime = mKeepTime;
        }

        //CPU 核数*2+1
        public void execute(Runnable run){
            /*
            * 参数1：线程池保留的线程数
            * 参数2：额外线程数
            * 参数3：线程存活时间
            * 参数4：时间单位
            * 参数5：LinkedBlockingQueue<Runnable>(num) 额外任务栈
            *
            * */
            if(mPool==null) {
                mPool = new ThreadPoolExecutor(mCoreSize,mMaxSize,mKeepTime, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(10),
                        Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            }
            mPool.execute(run);
        }

        public void cancel(Runnable run){
            if(mPool!=null&&!mPool.isShutdown()&&!mPool.isTerminated()){
                mPool.getQueue().remove(run);
            }
        }
    }
}
