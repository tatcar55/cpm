/*     */ package com.sun.xml.rpc.processor.schema;
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
/*     */ public class ParticleComponent
/*     */   extends Component
/*     */ {
/*     */   public static final int TERM_MODEL_GROUP = 1;
/*     */   public static final int TERM_WILDCARD = 2;
/*     */   public static final int TERM_ELEMENT = 3;
/*     */   private int _minOccurs;
/*     */   private int _maxOccurs;
/*     */   private int _termTag;
/*     */   private ModelGroupComponent _modelGroupTerm;
/*     */   private WildcardComponent _wildcardTerm;
/*     */   private ElementDeclarationComponent _elementTerm;
/*     */   private static final int UNBOUNDED = -1;
/*     */   
/*     */   public int getMinOccurs() {
/*  42 */     return this._minOccurs;
/*     */   }
/*     */   
/*     */   public void setMinOccurs(int i) {
/*  46 */     this._minOccurs = i;
/*     */   }
/*     */   
/*     */   public int getMaxOccurs() {
/*  50 */     if (this._maxOccurs == -1) {
/*  51 */       throw new IllegalStateException();
/*     */     }
/*  53 */     return this._maxOccurs;
/*     */   }
/*     */   
/*     */   public void setMaxOccurs(int i) {
/*  57 */     this._maxOccurs = i;
/*     */   }
/*     */   
/*     */   public boolean isMaxOccursUnbounded() {
/*  61 */     return (this._maxOccurs == -1);
/*     */   }
/*     */   
/*     */   public void setMaxOccursUnbounded() {
/*  65 */     this._maxOccurs = -1;
/*     */   }
/*     */   
/*     */   public boolean doesNotOccur() {
/*  69 */     return (this._minOccurs == 0 && this._maxOccurs == 0);
/*     */   }
/*     */   
/*     */   public boolean occursOnce() {
/*  73 */     return (this._minOccurs == 1 && this._maxOccurs == 1);
/*     */   }
/*     */   
/*     */   public boolean occursAtMostOnce() {
/*  77 */     return (this._minOccurs <= 1 && this._maxOccurs == 1);
/*     */   }
/*     */   
/*     */   public boolean occursAtLeastOnce() {
/*  81 */     return (this._minOccurs >= 1);
/*     */   }
/*     */   
/*     */   public boolean occursZeroOrMore() {
/*  85 */     return (this._minOccurs == 0 && this._maxOccurs == -1);
/*     */   }
/*     */   
/*     */   public boolean occursOnceOrMore() {
/*  89 */     return (this._minOccurs == 1 && this._maxOccurs == -1);
/*     */   }
/*     */   
/*     */   public boolean mayOccurMoreThanOnce() {
/*  93 */     return (this._maxOccurs > 1 || this._maxOccurs == -1);
/*     */   }
/*     */   
/*     */   public int getTermTag() {
/*  97 */     return this._termTag;
/*     */   }
/*     */   
/*     */   public void setTermTag(int i) {
/* 101 */     this._termTag = i;
/*     */   }
/*     */   
/*     */   public ModelGroupComponent getModelGroupTerm() {
/* 105 */     return this._modelGroupTerm;
/*     */   }
/*     */   
/*     */   public void setModelGroupTerm(ModelGroupComponent c) {
/* 109 */     this._modelGroupTerm = c;
/*     */   }
/*     */   
/*     */   public ElementDeclarationComponent getElementTerm() {
/* 113 */     return this._elementTerm;
/*     */   }
/*     */   
/*     */   public void setElementTerm(ElementDeclarationComponent c) {
/* 117 */     this._elementTerm = c;
/*     */   }
/*     */   
/*     */   public WildcardComponent getWildcardTerm() {
/* 121 */     return this._wildcardTerm;
/*     */   }
/*     */   
/*     */   public void setWildcardTerm(WildcardComponent c) {
/* 125 */     this._wildcardTerm = c;
/*     */   }
/*     */   
/*     */   public void accept(ComponentVisitor visitor) throws Exception {
/* 129 */     visitor.visit(this);
/*     */   }
/*     */   
/*     */   public boolean occursZeroOrOne() {
/* 133 */     return (this._minOccurs == 0 && this._maxOccurs == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\ParticleComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */