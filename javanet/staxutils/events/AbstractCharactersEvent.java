/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.events.Characters;
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
/*     */ public abstract class AbstractCharactersEvent
/*     */   extends AbstractXMLEvent
/*     */   implements Characters
/*     */ {
/*     */   protected String data;
/*     */   
/*     */   public AbstractCharactersEvent(String data) {
/*  55 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractCharactersEvent(String data, Location location) {
/*  61 */     super(location);
/*  62 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractCharactersEvent(String data, Location location, QName schemaType) {
/*  69 */     super(location, schemaType);
/*  70 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractCharactersEvent(Characters that) {
/*  76 */     super(that);
/*  77 */     this.data = that.getData();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getData() {
/*  83 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCharacters() {
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWhiteSpace() {
/*  95 */     String data = getData();
/*  96 */     for (int i = 0, s = data.length(); i < s; i++) {
/*     */       
/*  98 */       switch (data.charAt(i)) {
/*     */         case '\t':
/*     */         case '\n':
/*     */         case '\r':
/*     */         case ' ':
/*     */           break;
/*     */         
/*     */         default:
/* 106 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 112 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\AbstractCharactersEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */