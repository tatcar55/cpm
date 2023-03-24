/*    */ package com.sun.xml.ws.tx.coord.common.types;
/*    */ 
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseIdentifier<I>
/*    */ {
/*    */   protected I delegate;
/*    */   
/*    */   protected BaseIdentifier(I delegate) {
/* 67 */     this.delegate = delegate;
/*    */   }
/*    */   
/*    */   public I getDelegate() {
/* 71 */     return this.delegate;
/*    */   }
/*    */   
/*    */   public abstract String getValue();
/*    */   
/*    */   public abstract void setValue(String paramString);
/*    */   
/*    */   public abstract Map<QName, String> getOtherAttributes();
/*    */   
/*    */   public abstract QName getQName();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\types\BaseIdentifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */