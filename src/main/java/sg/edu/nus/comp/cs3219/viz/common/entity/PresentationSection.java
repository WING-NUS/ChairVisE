package sg.edu.nus.comp.cs3219.viz.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import sg.edu.nus.comp.cs3219.viz.logic.AnalysisLogic;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Entity
public class PresentationSection {

    private static final Logger log = Logger.getLogger(AnalysisLogic.class.getSimpleName());

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "sg.edu.nus.comp.cs3219.viz.common.entity.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presentation_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Presentation presentation;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String type;

    private String dataSet;

    // The following field does not worth to be stored as relation in RDBMS
    // we store them as serialized json string

    @Column(columnDefinition = "TEXT")
    private String selections;
    //private List<Selection> selections = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String involvedRecords;
    //private List<Record> involvedRecords = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String filters;
    //private List<Filter> filters = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String joiners;
    //private List<Joiner> joiners = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String groupers;
    //private List<Grouper> joiners = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String sorters;
    //private List<Sorter> joiners = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String extraData;

    public static class Selection {
        private String expression;

        private String rename;

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public String getRename() {
            return rename;
        }

        public void setRename(String rename) {
            this.rename = rename;
        }
    }

    public static class Record {
        private String name;

        private boolean isCustomized;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean isCustomized() {
            return isCustomized;
        }

        public void setCustomized(boolean customized) {
            isCustomized = customized;
        }
    }

    public static class Filter {
        private String field;
        private String comparator;
        private String value;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getComparator() {
            return comparator;
        }

        public void setComparator(String comparator) {
            this.comparator = comparator;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Joiner {
        private String left;
        private String right;

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }
    }

    public static class Grouper {
        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }

    public static class Sorter {
        private String field;

        private String order;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataSet() {
        return dataSet;
    }

    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
    }

    public List<Selection> getSelections() {
        try {
            return objectMapper.readValue(selections, new TypeReference<List<Selection>>() {
            });
        } catch (IOException e) {
            log.severe(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void setSelections(List<Selection> selections) {
        try {
            this.selections = objectMapper.writeValueAsString(selections);
        } catch (JsonProcessingException e) {
            log.severe(e.getMessage());
        }
    }

    public List<Record> getInvolvedRecords() {
        try {
            return objectMapper.readValue(involvedRecords, new TypeReference<List<Record>>() {
            });
        } catch (IOException e) {
            log.severe(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void setInvolvedRecords(List<Record> involvedRecords) {
        try {
            this.involvedRecords = objectMapper.writeValueAsString(involvedRecords);
        } catch (JsonProcessingException e) {
            log.severe(e.getMessage());
        }
    }

    public List<Filter> getFilters() {
        try {
            return objectMapper.readValue(filters, new TypeReference<List<Filter>>() {
            });
        } catch (IOException e) {
            log.severe(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void setFilters(List<Filter> filters) {
        try {
            this.filters = objectMapper.writeValueAsString(filters);
        } catch (JsonProcessingException e) {
            log.severe(e.getMessage());
        }
    }

    public List<Joiner> getJoiners() {
        try {
            return objectMapper.readValue(joiners, new TypeReference<List<Joiner>>() {
            });
        } catch (IOException e) {
            log.severe(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void setJoiners(List<Joiner> joiners) {
        try {
            this.joiners = objectMapper.writeValueAsString(joiners);
        } catch (JsonProcessingException e) {
            log.severe(e.getMessage());
        }
    }

    public List<Grouper> getGroupers() {
        try {
            return objectMapper.readValue(groupers, new TypeReference<List<Grouper>>() {
            });
        } catch (IOException e) {
            log.severe(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void setGroupers(List<Grouper> groupers) {
        try {
            this.groupers = objectMapper.writeValueAsString(groupers);
        } catch (JsonProcessingException e) {
            log.severe(e.getMessage());
        }
    }

    public List<Sorter> getSorters() {
        try {
            return objectMapper.readValue(sorters, new TypeReference<List<Sorter>>() {
            });
        } catch (IOException e) {
            log.severe(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void setSorters(List<Sorter> sorters) {
        try {
            this.sorters = objectMapper.writeValueAsString(sorters);
        } catch (JsonProcessingException e) {
            log.severe(e.getMessage());
        }
    }

    public Map<String, Object> getExtraData() {
        try {
            return objectMapper.readValue(extraData, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            log.severe(e.getMessage());
            return new HashMap<>();
        }
    }

    public void setExtraData(Map<String, Object> extraData) {
        try {
            this.extraData = objectMapper.writeValueAsString(extraData);
        } catch (JsonProcessingException e) {
            log.severe(e.getMessage());
        }
    }
}
