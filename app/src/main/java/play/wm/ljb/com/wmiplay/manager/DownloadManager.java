package play.wm.ljb.com.wmiplay.manager;

import android.content.Intent;
import android.net.Uri;

import com.lidroid.xutils.HttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.moudle.DownloadInfo;
import play.wm.ljb.com.wmiplay.utils.XgoUIUtils;

public class DownloadManager {
	/** 默认 */
	public static final int STATE_NONE = 0;
	/** 等待 */
	public static final int STATE_WAITING = 1;
	/** 下载中 */
	public static final int STATE_DOWNLOADING = 2;
	/** 暂停 */
	public static final int STATE_PAUSE = 3;
	/** 错误 */
	public static final int STATE_ERROR = 4;
	/** 下载完成 */
	public static final int STATE_DOWNLOED = 5;

	private static DownloadManager instance;

	private DownloadManager() {
	}

	/** 用于记录下载信息,如果是正式项目,需要持久化保存 */
	private Map<Long, DownloadInfo> mDownloadMap = new ConcurrentHashMap<Long, DownloadInfo>();
	/**用于记录所有下载的任务,方便取消下载时,能通过id找到该任务进行删除*/
	private Map<Long,DownloadTask> mTaskMap=new ConcurrentHashMap<Long, DownloadManager.DownloadTask>();

	private List<DownloadObserver> mObservers=new ArrayList<DownloadObserver>();

	/** 注册观察者 */
	public void registerObserver(DownloadObserver observer) {
		synchronized (mObservers) {
			if (!mObservers.contains(observer)) {
				mObservers.add(observer);
			}
		}
	}

	/** 反注册观察者 */
	public void unRegisterObserver(DownloadObserver observer) {
		synchronized (mObservers) {
			if (mObservers.contains(observer)) {
				mObservers.remove(observer);
			}
		}
	}
	/** 当下载状态发送改变的时候回调 */
	public void notifyDownloadStateChanged(DownloadInfo info) {
		synchronized (mObservers) {
			for (DownloadObserver observer : mObservers) {
				observer.onDownloadStateChanged(info);
			}
		}
	}

	/** 当下载进度发送改变的时候回调 */
	public void notifyDownloadProgressed(DownloadInfo info) {
		synchronized (mObservers) {
			for (DownloadObserver observer : mObservers) {
				observer.onDownloadProgressed(info);
			}
		}
	}
	// 单例
	public static synchronized DownloadManager getInstance() {
		if (instance == null) {
			instance = new DownloadManager();
		}
		return instance;
	}

	public synchronized void download(AppInfo appInfo) {
		DownloadInfo info = mDownloadMap.get(appInfo.getId());
		if (info == null) {
			info = DownloadInfo.clone(appInfo);
			mDownloadMap.put((long) appInfo.getId(), info);// 保存到集合中
		}
		if (info.getDownloadState() == STATE_NONE
				|| info.getDownloadState() == STATE_PAUSE
				|| info.getDownloadState() == STATE_ERROR) {
			// 下载之前，把状态设置为STATE_WAITING，
			// 因为此时并没有产开始下载，只是把任务放入了线程池中，
			// 当任务真正开始执行时，才会改为STATE_DOWNLOADING
			info.setDownloadState(STATE_WAITING);
			// 通知更新界面 
			notifyDownloadStateChanged(info);
			DownloadTask task = new DownloadTask(info);
			mTaskMap.put(info.getId(), task);
			ThreadManager.getDefaultManager().getLongThreadPool().execute(task);
		}

	}
	/** 安装应用 */
	public synchronized void install(AppInfo appInfo) {
		stopDownload(appInfo);
		DownloadInfo info = mDownloadMap.get(appInfo.getId());// 找出下载信息
		if (info != null) {// 发送安装的意图
			Intent installIntent = new Intent(Intent.ACTION_VIEW);
			installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			installIntent.setDataAndType(Uri.parse("file://" + info.getPath()),
					"application/vnd.android.package-archive");
			XgoUIUtils.getContext().startActivity(installIntent);
		}
		notifyDownloadStateChanged(info);
	}

	/**暂停下载*/
	public synchronized void pause(AppInfo appInfo){
		stopDownload(appInfo);
		DownloadInfo info=mDownloadMap.get(appInfo.getId());
		if(info!=null){// 修改下载状态
			info.setDownloadState(STATE_PAUSE);
			notifyDownloadStateChanged(info);
		}
	}
	/** 取消下载，逻辑和暂停类似，只是需要删除已下载的文件 */
	public synchronized void cancel(AppInfo appInfo) {
		stopDownload(appInfo);
		DownloadInfo info = mDownloadMap.get(appInfo.getId());// 找出下载信息
		if (info != null) {// 修改下载状态并删除文件
			info.setDownloadState(STATE_NONE);
			notifyDownloadStateChanged(info);
			info.setCurrentSize(0);
			File file = new File(info.getPath());
			file.delete();
		}
	}
	private void stopDownload(AppInfo appInfo) {
		DownloadTask task=mTaskMap.remove(appInfo.getId());
		if(task!=null){
			ThreadManager.getDefaultManager().getLongThreadPool().cancel(task);
		}
	}

	public class DownloadTask implements Runnable {
		private DownloadInfo info;
		private HttpUtils http;
		public DownloadTask(DownloadInfo info) {
			this.info = info;
		}

		@Override
		public void run() {
//			HttpUtils h;
//			ResponseStream stream = h.sendSync(HttpRequest.HttpMethod.GET, "", null);

			/*
			info.setDownloadState(STATE_DOWNLOADING);
			notifyDownloadStateChanged(info);
			File file = new File(info.getPath());// 获取下载文件
			HttpResult httpResult = null;
			InputStream stream = null;
			// 如果文件不存在, 或者进度为0,或者进度和文件长度不一致 重新下载
			if (info.getCurrentSize() == 0 || !file.exists()
					|| file.length() != info.getCurrentSize()) {
				info.setCurrentSize(0);
				file.delete();
				httpResult = HttpHelper.download(HttpHelper.URL
						+ "download?name=" + info.getUrl());

			} else {
				// 不需要重新下载
				// 文件存在且长度和进度相等，采用断点下载
				httpResult = HttpHelper.download(HttpHelper.URL
						+ "download?name=" + info.getUrl() + "&range="
						+ info.getCurrentSize());
			}
			if (httpResult == null
					|| (stream = httpResult.getInputStream()) == null) {
				info.setDownloadState(STATE_ERROR);
				notifyDownloadStateChanged(info);
			} else {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(file, true);
					int count = -1;
					byte[] buffer = new byte[1024];
					while (((count = stream.read(buffer)) != -1)
							&& info.getDownloadState() == STATE_DOWNLOADING) {
						fos.write(buffer, 0, count);
						fos.flush();
						info.setCurrentSize(info.getCurrentSize()+count);
						notifyDownloadProgressed(info);
					}
				} catch (Exception e) {
					LogUtils.e(e);// 出异常后需要修改状态并删除文件
					info.setDownloadState(STATE_ERROR);
					notifyDownloadStateChanged(info);
					info.setCurrentSize(0);
					file.delete();
				} finally {
					IOUtils.close(fos);
					if (httpResult != null) {
						httpResult.close();
					}
				}
				// 判断进度是否和App相等
				if (info.getCurrentSize() == info.getAppSize()) {
					info.setDownloadState(STATE_DOWNLOED);
					notifyDownloadStateChanged(info);
				} else if (info.getDownloadState() == STATE_PAUSE) {
					notifyDownloadStateChanged(info);
				} else {
					info.setDownloadState(STATE_ERROR);
					notifyDownloadStateChanged(info);
					info.setCurrentSize(0);
					file.delete();
				}
			}
			mTaskMap.remove(info.getId());
			*/
		}
	}
	public interface DownloadObserver {

		public void onDownloadStateChanged(DownloadInfo info);

		public void onDownloadProgressed(DownloadInfo info);
	}
	public DownloadInfo getDownloadInfo(long id) {
		// TODO Auto-generated method stub
		return mDownloadMap.get(id);
	}
}
