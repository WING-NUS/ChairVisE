package sg.edu.nus.comp.cs3219.viz.common.util.Deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Version;

import java.io.IOException;

public class VersionDeserializer extends StdDeserializer<Version> {

    public VersionDeserializer() {this(null);}

    protected VersionDeserializer(Class<?> vc) {
        super(vc);
    }

    private String getStrValueByField(JsonNode node, String fieldName){
        if (node.hasNonNull(fieldName)){
            return node.get(fieldName).asText();
        }
        return null;
    }

    @Override
    public Version deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        String dataSet = null;
        String recordType = getStrValueByField(node, "recordType");
        String version = getStrValueByField(node, "versionId");

        return new Version(new Version.VersionPK(dataSet, recordType, version));
    }
}
