package sg.edu.nus.comp.cs3219.viz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public abstract class BaseTestREST extends BaseTestWithGAE {

    @Autowired
    protected MockMvc mvc;

    protected String objectToJson(Object object) throws Exception {
        return new ObjectMapper().writeValueAsString(object);
    }

}
