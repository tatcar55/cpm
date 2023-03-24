/*     */ package com.sun.xml.ws.security.trust.impl.ic;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.impl.WSTrustContractImpl;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.w3c.dom.Document;
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
/*     */ public class ICContractImpl
/*     */   extends WSTrustContractImpl
/*     */ {
/*     */   protected void handleExtension(BaseSTSRequest request, BaseSTSResponse response, IssuedTokenContext context) throws WSTrustException {
/*  68 */     Map<QName, List<String>> claimedAttributes = (Map<QName, List<String>>)context.getOtherProperties().get("cliamedAttributes");
/*  69 */     handleDisplayToken((RequestSecurityToken)request, (RequestSecurityTokenResponse)response, claimedAttributes);
/*     */   }
/*     */   
/*     */   private void handleDisplayToken(RequestSecurityToken rst, RequestSecurityTokenResponse rstr, Map<QName, List<String>> claimedAttrs) throws WSTrustException {
/*  73 */     List<Object> list = rst.getExtensionElements();
/*  74 */     boolean displayToken = false;
/*  75 */     for (int i = 0; i < list.size(); i++) {
/*  76 */       Object ele = list.get(i);
/*  77 */       if (ele instanceof Element) {
/*  78 */         String localName = ((Element)ele).getLocalName();
/*  79 */         if ("RequestDisplayToken".equals(localName)) {
/*  80 */           displayToken = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  85 */     if (displayToken)
/*     */       
/*     */       try {
/*  88 */         DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*  89 */         dbf.setNamespaceAware(true);
/*  90 */         DocumentBuilder builder = dbf.newDocumentBuilder();
/*  91 */         Document doc = builder.newDocument();
/*  92 */         Element rdt = doc.createElementNS("http://schemas.xmlsoap.org/ws/2005/05/identity", "RequestedDisplayToken");
/*  93 */         rdt.setAttribute("xmlns", "http://schemas.xmlsoap.org/ws/2005/05/identity");
/*  94 */         Element dt = doc.createElementNS("http://schemas.xmlsoap.org/ws/2005/05/identity", "DisplayToken");
/*  95 */         dt.setAttribute("xml:lang", "en-us");
/*  96 */         rdt.appendChild(dt);
/*  97 */         Set<Map.Entry<QName, List<String>>> entries = claimedAttrs.entrySet();
/*  98 */         for (Map.Entry<QName, List<String>> entry : entries) {
/*  99 */           QName attrKey = entry.getKey();
/* 100 */           List<String> values = entry.getValue();
/* 101 */           if (values != null && values.size() > 0 && 
/* 102 */             !"NameID".equals(attrKey.getLocalPart())) {
/* 103 */             Element dc = doc.createElementNS("http://schemas.xmlsoap.org/ws/2005/05/identity", "DisplayClaim");
/* 104 */             dc.setAttribute("xmlns", "http://schemas.xmlsoap.org/ws/2005/05/identity");
/* 105 */             String uri = attrKey.getNamespaceURI() + "/" + attrKey.getLocalPart();
/* 106 */             dc.setAttribute("Uri", uri);
/* 107 */             dt.appendChild(dc);
/* 108 */             Element dtg = doc.createElementNS("http://schemas.xmlsoap.org/ws/2005/05/identity", "DisplayTag");
/* 109 */             dtg.appendChild(doc.createTextNode(attrKey.getLocalPart()));
/* 110 */             dc.appendChild(dtg);
/*     */             
/* 112 */             String displayValue = values.get(0);
/* 113 */             Element dtv = doc.createElementNS("http://schemas.xmlsoap.org/ws/2005/05/identity", "DisplayValue");
/*     */ 
/*     */ 
/*     */             
/* 117 */             dtv.appendChild(doc.createTextNode(displayValue));
/* 118 */             dc.appendChild(dtv);
/*     */           } 
/*     */         } 
/*     */         
/* 122 */         rstr.getAny().add(rdt);
/* 123 */       } catch (Exception ex) {
/* 124 */         throw new WSTrustException(ex.getMessage(), ex);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\ic\ICContractImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */