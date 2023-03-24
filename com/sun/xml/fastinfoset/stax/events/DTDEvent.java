/*    */ package com.sun.xml.fastinfoset.stax.events;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.xml.stream.events.DTD;
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
/*    */ public class DTDEvent
/*    */   extends EventBase
/*    */   implements DTD
/*    */ {
/*    */   private String _dtd;
/*    */   private List _notations;
/*    */   private List _entities;
/*    */   
/*    */   public DTDEvent() {
/* 37 */     setEventType(11);
/*    */   }
/*    */   
/*    */   public DTDEvent(String dtd) {
/* 41 */     setEventType(11);
/* 42 */     this._dtd = dtd;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDocumentTypeDeclaration() {
/* 54 */     return this._dtd;
/*    */   }
/*    */   public void setDTD(String dtd) {
/* 57 */     this._dtd = dtd;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List getEntities() {
/* 68 */     return this._entities;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List getNotations() {
/* 78 */     return this._notations;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getProcessedDTD() {
/* 87 */     return null;
/*    */   }
/*    */   
/*    */   public void setEntities(List entites) {
/* 91 */     this._entities = entites;
/*    */   }
/*    */   
/*    */   public void setNotations(List notations) {
/* 95 */     this._notations = notations;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 99 */     return this._dtd;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\DTDEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */