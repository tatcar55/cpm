/*     */ package com.sun.xml.rpc.processor.schema;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class ComplexTypeDefinitionComponent
/*     */   extends TypeDefinitionComponent
/*     */ {
/*     */   public static final int CONTENT_EMPTY = 1;
/*     */   public static final int CONTENT_SIMPLE = 2;
/*     */   public static final int CONTENT_MIXED = 3;
/*     */   public static final int CONTENT_ELEMENT_ONLY = 4;
/*     */   private TypeDefinitionComponent _baseTypeDefinition;
/*     */   private Symbol _derivationMethod;
/*     */   private Set _final;
/*     */   private boolean _abstract;
/*  46 */   private List _attributeUses = new ArrayList(); private WildcardComponent _attributeWildcard; private int _contentTag;
/*     */   private SimpleTypeDefinitionComponent _simpleTypeContent;
/*     */   
/*     */   public boolean isComplex() {
/*  50 */     return true;
/*     */   }
/*     */   private ParticleComponent _particleContent; private Set _prohibitedSubstitutions; private List _annotations;
/*     */   public TypeDefinitionComponent getBaseTypeDefinition() {
/*  54 */     return this._baseTypeDefinition;
/*     */   }
/*     */   
/*     */   public void setBaseTypeDefinition(TypeDefinitionComponent c) {
/*  58 */     this._baseTypeDefinition = c;
/*     */   }
/*     */   
/*     */   public Symbol getDerivationMethod() {
/*  62 */     return this._derivationMethod;
/*     */   }
/*     */   
/*     */   public void setDerivationMethod(Symbol s) {
/*  66 */     this._derivationMethod = s;
/*     */   }
/*     */   
/*     */   public void setProhibitedSubstitutions(Set s) {
/*  70 */     this._prohibitedSubstitutions = s;
/*     */   }
/*     */   
/*     */   public void setFinal(Set s) {
/*  74 */     this._final = s;
/*     */   }
/*     */   
/*     */   public boolean isAbstract() {
/*  78 */     return this._abstract;
/*     */   }
/*     */   
/*     */   public void setAbstract(boolean b) {
/*  82 */     this._abstract = b;
/*     */   }
/*     */   
/*     */   public Iterator attributeUses() {
/*  86 */     return this._attributeUses.iterator();
/*     */   }
/*     */   
/*     */   public boolean hasNoAttributeUses() {
/*  90 */     return (this._attributeUses.size() == 0);
/*     */   }
/*     */   
/*     */   public void addAttributeUse(AttributeUseComponent c) {
/*  94 */     this._attributeUses.add(c);
/*     */   }
/*     */   
/*     */   public void addAttributeGroup(AttributeGroupDefinitionComponent c) {
/*  98 */     for (Iterator<AttributeUseComponent> iter = c.attributeUses(); iter.hasNext(); ) {
/*  99 */       AttributeUseComponent a = iter.next();
/* 100 */       addAttributeUse(a);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getContentTag() {
/* 105 */     return this._contentTag;
/*     */   }
/*     */   
/*     */   public void setContentTag(int i) {
/* 109 */     this._contentTag = i;
/*     */   }
/*     */   
/*     */   public SimpleTypeDefinitionComponent getSimpleTypeContent() {
/* 113 */     return this._simpleTypeContent;
/*     */   }
/*     */   
/*     */   public void setSimpleTypeContent(SimpleTypeDefinitionComponent c) {
/* 117 */     this._simpleTypeContent = c;
/*     */   }
/*     */   
/*     */   public ParticleComponent getParticleContent() {
/* 121 */     return this._particleContent;
/*     */   }
/*     */   
/*     */   public void setParticleContent(ParticleComponent c) {
/* 125 */     this._particleContent = c;
/*     */   }
/*     */   
/*     */   public void accept(ComponentVisitor visitor) throws Exception {
/* 129 */     visitor.visit(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\ComplexTypeDefinitionComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */