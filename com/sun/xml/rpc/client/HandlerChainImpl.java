/*     */ package com.sun.xml.rpc.client;
/*     */ 
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.handler.Handler;
/*     */ import javax.xml.rpc.handler.HandlerChain;
/*     */ import javax.xml.rpc.handler.HandlerInfo;
/*     */ import javax.xml.rpc.handler.MessageContext;
/*     */ import javax.xml.rpc.soap.SOAPFaultException;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPHeaderElement;
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
/*     */ public class HandlerChainImpl
/*     */   extends Vector
/*     */   implements HandlerChain
/*     */ {
/*     */   protected List handlerInfos;
/*  56 */   String[] roles = null;
/*     */   
/*     */   boolean initialized;
/*     */   
/*     */   Hashtable handlerPool;
/*     */   List understoodHeaders;
/*     */   
/*     */   private void createHandlerInstances() {
/*  64 */     for (int i = 0; i < this.handlerInfos.size(); i++)
/*  65 */       add((E)newHandler(getHandlerInfo(i))); 
/*     */   }
/*     */   
/*     */   public boolean handleFault(MessageContext _context) {
/*  69 */     SOAPMessageContext context = (SOAPMessageContext)_context;
/*     */     
/*  71 */     int n = context.getCurrentHandler();
/*     */     
/*  73 */     for (int i = n; i >= 0; i--) {
/*  74 */       context.setCurrentHandler(i);
/*     */       
/*     */       try {
/*  77 */         if (!getHandlerInstance(i).handleFault((MessageContext)context)) {
/*  78 */           return false;
/*     */         }
/*  80 */       } catch (SOAPFaultException sfe) {
/*  81 */         throw sfe;
/*  82 */       } catch (RuntimeException re) {
/*  83 */         deleteHandlerInstance(i);
/*  84 */         setElementAt((E)newHandler(getHandlerInfo(i)), i);
/*  85 */         throw re;
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     context.setCurrentHandler(-1);
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleRequest(MessageContext _context) {
/*  95 */     SOAPMessageContext context = (SOAPMessageContext)_context;
/*  96 */     context.setRoles(this.roles);
/*     */     
/*  98 */     for (int i = 0; i < size(); i++) {
/*  99 */       Handler currentHandler = getHandlerInstance(i);
/* 100 */       context.setCurrentHandler(i);
/*     */       
/*     */       try {
/* 103 */         if (!currentHandler.handleRequest((MessageContext)context)) {
/* 104 */           return false;
/*     */         }
/* 106 */       } catch (SOAPFaultException sfe) {
/* 107 */         throw sfe;
/* 108 */       } catch (RuntimeException re) {
/* 109 */         deleteHandlerInstance(i);
/* 110 */         setElementAt((E)newHandler(getHandlerInfo(i)), i);
/* 111 */         throw re;
/*     */       } 
/*     */     } 
/* 114 */     context.setCurrentHandler(-1);
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public boolean handleResponse(MessageContext _context) {
/* 119 */     if (size() > 0) {
/* 120 */       SOAPMessageContext context = (SOAPMessageContext)_context;
/*     */       
/* 122 */       int n = context.getCurrentHandler();
/*     */       
/* 124 */       if (n == -1) {
/* 125 */         n = size() - 1;
/*     */       }
/* 127 */       for (int i = n; i >= 0; i--) {
/* 128 */         context.setCurrentHandler(i);
/*     */         
/*     */         try {
/* 131 */           if (!getHandlerInstance(i).handleResponse((MessageContext)context)) {
/*     */             
/* 133 */             context.setCurrentHandler(-1);
/* 134 */             return false;
/*     */           } 
/* 136 */         } catch (SOAPFaultException sfe) {
/* 137 */           throw sfe;
/* 138 */         } catch (RuntimeException re) {
/* 139 */           deleteHandlerInstance(i);
/* 140 */           setElementAt((E)newHandler(getHandlerInfo(i)), i);
/* 141 */           throw re;
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       context.setCurrentHandler(-1);
/*     */     } 
/* 147 */     return true;
/*     */   }
/*     */   public void init(Map config) {} public void destroy() { for (int i = 0; i < size(); i++)
/* 150 */       deleteHandlerInstance(i);  clear(); } protected void deleteHandlerInstance(int index) { Handler h = getHandlerInstance(index); h.destroy(); removeHandlerFromPool(h.getClass()); } public HandlerChainImpl(List handlerInfos) { this.initialized = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     this.handlerPool = new Hashtable<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     this.understoodHeaders = new ArrayList(); this.handlerInfos = handlerInfos; createHandlerInstances(); }
/*     */   public void addHandlerInfo(int index, HandlerInfo handlerInfo) { this.handlerInfos.add(index, handlerInfo); add(index, (E)newHandler(handlerInfo)); }
/*     */   public void addHandlerInfo(HandlerInfo handlerInfo) { addHandlerInfo(this.handlerInfos.size(), handlerInfo); }
/* 243 */   protected Handler getHandlerInstance(int index) { return castToHandler(get(index)); } public void addUnderstoodHeaders(QName[] ignoredHeaders) { if (ignoredHeaders != null)
/* 244 */       for (int i = 0; i < ignoredHeaders.length; i++)
/* 245 */         this.understoodHeaders.add(ignoredHeaders[i]);   } protected HandlerInfo getHandlerInfo(int index) { return this.handlerInfos.get(index); }
/*     */   protected void removeHandlerFromPool(Class clz) { this.handlerPool.remove(clz.getName()); }
/*     */   protected Handler getHandlerFromPool(HandlerInfo handlerInfo) { Class<Handler> clz = handlerInfo.getHandlerClass(); Handler h = (Handler)this.handlerPool.get(clz.getName()); if (h == null)
/*     */       try { h = clz.newInstance(); h.init(handlerInfo); addUnderstoodHeaders(h.getHeaders()); this.handlerPool.put(clz.getName(), h); } catch (Exception ex) { throw new HandlerException("Unable to instantiate handler: ", new Object[] { handlerInfo.getHandlerClass(), new LocalizableExceptionAdapter(ex) }); }
/*     */         return h; }
/* 250 */   public boolean checkMustUnderstand(MessageContext mc) throws SOAPException { if (this.roles != null && !isEmpty()) {
/* 251 */       SOAPMessage soapMessage = ((SOAPMessageContext)mc).getMessage();
/* 252 */       SOAPHeader header = soapMessage.getSOAPPart().getEnvelope().getHeader();
/*     */       
/* 254 */       if (header == null) {
/* 255 */         return true;
/*     */       }
/* 257 */       for (int i = 0; i < this.roles.length; i++) {
/* 258 */         String actor = this.roles[i];
/* 259 */         Iterator<SOAPHeaderElement> it = header.examineMustUnderstandHeaderElements(actor);
/*     */         
/* 261 */         while (it.hasNext()) {
/* 262 */           SOAPHeaderElement element = it.next();
/* 263 */           Name saajName = element.getElementName();
/* 264 */           QName qname = new QName(saajName.getURI(), saajName.getLocalName());
/*     */           
/* 266 */           if (!this.understoodHeaders.contains(qname)) {
/* 267 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 272 */     return true; }
/*     */ 
/*     */   
/*     */   protected Handler newHandler(HandlerInfo handlerInfo) {
/*     */     return getHandlerFromPool(handlerInfo);
/*     */   }
/*     */   
/*     */   public void setRoles(String[] soapActorNames) {
/*     */     this.roles = soapActorNames;
/*     */   }
/*     */   
/*     */   public String[] getRoles() {
/*     */     return this.roles;
/*     */   }
/*     */   
/*     */   protected Handler castToHandler(Object o) {
/*     */     if (!(o instanceof Handler))
/*     */       throw new HandlerException("handler.chain.contains.handler.only", new Object[] { o.getClass().getName() }); 
/*     */     return (Handler)o;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\HandlerChainImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */