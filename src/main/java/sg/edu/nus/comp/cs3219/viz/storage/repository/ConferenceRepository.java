package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.Conference;

import java.util.List;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    List<Conference> findByCreatorIdentifier(String creatorIdentifier);
}
