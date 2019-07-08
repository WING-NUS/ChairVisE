package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.junit.Test;
import sg.edu.nus.comp.cs3219.viz.BaseTestREST;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DBMetaDataControllerTest extends BaseTestREST {


    @Override
    protected String getDataBundleName() {
        return "/NoContent.json";
    }

    @Test
    public void testGetEntityMetaDataList_shouldReturnCorrectField() throws Exception {
        mvc.perform(get("/api/db/entity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));

        // more tests in DBMetaDataLogicTest
    }
}
