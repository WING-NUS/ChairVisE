package sg.edu.nus.comp.cs3219.viz.ui.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sg.edu.nus.comp.cs3219.viz.common.exception.UnauthorisedException;

@ControllerAdvice
class UnauthorisedAdvice {

    @ResponseBody
    @ExceptionHandler(UnauthorisedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String unauthorizedHandler(UnauthorisedException ex) {
        return ex.getMessage();
    }
}
