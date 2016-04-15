package play.wm.ljb.com.wmiplay.moudle;

import java.util.List;

/**
 * Created by Ljb on 2015/11/5.
 */
public class HomeTabBean {
    private List<TopPicInfo> picture;
    private List<AppInfo> list;

    public List<TopPicInfo> getPicture() {
        return picture;
    }

    public void setPicture(List<TopPicInfo> picture) {
        this.picture = picture;
    }

    public List<AppInfo> getList() {
        return list;
    }

    public void setList(List<AppInfo> list) {
        this.list = list;
    }
}
