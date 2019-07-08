package sg.edu.nus.comp.cs3219.viz.testhelper;

import com.google.appengine.tools.development.testing.*;
import sg.edu.nus.comp.cs3219.viz.common.util.Const;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a Singleton in-memory simulation of the GAE for unit testing.
 *
 * <p>This is not the same as testing against the dev server.
 * When testing against the GAE simulation, there is no need for the dev server to be running.
 */
public class GaeSimulation {

    private static final String QUEUE_XML_PATH = "src/main/webapp/WEB-INF/queue.xml";

    private static GaeSimulation instance = new GaeSimulation();

    private LocalServiceTestHelper helper;

    /**
     * Gets the GAE simulation instance.
     */
    public static GaeSimulation inst() {
        return instance;
    }

    /**
     * Sets up the GAE simulation.
     */
    public synchronized void setup() {
        System.out.println("Setting up GAE simulation");

        LocalTaskQueueTestConfig localTasks = new LocalTaskQueueTestConfig();
        localTasks.setQueueXmlPath(QUEUE_XML_PATH);

        LocalUserServiceTestConfig localUserServices = new LocalUserServiceTestConfig();
        LocalMailServiceTestConfig localMail = new LocalMailServiceTestConfig();
        LocalModulesServiceTestConfig localModules = new LocalModulesServiceTestConfig();
        helper = new LocalServiceTestHelper(localMail, localUserServices,
                localTasks, localModules);

        helper.setEnvAttributes(this.getEnvironmentAttributesWithApplicationHostname());
        helper.setUp();
    }

    /**
     * Logs in the user to the GAE simulation environment.
     */
    public void loginUser(String userEmail) {
        helper.setEnvIsLoggedIn(true);
        helper.setEnvEmail(userEmail);
        helper.setEnvAuthDomain("gmail.com");
    }

    /**
     * Logs the current user out of the GAE simulation environment.
     */
    public void logoutUser() {
        helper.setEnvIsLoggedIn(false);
    }

    /**
     * Tears down the GAE simulation.
     */
    public void tearDown() {
        try {
            if (helper != null) {
                helper.tearDown();
            }
        } catch (Exception e) {
            System.out.println("Ignoring exception during teardown...");
        }
    }

    /**
     * Returns an environment attribute with application host name.
     */
    private Map<String, Object> getEnvironmentAttributesWithApplicationHostname() {
        Map<String, Object> attributes = new HashMap<>();
        try {
            // can be any value as it is integration test
            attributes.put(Const.GaeEnvProperties.DEFAULT_VERSION_HOSTNAME,
                    new URL("http://localhost/").getAuthority());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return attributes;
    }

}
