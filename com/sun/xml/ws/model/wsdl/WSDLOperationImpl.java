/*     */ package com.sun.xml.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLInput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOutput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPortType;
/*     */ import com.sun.xml.ws.util.QNameMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public final class WSDLOperationImpl
/*     */   extends AbstractExtensibleImpl
/*     */   implements WSDLOperation
/*     */ {
/*     */   private final QName name;
/*     */   private String parameterOrder;
/*     */   private WSDLInputImpl input;
/*     */   private WSDLOutputImpl output;
/*     */   private final List<WSDLFaultImpl> faults;
/*     */   private final QNameMap<WSDLFaultImpl> faultMap;
/*     */   protected Iterable<WSDLMessageImpl> messages;
/*     */   private final WSDLPortType owner;
/*     */   
/*     */   public WSDLOperationImpl(XMLStreamReader xsr, WSDLPortTypeImpl owner, QName name) {
/*  72 */     super(xsr);
/*  73 */     this.name = name;
/*  74 */     this.faults = new ArrayList<WSDLFaultImpl>();
/*  75 */     this.faultMap = new QNameMap();
/*  76 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  80 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getParameterOrder() {
/*  84 */     return this.parameterOrder;
/*     */   }
/*     */   
/*     */   public void setParameterOrder(String parameterOrder) {
/*  88 */     this.parameterOrder = parameterOrder;
/*     */   }
/*     */   
/*     */   public WSDLInputImpl getInput() {
/*  92 */     return this.input;
/*     */   }
/*     */   
/*     */   public void setInput(WSDLInputImpl input) {
/*  96 */     this.input = input;
/*     */   }
/*     */   
/*     */   public WSDLOutputImpl getOutput() {
/* 100 */     return this.output;
/*     */   }
/*     */   
/*     */   public boolean isOneWay() {
/* 104 */     return (this.output == null);
/*     */   }
/*     */   
/*     */   public void setOutput(WSDLOutputImpl output) {
/* 108 */     this.output = output;
/*     */   }
/*     */   
/*     */   public Iterable<WSDLFaultImpl> getFaults() {
/* 112 */     return this.faults;
/*     */   }
/*     */   
/*     */   public WSDLFault getFault(QName faultDetailName) {
/* 116 */     WSDLFaultImpl fault = (WSDLFaultImpl)this.faultMap.get(faultDetailName);
/* 117 */     if (fault != null) {
/* 118 */       return fault;
/*     */     }
/* 120 */     for (WSDLFaultImpl fi : this.faults) {
/* 121 */       assert fi.getMessage().parts().iterator().hasNext();
/* 122 */       WSDLPartImpl part = fi.getMessage().parts().iterator().next();
/* 123 */       if (part.getDescriptor().name().equals(faultDetailName)) {
/* 124 */         this.faultMap.put(faultDetailName, fi);
/* 125 */         return fi;
/*     */       } 
/*     */     } 
/* 128 */     return null;
/*     */   }
/*     */   
/*     */   WSDLPortType getOwner() {
/* 132 */     return this.owner;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public QName getPortTypeName() {
/* 137 */     return this.owner.getName();
/*     */   }
/*     */   
/*     */   public void addFault(WSDLFaultImpl fault) {
/* 141 */     this.faults.add(fault);
/*     */   }
/*     */   
/*     */   public void freez(WSDLModelImpl root) {
/* 145 */     assert this.input != null;
/* 146 */     this.input.freeze(root);
/* 147 */     if (this.output != null)
/* 148 */       this.output.freeze(root); 
/* 149 */     for (WSDLFaultImpl fault : this.faults)
/* 150 */       fault.freeze(root); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLOperationImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */