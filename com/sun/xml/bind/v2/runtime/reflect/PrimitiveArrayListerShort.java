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
/*     */ final class PrimitiveArrayListerShort<BeanT>
/*     */   extends Lister<BeanT, short[], Short, PrimitiveArrayListerShort.ShortArrayPack>
/*     */ {
/*     */   static void register() {
/*  59 */     Lister.primitiveArrayListers.put(short.class, new PrimitiveArrayListerShort());
/*     */   }
/*     */   
/*     */   public ListIterator<Short> iterator(final short[] objects, XMLSerializer context) {
/*  63 */     return new ListIterator<Short>() {
/*  64 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  66 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Short next() {
/*  70 */           return Short.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ShortArrayPack startPacking(BeanT current, Accessor<BeanT, short[]> acc) {
/*  76 */     return new ShortArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(ShortArrayPack objects, Short o) {
/*  80 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(ShortArrayPack pack, BeanT bean, Accessor<BeanT, short[]> acc) throws AccessorException {
/*  84 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, short[]> acc) throws AccessorException {
/*  88 */     acc.set(o, new short[0]);
/*     */   }
/*     */   
/*     */   static final class ShortArrayPack {
/*  92 */     short[] buf = new short[16];
/*     */     int size;
/*     */     
/*     */     void add(Short b) {
/*  96 */       if (this.buf.length == this.size) {
/*     */         
/*  98 */         short[] nb = new short[this.buf.length * 2];
/*  99 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/* 100 */         this.buf = nb;
/*     */       } 
/* 102 */       if (b != null)
/* 103 */         this.buf[this.size++] = b.shortValue(); 
/*     */     }
/*     */     
/*     */     short[] build() {
/* 107 */       if (this.buf.length == this.size)
/*     */       {
/* 109 */         return this.buf;
/*     */       }
/* 111 */       short[] r = new short[this.size];
/* 112 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 113 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\PrimitiveArrayListerShort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */