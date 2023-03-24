/*     */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
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
/*     */ public class EntityResolverRI
/*     */   implements EntityResolver
/*     */ {
/*     */   public InputSource resolveEntity(String publicId, String systemId) throws IOException {
/*  71 */     String resource = null;
/*  72 */     if (publicId == null) {
/*     */ 
/*     */       
/*  75 */       if (systemId == null || systemId.lastIndexOf('/') == systemId.length())
/*     */       {
/*  77 */         return null;
/*     */       }
/*     */       
/*  80 */       if (systemId.endsWith("j2ee_jaxrpc_mapping_1_1.xsd")) {
/*  81 */         resource = "com/sun/xml/rpc/processor/modeler/j2ee/xml/j2ee_jaxrpc_mapping_1_1.xsd";
/*     */       }
/*  83 */       else if (systemId.endsWith("j2ee_1_4.xsd")) {
/*  84 */         resource = "com/sun/xml/rpc/processor/modeler/j2ee/xml/j2ee_1_4.xsd";
/*     */       }
/*  86 */       else if (systemId.endsWith("j2ee_web_services_client_1_1.xsd")) {
/*  87 */         resource = "com/sun/xml/rpc/processor/modeler/j2ee/xml/j2ee_web_services_client_1_1.xsd";
/*     */       }
/*  89 */       else if (systemId.endsWith("xml.xsd")) {
/*  90 */         resource = "com/sun/xml/rpc/processor/modeler/j2ee/xml/xml.xsd";
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  96 */       if (systemId == null || systemId.lastIndexOf('/') == systemId.length())
/*     */       {
/*  98 */         return null;
/*     */       }
/*     */       
/* 101 */       if (systemId.endsWith("XMLSchema.dtd")) {
/* 102 */         resource = "com/sun/xml/rpc/processor/modeler/j2ee/xml/XMLSchema.dtd";
/*     */       }
/* 104 */       else if (systemId.endsWith("datatypes.dtd")) {
/* 105 */         resource = "com/sun/xml/rpc/processor/modeler/j2ee/xml/datatypes.dtd";
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 110 */     if (resource == null) {
/*     */       
/* 112 */       System.out.println(systemId + " not resolved");
/* 113 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 118 */     InputStream inStrm = getClassLoader().getResourceAsStream(resource);
/*     */     
/* 120 */     if (inStrm == null) {
/* 121 */       System.out.println("unable to locate resource " + resource);
/* 122 */       throw new IOException("unable to locate resource " + resource);
/*     */     } 
/*     */ 
/*     */     
/* 126 */     InputSource is = new InputSource(inStrm);
/* 127 */     is.setSystemId(systemId);
/* 128 */     return is;
/*     */   }
/*     */   
/*     */   private ClassLoader getClassLoader() {
/* 132 */     ClassLoader loader = getClass().getClassLoader();
/*     */ 
/*     */     
/* 135 */     if (loader == null) {
/* 136 */       loader = Thread.currentThread().getContextClassLoader();
/*     */     }
/*     */     
/* 139 */     return loader;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\EntityResolverRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */