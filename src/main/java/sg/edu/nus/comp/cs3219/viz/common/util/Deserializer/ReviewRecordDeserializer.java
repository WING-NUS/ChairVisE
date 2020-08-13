package sg.edu.nus.comp.cs3219.viz.common.util.Deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.ReviewRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Version;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewRecordDeserializer extends StdDeserializer<ReviewRecord> {
    public ReviewRecordDeserializer() {
        this(null);
    }

    public ReviewRecordDeserializer(Class t) {
        super(t);
    }

    private String getStrValueByField(JsonNode node, String fieldName) {
        if (node.hasNonNull(fieldName)) {
            return node.get(fieldName).asText();
        }
        return null;
    }

    private int getIntValueByField(JsonNode node) {
        if (node.hasNonNull("numReviewAssignment")) {
            return node.get("numReviewAssignment").asInt();
        }
        return 0;
    }

    private double getDoubleValueByField(JsonNode node, String fieldName) {
        if (node.hasNonNull(fieldName)) {
            return node.get(fieldName).asDouble();
        }
        return 0;
    }

    private Date getDateValueByField(JsonNode node) {
        if (node.hasNonNull("reviewSubmissionTime")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(node.get("reviewSubmissionTime").asText());
            } catch (ParseException e) {
                System.out.println("Error in parsing/ deserializing date");
            }
        }
        return null;
    }

    @Override
    public ReviewRecord deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        JsonNode node = p.getCodec().readTree(p);
        String versionStr = getStrValueByField(node, "versionId");
        Version version = new Version(new Version.VersionPK(null, null, versionStr));

        String submissionId = getStrValueByField(node, "submissionId");
        String reviewId = getStrValueByField(node, "reviewId");
        int numReviewAssignment = getIntValueByField(node);
        String reviewerName = getStrValueByField(node, "reviewerName");
        double expertiseLevel = getDoubleValueByField(node, "expertiseLevel");
        double confidenceLevel = getDoubleValueByField(node, "confidenceLevel");
        String reviewComment = getStrValueByField(node, "reviewComment");
        double overallEvaluationScore = getDoubleValueByField(node, "overallEvaluationScore");
        Date reviewSubmissionTime = getDateValueByField(node);
        String hasRecommendedForBestPaper = getStrValueByField(node, "hasRecommendedForBestPaper");

        return new ReviewRecord(version, submissionId, reviewId, numReviewAssignment, reviewerName, expertiseLevel,
                confidenceLevel, reviewComment, overallEvaluationScore, reviewSubmissionTime, hasRecommendedForBestPaper);
    }
}
