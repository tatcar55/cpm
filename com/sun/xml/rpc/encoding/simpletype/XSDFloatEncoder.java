/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import com.sun.xml.rpc.streaming.XMLWriter;
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
/*    */ public class XSDFloatEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */ {
/* 37 */   private static final SimpleTypeEncoder encoder = new XSDFloatEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleTypeEncoder getInstance() {
/* 43 */     return encoder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/* 49 */     if (obj == null) {
/* 50 */       return null;
/*    */     }
/* 52 */     Float f = (Float)obj;
/* 53 */     float fVal = f.floatValue();
/* 54 */     if (f.isInfinite()) {
/* 55 */       if (fVal == Float.NEGATIVE_INFINITY) {
/* 56 */         return "-INF";
/*    */       }
/* 58 */       return "INF";
/*    */     } 
/* 60 */     if (f.isNaN()) {
/* 61 */       return "NaN";
/*    */     }
/* 63 */     return f.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 69 */     if (str == null) {
/* 70 */       return null;
/*    */     }
/* 72 */     str = EncoderUtils.collapseWhitespace(str);
/* 73 */     if (str.equals("-INF"))
/* 74 */       return new Float(Float.NEGATIVE_INFINITY); 
/* 75 */     if (str.equals("INF"))
/* 76 */       return new Float(Float.POSITIVE_INFINITY); 
/* 77 */     if (str.equals("NaN")) {
/* 78 */       return new Float(Float.NaN);
/*    */     }
/* 80 */     return new Float(str);
/*    */   }
/*    */   
/*    */   public void writeValue(Object obj, XMLWriter writer) throws Exception {
/* 84 */     writer.writeCharsUnquoted(objectToString(obj, writer));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDFloatEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */