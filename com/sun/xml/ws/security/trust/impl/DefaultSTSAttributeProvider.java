/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.STSAttributeProvider;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import com.sun.xml.wss.saml.AssertionUtil;
/*     */ import com.sun.xml.wss.saml.Attribute;
/*     */ import com.sun.xml.wss.saml.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.AuthenticationStatement;
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.util.SAMLUtil;
/*     */ import java.security.Principal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ public class DefaultSTSAttributeProvider
/*     */   implements STSAttributeProvider
/*     */ {
/*     */   public Map<QName, List<String>> getClaimedAttributes(Subject subject, String appliesTo, String tokenType, Claims claims) {
/*  70 */     Set<Principal> principals = subject.getPrincipals();
/*  71 */     Map<QName, List<String>> attrs = new HashMap<QName, List<String>>();
/*  72 */     if (principals != null && !principals.isEmpty()) {
/*  73 */       Iterator<Principal> iterator = principals.iterator();
/*  74 */       while (iterator.hasNext()) {
/*  75 */         String name = ((Principal)principals.iterator().next()).getName();
/*  76 */         if (name != null) {
/*  77 */           List<String> nameIds = new ArrayList<String>();
/*  78 */           nameIds.add(name);
/*  79 */           attrs.put(new QName("http://sun.com", "NameID"), nameIds);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/*  85 */       Set<Object> set = subject.getPublicCredentials();
/*  86 */       Element samlAssertion = null;
/*     */       try {
/*  88 */         Iterator i$ = set.iterator(); if (i$.hasNext()) { Object obj = i$.next();
/*  89 */           if (obj instanceof XMLStreamReader) {
/*  90 */             XMLStreamReader reader = (XMLStreamReader)obj;
/*     */             
/*  92 */             samlAssertion = SAMLUtil.createSAMLAssertion(reader);
/*  93 */           } else if (obj instanceof Element) {
/*  94 */             samlAssertion = (Element)obj;
/*     */           }  }
/*     */ 
/*     */         
/*  98 */         if (samlAssertion != null) {
/*  99 */           addAttributes(samlAssertion, attrs, false);
/*     */         }
/* 101 */       } catch (Exception ex) {
/* 102 */         throw new RuntimeException(ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 107 */     if ("true".equals(claims.getOtherAttributes().get(new QName("ActAs")))) {
/*     */ 
/*     */ 
/*     */       
/* 111 */       Element token = null;
/* 112 */       for (Object obj : claims.getSupportingProperties()) {
/* 113 */         if (obj instanceof Subject) {
/* 114 */           token = ((Subject)obj).getPublicCredentials().iterator().next();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       try {
/* 120 */         if (token != null) {
/* 121 */           addAttributes(token, attrs, true);
/*     */         }
/* 123 */       } catch (Exception ex) {
/* 124 */         throw new RuntimeException(ex);
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     if (attrs.size() < 2) {
/*     */       
/* 130 */       QName key = new QName("http://sun.com", "token-requestor");
/* 131 */       List<String> tokenRequestor = new ArrayList<String>();
/* 132 */       tokenRequestor.add("authenticated");
/* 133 */       attrs.put(key, tokenRequestor);
/*     */     } 
/*     */     
/* 136 */     return attrs;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addAttributes(Element token, Map<QName, List<String>> attrs, boolean isActAs) throws SAMLException {
/* 141 */     String name = null;
/* 142 */     String nameNS = null;
/* 143 */     String tokenName = token.getLocalName();
/* 144 */     if ("UsernameToken".equals(tokenName)) {
/*     */       
/* 146 */       name = token.getElementsByTagNameNS("*", "Username").item(0).getFirstChild().getNodeValue();
/* 147 */     } else if ("Assertion".equals(tokenName)) {
/*     */       
/* 149 */       Assertion assertion = AssertionUtil.fromElement(token);
/*     */       
/* 151 */       Subject subject = null;
/* 152 */       NameID nameID = null;
/*     */ 
/*     */       
/*     */       try {
/* 156 */         subject = assertion.getSubject();
/* 157 */       } catch (Exception ex) {
/* 158 */         subject = null;
/*     */       } 
/*     */       
/* 161 */       if (subject != null) {
/* 162 */         nameID = subject.getNameId();
/*     */       }
/*     */       
/* 165 */       List<Object> statements = assertion.getStatements();
/* 166 */       for (Object s : statements) {
/* 167 */         if (s instanceof AttributeStatement) {
/* 168 */           List<Attribute> samlAttrs = ((AttributeStatement)s).getAttributes();
/* 169 */           for (Attribute samlAttr : samlAttrs) {
/* 170 */             String attrName = samlAttr.getName();
/* 171 */             String attrNS = samlAttr.getNameFormat();
/* 172 */             List<Object> samlAttrValues = samlAttr.getAttributes();
/* 173 */             List<String> attrValues = new ArrayList<String>();
/* 174 */             for (Object samlAttrValue : samlAttrValues) {
/* 175 */               attrValues.add(((Element)samlAttrValue).getFirstChild().getNodeValue());
/*     */             }
/* 177 */             attrs.put(new QName(attrNS, attrName), attrValues);
/*     */           } 
/*     */ 
/*     */           
/* 181 */           if (subject == null)
/* 182 */             subject = ((AttributeStatement)s).getSubject();  continue;
/*     */         } 
/* 184 */         if (s instanceof AuthenticationStatement) {
/* 185 */           subject = ((AuthenticationStatement)s).getSubject();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 190 */       if (nameID != null) {
/*     */         
/* 192 */         name = nameID.getValue();
/* 193 */         nameNS = nameID.getNameQualifier();
/*     */       } else {
/*     */         
/* 196 */         NameIdentifier nameIdentifier = subject.getNameIdentifier();
/* 197 */         if (nameIdentifier != null) {
/* 198 */           name = nameIdentifier.getValue();
/* 199 */           nameNS = nameIdentifier.getNameQualifier();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 204 */     String idName = isActAs ? "ActAs" : "NameID";
/* 205 */     List<String> nameIds = new ArrayList<String>();
/* 206 */     if (name != null) {
/* 207 */       nameIds.add(name);
/*     */     }
/* 209 */     attrs.put(new QName(nameNS, idName), nameIds);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\DefaultSTSAttributeProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */