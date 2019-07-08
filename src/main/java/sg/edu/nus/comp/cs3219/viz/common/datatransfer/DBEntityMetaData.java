package sg.edu.nus.comp.cs3219.viz.common.datatransfer;

import java.util.List;

public class DBEntityMetaData {

    private String tableName;

    private String name;

    private String description;

    private List<DBFieldMetaData> fieldMetaDataList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DBFieldMetaData> getFieldMetaDataList() {
        return fieldMetaDataList;
    }

    public void setFieldMetaDataList(List<DBFieldMetaData> fieldMetaDataList) {
        this.fieldMetaDataList = fieldMetaDataList;
    }
}
