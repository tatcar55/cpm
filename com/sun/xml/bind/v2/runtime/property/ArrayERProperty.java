/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Lister;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.TagName;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ArrayERProperty<BeanT, ListT, ItemT>
/*     */   extends ArrayProperty<BeanT, ListT, ItemT>
/*     */ {
/*     */   protected final Name wrapperTagName;
/*     */   protected final boolean isWrapperNillable;
/*     */   
/*     */   protected ArrayERProperty(JAXBContextImpl grammar, RuntimePropertyInfo prop, QName tagName, boolean isWrapperNillable) {
/*  88 */     super(grammar, prop);
/*  89 */     if (tagName == null) {
/*  90 */       this.wrapperTagName = null;
/*     */     } else {
/*  92 */       this.wrapperTagName = grammar.nameBuilder.createElementName(tagName);
/*  93 */     }  this.isWrapperNillable = isWrapperNillable;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ItemsLoader
/*     */     extends Loader
/*     */   {
/*     */     private final Accessor acc;
/*     */     private final Lister lister;
/*     */     private final QNameMap<ChildLoader> children;
/*     */     
/*     */     public ItemsLoader(Accessor acc, Lister lister, QNameMap<ChildLoader> children) {
/* 105 */       super(false);
/* 106 */       this.acc = acc;
/* 107 */       this.lister = lister;
/* 108 */       this.children = children;
/*     */     }
/*     */ 
/*     */     
/*     */     public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 113 */       UnmarshallingContext context = state.getContext();
/* 114 */       context.startScope(1);
/*     */       
/* 116 */       state.target = state.prev.target;
/*     */ 
/*     */       
/* 119 */       context.getScope(0).start(this.acc, this.lister);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 126 */       ChildLoader child = (ChildLoader)this.children.get(ea.uri, ea.local);
/* 127 */       if (child == null) {
/* 128 */         child = (ChildLoader)this.children.get(StructureLoaderBuilder.CATCH_ALL);
/*     */       }
/* 130 */       if (child == null) {
/* 131 */         super.childElement(state, ea);
/*     */         return;
/*     */       } 
/* 134 */       state.loader = child.loader;
/* 135 */       state.receiver = child.receiver;
/*     */     }
/*     */ 
/*     */     
/*     */     public void leaveElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 140 */       state.getContext().endScope(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<QName> getExpectedChildElements() {
/* 145 */       return this.children.keySet();
/*     */     }
/*     */   }
/*     */   
/*     */   public final void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 150 */     ListT list = (ListT)this.acc.get(o);
/*     */     
/* 152 */     if (list != null) {
/* 153 */       if (this.wrapperTagName != null) {
/* 154 */         w.startElement(this.wrapperTagName, null);
/* 155 */         w.endNamespaceDecls(list);
/* 156 */         w.endAttributes();
/*     */       } 
/*     */       
/* 159 */       serializeListBody(o, w, list);
/*     */       
/* 161 */       if (this.wrapperTagName != null) {
/* 162 */         w.endElement();
/*     */       }
/*     */     }
/* 165 */     else if (this.isWrapperNillable) {
/* 166 */       w.startElement(this.wrapperTagName, null);
/* 167 */       w.writeXsiNilTrue();
/* 168 */       w.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void serializeListBody(BeanT paramBeanT, XMLSerializer paramXMLSerializer, ListT paramListT) throws IOException, XMLStreamException, SAXException, AccessorException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void createBodyUnmarshaller(UnmarshallerChain paramUnmarshallerChain, QNameMap<ChildLoader> paramQNameMap);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void buildChildElementUnmarshallers(UnmarshallerChain chain, QNameMap<ChildLoader> loaders) {
/* 189 */     if (this.wrapperTagName != null) {
/* 190 */       XsiNilLoader xsiNilLoader; UnmarshallerChain c = new UnmarshallerChain(chain.context);
/* 191 */       QNameMap<ChildLoader> m = new QNameMap();
/* 192 */       createBodyUnmarshaller(c, m);
/* 193 */       Loader loader = new ItemsLoader(this.acc, this.lister, m);
/* 194 */       if (this.isWrapperNillable || chain.context.allNillable)
/* 195 */         xsiNilLoader = new XsiNilLoader(loader); 
/* 196 */       loaders.put(this.wrapperTagName, new ChildLoader((Loader)xsiNilLoader, null));
/*     */     } else {
/* 198 */       createBodyUnmarshaller(chain, loaders);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected final class ReceiverImpl
/*     */     implements Receiver
/*     */   {
/*     */     private final int offset;
/*     */     
/*     */     protected ReceiverImpl(int offset) {
/* 209 */       this.offset = offset;
/*     */     }
/*     */     
/*     */     public void receive(UnmarshallingContext.State state, Object o) throws SAXException {
/* 213 */       state.getContext().getScope(this.offset).add(ArrayERProperty.this.acc, ArrayERProperty.this.lister, o);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\ArrayERProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */