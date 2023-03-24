/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.core.ID;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeRef;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.DefaultValueLoaderDecorator;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.LeafPropertyLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.LeafPropertyXsiLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import javax.xml.bind.JAXBElement;
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
/*     */ final class SingleElementLeafProperty<BeanT>
/*     */   extends PropertyImpl<BeanT>
/*     */ {
/*     */   private final Name tagName;
/*     */   private final boolean nillable;
/*     */   private final Accessor acc;
/*     */   private final String defaultValue;
/*     */   private final TransducedAccessor<BeanT> xacc;
/*     */   private final boolean improvedXsiTypeHandling;
/*     */   private final boolean idRef;
/*     */   
/*     */   public SingleElementLeafProperty(JAXBContextImpl context, RuntimeElementPropertyInfo prop) {
/*  86 */     super(context, (RuntimePropertyInfo)prop);
/*  87 */     RuntimeTypeRef ref = prop.getTypes().get(0);
/*  88 */     this.tagName = context.nameBuilder.createElementName(ref.getTagName());
/*  89 */     assert this.tagName != null;
/*  90 */     this.nillable = ref.isNillable();
/*  91 */     this.defaultValue = ref.getDefaultValue();
/*  92 */     this.acc = prop.getAccessor().optimize(context);
/*     */     
/*  94 */     this.xacc = TransducedAccessor.get(context, (RuntimeNonElementRef)ref);
/*  95 */     assert this.xacc != null;
/*     */     
/*  97 */     this.improvedXsiTypeHandling = context.improvedXsiTypeHandling;
/*  98 */     this.idRef = (ref.getSource().id() == ID.IDREF);
/*     */   }
/*     */   
/*     */   public void reset(BeanT o) throws AccessorException {
/* 102 */     this.acc.set(o, null);
/*     */   }
/*     */   
/*     */   public String getIdValue(BeanT bean) throws AccessorException, SAXException {
/* 106 */     return this.xacc.print(bean).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 111 */     boolean hasValue = this.xacc.hasValue(o);
/*     */     
/* 113 */     Object obj = null;
/*     */     
/*     */     try {
/* 116 */       obj = this.acc.getUnadapted(o);
/* 117 */     } catch (AccessorException ae) {}
/*     */ 
/*     */ 
/*     */     
/* 121 */     Class valueType = this.acc.getValueType();
/*     */ 
/*     */     
/* 124 */     if (xsiTypeNeeded(o, w, obj, valueType)) {
/* 125 */       w.startElement(this.tagName, outerPeer);
/* 126 */       w.childAsXsiType(obj, this.fieldName, w.grammar.getBeanInfo(valueType), false);
/* 127 */       w.endElement();
/*     */     }
/* 129 */     else if (hasValue) {
/* 130 */       this.xacc.writeLeafElement(w, this.tagName, o, this.fieldName);
/* 131 */     } else if (this.nillable) {
/* 132 */       w.startElement(this.tagName, null);
/* 133 */       w.writeXsiNilTrue();
/* 134 */       w.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean xsiTypeNeeded(BeanT bean, XMLSerializer w, Object value, Class valueTypeClass) {
/* 143 */     if (!this.improvedXsiTypeHandling)
/* 144 */       return false; 
/* 145 */     if (this.acc.isAdapted())
/* 146 */       return false; 
/* 147 */     if (value == null)
/* 148 */       return false; 
/* 149 */     if (value.getClass().equals(valueTypeClass))
/* 150 */       return false; 
/* 151 */     if (this.idRef)
/* 152 */       return false; 
/* 153 */     if (valueTypeClass.isPrimitive())
/* 154 */       return false; 
/* 155 */     return (this.acc.isValueTypeAbstractable() || isNillableAbstract(bean, w.grammar, value, valueTypeClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isNillableAbstract(BeanT bean, JAXBContextImpl context, Object value, Class<Object> valueTypeClass) {
/* 162 */     if (!this.nillable)
/* 163 */       return false; 
/* 164 */     if (valueTypeClass != Object.class)
/* 165 */       return false; 
/* 166 */     if (bean.getClass() != JAXBElement.class)
/* 167 */       return false; 
/* 168 */     JAXBElement jaxbElement = (JAXBElement)bean;
/* 169 */     Class<?> valueClass = value.getClass();
/* 170 */     Class declaredTypeClass = jaxbElement.getDeclaredType();
/* 171 */     if (declaredTypeClass.equals(valueClass))
/* 172 */       return false; 
/* 173 */     if (!declaredTypeClass.isAssignableFrom(valueClass))
/* 174 */       return false; 
/* 175 */     if (!Modifier.isAbstract(declaredTypeClass.getModifiers()))
/* 176 */       return false; 
/* 177 */     return this.acc.isAbstractable(declaredTypeClass); } public void buildChildElementUnmarshallers(UnmarshallerChain chain, QNameMap<ChildLoader> handlers) {
/*     */     DefaultValueLoaderDecorator defaultValueLoaderDecorator;
/*     */     XsiNilLoader.Single single;
/*     */     LeafPropertyXsiLoader leafPropertyXsiLoader;
/* 181 */     LeafPropertyLoader leafPropertyLoader = new LeafPropertyLoader(this.xacc);
/* 182 */     if (this.defaultValue != null)
/* 183 */       defaultValueLoaderDecorator = new DefaultValueLoaderDecorator((Loader)leafPropertyLoader, this.defaultValue); 
/* 184 */     if (this.nillable || chain.context.allNillable) {
/* 185 */       single = new XsiNilLoader.Single((Loader)defaultValueLoaderDecorator, this.acc);
/*     */     }
/*     */     
/* 188 */     if (this.improvedXsiTypeHandling) {
/* 189 */       leafPropertyXsiLoader = new LeafPropertyXsiLoader((Loader)single, this.xacc, this.acc);
/*     */     }
/* 191 */     handlers.put(this.tagName, new ChildLoader((Loader)leafPropertyXsiLoader, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyKind getKind() {
/* 196 */     return PropertyKind.ELEMENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 201 */     if (this.tagName.equals(nsUri, localName)) {
/* 202 */       return this.acc;
/*     */     }
/* 204 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\SingleElementLeafProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */