package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.SubmissionAuthorRecord;

import java.util.List;

public interface SubmissionAuthorRecordRepository extends JpaRepository<SubmissionAuthorRecord, Long> {

    SubmissionAuthorRecord findFirstByNameEqualsAndDataSetEquals(String name, String dataSet);

    void deleteAllByDataSetEquals(String dataSet);
}
