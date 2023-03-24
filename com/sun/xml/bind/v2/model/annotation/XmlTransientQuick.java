/*    */ package com.sun.xml.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlTransient;
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
/*    */ final class XmlTransientQuick
/*    */   extends Quick
/*    */   implements XmlTransient
/*    */ {
/*    */   private final XmlTransient core;
/*    */   
/*    */   public XmlTransientQuick(Locatable upstream, XmlTransient core) {
/* 54 */     super(upstream);
/* 55 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 59 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 63 */     return new XmlTransientQuick(upstream, (XmlTransient)core);
/*    */   }
/*    */   
/*    */   public Class<XmlTransient> annotationType() {
/* 67 */     return XmlTransient.class;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\XmlTransientQuick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */