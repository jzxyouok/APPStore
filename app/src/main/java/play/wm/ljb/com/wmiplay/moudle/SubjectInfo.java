package play.wm.ljb.com.wmiplay.moudle;

/**
 * Created by Ljb on 2015/11/6.
 */
public class SubjectInfo {
    private String des;
    private String url;

    public SubjectInfo(String des, String url) {
        this.des = des;
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
