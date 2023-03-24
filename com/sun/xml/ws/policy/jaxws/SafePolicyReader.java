/*     */ package com.sun.xml.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.ws.api.policy.ModelUnmarshaller;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import com.sun.xml.ws.resources.PolicyMessages;
/*     */ import java.io.StringReader;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class SafePolicyReader
/*     */ {
/*  67 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(SafePolicyReader.class);
/*     */ 
/*     */   
/*  70 */   private final Set<String> urlsRead = new HashSet<String>();
/*     */   
/*  72 */   private final Set<String> qualifiedPolicyUris = new HashSet<String>();
/*     */ 
/*     */   
/*     */   public final class PolicyRecord
/*     */   {
/*     */     PolicyRecord next;
/*     */     
/*     */     PolicySourceModel policyModel;
/*     */     
/*     */     Set<String> unresolvedURIs;
/*     */     
/*     */     private String uri;
/*     */     
/*     */     PolicyRecord insert(PolicyRecord insertedRec) {
/*  86 */       if (null == insertedRec.unresolvedURIs || insertedRec.unresolvedURIs.isEmpty()) {
/*  87 */         insertedRec.next = this;
/*  88 */         return insertedRec;
/*     */       } 
/*  90 */       PolicyRecord head = this;
/*  91 */       PolicyRecord oneBeforeCurrent = null;
/*     */       PolicyRecord current;
/*  93 */       for (current = head; null != current.next; ) {
/*  94 */         if (null != current.unresolvedURIs && current.unresolvedURIs.contains(insertedRec.uri)) {
/*  95 */           if (null == oneBeforeCurrent) {
/*  96 */             insertedRec.next = current;
/*  97 */             return insertedRec;
/*     */           } 
/*  99 */           oneBeforeCurrent.next = insertedRec;
/* 100 */           insertedRec.next = current;
/* 101 */           return head;
/*     */         } 
/*     */         
/* 104 */         if (insertedRec.unresolvedURIs.remove(current.uri) && insertedRec.unresolvedURIs.isEmpty()) {
/* 105 */           insertedRec.next = current.next;
/* 106 */           current.next = insertedRec;
/* 107 */           return head;
/*     */         } 
/* 109 */         oneBeforeCurrent = current;
/* 110 */         current = current.next;
/*     */       } 
/* 112 */       insertedRec.next = null;
/* 113 */       current.next = insertedRec;
/* 114 */       return head;
/*     */     }
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
/*     */     public void setUri(String uri, String id) throws PolicyException {
/* 127 */       if (SafePolicyReader.this.qualifiedPolicyUris.contains(uri)) {
/* 128 */         throw (PolicyException)SafePolicyReader.LOGGER.logSevereException(new PolicyException(PolicyMessages.WSP_1020_DUPLICATE_ID(id)));
/*     */       }
/* 130 */       this.uri = uri;
/* 131 */       SafePolicyReader.this.qualifiedPolicyUris.add(uri);
/*     */     }
/*     */     
/*     */     public String getUri() {
/* 135 */       return this.uri;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 140 */       String result = this.uri;
/* 141 */       if (null != this.next) {
/* 142 */         result = result + "->" + this.next.toString();
/*     */       }
/* 144 */       return result;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyRecord readPolicyElement(XMLStreamReader reader, String baseUrl) {
/* 161 */     if (null == reader || !reader.isStartElement()) {
/* 162 */       return null;
/*     */     }
/* 164 */     StringBuffer elementCode = new StringBuffer();
/* 165 */     PolicyRecord policyRec = new PolicyRecord();
/* 166 */     QName elementName = reader.getName();
/*     */     
/* 168 */     int depth = 0; try {
/*     */       do {
/*     */         boolean insidePolicyReferenceAttr; QName curName; StringBuffer xmlnsCode; Set<String> tmpNsSet; int attrCount; StringBuffer attrCode; int i;
/* 171 */         switch (reader.getEventType()) {
/*     */           case 1:
/* 173 */             curName = reader.getName();
/* 174 */             insidePolicyReferenceAttr = (NamespaceVersion.resolveAsToken(curName) == XmlToken.PolicyReference);
/* 175 */             if (elementName.equals(curName)) {
/* 176 */               depth++;
/*     */             }
/* 178 */             xmlnsCode = new StringBuffer();
/* 179 */             tmpNsSet = new HashSet<String>();
/* 180 */             if (null == curName.getPrefix() || "".equals(curName.getPrefix())) {
/* 181 */               elementCode.append('<').append(curName.getLocalPart());
/*     */ 
/*     */               
/* 184 */               xmlnsCode.append(" xmlns=\"").append(curName.getNamespaceURI()).append('"');
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */               
/* 190 */               elementCode.append('<').append(curName.getPrefix()).append(':').append(curName.getLocalPart());
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 195 */               xmlnsCode.append(" xmlns:").append(curName.getPrefix()).append("=\"").append(curName.getNamespaceURI()).append('"');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 201 */               tmpNsSet.add(curName.getPrefix());
/*     */             } 
/* 203 */             attrCount = reader.getAttributeCount();
/* 204 */             attrCode = new StringBuffer();
/* 205 */             for (i = 0; i < attrCount; i++) {
/* 206 */               boolean uriAttrFlg = false;
/* 207 */               if (insidePolicyReferenceAttr && "URI".equals(reader.getAttributeName(i).getLocalPart())) {
/*     */                 
/* 209 */                 uriAttrFlg = true;
/* 210 */                 if (null == policyRec.unresolvedURIs) {
/* 211 */                   policyRec.unresolvedURIs = new HashSet<String>();
/*     */                 }
/* 213 */                 policyRec.unresolvedURIs.add(relativeToAbsoluteUrl(reader.getAttributeValue(i), baseUrl));
/*     */               } 
/*     */               
/* 216 */               if (!"xmlns".equals(reader.getAttributePrefix(i)) || !tmpNsSet.contains(reader.getAttributeLocalName(i)))
/*     */               {
/*     */                 
/* 219 */                 if (null == reader.getAttributePrefix(i) || "".equals(reader.getAttributePrefix(i))) {
/* 220 */                   attrCode.append(' ').append(reader.getAttributeLocalName(i)).append("=\"").append(uriAttrFlg ? relativeToAbsoluteUrl(reader.getAttributeValue(i), baseUrl) : reader.getAttributeValue(i)).append('"');
/*     */ 
/*     */                 
/*     */                 }
/*     */                 else {
/*     */ 
/*     */                   
/* 227 */                   attrCode.append(' ').append(reader.getAttributePrefix(i)).append(':').append(reader.getAttributeLocalName(i)).append("=\"").append(uriAttrFlg ? relativeToAbsoluteUrl(reader.getAttributeValue(i), baseUrl) : reader.getAttributeValue(i)).append('"');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 235 */                   if (!tmpNsSet.contains(reader.getAttributePrefix(i))) {
/* 236 */                     xmlnsCode.append(" xmlns:").append(reader.getAttributePrefix(i)).append("=\"").append(reader.getAttributeNamespace(i)).append('"');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 242 */                     tmpNsSet.add(reader.getAttributePrefix(i));
/*     */                   } 
/*     */                 }  } 
/*     */             } 
/* 246 */             elementCode.append(xmlnsCode).append(attrCode).append('>');
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 2:
/* 256 */             curName = reader.getName();
/* 257 */             if (elementName.equals(curName)) {
/* 258 */               depth--;
/*     */             }
/* 260 */             elementCode.append("</").append("".equals(curName.getPrefix()) ? "" : (curName.getPrefix() + ':')).append(curName.getLocalPart()).append('>');
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 4:
/* 267 */             elementCode.append(reader.getText());
/*     */             break;
/*     */           case 12:
/* 270 */             elementCode.append("<![CDATA[").append(reader.getText()).append("]]>");
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 280 */         if (!reader.hasNext() || depth <= 0)
/* 281 */           continue;  reader.next();
/*     */       }
/* 283 */       while (8 != reader.getEventType() && depth > 0);
/* 284 */       policyRec.policyModel = ModelUnmarshaller.getUnmarshaller().unmarshalModel(new StringReader(elementCode.toString()));
/*     */       
/* 286 */       if (null != policyRec.policyModel.getPolicyId()) {
/* 287 */         policyRec.setUri(baseUrl + "#" + policyRec.policyModel.getPolicyId(), policyRec.policyModel.getPolicyId());
/* 288 */       } else if (policyRec.policyModel.getPolicyName() != null) {
/* 289 */         policyRec.setUri(policyRec.policyModel.getPolicyName(), policyRec.policyModel.getPolicyName());
/*     */       } 
/* 291 */     } catch (Exception e) {
/* 292 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1013_EXCEPTION_WHEN_READING_POLICY_ELEMENT(elementCode.toString()), e));
/*     */     } 
/* 294 */     this.urlsRead.add(baseUrl);
/* 295 */     return policyRec;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getUrlsRead() {
/* 300 */     return this.urlsRead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String readPolicyReferenceElement(XMLStreamReader reader) {
/*     */     try {
/* 312 */       if (NamespaceVersion.resolveAsToken(reader.getName()) == XmlToken.PolicyReference) {
/* 313 */         for (int i = 0; i < reader.getAttributeCount(); i++) {
/* 314 */           if (XmlToken.resolveToken(reader.getAttributeName(i).getLocalPart()) == XmlToken.Uri) {
/* 315 */             String uriValue = reader.getAttributeValue(i);
/* 316 */             reader.next();
/* 317 */             return uriValue;
/*     */           } 
/*     */         } 
/*     */       }
/* 321 */       reader.next();
/* 322 */       return null;
/* 323 */     } catch (XMLStreamException e) {
/* 324 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1001_XML_EXCEPTION_WHEN_PROCESSING_POLICY_REFERENCE(), e));
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
/*     */ 
/*     */   
/*     */   public static String relativeToAbsoluteUrl(String relativeUri, String baseUri) {
/* 340 */     if ('#' != relativeUri.charAt(0)) {
/* 341 */       return relativeUri;
/*     */     }
/* 343 */     return (null == baseUri) ? relativeUri : (baseUri + relativeUri);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\SafePolicyReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */