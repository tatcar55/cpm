/*     */ package com.sun.xml.rpc.processor.model.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.ModelException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SOAPAttributeOwningType
/*     */   extends SOAPType
/*     */ {
/*     */   protected SOAPAttributeOwningType() {}
/*     */   
/*     */   protected SOAPAttributeOwningType(QName name) {
/*  48 */     this(name, null);
/*     */   }
/*     */   
/*     */   protected SOAPAttributeOwningType(QName name, JavaStructureType javaType) {
/*  52 */     super(name, (JavaType)javaType);
/*     */   }
/*     */   
/*     */   public void add(SOAPAttributeMember m) {
/*  56 */     if (this._attributeMembersByName.containsKey(m.getName())) {
/*  57 */       throw new ModelException("model.uniqueness");
/*     */     }
/*  59 */     this._attributeMembers.add(m);
/*  60 */     this._attributeMembersByName.put(m.getName(), m);
/*     */   }
/*     */   
/*     */   public SOAPAttributeMember getAttributeMemberByName(String name) {
/*  64 */     if (this._attributeMembersByName.size() != this._attributeMembers.size()) {
/*  65 */       initializeAttributeMembersByName();
/*     */     }
/*  67 */     return (SOAPAttributeMember)this._attributeMembersByName.get(name);
/*     */   }
/*     */   
/*     */   public Iterator getAttributeMembers() {
/*  71 */     return this._attributeMembers.iterator();
/*     */   }
/*     */   
/*     */   public int getAttributeMembersCount() {
/*  75 */     return this._attributeMembers.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List getAttributeMembersList() {
/*  80 */     return this._attributeMembers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttributeMembersList(List l) {
/*  85 */     this._attributeMembers = l;
/*     */   }
/*     */   
/*     */   private void initializeAttributeMembersByName() {
/*  89 */     this._attributeMembersByName = new HashMap<Object, Object>();
/*  90 */     if (this._attributeMembers != null) {
/*  91 */       Iterator<SOAPAttributeMember> iter = this._attributeMembers.iterator();
/*  92 */       while (iter.hasNext()) {
/*     */         
/*  94 */         SOAPAttributeMember m = iter.next();
/*  95 */         if (m.getName() != null && this._attributeMembersByName.containsKey(m.getName()))
/*     */         {
/*     */           
/*  98 */           throw new ModelException("model.uniqueness");
/*     */         }
/* 100 */         this._attributeMembersByName.put(m.getName(), m);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/* 105 */   private List _attributeMembers = new ArrayList();
/* 106 */   private Map _attributeMembersByName = new HashMap<Object, Object>();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\soap\SOAPAttributeOwningType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */