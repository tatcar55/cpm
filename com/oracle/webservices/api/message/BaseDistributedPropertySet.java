/*     */ package com.oracle.webservices.api.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseDistributedPropertySet
/*     */   extends BasePropertySet
/*     */   implements DistributedPropertySet
/*     */ {
/*  99 */   private final Map<Class<? extends PropertySet>, PropertySet> satellites = new IdentityHashMap<Class<? extends PropertySet>, PropertySet>();
/*     */   
/*     */   private final Map<String, Object> viewthis;
/*     */ 
/*     */   
/*     */   public BaseDistributedPropertySet() {
/* 105 */     this.viewthis = super.createView();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSatellite(@NotNull PropertySet satellite) {
/* 110 */     addSatellite((Class)satellite.getClass(), satellite);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSatellite(@NotNull Class<? extends PropertySet> keyClass, @NotNull PropertySet satellite) {
/* 115 */     this.satellites.put(keyClass, satellite);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeSatellite(PropertySet satellite) {
/* 120 */     this.satellites.remove(satellite.getClass());
/*     */   }
/*     */   
/*     */   public void copySatelliteInto(@NotNull DistributedPropertySet r) {
/* 124 */     for (Map.Entry<Class<? extends PropertySet>, PropertySet> entry : this.satellites.entrySet()) {
/* 125 */       r.addSatellite(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void copySatelliteInto(MessageContext r) {
/* 131 */     copySatelliteInto(r);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <T extends PropertySet> T getSatellite(Class<T> satelliteClass) {
/* 136 */     PropertySet propertySet = this.satellites.get(satelliteClass);
/* 137 */     if (propertySet != null) {
/* 138 */       return (T)propertySet;
/*     */     }
/*     */     
/* 141 */     for (PropertySet child : this.satellites.values()) {
/* 142 */       if (satelliteClass.isInstance(child)) {
/* 143 */         return satelliteClass.cast(child);
/*     */       }
/*     */       
/* 146 */       if (DistributedPropertySet.class.isInstance(child)) {
/* 147 */         propertySet = ((DistributedPropertySet)DistributedPropertySet.class.cast(child)).getSatellite(satelliteClass);
/* 148 */         if (propertySet != null) {
/* 149 */           return (T)propertySet;
/*     */         }
/*     */       } 
/*     */     } 
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Class<? extends PropertySet>, PropertySet> getSatellites() {
/* 158 */     return this.satellites;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/* 164 */     for (PropertySet child : this.satellites.values()) {
/* 165 */       if (child.supports(key)) {
/* 166 */         return child.get(key);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 171 */     return super.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(String key, Object value) {
/* 177 */     for (PropertySet child : this.satellites.values()) {
/* 178 */       if (child.supports(key)) {
/* 179 */         return child.put(key, value);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 184 */     return super.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 189 */     if (this.viewthis.containsKey(key))
/* 190 */       return true; 
/* 191 */     for (PropertySet child : this.satellites.values()) {
/* 192 */       if (child.containsKey(key)) {
/* 193 */         return true;
/*     */       }
/*     */     } 
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supports(Object key) {
/* 202 */     for (PropertySet child : this.satellites.values()) {
/* 203 */       if (child.supports(key)) {
/* 204 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 208 */     return super.supports(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/* 214 */     for (PropertySet child : this.satellites.values()) {
/* 215 */       if (child.supports(key)) {
/* 216 */         return child.remove(key);
/*     */       }
/*     */     } 
/*     */     
/* 220 */     return super.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createEntrySet(Set<Map.Entry<String, Object>> core) {
/* 225 */     super.createEntrySet(core);
/* 226 */     for (PropertySet child : this.satellites.values()) {
/* 227 */       ((BasePropertySet)child).createEntrySet(core);
/*     */     }
/*     */   }
/*     */   
/*     */   protected Map<String, Object> asMapLocal() {
/* 232 */     return this.viewthis;
/*     */   }
/*     */   
/*     */   protected boolean supportsLocal(Object key) {
/* 236 */     return super.supports(key);
/*     */   }
/*     */   
/*     */   class DistributedMapView
/*     */     extends AbstractMap<String, Object> {
/*     */     public Object get(Object key) {
/* 242 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 243 */         if (child.supports(key)) {
/* 244 */           return child.get(key);
/*     */         }
/*     */       } 
/*     */       
/* 248 */       return BaseDistributedPropertySet.this.viewthis.get(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 253 */       int size = BaseDistributedPropertySet.this.viewthis.size();
/* 254 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 255 */         size += child.asMap().size();
/*     */       }
/* 257 */       return size;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 262 */       if (BaseDistributedPropertySet.this.viewthis.containsKey(key))
/* 263 */         return true; 
/* 264 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 265 */         if (child.containsKey(key))
/* 266 */           return true; 
/*     */       } 
/* 268 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<String, Object>> entrySet() {
/* 273 */       Set<Map.Entry<String, Object>> entries = new HashSet<Map.Entry<String, Object>>();
/* 274 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 275 */         for (Map.Entry<String, Object> entry : child.asMap().entrySet())
/*     */         {
/*     */           
/* 278 */           entries.add(new AbstractMap.SimpleImmutableEntry<String, Object>(entry.getKey(), entry.getValue()));
/*     */         }
/*     */       } 
/* 281 */       for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)BaseDistributedPropertySet.this.viewthis.entrySet())
/*     */       {
/*     */         
/* 284 */         entries.add(new AbstractMap.SimpleImmutableEntry<String, Object>(entry.getKey(), entry.getValue()));
/*     */       }
/*     */       
/* 287 */       return entries;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object put(String key, Object value) {
/* 292 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 293 */         if (child.supports(key)) {
/* 294 */           return child.put(key, value);
/*     */         }
/*     */       } 
/*     */       
/* 298 */       return BaseDistributedPropertySet.this.viewthis.put(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 303 */       BaseDistributedPropertySet.this.satellites.clear();
/* 304 */       BaseDistributedPropertySet.this.viewthis.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object remove(Object key) {
/* 309 */       for (PropertySet child : BaseDistributedPropertySet.this.satellites.values()) {
/* 310 */         if (child.supports(key)) {
/* 311 */           return child.remove(key);
/*     */         }
/*     */       } 
/*     */       
/* 315 */       return BaseDistributedPropertySet.this.viewthis.remove(key);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<String, Object> createView() {
/* 321 */     return new DistributedMapView();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\message\BaseDistributedPropertySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */