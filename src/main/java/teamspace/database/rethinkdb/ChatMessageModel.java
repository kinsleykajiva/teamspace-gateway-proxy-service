package teamspace.database.rethinkdb;

import java.time.OffsetDateTime;

import static teamspace.database.rethinkdb.DBManager.getinstance;
import static teamspace.database.rethinkdb.DBManager.r;

public class ChatMessageModel {


    public static final void saveChatMessage(String fromUserId ,  String toUserId ,String sentByUserId ,String message , String messageType , String companyClientId ,  boolean isActive ) {
        OffsetDateTime myDateTime = OffsetDateTime.now();
        r.table("chatMessages")
                .insert(
                        r.hashMap("fromUserId", fromUserId)
                                .with("toUserId", toUserId)
                                .with("sentByUserId", sentByUserId)
                                .with("message", message)
                                .with("messageType", messageType)
                                .with("companyClientId", companyClientId)
                                .with("isActive", isActive)

                                .with("createdAt", myDateTime)
                ).run(getinstance().getConn());
    }
}
