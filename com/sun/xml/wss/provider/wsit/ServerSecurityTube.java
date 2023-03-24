/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.wss.NonceManager;
/*     */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.message.AuthStatus;
/*     */ import javax.security.auth.message.config.ServerAuthContext;
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
/*     */ public class ServerSecurityTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*  70 */   protected static final Logger logger = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */   
/*     */   private final boolean isHttpBinding;
/*     */   
/*     */   private PipeHelper helper;
/*     */   
/*  76 */   private AuthStatus status = AuthStatus.SEND_SUCCESS;
/*  77 */   private ServerAuthContext sAC = null;
/*  78 */   private PacketMessageInfo info = null;
/*  79 */   private WSEndpoint wsEndpoint = null;
/*     */   
/*     */   public ServerSecurityTube(Map<Object, Object> props, Tube next, boolean isHttpBinding) {
/*  82 */     super(next);
/*  83 */     props.put("SECURITY_PIPE", this);
/*  84 */     this.helper = new PipeHelper("SOAP", props, null);
/*  85 */     this.isHttpBinding = isHttpBinding;
/*  86 */     this.wsEndpoint = (WSEndpoint)props.get("ENDPOINT");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ServerSecurityTube(ServerSecurityTube that, TubeCloner cloner) {
/*  94 */     super(that, cloner);
/*     */ 
/*     */     
/*  97 */     this.helper = that.helper;
/*  98 */     this.isHttpBinding = that.isHttpBinding;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 103 */     return (AbstractTubeImpl)new ServerSecurityTube(this, cloner);
/*     */   }
/*     */   
/*     */   private Subject getClientSubject(Packet p) {
/* 107 */     Subject s = null;
/* 108 */     if (p != null) {
/* 109 */       s = (Subject)p.invocationProperties.get("CLIENT_SUBJECT");
/*     */     }
/* 111 */     if (s == null) {
/* 112 */       s = this.helper.getClientSubject();
/* 113 */       if (p != null) {
/* 114 */         p.invocationProperties.put("CLIENT_SUBJECT", s);
/*     */       }
/*     */     } 
/* 117 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 124 */     this.info = new PacketMapMessageInfo(request, new Packet());
/*     */     
/* 126 */     Subject serverSubject = (Subject)request.invocationProperties.get("SERVER_SUBJECT");
/*     */     
/* 128 */     Packet validatedRequest = null;
/* 129 */     Subject clientSubject = getClientSubject(request);
/*     */     try {
/* 131 */       this.sAC = this.helper.getServerAuthContext(this.info, serverSubject);
/* 132 */       if (this.sAC != null) {
/*     */ 
/*     */ 
/*     */         
/* 136 */         this.status = this.sAC.validateRequest(this.info, clientSubject, serverSubject);
/* 137 */         validatedRequest = this.info.getRequestPacket();
/*     */       }
/*     */       else {
/*     */         
/* 141 */         validatedRequest = this.info.getRequestPacket();
/*     */ 
/*     */         
/* 144 */         this.status = AuthStatus.SUCCESS;
/*     */       } 
/* 146 */     } catch (Exception e) {
/* 147 */       logger.log(Level.SEVERE, LogStringsMessages.WSITPVD_0053_ERROR_VALIDATE_REQUEST(), e);
/* 148 */       WebServiceException wse = new WebServiceException("Cannot validate request for", e);
/*     */       
/* 150 */       this.status = AuthStatus.SEND_FAILURE;
/*     */       
/* 152 */       validatedRequest = this.helper.getFaultResponse(this.info.getRequestPacket(), this.info.getResponsePacket(), wse);
/* 153 */       return doReturnWith(validatedRequest);
/*     */     } 
/*     */     
/* 156 */     if (this.status == AuthStatus.SUCCESS) {
/*     */ 
/*     */ 
/*     */       
/* 160 */       this.helper.authorize(validatedRequest);
/* 161 */       if (System.getSecurityManager() == null) {
/* 162 */         return doInvoke(this.next, validatedRequest);
/*     */       }
/* 164 */       final Tube nextTube = this.next;
/* 165 */       final Packet valRequest = validatedRequest;
/*     */       try {
/* 167 */         return Subject.<NextAction>doAsPrivileged(clientSubject, new PrivilegedExceptionAction<NextAction>()
/*     */             {
/*     */               public Object run() throws Exception
/*     */               {
/* 171 */                 return ServerSecurityTube.this.doInvoke(nextTube, valRequest);
/*     */               }
/*     */             }(AccessControlContext)null);
/* 174 */       } catch (PrivilegedActionException pae) {
/* 175 */         Throwable cause = pae.getCause();
/* 176 */         if (cause instanceof javax.security.auth.message.AuthException) {
/* 177 */           logger.log(Level.SEVERE, LogStringsMessages.WSITPVD_0055_WS_ERROR_NEXT_PIPE(), cause);
/*     */         }
/* 179 */         Packet packet = this.helper.getFaultResponse(validatedRequest, this.info.getResponsePacket(), cause);
/* 180 */         return doReturnWith(packet);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (logger.isLoggable(Level.FINE)) {
/* 188 */       logger.log(Level.FINE, "ws.status_validate_request", this.status);
/*     */     }
/*     */     
/* 191 */     Packet response = this.info.getResponsePacket();
/* 192 */     return doReturnWith(response);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 199 */     Subject serverSubject = (Subject)response.invocationProperties.get("SERVER_SUBJECT");
/*     */     
/* 201 */     if (this.sAC != null && response.getMessage() != null) {
/*     */       try {
/* 203 */         this.info.setResponsePacket(response);
/* 204 */         response = processResponse(this.info, this.sAC, serverSubject);
/* 205 */       } catch (Exception ex) {
/* 206 */         logger.log(Level.SEVERE, LogStringsMessages.WSITPVD_0057_ERROR_PROCESS_RESPONSE(), ex);
/*     */       } 
/*     */     }
/*     */     
/* 210 */     return doReturnWith(response);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/* 215 */     if (!(t instanceof WebServiceException)) {
/* 216 */       t = new WebServiceException(t);
/*     */     }
/* 218 */     return doThrow(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet processResponse(PacketMessageInfo info, ServerAuthContext sAC, Subject serverSubject) {
/*     */     try {
/* 227 */       AuthStatus stat = sAC.secureResponse(info, serverSubject);
/* 228 */       if (logger.isLoggable(Level.FINE)) {
/* 229 */         logger.log(Level.FINE, "ws.status_secure_response", stat);
/*     */       }
/* 231 */       if (AuthStatus.SEND_FAILURE == stat) {
/* 232 */         return this.helper.makeFaultResponse(info.getResponsePacket(), new Exception("Error in Securing Response"));
/*     */       }
/* 234 */       return info.getResponsePacket();
/* 235 */     } catch (Exception e) {
/* 236 */       if (e instanceof javax.security.auth.message.AuthException) {
/* 237 */         if (logger.isLoggable(Level.INFO)) {
/* 238 */           logger.log(Level.INFO, "ws.error_secure_response", e);
/*     */         }
/*     */       } else {
/* 241 */         logger.log(Level.SEVERE, LogStringsMessages.WSITPVD_0054_ERROR_SECURE_RESPONSE(), e);
/*     */       } 
/* 243 */       return this.helper.makeFaultResponse(info.getResponsePacket(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 251 */     this.helper.disable();
/*     */ 
/*     */ 
/*     */     
/* 255 */     this.next.preDestroy();
/* 256 */     NonceManager.deleteInstance(this.wsEndpoint);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\ServerSecurityTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */