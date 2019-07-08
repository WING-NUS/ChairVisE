package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.ReviewRecord;

import java.util.List;

public interface ReviewRecordRepository extends JpaRepository<ReviewRecord, Long> {

    List<ReviewRecord> findByDataSetEquals(String dataSet);

    void deleteAllByDataSetEquals(String dataSet);
}
