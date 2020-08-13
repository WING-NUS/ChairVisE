package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.annotations.GenericGenerator;
import sg.edu.nus.comp.cs3219.viz.common.util.Deserializer.SubmissionRecordDeserializer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@JsonDeserialize(using = SubmissionRecordDeserializer.class)
@Exportable(name = "Submission Record", nameInDB = "submission_record")
@Entity
public class SubmissionRecord {
    public SubmissionRecord(){}

    public SubmissionRecord(Version v, String submissionId, String trackId, String trackName, String title, List<String> authors,
                            Date submissionTime, Date lastUpdatedTime, String keywords, String isAccepted,
                            String isNotified, String isReviewsSent, String submissionAbstract) {
        this.id = null;
        this.version = v;
        this.submissionId = submissionId;
        this.trackId = trackId;
        this.trackName = trackName;
        this.title = title;
        this.authors = authors;
        this.submissionTime = submissionTime;
        this.lastUpdatedTime = lastUpdatedTime;
        this.keywords = keywords;
        this.isAccepted = isAccepted;
        this.isNotified = isNotified;
        this.isReviewsSent = isReviewsSent;
        this.submissionAbstract = submissionAbstract;
    }

    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "sg.edu.nus.comp.cs3219.viz.common.entity.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "s_id")
    private Long id;
    /*
    // each record will be imported by each user, dataSet is used to distinguished records submitted by different user
    private String dataSet;

    public String getDataSet() {
        return dataSet;
    }

    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
    }
*/

    public Version getVersion(){return version;}
    public void setVersion(Version version){this.version = version;}

    @Exportable(name = "Submission Id", nameInDB = "s_submission_id")
    @Column(name = "s_submission_id")
    private String submissionId;

    // Track the submission is submitted to; like a full paper or just a poster
    @Exportable(name = "Track Id", nameInDB = "s_track_id", description = "Track the submission is submitted to")
    @Column(name = "s_track_id")
    private String trackId;

    // Name for the track referred in col2 (string)
    @Exportable(name = "Track Name", nameInDB = "s_track_name")
    @Column(name = "s_track_name")
    private String trackName;

    // Title of the submission
    @Exportable(name = "Title", nameInDB = "s_title")
    @Column(name = "s_title", columnDefinition = "TEXT")
    private String title;

    // authors of the associated submission
    @Exportable(name = "Authors", nameInDB = "s_authors")
    @Transient
    private List<String> authors;

    // internal set of authors of the associated
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.REFRESH,
        CascadeType.PERSIST
    })    
    @JoinTable(name = "submission_record_author_set",
            joinColumns = { 
                @JoinColumn(name = "s_id") 
            },
            inverseJoinColumns = { 
                @JoinColumn(name = "s_author_id", nullable = false, updatable = false) 
            })
    @JsonIgnore
    private List<SubmissionAuthorRecord> authorSet;

    // time submitted
    @Exportable(name = "Submission Time", nameInDB = "s_submission_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "s_submission_time")
    private Date submissionTime;

    //  time last updated
    @Exportable(name = "Last Updated Time", nameInDB = "s_last_updated_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "s_last_updated_time")
    private Date lastUpdatedTime;

    // keywords associated with submissions as put by the authors
    @Exportable(name = "Keywords", nameInDB = "s_keywords")
    @Column(name = "s_keywords", columnDefinition = "TEXT")
    private String keywords;

    // accept/reject decision
    @Exportable(name = "Is Accepted", nameInDB = "s_is_accepted", description = "Accept/Reject decision")
    @Column(name = "s_is_accepted")
    @JsonProperty("isAccepted")
    private String isAccepted;

    // acceptance/rejection mail sent to authors or not?
    @Exportable(name = "Is Notified", nameInDB = "s_is_notified", description = "Acceptance/rejection mail sent to authors or not?")
    @Column(name = "s_is_notified")
    @JsonProperty("isNotified")
    private String isNotified;

    // review sent in the mails or not?
    @Exportable(name = "Is Reviews Sent", nameInDB = "s_is_reviews_sent", description = "Review sent in the mails or not?")
    @Column(name = "s_is_reviews_sent")
    @JsonProperty("isReviewsSent")
    private String isReviewsSent;

    // abstract of the submission.
    @Exportable(name = "Submission Abstract", nameInDB = "s_submission_abstract", description = "Abstract of the submission")
    @Column(name = "s_submission_abstract", columnDefinition = "TEXT")
    private String submissionAbstract;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "data_set", referencedColumnName = "data_set"),
            @JoinColumn(name = "record_type", referencedColumnName = "record_type"),
            @JoinColumn(name = "version", referencedColumnName = "version"),
    })
    private Version version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<SubmissionAuthorRecord> getAuthorSet() {
        return authorSet;
    }

    public void setAuthorSet(List<SubmissionAuthorRecord> authorSet) {
        this.authorSet = authorSet;
    }

    public Date getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Date submissionTime) {
        this.submissionTime = submissionTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getIsNotified() {
        return isNotified;
    }

    public void setIsNotified(String isNotified) {
        this.isNotified = isNotified;
    }

    public String getIsReviewsSent() {
        return isReviewsSent;
    }

    public void setIsReviewsSent(String isReviewsSent) {
        this.isReviewsSent = isReviewsSent;
    }

    public String getSubmissionAbstract() {
        return submissionAbstract;
    }

    public void setSubmissionAbstract(String submissionAbstract) {
        this.submissionAbstract = submissionAbstract;
    }
}
