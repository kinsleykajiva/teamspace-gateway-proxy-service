package teamspace.database.rethinkdb;

import java.time.OffsetDateTime;

import static teamspace.database.rethinkdb.DBManager.getinstance;
import static teamspace.database.rethinkdb.DBManager.r;

public class GroupModel {


    public static final void saveTeam(String title, String  companyClientId , boolean isActive ) {
        OffsetDateTime myDateTime = OffsetDateTime.now();
        r.table("groups")
                .insert(
                        r.hashMap("title", title)
                                .with("isActive", isActive)
                                .with("companyClientId", companyClientId)
                                .with("createdAt", myDateTime)
                ).run(getinstance().getConn());
    }

}
