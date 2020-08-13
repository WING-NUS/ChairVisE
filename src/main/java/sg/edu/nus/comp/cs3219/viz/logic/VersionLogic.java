package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.UserInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Version;
import sg.edu.nus.comp.cs3219.viz.storage.repository.VersionRepository;

import java.util.List;

@Component
public class VersionLogic {

    private final VersionRepository versionRepository;

    public VersionLogic(VersionRepository versionRepository){
        this.versionRepository = versionRepository;
    }

    public List<Version> findAllForUser(UserInfo userInfo){
        return versionRepository.findById_DataSetEquals(userInfo.getUserEmail());
    }

    public List<Version> findAllForUserWithRecordType(UserInfo userInfo, String recordType){
        return versionRepository.findById_DataSetAndId_RecordType(userInfo.getUserEmail(), recordType);
    }

    public Version saveForUser(Version version, UserInfo userInfo){
        Version newVersion = new Version();
        Version.VersionPK newVersionID = version.getId();
        newVersionID.setDataSet(userInfo.getUserEmail());
        newVersion.setId(newVersionID);

        return versionRepository.save(newVersion);
    }

}
