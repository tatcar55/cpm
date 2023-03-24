/*     */ package com.sun.xml.ws.developer;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.bind.api.TypeReference;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface JAXBContextFactory
/*     */ {
/* 110 */   public static final JAXBContextFactory DEFAULT = new JAXBContextFactory() {
/*     */       @NotNull
/*     */       public JAXBRIContext createJAXBContext(@NotNull SEIModel sei, @NotNull List<Class<?>> classesToBind, @NotNull List<TypeReference> typeReferences) throws JAXBException {
/* 113 */         return JAXBRIContext.newInstance((Class[])classesToBind.<Class<?>[]>toArray((Class<?>[][])new Class[classesToBind.size()]), typeReferences, null, sei.getTargetNamespace(), false, null);
/*     */       }
/*     */     };
/*     */   
/*     */   @NotNull
/*     */   JAXBRIContext createJAXBContext(@NotNull SEIModel paramSEIModel, @NotNull List<Class> paramList, @NotNull List<TypeReference> paramList1) throws JAXBException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\JAXBContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */