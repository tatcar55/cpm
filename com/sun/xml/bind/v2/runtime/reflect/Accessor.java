/*     */ package com.sun.xml.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.Util;
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.core.Adapter;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.opt.OptimizedAccessorFactory;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.awt.Image;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.datatype.Duration;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ import javax.xml.transform.Source;
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
/*     */ public abstract class Accessor<BeanT, ValueT>
/*     */   implements Receiver
/*     */ {
/*     */   public final Class<ValueT> valueType;
/*     */   
/*     */   public Class<ValueT> getValueType() {
/*  94 */     return this.valueType;
/*     */   }
/*     */   
/*     */   protected Accessor(Class<ValueT> valueType) {
/*  98 */     this.valueType = valueType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Accessor<BeanT, ValueT> optimize(@Nullable JAXBContextImpl context) {
/* 109 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getUnadapted(BeanT bean) throws AccessorException {
/* 143 */     return get(bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdapted() {
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnadapted(BeanT bean, Object value) throws AccessorException {
/* 162 */     set(bean, (ValueT)value);
/*     */   }
/*     */   
/*     */   public void receive(UnmarshallingContext.State state, Object o) throws SAXException {
/*     */     try {
/* 167 */       set((BeanT)state.target, (ValueT)o);
/* 168 */     } catch (AccessorException e) {
/* 169 */       Loader.handleGenericException((Exception)e, true);
/* 170 */     } catch (IllegalAccessError iae) {
/*     */       
/* 172 */       Loader.handleGenericError(iae);
/*     */     } 
/*     */   }
/*     */   
/* 176 */   private static List<Class> nonAbstractableClasses = Arrays.asList(new Class[] { Object.class, Calendar.class, Duration.class, XMLGregorianCalendar.class, Image.class, DataHandler.class, Source.class, Date.class, File.class, URI.class, URL.class, Class.class, String.class, Source.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValueTypeAbstractable() {
/* 194 */     return !nonAbstractableClasses.contains(getValueType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbstractable(Class clazz) {
/* 203 */     return !nonAbstractableClasses.contains(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> Accessor<BeanT, T> adapt(Class<T> targetType, Class<? extends XmlAdapter<T, ValueT>> adapter) {
/* 211 */     return new AdaptedAccessor<BeanT, ValueT, T>(targetType, this, adapter);
/*     */   }
/*     */   
/*     */   public final <T> Accessor<BeanT, T> adapt(Adapter<Type, Class<?>> adapter) {
/* 215 */     return new AdaptedAccessor<BeanT, ValueT, T>(Navigator.REFLECTION.erasure((Type)adapter.defaultType), this, (Class<? extends XmlAdapter<T, ValueT>>)adapter.adapterType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean accessWarned = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FieldReflection<BeanT, ValueT>
/*     */     extends Accessor<BeanT, ValueT>
/*     */   {
/*     */     public final Field f;
/*     */ 
/*     */ 
/*     */     
/* 234 */     private static final Logger logger = Util.getClassLogger();
/*     */     
/*     */     public FieldReflection(Field f) {
/* 237 */       this(f, false);
/*     */     }
/*     */     
/*     */     public FieldReflection(Field f, boolean supressAccessorWarnings) {
/* 241 */       super((Class)f.getType());
/* 242 */       this.f = f;
/*     */       
/* 244 */       int mod = f.getModifiers();
/* 245 */       if (!Modifier.isPublic(mod) || Modifier.isFinal(mod) || !Modifier.isPublic(f.getDeclaringClass().getModifiers())) {
/*     */         
/*     */         try {
/*     */ 
/*     */           
/* 250 */           f.setAccessible(true);
/* 251 */         } catch (SecurityException e) {
/* 252 */           if (!Accessor.accessWarned && !supressAccessorWarnings)
/*     */           {
/* 254 */             logger.log(Level.WARNING, Messages.UNABLE_TO_ACCESS_NON_PUBLIC_FIELD.format(new Object[] { f.getDeclaringClass().getName(), f.getName() }, ), e);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 259 */           Accessor.accessWarned = true;
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     public ValueT get(BeanT bean) {
/*     */       try {
/* 266 */         return (ValueT)this.f.get(bean);
/* 267 */       } catch (IllegalAccessException e) {
/* 268 */         throw new IllegalAccessError(e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/*     */     public void set(BeanT bean, ValueT value) {
/*     */       try {
/* 274 */         if (value == null)
/* 275 */           value = (ValueT)Accessor.uninitializedValues.get(this.valueType); 
/* 276 */         this.f.set(bean, value);
/* 277 */       } catch (IllegalAccessException e) {
/* 278 */         throw new IllegalAccessError(e.getMessage());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Accessor<BeanT, ValueT> optimize(JAXBContextImpl context) {
/* 284 */       if (context != null && context.fastBoot)
/*     */       {
/* 286 */         return this; } 
/* 287 */       Accessor<BeanT, ValueT> acc = OptimizedAccessorFactory.get(this.f);
/* 288 */       if (acc != null) {
/* 289 */         return acc;
/*     */       }
/* 291 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class ReadOnlyFieldReflection<BeanT, ValueT>
/*     */     extends FieldReflection<BeanT, ValueT>
/*     */   {
/*     */     public ReadOnlyFieldReflection(Field f, boolean supressAccessorWarnings) {
/* 300 */       super(f, supressAccessorWarnings);
/*     */     }
/*     */     public ReadOnlyFieldReflection(Field f) {
/* 303 */       super(f);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void set(BeanT bean, ValueT value) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Accessor<BeanT, ValueT> optimize(JAXBContextImpl context) {
/* 313 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GetterSetterReflection<BeanT, ValueT>
/*     */     extends Accessor<BeanT, ValueT>
/*     */   {
/*     */     public final Method getter;
/*     */     
/*     */     public final Method setter;
/*     */     
/* 325 */     private static final Logger logger = Util.getClassLogger();
/*     */     
/*     */     public GetterSetterReflection(Method getter, Method setter) {
/* 328 */       super((getter != null) ? (Class)getter.getReturnType() : (Class)setter.getParameterTypes()[0]);
/*     */ 
/*     */ 
/*     */       
/* 332 */       this.getter = getter;
/* 333 */       this.setter = setter;
/*     */       
/* 335 */       if (getter != null)
/* 336 */         makeAccessible(getter); 
/* 337 */       if (setter != null)
/* 338 */         makeAccessible(setter); 
/*     */     }
/*     */     
/*     */     private void makeAccessible(Method m) {
/* 342 */       if (!Modifier.isPublic(m.getModifiers()) || !Modifier.isPublic(m.getDeclaringClass().getModifiers())) {
/*     */         try {
/* 344 */           m.setAccessible(true);
/* 345 */         } catch (SecurityException e) {
/* 346 */           if (!Accessor.accessWarned)
/*     */           {
/* 348 */             logger.log(Level.WARNING, Messages.UNABLE_TO_ACCESS_NON_PUBLIC_FIELD.format(new Object[] { m.getDeclaringClass().getName(), m.getName() }, ), e);
/*     */           }
/*     */ 
/*     */           
/* 352 */           Accessor.accessWarned = true;
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     public ValueT get(BeanT bean) throws AccessorException {
/*     */       try {
/* 359 */         return (ValueT)this.getter.invoke(bean, new Object[0]);
/* 360 */       } catch (IllegalAccessException e) {
/* 361 */         throw new IllegalAccessError(e.getMessage());
/* 362 */       } catch (InvocationTargetException e) {
/* 363 */         throw handleInvocationTargetException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void set(BeanT bean, ValueT value) throws AccessorException {
/*     */       try {
/* 369 */         if (value == null)
/* 370 */           value = (ValueT)Accessor.uninitializedValues.get(this.valueType); 
/* 371 */         this.setter.invoke(bean, new Object[] { value });
/* 372 */       } catch (IllegalAccessException e) {
/* 373 */         throw new IllegalAccessError(e.getMessage());
/* 374 */       } catch (InvocationTargetException e) {
/* 375 */         throw handleInvocationTargetException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private AccessorException handleInvocationTargetException(InvocationTargetException e) {
/* 381 */       Throwable t = e.getTargetException();
/* 382 */       if (t instanceof RuntimeException)
/* 383 */         throw (RuntimeException)t; 
/* 384 */       if (t instanceof Error) {
/* 385 */         throw (Error)t;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 392 */       return new AccessorException(t);
/*     */     }
/*     */ 
/*     */     
/*     */     public Accessor<BeanT, ValueT> optimize(JAXBContextImpl context) {
/* 397 */       if (this.getter == null || this.setter == null)
/*     */       {
/* 399 */         return this; } 
/* 400 */       if (context != null && context.fastBoot)
/*     */       {
/* 402 */         return this;
/*     */       }
/* 404 */       Accessor<BeanT, ValueT> acc = OptimizedAccessorFactory.get(this.getter, this.setter);
/* 405 */       if (acc != null) {
/* 406 */         return acc;
/*     */       }
/* 408 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class GetterOnlyReflection<BeanT, ValueT>
/*     */     extends GetterSetterReflection<BeanT, ValueT>
/*     */   {
/*     */     public GetterOnlyReflection(Method getter) {
/* 420 */       super(getter, null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(BeanT bean, ValueT value) throws AccessorException {
/* 425 */       throw new AccessorException(Messages.NO_SETTER.format(new Object[] { this.getter.toString() }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SetterOnlyReflection<BeanT, ValueT>
/*     */     extends GetterSetterReflection<BeanT, ValueT>
/*     */   {
/*     */     public SetterOnlyReflection(Method setter) {
/* 437 */       super(null, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     public ValueT get(BeanT bean) throws AccessorException {
/* 442 */       throw new AccessorException(Messages.NO_GETTER.format(new Object[] { this.setter.toString() }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A, B> Accessor<A, B> getErrorInstance() {
/* 451 */     return ERROR;
/*     */   }
/*     */   
/* 454 */   private static final Accessor ERROR = new Accessor<Object, Object>(Object.class) {
/*     */       public Object get(Object o) {
/* 456 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void set(Object o, Object o1) {}
/*     */     };
/*     */ 
/*     */ 
/*     */   
/* 466 */   public static final Accessor<JAXBElement, Object> JAXB_ELEMENT_VALUE = new Accessor<JAXBElement, Object>(Object.class) {
/*     */       public Object get(JAXBElement jaxbElement) {
/* 468 */         return jaxbElement.getValue();
/*     */       }
/*     */       
/*     */       public void set(JAXBElement<Object> jaxbElement, Object o) {
/* 472 */         jaxbElement.setValue(o);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 479 */   private static final Map<Class, Object> uninitializedValues = (Map)new HashMap<Class<?>, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 492 */     uninitializedValues.put(byte.class, Byte.valueOf((byte)0));
/* 493 */     uninitializedValues.put(boolean.class, Boolean.valueOf(false));
/* 494 */     uninitializedValues.put(char.class, Character.valueOf(false));
/* 495 */     uninitializedValues.put(float.class, Float.valueOf(0.0F));
/* 496 */     uninitializedValues.put(double.class, Double.valueOf(0.0D));
/* 497 */     uninitializedValues.put(int.class, Integer.valueOf(0));
/* 498 */     uninitializedValues.put(long.class, Long.valueOf(0L));
/* 499 */     uninitializedValues.put(short.class, Short.valueOf((short)0));
/*     */   }
/*     */   
/*     */   public abstract ValueT get(BeanT paramBeanT) throws AccessorException;
/*     */   
/*     */   public abstract void set(BeanT paramBeanT, ValueT paramValueT) throws AccessorException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\Accessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */