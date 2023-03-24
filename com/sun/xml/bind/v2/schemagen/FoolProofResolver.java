/*    */ package com.sun.xml.bind.v2.schemagen;
/*    */ 
/*    */ import com.sun.xml.bind.Util;
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Logger;
/*    */ import javax.xml.bind.SchemaOutputResolver;
/*    */ import javax.xml.transform.Result;
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
/*    */ final class FoolProofResolver
/*    */   extends SchemaOutputResolver
/*    */ {
/* 60 */   private static final Logger logger = Util.getClassLogger();
/*    */   private final SchemaOutputResolver resolver;
/*    */   
/*    */   public FoolProofResolver(SchemaOutputResolver resolver) {
/* 64 */     assert resolver != null;
/* 65 */     this.resolver = resolver;
/*    */   }
/*    */   
/*    */   public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
/* 69 */     logger.entering(getClass().getName(), "createOutput", new Object[] { namespaceUri, suggestedFileName });
/* 70 */     Result r = this.resolver.createOutput(namespaceUri, suggestedFileName);
/* 71 */     if (r != null) {
/* 72 */       String sysId = r.getSystemId();
/* 73 */       logger.finer("system ID = " + sysId);
/* 74 */       if (sysId == null)
/*    */       {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 83 */         throw new AssertionError("system ID cannot be null"); } 
/*    */     } 
/* 85 */     logger.exiting(getClass().getName(), "createOutput", r);
/* 86 */     return r;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\FoolProofResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */