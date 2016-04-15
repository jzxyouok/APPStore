package play.wm.ljb.com.wmiplay.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Ljb on 2015/11/13.
 */
public class XgoColorUtils {

    /**随机产生颜色*/
    public static int  gteRandomColor(){
        Random random = new Random();
        return Color.rgb(random.nextInt(200)+20,random.nextInt(200)+20,random.nextInt(200)+20);
    }

}
