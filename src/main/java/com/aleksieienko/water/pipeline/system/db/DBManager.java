package com.aleksieienko.water.pipeline.system.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	
	static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DATABASE_URL = "jdbc:h2:~/test";
    static final String USER = "root";
    static final String PASSWORD = "root";
	

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static DBManager instance;

	public static synchronized DBManager getInstance() {
		if (instance == null)
			instance = new DBManager();
		return instance;
	}

	public Connection getConnection() throws SQLException {
		Connection con = null;
		try {
			Class.forName(JDBC_DRIVER);
	        con = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
	        con.setAutoCommit(false);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return con;
	}/**/

	private DBManager() {
	}

	public void commitAndClose(Connection con) {
		try {
			con.commit();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void rollbackAndClose(Connection con) {
		try {
			con.rollback();
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
