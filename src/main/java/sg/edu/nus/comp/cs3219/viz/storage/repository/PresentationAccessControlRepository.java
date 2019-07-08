package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AccessLevel;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationAccessControl;

import java.util.List;

public interface PresentationAccessControlRepository extends JpaRepository<PresentationAccessControl, Long> {

    List<PresentationAccessControl> findAllByPresentation(Presentation presentation);

    boolean existsByPresentationAndUserIdentifierEqualsAndAccessLevelEquals(Presentation presentation, String userIdentifier, AccessLevel accessLevel);

}
