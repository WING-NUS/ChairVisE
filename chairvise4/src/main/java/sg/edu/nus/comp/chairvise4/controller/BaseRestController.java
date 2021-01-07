package sg.edu.nus.comp.chairvise4.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/10/2020 8:53 PM
 */
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class BaseRestController {
}
