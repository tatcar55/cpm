/*    */ package com.sun.xml.ws.mex.client;
/*    */ 
/*    */ import com.sun.xml.ws.api.wsdl.parser.MetaDataResolver;
/*    */ import com.sun.xml.ws.api.wsdl.parser.MetadataResolverFactory;
/*    */ import org.xml.sax.EntityResolver;
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
/*    */ public class MetadataResolverFactoryImpl
/*    */   extends MetadataResolverFactory
/*    */ {
/*    */   public MetaDataResolver metadataResolver(EntityResolver resolver) {
/* 57 */     return new MetadataResolverImpl();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\mex\client\MetadataResolverFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */