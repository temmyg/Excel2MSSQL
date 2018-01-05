package com.springboot_camel.base.mssql;

import com.springboot_camel.base.excel.SheetHolder;
import com.springboot_camel.base.model.ClubMember;
import com.springboot_camel.base.model.VisitHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("sheetDataRepository")
public class XLSXSheetsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertRow(Object rowObj) throws Exception   {

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
        int rowNum = 0;
        try {
            if(rowObj instanceof ClubMember){
                String sqlString = "insert into HealthMember (FirstName, LastName, Occupation, Age) values (?, ?, ?, ?)";
                ClubMember row = (ClubMember) rowObj;
                rowNum = jdbcTemplate.update(sqlString,  row.getFirstName(), row.getLastName(), row.getOccupation(),
                        row.getAge());
            }
            else if(rowObj instanceof VisitHistory) {
                String sqlString = "insert into VisitHistory (MemberId, VisitedDate, ExerciseZone) values (?, ?, ?)";
                VisitHistory row = (VisitHistory) rowObj;
                rowNum = jdbcTemplate.update(sqlString,  row.getMemberId(), row.getVisitedDate(), row.getExerciseZone());
            }
        }
        catch (Exception e) {
            throw e;
        }

        return rowNum;
    }
}
