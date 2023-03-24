/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.Attribute;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeTypeImpl;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
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
/*     */ public class Attribute
/*     */   extends AttributeImpl
/*     */   implements Attribute
/*     */ {
/*  67 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
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
/*     */   public static AttributeTypeImpl fromElement(Element element) throws SAMLException {
/*     */     try {
/*  81 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/*  83 */       Unmarshaller u = jc.createUnmarshaller();
/*  84 */       return (AttributeTypeImpl)u.unmarshal(element);
/*  85 */     } catch (Exception ex) {
/*  86 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setAttributeValue(List values) {
/*  91 */     Iterator it = values.iterator();
/*  92 */     List<AnyType> typeList = new LinkedList();
/*  93 */     while (it.hasNext()) {
/*  94 */       List tmpList = new LinkedList();
/*  95 */       tmpList.add(it.next());
/*  96 */       AnyType type = new AnyType();
/*  97 */       type.setContent(tmpList);
/*  98 */       typeList.add(type);
/*     */     } 
/* 100 */     this._AttributeValue = new ListImpl(typeList);
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
/*     */   public Attribute(String name, String nameSpace, List values) {
/* 116 */     setAttributeName(name);
/* 117 */     setAttributeNamespace(nameSpace);
/* 118 */     setAttributeValue(values);
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
/*     */   
/*     */   public Attribute(AttributeType attType) {
/* 135 */     setAttributeName(attType.getAttributeName());
/* 136 */     setAttributeNamespace(attType.getAttributeNamespace());
/* 137 */     setAttributeValue(attType.getAttributeValue());
/*     */   }
/*     */   
/*     */   public List<Object> getAttributes() {
/* 141 */     return getAttributeValue();
/*     */   }
/*     */   
/*     */   public String getFriendlyName() {
/* 145 */     return null;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 149 */     return getAttributeName();
/*     */   }
/*     */   
/*     */   public String getNameFormat() {
/* 153 */     return getAttributeNamespace();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\Attribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */