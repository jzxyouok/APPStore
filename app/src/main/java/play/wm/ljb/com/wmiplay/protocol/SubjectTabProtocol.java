package play.wm.ljb.com.wmiplay.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import play.wm.ljb.com.wmiplay.moudle.SubjectInfo;
import play.wm.ljb.com.wmiplay.moudle.SubjectTabBean;
import play.wm.ljb.com.wmiplay.protocol.base.BaseProtocol;


/**
 * Created by Ljb on 2015/11/6.
 */
public class SubjectTabProtocol extends BaseProtocol<SubjectTabBean> {

    @Override
    protected SubjectTabBean parseJson(String json) {
        List<SubjectInfo> list  = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i =0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject =  jsonArray.getJSONObject(i);
                String des = jsonObject.getString("des");
                String url = jsonObject.getString("url");
                list.add(new SubjectInfo(des,url));
            }
            return new SubjectTabBean(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getPath() {
        return "subject";
    }
}
