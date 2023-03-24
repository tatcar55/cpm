/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.pipe.PipeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterPipeImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerSecurityPipe
/*     */   extends AbstractFilterPipeImpl
/*     */ {
/*  69 */   protected static final Logger logger = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */ 
/*     */   
/*     */   private final boolean isHttpBinding;
/*     */ 
/*     */   
/*     */   private PipeHelper helper;
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSecurityPipe(Map<Object, Object> props, Pipe next, boolean isHttpBinding) {
/*  80 */     super(next);
/*     */     
/*  82 */     props.put("SECURITY_PIPE", this);
/*  83 */     this.helper = new PipeHelper("SOAP", props, null);
/*  84 */     this.isHttpBinding = isHttpBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ServerSecurityPipe(ServerSecurityPipe that, PipeCloner cloner) {
/*  90 */     super(that, cloner);
/*     */ 
/*     */     
/*  93 */     this.helper = that.helper;
/*     */     
/*  95 */     this.isHttpBinding = that.isHttpBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 103 */     this.helper.disable();
/*     */ 
/*     */ 
/*     */     
/* 107 */     this.next.preDestroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe copy(PipeCloner cloner) {
/* 114 */     return (Pipe)new ServerSecurityPipe(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet process(Packet request) {
/* 119 */     if (this.isHttpBinding) {
/* 120 */       return this.next.process(request);
/*     */     }
/*     */     
/* 123 */     Packet response = null;
/*     */ 
/*     */     
/*     */     try {
/* 127 */       response = processRequest(request);
/*     */     }
/* 129 */     catch (Exception e) {
/* 130 */       if (logger.isLoggable(Level.FINE)) {
/* 131 */         logger.log(Level.FINE, "Failure in security pipe process", e);
/*     */       }
/* 133 */       response = this.helper.makeFaultResponse(response, e);
/*     */     } 
/*     */     
/* 136 */     return response;
/*     */   }
/*     */   
/*     */   private Packet processRequest(Packet request) throws Exception {
/*     */     final Packet validatedRequest;
/* 141 */     AuthStatus status = AuthStatus.SUCCESS;
/*     */     
/* 143 */     PacketMessageInfo info = new PacketMapMessageInfo(request, new Packet());
/*     */ 
/*     */ 
/*     */     
/* 147 */     Subject serverSubject = (Subject)request.invocationProperties.get("SERVER_SUBJECT");
/*     */ 
/*     */ 
/*     */     
/* 151 */     ServerAuthContext sAC = this.helper.getServerAuthContext(info, serverSubject);
/*     */ 
/*     */     
/* 154 */     Subject clientSubject = getClientSubject(request);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 160 */       if (sAC != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 166 */         status = sAC.validateRequest(info, clientSubject, serverSubject);
/*     */       }
/*     */     }
/* 169 */     catch (Exception e) {
/*     */       
/* 171 */       logger.log(Level.SEVERE, LogStringsMessages.WSITPVD_0053_ERROR_VALIDATE_REQUEST(), e);
/*     */       
/* 173 */       WebServiceException wse = new WebServiceException("Cannot validate request for", e);
/*     */ 
/*     */ 
/*     */       
/* 177 */       status = AuthStatus.SEND_FAILURE;
/*     */ 
/*     */       
/* 180 */       return this.helper.getFaultResponse(info.getRequestPacket(), info.getResponsePacket(), wse);
/*     */     }
/*     */     finally {
/*     */       
/* 184 */       packet1 = info.getRequestPacket();
/*     */     } 
/*     */     
/* 187 */     Packet response = null;
/*     */     
/* 189 */     if (status == AuthStatus.SUCCESS) {
/*     */       
/* 191 */       boolean authorized = true;
/* 192 */       this.helper.authorize(packet1);
/* 193 */       if (authorized)
/*     */       {
/*     */         
/* 196 */         if (System.getSecurityManager() == null) {
/*     */           
/*     */           try {
/* 199 */             response = this.next.process(packet1);
/* 200 */           } catch (Exception e) {
/* 201 */             logger.log(Level.SEVERE, LogStringsMessages.WSITPVD_0055_WS_ERROR_NEXT_PIPE(), e);
/* 202 */             response = this.helper.getFaultResponse(packet1, info.getResponsePacket(), e);
/*     */           } 
/*     */         } else {
/*     */           
/*     */           try {
/* 207 */             response = Subject.<Packet>doAsPrivileged(clientSubject, new PrivilegedExceptionAction<Packet>()
/*     */                 {
/*     */                   public Object run() throws Exception
/*     */                   {
/* 211 */                     return ServerSecurityPipe.this.next.process(validatedRequest);
/*     */                   }
/*     */                 },  (AccessControlContext)null);
/* 214 */           } catch (PrivilegedActionException pae) {
/* 215 */             Throwable cause = pae.getCause();
/* 216 */             if (cause instanceof javax.security.auth.message.AuthException) {
/* 217 */               logger.log(Level.SEVERE, LogStringsMessages.WSITPVD_0055_WS_ERROR_NEXT_PIPE(), cause);
/*     */             }
/* 219 */             response = this.helper.getFaultResponse(packet1, info.getResponsePacket(), cause);
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 226 */       if (response == null) {
/*     */         
/* 228 */         WebServiceException wse = new WebServiceException("Invocation of Service {0} returned null response packet");
/*     */ 
/*     */         
/* 231 */         response = this.helper.getFaultResponse(packet1, info.getResponsePacket(), wse);
/*     */ 
/*     */         
/* 234 */         logger.log(Level.SEVERE, LogStringsMessages.WSITPVD_0056_NULL_RESPONSE(), wse);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 239 */       if (sAC != null && response.getMessage() != null) {
/* 240 */         info.setResponsePacket(response);
/* 241 */         response = processResponse(info, sAC, serverSubject);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 246 */       if (logger.isLoggable(Level.FINE)) {
/* 247 */         logger.log(Level.FINE, "ws.status_validate_request", status);
/*     */       }
/*     */       
/* 250 */       response = info.getResponsePacket();
/*     */     } 
/*     */     
/* 253 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet processResponse(PacketMessageInfo info, ServerAuthContext sAC, Subject serverSubject) throws Exception {
/*     */     AuthStatus status;
/*     */     try {
/* 265 */       status = sAC.secureResponse(info, serverSubject);
/*     */     }
/* 267 */     catch (Exception e) {
/* 268 */       if (e instanceof javax.security.auth.message.AuthException) {
/* 269 */         if (logger.isLoggable(Level.INFO)) {
/* 270 */           logger.log(Level.INFO, "ws.error_secure_response", e);
/*     */         }
/*     */       } else {
/* 273 */         logger.log(Level.SEVERE, LogStringsMessages.WSITPVD_0054_ERROR_SECURE_RESPONSE(), e);
/*     */       } 
/*     */       
/* 276 */       return this.helper.makeFaultResponse(info.getResponsePacket(), e);
/*     */     } 
/*     */     
/* 279 */     if (logger.isLoggable(Level.FINE)) {
/* 280 */       logger.log(Level.FINE, "ws.status_secure_response", status);
/*     */     }
/*     */     
/* 283 */     return info.getResponsePacket();
/*     */   }
/*     */ 
/*     */   
/*     */   private Subject getClientSubject(Packet p) {
/* 288 */     Subject s = null;
/* 289 */     if (p != null) {
/* 290 */       s = (Subject)p.invocationProperties.get("CLIENT_SUBJECT");
/*     */     }
/*     */     
/* 293 */     if (s == null) {
/* 294 */       s = this.helper.getClientSubject();
/* 295 */       if (p != null) {
/* 296 */         p.invocationProperties.put("CLIENT_SUBJECT", s);
/*     */       }
/*     */     } 
/* 299 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\ServerSecurityPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */