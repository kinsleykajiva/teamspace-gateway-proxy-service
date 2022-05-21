package teamspace.database.rethinkdb;

import com.rethinkdb.net.Cursor;
import teamspace.pojo.CompanyPojo;

import java.time.OffsetDateTime;
import java.util.List;

import static teamspace.database.rethinkdb.DBManager.getinstance;
import static teamspace.database.rethinkdb.DBManager.r;


public class CompanyModel {


    public static final void saveCompany(String title, String adminEmail) {
        OffsetDateTime myDateTime = OffsetDateTime.now();
        r.table("company")
                .insert(
                        r.hashMap("title", title)
                                .with("adminEmail", adminEmail)
                                .with("isActive", true)
                                .with("createdAt", myDateTime)
                ).run(getinstance().getConn());
    }


    public static List<CompanyPojo> getAllCompanies() {
        Cursor<CompanyPojo> cursor = r.table("company").run(getinstance().getConn(), CompanyPojo.class);

        return cursor.bufferedItems();


    }

    public static CompanyPojo getCompanyById(String companyClientId) {
        System.out.println("companyClientId: " + companyClientId);
        Cursor<CompanyPojo> cursor = r.table("company").filter(row -> row.g("id_").eq( companyClientId)).run(getinstance().getConn(), CompanyPojo.class);

        return cursor.next();


    }

    public static CompanyPojo getCompanyByName(String title) {
        Cursor<CompanyPojo> cursor = r.table("company").filter(row -> row.g("title").eq( title)).run(getinstance().getConn(), CompanyPojo.class);
        // return cursor.next();
      //  cursor.bufferedItems().forEach(e->System.out.println( "XXXX:: "+ e.getTitle()));
          return cursor.next();

    }

    public static boolean checkIfCompanyExists(String title) {
        Cursor<CompanyPojo> cursor = r.table("company").filter(row -> row.g("title").eq(title)).run(getinstance().getConn(), CompanyPojo.class);
       /* System.out.println(cursor.bufferedItems().size());
        cursor.forEach(System.out::println);*/
        return cursor.bufferedItems().size() > 0;


    }
}
