package teamspace.database.rethinkdb;



import com.rethinkdb.net.Cursor;
import teamspace.pojo.UserPojo;

import java.time.OffsetDateTime;
import java.util.List;

import static teamspace.database.rethinkdb.DBManager.getinstance;
import static teamspace.database.rethinkdb.DBManager.r;


public class UserModel {



    public static final void saveUser(String email,String password,String fullName , String profilePictureUrl ,
                                         boolean isActive,boolean isIsVerified, String companyClientId) {
        OffsetDateTime myDateTime = OffsetDateTime.now();
        r.table("users")
                .insert(
                        r.hashMap("email", email)
                                .with("password", password)
                                .with("fullName", fullName)
                                .with("profilePictureUrl", profilePictureUrl)
                                .with("isActive", isActive)
                                .with("companyClientId", companyClientId)
                                .with("isIsVerified", isIsVerified)
                                .with("createdAt", myDateTime)
                ).run(getinstance().getConn());
    }

    public static List<UserPojo> getAllUsers(){
        Cursor<UserPojo> cursor =  r.table("users").run(getinstance().getConn(), UserPojo.class);

        return cursor.bufferedItems();


    }

    public static List<UserPojo> getAllUsersByCompanyId(final String companyClientId){
        Cursor<UserPojo> cursor =  r.table("users")
                .filter(row->row.g("companyClientId").eq(companyClientId))
                .pluck("id","email","fullName","profilePictureUrl","isActive","isIsVerified","id_","companyClientId" ,"createdAt")
                .run(getinstance().getConn(), UserPojo.class);

        return cursor.bufferedItems();


    }

  /*  public static List<UserPojo> getAllUsersByCompanyIdAndIgnoreUser(final String companyClientId,String userId){
        Cursor<UserPojo> cursor =  r.table("users")
                .filter(row->row.g("companyClientId").eq(companyClientId) && row.not().g("id").eq(userId))
                .pluck("id","email","fullName","profilePictureUrl","isActive","isIsVerified","companyClientId")
                .run(getinstance().getConn(), UserPojo.class);

        return cursor.bufferedItems();


    }*/

    /* r.db('teamspace').table("users").insert({
        "profilePictureUrl": null,
                "createdAt": {
            "timezone": "+02:00",
                    "$reql_type$": "TIME",
                    "epoch_time": 1.653080232226E9
        },
        "password": "$2a$10$UESczqqkoqMYhd72XOhEXeBunptz/xwHGnSdDhEwxjBCGDzPitbK.",

                "companyClientId": "8f42302d-c0ee-4f6c-88d6-d0685aaaa88a",
                "fullName": "Teddy",
                "isActive": true,
                "email": "teddy@gmail.com",
                "isIsVerified": true
    })*/


    public static UserPojo getAUserById(String id_){
        Cursor<UserPojo> cursor =  r.table("users").filter(row->row.g("id_").eq(id_)).run(getinstance().getConn(), UserPojo.class);

        return cursor.next();


    }
    public static UserPojo getByEmailUser(String email){
        Cursor<UserPojo> cursor =  r.table("users").filter(row->row.g("email").eq(email)).run(getinstance().getConn(), UserPojo.class);

        return cursor.next();


    }

    public static boolean checkIfUserExists(String email){
        Cursor<UserPojo> cursor =  r.table("users").filter(row->row.g("email").eq(email)).run(getinstance().getConn(), UserPojo.class);

        return cursor.bufferedItems().size() > 0;


    }
}
