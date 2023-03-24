/*    */ package com.sun.xml.ws.rx.mc.dev;
/*    */ 
/*    */ import com.oracle.webservices.api.message.BasePropertySet;
/*    */ import com.oracle.webservices.api.message.PropertySet.Property;
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.PropertySet;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import java.util.LinkedList;
/*    */ import java.util.Queue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AdditionalResponses
/*    */   extends PropertySet
/*    */ {
/*    */   public static final String ADDITIONAL_RESPONSE_QUEUE = "com.sun.xml.ws.rx.mc.api.AditionalResponses.ADDITIONAL_RESPONSE_QUEUE";
/* 59 */   private final Queue<Packet> additionalResponsePacketQueue = new LinkedList<Packet>();
/*    */ 
/*    */   
/*    */   public static PropertySet.PropertyMap getMODEL() {
/* 63 */     return MODEL;
/*    */   }
/*    */   @Property({"com.sun.xml.ws.rx.mc.api.AditionalResponses.ADDITIONAL_RESPONSE_QUEUE"})
/*    */   @NotNull
/*    */   public Queue<Packet> getAdditionalResponsePacketQueue() {
/* 68 */     return this.additionalResponsePacketQueue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   private static final PropertySet.PropertyMap MODEL = parse(AdditionalResponses.class);
/*    */ 
/*    */ 
/*    */   
/*    */   protected PropertySet.PropertyMap getPropertyMap() {
/* 82 */     return MODEL;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\dev\AdditionalResponses.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */