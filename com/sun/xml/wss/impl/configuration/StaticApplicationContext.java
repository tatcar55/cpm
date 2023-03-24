/*     */ package com.sun.xml.wss.impl.configuration;
/*     */ 
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
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
/*     */ public final class StaticApplicationContext
/*     */   implements StaticPolicyContext
/*     */ {
/*     */   private boolean isService = false;
/*     */   private boolean isPort = false;
/*     */   private boolean isOperation = false;
/*  68 */   private String UUID = "";
/*  69 */   private String contextRoot = "";
/*     */   
/*  71 */   private String serviceIdentifier = "";
/*  72 */   private String portIdentifier = "";
/*  73 */   private String operationIdentifier = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StaticApplicationContext() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StaticApplicationContext(StaticApplicationContext context) {
/*  86 */     copy(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isService(boolean isService) {
/*  94 */     this.isService = isService;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isService() {
/* 101 */     return this.isService;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isPort(boolean isPort) {
/* 109 */     this.isPort = isPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPort() {
/* 116 */     return this.isPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isOperation(boolean isOperation) {
/* 124 */     this.isOperation = isOperation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOperation() {
/* 131 */     return this.isOperation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServiceIdentifier(String service) {
/* 139 */     this.serviceIdentifier = service;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServiceIdentifier() {
/* 146 */     return this.serviceIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPortIdentifier(String port) {
/* 154 */     this.portIdentifier = port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPortIdentifier() {
/* 161 */     return this.portIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOperationIdentifier(String operation) {
/* 169 */     isOperation(true);
/* 170 */     this.operationIdentifier = operation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOperationIdentifier() {
/* 177 */     return this.operationIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUUID(String uuid) {
/* 185 */     this.UUID = uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUUID() {
/* 192 */     return this.UUID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setApplicationContextRoot(String ctxRoot) {
/* 199 */     this.contextRoot = ctxRoot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getApplicationContextRoot() {
/* 206 */     return this.contextRoot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copy(StaticApplicationContext ctx) {
/* 214 */     setUUID(ctx.getUUID());
/* 215 */     setApplicationContextRoot(ctx.getApplicationContextRoot());
/*     */     
/* 217 */     isService(ctx.isService());
/* 218 */     isPort(ctx.isPort());
/* 219 */     isOperation(ctx.isOperation());
/*     */     
/* 221 */     setServiceIdentifier(ctx.getServiceIdentifier());
/* 222 */     setPortIdentifier(ctx.getPortIdentifier());
/* 223 */     this.operationIdentifier = ctx.getOperationIdentifier();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 239 */     if (obj instanceof StaticApplicationContext) {
/* 240 */       return equals((StaticApplicationContext)obj);
/*     */     }
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(StaticApplicationContext ctx) {
/* 252 */     boolean b1 = this.UUID.equalsIgnoreCase(ctx.getUUID());
/*     */ 
/*     */     
/* 255 */     if (!b1) return false;
/*     */     
/* 257 */     boolean b2 = (this.serviceIdentifier.equalsIgnoreCase(ctx.getServiceIdentifier()) && this.portIdentifier.equalsIgnoreCase(ctx.getPortIdentifier()) && this.operationIdentifier.equalsIgnoreCase(ctx.getOperationIdentifier()));
/*     */ 
/*     */ 
/*     */     
/* 261 */     if (!b2) return false;
/*     */     
/* 263 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 274 */     return this.UUID.hashCode() + this.serviceIdentifier.hashCode() + this.portIdentifier.hashCode() + this.operationIdentifier.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 280 */     String ret = "isService=" + this.isService + "\nisPort=" + this.isPort + "\nisOperation=" + this.isOperation + "\nUUID=" + this.UUID + "\nserviceIdentifier=" + this.serviceIdentifier + "\nportIdentifier=" + this.portIdentifier + "\noperationIdentifier=" + this.operationIdentifier;
/*     */ 
/*     */     
/* 283 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\configuration\StaticApplicationContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */