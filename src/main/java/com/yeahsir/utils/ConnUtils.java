package com.yeahsir.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * jdbc的帮助类
 * 可以获取到Connection连接对象
 * 也可以获取DataSource数据源，连接池
 */
public class ConnUtils {
	private static Connection conn = null;
	private static Properties pros = null;

	/**
	 * 通过Druid连接池得到数据连接对象Connection
	 */
	public static Connection getConnByDruid() {
		try {
			pros = new Properties();
			String fileName = "druid.properties";
			InputStream is = ConnUtils.class.getClassLoader().getResourceAsStream(fileName);
			pros.load(is);

			DataSource dataSource = DruidDataSourceFactory.createDataSource(pros);
			conn = dataSource.getConnection();
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获取数据源（连接池）通过Druid
	 */
	public static DataSource getDataSourceByDruid() {
		DataSource dataSource = null;
		try {
			pros = new Properties();
			String fileName = "druid.properties";
			InputStream is = ConnUtils.class.getClassLoader().getResourceAsStream(fileName);
			pros.load(is);

			dataSource = DruidDataSourceFactory.createDataSource(pros);
			return dataSource;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	/**
	 * 获取连接对象
	 */
	public static Connection getConn() {
		pros = new Properties();
		if (conn == null) {
			// 为null就创建
			try {
//				FileInputStream is = new FileInputStream("src/database.properties");
				InputStream is = ConnUtils.class.getClassLoader().getResourceAsStream("database.properties");
				pros.load(is);

				Class.forName(pros.getProperty("driver"));
				conn = DriverManager.getConnection(pros.getProperty("url"), pros.getProperty("uname"),
						pros.getProperty("password"));
			} catch (ClassNotFoundException | SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	/**
	 * 释放资源
	 * 
	 * @param conn 连接对象
	 * @param pstm 执行SQL对象
	 * @param rs   返回结果集
	 */
	public static void closeAll(Connection conn, PreparedStatement pstm, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 偷懒的方法
	/**
	 * 做 增删改 操作
	 * 
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static int insertOrUpdateOrDelete(String sql, Object... param) throws Exception {
		Connection conn = getConn();
		PreparedStatement pstm = conn.prepareStatement(sql);
		// 参数
		for (int i = 0; i < param.length; i++) {
			pstm.setObject(i + 1, param[i]);
		}
		int result = pstm.executeUpdate();
		closeAll(conn, pstm, null);
		return result;
	}
}
