package sg.edu.nus.comp.chairvise4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.edu.nus.comp.chairvise4.entity.Person;

import java.util.List;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/7/2020 3:40 PM
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findPersonByEmail(String email);

}
