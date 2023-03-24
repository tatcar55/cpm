/*     */ package com.sun.xml.fastinfoset.stax;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import java.util.HashMap;
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
/*     */ public class StAXManager
/*     */ {
/*     */   protected static final String STAX_NOTATIONS = "javax.xml.stream.notations";
/*     */   protected static final String STAX_ENTITIES = "javax.xml.stream.entities";
/*  29 */   HashMap features = new HashMap<Object, Object>();
/*     */   
/*     */   public static final int CONTEXT_READER = 1;
/*     */   
/*     */   public static final int CONTEXT_WRITER = 2;
/*     */ 
/*     */   
/*     */   public StAXManager() {}
/*     */ 
/*     */   
/*     */   public StAXManager(int context) {
/*  40 */     switch (context) {
/*     */       case 1:
/*  42 */         initConfigurableReaderProperties();
/*     */         break;
/*     */       
/*     */       case 2:
/*  46 */         initWriterProps();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXManager(StAXManager manager) {
/*  54 */     HashMap properties = manager.getProperties();
/*  55 */     this.features.putAll(properties);
/*     */   }
/*     */   
/*     */   private HashMap getProperties() {
/*  59 */     return this.features;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initConfigurableReaderProperties() {
/*  64 */     this.features.put("javax.xml.stream.isNamespaceAware", Boolean.TRUE);
/*  65 */     this.features.put("javax.xml.stream.isValidating", Boolean.FALSE);
/*  66 */     this.features.put("javax.xml.stream.isReplacingEntityReferences", Boolean.TRUE);
/*  67 */     this.features.put("javax.xml.stream.isSupportingExternalEntities", Boolean.TRUE);
/*  68 */     this.features.put("javax.xml.stream.isCoalescing", Boolean.FALSE);
/*  69 */     this.features.put("javax.xml.stream.supportDTD", Boolean.FALSE);
/*  70 */     this.features.put("javax.xml.stream.reporter", (Object)null);
/*  71 */     this.features.put("javax.xml.stream.resolver", (Object)null);
/*  72 */     this.features.put("javax.xml.stream.allocator", (Object)null);
/*  73 */     this.features.put("javax.xml.stream.notations", (Object)null);
/*     */   }
/*     */   
/*     */   private void initWriterProps() {
/*  77 */     this.features.put("javax.xml.stream.isRepairingNamespaces", Boolean.FALSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsProperty(String property) {
/*  86 */     return this.features.containsKey(property);
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) {
/*  90 */     checkProperty(name);
/*  91 */     return this.features.get(name);
/*     */   }
/*     */   
/*     */   public void setProperty(String name, Object value) {
/*  95 */     checkProperty(name);
/*  96 */     if (name.equals("javax.xml.stream.isValidating") && Boolean.TRUE.equals(value))
/*     */     {
/*  98 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.validationNotSupported") + CommonResourceBundle.getInstance().getString("support_validation"));
/*     */     }
/* 100 */     if (name.equals("javax.xml.stream.isSupportingExternalEntities") && Boolean.TRUE.equals(value))
/*     */     {
/* 102 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.externalEntities") + CommonResourceBundle.getInstance().getString("resolve_external_entities_"));
/*     */     }
/*     */     
/* 105 */     this.features.put(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkProperty(String name) {
/* 110 */     if (!this.features.containsKey(name))
/* 111 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.propertyNotSupported", new Object[] { name })); 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 115 */     return this.features.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\StAXManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */