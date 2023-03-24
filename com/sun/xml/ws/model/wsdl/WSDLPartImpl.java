/*    */ package com.sun.xml.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.xml.ws.api.model.ParameterBinding;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPart;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPartDescriptor;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WSDLPartImpl
/*    */   extends AbstractObjectImpl
/*    */   implements WSDLPart
/*    */ {
/*    */   private final String name;
/*    */   private ParameterBinding binding;
/*    */   private int index;
/*    */   private final WSDLPartDescriptor descriptor;
/*    */   
/*    */   public WSDLPartImpl(XMLStreamReader xsr, String partName, int index, WSDLPartDescriptor descriptor) {
/* 61 */     super(xsr);
/* 62 */     this.name = partName;
/* 63 */     this.binding = ParameterBinding.UNBOUND;
/* 64 */     this.index = index;
/* 65 */     this.descriptor = descriptor;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 70 */     return this.name;
/*    */   }
/*    */   
/*    */   public ParameterBinding getBinding() {
/* 74 */     return this.binding;
/*    */   }
/*    */   
/*    */   public void setBinding(ParameterBinding binding) {
/* 78 */     this.binding = binding;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 82 */     return this.index;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setIndex(int index) {
/* 87 */     this.index = index;
/*    */   }
/*    */   
/*    */   boolean isBody() {
/* 91 */     return this.binding.isBody();
/*    */   }
/*    */   
/*    */   public WSDLPartDescriptor getDescriptor() {
/* 95 */     return this.descriptor;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLPartImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */