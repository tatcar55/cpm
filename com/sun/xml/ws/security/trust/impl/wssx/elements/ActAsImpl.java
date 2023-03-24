/*    */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*    */ 
/*    */ import com.sun.xml.ws.security.Token;
/*    */ import com.sun.xml.ws.security.trust.elements.ActAs;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActAsImpl
/*    */   implements ActAs
/*    */ {
/*    */   private Object obj;
/*    */   
/*    */   public ActAsImpl(Token aaToken) {
/* 56 */     this.obj = aaToken.getTokenValue();
/*    */   }
/*    */   
/*    */   public ActAsImpl(Element actAsElement) {
/* 60 */     this.obj = actAsElement.getElementsByTagName("*").item(0);
/*    */   }
/*    */   
/*    */   public void setAny(Object obj) {
/* 64 */     this.obj = obj;
/*    */   }
/*    */   
/*    */   public Object getAny() {
/* 68 */     return this.obj;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\ActAsImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */