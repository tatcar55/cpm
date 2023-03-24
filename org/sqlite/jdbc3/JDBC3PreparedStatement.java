/*     */ package org.sqlite.jdbc3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.math.BigDecimal;
/*     */ import java.net.URL;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Date;
/*     */ import java.sql.ParameterMetaData;
/*     */ import java.sql.Ref;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import org.sqlite.SQLiteConnection;
/*     */ import org.sqlite.core.CorePreparedStatement;
/*     */ import org.sqlite.core.CoreStatement;
/*     */ 
/*     */ public abstract class JDBC3PreparedStatement
/*     */   extends CorePreparedStatement {
/*     */   protected JDBC3PreparedStatement(SQLiteConnection conn, String sql) throws SQLException {
/*  30 */     super(conn, sql);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearParameters() throws SQLException {
/*  37 */     checkOpen();
/*  38 */     this.conn.getDatabase().clear_bindings(this.pointer);
/*  39 */     this.paramValid.clear();
/*  40 */     if (this.batch != null) {
/*  41 */       for (int i = this.batchPos; i < this.batchPos + this.paramCount; i++) {
/*  42 */         this.batch[i] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute() throws SQLException {
/*  49 */     checkOpen();
/*  50 */     this.rs.close();
/*  51 */     this.conn.getDatabase().reset(this.pointer);
/*  52 */     checkParameters();
/*     */     
/*  54 */     boolean success = false;
/*     */     try {
/*  56 */       this.resultsWaiting = this.conn.getDatabase().execute((CoreStatement)this, this.batch);
/*  57 */       success = true;
/*  58 */       return (this.columnCount != 0);
/*     */     } finally {
/*  60 */       if (!success && this.pointer != 0L) this.conn.getDatabase().reset(this.pointer);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery() throws SQLException {
/*  68 */     checkOpen();
/*     */     
/*  70 */     if (this.columnCount == 0) {
/*  71 */       throw new SQLException("Query does not return results");
/*     */     }
/*     */     
/*  74 */     this.rs.close();
/*  75 */     this.conn.getDatabase().reset(this.pointer);
/*  76 */     checkParameters();
/*     */     
/*  78 */     boolean success = false;
/*     */     try {
/*  80 */       this.resultsWaiting = this.conn.getDatabase().execute((CoreStatement)this, this.batch);
/*  81 */       success = true;
/*     */     } finally {
/*  83 */       if (!success && this.pointer != 0L) this.conn.getDatabase().reset(this.pointer); 
/*     */     } 
/*  85 */     return getResultSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate() throws SQLException {
/*  92 */     checkOpen();
/*     */     
/*  94 */     if (this.columnCount != 0) {
/*  95 */       throw new SQLException("Query returns results");
/*     */     }
/*     */     
/*  98 */     this.rs.close();
/*  99 */     this.conn.getDatabase().reset(this.pointer);
/* 100 */     checkParameters();
/*     */     
/* 102 */     return this.conn.getDatabase().executeUpdate((CoreStatement)this, this.batch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch() throws SQLException {
/* 109 */     checkOpen();
/* 110 */     checkParameters();
/* 111 */     this.batchPos += this.paramCount;
/* 112 */     this.batchQueryCount++;
/* 113 */     if (this.batch == null) {
/* 114 */       this.batch = new Object[this.paramCount];
/* 115 */       this.paramValid.clear();
/*     */     } 
/* 117 */     if (this.batchPos + this.paramCount > this.batch.length) {
/* 118 */       Object[] nb = new Object[this.batch.length * 2];
/* 119 */       System.arraycopy(this.batch, 0, nb, 0, this.batch.length);
/* 120 */       this.batch = nb;
/*     */     } 
/* 122 */     System.arraycopy(this.batch, this.batchPos - this.paramCount, this.batch, this.batchPos, this.paramCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterMetaData getParameterMetaData() {
/* 131 */     return (ParameterMetaData)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParameterCount() throws SQLException {
/* 138 */     checkOpen();
/* 139 */     return this.paramCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameterClassName(int param) throws SQLException {
/* 146 */     checkOpen();
/* 147 */     return "java.lang.String";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getParameterTypeName(int pos) {
/* 154 */     return "VARCHAR";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParameterType(int pos) {
/* 161 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParameterMode(int pos) {
/* 168 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrecision(int pos) {
/* 175 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScale(int pos) {
/* 182 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int isNullable(int pos) {
/* 189 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSigned(int pos) {
/* 196 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Statement getStatement() {
/* 203 */     return (Statement)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBigDecimal(int pos, BigDecimal value) throws SQLException {
/* 210 */     batch(pos, (value == null) ? null : value.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] readBytes(InputStream istream, int length) throws SQLException {
/* 221 */     if (length < 0) {
/* 222 */       SQLException exception = new SQLException("Error reading stream. Length should be non-negative");
/*     */ 
/*     */       
/* 225 */       throw exception;
/*     */     } 
/*     */     
/* 228 */     byte[] bytes = new byte[length];
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 233 */       int totalBytesRead = 0;
/*     */       
/* 235 */       while (totalBytesRead < length) {
/* 236 */         int bytesRead = istream.read(bytes, totalBytesRead, length - totalBytesRead);
/* 237 */         if (bytesRead == -1) {
/* 238 */           throw new IOException("End of stream has been reached");
/*     */         }
/* 240 */         totalBytesRead += bytesRead;
/*     */       } 
/*     */       
/* 243 */       return bytes;
/*     */     }
/* 245 */     catch (IOException cause) {
/*     */       
/* 247 */       SQLException exception = new SQLException("Error reading stream");
/*     */       
/* 249 */       exception.initCause(cause);
/* 250 */       throw exception;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBinaryStream(int pos, InputStream istream, int length) throws SQLException {
/* 258 */     if (istream == null && length == 0) {
/* 259 */       setBytes(pos, (byte[])null);
/*     */     }
/*     */     
/* 262 */     setBytes(pos, readBytes(istream, length));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAsciiStream(int pos, InputStream istream, int length) throws SQLException {
/* 269 */     setUnicodeStream(pos, istream, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnicodeStream(int pos, InputStream istream, int length) throws SQLException {
/* 276 */     if (istream == null && length == 0) {
/* 277 */       setString(pos, (String)null);
/*     */     }
/*     */     
/*     */     try {
/* 281 */       setString(pos, new String(readBytes(istream, length), "UTF-8"));
/* 282 */     } catch (UnsupportedEncodingException e) {
/* 283 */       SQLException exception = new SQLException("UTF-8 is not supported");
/*     */       
/* 285 */       exception.initCause(e);
/* 286 */       throw exception;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoolean(int pos, boolean value) throws SQLException {
/* 294 */     setInt(pos, value ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByte(int pos, byte value) throws SQLException {
/* 301 */     setInt(pos, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBytes(int pos, byte[] value) throws SQLException {
/* 308 */     batch(pos, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(int pos, double value) throws SQLException {
/* 315 */     batch(pos, new Double(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(int pos, float value) throws SQLException {
/* 322 */     batch(pos, new Float(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInt(int pos, int value) throws SQLException {
/* 329 */     batch(pos, new Integer(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(int pos, long value) throws SQLException {
/* 336 */     batch(pos, new Long(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNull(int pos, int u1) throws SQLException {
/* 343 */     setNull(pos, u1, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNull(int pos, int u1, String u2) throws SQLException {
/* 350 */     batch(pos, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(int pos, Object value) throws SQLException {
/* 357 */     if (value == null) {
/* 358 */       batch(pos, null);
/*     */     }
/* 360 */     else if (value instanceof Date) {
/* 361 */       setDateByMilliseconds(pos, Long.valueOf(((Date)value).getTime()), Calendar.getInstance());
/*     */     }
/* 363 */     else if (value instanceof Long) {
/* 364 */       batch(pos, value);
/*     */     }
/* 366 */     else if (value instanceof Integer) {
/* 367 */       batch(pos, value);
/*     */     }
/* 369 */     else if (value instanceof Short) {
/* 370 */       batch(pos, new Integer(((Short)value).intValue()));
/*     */     }
/* 372 */     else if (value instanceof Float) {
/* 373 */       batch(pos, value);
/*     */     }
/* 375 */     else if (value instanceof Double) {
/* 376 */       batch(pos, value);
/*     */     }
/* 378 */     else if (value instanceof Boolean) {
/* 379 */       setBoolean(pos, ((Boolean)value).booleanValue());
/*     */     }
/* 381 */     else if (value instanceof byte[]) {
/* 382 */       batch(pos, value);
/*     */     }
/* 384 */     else if (value instanceof BigDecimal) {
/* 385 */       setBigDecimal(pos, (BigDecimal)value);
/*     */     } else {
/*     */       
/* 388 */       batch(pos, value.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(int p, Object v, int t) throws SQLException {
/* 396 */     setObject(p, v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(int p, Object v, int t, int s) throws SQLException {
/* 403 */     setObject(p, v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShort(int pos, short value) throws SQLException {
/* 410 */     setInt(pos, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(int pos, String value) throws SQLException {
/* 417 */     batch(pos, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCharacterStream(int pos, Reader reader, int length) throws SQLException {
/*     */     try {
/* 426 */       StringBuffer sb = new StringBuffer();
/* 427 */       char[] cbuf = new char[8192];
/*     */       
/*     */       int cnt;
/* 430 */       while ((cnt = reader.read(cbuf)) > 0) {
/* 431 */         sb.append(cbuf, 0, cnt);
/*     */       }
/*     */ 
/*     */       
/* 435 */       setString(pos, sb.toString());
/*     */     }
/* 437 */     catch (IOException e) {
/* 438 */       throw new SQLException("Cannot read from character stream, exception message: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDate(int pos, Date x) throws SQLException {
/* 446 */     setDate(pos, x, Calendar.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDate(int pos, Date x, Calendar cal) throws SQLException {
/* 453 */     if (x == null) {
/* 454 */       setObject(pos, (Object)null);
/*     */     } else {
/* 456 */       setDateByMilliseconds(pos, Long.valueOf(x.getTime()), cal);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(int pos, Time x) throws SQLException {
/* 465 */     setTime(pos, x, Calendar.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(int pos, Time x, Calendar cal) throws SQLException {
/* 472 */     if (x == null) {
/* 473 */       setObject(pos, (Object)null);
/*     */     } else {
/* 475 */       setDateByMilliseconds(pos, Long.valueOf(x.getTime()), cal);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimestamp(int pos, Timestamp x) throws SQLException {
/* 483 */     setTimestamp(pos, x, Calendar.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimestamp(int pos, Timestamp x, Calendar cal) throws SQLException {
/* 490 */     if (x == null) {
/* 491 */       setObject(pos, (Object)null);
/*     */     } else {
/* 493 */       setDateByMilliseconds(pos, Long.valueOf(x.getTime()), cal);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetMetaData getMetaData() throws SQLException {
/* 501 */     checkOpen();
/* 502 */     return (ResultSetMetaData)this.rs;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SQLException unused() {
/* 507 */     return new SQLException("not implemented by SQLite JDBC driver");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setArray(int i, Array x) throws SQLException {
/* 514 */     throw unused();
/*     */   }
/*     */   
/*     */   public void setBlob(int i, Blob x) throws SQLException {
/* 518 */     throw unused();
/*     */   } public void setClob(int i, Clob x) throws SQLException {
/* 520 */     throw unused();
/*     */   } public void setRef(int i, Ref x) throws SQLException {
/* 522 */     throw unused();
/*     */   } public void setURL(int pos, URL x) throws SQLException {
/* 524 */     throw unused();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String sql) throws SQLException {
/* 531 */     throw unused();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String sql) throws SQLException {
/* 539 */     throw unused();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery(String sql) throws SQLException {
/* 547 */     throw unused();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBatch(String sql) throws SQLException {
/* 554 */     throw unused();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlite\jdbc3\JDBC3PreparedStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */