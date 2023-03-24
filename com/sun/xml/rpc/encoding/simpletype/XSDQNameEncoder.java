/*     */ package com.sun.xml.rpc.encoding.simpletype;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
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
/*     */ public class XSDQNameEncoder
/*     */   extends SimpleTypeEncoderBase
/*     */ {
/*  41 */   private static final SimpleTypeEncoder encoder = new XSDQNameEncoder();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleTypeEncoder getInstance() {
/*  47 */     return encoder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/*  53 */     if (obj == null) {
/*  54 */       return null;
/*     */     }
/*  56 */     QName qn = (QName)obj;
/*  57 */     String str = "";
/*     */     
/*  59 */     String nsURI = qn.getNamespaceURI();
/*  60 */     if (nsURI != null && nsURI.length() > 0) {
/*  61 */       String prefix = writer.getPrefix(nsURI);
/*  62 */       if (prefix == null) {
/*  63 */         prefix = writer.getPrefixFactory().getPrefix(nsURI);
/*     */       }
/*  65 */       str = str + prefix + ":";
/*     */     } 
/*     */     
/*  68 */     str = str + qn.getLocalPart();
/*     */     
/*  70 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/*  76 */     if (str == null) {
/*  77 */       return null;
/*     */     }
/*  79 */     String uri = "";
/*  80 */     str = EncoderUtils.collapseWhitespace(str);
/*  81 */     String prefix = XmlUtil.getPrefix(str);
/*  82 */     if (prefix != null) {
/*  83 */       uri = reader.getURI(prefix);
/*  84 */       if (uri == null) {
/*  85 */         throw new DeserializationException("xsd.unknownPrefix", prefix);
/*     */       }
/*     */     } 
/*     */     
/*  89 */     String localPart = XmlUtil.getLocalPart(str);
/*     */     
/*  91 */     return new QName(uri, localPart);
/*     */   }
/*     */   
/*     */   public void writeValue(Object obj, XMLWriter writer) throws Exception {
/*  95 */     writer.writeCharsUnquoted(objectToString(obj, writer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAdditionalNamespaceDeclarations(Object obj, XMLWriter writer) throws Exception {
/* 103 */     QName value = (QName)obj;
/* 104 */     if (value != null) {
/* 105 */       String uri = value.getNamespaceURI();
/* 106 */       if (!uri.equals("") && 
/* 107 */         writer.getPrefix(uri) == null)
/* 108 */         writer.writeNamespaceDeclaration(uri); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDQNameEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */