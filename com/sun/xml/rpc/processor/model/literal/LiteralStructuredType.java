/*     */ package com.sun.xml.rpc.processor.model.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
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
/*     */ public abstract class LiteralStructuredType
/*     */   extends LiteralAttributeOwningType
/*     */ {
/*     */   protected LiteralStructuredType() {}
/*     */   
/*     */   protected LiteralStructuredType(QName name) {
/*  51 */     this(name, null);
/*     */   }
/*     */   
/*     */   protected LiteralStructuredType(QName name, JavaStructureType javaType) {
/*  55 */     super(name, javaType);
/*     */   }
/*     */   
/*     */   public void add(LiteralElementMember m) {
/*  59 */     if (this._elementMembersByName.containsKey(m.getName())) {
/*  60 */       throw new ModelException("model.uniqueness");
/*     */     }
/*  62 */     this._elementMembers.add(m);
/*  63 */     if (m.getName() != null) {
/*  64 */       this._elementMembersByName.put(m.getName().getLocalPart(), m);
/*     */     }
/*     */   }
/*     */   
/*     */   public LiteralElementMember getElementMemberByName(String name) {
/*  69 */     if (this._elementMembersByName.size() != this._elementMembers.size()) {
/*  70 */       initializeElementMembersByName();
/*     */     }
/*  72 */     return (LiteralElementMember)this._elementMembersByName.get(name);
/*     */   }
/*     */   
/*     */   public Iterator getElementMembers() {
/*  76 */     return this._elementMembers.iterator();
/*     */   }
/*     */   
/*     */   public int getElementMembersCount() {
/*  80 */     return this._elementMembers.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List getElementMembersList() {
/*  85 */     return this._elementMembers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElementMembersList(List l) {
/*  90 */     this._elementMembers = l;
/*     */   }
/*     */   
/*     */   private void initializeElementMembersByName() {
/*  94 */     this._elementMembersByName = new HashMap<Object, Object>();
/*  95 */     if (this._elementMembers != null) {
/*  96 */       for (Iterator<LiteralElementMember> iter = this._elementMembers.iterator(); iter.hasNext(); ) {
/*  97 */         LiteralElementMember m = iter.next();
/*  98 */         if (m.getName() != null && this._elementMembersByName.containsKey(m.getName()))
/*     */         {
/*     */           
/* 101 */           throw new ModelException("model.uniqueness");
/*     */         }
/* 103 */         if (m.getName() != null) {
/* 104 */           this._elementMembersByName.put(m.getName().getLocalPart(), m);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public LiteralContentMember getContentMember() {
/* 111 */     return this._contentMember;
/*     */   }
/*     */   
/*     */   public void setContentMember(LiteralContentMember t) {
/* 115 */     this._contentMember = t;
/*     */   }
/*     */   
/*     */   public void addSubtype(LiteralStructuredType type) {
/* 119 */     if (this._subtypes == null) {
/* 120 */       this._subtypes = new HashSet();
/*     */     }
/* 122 */     this._subtypes.add(type);
/* 123 */     type.setParentType(this);
/*     */   }
/*     */   
/*     */   public Iterator getSubtypes() {
/* 127 */     if (this._subtypes != null) {
/* 128 */       return this._subtypes.iterator();
/*     */     }
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getSubtypesSet() {
/* 135 */     return this._subtypes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSubtypesSet(Set s) {
/* 140 */     this._subtypes = s;
/*     */   }
/*     */   
/*     */   public void setParentType(LiteralStructuredType parent) {
/* 144 */     if (this._parentType != null && parent != null && !this._parentType.equals(parent))
/*     */     {
/*     */ 
/*     */       
/* 148 */       throw new ModelException("model.parent.type.already.set", new Object[] { getName().toString(), this._parentType.getName().toString(), parent.getName().toString() });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 153 */     this._parentType = parent;
/*     */   }
/*     */   
/*     */   public LiteralStructuredType getParentType() {
/* 157 */     return this._parentType;
/*     */   }
/*     */   
/*     */   public void setRpcWrapper(boolean rpcWrapper) {
/* 161 */     this._rpcWrapper = rpcWrapper;
/*     */   }
/*     */   
/*     */   public boolean isRpcWrapper() {
/* 165 */     return this._rpcWrapper;
/*     */   }
/*     */   
/* 168 */   private List _elementMembers = new ArrayList();
/* 169 */   private Map _elementMembersByName = new HashMap<Object, Object>();
/*     */   private LiteralContentMember _contentMember;
/* 171 */   private Set _subtypes = null;
/* 172 */   private LiteralStructuredType _parentType = null;
/*     */   private boolean _rpcWrapper = false;
/*     */   private boolean requestResponseStruct;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralStructuredType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */