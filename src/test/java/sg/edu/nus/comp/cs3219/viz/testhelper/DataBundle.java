package sg.edu.nus.comp.cs3219.viz.testhelper;

import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationAccessControl;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationSection;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.AuthorRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.ReviewRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.SubmissionRecord;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataBundle {
    public Map<String, Presentation> presentations = new LinkedHashMap<>();
    public Map<String, PresentationAccessControl> presentationAccessControls = new LinkedHashMap<>();
    public Map<String, PresentationSection> presentationSections = new LinkedHashMap<>();

    public Map<String, AuthorRecord> authorRecords = new LinkedHashMap<>();
    public Map<String, ReviewRecord> reviewRecords = new LinkedHashMap<>();
    public Map<String, SubmissionRecord> submissionRecords = new LinkedHashMap<>();
}
