package sg.edu.nus.comp.cs3219.viz.common.exception;

public class PresentationAccessControlNotFoundException extends RuntimeException {

    public PresentationAccessControlNotFoundException(Long presentationId, Long accessControlId) {
        super(String.format("Could not find accessControl %d for presentation %d", accessControlId, presentationId));
    }

}
