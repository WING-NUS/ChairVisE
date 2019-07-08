package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import sg.edu.nus.comp.cs3219.viz.BaseTestREST;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationSection;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PresentationSectionControllerTest extends BaseTestREST {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected String getDataBundleName() {
        return "/PresentationSectionControllerTest.json";
    }

    @Test
    public void testAll_notLogin_shouldNotAccess() throws Exception {
        gaeSimulation.logoutUser();

        mvc.perform(get("/api/presentations/1/sections"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAll_loginWithoutReadAccess_shouldNotAccess() throws Exception {
        gaeSimulation.loginUser("random@email.com");

        mvc.perform(get("/api/presentations/1/sections"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAll_nonExistentPresentation_shouldThrowNotFound() throws Exception {
        gaeSimulation.loginUser("test1@viz.test");

        mvc.perform(get("/api/presentations/100/sections"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAll_loginWithReadAccess_shouldAccess() throws Exception {
        gaeSimulation.loginUser("test1@viz.test");

        mvc.perform(get("/api/presentations/1/sections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].presentation").doesNotExist())
                .andExpect(jsonPath("$[0].title").value("my section title"))
                .andExpect(jsonPath("$[0].description").value("my section description"))
                .andExpect(jsonPath("$[0].type").value("word_cloud"))
                .andExpect(jsonPath("$[0].dataSet").value("test@viz.test"))
                .andExpect(jsonPath("$[0].selections").isArray())
                .andExpect(jsonPath("$[0].selections", hasSize(1)))
                .andExpect(jsonPath("$[0].selections[0].expression").value("selection1"))
                .andExpect(jsonPath("$[0].selections[0].rename").value("hi"))
                .andExpect(jsonPath("$[0].involvedRecords", hasSize(2)))
                .andExpect(jsonPath("$[0].involvedRecords[0].name").value("record1"))
                .andExpect(jsonPath("$[0].involvedRecords[0].customized").value(true))
                .andExpect(jsonPath("$[0].involvedRecords[1].name").value("record2"))
                .andExpect(jsonPath("$[0].involvedRecords[1].customized").value(true))
                .andExpect(jsonPath("$[0].filters", hasSize(1)))
                .andExpect(jsonPath("$[0].filters[0].field").value("filter1"))
                .andExpect(jsonPath("$[0].filters[0].comparator").value("="))
                .andExpect(jsonPath("$[0].filters[0].value").value("test"))
                .andExpect(jsonPath("$[0].joiners", hasSize(1)))
                .andExpect(jsonPath("$[0].joiners[0].left").value("record1"))
                .andExpect(jsonPath("$[0].joiners[0].right").value("record2"));
    }

    @Test
    public void testNewPresentationSection_notLogin_shouldThrowUnauthorized() throws Exception {
        gaeSimulation.logoutUser();

        mvc.perform(
                post("/api/presentations/1/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(dataBundle.presentationSections.get("presentationSection1"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testNewPresentationSection_nonExistentPresentation_shouldThrowNotFound() throws Exception {
        gaeSimulation.loginUser("test1@viz.test");

        mvc.perform(
                post("/api/presentations/100/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(dataBundle.presentationSections.get("presentationSection1"))))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testNewPresentationSection_noWriteAccess_shouldThrowUnauthorized() throws Exception {
        gaeSimulation.loginUser("test1@viz.test");

        mvc.perform(
                post("/api/presentations/1/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(dataBundle.presentationSections.get("presentationSection1"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testNewPresentationSection_withWriteAccess_shouldSaveCorrectly() throws Exception {
        gaeSimulation.loginUser("test2@viz.test");

        Assert.assertFalse(presentationSectionRepository.findById(3L).isPresent());

        PresentationSection presentationSectionToSave = dataBundle.presentationSections.get("presentationSection1");

        mvc.perform(
                post("/api/presentations/1/sections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(presentationSectionToSave)))
                .andExpect(status().isCreated());

        PresentationSection savedPresentationSection = presentationSectionRepository.findById(3L)
                .orElseThrow(AssertionError::new);
        // make two consistent
        savedPresentationSection.setId(presentationSectionToSave.getId());

        Assert.assertEquals(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(presentationSectionToSave),
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedPresentationSection));
    }

    @Test
    public void testUpdatePresentationSection_notLogin_shouldThrowUnauthorized() throws Exception {
        gaeSimulation.logoutUser();

        mvc.perform(
                put("/api/presentations/1/sections/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(dataBundle.presentationSections.get("presentationSection2"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdatePresentationSection_nonExistentPresentationSection_shouldThrowNotFound() throws Exception {
        gaeSimulation.loginUser("test1@viz.test");

        mvc.perform(
                put("/api/presentations/1/sections/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(dataBundle.presentationSections.get("presentationSection2"))))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePresentationSection_noWriteAccess_shouldThrowUnauthorized() throws Exception {
        gaeSimulation.loginUser("test1@viz.test");

        mvc.perform(
                put("/api/presentations/1/sections/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(dataBundle.presentationSections.get("presentationSection2"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdatePresentationSection_withWriteAccess_shouldUpdateCorrectly() throws Exception {
        gaeSimulation.loginUser("test2@viz.test");

        PresentationSection presentationSectionUsedToUpdate = dataBundle.presentationSections.get("presentationSection2");

        mvc.perform(
                put("/api/presentations/1/sections/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(presentationSectionUsedToUpdate)))
                .andExpect(status().isCreated());

        PresentationSection updatedPresentationSection = presentationSectionRepository.findById(1L)
                .orElseThrow(AssertionError::new);
        // make two consistent
        updatedPresentationSection.setId(presentationSectionUsedToUpdate.getId());
        // type cannot be updated
        presentationSectionUsedToUpdate.setType(updatedPresentationSection.getType());

        Assert.assertEquals(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updatedPresentationSection),
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(presentationSectionUsedToUpdate));
    }

    @Test
    public void testDeletePresentationSection_notLogin_shouldThrowUnauthorized() throws Exception {
        gaeSimulation.logoutUser();

        mvc.perform(delete("/api/presentations/1/sections/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeletePresentationSection_nonExistentPresentationSection_shouldThrowNotFound() throws Exception {
        gaeSimulation.loginUser("test1@viz.test");

        mvc.perform(delete("/api/presentations/1/sections/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePresentationSection_noWriteAccess_shouldThrowUnauthorized() throws Exception {
        gaeSimulation.loginUser("test1@viz.test");

        mvc.perform(delete("/api/presentations/1/sections/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeletePresentationSection_withWriteAccess_shouldDeleteCorrectly() throws Exception {
        gaeSimulation.loginUser("test2@viz.test");

        mvc.perform(delete("/api/presentations/1/sections/1"))
                .andExpect(status().isNoContent());

        Assert.assertFalse(presentationSectionRepository.findById(1L).isPresent());
    }
}
