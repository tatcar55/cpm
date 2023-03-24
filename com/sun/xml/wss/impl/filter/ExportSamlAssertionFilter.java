/*     */ package com.sun.xml.wss.impl.filter;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.stax.StreamWriterBufferCreator;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.SSEData;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.opt.impl.message.GSHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.WSSElementFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.SecurityHeader;
/*     */ import com.sun.xml.wss.core.SecurityHeaderBlock;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
/*     */ import com.sun.xml.wss.impl.keyinfo.KeyIdentifierStrategy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.Assertion;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.Assertion;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Assertion;
/*     */ import java.util.HashMap;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class ExportSamlAssertionFilter
/*     */ {
/*     */   private static XMLStreamReader reader;
/*     */   private static MutableXMLStreamBuffer buffer;
/*     */   private static String id;
/*     */   private static String version;
/*     */   
/*     */   public static void process(FilterProcessingContext context) throws XWSSecurityException {
/*     */     GSHeaderElement gSHeaderElement;
/*     */     Assertion assertion;
/*  89 */     boolean isOptimized = false;
/*  90 */     SecurableSoapMessage secureMessage = null;
/*  91 */     SecurityHeader securityHeader = null;
/*  92 */     SecurityHeader optSecHeader = null;
/*  93 */     SecurityHeaderElement she = null;
/*  94 */     if (context instanceof JAXBFilterProcessingContext) {
/*  95 */       isOptimized = true;
/*  96 */       optSecHeader = ((JAXBFilterProcessingContext)context).getSecurityHeader();
/*     */     } else {
/*  98 */       secureMessage = context.getSecurableSoapMessage();
/*  99 */       securityHeader = secureMessage.findOrCreateSecurityHeader();
/*     */     } 
/*     */     
/* 102 */     AuthenticationTokenPolicy policy = (AuthenticationTokenPolicy)context.getSecurityPolicy();
/*     */     
/* 104 */     AuthenticationTokenPolicy.SAMLAssertionBinding samlPolicy = (AuthenticationTokenPolicy.SAMLAssertionBinding)policy.getFeatureBinding();
/*     */ 
/*     */     
/* 107 */     if (samlPolicy.getIncludeToken() == AuthenticationTokenPolicy.SAMLAssertionBinding.INCLUDE_ONCE) {
/* 108 */       throw new XWSSecurityException("Include Token ONCE not supported for SAMLToken Assertions");
/*     */     }
/*     */     
/* 111 */     if (samlPolicy.getAssertionType() != "SV")
/*     */     {
/*     */       
/* 114 */       throw new XWSSecurityException("Internal Error: ExportSamlAssertionFilter called for HOK assertion");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 119 */     samlPolicy = (AuthenticationTokenPolicy.SAMLAssertionBinding)policy.getFeatureBinding();
/*     */     
/* 121 */     samlPolicy.isReadOnly(true);
/*     */     
/* 123 */     DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */     
/* 125 */     dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 126 */     dynamicContext.inBoundMessage(false);
/*     */     
/* 128 */     AuthenticationTokenPolicy.SAMLAssertionBinding resolvedPolicy = context.getSecurityEnvironment().populateSAMLPolicy(context.getExtraneousProperties(), samlPolicy, dynamicContext);
/*     */ 
/*     */     
/* 131 */     Assertion _assertion = null;
/* 132 */     Element assertionElement = resolvedPolicy.getAssertion();
/* 133 */     Element _authorityBinding = resolvedPolicy.getAuthorityBinding();
/*     */     
/* 135 */     if (assertionElement == null) {
/* 136 */       reader = resolvedPolicy.getAssertionReader();
/* 137 */       if (reader != null) {
/*     */         try {
/* 139 */           reader.next();
/* 140 */           id = reader.getAttributeValue(null, "AssertionID");
/* 141 */           if (id == null) {
/* 142 */             id = reader.getAttributeValue(null, "ID");
/*     */           }
/* 144 */           version = reader.getAttributeValue(null, "Version");
/* 145 */           buffer = new MutableXMLStreamBuffer();
/* 146 */           StreamWriterBufferCreator bCreator = new StreamWriterBufferCreator(buffer);
/* 147 */           StreamWriterBufferCreator streamWriterBufferCreator1 = bCreator;
/* 148 */           while (8 != reader.getEventType()) {
/* 149 */             StreamUtil.writeCurrentEvent(reader, (XMLStreamWriter)streamWriterBufferCreator1);
/* 150 */             reader.next();
/*     */           } 
/* 152 */         } catch (XMLStreamException ex) {
/* 153 */           throw new XWSSecurityException(ex);
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       try {
/* 158 */         if (System.getProperty("com.sun.xml.wss.saml.binding.jaxb") == null) {
/* 159 */           if (assertionElement.getAttributeNode("ID") != null) {
/* 160 */             Assertion assertion1 = Assertion.fromElement(assertionElement);
/*     */           } else {
/* 162 */             Assertion assertion1 = Assertion.fromElement(assertionElement);
/*     */           } 
/*     */         } else {
/* 165 */           assertion = Assertion.fromElement(assertionElement);
/*     */         } 
/* 167 */       } catch (SAMLException ex) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 172 */     if (samlPolicy.getIncludeToken() == AuthenticationTokenPolicy.SAMLAssertionBinding.INCLUDE_NEVER || samlPolicy.getIncludeToken() == AuthenticationTokenPolicy.SAMLAssertionBinding.INCLUDE_NEVER_VER2)
/*     */     {
/* 174 */       if (_authorityBinding != null)
/*     */       {
/*     */         
/* 177 */         assertionElement = null;
/*     */       }
/*     */     }
/*     */     
/* 181 */     if (assertion == null && _authorityBinding == null && reader == null) {
/* 182 */       throw new XWSSecurityException("None of SAML Assertion,SAML Assertion Reader or  SAML AuthorityBinding information was set into  the Policy by the CallbackHandler");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     if (assertion != null) {
/* 189 */       if (assertion.getVersion() == null && _authorityBinding == null) {
/* 190 */         if (!isOptimized) {
/* 191 */           if (System.getProperty("com.sun.xml.wss.saml.binding.jaxb") == null) {
/* 192 */             ((Assertion)assertion).toElement((Node)securityHeader);
/*     */           } else {
/* 194 */             assertion.toElement((Node)securityHeader);
/*     */           } 
/*     */         } else {
/* 197 */           gSHeaderElement = new GSHeaderElement(assertionElement, ((JAXBFilterProcessingContext)context).getSOAPVersion());
/* 198 */           if (optSecHeader.getChildElement(gSHeaderElement.getId()) == null) {
/* 199 */             optSecHeader.add((SecurityHeaderElement)gSHeaderElement);
/*     */           } else {
/*     */             return;
/*     */           } 
/*     */         } 
/* 204 */         HashMap<String, Assertion> tokenCache = context.getTokenCache();
/*     */         
/* 206 */         tokenCache.put(assertion.getAssertionID(), assertion);
/* 207 */       } else if (assertion.getVersion() != null) {
/* 208 */         if (!isOptimized) {
/* 209 */           ((Assertion)assertion).toElement((Node)securityHeader);
/*     */         } else {
/* 211 */           gSHeaderElement = new GSHeaderElement(assertionElement, ((JAXBFilterProcessingContext)context).getSOAPVersion());
/* 212 */           if (optSecHeader.getChildElement(gSHeaderElement.getId()) == null) {
/* 213 */             optSecHeader.add((SecurityHeaderElement)gSHeaderElement);
/*     */           } else {
/*     */             return;
/*     */           } 
/*     */         } 
/* 218 */         HashMap<String, Assertion> tokenCache = context.getTokenCache();
/*     */         
/* 220 */         tokenCache.put(assertion.getID(), assertion);
/*     */       
/*     */       }
/* 223 */       else if (null == resolvedPolicy.getSTRID()) {
/* 224 */         throw new XWSSecurityException("Unsupported configuration: required wsu:Id value  for SecurityTokenReference to Remote SAML Assertion not found  in Policy");
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 230 */     else if (reader != null) {
/* 231 */       gSHeaderElement = new GSHeaderElement((XMLStreamBuffer)buffer);
/* 232 */       gSHeaderElement.setId(id);
/* 233 */       if (optSecHeader.getChildElement(gSHeaderElement.getId()) == null) {
/* 234 */         optSecHeader.add((SecurityHeaderElement)gSHeaderElement);
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     if (null != resolvedPolicy.getSTRID()) {
/*     */       
/* 242 */       if (assertion == null && null == resolvedPolicy.getAssertionId() && reader == null) {
/* 243 */         throw new XWSSecurityException("None of SAML Assertion, SAML Assertion Reader or SAML Assertion Id information was set into  the Policy by the CallbackHandler");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 248 */       String assertionId = resolvedPolicy.getAssertionId();
/* 249 */       if (assertion != null) {
/* 250 */         assertionId = assertion.getAssertionID();
/*     */       } else {
/* 252 */         assertionId = (id != null) ? id : assertionId;
/*     */       } 
/* 254 */       if (!isOptimized) {
/* 255 */         SecurityTokenReference tokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/* 256 */         tokenRef.setWsuId(resolvedPolicy.getSTRID());
/*     */         
/* 258 */         if (assertion != null && assertion.getVersion() != null) {
/* 259 */           tokenRef.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
/*     */         }
/* 261 */         else if (reader != null) {
/* 262 */           if (version == "2.0") {
/* 263 */             tokenRef.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
/*     */           } else {
/* 265 */             tokenRef.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
/*     */           } 
/*     */         } else {
/* 268 */           tokenRef.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
/*     */         } 
/*     */ 
/*     */         
/* 272 */         if (_authorityBinding != null) {
/* 273 */           tokenRef.setSamlAuthorityBinding(_authorityBinding, secureMessage.getSOAPPart());
/*     */         }
/*     */         
/* 276 */         KeyIdentifierStrategy strat = new KeyIdentifierStrategy(assertionId);
/* 277 */         strat.insertKey(tokenRef, context.getSecurableSoapMessage());
/* 278 */         securityHeader.insertHeaderBlock((SecurityHeaderBlock)tokenRef);
/*     */       } else {
/* 280 */         JAXBFilterProcessingContext optContext = (JAXBFilterProcessingContext)context;
/* 281 */         WSSElementFactory elementFactory = new WSSElementFactory(optContext.getSOAPVersion());
/* 282 */         KeyIdentifier ref = elementFactory.createKeyIdentifier();
/* 283 */         ref.setValue(assertionId);
/* 284 */         if (assertion != null && assertion.getVersion() != null) {
/* 285 */           ref.setValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
/*     */         }
/* 287 */         else if (reader != null) {
/* 288 */           if (version == "2.0") {
/* 289 */             ref.setValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
/*     */           } else {
/* 291 */             ref.setValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
/*     */           } 
/*     */         } else {
/* 294 */           ref.setValueType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
/*     */         } 
/*     */         
/* 297 */         SecurityTokenReference secTokRef = elementFactory.createSecurityTokenReference((Reference)ref);
/* 298 */         String strId = resolvedPolicy.getSTRID();
/* 299 */         secTokRef.setId(strId);
/* 300 */         if ("true".equals(optContext.getExtraneousProperty("EnableWSS11PolicySender"))) {
/*     */           
/* 302 */           if (assertion != null && assertion.getVersion() != null) {
/* 303 */             secTokRef.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
/*     */           }
/* 305 */           else if (reader != null) {
/* 306 */             if (version == "2.0") {
/* 307 */               secTokRef.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
/*     */             } else {
/* 309 */               secTokRef.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
/*     */             } 
/*     */           } else {
/* 312 */             secTokRef.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
/*     */           } 
/*     */           
/* 315 */           ((NamespaceContextEx)optContext.getNamespaceContext()).addWSS11NS();
/*     */         } 
/* 317 */         SSEData sSEData = new SSEData((SecurityElement)gSHeaderElement, false, optContext.getNamespaceContext());
/* 318 */         optContext.getElementCache().put(strId, sSEData);
/* 319 */         optSecHeader.add((SecurityHeaderElement)secTokRef);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\filter\ExportSamlAssertionFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */