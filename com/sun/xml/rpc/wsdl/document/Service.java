/*     */ package com.sun.xml.rpc.wsdl.document;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.Defining;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.ExtensibilityHelper;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.GlobalEntity;
/*     */ import com.sun.xml.rpc.wsdl.framework.Kind;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class Service
/*     */   extends GlobalEntity
/*     */   implements Extensible
/*     */ {
/*     */   private ExtensibilityHelper _helper;
/*     */   private Documentation _documentation;
/*     */   private List _ports;
/*     */   
/*     */   public Service(Defining defining) {
/*  52 */     super(defining);
/*  53 */     this._ports = new ArrayList();
/*  54 */     this._helper = new ExtensibilityHelper();
/*     */   }
/*     */   
/*     */   public void add(Port port) {
/*  58 */     port.setService(this);
/*  59 */     this._ports.add(port);
/*     */   }
/*     */   
/*     */   public Iterator ports() {
/*  63 */     return this._ports.iterator();
/*     */   }
/*     */   
/*     */   public Kind getKind() {
/*  67 */     return Kinds.SERVICE;
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/*  71 */     return WSDLConstants.QNAME_SERVICE;
/*     */   }
/*     */   
/*     */   public Documentation getDocumentation() {
/*  75 */     return this._documentation;
/*     */   }
/*     */   
/*     */   public void setDocumentation(Documentation d) {
/*  79 */     this._documentation = d;
/*     */   }
/*     */   
/*     */   public void withAllSubEntitiesDo(EntityAction action) {
/*  83 */     for (Iterator<Entity> iter = this._ports.iterator(); iter.hasNext();) {
/*  84 */       action.perform(iter.next());
/*     */     }
/*  86 */     this._helper.withAllSubEntitiesDo(action);
/*     */   }
/*     */   
/*     */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/*  90 */     visitor.preVisit(this);
/*  91 */     for (Iterator<Port> iter = this._ports.iterator(); iter.hasNext();) {
/*  92 */       ((Port)iter.next()).accept(visitor);
/*     */     }
/*  94 */     this._helper.accept(visitor);
/*  95 */     visitor.postVisit(this);
/*     */   }
/*     */   
/*     */   public void validateThis() {
/*  99 */     if (getName() == null) {
/* 100 */       failValidation("validation.missingRequiredAttribute", "name");
/*     */     }
/*     */   }
/*     */   
/*     */   public void addExtension(Extension e) {
/* 105 */     this._helper.addExtension(e);
/*     */   }
/*     */   
/*     */   public Iterator extensions() {
/* 109 */     return this._helper.extensions();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\Service.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */