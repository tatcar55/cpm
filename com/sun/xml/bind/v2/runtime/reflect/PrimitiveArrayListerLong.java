/*     */ package com.sun.xml.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
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
/*     */ final class PrimitiveArrayListerLong<BeanT>
/*     */   extends Lister<BeanT, long[], Long, PrimitiveArrayListerLong.LongArrayPack>
/*     */ {
/*     */   static void register() {
/*  59 */     Lister.primitiveArrayListers.put(long.class, new PrimitiveArrayListerLong());
/*     */   }
/*     */   
/*     */   public ListIterator<Long> iterator(final long[] objects, XMLSerializer context) {
/*  63 */     return new ListIterator<Long>() {
/*  64 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  66 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Long next() {
/*  70 */           return Long.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public LongArrayPack startPacking(BeanT current, Accessor<BeanT, long[]> acc) {
/*  76 */     return new LongArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(LongArrayPack objects, Long o) {
/*  80 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(LongArrayPack pack, BeanT bean, Accessor<BeanT, long[]> acc) throws AccessorException {
/*  84 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, long[]> acc) throws AccessorException {
/*  88 */     acc.set(o, new long[0]);
/*     */   }
/*     */   
/*     */   static final class LongArrayPack {
/*  92 */     long[] buf = new long[16];
/*     */     int size;
/*     */     
/*     */     void add(Long b) {
/*  96 */       if (this.buf.length == this.size) {
/*     */         
/*  98 */         long[] nb = new long[this.buf.length * 2];
/*  99 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/* 100 */         this.buf = nb;
/*     */       } 
/* 102 */       if (b != null)
/* 103 */         this.buf[this.size++] = b.longValue(); 
/*     */     }
/*     */     
/*     */     long[] build() {
/* 107 */       if (this.buf.length == this.size)
/*     */       {
/* 109 */         return this.buf;
/*     */       }
/* 111 */       long[] r = new long[this.size];
/* 112 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 113 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\PrimitiveArrayListerLong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */