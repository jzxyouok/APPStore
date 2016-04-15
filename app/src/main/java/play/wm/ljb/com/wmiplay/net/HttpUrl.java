package play.wm.ljb.com.wmiplay.net;

/**
 * Created by Ljb on 2015/11/5.
 */
public class HttpUrl {

    public static final String ROOT = "http://127.0.0.1:8090/";
    // http://127.0.0.1:8090/home
    // http://127.0.0.1:8090/home?index=0
    public static final String TAB_HOME = ROOT + "home";

    public static String getImageUrl(String path){
        return ROOT + "image?name="+ path;
    }
}
