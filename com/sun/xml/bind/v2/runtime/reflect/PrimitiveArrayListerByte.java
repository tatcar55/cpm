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
/*     */ final class PrimitiveArrayListerByte<BeanT>
/*     */   extends Lister<BeanT, byte[], Byte, PrimitiveArrayListerByte.ByteArrayPack>
/*     */ {
/*     */   static void register() {
/*  59 */     Lister.primitiveArrayListers.put(byte.class, new PrimitiveArrayListerByte());
/*     */   }
/*     */   
/*     */   public ListIterator<Byte> iterator(final byte[] objects, XMLSerializer context) {
/*  63 */     return new ListIterator<Byte>() {
/*  64 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  66 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Byte next() {
/*  70 */           return Byte.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ByteArrayPack startPacking(BeanT current, Accessor<BeanT, byte[]> acc) {
/*  76 */     return new ByteArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(ByteArrayPack objects, Byte o) {
/*  80 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(ByteArrayPack pack, BeanT bean, Accessor<BeanT, byte[]> acc) throws AccessorException {
/*  84 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, byte[]> acc) throws AccessorException {
/*  88 */     acc.set(o, new byte[0]);
/*     */   }
/*     */   
/*     */   static final class ByteArrayPack {
/*  92 */     byte[] buf = new byte[16];
/*     */     int size;
/*     */     
/*     */     void add(Byte b) {
/*  96 */       if (this.buf.length == this.size) {
/*     */         
/*  98 */         byte[] nb = new byte[this.buf.length * 2];
/*  99 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/* 100 */         this.buf = nb;
/*     */       } 
/* 102 */       if (b != null)
/* 103 */         this.buf[this.size++] = b.byteValue(); 
/*     */     }
/*     */     
/*     */     byte[] build() {
/* 107 */       if (this.buf.length == this.size)
/*     */       {
/* 109 */         return this.buf;
/*     */       }
/* 111 */       byte[] r = new byte[this.size];
/* 112 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 113 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\PrimitiveArrayListerByte.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */