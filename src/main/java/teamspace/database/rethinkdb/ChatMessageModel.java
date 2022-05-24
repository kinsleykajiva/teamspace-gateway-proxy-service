package teamspace.database.rethinkdb;

import com.rethinkdb.net.Cursor;
import teamspace.pojo.ChatMessagePojo;
import teamspace.pojo.CompanyPojo;

import java.time.OffsetDateTime;
import java.util.List;

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


    public static List<ChatMessagePojo> getUsersMessages(String userId){

        Cursor<ChatMessagePojo> cursor = r.table("chatMessages")
                .filter(row -> row.g("toUserId").eq( userId)
                        .or(row.g("sentByUserId").eq( userId))
                        .or(row.g("fromUserId").eq( userId))
                )
                .run(getinstance().getConn(), ChatMessagePojo.class);

        return cursor.bufferedItems();
       /* return r.table("chatMessages")
                .filter(r.hashMap("fromUserId", userId).or(r.hashMap("toUserId", userId)))
                .orderBy(r.desc("createdAt"))
                .run(getinstance().getConn() ,ChatMessagePojo.class)
                .toList();*/

    }





}
