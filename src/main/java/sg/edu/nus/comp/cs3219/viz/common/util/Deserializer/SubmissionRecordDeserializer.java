package sg.edu.nus.comp.cs3219.viz.common.util.Deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.SubmissionAuthorRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.SubmissionRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Version;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SubmissionRecordDeserializer extends StdDeserializer<SubmissionRecord> {

    public SubmissionRecordDeserializer() {this(null);}

    public SubmissionRecordDeserializer(Class t){
        super(t);
    }

    private String getStrValueByField(JsonNode node, String fieldName) {
        if (node.hasNonNull(fieldName)){
            return node.get(fieldName).asText();
        }
        return null;
    }

    private List<String> getListOfStrValueByField(JsonNode node, String fieldName){
        if (node.hasNonNull(fieldName)){
            List<String> stringValueList = new ArrayList<>();
            Iterator<JsonNode> temp = node.get(fieldName).elements();
            while (temp.hasNext()){
                String author = temp.next().asText();
                stringValueList.add(author);
            }
            return stringValueList;
        }
        return null;
    }

    private Date getDateValueByField(JsonNode node, String fieldName) {
        if (node.hasNonNull(fieldName)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.parse(node.get(fieldName).asText());
            } catch (ParseException e) {
                System.out.println("Error in parsing/ deserializing date");
            }
        }
        return null;
    }
    @Override
    public SubmissionRecord deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        System.out.println("called");
        JsonNode node = p.getCodec().readTree(p);
        String versionStr = getStrValueByField(node, "versionId");
        Version version = new Version(new Version.VersionPK(null, null, versionStr));

        String submissionId = getStrValueByField(node, "submissionId");
        String trackId = getStrValueByField(node,"trackId");
        String trackName = getStrValueByField(node, "trackName");
        String title = getStrValueByField(node, "title");
        List<String> authors = getListOfStrValueByField(node, "authors");
        Date submissionTime = getDateValueByField(node, "submissionTime");
        Date lastUpdatedTime = getDateValueByField(node, "lastUpdatedTime");
        String keywords = getStrValueByField(node, "keywords");
        String isAccepted = getStrValueByField(node, "isAccepted");
        String isNotified = getStrValueByField(node, "isNotified");
        String isReviewsSent = getStrValueByField(node, "isReviewsSent");
        String submissionAbstract = getStrValueByField(node, "submissionAbstract");

        return new SubmissionRecord(version, submissionId, trackId, trackName, title, authors,
                submissionTime, lastUpdatedTime, keywords, isAccepted,
                isNotified, isReviewsSent, submissionAbstract);
    }
}
