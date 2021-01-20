package sg.edu.nus.comp.chairvise4.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.comp.chairvise4.entity.Conference;
import sg.edu.nus.comp.chairvise4.entity.Person;
import sg.edu.nus.comp.chairvise4.repository.ConferenceRepository;
import sg.edu.nus.comp.chairvise4.repository.PersonRepository;

import java.util.Date;
import java.util.List;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/7/2020 5:24 PM
 */
@RestController
public class ConferenceController extends BaseRestController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ConferenceRepository conferenceRepository;

    @GetMapping("/conference")
    @CrossOrigin
    public List<Conference> getMyConference(@RequestHeader(name = "Authorization") String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String email = decodedJWT.getClaim("email").asString();
        List<Conference> conferences = conferenceRepository.findConferencesByEmail(email);
        return conferences;
    }

    @PutMapping("/conference")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Conference addConference(@RequestHeader(name = "Authorization") String token, @RequestBody Conference conference) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String email = decodedJWT.getClaim("email").asString();
        Person person = personRepository.findPersonByEmail(email);
        conference.setChair(person);
        conference.setCreatedAt(new Date());
        return conferenceRepository.save(conference);
    }

    @PatchMapping("/conference")
    @Transactional
    public Conference editConference(@RequestBody Conference conference) {
        Conference conferenceFromDB = conferenceRepository.findConferenceById(conference.getId());
        conference.setChair(conferenceFromDB.getChair());
        conference.setPresentations(conferenceFromDB.getPresentations());
        return conferenceRepository.saveAndFlush(conference);
    }

    @DeleteMapping("/conference")
    public void deleteConference(@RequestParam(name = "id") int id) {
        conferenceRepository.deleteById(id);
    }
}
