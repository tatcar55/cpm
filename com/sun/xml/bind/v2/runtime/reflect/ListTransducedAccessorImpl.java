/*     */ package com.sun.xml.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
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
/*     */ public final class ListTransducedAccessorImpl<BeanT, ListT, ItemT, PackT>
/*     */   extends DefaultTransducedAccessor<BeanT>
/*     */ {
/*     */   private final Transducer<ItemT> xducer;
/*     */   private final Lister<BeanT, ListT, ItemT, PackT> lister;
/*     */   private final Accessor<BeanT, ListT> acc;
/*     */   
/*     */   public ListTransducedAccessorImpl(Transducer<ItemT> xducer, Accessor<BeanT, ListT> acc, Lister<BeanT, ListT, ItemT, PackT> lister) {
/*  72 */     this.xducer = xducer;
/*  73 */     this.lister = lister;
/*  74 */     this.acc = acc;
/*     */   }
/*     */   
/*     */   public boolean useNamespace() {
/*  78 */     return this.xducer.useNamespace();
/*     */   }
/*     */   
/*     */   public void declareNamespace(BeanT bean, XMLSerializer w) throws AccessorException, SAXException {
/*  82 */     ListT list = this.acc.get(bean);
/*     */     
/*  84 */     if (list != null) {
/*  85 */       ListIterator<ItemT> itr = this.lister.iterator(list, w);
/*     */       
/*  87 */       while (itr.hasNext()) {
/*     */         try {
/*  89 */           ItemT item = itr.next();
/*  90 */           if (item != null) {
/*  91 */             this.xducer.declareNamespace(item, w);
/*     */           }
/*  93 */         } catch (JAXBException e) {
/*  94 */           w.reportError(null, e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String print(BeanT o) throws AccessorException, SAXException {
/* 104 */     ListT list = this.acc.get(o);
/*     */     
/* 106 */     if (list == null) {
/* 107 */       return null;
/*     */     }
/* 109 */     StringBuilder buf = new StringBuilder();
/* 110 */     XMLSerializer w = XMLSerializer.getInstance();
/* 111 */     ListIterator<ItemT> itr = this.lister.iterator(list, w);
/*     */     
/* 113 */     while (itr.hasNext()) {
/*     */       try {
/* 115 */         ItemT item = itr.next();
/* 116 */         if (item != null) {
/* 117 */           if (buf.length() > 0) buf.append(' '); 
/* 118 */           buf.append(this.xducer.print(item));
/*     */         } 
/* 120 */       } catch (JAXBException e) {
/* 121 */         w.reportError(null, e);
/*     */       } 
/*     */     } 
/* 124 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private void processValue(BeanT bean, CharSequence s) throws AccessorException, SAXException {
/* 128 */     PackT pack = this.lister.startPacking(bean, this.acc);
/*     */     
/* 130 */     int idx = 0;
/* 131 */     int len = s.length();
/*     */     
/*     */     while (true) {
/* 134 */       int p = idx;
/* 135 */       while (p < len && !WhiteSpaceProcessor.isWhiteSpace(s.charAt(p))) {
/* 136 */         p++;
/*     */       }
/* 138 */       CharSequence token = s.subSequence(idx, p);
/* 139 */       if (!token.equals("")) {
/* 140 */         this.lister.addToPack(pack, (ItemT)this.xducer.parse(token));
/*     */       }
/* 142 */       if (p == len)
/*     */         break; 
/* 144 */       while (p < len && WhiteSpaceProcessor.isWhiteSpace(s.charAt(p)))
/* 145 */         p++; 
/* 146 */       if (p == len)
/*     */         break; 
/* 148 */       idx = p;
/*     */     } 
/*     */     
/* 151 */     this.lister.endPacking(pack, bean, this.acc);
/*     */   }
/*     */   
/*     */   public void parse(BeanT bean, CharSequence lexical) throws AccessorException, SAXException {
/* 155 */     processValue(bean, lexical);
/*     */   }
/*     */   
/*     */   public boolean hasValue(BeanT bean) throws AccessorException {
/* 159 */     return (this.acc.get(bean) != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\ListTransducedAccessorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */