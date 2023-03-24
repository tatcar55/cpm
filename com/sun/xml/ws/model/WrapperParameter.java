/*     */ package com.sun.xml.ws.model;
/*     */ 
/*     */ import com.sun.xml.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.ws.spi.db.WrapperComposite;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.jws.WebParam;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperParameter
/*     */   extends ParameterImpl
/*     */ {
/*  70 */   protected final List<ParameterImpl> wrapperChildren = new ArrayList<ParameterImpl>();
/*     */ 
/*     */   
/*     */   public WrapperParameter(JavaMethodImpl parent, TypeInfo typeRef, WebParam.Mode mode, int index) {
/*  74 */     super(parent, typeRef, mode, index);
/*     */     
/*  76 */     typeRef.properties().put(WrapperParameter.class.getName(), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWrapperStyle() {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ParameterImpl> getWrapperChildren() {
/*  93 */     return this.wrapperChildren;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addWrapperChild(ParameterImpl wrapperChild) {
/* 102 */     this.wrapperChildren.add(wrapperChild);
/* 103 */     wrapperChild.wrapper = this;
/*     */     
/* 105 */     assert wrapperChild.getBinding() == ParameterBinding.BODY;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 109 */     this.wrapperChildren.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   void fillTypes(List<TypeInfo> types) {
/* 114 */     super.fillTypes(types);
/* 115 */     if (WrapperComposite.class.equals((getTypeInfo()).type))
/* 116 */       for (ParameterImpl p : this.wrapperChildren) p.fillTypes(types);  
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\WrapperParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */