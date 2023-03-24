/*     */ package com.sun.xml.rpc.processor.model.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
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
/*     */ public abstract class SOAPStructureType
/*     */   extends SOAPAttributeOwningType
/*     */ {
/*     */   protected SOAPStructureType() {}
/*     */   
/*     */   protected SOAPStructureType(QName name) {
/*  52 */     this(name, SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   protected SOAPStructureType(QName name, SOAPVersion version) {
/*  56 */     super(name, (JavaStructureType)null);
/*     */   }
/*     */   
/*     */   protected SOAPStructureType(QName name, JavaStructureType javaType) {
/*  60 */     super(name, javaType);
/*     */   }
/*     */   
/*     */   public void add(SOAPStructureMember m) {
/*  64 */     if (this._membersByName.containsKey(m.getName())) {
/*  65 */       throw new ModelException("model.uniqueness");
/*     */     }
/*  67 */     this._members.add(m);
/*  68 */     this._membersByName.put(m.getName(), m);
/*     */   }
/*     */   
/*     */   public SOAPStructureMember getMemberByName(QName name) {
/*  72 */     if (this._membersByName.size() != this._members.size()) {
/*  73 */       initializeMembersByName();
/*     */     }
/*  75 */     return (SOAPStructureMember)this._membersByName.get(name);
/*     */   }
/*     */   
/*     */   public Iterator getMembers() {
/*  79 */     return this._members.iterator();
/*     */   }
/*     */   
/*     */   public int getMembersCount() {
/*  83 */     return this._members.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List getMembersList() {
/*  88 */     return this._members;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMembersList(List l) {
/*  93 */     this._members = l;
/*     */   }
/*     */   
/*     */   private void initializeMembersByName() {
/*  97 */     this._membersByName = new HashMap<Object, Object>();
/*  98 */     if (this._members != null) {
/*  99 */       for (Iterator<SOAPStructureMember> iter = this._members.iterator(); iter.hasNext(); ) {
/* 100 */         SOAPStructureMember m = iter.next();
/* 101 */         if (m.getName() != null && this._membersByName.containsKey(m.getName()))
/*     */         {
/*     */           
/* 104 */           throw new ModelException("model.uniqueness");
/*     */         }
/* 106 */         this._membersByName.put(m.getName(), m);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void addSubtype(SOAPStructureType type) {
/* 112 */     if (this._subtypes == null) {
/* 113 */       this._subtypes = new HashSet();
/*     */     }
/* 115 */     this._subtypes.add(type);
/* 116 */     type.setParentType(this);
/*     */   }
/*     */   
/*     */   public Iterator getSubtypes() {
/* 120 */     if (this._subtypes != null)
/* 121 */       return this._subtypes.iterator(); 
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getSubtypesSet() {
/* 127 */     return this._subtypes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSubtypesSet(Set s) {
/* 132 */     this._subtypes = s;
/*     */   }
/*     */   
/*     */   public void setParentType(SOAPStructureType parent) {
/* 136 */     if (this._parentType != null && parent != null && !this._parentType.equals(parent))
/*     */     {
/*     */ 
/*     */       
/* 140 */       throw new ModelException("model.parent.type.already.set", new Object[] { getName().toString(), this._parentType.getName().toString(), parent.getName().toString() });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 145 */     this._parentType = parent;
/*     */   }
/*     */   
/*     */   public SOAPStructureType getParentType() {
/* 149 */     return this._parentType;
/*     */   }
/*     */   
/* 152 */   private List _members = new ArrayList();
/* 153 */   private Map _membersByName = new HashMap<Object, Object>();
/* 154 */   private Set _subtypes = null;
/* 155 */   private SOAPStructureType _parentType = null;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPStructureType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */