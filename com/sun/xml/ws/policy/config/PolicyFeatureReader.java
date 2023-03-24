/*    */ package com.sun.xml.ws.policy.config;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.config.metro.dev.FeatureReader;
/*    */ import com.sun.xml.ws.policy.PolicyException;
/*    */ import com.sun.xml.ws.policy.sourcemodel.PolicyModelUnmarshaller;
/*    */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*    */ import javax.xml.stream.XMLEventReader;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ import javax.xml.ws.WebServiceFeature;
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
/*    */ public class PolicyFeatureReader
/*    */   implements FeatureReader
/*    */ {
/* 59 */   private static final Logger LOGGER = Logger.getLogger(PolicyFeatureReader.class);
/*    */ 
/*    */   
/*    */   public PolicyFeature parse(XMLEventReader reader) throws WebServiceException {
/*    */     try {
/* 64 */       PolicyModelUnmarshaller unmarshaller = PolicyModelUnmarshaller.getXmlUnmarshaller();
/* 65 */       PolicySourceModel model = unmarshaller.unmarshalModel(reader);
/* 66 */       return new PolicyFeature(null);
/* 67 */     } catch (PolicyException e) {
/*    */       
/* 69 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to unmarshal policy expression", e));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\config\PolicyFeatureReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */