package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.annotations.GenericGenerator;
import sg.edu.nus.comp.cs3219.viz.common.util.Deserializer.ReviewRecordDeserializer;

import javax.persistence.*;
import java.util.Date;

@JsonDeserialize(using = ReviewRecordDeserializer.class)
@Exportable(name = "Review Record", nameInDB = "review_record")
@Entity
public class ReviewRecord {
    public ReviewRecord(){}

    public ReviewRecord(Version v, String submissionId, String reviewId, int numReviewAssignment, String reviewerName, double expertiseLevel,
                        double confidenceLevel, String reviewComment, double overallEvaluationScore, Date reviewSubmissionTime, String hasRecommendedForBestPaper){
        this.id = null;
        this.version = v;
        this.submissionId = submissionId;
        this.reviewId = reviewId;
        this.numReviewAssignment = numReviewAssignment;
        this.reviewerName = reviewerName;
        this.expertiseLevel = expertiseLevel;
        this.confidenceLevel = confidenceLevel;
        this.reviewComment = reviewComment;
        this.overallEvaluationScore = overallEvaluationScore;
        this.reviewSubmissionTime = reviewSubmissionTime;
        this.hasRecommendedForBestPaper = hasRecommendedForBestPaper;
    }

    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "sg.edu.nus.comp.cs3219.viz.common.entity.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "r_id")
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

    @Exportable(name = "Submission Id", nameInDB = "r_submission_id")
    @Column(name = "r_submission_id")
    private String submissionId;

    @Exportable(name = "Review Id", nameInDB = "r_review_id")
    @Column(name = "r_review_id")
    private String reviewId;

    // (Each reviewer is given a number for each track he/she is reviewing.
    // For example Animesh reviewed 2 different tracks but 3 papers in total- one from Track 1 and two papers from Track 2. He therefore has 2 uniques numbers assigned
    @Exportable(name = "Num Review Assignment", nameInDB = "r_num_review_assignment", description = "Each reviewer is given a number for each track he/she is reviewing")
    @Column(name = "r_num_review_assignment")
    private int numReviewAssignment;

    @Exportable(name = "Reviewer Name", nameInDB = "r_reviewer_name")
    @Column(name = "r_reviewer_name")
    private String reviewerName;

    // Reviewer selects a field 1-5 to indicate expertise while submitting the review. For example 5: expert, 1: passing knowledge
    @Exportable(name = "Expertise Level", nameInDB = "r_expertise_level", description = "Reviewer selects a field 1-5 to indicate expertise when submitting the review.")
    @Column(name = "r_expertise_level")
    private double expertiseLevel;

    @Exportable(name = "Confidence Level", nameInDB = "r_confidence_level", description = "Reviewer selects a field 1-5 to indicate confidence level for the review.")
    @Column(name = "r_confidence_level")
    private double confidenceLevel;

    @Exportable(name = "Review Comment", nameInDB = "r_review_comment")
    @Column(name = "r_review_comment", columnDefinition = "TEXT")
    private String reviewComment;

    @Exportable(name = "Overall Evaluation Score", nameInDB = "r_overall_evaluation_score")
    @Column(name = "r_overall_evaluation_score")
    private double overallEvaluationScore;

    @Exportable(name = "Review Submission Time", nameInDB = "r_review_submission_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "r_review_submission_time")
    private Date reviewSubmissionTime;

    @Exportable(name = "Has Recommended for the Best Paper", nameInDB = "r_has_recommended_for_best_paper")
    @Column(name = "r_has_recommended_for_best_paper")
    private String hasRecommendedForBestPaper;

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

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public int getNumReviewAssignment() {
        return numReviewAssignment;
    }

    public void setNumReviewAssignment(int numReviewAssignment) {
        this.numReviewAssignment = numReviewAssignment;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public double getExpertiseLevel() {
        return expertiseLevel;
    }

    public void setExpertiseLevel(double expertiseLevel) {
        this.expertiseLevel = expertiseLevel;
    }

    public double getConfidenceLevel() {
        return confidenceLevel;
    }

    public void setConfidenceLevel(double confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public double getOverallEvaluationScore() {
        return overallEvaluationScore;
    }

    public void setOverallEvaluationScore(double overallEvaluationScore) {
        this.overallEvaluationScore = overallEvaluationScore;
    }

    public Date getReviewSubmissionTime() {
        return reviewSubmissionTime;
    }

    public void setReviewSubmissionTime(Date reviewSubmissionTime) {
        this.reviewSubmissionTime = reviewSubmissionTime;
    }

    public String getHasRecommendedForBestPaper() {
        return hasRecommendedForBestPaper;
    }

    public void setHasRecommendedForBestPaper(String hasRecommendedForBestPaper) {
        this.hasRecommendedForBestPaper = hasRecommendedForBestPaper;
    }
}
