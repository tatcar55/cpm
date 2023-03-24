/*    */ package com.sun.xml.rpc.encoding;
/*    */ 
/*    */ import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.xml.namespace.QName;
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
/*    */ public class SimpleMultiTypeSerializer
/*    */   extends SimpleTypeSerializer
/*    */ {
/*    */   private Set supportedTypes;
/*    */   
/*    */   public SimpleMultiTypeSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, SimpleTypeEncoder encoder, QName[] types) {
/* 52 */     super(type, encodeType, isNullable, encodingStyle, encoder);
/* 53 */     this.supportedTypes = new HashSet();
/* 54 */     for (int i = 0; i < types.length; i++)
/* 55 */       this.supportedTypes.add(types[i]); 
/*    */   }
/*    */   
/*    */   protected boolean isAcceptableType(QName actualType) {
/* 59 */     return this.supportedTypes.contains(actualType);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SimpleMultiTypeSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */