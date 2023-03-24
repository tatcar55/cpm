/*    */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*    */ 
/*    */ import com.sun.xml.wss.saml.DoNotCacheCondition;
/*    */ import com.sun.xml.wss.saml.SAMLException;
/*    */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DoNotCacheConditionImpl;
/*    */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DoNotCacheConditionTypeImpl;
/*    */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
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
/*    */ public class DoNotCacheCondition
/*    */   extends DoNotCacheConditionImpl
/*    */   implements DoNotCacheCondition
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
/*    */   public static DoNotCacheConditionTypeImpl fromElement(Element element) throws SAMLException {
/*    */     try {
/* 85 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*    */       
/* 87 */       Unmarshaller u = jc.createUnmarshaller();
/* 88 */       return (DoNotCacheConditionTypeImpl)u.unmarshal(element);
/* 89 */     } catch (Exception ex) {
/* 90 */       throw new SAMLException(ex.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\DoNotCacheCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */