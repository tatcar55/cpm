/*    */ package com.sun.xml.ws.rx.rm.runtime.sequence.persistent;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*    */ import javax.naming.InitialContext;
/*    */ import javax.naming.NamingException;
/*    */ import javax.sql.DataSource;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultDataSourceProvider
/*    */   implements DataSourceProvider
/*    */ {
/*    */   private static final String RM_JDBC_POOL_NAME = "jdbc/ReliableMessagingPool";
/* 60 */   private static final Logger LOGGER = Logger.getLogger(DefaultDataSourceProvider.class);
/*    */   private static synchronized DataSource getDataSource(String jndiName) throws PersistenceException {
/*    */     try {
/*    */       DataSource ds;
/* 64 */       InitialContext ic = new InitialContext();
/* 65 */       Object __ds = ic.lookup(jndiName);
/*    */       
/* 67 */       if (__ds instanceof DataSource) {
/* 68 */         ds = DataSource.class.cast(__ds);
/*    */       } else {
/* 70 */         throw new PersistenceException(LocalizationMessages.WSRM_1154_UNEXPECTED_CLASS_OF_JNDI_BOUND_OBJECT(__ds.getClass().getName(), jndiName, DataSource.class.getName()));
/*    */       } 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 76 */       return ds;
/* 77 */     } catch (NamingException ex) {
/* 78 */       throw (PersistenceException)LOGGER.logSevereException(new PersistenceException(LocalizationMessages.WSRM_1155_RM_JDBC_CONNECTION_POOL_NOT_FOUND(), ex));
/*    */     } 
/*    */   }
/*    */   
/*    */   private final DataSource ds;
/*    */   
/*    */   public DefaultDataSourceProvider() {
/* 85 */     this.ds = getDataSource("jdbc/ReliableMessagingPool");
/*    */   }
/*    */   
/*    */   public DataSource getDataSource() {
/* 89 */     return this.ds;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\persistent\DefaultDataSourceProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */