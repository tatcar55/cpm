/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.Util;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ public abstract class JaxBeanInfo<BeanT>
/*     */ {
/*     */   protected boolean isNilIncluded = false;
/*     */   protected short flag;
/*     */   private static final short FLAG_IS_ELEMENT = 1;
/*     */   private static final short FLAG_IS_IMMUTABLE = 2;
/*     */   private static final short FLAG_HAS_ELEMENT_ONLY_CONTENTMODEL = 4;
/*     */   private static final short FLAG_HAS_BEFORE_UNMARSHAL_METHOD = 8;
/*     */   private static final short FLAG_HAS_AFTER_UNMARSHAL_METHOD = 16;
/*     */   private static final short FLAG_HAS_BEFORE_MARSHAL_METHOD = 32;
/*     */   private static final short FLAG_HAS_AFTER_MARSHAL_METHOD = 64;
/*     */   private static final short FLAG_HAS_LIFECYCLE_EVENTS = 128;
/*     */   private LifecycleMethods lcm;
/*     */   public final Class<BeanT> jaxbType;
/*     */   private final Object typeName;
/*     */   
/*     */   protected JaxBeanInfo(JAXBContextImpl grammar, RuntimeTypeInfo rti, Class<BeanT> jaxbType, QName[] typeNames, boolean isElement, boolean isImmutable, boolean hasLifecycleEvents) {
/* 100 */     this(grammar, rti, jaxbType, typeNames, isElement, isImmutable, hasLifecycleEvents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JaxBeanInfo(JAXBContextImpl grammar, RuntimeTypeInfo rti, Class<BeanT> jaxbType, QName typeName, boolean isElement, boolean isImmutable, boolean hasLifecycleEvents) {
/* 107 */     this(grammar, rti, jaxbType, typeName, isElement, isImmutable, hasLifecycleEvents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JaxBeanInfo(JAXBContextImpl grammar, RuntimeTypeInfo rti, Class<BeanT> jaxbType, boolean isElement, boolean isImmutable, boolean hasLifecycleEvents) {
/* 114 */     this(grammar, rti, jaxbType, (Object)null, isElement, isImmutable, hasLifecycleEvents);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JaxBeanInfo(JAXBContextImpl grammar, RuntimeTypeInfo rti, Class<BeanT> jaxbType, Object typeName, boolean isElement, boolean isImmutable, boolean hasLifecycleEvents) {
/* 142 */     this.lcm = null;
/*     */     grammar.beanInfos.put(rti, this);
/*     */     this.jaxbType = jaxbType;
/*     */     this.typeName = typeName;
/*     */     this.flag = (short)((isElement ? 1 : 0) | (isImmutable ? 2 : 0) | (hasLifecycleEvents ? 128 : 0));
/*     */   } public final boolean hasBeforeUnmarshalMethod() {
/* 148 */     return ((this.flag & 0x8) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasAfterUnmarshalMethod() {
/* 155 */     return ((this.flag & 0x10) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasBeforeMarshalMethod() {
/* 162 */     return ((this.flag & 0x20) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasAfterMarshalMethod() {
/* 169 */     return ((this.flag & 0x40) != 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isElement() {
/* 192 */     return ((this.flag & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isImmutable() {
/* 203 */     return ((this.flag & 0x2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasElementOnlyContentModel() {
/* 213 */     return ((this.flag & 0x4) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void hasElementOnlyContentModel(boolean value) {
/* 223 */     if (value) {
/* 224 */       this.flag = (short)(this.flag | 0x4);
/*     */     } else {
/* 226 */       this.flag = (short)(this.flag & 0xFFFFFFFB);
/*     */     } 
/*     */   }
/*     */   public boolean isNilIncluded() {
/* 230 */     return this.isNilIncluded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean lookForLifecycleMethods() {
/* 241 */     return ((this.flag & 0x80) != 0);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getElementNamespaceURI(BeanT paramBeanT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getElementLocalName(BeanT paramBeanT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<QName> getTypeNames() {
/* 290 */     if (this.typeName == null) return Collections.emptyList(); 
/* 291 */     if (this.typeName instanceof QName) return Collections.singletonList((QName)this.typeName); 
/* 292 */     return Arrays.asList((QName[])this.typeName);
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
/*     */   public QName getTypeName(@NotNull BeanT instance) {
/* 304 */     if (this.typeName == null) return null; 
/* 305 */     if (this.typeName instanceof QName) return (QName)this.typeName; 
/* 306 */     return ((QName[])this.typeName)[0];
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
/*     */   public abstract BeanT createInstance(UnmarshallingContext paramUnmarshallingContext) throws IllegalAccessException, InvocationTargetException, InstantiationException, SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean reset(BeanT paramBeanT, UnmarshallingContext paramUnmarshallingContext) throws SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getId(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void serializeBody(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException, IOException, XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void serializeAttributes(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException, IOException, XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void serializeRoot(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException, IOException, XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void serializeURIs(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Loader getLoader(JAXBContextImpl paramJAXBContextImpl, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Transducer<BeanT> getTransducer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void link(JAXBContextImpl grammar) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void wrapUp() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 430 */   private static final Class[] unmarshalEventParams = new Class[] { Unmarshaller.class, Object.class };
/* 431 */   private static Class[] marshalEventParams = new Class[] { Marshaller.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setLifecycleFlags() {
/*     */     try {
/* 439 */       Class<BeanT> jt = this.jaxbType;
/*     */       
/* 441 */       if (this.lcm == null) {
/* 442 */         this.lcm = new LifecycleMethods();
/*     */       }
/*     */       
/* 445 */       while (jt != null) {
/* 446 */         for (Method m : jt.getDeclaredMethods()) {
/* 447 */           String name = m.getName();
/*     */           
/* 449 */           if (this.lcm.beforeUnmarshal == null && 
/* 450 */             name.equals("beforeUnmarshal") && 
/* 451 */             match(m, unmarshalEventParams)) {
/* 452 */             cacheLifecycleMethod(m, (short)8);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 457 */           if (this.lcm.afterUnmarshal == null && 
/* 458 */             name.equals("afterUnmarshal") && 
/* 459 */             match(m, unmarshalEventParams)) {
/* 460 */             cacheLifecycleMethod(m, (short)16);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 465 */           if (this.lcm.beforeMarshal == null && 
/* 466 */             name.equals("beforeMarshal") && 
/* 467 */             match(m, marshalEventParams)) {
/* 468 */             cacheLifecycleMethod(m, (short)32);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 473 */           if (this.lcm.afterMarshal == null && 
/* 474 */             name.equals("afterMarshal") && 
/* 475 */             match(m, marshalEventParams)) {
/* 476 */             cacheLifecycleMethod(m, (short)64);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 481 */         jt = (Class)jt.getSuperclass();
/*     */       } 
/* 483 */     } catch (SecurityException e) {
/*     */       
/* 485 */       logger.log(Level.WARNING, Messages.UNABLE_TO_DISCOVER_EVENTHANDLER.format(new Object[] { this.jaxbType.getName(), e }));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean match(Method m, Class[] params) {
/* 491 */     return Arrays.equals((Object[])m.getParameterTypes(), (Object[])params);
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
/*     */   private void cacheLifecycleMethod(Method m, short lifecycleFlag) {
/* 504 */     if (this.lcm == null) {
/* 505 */       this.lcm = new LifecycleMethods();
/*     */     }
/*     */ 
/*     */     
/* 509 */     m.setAccessible(true);
/*     */     
/* 511 */     this.flag = (short)(this.flag | lifecycleFlag);
/*     */     
/* 513 */     switch (lifecycleFlag) {
/*     */       case 8:
/* 515 */         this.lcm.beforeUnmarshal = m;
/*     */         break;
/*     */       case 16:
/* 518 */         this.lcm.afterUnmarshal = m;
/*     */         break;
/*     */       case 32:
/* 521 */         this.lcm.beforeMarshal = m;
/*     */         break;
/*     */       case 64:
/* 524 */         this.lcm.afterMarshal = m;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final LifecycleMethods getLifecycleMethods() {
/* 535 */     return this.lcm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void invokeBeforeUnmarshalMethod(UnmarshallerImpl unm, Object child, Object parent) throws SAXException {
/* 542 */     Method m = (getLifecycleMethods()).beforeUnmarshal;
/* 543 */     invokeUnmarshallCallback(m, child, unm, parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void invokeAfterUnmarshalMethod(UnmarshallerImpl unm, Object child, Object parent) throws SAXException {
/* 550 */     Method m = (getLifecycleMethods()).afterUnmarshal;
/* 551 */     invokeUnmarshallCallback(m, child, unm, parent);
/*     */   }
/*     */   
/*     */   private void invokeUnmarshallCallback(Method m, Object child, UnmarshallerImpl unm, Object parent) throws SAXException {
/*     */     try {
/* 556 */       m.invoke(child, new Object[] { unm, parent });
/* 557 */     } catch (IllegalAccessException e) {
/* 558 */       UnmarshallingContext.getInstance().handleError(e, false);
/* 559 */     } catch (InvocationTargetException e) {
/* 560 */       UnmarshallingContext.getInstance().handleError(e, false);
/*     */     } 
/*     */   }
/*     */   
/* 564 */   private static final Logger logger = Util.getClassLogger();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\JaxBeanInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */