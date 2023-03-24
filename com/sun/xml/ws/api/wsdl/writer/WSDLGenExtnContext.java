/*     */ package com.sun.xml.ws.api.wsdl.writer;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.txw2.TypedXmlWriter;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSDLGenExtnContext
/*     */ {
/*     */   private final TypedXmlWriter root;
/*     */   private final SEIModel model;
/*     */   private final WSBinding binding;
/*     */   private final Container container;
/*     */   private final Class endpointClass;
/*     */   
/*     */   public WSDLGenExtnContext(@NotNull TypedXmlWriter root, @NotNull SEIModel model, @NotNull WSBinding binding, @Nullable Container container, @NotNull Class endpointClass) {
/*  78 */     this.root = root;
/*  79 */     this.model = model;
/*  80 */     this.binding = binding;
/*  81 */     this.container = container;
/*  82 */     this.endpointClass = endpointClass;
/*     */   }
/*     */   
/*     */   public TypedXmlWriter getRoot() {
/*  86 */     return this.root;
/*     */   }
/*     */   
/*     */   public SEIModel getModel() {
/*  90 */     return this.model;
/*     */   }
/*     */   
/*     */   public WSBinding getBinding() {
/*  94 */     return this.binding;
/*     */   }
/*     */   
/*     */   public Container getContainer() {
/*  98 */     return this.container;
/*     */   }
/*     */   
/*     */   public Class getEndpointClass() {
/* 102 */     return this.endpointClass;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\wsdl\writer\WSDLGenExtnContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */