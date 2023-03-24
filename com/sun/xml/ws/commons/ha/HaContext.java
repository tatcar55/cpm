/*     */ package com.sun.xml.ws.commons.ha;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HaContext
/*     */ {
/*  54 */   private static final Logger LOGGER = Logger.getLogger(HaContext.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class State
/*     */   {
/*  61 */     private static final State EMPTY = new State(null, null);
/*     */     
/*     */     private final Packet packet;
/*     */     private final HaInfo haInfo;
/*     */     
/*     */     private State(Packet packet, HaInfo haInfo) {
/*  67 */       this.packet = packet;
/*  68 */       this.haInfo = haInfo;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  73 */       return "HaState{packet=" + this.packet + ", haInfo=" + HaContext.asString(this.haInfo) + '}';
/*     */     }
/*     */   }
/*     */   
/*  77 */   private static final ThreadLocal<State> state = new ThreadLocal<State>()
/*     */     {
/*     */       protected HaContext.State initialValue()
/*     */       {
/*  81 */         return HaContext.State.EMPTY;
/*     */       }
/*     */     };
/*     */   
/*     */   public static State initFrom(Packet packet) {
/*  86 */     State oldState = state.get();
/*     */     
/*  88 */     HaInfo haInfo = null;
/*  89 */     if (packet != null && packet.supports("com.sun.xml.ws.api.message.packet.hainfo")) {
/*  90 */       haInfo = (HaInfo)packet.get("com.sun.xml.ws.api.message.packet.hainfo");
/*     */     }
/*  92 */     State newState = new State(packet, haInfo);
/*  93 */     state.set(newState);
/*  94 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  95 */       LOGGER.finer("[METRO-HA] " + Thread.currentThread().toString() + " : Initialized from packet - replaced old " + oldState.toString() + " with a new " + newState.toString());
/*     */     }
/*     */     
/*  98 */     return oldState;
/*     */   }
/*     */   
/*     */   public static State initFrom(State newState) {
/* 102 */     State oldState = state.get();
/*     */     
/* 104 */     state.set(newState);
/* 105 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 106 */       LOGGER.finer("[METRO-HA] " + Thread.currentThread().toString() + " : Initialized from state - replaced old " + oldState.toString() + " with a new " + newState.toString());
/*     */     }
/*     */     
/* 109 */     return oldState;
/*     */   }
/*     */   
/*     */   public static State currentState() {
/* 113 */     return state.get();
/*     */   }
/*     */   
/*     */   public static void clear() {
/* 117 */     state.set(State.EMPTY);
/* 118 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 119 */       LOGGER.finer("[METRO-HA] " + Thread.currentThread().toString() + " : Current HA state cleared");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static HaInfo currentHaInfo() {
/* 125 */     return (state.get()).haInfo;
/*     */   }
/*     */   
/*     */   public static void udpateReplicaInstance(String replicaInstance) {
/* 129 */     boolean updateNeeded = false;
/* 130 */     State currentState = state.get();
/*     */     
/* 132 */     if (currentState.haInfo == null) {
/* 133 */       throw new IllegalStateException("Unable to update replicaInstance. Current HaInfo in the local thread is null.");
/*     */     }
/*     */     
/* 136 */     if (replicaInstance == null) {
/* 137 */       updateNeeded = (currentState.haInfo.getReplicaInstance() != null);
/*     */     } else {
/* 139 */       updateNeeded = !replicaInstance.equals(currentState.haInfo.getReplicaInstance());
/*     */     } 
/*     */     
/* 142 */     if (updateNeeded) {
/* 143 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 144 */         LOGGER.finer("[METRO-HA] " + Thread.currentThread().toString() + " : Replica instance value changed to '" + replicaInstance + "'. Updating current HaInfo.");
/*     */       }
/*     */       
/* 147 */       HaInfo old = currentState.haInfo;
/* 148 */       updateHaInfo(new HaInfo(old.getKey(), replicaInstance, old.isFailOver()));
/* 149 */     } else if (LOGGER.isLoggable(Level.FINER)) {
/* 150 */       LOGGER.finer("[METRO-HA] " + Thread.currentThread().toString() + " : New replica instance value '" + replicaInstance + "' same as current - no HaInfo update necessary");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void updateHaInfo(HaInfo newValue) {
/* 155 */     Packet packet = (state.get()).packet;
/* 156 */     state.set(new State(packet, newValue));
/* 157 */     if (packet != null && packet.supports("com.sun.xml.ws.api.message.packet.hainfo")) {
/* 158 */       packet.put("com.sun.xml.ws.api.message.packet.hainfo", newValue);
/*     */     }
/*     */     
/* 161 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 162 */       LOGGER.finer("[METRO-HA] " + Thread.currentThread().toString() + " : HaInfo value updated: " + asString(newValue));
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean failoverDetected() {
/* 167 */     HaInfo haInfo = (state.get()).haInfo;
/* 168 */     return (haInfo != null && haInfo.isFailOver());
/*     */   }
/*     */   
/*     */   public static String asString(HaInfo haInfo) {
/* 172 */     if (haInfo == null) {
/* 173 */       return "null";
/*     */     }
/*     */     
/* 176 */     return "HaInfo{hashableKey=" + haInfo.getKey() + ", replicaInstance=" + haInfo.getReplicaInstance() + ", isFailover=" + haInfo.isFailOver() + "}";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\commons\ha\HaContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */