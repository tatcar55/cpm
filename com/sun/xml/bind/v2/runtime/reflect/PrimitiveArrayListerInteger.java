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
/*     */ final class PrimitiveArrayListerInteger<BeanT>
/*     */   extends Lister<BeanT, int[], Integer, PrimitiveArrayListerInteger.IntegerArrayPack>
/*     */ {
/*     */   static void register() {
/*  59 */     Lister.primitiveArrayListers.put(int.class, new PrimitiveArrayListerInteger());
/*     */   }
/*     */   
/*     */   public ListIterator<Integer> iterator(final int[] objects, XMLSerializer context) {
/*  63 */     return new ListIterator<Integer>() {
/*  64 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  66 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Integer next() {
/*  70 */           return Integer.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public IntegerArrayPack startPacking(BeanT current, Accessor<BeanT, int[]> acc) {
/*  76 */     return new IntegerArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(IntegerArrayPack objects, Integer o) {
/*  80 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(IntegerArrayPack pack, BeanT bean, Accessor<BeanT, int[]> acc) throws AccessorException {
/*  84 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, int[]> acc) throws AccessorException {
/*  88 */     acc.set(o, new int[0]);
/*     */   }
/*     */   
/*     */   static final class IntegerArrayPack {
/*  92 */     int[] buf = new int[16];
/*     */     int size;
/*     */     
/*     */     void add(Integer b) {
/*  96 */       if (this.buf.length == this.size) {
/*     */         
/*  98 */         int[] nb = new int[this.buf.length * 2];
/*  99 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/* 100 */         this.buf = nb;
/*     */       } 
/* 102 */       if (b != null)
/* 103 */         this.buf[this.size++] = b.intValue(); 
/*     */     }
/*     */     
/*     */     int[] build() {
/* 107 */       if (this.buf.length == this.size)
/*     */       {
/* 109 */         return this.buf;
/*     */       }
/* 111 */       int[] r = new int[this.size];
/* 112 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 113 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\PrimitiveArrayListerInteger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */