/*     */ package com.sun.xml.ws.model;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.model.Parameter;
/*     */ import com.sun.xml.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.spi.db.RepeatedElementBridge;
/*     */ import com.sun.xml.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.ws.spi.db.WrapperComposite;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.util.List;
/*     */ import javax.jws.WebParam;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Holder;
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
/*     */ public class ParameterImpl
/*     */   implements Parameter
/*     */ {
/*     */   private ParameterBinding binding;
/*     */   private ParameterBinding outBinding;
/*     */   private String partName;
/*     */   private final int index;
/*     */   private final WebParam.Mode mode;
/*     */   private TypeReference typeReference;
/*     */   private TypeInfo typeInfo;
/*     */   private QName name;
/*     */   private final JavaMethodImpl parent;
/*     */   WrapperParameter wrapper;
/*     */   TypeInfo itemTypeInfo;
/*     */   
/*     */   public ParameterImpl(JavaMethodImpl parent, TypeInfo type, WebParam.Mode mode, int index) {
/*  89 */     assert type != null;
/*     */     
/*  91 */     this.typeInfo = type;
/*  92 */     this.name = type.tagName;
/*  93 */     this.mode = mode;
/*  94 */     this.index = index;
/*  95 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public AbstractSEIModelImpl getOwner() {
/*  99 */     return this.parent.owner;
/*     */   }
/*     */   
/*     */   public JavaMethod getParent() {
/* 103 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 110 */     return this.name;
/*     */   }
/*     */   
/*     */   public XMLBridge getXMLBridge() {
/* 114 */     return getOwner().getXMLBridge(this.typeInfo);
/*     */   }
/*     */   
/*     */   public XMLBridge getInlinedRepeatedElementBridge() {
/* 118 */     TypeInfo itemType = getItemType();
/* 119 */     if (itemType != null) {
/* 120 */       XMLBridge xb = getOwner().getXMLBridge(itemType);
/* 121 */       if (xb != null) return (XMLBridge)new RepeatedElementBridge(this.typeInfo, xb); 
/*     */     } 
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   public TypeInfo getItemType() {
/* 127 */     if (this.itemTypeInfo != null) return this.itemTypeInfo;
/*     */     
/* 129 */     if (this.parent.getBinding().isRpcLit() || this.wrapper == null) return null;
/*     */     
/* 131 */     if (!WrapperComposite.class.equals((this.wrapper.getTypeInfo()).type)) return null; 
/* 132 */     if (!getBinding().isBody()) return null; 
/* 133 */     this.itemTypeInfo = this.typeInfo.getItemType();
/* 134 */     return this.itemTypeInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bridge getBridge() {
/* 139 */     return getOwner().getBridge(this.typeReference);
/*     */   }
/*     */   
/*     */   protected Bridge getBridge(TypeReference typeRef) {
/* 143 */     return getOwner().getBridge(typeRef);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeReference getTypeReference() {
/* 153 */     return this.typeReference;
/*     */   }
/*     */   public TypeInfo getTypeInfo() {
/* 156 */     return this.typeInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTypeReference(TypeReference type) {
/* 165 */     this.typeReference = type;
/* 166 */     this.name = type.tagName;
/*     */   }
/*     */ 
/*     */   
/*     */   public WebParam.Mode getMode() {
/* 171 */     return this.mode;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/* 175 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWrapperStyle() {
/* 182 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isReturnValue() {
/* 186 */     return (this.index == -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterBinding getBinding() {
/* 193 */     if (this.binding == null)
/* 194 */       return ParameterBinding.BODY; 
/* 195 */     return this.binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBinding(ParameterBinding binding) {
/* 202 */     this.binding = binding;
/*     */   }
/*     */   
/*     */   public void setInBinding(ParameterBinding binding) {
/* 206 */     this.binding = binding;
/*     */   }
/*     */   
/*     */   public void setOutBinding(ParameterBinding binding) {
/* 210 */     this.outBinding = binding;
/*     */   }
/*     */   
/*     */   public ParameterBinding getInBinding() {
/* 214 */     return this.binding;
/*     */   }
/*     */   
/*     */   public ParameterBinding getOutBinding() {
/* 218 */     if (this.outBinding == null)
/* 219 */       return this.binding; 
/* 220 */     return this.outBinding;
/*     */   }
/*     */   
/*     */   public boolean isIN() {
/* 224 */     return (this.mode == WebParam.Mode.IN);
/*     */   }
/*     */   
/*     */   public boolean isOUT() {
/* 228 */     return (this.mode == WebParam.Mode.OUT);
/*     */   }
/*     */   
/*     */   public boolean isINOUT() {
/* 232 */     return (this.mode == WebParam.Mode.INOUT);
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
/*     */   public boolean isResponse() {
/* 244 */     return (this.index == -1);
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
/*     */   public Object getHolderValue(Object obj) {
/* 256 */     if (obj != null && obj instanceof Holder)
/* 257 */       return ((Holder)obj).value; 
/* 258 */     return obj;
/*     */   }
/*     */   
/*     */   public String getPartName() {
/* 262 */     if (this.partName == null)
/* 263 */       return this.name.getLocalPart(); 
/* 264 */     return this.partName;
/*     */   }
/*     */   
/*     */   public void setPartName(String partName) {
/* 268 */     this.partName = partName;
/*     */   }
/*     */   
/*     */   void fillTypes(List<TypeInfo> types) {
/* 272 */     TypeInfo itemType = getItemType();
/* 273 */     types.add((itemType != null) ? itemType : getTypeInfo());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\ParameterImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */