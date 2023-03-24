/*     */ package com.sun.xml.rpc.processor.schema;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class InternalSchema
/*     */ {
/*     */   private InternalSchemaBuilderBase _builder;
/*     */   private Map _typeDefinitions;
/*     */   private Map _attributeDeclarations;
/*     */   private Map _elementDeclarations;
/*     */   private Map _attributeGroupDefinitions;
/*     */   private Map _modelGroupDefinitions;
/*     */   private List _notationDeclarations;
/*     */   private List _annotations;
/*     */   
/*     */   public InternalSchema(InternalSchemaBuilderBase builder) {
/*  45 */     this._builder = builder;
/*     */     
/*  47 */     this._typeDefinitions = new HashMap<Object, Object>();
/*  48 */     this._attributeDeclarations = new HashMap<Object, Object>();
/*  49 */     this._elementDeclarations = new HashMap<Object, Object>();
/*  50 */     this._attributeGroupDefinitions = new HashMap<Object, Object>();
/*  51 */     this._modelGroupDefinitions = new HashMap<Object, Object>();
/*  52 */     this._notationDeclarations = new ArrayList();
/*  53 */     this._annotations = new ArrayList();
/*     */   }
/*     */   
/*     */   public void add(TypeDefinitionComponent c) {
/*  57 */     this._typeDefinitions.put(c.getName(), c);
/*     */   }
/*     */   
/*     */   public TypeDefinitionComponent findTypeDefinition(QName name) {
/*  61 */     Object result = this._typeDefinitions.get(name);
/*  62 */     if (result == null) {
/*     */       try {
/*  64 */         result = this._builder.getTypeDefinitionComponentBeingDefined(name);
/*  65 */         if (result == null) {
/*  66 */           result = this._builder.buildTypeDefinition(name);
/*     */         }
/*  68 */       } catch (ModelException e) {
/*  69 */         result = e;
/*  70 */         this._typeDefinitions.put(name, result);
/*     */       } 
/*     */     }
/*  73 */     if (result instanceof ModelException) {
/*  74 */       throw (ModelException)result;
/*     */     }
/*  76 */     return (TypeDefinitionComponent)result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(AttributeDeclarationComponent c) {
/*  81 */     this._attributeDeclarations.put(c.getName(), c);
/*     */   }
/*     */   
/*     */   public AttributeDeclarationComponent findAttributeDeclaration(QName name) {
/*  85 */     Object result = this._attributeDeclarations.get(name);
/*  86 */     if (result == null) {
/*     */       try {
/*  88 */         result = this._builder.buildAttributeDeclaration(name);
/*  89 */       } catch (ModelException e) {
/*  90 */         result = e;
/*  91 */         this._attributeDeclarations.put(name, result);
/*     */       } 
/*     */     }
/*  94 */     if (result instanceof ModelException) {
/*  95 */       throw (ModelException)result;
/*     */     }
/*  97 */     return (AttributeDeclarationComponent)result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(ElementDeclarationComponent c) {
/* 102 */     this._elementDeclarations.put(c.getName(), c);
/*     */   }
/*     */   
/*     */   public ElementDeclarationComponent findElementDeclaration(QName name) {
/* 106 */     Object result = this._elementDeclarations.get(name);
/* 107 */     if (result == null) {
/*     */       try {
/* 109 */         result = this._builder.buildElementDeclaration(name);
/* 110 */       } catch (ModelException e) {
/* 111 */         result = e;
/* 112 */         this._elementDeclarations.put(name, result);
/*     */       } 
/*     */     }
/* 115 */     if (result instanceof ModelException) {
/* 116 */       throw (ModelException)result;
/*     */     }
/* 118 */     return (ElementDeclarationComponent)result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(AttributeGroupDefinitionComponent c) {
/* 123 */     this._attributeGroupDefinitions.put(c.getName(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeGroupDefinitionComponent findAttributeGroupDefinition(QName name) {
/* 129 */     Object result = this._attributeGroupDefinitions.get(name);
/* 130 */     if (result == null) {
/*     */       try {
/* 132 */         result = this._builder.buildAttributeGroupDefinition(name);
/* 133 */       } catch (ModelException e) {
/* 134 */         result = e;
/* 135 */         this._attributeGroupDefinitions.put(name, result);
/*     */       } 
/*     */     }
/* 138 */     if (result instanceof ModelException) {
/* 139 */       throw (ModelException)result;
/*     */     }
/* 141 */     return (AttributeGroupDefinitionComponent)result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(ModelGroupDefinitionComponent c) {
/* 146 */     this._modelGroupDefinitions.put(c.getName(), c);
/*     */   }
/*     */   
/*     */   public ModelGroupDefinitionComponent findModelGroupDefinition(QName name) {
/* 150 */     Object result = this._modelGroupDefinitions.get(name);
/* 151 */     if (result == null) {
/*     */       try {
/* 153 */         result = this._builder.buildModelGroupDefinition(name);
/* 154 */       } catch (ModelException e) {
/* 155 */         result = e;
/* 156 */         this._modelGroupDefinitions.put(name, result);
/*     */       } 
/*     */     }
/* 159 */     if (result instanceof ModelException) {
/* 160 */       throw (ModelException)result;
/*     */     }
/* 162 */     return (ModelGroupDefinitionComponent)result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(NotationDeclarationComponent c) {
/* 167 */     this._notationDeclarations.add(c);
/*     */   }
/*     */   
/*     */   public void add(AnnotationComponent c) {
/* 171 */     this._annotations.add(c);
/*     */   }
/*     */   
/*     */   public SimpleTypeDefinitionComponent getSimpleUrType() {
/* 175 */     return this._builder.getSimpleUrType();
/*     */   }
/*     */   
/*     */   public ComplexTypeDefinitionComponent getUrType() {
/* 179 */     return this._builder.getUrType();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\InternalSchema.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */