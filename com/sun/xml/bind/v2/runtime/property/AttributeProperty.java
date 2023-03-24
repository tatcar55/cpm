/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeAttributePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AttributeProperty<BeanT>
/*     */   extends PropertyImpl<BeanT>
/*     */   implements Comparable<AttributeProperty>
/*     */ {
/*     */   public final Name attName;
/*     */   public final TransducedAccessor<BeanT> xacc;
/*     */   private final Accessor acc;
/*     */   
/*     */   public AttributeProperty(JAXBContextImpl context, RuntimeAttributePropertyInfo prop) {
/*  89 */     super(context, (RuntimePropertyInfo)prop);
/*  90 */     this.attName = context.nameBuilder.createAttributeName(prop.getXmlName());
/*  91 */     this.xacc = TransducedAccessor.get(context, (RuntimeNonElementRef)prop);
/*  92 */     this.acc = prop.getAccessor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(BeanT o, XMLSerializer w) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 101 */     CharSequence value = this.xacc.print(o);
/* 102 */     if (value != null)
/* 103 */       w.attribute(this.attName, value.toString()); 
/*     */   }
/*     */   
/*     */   public void serializeURIs(BeanT o, XMLSerializer w) throws AccessorException, SAXException {
/* 107 */     this.xacc.declareNamespace(o, w);
/*     */   }
/*     */   
/*     */   public boolean hasSerializeURIAction() {
/* 111 */     return this.xacc.useNamespace();
/*     */   }
/*     */   
/*     */   public void buildChildElementUnmarshallers(UnmarshallerChain chainElem, QNameMap<ChildLoader> handlers) {
/* 115 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyKind getKind() {
/* 120 */     return PropertyKind.ATTRIBUTE;
/*     */   }
/*     */   
/*     */   public void reset(BeanT o) throws AccessorException {
/* 124 */     this.acc.set(o, null);
/*     */   }
/*     */   
/*     */   public String getIdValue(BeanT bean) throws AccessorException, SAXException {
/* 128 */     return this.xacc.print(bean).toString();
/*     */   }
/*     */   
/*     */   public int compareTo(AttributeProperty that) {
/* 132 */     return this.attName.compareTo(that.attName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\AttributeProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */