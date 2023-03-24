/*    */ package com.sun.xml.fastinfoset.stax.events;
/*    */ 
/*    */ import javax.xml.stream.events.EntityDeclaration;
/*    */ import javax.xml.stream.events.EntityReference;
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
/*    */ public class EntityReferenceEvent
/*    */   extends EventBase
/*    */   implements EntityReference
/*    */ {
/*    */   private EntityDeclaration _entityDeclaration;
/*    */   private String _entityName;
/*    */   
/*    */   public EntityReferenceEvent() {
/* 29 */     init();
/*    */   }
/*    */   
/*    */   public EntityReferenceEvent(String entityName, EntityDeclaration entityDeclaration) {
/* 33 */     init();
/* 34 */     this._entityName = entityName;
/* 35 */     this._entityDeclaration = entityDeclaration;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 43 */     return this._entityName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityDeclaration getDeclaration() {
/* 50 */     return this._entityDeclaration;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 54 */     this._entityName = name;
/*    */   }
/*    */   
/*    */   public void setDeclaration(EntityDeclaration declaration) {
/* 58 */     this._entityDeclaration = declaration;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     String text = this._entityDeclaration.getReplacementText();
/* 63 */     if (text == null)
/* 64 */       text = ""; 
/* 65 */     return "&" + getName() + ";='" + text + "'";
/*    */   }
/*    */   
/*    */   protected void init() {
/* 69 */     setEventType(9);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\EntityReferenceEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */