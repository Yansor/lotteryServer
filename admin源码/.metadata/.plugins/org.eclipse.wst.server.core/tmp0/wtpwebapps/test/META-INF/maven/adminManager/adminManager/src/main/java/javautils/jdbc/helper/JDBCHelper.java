package javautils.jdbc.helper;

import javax.naming.Context;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import java.sql.ResultSetMetaData;
import net.sf.json.JSONObject;
import java.sql.ResultSet;
import net.sf.json.JSONArray;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class JDBCHelper
{
    public static final String MYSQL = "com.mysql.jdbc.Driver";
    
    public static Connection openConnection(final String url, final String username, final String password, final String type) {
        Connection conn = null;
        try {
            Class.forName(type);
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (Exception ex) {}
        return conn;
    }
    
    public static boolean executeUpdate(final String sql, final Connection conn) {
        boolean flag = false;
        if (conn != null) {
            PreparedStatement pst = null;
            try {
                pst = conn.prepareStatement(sql);
                final int count = pst.executeUpdate();
                flag = (count > 0);
            }
            catch (Exception ex) {}
            finally {
                try {
                    if (pst != null) {
                        pst.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                }
                catch (SQLException ex2) {}
            }
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex3) {}
        }
        return flag;
    }
    
    public static JSONArray executeQuery(final String sql, final Connection conn) {
        JSONArray list = null;
        if (conn != null) {
            PreparedStatement pst = null;
            try {
                pst = conn.prepareStatement(sql);
                final ResultSet rs = pst.executeQuery();
                list = toJsonArray(rs);
            }
            catch (Exception ex) {}
            finally {
                try {
                    if (pst != null) {
                        pst.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                }
                catch (SQLException ex2) {}
            }
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex3) {}
        }
        return list;
    }
    
    public static JSONArray toJsonArray(final ResultSet rs) throws Exception {
        final JSONArray list = new JSONArray();
        if (rs != null) {
            final ResultSetMetaData metaData = rs.getMetaData();
            final int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                final JSONObject obj = new JSONObject();
                for (int i = 0; i < columnCount; ++i) {
                    final String columnName = metaData.getColumnLabel(i + 1);
                    final String value = rs.getString(columnName);
                    obj.put((Object)columnName, (Object)value);
                }
                list.add((Object)obj);
            }
        }
        return list;
    }
    
    public static Connection openConnection(final String jndi) {
        Connection conn = null;
        try {
            final Context context = new InitialContext();
            final DataSource source = (DataSource)context.lookup(jndi);
            conn = source.getConnection();
        }
        catch (Exception ex) {}
        return conn;
    }
    
    public static void main(final String[] args) {
        final String url = "jdbc:mysql://192.168.1.111:3306/test?useUnicode=true&characterEncoding=utf-8";
        final String username = "root";
        final String password = "root";
        final Connection conn = openConnection(url, username, password, "com.mysql.jdbc.Driver");
        final String sql = "select * from user";
        executeQuery(sql, conn);
    }
}
