/*    */ package com.sun.xml.ws.wsdl;
/*    */ 
/*    */ import com.sun.istack.NotNull;
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
/*    */ public class ActionBasedOperationSignature
/*    */ {
/*    */   private final String action;
/*    */   private final QName payloadQName;
/*    */   
/*    */   public ActionBasedOperationSignature(@NotNull String action, @NotNull QName payloadQName) {
/* 57 */     this.action = action;
/* 58 */     this.payloadQName = payloadQName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 63 */     if (this == o) return true; 
/* 64 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 66 */     ActionBasedOperationSignature that = (ActionBasedOperationSignature)o;
/*    */     
/* 68 */     if (!this.action.equals(that.action)) return false; 
/* 69 */     if (!this.payloadQName.equals(that.payloadQName)) return false;
/*    */     
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 76 */     int result = this.action.hashCode();
/* 77 */     result = 31 * result + this.payloadQName.hashCode();
/* 78 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\ActionBasedOperationSignature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */