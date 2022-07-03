package services;

import javax.ws.rs.*;
import java.io.IOException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;


@Path("/songs")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class SongService {

    @GET
    @Path("/{firstname}/{lastname}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public String store(@PathParam("firstname") String firstname,@PathParam("lastname") String lastname) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:postgresql:///%s", "start"));
        config.setUsername("postgres"); // e.g. "root", "postgres"
        config.setPassword("postgres"); // e.g. "my-password"

        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", "holdmystuff");

        DataSource pool = new HikariDataSource(config);
        /*
        try {
            write(pool,firstname,lastname);
        } catch (SQLException e) {
            return "Fail";
        }

         */
        return firstname+" "+lastname;

    }
    private static void write(DataSource pool,String firstname,String lastname) throws SQLException {
        // Safely attempt to create the table schema.
        try (Connection conn = pool.getConnection()) {
            String stmt = "INSERT INTO users VALUES('Paolo','Agyei');";

            try (PreparedStatement createTableStatement = conn.prepareStatement(stmt);) {
                createTableStatement.execute();
            }
        }
    }

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public String store() {
        return "Successfully complete";
    }
}