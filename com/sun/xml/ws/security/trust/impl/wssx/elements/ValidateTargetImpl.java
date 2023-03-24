/*    */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*    */ 
/*    */ import com.sun.xml.ws.security.Token;
/*    */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*    */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*    */ import com.sun.xml.ws.security.trust.elements.ValidateTarget;
/*    */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*    */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ValidateTargetType;
/*    */ import javax.xml.bind.JAXBElement;
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
/*    */ public class ValidateTargetImpl
/*    */   extends ValidateTargetType
/*    */   implements ValidateTarget
/*    */ {
/* 57 */   private SecurityTokenReference str = null;
/*    */   
/*    */   public ValidateTargetImpl(Token token) {
/* 60 */     Element element = (Element)token.getTokenValue();
/* 61 */     setAny(element);
/*    */   }
/*    */   
/*    */   public ValidateTargetImpl(ValidateTargetType vtType) {
/* 65 */     Object vt = vtType.getAny();
/* 66 */     if (vt != null) {
/* 67 */       setAny(vt);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setSecurityTokenReference(SecurityTokenReference ref) {
/* 72 */     this.str = ref;
/* 73 */     if (ref != null) {
/* 74 */       JAXBElement<SecurityTokenReferenceType> strElement = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)ref);
/*    */       
/* 76 */       setAny(strElement);
/*    */     } 
/*    */   }
/*    */   
/*    */   public SecurityTokenReference getSecurityTokenReference() {
/* 81 */     return this.str;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\ValidateTargetImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */