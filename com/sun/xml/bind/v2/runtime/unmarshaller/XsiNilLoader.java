/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import java.util.Collection;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class XsiNilLoader
/*     */   extends ProxyLoader
/*     */ {
/*     */   private final Loader defaultLoader;
/*     */   
/*     */   public XsiNilLoader(Loader defaultLoader) {
/*  66 */     this.defaultLoader = defaultLoader;
/*  67 */     assert defaultLoader != null;
/*     */   }
/*     */   
/*     */   protected Loader selectLoader(UnmarshallingContext.State state, TagName ea) throws SAXException {
/*  71 */     int idx = ea.atts.getIndex("http://www.w3.org/2001/XMLSchema-instance", "nil");
/*     */     
/*  73 */     if (idx != -1) {
/*  74 */       Boolean b = DatatypeConverterImpl._parseBoolean(ea.atts.getValue(idx));
/*     */       
/*  76 */       if (b != null && b.booleanValue()) {
/*  77 */         onNil(state);
/*  78 */         boolean hasOtherAttributes = (ea.atts.getLength() - 1 > 0);
/*     */         
/*  80 */         if (!hasOtherAttributes || !(state.prev.target instanceof javax.xml.bind.JAXBElement)) {
/*  81 */           return Discarder.INSTANCE;
/*     */         }
/*     */       } 
/*     */     } 
/*  85 */     return this.defaultLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedChildElements() {
/*  90 */     return this.defaultLoader.getExpectedChildElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedAttributes() {
/*  95 */     return this.defaultLoader.getExpectedAttributes();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onNil(UnmarshallingContext.State state) throws SAXException {}
/*     */   
/*     */   public static final class Single
/*     */     extends XsiNilLoader
/*     */   {
/*     */     private final Accessor acc;
/*     */     
/*     */     public Single(Loader l, Accessor acc) {
/* 107 */       super(l);
/* 108 */       this.acc = acc;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onNil(UnmarshallingContext.State state) throws SAXException {
/*     */       try {
/* 114 */         this.acc.set(state.prev.target, null);
/* 115 */         state.prev.nil = true;
/* 116 */       } catch (AccessorException e) {
/* 117 */         handleGenericException((Exception)e, true);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Array
/*     */     extends XsiNilLoader {
/*     */     public Array(Loader core) {
/* 125 */       super(core);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void onNil(UnmarshallingContext.State state) {
/* 131 */       state.target = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\XsiNilLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */