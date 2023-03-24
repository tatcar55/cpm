/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.security.secconv.WSSecureConversationRuntimeException;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BinarySecurityToken;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.DetailEntry;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPUtil
/*     */ {
/*     */   private static boolean enableFaultDetail = false;
/*     */   private static final String WSS_DEBUG_PROPERTY = "com.sun.xml.wss.debug";
/*     */   private static final String ENABLE_FAULT_DETAIL = "FaultDetail";
/*  81 */   protected static final LocalStringManagerImpl localStrings = new LocalStringManagerImpl(SOAPUtil.class);
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  86 */     String debugFlag = System.getProperty("com.sun.xml.wss.debug");
/*  87 */     if (debugFlag != null && debugFlag.contains("FaultDetail"))
/*  88 */       enableFaultDetail = true; 
/*     */   }
/*  90 */   private static final String localizedGenericError = localStrings.getLocalString("generic.validation.error", "Invalid Security Header");
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPFaultException getSOAPFaultException(QName faultCode, WSSecureConversationRuntimeException wsre, SOAPFactory soapFactory, SOAPVersion sOAPVersion) {
/*  95 */     throw new UnsupportedOperationException("Not yet implemented");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEnableFaultDetail() {
/* 102 */     return enableFaultDetail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getLocalizedGenericError() {
/* 109 */     return localizedGenericError;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getIdFromFragmentRef(String ref) {
/* 118 */     char start = ref.charAt(0);
/* 119 */     if (start == '#') {
/* 120 */       return ref.substring(1);
/*     */     }
/* 122 */     return ref;
/*     */   }
/*     */ 
/*     */   
/*     */   public static X509Certificate getCertificateFromToken(BinarySecurityToken bst) throws XWSSecurityException {
/* 127 */     byte[] data = bst.getTokenValue();
/*     */     try {
/* 129 */       CertificateFactory certFact = CertificateFactory.getInstance("X.509");
/* 130 */       return (X509Certificate)certFact.generateCertificate(new ByteArrayInputStream(data));
/*     */     }
/* 132 */     catch (Exception e) {
/* 133 */       throw new XWSSecurityException("Unable to create X509Certificate from data");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WssSoapFaultException newSOAPFaultException(QName faultCode, String faultstring, Throwable th) {
/* 158 */     if (!isEnableFaultDetail()) {
/* 159 */       return new WssSoapFaultException(MessageConstants.WSSE_INVALID_SECURITY, getLocalizedGenericError(), null, null);
/*     */     }
/* 161 */     WssSoapFaultException sfe = new WssSoapFaultException(faultCode, faultstring, null, null);
/*     */     
/* 163 */     sfe.initCause(th);
/* 164 */     return sfe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WssSoapFaultException newSOAPFaultException(QName faultCode, String faultstring, Throwable th, boolean faultDetail) {
/* 172 */     if (!faultDetail) {
/* 173 */       return new WssSoapFaultException(MessageConstants.WSSE_INVALID_SECURITY, getLocalizedGenericError(), null, null);
/*     */     }
/* 175 */     WssSoapFaultException sfe = new WssSoapFaultException(faultCode, faultstring, null, null);
/*     */     
/* 177 */     sfe.initCause(th);
/* 178 */     return sfe;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static SOAPFault getSOAPFault(WssSoapFaultException sfe, SOAPFactory soapFactory, SOAPVersion version) {
/*     */     SOAPFault fault;
/* 184 */     String reasonText = sfe.getFaultString();
/* 185 */     if (reasonText == null) {
/* 186 */       reasonText = (sfe.getMessage() != null) ? sfe.getMessage() : "";
/*     */     }
/*     */     try {
/* 189 */       if (version == SOAPVersion.SOAP_12) {
/* 190 */         fault = soapFactory.createFault(reasonText, SOAPConstants.SOAP_SENDER_FAULT);
/* 191 */         fault.appendFaultSubcode(sfe.getFaultCode());
/*     */       } else {
/* 193 */         fault = soapFactory.createFault(reasonText, sfe.getFaultCode());
/*     */       } 
/* 195 */     } catch (Exception e) {
/* 196 */       throw new XWSSecurityRuntimeException(e);
/*     */     } 
/* 198 */     return fault;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static SOAPFault getSOAPFault(QName faultCode, String faultString, SOAPFactory soapFactory, SOAPVersion version) {
/*     */     SOAPFault fault;
/*     */     try {
/* 205 */       if (version == SOAPVersion.SOAP_12) {
/* 206 */         fault = soapFactory.createFault(faultString, SOAPConstants.SOAP_SENDER_FAULT);
/* 207 */         fault.appendFaultSubcode(faultCode);
/*     */       } else {
/* 209 */         fault = soapFactory.createFault(faultString, faultCode);
/*     */       } 
/* 211 */     } catch (Exception e) {
/* 212 */       throw new XWSSecurityRuntimeException(e);
/*     */     } 
/* 214 */     return fault;
/*     */   }
/*     */   
/*     */   public static SOAPFaultException getSOAPFaultException(WssSoapFaultException ex, SOAPFactory factory, SOAPVersion version) {
/* 218 */     SOAPFault fault = getSOAPFault(ex, factory, version);
/* 219 */     if (!isEnableFaultDetail()) {
/* 220 */       return createSOAPFault(fault, (Throwable)ex);
/*     */     }
/* 222 */     Throwable cause = ex.getCause();
/* 223 */     setFaultDetail(fault, cause);
/* 224 */     return createSOAPFault(fault, (Throwable)ex);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SOAPFaultException getSOAPFaultException(QName faultCode, Exception ex, SOAPFactory factory, SOAPVersion version) {
/* 229 */     String msg = getExceptionMessage(ex);
/* 230 */     SOAPFault fault = getSOAPFault(faultCode, msg, factory, version);
/* 231 */     if (!isEnableFaultDetail()) {
/* 232 */       return createSOAPFault(fault, ex);
/*     */     }
/* 234 */     setFaultDetail(fault, ex);
/* 235 */     return createSOAPFault(fault, ex);
/*     */   }
/*     */   
/*     */   public static SOAPFaultException getSOAPFaultException(Exception ex, SOAPFactory factory, SOAPVersion version) {
/* 239 */     String msg = getExceptionMessage(ex);
/*     */     
/* 241 */     SOAPFault fault = getSOAPFault(MessageConstants.WSSE_INVALID_SECURITY, msg, factory, version);
/* 242 */     if (!isEnableFaultDetail()) {
/* 243 */       return createSOAPFault(fault, ex);
/*     */     }
/* 245 */     setFaultDetail(fault, ex);
/* 246 */     return createSOAPFault(fault, ex);
/*     */   }
/*     */ 
/*     */   
/*     */   private static SOAPFaultException createSOAPFault(SOAPFault fault, Throwable cause) {
/* 251 */     SOAPFaultException sfe = new SOAPFaultException(fault);
/* 252 */     if (isEnableFaultDetail()) {
/* 253 */       sfe.initCause(cause);
/*     */     } else {
/* 255 */       sfe.initCause(new Exception());
/*     */     } 
/* 257 */     return sfe;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setFaultDetail(SOAPFault fault, Throwable cause) {
/*     */     try {
/* 264 */       Detail detail = fault.addDetail();
/* 265 */       QName name = new QName("https://xwss.dev.java.net", "FaultDetail", "xwssfault");
/* 266 */       DetailEntry entry = detail.addDetailEntry(name);
/* 267 */       String exception = "Cause Not Set";
/* 268 */       if (cause != null) {
/* 269 */         ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 270 */         PrintWriter s = new PrintWriter(bos);
/* 271 */         cause.printStackTrace(s);
/* 272 */         s.flush();
/* 273 */         exception = bos.toString();
/*     */       } 
/* 275 */       entry.addTextNode(exception);
/* 276 */     } catch (SOAPException ex) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getExceptionMessage(Throwable ex) {
/* 282 */     if (!isEnableFaultDetail()) {
/* 283 */       return getLocalizedGenericError();
/*     */     }
/* 285 */     String msg = ex.getMessage();
/* 286 */     if (msg == null) {
/* 287 */       msg = ex.getClass().getName();
/*     */     }
/* 289 */     return msg;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\SOAPUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */