package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.DBEntityMetaData;
import sg.edu.nus.comp.cs3219.viz.logic.DBMetaDataLogic;

import java.util.List;

@RestController
public class DBMetaDataController extends BaseRestController {

    private DBMetaDataLogic dbMetaDataLogic;

    public DBMetaDataController(DBMetaDataLogic dbMetaDataLogic) {
        this.dbMetaDataLogic = dbMetaDataLogic;
    }

    @GetMapping("/db/entity")
    public List<DBEntityMetaData> getEntityMetaDataList() {
        return dbMetaDataLogic.getEntityMetaDataList();
    }

}
