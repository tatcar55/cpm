/*     */ package com.sun.xml.bind.v2.util;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CollisionCheckStack<E>
/*     */   extends AbstractList<E>
/*     */ {
/*     */   private Object[] data;
/*     */   private int[] next;
/*  69 */   private int size = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean latestPushResult = false;
/*     */ 
/*     */   
/*     */   private boolean useIdentity = true;
/*     */ 
/*     */   
/*     */   private final int[] initialHash;
/*     */ 
/*     */ 
/*     */   
/*     */   public CollisionCheckStack() {
/*  84 */     this.initialHash = new int[17];
/*  85 */     this.data = new Object[16];
/*  86 */     this.next = new int[16];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseIdentity(boolean useIdentity) {
/*  94 */     this.useIdentity = useIdentity;
/*     */   }
/*     */   
/*     */   public boolean getUseIdentity() {
/*  98 */     return this.useIdentity;
/*     */   }
/*     */   
/*     */   public boolean getLatestPushResult() {
/* 102 */     return this.latestPushResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean push(E o) {
/* 112 */     if (this.data.length == this.size) {
/* 113 */       expandCapacity();
/*     */     }
/* 115 */     this.data[this.size] = o;
/* 116 */     int hash = hash(o);
/* 117 */     boolean r = findDuplicate(o, hash);
/* 118 */     this.next[this.size] = this.initialHash[hash];
/* 119 */     this.initialHash[hash] = this.size + 1;
/* 120 */     this.size++;
/* 121 */     this.latestPushResult = r;
/* 122 */     return this.latestPushResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushNocheck(E o) {
/* 130 */     if (this.data.length == this.size)
/* 131 */       expandCapacity(); 
/* 132 */     this.data[this.size] = o;
/* 133 */     this.next[this.size] = -1;
/* 134 */     this.size++;
/*     */   }
/*     */   
/*     */   public boolean findDuplicate(E o) {
/* 138 */     int hash = hash(o);
/* 139 */     return findDuplicate(o, hash);
/*     */   }
/*     */ 
/*     */   
/*     */   public E get(int index) {
/* 144 */     return (E)this.data[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 149 */     return this.size;
/*     */   }
/*     */   
/*     */   private int hash(Object o) {
/* 153 */     return ((this.useIdentity ? System.identityHashCode(o) : o.hashCode()) & Integer.MAX_VALUE) % this.initialHash.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E pop() {
/* 160 */     this.size--;
/* 161 */     Object o = this.data[this.size];
/* 162 */     this.data[this.size] = null;
/* 163 */     int n = this.next[this.size];
/* 164 */     if (n >= 0) {
/*     */ 
/*     */       
/* 167 */       int hash = hash(o);
/* 168 */       assert this.initialHash[hash] == this.size + 1;
/* 169 */       this.initialHash[hash] = n;
/*     */     } 
/* 171 */     return (E)o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E peek() {
/* 178 */     return (E)this.data[this.size - 1];
/*     */   }
/*     */   
/*     */   private boolean findDuplicate(E o, int hash) {
/* 182 */     int p = this.initialHash[hash];
/* 183 */     while (p != 0) {
/* 184 */       p--;
/* 185 */       Object existing = this.data[p];
/* 186 */       if (this.useIdentity)
/* 187 */       { if (existing == o) return true;
/*     */          }
/* 189 */       else if (o.equals(existing)) { return true; }
/*     */       
/* 191 */       p = this.next[p];
/*     */     } 
/* 193 */     return false;
/*     */   }
/*     */   
/*     */   private void expandCapacity() {
/* 197 */     int oldSize = this.data.length;
/* 198 */     int newSize = oldSize * 2;
/* 199 */     Object[] d = new Object[newSize];
/* 200 */     int[] n = new int[newSize];
/*     */     
/* 202 */     System.arraycopy(this.data, 0, d, 0, oldSize);
/* 203 */     System.arraycopy(this.next, 0, n, 0, oldSize);
/*     */     
/* 205 */     this.data = d;
/* 206 */     this.next = n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 213 */     if (this.size > 0) {
/* 214 */       this.size = 0;
/* 215 */       Arrays.fill(this.initialHash, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCycleString() {
/*     */     Object x;
/* 223 */     StringBuilder sb = new StringBuilder();
/* 224 */     int i = size() - 1;
/* 225 */     E obj = get(i);
/* 226 */     sb.append(obj);
/*     */     
/*     */     do {
/* 229 */       sb.append(" -> ");
/* 230 */       x = get(--i);
/* 231 */       sb.append(x);
/* 232 */     } while (obj != x);
/*     */     
/* 234 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v\\util\CollisionCheckStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */