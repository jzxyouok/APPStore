package play.wm.ljb.com.wmiplay.protocol;

import com.google.gson.Gson;

import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.protocol.base.BaseProtocol;

/**
 * Created by Ljb on 2015/11/11.
 */
public class AppDetailProtocol extends BaseProtocol<AppInfo> {


    @Override
    protected AppInfo parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, AppInfo.class);
    }

    @Override
    protected String getPath() {
        return "detail";
    }
}
