package play.wm.ljb.com.wmiplay.moudle;

/**
 * Created by Ljb on 2015/11/18.
 */
public class TopPicInfo {
    private String picUrl;
    private String picDes;

    public  TopPicInfo(){}
    public TopPicInfo(String picUrl, String picDes) {
        this.picUrl = picUrl;
        this.picDes = picDes;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicDes() {
        return picDes;
    }

    public void setPicDes(String picDes) {
        this.picDes = picDes;
    }
}
