/*     */ package com.sun.xml.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.bind.v2.ClassFactory;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.model.core.WildcardMode;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElement;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeReferencePropertyInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.ListIterator;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.WildcardLoader;
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.annotation.DomHandler;
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
/*     */ class ArrayReferenceNodeProperty<BeanT, ListT, ItemT>
/*     */   extends ArrayERProperty<BeanT, ListT, ItemT>
/*     */ {
/*  76 */   private final QNameMap<JaxBeanInfo> expectedElements = new QNameMap();
/*     */   
/*     */   private final boolean isMixed;
/*     */   
/*     */   private final DomHandler domHandler;
/*     */   private final WildcardMode wcMode;
/*     */   
/*     */   public ArrayReferenceNodeProperty(JAXBContextImpl p, RuntimeReferencePropertyInfo prop) {
/*  84 */     super(p, (RuntimePropertyInfo)prop, prop.getXmlName(), prop.isCollectionNillable());
/*     */     
/*  86 */     for (RuntimeElement e : prop.getElements()) {
/*  87 */       JaxBeanInfo bi = p.getOrCreate((RuntimeTypeInfo)e);
/*  88 */       this.expectedElements.put(e.getElementName().getNamespaceURI(), e.getElementName().getLocalPart(), bi);
/*     */     } 
/*     */     
/*  91 */     this.isMixed = prop.isMixed();
/*     */     
/*  93 */     if (prop.getWildcard() != null) {
/*  94 */       this.domHandler = (DomHandler)ClassFactory.create((Class)prop.getDOMHandler());
/*  95 */       this.wcMode = prop.getWildcard();
/*     */     } else {
/*  97 */       this.domHandler = null;
/*  98 */       this.wcMode = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void serializeListBody(BeanT o, XMLSerializer w, ListT list) throws IOException, XMLStreamException, SAXException {
/* 103 */     ListIterator<ItemT> itr = this.lister.iterator(list, w);
/*     */     
/* 105 */     while (itr.hasNext()) {
/*     */       try {
/* 107 */         ItemT item = (ItemT)itr.next();
/* 108 */         if (item != null) {
/* 109 */           if (this.isMixed && item.getClass() == String.class) {
/* 110 */             w.text((String)item, null); continue;
/*     */           } 
/* 112 */           JaxBeanInfo bi = w.grammar.getBeanInfo(item, true);
/* 113 */           if (bi.jaxbType == Object.class && this.domHandler != null) {
/*     */ 
/*     */             
/* 116 */             w.writeDom(item, this.domHandler, o, this.fieldName); continue;
/*     */           } 
/* 118 */           bi.serializeRoot(item, w);
/*     */         }
/*     */       
/* 121 */       } catch (JAXBException e) {
/* 122 */         w.reportError(this.fieldName, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createBodyUnmarshaller(UnmarshallerChain chain, QNameMap<ChildLoader> loaders) {
/* 129 */     int offset = chain.allocateOffset();
/*     */     
/* 131 */     Receiver recv = new ArrayERProperty.ReceiverImpl(this, offset);
/*     */     
/* 133 */     for (QNameMap.Entry<JaxBeanInfo> n : (Iterable<QNameMap.Entry<JaxBeanInfo>>)this.expectedElements.entrySet()) {
/* 134 */       JaxBeanInfo beanInfo = (JaxBeanInfo)n.getValue();
/* 135 */       loaders.put(n.nsUri, n.localName, new ChildLoader(beanInfo.getLoader(chain.context, true), recv));
/*     */     } 
/*     */     
/* 138 */     if (this.isMixed)
/*     */     {
/* 140 */       loaders.put(TEXT_HANDLER, new ChildLoader(new MixedTextLoader(recv), null));
/*     */     }
/*     */ 
/*     */     
/* 144 */     if (this.domHandler != null) {
/* 145 */       loaders.put(CATCH_ALL, new ChildLoader((Loader)new WildcardLoader(this.domHandler, this.wcMode), recv));
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MixedTextLoader
/*     */     extends Loader
/*     */   {
/*     */     private final Receiver recv;
/*     */     
/*     */     public MixedTextLoader(Receiver recv) {
/* 155 */       super(true);
/* 156 */       this.recv = recv;
/*     */     }
/*     */     
/*     */     public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/* 160 */       if (text.length() != 0) {
/* 161 */         this.recv.receive(state, text.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public PropertyKind getKind() {
/* 167 */     return PropertyKind.REFERENCE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 173 */     if (this.wrapperTagName != null) {
/* 174 */       if (this.wrapperTagName.equals(nsUri, localName)) {
/* 175 */         return this.acc;
/*     */       }
/* 177 */     } else if (this.expectedElements.containsKey(nsUri, localName)) {
/* 178 */       return this.acc;
/*     */     } 
/* 180 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\property\ArrayReferenceNodeProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */