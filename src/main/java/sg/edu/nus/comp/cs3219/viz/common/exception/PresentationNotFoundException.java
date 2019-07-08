package sg.edu.nus.comp.cs3219.viz.common.exception;

public class PresentationNotFoundException extends RuntimeException {

    public PresentationNotFoundException(Long id) {
        super("Could not find presentation " + id);
    }

}
