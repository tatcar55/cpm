/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XsiTypeLoader
/*     */   extends Loader
/*     */ {
/*     */   private final JaxBeanInfo defaultBeanInfo;
/*     */   
/*     */   public XsiTypeLoader(JaxBeanInfo defaultBeanInfo) {
/*  69 */     super(true);
/*  70 */     this.defaultBeanInfo = defaultBeanInfo;
/*     */   }
/*     */   
/*     */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/*  74 */     JaxBeanInfo beanInfo = parseXsiType(state, ea, this.defaultBeanInfo);
/*  75 */     if (beanInfo == null) {
/*  76 */       beanInfo = this.defaultBeanInfo;
/*     */     }
/*  78 */     Loader loader = beanInfo.getLoader(null, false);
/*  79 */     state.loader = loader;
/*  80 */     loader.startElement(state, ea);
/*     */   }
/*     */   
/*     */   static JaxBeanInfo parseXsiType(UnmarshallingContext.State state, TagName ea, @Nullable JaxBeanInfo defaultBeanInfo) throws SAXException {
/*  84 */     UnmarshallingContext context = state.getContext();
/*  85 */     JaxBeanInfo beanInfo = null;
/*     */ 
/*     */     
/*  88 */     Attributes atts = ea.atts;
/*  89 */     int idx = atts.getIndex("http://www.w3.org/2001/XMLSchema-instance", "type");
/*     */     
/*  91 */     if (idx >= 0) {
/*     */ 
/*     */       
/*  94 */       String value = atts.getValue(idx);
/*     */       
/*  96 */       QName type = DatatypeConverterImpl._parseQName(value, context);
/*  97 */       if (type == null) {
/*  98 */         reportError(Messages.NOT_A_QNAME.format(new Object[] { value }, ), true);
/*     */       } else {
/* 100 */         if (defaultBeanInfo != null && defaultBeanInfo.getTypeNames().contains(type))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 107 */           return defaultBeanInfo;
/*     */         }
/* 109 */         beanInfo = context.getJAXBContext().getGlobalType(type);
/* 110 */         if (beanInfo == null) {
/* 111 */           String nearest = context.getJAXBContext().getNearestTypeName(type);
/* 112 */           if (nearest != null) {
/* 113 */             reportError(Messages.UNRECOGNIZED_TYPE_NAME_MAYBE.format(new Object[] { type, nearest }, ), true);
/*     */           } else {
/* 115 */             reportError(Messages.UNRECOGNIZED_TYPE_NAME.format(new Object[] { type }, ), true);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     return beanInfo;
/*     */   }
/*     */   
/* 130 */   static final QName XsiTypeQNAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "type");
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedAttributes() {
/* 134 */     Collection<QName> expAttrs = new HashSet<QName>();
/* 135 */     expAttrs.addAll(super.getExpectedAttributes());
/* 136 */     expAttrs.add(XsiTypeQNAME);
/* 137 */     return Collections.unmodifiableCollection(expAttrs);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\XsiTypeLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */