/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
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
/*     */ public class XMLReaderUtil
/*     */ {
/*     */   public static QName getQNameValue(XMLReader reader, QName attributeName) {
/*  51 */     String attribute = reader.getAttributes().getValue(attributeName);
/*  52 */     return (attribute == null) ? null : decodeQName(reader, attribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QName decodeQName(XMLReader reader, String rawName) {
/*  60 */     String prefix = XmlUtil.getPrefix(rawName);
/*  61 */     String local = XmlUtil.getLocalPart(rawName);
/*  62 */     String uri = (prefix == null) ? null : reader.getURI(prefix);
/*  63 */     return new QName(uri, local);
/*     */   }
/*     */   
/*     */   public static void verifyReaderState(XMLReader reader, int expectedState) {
/*  67 */     if (reader.getState() != expectedState) {
/*  68 */       throw new XMLReaderException("xmlreader.unexpectedState", new Object[] { getStateName(expectedState), getLongStateName(reader) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getStateName(XMLReader reader) {
/*  77 */     return getStateName(reader.getState());
/*     */   }
/*     */   public static String getLongStateName(XMLReader reader) {
/*  80 */     int state = reader.getState();
/*  81 */     String name = getStateName(state);
/*  82 */     if (state == 1 || state == 1) {
/*  83 */       name = name + ": " + reader.getName();
/*     */     }
/*  85 */     return name;
/*     */   }
/*     */   public static String getStateName(int state) {
/*  88 */     switch (state) {
/*     */       case 0:
/*  90 */         return "BOF";
/*     */       case 1:
/*  92 */         return "START";
/*     */       case 2:
/*  94 */         return "END";
/*     */       case 3:
/*  96 */         return "CHARS";
/*     */       case 4:
/*  98 */         return "PI";
/*     */       case 5:
/* 100 */         return "EOF";
/*     */     } 
/* 102 */     return "UNKNOWN";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLReaderUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */