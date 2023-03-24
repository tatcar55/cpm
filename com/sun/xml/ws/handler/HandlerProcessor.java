/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.ProtocolException;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class HandlerProcessor<C extends MessageUpdatableContext>
/*     */ {
/*     */   boolean isClient;
/*  59 */   static final Logger logger = Logger.getLogger("com.sun.xml.ws.handler");
/*     */   private List<? extends Handler> handlers;
/*     */   WSBinding binding;
/*     */   
/*     */   public enum RequestOrResponse {
/*  64 */     REQUEST, RESPONSE; }
/*     */   
/*     */   public enum Direction {
/*  67 */     OUTBOUND, INBOUND;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  72 */   private int index = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HandlerTube owner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HandlerProcessor(HandlerTube owner, WSBinding binding, List<? extends Handler> chain) {
/*  85 */     this.owner = owner;
/*  86 */     if (chain == null) {
/*  87 */       chain = new ArrayList<Handler>();
/*     */     }
/*  89 */     this.handlers = chain;
/*  90 */     this.binding = binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getIndex() {
/*  98 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setIndex(int i) {
/* 105 */     this.index = i;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean callHandlersRequest(Direction direction, C context, boolean responseExpected) {
/*     */     boolean result;
/* 133 */     setDirection(direction, context);
/*     */ 
/*     */     
/*     */     try {
/* 137 */       if (direction == Direction.OUTBOUND) {
/* 138 */         result = callHandleMessage(context, 0, this.handlers.size() - 1);
/*     */       } else {
/* 140 */         result = callHandleMessage(context, this.handlers.size() - 1, 0);
/*     */       } 
/* 142 */     } catch (ProtocolException pe) {
/* 143 */       logger.log(Level.FINER, "exception in handler chain", pe);
/* 144 */       if (responseExpected) {
/*     */         
/* 146 */         insertFaultMessage(context, pe);
/*     */         
/* 148 */         reverseDirection(direction, context);
/*     */         
/* 150 */         setHandleFaultProperty();
/*     */         
/* 152 */         if (direction == Direction.OUTBOUND) {
/* 153 */           callHandleFault(context, getIndex() - 1, 0);
/*     */         } else {
/* 155 */           callHandleFault(context, getIndex() + 1, this.handlers.size() - 1);
/*     */         } 
/* 157 */         return false;
/*     */       } 
/* 159 */       throw pe;
/* 160 */     } catch (RuntimeException re) {
/* 161 */       logger.log(Level.FINER, "exception in handler chain", re);
/* 162 */       throw re;
/*     */     } 
/*     */     
/* 165 */     if (!result) {
/* 166 */       if (responseExpected) {
/*     */         
/* 168 */         reverseDirection(direction, context);
/*     */         
/* 170 */         if (direction == Direction.OUTBOUND) {
/* 171 */           callHandleMessageReverse(context, getIndex() - 1, 0);
/*     */         } else {
/* 173 */           callHandleMessageReverse(context, getIndex() + 1, this.handlers.size() - 1);
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 179 */         setHandleFalseProperty();
/*     */       } 
/* 181 */       return false;
/*     */     } 
/*     */     
/* 184 */     return result;
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
/*     */   public void callHandlersResponse(Direction direction, C context, boolean isFault) {
/* 200 */     setDirection(direction, context);
/*     */     try {
/* 202 */       if (isFault) {
/*     */         
/* 204 */         if (direction == Direction.OUTBOUND) {
/* 205 */           callHandleFault(context, 0, this.handlers.size() - 1);
/*     */         } else {
/* 207 */           callHandleFault(context, this.handlers.size() - 1, 0);
/*     */         }
/*     */       
/*     */       }
/* 211 */       else if (direction == Direction.OUTBOUND) {
/* 212 */         callHandleMessageReverse(context, 0, this.handlers.size() - 1);
/*     */       } else {
/* 214 */         callHandleMessageReverse(context, this.handlers.size() - 1, 0);
/*     */       }
/*     */     
/* 217 */     } catch (RuntimeException re) {
/* 218 */       logger.log(Level.FINER, "exception in handler chain", re);
/* 219 */       throw re;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void reverseDirection(Direction origDirection, C context) {
/* 229 */     if (origDirection == Direction.OUTBOUND) {
/* 230 */       context.put("javax.xml.ws.handler.message.outbound", Boolean.valueOf(false));
/*     */     } else {
/* 232 */       context.put("javax.xml.ws.handler.message.outbound", Boolean.valueOf(true));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDirection(Direction direction, C context) {
/* 241 */     if (direction == Direction.OUTBOUND) {
/* 242 */       context.put("javax.xml.ws.handler.message.outbound", Boolean.valueOf(true));
/*     */     } else {
/* 244 */       context.put("javax.xml.ws.handler.message.outbound", Boolean.valueOf(false));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setHandleFaultProperty() {
/* 253 */     this.owner.setHandleFault();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setHandleFalseProperty() {
/* 261 */     this.owner.setHandleFalse();
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
/*     */   abstract void insertFaultMessage(C paramC, ProtocolException paramProtocolException);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean callHandleMessage(C context, int start, int end) {
/* 285 */     int i = start;
/*     */     try {
/* 287 */       if (start > end) {
/* 288 */         while (i >= end) {
/* 289 */           if (!((Handler)this.handlers.get(i)).handleMessage(context)) {
/* 290 */             setIndex(i);
/* 291 */             return false;
/*     */           } 
/* 293 */           i--;
/*     */         } 
/*     */       } else {
/* 296 */         while (i <= end) {
/* 297 */           if (!((Handler)this.handlers.get(i)).handleMessage(context)) {
/* 298 */             setIndex(i);
/* 299 */             return false;
/*     */           } 
/* 301 */           i++;
/*     */         } 
/*     */       } 
/* 304 */     } catch (RuntimeException e) {
/* 305 */       setIndex(i);
/* 306 */       throw e;
/*     */     } 
/* 308 */     return true;
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
/*     */   private boolean callHandleMessageReverse(C context, int start, int end) {
/* 322 */     if (this.handlers.isEmpty() || start == -1 || start == this.handlers.size())
/*     */     {
/*     */       
/* 325 */       return false;
/*     */     }
/*     */     
/* 328 */     int i = start;
/*     */     
/* 330 */     if (start > end) {
/* 331 */       while (i >= end) {
/* 332 */         if (!((Handler)this.handlers.get(i)).handleMessage(context)) {
/*     */           
/* 334 */           setHandleFalseProperty();
/* 335 */           return false;
/*     */         } 
/* 337 */         i--;
/*     */       } 
/*     */     } else {
/* 340 */       while (i <= end) {
/* 341 */         if (!((Handler)this.handlers.get(i)).handleMessage(context)) {
/*     */           
/* 343 */           setHandleFalseProperty();
/* 344 */           return false;
/*     */         } 
/* 346 */         i++;
/*     */       } 
/*     */     } 
/* 349 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean callHandleFault(C context, int start, int end) {
/* 360 */     if (this.handlers.isEmpty() || start == -1 || start == this.handlers.size())
/*     */     {
/*     */       
/* 363 */       return false;
/*     */     }
/*     */     
/* 366 */     int i = start;
/* 367 */     if (start > end) {
/*     */       try {
/* 369 */         while (i >= end) {
/* 370 */           if (!((Handler)this.handlers.get(i)).handleFault(context)) {
/* 371 */             return false;
/*     */           }
/* 373 */           i--;
/*     */         } 
/* 375 */       } catch (RuntimeException re) {
/* 376 */         logger.log(Level.FINER, "exception in handler chain", re);
/*     */         
/* 378 */         throw re;
/*     */       } 
/*     */     } else {
/*     */       try {
/* 382 */         while (i <= end) {
/* 383 */           if (!((Handler)this.handlers.get(i)).handleFault(context)) {
/* 384 */             return false;
/*     */           }
/* 386 */           i++;
/*     */         } 
/* 388 */       } catch (RuntimeException re) {
/* 389 */         logger.log(Level.FINER, "exception in handler chain", re);
/*     */         
/* 391 */         throw re;
/*     */       } 
/*     */     } 
/* 394 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void closeHandlers(MessageContext context, int start, int end) {
/* 403 */     if (this.handlers.isEmpty() || start == -1) {
/*     */       return;
/*     */     }
/*     */     
/* 407 */     if (start > end) {
/* 408 */       for (int i = start; i >= end; i--) {
/*     */         try {
/* 410 */           ((Handler)this.handlers.get(i)).close(context);
/* 411 */         } catch (RuntimeException re) {
/* 412 */           logger.log(Level.INFO, "Exception ignored during close", re);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 417 */       for (int i = start; i <= end; i++) {
/*     */         try {
/* 419 */           ((Handler)this.handlers.get(i)).close(context);
/* 420 */         } catch (RuntimeException re) {
/* 421 */           logger.log(Level.INFO, "Exception ignored during close", re);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\HandlerProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */