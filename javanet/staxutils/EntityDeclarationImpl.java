/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ class EntityDeclarationImpl
/*     */   extends EventHelper
/*     */   implements EntityDeclaration
/*     */ {
/*     */   private final String entityName;
/*     */   private final String publicId;
/*     */   private final String systemId;
/*     */   private final String notationName;
/*     */   private final String replacementText;
/*     */   
/*     */   public EntityDeclarationImpl(Location location, String entityName, String publicId, String systemId, String notationName, String replacementText) {
/*  61 */     super(location);
/*  62 */     this.entityName = entityName;
/*  63 */     this.publicId = publicId;
/*  64 */     this.systemId = systemId;
/*  65 */     this.notationName = notationName;
/*  66 */     this.replacementText = replacementText;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseURI() {
/*  71 */     return null;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  75 */     return this.entityName;
/*     */   }
/*     */   
/*     */   public String getNotationName() {
/*  79 */     return this.notationName;
/*     */   }
/*     */   
/*     */   public String getPublicId() {
/*  83 */     return this.publicId;
/*     */   }
/*     */   
/*     */   public String getReplacementText() {
/*  87 */     return this.replacementText;
/*     */   }
/*     */   
/*     */   public String getSystemId() {
/*  91 */     return this.systemId;
/*     */   }
/*     */   
/*     */   public int getEventType() {
/*  95 */     return 15;
/*     */   }
/*     */   
/*     */   public boolean isEntityReference() {
/*  99 */     return true;
/*     */   }
/*     */   
/*     */   public void writeAsEncodedUnicode(Writer w) throws XMLStreamException {
/*     */     try {
/* 104 */       w.write(38);
/* 105 */       w.write(this.entityName);
/* 106 */       w.write(59);
/* 107 */     } catch (IOException ie) {
/* 108 */       throw new XMLStreamException(ie);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\EntityDeclarationImpl.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */