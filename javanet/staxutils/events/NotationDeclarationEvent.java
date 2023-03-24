/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.events.NotationDeclaration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NotationDeclarationEvent
/*     */   extends AbstractXMLEvent
/*     */   implements NotationDeclaration
/*     */ {
/*     */   protected String name;
/*     */   protected String publicId;
/*     */   protected String systemId;
/*     */   
/*     */   public NotationDeclarationEvent(String name, String publicId, Location location) {
/*  61 */     super(location);
/*  62 */     this.name = name;
/*  63 */     this.publicId = publicId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NotationDeclarationEvent(String name, String publicId, String systemId, Location location) {
/*  70 */     super(location);
/*  71 */     this.name = name;
/*  72 */     this.publicId = publicId;
/*  73 */     this.systemId = systemId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NotationDeclarationEvent(NotationDeclaration that) {
/*  79 */     super(that);
/*  80 */     this.name = that.getName();
/*  81 */     this.publicId = that.getPublicId();
/*  82 */     this.systemId = that.getSystemId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventType() {
/*  91 */     return 14;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  97 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/* 103 */     return this.publicId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/* 109 */     return this.systemId;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\NotationDeclarationEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */