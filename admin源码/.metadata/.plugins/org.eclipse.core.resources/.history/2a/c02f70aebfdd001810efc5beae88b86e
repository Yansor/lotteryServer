package javautils.jdbc.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class SQLUtil
{
    private static final Logger logger;
    
    static {
        logger = LoggerFactory.getLogger((Class)SQLUtil.class);
    }
    
    public boolean aduSql(final Connection conn, final String sql) {
        boolean flag = false;
        try {
            if (conn != null) {
                final PreparedStatement ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                ps.close();
                flag = true;
            }
        }
        catch (Exception e) {
            SQLUtil.logger.error("aduSql异常", (Throwable)e);
            flag = false;
        }
        return flag;
    }
    
    public static boolean aduSql(final Connection conn, final String sql, final Object[] params) {
        boolean flag = false;
        try {
            if (conn != null) {
                final PreparedStatement ps = conn.prepareStatement(sql);
                if (params != null) {
                    for (int i = 0; i < params.length; ++i) {
                        ps.setObject(i + 1, params[i]);
                    }
                }
                ps.executeUpdate();
                flag = true;
                ps.close();
            }
        }
        catch (Exception e) {
            SQLUtil.logger.error("aduSql异常", (Throwable)e);
            flag = false;
        }
        return flag;
    }
    
    public static List<Object[]> exList(final Connection conn, final String sql, final Object[] params) {
        final ArrayList<Object[]> list = new ArrayList<Object[]>();
        try {
            if (conn != null) {
                final PreparedStatement ps = conn.prepareStatement(sql);
                if (params != null) {
                    for (int i = 0; i < params.length; ++i) {
                        ps.setObject(i + 1, params[i]);
                    }
                }
                final ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    final int columns = rs.getMetaData().getColumnCount();
                    final Object[] objArr = new Object[columns];
                    for (int j = 0; j < columns; ++j) {
                        objArr[j] = rs.getObject(j + 1);
                    }
                    list.add(objArr);
                }
                rs.close();
                ps.close();
            }
        }
        catch (Exception e) {
            SQLUtil.logger.error("exList异常", (Throwable)e);
        }
        return list;
    }
}
