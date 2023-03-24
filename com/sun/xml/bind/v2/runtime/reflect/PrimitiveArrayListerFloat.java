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
/*     */ final class PrimitiveArrayListerFloat<BeanT>
/*     */   extends Lister<BeanT, float[], Float, PrimitiveArrayListerFloat.FloatArrayPack>
/*     */ {
/*     */   static void register() {
/*  59 */     Lister.primitiveArrayListers.put(float.class, new PrimitiveArrayListerFloat());
/*     */   }
/*     */   
/*     */   public ListIterator<Float> iterator(final float[] objects, XMLSerializer context) {
/*  63 */     return new ListIterator<Float>() {
/*  64 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  66 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Float next() {
/*  70 */           return Float.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public FloatArrayPack startPacking(BeanT current, Accessor<BeanT, float[]> acc) {
/*  76 */     return new FloatArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(FloatArrayPack objects, Float o) {
/*  80 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(FloatArrayPack pack, BeanT bean, Accessor<BeanT, float[]> acc) throws AccessorException {
/*  84 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, float[]> acc) throws AccessorException {
/*  88 */     acc.set(o, new float[0]);
/*     */   }
/*     */   
/*     */   static final class FloatArrayPack {
/*  92 */     float[] buf = new float[16];
/*     */     int size;
/*     */     
/*     */     void add(Float b) {
/*  96 */       if (this.buf.length == this.size) {
/*     */         
/*  98 */         float[] nb = new float[this.buf.length * 2];
/*  99 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/* 100 */         this.buf = nb;
/*     */       } 
/* 102 */       if (b != null)
/* 103 */         this.buf[this.size++] = b.floatValue(); 
/*     */     }
/*     */     
/*     */     float[] build() {
/* 107 */       if (this.buf.length == this.size)
/*     */       {
/* 109 */         return this.buf;
/*     */       }
/* 111 */       float[] r = new float[this.size];
/* 112 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 113 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\PrimitiveArrayListerFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */