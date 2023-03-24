/*    */ package com.sun.xml.ws.transport.tcp.util;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FrameType
/*    */ {
/*    */   public static final int MESSAGE = 0;
/*    */   public static final int MESSAGE_START_CHUNK = 1;
/*    */   public static final int MESSAGE_CHUNK = 2;
/*    */   public static final int MESSAGE_END_CHUNK = 3;
/*    */   public static final int ERROR = 4;
/*    */   public static final int NULL = 5;
/* 61 */   private static final Set<Integer> typesContainParameters = new HashSet<Integer>(); static {
/* 62 */     typesContainParameters.add(Integer.valueOf(0));
/* 63 */     typesContainParameters.add(Integer.valueOf(1));
/*    */   }
/*    */   
/*    */   public static boolean isFrameContainsParams(int msgId) {
/* 67 */     return typesContainParameters.contains(Integer.valueOf(msgId));
/*    */   }
/*    */   
/*    */   public static boolean isLastFrame(int msgId) {
/* 71 */     return (msgId == 0 || msgId == 3 || msgId == 4 || msgId == 5);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\FrameType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */