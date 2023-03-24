/*    */ package com.sun.xml.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlNs;
/*    */ import javax.xml.bind.annotation.XmlNsForm;
/*    */ import javax.xml.bind.annotation.XmlSchema;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class XmlSchemaQuick
/*    */   extends Quick
/*    */   implements XmlSchema
/*    */ {
/*    */   private final XmlSchema core;
/*    */   
/*    */   public XmlSchemaQuick(Locatable upstream, XmlSchema core) {
/* 56 */     super(upstream);
/* 57 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 61 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 65 */     return new XmlSchemaQuick(upstream, (XmlSchema)core);
/*    */   }
/*    */   
/*    */   public Class<XmlSchema> annotationType() {
/* 69 */     return XmlSchema.class;
/*    */   }
/*    */   
/*    */   public String location() {
/* 73 */     return this.core.location();
/*    */   }
/*    */   
/*    */   public String namespace() {
/* 77 */     return this.core.namespace();
/*    */   }
/*    */   
/*    */   public XmlNs[] xmlns() {
/* 81 */     return this.core.xmlns();
/*    */   }
/*    */   
/*    */   public XmlNsForm elementFormDefault() {
/* 85 */     return this.core.elementFormDefault();
/*    */   }
/*    */   
/*    */   public XmlNsForm attributeFormDefault() {
/* 89 */     return this.core.attributeFormDefault();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\XmlSchemaQuick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */