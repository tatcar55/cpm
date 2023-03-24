/*     */ package com.sun.xml.rpc.wsdl.framework;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDocument
/*     */ {
/*  47 */   private Map _kinds = new HashMap<Object, Object>();
/*  48 */   private Map _identifiables = new HashMap<Object, Object>();
/*  49 */   private List _importedEntities = new ArrayList();
/*  50 */   private Set _importedDocuments = new HashSet();
/*  51 */   private List _includedEntities = new ArrayList();
/*  52 */   private Set _includedDocuments = new HashSet();
/*     */   private String _systemId;
/*     */   
/*     */   public String getSystemId() {
/*  56 */     return this._systemId;
/*     */   }
/*     */   
/*     */   public void setSystemId(String s) {
/*  60 */     if (this._systemId != null && !this._systemId.equals(s))
/*     */     {
/*  62 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  65 */     this._systemId = s;
/*  66 */     if (s != null) {
/*  67 */       this._importedDocuments.add(s);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addIncludedDocument(String systemId) {
/*  72 */     this._includedDocuments.add(systemId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncludedDocument(String systemId) {
/*  77 */     return (this._includedDocuments.contains(systemId) || this._importedDocuments.contains(systemId));
/*     */   }
/*     */   
/*     */   public void addIncludedEntity(Entity entity) {
/*  81 */     this._includedEntities.add(entity);
/*     */   }
/*     */   
/*     */   public void addImportedDocument(String systemId) {
/*  85 */     this._importedDocuments.add(systemId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isImportedDocument(String systemId) {
/*  90 */     return (this._importedDocuments.contains(systemId) || this._includedDocuments.contains(systemId));
/*     */   }
/*     */   
/*     */   public void addImportedEntity(Entity entity) {
/*  94 */     this._importedEntities.add(entity);
/*     */   }
/*     */   
/*     */   public void withAllSubEntitiesDo(EntityAction action) {
/*  98 */     if (getRoot() != null) {
/*  99 */       action.perform(getRoot());
/*     */     }
/*     */     
/* 102 */     for (Iterator<Entity> iterator1 = this._importedEntities.iterator(); iterator1.hasNext();) {
/* 103 */       action.perform(iterator1.next());
/*     */     }
/*     */     
/* 106 */     for (Iterator<Entity> iter = this._includedEntities.iterator(); iter.hasNext();) {
/* 107 */       action.perform(iter.next());
/*     */     }
/*     */   }
/*     */   
/*     */   public Map getMap(Kind k) {
/* 112 */     Map<Object, Object> m = (Map)this._kinds.get(k.getName());
/* 113 */     if (m == null) {
/* 114 */       m = new HashMap<Object, Object>();
/* 115 */       this._kinds.put(k.getName(), m);
/*     */     } 
/* 117 */     return m;
/*     */   }
/*     */   
/*     */   public void define(GloballyKnown e) {
/* 121 */     Map<QName, GloballyKnown> map = getMap(e.getKind());
/* 122 */     if (e.getName() == null) {
/*     */       return;
/*     */     }
/* 125 */     QName name = new QName(e.getDefining().getTargetNamespaceURI(), e.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     if (map.containsKey(name)) {
/* 131 */       throw new DuplicateEntityException(e);
/*     */     }
/* 133 */     map.put(name, e);
/*     */   }
/*     */   
/*     */   public void undefine(GloballyKnown e) {
/* 137 */     Map map = getMap(e.getKind());
/* 138 */     if (e.getName() == null)
/*     */       return; 
/* 140 */     QName name = new QName(e.getDefining().getTargetNamespaceURI(), e.getName());
/*     */ 
/*     */     
/* 143 */     if (map.containsKey(name)) {
/* 144 */       throw new NoSuchEntityException(name);
/*     */     }
/* 146 */     map.remove(name);
/*     */   }
/*     */   
/*     */   public GloballyKnown find(Kind k, QName name) {
/* 150 */     Map map = getMap(k);
/* 151 */     Object result = map.get(name);
/* 152 */     if (result == null)
/* 153 */       throw new NoSuchEntityException(name); 
/* 154 */     return (GloballyKnown)result;
/*     */   }
/*     */   
/*     */   public void defineID(Identifiable e) {
/* 158 */     String id = e.getID();
/* 159 */     if (id == null) {
/*     */       return;
/*     */     }
/* 162 */     if (this._identifiables.containsKey(id)) {
/* 163 */       throw new DuplicateEntityException(e);
/*     */     }
/* 165 */     this._identifiables.put(id, e);
/*     */   }
/*     */   
/*     */   public void undefineID(Identifiable e) {
/* 169 */     String id = e.getID();
/*     */     
/* 171 */     if (id == null) {
/*     */       return;
/*     */     }
/* 174 */     if (this._identifiables.containsKey(id)) {
/* 175 */       throw new NoSuchEntityException(id);
/*     */     }
/* 177 */     this._identifiables.remove(id);
/*     */   }
/*     */   
/*     */   public Identifiable findByID(String id) {
/* 181 */     Object result = this._identifiables.get(id);
/* 182 */     if (result == null)
/* 183 */       throw new NoSuchEntityException(id); 
/* 184 */     return (Identifiable)result;
/*     */   }
/*     */   
/*     */   public Set collectAllQNames() {
/* 188 */     final Set result = new HashSet();
/* 189 */     EntityAction action = new EntityAction() {
/*     */         public void perform(Entity entity) {
/* 191 */           entity.withAllQNamesDo(new QNameAction() {
/*     */                 public void perform(QName name) {
/* 193 */                   result.add(name);
/*     */                 }
/*     */               });
/* 196 */           entity.withAllSubEntitiesDo(this);
/*     */         }
/*     */       };
/* 199 */     withAllSubEntitiesDo(action);
/* 200 */     return result;
/*     */   }
/*     */   
/*     */   public Set collectAllNamespaces() {
/* 204 */     final Set result = new HashSet();
/*     */     
/* 206 */     EntityAction action = new EntityAction() {
/*     */         public void perform(Entity entity) {
/* 208 */           entity.withAllQNamesDo(new QNameAction() {
/*     */                 public void perform(QName name) {
/* 210 */                   result.add(name.getNamespaceURI());
/*     */                 }
/*     */               });
/*     */           
/* 214 */           entity.withAllSubEntitiesDo(this);
/*     */         }
/*     */       };
/* 217 */     withAllSubEntitiesDo(action);
/* 218 */     return result;
/*     */   }
/*     */   
/*     */   public void validateLocally() {
/* 222 */     LocallyValidatingAction action = new LocallyValidatingAction();
/* 223 */     withAllSubEntitiesDo(action);
/* 224 */     if (action.getException() != null) {
/* 225 */       throw action.getException();
/*     */     }
/*     */   }
/*     */   
/*     */   public void validate() {
/* 230 */     validate(null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void validate(EntityReferenceValidator paramEntityReferenceValidator);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Entity getRoot();
/*     */ 
/*     */ 
/*     */   
/*     */   private class LocallyValidatingAction
/*     */     implements EntityAction
/*     */   {
/*     */     private ValidationException _exception;
/*     */ 
/*     */     
/*     */     public void perform(Entity entity) {
/*     */       try {
/* 251 */         entity.validateThis();
/* 252 */         entity.withAllSubEntitiesDo(this);
/* 253 */       } catch (ValidationException e) {
/* 254 */         if (this._exception == null) {
/* 255 */           this._exception = e;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public ValidationException getException() {
/* 261 */       return this._exception;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\AbstractDocument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */