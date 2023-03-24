/*     */ package org.sqlite.jdbc3;
/*     */ 
/*     */ import java.sql.BatchUpdateException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import org.sqlite.ExtendedCommand;
/*     */ import org.sqlite.SQLiteConnection;
/*     */ import org.sqlite.core.CoreStatement;
/*     */ import org.sqlite.core.DB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JDBC3Statement
/*     */   extends CoreStatement
/*     */ {
/*     */   protected JDBC3Statement(SQLiteConnection conn) {
/*  20 */     super(conn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/*  27 */     internalClose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws SQLException {
/*  34 */     close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String sql) throws SQLException {
/*  41 */     internalClose();
/*     */     
/*  43 */     ExtendedCommand.SQLExtension ext = ExtendedCommand.parse(sql);
/*  44 */     if (ext != null) {
/*  45 */       ext.execute(this.conn.getDatabase());
/*     */       
/*  47 */       return false;
/*     */     } 
/*     */     
/*  50 */     this.sql = sql;
/*     */     
/*  52 */     this.conn.getDatabase().prepare(this);
/*  53 */     return exec();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery(String sql, boolean closeStmt) throws SQLException {
/*  61 */     this.rs.closeStmt = closeStmt;
/*     */     
/*  63 */     return executeQuery(sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery(String sql) throws SQLException {
/*  70 */     internalClose();
/*  71 */     this.sql = sql;
/*     */     
/*  73 */     this.conn.getDatabase().prepare(this);
/*     */     
/*  75 */     if (!exec()) {
/*  76 */       internalClose();
/*  77 */       throw new SQLException("query does not return ResultSet", "SQLITE_DONE", 101);
/*     */     } 
/*     */     
/*  80 */     return getResultSet();
/*     */   }
/*     */   
/*     */   static class BackupObserver
/*     */     implements DB.ProgressObserver {
/*     */     public void progress(int remaining, int pageCount) {
/*  86 */       System.out.println(String.format("remaining:%d, page count:%d", new Object[] { Integer.valueOf(remaining), Integer.valueOf(pageCount) }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String sql) throws SQLException {
/*  94 */     internalClose();
/*  95 */     this.sql = sql;
/*  96 */     DB db = this.conn.getDatabase();
/*     */     
/*  98 */     int changes = 0;
/*  99 */     ExtendedCommand.SQLExtension ext = ExtendedCommand.parse(sql);
/* 100 */     if (ext != null) {
/*     */       
/* 102 */       ext.execute(db);
/*     */     } else {
/*     */       
/*     */       try {
/* 106 */         changes = db.total_changes();
/*     */ 
/*     */         
/* 109 */         int statusCode = db._exec(sql);
/* 110 */         if (statusCode != 0) {
/* 111 */           throw DB.newSQLException(statusCode, "");
/*     */         }
/* 113 */         changes = db.total_changes() - changes;
/*     */       } finally {
/*     */         
/* 116 */         internalClose();
/*     */       } 
/*     */     } 
/* 119 */     return changes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getResultSet() throws SQLException {
/* 126 */     checkOpen();
/*     */     
/* 128 */     if (this.rs.isOpen()) {
/* 129 */       throw new SQLException("ResultSet already requested");
/*     */     }
/* 131 */     DB db = this.conn.getDatabase();
/*     */     
/* 133 */     if (db.column_count(this.pointer) == 0) {
/* 134 */       return null;
/*     */     }
/*     */     
/* 137 */     if (this.rs.colsMeta == null) {
/* 138 */       this.rs.colsMeta = db.column_names(this.pointer);
/*     */     }
/*     */     
/* 141 */     this.rs.cols = this.rs.colsMeta;
/* 142 */     this.rs.open = this.resultsWaiting;
/* 143 */     this.resultsWaiting = false;
/*     */     
/* 145 */     return (ResultSet)this.rs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpdateCount() throws SQLException {
/* 155 */     DB db = this.conn.getDatabase();
/* 156 */     if (this.pointer != 0L && !this.rs.isOpen() && !this.resultsWaiting && db.column_count(this.pointer) == 0)
/* 157 */       return db.changes(); 
/* 158 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch(String sql) throws SQLException {
/* 165 */     internalClose();
/* 166 */     if (this.batch == null || this.batchPos + 1 >= this.batch.length) {
/* 167 */       Object[] nb = new Object[Math.max(10, this.batchPos * 2)];
/* 168 */       if (this.batch != null)
/* 169 */         System.arraycopy(this.batch, 0, nb, 0, this.batch.length); 
/* 170 */       this.batch = nb;
/*     */     } 
/* 172 */     this.batch[this.batchPos++] = sql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearBatch() throws SQLException {
/* 179 */     this.batchPos = 0;
/* 180 */     if (this.batch != null) {
/* 181 */       for (int i = 0; i < this.batch.length; i++) {
/* 182 */         this.batch[i] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] executeBatch() throws SQLException {
/* 190 */     internalClose();
/* 191 */     if (this.batch == null || this.batchPos == 0) {
/* 192 */       return new int[0];
/*     */     }
/* 194 */     int[] changes = new int[this.batchPos];
/* 195 */     DB db = this.conn.getDatabase();
/* 196 */     synchronized (db) {
/*     */       try {
/* 198 */         for (int i = 0; i < changes.length; i++) {
/*     */           
/* 200 */           try { this.sql = (String)this.batch[i];
/* 201 */             db.prepare(this);
/* 202 */             changes[i] = db.executeUpdate(this, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 208 */             db.finalize(this); } catch (SQLException e) { throw new BatchUpdateException("batch entry " + i + ": " + e.getMessage(), changes); } finally { db.finalize(this); }
/*     */         
/*     */         } 
/*     */       } finally {
/*     */         
/* 213 */         clearBatch();
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     return changes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorName(String name) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLWarning getWarnings() throws SQLException {
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearWarnings() throws SQLException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() throws SQLException {
/* 241 */     return (Connection)this.conn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() throws SQLException {
/* 248 */     this.conn.getDatabase().interrupt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQueryTimeout() throws SQLException {
/* 255 */     return this.conn.getBusyTimeout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQueryTimeout(int seconds) throws SQLException {
/* 262 */     if (seconds < 0)
/* 263 */       throw new SQLException("query timeout must be >= 0"); 
/* 264 */     this.conn.setBusyTimeout(1000 * seconds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxRows() throws SQLException {
/* 273 */     return this.rs.maxRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxRows(int max) throws SQLException {
/* 281 */     if (max < 0)
/* 282 */       throw new SQLException("max row count must be >= 0"); 
/* 283 */     this.rs.maxRows = max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxFieldSize() throws SQLException {
/* 290 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxFieldSize(int max) throws SQLException {
/* 297 */     if (max < 0) {
/* 298 */       throw new SQLException("max field size " + max + " cannot be negative");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFetchSize() throws SQLException {
/* 305 */     return ((ResultSet)this.rs).getFetchSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchSize(int r) throws SQLException {
/* 312 */     ((ResultSet)this.rs).setFetchSize(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFetchDirection() throws SQLException {
/* 319 */     return ((ResultSet)this.rs).getFetchDirection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchDirection(int d) throws SQLException {
/* 326 */     ((ResultSet)this.rs).setFetchDirection(d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getGeneratedKeys() throws SQLException {
/* 336 */     return this.conn.getSQLiteDatabaseMetaData().getGeneratedKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMoreResults() throws SQLException {
/* 344 */     return getMoreResults(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getMoreResults(int c) throws SQLException {
/* 351 */     checkOpen();
/* 352 */     internalClose();
/* 353 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetConcurrency() throws SQLException {
/* 360 */     return 1007;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetHoldability() throws SQLException {
/* 367 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResultSetType() throws SQLException {
/* 374 */     return 1003;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEscapeProcessing(boolean enable) throws SQLException {
/* 381 */     if (enable) {
/* 382 */       throw unused();
/*     */     }
/*     */   }
/*     */   
/*     */   protected SQLException unused() {
/* 387 */     return new SQLException("not implemented by SQLite JDBC driver");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String sql, int[] colinds) throws SQLException {
/* 394 */     throw unused();
/*     */   } public boolean execute(String sql, String[] colnames) throws SQLException {
/* 396 */     throw unused();
/*     */   } public int executeUpdate(String sql, int autoKeys) throws SQLException {
/* 398 */     throw unused();
/*     */   } public int executeUpdate(String sql, int[] colinds) throws SQLException {
/* 400 */     throw unused();
/*     */   } public int executeUpdate(String sql, String[] cols) throws SQLException {
/* 402 */     throw unused();
/*     */   } public boolean execute(String sql, int autokeys) throws SQLException {
/* 404 */     throw unused();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlite\jdbc3\JDBC3Statement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */