package sg.edu.nus.comp.chairvise4.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.comp.chairvise4.entity.Conference;
import sg.edu.nus.comp.chairvise4.entity.Person;
import sg.edu.nus.comp.chairvise4.repository.PersonRepository;

import java.util.List;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/7/2020 4:43 PM
 */
@RestController
public class PersonController extends BaseRestController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/person")
    public List<Person> getAllPerson(@RequestHeader(name = "Authorization") String token) {
        DecodedJWT decodedJWT = JWT.decode(token.substring(7));
        String email = decodedJWT.getClaim("email").asString();
        return personRepository.findAll();
    }

    @PostMapping("/login")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Person login(@RequestHeader(name = "Authorization") String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        String email = decodedJWT.getClaim("email").asString();
        Person person = personRepository.findPersonByEmail(email);
        if (person == null) {
            Person newPerson = new Person();
            newPerson.setName(decodedJWT.getClaim("name").asString());
            newPerson.setEmail(email);
            person = personRepository.saveAndFlush(newPerson);
        }
        return person;
    }
}
