/*      */ package org.sqlite.jdbc3;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.math.BigDecimal;
/*      */ import java.sql.Date;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.Locale;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.sqlite.core.CoreResultSet;
/*      */ import org.sqlite.core.CoreStatement;
/*      */ import org.sqlite.core.DB;
/*      */ import org.sqlite.date.FastDateFormat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class JDBC3ResultSet
/*      */   extends CoreResultSet
/*      */ {
/*      */   protected JDBC3ResultSet(CoreStatement stmt) {
/*   31 */     super(stmt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int findColumn(String col) throws SQLException {
/*   39 */     checkOpen();
/*   40 */     Integer index = findColumnIndexInCache(col);
/*   41 */     if (index != null) {
/*   42 */       return index.intValue();
/*      */     }
/*   44 */     for (int i = 0; i < this.cols.length; i++) {
/*   45 */       if (col.equalsIgnoreCase(this.cols[i])) {
/*   46 */         return addColumnIndexInCache(col, i + 1);
/*      */       }
/*      */     } 
/*   49 */     throw new SQLException("no such column: '" + col + "'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean next() throws SQLException {
/*   56 */     if (!this.open)
/*      */     {
/*   58 */       return false;
/*      */     }
/*   60 */     this.lastCol = -1;
/*      */ 
/*      */     
/*   63 */     if (this.row == 0) {
/*   64 */       this.row++;
/*   65 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*   69 */     if (this.maxRows != 0 && this.row == this.maxRows) {
/*   70 */       return false;
/*      */     }
/*      */ 
/*      */     
/*   74 */     int statusCode = getDatabase().step(this.stmt.pointer);
/*   75 */     switch (statusCode) {
/*      */       case 101:
/*   77 */         close();
/*   78 */         return false;
/*      */       case 100:
/*   80 */         this.row++;
/*   81 */         return true;
/*      */     } 
/*      */     
/*   84 */     getDatabase().throwex(statusCode);
/*   85 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getType() throws SQLException {
/*   93 */     return 1003;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFetchSize() throws SQLException {
/*  100 */     return this.limitRows;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFetchSize(int rows) throws SQLException {
/*  107 */     if (0 > rows || (this.maxRows != 0 && rows > this.maxRows)) {
/*  108 */       throw new SQLException("fetch size " + rows + " out of bounds " + this.maxRows);
/*      */     }
/*  110 */     this.limitRows = rows;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFetchDirection() throws SQLException {
/*  117 */     checkOpen();
/*  118 */     return 1000;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFetchDirection(int d) throws SQLException {
/*  125 */     checkOpen();
/*  126 */     if (d != 1000) {
/*  127 */       throw new SQLException("only FETCH_FORWARD direction supported");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAfterLast() throws SQLException {
/*  135 */     return !this.open;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBeforeFirst() throws SQLException {
/*  142 */     return (this.open && this.row == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFirst() throws SQLException {
/*  149 */     return (this.row == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLast() throws SQLException {
/*  156 */     throw new SQLException("function not yet implemented for SQLite");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalize() throws SQLException {
/*  164 */     close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRow() throws SQLException {
/*  171 */     return this.row;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean wasNull() throws SQLException {
/*  178 */     return (getDatabase().column_type(this.stmt.pointer, markCol(this.lastCol)) == 5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(int col) throws SQLException {
/*  187 */     String stringValue = getString(col);
/*  188 */     if (stringValue == null) {
/*  189 */       return null;
/*      */     }
/*      */     
/*      */     try {
/*  193 */       return new BigDecimal(stringValue);
/*      */     }
/*  195 */     catch (NumberFormatException e) {
/*  196 */       throw new SQLException("Bad value for type BigDecimal : " + stringValue);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(String col) throws SQLException {
/*  205 */     return getBigDecimal(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int col) throws SQLException {
/*  212 */     return !(getInt(col) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(String col) throws SQLException {
/*  219 */     return getBoolean(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getBinaryStream(int col) throws SQLException {
/*  226 */     byte[] bytes = getBytes(col);
/*  227 */     if (bytes != null) {
/*  228 */       return new ByteArrayInputStream(bytes);
/*      */     }
/*      */     
/*  231 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputStream getBinaryStream(String col) throws SQLException {
/*  239 */     return getBinaryStream(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getByte(int col) throws SQLException {
/*  246 */     return (byte)getInt(col);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getByte(String col) throws SQLException {
/*  253 */     return getByte(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBytes(int col) throws SQLException {
/*  260 */     return getDatabase().column_blob(this.stmt.pointer, markCol(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBytes(String col) throws SQLException {
/*  267 */     return getBytes(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reader getCharacterStream(int col) throws SQLException {
/*  274 */     String string = getString(col);
/*  275 */     return (string == null) ? null : new StringReader(string);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reader getCharacterStream(String col) throws SQLException {
/*  282 */     return getCharacterStream(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(int col) throws SQLException {
/*  289 */     DB db = getDatabase();
/*  290 */     switch (db.column_type(this.stmt.pointer, markCol(col))) {
/*      */       case 5:
/*  292 */         return null;
/*      */       
/*      */       case 3:
/*      */         try {
/*  296 */           return new Date(getConnectionConfig().getDateFormat().parse(db.column_text(this.stmt.pointer, markCol(col))).getTime());
/*      */         }
/*  298 */         catch (Exception e) {
/*  299 */           SQLException error = new SQLException("Error parsing date");
/*  300 */           error.initCause(e);
/*      */           
/*  302 */           throw error;
/*      */         } 
/*      */       
/*      */       case 2:
/*  306 */         return new Date(julianDateToCalendar(Double.valueOf(db.column_double(this.stmt.pointer, markCol(col)))).getTimeInMillis());
/*      */     } 
/*      */     
/*  309 */     return new Date(db.column_long(this.stmt.pointer, markCol(col)) * getConnectionConfig().getDateMultiplier());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(int col, Calendar cal) throws SQLException {
/*  317 */     checkCalendar(cal);
/*      */     
/*  319 */     DB db = getDatabase();
/*  320 */     switch (db.column_type(this.stmt.pointer, markCol(col))) {
/*      */       case 5:
/*  322 */         return null;
/*      */       
/*      */       case 3:
/*      */         try {
/*  326 */           FastDateFormat dateFormat = FastDateFormat.getInstance(getConnectionConfig().getDateStringFormat(), cal.getTimeZone());
/*      */           
/*  328 */           return new Date(dateFormat.parse(db.column_text(this.stmt.pointer, markCol(col))).getTime());
/*      */         }
/*  330 */         catch (Exception e) {
/*  331 */           SQLException error = new SQLException("Error parsing time stamp");
/*  332 */           error.initCause(e);
/*      */           
/*  334 */           throw error;
/*      */         } 
/*      */       
/*      */       case 2:
/*  338 */         return new Date(julianDateToCalendar(Double.valueOf(db.column_double(this.stmt.pointer, markCol(col))), cal).getTimeInMillis());
/*      */     } 
/*      */     
/*  341 */     cal.setTimeInMillis(db.column_long(this.stmt.pointer, markCol(col)) * getConnectionConfig().getDateMultiplier());
/*  342 */     return new Date(cal.getTime().getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(String col) throws SQLException {
/*  350 */     return getDate(findColumn(col), Calendar.getInstance());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getDate(String col, Calendar cal) throws SQLException {
/*  357 */     return getDate(findColumn(col), cal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble(int col) throws SQLException {
/*  364 */     DB db = getDatabase();
/*  365 */     if (db.column_type(this.stmt.pointer, markCol(col)) == 5) {
/*  366 */       return 0.0D;
/*      */     }
/*  368 */     return db.column_double(this.stmt.pointer, markCol(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble(String col) throws SQLException {
/*  375 */     return getDouble(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloat(int col) throws SQLException {
/*  382 */     DB db = getDatabase();
/*  383 */     if (db.column_type(this.stmt.pointer, markCol(col)) == 5) {
/*  384 */       return 0.0F;
/*      */     }
/*  386 */     return (float)db.column_double(this.stmt.pointer, markCol(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloat(String col) throws SQLException {
/*  393 */     return getFloat(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(int col) throws SQLException {
/*  400 */     DB db = getDatabase();
/*  401 */     return db.column_int(this.stmt.pointer, markCol(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(String col) throws SQLException {
/*  408 */     return getInt(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong(int col) throws SQLException {
/*  415 */     DB db = getDatabase();
/*  416 */     return db.column_long(this.stmt.pointer, markCol(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong(String col) throws SQLException {
/*  423 */     return getLong(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getShort(int col) throws SQLException {
/*  430 */     return (short)getInt(col);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getShort(String col) throws SQLException {
/*  437 */     return getShort(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(int col) throws SQLException {
/*  444 */     DB db = getDatabase();
/*  445 */     return db.column_text(this.stmt.pointer, markCol(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(String col) throws SQLException {
/*  452 */     return getString(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Time getTime(int col) throws SQLException {
/*  459 */     DB db = getDatabase();
/*  460 */     switch (db.column_type(this.stmt.pointer, markCol(col))) {
/*      */       case 5:
/*  462 */         return null;
/*      */       
/*      */       case 3:
/*      */         try {
/*  466 */           return new Time(getConnectionConfig().getDateFormat().parse(db.column_text(this.stmt.pointer, markCol(col))).getTime());
/*      */         }
/*  468 */         catch (Exception e) {
/*  469 */           SQLException error = new SQLException("Error parsing time");
/*  470 */           error.initCause(e);
/*      */           
/*  472 */           throw error;
/*      */         } 
/*      */       
/*      */       case 2:
/*  476 */         return new Time(julianDateToCalendar(Double.valueOf(db.column_double(this.stmt.pointer, markCol(col)))).getTimeInMillis());
/*      */     } 
/*      */     
/*  479 */     return new Time(db.column_long(this.stmt.pointer, markCol(col)) * getConnectionConfig().getDateMultiplier());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Time getTime(int col, Calendar cal) throws SQLException {
/*  487 */     checkCalendar(cal);
/*  488 */     DB db = getDatabase();
/*  489 */     switch (db.column_type(this.stmt.pointer, markCol(col))) {
/*      */       case 5:
/*  491 */         return null;
/*      */       
/*      */       case 3:
/*      */         try {
/*  495 */           FastDateFormat dateFormat = FastDateFormat.getInstance(getConnectionConfig().getDateStringFormat(), cal.getTimeZone());
/*      */           
/*  497 */           return new Time(dateFormat.parse(db.column_text(this.stmt.pointer, markCol(col))).getTime());
/*      */         }
/*  499 */         catch (Exception e) {
/*  500 */           SQLException error = new SQLException("Error parsing time");
/*  501 */           error.initCause(e);
/*      */           
/*  503 */           throw error;
/*      */         } 
/*      */       
/*      */       case 2:
/*  507 */         return new Time(julianDateToCalendar(Double.valueOf(db.column_double(this.stmt.pointer, markCol(col))), cal).getTimeInMillis());
/*      */     } 
/*      */     
/*  510 */     cal.setTimeInMillis(db.column_long(this.stmt.pointer, markCol(col)) * getConnectionConfig().getDateMultiplier());
/*  511 */     return new Time(cal.getTime().getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Time getTime(String col) throws SQLException {
/*  519 */     return getTime(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Time getTime(String col, Calendar cal) throws SQLException {
/*  526 */     return getTime(findColumn(col), cal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(int col) throws SQLException {
/*  533 */     DB db = getDatabase();
/*  534 */     switch (db.column_type(this.stmt.pointer, markCol(col))) {
/*      */       case 5:
/*  536 */         return null;
/*      */       
/*      */       case 3:
/*      */         try {
/*  540 */           return new Timestamp(getConnectionConfig().getDateFormat().parse(db.column_text(this.stmt.pointer, markCol(col))).getTime());
/*      */         }
/*  542 */         catch (Exception e) {
/*  543 */           SQLException error = new SQLException("Error parsing time stamp");
/*  544 */           error.initCause(e);
/*      */           
/*  546 */           throw error;
/*      */         } 
/*      */       
/*      */       case 2:
/*  550 */         return new Timestamp(julianDateToCalendar(Double.valueOf(db.column_double(this.stmt.pointer, markCol(col)))).getTimeInMillis());
/*      */     } 
/*      */     
/*  553 */     return new Timestamp(db.column_long(this.stmt.pointer, markCol(col)) * getConnectionConfig().getDateMultiplier());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(int col, Calendar cal) throws SQLException {
/*  561 */     if (cal == null) {
/*  562 */       return getTimestamp(col);
/*      */     }
/*      */     
/*  565 */     DB db = getDatabase();
/*  566 */     switch (db.column_type(this.stmt.pointer, markCol(col))) {
/*      */       case 5:
/*  568 */         return null;
/*      */       
/*      */       case 3:
/*      */         try {
/*  572 */           FastDateFormat dateFormat = FastDateFormat.getInstance(getConnectionConfig().getDateStringFormat(), cal.getTimeZone());
/*      */           
/*  574 */           return new Timestamp(dateFormat.parse(db.column_text(this.stmt.pointer, markCol(col))).getTime());
/*      */         }
/*  576 */         catch (Exception e) {
/*  577 */           SQLException error = new SQLException("Error parsing time stamp");
/*  578 */           error.initCause(e);
/*      */           
/*  580 */           throw error;
/*      */         } 
/*      */       
/*      */       case 2:
/*  584 */         return new Timestamp(julianDateToCalendar(Double.valueOf(db.column_double(this.stmt.pointer, markCol(col))), cal).getTimeInMillis());
/*      */     } 
/*      */     
/*  587 */     cal.setTimeInMillis(db.column_long(this.stmt.pointer, markCol(col)) * getConnectionConfig().getDateMultiplier());
/*      */     
/*  589 */     return new Timestamp(cal.getTime().getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(String col) throws SQLException {
/*  597 */     return getTimestamp(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Timestamp getTimestamp(String c, Calendar ca) throws SQLException {
/*  604 */     return getTimestamp(findColumn(c), ca);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObject(int col) throws SQLException {
/*      */     long val;
/*  611 */     switch (getDatabase().column_type(this.stmt.pointer, markCol(col))) {
/*      */       case 1:
/*  613 */         val = getLong(col);
/*  614 */         if (val > 2147483647L || val < -2147483648L) {
/*  615 */           return new Long(val);
/*      */         }
/*      */         
/*  618 */         return new Integer((int)val);
/*      */       
/*      */       case 2:
/*  621 */         return new Double(getDouble(col));
/*      */       case 4:
/*  623 */         return getBytes(col);
/*      */       case 5:
/*  625 */         return null;
/*      */     } 
/*      */     
/*  628 */     return getString(col);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObject(String col) throws SQLException {
/*  636 */     return getObject(findColumn(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement getStatement() {
/*  643 */     return (Statement)this.stmt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCursorName() throws SQLException {
/*  650 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SQLWarning getWarnings() throws SQLException {
/*  657 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearWarnings() throws SQLException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  670 */   protected static final Pattern COLUMN_TYPENAME = Pattern.compile("([^\\(]*)");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  675 */   protected static final Pattern COLUMN_TYPECAST = Pattern.compile("cast\\(.*?\\s+as\\s+(.*?)\\s*\\)");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  680 */   protected static final Pattern COLUMN_PRECISION = Pattern.compile(".*?\\((.*?)\\)");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResultSetMetaData getMetaData() throws SQLException {
/*  689 */     return (ResultSetMetaData)this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCatalogName(int col) throws SQLException {
/*  696 */     return getDatabase().column_table_name(this.stmt.pointer, checkCol(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getColumnClassName(int col) throws SQLException {
/*  703 */     checkCol(col);
/*  704 */     return "java.lang.Object";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnCount() throws SQLException {
/*  711 */     checkCol(1);
/*  712 */     return this.colsMeta.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnDisplaySize(int col) throws SQLException {
/*  719 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getColumnLabel(int col) throws SQLException {
/*  726 */     return getColumnName(col);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getColumnName(int col) throws SQLException {
/*  733 */     return getDatabase().column_name(this.stmt.pointer, checkCol(col));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColumnType(int col) throws SQLException {
/*  740 */     String typeName = getColumnTypeName(col);
/*  741 */     int valueType = getDatabase().column_type(this.stmt.pointer, checkCol(col));
/*      */     
/*  743 */     if (valueType == 1 || valueType == 5) {
/*  744 */       if ("BOOLEAN".equals(typeName)) {
/*  745 */         return 16;
/*      */       }
/*      */       
/*  748 */       if ("TINYINT".equals(typeName)) {
/*  749 */         return -6;
/*      */       }
/*      */       
/*  752 */       if ("SMALLINT".equals(typeName) || "INT2".equals(typeName)) {
/*  753 */         return 5;
/*      */       }
/*      */       
/*  756 */       if ("BIGINT".equals(typeName) || "INT8".equals(typeName) || "UNSIGNED BIG INT"
/*  757 */         .equals(typeName)) {
/*  758 */         return -5;
/*      */       }
/*      */       
/*  761 */       if ("DATE".equals(typeName) || "DATETIME".equals(typeName)) {
/*  762 */         return 91;
/*      */       }
/*      */       
/*  765 */       if ("TIMESTAMP".equals(typeName)) {
/*  766 */         return 93;
/*      */       }
/*      */       
/*  769 */       if (valueType == 1 || "INT"
/*  770 */         .equals(typeName) || "INTEGER"
/*  771 */         .equals(typeName) || "MEDIUMINT"
/*  772 */         .equals(typeName)) {
/*  773 */         return 4;
/*      */       }
/*      */     } 
/*      */     
/*  777 */     if (valueType == 2 || valueType == 5) {
/*  778 */       if ("DECIMAL".equals(typeName)) {
/*  779 */         return 3;
/*      */       }
/*      */       
/*  782 */       if ("DOUBLE".equals(typeName) || "DOUBLE PRECISION".equals(typeName)) {
/*  783 */         return 8;
/*      */       }
/*      */       
/*  786 */       if ("NUMERIC".equals(typeName)) {
/*  787 */         return 2;
/*      */       }
/*      */       
/*  790 */       if ("REAL".equals(typeName)) {
/*  791 */         return 7;
/*      */       }
/*      */       
/*  794 */       if (valueType == 2 || "FLOAT"
/*  795 */         .equals(typeName)) {
/*  796 */         return 6;
/*      */       }
/*      */     } 
/*      */     
/*  800 */     if (valueType == 3 || valueType == 5) {
/*  801 */       if ("CHARACTER".equals(typeName) || "NCHAR".equals(typeName) || "NATIVE CHARACTER"
/*  802 */         .equals(typeName) || "CHAR".equals(typeName)) {
/*  803 */         return 1;
/*      */       }
/*      */       
/*  806 */       if ("CLOB".equals(typeName)) {
/*  807 */         return 2005;
/*      */       }
/*      */       
/*  810 */       if ("DATE".equals(typeName) || "DATETIME".equals(typeName)) {
/*  811 */         return 91;
/*      */       }
/*      */       
/*  814 */       if (valueType == 3 || "VARCHAR"
/*  815 */         .equals(typeName) || "VARYING CHARACTER"
/*  816 */         .equals(typeName) || "NVARCHAR"
/*  817 */         .equals(typeName) || "TEXT"
/*  818 */         .equals(typeName)) {
/*  819 */         return 12;
/*      */       }
/*      */     } 
/*      */     
/*  823 */     if (valueType == 4 || valueType == 5) {
/*  824 */       if ("BINARY".equals(typeName)) {
/*  825 */         return -2;
/*      */       }
/*      */       
/*  828 */       if (valueType == 4 || "BLOB"
/*  829 */         .equals(typeName)) {
/*  830 */         return 2004;
/*      */       }
/*      */     } 
/*      */     
/*  834 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getColumnTypeName(int col) throws SQLException {
/*  843 */     String declType = getColumnDeclType(col);
/*      */     
/*  845 */     if (declType != null) {
/*  846 */       Matcher matcher = COLUMN_TYPENAME.matcher(declType);
/*      */       
/*  848 */       matcher.find();
/*  849 */       return matcher.group(1).toUpperCase(Locale.ENGLISH);
/*      */     } 
/*      */     
/*  852 */     switch (getDatabase().column_type(this.stmt.pointer, checkCol(col))) {
/*      */       case 1:
/*  854 */         return "INTEGER";
/*      */       case 2:
/*  856 */         return "FLOAT";
/*      */       case 4:
/*  858 */         return "BLOB";
/*      */       case 5:
/*  860 */         return "NUMERIC";
/*      */       case 3:
/*  862 */         return "TEXT";
/*      */     } 
/*  864 */     return "NUMERIC";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPrecision(int col) throws SQLException {
/*  872 */     String declType = getColumnDeclType(col);
/*      */     
/*  874 */     if (declType != null) {
/*  875 */       Matcher matcher = COLUMN_PRECISION.matcher(declType);
/*      */       
/*  877 */       return matcher.find() ? Integer.parseInt(matcher.group(1).split(",")[0].trim()) : 0;
/*      */     } 
/*      */     
/*  880 */     return 0;
/*      */   }
/*      */   
/*      */   private String getColumnDeclType(int col) throws SQLException {
/*  884 */     DB db = getDatabase();
/*  885 */     String declType = db.column_decltype(this.stmt.pointer, checkCol(col));
/*      */     
/*  887 */     if (declType == null) {
/*  888 */       Matcher matcher = COLUMN_TYPECAST.matcher(db.column_name(this.stmt.pointer, checkCol(col)));
/*  889 */       declType = matcher.find() ? matcher.group(1) : null;
/*      */     } 
/*      */     
/*  892 */     return declType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getScale(int col) throws SQLException {
/*  898 */     String declType = getColumnDeclType(col);
/*      */     
/*  900 */     if (declType != null) {
/*  901 */       Matcher matcher = COLUMN_PRECISION.matcher(declType);
/*      */       
/*  903 */       if (matcher.find()) {
/*  904 */         String[] array = matcher.group(1).split(",");
/*      */         
/*  906 */         if (array.length == 2) {
/*  907 */           return Integer.parseInt(array[1].trim());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  912 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSchemaName(int col) throws SQLException {
/*  919 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTableName(int col) throws SQLException {
/*  926 */     String tableName = getDatabase().column_table_name(this.stmt.pointer, checkCol(col));
/*  927 */     if (tableName == null)
/*      */     {
/*      */       
/*  930 */       return "";
/*      */     }
/*  932 */     return tableName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int isNullable(int col) throws SQLException {
/*  939 */     checkMeta();
/*  940 */     return this.meta[checkCol(col)][1] ? 0 : 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAutoIncrement(int col) throws SQLException {
/*  947 */     checkMeta();
/*  948 */     return this.meta[checkCol(col)][2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCaseSensitive(int col) throws SQLException {
/*  955 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCurrency(int col) throws SQLException {
/*  962 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDefinitelyWritable(int col) throws SQLException {
/*  969 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadOnly(int col) throws SQLException {
/*  976 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSearchable(int col) throws SQLException {
/*  983 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSigned(int col) throws SQLException {
/*  990 */     String typeName = getColumnTypeName(col);
/*      */     
/*  992 */     return ("NUMERIC".equals(typeName) || "INTEGER"
/*  993 */       .equals(typeName) || "REAL"
/*  994 */       .equals(typeName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWritable(int col) throws SQLException {
/* 1001 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getConcurrency() throws SQLException {
/* 1008 */     return 1007;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean rowDeleted() throws SQLException {
/* 1015 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean rowInserted() throws SQLException {
/* 1022 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean rowUpdated() throws SQLException {
/* 1029 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Calendar julianDateToCalendar(Double jd) {
/* 1036 */     return julianDateToCalendar(jd, Calendar.getInstance());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Calendar julianDateToCalendar(Double jd, Calendar cal) {
/*      */     int A;
/* 1045 */     if (jd == null) {
/* 1046 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1051 */     double w = jd.doubleValue() + 0.5D;
/* 1052 */     int Z = (int)w;
/* 1053 */     double F = w - Z;
/*      */     
/* 1055 */     if (Z < 2299161) {
/* 1056 */       A = Z;
/*      */     } else {
/*      */       
/* 1059 */       int alpha = (int)((Z - 1867216.25D) / 36524.25D);
/* 1060 */       A = Z + 1 + alpha - (int)(alpha / 4.0D);
/*      */     } 
/*      */     
/* 1063 */     int B = A + 1524;
/* 1064 */     int C = (int)((B - 122.1D) / 365.25D);
/* 1065 */     int D = (int)(365.25D * C);
/* 1066 */     int E = (int)((B - D) / 30.6001D);
/*      */ 
/*      */     
/* 1069 */     int mm = E - ((E < 13.5D) ? 1 : 13);
/*      */ 
/*      */     
/* 1072 */     int yyyy = C - ((mm > 2.5D) ? 4716 : 4715);
/*      */ 
/*      */     
/* 1075 */     double jjd = (B - D - (int)(30.6001D * E)) + F;
/* 1076 */     int dd = (int)jjd;
/*      */ 
/*      */     
/* 1079 */     double hhd = jjd - dd;
/* 1080 */     int hh = (int)(24.0D * hhd);
/*      */ 
/*      */     
/* 1083 */     double mnd = 24.0D * hhd - hh;
/* 1084 */     int mn = (int)(60.0D * mnd);
/*      */ 
/*      */     
/* 1087 */     double ssd = 60.0D * mnd - mn;
/* 1088 */     int ss = (int)(60.0D * ssd);
/*      */ 
/*      */     
/* 1091 */     double msd = 60.0D * ssd - ss;
/* 1092 */     int ms = (int)(1000.0D * msd);
/*      */     
/* 1094 */     cal.set(yyyy, mm - 1, dd, hh, mn, ss);
/* 1095 */     cal.set(14, ms);
/*      */     
/* 1097 */     if (yyyy < 1) {
/* 1098 */       cal.set(0, 0);
/* 1099 */       cal.set(1, -(yyyy - 1));
/*      */     } 
/*      */     
/* 1102 */     return cal;
/*      */   }
/*      */   
/*      */   public void checkCalendar(Calendar cal) throws SQLException {
/* 1106 */     if (cal != null) {
/*      */       return;
/*      */     }
/* 1109 */     SQLException e = new SQLException("Expected a calendar instance.");
/* 1110 */     e.initCause(new NullPointerException());
/*      */     
/* 1112 */     throw e;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlite\jdbc3\JDBC3ResultSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */