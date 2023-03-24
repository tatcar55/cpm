/*     */ package com.sun.xml.rpc.processor.schema;
/*     */ 
/*     */ import com.sun.xml.rpc.util.NullIterator;
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
/*     */ public class SimpleTypeDefinitionComponent
/*     */   extends TypeDefinitionComponent
/*     */ {
/*     */   public static final int VARIETY_ATOMIC = 1;
/*     */   public static final int VARIETY_LIST = 2;
/*     */   public static final int VARIETY_UNION = 3;
/*     */   private SimpleTypeDefinitionComponent _baseTypeDefinition;
/*     */   private List _facets;
/*     */   private List _fundamentalFacets;
/*     */   private Set _final;
/*     */   private int _varietyTag;
/*     */   private SimpleTypeDefinitionComponent _primitiveTypeDefinition;
/*     */   private SimpleTypeDefinitionComponent _itemTypeDefinition;
/*     */   private List _memberTypeDefinitions;
/*     */   private AnnotationComponent _annotation;
/*     */   
/*     */   public boolean isSimple() {
/*  49 */     return true;
/*     */   }
/*     */   
/*     */   public SimpleTypeDefinitionComponent getBaseTypeDefinition() {
/*  53 */     return this._baseTypeDefinition;
/*     */   }
/*     */   
/*     */   public void setBaseTypeDefinition(SimpleTypeDefinitionComponent c) {
/*  57 */     this._baseTypeDefinition = c;
/*     */   }
/*     */   
/*     */   public SimpleTypeDefinitionComponent getPrimitiveTypeDefinition() {
/*  61 */     return this._primitiveTypeDefinition;
/*     */   }
/*     */   
/*     */   public void setPrimitiveTypeDefinition(SimpleTypeDefinitionComponent c) {
/*  65 */     this._primitiveTypeDefinition = c;
/*     */   }
/*     */   
/*     */   public SimpleTypeDefinitionComponent getItemTypeDefinition() {
/*  69 */     return this._itemTypeDefinition;
/*     */   }
/*     */   
/*     */   public void setItemTypeDefinition(SimpleTypeDefinitionComponent c) {
/*  73 */     this._itemTypeDefinition = c;
/*     */   }
/*     */   
/*     */   public void setFinal(Set s) {
/*  77 */     this._final = s;
/*     */   }
/*     */   
/*     */   public int getVarietyTag() {
/*  81 */     return this._varietyTag;
/*     */   }
/*     */   
/*     */   public void setVarietyTag(int i) {
/*  85 */     this._varietyTag = i;
/*     */   }
/*     */   
/*     */   public void addFacet(Facet f) {
/*  89 */     if (this._facets == null) {
/*  90 */       this._facets = new ArrayList();
/*     */     }
/*     */     
/*  93 */     this._facets.add(f);
/*     */   }
/*     */   
/*     */   public Iterator facets() {
/*  97 */     return (this._facets == null) ? (Iterator)NullIterator.getInstance() : this._facets.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFundamentalFacet(FundamentalFacet f) {
/* 102 */     if (this._fundamentalFacets == null) {
/* 103 */       this._fundamentalFacets = new ArrayList();
/*     */     }
/*     */     
/* 106 */     this._fundamentalFacets.add(f);
/*     */   }
/*     */   
/*     */   public Iterator fundamentalFacets() {
/* 110 */     return (this._fundamentalFacets == null) ? (Iterator)NullIterator.getInstance() : this._fundamentalFacets.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(ComponentVisitor visitor) throws Exception {
/* 115 */     visitor.visit(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\SimpleTypeDefinitionComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */