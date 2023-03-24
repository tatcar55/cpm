/*    */ package com.sun.xml.ws.rx.mc.runtime;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.LinkedList;
/*    */ import java.util.Queue;
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
/*    */ class PendingResponseIdentifiers
/*    */   implements Serializable
/*    */ {
/* 56 */   private final Queue<String> messageIdentifiers = new LinkedList<String>();
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 60 */     return this.messageIdentifiers.isEmpty();
/*    */   }
/*    */   
/*    */   public String poll() {
/* 64 */     return this.messageIdentifiers.poll();
/*    */   }
/*    */   
/*    */   public boolean offer(String messageId) {
/* 68 */     return this.messageIdentifiers.offer(messageId);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 73 */     return "PendingResponseIdentifiers{messageIdentifiers=" + this.messageIdentifiers + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\PendingResponseIdentifiers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */