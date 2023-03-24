/*     */ package com.sun.xml.ws.developer;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class UsesJAXBContextFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://jax-ws.dev.java.net/features/uses-jaxb-context";
/*     */   private final JAXBContextFactory factory;
/*     */   
/*     */   @FeatureConstructor({"value"})
/*     */   public UsesJAXBContextFeature(@NotNull Class<? extends JAXBContextFactory> factoryClass) {
/*     */     try {
/*  87 */       this.factory = factoryClass.getConstructor(new Class[0]).newInstance(new Object[0]);
/*  88 */     } catch (InstantiationException e) {
/*  89 */       Error x = new InstantiationError(e.getMessage());
/*  90 */       x.initCause(e);
/*  91 */       throw x;
/*  92 */     } catch (IllegalAccessException e) {
/*  93 */       Error x = new IllegalAccessError(e.getMessage());
/*  94 */       x.initCause(e);
/*  95 */       throw x;
/*  96 */     } catch (InvocationTargetException e) {
/*  97 */       Error x = new InstantiationError(e.getMessage());
/*  98 */       x.initCause(e);
/*  99 */       throw x;
/* 100 */     } catch (NoSuchMethodException e) {
/* 101 */       Error x = new NoSuchMethodError(e.getMessage());
/* 102 */       x.initCause(e);
/* 103 */       throw x;
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
/*     */   public UsesJAXBContextFeature(@Nullable JAXBContextFactory factory) {
/* 115 */     this.factory = factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UsesJAXBContextFeature(@Nullable final JAXBRIContext context) {
/* 123 */     this.factory = new JAXBContextFactory() {
/*     */         @NotNull
/*     */         public JAXBRIContext createJAXBContext(@NotNull SEIModel sei, @NotNull List<Class<?>> classesToBind, @NotNull List<TypeReference> typeReferences) throws JAXBException {
/* 126 */           return context;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Nullable
/*     */   public JAXBContextFactory getFactory() {
/* 139 */     return this.factory;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/* 144 */     return "http://jax-ws.dev.java.net/features/uses-jaxb-context";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\UsesJAXBContextFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */