/*    */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*    */ 
/*    */ import com.sun.xml.ws.security.trust.elements.Participant;
/*    */ import com.sun.xml.ws.security.trust.elements.Participants;
/*    */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ParticipantType;
/*    */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ParticipantsType;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticipantsImpl
/*    */   extends ParticipantsType
/*    */   implements Participants
/*    */ {
/*    */   public ParticipantsImpl(ParticipantsType psType) throws Exception {}
/*    */   
/*    */   public List<Participant> getParticipants() {
/* 65 */     return null;
/*    */   }
/*    */   
/*    */   public Participant getPrimaryParticipant() {
/* 69 */     return (Participant)getPrimary();
/*    */   }
/*    */   
/*    */   public void setPrimaryParticipant(Participant primary) {
/* 73 */     setPrimary((ParticipantType)primary);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\ParticipantsImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */