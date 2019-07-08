package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.DBEntityMetaData;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.DBFieldMetaData;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.AuthorRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Exportable;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.ReviewRecord;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.SubmissionRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DBMetaDataLogic {

    private static final List<DBEntityMetaData> entityMetaDataList = new ArrayList<>();


    static {
        registerEntityMetaData(AuthorRecord.class);
        registerEntityMetaData(ReviewRecord.class);
        registerEntityMetaData(SubmissionRecord.class);
    }

    private static void registerEntityMetaData(Class<?> classToRegister) {
        Exportable entityExportable = classToRegister.getAnnotation(Exportable.class);
        DBEntityMetaData metaData = new DBEntityMetaData();
        metaData.setTableName(entityExportable.nameInDB());
        metaData.setName(entityExportable.name());
        metaData.setDescription(entityExportable.description());

        metaData.setFieldMetaDataList(Arrays.stream(classToRegister.getDeclaredFields())
                .filter(f -> f.getAnnotation(Exportable.class) != null)
                .map(f -> {
                    Exportable exportable = f.getAnnotation(Exportable.class);
                    DBFieldMetaData fieldMetaData = new DBFieldMetaData();
                    fieldMetaData.setFieldName(exportable.nameInDB());
                    fieldMetaData.setJsonProperty(f.getName());
                    fieldMetaData.setName(exportable.name());
                    fieldMetaData.setType(f.getType().getSimpleName());
                    fieldMetaData.setDescription(exportable.description());
                    return fieldMetaData;
                }).collect(Collectors.toList()));

        entityMetaDataList.add(metaData);
    }

    public List<DBEntityMetaData> getEntityMetaDataList() {
        return new ArrayList<>(entityMetaDataList);
    }

}
