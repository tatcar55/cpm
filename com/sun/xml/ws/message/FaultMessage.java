/*    */ package com.sun.xml.ws.message;
/*    */ 
/*    */ import com.sun.istack.Nullable;
/*    */ import com.sun.xml.ws.api.message.FilterMessageImpl;
/*    */ import com.sun.xml.ws.api.message.Message;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FaultMessage
/*    */   extends FilterMessageImpl
/*    */ {
/*    */   @Nullable
/*    */   private final QName detailEntryName;
/*    */   
/*    */   public FaultMessage(Message delegate, @Nullable QName detailEntryName) {
/* 62 */     super(delegate);
/* 63 */     this.detailEntryName = detailEntryName;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public QName getFirstDetailEntryName() {
/* 68 */     return this.detailEntryName;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\FaultMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */