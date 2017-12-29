package com.springboot_camel.base.mssql;

import com.springboot_camel.base.excel.SheetHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("sheetDataRepository")
public class XLSXSheetsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertRow(SheetHolder sh) {

        /*
         *  Using raw JDBC Sql Server Connection
         */
//        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=Blogs;user=sa;password=Passw0rd";
//        try(Connection conn = DriverManager.getConnection(connectionUrl)) {
//            System.out.println(conn.getClass());
//            System.out.println(jdbcTemplate.getDataSource().getConnection());
//            String sql = new StringBuilder().append("INSERT into HealthMember (FirstName, LastName, Occupation) ").append("VALUES (?, ?, ?);")
//                    .toString();
//            try (PreparedStatement statement = conn.prepareStatement(sql)) {
//                statement.setString(1, "Jake");
//                statement.setString(2, "Arton");
//                statement.setString(3, "Player");
//                int rowsAffected = statement.executeUpdate();
//                System.out.println(rowsAffected + " row(s) inserted");
//            }
//
//            System.out.println("done!!");
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        /*
         * Below using AutoConfigured DataSource by Spring Boot,
         * this DataSource get a connection from TomCat Connection Pool
         */
        int rowNum;
        try {
            String sqlString = "insert into HealthMember (FirstName, LastName, Occupation) values (?, ?, ?)";
            rowNum = jdbcTemplate.update(sqlString, "Ujin", "Karmonova", "Manager");
        }
        catch (Exception e) {
            throw e;
        }
        System.out.println("done!!");
        return rowNum;
    }
}
