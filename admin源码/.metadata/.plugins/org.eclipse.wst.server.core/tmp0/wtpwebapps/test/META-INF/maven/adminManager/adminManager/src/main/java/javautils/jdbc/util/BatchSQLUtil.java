package javautils.jdbc.util;

import java.sql.SQLException;
import org.slf4j.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.Connection;
import org.slf4j.Logger;

public class BatchSQLUtil
{
    private static final Logger logger;
    private Connection conn;
    private PreparedStatement ps;
    
    static {
        logger = LoggerFactory.getLogger((Class)BatchSQLUtil.class);
    }
    
    public BatchSQLUtil(final Connection conn, final String sql) {
        try {
            (this.conn = conn).setAutoCommit(false);
            this.ps = this.conn.prepareStatement(sql);
        }
        catch (Exception e) {
            BatchSQLUtil.logger.error("BatchSQLUtil异常", (Throwable)e);
        }
    }
    
    public void addCount(final Object[] param) {
        try {
            for (int i = 0; i < param.length; ++i) {
                this.ps.setObject(i + 1, param[i]);
            }
            this.ps.addBatch();
        }
        catch (SQLException e) {
            BatchSQLUtil.logger.error("addCount异常", (Throwable)e);
        }
    }
    
    public void commit() {
        try {
            this.ps.executeBatch();
            this.conn.commit();
            this.ps.clearBatch();
        }
        catch (SQLException e) {
            BatchSQLUtil.logger.error("commit异常", (Throwable)e);
        }
    }
    
    public void close() {
        try {
            this.ps.close();
            this.conn.close();
        }
        catch (SQLException e) {
            BatchSQLUtil.logger.error("close异常", (Throwable)e);
        }
    }
}
