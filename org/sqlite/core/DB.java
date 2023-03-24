/*      */ package org.sqlite.core;
/*      */ 
/*      */ import java.sql.BatchUpdateException;
/*      */ import java.sql.SQLException;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import org.sqlite.BusyHandler;
/*      */ import org.sqlite.Function;
/*      */ import org.sqlite.ProgressHandler;
/*      */ import org.sqlite.SQLiteConfig;
/*      */ import org.sqlite.SQLiteErrorCode;
/*      */ import org.sqlite.SQLiteException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class DB
/*      */   implements Codes
/*      */ {
/*      */   private final String url;
/*      */   private final String fileName;
/*      */   private final SQLiteConfig config;
/*   49 */   private final AtomicBoolean closed = new AtomicBoolean(true);
/*      */ 
/*      */   
/*   52 */   long begin = 0L;
/*   53 */   long commit = 0L;
/*      */ 
/*      */   
/*   56 */   private final Map<Long, CoreStatement> stmts = new HashMap<Long, CoreStatement>();
/*      */ 
/*      */ 
/*      */   
/*      */   public DB(String url, String fileName, SQLiteConfig config) throws SQLException {
/*   61 */     this.url = url;
/*   62 */     this.fileName = fileName;
/*   63 */     this.config = config;
/*      */   }
/*      */   
/*      */   public String getUrl() {
/*   67 */     return this.url;
/*      */   }
/*      */   
/*      */   public boolean isClosed() {
/*   71 */     return this.closed.get();
/*      */   }
/*      */   
/*      */   public SQLiteConfig getConfig() {
/*   75 */     return this.config;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void interrupt() throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void busy_timeout(int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void busy_handler(BusyHandler paramBusyHandler) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract String errmsg() throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String libversion() throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int changes() throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int total_changes() throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int shared_cache(boolean paramBoolean) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int enable_load_extension(boolean paramBoolean) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void exec(String sql, boolean autoCommit) throws SQLException {
/*  167 */     long pointer = 0L;
/*      */     try {
/*  169 */       pointer = prepare(sql);
/*  170 */       int rc = step(pointer);
/*  171 */       switch (rc) {
/*      */         case 101:
/*  173 */           ensureAutoCommit(autoCommit);
/*      */           return;
/*      */         case 100:
/*      */           return;
/*      */       } 
/*  178 */       throwex(rc);
/*      */     }
/*      */     finally {
/*      */       
/*  182 */       finalize(pointer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void open(String file, int openFlags) throws SQLException {
/*  195 */     _open(file, openFlags);
/*  196 */     this.closed.set(false);
/*      */     
/*  198 */     if (this.fileName.startsWith("file:") && !this.fileName.contains("cache="))
/*      */     {
/*  200 */       shared_cache(this.config.isEnabledSharedCache());
/*      */     }
/*  202 */     enable_load_extension(this.config.isEnabledLoadExtension());
/*  203 */     busy_timeout(this.config.getBusyTimeout());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void close() throws SQLException {
/*  214 */     synchronized (this.stmts) {
/*  215 */       Iterator<Map.Entry<Long, CoreStatement>> i = this.stmts.entrySet().iterator();
/*  216 */       while (i.hasNext()) {
/*  217 */         Map.Entry<Long, CoreStatement> entry = i.next();
/*  218 */         CoreStatement stmt = entry.getValue();
/*  219 */         finalize(((Long)entry.getKey()).longValue());
/*  220 */         if (stmt != null) {
/*  221 */           stmt.pointer = 0L;
/*      */         }
/*  223 */         i.remove();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  228 */     free_functions();
/*      */ 
/*      */     
/*  231 */     if (this.begin != 0L) {
/*  232 */       finalize(this.begin);
/*  233 */       this.begin = 0L;
/*      */     } 
/*  235 */     if (this.commit != 0L) {
/*  236 */       finalize(this.commit);
/*  237 */       this.commit = 0L;
/*      */     } 
/*      */     
/*  240 */     this.closed.set(true);
/*  241 */     _close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void prepare(CoreStatement stmt) throws SQLException {
/*  251 */     if (stmt.sql == null) {
/*  252 */       throw new NullPointerException();
/*      */     }
/*  254 */     if (stmt.pointer != 0L) {
/*  255 */       finalize(stmt);
/*      */     }
/*  257 */     stmt.pointer = prepare(stmt.sql);
/*  258 */     this.stmts.put(new Long(stmt.pointer), stmt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized int finalize(CoreStatement stmt) throws SQLException {
/*  269 */     if (stmt.pointer == 0L) {
/*  270 */       return 0;
/*      */     }
/*  272 */     int rc = 1;
/*      */     try {
/*  274 */       rc = finalize(stmt.pointer);
/*      */     } finally {
/*      */       
/*  277 */       this.stmts.remove(new Long(stmt.pointer));
/*  278 */       stmt.pointer = 0L;
/*      */     } 
/*  280 */     return rc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void _open(String paramString, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void _close() throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int _exec(String paramString) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract long prepare(String paramString) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract int finalize(long paramLong) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int step(long paramLong) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int reset(long paramLong) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int clear_bindings(long paramLong) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract int bind_parameter_count(long paramLong) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int column_count(long paramLong) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int column_type(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String column_decltype(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String column_table_name(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String column_name(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String column_text(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract byte[] column_blob(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract double column_double(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract long column_long(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int column_int(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract int bind_null(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract int bind_int(long paramLong, int paramInt1, int paramInt2) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract int bind_long(long paramLong1, int paramInt, long paramLong2) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract int bind_double(long paramLong, int paramInt, double paramDouble) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract int bind_text(long paramLong, int paramInt, String paramString) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract int bind_blob(long paramLong, int paramInt, byte[] paramArrayOfbyte) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void result_null(long paramLong) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void result_text(long paramLong, String paramString) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void result_blob(long paramLong, byte[] paramArrayOfbyte) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void result_double(long paramLong, double paramDouble) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void result_long(long paramLong1, long paramLong2) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void result_int(long paramLong, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void result_error(long paramLong, String paramString) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String value_text(Function paramFunction, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract byte[] value_blob(Function paramFunction, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract double value_double(Function paramFunction, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract long value_long(Function paramFunction, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int value_int(Function paramFunction, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int value_type(Function paramFunction, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int create_function(String paramString, Function paramFunction, int paramInt1, int paramInt2) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int destroy_function(String paramString, int paramInt) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract void free_functions() throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int backup(String paramString1, String paramString2, ProgressObserver paramProgressObserver) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int restore(String paramString1, String paramString2, ProgressObserver paramProgressObserver) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void register_progress_handler(int paramInt, ProgressHandler paramProgressHandler) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void clear_progress_handler() throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   abstract boolean[][] column_metadata(long paramLong) throws SQLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized String[] column_names(long stmt) throws SQLException {
/*  722 */     String[] names = new String[column_count(stmt)];
/*  723 */     for (int i = 0; i < names.length; i++) {
/*  724 */       names[i] = column_name(stmt, i);
/*      */     }
/*  726 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized int sqlbind(long stmt, int pos, Object v) throws SQLException {
/*  739 */     pos++;
/*  740 */     if (v == null) {
/*  741 */       return bind_null(stmt, pos);
/*      */     }
/*  743 */     if (v instanceof Integer) {
/*  744 */       return bind_int(stmt, pos, ((Integer)v).intValue());
/*      */     }
/*  746 */     if (v instanceof Short) {
/*  747 */       return bind_int(stmt, pos, ((Short)v).intValue());
/*      */     }
/*  749 */     if (v instanceof Long) {
/*  750 */       return bind_long(stmt, pos, ((Long)v).longValue());
/*      */     }
/*  752 */     if (v instanceof Float) {
/*  753 */       return bind_double(stmt, pos, ((Float)v).doubleValue());
/*      */     }
/*  755 */     if (v instanceof Double) {
/*  756 */       return bind_double(stmt, pos, ((Double)v).doubleValue());
/*      */     }
/*  758 */     if (v instanceof String) {
/*  759 */       return bind_text(stmt, pos, (String)v);
/*      */     }
/*  761 */     if (v instanceof byte[]) {
/*  762 */       return bind_blob(stmt, pos, (byte[])v);
/*      */     }
/*      */     
/*  765 */     throw new SQLException("unexpected param type: " + v.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized int[] executeBatch(long stmt, int count, Object[] vals, boolean autoCommit) throws SQLException {
/*  780 */     if (count < 1) {
/*  781 */       throw new SQLException("count (" + count + ") < 1");
/*      */     }
/*      */     
/*  784 */     int params = bind_parameter_count(stmt);
/*      */ 
/*      */     
/*  787 */     int[] changes = new int[count];
/*      */     
/*      */     try {
/*  790 */       for (int i = 0; i < count; i++) {
/*  791 */         reset(stmt);
/*  792 */         for (int j = 0; j < params; j++) {
/*  793 */           int k = sqlbind(stmt, j, vals[i * params + j]);
/*  794 */           if (k != 0) {
/*  795 */             throwex(k);
/*      */           }
/*      */         } 
/*      */         
/*  799 */         int rc = step(stmt);
/*  800 */         if (rc != 101) {
/*  801 */           reset(stmt);
/*  802 */           if (rc == 100) {
/*  803 */             throw new BatchUpdateException("batch entry " + i + ": query returns results", changes);
/*      */           }
/*  805 */           throwex(rc);
/*      */         } 
/*      */         
/*  808 */         changes[i] = changes();
/*      */       } 
/*      */     } finally {
/*      */       
/*  812 */       ensureAutoCommit(autoCommit);
/*      */     } 
/*      */     
/*  815 */     reset(stmt);
/*  816 */     return changes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized boolean execute(CoreStatement stmt, Object[] vals) throws SQLException {
/*  827 */     if (vals != null) {
/*  828 */       int params = bind_parameter_count(stmt.pointer);
/*  829 */       if (params > vals.length) {
/*  830 */         throw new SQLException("assertion failure: param count (" + params + ") > value count (" + vals.length + ")");
/*      */       }
/*      */ 
/*      */       
/*  834 */       for (int i = 0; i < params; i++) {
/*  835 */         int rc = sqlbind(stmt.pointer, i, vals[i]);
/*  836 */         if (rc != 0) {
/*  837 */           throwex(rc);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  842 */     int statusCode = step(stmt.pointer) & 0xFF;
/*  843 */     switch (statusCode) {
/*      */       case 101:
/*  845 */         reset(stmt.pointer);
/*  846 */         ensureAutoCommit(stmt.conn.getAutoCommit());
/*  847 */         return false;
/*      */       case 100:
/*  849 */         return true;
/*      */       case 5:
/*      */       case 6:
/*      */       case 19:
/*      */       case 21:
/*  854 */         throw newSQLException(statusCode);
/*      */     } 
/*  856 */     finalize(stmt);
/*  857 */     throw newSQLException(statusCode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final synchronized boolean execute(String sql, boolean autoCommit) throws SQLException {
/*  870 */     int statusCode = _exec(sql);
/*  871 */     switch (statusCode) {
/*      */       case 0:
/*  873 */         return false;
/*      */       case 101:
/*  875 */         ensureAutoCommit(autoCommit);
/*  876 */         return false;
/*      */       case 100:
/*  878 */         return true;
/*      */     } 
/*  880 */     throw newSQLException(statusCode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized int executeUpdate(CoreStatement stmt, Object[] vals) throws SQLException {
/*      */     try {
/*  895 */       if (execute(stmt, vals)) {
/*  896 */         throw new SQLException("query returns results");
/*      */       }
/*      */     } finally {
/*  899 */       if (stmt.pointer != 0L) reset(stmt.pointer); 
/*      */     } 
/*  901 */     return changes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void throwex() throws SQLException {
/*  909 */     throw new SQLException(errmsg());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void throwex(int errorCode) throws SQLException {
/*  918 */     throw newSQLException(errorCode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final void throwex(int errorCode, String errorMessage) throws SQLiteException {
/*  928 */     throw newSQLException(errorCode, errorMessage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SQLiteException newSQLException(int errorCode, String errorMessage) {
/*  939 */     SQLiteErrorCode code = SQLiteErrorCode.getErrorCode(errorCode);
/*      */     
/*  941 */     SQLiteException e = new SQLiteException(String.format("%s (%s)", new Object[] { code, errorMessage }), code);
/*      */     
/*  943 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SQLiteException newSQLException(int errorCode) throws SQLException {
/*  953 */     return newSQLException(errorCode, errmsg());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void ensureAutoCommit(boolean autoCommit) throws SQLException {
/*  989 */     if (!autoCommit) {
/*      */       return;
/*      */     }
/*      */     
/*  993 */     if (this.begin == 0L) {
/*  994 */       this.begin = prepare("begin;");
/*      */     }
/*  996 */     if (this.commit == 0L) {
/*  997 */       this.commit = prepare("commit;");
/*      */     }
/*      */     
/*      */     try {
/* 1001 */       if (step(this.begin) != 101) {
/*      */         return;
/*      */       }
/*      */       
/* 1005 */       int rc = step(this.commit);
/* 1006 */       if (rc != 101) {
/* 1007 */         reset(this.commit);
/* 1008 */         throwex(rc);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/* 1013 */       reset(this.begin);
/* 1014 */       reset(this.commit);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static interface ProgressObserver {
/*      */     void progress(int param1Int1, int param1Int2);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlite\core\DB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */