/*     */ package com.sun.xml.rpc.processor.model;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.HandlerChainInfo;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.spi.model.Port;
/*     */ import com.sun.xml.rpc.spi.tools.HandlerChainInfo;
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
/*     */ public class Port
/*     */   extends ModelObject
/*     */   implements Port
/*     */ {
/*     */   private QName _name;
/*     */   
/*     */   public Port() {}
/*     */   
/*     */   public Port(QName name) {
/*  52 */     this._name = name;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  56 */     return this._name;
/*     */   }
/*     */   
/*     */   public void setName(QName n) {
/*  60 */     this._name = n;
/*     */   }
/*     */   
/*     */   public void addOperation(Operation operation) {
/*  64 */     this._operations.add(operation);
/*  65 */     this.operationsByName.put(operation.getUniqueName(), operation);
/*     */   }
/*     */   
/*     */   public Iterator getOperations() {
/*  69 */     return this._operations.iterator();
/*     */   }
/*     */   
/*     */   public Operation getOperationByUniqueName(String name) {
/*  73 */     if (this.operationsByName.size() != this._operations.size()) {
/*  74 */       initializeOperationsByName();
/*     */     }
/*  76 */     return (Operation)this.operationsByName.get(name);
/*     */   }
/*     */   
/*     */   private void initializeOperationsByName() {
/*  80 */     this.operationsByName = new HashMap<Object, Object>();
/*  81 */     if (this._operations != null) {
/*  82 */       for (Iterator<Operation> iter = this._operations.iterator(); iter.hasNext(); ) {
/*  83 */         Operation operation = iter.next();
/*  84 */         if (operation.getUniqueName() != null && this.operationsByName.containsKey(operation.getUniqueName()))
/*     */         {
/*     */           
/*  87 */           throw new ModelException("model.uniqueness");
/*     */         }
/*  89 */         this.operationsByName.put(operation.getUniqueName(), operation);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List getOperationsList() {
/*  96 */     return this._operations;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOperationsList(List l) {
/* 101 */     this._operations = l;
/*     */   }
/*     */   
/*     */   public JavaInterface getJavaInterface() {
/* 105 */     return this._javaInterface;
/*     */   }
/*     */   
/*     */   public void setJavaInterface(JavaInterface i) {
/* 109 */     this._javaInterface = i;
/*     */   }
/*     */   
/*     */   public String getAddress() {
/* 113 */     return this._address;
/*     */   }
/*     */   
/*     */   public void setAddress(String s) {
/* 117 */     this._address = s;
/*     */   }
/*     */   
/*     */   public HandlerChainInfo getClientHandlerChainInfo() {
/* 121 */     if (this._clientHandlerChainInfo == null) {
/* 122 */       this._clientHandlerChainInfo = new HandlerChainInfo();
/*     */     }
/* 124 */     return this._clientHandlerChainInfo;
/*     */   }
/*     */   
/*     */   public void setClientHandlerChainInfo(HandlerChainInfo i) {
/* 128 */     this._clientHandlerChainInfo = i;
/*     */   }
/*     */   
/*     */   public HandlerChainInfo getServerHCI() {
/* 132 */     return (HandlerChainInfo)getServerHandlerChainInfo();
/*     */   }
/*     */   
/*     */   public HandlerChainInfo getServerHandlerChainInfo() {
/* 136 */     if (this._serverHandlerChainInfo == null) {
/* 137 */       this._serverHandlerChainInfo = new HandlerChainInfo();
/*     */     }
/* 139 */     return this._serverHandlerChainInfo;
/*     */   }
/*     */   
/*     */   public void setServerHandlerChainInfo(HandlerChainInfo i) {
/* 143 */     this._serverHandlerChainInfo = i;
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 147 */     return this._soapVersion;
/*     */   }
/*     */   
/*     */   public void setSOAPVersion(SOAPVersion soapVersion) {
/* 151 */     this._soapVersion = soapVersion;
/*     */   }
/*     */   
/*     */   public void accept(ModelVisitor visitor) throws Exception {
/* 155 */     visitor.visit(this);
/*     */   }
/*     */ 
/*     */   
/* 159 */   private List _operations = new ArrayList();
/*     */   private JavaInterface _javaInterface;
/*     */   private String _address;
/* 162 */   private Map operationsByName = new HashMap<Object, Object>();
/*     */   private HandlerChainInfo _clientHandlerChainInfo;
/*     */   private HandlerChainInfo _serverHandlerChainInfo;
/* 165 */   private SOAPVersion _soapVersion = SOAPVersion.SOAP_11;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\Port.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */