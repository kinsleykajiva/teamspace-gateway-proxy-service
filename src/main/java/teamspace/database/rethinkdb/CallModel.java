package teamspace.database.rethinkdb;

import java.time.OffsetDateTime;

import static teamspace.database.rethinkdb.DBManager.getinstance;
import static teamspace.database.rethinkdb.DBManager.r;


public class CallModel {



    public static final void saveCall(String fromUserId , String  toUserId , String callType , String companyClientId ) {
        OffsetDateTime myDateTime = OffsetDateTime.now();
        r.table("calls")
                .insert(
                        r.hashMap("fromUserId", fromUserId)
                                .with("toUserId", toUserId)
                                .with("callType", callType)
                                .with("companyClientId", companyClientId)

                                .with("createdAt", myDateTime)
                ).run(getinstance().getConn());
    }


}
