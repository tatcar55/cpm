/*    */ package javanet.staxutils.events;
/*    */ 
/*    */ import javax.xml.stream.Location;
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
/*    */ public class EntityReferenceEvent
/*    */   extends AbstractXMLEvent
/*    */   implements EntityReference
/*    */ {
/*    */   protected String name;
/*    */   protected EntityDeclaration declaration;
/*    */   
/*    */   public EntityReferenceEvent(String name, EntityDeclaration declaration) {
/* 58 */     this.name = name;
/* 59 */     this.declaration = declaration;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityReferenceEvent(String name, EntityDeclaration declaration, Location location) {
/* 66 */     super(location);
/* 67 */     this.name = name;
/* 68 */     this.declaration = declaration;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityReferenceEvent(EntityReference that) {
/* 74 */     super(that);
/* 75 */     this.name = that.getName();
/* 76 */     this.declaration = that.getDeclaration();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityDeclaration getDeclaration() {
/* 82 */     return this.declaration;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 88 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventType() {
/* 95 */     return 9;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\EntityReferenceEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */