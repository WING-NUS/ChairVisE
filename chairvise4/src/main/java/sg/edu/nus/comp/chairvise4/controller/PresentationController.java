package sg.edu.nus.comp.chairvise4.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.comp.chairvise4.entity.Person;
import sg.edu.nus.comp.chairvise4.entity.Presentation;
import sg.edu.nus.comp.chairvise4.repository.PersonRepository;
import sg.edu.nus.comp.chairvise4.repository.PresentationRepository;


import java.util.Date;
import java.util.List;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/8/2020 3:31 PM
 */
@RestController
public class PresentationController extends BaseRestController {

    @Autowired
    private PresentationRepository presentationRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/presentation/{id}")
    public Presentation getPresentation(@PathVariable(name = "id") int id, @RequestHeader(name = "Authorization") String token) {
        Presentation presentation = presentationRepository.findById(id).get();
        DecodedJWT decodedJWT = JWT.decode(token);
        String email = decodedJWT.getClaim("email").asString();
        if (presentation.isPublic() || presentation.getChair().getEmail().equals(email)) {
            return presentation;
        }
        return null;
    }

    @PutMapping("/presentation")
    @ResponseStatus(HttpStatus.CREATED)
    public Presentation addPresentation(@RequestBody Presentation presentation, @RequestHeader(name = "Authorization") String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String email = decodedJWT.getClaim("email").asString();
        Person person = personRepository.findPersonByEmail(email);
        presentation.setChair(person);
        presentation.setCreatedAt(new Date());
        return presentationRepository.saveAndFlush(presentation);
    }

    @PostMapping("/presentation")
    public Presentation updatePresentationSection(@RequestBody Presentation presentation) {
        Presentation presentationFromDB = presentationRepository.findById(presentation.getId()).get();
        presentationFromDB.setSectionList(presentation.getSectionList());
        return presentationRepository.saveAndFlush(presentationFromDB);
    }

    @PatchMapping("/presentation")
    public Presentation updatePresentationSummary(@RequestParam(name = "id") int id,
                                                  @RequestParam(name = "name") String name,
                                                  @RequestParam(name = "description") String description) {
        Presentation presentation = presentationRepository.findById(id).get();
        presentation.setName(name);
        presentation.setDescription(description);
        return presentationRepository.saveAndFlush(presentation);
    }

    @DeleteMapping("/presentation")
    public void deletePresentation(@RequestBody Presentation presentation) {
        presentationRepository.deleteById(presentation.getId());
    }

    @GetMapping("/chairhub")
    public List<Presentation> getPublicPresentation() {
        return presentationRepository.findPublicPresentations();
    }

    @PostMapping("/chairhub")
    @Transactional
    public void setPresentationPublic(@RequestParam(name = "id") int id,
                                      @RequestParam(name = "isPublic") boolean isPublic) {
        Presentation presentation = presentationRepository.findById(id).get();
        presentation.setPublic(isPublic);
        presentationRepository.saveAndFlush(presentation);
    }
}
