/*    */ package com.sun.xml.fastinfoset.algorithm;
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
/*    */ public final class BuiltInEncodingAlgorithmFactory
/*    */ {
/* 25 */   private static final BuiltInEncodingAlgorithm[] table = new BuiltInEncodingAlgorithm[10];
/*    */ 
/*    */   
/* 28 */   public static final HexadecimalEncodingAlgorithm hexadecimalEncodingAlgorithm = new HexadecimalEncodingAlgorithm();
/*    */   
/* 30 */   public static final BASE64EncodingAlgorithm base64EncodingAlgorithm = new BASE64EncodingAlgorithm();
/*    */   
/* 32 */   public static final BooleanEncodingAlgorithm booleanEncodingAlgorithm = new BooleanEncodingAlgorithm();
/*    */   
/* 34 */   public static final ShortEncodingAlgorithm shortEncodingAlgorithm = new ShortEncodingAlgorithm();
/*    */   
/* 36 */   public static final IntEncodingAlgorithm intEncodingAlgorithm = new IntEncodingAlgorithm();
/*    */   
/* 38 */   public static final LongEncodingAlgorithm longEncodingAlgorithm = new LongEncodingAlgorithm();
/*    */   
/* 40 */   public static final FloatEncodingAlgorithm floatEncodingAlgorithm = new FloatEncodingAlgorithm();
/*    */   
/* 42 */   public static final DoubleEncodingAlgorithm doubleEncodingAlgorithm = new DoubleEncodingAlgorithm();
/*    */   
/* 44 */   public static final UUIDEncodingAlgorithm uuidEncodingAlgorithm = new UUIDEncodingAlgorithm();
/*    */   
/*    */   static {
/* 47 */     table[0] = hexadecimalEncodingAlgorithm;
/* 48 */     table[1] = base64EncodingAlgorithm;
/* 49 */     table[2] = shortEncodingAlgorithm;
/* 50 */     table[3] = intEncodingAlgorithm;
/* 51 */     table[4] = longEncodingAlgorithm;
/* 52 */     table[5] = booleanEncodingAlgorithm;
/* 53 */     table[6] = floatEncodingAlgorithm;
/* 54 */     table[7] = doubleEncodingAlgorithm;
/* 55 */     table[8] = uuidEncodingAlgorithm;
/*    */   }
/*    */   
/*    */   public static BuiltInEncodingAlgorithm getAlgorithm(int index) {
/* 59 */     return table[index];
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\algorithm\BuiltInEncodingAlgorithmFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */