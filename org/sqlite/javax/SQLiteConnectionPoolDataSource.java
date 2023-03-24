/*    */ package org.sqlite.javax;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.ConnectionPoolDataSource;
/*    */ import javax.sql.PooledConnection;
/*    */ import org.sqlite.SQLiteConfig;
/*    */ import org.sqlite.SQLiteDataSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SQLiteConnectionPoolDataSource
/*    */   extends SQLiteDataSource
/*    */   implements ConnectionPoolDataSource
/*    */ {
/*    */   public SQLiteConnectionPoolDataSource() {}
/*    */   
/*    */   public SQLiteConnectionPoolDataSource(SQLiteConfig config) {
/* 35 */     super(config);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PooledConnection getPooledConnection() throws SQLException {
/* 42 */     return getPooledConnection(null, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PooledConnection getPooledConnection(String user, String password) throws SQLException {
/* 49 */     return (PooledConnection)new SQLitePooledConnection(getConnection(user, password));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlite\javax\SQLiteConnectionPoolDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */