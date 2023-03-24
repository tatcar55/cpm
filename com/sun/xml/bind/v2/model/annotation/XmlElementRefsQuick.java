/*    */ package com.sun.xml.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlElementRef;
/*    */ import javax.xml.bind.annotation.XmlElementRefs;
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
/*    */ final class XmlElementRefsQuick
/*    */   extends Quick
/*    */   implements XmlElementRefs
/*    */ {
/*    */   private final XmlElementRefs core;
/*    */   
/*    */   public XmlElementRefsQuick(Locatable upstream, XmlElementRefs core) {
/* 55 */     super(upstream);
/* 56 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 60 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 64 */     return new XmlElementRefsQuick(upstream, (XmlElementRefs)core);
/*    */   }
/*    */   
/*    */   public Class<XmlElementRefs> annotationType() {
/* 68 */     return XmlElementRefs.class;
/*    */   }
/*    */   
/*    */   public XmlElementRef[] value() {
/* 72 */     return this.core.value();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\XmlElementRefsQuick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */