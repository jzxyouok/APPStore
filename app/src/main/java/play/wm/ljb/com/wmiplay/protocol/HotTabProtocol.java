package play.wm.ljb.com.wmiplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import play.wm.ljb.com.wmiplay.moudle.HotTabBean;
import play.wm.ljb.com.wmiplay.protocol.base.BaseProtocol;

/**
 * Created by Ljb on 2015/11/12.
 */
public class HotTabProtocol  extends BaseProtocol<HotTabBean>{
    @Override
    protected HotTabBean parseJson(String json) {
        try {
            List<String> tabNames = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0 ; i<jsonArray.length() ; i++){
                String tabName = jsonArray.getString(i);
                tabNames.add(tabName);
            }
            return new HotTabBean(tabNames);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getPath() {
        return "hot";
    }
}
