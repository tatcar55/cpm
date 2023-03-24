/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeLeafInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.TextLoader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.XsiTypeLoader;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LeafBeanInfoImpl<BeanT>
/*     */   extends JaxBeanInfo<BeanT>
/*     */ {
/*     */   private final Loader loader;
/*     */   private final Loader loaderWithSubst;
/*     */   private final Transducer<BeanT> xducer;
/*     */   private final Name tagName;
/*     */   
/*     */   public LeafBeanInfoImpl(JAXBContextImpl grammar, RuntimeLeafInfo li) {
/*  85 */     super(grammar, (RuntimeTypeInfo)li, li.getClazz(), li.getTypeNames(), li.isElement(), true, false);
/*     */     
/*  87 */     this.xducer = li.getTransducer();
/*  88 */     this.loader = (Loader)new TextLoader(this.xducer);
/*  89 */     this.loaderWithSubst = (Loader)new XsiTypeLoader(this);
/*     */     
/*  91 */     if (isElement()) {
/*  92 */       this.tagName = grammar.nameBuilder.createElementName(li.getElementName());
/*     */     } else {
/*  94 */       this.tagName = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public QName getTypeName(BeanT instance) {
/*  99 */     QName tn = this.xducer.getTypeName(instance);
/* 100 */     if (tn != null) return tn;
/*     */     
/* 102 */     return super.getTypeName(instance);
/*     */   }
/*     */   
/*     */   public final String getElementNamespaceURI(BeanT t) {
/* 106 */     return this.tagName.nsUri;
/*     */   }
/*     */   
/*     */   public final String getElementLocalName(BeanT t) {
/* 110 */     return this.tagName.localName;
/*     */   }
/*     */   
/*     */   public BeanT createInstance(UnmarshallingContext context) {
/* 114 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public final boolean reset(BeanT bean, UnmarshallingContext context) {
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   public final String getId(BeanT bean, XMLSerializer target) {
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serializeBody(BeanT bean, XMLSerializer w) throws SAXException, IOException, XMLStreamException {
/*     */     try {
/* 130 */       this.xducer.writeText(w, bean, null);
/* 131 */     } catch (AccessorException e) {
/* 132 */       w.reportError(null, (Throwable)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void serializeAttributes(BeanT bean, XMLSerializer target) {}
/*     */ 
/*     */   
/*     */   public final void serializeRoot(BeanT bean, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 141 */     if (this.tagName == null) {
/* 142 */       target.reportError(new ValidationEventImpl(1, Messages.UNABLE_TO_MARSHAL_NON_ELEMENT.format(new Object[] { bean.getClass().getName() }, ), null, null));
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 150 */       target.startElement(this.tagName, bean);
/* 151 */       target.childAsSoleContent(bean, null);
/* 152 */       target.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serializeURIs(BeanT bean, XMLSerializer target) throws SAXException {
/* 159 */     if (this.xducer.useNamespace()) {
/*     */       try {
/* 161 */         this.xducer.declareNamespace(bean, target);
/* 162 */       } catch (AccessorException e) {
/* 163 */         target.reportError(null, (Throwable)e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public final Loader getLoader(JAXBContextImpl context, boolean typeSubstitutionCapable) {
/* 169 */     if (typeSubstitutionCapable) {
/* 170 */       return this.loaderWithSubst;
/*     */     }
/* 172 */     return this.loader;
/*     */   }
/*     */   
/*     */   public Transducer<BeanT> getTransducer() {
/* 176 */     return this.xducer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\LeafBeanInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */