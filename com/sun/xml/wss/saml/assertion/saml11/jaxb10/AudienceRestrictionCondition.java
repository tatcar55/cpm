/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.AudienceRestrictionCondition;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AudienceRestrictionConditionImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AudienceRestrictionConditionTypeImpl;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class AudienceRestrictionCondition
/*     */   extends AudienceRestrictionConditionImpl
/*     */   implements AudienceRestrictionCondition
/*     */ {
/*  68 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setAudience(List audience) {
/*  74 */     this._Audience = new ListImpl(audience);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudienceRestrictionCondition(List audience) {
/*  85 */     setAudience(audience);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static AudienceRestrictionConditionTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/* 102 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 104 */       Unmarshaller u = jc.createUnmarshaller();
/* 105 */       return (AudienceRestrictionConditionTypeImpl)u.unmarshal(element);
/* 106 */     } catch (Exception ex) {
/* 107 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   public List<String> getAudience() {
/* 111 */     List base = super.getAudience();
/* 112 */     List<String> ret = new ArrayList<String>();
/* 113 */     for (Object obj : base) {
/* 114 */       ret.add((String)obj);
/*     */     }
/* 116 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\AudienceRestrictionCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */