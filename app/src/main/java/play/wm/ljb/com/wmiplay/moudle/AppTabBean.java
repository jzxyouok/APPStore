package play.wm.ljb.com.wmiplay.moudle;

import java.util.List;

/**
 * Created by Ljb on 2015/11/9.
 */
public class AppTabBean {

    List<AppInfo> list;

    public AppTabBean(List<AppInfo> list) {
        this.list = list;
    }

    public List<AppInfo> getList() {
        return list;
    }

    public void setList(List<AppInfo> list) {
        this.list = list;
    }
}
