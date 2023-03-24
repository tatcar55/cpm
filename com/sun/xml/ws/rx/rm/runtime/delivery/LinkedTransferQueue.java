/*     */ package com.sun.xml.ws.rx.rm.runtime.delivery;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractQueue;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ import java.util.concurrent.locks.LockSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinkedTransferQueue<E>
/*     */   extends AbstractQueue<E>
/*     */   implements TransferQueue<E>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3223113410248163686L;
/*     */   static final int NOWAIT = 0;
/*     */   static final int TIMEOUT = 1;
/*     */   static final int WAIT = 2;
/* 117 */   static final int NCPUS = Runtime.getRuntime().availableProcessors();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   static final int maxTimedSpins = (NCPUS < 2) ? 0 : 32;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   static final int maxUntimedSpins = maxTimedSpins * 16;
/*     */   
/*     */   static final long spinForTimeoutThreshold = 1000L;
/*     */   
/*     */   private final transient PaddedAtomicReference<QNode> head;
/*     */   
/*     */   private final transient PaddedAtomicReference<QNode> tail;
/*     */   
/*     */   private final transient PaddedAtomicReference<QNode> cleanMe;
/*     */ 
/*     */   
/*     */   static final class QNode
/*     */     extends AtomicReference<Object>
/*     */   {
/*     */     private static final long serialVersionUID = 3362675195106867587L;
/*     */     
/*     */     volatile QNode next;
/*     */     
/*     */     volatile Thread waiter;
/*     */     final boolean isData;
/*     */     
/*     */     QNode(Object item, boolean isData) {
/* 153 */       super(item);
/* 154 */       this.isData = isData;
/*     */     }
/* 156 */     static final AtomicReferenceFieldUpdater<QNode, QNode> nextUpdater = AtomicReferenceFieldUpdater.newUpdater(QNode.class, QNode.class, "next");
/*     */     
/*     */     final boolean casNext(QNode cmp, QNode val) {
/* 159 */       return nextUpdater.compareAndSet(this, cmp, val);
/*     */     }
/*     */     
/*     */     final void clearNext() {
/* 163 */       nextUpdater.set(this, this);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class PaddedAtomicReference<T>
/*     */     extends AtomicReference<T> {
/*     */     private static final long serialVersionUID = -5401590975249751717L;
/*     */     Object p0;
/*     */     Object p1;
/*     */     Object p2;
/*     */     Object p3;
/*     */     Object p4;
/*     */     Object p5;
/*     */     Object p6;
/*     */     
/*     */     PaddedAtomicReference(T r) {
/* 179 */       super(r);
/*     */     }
/*     */ 
/*     */     
/*     */     Object p7;
/*     */     
/*     */     Object p8;
/*     */     
/*     */     Object p9;
/*     */     
/*     */     Object pa;
/*     */     
/*     */     Object pb;
/*     */     Object pc;
/*     */     Object pd;
/*     */     Object pe;
/*     */   }
/*     */   
/*     */   private boolean advanceHead(QNode h, QNode nh) {
/* 198 */     if (h == this.head.get() && this.head.compareAndSet(h, nh)) {
/* 199 */       h.clearNext();
/* 200 */       return true;
/*     */     } 
/* 202 */     return false;
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
/*     */   private Object xfer(Object e, int mode, long nanos) {
/* 215 */     boolean isData = (e != null);
/* 216 */     QNode s = null;
/* 217 */     PaddedAtomicReference<QNode> headRef = this.head;
/* 218 */     PaddedAtomicReference<QNode> tailRef = this.tail;
/*     */     
/*     */     while (true) {
/* 221 */       QNode t = tailRef.get();
/* 222 */       QNode h = headRef.get();
/*     */       
/* 224 */       if (t != null && (t == h || t.isData == isData)) {
/* 225 */         if (s == null) {
/* 226 */           s = new QNode(e, isData);
/*     */         }
/* 228 */         QNode last = t.next;
/* 229 */         if (last != null) {
/* 230 */           if (t == tailRef.get())
/* 231 */             tailRef.compareAndSet(t, last);  continue;
/*     */         } 
/* 233 */         if (t.casNext((QNode)null, s)) {
/* 234 */           tailRef.compareAndSet(t, s);
/* 235 */           return awaitFulfill(t, s, e, mode, nanos);
/*     */         }  continue;
/* 237 */       }  if (h != null) {
/* 238 */         QNode first = h.next;
/* 239 */         if (t == tailRef.get() && first != null && advanceHead(h, first)) {
/*     */           
/* 241 */           Object x = first.get();
/* 242 */           if (x != first && first.compareAndSet(x, e)) {
/* 243 */             LockSupport.unpark(first.waiter);
/* 244 */             return isData ? e : x;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object fulfill(Object e) {
/* 256 */     boolean isData = (e != null);
/* 257 */     PaddedAtomicReference<QNode> headRef = this.head;
/* 258 */     PaddedAtomicReference<QNode> tailRef = this.tail;
/*     */     
/*     */     while (true) {
/* 261 */       QNode t = tailRef.get();
/* 262 */       QNode h = headRef.get();
/*     */       
/* 264 */       if (t != null && (t == h || t.isData == isData)) {
/* 265 */         QNode last = t.next;
/* 266 */         if (t == tailRef.get()) {
/* 267 */           if (last != null) {
/* 268 */             tailRef.compareAndSet(t, last); continue;
/*     */           } 
/* 270 */           return null;
/*     */         }  continue;
/*     */       } 
/* 273 */       if (h != null) {
/* 274 */         QNode first = h.next;
/* 275 */         if (t == tailRef.get() && first != null && advanceHead(h, first)) {
/*     */ 
/*     */           
/* 278 */           Object x = first.get();
/* 279 */           if (x != first && first.compareAndSet(x, e)) {
/* 280 */             LockSupport.unpark(first.waiter);
/* 281 */             return isData ? e : x;
/*     */           } 
/*     */         } 
/*     */       } 
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
/*     */   private Object awaitFulfill(QNode pred, QNode s, Object e, int mode, long nanos) {
/* 301 */     if (mode == 0) {
/* 302 */       return null;
/*     */     }
/*     */     
/* 305 */     long lastTime = (mode == 1) ? System.nanoTime() : 0L;
/* 306 */     Thread w = Thread.currentThread();
/* 307 */     int spins = -1;
/*     */     while (true) {
/* 309 */       if (w.isInterrupted()) {
/* 310 */         s.compareAndSet(e, s);
/*     */       }
/* 312 */       Object x = s.get();
/* 313 */       if (x != e) {
/* 314 */         advanceHead(pred, s);
/* 315 */         if (x == s) {
/* 316 */           clean(pred, s);
/* 317 */           return null;
/* 318 */         }  if (x != null) {
/* 319 */           s.set(s);
/* 320 */           return x;
/*     */         } 
/* 322 */         return e;
/*     */       } 
/*     */       
/* 325 */       if (mode == 1) {
/* 326 */         long now = System.nanoTime();
/* 327 */         nanos -= now - lastTime;
/* 328 */         lastTime = now;
/* 329 */         if (nanos <= 0L) {
/* 330 */           s.compareAndSet(e, s);
/*     */           continue;
/*     */         } 
/*     */       } 
/* 334 */       if (spins < 0) {
/* 335 */         QNode h = this.head.get();
/* 336 */         spins = (h != null && h.next == s) ? ((mode == 1) ? maxTimedSpins : maxUntimedSpins) : 0;
/*     */       } 
/* 338 */       if (spins > 0) {
/* 339 */         spins--; continue;
/* 340 */       }  if (s.waiter == null) {
/* 341 */         s.waiter = w; continue;
/* 342 */       }  if (mode != 1) {
/* 343 */         LockSupport.park();
/* 344 */         s.waiter = null;
/* 345 */         spins = -1; continue;
/* 346 */       }  if (nanos > 1000L) {
/* 347 */         LockSupport.parkNanos(nanos);
/* 348 */         s.waiter = null;
/* 349 */         spins = -1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private QNode getValidatedTail() {
/*     */     QNode t;
/*     */     while (true) {
/* 359 */       QNode h = this.head.get();
/* 360 */       QNode first = h.next;
/* 361 */       if (first != null && first.next == first) {
/* 362 */         advanceHead(h, first);
/*     */         continue;
/*     */       } 
/* 365 */       t = this.tail.get();
/* 366 */       QNode last = t.next;
/* 367 */       if (t == this.tail.get()) {
/* 368 */         if (last != null)
/* 369 */         { this.tail.compareAndSet(t, last); continue; }  break;
/*     */       } 
/* 371 */     }  return t;
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
/*     */   private void clean(QNode pred, QNode s) {
/* 383 */     Thread w = s.waiter;
/* 384 */     if (w != null) {
/* 385 */       s.waiter = null;
/* 386 */       if (w != Thread.currentThread()) {
/* 387 */         LockSupport.unpark(w);
/*     */       }
/*     */     } 
/*     */     
/* 391 */     if (pred == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 403 */     while (pred.next == s) {
/* 404 */       QNode oldpred = reclean();
/* 405 */       QNode t = getValidatedTail();
/* 406 */       if (s != t) {
/* 407 */         QNode sn = s.next;
/* 408 */         if (sn == s || pred.casNext(s, sn))
/*     */           break;  continue;
/*     */       } 
/* 411 */       if (oldpred == pred || (oldpred == null && this.cleanMe.compareAndSet(null, pred))) {
/*     */         break;
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QNode reclean() {
/*     */     QNode pred;
/* 436 */     while ((pred = this.cleanMe.get()) != null) {
/* 437 */       QNode t = getValidatedTail();
/* 438 */       QNode s = pred.next;
/* 439 */       if (s != t) {
/*     */         QNode sn;
/* 441 */         if (s == null || s == pred || s.get() != s || (sn = s.next) == s || pred.casNext(s, sn))
/*     */         {
/* 443 */           this.cleanMe.compareAndSet(pred, null);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 450 */     return pred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedTransferQueue() {
/* 457 */     QNode dummy = new QNode(null, false);
/* 458 */     this.head = new PaddedAtomicReference<QNode>(dummy);
/* 459 */     this.tail = new PaddedAtomicReference<QNode>(dummy);
/* 460 */     this.cleanMe = new PaddedAtomicReference<QNode>(null);
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
/*     */   public LinkedTransferQueue(Collection<? extends E> c) {
/* 472 */     this();
/* 473 */     addAll(c);
/*     */   }
/*     */   
/*     */   public void put(E e) throws InterruptedException {
/* 477 */     if (e == null) {
/* 478 */       throw new NullPointerException();
/*     */     }
/* 480 */     if (Thread.interrupted()) {
/* 481 */       throw new InterruptedException();
/*     */     }
/* 483 */     xfer(e, 0, 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
/* 488 */     if (e == null) {
/* 489 */       throw new NullPointerException();
/*     */     }
/* 491 */     if (Thread.interrupted()) {
/* 492 */       throw new InterruptedException();
/*     */     }
/* 494 */     xfer(e, 0, 0L);
/* 495 */     return true;
/*     */   }
/*     */   
/*     */   public boolean offer(E e) {
/* 499 */     if (e == null) {
/* 500 */       throw new NullPointerException();
/*     */     }
/* 502 */     xfer(e, 0, 0L);
/* 503 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(E e) {
/* 508 */     if (e == null) {
/* 509 */       throw new NullPointerException();
/*     */     }
/* 511 */     xfer(e, 0, 0L);
/* 512 */     return true;
/*     */   }
/*     */   
/*     */   public void transfer(E e) throws InterruptedException {
/* 516 */     if (e == null) {
/* 517 */       throw new NullPointerException();
/*     */     }
/* 519 */     if (xfer(e, 2, 0L) == null) {
/* 520 */       Thread.interrupted();
/* 521 */       throw new InterruptedException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryTransfer(E e, long timeout, TimeUnit unit) throws InterruptedException {
/* 527 */     if (e == null) {
/* 528 */       throw new NullPointerException();
/*     */     }
/* 530 */     if (xfer(e, 1, unit.toNanos(timeout)) != null) {
/* 531 */       return true;
/*     */     }
/* 533 */     if (!Thread.interrupted()) {
/* 534 */       return false;
/*     */     }
/* 536 */     throw new InterruptedException();
/*     */   }
/*     */   
/*     */   public boolean tryTransfer(E e) {
/* 540 */     if (e == null) {
/* 541 */       throw new NullPointerException();
/*     */     }
/* 543 */     return (fulfill(e) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public E take() throws InterruptedException {
/* 548 */     Object e = xfer((Object)null, 2, 0L);
/* 549 */     if (e != null) {
/* 550 */       return (E)e;
/*     */     }
/* 552 */     Thread.interrupted();
/* 553 */     throw new InterruptedException();
/*     */   }
/*     */ 
/*     */   
/*     */   public E poll(long timeout, TimeUnit unit) throws InterruptedException {
/* 558 */     Object e = xfer((Object)null, 1, unit.toNanos(timeout));
/* 559 */     if (e != null || !Thread.interrupted()) {
/* 560 */       return (E)e;
/*     */     }
/* 562 */     throw new InterruptedException();
/*     */   }
/*     */ 
/*     */   
/*     */   public E poll() {
/* 567 */     return (E)fulfill((Object)null);
/*     */   }
/*     */   
/*     */   public int drainTo(Collection<? super E> c) {
/* 571 */     if (c == null) {
/* 572 */       throw new NullPointerException();
/*     */     }
/* 574 */     if (c == this) {
/* 575 */       throw new IllegalArgumentException();
/*     */     }
/* 577 */     int n = 0;
/*     */     E e;
/* 579 */     while ((e = poll()) != null) {
/* 580 */       c.add(e);
/* 581 */       n++;
/*     */     } 
/* 583 */     return n;
/*     */   }
/*     */   
/*     */   public int drainTo(Collection<? super E> c, int maxElements) {
/* 587 */     if (c == null) {
/* 588 */       throw new NullPointerException();
/*     */     }
/* 590 */     if (c == this) {
/* 591 */       throw new IllegalArgumentException();
/*     */     }
/* 593 */     int n = 0;
/*     */     E e;
/* 595 */     while (n < maxElements && (e = poll()) != null) {
/* 596 */       c.add(e);
/* 597 */       n++;
/*     */     } 
/* 599 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QNode traversalHead() {
/*     */     while (true) {
/* 608 */       QNode t = this.tail.get();
/* 609 */       QNode h = this.head.get();
/* 610 */       if (h != null && t != null) {
/* 611 */         QNode last = t.next;
/* 612 */         QNode first = h.next;
/* 613 */         if (t == this.tail.get()) {
/* 614 */           if (last != null) {
/* 615 */             this.tail.compareAndSet(t, last);
/* 616 */           } else if (first != null) {
/* 617 */             Object x = first.get();
/* 618 */             if (x == first) {
/* 619 */               advanceHead(h, first);
/*     */             } else {
/* 621 */               return h;
/*     */             } 
/*     */           } else {
/* 624 */             return h;
/*     */           } 
/*     */         }
/*     */       } 
/* 628 */       reclean();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Iterator<E> iterator() {
/* 633 */     return new Itr();
/*     */   }
/*     */ 
/*     */   
/*     */   class Itr
/*     */     implements Iterator<E>
/*     */   {
/*     */     LinkedTransferQueue.QNode next;
/*     */     
/*     */     LinkedTransferQueue.QNode pnext;
/*     */     
/*     */     LinkedTransferQueue.QNode snext;
/*     */     
/*     */     LinkedTransferQueue.QNode curr;
/*     */     
/*     */     LinkedTransferQueue.QNode pcurr;
/*     */     
/*     */     E nextItem;
/*     */     
/*     */     Itr() {
/* 653 */       findNext();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void findNext() {
/*     */       while (true) {
/* 662 */         LinkedTransferQueue.QNode pred = this.pnext;
/* 663 */         LinkedTransferQueue.QNode q = this.next;
/* 664 */         if (pred == null || pred == q) {
/* 665 */           pred = LinkedTransferQueue.this.traversalHead();
/* 666 */           q = pred.next;
/*     */         } 
/* 668 */         if (q == null || !q.isData) {
/* 669 */           this.next = null;
/*     */           return;
/*     */         } 
/* 672 */         Object x = q.get();
/* 673 */         LinkedTransferQueue.QNode s = q.next;
/* 674 */         if (x != null && q != x && q != s) {
/* 675 */           this.nextItem = (E)x;
/* 676 */           this.snext = s;
/* 677 */           this.pnext = pred;
/* 678 */           this.next = q;
/*     */           return;
/*     */         } 
/* 681 */         this.pnext = q;
/* 682 */         this.next = s;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 687 */       return (this.next != null);
/*     */     }
/*     */     
/*     */     public E next() {
/* 691 */       if (this.next == null) {
/* 692 */         throw new NoSuchElementException();
/*     */       }
/* 694 */       this.pcurr = this.pnext;
/* 695 */       this.curr = this.next;
/* 696 */       this.pnext = this.next;
/* 697 */       this.next = this.snext;
/* 698 */       E x = this.nextItem;
/* 699 */       findNext();
/* 700 */       return x;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 704 */       LinkedTransferQueue.QNode p = this.curr;
/* 705 */       if (p == null) {
/* 706 */         throw new IllegalStateException();
/*     */       }
/* 708 */       Object x = p.get();
/* 709 */       if (x != null && x != p && p.compareAndSet(x, p)) {
/* 710 */         LinkedTransferQueue.this.clean(this.pcurr, p);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public E peek() {
/*     */     while (true) {
/* 718 */       QNode h = traversalHead();
/* 719 */       QNode p = h.next;
/* 720 */       if (p == null) {
/* 721 */         return null;
/*     */       }
/* 723 */       Object x = p.get();
/* 724 */       if (p != x) {
/* 725 */         if (!p.isData) {
/* 726 */           return null;
/*     */         }
/* 728 */         if (x != null) {
/* 729 */           return (E)x;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*     */     while (true) {
/* 738 */       QNode h = traversalHead();
/* 739 */       QNode p = h.next;
/* 740 */       if (p == null) {
/* 741 */         return true;
/*     */       }
/* 743 */       Object x = p.get();
/* 744 */       if (p != x) {
/* 745 */         if (!p.isData) {
/* 746 */           return true;
/*     */         }
/* 748 */         if (x != null) {
/* 749 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasWaitingConsumer() {
/*     */     while (true) {
/* 757 */       QNode h = traversalHead();
/* 758 */       QNode p = h.next;
/* 759 */       if (p == null) {
/* 760 */         return false;
/*     */       }
/* 762 */       Object x = p.get();
/* 763 */       if (p != x) {
/* 764 */         return !p.isData;
/*     */       }
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
/*     */   public int size() {
/* 782 */     int count = 0;
/* 783 */     QNode h = traversalHead();
/* 784 */     for (QNode p = h.next; p != null && p.isData; p = p.next) {
/* 785 */       Object x = p.get();
/* 786 */       if (x != null && x != p && 
/* 787 */         ++count == Integer.MAX_VALUE) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 793 */     return count;
/*     */   }
/*     */   
/*     */   public int getWaitingConsumerCount() {
/* 797 */     int count = 0;
/* 798 */     QNode h = traversalHead();
/* 799 */     for (QNode p = h.next; p != null && !p.isData && (
/* 800 */       p.get() != null || 
/* 801 */       ++count != Integer.MAX_VALUE); p = p.next);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 806 */     return count;
/*     */   }
/*     */   
/*     */   public int remainingCapacity() {
/* 810 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/* 815 */     if (o == null) {
/* 816 */       return false;
/*     */     }
/*     */     label21: while (true) {
/* 819 */       QNode pred = traversalHead();
/*     */       while (true) {
/* 821 */         QNode q = pred.next;
/* 822 */         if (q == null || !q.isData) {
/* 823 */           return false;
/*     */         }
/* 825 */         if (q == pred) {
/*     */           continue label21;
/*     */         }
/*     */         
/* 829 */         Object x = q.get();
/* 830 */         if (x != null && x != q && o.equals(x) && q.compareAndSet(x, q)) {
/*     */           
/* 832 */           clean(pred, q);
/* 833 */           return true;
/*     */         } 
/* 835 */         pred = q;
/*     */       } 
/*     */       break;
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
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 849 */     throw new UnsupportedOperationException("serialization is not Not supported");
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
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 865 */     throw new UnsupportedOperationException("serialization is not Not supported");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\delivery\LinkedTransferQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */