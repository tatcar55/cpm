/*     */ package org.sqlite;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import java.util.concurrent.Executor;
/*     */ import org.sqlite.core.CoreDatabaseMetaData;
/*     */ import org.sqlite.core.DB;
/*     */ import org.sqlite.core.NativeDB;
/*     */ import org.sqlite.jdbc4.JDBC4DatabaseMetaData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SQLiteConnection
/*     */   implements Connection
/*     */ {
/*     */   private static final String RESOURCE_NAME_PREFIX = ":resource:";
/*     */   private final DB db;
/*  30 */   private CoreDatabaseMetaData meta = null;
/*     */ 
/*     */   
/*     */   private final SQLiteConnectionConfig connectionConfig;
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLiteConnection(DB db) {
/*  38 */     this.db = db;
/*  39 */     this.connectionConfig = db.getConfig().newConnectionConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLiteConnection(String url, String fileName) throws SQLException {
/*  49 */     this(url, fileName, new Properties());
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
/*     */   public SQLiteConnection(String url, String fileName, Properties prop) throws SQLException {
/*  61 */     this.db = open(url, fileName, prop);
/*  62 */     SQLiteConfig config = this.db.getConfig();
/*  63 */     this.connectionConfig = this.db.getConfig().newConnectionConfig();
/*     */     
/*  65 */     config.apply(this);
/*     */   }
/*     */   
/*     */   public SQLiteConnectionConfig getConnectionConfig() {
/*  69 */     return this.connectionConfig;
/*     */   }
/*     */   
/*     */   public CoreDatabaseMetaData getSQLiteDatabaseMetaData() throws SQLException {
/*  73 */     checkOpen();
/*     */     
/*  75 */     if (this.meta == null) {
/*  76 */       this.meta = (CoreDatabaseMetaData)new JDBC4DatabaseMetaData(this);
/*     */     }
/*     */     
/*  79 */     return this.meta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseMetaData getMetaData() throws SQLException {
/*  86 */     return (DatabaseMetaData)getSQLiteDatabaseMetaData();
/*     */   }
/*     */   
/*     */   public String getUrl() {
/*  90 */     return this.db.getUrl();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSchema(String schema) throws SQLException {}
/*     */ 
/*     */   
/*     */   public String getSchema() throws SQLException {
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void abort(Executor executor) throws SQLException {}
/*     */ 
/*     */   
/*     */   public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {}
/*     */   
/*     */   public int getNetworkTimeout() throws SQLException {
/* 109 */     return 0;
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkCursor(int rst, int rsc, int rsh) throws SQLException {
/* 125 */     if (rst != 1003)
/* 126 */       throw new SQLException("SQLite only supports TYPE_FORWARD_ONLY cursors"); 
/* 127 */     if (rsc != 1007)
/* 128 */       throw new SQLException("SQLite only supports CONCUR_READ_ONLY cursors"); 
/* 129 */     if (rsh != 2) {
/* 130 */       throw new SQLException("SQLite only supports closing cursors at commit");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTransactionMode(SQLiteConfig.TransactionMode mode) {
/* 139 */     this.connectionConfig.setTransactionMode(mode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransactionIsolation() {
/* 147 */     return this.connectionConfig.getTransactionIsolation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionIsolation(int level) throws SQLException {
/* 154 */     checkOpen();
/*     */     
/* 156 */     switch (level) {
/*     */       case 8:
/* 158 */         getDatabase().exec("PRAGMA read_uncommitted = false;", getAutoCommit());
/*     */         break;
/*     */       case 1:
/* 161 */         getDatabase().exec("PRAGMA read_uncommitted = true;", getAutoCommit());
/*     */         break;
/*     */       default:
/* 164 */         throw new SQLException("SQLite supports only TRANSACTION_SERIALIZABLE and TRANSACTION_READ_UNCOMMITTED.");
/*     */     } 
/* 166 */     this.connectionConfig.setTransactionIsolation(level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DB open(String url, String origFileName, Properties props) throws SQLException {
/*     */     NativeDB nativeDB;
/* 176 */     Properties newProps = new Properties();
/* 177 */     newProps.putAll(props);
/*     */ 
/*     */     
/* 180 */     String fileName = extractPragmasFromFilename(url, origFileName, newProps);
/* 181 */     SQLiteConfig config = new SQLiteConfig(newProps);
/*     */ 
/*     */     
/* 184 */     if (!fileName.isEmpty() && !":memory:".equals(fileName) && !fileName.startsWith("file:") && !fileName.contains("mode=memory")) {
/* 185 */       if (fileName.startsWith(":resource:")) {
/* 186 */         String resourceName = fileName.substring(":resource:".length());
/*     */ 
/*     */         
/* 189 */         ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
/* 190 */         URL resourceAddr = contextCL.getResource(resourceName);
/* 191 */         if (resourceAddr == null) {
/*     */           try {
/* 193 */             resourceAddr = new URL(resourceName);
/*     */           }
/* 195 */           catch (MalformedURLException e) {
/* 196 */             throw new SQLException(String.format("resource %s not found: %s", new Object[] { resourceName, e }));
/*     */           } 
/*     */         }
/*     */         
/*     */         try {
/* 201 */           fileName = extractResource(resourceAddr).getAbsolutePath();
/*     */         }
/* 203 */         catch (IOException e) {
/* 204 */           throw new SQLException(String.format("failed to load %s: %s", new Object[] { resourceName, e }));
/*     */         } 
/*     */       } else {
/*     */         
/* 208 */         File file = (new File(fileName)).getAbsoluteFile();
/* 209 */         File parent = file.getParentFile();
/* 210 */         if (parent != null && !parent.exists()) {
/* 211 */           for (File up = parent; up != null && !up.exists(); ) {
/* 212 */             parent = up;
/* 213 */             up = up.getParentFile();
/*     */           } 
/* 215 */           throw new SQLException("path to '" + fileName + "': '" + parent + "' does not exist");
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 222 */           if (!file.exists() && file.createNewFile()) {
/* 223 */             file.delete();
/*     */           }
/* 225 */         } catch (Exception e) {
/* 226 */           throw new SQLException("opening db: '" + fileName + "': " + e.getMessage());
/*     */         } 
/* 228 */         fileName = file.getAbsolutePath();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 233 */     DB db = null;
/*     */     try {
/* 235 */       NativeDB.load();
/* 236 */       nativeDB = new NativeDB(url, fileName, config);
/*     */     }
/* 238 */     catch (Exception e) {
/* 239 */       SQLException err = new SQLException("Error opening connection");
/* 240 */       err.initCause(e);
/* 241 */       throw err;
/*     */     } 
/* 243 */     nativeDB.open(fileName, config.getOpenModeFlags());
/* 244 */     return (DB)nativeDB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static File extractResource(URL resourceAddr) throws IOException {
/* 254 */     if (resourceAddr.getProtocol().equals("file")) {
/*     */       try {
/* 256 */         return new File(resourceAddr.toURI());
/*     */       }
/* 258 */       catch (URISyntaxException e) {
/* 259 */         throw new IOException(e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 263 */     String tempFolder = (new File(System.getProperty("java.io.tmpdir"))).getAbsolutePath();
/* 264 */     String dbFileName = String.format("sqlite-jdbc-tmp-%d.db", new Object[] { Integer.valueOf(resourceAddr.hashCode()) });
/* 265 */     File dbFile = new File(tempFolder, dbFileName);
/*     */     
/* 267 */     if (dbFile.exists()) {
/* 268 */       long resourceLastModified = resourceAddr.openConnection().getLastModified();
/* 269 */       long tmpFileLastModified = dbFile.lastModified();
/* 270 */       if (resourceLastModified < tmpFileLastModified) {
/* 271 */         return dbFile;
/*     */       }
/*     */ 
/*     */       
/* 275 */       boolean deletionSucceeded = dbFile.delete();
/* 276 */       if (!deletionSucceeded) {
/* 277 */         throw new IOException("failed to remove existing DB file: " + dbFile.getAbsolutePath());
/*     */       }
/*     */     } 
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
/* 291 */     byte[] buffer = new byte[8192];
/* 292 */     FileOutputStream writer = new FileOutputStream(dbFile);
/* 293 */     InputStream reader = resourceAddr.openStream();
/*     */     try {
/* 295 */       int bytesRead = 0;
/* 296 */       while ((bytesRead = reader.read(buffer)) != -1) {
/* 297 */         writer.write(buffer, 0, bytesRead);
/*     */       }
/* 299 */       return dbFile;
/*     */     } finally {
/*     */       
/* 302 */       writer.close();
/* 303 */       reader.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DB getDatabase() {
/* 309 */     return this.db;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAutoCommit() throws SQLException {
/* 317 */     checkOpen();
/*     */     
/* 319 */     return this.connectionConfig.isAutoCommit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutoCommit(boolean ac) throws SQLException {
/* 327 */     checkOpen();
/* 328 */     if (this.connectionConfig.isAutoCommit() == ac) {
/*     */       return;
/*     */     }
/* 331 */     this.connectionConfig.setAutoCommit(ac);
/* 332 */     this.db.exec(this.connectionConfig.isAutoCommit() ? "commit;" : this.connectionConfig.transactionPrefix(), ac);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBusyTimeout() {
/* 340 */     return this.db.getConfig().getBusyTimeout();
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
/*     */   public void setBusyTimeout(int timeoutMillis) throws SQLException {
/* 353 */     this.db.getConfig().setBusyTimeout(timeoutMillis);
/* 354 */     this.db.busy_timeout(timeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() throws SQLException {
/* 360 */     return this.db.isClosed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SQLException {
/* 368 */     if (isClosed())
/*     */       return; 
/* 370 */     if (this.meta != null) {
/* 371 */       this.meta.close();
/*     */     }
/* 373 */     this.db.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkOpen() throws SQLException {
/* 381 */     if (isClosed()) {
/* 382 */       throw new SQLException("database connection closed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String libversion() throws SQLException {
/* 391 */     checkOpen();
/*     */     
/* 393 */     return this.db.libversion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void commit() throws SQLException {
/* 401 */     checkOpen();
/* 402 */     if (this.connectionConfig.isAutoCommit())
/* 403 */       throw new SQLException("database in auto-commit mode"); 
/* 404 */     this.db.exec("commit;", getAutoCommit());
/* 405 */     this.db.exec(this.connectionConfig.transactionPrefix(), getAutoCommit());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback() throws SQLException {
/* 413 */     checkOpen();
/* 414 */     if (this.connectionConfig.isAutoCommit())
/* 415 */       throw new SQLException("database in auto-commit mode"); 
/* 416 */     this.db.exec("rollback;", getAutoCommit());
/* 417 */     this.db.exec(this.connectionConfig.transactionPrefix(), getAutoCommit());
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
/*     */ 
/*     */   
/*     */   protected static String extractPragmasFromFilename(String url, String filename, Properties prop) throws SQLException {
/* 432 */     int parameterDelimiter = filename.indexOf('?');
/* 433 */     if (parameterDelimiter == -1)
/*     */     {
/* 435 */       return filename;
/*     */     }
/*     */     
/* 438 */     StringBuilder sb = new StringBuilder();
/* 439 */     sb.append(filename.substring(0, parameterDelimiter));
/*     */     
/* 441 */     int nonPragmaCount = 0;
/* 442 */     String[] parameters = filename.substring(parameterDelimiter + 1).split("&");
/* 443 */     for (int i = 0; i < parameters.length; i++) {
/*     */       
/* 445 */       String parameter = parameters[parameters.length - 1 - i].trim();
/*     */       
/* 447 */       if (!parameter.isEmpty()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 452 */         String[] kvp = parameter.split("=");
/* 453 */         String key = kvp[0].trim().toLowerCase();
/* 454 */         if (SQLiteConfig.pragmaSet.contains(key)) {
/* 455 */           if (kvp.length == 1) {
/* 456 */             throw new SQLException(String.format("Please specify a value for PRAGMA %s in URL %s", new Object[] { key, url }));
/*     */           }
/* 458 */           String value = kvp[1].trim();
/* 459 */           if (!value.isEmpty() && 
/* 460 */             !prop.containsKey(key))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 470 */             prop.setProperty(key, value);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 475 */           sb.append((nonPragmaCount == 0) ? 63 : 38);
/* 476 */           sb.append(parameter);
/* 477 */           nonPragmaCount++;
/*     */         } 
/*     */       } 
/*     */     } 
/* 481 */     String newFilename = sb.toString();
/* 482 */     return newFilename;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlite\SQLiteConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */