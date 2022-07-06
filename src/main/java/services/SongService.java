package services;

import javax.ws.rs.*;
import java.io.IOException;
// Going
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import org.springframework.web.bind.annotation.RestController;

@Path("/names")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class SongService {

    @GET
    @Path("/{firstname}/{lastname}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public String store(@PathParam("firstname") String firstname,@PathParam("lastname") String lastname) {
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
            String sql = String.format("INSERT INTO details VALUES('%s','%s');",firstname,lastname);
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Data inserted successfully");



        return firstname+" "+lastname;

    }
    /*
    private static void write(DataSource pool,String firstname,String lastname) throws SQLException {
        // Safely attempt to create the table schema.
        try (Connection conn = pool.getConnection()) {
            String stmt = "INSERT INTO users VALUES('Paolo','Agyei');";

            try (PreparedStatement createTableStatement = conn.prepareStatement(stmt);) {
                createTableStatement.execute();
            }
        }
    }

     */

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public String store() {
        return "Successfully complete";
    }
}