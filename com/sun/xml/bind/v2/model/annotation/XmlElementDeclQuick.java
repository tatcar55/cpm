/*    */ package com.sun.xml.bind.v2.model.annotation;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import javax.xml.bind.annotation.XmlElementDecl;
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
/*    */ final class XmlElementDeclQuick
/*    */   extends Quick
/*    */   implements XmlElementDecl
/*    */ {
/*    */   private final XmlElementDecl core;
/*    */   
/*    */   public XmlElementDeclQuick(Locatable upstream, XmlElementDecl core) {
/* 54 */     super(upstream);
/* 55 */     this.core = core;
/*    */   }
/*    */   
/*    */   protected Annotation getAnnotation() {
/* 59 */     return this.core;
/*    */   }
/*    */   
/*    */   protected Quick newInstance(Locatable upstream, Annotation core) {
/* 63 */     return new XmlElementDeclQuick(upstream, (XmlElementDecl)core);
/*    */   }
/*    */   
/*    */   public Class<XmlElementDecl> annotationType() {
/* 67 */     return XmlElementDecl.class;
/*    */   }
/*    */   
/*    */   public String name() {
/* 71 */     return this.core.name();
/*    */   }
/*    */   
/*    */   public Class scope() {
/* 75 */     return this.core.scope();
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
/*    */   public String substitutionHeadNamespace() {
/* 87 */     return this.core.substitutionHeadNamespace();
/*    */   }
/*    */   
/*    */   public String substitutionHeadName() {
/* 91 */     return this.core.substitutionHeadName();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\XmlElementDeclQuick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */