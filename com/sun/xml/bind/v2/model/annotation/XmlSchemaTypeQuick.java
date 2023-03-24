/*    */ package com.sun.xml.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlSchemaType;
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
/*    */ final class XmlSchemaTypeQuick
/*    */   extends Quick
/*    */   implements XmlSchemaType
/*    */ {
/*    */   private final XmlSchemaType core;
/*    */   
/*    */   public XmlSchemaTypeQuick(Locatable upstream, XmlSchemaType core) {
/* 54 */     super(upstream);
/* 55 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 59 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 63 */     return new XmlSchemaTypeQuick(upstream, (XmlSchemaType)core);
/*    */   }
/*    */   
/*    */   public Class<XmlSchemaType> annotationType() {
/* 67 */     return XmlSchemaType.class;
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
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\XmlSchemaTypeQuick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */