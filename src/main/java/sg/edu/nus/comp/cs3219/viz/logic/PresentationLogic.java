package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.storage.repository.PresentationRepository;

import java.util.List;
import java.util.Optional;

@Component
public class PresentationLogic {

    private final PresentationRepository presentationRepository;

    public PresentationLogic(PresentationRepository presentationRepository) {
        this.presentationRepository = presentationRepository;
    }

    public List<Presentation> findAllForUser(UserInfo userInfo) {
        return presentationRepository.findByCreatorIdentifier(userInfo.getUserEmail());
    }

    public Optional<Presentation> findById(Long id) {
        return presentationRepository.findById(id);
    }

    public Presentation saveForUser(Presentation presentation, UserInfo userInfo) {
        Presentation newPresentation = new Presentation();
        newPresentation.setName(presentation.getName());
        newPresentation.setDescription(presentation.getDescription());
        newPresentation.setCreatorIdentifier(userInfo.getUserEmail());

        return presentationRepository.save(newPresentation);
    }

    public Presentation updatePresentation(Presentation oldPresentation, Presentation newPresentation) {
        oldPresentation.setName(newPresentation.getName());
        oldPresentation.setDescription(newPresentation.getDescription());
        return presentationRepository.save(oldPresentation);
    }

    public void deleteById(Long id) {
        presentationRepository.deleteById(id);
    }
}
