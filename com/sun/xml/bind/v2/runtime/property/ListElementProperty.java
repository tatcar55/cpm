/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeRef;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.ListTransducedAccessorImpl;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.DefaultValueLoaderDecorator;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.LeafPropertyLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
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
/*     */ final class ListElementProperty<BeanT, ListT, ItemT>
/*     */   extends ArrayProperty<BeanT, ListT, ItemT>
/*     */ {
/*     */   private final Name tagName;
/*     */   private final String defaultValue;
/*     */   private final TransducedAccessor<BeanT> xacc;
/*     */   
/*     */   public ListElementProperty(JAXBContextImpl grammar, RuntimeElementPropertyInfo prop) {
/*  84 */     super(grammar, (RuntimePropertyInfo)prop);
/*     */     
/*  86 */     assert prop.isValueList();
/*  87 */     assert prop.getTypes().size() == 1;
/*  88 */     RuntimeTypeRef ref = prop.getTypes().get(0);
/*     */     
/*  90 */     this.tagName = grammar.nameBuilder.createElementName(ref.getTagName());
/*  91 */     this.defaultValue = ref.getDefaultValue();
/*     */ 
/*     */     
/*  94 */     Transducer xducer = ref.getTransducer();
/*     */     
/*  96 */     this.xacc = (TransducedAccessor<BeanT>)new ListTransducedAccessorImpl(xducer, this.acc, this.lister);
/*     */   }
/*     */   
/*     */   public PropertyKind getKind() {
/* 100 */     return PropertyKind.ELEMENT;
/*     */   }
/*     */   
/*     */   public void buildChildElementUnmarshallers(UnmarshallerChain chain, QNameMap<ChildLoader> handlers) {
/* 104 */     LeafPropertyLoader leafPropertyLoader = new LeafPropertyLoader(this.xacc);
/* 105 */     DefaultValueLoaderDecorator defaultValueLoaderDecorator = new DefaultValueLoaderDecorator((Loader)leafPropertyLoader, this.defaultValue);
/* 106 */     handlers.put(this.tagName, new ChildLoader((Loader)defaultValueLoaderDecorator, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 111 */     ListT list = (ListT)this.acc.get(o);
/*     */     
/* 113 */     if (list != null) {
/* 114 */       if (this.xacc.useNamespace()) {
/* 115 */         w.startElement(this.tagName, null);
/* 116 */         this.xacc.declareNamespace(o, w);
/* 117 */         w.endNamespaceDecls(list);
/* 118 */         w.endAttributes();
/* 119 */         this.xacc.writeText(w, o, this.fieldName);
/* 120 */         w.endElement();
/*     */       } else {
/* 122 */         this.xacc.writeLeafElement(w, this.tagName, o, this.fieldName);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 129 */     if (this.tagName != null && 
/* 130 */       this.tagName.equals(nsUri, localName)) {
/* 131 */       return this.acc;
/*     */     }
/* 133 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\ListElementProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */