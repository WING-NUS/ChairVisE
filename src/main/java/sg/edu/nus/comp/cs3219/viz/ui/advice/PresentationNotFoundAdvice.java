package sg.edu.nus.comp.cs3219.viz.ui.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sg.edu.nus.comp.cs3219.viz.common.exception.PresentationNotFoundException;

@ControllerAdvice
class PresentationNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PresentationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String presentationNotFoundHandler(PresentationNotFoundException ex) {
        return ex.getMessage();
    }
}
