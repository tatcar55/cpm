/*    */ package org.glassfish.gmbal.generic;
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
/*    */ public class Holder<T>
/*    */ {
/*    */   private transient T _content;
/*    */   
/*    */   public Holder(T content) {
/* 53 */     this._content = content;
/*    */   }
/*    */ 
/*    */   
/*    */   public Holder() {
/* 58 */     this(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public T content() {
/* 63 */     return this._content;
/*    */   }
/*    */ 
/*    */   
/*    */   public void content(T content) {
/* 68 */     this._content = content;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 74 */     if (!(obj instanceof Holder)) {
/* 75 */       return false;
/*    */     }
/*    */     
/* 78 */     Holder other = Holder.class.cast(obj);
/*    */     
/* 80 */     return this._content.equals(other.content());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 86 */     return this._content.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 92 */     return "Holder[" + this._content + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\Holder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */