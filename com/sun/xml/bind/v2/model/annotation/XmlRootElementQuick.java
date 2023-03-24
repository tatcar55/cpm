/*    */ package com.sun.xml.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
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
/*    */ final class XmlRootElementQuick
/*    */   extends Quick
/*    */   implements XmlRootElement
/*    */ {
/*    */   private final XmlRootElement core;
/*    */   
/*    */   public XmlRootElementQuick(Locatable upstream, XmlRootElement core) {
/* 54 */     super(upstream);
/* 55 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 59 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 63 */     return new XmlRootElementQuick(upstream, (XmlRootElement)core);
/*    */   }
/*    */   
/*    */   public Class<XmlRootElement> annotationType() {
/* 67 */     return XmlRootElement.class;
/*    */   }
/*    */   
/*    */   public String name() {
/* 71 */     return this.core.name();
/*    */   }
/*    */   
/*    */   public String namespace() {
/* 75 */     return this.core.namespace();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\XmlRootElementQuick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */