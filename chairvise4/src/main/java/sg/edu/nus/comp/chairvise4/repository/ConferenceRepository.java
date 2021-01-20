package sg.edu.nus.comp.chairvise4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sg.edu.nus.comp.chairvise4.entity.Conference;

import java.util.List;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/7/2020 5:15 PM
 */
@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

    @Query(value = "SELECT c FROM Conference c JOIN Person p ON p.id = c.chair.id WHERE p.email = :email")
    List<Conference> findConferencesByEmail(@Param("email") String email);

    Conference findConferenceById(int id);
}
