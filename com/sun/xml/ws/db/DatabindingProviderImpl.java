/*     */ package com.sun.xml.ws.db;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.Databinding;
/*     */ import com.oracle.webservices.api.databinding.WSDLGenerator;
/*     */ import com.oracle.webservices.api.databinding.WSDLResolver;
/*     */ import com.sun.xml.ws.api.databinding.Databinding;
/*     */ import com.sun.xml.ws.api.databinding.DatabindingConfig;
/*     */ import com.sun.xml.ws.api.databinding.WSDLGenInfo;
/*     */ import com.sun.xml.ws.spi.db.DatabindingProvider;
/*     */ import java.io.File;
/*     */ import java.util.Map;
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
/*     */ public class DatabindingProviderImpl
/*     */   implements DatabindingProvider
/*     */ {
/*     */   private static final String CachedDatabinding = "com.sun.xml.ws.db.DatabindingProviderImpl";
/*     */   Map<String, Object> properties;
/*     */   
/*     */   public void init(Map<String, Object> p) {
/*  63 */     this.properties = p;
/*     */   }
/*     */   
/*     */   DatabindingImpl getCachedDatabindingImpl(DatabindingConfig config) {
/*  67 */     Object object = config.properties().get("com.sun.xml.ws.db.DatabindingProviderImpl");
/*  68 */     return (object != null && object instanceof DatabindingImpl) ? (DatabindingImpl)object : null;
/*     */   }
/*     */   
/*     */   public Databinding create(DatabindingConfig config) {
/*  72 */     DatabindingImpl impl = getCachedDatabindingImpl(config);
/*  73 */     if (impl == null) {
/*  74 */       impl = new DatabindingImpl(this, config);
/*  75 */       config.properties().put("com.sun.xml.ws.db.DatabindingProviderImpl", impl);
/*     */     } 
/*  77 */     return impl;
/*     */   }
/*     */   
/*     */   public WSDLGenerator wsdlGen(DatabindingConfig config) {
/*  81 */     DatabindingImpl impl = (DatabindingImpl)create(config);
/*  82 */     return new JaxwsWsdlGen(impl);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFor(String databindingMode) {
/*  87 */     return true;
/*     */   }
/*     */   
/*     */   public static class JaxwsWsdlGen implements WSDLGenerator {
/*     */     DatabindingImpl databinding;
/*     */     WSDLGenInfo wsdlGenInfo;
/*     */     
/*     */     JaxwsWsdlGen(DatabindingImpl impl) {
/*  95 */       this.databinding = impl;
/*  96 */       this.wsdlGenInfo = new WSDLGenInfo();
/*     */     }
/*     */     
/*     */     public WSDLGenerator inlineSchema(boolean inline) {
/* 100 */       this.wsdlGenInfo.setInlineSchemas(inline);
/* 101 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public WSDLGenerator property(String name, Object value) {
/* 106 */       return this;
/*     */     }
/*     */     
/*     */     public void generate(WSDLResolver wsdlResolver) {
/* 110 */       this.wsdlGenInfo.setWsdlResolver(wsdlResolver);
/* 111 */       this.databinding.generateWSDL(this.wsdlGenInfo);
/*     */     }
/*     */ 
/*     */     
/*     */     public void generate(File outputDir, String name) {
/* 116 */       this.databinding.generateWSDL(this.wsdlGenInfo);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\db\DatabindingProviderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */