/*    */ package com.sun.xml.ws.mex.client;
/*    */ 
/*    */ import com.sun.xml.ws.api.wsdl.parser.MetaDataResolver;
/*    */ import com.sun.xml.ws.api.wsdl.parser.ServiceDescriptor;
/*    */ import com.sun.xml.ws.mex.client.schema.Metadata;
/*    */ import java.net.URI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MetadataResolverImpl
/*    */   extends MetaDataResolver
/*    */ {
/* 56 */   MetadataClient mClient = new MetadataClient();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ServiceDescriptor resolve(URI location) {
/* 67 */     Metadata mData = this.mClient.retrieveMetadata(location.toString());
/* 68 */     if (mData == null) {
/* 69 */       return null;
/*    */     }
/* 71 */     return new ServiceDescriptorImpl(mData);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\mex\client\MetadataResolverImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */