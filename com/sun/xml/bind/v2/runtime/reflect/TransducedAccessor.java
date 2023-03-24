/*     */ package com.sun.xml.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.istack.SAXException2;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.core.ID;
/*     */ import com.sun.xml.bind.v2.model.impl.RuntimeModelBuilder;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.opt.OptimizedTransducedAccessorFactory;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.LocatorEx;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Patcher;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ 
/*     */ 
/*     */ public abstract class TransducedAccessor<BeanT>
/*     */ {
/*     */   public boolean useNamespace() {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void declareNamespace(BeanT o, XMLSerializer w) throws AccessorException, SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract CharSequence print(@NotNull BeanT paramBeanT) throws AccessorException, SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void parse(BeanT paramBeanT, CharSequence paramCharSequence) throws AccessorException, SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean hasValue(BeanT paramBeanT) throws AccessorException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> TransducedAccessor<T> get(JAXBContextImpl context, RuntimeNonElementRef ref) {
/* 157 */     Transducer<?> xducer = RuntimeModelBuilder.createTransducer(ref);
/* 158 */     RuntimePropertyInfo prop = ref.getSource();
/*     */     
/* 160 */     if (prop.isCollection()) {
/* 161 */       return new ListTransducedAccessorImpl<T, Object, Object, Object>(xducer, prop.getAccessor(), Lister.create(Navigator.REFLECTION.erasure(prop.getRawType()), prop.id(), prop.getAdapter()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 166 */     if (prop.id() == ID.IDREF) {
/* 167 */       return new IDREFTransducedAccessorImpl<T, Object>(prop.getAccessor());
/*     */     }
/* 169 */     if (xducer.isDefault() && context != null && !context.fastBoot) {
/* 170 */       TransducedAccessor<T> xa = OptimizedTransducedAccessorFactory.get(prop);
/* 171 */       if (xa != null) return xa;
/*     */     
/*     */     } 
/* 174 */     if (xducer.useNamespace()) {
/* 175 */       return new CompositeContextDependentTransducedAccessorImpl<T, Object>(context, xducer, prop.getAccessor());
/*     */     }
/* 177 */     return new CompositeTransducedAccessorImpl<T, Object>(context, xducer, prop.getAccessor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void writeLeafElement(XMLSerializer paramXMLSerializer, Name paramName, BeanT paramBeanT, String paramString) throws SAXException, AccessorException, IOException, XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void writeText(XMLSerializer paramXMLSerializer, BeanT paramBeanT, String paramString) throws AccessorException, SAXException, IOException, XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class CompositeContextDependentTransducedAccessorImpl<BeanT, ValueT>
/*     */     extends CompositeTransducedAccessorImpl<BeanT, ValueT>
/*     */   {
/*     */     public CompositeContextDependentTransducedAccessorImpl(JAXBContextImpl context, Transducer<ValueT> xducer, Accessor<BeanT, ValueT> acc) {
/* 199 */       super(context, xducer, acc);
/* 200 */       assert xducer.useNamespace();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean useNamespace() {
/* 205 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void declareNamespace(BeanT bean, XMLSerializer w) throws AccessorException {
/* 210 */       ValueT o = this.acc.get(bean);
/* 211 */       if (o != null) {
/* 212 */         this.xducer.declareNamespace(o, w);
/*     */       }
/*     */     }
/*     */     
/*     */     public void writeLeafElement(XMLSerializer w, Name tagName, BeanT o, String fieldName) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 217 */       w.startElement(tagName, null);
/* 218 */       declareNamespace(o, w);
/* 219 */       w.endNamespaceDecls(null);
/* 220 */       w.endAttributes();
/* 221 */       this.xducer.writeText(w, this.acc.get(o), fieldName);
/* 222 */       w.endElement();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CompositeTransducedAccessorImpl<BeanT, ValueT>
/*     */     extends TransducedAccessor<BeanT>
/*     */   {
/*     */     protected final Transducer<ValueT> xducer;
/*     */     
/*     */     protected final Accessor<BeanT, ValueT> acc;
/*     */ 
/*     */     
/*     */     public CompositeTransducedAccessorImpl(JAXBContextImpl context, Transducer<ValueT> xducer, Accessor<BeanT, ValueT> acc) {
/* 236 */       this.xducer = xducer;
/* 237 */       this.acc = acc.optimize(context);
/*     */     }
/*     */     
/*     */     public CharSequence print(BeanT bean) throws AccessorException {
/* 241 */       ValueT o = this.acc.get(bean);
/* 242 */       if (o == null) return null; 
/* 243 */       return this.xducer.print(o);
/*     */     }
/*     */     
/*     */     public void parse(BeanT bean, CharSequence lexical) throws AccessorException, SAXException {
/* 247 */       this.acc.set(bean, (ValueT)this.xducer.parse(lexical));
/*     */     }
/*     */     
/*     */     public boolean hasValue(BeanT bean) throws AccessorException {
/* 251 */       return (this.acc.getUnadapted(bean) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeLeafElement(XMLSerializer w, Name tagName, BeanT o, String fieldName) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 256 */       this.xducer.writeLeafElement(w, tagName, this.acc.get(o), fieldName);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeText(XMLSerializer w, BeanT o, String fieldName) throws AccessorException, SAXException, IOException, XMLStreamException {
/* 261 */       this.xducer.writeText(w, this.acc.get(o), fieldName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class IDREFTransducedAccessorImpl<BeanT, TargetT>
/*     */     extends DefaultTransducedAccessor<BeanT>
/*     */   {
/*     */     private final Accessor<BeanT, TargetT> acc;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class<TargetT> targetType;
/*     */ 
/*     */ 
/*     */     
/*     */     public IDREFTransducedAccessorImpl(Accessor<BeanT, TargetT> acc) {
/* 280 */       this.acc = acc;
/* 281 */       this.targetType = acc.getValueType();
/*     */     }
/*     */     
/*     */     public String print(BeanT bean) throws AccessorException, SAXException {
/* 285 */       TargetT target = this.acc.get(bean);
/* 286 */       if (target == null) return null;
/*     */       
/* 288 */       XMLSerializer w = XMLSerializer.getInstance();
/*     */       try {
/* 290 */         String id = w.grammar.getBeanInfo(target, true).getId(target, w);
/* 291 */         if (id == null)
/* 292 */           w.errorMissingId(target); 
/* 293 */         return id;
/* 294 */       } catch (JAXBException e) {
/* 295 */         w.reportError(null, e);
/* 296 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void assign(BeanT bean, TargetT t, UnmarshallingContext context) throws AccessorException {
/* 301 */       if (!this.targetType.isInstance(t)) {
/* 302 */         context.handleError(Messages.UNASSIGNABLE_TYPE.format(new Object[] { this.targetType, t.getClass() }));
/*     */       } else {
/* 304 */         this.acc.set(bean, t);
/*     */       } 
/*     */     } public void parse(final BeanT bean, CharSequence lexical) throws AccessorException, SAXException {
/*     */       TargetT t;
/* 308 */       final String idref = WhiteSpaceProcessor.trim(lexical).toString();
/* 309 */       final UnmarshallingContext context = UnmarshallingContext.getInstance();
/*     */       
/* 311 */       final Callable<TargetT> callable = context.getObjectFromId(idref, this.acc.valueType);
/* 312 */       if (callable == null) {
/*     */         
/* 314 */         context.errorUnresolvedIDREF(bean, idref, context.getLocator());
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       try {
/* 320 */         t = callable.call();
/* 321 */       } catch (SAXException e) {
/* 322 */         throw e;
/* 323 */       } catch (RuntimeException e) {
/* 324 */         throw e;
/* 325 */       } catch (Exception e) {
/* 326 */         throw new SAXException2(e);
/*     */       } 
/* 328 */       if (t != null) {
/* 329 */         assign(bean, t, context);
/*     */       } else {
/*     */         
/* 332 */         final LocatorEx.Snapshot loc = new LocatorEx.Snapshot(context.getLocator());
/* 333 */         context.addPatcher(new Patcher() {
/*     */               public void run() throws SAXException {
/*     */                 try {
/* 336 */                   TargetT t = callable.call();
/* 337 */                   if (t == null) {
/* 338 */                     context.errorUnresolvedIDREF(bean, idref, loc);
/*     */                   } else {
/* 340 */                     TransducedAccessor.IDREFTransducedAccessorImpl.this.assign((BeanT)bean, t, context);
/*     */                   } 
/* 342 */                 } catch (AccessorException e) {
/* 343 */                   context.handleError((Exception)e);
/* 344 */                 } catch (SAXException e) {
/* 345 */                   throw e;
/* 346 */                 } catch (RuntimeException e) {
/* 347 */                   throw e;
/* 348 */                 } catch (Exception e) {
/* 349 */                   throw new SAXException2(e);
/*     */                 } 
/*     */               }
/*     */             });
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasValue(BeanT bean) throws AccessorException {
/* 357 */       return (this.acc.get(bean) != null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\TransducedAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */