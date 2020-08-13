package sg.edu.nus.comp.cs3219.viz.common.util.Serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Version;

import java.io.IOException;

public class VersionSerializer extends StdSerializer<Version> {

    public VersionSerializer() {this(null);}

    protected VersionSerializer(Class<Version> t) {
        super(t);
    }

    @Override
    public void serialize(Version value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("recordType", value.getId().getRecordType());
        gen.writeStringField("versionId", value.getId().getVersion());
        gen.writeEndObject();
    }
}
