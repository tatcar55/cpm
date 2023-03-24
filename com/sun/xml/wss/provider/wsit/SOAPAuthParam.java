/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPAuthParam
/*     */   implements AuthParam
/*     */ {
/*     */   private HashMap<Object, Object> infoMap;
/*     */   private boolean requestInPacket;
/*     */   private boolean responseInPacket;
/*     */   private SOAPMessage request;
/*     */   private SOAPMessage response;
/*  76 */   private static Exception classLoadingException = checkForPacket();
/*     */   
/*     */   private static final String REQ_PACKET = "REQ_PACKET";
/*     */   
/*     */   private static final String RES_PACKET = "RES_PACKET";
/*     */   private static boolean REQUEST_PACKET = true;
/*     */   private static boolean RESPONSE_PACKET = false;
/*     */   
/*     */   private static Exception checkForPacket() {
/*  85 */     Exception rvalue = null;
/*     */     try {
/*  87 */       if (Class.forName("com.sun.xml.ws.api.message.Packet") == null || Class.forName("com.sun.xml.ws.api.message.Messages") != null);
/*     */     
/*     */     }
/*  90 */     catch (Exception e) {
/*     */       
/*  92 */       rvalue = e;
/*     */     } 
/*  94 */     return rvalue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPAuthParam(SOAPMessage request, SOAPMessage response) {
/* 104 */     this.infoMap = null;
/* 105 */     this.request = request;
/* 106 */     this.response = response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPAuthParam(Object request, Object response, int dummy) {
/* 117 */     if (classLoadingException != null) {
/* 118 */       throw new RuntimeException(classLoadingException);
/*     */     }
/* 120 */     if ((request == null || request instanceof Packet) && (response == null || response instanceof Packet)) {
/*     */       
/* 122 */       this.infoMap = new HashMap<Object, Object>();
/* 123 */       this.infoMap.put("REQ_PACKET", request);
/* 124 */       this.infoMap.put("RES_PACKET", response);
/* 125 */       this.requestInPacket = !(request == null);
/* 126 */       this.responseInPacket = !(response == null);
/*     */     } else {
/* 128 */       throw new RuntimeException("argument is not packet");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Object, Object> getMap() {
/* 139 */     if (this.infoMap == null) {
/* 140 */       this.infoMap = new HashMap<Object, Object>();
/*     */     }
/* 142 */     return this.infoMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage getRequest() {
/* 152 */     if (this.request == null) {
/*     */       
/* 154 */       Object p = getPacket(REQUEST_PACKET, true);
/*     */       
/* 156 */       if (p != null && this.requestInPacket)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 162 */         this.request = getSOAPFromPacket(REQUEST_PACKET, p);
/*     */       }
/*     */     } 
/*     */     
/* 166 */     return this.request;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage getResponse() {
/* 176 */     if (this.response == null) {
/*     */       
/* 178 */       Object p = getPacket(RESPONSE_PACKET, false);
/*     */       
/* 180 */       if (p != null && this.responseInPacket)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 186 */         this.response = getSOAPFromPacket(RESPONSE_PACKET, p);
/*     */       }
/*     */     } 
/*     */     
/* 190 */     return this.response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRequest(SOAPMessage request) {
/* 199 */     Object p = getPacket(REQUEST_PACKET, false);
/* 200 */     if (p != null) {
/* 201 */       this.requestInPacket = putSOAPInPacket(request, p);
/*     */     }
/* 203 */     this.request = request;
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
/*     */   public void setResponse(SOAPMessage response) {
/* 219 */     Object p = getPacket(RESPONSE_PACKET, false);
/* 220 */     if (p != null) {
/* 221 */       this.responseInPacket = putSOAPInPacket(response, p);
/*     */     }
/* 223 */     this.response = response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getRequestPacket() {
/* 232 */     if (classLoadingException != null) {
/* 233 */       throw new RuntimeException(classLoadingException);
/*     */     }
/* 235 */     return getPacket(REQUEST_PACKET, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getResponsePacket() {
/* 244 */     if (classLoadingException != null) {
/* 245 */       throw new RuntimeException(classLoadingException);
/*     */     }
/* 247 */     return getPacket(RESPONSE_PACKET, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRequestPacket(Object p) {
/* 258 */     if (classLoadingException != null) {
/* 259 */       throw new RuntimeException(classLoadingException);
/*     */     }
/* 261 */     if (p == null || p instanceof Packet) {
/* 262 */       getMap().put("REQ_PACKET", p);
/* 263 */       this.requestInPacket = !(p == null);
/* 264 */       this.request = null;
/*     */     } else {
/* 266 */       throw new RuntimeException("argument is not packet");
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
/*     */   public void setResponsePacket(Object p) {
/* 278 */     if (classLoadingException != null) {
/* 279 */       throw new RuntimeException(classLoadingException);
/*     */     }
/* 281 */     if (p == null || p instanceof Packet) {
/* 282 */       getMap().put("RES_PACKET", p);
/* 283 */       this.responseInPacket = !(p == null);
/* 284 */       this.response = null;
/*     */     } else {
/* 286 */       throw new RuntimeException("argument is not packet");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getPacket(boolean isRequestPacket, boolean putDesired) {
/* 297 */     Object p = (this.infoMap == null) ? null : this.infoMap.get(isRequestPacket ? "REQ_PACKET" : "RES_PACKET");
/*     */ 
/*     */ 
/*     */     
/* 301 */     if (putDesired) {
/*     */       
/* 303 */       SOAPMessage m = isRequestPacket ? this.request : this.response;
/*     */       
/* 305 */       if (p != null && m != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 311 */         if (isRequestPacket) {
/* 312 */           if (!this.requestInPacket) {
/* 313 */             this.requestInPacket = putSOAPInPacket(m, p);
/*     */           }
/*     */         }
/* 316 */         else if (!this.responseInPacket) {
/* 317 */           this.responseInPacket = putSOAPInPacket(m, p);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 323 */     return p;
/*     */   }
/*     */   
/*     */   private SOAPMessage getSOAPFromPacket(boolean isRequestPacket, Object p) {
/* 327 */     if (classLoadingException != null) {
/* 328 */       throw new RuntimeException(classLoadingException);
/*     */     }
/* 330 */     SOAPMessage s = null;
/* 331 */     if (p instanceof Packet) {
/* 332 */       Message m = ((Packet)p).getMessage();
/* 333 */       if (m != null) {
/*     */         try {
/* 335 */           s = m.readAsSOAPMessage();
/* 336 */         } catch (Exception e) {
/* 337 */           throw new RuntimeException(e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 342 */     if (s != null)
/*     */     {
/*     */       
/* 345 */       if (isRequestPacket) {
/* 346 */         this.requestInPacket = false;
/*     */       } else {
/* 348 */         this.responseInPacket = false;
/*     */       } 
/*     */     }
/* 351 */     return s;
/*     */   }
/*     */   
/*     */   private boolean putSOAPInPacket(SOAPMessage m, Object p) {
/* 355 */     if (m == null) {
/* 356 */       ((Packet)p).setMessage(null);
/*     */     } else {
/* 358 */       Message msg = Messages.create(m);
/* 359 */       ((Packet)p).setMessage(msg);
/*     */     } 
/* 361 */     return true;
/*     */   }
/*     */   
/*     */   public static void printSOAP(SOAPMessage s) {
/*     */     try {
/* 366 */       if (s != null) {
/* 367 */         s.writeTo(System.out);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 372 */     catch (Exception e) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\SOAPAuthParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */