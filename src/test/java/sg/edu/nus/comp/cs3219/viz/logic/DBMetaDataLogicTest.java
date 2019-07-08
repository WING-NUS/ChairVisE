package sg.edu.nus.comp.cs3219.viz.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.DBEntityMetaData;
import sg.edu.nus.comp.cs3219.viz.testhelper.TestProperties;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DBMetaDataLogicTest {

    private static final DBMetaDataLogic dbMetaDataLogic = new DBMetaDataLogic();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetEntityMetaDataList_shouldReturnCorrectValue() throws Exception {
        String jsonString = new String(
                Files.readAllBytes(Paths.get(TestProperties.TEST_DATA_FOLDER + "/DBMetaDataLogicTestExpected.json")));
        List<DBEntityMetaData> dbEntityMetaDataListExpected =
                objectMapper.readValue(jsonString, new TypeReference<List<DBEntityMetaData>>() {
                });

        List<DBEntityMetaData> dbEntityMetaDataList = dbMetaDataLogic.getEntityMetaDataList();

        Assert.assertEquals(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dbEntityMetaDataListExpected),
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dbEntityMetaDataList));
    }

}
