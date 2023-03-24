/*    */ package com.sun.xml.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlElement;
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
/*    */ final class XmlElementQuick
/*    */   extends Quick
/*    */   implements XmlElement
/*    */ {
/*    */   private final XmlElement core;
/*    */   
/*    */   public XmlElementQuick(Locatable upstream, XmlElement core) {
/* 54 */     super(upstream);
/* 55 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 59 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 63 */     return new XmlElementQuick(upstream, (XmlElement)core);
/*    */   }
/*    */   
/*    */   public Class<XmlElement> annotationType() {
/* 67 */     return XmlElement.class;
/*    */   }
/*    */   
/*    */   public String name() {
/* 71 */     return this.core.name();
/*    */   }
/*    */   
/*    */   public Class type() {
/* 75 */     return this.core.type();
/*    */   }
/*    */   
/*    */   public String namespace() {
/* 79 */     return this.core.namespace();
/*    */   }
/*    */   
/*    */   public String defaultValue() {
/* 83 */     return this.core.defaultValue();
/*    */   }
/*    */   
/*    */   public boolean required() {
/* 87 */     return this.core.required();
/*    */   }
/*    */   
/*    */   public boolean nillable() {
/* 91 */     return this.core.nillable();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\XmlElementQuick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */