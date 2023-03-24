/*    */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*    */ 
/*    */ import com.sun.xml.ws.api.security.trust.Claims;
/*    */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*    */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*    */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*    */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ClaimsType;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.Unmarshaller;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClaimsImpl
/*    */   extends ClaimsType
/*    */   implements Claims
/*    */ {
/* 68 */   List<Object> supportingInfo = new ArrayList();
/*    */ 
/*    */   
/*    */   public ClaimsImpl() {}
/*    */ 
/*    */   
/*    */   public ClaimsImpl(String dialect) {
/* 75 */     setDialect(dialect);
/*    */   }
/*    */   
/*    */   public ClaimsImpl(ClaimsType clType) throws WSTrustException {
/* 79 */     setDialect(clType.getDialect());
/* 80 */     getAny().addAll(clType.getAny());
/* 81 */     getOtherAttributes().putAll(clType.getOtherAttributes());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ClaimsType fromElement(Element element) throws WSTrustException {
/*    */     try {
/* 87 */       Unmarshaller unmarshaller = WSTrustElementFactory.getContext(WSTrustVersion.WS_TRUST_13).createUnmarshaller();
/* 88 */       return ((JAXBElement<ClaimsType>)unmarshaller.unmarshal(element)).getValue();
/* 89 */     } catch (Exception ex) {
/* 90 */       throw new WSTrustException(ex.getMessage(), ex);
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<Object> getSupportingProperties() {
/* 95 */     return this.supportingInfo;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\ClaimsImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */