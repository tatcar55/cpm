/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.security.secconv.SecureConversationInitiator;
/*     */ import com.sun.xml.ws.security.secconv.WSSecureConversationException;
/*     */ import com.sun.xml.wss.jaxws.impl.TubeConfiguration;
/*     */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*     */ import java.security.cert.X509Certificate;
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
/*     */ public class ClientSecurityTube
/*     */   extends AbstractFilterTubeImpl
/*     */   implements SecureConversationInitiator
/*     */ {
/*     */   private static final String WSIT_CLIENT_AUTH_CONTEXT = "com.sun.xml.wss.provider.wsit.WSITClientAuthContext";
/*     */   protected PipeHelper helper;
/*  76 */   private AuthStatus status = AuthStatus.SEND_SUCCESS;
/*  77 */   private ClientAuthContext cAC = null;
/*  78 */   private Subject clientSubject = null;
/*  79 */   private PacketMessageInfo pmInfo = null;
/*  80 */   protected X509Certificate serverCert = null;
/*     */   
/*  82 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientSecurityTube(TubeConfiguration config, Tube nextTube) {
/*  90 */     super(nextTube);
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientSecurityTube(Map<Object, Object> props, Tube next) {
/*  95 */     super(next);
/*  96 */     props.put("SECURITY_PIPE", this);
/*     */     
/*  98 */     WSDLPort wsdlModel = (WSDLPort)props.get("WSDL_MODEL");
/*  99 */     if (wsdlModel != null) {
/* 100 */       props.put("WSDL_SERVICE", wsdlModel.getOwner().getName());
/*     */     }
/*     */     
/* 103 */     this.helper = new PipeHelper("SOAP", props, null);
/*     */   }
/*     */   
/*     */   protected ClientSecurityTube(ClientSecurityTube that, TubeCloner cloner) {
/* 107 */     super(that, cloner);
/* 108 */     this.helper = that.helper;
/* 109 */     this.serverCert = that.serverCert;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 114 */     return (AbstractTubeImpl)new ClientSecurityTube(this, cloner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/*     */     try {
/* 122 */       Packet request = new Packet();
/* 123 */       PacketMessageInfo info = new PacketMapMessageInfo(request, new Packet());
/* 124 */       Subject subj = getClientSubject(request);
/* 125 */       ClientAuthContext cAC = this.helper.getClientAuthContext(info, subj);
/* 126 */       if (cAC != null && "com.sun.xml.wss.provider.wsit.WSITClientAuthContext".equals(cAC.getClass().getName())) {
/* 127 */         cAC.cleanSubject(info, subj);
/*     */       }
/* 129 */     } catch (Exception ex) {}
/*     */ 
/*     */     
/* 132 */     this.helper.disable();
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet packet) {
/*     */     try {
/* 138 */       packet = processClientRequest(packet);
/*     */       
/* 140 */       this.clientSubject = (Subject)packet.invocationProperties.get("CLIENT_SUBJECT");
/* 141 */     } catch (Throwable t) {
/* 142 */       if (!(t instanceof WebServiceException)) {
/* 143 */         t = new WebServiceException(t);
/*     */       }
/* 145 */       return doThrow(t);
/*     */     } 
/* 147 */     if (this.status == AuthStatus.FAILURE) {
/* 148 */       return doReturnWith(packet);
/*     */     }
/* 150 */     return doInvoke(this.next, packet);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet ret) {
/*     */     try {
/* 157 */       ret.invocationProperties.put("CLIENT_SUBJECT", this.clientSubject);
/* 158 */       ret = processClientResponse(ret);
/* 159 */     } catch (Throwable t) {
/* 160 */       if (!(t instanceof WebServiceException)) {
/* 161 */         t = new WebServiceException(t);
/*     */       }
/* 163 */       return doThrow(t);
/*     */     } 
/* 165 */     return doReturnWith(ret);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/* 170 */     if (!(t instanceof WebServiceException)) {
/* 171 */       t = new WebServiceException(t);
/*     */     }
/* 173 */     return doThrow(t);
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
/*     */   private Packet processClientRequest(Packet request) {
/* 186 */     PacketMessageInfo info = new PacketMapMessageInfo(request, new Packet());
/* 187 */     info.getMap().put("javax.xml.ws.wsdl.service", this.helper.getProperty("WSDL_SERVICE"));
/*     */     
/* 189 */     this.clientSubject = getClientSubject(request);
/* 190 */     this.cAC = null;
/*     */     try {
/* 192 */       this.cAC = this.helper.getClientAuthContext(info, this.clientSubject);
/* 193 */       if (this.cAC != null)
/*     */       {
/* 195 */         this.status = this.cAC.secureRequest(info, this.clientSubject);
/*     */       }
/* 197 */     } catch (Exception e) {
/* 198 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0058_ERROR_SECURE_REQUEST(), e);
/* 199 */       throw new WebServiceException("Cannot secure request", e);
/*     */     } 
/*     */ 
/*     */     
/* 203 */     Packet response = null;
/* 204 */     if (this.status == AuthStatus.FAILURE) {
/* 205 */       if (log.isLoggable(Level.FINE)) {
/* 206 */         log.log(Level.FINE, "ws.status_secure_request", this.status);
/*     */       }
/* 208 */       response = info.getResponsePacket();
/*     */     } else {
/* 210 */       response = info.getRequestPacket();
/*     */     } 
/*     */     
/* 213 */     this.pmInfo = info;
/* 214 */     return response;
/*     */   }
/*     */ 
/*     */   
/*     */   private Packet processClientResponse(Packet response) {
/* 219 */     Message m = response.getMessage();
/* 220 */     if (m != null && 
/* 221 */       this.cAC != null) {
/* 222 */       AuthStatus authstatus = AuthStatus.SUCCESS;
/* 223 */       this.pmInfo.setResponsePacket(response);
/*     */       try {
/* 225 */         authstatus = this.cAC.validateResponse(this.pmInfo, this.clientSubject, null);
/* 226 */       } catch (Exception e) {
/* 227 */         throw new WebServiceException("Cannot validate response for {0}", e);
/*     */       } 
/*     */       
/* 230 */       if (authstatus == AuthStatus.SEND_CONTINUE) {
/* 231 */         response = processSecureRequest(this.pmInfo, this.cAC, this.clientSubject);
/*     */       } else {
/* 233 */         response = this.pmInfo.getResponsePacket();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 238 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet processSecureRequest(PacketMessageInfo info, ClientAuthContext cAC, Subject clientSubject) throws WebServiceException {
/* 246 */     Fiber fiber = (Fiber.current()).owner.createFiber();
/* 247 */     Packet response = fiber.runSync(this.next, info.getRequestPacket());
/*     */ 
/*     */     
/* 250 */     Message m = response.getMessage();
/* 251 */     if (m != null && 
/* 252 */       cAC != null) {
/* 253 */       AuthStatus status = AuthStatus.SUCCESS;
/* 254 */       info.setResponsePacket(response);
/*     */       try {
/* 256 */         status = cAC.validateResponse(info, clientSubject, null);
/* 257 */       } catch (Exception e) {
/* 258 */         throw new WebServiceException("Cannot validate response for {0}", e);
/*     */       } 
/*     */       
/* 261 */       if (status == AuthStatus.SEND_CONTINUE) {
/* 262 */         response = processSecureRequest(info, cAC, clientSubject);
/*     */       } else {
/* 264 */         response = info.getResponsePacket();
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     return response;
/*     */   }
/*     */ 
/*     */   
/*     */   public JAXBElement startSecureConversation(Packet packet) throws WSSecureConversationException {
/* 273 */     PacketMessageInfo info = new PacketMapMessageInfo(packet, new Packet());
/* 274 */     JAXBElement token = null;
/*     */ 
/*     */     
/*     */     try {
/* 278 */       Subject clientSubject = getClientSubject(packet);
/*     */ 
/*     */       
/* 281 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 282 */       map.put("SECURITY_TOKEN", info);
/* 283 */       this.helper.getSessionToken(map, info, clientSubject);
/*     */       
/* 285 */       Object o = info.getMap().get("SECURITY_TOKEN");
/* 286 */       if (o != null && o instanceof JAXBElement) {
/* 287 */         token = (JAXBElement)o;
/*     */       }
/* 289 */     } catch (Exception e) {
/* 290 */       if (e instanceof WSSecureConversationException) {
/* 291 */         throw (WSSecureConversationException)e;
/*     */       }
/* 293 */       throw new WSSecureConversationException("Secure Conversation failure: ", e);
/*     */     } 
/*     */ 
/*     */     
/* 297 */     return token;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Subject getClientSubject(Packet p) {
/* 303 */     Subject s = null;
/* 304 */     if (p != null) {
/* 305 */       s = (Subject)p.invocationProperties.get("CLIENT_SUBJECT");
/*     */     }
/*     */     
/* 308 */     if (s == null) {
/* 309 */       s = this.helper.getClientSubject();
/* 310 */       if (p != null) {
/* 311 */         p.invocationProperties.put("CLIENT_SUBJECT", s);
/*     */       }
/*     */     } 
/* 314 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\ClientSecurityTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */