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
/*     */ final class PrimitiveArrayListerBoolean<BeanT>
/*     */   extends Lister<BeanT, boolean[], Boolean, PrimitiveArrayListerBoolean.BooleanArrayPack>
/*     */ {
/*     */   static void register() {
/*  59 */     Lister.primitiveArrayListers.put(boolean.class, new PrimitiveArrayListerBoolean());
/*     */   }
/*     */   
/*     */   public ListIterator<Boolean> iterator(final boolean[] objects, XMLSerializer context) {
/*  63 */     return new ListIterator<Boolean>() {
/*  64 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  66 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Boolean next() {
/*  70 */           return Boolean.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public BooleanArrayPack startPacking(BeanT current, Accessor<BeanT, boolean[]> acc) {
/*  76 */     return new BooleanArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(BooleanArrayPack objects, Boolean o) {
/*  80 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(BooleanArrayPack pack, BeanT bean, Accessor<BeanT, boolean[]> acc) throws AccessorException {
/*  84 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, boolean[]> acc) throws AccessorException {
/*  88 */     acc.set(o, new boolean[0]);
/*     */   }
/*     */   
/*     */   static final class BooleanArrayPack {
/*  92 */     boolean[] buf = new boolean[16];
/*     */     int size;
/*     */     
/*     */     void add(Boolean b) {
/*  96 */       if (this.buf.length == this.size) {
/*     */         
/*  98 */         boolean[] nb = new boolean[this.buf.length * 2];
/*  99 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/* 100 */         this.buf = nb;
/*     */       } 
/* 102 */       if (b != null)
/* 103 */         this.buf[this.size++] = b.booleanValue(); 
/*     */     }
/*     */     
/*     */     boolean[] build() {
/* 107 */       if (this.buf.length == this.size)
/*     */       {
/* 109 */         return this.buf;
/*     */       }
/* 111 */       boolean[] r = new boolean[this.size];
/* 112 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 113 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\PrimitiveArrayListerBoolean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */