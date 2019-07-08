package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.AuthorRecord;

import java.util.List;

public interface AuthorRecordRepository extends JpaRepository<AuthorRecord, Long> {

    List<AuthorRecord> findByDataSetEquals(String dataSet);

    void deleteAllByDataSetEquals(String dataSet);

}
