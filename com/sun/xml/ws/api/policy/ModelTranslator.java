/*    */ package com.sun.xml.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.ws.config.management.policy.ManagementAssertionCreator;
/*    */ import com.sun.xml.ws.policy.PolicyException;
/*    */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*    */ import com.sun.xml.ws.policy.sourcemodel.PolicyModelTranslator;
/*    */ import com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
/*    */ import com.sun.xml.ws.resources.ManagementMessages;
/*    */ import java.util.Arrays;
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
/*    */ public class ModelTranslator
/*    */   extends PolicyModelTranslator
/*    */ {
/* 61 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(ModelTranslator.class);
/*    */   
/* 63 */   private static final PolicyAssertionCreator[] JAXWS_ASSERTION_CREATORS = new PolicyAssertionCreator[] { (PolicyAssertionCreator)new ManagementAssertionCreator() };
/*    */   
/*    */   private static final ModelTranslator translator;
/*    */   
/*    */   private static final PolicyException creationException;
/*    */ 
/*    */   
/*    */   static {
/* 71 */     ModelTranslator tempTranslator = null;
/* 72 */     PolicyException tempException = null;
/*    */     try {
/* 74 */       tempTranslator = new ModelTranslator();
/* 75 */     } catch (PolicyException e) {
/* 76 */       tempException = e;
/* 77 */       LOGGER.warning(ManagementMessages.WSM_1007_FAILED_MODEL_TRANSLATOR_INSTANTIATION(), (Throwable)e);
/*    */     } finally {
/* 79 */       translator = tempTranslator;
/* 80 */       creationException = tempException;
/*    */     } 
/*    */   }
/*    */   
/*    */   private ModelTranslator() throws PolicyException {
/* 85 */     super(Arrays.asList(JAXWS_ASSERTION_CREATORS));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ModelTranslator getTranslator() throws PolicyException {
/* 95 */     if (creationException != null) {
/* 96 */       throw (PolicyException)LOGGER.logSevereException(creationException);
/*    */     }
/*    */     
/* 99 */     return translator;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\policy\ModelTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */