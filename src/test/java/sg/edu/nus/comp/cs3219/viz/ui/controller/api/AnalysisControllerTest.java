package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import sg.edu.nus.comp.cs3219.viz.BaseTestREST;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AnalysisRequest;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationSection;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnalysisControllerTest extends BaseTestREST {

    @Override
    protected String getDataBundleName() {
        return "/AnalysisControllerTest.json";
    }

    @Test
    public void testPostAnalyse_notLogin_forbiddenError() throws Exception {
        gaeSimulation.logoutUser();

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record record = new PresentationSection.Record();
        record.setName("submission_record");
        analysisRequest.getInvolvedRecords().add(record);

        mvc.perform(
                post("/api/presentations/1/analysis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(analysisRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPostAnalyse_loginWithNoReadAccess_forbiddenError() throws Exception {
        gaeSimulation.loginUser("random@people.com");

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record record = new PresentationSection.Record();
        record.setName("author_record");
        analysisRequest.getInvolvedRecords().add(record);

        mvc.perform(
                post("/api/presentations/1/analysis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(analysisRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPostAnalyse_loginWithReadAccess_shouldAccessAndPerformAnalysis() throws Exception {
        gaeSimulation.loginUser("test@viz.test");

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record record = new PresentationSection.Record();
        record.setName("author_record");
        analysisRequest.getInvolvedRecords().add(record);

        mvc.perform(
                post("/api/presentations/1/analysis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(analysisRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostAnalyse_dataSetMissing_validationError() throws Exception {
        gaeSimulation.loginUser("test@example.com");

        AnalysisRequest analysisRequest = new AnalysisRequest();

        PresentationSection.Record record = new PresentationSection.Record();
        record.setName("submission_record");
        analysisRequest.getInvolvedRecords().add(record);

        mvc.perform(
                post("/api/presentations/1/analysis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(analysisRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPostAnalyse_involvedRecordsMissing_validationError() throws Exception {
        gaeSimulation.loginUser("test@example.com");

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        Assert.assertTrue(analysisRequest.getInvolvedRecords().isEmpty());

        mvc.perform(
                post("/api/presentations/1/analysis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(analysisRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testPostAnalyse_typicalCase_shouldProcessCorrectly() throws Exception {
        gaeSimulation.loginUser("test@example.com");

        AnalysisRequest analysisRequest = new AnalysisRequest();

        analysisRequest.setDataSet("test@example.com");

        PresentationSection.Record record = new PresentationSection.Record();
        record.setName("submission_record");
        analysisRequest.getInvolvedRecords().add(record);
        record = new PresentationSection.Record();
        record.setName("review_record");
        analysisRequest.getInvolvedRecords().add(record);
        record = new PresentationSection.Record();
        record.setName("author_record");
        analysisRequest.getInvolvedRecords().add(record);

        PresentationSection.Joiner joiner = new PresentationSection.Joiner();
        joiner.setLeft("s_submission_id");
        joiner.setRight("r_submission_id");
        joiner = new PresentationSection.Joiner();
        joiner.setLeft("s_submission_id");
        joiner.setRight("a_submission_id");

        mvc.perform(
                post("/api/presentations/1/analysis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(analysisRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].s_is_accepted").value("no"))
                .andExpect(jsonPath("$[0].a_country").value("United States"))
                .andExpect(jsonPath("$[0].r_reviewer_name").value("Juxxxx Bruxxxx"));
    }

}
