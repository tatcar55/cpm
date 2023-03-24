/*     */ package javanet.staxutils.events;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.events.DTD;
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
/*     */ public class DTDEvent
/*     */   extends AbstractXMLEvent
/*     */   implements DTD
/*     */ {
/*     */   protected String declaration;
/*     */   protected List entities;
/*     */   protected List notations;
/*     */   
/*     */   public DTDEvent(String declaration, Location location) {
/*  67 */     super(location);
/*  68 */     this.declaration = declaration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTDEvent(String declaration, List entities, List notations, Location location) {
/*  76 */     super(location);
/*  77 */     this.declaration = declaration;
/*  78 */     this.entities = (entities == null) ? Collections.EMPTY_LIST : entities;
/*  79 */     this.notations = (notations == null) ? Collections.EMPTY_LIST : notations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DTDEvent(DTD that) {
/*  88 */     super(that);
/*  89 */     this.declaration = that.getDocumentTypeDeclaration();
/*  90 */     this.entities = that.getEntities();
/*  91 */     this.notations = that.getNotations();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventType() {
/* 100 */     return 11;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDocumentTypeDeclaration() {
/* 106 */     return this.declaration;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getEntities() {
/* 112 */     return this.entities;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getNotations() {
/* 118 */     return this.notations;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProcessedDTD() {
/* 124 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\DTDEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */