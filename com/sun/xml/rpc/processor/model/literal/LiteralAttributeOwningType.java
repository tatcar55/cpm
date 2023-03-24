/*     */ package com.sun.xml.rpc.processor.model.literal;
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
/*     */ 
/*     */ public abstract class LiteralAttributeOwningType
/*     */   extends LiteralType
/*     */ {
/*     */   protected LiteralAttributeOwningType() {}
/*     */   
/*     */   protected LiteralAttributeOwningType(QName name) {
/*  49 */     this(name, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LiteralAttributeOwningType(QName name, JavaStructureType javaType) {
/*  55 */     super(name, (JavaType)javaType);
/*     */   }
/*     */   
/*     */   public void add(LiteralAttributeMember m) {
/*  59 */     if (this._attributeMembersByName.containsKey(m.getName())) {
/*  60 */       throw new ModelException("model.uniqueness");
/*     */     }
/*  62 */     this._attributeMembers.add(m);
/*  63 */     this._attributeMembersByName.put(m.getName(), m);
/*     */   }
/*     */   
/*     */   public LiteralAttributeMember getAttributeMemberByName(String name) {
/*  67 */     if (this._attributeMembersByName.size() != this._attributeMembers.size()) {
/*  68 */       initializeAttributeMembersByName();
/*     */     }
/*  70 */     return (LiteralAttributeMember)this._attributeMembersByName.get(name);
/*     */   }
/*     */   
/*     */   public Iterator getAttributeMembers() {
/*  74 */     return this._attributeMembers.iterator();
/*     */   }
/*     */   
/*     */   public int getAttributeMembersCount() {
/*  78 */     return this._attributeMembers.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List getAttributeMembersList() {
/*  83 */     return this._attributeMembers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttributeMembersList(List l) {
/*  88 */     this._attributeMembers = l;
/*     */   }
/*     */   
/*     */   private void initializeAttributeMembersByName() {
/*  92 */     this._attributeMembersByName = new HashMap<Object, Object>();
/*  93 */     if (this._attributeMembers != null) {
/*  94 */       Iterator<LiteralAttributeMember> iter = this._attributeMembers.iterator();
/*  95 */       while (iter.hasNext()) {
/*     */         
/*  97 */         LiteralAttributeMember m = iter.next();
/*  98 */         if (m.getName() != null && this._attributeMembersByName.containsKey(m.getName()))
/*     */         {
/*     */           
/* 101 */           throw new ModelException("model.uniqueness");
/*     */         }
/* 103 */         this._attributeMembersByName.put(m.getName(), m);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/* 108 */   private List _attributeMembers = new ArrayList();
/* 109 */   private Map _attributeMembersByName = new HashMap<Object, Object>();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\literal\LiteralAttributeOwningType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */