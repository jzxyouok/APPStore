package play.wm.ljb.com.wmiplay.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import play.wm.ljb.com.wmiplay.moudle.ClassifyInfo;
import play.wm.ljb.com.wmiplay.protocol.base.BaseProtocol;

/**
 * Created by Ljb on 2015/11/12.
 */
public class ClassifyTabProtocol extends BaseProtocol<List<ClassifyInfo>> {

    @Override
    protected List<ClassifyInfo> parseJson(String json) {
        List<ClassifyInfo> list  =  new ArrayList<>();
        try {
            JSONArray jsonArray  = new JSONArray(json);
            for(int i =0 ; i<jsonArray.length() ; i++){
                ClassifyInfo classifyInfo = new ClassifyInfo();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                classifyInfo.setIsTitle(true);
                classifyInfo.setTitle(title);
                list.add(classifyInfo);
                JSONArray infos = jsonObject.getJSONArray("infos");
                for(int j =0 ;j<infos.length();j++){
                    JSONObject infosJSONObject = infos.getJSONObject(j);
                    classifyInfo =  new ClassifyInfo();
                    String url1 = infosJSONObject.getString("url1");
                    String url2 = infosJSONObject.getString("url2");
                    String url3 = infosJSONObject.getString("url3");
                    String name1 = infosJSONObject.getString("name1");
                    String name2 = infosJSONObject.getString("name2");
                    String name3 = infosJSONObject.getString("name3");

                    classifyInfo.setImageUrl1(url1);
                    classifyInfo.setImageUrl2(url2);
                    classifyInfo.setImageUrl3(url3);
                    classifyInfo.setName1(name1);
                    classifyInfo.setName2(name2);
                    classifyInfo.setName3(name3);
                    list.add(classifyInfo);
                }
            }
            return  list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getPath() {
        return "category";
    }
}
