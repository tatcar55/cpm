/*     */ package org.sqlite;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.sqlite.core.DB;
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
/*     */ public class ExtendedCommand
/*     */ {
/*     */   public static SQLExtension parse(String sql) throws SQLException {
/*  40 */     if (sql == null) {
/*  41 */       return null;
/*     */     }
/*  43 */     if (sql.startsWith("backup"))
/*  44 */       return BackupCommand.parse(sql); 
/*  45 */     if (sql.startsWith("restore")) {
/*  46 */       return RestoreCommand.parse(sql);
/*     */     }
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeQuotation(String s) {
/*  57 */     if (s == null) {
/*  58 */       return s;
/*     */     }
/*  60 */     if ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))) {
/*  61 */       return s.substring(1, s.length() - 1);
/*     */     }
/*  63 */     return s;
/*     */   }
/*     */   
/*     */   public static interface SQLExtension
/*     */   {
/*     */     void execute(DB param1DB) throws SQLException;
/*     */   }
/*     */   
/*     */   public static class BackupCommand
/*     */     implements SQLExtension {
/*     */     public final String srcDB;
/*     */     public final String destFile;
/*     */     
/*     */     public BackupCommand(String srcDB, String destFile) {
/*  77 */       this.srcDB = srcDB;
/*  78 */       this.destFile = destFile;
/*     */     }
/*     */ 
/*     */     
/*  82 */     private static Pattern backupCmd = Pattern.compile("backup(\\s+(\"[^\"]*\"|'[^']*'|\\S+))?\\s+to\\s+(\"[^\"]*\"|'[^']*'|\\S+)");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static BackupCommand parse(String sql) throws SQLException {
/*  91 */       if (sql != null) {
/*  92 */         Matcher m = backupCmd.matcher(sql);
/*  93 */         if (m.matches()) {
/*  94 */           String dbName = ExtendedCommand.removeQuotation(m.group(2));
/*  95 */           String dest = ExtendedCommand.removeQuotation(m.group(3));
/*  96 */           if (dbName == null || dbName.length() == 0) {
/*  97 */             dbName = "main";
/*     */           }
/*  99 */           return new BackupCommand(dbName, dest);
/*     */         } 
/*     */       } 
/* 102 */       throw new SQLException("syntax error: " + sql);
/*     */     }
/*     */     
/*     */     public void execute(DB db) throws SQLException {
/* 106 */       db.backup(this.srcDB, this.destFile, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class RestoreCommand
/*     */     implements SQLExtension
/*     */   {
/*     */     public final String targetDB;
/*     */     public final String srcFile;
/* 116 */     private static Pattern restoreCmd = Pattern.compile("restore(\\s+(\"[^\"]*\"|'[^']*'|\\S+))?\\s+from\\s+(\"[^\"]*\"|'[^']*'|\\S+)");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RestoreCommand(String targetDB, String srcFile) {
/* 124 */       this.targetDB = targetDB;
/* 125 */       this.srcFile = srcFile;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static RestoreCommand parse(String sql) throws SQLException {
/* 135 */       if (sql != null) {
/* 136 */         Matcher m = restoreCmd.matcher(sql);
/* 137 */         if (m.matches()) {
/* 138 */           String dbName = ExtendedCommand.removeQuotation(m.group(2));
/* 139 */           String dest = ExtendedCommand.removeQuotation(m.group(3));
/* 140 */           if (dbName == null || dbName.length() == 0)
/* 141 */             dbName = "main"; 
/* 142 */           return new RestoreCommand(dbName, dest);
/*     */         } 
/*     */       } 
/* 145 */       throw new SQLException("syntax error: " + sql);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void execute(DB db) throws SQLException {
/* 152 */       db.restore(this.targetDB, this.srcFile, null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlite\ExtendedCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */