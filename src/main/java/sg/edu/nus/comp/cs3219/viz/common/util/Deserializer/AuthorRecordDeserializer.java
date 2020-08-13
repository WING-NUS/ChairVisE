package sg.edu.nus.comp.cs3219.viz.common.util.Deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.AuthorRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Version;

import java.io.IOException;

public class AuthorRecordDeserializer extends StdDeserializer<AuthorRecord> {

    public AuthorRecordDeserializer() {this(null);}

    public AuthorRecordDeserializer(Class t){
        super(t);
    }

    private String getStrValueByField(JsonNode node, String fieldName){
        if (node.hasNonNull(fieldName)){
            return node.get(fieldName).asText();
        }
        return null;
    }

    @Override
    public AuthorRecord deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        JsonNode node = p.getCodec().readTree(p);
        String versionStr = getStrValueByField(node, "versionId");
        Version version = new Version(new Version.VersionPK(null, null, versionStr));

        String submissionId = getStrValueByField(node, "submissionId");
        String firstName = getStrValueByField(node,"firstName");
        String lastName = getStrValueByField(node, "lastName");
        String email = getStrValueByField(node, "email");
        String country = getStrValueByField(node, "country");
        String organisation = getStrValueByField(node, "organisation");
        String webPage = getStrValueByField(node, "webPage");
        String personId = getStrValueByField(node, "personId");
        String isCorresponding = getStrValueByField(node, "isCorresponding");

        return new AuthorRecord(version, submissionId, firstName, lastName, email, country, organisation, webPage, personId, isCorresponding);
    }
}
