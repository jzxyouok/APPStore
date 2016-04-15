package play.wm.ljb.com.wmiplay.moudle;

import java.util.List;

/**
 * Created by Ljb on 2015/11/6.
 */
public class SubjectTabBean {

    private List<SubjectInfo> list;

    public SubjectTabBean(List<SubjectInfo> list) {
        this.list = list;
    }

    public List<SubjectInfo> getList() {
        return list;
    }

    public void setList(List<SubjectInfo> list) {
        this.list = list;
    }
}
