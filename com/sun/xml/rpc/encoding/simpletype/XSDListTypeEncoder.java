/*     */ package com.sun.xml.rpc.encoding.simpletype;
/*     */ 
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class XSDListTypeEncoder
/*     */   extends SimpleTypeEncoderBase
/*     */ {
/*  41 */   private SimpleTypeEncoder encoder = null;
/*  42 */   private Class typeClass = null;
/*     */   
/*     */   protected XSDListTypeEncoder(SimpleTypeEncoder encoder, Class typeClass) {
/*  45 */     this.encoder = encoder;
/*  46 */     this.typeClass = typeClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleTypeEncoder getInstance(SimpleTypeEncoder itemEnc, Class typeClass) {
/*  55 */     return new XSDListTypeEncoder(itemEnc, typeClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/*  61 */     if (null == obj) {
/*  62 */       return null;
/*     */     }
/*  64 */     if (!obj.getClass().isArray()) {
/*  65 */       throw new IllegalArgumentException();
/*     */     }
/*  67 */     StringBuffer ret = new StringBuffer();
/*     */     
/*  69 */     int len = Array.getLength(obj);
/*  70 */     if (len == 0) {
/*  71 */       return "";
/*     */     }
/*  73 */     for (int i = 0; i < len; i++) {
/*  74 */       ret.append(Array.get(obj, i));
/*  75 */       if (i + 1 < len)
/*  76 */         ret.append(' '); 
/*     */     } 
/*  78 */     return ret.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/*  84 */     if (str == null)
/*  85 */       return null; 
/*  86 */     StringTokenizer in = new StringTokenizer(str.trim(), " ");
/*  87 */     Object objArray = Array.newInstance(this.typeClass, in.countTokens());
/*  88 */     if (in.countTokens() == 0)
/*  89 */       return objArray; 
/*  90 */     int i = 0;
/*  91 */     while (in.hasMoreTokens()) {
/*  92 */       Array.set(objArray, i++, this.encoder.stringToObject(in.nextToken(), reader));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  97 */     return objArray;
/*     */   }
/*     */   
/*     */   public void writeValue(Object obj, XMLWriter writer) throws Exception {
/* 101 */     writer.writeCharsUnquoted(objectToString(obj, writer));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDListTypeEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */