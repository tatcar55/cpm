/*     */ package com.sun.xml.rpc.wsdl.document.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.ExtensionVisitor;
/*     */ import com.sun.xml.rpc.wsdl.framework.QNameAction;
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
/*     */ 
/*     */ public class SOAPHeader
/*     */   extends Extension
/*     */ {
/*     */   private String _encodingStyle;
/*     */   private String _namespace;
/*     */   private String _part;
/*     */   private QName _message;
/*     */   private SOAPUse _use;
/*  49 */   private List _faults = new ArrayList();
/*     */ 
/*     */   
/*     */   public void add(SOAPHeaderFault fault) {
/*  53 */     this._faults.add(fault);
/*     */   }
/*     */   
/*     */   public Iterator faults() {
/*  57 */     return this._faults.iterator();
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/*  61 */     return SOAPConstants.QNAME_HEADER;
/*     */   }
/*     */   
/*     */   public String getNamespace() {
/*  65 */     return this._namespace;
/*     */   }
/*     */   
/*     */   public void setNamespace(String s) {
/*  69 */     this._namespace = s;
/*     */   }
/*     */   
/*     */   public SOAPUse getUse() {
/*  73 */     return this._use;
/*     */   }
/*     */   
/*     */   public void setUse(SOAPUse u) {
/*  77 */     this._use = u;
/*     */   }
/*     */   
/*     */   public boolean isEncoded() {
/*  81 */     return (this._use == SOAPUse.ENCODED);
/*     */   }
/*     */   
/*     */   public boolean isLiteral() {
/*  85 */     return (this._use == SOAPUse.LITERAL);
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/*  89 */     return this._encodingStyle;
/*     */   }
/*     */   
/*     */   public void setEncodingStyle(String s) {
/*  93 */     this._encodingStyle = s;
/*     */   }
/*     */   
/*     */   public String getPart() {
/*  97 */     return this._part;
/*     */   }
/*     */   
/*     */   public void setMessage(QName message) {
/* 101 */     this._message = message;
/*     */   }
/*     */   
/*     */   public QName getMessage() {
/* 105 */     return this._message;
/*     */   }
/*     */   
/*     */   public void setPart(String s) {
/* 109 */     this._part = s;
/*     */   }
/*     */   
/*     */   public void withAllSubEntitiesDo(EntityAction action) {
/* 113 */     super.withAllSubEntitiesDo(action);
/*     */     
/* 115 */     for (Iterator<Entity> iter = this._faults.iterator(); iter.hasNext();) {
/* 116 */       action.perform(iter.next());
/*     */     }
/*     */   }
/*     */   
/*     */   public void withAllQNamesDo(QNameAction action) {
/* 121 */     super.withAllQNamesDo(action);
/*     */     
/* 123 */     if (this._message != null) {
/* 124 */       action.perform(this._message);
/*     */     }
/*     */   }
/*     */   
/*     */   public void accept(ExtensionVisitor visitor) throws Exception {
/* 129 */     visitor.preVisit(this);
/* 130 */     for (Iterator<SOAPHeaderFault> iter = this._faults.iterator(); iter.hasNext();) {
/* 131 */       ((SOAPHeaderFault)iter.next()).accept(visitor);
/*     */     }
/* 133 */     visitor.postVisit(this);
/*     */   }
/*     */   
/*     */   public void validateThis() {
/* 137 */     if (this._message == null) {
/* 138 */       failValidation("validation.missingRequiredAttribute", "message");
/*     */     }
/* 140 */     if (this._part == null)
/* 141 */       failValidation("validation.missingRequiredAttribute", "part"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\soap\SOAPHeader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */