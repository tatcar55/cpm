/*     */ package com.sun.xml.rpc.client.dii;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ public class PortInfo
/*     */ {
/*     */   Map operationMap;
/*     */   String targetEndpoint;
/*     */   String defaultNamespace;
/*     */   QName name;
/*     */   QName portTypeName;
/*     */   
/*     */   public PortInfo(QName name) {
/*  49 */     init();
/*  50 */     this.name = name;
/*     */   }
/*     */   
/*     */   protected void init() {
/*  54 */     this.operationMap = new HashMap<Object, Object>();
/*  55 */     this.targetEndpoint = "";
/*  56 */     this.defaultNamespace = "";
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  60 */     return this.name;
/*     */   }
/*     */   
/*     */   public OperationInfo createOperationForName(String operationName) {
/*  64 */     OperationInfo operation = (OperationInfo)this.operationMap.get(operationName);
/*     */     
/*  66 */     if (operation == null) {
/*  67 */       operation = new OperationInfo(operationName);
/*  68 */       operation.setNamespace(this.defaultNamespace);
/*  69 */       this.operationMap.put(operationName, operation);
/*     */     } 
/*  71 */     return operation;
/*     */   }
/*     */   
/*     */   public void setPortTypeName(QName typeName) {
/*  75 */     this.portTypeName = typeName;
/*     */   }
/*     */   
/*     */   public QName getPortTypeName() {
/*  79 */     return this.portTypeName;
/*     */   }
/*     */   
/*     */   public void setDefaultNamespace(String namespace) {
/*  83 */     this.defaultNamespace = namespace;
/*     */   }
/*     */   
/*     */   public boolean isOperationKnown(String operationName) {
/*  87 */     return (this.operationMap.get(operationName) != null);
/*     */   }
/*     */   
/*     */   public String getTargetEndpoint() {
/*  91 */     return this.targetEndpoint;
/*     */   }
/*     */   
/*     */   public void setTargetEndpoint(String target) {
/*  95 */     this.targetEndpoint = target;
/*     */   }
/*     */   
/*     */   public Iterator getOperations() {
/*  99 */     return this.operationMap.values().iterator();
/*     */   }
/*     */   
/*     */   public int getOperationCount() {
/* 103 */     return this.operationMap.values().size();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\PortInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */