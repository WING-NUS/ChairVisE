package sg.edu.nus.comp.cs3219.viz.common.exception;

public class PresentationSectionNotFoundException extends RuntimeException {

    public PresentationSectionNotFoundException(Long presentationId, Long sectionId) {
        super(String.format("Could not find section %d for presentation %d", sectionId, presentationId));
    }

}
