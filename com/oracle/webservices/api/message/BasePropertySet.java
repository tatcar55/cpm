/*     */ package com.oracle.webservices.api.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BasePropertySet
/*     */   implements PropertySet
/*     */ {
/*     */   private Map<String, Object> mapView;
/*     */   
/*     */   protected abstract PropertyMap getPropertyMap();
/*     */   
/*     */   protected static class PropertyMap
/*     */     extends HashMap<String, Accessor>
/*     */   {
/*  87 */     transient BasePropertySet.PropertyMapEntry[] cachedEntries = null;
/*     */     
/*     */     BasePropertySet.PropertyMapEntry[] getPropertyMapEntries() {
/*  90 */       if (this.cachedEntries == null) {
/*  91 */         this.cachedEntries = createPropertyMapEntries();
/*     */       }
/*  93 */       return this.cachedEntries;
/*     */     }
/*     */     
/*     */     private BasePropertySet.PropertyMapEntry[] createPropertyMapEntries() {
/*  97 */       BasePropertySet.PropertyMapEntry[] modelEntries = new BasePropertySet.PropertyMapEntry[size()];
/*  98 */       int i = 0;
/*  99 */       for (Map.Entry<String, BasePropertySet.Accessor> e : entrySet()) {
/* 100 */         modelEntries[i++] = new BasePropertySet.PropertyMapEntry(e.getKey(), e.getValue());
/*     */       }
/* 102 */       return modelEntries;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PropertyMapEntry
/*     */   {
/*     */     String key;
/*     */     BasePropertySet.Accessor value;
/*     */     
/*     */     public PropertyMapEntry(String k, BasePropertySet.Accessor v) {
/* 112 */       this.key = k; this.value = v;
/*     */     }
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
/*     */   protected static PropertyMap parse(final Class clazz) {
/* 144 */     return AccessController.<PropertyMap>doPrivileged(new PrivilegedAction<PropertyMap>()
/*     */         {
/*     */           public BasePropertySet.PropertyMap run() {
/* 147 */             BasePropertySet.PropertyMap props = new BasePropertySet.PropertyMap();
/* 148 */             for (Class c = clazz; c != null; c = c.getSuperclass()) {
/* 149 */               for (Field f : c.getDeclaredFields()) {
/* 150 */                 PropertySet.Property cp = f.<PropertySet.Property>getAnnotation(PropertySet.Property.class);
/* 151 */                 if (cp != null) {
/* 152 */                   for (String value : cp.value()) {
/* 153 */                     props.put(value, new BasePropertySet.FieldAccessor(f, value));
/*     */                   }
/*     */                 }
/*     */               } 
/* 157 */               for (Method m : c.getDeclaredMethods()) {
/* 158 */                 PropertySet.Property cp = m.<PropertySet.Property>getAnnotation(PropertySet.Property.class);
/* 159 */                 if (cp != null) {
/* 160 */                   Method method; String name = m.getName();
/* 161 */                   assert name.startsWith("get") || name.startsWith("is");
/*     */                   
/* 163 */                   String setName = name.startsWith("is") ? ("set" + name.substring(2)) : ('s' + name.substring(1));
/*     */ 
/*     */                   
/*     */                   try {
/* 167 */                     method = clazz.getMethod(setName, new Class[] { m.getReturnType() });
/* 168 */                   } catch (NoSuchMethodException e) {
/* 169 */                     method = null;
/*     */                   } 
/* 171 */                   for (String value : cp.value()) {
/* 172 */                     props.put(value, new BasePropertySet.MethodAccessor(m, method, value));
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 178 */             return props;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class FieldAccessor
/*     */     implements Accessor
/*     */   {
/*     */     private final Field f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected FieldAccessor(Field f, String name) {
/* 205 */       this.f = f;
/* 206 */       f.setAccessible(true);
/* 207 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 212 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasValue(PropertySet props) {
/* 217 */       return (get(props) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object get(PropertySet props) {
/*     */       try {
/* 223 */         return this.f.get(props);
/* 224 */       } catch (IllegalAccessException e) {
/* 225 */         throw new AssertionError();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(PropertySet props, Object value) {
/*     */       try {
/* 232 */         this.f.set(props, value);
/* 233 */       } catch (IllegalAccessException e) {
/* 234 */         throw new AssertionError();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class MethodAccessor
/*     */     implements Accessor
/*     */   {
/*     */     @NotNull
/*     */     private final Method getter;
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private final Method setter;
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     protected MethodAccessor(Method getter, Method setter, String value) {
/* 256 */       this.getter = getter;
/* 257 */       this.setter = setter;
/* 258 */       this.name = value;
/* 259 */       getter.setAccessible(true);
/* 260 */       if (setter != null) {
/* 261 */         setter.setAccessible(true);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 267 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasValue(PropertySet props) {
/* 272 */       return (get(props) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object get(PropertySet props) {
/*     */       try {
/* 278 */         return this.getter.invoke(props, new Object[0]);
/* 279 */       } catch (IllegalAccessException e) {
/* 280 */         throw new AssertionError();
/* 281 */       } catch (InvocationTargetException e) {
/* 282 */         handle(e);
/* 283 */         return Integer.valueOf(0);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(PropertySet props, Object value) {
/* 289 */       if (this.setter == null) {
/* 290 */         throw new ReadOnlyPropertyException(getName());
/*     */       }
/*     */       try {
/* 293 */         this.setter.invoke(props, new Object[] { value });
/* 294 */       } catch (IllegalAccessException e) {
/* 295 */         throw new AssertionError();
/* 296 */       } catch (InvocationTargetException e) {
/* 297 */         handle(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Exception handle(InvocationTargetException e) {
/* 307 */       Throwable t = e.getTargetException();
/* 308 */       if (t instanceof Error) {
/* 309 */         throw (Error)t;
/*     */       }
/* 311 */       if (t instanceof RuntimeException) {
/* 312 */         throw (RuntimeException)t;
/*     */       }
/* 314 */       throw new Error(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final class MapView
/*     */     extends HashMap<String, Object>
/*     */   {
/*     */     boolean extensible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MapView(boolean extensible) {
/* 333 */       super((BasePropertySet.this.getPropertyMap().getPropertyMapEntries()).length);
/* 334 */       this.extensible = extensible;
/* 335 */       initialize();
/*     */     }
/*     */ 
/*     */     
/*     */     public void initialize() {
/* 340 */       BasePropertySet.PropertyMapEntry[] entries = BasePropertySet.this.getPropertyMap().getPropertyMapEntries();
/* 341 */       for (BasePropertySet.PropertyMapEntry entry : entries) {
/* 342 */         super.put(entry.key, entry.value);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Object get(Object key) {
/* 348 */       Object o = super.get(key);
/* 349 */       if (o instanceof BasePropertySet.Accessor) {
/* 350 */         return ((BasePropertySet.Accessor)o).get(BasePropertySet.this);
/*     */       }
/* 352 */       return o;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<String, Object>> entrySet() {
/* 358 */       Set<Map.Entry<String, Object>> entries = new HashSet<Map.Entry<String, Object>>();
/* 359 */       for (String key : keySet()) {
/* 360 */         entries.add(new AbstractMap.SimpleImmutableEntry<String, Object>(key, get(key)));
/*     */       }
/* 362 */       return entries;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object put(String key, Object value) {
/* 368 */       Object o = super.get(key);
/* 369 */       if (o != null && o instanceof BasePropertySet.Accessor) {
/*     */         
/* 371 */         Object oldValue = ((BasePropertySet.Accessor)o).get(BasePropertySet.this);
/* 372 */         ((BasePropertySet.Accessor)o).set(BasePropertySet.this, value);
/* 373 */         return oldValue;
/*     */       } 
/*     */ 
/*     */       
/* 377 */       if (this.extensible) {
/* 378 */         return super.put(key, value);
/*     */       }
/* 380 */       throw new IllegalStateException("Unknown property [" + key + "] for PropertySet [" + BasePropertySet.this.getClass().getName() + "]");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 388 */       for (String key : keySet()) {
/* 389 */         remove(key);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object remove(Object key) {
/* 396 */       Object o = super.get(key);
/* 397 */       if (o instanceof BasePropertySet.Accessor) {
/* 398 */         ((BasePropertySet.Accessor)o).set(BasePropertySet.this, null);
/*     */       }
/* 400 */       return super.remove(key);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 406 */     Accessor sp = getPropertyMap().get(key);
/* 407 */     if (sp != null) {
/* 408 */       return (sp.get(this) != null);
/*     */     }
/* 410 */     return false;
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
/*     */   public Object get(Object key) {
/* 423 */     Accessor sp = getPropertyMap().get(key);
/* 424 */     if (sp != null) {
/* 425 */       return sp.get(this);
/*     */     }
/* 427 */     throw new IllegalArgumentException("Undefined property " + key);
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
/*     */   public Object put(String key, Object value) {
/* 445 */     Accessor sp = getPropertyMap().get(key);
/* 446 */     if (sp != null) {
/* 447 */       Object old = sp.get(this);
/* 448 */       sp.set(this, value);
/* 449 */       return old;
/*     */     } 
/* 451 */     throw new IllegalArgumentException("Undefined property " + key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supports(Object key) {
/* 460 */     return getPropertyMap().containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/* 465 */     Accessor sp = getPropertyMap().get(key);
/* 466 */     if (sp != null) {
/* 467 */       Object old = sp.get(this);
/* 468 */       sp.set(this, null);
/* 469 */       return old;
/*     */     } 
/* 471 */     throw new IllegalArgumentException("Undefined property " + key);
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
/*     */   @Deprecated
/*     */   public final Map<String, Object> createMapView() {
/* 495 */     final Set<Map.Entry<String, Object>> core = new HashSet<Map.Entry<String, Object>>();
/* 496 */     createEntrySet(core);
/*     */     
/* 498 */     return new AbstractMap<String, Object>()
/*     */       {
/*     */         public Set<Map.Entry<String, Object>> entrySet() {
/* 501 */           return core;
/*     */         }
/*     */       };
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
/*     */   public Map<String, Object> asMap() {
/* 520 */     if (this.mapView == null) {
/* 521 */       this.mapView = createView();
/*     */     }
/* 523 */     return this.mapView;
/*     */   }
/*     */   
/*     */   protected Map<String, Object> createView() {
/* 527 */     return new MapView(mapAllowsAdditionalProperties());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean mapAllowsAdditionalProperties() {
/* 537 */     return false;
/*     */   }
/*     */   
/*     */   protected void createEntrySet(Set<Map.Entry<String, Object>> core) {
/* 541 */     for (Map.Entry<String, Accessor> e : getPropertyMap().entrySet()) {
/* 542 */       core.add(new Map.Entry<String, Object>()
/*     */           {
/*     */             public String getKey() {
/* 545 */               return (String)e.getKey();
/*     */             }
/*     */ 
/*     */             
/*     */             public Object getValue() {
/* 550 */               return ((BasePropertySet.Accessor)e.getValue()).get(BasePropertySet.this);
/*     */             }
/*     */ 
/*     */             
/*     */             public Object setValue(Object value) {
/* 555 */               BasePropertySet.Accessor acc = (BasePropertySet.Accessor)e.getValue();
/* 556 */               Object old = acc.get(BasePropertySet.this);
/* 557 */               acc.set(BasePropertySet.this, value);
/* 558 */               return old;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static interface Accessor {
/*     */     String getName();
/*     */     
/*     */     boolean hasValue(PropertySet param1PropertySet);
/*     */     
/*     */     Object get(PropertySet param1PropertySet);
/*     */     
/*     */     void set(PropertySet param1PropertySet, Object param1Object);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\message\BasePropertySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */