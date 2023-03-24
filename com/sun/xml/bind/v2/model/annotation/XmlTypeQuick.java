/*    */ package com.sun.xml.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class XmlTypeQuick
/*    */   extends Quick
/*    */   implements XmlType
/*    */ {
/*    */   private final XmlType core;
/*    */   
/*    */   public XmlTypeQuick(Locatable upstream, XmlType core) {
/* 54 */     super(upstream);
/* 55 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 59 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 63 */     return new XmlTypeQuick(upstream, (XmlType)core);
/*    */   }
/*    */   
/*    */   public Class<XmlType> annotationType() {
/* 67 */     return XmlType.class;
/*    */   }
/*    */   
/*    */   public String name() {
/* 71 */     return this.core.name();
/*    */   }
/*    */   
/*    */   public String namespace() {
/* 75 */     return this.core.namespace();
/*    */   }
/*    */   
/*    */   public String[] propOrder() {
/* 79 */     return this.core.propOrder();
/*    */   }
/*    */   
/*    */   public Class factoryClass() {
/* 83 */     return this.core.factoryClass();
/*    */   }
/*    */   
/*    */   public String factoryMethod() {
/* 87 */     return this.core.factoryMethod();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\XmlTypeQuick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */