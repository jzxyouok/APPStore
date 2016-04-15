package play.wm.ljb.com.wmiplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import play.wm.ljb.com.wmiplay.moudle.AppInfo;
import play.wm.ljb.com.wmiplay.moudle.GameTabBean;
import play.wm.ljb.com.wmiplay.protocol.base.BaseProtocol;

/**
 * Created by Ljb on 2015/11/9.
 */
public class GameTabProtocol extends BaseProtocol<GameTabBean> {
    @Override
    protected GameTabBean parseJson(String json) {
        List<AppInfo> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i =0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String packageName = jsonObject.getString("packageName");
                String iconUrl = jsonObject.getString("iconUrl");
                double stars =  jsonObject.getDouble("stars");
                int size =  jsonObject.getInt("size");
                String downloadUrl = jsonObject.getString("downloadUrl");
                String des = jsonObject.getString("des");
                list.add(new AppInfo(id,name,packageName,iconUrl,(float)stars,size,downloadUrl,des));
            }
            return  new GameTabBean(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getPath() {
        return "game";
    }
}
