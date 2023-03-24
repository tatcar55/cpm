/*    */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*    */ 
/*    */ import com.sun.xml.wss.saml.OneTimeUse;
/*    */ import com.sun.xml.wss.saml.SAMLException;
/*    */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.OneTimeUseType;
/*    */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
/*    */ import java.util.logging.Logger;
/*    */ import javax.xml.bind.JAXBContext;
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
/*    */ public class OneTimeUse
/*    */   extends OneTimeUseType
/*    */   implements OneTimeUse
/*    */ {
/* 66 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static OneTimeUseType fromElement(Element element) throws SAMLException {
/*    */     try {
/* 85 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*    */       
/* 87 */       Unmarshaller u = jc.createUnmarshaller();
/* 88 */       return (OneTimeUseType)u.unmarshal(element);
/* 89 */     } catch (Exception ex) {
/* 90 */       throw new SAMLException(ex.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\OneTimeUse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */