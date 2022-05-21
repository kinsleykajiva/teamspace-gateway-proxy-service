package teamspace.database.rethinkdb;

import java.time.OffsetDateTime;

import static teamspace.database.rethinkdb.DBManager.getinstance;
import static teamspace.database.rethinkdb.DBManager.r;

public class GroupDetailModel {


    public static final void saveTeam(String groupId, String  usersId,String companyClientId , boolean isActive ) {
        OffsetDateTime myDateTime = OffsetDateTime.now();
        r.table("groupDetails")
                .insert(
                        r.hashMap("groupId", groupId)
                                .with("usersId", usersId)
                                .with("isActive", isActive)
                                .with("companyClientId", companyClientId)

                                .with("createdAt", myDateTime)
                ).run(getinstance().getConn());
    }
}
