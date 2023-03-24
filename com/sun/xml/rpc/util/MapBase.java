/*     */ package com.sun.xml.rpc.util;
/*     */ 
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MapBase
/*     */   implements Map
/*     */ {
/*     */   public int size() {
/*  95 */     return entrySet().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 106 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 127 */     Iterator<Map.Entry> i = entrySet().iterator();
/* 128 */     if (value == null) {
/* 129 */       while (i.hasNext()) {
/* 130 */         Map.Entry e = i.next();
/* 131 */         if (e.getValue() == null)
/* 132 */           return true; 
/*     */       } 
/*     */     } else {
/* 135 */       while (i.hasNext()) {
/* 136 */         Map.Entry e = i.next();
/* 137 */         if (value.equals(e.getValue()))
/* 138 */           return true; 
/*     */       } 
/*     */     } 
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 163 */     Iterator<Map.Entry> i = entrySet().iterator();
/* 164 */     if (key == null) {
/* 165 */       while (i.hasNext()) {
/* 166 */         Map.Entry e = i.next();
/* 167 */         if (e.getKey() == null)
/* 168 */           return true; 
/*     */       } 
/*     */     } else {
/* 171 */       while (i.hasNext()) {
/* 172 */         Map.Entry e = i.next();
/* 173 */         if (key.equals(e.getKey()))
/* 174 */           return true; 
/*     */       } 
/*     */     } 
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/* 204 */     Iterator<Map.Entry> i = entrySet().iterator();
/* 205 */     if (key == null) {
/* 206 */       while (i.hasNext()) {
/* 207 */         Map.Entry e = i.next();
/* 208 */         if (e.getKey() == null)
/* 209 */           return e.getValue(); 
/*     */       } 
/*     */     } else {
/* 212 */       while (i.hasNext()) {
/* 213 */         Map.Entry e = i.next();
/* 214 */         if (key.equals(e.getKey()))
/* 215 */           return e.getValue(); 
/*     */       } 
/*     */     } 
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(Object key, Object value) {
/* 254 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/* 285 */     Iterator<Map.Entry> i = entrySet().iterator();
/* 286 */     Map.Entry correctEntry = null;
/* 287 */     if (key == null) {
/* 288 */       while (correctEntry == null && i.hasNext()) {
/* 289 */         Map.Entry e = i.next();
/* 290 */         if (e.getKey() == null)
/* 291 */           correctEntry = e; 
/*     */       } 
/*     */     } else {
/* 294 */       while (correctEntry == null && i.hasNext()) {
/* 295 */         Map.Entry e = i.next();
/* 296 */         if (key.equals(e.getKey())) {
/* 297 */           correctEntry = e;
/*     */         }
/*     */       } 
/*     */     } 
/* 301 */     Object oldValue = null;
/* 302 */     if (correctEntry != null) {
/* 303 */       oldValue = correctEntry.getValue();
/* 304 */       i.remove();
/*     */     } 
/* 306 */     return oldValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map t) {
/* 339 */     Iterator<Map.Entry> i = t.entrySet().iterator();
/* 340 */     while (i.hasNext()) {
/* 341 */       Map.Entry e = i.next();
/* 342 */       put(e.getKey(), e.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 359 */     entrySet().clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 369 */   volatile transient Set keySet = null;
/* 370 */   volatile transient Collection values = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set keySet() {
/* 396 */     if (this.keySet == null) {
/* 397 */       this.keySet = new AbstractSet() {
/*     */           public Iterator iterator() {
/* 399 */             return new Iterator() {
/* 400 */                 private Iterator i = MapBase.this.entrySet().iterator();
/*     */                 
/*     */                 public boolean hasNext() {
/* 403 */                   return this.i.hasNext();
/*     */                 }
/*     */                 
/*     */                 public Object next() {
/* 407 */                   return ((Map.Entry)this.i.next()).getKey();
/*     */                 }
/*     */                 
/*     */                 public void remove() {
/* 411 */                   this.i.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */           
/*     */           public int size() {
/* 417 */             return MapBase.this.size();
/*     */           }
/*     */           
/*     */           public boolean contains(Object k) {
/* 421 */             return MapBase.this.containsKey(k);
/*     */           }
/*     */         };
/*     */     }
/* 425 */     return this.keySet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection values() {
/* 453 */     if (this.values == null) {
/* 454 */       this.values = new AbstractCollection() {
/*     */           public Iterator iterator() {
/* 456 */             return new Iterator() {
/* 457 */                 private Iterator i = MapBase.this.entrySet().iterator();
/*     */                 
/*     */                 public boolean hasNext() {
/* 460 */                   return this.i.hasNext();
/*     */                 }
/*     */                 
/*     */                 public Object next() {
/* 464 */                   return ((Map.Entry)this.i.next()).getValue();
/*     */                 }
/*     */                 
/*     */                 public void remove() {
/* 468 */                   this.i.remove();
/*     */                 }
/*     */               };
/*     */           }
/*     */           
/*     */           public int size() {
/* 474 */             return MapBase.this.size();
/*     */           }
/*     */           
/*     */           public boolean contains(Object v) {
/* 478 */             return MapBase.this.containsValue(v);
/*     */           }
/*     */         };
/*     */     }
/* 482 */     return this.values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Set entrySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 526 */     if (o == this) {
/* 527 */       return true;
/*     */     }
/* 529 */     if (!(o instanceof Map))
/* 530 */       return false; 
/* 531 */     Map t = (Map)o;
/* 532 */     if (t.size() != size()) {
/* 533 */       return false;
/*     */     }
/*     */     try {
/* 536 */       Iterator<Map.Entry> i = entrySet().iterator();
/* 537 */       while (i.hasNext()) {
/* 538 */         Map.Entry e = i.next();
/* 539 */         Object key = e.getKey();
/* 540 */         Object value = e.getValue();
/* 541 */         if (value == null) {
/* 542 */           if (t.get(key) != null || !t.containsKey(key))
/* 543 */             return false;  continue;
/*     */         } 
/* 545 */         if (!value.equals(t.get(key))) {
/* 546 */           return false;
/*     */         }
/*     */       } 
/* 549 */     } catch (ClassCastException unused) {
/* 550 */       return false;
/* 551 */     } catch (NullPointerException unused) {
/* 552 */       return false;
/*     */     } 
/*     */     
/* 555 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 577 */     int h = 0;
/* 578 */     Iterator<E> i = entrySet().iterator();
/* 579 */     while (i.hasNext())
/* 580 */       h += i.next().hashCode(); 
/* 581 */     return h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 604 */     StringBuffer buf = new StringBuffer();
/* 605 */     buf.append("{");
/*     */     
/* 607 */     Iterator<Map.Entry> i = entrySet().iterator();
/* 608 */     boolean hasNext = i.hasNext();
/* 609 */     while (hasNext) {
/* 610 */       Map.Entry e = i.next();
/* 611 */       Object key = e.getKey();
/* 612 */       Object value = e.getValue();
/* 613 */       buf.append(((key == this) ? "(this Map)" : (String)key) + "=" + ((value == this) ? "(this Map)" : (String)value));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 618 */       hasNext = i.hasNext();
/* 619 */       if (hasNext) {
/* 620 */         buf.append(", ");
/*     */       }
/*     */     } 
/* 623 */     buf.append("}");
/* 624 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() throws CloneNotSupportedException {
/* 634 */     MapBase result = (MapBase)super.clone();
/* 635 */     result.keySet = null;
/* 636 */     result.values = null;
/* 637 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   static class SimpleEntry
/*     */     implements Map.Entry
/*     */   {
/*     */     Object key;
/*     */     
/*     */     Object value;
/*     */     
/*     */     public SimpleEntry(Object key, Object value) {
/* 649 */       this.key = key;
/* 650 */       this.value = value;
/*     */     }
/*     */     
/*     */     public SimpleEntry(Map.Entry e) {
/* 654 */       this.key = e.getKey();
/* 655 */       this.value = e.getValue();
/*     */     }
/*     */     
/*     */     public Object getKey() {
/* 659 */       return this.key;
/*     */     }
/*     */     
/*     */     public Object getValue() {
/* 663 */       return this.value;
/*     */     }
/*     */     
/*     */     public Object setValue(Object value) {
/* 667 */       Object oldValue = this.value;
/* 668 */       this.value = value;
/* 669 */       return oldValue;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 673 */       if (!(o instanceof Map.Entry))
/* 674 */         return false; 
/* 675 */       Map.Entry e = (Map.Entry)o;
/* 676 */       return (eq(this.key, e.getKey()) && eq(this.value, e.getValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 681 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 686 */       return this.key + "=" + this.value;
/*     */     }
/*     */     
/*     */     private static boolean eq(Object o1, Object o2) {
/* 690 */       return (o1 == null) ? ((o2 == null)) : o1.equals(o2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\MapBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */