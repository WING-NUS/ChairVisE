package sg.edu.nus.comp.cs3219.viz.logic;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sg.edu.nus.comp.cs3219.viz.BaseTestWithDBAccess;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AnalysisRequest;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationSection;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.ReviewRecord;

import java.util.List;
import java.util.Map;

public class AnalysisLogicTest extends BaseTestWithDBAccess {

    @Autowired
    private AnalysisLogic analysisLogic;

    @Override
    protected String getDataBundleName() {
        return "/AnalysisLogicTest.json";
    }

    @Test
    public void testAnalyse_queryOnlySubmissionRecord_shouldQueryCorrectly() {
        Assert.assertEquals(1, submissionRecordRepository.findByDataSetEquals("test@example.com").size());

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("submission_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testAnalyse_queryOnlySubmissionRecordWithSelectionSpecified_shouldQueryCorrectly() {
        Assert.assertEquals(1, submissionRecordRepository.findByDataSetEquals("test@example.com").size());

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("submission_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);

        PresentationSection.Selection selection = new PresentationSection.Selection();
        selection.setExpression("s_track_name");
        selection.setRename("trackName");
        analysisRequest.getSelections().add(selection);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(1, result.get(0).keySet().size());
        // should rename
        Assert.assertNull(result.get(0).get("s_track_name"));
        Assert.assertEquals("Posters and Demos", result.get(0).get("trackName"));
    }

    @Test
    public void testAnalyse_queryOnlySubmissionRecordWithSomeSelectionsSpecified_shouldQueryCorrectly() {
        Assert.assertEquals(1, submissionRecordRepository.findByDataSetEquals("test@example.com").size());

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("submission_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);

        PresentationSection.Selection selection = new PresentationSection.Selection();
        selection.setExpression("s_track_name");
        selection.setRename("trackName");
        analysisRequest.getSelections().add(selection);
        selection = new PresentationSection.Selection();
        selection.setExpression("s_is_accepted");
        selection.setRename("isAccepted");
        analysisRequest.getSelections().add(selection);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(2, result.get(0).keySet().size());
        Assert.assertNull(result.get(0).get("s_track_name"));
        Assert.assertEquals("Posters and Demos", result.get(0).get("trackName"));
        Assert.assertNull(result.get(0).get("s_is_accepted"));
        Assert.assertEquals("no", result.get(0).get("isAccepted"));
    }

    @Test
    public void testAnalyse_queryOnlyAuthorRecord_shouldQueryCorrectly() {
        Assert.assertEquals(2, authorRecordRepository.findByDataSetEquals("test1@example.com").size());

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test1@example.com");

        PresentationSection.Record submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("author_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testAnalyse_queryOnlyReviewRecord_shouldQueryCorrectly() {
        Assert.assertEquals(2, reviewRecordRepository.findByDataSetEquals("test@example.com").size());

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("review_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testAnalyse_queryOnlySubmissionRecordWithStringFilter_shouldQueryCorrectly() {
        Assert.assertTrue(1 < submissionRecordRepository.findByDataSetEquals("test1@example.com").size());

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test1@example.com");

        PresentationSection.Record submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("submission_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);

        PresentationSection.Filter filter = new PresentationSection.Filter();
        filter.setField("s_track_name");
        filter.setComparator("=");
        filter.setValue("test");
        analysisRequest.getFilters().add(filter);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals("test", result.get(0).get("s_track_name"));
    }

    @Test
    public void testAnalyse_queryOnlyReviewRecordWithNumberFilter_shouldQueryCorrectly() {
        Assert.assertNotEquals(
                0,
                reviewRecordRepository.findByDataSetEquals("test@example.com").stream()
                        .filter(s -> s.getNumReviewAssignment() == 47)
                        .count()
        );

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("review_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);

        PresentationSection.Filter filter = new PresentationSection.Filter();
        filter.setField("r_num_review_assignment");
        filter.setComparator("=");
        filter.setValue("47");
        analysisRequest.getFilters().add(filter);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(47, result.get(0).get("r_num_review_assignment"));
    }

    @Test
    public void testAnalyse_queryOnlyReviewRecordWithMixedFilters_shouldQueryCorrectly() {
        Assert.assertNotEquals(
                0,
                reviewRecordRepository.findByDataSetEquals("test@example.com").stream()
                        .filter(s -> s.getNumReviewAssignment() == 47)
                        .filter(s -> s.getHasRecommendedForBestPaper().equals("no"))
                        .filter(s -> s.getReviewerName().equals("Juxxxx Bruxxxx"))
                        .count()
        );

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("review_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);

        PresentationSection.Filter filter = new PresentationSection.Filter();
        filter.setField("r_num_review_assignment");
        filter.setComparator("=");
        filter.setValue("47");
        analysisRequest.getFilters().add(filter);
        filter = new PresentationSection.Filter();
        filter.setField("r_has_recommended_for_best_paper");
        filter.setComparator("=");
        filter.setValue("no");
        filter = new PresentationSection.Filter();
        filter.setField("r_reviewer_name");
        filter.setComparator("=");
        filter.setValue("Juxxxx Bruxxxx");
        analysisRequest.getFilters().add(filter);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(47, result.get(0).get("r_num_review_assignment"));
        Assert.assertEquals("no", result.get(0).get("r_has_recommended_for_best_paper"));
        Assert.assertEquals("Juxxxx Bruxxxx", result.get(0).get("r_reviewer_Name"));
    }

    @Test
    public void testAnalyse_querySubmissionReviewRecordJoin_shouldQueryCorrectly() {
        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("submission_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);
        submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("review_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);

        PresentationSection.Filter filter = new PresentationSection.Filter();
        filter.setField("s_submission_id");
        filter.setComparator("=");
        filter.setValue("2");
        analysisRequest.getFilters().add(filter);

        PresentationSection.Joiner joiner = new PresentationSection.Joiner();
        joiner.setLeft("s_submission_id");
        joiner.setRight("r_submission_id");
        analysisRequest.getJoiners().add(joiner);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(2, result.size());
        Assert.assertEquals("Posters and Demos", result.get(0).get("s_track_name"));
        Assert.assertEquals("Posters and Demos", result.get(1).get("s_track_name"));
        Assert.assertNotEquals(result.get(0).get("r_review_comment"), result.get(1).get("r_review_comment"));
    }

    @Test
    public void testAnalyse_querySubmissionAuthorRecordJoin_shouldQueryCorrectly() {
        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test1@example.com");

        PresentationSection.Record submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("submission_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);
        submissionRecord = new PresentationSection.Record();
        submissionRecord.setName("author_record");
        analysisRequest.getInvolvedRecords().add(submissionRecord);

        PresentationSection.Joiner joiner = new PresentationSection.Joiner();
        joiner.setLeft("s_submission_id");
        joiner.setRight("a_submission_id");
        analysisRequest.getJoiners().add(joiner);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(2, result.size());
        Assert.assertNotEquals(result.get(0).get("a_country"), result.get(1).get("a_country"));
    }

    @Test
    public void testAnalyse_queryReviewWithSorting_shouldQueryCorrectly() {
        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record reviewRecord = new PresentationSection.Record();
        reviewRecord.setName("review_record");
        analysisRequest.getInvolvedRecords().add(reviewRecord);

        PresentationSection.Sorter sorter = new PresentationSection.Sorter();
        sorter.setField("r_overall_evaluation_score");
        sorter.setOrder("DESC");
        analysisRequest.getSorters().add(sorter);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(-2.1, result.get(0).get("r_overall_evaluation_score"));
        Assert.assertEquals(-3.0, result.get(1).get("r_overall_evaluation_score"));
    }

    @Test
    public void testAnalyse_queryReviewWithAggregation_shouldQueryCorrectly() {
        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record reviewRecord = new PresentationSection.Record();
        reviewRecord.setName("review_record");
        analysisRequest.getInvolvedRecords().add(reviewRecord);

        PresentationSection.Selection selection = new PresentationSection.Selection();
        selection.setExpression("SUM(r_num_review_assignment)");
        selection.setRename("totalSum");
        analysisRequest.getSelections().add(selection);

        PresentationSection.Grouper grouper = new PresentationSection.Grouper();
        grouper.setField("r_submission_id");
        analysisRequest.getGroupers().add(grouper);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        long totalSum = reviewRecordRepository.findByDataSetEquals("test@example.com").stream()
                .mapToLong(ReviewRecord::getNumReviewAssignment)
                .sum();

        Assert.assertEquals(totalSum, result.get(0).get("totalSum"));
    }

    @Test
    public void testAnalyse_withCustomizedInvolvedRecord_shouldQueryCorrectly() {
        Assert.assertNotEquals(0,
                reviewRecordRepository.findAll().stream()
                        .filter(r -> !r.getDataSet().equals("test@example.com"))
                        .count());

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record reviewRecord = new PresentationSection.Record();
        reviewRecord.setName("(SELECT * FROM review_record WHERE review_record.data_set = 'test@example.com') AS `tmp`");
        reviewRecord.setCustomized(true);
        analysisRequest.getInvolvedRecords().add(reviewRecord);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testAnalyse_withCustomizedInvolvedRecordAndFilter_shouldQueryCorrectly() {
        Assert.assertNotEquals(0,
                reviewRecordRepository.findAll().stream()
                        .filter(r -> !r.getDataSet().equals("test@example.com"))
                        .count());

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record reviewRecord = new PresentationSection.Record();
        reviewRecord.setName("(SELECT * FROM review_record WHERE review_record.data_set = 'test@example.com') AS `tmp`");
        reviewRecord.setCustomized(true);
        analysisRequest.getInvolvedRecords().add(reviewRecord);

        PresentationSection.Filter filter = new PresentationSection.Filter();
        filter.setField("r_reviewer_name");
        filter.setComparator("=");
        filter.setValue("Juxxxx Bruxxxx");
        analysisRequest.getFilters().add(filter);

        List<Map<String, Object>> result = analysisLogic.analyse(analysisRequest);

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testWrapValue_typicalFieldName_shouldGenerateWrapperCorrectly() {
        Assert.assertEquals("'yes'", analysisLogic.wrapValue("a_is_corresponding", "yes"));
        Assert.assertEquals("21", analysisLogic.wrapValue("r_num_review_assignment", "21"));
        Assert.assertEquals("21.0", analysisLogic.wrapValue("r_overall_evaluation_score", "21.0"));
        Assert.assertEquals("22.0", analysisLogic.wrapValue("r_expertise_level", "22.0"));
        Assert.assertEquals("'NUS'", analysisLogic.wrapValue("a_organisation", "NUS"));
        Assert.assertEquals("'NUS'", analysisLogic.wrapValue("unknown", "NUS"));
    }
}
