package play.wm.ljb.com.wmiplay.moudle;

/**
 * Created by Ljb on 2015/11/10.
 */
public class MenuBean {
    private int iamgeResId;
    private String menuText;

    public MenuBean(int iamgeResId, String menuText) {
        this.iamgeResId = iamgeResId;
        this.menuText = menuText;
    }

    public int getIamgeResId() {
        return iamgeResId;
    }

    public void setIamgeResId(int iamgeResId) {
        this.iamgeResId = iamgeResId;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }
}
