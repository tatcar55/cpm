/*     */ package com.sun.xml.ws.security.trust;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.Status;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.security.EncryptedKey;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.secconv.WSSCVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.ActAs;
/*     */ import com.sun.xml.ws.security.trust.elements.AllowPostdating;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.CancelTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.IssuedTokens;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.OnBehalfOf;
/*     */ import com.sun.xml.ws.security.trust.elements.RenewTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.Renewing;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedAttachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedUnattachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.SecondaryParameters;
/*     */ import com.sun.xml.ws.security.trust.elements.UseKey;
/*     */ import com.sun.xml.ws.security.trust.elements.ValidateTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.str.DirectReference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.trust.elements.str.Reference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.wsu10.AttributedDateTime;
/*     */ import java.net.URI;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Source;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSTrustElementFactory
/*     */ {
/* 128 */   private static JAXBContext jaxbContext = null;
/* 129 */   private static JAXBContext jaxbContext13 = null;
/* 130 */   private static Map<String, WSTrustElementFactory> intMap = new HashMap<String, WSTrustElementFactory>();
/*     */   
/*     */   static {
/*     */     try {
/* 134 */       jaxbContext = JAXBContext.newInstance("com.sun.xml.ws.security.trust.impl.bindings:com.sun.xml.ws.security.secconv.impl.bindings:com.sun.xml.ws.security.secext10:com.sun.xml.security.core.ai:com.sun.xml.security.core.dsig:com.sun.xml.ws.policy.impl.bindings");
/* 135 */       jaxbContext13 = JAXBContext.newInstance("com.sun.xml.ws.security.trust.impl.wssx.bindings:com.sun.xml.ws.security.secconv.impl.wssx.bindings:com.sun.xml.ws.security.secext10:com.sun.xml.security.core.ai:com.sun.xml.security.core.dsig:com.sun.xml.ws.policy.impl.bindings");
/* 136 */     } catch (JAXBException jbe) {
/* 137 */       throw new RuntimeException(jbe.getMessage(), jbe);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static JAXBContext getContext() {
/* 142 */     return jaxbContext;
/*     */   }
/*     */   
/*     */   public static JAXBContext getContext(WSTrustVersion wstVer) {
/* 146 */     if (wstVer instanceof com.sun.xml.ws.security.trust.impl.wssx.WSTrustVersion13) {
/* 147 */       return jaxbContext13;
/*     */     }
/* 149 */     return jaxbContext;
/*     */   }
/*     */   
/*     */   public static WSTrustElementFactory newInstance() {
/* 153 */     return newInstance("http://schemas.xmlsoap.org/ws/2005/02/trust");
/*     */   }
/*     */   
/*     */   public static WSTrustElementFactory newInstance(String nsUri) {
/* 157 */     WSTrustElementFactory fac = intMap.get(nsUri);
/* 158 */     if (fac != null) {
/* 159 */       return fac;
/*     */     }
/*     */     
/* 162 */     String type = getInstanceClassName(nsUri);
/*     */     try {
/* 164 */       Class<?> clazz = null;
/* 165 */       ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*     */       
/* 167 */       if (loader == null) {
/* 168 */         clazz = Class.forName(type);
/*     */       } else {
/* 170 */         clazz = loader.loadClass(type);
/*     */       } 
/*     */       
/* 173 */       if (clazz != null) {
/*     */         
/* 175 */         Class<WSTrustElementFactory> typedClass = (Class)clazz;
/* 176 */         fac = typedClass.newInstance();
/*     */       } 
/* 178 */     } catch (Exception ex) {
/* 179 */       throw new RuntimeException("unable to initialize the WSTrustElementFactory for the protocol " + nsUri, ex);
/*     */     } 
/*     */     
/* 182 */     intMap.put(nsUri, fac);
/*     */     
/* 184 */     return fac;
/*     */   }
/*     */   
/*     */   public static WSTrustElementFactory newInstance(WSTrustVersion wstVer) {
/* 188 */     return newInstance(wstVer.getNamespaceURI());
/*     */   }
/*     */   
/*     */   public static WSTrustElementFactory newInstance(WSSCVersion wsscVer) {
/* 192 */     return newInstance(wsscVer.getNamespaceURI());
/*     */   }
/*     */   
/*     */   private static String getInstanceClassName(String nsUri) {
/* 196 */     if ("http://schemas.xmlsoap.org/ws/2005/02/trust".equals(nsUri))
/* 197 */       return "com.sun.xml.ws.security.trust.impl.WSTrustElementFactoryImpl"; 
/* 198 */     if ("http://docs.oasis-open.org/ws-sx/ws-trust/200512".equals(nsUri)) {
/* 199 */       return "com.sun.xml.ws.security.trust.impl.wssx.WSTrustElementFactoryImpl";
/*     */     }
/* 201 */     if ("http://schemas.xmlsoap.org/ws/2005/02/sc".equals(nsUri))
/* 202 */       return "com.sun.xml.ws.security.secconv.WSSCElementFactory"; 
/* 203 */     if ("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512".equals(nsUri)) {
/* 204 */       return "com.sun.xml.ws.security.secconv.WSSCElementFactory13";
/*     */     }
/*     */     
/* 207 */     return "com.sun.xml.ws.security.trust.impl.WSTrustElementFactoryImpl";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element toElement(Object jaxbEle) {
/* 538 */     if (jaxbEle instanceof Element) {
/* 539 */       return (Element)jaxbEle;
/*     */     }
/*     */     try {
/* 542 */       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 543 */       dbf.setNamespaceAware(true);
/* 544 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 545 */       Document doc = db.newDocument();
/* 546 */       getMarshaller().marshal(jaxbEle, doc);
/* 547 */       return doc.getDocumentElement();
/* 548 */     } catch (Exception ex) {
/* 549 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract RequestSecurityToken createRSTForIssue(URI paramURI1, URI paramURI2, URI paramURI3, AppliesTo paramAppliesTo, Claims paramClaims, Entropy paramEntropy, Lifetime paramLifetime) throws WSTrustException;
/*     */   
/*     */   public abstract RequestSecurityTokenResponse createRSTRForIssue(URI paramURI1, URI paramURI2, RequestedSecurityToken paramRequestedSecurityToken, AppliesTo paramAppliesTo, RequestedAttachedReference paramRequestedAttachedReference, RequestedUnattachedReference paramRequestedUnattachedReference, RequestedProofToken paramRequestedProofToken, Entropy paramEntropy, Lifetime paramLifetime) throws WSTrustException;
/*     */   
/*     */   public abstract RequestSecurityTokenResponseCollection createRSTRCollectionForIssue(URI paramURI1, URI paramURI2, RequestedSecurityToken paramRequestedSecurityToken, AppliesTo paramAppliesTo, RequestedAttachedReference paramRequestedAttachedReference, RequestedUnattachedReference paramRequestedUnattachedReference, RequestedProofToken paramRequestedProofToken, Entropy paramEntropy, Lifetime paramLifetime) throws WSTrustException;
/*     */   
/*     */   public abstract IssuedTokens createIssuedTokens(RequestSecurityTokenResponseCollection paramRequestSecurityTokenResponseCollection);
/*     */   
/*     */   public abstract Entropy createEntropy(BinarySecret paramBinarySecret);
/*     */   
/*     */   public abstract Entropy createEntropy(EncryptedKey paramEncryptedKey);
/*     */   
/*     */   public abstract SecondaryParameters createSecondaryParameters();
/*     */   
/*     */   public abstract BinarySecret createBinarySecret(byte[] paramArrayOfbyte, String paramString);
/*     */   
/*     */   public abstract BinarySecret createBinarySecret(Element paramElement) throws WSTrustException;
/*     */   
/*     */   public abstract UseKey createUseKey(Token paramToken, String paramString);
/*     */   
/*     */   public abstract OnBehalfOf createOnBehalfOf(Token paramToken);
/*     */   
/*     */   public abstract ActAs createActAs(Token paramToken);
/*     */   
/*     */   public abstract ValidateTarget createValidateTarget(Token paramToken);
/*     */   
/*     */   public abstract Status createStatus(String paramString1, String paramString2);
/*     */   
/*     */   public abstract Lifetime createLifetime(AttributedDateTime paramAttributedDateTime1, AttributedDateTime paramAttributedDateTime2);
/*     */   
/*     */   public abstract RequestedProofToken createRequestedProofToken();
/*     */   
/*     */   public abstract RequestedSecurityToken createRequestedSecurityToken(Token paramToken);
/*     */   
/*     */   public abstract RequestedSecurityToken createRequestedSecurityToken();
/*     */   
/*     */   public abstract DirectReference createDirectReference(String paramString1, String paramString2);
/*     */   
/*     */   public abstract KeyIdentifier createKeyIdentifier(String paramString1, String paramString2);
/*     */   
/*     */   public abstract SecurityTokenReference createSecurityTokenReference(Reference paramReference);
/*     */   
/*     */   public abstract SecurityContextToken createSecurityContextToken(URI paramURI, String paramString1, String paramString2);
/*     */   
/*     */   public abstract RequestedAttachedReference createRequestedAttachedReference(SecurityTokenReference paramSecurityTokenReference);
/*     */   
/*     */   public abstract RequestedUnattachedReference createRequestedUnattachedReference(SecurityTokenReference paramSecurityTokenReference);
/*     */   
/*     */   public abstract RequestSecurityToken createRSTForRenew(URI paramURI1, URI paramURI2, URI paramURI3, RenewTarget paramRenewTarget, AllowPostdating paramAllowPostdating, Renewing paramRenewing);
/*     */   
/*     */   public abstract RequestSecurityTokenResponse createRSTRForRenew(URI paramURI1, URI paramURI2, RequestedSecurityToken paramRequestedSecurityToken, RequestedAttachedReference paramRequestedAttachedReference, RequestedUnattachedReference paramRequestedUnattachedReference, RequestedProofToken paramRequestedProofToken, Entropy paramEntropy, Lifetime paramLifetime) throws WSTrustException;
/*     */   
/*     */   public abstract RenewTarget createRenewTarget(SecurityTokenReference paramSecurityTokenReference);
/*     */   
/*     */   public abstract CancelTarget createCancelTarget(SecurityTokenReference paramSecurityTokenReference);
/*     */   
/*     */   public abstract RequestSecurityToken createRSTForCancel(URI paramURI, CancelTarget paramCancelTarget);
/*     */   
/*     */   public abstract RequestSecurityTokenResponse createRSTRForCancel();
/*     */   
/*     */   public abstract RequestSecurityToken createRSTForValidate(URI paramURI1, URI paramURI2);
/*     */   
/*     */   public abstract RequestSecurityTokenResponse createRSTRForValidate(URI paramURI, RequestedSecurityToken paramRequestedSecurityToken, Status paramStatus);
/*     */   
/*     */   public abstract RequestSecurityTokenResponseCollection createRSTRC(List<RequestSecurityTokenResponse> paramList);
/*     */   
/*     */   public abstract RequestSecurityToken createRST();
/*     */   
/*     */   public abstract RequestSecurityTokenResponse createRSTR();
/*     */   
/*     */   public abstract RequestSecurityToken createRSTFrom(Source paramSource);
/*     */   
/*     */   public abstract RequestSecurityToken createRSTFrom(Element paramElement);
/*     */   
/*     */   public abstract RequestSecurityTokenResponse createRSTRFrom(Source paramSource);
/*     */   
/*     */   public abstract RequestSecurityTokenResponse createRSTRFrom(Element paramElement);
/*     */   
/*     */   public abstract RequestSecurityTokenResponseCollection createRSTRCollectionFrom(Source paramSource);
/*     */   
/*     */   public abstract RequestSecurityTokenResponseCollection createRSTRCollectionFrom(Element paramElement);
/*     */   
/*     */   public abstract Claims createClaims(Element paramElement) throws WSTrustException;
/*     */   
/*     */   public abstract Claims createClaims(Claims paramClaims) throws WSTrustException;
/*     */   
/*     */   public abstract Claims createClaims() throws WSTrustException;
/*     */   
/*     */   public abstract RequestSecurityToken createRSTFrom(JAXBElement paramJAXBElement);
/*     */   
/*     */   public abstract RequestSecurityTokenResponse createRSTRFrom(JAXBElement paramJAXBElement);
/*     */   
/*     */   public abstract RequestSecurityTokenResponseCollection createRSTRCollectionFrom(JAXBElement paramJAXBElement);
/*     */   
/*     */   public abstract SecurityTokenReference createSecurityTokenReference(JAXBElement paramJAXBElement);
/*     */   
/*     */   public abstract JAXBElement toJAXBElement(BaseSTSRequest paramBaseSTSRequest);
/*     */   
/*     */   public abstract JAXBElement toJAXBElement(BaseSTSResponse paramBaseSTSResponse);
/*     */   
/*     */   public abstract JAXBElement toJAXBElement(SecurityTokenReference paramSecurityTokenReference);
/*     */   
/*     */   public abstract JAXBElement toJAXBElement(RequestSecurityToken paramRequestSecurityToken);
/*     */   
/*     */   public abstract JAXBElement toJAXBElement(RequestSecurityTokenResponse paramRequestSecurityTokenResponse);
/*     */   
/*     */   public abstract JAXBElement toJAXBElement(RequestSecurityTokenResponseCollection paramRequestSecurityTokenResponseCollection);
/*     */   
/*     */   public abstract Source toSource(BaseSTSRequest paramBaseSTSRequest);
/*     */   
/*     */   public abstract Source toSource(BaseSTSResponse paramBaseSTSResponse);
/*     */   
/*     */   public abstract Source toSource(RequestSecurityToken paramRequestSecurityToken);
/*     */   
/*     */   public abstract Source toSource(RequestSecurityTokenResponse paramRequestSecurityTokenResponse);
/*     */   
/*     */   public abstract Source toSource(RequestSecurityTokenResponseCollection paramRequestSecurityTokenResponseCollection);
/*     */   
/*     */   public abstract Element toElement(BaseSTSRequest paramBaseSTSRequest);
/*     */   
/*     */   public abstract Element toElement(BaseSTSResponse paramBaseSTSResponse);
/*     */   
/*     */   public abstract Element toElement(RequestSecurityToken paramRequestSecurityToken);
/*     */   
/*     */   public abstract Element toElement(RequestSecurityTokenResponse paramRequestSecurityTokenResponse);
/*     */   
/*     */   public abstract Element toElement(RequestSecurityTokenResponse paramRequestSecurityTokenResponse, Document paramDocument);
/*     */   
/*     */   public abstract Element toElement(RequestSecurityTokenResponseCollection paramRequestSecurityTokenResponseCollection);
/*     */   
/*     */   public abstract Element toElement(BinarySecret paramBinarySecret);
/*     */   
/*     */   public abstract Element toElement(SecurityTokenReference paramSecurityTokenReference, Document paramDocument);
/*     */   
/*     */   public abstract Element toElement(BinarySecret paramBinarySecret, Document paramDocument);
/*     */   
/*     */   public abstract Marshaller getMarshaller();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\WSTrustElementFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */