package sg.edu.nus.comp.cs3219.viz.storage.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sg.edu.nus.comp.cs3219.viz.BaseTestWithDBAccess;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AccessLevel;

public class PresentationAccessControlRepositoryTest extends BaseTestWithDBAccess {

    @Override
    protected String getDataBundleName() {
        return "/PresentationAccessControlRepositoryTest.json";
    }

    @Autowired
    private PresentationAccessControlRepository presentationAccessControlRepository;

    @Test
    public void testExistsByPresentationAndUserIdentifierEqualsAndAccessLevelEquals_typicalCase_shouldReturnsCorrectly() {
        Assert.assertTrue(presentationAccessControlRepository
                .existsByPresentationAndUserIdentifierEqualsAndAccessLevelEquals(
                        dataBundle.presentations.get("presentationA"),
                        "test1@viz.test",
                        AccessLevel.CAN_READ
                ));

        Assert.assertFalse(presentationAccessControlRepository
                .existsByPresentationAndUserIdentifierEqualsAndAccessLevelEquals(
                        dataBundle.presentations.get("presentationA"),
                        "test1@viz.test",
                        AccessLevel.CAN_WRITE
                ));

        Assert.assertFalse(presentationAccessControlRepository
                .existsByPresentationAndUserIdentifierEqualsAndAccessLevelEquals(
                        dataBundle.presentations.get("presentationA"),
                        "test2@viz.test",
                        AccessLevel.CAN_READ
                ));

        Assert.assertTrue(presentationAccessControlRepository
                .existsByPresentationAndUserIdentifierEqualsAndAccessLevelEquals(
                        dataBundle.presentations.get("presentationA"),
                        "test2@viz.test",
                        AccessLevel.CAN_WRITE
                ));
    }
}
