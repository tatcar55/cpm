/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.security.trust.client.STSIssuedTokenConfiguration;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.wss.AliasSelector;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.ProcessingContextImpl;
/*     */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*     */ import com.sun.xml.wss.impl.callback.KeyStoreCallback;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.jaxws.impl.TubeConfiguration;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ import org.jvnet.staxex.XMLStreamReaderEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CertificateRetriever
/*     */ {
/*  99 */   protected TubeConfiguration pipeConfig = null;
/* 100 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */   
/* 102 */   private String location = null;
/* 103 */   private String password = null;
/* 104 */   private String alias = null;
/* 105 */   private Certificate cs = null;
/* 106 */   private FileInputStream fis = null;
/* 107 */   private Policy ep = null;
/* 108 */   private String callbackHandler = null;
/* 109 */   private String aliasSelector = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBSTFromIdentityExtension(XMLStreamReader reader) throws XMLStreamException {
/* 115 */     boolean isKeyInfo = false;
/* 116 */     boolean isBST = false;
/* 117 */     byte[] bstValue = null;
/* 118 */     while (reader.hasNext()) {
/* 119 */       if (reader.getEventType() == 1) {
/* 120 */         isBST = ("BinarySecurityToken".equals(reader.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(reader.getNamespaceURI()));
/* 121 */         isKeyInfo = ("KeyInfo".equals(reader.getLocalName()) && "http://www.w3.org/2000/09/xmldsig#".equals(reader.getNamespaceURI()));
/* 122 */         if (isBST || isKeyInfo) {
/* 123 */           if (isBST) {
/* 124 */             reader.next();
/* 125 */           } else if (isKeyInfo) {
/* 126 */             while (reader.hasNext() && !"X509Certificate".equals(reader.getLocalName())) {
/* 127 */               reader.next();
/*     */             }
/* 129 */             reader.next();
/*     */           } 
/* 131 */           if (reader.getEventType() == 4) {
/*     */             
/* 133 */             if (reader instanceof XMLStreamReaderEx) {
/* 134 */               CharSequence data = ((XMLStreamReaderEx)reader).getPCDATA();
/* 135 */               if (data instanceof Base64Data) {
/* 136 */                 Base64Data binaryData = (Base64Data)data;
/* 137 */                 bstValue = binaryData.getExact();
/* 138 */                 return bstValue;
/*     */               } 
/*     */             } 
/*     */             try {
/* 142 */               bstValue = Base64.decode(StreamUtil.getCV(reader));
/* 143 */             } catch (Base64DecodingException ex) {
/* 144 */               log.log(Level.WARNING, LogStringsMessages.WSS_0819_ERROR_GETTING_CERTIFICATE_EPRIDENTITY(), ex);
/*     */             } 
/*     */           } else {
/*     */             
/* 148 */             log.log(Level.WARNING, LogStringsMessages.WSS_0819_ERROR_GETTING_CERTIFICATE_EPRIDENTITY());
/*     */           } 
/*     */           
/* 151 */           return bstValue;
/*     */         } 
/*     */       } 
/* 154 */       reader.next();
/*     */     } 
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Certificate getServerKeyStore(WSEndpoint wse) throws IOException, XWSSecurityException {
/* 161 */     QName keyStoreQName = new QName("http://schemas.sun.com/2006/03/wss/server", "KeyStore");
/* 162 */     setLocationPasswordAndAlias(keyStoreQName, wse);
/*     */     
/* 164 */     if (this.password == null || this.location == null) {
/* 165 */       if (this.callbackHandler == null) {
/* 166 */         return null;
/*     */       }
/* 168 */       this.cs = getCertificateUsingCallbackHandler(this.callbackHandler);
/* 169 */       return this.cs;
/*     */     } 
/*     */     
/* 172 */     if (this.alias == null) {
/* 173 */       this.alias = getAliasUsingAliasSelector();
/*     */     }
/* 175 */     KeyStore keyStore = null;
/*     */     try {
/* 177 */       keyStore = KeyStore.getInstance("JKS");
/* 178 */       this.fis = new FileInputStream(this.location);
/* 179 */       keyStore.load(this.fis, this.password.toCharArray());
/* 180 */       this.cs = keyStore.getCertificate(this.alias);
/* 181 */       if (this.cs == null) {
/* 182 */         log.log(Level.WARNING, LogStringsMessages.WSS_0821_CERTIFICATE_NOT_FOUND_FOR_ALIAS(this.alias));
/*     */       }
/* 184 */     } catch (FileNotFoundException ex) {
/* 185 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 186 */       return null;
/* 187 */     } catch (IOException ex) {
/* 188 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 189 */       return null;
/* 190 */     } catch (NoSuchAlgorithmException ex) {
/* 191 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 192 */       return null;
/* 193 */     } catch (CertificateException ex) {
/* 194 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 195 */       return null;
/* 196 */     } catch (KeyStoreException ex) {
/* 197 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 198 */       return null;
/*     */     } finally {
/* 200 */       keyStore = null;
/* 201 */       this.fis.close();
/*     */     } 
/* 203 */     return this.cs;
/*     */   }
/*     */ 
/*     */   
/*     */   public X509Certificate constructCertificate(byte[] bstValue) {
/*     */     try {
/* 209 */       X509Certificate cert = null;
/* 210 */       CertificateFactory fact = CertificateFactory.getInstance("X.509");
/* 211 */       cert = (X509Certificate)fact.generateCertificate(new ByteArrayInputStream(bstValue));
/* 212 */       return cert;
/* 213 */     } catch (CertificateException ex) {
/* 214 */       log.log(Level.SEVERE, "error while constructing the certificate from bst value ", ex);
/* 215 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkforEPRIdentity(WSEndpoint wse, QName eprQName) {
/* 221 */     if (wse.getPort() == null) {
/* 222 */       return true;
/*     */     }
/* 224 */     getEndpointOROperationalLevelPolicy(wse);
/* 225 */     if (this.ep == null) {
/* 226 */       return false;
/*     */     }
/* 228 */     for (AssertionSet assertionSet : this.ep) {
/* 229 */       for (PolicyAssertion pa : assertionSet) {
/* 230 */         if (pa.getName().equals(eprQName)) {
/* 231 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 235 */     return false;
/*     */   }
/*     */   
/*     */   private String getAliasUsingAliasSelector() {
/* 239 */     if (this.aliasSelector == null) {
/* 240 */       return null;
/*     */     }
/* 242 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 243 */     Class<?> aliasSelectorClass = null;
/* 244 */     if (loader != null) {
/*     */       try {
/* 246 */         aliasSelectorClass = loader.loadClass(this.aliasSelector);
/* 247 */       } catch (ClassNotFoundException e) {
/* 248 */         return null;
/*     */       } 
/*     */     }
/* 251 */     if (aliasSelectorClass == null) {
/*     */       
/* 253 */       loader = getClass().getClassLoader();
/*     */       try {
/* 255 */         aliasSelectorClass = loader.loadClass(this.aliasSelector);
/* 256 */       } catch (ClassNotFoundException e) {
/* 257 */         return null;
/*     */       } 
/*     */     } 
/* 260 */     if (aliasSelectorClass == null) {
/* 261 */       return null;
/*     */     }
/*     */     try {
/* 264 */       AliasSelector as = (AliasSelector)aliasSelectorClass.newInstance();
/* 265 */       String myAlias = as.select(new HashMap<Object, Object>());
/* 266 */       if (myAlias == null) {
/* 267 */         log.log(Level.WARNING, LogStringsMessages.WSS_0823_ALIAS_NOTFOUND_FOR_ALIAS_SELECTOR());
/*     */       }
/* 269 */       return myAlias;
/* 270 */     } catch (InstantiationException ex) {
/* 271 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 272 */       return null;
/* 273 */     } catch (IllegalAccessException ex) {
/* 274 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 275 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private X509Certificate getCertificateUsingCallbackHandler(String callbackHandler) {
/* 281 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 282 */     Class<?> callbackHandlerClass = null;
/* 283 */     if (loader != null) {
/*     */       try {
/* 285 */         callbackHandlerClass = loader.loadClass(callbackHandler);
/* 286 */       } catch (ClassNotFoundException e) {
/* 287 */         return null;
/*     */       } 
/*     */     }
/* 290 */     if (callbackHandlerClass == null) {
/*     */       
/* 292 */       loader = getClass().getClassLoader();
/*     */       try {
/* 294 */         callbackHandlerClass = loader.loadClass(callbackHandler);
/* 295 */       } catch (ClassNotFoundException e) {
/* 296 */         return null;
/*     */       } 
/*     */     } 
/* 299 */     if (callbackHandlerClass == null) {
/* 300 */       return null;
/*     */     }
/* 302 */     KeyStoreCallback ksc = new KeyStoreCallback();
/* 303 */     Callback[] callbacks = new Callback[1];
/* 304 */     callbacks[0] = (Callback)ksc;
/*     */     try {
/* 306 */       CallbackHandler cbh = (CallbackHandler)callbackHandlerClass.newInstance();
/* 307 */       cbh.handle(callbacks);
/* 308 */       X509Certificate cert = null;
/* 309 */       cert = (ksc.getKeystore() != null) ? (X509Certificate)ksc.getKeystore().getCertificate(this.alias) : null;
/* 310 */       if (cert == null && this.alias != null) {
/* 311 */         log.log(Level.WARNING, LogStringsMessages.WSS_0821_CERTIFICATE_NOT_FOUND_FOR_ALIAS(this.alias));
/*     */       }
/* 313 */       return cert;
/* 314 */     } catch (IOException ex) {
/* 315 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 316 */       return null;
/* 317 */     } catch (UnsupportedCallbackException ex) {
/* 318 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 319 */       return null;
/* 320 */     } catch (InstantiationException ex) {
/* 321 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 322 */       return null;
/* 323 */     } catch (IllegalAccessException ex) {
/* 324 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 325 */       return null;
/* 326 */     } catch (KeyStoreException ex) {
/* 327 */       log.log(Level.WARNING, LogStringsMessages.WSS_0818_ERROR_PUTTING_CERTIFICATE_EPRIDENTITY(), ex);
/* 328 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getEndpointOROperationalLevelPolicy(WSEndpoint wse) {
/* 333 */     PolicyMap pm = wse.getPolicyMap();
/* 334 */     WSDLPort port = wse.getPort();
/* 335 */     QName serviceName = port.getOwner().getName();
/* 336 */     QName portName = port.getName();
/*     */     
/* 338 */     PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(serviceName, portName);
/*     */     
/*     */     try {
/* 341 */       this.ep = pm.getEndpointEffectivePolicy(endpointKey);
/* 342 */       if (this.ep == null) {
/* 343 */         for (WSDLBoundOperation operation : port.getBinding().getBindingOperations()) {
/* 344 */           QName operationName = new QName(operation.getBoundPortType().getName().getNamespaceURI(), operation.getName().getLocalPart());
/*     */           
/* 346 */           PolicyMapKey operationKey = PolicyMap.createWsdlOperationScopeKey(serviceName, portName, operationName);
/* 347 */           this.ep = pm.getOperationEffectivePolicy(operationKey);
/* 348 */           if (this.ep != null) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       }
/* 353 */     } catch (PolicyException ex) {
/* 354 */       throw new RuntimeException(ex);
/* 355 */     } catch (IllegalArgumentException ex) {
/* 356 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setLocationPasswordAndAlias(QName qName, WSEndpoint wse) {
/* 361 */     if (wse.getPort() == null) {
/*     */       return;
/*     */     }
/* 364 */     if (this.ep == null) {
/*     */       return;
/*     */     }
/* 367 */     for (AssertionSet assertionSet : this.ep) {
/* 368 */       for (PolicyAssertion pa : assertionSet) {
/* 369 */         if (!PolicyUtil.isConfigPolicyAssertion(pa) || 
/* 370 */           !pa.getName().equals(qName)) {
/*     */           continue;
/*     */         }
/*     */         
/* 374 */         this.password = pa.getAttributeValue(new QName("storepass"));
/* 375 */         this.location = pa.getAttributeValue(new QName("location"));
/* 376 */         this.alias = pa.getAttributeValue(new QName("alias"));
/* 377 */         this.callbackHandler = pa.getAttributeValue(new QName("callbackHandler"));
/* 378 */         this.aliasSelector = pa.getAttributeValue(new QName("aliasSelector"));
/*     */         
/* 380 */         StringBuffer sb = null;
/* 381 */         if (this.location != null) {
/* 382 */           sb = new StringBuffer(this.location);
/* 383 */           if (this.location.startsWith("$WSIT")) {
/* 384 */             String path = System.getProperty("WSIT_HOME");
/* 385 */             sb.replace(0, 10, path);
/* 386 */             this.location = sb.toString();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setServerCertInTheContext(ProcessingContextImpl ctx, SecurityEnvironment secEnv, X509Certificate serverCert) {
/* 395 */     boolean valid = false;
/*     */     try {
/* 397 */       valid = secEnv.validateCertificate(serverCert, ctx.getExtraneousProperties());
/* 398 */     } catch (WssSoapFaultException ex) {
/*     */     
/* 400 */     } catch (XWSSecurityException ex) {
/* 401 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0820_ERROR_VALIDATE_CERTIFICATE_EPRIDENTITY(), (Throwable)ex);
/*     */     } 
/* 403 */     if (valid) {
/* 404 */       log.log(Level.INFO, LogStringsMessages.WSS_0824_USING_SERVER_CERTIFICATE_FROM_EPR_IDENTITY());
/* 405 */       ctx.getExtraneousProperties().put("server-certificate", serverCert);
/*     */     } else {
/* 407 */       log.log(Level.WARNING, LogStringsMessages.WSS_0822_ERROR_VALIDATING_SERVER_CERTIFICATE());
/*     */     } 
/* 409 */     return valid;
/*     */   }
/*     */   
/*     */   public boolean setServerCertInTheSTSConfig(STSIssuedTokenConfiguration config, SecurityEnvironment secEnv, X509Certificate serverCert) {
/* 413 */     boolean valid = false;
/*     */     try {
/* 415 */       valid = secEnv.validateCertificate(serverCert, config.getOtherOptions());
/* 416 */     } catch (WssSoapFaultException ex) {
/*     */     
/* 418 */     } catch (XWSSecurityException ex) {
/* 419 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0820_ERROR_VALIDATE_CERTIFICATE_EPRIDENTITY(), (Throwable)ex);
/*     */     } 
/* 421 */     if (valid) {
/* 422 */       log.log(Level.INFO, LogStringsMessages.WSS_0824_USING_SERVER_CERTIFICATE_FROM_EPR_IDENTITY());
/* 423 */       config.getOtherOptions().put("Identity", serverCert);
/*     */     } else {
/* 425 */       log.log(Level.WARNING, LogStringsMessages.WSS_0822_ERROR_VALIDATING_SERVER_CERTIFICATE());
/*     */     } 
/* 427 */     return valid;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\CertificateRetriever.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */