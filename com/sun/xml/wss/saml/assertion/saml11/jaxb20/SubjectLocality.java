/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.SubjectLocality;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.SubjectLocalityType;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubjectLocality
/*     */   extends SubjectLocalityType
/*     */   implements SubjectLocality
/*     */ {
/*  64 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectLocality() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SubjectLocalityType fromElement(Element element) throws SAMLException {
/*     */     try {
/*  89 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  91 */       Unmarshaller u = jc.createUnmarshaller();
/*  92 */       return (SubjectLocalityType)u.unmarshal(element);
/*  93 */     } catch (Exception ex) {
/*  94 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectLocality(String ipAddress, String dnsAddress) {
/* 108 */     setIPAddress(ipAddress);
/* 109 */     setDNSAddress(dnsAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDNSAddress() {
/* 114 */     return super.getDNSAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIPAddress() {
/* 119 */     return super.getIPAddress();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\SubjectLocality.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */