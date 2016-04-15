package play.wm.ljb.com.wmiplay.moudle;

/**
 * Created by Ljb on 2015/11/17.
 */
public class WelfareInfo {
    private int welfareImageId;
    private String welfareName;

    public WelfareInfo(int welfareImageId, String welfareName) {
        this.welfareImageId = welfareImageId;
        this.welfareName = welfareName;
    }

    public int getWelfareImageId() {
        return welfareImageId;
    }

    public void setWelfareImageId(int welfareImageId) {
        this.welfareImageId = welfareImageId;
    }

    public String getWelfareName() {
        return welfareName;
    }

    public void setWelfareName(String welfareName) {
        this.welfareName = welfareName;
    }
}
