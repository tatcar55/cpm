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
/*     */ final class PrimitiveArrayListerCharacter<BeanT>
/*     */   extends Lister<BeanT, char[], Character, PrimitiveArrayListerCharacter.CharacterArrayPack>
/*     */ {
/*     */   static void register() {
/*  59 */     Lister.primitiveArrayListers.put(char.class, new PrimitiveArrayListerCharacter());
/*     */   }
/*     */   
/*     */   public ListIterator<Character> iterator(final char[] objects, XMLSerializer context) {
/*  63 */     return new ListIterator<Character>() {
/*  64 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  66 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Character next() {
/*  70 */           return Character.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public CharacterArrayPack startPacking(BeanT current, Accessor<BeanT, char[]> acc) {
/*  76 */     return new CharacterArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(CharacterArrayPack objects, Character o) {
/*  80 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(CharacterArrayPack pack, BeanT bean, Accessor<BeanT, char[]> acc) throws AccessorException {
/*  84 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, char[]> acc) throws AccessorException {
/*  88 */     acc.set(o, new char[0]);
/*     */   }
/*     */   
/*     */   static final class CharacterArrayPack {
/*  92 */     char[] buf = new char[16];
/*     */     int size;
/*     */     
/*     */     void add(Character b) {
/*  96 */       if (this.buf.length == this.size) {
/*     */         
/*  98 */         char[] nb = new char[this.buf.length * 2];
/*  99 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/* 100 */         this.buf = nb;
/*     */       } 
/* 102 */       if (b != null)
/* 103 */         this.buf[this.size++] = b.charValue(); 
/*     */     }
/*     */     
/*     */     char[] build() {
/* 107 */       if (this.buf.length == this.size)
/*     */       {
/* 109 */         return this.buf;
/*     */       }
/* 111 */       char[] r = new char[this.size];
/* 112 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 113 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\PrimitiveArrayListerCharacter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */