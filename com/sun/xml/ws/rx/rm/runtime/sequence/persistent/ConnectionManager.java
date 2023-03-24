/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence.persistent;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Level;
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
/*     */ final class ConnectionManager
/*     */ {
/*  59 */   private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class);
/*     */   
/*     */   private final DataSourceProvider dataSourceProvider;
/*     */   
/*     */   public static ConnectionManager getInstance(DataSourceProvider dataSourceProvider) {
/*  64 */     return new ConnectionManager(dataSourceProvider);
/*     */   }
/*     */   
/*     */   private ConnectionManager(DataSourceProvider dataSourceProvider) {
/*  68 */     this.dataSourceProvider = dataSourceProvider;
/*     */   }
/*     */   
/*     */   Connection getConnection(boolean autoCommit) throws PersistenceException {
/*     */     try {
/*  73 */       Connection connection = this.dataSourceProvider.getDataSource().getConnection();
/*     */ 
/*     */       
/*  76 */       connection.setAutoCommit(autoCommit);
/*  77 */       return connection;
/*  78 */     } catch (SQLException ex) {
/*  79 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException("Unable to setup required JDBC connection parameters", ex));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   PreparedStatement prepareStatement(Connection sqlConnection, String sqlStatement) throws SQLException {
/*  85 */     LOGGER.finer(String.format("Preparing SQL statement:\n%s", new Object[] { sqlStatement }));
/*     */     
/*  87 */     return sqlConnection.prepareStatement(sqlStatement, 1004, 1007);
/*     */   }
/*     */   
/*     */   void recycle(ResultSet... resources) {
/*  91 */     for (ResultSet resource : resources) {
/*  92 */       if (resource != null) {
/*     */         try {
/*  94 */           resource.close();
/*  95 */         } catch (SQLException ex) {
/*  96 */           LOGGER.logException(ex, Level.WARNING);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void recycle(PreparedStatement... resources) {
/* 103 */     for (PreparedStatement resource : resources) {
/* 104 */       if (resource != null) {
/*     */         try {
/* 106 */           resource.close();
/* 107 */         } catch (SQLException ex) {
/* 108 */           LOGGER.logException(ex, Level.WARNING);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void recycle(Connection connection) {
/* 115 */     if (connection != null) {
/*     */       try {
/* 117 */         connection.close();
/* 118 */       } catch (SQLException ex) {
/* 119 */         LOGGER.logException(ex, Level.WARNING);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void rollback(Connection sqlConnection) {
/*     */     try {
/* 127 */       sqlConnection.rollback();
/* 128 */     } catch (SQLException ex) {
/* 129 */       LOGGER.warning("Unexpected exception occured while performing transaction rollback", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   void commit(Connection sqlConnection) throws PersistenceException {
/*     */     try {
/* 135 */       sqlConnection.commit();
/* 136 */     } catch (SQLException ex) {
/* 137 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException("Unexpected exception occured while performing transaction commit", ex));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\persistent\ConnectionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */