/*     */ package com.sun.xml.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLInput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLMessage;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Locator;
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
/*     */ public final class WSDLInputImpl
/*     */   extends AbstractExtensibleImpl
/*     */   implements WSDLInput
/*     */ {
/*     */   private String name;
/*     */   private QName messageName;
/*     */   private WSDLOperationImpl operation;
/*     */   private WSDLMessageImpl message;
/*     */   private String action;
/*     */   private boolean defaultAction = true;
/*     */   
/*     */   public WSDLInputImpl(XMLStreamReader xsr, String name, QName messageName, WSDLOperationImpl operation) {
/*  64 */     super(xsr);
/*  65 */     this.name = name;
/*  66 */     this.messageName = messageName;
/*  67 */     this.operation = operation;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  71 */     if (this.name != null) {
/*  72 */       return this.name;
/*     */     }
/*  74 */     return this.operation.isOneWay() ? this.operation.getName().getLocalPart() : (this.operation.getName().getLocalPart() + "Request");
/*     */   }
/*     */   
/*     */   public WSDLMessage getMessage() {
/*  78 */     return this.message;
/*     */   }
/*     */   
/*     */   public String getAction() {
/*  82 */     return this.action;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WSDLOperation getOperation() {
/*  87 */     return this.operation;
/*     */   }
/*     */   
/*     */   public QName getQName() {
/*  91 */     return new QName(this.operation.getName().getNamespaceURI(), getName());
/*     */   }
/*     */   
/*     */   public void setAction(String action) {
/*  95 */     this.action = action;
/*     */   }
/*     */   
/*     */   public boolean isDefaultAction() {
/*  99 */     return this.defaultAction;
/*     */   }
/*     */   
/*     */   public void setDefaultAction(boolean defaultAction) {
/* 103 */     this.defaultAction = defaultAction;
/*     */   }
/*     */   
/*     */   void freeze(WSDLModelImpl parent) {
/* 107 */     this.message = parent.getMessage(this.messageName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLInputImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */