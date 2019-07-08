package sg.edu.nus.comp.cs3219.viz.ui.controller.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;

public class AuthInfo {

    private boolean isLogin;

    private UserInfo userInfo;

    private String loginUrl;

    private String logoutUrl;

    @JsonProperty("isLogin")
    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
