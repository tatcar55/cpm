/*     */ package com.sun.xml.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLMessage;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOutput;
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
/*     */ public final class WSDLOutputImpl
/*     */   extends AbstractExtensibleImpl
/*     */   implements WSDLOutput
/*     */ {
/*     */   private String name;
/*     */   private QName messageName;
/*     */   private WSDLOperationImpl operation;
/*     */   private WSDLMessageImpl message;
/*     */   private String action;
/*     */   private boolean defaultAction = true;
/*     */   
/*     */   public WSDLOutputImpl(XMLStreamReader xsr, String name, QName messageName, WSDLOperationImpl operation) {
/*  62 */     super(xsr);
/*  63 */     this.name = name;
/*  64 */     this.messageName = messageName;
/*  65 */     this.operation = operation;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  69 */     return (this.name == null) ? (this.operation.getName().getLocalPart() + "Response") : this.name;
/*     */   }
/*     */   
/*     */   public WSDLMessage getMessage() {
/*  73 */     return this.message;
/*     */   }
/*     */   
/*     */   public String getAction() {
/*  77 */     return this.action;
/*     */   }
/*     */   
/*     */   public boolean isDefaultAction() {
/*  81 */     return this.defaultAction;
/*     */   }
/*     */   
/*     */   public void setDefaultAction(boolean defaultAction) {
/*  85 */     this.defaultAction = defaultAction;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WSDLOperation getOperation() {
/*  90 */     return this.operation;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public QName getQName() {
/*  95 */     return new QName(this.operation.getName().getNamespaceURI(), getName());
/*     */   }
/*     */   
/*     */   public void setAction(String action) {
/*  99 */     this.action = action;
/*     */   }
/*     */   
/*     */   void freeze(WSDLModelImpl root) {
/* 103 */     this.message = root.getMessage(this.messageName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLOutputImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */