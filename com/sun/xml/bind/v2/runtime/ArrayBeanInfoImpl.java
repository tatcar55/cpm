/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeArrayInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.TagName;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import javax.xml.namespace.QName;
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
/*     */ final class ArrayBeanInfoImpl
/*     */   extends JaxBeanInfo
/*     */ {
/*     */   private final Class itemType;
/*     */   private final JaxBeanInfo itemBeanInfo;
/*     */   private Loader loader;
/*     */   
/*     */   public ArrayBeanInfoImpl(JAXBContextImpl owner, RuntimeArrayInfo rai) {
/*  76 */     super(owner, (RuntimeTypeInfo)rai, rai.getType(), rai.getTypeName(), false, true, false);
/*  77 */     this.itemType = this.jaxbType.getComponentType();
/*  78 */     this.itemBeanInfo = owner.getOrCreate((RuntimeTypeInfo)rai.getItemType());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void link(JAXBContextImpl grammar) {
/*  83 */     getLoader(grammar, false);
/*  84 */     super.link(grammar);
/*     */   }
/*     */   
/*     */   private final class ArrayLoader extends Loader implements Receiver {
/*     */     public ArrayLoader(JAXBContextImpl owner) {
/*  89 */       super(false);
/*  90 */       this.itemLoader = ArrayBeanInfoImpl.this.itemBeanInfo.getLoader(owner, true);
/*     */     }
/*     */ 
/*     */     
/*     */     private final Loader itemLoader;
/*     */     
/*     */     public void startElement(UnmarshallingContext.State state, TagName ea) {
/*  97 */       state.target = new ArrayList();
/*     */     }
/*     */ 
/*     */     
/*     */     public void leaveElement(UnmarshallingContext.State state, TagName ea) {
/* 102 */       state.target = ArrayBeanInfoImpl.this.toArray((List)state.target);
/*     */     }
/*     */ 
/*     */     
/*     */     public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 107 */       if (ea.matches("", "item")) {
/* 108 */         state.loader = this.itemLoader;
/* 109 */         state.receiver = this;
/*     */       } else {
/* 111 */         super.childElement(state, ea);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<QName> getExpectedChildElements() {
/* 117 */       return Collections.singleton(new QName("", "item"));
/*     */     }
/*     */     
/*     */     public void receive(UnmarshallingContext.State state, Object o) {
/* 121 */       ((List<Object>)state.target).add(o);
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object toArray(List list) {
/* 126 */     int len = list.size();
/* 127 */     Object array = Array.newInstance(this.itemType, len);
/* 128 */     for (int i = 0; i < len; i++)
/* 129 */       Array.set(array, i, list.get(i)); 
/* 130 */     return array;
/*     */   }
/*     */   
/*     */   public void serializeBody(Object array, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 134 */     int len = Array.getLength(array);
/* 135 */     for (int i = 0; i < len; i++) {
/* 136 */       Object item = Array.get(array, i);
/*     */       
/* 138 */       target.startElement("", "item", null, null);
/* 139 */       if (item == null) {
/* 140 */         target.writeXsiNilTrue();
/*     */       } else {
/* 142 */         target.childAsXsiType(item, "arrayItem", this.itemBeanInfo, false);
/*     */       } 
/* 144 */       target.endElement();
/*     */     } 
/*     */   }
/*     */   
/*     */   public final String getElementNamespaceURI(Object array) {
/* 149 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public final String getElementLocalName(Object array) {
/* 153 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object createInstance(UnmarshallingContext context) {
/* 158 */     return new ArrayList();
/*     */   }
/*     */   
/*     */   public final boolean reset(Object array, UnmarshallingContext context) {
/* 162 */     return false;
/*     */   }
/*     */   
/*     */   public final String getId(Object array, XMLSerializer target) {
/* 166 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void serializeAttributes(Object array, XMLSerializer target) {}
/*     */ 
/*     */   
/*     */   public final void serializeRoot(Object array, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 174 */     target.reportError(new ValidationEventImpl(1, Messages.UNABLE_TO_MARSHAL_NON_ELEMENT.format(new Object[] { array.getClass().getName() }, ), null, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serializeURIs(Object array, XMLSerializer target) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Transducer getTransducer() {
/* 187 */     return null;
/*     */   }
/*     */   
/*     */   public final Loader getLoader(JAXBContextImpl context, boolean typeSubstitutionCapable) {
/* 191 */     if (this.loader == null) {
/* 192 */       this.loader = new ArrayLoader(context);
/*     */     }
/*     */     
/* 195 */     return this.loader;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\ArrayBeanInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */