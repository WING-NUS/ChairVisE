package sg.edu.nus.comp.cs3219.viz.ui.controller.webpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebPageController {

    @RequestMapping("/web/**")
    public String handleFrontEndRequest() {
        return "/dist/index.html";
    }

    @RequestMapping("/")
    public String welcomeStranger() {
        return "redirect:/web/";
    }

}
