package javautils.jdbc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchSQLUtil
{
  private static final Logger logger = LoggerFactory.getLogger(BatchSQLUtil.class);
  private Connection conn;
  private PreparedStatement ps;
  
  public BatchSQLUtil(Connection conn, String sql)
  {
    try
    {
      this.conn = conn;
      this.conn.setAutoCommit(false);
      this.ps = this.conn.prepareStatement(sql);
    }
    catch (Exception e)
    {
      logger.error("BatchSQLUtil异常", e);
    }
  }
  
  public void addCount(Object[] param)
  {
    try
    {
      for (int i = 0; i < param.length; i++) {
        this.ps.setObject(i + 1, param[i]);
      }
      this.ps.addBatch();
    }
    catch (SQLException e)
    {
      logger.error("addCount异常", e);
    }
  }
  
  public void commit()
  {
    try
    {
      this.ps.executeBatch();
      this.conn.commit();
      this.ps.clearBatch();
    }
    catch (SQLException e)
    {
      logger.error("commit异常", e);
    }
  }
  
  public void close()
  {
    try
    {
      this.ps.close();
      this.conn.close();
    }
    catch (SQLException e)
    {
      logger.error("close异常", e);
    }
  }
}
