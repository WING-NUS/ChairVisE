package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationAccessControl;
import sg.edu.nus.comp.cs3219.viz.storage.repository.PresentationAccessControlRepository;

import java.util.List;
import java.util.Optional;

@Component
public class PresentationAccessControlLogic {

    private PresentationAccessControlRepository presentationAccessControlRepository;

    public PresentationAccessControlLogic(PresentationAccessControlRepository presentationAccessControlRepository) {
        this.presentationAccessControlRepository = presentationAccessControlRepository;
    }

    public List<PresentationAccessControl> findAllByPresentation(Presentation presentation) {
        return presentationAccessControlRepository.findAllByPresentation(presentation);
    }

    public PresentationAccessControl saveForPresentation(Presentation presentation, PresentationAccessControl presentationAccessControl) {
        PresentationAccessControl newPresentationAccessControl = new PresentationAccessControl();

        newPresentationAccessControl.setPresentation(presentation);
        newPresentationAccessControl.setUserIdentifier(presentationAccessControl.getUserIdentifier());
        newPresentationAccessControl.setAccessLevel(presentationAccessControl.getAccessLevel());

        return presentationAccessControlRepository.save(newPresentationAccessControl);
    }

    public PresentationAccessControl updatePresentationAccessControl(PresentationAccessControl oldPresentationAccessControl, PresentationAccessControl newPresentationAccessControl) {
        oldPresentationAccessControl.setAccessLevel(newPresentationAccessControl.getAccessLevel());

        return presentationAccessControlRepository.save(oldPresentationAccessControl);
    }

    public Optional<PresentationAccessControl> findById(Long id) {
        return presentationAccessControlRepository.findById(id);
    }

    public void deleteById(Long id) {
        presentationAccessControlRepository.deleteById(id);
    }
}
