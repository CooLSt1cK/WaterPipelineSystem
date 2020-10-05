package com.aleksieienko.water.pipeline.system.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class GraphDao {

    public boolean insertGraph(String filePath) throws SQLException {
        Statement stmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            stmt.execute("drop table pipes if exists");
            stmt.execute("create table pipes( " +
                    "id int AUTO_INCREMENT primary key, " +
                    "point_x int not null," +
                    "point_y int not null," +
                    "length int not null)");
            stmt.execute("insert into pipes(point_x,point_y,length) select convert( \"IDX\",int ), convert( \"IDY\",int ), convert( \"LENGTH\", int) from CSVREAD( '" + filePath + "', null, 'UTF-8', ';')");

            stmt.close();
        } catch (SQLException ex) {
            if(con!=null && !con.isClosed()) {
                DBManager.getInstance().rollbackAndClose(con);
            }
            ex.printStackTrace();
            return false;
        } finally {
            if(con!=null && !con.isClosed()) {
                DBManager.getInstance().commitAndClose(con);
            }
        }
        return true;
    }
}
