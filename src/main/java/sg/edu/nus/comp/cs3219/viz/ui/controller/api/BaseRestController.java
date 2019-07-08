package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BaseRestController {
}
