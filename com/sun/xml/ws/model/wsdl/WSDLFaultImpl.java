/*     */ package com.sun.xml.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
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
/*     */ public final class WSDLFaultImpl
/*     */   extends AbstractExtensibleImpl
/*     */   implements WSDLFault
/*     */ {
/*     */   private final String name;
/*     */   private final QName messageName;
/*     */   private WSDLMessageImpl message;
/*     */   private WSDLOperationImpl operation;
/*  58 */   private String action = "";
/*     */   private boolean defaultAction = true;
/*     */   
/*     */   public WSDLFaultImpl(XMLStreamReader xsr, String name, QName messageName, WSDLOperationImpl operation) {
/*  62 */     super(xsr);
/*  63 */     this.name = name;
/*  64 */     this.messageName = messageName;
/*  65 */     this.operation = operation;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  69 */     return this.name;
/*     */   }
/*     */   
/*     */   public WSDLMessageImpl getMessage() {
/*  73 */     return this.message;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WSDLOperation getOperation() {
/*  78 */     return this.operation;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public QName getQName() {
/*  83 */     return new QName(this.operation.getName().getNamespaceURI(), this.name);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getAction() {
/*  88 */     return this.action;
/*     */   }
/*     */   public void setAction(String action) {
/*  91 */     this.action = action;
/*     */   }
/*     */   
/*     */   public boolean isDefaultAction() {
/*  95 */     return this.defaultAction;
/*     */   }
/*     */   
/*     */   public void setDefaultAction(boolean defaultAction) {
/*  99 */     this.defaultAction = defaultAction;
/*     */   }
/*     */   
/*     */   void freeze(WSDLModelImpl root) {
/* 103 */     this.message = root.getMessage(this.messageName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLFaultImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */