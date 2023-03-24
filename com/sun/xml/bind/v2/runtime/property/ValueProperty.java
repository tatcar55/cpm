/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeValuePropertyInfo;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ValuePropertyLoader;
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ValueProperty<BeanT>
/*     */   extends PropertyImpl<BeanT>
/*     */ {
/*     */   private final TransducedAccessor<BeanT> xacc;
/*     */   private final Accessor<BeanT, ?> acc;
/*     */   
/*     */   public ValueProperty(JAXBContextImpl context, RuntimeValuePropertyInfo prop) {
/*  80 */     super(context, (RuntimePropertyInfo)prop);
/*  81 */     this.xacc = TransducedAccessor.get(context, (RuntimeNonElementRef)prop);
/*  82 */     this.acc = prop.getAccessor();
/*     */   }
/*     */   
/*     */   public final void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {
/*  86 */     if (this.xacc.hasValue(o))
/*  87 */       this.xacc.writeText(w, o, this.fieldName); 
/*     */   }
/*     */   
/*     */   public void serializeURIs(BeanT o, XMLSerializer w) throws SAXException, AccessorException {
/*  91 */     this.xacc.declareNamespace(o, w);
/*     */   }
/*     */   
/*     */   public boolean hasSerializeURIAction() {
/*  95 */     return this.xacc.useNamespace();
/*     */   }
/*     */   
/*     */   public void buildChildElementUnmarshallers(UnmarshallerChain chainElem, QNameMap<ChildLoader> handlers) {
/*  99 */     handlers.put(StructureLoaderBuilder.TEXT_HANDLER, new ChildLoader((Loader)new ValuePropertyLoader(this.xacc), null));
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyKind getKind() {
/* 104 */     return PropertyKind.VALUE;
/*     */   }
/*     */   
/*     */   public void reset(BeanT o) throws AccessorException {
/* 108 */     this.acc.set(o, null);
/*     */   }
/*     */   
/*     */   public String getIdValue(BeanT bean) throws AccessorException, SAXException {
/* 112 */     return this.xacc.print(bean).toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\ValueProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */