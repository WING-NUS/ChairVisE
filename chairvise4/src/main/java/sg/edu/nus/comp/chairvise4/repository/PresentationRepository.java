package sg.edu.nus.comp.chairvise4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.nus.comp.chairvise4.entity.Presentation;

import java.util.List;

/**
 * @author liuruiping
 * @version 1.0
 * @date 10/8/2020 10:38 PM
 */
@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

    @Query(value = "select p from Presentation p where p.isPublic = true")
    List<Presentation> findPublicPresentations();
}
