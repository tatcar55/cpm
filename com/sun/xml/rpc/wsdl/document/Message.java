/*     */ package com.sun.xml.rpc.wsdl.document;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.Defining;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.GlobalEntity;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
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
/*     */ public class Message
/*     */   extends GlobalEntity
/*     */ {
/*     */   private Documentation _documentation;
/*     */   private List _parts;
/*     */   private Map _partsByName;
/*     */   
/*     */   public Message(Defining defining) {
/*  52 */     super(defining);
/*  53 */     this._parts = new ArrayList();
/*  54 */     this._partsByName = new HashMap<Object, Object>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(MessagePart part) {
/*  66 */     this._partsByName.put(part.getName(), part);
/*  67 */     this._parts.add(part);
/*     */   }
/*     */   
/*     */   public Iterator parts() {
/*  71 */     return this._parts.iterator();
/*     */   }
/*     */   
/*     */   public MessagePart getPart(String name) {
/*  75 */     return (MessagePart)this._partsByName.get(name);
/*     */   }
/*     */   
/*     */   public int numParts() {
/*  79 */     return this._parts.size();
/*     */   }
/*     */   
/*     */   public Kind getKind() {
/*  83 */     return Kinds.MESSAGE;
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/*  87 */     return WSDLConstants.QNAME_MESSAGE;
/*     */   }
/*     */   
/*     */   public Documentation getDocumentation() {
/*  91 */     return this._documentation;
/*     */   }
/*     */   
/*     */   public void setDocumentation(Documentation d) {
/*  95 */     this._documentation = d;
/*     */   }
/*     */   
/*     */   public void withAllSubEntitiesDo(EntityAction action) {
/*  99 */     super.withAllSubEntitiesDo(action);
/*     */     
/* 101 */     for (Iterator<Entity> iter = this._parts.iterator(); iter.hasNext();) {
/* 102 */       action.perform(iter.next());
/*     */     }
/*     */   }
/*     */   
/*     */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 107 */     visitor.preVisit(this);
/* 108 */     for (Iterator<MessagePart> iter = this._parts.iterator(); iter.hasNext();) {
/* 109 */       ((MessagePart)iter.next()).accept(visitor);
/*     */     }
/* 111 */     visitor.postVisit(this);
/*     */   }
/*     */   
/*     */   public void validateThis() {
/* 115 */     if (getName() == null)
/* 116 */       failValidation("validation.missingRequiredAttribute", "name"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\Message.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */