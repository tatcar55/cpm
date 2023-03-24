/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.events.EntityDeclaration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityDeclarationEvent
/*     */   extends AbstractXMLEvent
/*     */   implements EntityDeclaration
/*     */ {
/*     */   protected String name;
/*     */   protected String replacementText;
/*     */   protected String baseURI;
/*     */   protected String publicId;
/*     */   protected String systemId;
/*     */   protected String notationName;
/*     */   
/*     */   public EntityDeclarationEvent(String name, String replacementText, Location location) {
/*  74 */     super(location);
/*  75 */     this.name = name;
/*  76 */     this.replacementText = replacementText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityDeclarationEvent(String name, String replacementText, String notationName, Location location) {
/*  83 */     super(location);
/*  84 */     this.name = name;
/*  85 */     this.replacementText = replacementText;
/*  86 */     this.notationName = notationName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityDeclarationEvent(String name, String publicId, String systemId, String baseURI, String notationName, Location location) {
/*  94 */     super(location);
/*  95 */     this.name = name;
/*  96 */     this.publicId = publicId;
/*  97 */     this.systemId = systemId;
/*  98 */     this.baseURI = baseURI;
/*  99 */     this.notationName = notationName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityDeclarationEvent(EntityDeclaration that) {
/* 105 */     super(that);
/* 106 */     this.name = that.getName();
/* 107 */     this.replacementText = that.getReplacementText();
/* 108 */     this.publicId = that.getPublicId();
/* 109 */     this.systemId = that.getSystemId();
/* 110 */     this.baseURI = that.getBaseURI();
/* 111 */     this.notationName = that.getNotationName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventType() {
/* 120 */     return 15;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseURI() {
/* 126 */     return this.baseURI;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 132 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNotationName() {
/* 138 */     return this.notationName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/* 144 */     return this.publicId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReplacementText() {
/* 150 */     return this.replacementText;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/* 156 */     return this.systemId;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\EntityDeclarationEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */