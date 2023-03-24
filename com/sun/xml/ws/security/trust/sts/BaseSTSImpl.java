/*     */ package com.sun.xml.ws.security.trust.sts;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.BaseSTS;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustContract;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.config.STSConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.config.TrustSPMetadata;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.impl.IssuedTokenContextImpl;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.impl.DefaultSTSConfiguration;
/*     */ import com.sun.xml.ws.security.trust.impl.DefaultTrustSPMetadata;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.SubjectAccessor;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseSTSImpl
/*     */   implements BaseSTS
/*     */ {
/*     */   public static final int DEFAULT_TIMEOUT = 36000;
/*     */   public static final String DEFAULT_ISSUER = "SampleSunSTS";
/*     */   public static final String STS_CONFIGURATION = "STSConfiguration";
/*     */   public static final String DEFAULT_IMPL = "com.sun.xml.ws.security.trust.impl.IssueSamlTokenContractImpl";
/*     */   public static final String DEFAULT_APPLIESTO = "default";
/*     */   public static final String APPLIES_TO = "AppliesTo";
/*     */   public static final String LIFETIME = "LifeTime";
/*     */   public static final String ALIAS = "CertAlias";
/*     */   public static final String ENCRYPT_KEY = "encryptIssuedKey";
/*     */   public static final String ENCRYPT_TOKEN = "encryptIssuedToken";
/*     */   public static final String CONTRACT = "Contract";
/*     */   public static final String ISSUER = "Issuer";
/*     */   public static final String TOKEN_TYPE = "TokenType";
/*     */   public static final String KEY_TYPE = "KeyType";
/*     */   public static final String SERVICE_PROVIDERS = "ServiceProviders";
/*     */   public static final String END_POINT = "endPoint";
/* 164 */   private static final QName Q_EK = new QName("", "encryptIssuedKey");
/*     */   
/* 166 */   private static final QName Q_ET = new QName("", "encryptIssuedToken");
/*     */   
/* 168 */   private static final QName Q_EP = new QName("", "endPoint");
/*     */   
/* 170 */   protected WSTrustVersion wstVer = WSTrustVersion.WS_TRUST_10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source invoke(Source rstElement) {
/* 183 */     STSConfiguration config = getConfiguration();
/* 184 */     Source rstrEle = null;
/*     */     
/*     */     try {
/* 187 */       WSTrustElementFactory eleFac = WSTrustElementFactory.newInstance(this.wstVer);
/* 188 */       RequestSecurityToken rst = parseRST(rstElement, config);
/*     */       
/* 190 */       String appliesTo = null;
/* 191 */       AppliesTo applTo = rst.getAppliesTo();
/* 192 */       if (applTo != null) {
/* 193 */         appliesTo = WSTrustUtil.getAppliesToURI(applTo);
/*     */       }
/*     */       
/* 196 */       if (appliesTo == null) {
/* 197 */         appliesTo = "default";
/*     */       }
/*     */       
/* 200 */       if (rst.getRequestType().toString().equals(this.wstVer.getIssueRequestTypeURI())) {
/* 201 */         rstrEle = issue(config, appliesTo, eleFac, (BaseSTSRequest)rst);
/* 202 */       } else if (rst.getRequestType().toString().equals(this.wstVer.getCancelRequestTypeURI())) {
/* 203 */         rstrEle = cancel(config, appliesTo, eleFac, (BaseSTSRequest)rst);
/* 204 */       } else if (rst.getRequestType().toString().equals(this.wstVer.getRenewRequestTypeURI())) {
/* 205 */         rstrEle = renew(config, appliesTo, eleFac, rst);
/* 206 */       } else if (rst.getRequestType().toString().equals(this.wstVer.getValidateRequestTypeURI())) {
/* 207 */         rstrEle = validate(config, appliesTo, eleFac, (BaseSTSRequest)rst);
/*     */       } 
/* 209 */     } catch (Exception ex) {
/*     */       
/* 211 */       throw new WebServiceException(ex);
/*     */     } 
/*     */     
/* 214 */     return rstrEle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   STSConfiguration getConfiguration() {
/* 225 */     MessageContext msgCtx = getMessageContext();
/*     */     
/* 227 */     SecurityEnvironment secEnv = (SecurityEnvironment)msgCtx.get("SecurityEnvironment");
/* 228 */     WSTrustVersion wstVersion = (WSTrustVersion)msgCtx.get("WSTrustVersion");
/* 229 */     String authnCtxClass = (String)msgCtx.get("AuthnContextClass");
/* 230 */     if (wstVersion != null) {
/* 231 */       this.wstVer = wstVersion;
/*     */     }
/*     */     
/* 234 */     STSConfiguration rtConfig = WSTrustFactory.getRuntimeSTSConfiguration();
/* 235 */     if (rtConfig != null) {
/* 236 */       if (rtConfig.getCallbackHandler() == null) {
/* 237 */         rtConfig.getOtherOptions().put("SecurityEnvironment", secEnv);
/*     */       }
/* 239 */       if (wstVersion == null) {
/* 240 */         wstVersion = (WSTrustVersion)rtConfig.getOtherOptions().get("WSTrustVersion");
/* 241 */         if (wstVersion != null) {
/* 242 */           this.wstVer = wstVersion;
/*     */         }
/*     */       } 
/*     */       
/* 246 */       rtConfig.getOtherOptions().put("WSTrustVersion", this.wstVer);
/*     */       
/* 248 */       return rtConfig;
/*     */     } 
/*     */ 
/*     */     
/* 252 */     DefaultSTSConfiguration config = new DefaultSTSConfiguration();
/* 253 */     config.getOtherOptions().put("SecurityEnvironment", secEnv);
/*     */     
/* 255 */     Iterator<PolicyAssertion> iterator = (Iterator)msgCtx.get("http://schemas.sun.com/ws/2006/05/trust/server");
/*     */     
/* 257 */     if (iterator == null) {
/* 258 */       throw new WebServiceException("STS configuration information is not available");
/*     */     }
/*     */     
/* 261 */     while (iterator.hasNext()) {
/* 262 */       PolicyAssertion assertion = iterator.next();
/* 263 */       if (!"STSConfiguration".equals(assertion.getName().getLocalPart())) {
/*     */         continue;
/*     */       }
/* 266 */       config.setEncryptIssuedToken(Boolean.parseBoolean(assertion.getAttributeValue(Q_ET)));
/* 267 */       config.setEncryptIssuedKey(Boolean.parseBoolean(assertion.getAttributeValue(Q_EK)));
/* 268 */       Iterator<PolicyAssertion> stsConfig = assertion.getNestedAssertionsIterator();
/*     */       
/* 270 */       while (stsConfig.hasNext()) {
/* 271 */         PolicyAssertion serviceSTSPolicy = stsConfig.next();
/* 272 */         if ("LifeTime".equals(serviceSTSPolicy.getName().getLocalPart())) {
/* 273 */           config.setIssuedTokenTimeout(Integer.parseInt(serviceSTSPolicy.getValue()));
/*     */           
/*     */           continue;
/*     */         } 
/* 277 */         if ("Contract".equals(serviceSTSPolicy.getName().getLocalPart())) {
/* 278 */           config.setType(serviceSTSPolicy.getValue());
/*     */           continue;
/*     */         } 
/* 281 */         if ("Issuer".equals(serviceSTSPolicy.getName().getLocalPart())) {
/* 282 */           config.setIssuer(serviceSTSPolicy.getValue());
/*     */           
/*     */           continue;
/*     */         } 
/* 286 */         if ("ServiceProviders".equals(serviceSTSPolicy.getName().getLocalPart())) {
/* 287 */           Iterator<PolicyAssertion> serviceProviders = serviceSTSPolicy.getNestedAssertionsIterator();
/*     */           
/* 289 */           String endpointUri = null;
/* 290 */           while (serviceProviders.hasNext()) {
/* 291 */             PolicyAssertion serviceProvider = serviceProviders.next();
/* 292 */             endpointUri = serviceProvider.getAttributeValue(Q_EP);
/* 293 */             if (endpointUri == null) {
/* 294 */               endpointUri = serviceProvider.getAttributeValue(new QName("", "endPoint".toLowerCase()));
/*     */             }
/* 296 */             DefaultTrustSPMetadata data = new DefaultTrustSPMetadata(endpointUri);
/* 297 */             Iterator<PolicyAssertion> spConfig = serviceProvider.getNestedAssertionsIterator();
/* 298 */             while (spConfig.hasNext()) {
/* 299 */               PolicyAssertion policy = spConfig.next();
/* 300 */               if ("CertAlias".equals(policy.getName().getLocalPart())) {
/* 301 */                 data.setCertAlias(policy.getValue()); continue;
/* 302 */               }  if ("TokenType".equals(policy.getName().getLocalPart())) {
/* 303 */                 data.setTokenType(policy.getValue()); continue;
/* 304 */               }  if ("KeyType".equals(policy.getName().getLocalPart())) {
/* 305 */                 data.setKeyType(policy.getValue());
/*     */               }
/*     */             } 
/*     */             
/* 309 */             config.addTrustSPMetadata((TrustSPMetadata)data, endpointUri);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 314 */     config.getOtherOptions().put("WSTrustVersion", this.wstVer);
/*     */     
/* 316 */     if (authnCtxClass != null) {
/* 317 */       config.getOtherOptions().put("AuthnContextClass", authnCtxClass);
/*     */     }
/* 319 */     config.getOtherOptions().putAll(msgCtx);
/*     */     
/* 321 */     return (STSConfiguration)config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Source issue(STSConfiguration config, String appliesTo, WSTrustElementFactory eleFac, BaseSTSRequest rst) throws WSTrustException, TransformerException {
/* 329 */     WSTrustContract<BaseSTSRequest, BaseSTSResponse> contract = WSTrustFactory.newWSTrustContract(config, appliesTo);
/*     */     
/* 331 */     IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/*     */     try {
/* 333 */       issuedTokenContextImpl.setRequestorSubject(SubjectAccessor.getRequesterSubject(getMessageContext()));
/* 334 */     } catch (XWSSecurityException ex) {
/* 335 */       throw new WSTrustException("error getting subject", ex);
/*     */     } 
/*     */     
/* 338 */     BaseSTSResponse response = (BaseSTSResponse)contract.issue(rst, (IssuedTokenContext)issuedTokenContextImpl);
/*     */     
/* 340 */     return eleFac.toSource(response);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Source cancel(STSConfiguration config, String appliesTo, WSTrustElementFactory eleFac, BaseSTSRequest rst) {
/* 346 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Source renew(STSConfiguration config, String appliesTo, WSTrustElementFactory eleFac, RequestSecurityToken rst) throws WSTrustException {
/* 355 */     WSTrustContract<BaseSTSRequest, BaseSTSResponse> contract = WSTrustFactory.newWSTrustContract(config, appliesTo);
/*     */     
/* 357 */     IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/*     */     
/* 359 */     BaseSTSResponse rstr = (BaseSTSResponse)contract.renew(rst, (IssuedTokenContext)issuedTokenContextImpl);
/*     */     
/* 361 */     Source rstrEle = eleFac.toSource(rstr);
/* 362 */     return rstrEle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Source validate(STSConfiguration config, String appliesTo, WSTrustElementFactory eleFac, BaseSTSRequest rst) throws WSTrustException {
/* 371 */     WSTrustContract<BaseSTSRequest, BaseSTSResponse> contract = WSTrustFactory.newWSTrustContract(config, appliesTo);
/*     */     
/* 373 */     IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/*     */     
/* 375 */     BaseSTSResponse rstr = (BaseSTSResponse)contract.validate(rst, (IssuedTokenContext)issuedTokenContextImpl);
/*     */     
/* 377 */     Source rstrEle = eleFac.toSource(rstr);
/* 378 */     return rstrEle;
/*     */   }
/*     */   
/*     */   private RequestSecurityToken parseRST(Source source, STSConfiguration config) throws WSTrustException {
/* 382 */     Element ele = null;
/*     */     try {
/* 384 */       DOMResult result = new DOMResult();
/* 385 */       TransformerFactory tfactory = WSITXMLFactory.createTransformerFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 386 */       Transformer tf = tfactory.newTransformer();
/* 387 */       tf.transform(source, result);
/*     */       
/* 389 */       Node node = result.getNode();
/* 390 */       if (node instanceof Document) {
/* 391 */         ele = ((Document)node).getDocumentElement();
/* 392 */       } else if (node instanceof Element) {
/* 393 */         ele = (Element)node;
/*     */       } 
/* 395 */     } catch (Exception xe) {
/* 396 */       throw new WSTrustException("Error occurred while trying to parse RST stream", xe);
/*     */     } 
/* 398 */     WSTrustElementFactory fact = WSTrustElementFactory.newInstance(this.wstVer);
/* 399 */     RequestSecurityToken rst = fact.createRSTFrom(ele);
/*     */ 
/*     */ 
/*     */     
/* 403 */     NodeList list = ele.getElementsByTagNameNS("*", "Assertion");
/* 404 */     if (list.getLength() > 0) {
/* 405 */       Element assertion = (Element)list.item(0);
/* 406 */       config.getOtherOptions().put("SamlAssertionElementInRST", assertion);
/*     */     } 
/*     */     
/* 409 */     return rst;
/*     */   }
/*     */   
/*     */   protected abstract MessageContext getMessageContext();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\sts\BaseSTSImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */