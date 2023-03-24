/*     */ package org.sqlite;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverPropertyInfo;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public class SQLiteConfig
/*     */ {
/*     */   public static final String DEFAULT_DATE_STRING_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
/*     */   private final Properties pragmaTable;
/*  51 */   private int openModeFlag = 0;
/*     */ 
/*     */   
/*     */   private final int busyTimeout;
/*     */ 
/*     */   
/*     */   private final SQLiteConnectionConfig defaultConnectionConfig;
/*     */ 
/*     */   
/*     */   public SQLiteConfig() {
/*  61 */     this(new Properties());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLiteConfig(Properties prop) {
/*  70 */     this.pragmaTable = prop;
/*     */     
/*  72 */     String openMode = this.pragmaTable.getProperty(Pragma.OPEN_MODE.pragmaName);
/*  73 */     if (openMode != null) {
/*  74 */       this.openModeFlag = Integer.parseInt(openMode);
/*     */     }
/*     */     else {
/*     */       
/*  78 */       setOpenMode(SQLiteOpenMode.READWRITE);
/*  79 */       setOpenMode(SQLiteOpenMode.CREATE);
/*     */     } 
/*     */     
/*  82 */     setSharedCache(Boolean.parseBoolean(this.pragmaTable.getProperty(Pragma.SHARED_CACHE.pragmaName, "false")));
/*     */     
/*  84 */     setOpenMode(SQLiteOpenMode.OPEN_URI);
/*     */     
/*  86 */     this.busyTimeout = Integer.parseInt(this.pragmaTable.getProperty(Pragma.BUSY_TIMEOUT.pragmaName, "3000"));
/*  87 */     this.defaultConnectionConfig = SQLiteConnectionConfig.fromPragmaTable(this.pragmaTable);
/*     */   }
/*     */ 
/*     */   
/*     */   public SQLiteConnectionConfig newConnectionConfig() {
/*  92 */     return this.defaultConnectionConfig.copyConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection createConnection(String url) throws SQLException {
/* 101 */     return JDBC.createConnection(url, toProperties());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply(Connection conn) throws SQLException {
/* 111 */     HashSet<String> pragmaParams = new HashSet<String>();
/* 112 */     for (Pragma each : Pragma.values()) {
/* 113 */       pragmaParams.add(each.pragmaName);
/*     */     }
/*     */     
/* 116 */     pragmaParams.remove(Pragma.OPEN_MODE.pragmaName);
/* 117 */     pragmaParams.remove(Pragma.SHARED_CACHE.pragmaName);
/* 118 */     pragmaParams.remove(Pragma.LOAD_EXTENSION.pragmaName);
/* 119 */     pragmaParams.remove(Pragma.DATE_PRECISION.pragmaName);
/* 120 */     pragmaParams.remove(Pragma.DATE_CLASS.pragmaName);
/* 121 */     pragmaParams.remove(Pragma.DATE_STRING_FORMAT.pragmaName);
/* 122 */     pragmaParams.remove(Pragma.PASSWORD.pragmaName);
/* 123 */     pragmaParams.remove(Pragma.HEXKEY_MODE.pragmaName);
/*     */     
/* 125 */     Statement stat = conn.createStatement();
/*     */     try {
/* 127 */       if (this.pragmaTable.containsKey(Pragma.PASSWORD.pragmaName)) {
/* 128 */         String password = this.pragmaTable.getProperty(Pragma.PASSWORD.pragmaName);
/* 129 */         if (password != null && !password.isEmpty()) {
/* 130 */           String passwordPragma, hexkeyMode = this.pragmaTable.getProperty(Pragma.HEXKEY_MODE.pragmaName);
/*     */           
/* 132 */           if (HexKeyMode.SSE.name().equalsIgnoreCase(hexkeyMode)) {
/* 133 */             passwordPragma = "pragma hexkey = '%s'";
/* 134 */           } else if (HexKeyMode.SQLCIPHER.name().equalsIgnoreCase(hexkeyMode)) {
/* 135 */             passwordPragma = "pragma key = \"x'%s'\"";
/*     */           } else {
/* 137 */             passwordPragma = "pragma key = '%s'";
/*     */           } 
/* 139 */           stat.execute(String.format(passwordPragma, new Object[] { password.replace("'", "''") }));
/* 140 */           stat.execute("select 1 from sqlite_master");
/*     */         } 
/*     */       } 
/*     */       
/* 144 */       for (Object each : this.pragmaTable.keySet()) {
/* 145 */         String key = each.toString();
/* 146 */         if (!pragmaParams.contains(key)) {
/*     */           continue;
/*     */         }
/*     */         
/* 150 */         String value = this.pragmaTable.getProperty(key);
/* 151 */         if (value != null) {
/* 152 */           stat.execute(String.format("pragma %s=%s", new Object[] { key, value }));
/*     */         }
/*     */       } 
/*     */     } finally {
/*     */       
/* 157 */       if (stat != null) {
/* 158 */         stat.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void set(Pragma pragma, boolean flag) {
/* 170 */     setPragma(pragma, Boolean.toString(flag));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void set(Pragma pragma, int num) {
/* 179 */     setPragma(pragma, Integer.toString(num));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean getBoolean(Pragma pragma, String defaultValue) {
/* 189 */     return Boolean.parseBoolean(this.pragmaTable.getProperty(pragma.pragmaName, defaultValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabledSharedCache() {
/* 197 */     return getBoolean(Pragma.SHARED_CACHE, "false");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabledLoadExtension() {
/* 205 */     return getBoolean(Pragma.LOAD_EXTENSION, "false");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOpenModeFlags() {
/* 212 */     return this.openModeFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPragma(Pragma pragma, String value) {
/* 221 */     this.pragmaTable.put(pragma.pragmaName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Properties toProperties() {
/* 230 */     this.pragmaTable.setProperty(Pragma.OPEN_MODE.pragmaName, Integer.toString(this.openModeFlag));
/* 231 */     this.pragmaTable.setProperty(Pragma.TRANSACTION_MODE.pragmaName, this.defaultConnectionConfig.getTransactionMode().getValue());
/* 232 */     this.pragmaTable.setProperty(Pragma.DATE_CLASS.pragmaName, this.defaultConnectionConfig.getDateClass().getValue());
/* 233 */     this.pragmaTable.setProperty(Pragma.DATE_PRECISION.pragmaName, this.defaultConnectionConfig.getDatePrecision().getValue());
/* 234 */     this.pragmaTable.setProperty(Pragma.DATE_STRING_FORMAT.pragmaName, this.defaultConnectionConfig.getDateStringFormat());
/*     */     
/* 236 */     return this.pragmaTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static DriverPropertyInfo[] getDriverPropertyInfo() {
/* 243 */     Pragma[] pragma = Pragma.values();
/* 244 */     DriverPropertyInfo[] result = new DriverPropertyInfo[pragma.length];
/* 245 */     int index = 0;
/* 246 */     for (Pragma p : Pragma.values()) {
/* 247 */       DriverPropertyInfo di = new DriverPropertyInfo(p.pragmaName, null);
/* 248 */       di.choices = p.choices;
/* 249 */       di.description = p.description;
/* 250 */       di.required = false;
/* 251 */       result[index++] = di;
/*     */     } 
/*     */     
/* 254 */     return result;
/*     */   }
/*     */   
/* 257 */   private static final String[] OnOff = new String[] { "true", "false" };
/*     */   
/* 259 */   static final Set<String> pragmaSet = new TreeSet<String>();
/*     */   
/*     */   static {
/* 262 */     for (Pragma pragma : Pragma.values()) {
/* 263 */       pragmaSet.add(pragma.pragmaName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public enum Pragma
/*     */   {
/* 270 */     OPEN_MODE("open_mode", "Database open-mode flag", null),
/* 271 */     SHARED_CACHE("shared_cache", "Enable SQLite Shared-Cache mode, native driver only", (String)SQLiteConfig.OnOff),
/* 272 */     LOAD_EXTENSION("enable_load_extension", "Enable SQLite load_extention() function, native driver only", (String)SQLiteConfig.OnOff),
/*     */ 
/*     */     
/* 275 */     CACHE_SIZE("cache_size"),
/* 276 */     MMAP_SIZE("mmap_size"),
/* 277 */     CASE_SENSITIVE_LIKE("case_sensitive_like", SQLiteConfig.OnOff),
/* 278 */     COUNT_CHANGES("count_changes", SQLiteConfig.OnOff),
/* 279 */     DEFAULT_CACHE_SIZE("default_cache_size"),
/* 280 */     DEFER_FOREIGN_KEYS("defer_foreign_keys", SQLiteConfig.OnOff),
/* 281 */     EMPTY_RESULT_CALLBACKS("empty_result_callback", SQLiteConfig.OnOff),
/* 282 */     ENCODING("encoding", SQLiteConfig.toStringArray((SQLiteConfig.PragmaValue[])SQLiteConfig.Encoding.values())),
/* 283 */     FOREIGN_KEYS("foreign_keys", SQLiteConfig.OnOff),
/* 284 */     FULL_COLUMN_NAMES("full_column_names", SQLiteConfig.OnOff),
/* 285 */     FULL_SYNC("fullsync", SQLiteConfig.OnOff),
/* 286 */     INCREMENTAL_VACUUM("incremental_vacuum"),
/* 287 */     JOURNAL_MODE("journal_mode", SQLiteConfig.toStringArray((SQLiteConfig.PragmaValue[])SQLiteConfig.JournalMode.values())),
/* 288 */     JOURNAL_SIZE_LIMIT("journal_size_limit"),
/* 289 */     LEGACY_FILE_FORMAT("legacy_file_format", SQLiteConfig.OnOff),
/* 290 */     LOCKING_MODE("locking_mode", SQLiteConfig.toStringArray((SQLiteConfig.PragmaValue[])SQLiteConfig.LockingMode.values())),
/* 291 */     PAGE_SIZE("page_size"),
/* 292 */     MAX_PAGE_COUNT("max_page_count"),
/* 293 */     READ_UNCOMMITED("read_uncommited", SQLiteConfig.OnOff),
/* 294 */     RECURSIVE_TRIGGERS("recursive_triggers", SQLiteConfig.OnOff),
/* 295 */     REVERSE_UNORDERED_SELECTS("reverse_unordered_selects", SQLiteConfig.OnOff),
/* 296 */     SECURE_DELETE("secure_delete", new String[] { "true", "false", "fast" }),
/* 297 */     SHORT_COLUMN_NAMES("short_column_names", SQLiteConfig.OnOff),
/* 298 */     SYNCHRONOUS("synchronous", SQLiteConfig.toStringArray((SQLiteConfig.PragmaValue[])SQLiteConfig.SynchronousMode.values())),
/* 299 */     TEMP_STORE("temp_store", SQLiteConfig.toStringArray((SQLiteConfig.PragmaValue[])SQLiteConfig.TempStore.values())),
/* 300 */     TEMP_STORE_DIRECTORY("temp_store_directory"),
/* 301 */     USER_VERSION("user_version"),
/* 302 */     APPLICATION_ID("application_id"),
/*     */ 
/*     */     
/* 305 */     TRANSACTION_MODE("transaction_mode", SQLiteConfig.toStringArray((SQLiteConfig.PragmaValue[])SQLiteConfig.TransactionMode.values())),
/* 306 */     DATE_PRECISION("date_precision", "\"seconds\": Read and store integer dates as seconds from the Unix Epoch (SQLite standard).\n\"milliseconds\": (DEFAULT) Read and store integer dates as milliseconds from the Unix Epoch (Java standard).", (String)SQLiteConfig.toStringArray((SQLiteConfig.PragmaValue[])SQLiteConfig.DatePrecision.values())),
/* 307 */     DATE_CLASS("date_class", "\"integer\": (Default) store dates as number of seconds or milliseconds from the Unix Epoch\n\"text\": store dates as a string of text\n\"real\": store dates as Julian Dates", (String)SQLiteConfig.toStringArray((SQLiteConfig.PragmaValue[])SQLiteConfig.DateClass.values())),
/* 308 */     DATE_STRING_FORMAT("date_string_format", "Format to store and retrieve dates stored as text. Defaults to \"yyyy-MM-dd HH:mm:ss.SSS\"", null),
/* 309 */     BUSY_TIMEOUT("busy_timeout", null),
/* 310 */     HEXKEY_MODE("hexkey_mode", SQLiteConfig.toStringArray((SQLiteConfig.PragmaValue[])SQLiteConfig.HexKeyMode.values())),
/* 311 */     PASSWORD("password", null);
/*     */ 
/*     */ 
/*     */     
/*     */     public final String pragmaName;
/*     */ 
/*     */     
/*     */     public final String[] choices;
/*     */ 
/*     */     
/*     */     public final String description;
/*     */ 
/*     */ 
/*     */     
/*     */     Pragma(String pragmaName, String description, String[] choices) {
/* 326 */       this.pragmaName = pragmaName;
/* 327 */       this.description = description;
/* 328 */       this.choices = choices;
/*     */     }
/*     */ 
/*     */     
/*     */     public final String getPragmaName() {
/* 333 */       return this.pragmaName;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOpenMode(SQLiteOpenMode mode) {
/* 343 */     this.openModeFlag |= mode.flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetOpenMode(SQLiteOpenMode mode) {
/* 352 */     this.openModeFlag &= mode.flag ^ 0xFFFFFFFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSharedCache(boolean enable) {
/* 362 */     set(Pragma.SHARED_CACHE, enable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableLoadExtension(boolean enable) {
/* 371 */     set(Pragma.LOAD_EXTENSION, enable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadOnly(boolean readOnly) {
/* 379 */     if (readOnly) {
/* 380 */       setOpenMode(SQLiteOpenMode.READONLY);
/* 381 */       resetOpenMode(SQLiteOpenMode.CREATE);
/* 382 */       resetOpenMode(SQLiteOpenMode.READWRITE);
/*     */     } else {
/*     */       
/* 385 */       setOpenMode(SQLiteOpenMode.READWRITE);
/* 386 */       setOpenMode(SQLiteOpenMode.CREATE);
/* 387 */       resetOpenMode(SQLiteOpenMode.READONLY);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCacheSize(int numberOfPages) {
/* 398 */     set(Pragma.CACHE_SIZE, numberOfPages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableCaseSensitiveLike(boolean enable) {
/* 407 */     set(Pragma.CASE_SENSITIVE_LIKE, enable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void enableCountChanges(boolean enable) {
/* 419 */     set(Pragma.COUNT_CHANGES, enable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultCacheSize(int numberOfPages) {
/* 430 */     set(Pragma.DEFAULT_CACHE_SIZE, numberOfPages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deferForeignKeys(boolean enable) {
/* 440 */     set(Pragma.DEFER_FOREIGN_KEYS, enable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void enableEmptyResultCallBacks(boolean enable) {
/* 452 */     set(Pragma.EMPTY_RESULT_CALLBACKS, enable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static interface PragmaValue
/*     */   {
/*     */     String getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] toStringArray(PragmaValue[] list) {
/* 470 */     String[] result = new String[list.length];
/* 471 */     for (int i = 0; i < list.length; i++) {
/* 472 */       result[i] = list[i].getValue();
/*     */     }
/* 474 */     return result;
/*     */   }
/*     */   
/*     */   public enum Encoding implements PragmaValue {
/* 478 */     UTF8("'UTF-8'"),
/* 479 */     UTF16("'UTF-16'"),
/* 480 */     UTF16_LITTLE_ENDIAN("'UTF-16le'"),
/* 481 */     UTF16_BIG_ENDIAN("'UTF-16be'"),
/* 482 */     UTF_8((String)UTF8),
/* 483 */     UTF_16((String)UTF16),
/* 484 */     UTF_16LE((String)UTF16_LITTLE_ENDIAN),
/* 485 */     UTF_16BE((String)UTF16_BIG_ENDIAN);
/*     */     
/*     */     public final String typeName;
/*     */     
/*     */     Encoding(String typeName) {
/* 490 */       this.typeName = typeName;
/*     */     }
/*     */     
/*     */     Encoding(Encoding encoding) {
/* 494 */       this.typeName = encoding.getValue();
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 498 */       return this.typeName;
/*     */     }
/*     */     
/*     */     public static Encoding getEncoding(String value) {
/* 502 */       return valueOf(value.replaceAll("-", "_").toUpperCase());
/*     */     }
/*     */   }
/*     */   
/*     */   public enum JournalMode implements PragmaValue {
/* 507 */     DELETE, TRUNCATE, PERSIST, MEMORY, WAL, OFF;
/*     */     
/*     */     public String getValue() {
/* 510 */       return name();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncoding(Encoding encoding) {
/* 520 */     setPragma(Pragma.ENCODING, encoding.typeName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enforceForeignKeys(boolean enforce) {
/* 531 */     set(Pragma.FOREIGN_KEYS, enforce);
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
/*     */   @Deprecated
/*     */   public void enableFullColumnNames(boolean enable) {
/* 544 */     set(Pragma.FULL_COLUMN_NAMES, enable);
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
/*     */   public void enableFullSync(boolean enable) {
/* 556 */     set(Pragma.FULL_SYNC, enable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementalVacuum(int numberOfPagesToBeRemoved) {
/* 567 */     set(Pragma.INCREMENTAL_VACUUM, numberOfPagesToBeRemoved);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJournalMode(JournalMode mode) {
/* 577 */     setPragma(Pragma.JOURNAL_MODE, mode.name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJounalSizeLimit(int limit) {
/* 588 */     set(Pragma.JOURNAL_SIZE_LIMIT, limit);
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
/*     */   public void useLegacyFileFormat(boolean use) {
/* 601 */     set(Pragma.LEGACY_FILE_FORMAT, use);
/*     */   }
/*     */   
/*     */   public enum LockingMode implements PragmaValue {
/* 605 */     NORMAL, EXCLUSIVE;
/*     */     public String getValue() {
/* 607 */       return name();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLockingMode(LockingMode mode) {
/* 617 */     setPragma(Pragma.LOCKING_MODE, mode.name());
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
/*     */   public void setPageSize(int numBytes) {
/* 631 */     set(Pragma.PAGE_SIZE, numBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxPageCount(int numPages) {
/* 640 */     set(Pragma.MAX_PAGE_COUNT, numPages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadUncommited(boolean useReadUncommitedIsolationMode) {
/* 650 */     set(Pragma.READ_UNCOMMITED, useReadUncommitedIsolationMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableRecursiveTriggers(boolean enable) {
/* 659 */     set(Pragma.RECURSIVE_TRIGGERS, enable);
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
/*     */   public void enableReverseUnorderedSelects(boolean enable) {
/* 671 */     set(Pragma.REVERSE_UNORDERED_SELECTS, enable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableShortColumnNames(boolean enable) {
/* 681 */     set(Pragma.SHORT_COLUMN_NAMES, enable);
/*     */   }
/*     */   
/*     */   public enum SynchronousMode implements PragmaValue {
/* 685 */     OFF, NORMAL, FULL;
/*     */     
/*     */     public String getValue() {
/* 688 */       return name();
/*     */     }
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
/*     */   
/*     */   public void setSynchronous(SynchronousMode mode) {
/* 706 */     setPragma(Pragma.SYNCHRONOUS, mode.name());
/*     */   }
/*     */   
/*     */   public enum TempStore implements PragmaValue {
/* 710 */     DEFAULT, FILE, MEMORY;
/*     */     
/*     */     public String getValue() {
/* 713 */       return name();
/*     */     }
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
/*     */   public void setHexKeyMode(HexKeyMode mode) {
/* 726 */     setPragma(Pragma.HEXKEY_MODE, mode.name());
/*     */   }
/*     */   
/*     */   public enum HexKeyMode implements PragmaValue {
/* 730 */     NONE, SSE, SQLCIPHER;
/*     */     
/*     */     public String getValue() {
/* 733 */       return name();
/*     */     }
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
/*     */   public void setTempStore(TempStore storeType) {
/* 750 */     setPragma(Pragma.TEMP_STORE, storeType.name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTempStoreDirectory(String directoryName) {
/* 760 */     setPragma(Pragma.TEMP_STORE_DIRECTORY, String.format("'%s'", new Object[] { directoryName }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUserVersion(int version) {
/* 771 */     set(Pragma.USER_VERSION, version);
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
/*     */   public void setApplicationId(int id) {
/* 784 */     set(Pragma.APPLICATION_ID, id);
/*     */   }
/*     */ 
/*     */   
/*     */   public enum TransactionMode
/*     */     implements PragmaValue
/*     */   {
/* 791 */     DEFFERED,
/* 792 */     DEFERRED, IMMEDIATE, EXCLUSIVE;
/*     */     
/*     */     public String getValue() {
/* 795 */       return name();
/*     */     }
/*     */     
/*     */     public static TransactionMode getMode(String mode) {
/* 799 */       if ("DEFFERED".equalsIgnoreCase(mode)) {
/* 800 */         return DEFERRED;
/*     */       }
/* 802 */       return valueOf(mode.toUpperCase());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionMode(TransactionMode transactionMode) {
/* 812 */     this.defaultConnectionConfig.setTransactionMode(transactionMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionMode(String transactionMode) {
/* 821 */     setTransactionMode(TransactionMode.getMode(transactionMode));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionMode getTransactionMode() {
/* 828 */     return this.defaultConnectionConfig.getTransactionMode();
/*     */   }
/*     */   
/*     */   public enum DatePrecision implements PragmaValue {
/* 832 */     SECONDS, MILLISECONDS;
/*     */     
/*     */     public String getValue() {
/* 835 */       return name();
/*     */     }
/*     */     
/*     */     public static DatePrecision getPrecision(String precision) {
/* 839 */       return valueOf(precision.toUpperCase());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDatePrecision(String datePrecision) throws SQLException {
/* 848 */     this.defaultConnectionConfig.setDatePrecision(DatePrecision.getPrecision(datePrecision));
/*     */   }
/*     */   
/*     */   public enum DateClass implements PragmaValue {
/* 852 */     INTEGER, TEXT, REAL;
/*     */     
/*     */     public String getValue() {
/* 855 */       return name();
/*     */     }
/*     */     
/*     */     public static DateClass getDateClass(String dateClass) {
/* 859 */       return valueOf(dateClass.toUpperCase());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDateClass(String dateClass) {
/* 867 */     this.defaultConnectionConfig.setDateClass(DateClass.getDateClass(dateClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDateStringFormat(String dateStringFormat) {
/* 875 */     this.defaultConnectionConfig.setDateStringFormat(dateStringFormat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBusyTimeout(int milliseconds) {
/* 882 */     setPragma(Pragma.BUSY_TIMEOUT, Integer.toString(milliseconds));
/*     */   }
/*     */   
/*     */   public int getBusyTimeout() {
/* 886 */     return this.busyTimeout;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlite\SQLiteConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */