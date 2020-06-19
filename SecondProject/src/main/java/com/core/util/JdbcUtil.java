package com.core.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/* 环境
 * 1.JDK7
 * 2.Neo4j3.x
 * 3.切换数据库需要删除C:\Users\xxx\.neo4j\known_hosts
 */

/* 添加jar包
 * 1.neo4j-jdbc-driver-3.0.1.jar
 */

/* 配置文件：jdbc.properties
 * jdbc.username = neo4j
 * jdbc.password = root
 * jdbc.driver   = org.neo4j.jdbc.Driver
 * jdbc.url      = jdbc:neo4j:bolt://localhost
 */

public class JdbcUtil {
	private static final Logger log = Logger.getLogger(JdbcUtil.class);// 日志文件
	// 表示定义数据库的用户名
	private static String USERNAME;
	// 定义数据库的密码
	private static String PASSWORD;
	// 定义数据库的驱动信息
	private static String DRIVER;
	// 定义访问数据库的地址
	private static String URL;
	// 定义数据库的链接
	private Connection conn;
	// 定义sql语句的执行对象
	private PreparedStatement st;
	// 定义查询返回的结果集合
	private ResultSet rs;

	static {
		// 加载数据库配置信息，并给相关的属性赋值
		loadConfig();
	}

	/**
	 * 加载数据库配置信息，并给相关的属性赋值
	 */
	public static void loadConfig() {
		try {
			InputStream inStream = JdbcUtil.class.getResourceAsStream("/jdbc.properties");
			Properties prop = new Properties();
			prop.load(inStream);
			USERNAME = prop.getProperty("jdbc.username");
			PASSWORD = prop.getProperty("jdbc.password");
			DRIVER = prop.getProperty("jdbc.driver");
			URL = prop.getProperty("jdbc.url");
		} catch (Exception e) {
			throw new RuntimeException("读取数据库配置文件异常！", e);
		}
	}

	public JdbcUtil() {
	}

	/**
	 * 获取数据库连接
	 */
	public Connection getConnection() {
		try {
			// 加载驱动特殊处理，否则找不到驱动包
			Class.forName(DRIVER).newInstance();
			// 注册驱动
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 获取连接
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		log.info("获取连接成功！");
		return conn;
	}

	/**
	 * 执行更新操作
	 */
	public int update(String cql, Object... params) throws SQLException {
		System.out.println(cql + ":::" + Arrays.toString(params));
		log.info(cql + ":::" + Arrays.toString(params));
		st = conn.prepareStatement(cql);
		int index = 1;
		// 填充sql语句中的占位符
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				st.setObject(index++, params[i]);
			}
		}
		return st.executeUpdate();
	}

	/**
	 * 执行查询操作
	 */
	public List<Map<String, Object>> findList(String cql, Object... params) throws SQLException {
		System.out.println(cql + ":::" + Arrays.toString(params));
		log.info(cql + ":::" + Arrays.toString(params));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		st = conn.prepareStatement(cql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				st.setObject(index++, params[i]);
			}
		}
		rs = st.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = rs.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 释放资源
	 */
	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
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

	public static void main(String[] args) throws IOException, SQLException {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		// 查询点
		String sql1 = "MATCH (n) RETURN n LIMIT {1} ";
		// 查询线
		String sql2 = "MATCH ()-[r]->() RETURN r LIMIT {1} ";
		// 查询所有
		String sql3 = "MATCH (a)-[r]->(b) RETURN a,b,r LIMIT {1} ";
		try {
			List<Map<String, Object>> result = jdbcUtil.findList(sql1, 100);
			for (Map<String, Object> m : result) {
				System.out.println(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != jdbcUtil) {
				jdbcUtil.close();
			}
		}
		System.out.println("OVER!");
	}
}
