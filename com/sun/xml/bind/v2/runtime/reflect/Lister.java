/*     */ package com.sun.xml.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.istack.SAXException2;
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.ClassFactory;
/*     */ import com.sun.xml.bind.v2.TODO;
/*     */ import com.sun.xml.bind.v2.model.core.Adapter;
/*     */ import com.sun.xml.bind.v2.model.core.ID;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.LocatorEx;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Patcher;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import java.util.TreeSet;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Lister<BeanT, PropT, ItemT, PackT>
/*     */ {
/*     */   public static <BeanT, PropT, ItemT, PackT> Lister<BeanT, PropT, ItemT, PackT> create(Type fieldType, ID idness, Adapter<Type, Class<?>> adapter) {
/*     */     // Byte code:
/*     */     //   0: getstatic com/sun/xml/bind/v2/model/nav/Navigator.REFLECTION : Lcom/sun/xml/bind/v2/model/nav/ReflectionNavigator;
/*     */     //   3: aload_0
/*     */     //   4: invokevirtual erasure : (Ljava/lang/reflect/Type;)Ljava/lang/Class;
/*     */     //   7: astore_3
/*     */     //   8: aload_3
/*     */     //   9: invokevirtual isArray : ()Z
/*     */     //   12: ifeq -> 31
/*     */     //   15: aload_3
/*     */     //   16: invokevirtual getComponentType : ()Ljava/lang/Class;
/*     */     //   19: astore #4
/*     */     //   21: aload #4
/*     */     //   23: invokestatic getArrayLister : (Ljava/lang/Class;)Lcom/sun/xml/bind/v2/runtime/reflect/Lister;
/*     */     //   26: astore #5
/*     */     //   28: goto -> 107
/*     */     //   31: ldc_w java/util/Collection
/*     */     //   34: aload_3
/*     */     //   35: invokevirtual isAssignableFrom : (Ljava/lang/Class;)Z
/*     */     //   38: ifeq -> 105
/*     */     //   41: getstatic com/sun/xml/bind/v2/model/nav/Navigator.REFLECTION : Lcom/sun/xml/bind/v2/model/nav/ReflectionNavigator;
/*     */     //   44: aload_0
/*     */     //   45: ldc_w java/util/Collection
/*     */     //   48: invokevirtual getBaseClass : (Ljava/lang/reflect/Type;Ljava/lang/Class;)Ljava/lang/reflect/Type;
/*     */     //   51: astore #6
/*     */     //   53: aload #6
/*     */     //   55: instanceof java/lang/reflect/ParameterizedType
/*     */     //   58: ifeq -> 84
/*     */     //   61: getstatic com/sun/xml/bind/v2/model/nav/Navigator.REFLECTION : Lcom/sun/xml/bind/v2/model/nav/ReflectionNavigator;
/*     */     //   64: aload #6
/*     */     //   66: checkcast java/lang/reflect/ParameterizedType
/*     */     //   69: invokeinterface getActualTypeArguments : ()[Ljava/lang/reflect/Type;
/*     */     //   74: iconst_0
/*     */     //   75: aaload
/*     */     //   76: invokevirtual erasure : (Ljava/lang/reflect/Type;)Ljava/lang/Class;
/*     */     //   79: astore #4
/*     */     //   81: goto -> 89
/*     */     //   84: ldc_w java/lang/Object
/*     */     //   87: astore #4
/*     */     //   89: new com/sun/xml/bind/v2/runtime/reflect/Lister$CollectionLister
/*     */     //   92: dup
/*     */     //   93: aload_3
/*     */     //   94: invokestatic getImplClass : (Ljava/lang/Class;)Ljava/lang/Class;
/*     */     //   97: invokespecial <init> : (Ljava/lang/Class;)V
/*     */     //   100: astore #5
/*     */     //   102: goto -> 107
/*     */     //   105: aconst_null
/*     */     //   106: areturn
/*     */     //   107: aload_1
/*     */     //   108: getstatic com/sun/xml/bind/v2/model/core/ID.IDREF : Lcom/sun/xml/bind/v2/model/core/ID;
/*     */     //   111: if_acmpne -> 127
/*     */     //   114: new com/sun/xml/bind/v2/runtime/reflect/Lister$IDREFS
/*     */     //   117: dup
/*     */     //   118: aload #5
/*     */     //   120: aload #4
/*     */     //   122: invokespecial <init> : (Lcom/sun/xml/bind/v2/runtime/reflect/Lister;Ljava/lang/Class;)V
/*     */     //   125: astore #5
/*     */     //   127: aload_2
/*     */     //   128: ifnull -> 149
/*     */     //   131: new com/sun/xml/bind/v2/runtime/reflect/AdaptedLister
/*     */     //   134: dup
/*     */     //   135: aload #5
/*     */     //   137: aload_2
/*     */     //   138: getfield adapterType : Ljava/lang/Object;
/*     */     //   141: checkcast java/lang/Class
/*     */     //   144: invokespecial <init> : (Lcom/sun/xml/bind/v2/runtime/reflect/Lister;Ljava/lang/Class;)V
/*     */     //   147: astore #5
/*     */     //   149: aload #5
/*     */     //   151: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #134	-> 0
/*     */     //   #138	-> 8
/*     */     //   #139	-> 15
/*     */     //   #140	-> 21
/*     */     //   #142	-> 31
/*     */     //   #143	-> 41
/*     */     //   #144	-> 53
/*     */     //   #145	-> 61
/*     */     //   #147	-> 84
/*     */     //   #148	-> 89
/*     */     //   #149	-> 102
/*     */     //   #150	-> 105
/*     */     //   #152	-> 107
/*     */     //   #153	-> 114
/*     */     //   #155	-> 127
/*     */     //   #156	-> 131
/*     */     //   #158	-> 149
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   21	10	4	itemType	Ljava/lang/Class;
/*     */     //   28	3	5	l	Lcom/sun/xml/bind/v2/runtime/reflect/Lister;
/*     */     //   81	3	4	itemType	Ljava/lang/Class;
/*     */     //   53	49	6	bt	Ljava/lang/reflect/Type;
/*     */     //   89	16	4	itemType	Ljava/lang/Class;
/*     */     //   102	3	5	l	Lcom/sun/xml/bind/v2/runtime/reflect/Lister;
/*     */     //   0	152	0	fieldType	Ljava/lang/reflect/Type;
/*     */     //   0	152	1	idness	Lcom/sun/xml/bind/v2/model/core/ID;
/*     */     //   0	152	2	adapter	Lcom/sun/xml/bind/v2/model/core/Adapter;
/*     */     //   8	144	3	rawType	Ljava/lang/Class;
/*     */     //   107	45	4	itemType	Ljava/lang/Class;
/*     */     //   107	45	5	l	Lcom/sun/xml/bind/v2/runtime/reflect/Lister;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	152	2	adapter	Lcom/sun/xml/bind/v2/model/core/Adapter<Ljava/lang/reflect/Type;Ljava/lang/Class;>;
/*     */   }
/*     */   
/*     */   private static Class getImplClass(Class<?> fieldType) {
/* 162 */     return ClassFactory.inferImplClass(fieldType, COLLECTION_IMPL_CLASSES);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   private static final Map<Class, WeakReference<Lister>> arrayListerCache = Collections.synchronizedMap(new WeakHashMap<Class<?>, WeakReference<Lister>>());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Lister getArrayLister(Class<?> componentType) {
/* 175 */     Lister<Object, Object> l = null;
/* 176 */     if (componentType.isPrimitive()) {
/* 177 */       l = primitiveArrayListers.get(componentType);
/*     */     } else {
/* 179 */       WeakReference<Lister> wr = arrayListerCache.get(componentType);
/* 180 */       if (wr != null)
/* 181 */         l = wr.get(); 
/* 182 */       if (l == null) {
/* 183 */         l = (Lister<Object, Object>)new ArrayLister<Object, Object>(componentType);
/* 184 */         arrayListerCache.put(componentType, new WeakReference<Lister>(l));
/*     */       } 
/*     */     } 
/* 187 */     assert l != null;
/* 188 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ArrayLister<BeanT, ItemT>
/*     */     extends Lister<BeanT, ItemT[], ItemT, Pack<ItemT>>
/*     */   {
/*     */     private final Class<ItemT> itemType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArrayLister(Class<ItemT> itemType) {
/* 203 */       this.itemType = itemType;
/*     */     }
/*     */     
/*     */     public ListIterator<ItemT> iterator(final ItemT[] objects, XMLSerializer context) {
/* 207 */       return new ListIterator<ItemT>() {
/* 208 */           int idx = 0;
/*     */           public boolean hasNext() {
/* 210 */             return (this.idx < objects.length);
/*     */           }
/*     */           
/*     */           public ItemT next() {
/* 214 */             return (ItemT)objects[this.idx++];
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public Lister.Pack startPacking(BeanT current, Accessor<BeanT, ItemT[]> acc) {
/* 220 */       return new Lister.Pack<ItemT>(this.itemType);
/*     */     }
/*     */     
/*     */     public void addToPack(Lister.Pack<ItemT> objects, ItemT o) {
/* 224 */       objects.add(o);
/*     */     }
/*     */     
/*     */     public void endPacking(Lister.Pack<ItemT> pack, BeanT bean, Accessor<BeanT, ItemT[]> acc) throws AccessorException {
/* 228 */       acc.set(bean, pack.build());
/*     */     }
/*     */     
/*     */     public void reset(BeanT o, Accessor<BeanT, ItemT[]> acc) throws AccessorException {
/* 232 */       acc.set(o, (ItemT[])Array.newInstance(this.itemType, 0));
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Pack<ItemT>
/*     */     extends ArrayList<ItemT> {
/*     */     private final Class<ItemT> itemType;
/*     */     
/*     */     public Pack(Class<ItemT> itemType) {
/* 241 */       this.itemType = itemType;
/*     */     }
/*     */     
/*     */     public ItemT[] build() {
/* 245 */       return toArray((ItemT[])Array.newInstance(this.itemType, size()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   static final Map<Class, Lister> primitiveArrayListers = (Map)new HashMap<Class<?>, Lister>();
/*     */ 
/*     */   
/*     */   static {
/* 256 */     PrimitiveArrayListerBoolean.register();
/* 257 */     PrimitiveArrayListerByte.register();
/* 258 */     PrimitiveArrayListerCharacter.register();
/* 259 */     PrimitiveArrayListerDouble.register();
/* 260 */     PrimitiveArrayListerFloat.register();
/* 261 */     PrimitiveArrayListerInteger.register();
/* 262 */     PrimitiveArrayListerLong.register();
/* 263 */     PrimitiveArrayListerShort.register();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class CollectionLister<BeanT, T extends Collection>
/*     */     extends Lister<BeanT, T, Object, T>
/*     */   {
/*     */     private final Class<? extends T> implClass;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CollectionLister(Class<? extends T> implClass) {
/* 278 */       this.implClass = implClass;
/*     */     }
/*     */     
/*     */     public ListIterator iterator(T collection, XMLSerializer context) {
/* 282 */       final Iterator itr = collection.iterator();
/* 283 */       return new ListIterator() {
/*     */           public boolean hasNext() {
/* 285 */             return itr.hasNext();
/*     */           }
/*     */           public Object next() {
/* 288 */             return itr.next();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public T startPacking(BeanT bean, Accessor<BeanT, T> acc) throws AccessorException {
/* 294 */       Collection collection = (Collection)acc.get(bean);
/* 295 */       if (collection == null) {
/* 296 */         collection = (Collection)ClassFactory.create(this.implClass);
/* 297 */         if (!acc.isAdapted())
/* 298 */           acc.set(bean, (T)collection); 
/*     */       } 
/* 300 */       collection.clear();
/* 301 */       return (T)collection;
/*     */     }
/*     */     
/*     */     public void addToPack(T collection, Object o) {
/* 305 */       collection.add(o);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void endPacking(T collection, BeanT bean, Accessor<BeanT, T> acc) throws AccessorException {
/*     */       try {
/* 318 */         if (acc.isAdapted()) {
/* 319 */           acc.set(bean, collection);
/*     */         }
/* 321 */       } catch (AccessorException ae) {
/* 322 */         if (acc.isAdapted()) throw ae; 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void reset(BeanT bean, Accessor<BeanT, T> acc) throws AccessorException {
/* 327 */       Collection collection = (Collection)acc.get(bean);
/* 328 */       if (collection == null) {
/*     */         return;
/*     */       }
/* 331 */       collection.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class IDREFS<BeanT, PropT>
/*     */     extends Lister<BeanT, PropT, String, IDREFS<BeanT, PropT>.Pack>
/*     */   {
/*     */     private final Lister<BeanT, PropT, Object, Object> core;
/*     */     
/*     */     private final Class itemType;
/*     */ 
/*     */     
/*     */     public IDREFS(Lister<BeanT, PropT, Object, Object> core, Class itemType) {
/* 346 */       this.core = core;
/* 347 */       this.itemType = itemType;
/*     */     }
/*     */     
/*     */     public ListIterator<String> iterator(PropT prop, XMLSerializer context) {
/* 351 */       ListIterator i = this.core.iterator(prop, context);
/*     */       
/* 353 */       return new Lister.IDREFSIterator(i, context);
/*     */     }
/*     */     
/*     */     public Pack startPacking(BeanT bean, Accessor<BeanT, PropT> acc) {
/* 357 */       return new Pack(bean, acc);
/*     */     }
/*     */     
/*     */     public void addToPack(Pack pack, String item) {
/* 361 */       pack.add(item);
/*     */     }
/*     */ 
/*     */     
/*     */     public void endPacking(Pack pack, BeanT bean, Accessor<BeanT, PropT> acc) {}
/*     */     
/*     */     public void reset(BeanT bean, Accessor<BeanT, PropT> acc) throws AccessorException {
/* 368 */       this.core.reset(bean, acc);
/*     */     }
/*     */ 
/*     */     
/*     */     private class Pack
/*     */       implements Patcher
/*     */     {
/*     */       private final BeanT bean;
/* 376 */       private final List<String> idrefs = new ArrayList<String>();
/*     */       private final UnmarshallingContext context;
/*     */       private final Accessor<BeanT, PropT> acc;
/*     */       private final LocatorEx location;
/*     */       
/*     */       public Pack(BeanT bean, Accessor<BeanT, PropT> acc) {
/* 382 */         this.bean = bean;
/* 383 */         this.acc = acc;
/* 384 */         this.context = UnmarshallingContext.getInstance();
/* 385 */         this.location = (LocatorEx)new LocatorEx.Snapshot(this.context.getLocator());
/* 386 */         this.context.addPatcher(this);
/*     */       }
/*     */       
/*     */       public void add(String item) {
/* 390 */         this.idrefs.add(item);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void run() throws SAXException {
/*     */         try {
/* 398 */           Object pack = Lister.IDREFS.this.core.startPacking(this.bean, this.acc);
/*     */           
/* 400 */           for (String id : this.idrefs) {
/* 401 */             Object t; Callable callable = this.context.getObjectFromId(id, Lister.IDREFS.this.itemType);
/*     */ 
/*     */             
/*     */             try {
/* 405 */               t = (callable != null) ? callable.call() : null;
/* 406 */             } catch (SAXException e) {
/* 407 */               throw e;
/* 408 */             } catch (Exception e) {
/* 409 */               throw new SAXException2(e);
/*     */             } 
/*     */             
/* 412 */             if (t == null) {
/* 413 */               this.context.errorUnresolvedIDREF(this.bean, id, this.location); continue;
/*     */             } 
/* 415 */             TODO.prototype();
/* 416 */             Lister.IDREFS.this.core.addToPack(pack, t);
/*     */           } 
/*     */ 
/*     */           
/* 420 */           Lister.IDREFS.this.core.endPacking(pack, this.bean, this.acc);
/* 421 */         } catch (AccessorException e) {
/* 422 */           this.context.handleError((Exception)e);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class IDREFSIterator
/*     */     implements ListIterator<String>
/*     */   {
/*     */     private final ListIterator i;
/*     */     
/*     */     private final XMLSerializer context;
/*     */     
/*     */     private Object last;
/*     */ 
/*     */     
/*     */     private IDREFSIterator(ListIterator i, XMLSerializer context) {
/* 441 */       this.i = i;
/* 442 */       this.context = context;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 446 */       return this.i.hasNext();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object last() {
/* 453 */       return this.last;
/*     */     }
/*     */     
/*     */     public String next() throws SAXException, JAXBException {
/* 457 */       this.last = this.i.next();
/* 458 */       String id = this.context.grammar.getBeanInfo(this.last, true).getId(this.last, this.context);
/* 459 */       if (id == null) {
/* 460 */         this.context.errorMissingId(this.last);
/*     */       }
/* 462 */       return id;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A, B, C, D> Lister<A, B, C, D> getErrorInstance() {
/* 471 */     return ERROR;
/*     */   }
/*     */   
/* 474 */   public static final Lister ERROR = new Lister() {
/*     */       public ListIterator iterator(Object o, XMLSerializer context) {
/* 476 */         return Lister.EMPTY_ITERATOR;
/*     */       }
/*     */       
/*     */       public Object startPacking(Object o, Accessor accessor) {
/* 480 */         return null;
/*     */       }
/*     */ 
/*     */       
/*     */       public void addToPack(Object o, Object o1) {}
/*     */ 
/*     */       
/*     */       public void endPacking(Object o, Object o1, Accessor accessor) {}
/*     */ 
/*     */       
/*     */       public void reset(Object o, Accessor accessor) {}
/*     */     };
/*     */   
/* 493 */   private static final ListIterator EMPTY_ITERATOR = new ListIterator() {
/*     */       public boolean hasNext() {
/* 495 */         return false;
/*     */       }
/*     */       
/*     */       public Object next() {
/* 499 */         throw new IllegalStateException();
/*     */       }
/*     */     };
/*     */   
/* 503 */   private static final Class[] COLLECTION_IMPL_CLASSES = new Class[] { ArrayList.class, LinkedList.class, HashSet.class, TreeSet.class, Stack.class };
/*     */   
/*     */   public abstract ListIterator<ItemT> iterator(PropT paramPropT, XMLSerializer paramXMLSerializer);
/*     */   
/*     */   public abstract PackT startPacking(BeanT paramBeanT, Accessor<BeanT, PropT> paramAccessor) throws AccessorException;
/*     */   
/*     */   public abstract void addToPack(PackT paramPackT, ItemT paramItemT) throws AccessorException;
/*     */   
/*     */   public abstract void endPacking(PackT paramPackT, BeanT paramBeanT, Accessor<BeanT, PropT> paramAccessor) throws AccessorException;
/*     */   
/*     */   public abstract void reset(BeanT paramBeanT, Accessor<BeanT, PropT> paramAccessor) throws AccessorException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\Lister.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */