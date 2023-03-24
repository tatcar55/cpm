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
/*     */ final class PrimitiveArrayListerDouble<BeanT>
/*     */   extends Lister<BeanT, double[], Double, PrimitiveArrayListerDouble.DoubleArrayPack>
/*     */ {
/*     */   static void register() {
/*  59 */     Lister.primitiveArrayListers.put(double.class, new PrimitiveArrayListerDouble());
/*     */   }
/*     */   
/*     */   public ListIterator<Double> iterator(final double[] objects, XMLSerializer context) {
/*  63 */     return new ListIterator<Double>() {
/*  64 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  66 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Double next() {
/*  70 */           return Double.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public DoubleArrayPack startPacking(BeanT current, Accessor<BeanT, double[]> acc) {
/*  76 */     return new DoubleArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(DoubleArrayPack objects, Double o) {
/*  80 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(DoubleArrayPack pack, BeanT bean, Accessor<BeanT, double[]> acc) throws AccessorException {
/*  84 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, double[]> acc) throws AccessorException {
/*  88 */     acc.set(o, new double[0]);
/*     */   }
/*     */   
/*     */   static final class DoubleArrayPack {
/*  92 */     double[] buf = new double[16];
/*     */     int size;
/*     */     
/*     */     void add(Double b) {
/*  96 */       if (this.buf.length == this.size) {
/*     */         
/*  98 */         double[] nb = new double[this.buf.length * 2];
/*  99 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/* 100 */         this.buf = nb;
/*     */       } 
/* 102 */       if (b != null)
/* 103 */         this.buf[this.size++] = b.doubleValue(); 
/*     */     }
/*     */     
/*     */     double[] build() {
/* 107 */       if (this.buf.length == this.size)
/*     */       {
/* 109 */         return this.buf;
/*     */       }
/* 111 */       double[] r = new double[this.size];
/* 112 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 113 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\PrimitiveArrayListerDouble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */