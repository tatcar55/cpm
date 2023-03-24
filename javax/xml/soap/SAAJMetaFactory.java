/*    */ package javax.xml.soap;
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
/*    */ public abstract class SAAJMetaFactory
/*    */ {
/*    */   private static final String META_FACTORY_CLASS_PROPERTY = "javax.xml.soap.MetaFactory";
/*    */   private static final String DEFAULT_META_FACTORY_CLASS = "com.sun.xml.internal.messaging.saaj.soap.SAAJMetaFactoryImpl";
/*    */   
/*    */   static SAAJMetaFactory getInstance() throws SOAPException {
/*    */     try {
/* 88 */       SAAJMetaFactory instance = (SAAJMetaFactory)FactoryFinder.find("javax.xml.soap.MetaFactory", "com.sun.xml.internal.messaging.saaj.soap.SAAJMetaFactoryImpl");
/*    */ 
/*    */ 
/*    */       
/* 92 */       return instance;
/* 93 */     } catch (Exception e) {
/* 94 */       throw new SOAPException("Unable to create SAAJ meta-factory" + e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract MessageFactory newMessageFactory(String paramString) throws SOAPException;
/*    */   
/*    */   protected abstract SOAPFactory newSOAPFactory(String paramString) throws SOAPException;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\soap\SAAJMetaFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */