/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.util.IntegerArrayList;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPDeserializationState
/*     */ {
/*     */   private static final boolean writeDebug = false;
/*     */   private static final int CREATION_GATES_CONSTRUCTION = 5;
/*     */   private static final int INITIALIZATION_GATES_CONSTRUCTION = 17;
/*     */   private static final int COMPLETION_GATES_CONSTRUCTION = 9;
/*     */   private static final int CREATION_GATES_INITIALIZATION = 6;
/*     */   private static final int INITIALIZATION_GATES_INITIALIZATION = 10;
/*     */   private static final int COMPLETION_GATES_INITIALIZATION = 18;
/*     */   private static final int NO_STATE = 0;
/*     */   private static final int CREATED_STATE = 4;
/*     */   private static final int INITIALIZED_STATE = 8;
/*     */   private static final int COMPLETE_STATE = 16;
/*     */   private static final int CREATION_EVENT = 4;
/*     */   private static final int INITIALIZATION_EVENT = 8;
/*     */   private static final int COMPLETION_EVENT = 16;
/*     */   private static final int EVENT_BIT_MASK = 28;
/*     */   private static final int CREATION_GATE = 1;
/*     */   private static final int INITIALIZATION_GATE = 2;
/*     */   private static final int GATE_BIT_MASK = 3;
/*  89 */   private JAXRPCDeserializer deserializer = null;
/*  90 */   private SOAPInstanceBuilder builder = null;
/*  91 */   private List listeners = new ArrayList();
/*  92 */   private IntegerArrayList listenerMembers = new IntegerArrayList();
/*  93 */   private int constructionGates = 0;
/*  94 */   private int initializationGates = 0;
/*  95 */   private int completionGates = 0;
/*     */   private boolean hasBeenRead = false;
/*  97 */   private int state = 0;
/*  98 */   private Object instance = null;
/*  99 */   private XMLReader recordedElement = null;
/* 100 */   private QName recordedElementExpectedName = null;
/*     */   private SOAPDeserializationContext recordedElementDeserialzationContext;
/*     */   
/*     */   public boolean isCompleteForKnownMembers() {
/* 104 */     return (this.completionGates == 0);
/*     */   }
/*     */   
/*     */   public boolean isComplete() {
/* 108 */     return (this.state == 16);
/*     */   }
/*     */   
/*     */   public void promoteToCompleteOrFail() {
/* 112 */     switch (this.state) {
/*     */       case 0:
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 16:
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 8:
/* 124 */         this.state = 16;
/*     */         return;
/*     */     } 
/* 127 */     throw new DeserializationException("soap.incompleteObject");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerListener(SOAPDeserializationState parentState, int memberIndex) {
/* 137 */     if (this.deserializer == null) {
/* 138 */       throw new DeserializationException("soap.state.wont.notify.without.deserializer");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     this.listeners.add(parentState);
/* 152 */     this.listenerMembers.add(memberIndex);
/*     */     
/* 154 */     parentState.waitFor(memberIndex);
/*     */     
/* 156 */     sendPastEventsTo(parentState, memberIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPastEventsTo(SOAPDeserializationState listener, int memberIndex) {
/* 163 */     int pastState = 0;
/*     */     
/* 165 */     while (pastState != this.state) {
/* 166 */       switch (pastState) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 0:
/* 177 */           listener.setMember(memberIndex, getInstance());
/* 178 */           pastState = 4;
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 4:
/* 190 */           pastState = 8;
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 8:
/* 202 */           pastState = 16;
/*     */           break;
/*     */       } 
/* 205 */       listener.beNotified(memberIndex, pastState);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void waitFor(int memberIndex) {
/* 210 */     switch (memberGateType(memberIndex)) {
/*     */       case 5:
/*     */       case 9:
/*     */       case 17:
/* 214 */         this.constructionGates++;
/*     */         break;
/*     */       case 6:
/*     */       case 10:
/*     */       case 18:
/* 219 */         this.initializationGates++;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 224 */     this.completionGates++;
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
/*     */   public void beNotified(int memberIndex, int event) {
/* 240 */     int gateType = memberGateType(memberIndex);
/* 241 */     int watchedEvent = gateType & 0x1C;
/*     */     
/* 243 */     if (event == watchedEvent) {
/* 244 */       int gatedState = gateType & 0x3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 250 */       switch (gatedState) {
/*     */         case 1:
/* 252 */           this.constructionGates--;
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 262 */           this.initializationGates--;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 274 */     if (event == 16) {
/* 275 */       this.completionGates--;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     updateState();
/*     */   }
/*     */   
/*     */   private void updateState() {
/* 289 */     switch (this.state) {
/*     */       case 0:
/* 291 */         if (this.constructionGates > 0) {
/*     */           return;
/*     */         }
/* 294 */         if (this.instance == null && this.builder != null) {
/* 295 */           this.builder.construct();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 300 */         changeStateTo(4);
/*     */       case 4:
/* 302 */         if (this.initializationGates > 0 || !this.hasBeenRead) {
/*     */           return;
/*     */         }
/* 305 */         if (this.builder != null) {
/* 306 */           this.builder.initialize();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 312 */         changeStateTo(8);
/*     */       case 8:
/* 314 */         if (this.completionGates > 0) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 321 */         changeStateTo(16);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   private void changeStateTo(int newState) {
/* 326 */     this.state = newState;
/* 327 */     notifyListeners();
/*     */   }
/*     */   
/*     */   private void notifyListeners() {
/* 331 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 332 */       SOAPDeserializationState eachListener = this.listeners.get(i);
/*     */       
/* 334 */       int listenerMember = this.listenerMembers.get(i);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 339 */       if (this.state == 4) {
/* 340 */         eachListener.setMember(listenerMember, getInstance());
/*     */       }
/*     */       
/* 343 */       eachListener.beNotified(listenerMember, this.state);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int memberGateType(int memberIndex) {
/* 348 */     if (this.builder == null) {
/* 349 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 352 */     return this.builder.memberGateType(memberIndex);
/*     */   }
/*     */   
/*     */   public void setInstance(Object instance) {
/* 356 */     this.instance = instance;
/*     */     
/* 358 */     if (this.builder != null) {
/* 359 */       this.builder.setInstance(instance);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setMember(int memberIndex, Object value) {
/* 364 */     if (this.builder == null) {
/* 365 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 368 */     this.builder.setMember(memberIndex, value);
/*     */   }
/*     */   
/*     */   public void setBuilder(SOAPInstanceBuilder newBuilder) {
/* 372 */     if (newBuilder == null) {
/* 373 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 376 */     if (this.builder != null && this.builder != newBuilder) {
/* 377 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 380 */     this.builder = newBuilder;
/* 381 */     this.builder.setInstance(this.instance);
/*     */   }
/*     */   
/*     */   public SOAPInstanceBuilder getBuilder() {
/* 385 */     return this.builder;
/*     */   }
/*     */   
/*     */   public void setDeserializer(JAXRPCDeserializer deserializer) {
/*     */     try {
/* 390 */       if (deserializer == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 395 */       if (this.deserializer != null) {
/*     */         return;
/*     */       }
/*     */       
/* 399 */       this.deserializer = deserializer;
/*     */       
/* 401 */       if (this.recordedElement != null) {
/* 402 */         deserialize(this.recordedElementExpectedName, this.recordedElement, this.recordedElementDeserialzationContext);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 407 */     catch (JAXRPCExceptionBase e) {
/* 408 */       throw new DeserializationException(e);
/* 409 */     } catch (Exception e) {
/* 410 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doneReading() {
/* 419 */     this.hasBeenRead = true;
/* 420 */     updateState();
/*     */   }
/*     */   
/*     */   public Object getInstance() {
/* 424 */     if (this.builder == null) {
/* 425 */       return this.instance;
/*     */     }
/*     */     
/* 428 */     return this.builder.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/*     */     try {
/* 437 */       if (this.deserializer == null) {
/* 438 */         this.recordedElementExpectedName = name;
/* 439 */         this.recordedElement = reader.recordElement();
/* 440 */         this.recordedElementDeserialzationContext = context;
/*     */         
/*     */         return;
/*     */       } 
/* 444 */       this.deserializer.deserialize(name, reader, context);
/* 445 */     } catch (DeserializationException e) {
/* 446 */       throw e;
/* 447 */     } catch (JAXRPCExceptionBase e) {
/* 448 */       throw new DeserializationException(e);
/* 449 */     } catch (Exception e) {
/* 450 */       e.printStackTrace();
/* 451 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deserialize(DataHandler dataHandler, SOAPDeserializationContext context) throws DeserializationException {
/*     */     try {
/* 462 */       if (this.deserializer == null) {
/* 463 */         throw new DeserializationException("deserializationstate.deserialize.no.deserializer");
/*     */       }
/*     */       
/* 466 */       this.deserializer.deserialize(dataHandler, context);
/* 467 */     } catch (JAXRPCExceptionBase e) {
/* 468 */       throw new DeserializationException(e);
/* 469 */     } catch (Exception e) {
/* 470 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String stringRep() {
/* 476 */     StringBuffer rep = new StringBuffer("" + hashCode() + ":");
/* 477 */     if (getInstance() != null) {
/* 478 */       String instanceClassName = getInstance().getClass().getName();
/* 479 */       int lastDotLoc = instanceClassName.lastIndexOf('.');
/* 480 */       rep.append(instanceClassName.substring(lastDotLoc));
/*     */     } 
/* 482 */     rep.append(":");
/* 483 */     if (this.deserializer != null) {
/* 484 */       String deserializerClassName = this.deserializer.getClass().getName();
/* 485 */       int lastDotLoc = deserializerClassName.lastIndexOf('.');
/* 486 */       rep.append(deserializerClassName.substring(lastDotLoc));
/*     */     } 
/* 488 */     return rep.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SOAPDeserializationState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */