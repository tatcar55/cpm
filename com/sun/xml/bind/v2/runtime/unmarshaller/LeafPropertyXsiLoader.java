/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.bind.v2.runtime.ClassBeanInfoImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import java.util.Collection;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.xml.sax.Attributes;
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
/*     */ public final class LeafPropertyXsiLoader
/*     */   extends Loader
/*     */ {
/*     */   private final Loader defaultLoader;
/*     */   private final TransducedAccessor xacc;
/*     */   private final Accessor acc;
/*     */   
/*     */   public LeafPropertyXsiLoader(Loader defaultLoader, TransducedAccessor xacc, Accessor acc) {
/*  64 */     this.defaultLoader = defaultLoader;
/*  65 */     this.expectText = true;
/*  66 */     this.xacc = xacc;
/*  67 */     this.acc = acc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/*  73 */     state.loader = selectLoader(state, ea);
/*     */     
/*  75 */     state.loader.startElement(state, ea);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Loader selectLoader(UnmarshallingContext.State state, TagName ea) throws SAXException {
/*  80 */     UnmarshallingContext context = state.getContext();
/*  81 */     JaxBeanInfo beanInfo = null;
/*     */ 
/*     */     
/*  84 */     Attributes atts = ea.atts;
/*  85 */     int idx = atts.getIndex("http://www.w3.org/2001/XMLSchema-instance", "type");
/*     */     
/*  87 */     if (idx >= 0) {
/*  88 */       ClassBeanInfoImpl cbii; String value = atts.getValue(idx);
/*     */       
/*  90 */       QName type = DatatypeConverterImpl._parseQName(value, context);
/*     */       
/*  92 */       if (type == null) {
/*  93 */         return this.defaultLoader;
/*     */       }
/*  95 */       beanInfo = context.getJAXBContext().getGlobalType(type);
/*  96 */       if (beanInfo == null) {
/*  97 */         return this.defaultLoader;
/*     */       }
/*     */       try {
/* 100 */         cbii = (ClassBeanInfoImpl)beanInfo;
/* 101 */       } catch (ClassCastException cce) {
/* 102 */         return this.defaultLoader;
/*     */       } 
/*     */       
/* 105 */       if (null == cbii.getTransducer()) {
/* 106 */         return this.defaultLoader;
/*     */       }
/*     */       
/* 109 */       return new LeafPropertyLoader((TransducedAccessor)new TransducedAccessor.CompositeTransducedAccessorImpl(state.getContext().getJAXBContext(), cbii.getTransducer(), this.acc));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     return this.defaultLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedChildElements() {
/* 121 */     return this.defaultLoader.getExpectedChildElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedAttributes() {
/* 126 */     return this.defaultLoader.getExpectedAttributes();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\LeafPropertyXsiLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */