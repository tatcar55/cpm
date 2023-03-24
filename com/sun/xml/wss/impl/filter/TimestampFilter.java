/*     */ package com.sun.xml.wss.impl.filter;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.tokens.Timestamp;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.SecurityHeader;
/*     */ import com.sun.xml.wss.core.SecurityHeaderBlock;
/*     */ import com.sun.xml.wss.core.Timestamp;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.HarnessUtil;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.callback.DynamicPolicyCallback;
/*     */ import com.sun.xml.wss.impl.configuration.DynamicApplicationContext;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import com.sun.xml.wss.impl.policy.DynamicPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.logging.impl.filter.LogStringsMessages;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimestampFilter
/*     */ {
/* 103 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void process(FilterProcessingContext context) throws XWSSecurityException {
/* 114 */     if (!context.isInboundMessage()) {
/*     */ 
/*     */ 
/*     */       
/* 118 */       if (context.timestampExported()) {
/*     */         return;
/*     */       }
/* 121 */       TimestampPolicy policy = (TimestampPolicy)context.getSecurityPolicy();
/* 122 */       long timeout = policy.getTimeout();
/* 123 */       String created = policy.getCreationTime();
/* 124 */       String id = policy.getUUID();
/* 125 */       if (context.getTimestampTimeout() > 0L) {
/* 126 */         timeout = context.getTimestampTimeout();
/*     */       }
/* 128 */       if (context.makeDynamicPolicyCallback()) {
/* 129 */         TimestampPolicy policyClone = (TimestampPolicy)policy.clone();
/*     */         try {
/* 131 */           DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */ 
/*     */           
/* 134 */           dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 135 */           dynamicContext.inBoundMessage(false);
/*     */           
/* 137 */           DynamicPolicyCallback callback = new DynamicPolicyCallback((SecurityPolicy)policyClone, (DynamicPolicyContext)dynamicContext);
/*     */           
/* 139 */           ProcessingContext.copy(dynamicContext.getRuntimeProperties(), context.getExtraneousProperties());
/* 140 */           HarnessUtil.makeDynamicPolicyCallback(callback, context.getSecurityEnvironment().getCallbackHandler());
/*     */         
/*     */         }
/* 143 */         catch (Exception e) {
/* 144 */           log.log(Level.SEVERE, "Message does not conform to time stamp policy", e);
/* 145 */           throw new XWSSecurityException(e);
/*     */         } 
/*     */         
/* 148 */         timeout = policyClone.getTimeout();
/* 149 */         created = policyClone.getCreationTime();
/*     */       } 
/*     */       
/* 152 */       setTimestamp(context, Long.valueOf(timeout), created, id);
/*     */ 
/*     */ 
/*     */       
/* 156 */       context.timestampExported(true);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 161 */       Timestamp timestamp = null;
/*     */       
/* 163 */       if (context.getMode() == 0) {
/*     */         
/* 165 */         if (context.makeDynamicPolicyCallback()) {
/* 166 */           TimestampPolicy policyClone = (TimestampPolicy)((TimestampPolicy)context.getSecurityPolicy()).clone();
/*     */           
/*     */           try {
/* 169 */             DynamicApplicationContext dynamicContext = new DynamicApplicationContext(context.getPolicyContext());
/*     */ 
/*     */             
/* 172 */             dynamicContext.setMessageIdentifier(context.getMessageIdentifier());
/* 173 */             dynamicContext.inBoundMessage(true);
/* 174 */             DynamicPolicyCallback callback = new DynamicPolicyCallback((SecurityPolicy)policyClone, (DynamicPolicyContext)dynamicContext);
/*     */             
/* 176 */             ProcessingContext.copy(dynamicContext.getRuntimeProperties(), context.getExtraneousProperties());
/* 177 */             HarnessUtil.makeDynamicPolicyCallback(callback, context.getSecurityEnvironment().getCallbackHandler());
/*     */           
/*     */           }
/* 180 */           catch (Exception e) {
/* 181 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1436_MESSAGE_DOESNOT_CONFORM_TIMESTAMP_POLICY(), e);
/* 182 */             throw new XWSSecurityException(e);
/*     */           } 
/* 184 */           context.setSecurityPolicy((SecurityPolicy)policyClone);
/*     */         } 
/*     */         
/* 187 */         TimestampPolicy policy = (TimestampPolicy)context.getSecurityPolicy();
/* 188 */         long maxClockSkew = policy.getMaxClockSkew();
/* 189 */         long timeStampFreshness = policy.getTimestampFreshness();
/*     */         
/* 191 */         SecurityHeader secHeader = context.getSecurableSoapMessage().findSecurityHeader();
/* 192 */         if (secHeader == null) {
/* 193 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0276_INVALID_POLICY_NO_TIMESTAMP_SEC_HEADER());
/* 194 */           throw new XWSSecurityException("Message does not conform to Timestamp policy: wsu:Timestamp element not found in header");
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 199 */         SOAPElement ts = null;
/*     */         
/*     */         try {
/* 202 */           SOAPFactory factory = SOAPFactory.newInstance();
/* 203 */           Name name = factory.createName("Timestamp", "wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */ 
/*     */ 
/*     */           
/* 207 */           Iterator<SOAPElement> i = secHeader.getChildElements(name);
/*     */           
/* 209 */           if (i.hasNext()) {
/* 210 */             ts = i.next();
/* 211 */             if (i.hasNext()) {
/* 212 */               log.log(Level.SEVERE, LogStringsMessages.BSP_3227_SINGLE_TIMESTAMP());
/* 213 */               throw new XWSSecurityException("More than one wsu:Timestamp element in the header");
/*     */             } 
/*     */           } else {
/* 216 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0276_INVALID_POLICY_NO_TIMESTAMP_SEC_HEADER());
/* 217 */             throw new XWSSecurityException("Message does not conform to Timestamp policy: wsu:Timestamp element not found in header");
/*     */           }
/*     */         
/*     */         }
/* 221 */         catch (SOAPException se) {
/*     */           
/* 223 */           throw new XWSSecurityRuntimeException(se);
/*     */         } 
/*     */         
/*     */         try {
/* 227 */           timestamp = new Timestamp(ts);
/* 228 */         } catch (XWSSecurityException xwsse) {
/* 229 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1429_ERROR_TIMESTAMP_INTERNALIZATION(), (Throwable)xwsse);
/* 230 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Failure in Timestamp internalization.\nMessage is: " + xwsse.getMessage(), xwsse);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 238 */           context.getSecurityEnvironment().validateTimestamp(context.getExtraneousProperties(), timestamp, maxClockSkew, timeStampFreshness);
/*     */         }
/* 240 */         catch (XWSSecurityException xwsse) {
/* 241 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1430_ERROR_TIMESTAMP_VALIDATION(), (Throwable)xwsse);
/* 242 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Failure in Timestamp validation.\nMessage is: " + xwsse.getMessage(), xwsse);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 254 */         if (context.getMode() == 1) {
/* 255 */           throw new XWSSecurityException("Internal Error: Called TimestampFilter in POSTHOC Mode");
/*     */         }
/*     */         
/* 258 */         if (context.getMode() == 3) {
/* 259 */           TimestampPolicy ts = new TimestampPolicy();
/* 260 */           context.getInferredSecurityPolicy().append((SecurityPolicy)ts);
/*     */         } 
/*     */         
/* 263 */         SecurityHeader secHeader = context.getSecurableSoapMessage().findSecurityHeader();
/*     */         try {
/* 265 */           timestamp = (Timestamp)SecurityHeaderBlockImpl.fromSoapElement(secHeader.getCurrentHeaderElement(), Timestamp.class);
/*     */         
/*     */         }
/* 268 */         catch (XWSSecurityException xwsse) {
/* 269 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1429_ERROR_TIMESTAMP_INTERNALIZATION(), (Throwable)xwsse);
/* 270 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Failure in Timestamp internalization.\nMessage is: " + xwsse.getMessage(), xwsse);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 278 */           context.getSecurityEnvironment().validateTimestamp(context.getExtraneousProperties(), timestamp, 300000L, 300000L);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 283 */         catch (XWSSecurityException xwsse) {
/* 284 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1430_ERROR_TIMESTAMP_VALIDATION(), (Throwable)xwsse);
/* 285 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Failure in Timestamp validation.\nMessage is: " + xwsse.getMessage(), xwsse);
/*     */         } 
/*     */       } 
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
/*     */   private static void setTimestamp(FilterProcessingContext context, Long timeout, String created, String id) throws XWSSecurityException {
/* 309 */     if (context instanceof JAXBFilterProcessingContext) {
/* 310 */       JAXBFilterProcessingContext optContext = (JAXBFilterProcessingContext)context;
/* 311 */       SecurityHeader secHeader = optContext.getSecurityHeader();
/*     */       
/* 313 */       Timestamp wsuTimestamp = new Timestamp(optContext.getSOAPVersion());
/*     */ 
/*     */       
/* 316 */       wsuTimestamp.setTimeout(timeout.longValue());
/* 317 */       if (id != null) {
/* 318 */         wsuTimestamp.setId(id);
/*     */       }
/*     */ 
/*     */       
/* 322 */       wsuTimestamp.createDateTime();
/*     */       
/* 324 */       secHeader.add((SecurityHeaderElement)wsuTimestamp);
/*     */     } else {
/*     */       
/* 327 */       SecurityHeader secHeader = context.getSecurableSoapMessage().findOrCreateSecurityHeader();
/*     */       
/* 329 */       Timestamp wsuTimestamp = new Timestamp();
/* 330 */       if ("".equals(created)) {
/* 331 */         wsuTimestamp.setCreated(null);
/*     */       } else {
/* 333 */         wsuTimestamp.setCreated(created);
/*     */       } 
/*     */       
/* 336 */       wsuTimestamp.setTimeout(timeout.longValue());
/* 337 */       if (id != null) {
/* 338 */         wsuTimestamp.setId(id);
/*     */       }
/* 340 */       secHeader.insertHeaderBlock((SecurityHeaderBlock)wsuTimestamp);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\filter\TimestampFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */