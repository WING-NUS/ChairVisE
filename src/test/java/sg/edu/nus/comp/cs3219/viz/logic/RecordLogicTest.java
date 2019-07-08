package sg.edu.nus.comp.cs3219.viz.logic;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sg.edu.nus.comp.cs3219.viz.BaseTestWithDBAccess;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.SubmissionAuthorRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.SubmissionRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecordLogicTest extends BaseTestWithDBAccess {

    @Autowired
    RecordLogic recordLogic;

    @Override
    protected String getDataBundleName() {
        return "/NoContent.json";
    }

    @Test
    public void testImportSubmissionRecord_shouldEstablishAuthorManyToManyRelationship() {
        SubmissionRecord typicalSubmissionRecord = new SubmissionRecord();
        typicalSubmissionRecord.setAuthors(Arrays.asList("XP", "HY", "YZH", "JACOB"));

        SubmissionRecord typicalSubmissionRecord2 = new SubmissionRecord();
        typicalSubmissionRecord2.setAuthors(Arrays.asList("XP", "HY"));

        recordLogic.removeAndPersistSubmissionRecordForDataSet("test",
                Arrays.asList(typicalSubmissionRecord, typicalSubmissionRecord2));

        List<SubmissionAuthorRecord> submissionAuthorRecordList =
                submissionAuthorRecordRepository.findAll();
        Set<String> authorNameSet =
                submissionAuthorRecordList.stream()
                        .map(SubmissionAuthorRecord::getName)
                        .collect(Collectors.toSet());
        Assert.assertEquals(4, authorNameSet.size());
        Assert.assertTrue(authorNameSet.contains("XP"));
        Assert.assertTrue(authorNameSet.contains("HY"));
        Assert.assertTrue(authorNameSet.contains("YZH"));
        Assert.assertTrue(authorNameSet.contains("JACOB"));
    }
}
