package sg.edu.nus.comp.cs3219.viz.common.exception;

public class ConferenceNotFoundException extends RuntimeException {

    public ConferenceNotFoundException(Long id) {
        super("Could not find conference " + id);
    }

}
