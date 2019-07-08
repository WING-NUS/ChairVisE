package sg.edu.nus.comp.cs3219.viz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sg.edu.nus.comp.cs3219.viz.storage.repository.*;
import sg.edu.nus.comp.cs3219.viz.testhelper.DataBundle;
import sg.edu.nus.comp.cs3219.viz.testhelper.TestProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseTestWithDBAccess {

    protected DataBundle dataBundle;

    @Autowired
    protected PresentationRepository presentationRepository;

    @Autowired
    protected PresentationAccessControlRepository presentationAccessControlRepository;

    @Autowired
    protected PresentationSectionRepository presentationSectionRepository;

    @Autowired
    protected AuthorRecordRepository authorRecordRepository;

    @Autowired
    protected ReviewRecordRepository reviewRecordRepository;

    @Autowired
    protected SubmissionRecordRepository submissionRecordRepository;

    @Autowired
    protected SubmissionAuthorRecordRepository submissionAuthorRecordRepository;

    protected abstract String getDataBundleName();

    @Before
    public void injectDataBundle() {
        dataBundle = loadDataBundle(getDataBundleName());
        presentationRepository.saveAll(dataBundle.presentations.values());
        presentationAccessControlRepository.saveAll(dataBundle.presentationAccessControls.values());
        presentationSectionRepository.saveAll(dataBundle.presentationSections.values());
        authorRecordRepository.saveAll(dataBundle.authorRecords.values());
        reviewRecordRepository.saveAll(dataBundle.reviewRecords.values());
        submissionRecordRepository.saveAll(dataBundle.submissionRecords.values());
    }

    @After
    public void removeDataBundle() {
        presentationAccessControlRepository.deleteAll();
        presentationSectionRepository.deleteAll();
        presentationRepository.deleteAll();
        authorRecordRepository.deleteAll();
        reviewRecordRepository.deleteAll();
        submissionRecordRepository.deleteAll();
    }

    protected static DataBundle loadDataBundle(String pathToJsonFileParam) {
        try {
            String jsonString = new String(
                    Files.readAllBytes(Paths.get(TestProperties.TEST_DATA_FOLDER + pathToJsonFileParam)));
            return new ObjectMapper().readValue(jsonString, DataBundle.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
