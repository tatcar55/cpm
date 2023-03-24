/*    */ package com.sun.xml.rpc.encoding.simpletype;
/*    */ 
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import com.sun.xml.rpc.streaming.XMLWriter;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.ListIterator;
/*    */ import java.util.StringTokenizer;
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
/*    */ public class XSDListEncoder
/*    */   extends SimpleTypeEncoderBase
/*    */ {
/* 43 */   private static final SimpleTypeEncoder encoder = new XSDListEncoder();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleTypeEncoder getInstance() {
/* 49 */     return encoder;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String objectToString(Object obj, XMLWriter writer) throws Exception {
/* 55 */     if (null == obj) {
/* 56 */       return null;
/*    */     }
/* 58 */     if (!(obj instanceof List)) {
/* 59 */       throw new IllegalArgumentException();
/*    */     }
/* 61 */     if (((List)obj).isEmpty()) {
/* 62 */       return new String();
/*    */     }
/* 64 */     ListIterator li = ((List)obj).listIterator();
/* 65 */     StringBuffer result = new StringBuffer();
/* 66 */     while (li.hasNext()) {
/* 67 */       result.append(li.next());
/* 68 */       result.append(' ');
/*    */     } 
/* 70 */     return result.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object stringToObject(String str, XMLReader reader) throws Exception {
/* 76 */     if (str == null)
/* 77 */       return null; 
/* 78 */     ArrayList<String> list = new ArrayList();
/* 79 */     StringTokenizer in = new StringTokenizer(str.trim(), " ");
/* 80 */     while (in.hasMoreTokens()) {
/* 81 */       list.add(in.nextToken());
/*    */     }
/* 83 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\XSDListEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */