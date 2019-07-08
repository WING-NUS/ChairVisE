package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AccessLevel;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationAccessControl;
import sg.edu.nus.comp.cs3219.viz.common.exception.PresentationAccessControlNotFoundException;
import sg.edu.nus.comp.cs3219.viz.common.exception.PresentationNotFoundException;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.PresentationAccessControlLogic;
import sg.edu.nus.comp.cs3219.viz.logic.PresentationLogic;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class PresentationAccessControlController extends BaseRestController {
    private final PresentationLogic presentationLogic;

    private final GateKeeper gateKeeper;

    private PresentationAccessControlLogic presentationAccessControlLogic;

    public PresentationAccessControlController(PresentationLogic presentationLogic,
                                               GateKeeper gateKeeper,
                                               PresentationAccessControlLogic presentationAccessControlLogic) {
        this.presentationAccessControlLogic = presentationAccessControlLogic;
        this.presentationLogic = presentationLogic;
        this.gateKeeper = gateKeeper;
    }

    @GetMapping("/presentations/{presentationId}/accessControl")
    public List<PresentationAccessControl> all(@PathVariable Long presentationId) {
        Presentation presentation = presentationLogic.findById(presentationId)
                .orElseThrow(() -> new PresentationNotFoundException(presentationId));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_READ);

        return presentationAccessControlLogic.findAllByPresentation(presentation);
    }

    @PostMapping("/presentations/{presentationId}/accessControl")
    public ResponseEntity<?> addPermission(@RequestBody PresentationAccessControl presentationAccessControl, @PathVariable Long presentationId) throws URISyntaxException {
        Presentation presentation = presentationLogic.findById(presentationId)
                .orElseThrow(() -> new PresentationNotFoundException(presentationId));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_WRITE);

        PresentationAccessControl newAccessControl = presentationAccessControlLogic.saveForPresentation(presentation, presentationAccessControl);
        return ResponseEntity
                .created(new URI("/presentations/" + presentation.getId() + "/accessControl/" + newAccessControl.getId()))
                .body(newAccessControl);
    }

    @PutMapping("/presentations/{presentationId}/accessControl/{accessControlId}")
    public ResponseEntity<?> updatePermission(@RequestBody PresentationAccessControl presentationAccessControl, @PathVariable Long presentationId, @PathVariable Long accessControlId) throws URISyntaxException {
        Presentation presentation = presentationLogic.findById(presentationId)
                .orElseThrow(() -> new PresentationNotFoundException(presentationId));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_WRITE);

        PresentationAccessControl oldPresentationAccessControl = presentationAccessControlLogic.findById(accessControlId)
                .orElseThrow(() -> new PresentationAccessControlNotFoundException(presentationId, accessControlId));

        PresentationAccessControl updatedPresentationAccessControl =
                presentationAccessControlLogic.updatePresentationAccessControl(oldPresentationAccessControl, presentationAccessControl);

        return ResponseEntity
                .created(new URI("/presentations/" + presentationId + "/accessControl/" + accessControlId))
                .body(updatedPresentationAccessControl);
    }

    @DeleteMapping("/presentations/{presentationId}/accessControl/{accessControlId}")
    public ResponseEntity<?> removePermission(@PathVariable Long presentationId, @PathVariable Long accessControlId) {
        Presentation presentation = presentationLogic.findById(presentationId)
                .orElseThrow(() -> new PresentationNotFoundException(presentationId));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_WRITE);

        presentationAccessControlLogic.deleteById(accessControlId);

        return ResponseEntity.noContent().build();
    }
}
