package sg.edu.nus.comp.cs3219.viz.common.util;

import com.google.appengine.api.utils.SystemProperty;
import com.google.apphosting.api.ApiProxy;


public class Config {
    /**
     * The value of the application URL, or null if no server instance is running.
     */
    public static final String APP_URL;

    static {
        APP_URL = readAppUrl();
    }

    private static String readAppUrl() {
        ApiProxy.Environment serverEnvironment = ApiProxy.getCurrentEnvironment();
        if (serverEnvironment == null) {
            return null;
        }
        String hostname = (String) serverEnvironment.getAttributes()
                .get(Const.GaeEnvProperties.DEFAULT_VERSION_HOSTNAME);
        if (hostname == null) {
            return null;
        }
        return (isDevServer() ? "http://" : "https://") + hostname;
    }

    public static boolean isDevServer() {
        return SystemProperty.environment.value() != SystemProperty.Environment.Value.Production;
    }
}
