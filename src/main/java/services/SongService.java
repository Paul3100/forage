package services;

import javax.ws.rs.*;
import java.io.IOException;
// Going
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.System.out;

@Path("/names")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class SongService {

    @GET
    @Path("add/{firstname}/{lastname}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public String store(@PathParam("firstname") String firstname,@PathParam("lastname") String lastname) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://34.142.127.164:5432/start",
                            "postgres", "postgres");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        try{
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO details VALUES('%s','%s');",firstname,lastname);
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        out.println("Data inserted successfully");

        return firstname+" "+lastname;

    }
    @GET
    @Path("check/{firstname}/{lastname}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public String check(@PathParam("firstname") String firstname,@PathParam("lastname") String lastname) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://34.142.127.164:5432/start",
                            "postgres", "postgres");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        try{
            stmt = c.createStatement();
            String sql = String.format("SELECT * FROM details WHERE first='%s' AND surname='%s';",firstname,lastname);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                stmt.close();
                c.close();
                return "true";
            } else{
                stmt.close();
                c.close();
                return "false";
            }


        }catch ( Exception e ) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return firstname+" "+lastname;

    }

}