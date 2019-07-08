package sg.edu.nus.comp.cs3219.viz.config;

import com.google.appengine.api.utils.SystemProperty;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import sg.edu.nus.comp.cs3219.viz.VizApplication;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            application.profiles("prod");
        } else {
            application.profiles("local");
        }

        return application.sources(VizApplication.class);
    }

}
