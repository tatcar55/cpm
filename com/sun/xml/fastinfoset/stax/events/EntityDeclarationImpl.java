/*     */ package com.sun.xml.fastinfoset.stax.events;
/*     */ 
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
/*     */ public class EntityDeclarationImpl
/*     */   extends EventBase
/*     */   implements EntityDeclaration
/*     */ {
/*     */   private String _publicId;
/*     */   private String _systemId;
/*     */   private String _baseURI;
/*     */   private String _entityName;
/*     */   private String _replacement;
/*     */   private String _notationName;
/*     */   
/*     */   public EntityDeclarationImpl() {
/*  33 */     init();
/*     */   }
/*     */   
/*     */   public EntityDeclarationImpl(String entityName, String replacement) {
/*  37 */     init();
/*  38 */     this._entityName = entityName;
/*  39 */     this._replacement = replacement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPublicId() {
/*  47 */     return this._publicId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/*  55 */     return this._systemId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  63 */     return this._entityName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNotationName() {
/*  71 */     return this._notationName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReplacementText() {
/*  81 */     return this._replacement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseURI() {
/*  90 */     return this._baseURI;
/*     */   }
/*     */   
/*     */   public void setPublicId(String publicId) {
/*  94 */     this._publicId = publicId;
/*     */   }
/*     */   
/*     */   public void setSystemId(String systemId) {
/*  98 */     this._systemId = systemId;
/*     */   }
/*     */   
/*     */   public void setBaseURI(String baseURI) {
/* 102 */     this._baseURI = baseURI;
/*     */   }
/*     */   
/*     */   public void setName(String entityName) {
/* 106 */     this._entityName = entityName;
/*     */   }
/*     */   
/*     */   public void setReplacementText(String replacement) {
/* 110 */     this._replacement = replacement;
/*     */   }
/*     */   
/*     */   public void setNotationName(String notationName) {
/* 114 */     this._notationName = notationName;
/*     */   }
/*     */   
/*     */   protected void init() {
/* 118 */     setEventType(15);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\events\EntityDeclarationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */