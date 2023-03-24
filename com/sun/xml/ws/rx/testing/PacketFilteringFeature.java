/*     */ package com.sun.xml.ws.rx.testing;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RuntimeContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedData
/*     */ public final class PacketFilteringFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "com.sun.xml.ws.rm.runtime.testing.PacketFilteringFeature";
/*  63 */   private static final Logger LOGGER = Logger.getLogger(PacketFilteringFeature.class);
/*     */   
/*     */   private final List<Class<? extends PacketFilter>> filterClasses;
/*     */ 
/*     */   
/*     */   public PacketFilteringFeature() {
/*  69 */     this.filterClasses = Collections.emptyList();
/*  70 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketFilteringFeature(boolean enabled) {
/*  75 */     this.filterClasses = Collections.emptyList();
/*  76 */     this.enabled = enabled;
/*     */   }
/*     */   
/*     */   public PacketFilteringFeature(Class<? extends PacketFilter>... filterClasses) {
/*  80 */     this(true, filterClasses);
/*     */   }
/*     */   
/*     */   @FeatureConstructor({"enabled", "filters"})
/*     */   public PacketFilteringFeature(boolean enabled, Class<? extends PacketFilter>... filterClasses) {
/*  85 */     this.enabled = enabled;
/*  86 */     if (filterClasses != null && filterClasses.length > 0) {
/*  87 */       this.filterClasses = Collections.unmodifiableList(Arrays.asList(filterClasses));
/*     */     } else {
/*  89 */       this.filterClasses = Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/*  96 */     return "com.sun.xml.ws.rm.runtime.testing.PacketFilteringFeature";
/*     */   }
/*     */   
/*     */   List<PacketFilter> createFilters(RuntimeContext context) {
/* 100 */     List<PacketFilter> filters = new ArrayList<PacketFilter>(this.filterClasses.size());
/*     */     
/* 102 */     for (Class<? extends PacketFilter> filterClass : this.filterClasses) {
/*     */       try {
/* 104 */         PacketFilter filter = filterClass.newInstance();
/* 105 */         filter.configure(context);
/* 106 */         filters.add(filter);
/* 107 */       } catch (InstantiationException ex) {
/* 108 */         LOGGER.warning("Error instantiating packet filter of class [" + filterClass.getName() + "]", ex);
/* 109 */       } catch (IllegalAccessException ex) {
/* 110 */         LOGGER.warning("Error instantiating packet filter of class [" + filterClass.getName() + "]", ex);
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     return filters;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   boolean hasFilters() {
/* 119 */     return !this.filterClasses.isEmpty();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\testing\PacketFilteringFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */