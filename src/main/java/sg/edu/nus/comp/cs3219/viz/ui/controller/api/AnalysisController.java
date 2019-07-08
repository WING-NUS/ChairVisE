package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AccessLevel;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AnalysisRequest;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.exception.PresentationNotFoundException;
import sg.edu.nus.comp.cs3219.viz.logic.AnalysisLogic;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.PresentationLogic;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class AnalysisController extends BaseRestController {

    private GateKeeper gateKeeper;

    private AnalysisLogic analysisLogic;

    private PresentationLogic presentationLogic;

    private static final Logger log = Logger.getLogger(AnalysisLogic.class.getSimpleName());

    public AnalysisController(GateKeeper gateKeeper, PresentationLogic presentationLogic, AnalysisLogic analysisLogic) {
        this.analysisLogic = analysisLogic;
        this.presentationLogic = presentationLogic;
        this.gateKeeper = gateKeeper;
    }

    @PostMapping("/presentations/{id}/analysis")
    public List<Map<String, Object>> analysis(@PathVariable Long id, @Valid @RequestBody AnalysisRequest analysisRequest) {
        // verify access level
        Presentation presentation = presentationLogic.findById(id)
                .orElseThrow(() -> new PresentationNotFoundException(id));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_READ);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);
        log.info("Analysis Result from query: " + result);
        // convert to map with key all in lower case
        return result.stream().map(m -> {
            Map<String, Object> map = new HashMap<>();
            m.forEach((k, v) -> map.put(k.toLowerCase(), v));
            return map;
        }).collect(Collectors.toList());
    }

}
