/*    */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*    */ 
/*    */ import com.sun.xml.security.core.dsig.ObjectType;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlTransient;
/*    */ import javax.xml.crypto.dsig.XMLObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLObject
/*    */   extends ObjectType
/*    */   implements XMLObject
/*    */ {
/*    */   @XmlTransient
/* 61 */   private List content1 = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFeatureSupported(String string) {
/* 69 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List getContent() {
/* 75 */     return this.content1;
/*    */   }
/*    */   
/*    */   public void setContent(List content) {
/* 79 */     this.content1 = content;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\XMLObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */