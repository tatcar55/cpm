/*     */ package org.sqlite.core;
/*     */ 
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.util.BitSet;
/*     */ import java.util.Calendar;
/*     */ import org.sqlite.SQLiteConfig;
/*     */ import org.sqlite.SQLiteConnection;
/*     */ import org.sqlite.SQLiteConnectionConfig;
/*     */ import org.sqlite.date.FastDateFormat;
/*     */ import org.sqlite.jdbc4.JDBC4Statement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CorePreparedStatement
/*     */   extends JDBC4Statement
/*     */ {
/*     */   protected int columnCount;
/*     */   protected int paramCount;
/*     */   protected int batchQueryCount;
/*     */   protected BitSet paramValid;
/*     */   
/*     */   protected CorePreparedStatement(SQLiteConnection conn, String sql) throws SQLException {
/*  43 */     super(conn);
/*     */     
/*  45 */     this.sql = sql;
/*  46 */     DB db = conn.getDatabase();
/*  47 */     db.prepare((CoreStatement)this);
/*  48 */     this.rs.colsMeta = db.column_names(this.pointer);
/*  49 */     this.columnCount = db.column_count(this.pointer);
/*  50 */     this.paramCount = db.bind_parameter_count(this.pointer);
/*  51 */     this.paramValid = new BitSet(this.paramCount);
/*  52 */     this.batchQueryCount = 0;
/*  53 */     this.batch = null;
/*  54 */     this.batchPos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws SQLException {
/*  62 */     close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkParameters() throws SQLException {
/*  70 */     if (this.paramValid.cardinality() != this.paramCount) {
/*  71 */       throw new SQLException("Values not bound to statement");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] executeBatch() throws SQLException {
/*  79 */     if (this.batchQueryCount == 0) {
/*  80 */       return new int[0];
/*     */     }
/*     */     
/*  83 */     checkParameters();
/*     */     
/*     */     try {
/*  86 */       return this.conn.getDatabase().executeBatch(this.pointer, this.batchQueryCount, this.batch, this.conn.getAutoCommit());
/*     */     } finally {
/*     */       
/*  89 */       clearBatch();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearBatch() throws SQLException {
/*  98 */     super.clearBatch();
/*  99 */     this.paramValid.clear();
/* 100 */     this.batchQueryCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUpdateCount() throws SQLException {
/* 108 */     if (this.pointer == 0L || this.resultsWaiting || this.rs.isOpen()) {
/* 109 */       return -1;
/*     */     }
/*     */     
/* 112 */     return this.conn.getDatabase().changes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void batch(int pos, Object value) throws SQLException {
/* 125 */     checkOpen();
/* 126 */     if (this.batch == null) {
/* 127 */       this.batch = new Object[this.paramCount];
/* 128 */       this.paramValid.clear();
/*     */     } 
/* 130 */     this.batch[this.batchPos + pos - 1] = value;
/* 131 */     this.paramValid.set(pos - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDateByMilliseconds(int pos, Long value, Calendar calendar) throws SQLException {
/* 139 */     SQLiteConnectionConfig config = this.conn.getConnectionConfig();
/* 140 */     switch (config.getDateClass()) {
/*     */       case TEXT:
/* 142 */         batch(pos, FastDateFormat.getInstance(config.getDateStringFormat(), calendar.getTimeZone()).format(new Date(value.longValue())));
/*     */         return;
/*     */ 
/*     */       
/*     */       case REAL:
/* 147 */         batch(pos, new Double(value.longValue() / 8.64E7D + 2440587.5D));
/*     */         return;
/*     */     } 
/*     */     
/* 151 */     batch(pos, new Long(value.longValue() / config.getDateMultiplier()));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlite\core\CorePreparedStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */