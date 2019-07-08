package sg.edu.nus.comp.cs3219.viz.common.exception;

public class UnauthorisedException extends RuntimeException {

    public UnauthorisedException() {
        super("You are unauthorised to view the recourse");
    }

}
