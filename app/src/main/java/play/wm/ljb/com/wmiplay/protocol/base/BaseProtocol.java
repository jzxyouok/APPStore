package play.wm.ljb.com.wmiplay.protocol.base;

import android.text.TextUtils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import play.wm.ljb.com.wmiplay.net.HttpUrl;
import play.wm.ljb.com.wmiplay.utils.XgoFileUtils;
import play.wm.ljb.com.wmiplay.utils.XgoLog;

/**
 * Created by Ljb on 2015/11/6.
 */
public abstract class BaseProtocol<T> {

    private static final int DEFAULT_TIMEOUT = 5000;

    private String mUrl;
    private RequestParams mParams;
    private TreeMap<String , Object> mMapParams;
    private HttpRequest.HttpMethod mHttpMethod;
    private int mTimeOut = DEFAULT_TIMEOUT;

    /**
     * 该构造方法只是为了满足当前项目Url的特殊性，该项目的Http请求并不标准
     */
    public BaseProtocol() {
        this("", HttpRequest.HttpMethod.GET, null, DEFAULT_TIMEOUT);
    }

    public BaseProtocol(String url, HttpRequest.HttpMethod method) {
        this(url, method, null, DEFAULT_TIMEOUT);
    }

    public BaseProtocol(String url, HttpRequest.HttpMethod method, RequestParams parms) {
        this(url, method, parms, DEFAULT_TIMEOUT);
    }

    public BaseProtocol(String url, HttpRequest.HttpMethod method, int timeOut) {
        this(url, method, null, timeOut);
    }

    public BaseProtocol(String url, HttpRequest.HttpMethod method, RequestParams parms, int timeOut) {
        this.mUrl = url;
        this.mHttpMethod = method;
        this.mParams = parms;
        this.mTimeOut = timeOut;
    }

    public void setRequestParams(TreeMap<String , Object> params) {
        this.mMapParams = params;
    }

    /**
     * 如果是页码为0，则需要缓存（缓存有效时间）
     * 并在下次再次请求第0页前，读取缓存，判断缓存是否还有效
     * 有效，直接使用缓存
     * 无效，开启网络请求
     */
    public T load(int index) {

        String json = null;
        if (index == 0) {
            json = loadLocal(index+getMapParams());
        }

        if (TextUtils.isEmpty(json)) {
            json = loadNet(index);

            if (TextUtils.isEmpty(json)) {
                return null;
            } else if (index == 0) {
                saveLocal(json, index+getMapParams());
            }
        }
        return parseJson(json);
    }

    /**Josn解析*/
    protected abstract T parseJson(String json) ;

    /**加载本地缓存*/
    private String loadLocal(String index) {
        return XgoFileUtils.readCache(XgoFileUtils.getCache(getPath(), index));
    }

    /**保存缓存*/
    private void saveLocal(String netJson, String index) {
        XgoFileUtils.writeCache(XgoFileUtils.getCache(getPath(), index), netJson);
    }

    /**网路加载数据*/
    private String loadNet(int index) {
        String responseResult = null;
        HttpUtils httpUtils = new HttpUtils(mTimeOut);
        ResponseStream responseStream;
        try {
            //responseStream = httpUtils.sendSync(mHttpMethod, mUrl, mParams);
            XgoLog.d(HttpUrl.ROOT + getPath() + "?index=" + index+getMapParams());
            responseStream = httpUtils.sendSync(mHttpMethod, HttpUrl.ROOT + getPath() + "?index=" + index+getMapParams());
            responseResult = responseStream.readString();
            responseStream.close();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        XgoLog.d("responseResult::" + responseResult);
        return responseResult;
    }

    /**Url路径组合path*/
    protected abstract String getPath();


    /**
     * 由于本地服务器的http协议不标准，此方法只是针对这种情况添加参数
     * */
    private   String getMapParams(){
        String params = "";
        if(mMapParams!=null){
            Set<Map.Entry<String, Object>> entries = mMapParams.entrySet();
            for(Map.Entry e :entries){
                params+="&"+e.getKey()+"="+e.getValue();
            }
        }
            return params;
    }
}
