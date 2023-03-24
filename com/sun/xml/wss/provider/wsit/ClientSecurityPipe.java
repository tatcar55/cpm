/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.pipe.PipeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterPipeImpl;
/*     */ import com.sun.xml.ws.security.secconv.SecureConversationInitiator;
/*     */ import com.sun.xml.ws.security.secconv.WSSecureConversationException;
/*     */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.message.AuthStatus;
/*     */ import javax.security.auth.message.config.ClientAuthContext;
/*     */ import javax.xml.bind.JAXBElement;
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
/*     */ public class ClientSecurityPipe
/*     */   extends AbstractFilterPipeImpl
/*     */   implements SecureConversationInitiator
/*     */ {
/*     */   private static final String WSIT_CLIENT_AUTH_CONTEXT = "com.sun.xml.wss.provider.wsit.WSITClientAuthContext";
/*     */   protected PipeHelper helper;
/*  75 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientSecurityPipe(Map<Object, Object> props, Pipe next) {
/*  82 */     super(next);
/*  83 */     props.put("SECURITY_PIPE", this);
/*     */     
/*  85 */     WSDLPort wsdlModel = (WSDLPort)props.get("WSDL_MODEL");
/*  86 */     if (wsdlModel != null) {
/*  87 */       props.put("WSDL_SERVICE", wsdlModel.getOwner().getName());
/*     */     }
/*     */     
/*  90 */     this.helper = new PipeHelper("SOAP", props, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClientSecurityPipe(ClientSecurityPipe that, PipeCloner cloner) {
/*  95 */     super(that, cloner);
/*  96 */     this.helper = that.helper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/*     */     try {
/* 104 */       Packet request = new Packet();
/* 105 */       PacketMessageInfo info = new PacketMapMessageInfo(request, new Packet());
/* 106 */       Subject subj = getClientSubject(request);
/* 107 */       ClientAuthContext cAC = this.helper.getClientAuthContext(info, subj);
/* 108 */       if (cAC != null && "com.sun.xml.wss.provider.wsit.WSITClientAuthContext".equals(cAC.getClass().getName())) {
/* 109 */         cAC.cleanSubject(info, subj);
/*     */       }
/* 111 */     } catch (Exception ex) {}
/*     */ 
/*     */     
/* 114 */     this.helper.disable();
/*     */   }
/*     */   
/*     */   public final Pipe copy(PipeCloner cloner) {
/* 118 */     return (Pipe)new ClientSecurityPipe(this, cloner);
/*     */   }
/*     */   
/*     */   public PipeHelper getPipeHelper() {
/* 122 */     return this.helper;
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
/*     */   public Packet process(Packet request) {
/* 136 */     PacketMessageInfo info = new PacketMapMessageInfo(request, new Packet());
/*     */     
/* 138 */     info.getMap().put("javax.xml.ws.wsdl.service", this.helper.getProperty("WSDL_SERVICE"));
/*     */ 
/*     */     
/* 141 */     AuthStatus status = AuthStatus.SEND_SUCCESS;
/*     */     
/* 143 */     Subject clientSubject = getClientSubject(request);
/*     */     
/* 145 */     ClientAuthContext cAC = null;
/*     */ 
/*     */     
/*     */     try {
/* 149 */       cAC = this.helper.getClientAuthContext(info, clientSubject);
/*     */       
/* 151 */       if (cAC != null)
/*     */       {
/*     */         
/* 154 */         status = cAC.secureRequest(info, clientSubject);
/*     */       }
/*     */     }
/* 157 */     catch (Exception e) {
/*     */       
/* 159 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0058_ERROR_SECURE_REQUEST(), e);
/*     */       
/* 161 */       throw new WebServiceException("Cannot secure request", e);
/*     */     } 
/*     */ 
/*     */     
/* 165 */     Packet response = null;
/*     */     
/* 167 */     if (status == AuthStatus.FAILURE) {
/* 168 */       if (log.isLoggable(Level.FINE)) {
/* 169 */         log.log(Level.FINE, "ws.status_secure_request", status);
/*     */       }
/* 171 */       response = info.getResponsePacket();
/*     */     } else {
/* 173 */       response = processSecureRequest(info, cAC, clientSubject);
/*     */     } 
/*     */ 
/*     */     
/* 177 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet processSecureRequest(PacketMessageInfo info, ClientAuthContext cAC, Subject clientSubject) throws WebServiceException {
/* 185 */     Packet response = this.next.process(info.getRequestPacket());
/*     */ 
/*     */     
/* 188 */     Message m = response.getMessage();
/*     */     
/* 190 */     if (m != null)
/*     */     {
/* 192 */       if (cAC != null) {
/*     */         
/* 194 */         AuthStatus status = AuthStatus.SUCCESS;
/*     */         
/* 196 */         info.setResponsePacket(response);
/*     */ 
/*     */         
/*     */         try {
/* 200 */           status = cAC.validateResponse(info, clientSubject, null);
/*     */         }
/* 202 */         catch (Exception e) {
/*     */           
/* 204 */           throw new WebServiceException("Cannot validate response for {0}", e);
/*     */         } 
/*     */ 
/*     */         
/* 208 */         if (status == AuthStatus.SEND_CONTINUE) {
/* 209 */           response = processSecureRequest(info, cAC, clientSubject);
/*     */         } else {
/* 211 */           response = info.getResponsePacket();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 216 */     return response;
/*     */   }
/*     */ 
/*     */   
/*     */   private Subject getClientSubject(Packet p) {
/* 221 */     Subject s = null;
/* 222 */     if (p != null) {
/* 223 */       s = (Subject)p.invocationProperties.get("CLIENT_SUBJECT");
/*     */     }
/*     */     
/* 226 */     if (s == null) {
/* 227 */       s = this.helper.getClientSubject();
/* 228 */       if (p != null) {
/* 229 */         p.invocationProperties.put("CLIENT_SUBJECT", s);
/*     */       }
/*     */     } 
/*     */     
/* 233 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement startSecureConversation(Packet packet) throws WSSecureConversationException {
/* 239 */     PacketMessageInfo info = new PacketMapMessageInfo(packet, new Packet());
/* 240 */     JAXBElement token = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 245 */       Subject clientSubject = getClientSubject(packet);
/*     */ 
/*     */ 
/*     */       
/* 249 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 250 */       map.put("SECURITY_TOKEN", info);
/*     */       
/* 252 */       this.helper.getSessionToken(map, info, clientSubject);
/*     */ 
/*     */       
/* 255 */       Object o = info.getMap().get("SECURITY_TOKEN");
/*     */       
/* 257 */       if (o != null && o instanceof JAXBElement) {
/* 258 */         token = (JAXBElement)o;
/*     */       }
/*     */     }
/* 261 */     catch (Exception e) {
/*     */       
/* 263 */       if (e instanceof WSSecureConversationException) {
/* 264 */         throw (WSSecureConversationException)e;
/*     */       }
/* 266 */       throw new WSSecureConversationException("Secure Conversation failure: ", e);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 271 */     return token;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\ClientSecurityPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */