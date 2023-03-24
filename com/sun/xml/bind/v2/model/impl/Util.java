/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationSource;
/*     */ import com.sun.xml.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.bind.v2.runtime.IllegalAnnotationException;
/*     */ import javax.activation.MimeType;
/*     */ import javax.activation.MimeTypeParseException;
/*     */ import javax.xml.bind.annotation.XmlMimeType;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlSchemaTypes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Util
/*     */ {
/*     */   static <T, C, F, M> QName calcSchemaType(AnnotationReader<T, C, F, M> reader, AnnotationSource primarySource, C enclosingClass, T individualType, Locatable src) {
/*  65 */     XmlSchemaType xst = (XmlSchemaType)primarySource.readAnnotation(XmlSchemaType.class);
/*  66 */     if (xst != null) {
/*  67 */       return new QName(xst.namespace(), xst.name());
/*     */     }
/*     */ 
/*     */     
/*  71 */     XmlSchemaTypes xsts = (XmlSchemaTypes)reader.getPackageAnnotation(XmlSchemaTypes.class, enclosingClass, src);
/*  72 */     XmlSchemaType[] values = null;
/*  73 */     if (xsts != null) {
/*  74 */       values = xsts.value();
/*     */     } else {
/*  76 */       xst = (XmlSchemaType)reader.getPackageAnnotation(XmlSchemaType.class, enclosingClass, src);
/*  77 */       if (xst != null) {
/*  78 */         values = new XmlSchemaType[1];
/*  79 */         values[0] = xst;
/*     */       } 
/*     */     } 
/*  82 */     if (values != null) {
/*  83 */       for (XmlSchemaType item : values) {
/*  84 */         if (reader.getClassValue(item, "type").equals(individualType)) {
/*  85 */           return new QName(item.namespace(), item.name());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   static MimeType calcExpectedMediaType(AnnotationSource primarySource, ModelBuilder builder) {
/*  95 */     XmlMimeType xmt = (XmlMimeType)primarySource.readAnnotation(XmlMimeType.class);
/*  96 */     if (xmt == null) {
/*  97 */       return null;
/*     */     }
/*     */     try {
/* 100 */       return new MimeType(xmt.value());
/* 101 */     } catch (MimeTypeParseException e) {
/* 102 */       builder.reportError(new IllegalAnnotationException(Messages.ILLEGAL_MIME_TYPE.format(new Object[] { xmt.value(), e.getMessage() }, ), xmt));
/*     */ 
/*     */ 
/*     */       
/* 106 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */