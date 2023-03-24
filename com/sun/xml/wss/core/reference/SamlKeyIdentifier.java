/*    */ package com.sun.xml.wss.core.reference;
/*    */ 
/*    */ import com.sun.xml.wss.XWSSecurityException;
/*    */ import javax.xml.soap.SOAPElement;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SamlKeyIdentifier
/*    */   extends KeyIdentifier
/*    */ {
/* 61 */   private String valueType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SamlKeyIdentifier(Document doc) throws XWSSecurityException {
/* 69 */     super(doc);
/*    */     
/* 71 */     String vType = this.valueType;
/* 72 */     NodeList nodeList = doc.getElementsByTagName("Assertion");
/* 73 */     if (nodeList.getLength() > 0) {
/* 74 */       Node assertion = nodeList.item(0);
/* 75 */       if (assertion.getNamespaceURI() == "urn:oasis:names:tc:SAML:2.0:assertion") {
/* 76 */         vType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID";
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 81 */     setAttribute("ValueType", vType);
/*    */   }
/*    */   
/*    */   public SamlKeyIdentifier(SOAPElement element) throws XWSSecurityException {
/* 85 */     super(element);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\reference\SamlKeyIdentifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */