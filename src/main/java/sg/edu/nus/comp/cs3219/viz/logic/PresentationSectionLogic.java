package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationSection;
import sg.edu.nus.comp.cs3219.viz.storage.repository.PresentationSectionRepository;

import java.util.List;
import java.util.Optional;

@Component
public class PresentationSectionLogic {

    private PresentationSectionRepository presentationSectionRepository;

    public PresentationSectionLogic(PresentationSectionRepository presentationSectionRepository) {
        this.presentationSectionRepository = presentationSectionRepository;
    }

    public List<PresentationSection> findAllByPresentation(Presentation presentation) {
        return presentationSectionRepository.findAllByPresentation(presentation);
    }

    public PresentationSection saveForPresentation(Presentation presentation, PresentationSection presentationSection) {
        PresentationSection newPresentationSection = new PresentationSection();

        newPresentationSection.setPresentation(presentation);
        newPresentationSection.setTitle(presentationSection.getTitle());
        newPresentationSection.setDescription(presentationSection.getDescription());
        newPresentationSection.setType(presentationSection.getType());
        newPresentationSection.setDataSet(presentationSection.getDataSet());
        newPresentationSection.setSelections(presentationSection.getSelections());
        newPresentationSection.setInvolvedRecords(presentationSection.getInvolvedRecords());
        newPresentationSection.setFilters(presentationSection.getFilters());
        newPresentationSection.setJoiners(presentationSection.getJoiners());
        newPresentationSection.setGroupers(presentationSection.getGroupers());
        newPresentationSection.setSorters(presentationSection.getSorters());
        newPresentationSection.setExtraData(presentationSection.getExtraData());

        return presentationSectionRepository.save(newPresentationSection);
    }

    public Optional<PresentationSection> findById(Long id) {
        return presentationSectionRepository.findById(id);
    }

    public PresentationSection updatePresentation(PresentationSection oldPresentationSection, PresentationSection newPresentationSection) {
        oldPresentationSection.setTitle(newPresentationSection.getTitle());
        oldPresentationSection.setDescription(newPresentationSection.getDescription());
        oldPresentationSection.setDataSet(newPresentationSection.getDataSet());
        oldPresentationSection.setSelections(newPresentationSection.getSelections());
        oldPresentationSection.setInvolvedRecords(newPresentationSection.getInvolvedRecords());
        oldPresentationSection.setFilters(newPresentationSection.getFilters());
        oldPresentationSection.setJoiners(newPresentationSection.getJoiners());
        oldPresentationSection.setGroupers(newPresentationSection.getGroupers());
        oldPresentationSection.setSorters(newPresentationSection.getSorters());
        oldPresentationSection.setExtraData(newPresentationSection.getExtraData());

        return presentationSectionRepository.save(oldPresentationSection);
    }

    public void deleteById(Long id) {
        presentationSectionRepository.deleteById(id);
    }
}
