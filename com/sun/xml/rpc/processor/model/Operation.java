/*     */ package com.sun.xml.rpc.processor.model;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.java.JavaMethod;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
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
/*     */ public class Operation
/*     */   extends ModelObject
/*     */ {
/*     */   private QName _name;
/*     */   private String _uniqueName;
/*     */   private Request _request;
/*     */   private Response _response;
/*     */   private JavaMethod _javaMethod;
/*     */   private String _soapAction;
/*     */   private SOAPStyle _style;
/*     */   private SOAPUse _use;
/*     */   private Set _faultNames;
/*     */   private Set _faults;
/*     */   
/*     */   public Operation() {}
/*     */   
/*     */   public Operation(QName name) {
/*  48 */     this._name = name;
/*  49 */     this._uniqueName = name.getLocalPart();
/*  50 */     this._faultNames = new HashSet();
/*  51 */     this._faults = new HashSet();
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  55 */     return this._name;
/*     */   }
/*     */   
/*     */   public void setName(QName n) {
/*  59 */     this._name = n;
/*     */   }
/*     */   
/*     */   public String getUniqueName() {
/*  63 */     return this._uniqueName;
/*     */   }
/*     */   
/*     */   public void setUniqueName(String s) {
/*  67 */     this._uniqueName = s;
/*     */   }
/*     */   
/*     */   public Request getRequest() {
/*  71 */     return this._request;
/*     */   }
/*     */   
/*     */   public void setRequest(Request r) {
/*  75 */     this._request = r;
/*     */   }
/*     */   
/*     */   public Response getResponse() {
/*  79 */     return this._response;
/*     */   }
/*     */   
/*     */   public void setResponse(Response r) {
/*  83 */     this._response = r;
/*     */   }
/*     */   
/*     */   public boolean isOverloaded() {
/*  87 */     return !this._name.getLocalPart().equals(this._uniqueName);
/*     */   }
/*     */   
/*     */   public void addFault(Fault f) {
/*  91 */     if (this._faultNames.contains(f.getName())) {
/*  92 */       throw new ModelException("model.uniqueness");
/*     */     }
/*  94 */     this._faultNames.add(f.getName());
/*  95 */     this._faults.add(f);
/*     */   }
/*     */   
/*     */   public Iterator getFaults() {
/*  99 */     return this._faults.iterator();
/*     */   }
/*     */   
/*     */   public Set getFaultsSet() {
/* 103 */     return this._faults;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFaultsSet(Set s) {
/* 108 */     this._faults = s;
/* 109 */     initializeFaultNames();
/*     */   }
/*     */   
/*     */   private void initializeFaultNames() {
/* 113 */     this._faultNames = new HashSet();
/* 114 */     if (this._faults != null) {
/* 115 */       for (Iterator<Fault> iter = this._faults.iterator(); iter.hasNext(); ) {
/* 116 */         Fault f = iter.next();
/* 117 */         if (f.getName() != null && this._faultNames.contains(f.getName())) {
/* 118 */           throw new ModelException("model.uniqueness");
/*     */         }
/* 120 */         this._faultNames.add(f.getName());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator getAllFaults() {
/* 126 */     Set allFaults = getAllFaultsSet();
/* 127 */     if (allFaults.size() == 0) {
/* 128 */       return null;
/*     */     }
/* 130 */     return allFaults.iterator();
/*     */   }
/*     */   
/*     */   public Set getAllFaultsSet() {
/* 134 */     Set transSet = new HashSet();
/* 135 */     transSet.addAll(this._faults);
/* 136 */     Iterator<Fault> iter = this._faults.iterator();
/*     */ 
/*     */     
/* 139 */     while (iter.hasNext()) {
/* 140 */       Set tmpSet = ((Fault)iter.next()).getAllFaultsSet();
/* 141 */       transSet.addAll(tmpSet);
/*     */     } 
/* 143 */     return transSet;
/*     */   }
/*     */   
/*     */   public int getFaultCount() {
/* 147 */     return this._faults.size();
/*     */   }
/*     */   
/*     */   public JavaMethod getJavaMethod() {
/* 151 */     return this._javaMethod;
/*     */   }
/*     */   
/*     */   public void setJavaMethod(JavaMethod i) {
/* 155 */     this._javaMethod = i;
/*     */   }
/*     */   
/*     */   public String getSOAPAction() {
/* 159 */     return this._soapAction;
/*     */   }
/*     */   
/*     */   public void setSOAPAction(String s) {
/* 163 */     this._soapAction = s;
/*     */   }
/*     */   
/*     */   public SOAPStyle getStyle() {
/* 167 */     return this._style;
/*     */   }
/*     */   
/*     */   public void setStyle(SOAPStyle s) {
/* 171 */     this._style = s;
/*     */   }
/*     */   
/*     */   public SOAPUse getUse() {
/* 175 */     return this._use;
/*     */   }
/*     */   
/*     */   public void setUse(SOAPUse u) {
/* 179 */     this._use = u;
/*     */   }
/*     */   
/*     */   public void accept(ModelVisitor visitor) throws Exception {
/* 183 */     visitor.visit(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\Operation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */