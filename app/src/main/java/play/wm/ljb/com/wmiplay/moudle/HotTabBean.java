package play.wm.ljb.com.wmiplay.moudle;

import java.util.List;

/**
 * Created by Ljb on 2015/11/12.
 */
public class HotTabBean {
    private List<String> tabNames;

    public HotTabBean() {
    }

    public HotTabBean(List<String> tabNames) {
        this.tabNames = tabNames;
    }

    public List<String> getTabNames() {
        return tabNames;
    }

    public void setTabNames(List<String> tabName) {
        this.tabNames = tabName;
    }
}
