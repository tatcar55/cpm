/*     */ package com.sun.xml.ws.spi.db;
/*     */ 
/*     */ import com.sun.xml.ws.db.glassfish.JAXBRIContextFactory;
/*     */ import com.sun.xml.ws.util.ServiceConfigurationError;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Marshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BindingContextFactory
/*     */ {
/*     */   public static final String DefaultDatabindingMode = "glassfish.jaxb";
/*  64 */   public static final String JAXB_CONTEXT_FACTORY_PROPERTY = BindingContextFactory.class.getName();
/*  65 */   public static final Logger LOGGER = Logger.getLogger(BindingContextFactory.class.getName());
/*     */ 
/*     */   
/*     */   public static Iterator<BindingContextFactory> serviceIterator() {
/*  69 */     ServiceFinder<BindingContextFactory> sf = ServiceFinder.find(BindingContextFactory.class);
/*     */     
/*  71 */     final Iterator<BindingContextFactory> ibcf = sf.iterator();
/*     */     
/*  73 */     return new Iterator<BindingContextFactory>() {
/*     */         private BindingContextFactory bcf;
/*     */         
/*     */         public boolean hasNext() {
/*     */           while (true) {
/*     */             try {
/*  79 */               if (ibcf.hasNext()) {
/*  80 */                 this.bcf = ibcf.next();
/*  81 */                 return true;
/*     */               } 
/*  83 */               return false;
/*  84 */             } catch (ServiceConfigurationError e) {
/*  85 */               BindingContextFactory.LOGGER.warning("skipping factory: ServiceConfigurationError: " + e.getMessage());
/*     */             }
/*  87 */             catch (NoClassDefFoundError ncdfe) {
/*  88 */               BindingContextFactory.LOGGER.fine("skipping factory: NoClassDefFoundError: " + ncdfe.getMessage());
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public BindingContextFactory next() {
/*  95 */           if (BindingContextFactory.LOGGER.isLoggable(Level.FINER)) {
/*  96 */             BindingContextFactory.LOGGER.finer("SPI found provider: " + this.bcf.getClass().getName());
/*     */           }
/*  98 */           return this.bcf;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 102 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static List<BindingContextFactory> factories() {
/* 108 */     List<BindingContextFactory> factories = new ArrayList<BindingContextFactory>();
/* 109 */     Iterator<BindingContextFactory> ibcf = serviceIterator();
/* 110 */     while (ibcf.hasNext()) {
/* 111 */       factories.add(ibcf.next());
/*     */     }
/*     */     
/* 114 */     if (factories.isEmpty()) {
/* 115 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 116 */         LOGGER.log(Level.FINER, "No SPI providers for BindingContextFactory found, adding: " + JAXBRIContextFactory.class.getName());
/*     */       }
/* 118 */       factories.add(new JAXBRIContextFactory());
/*     */     } 
/* 120 */     return factories;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract BindingContext newContext(JAXBContext paramJAXBContext);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract BindingContext newContext(BindingInfo paramBindingInfo);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isFor(String paramString);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract BindingContext getContext(Marshaller paramMarshaller);
/*     */ 
/*     */   
/*     */   private static BindingContextFactory getFactory(String mode) {
/* 141 */     for (BindingContextFactory f : factories()) {
/* 142 */       if (f.isFor(mode))
/* 143 */         return f; 
/*     */     } 
/* 145 */     return null;
/*     */   }
/*     */   
/*     */   public static BindingContext create(JAXBContext context) throws DatabindingException {
/* 149 */     return getJAXBFactory(context).newContext(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BindingContext create(BindingInfo bi) {
/* 155 */     String mode = bi.getDatabindingMode();
/* 156 */     if (mode != null) {
/* 157 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 158 */         LOGGER.log(Level.FINE, "Using SEI-configured databindng mode: " + mode);
/*     */       }
/* 160 */     } else if ((mode = System.getProperty("BindingContextFactory")) != null) {
/*     */ 
/*     */       
/* 163 */       bi.setDatabindingMode(mode);
/* 164 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 165 */         LOGGER.log(Level.FINE, "Using databindng: " + mode + " based on 'BindingContextFactory' System property");
/*     */       }
/* 167 */     } else if ((mode = System.getProperty(JAXB_CONTEXT_FACTORY_PROPERTY)) != null) {
/* 168 */       bi.setDatabindingMode(mode);
/* 169 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 170 */         LOGGER.log(Level.FINE, "Using databindng: " + mode + " based on '" + JAXB_CONTEXT_FACTORY_PROPERTY + "' System property");
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 176 */       Iterator<BindingContextFactory> i$ = factories().iterator(); if (i$.hasNext()) { BindingContextFactory factory = i$.next();
/* 177 */         if (LOGGER.isLoggable(Level.FINE)) {
/* 178 */           LOGGER.log(Level.FINE, "Using SPI-determined databindng mode: " + factory.getClass().getName());
/*     */         }
/*     */ 
/*     */         
/* 182 */         return factory.newContext(bi); }
/*     */ 
/*     */ 
/*     */       
/* 186 */       LOGGER.log(Level.SEVERE, "No Binding Context Factories found.");
/* 187 */       throw new DatabindingException("No Binding Context Factories found.");
/*     */     } 
/* 189 */     BindingContextFactory f = getFactory(mode);
/* 190 */     if (f != null)
/* 191 */       return f.newContext(bi); 
/* 192 */     LOGGER.severe("Unknown Databinding mode: " + mode);
/* 193 */     throw new DatabindingException("Unknown Databinding mode: " + mode);
/*     */   }
/*     */   
/*     */   public static boolean isContextSupported(Object o) {
/* 197 */     if (o == null) return false; 
/* 198 */     String pkgName = o.getClass().getPackage().getName();
/* 199 */     for (BindingContextFactory f : factories()) { if (f.isFor(pkgName)) return true;  }
/* 200 */      return false;
/*     */   }
/*     */   
/*     */   static BindingContextFactory getJAXBFactory(Object o) {
/* 204 */     String pkgName = o.getClass().getPackage().getName();
/* 205 */     BindingContextFactory f = getFactory(pkgName);
/* 206 */     if (f != null) return f; 
/* 207 */     throw new DatabindingException("Unknown JAXBContext implementation: " + o.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BindingContext getBindingContext(Marshaller m) {
/* 215 */     return getJAXBFactory(m).getContext(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\BindingContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */