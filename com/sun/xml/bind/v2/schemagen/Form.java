/*     */ package com.sun.xml.bind.v2.schemagen;
/*     */ 
/*     */ import com.sun.xml.bind.v2.schemagen.xmlschema.LocalAttribute;
/*     */ import com.sun.xml.bind.v2.schemagen.xmlschema.LocalElement;
/*     */ import com.sun.xml.bind.v2.schemagen.xmlschema.Schema;
/*     */ import com.sun.xml.txw2.TypedXmlWriter;
/*     */ import javax.xml.bind.annotation.XmlNsForm;
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ 
/*     */ enum Form
/*     */ {
/*  57 */   QUALIFIED(XmlNsForm.QUALIFIED, true) {
/*     */     void declare(String attName, Schema schema) {
/*  59 */       schema._attribute(attName, "qualified");
/*     */     }
/*     */   },
/*  62 */   UNQUALIFIED(XmlNsForm.UNQUALIFIED, false)
/*     */   {
/*     */     void declare(String attName, Schema schema)
/*     */     {
/*  66 */       schema._attribute(attName, "unqualified");
/*     */     }
/*     */   },
/*  69 */   UNSET(XmlNsForm.UNSET, false)
/*     */   {
/*     */     void declare(String attName, Schema schema) {}
/*     */   };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final XmlNsForm xnf;
/*     */ 
/*     */   
/*     */   public final boolean isEffectivelyQualified;
/*     */ 
/*     */ 
/*     */   
/*     */   Form(XmlNsForm xnf, boolean effectivelyQualified) {
/*  85 */     this.xnf = xnf;
/*  86 */     this.isEffectivelyQualified = effectivelyQualified;
/*     */   }
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
/*     */   public void writeForm(LocalElement e, QName tagName) {
/*  99 */     _writeForm((TypedXmlWriter)e, tagName);
/*     */   }
/*     */   
/*     */   public void writeForm(LocalAttribute a, QName tagName) {
/* 103 */     _writeForm((TypedXmlWriter)a, tagName);
/*     */   }
/*     */   
/*     */   private void _writeForm(TypedXmlWriter e, QName tagName) {
/* 107 */     boolean qualified = (tagName.getNamespaceURI().length() > 0);
/*     */     
/* 109 */     if (qualified && this != QUALIFIED) {
/* 110 */       e._attribute("form", "qualified");
/*     */     }
/* 112 */     else if (!qualified && this == QUALIFIED) {
/* 113 */       e._attribute("form", "unqualified");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Form get(XmlNsForm xnf) {
/* 120 */     for (Form v : values()) {
/* 121 */       if (v.xnf == xnf)
/* 122 */         return v; 
/*     */     } 
/* 124 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   abstract void declare(String paramString, Schema paramSchema);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\Form.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */