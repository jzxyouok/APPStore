package play.wm.ljb.com.wmiplay.protocol;


import com.google.gson.Gson;

import play.wm.ljb.com.wmiplay.moudle.HomeTabBean;
import play.wm.ljb.com.wmiplay.protocol.base.BaseProtocol;

/**
 * Created by Ljb on 2015/11/5.
 */
public class HomeTabProtocol extends BaseProtocol<HomeTabBean> {

    @Override
    protected HomeTabBean parseJson(String json) {
        Gson gson = new Gson();
        return   gson.fromJson(json,HomeTabBean.class);
    }

    @Override
    public String getPath() {
        return "home";
    }
}
