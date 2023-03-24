/*      */ package com.sun.xml.ws.api.pipe;
/*      */ 
/*      */ import com.oracle.webservices.api.message.PropertySet;
/*      */ import com.sun.istack.NotNull;
/*      */ import com.sun.istack.Nullable;
/*      */ import com.sun.xml.ws.api.Cancelable;
/*      */ import com.sun.xml.ws.api.Component;
/*      */ import com.sun.xml.ws.api.ComponentRegistry;
/*      */ import com.sun.xml.ws.api.SOAPVersion;
/*      */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*      */ import com.sun.xml.ws.api.message.AddressingUtils;
/*      */ import com.sun.xml.ws.api.message.Packet;
/*      */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*      */ import com.sun.xml.ws.api.server.Container;
/*      */ import com.sun.xml.ws.api.server.ContainerResolver;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.CopyOnWriteArraySet;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.ws.Holder;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Fiber
/*      */   implements Runnable, Cancelable, ComponentRegistry
/*      */ {
/*  149 */   private final List<Listener> _listeners = new ArrayList<Listener>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addListener(Listener listener) {
/*  158 */     synchronized (this._listeners) {
/*  159 */       if (!this._listeners.contains(listener)) {
/*  160 */         this._listeners.add(listener);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeListener(Listener listener) {
/*  172 */     synchronized (this._listeners) {
/*  173 */       this._listeners.remove(listener);
/*      */     } 
/*      */   }
/*      */   
/*      */   List<Listener> getCurrentListeners() {
/*  178 */     synchronized (this._listeners) {
/*  179 */       return new ArrayList<Listener>(this._listeners);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void clearListeners() {
/*  184 */     synchronized (this._listeners) {
/*  185 */       this._listeners.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  193 */   private Tube[] conts = new Tube[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int contsSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Tube next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Packet packet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Throwable throwable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Engine owner;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  233 */   private volatile int suspendedCount = 0;
/*      */ 
/*      */   
/*      */   private volatile boolean isInsideSuspendCallbacks = false;
/*      */ 
/*      */   
/*      */   private boolean synchronous;
/*      */ 
/*      */   
/*      */   private boolean interrupted;
/*      */ 
/*      */   
/*      */   private final int id;
/*      */ 
/*      */   
/*      */   private List<FiberContextSwitchInterceptor> interceptors;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private ClassLoader contextClassLoader;
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private CompletionCallback completionCallback;
/*      */ 
/*      */   
/*      */   private boolean isDeliverThrowableInPacket = false;
/*      */   
/*      */   private Thread currentThread;
/*      */ 
/*      */   
/*      */   public void setDeliverThrowableInPacket(boolean isDeliverThrowableInPacket) {
/*  265 */     this.isDeliverThrowableInPacket = isDeliverThrowableInPacket;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  277 */   private final ReentrantLock lock = new ReentrantLock();
/*  278 */   private final Condition condition = this.lock.newCondition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile boolean isCanceled;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean started;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean startedSync;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void start(@NotNull Tube tubeline, @NotNull Packet request, @Nullable CompletionCallback completionCallback) {
/*  343 */     start(tubeline, request, completionCallback, false);
/*      */   }
/*      */   
/*      */   private void dumpFiberContext(String desc) {
/*  347 */     if (isTraceEnabled()) {
/*  348 */       String actionAndMsgDesc, tubeDesc, action = null;
/*  349 */       String msgId = null;
/*  350 */       if (this.packet != null) {
/*  351 */         for (SOAPVersion sv : SOAPVersion.values()) {
/*  352 */           for (AddressingVersion av : AddressingVersion.values()) {
/*  353 */             action = (this.packet.getMessage() != null) ? AddressingUtils.getAction(this.packet.getMessage().getMessageHeaders(), av, sv) : null;
/*  354 */             msgId = (this.packet.getMessage() != null) ? AddressingUtils.getMessageID(this.packet.getMessage().getMessageHeaders(), av, sv) : null;
/*  355 */             if (action != null || msgId != null) {
/*      */               break;
/*      */             }
/*      */           } 
/*  359 */           if (action != null || msgId != null) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  365 */       if (action == null && msgId == null) {
/*  366 */         actionAndMsgDesc = "NO ACTION or MSG ID";
/*      */       } else {
/*  368 */         actionAndMsgDesc = "'" + action + "' and msgId '" + msgId + "'";
/*      */       } 
/*      */ 
/*      */       
/*  372 */       if (this.next != null) {
/*  373 */         tubeDesc = this.next.toString() + ".processRequest()";
/*      */       } else {
/*  375 */         tubeDesc = peekCont() + ".processResponse()";
/*      */       } 
/*      */       
/*  378 */       LOGGER.log(Level.FINE, "{0} {1} with {2} and ''current'' tube {3} from thread {4} with Packet: {5}", new Object[] { getName(), desc, actionAndMsgDesc, tubeDesc, Thread.currentThread().getName(), (this.packet != null) ? this.packet.toShortString() : null });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void start(@NotNull Tube tubeline, @NotNull Packet request, @Nullable CompletionCallback completionCallback, boolean forceSync) {
/*  413 */     this.next = tubeline;
/*  414 */     this.packet = request;
/*  415 */     this.completionCallback = completionCallback;
/*      */     
/*  417 */     if (forceSync) {
/*  418 */       this.startedSync = true;
/*  419 */       dumpFiberContext("starting (sync)");
/*  420 */       run();
/*      */     } else {
/*  422 */       this.started = true;
/*  423 */       dumpFiberContext("starting (async)");
/*  424 */       this.owner.addRunnable(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Packet resumePacket) {
/*  452 */     resume(resumePacket, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Packet resumePacket, boolean forceSync) {
/*  467 */     resume(resumePacket, forceSync, (CompletionCallback)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Packet resumePacket, boolean forceSync, CompletionCallback callback) {
/*  478 */     this.lock.lock();
/*      */     try {
/*  480 */       if (callback != null) {
/*  481 */         setCompletionCallback(callback);
/*      */       }
/*  483 */       if (isTraceEnabled())
/*  484 */         LOGGER.log(Level.FINE, "{0} resuming. Will have suspendedCount={1}", new Object[] { getName(), Integer.valueOf(this.suspendedCount - 1) }); 
/*  485 */       this.packet = resumePacket;
/*  486 */       if (--this.suspendedCount == 0) {
/*  487 */         if (!this.isInsideSuspendCallbacks) {
/*  488 */           List<Listener> listeners = getCurrentListeners();
/*  489 */           for (Listener listener : listeners) {
/*      */             try {
/*  491 */               listener.fiberResumed(this);
/*  492 */             } catch (Throwable e) {
/*  493 */               if (isTraceEnabled()) {
/*  494 */                 LOGGER.log(Level.FINE, "Listener {0} threw exception: {1}", new Object[] { listener, e.getMessage() });
/*      */               }
/*      */             } 
/*      */           } 
/*  498 */           if (this.synchronous) {
/*  499 */             this.condition.signalAll();
/*  500 */           } else if (forceSync || this.startedSync) {
/*  501 */             run();
/*      */           } else {
/*  503 */             dumpFiberContext("resuming (async)");
/*  504 */             this.owner.addRunnable(this);
/*      */           }
/*      */         
/*      */         } 
/*  508 */       } else if (isTraceEnabled()) {
/*  509 */         LOGGER.log(Level.FINE, "{0} taking no action on resume because suspendedCount != 0: {1}", new Object[] { getName(), Integer.valueOf(this.suspendedCount) });
/*      */       } 
/*      */     } finally {
/*      */       
/*  513 */       this.lock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resumeAndReturn(@NotNull Packet resumePacket, boolean forceSync) {
/*  523 */     if (isTraceEnabled())
/*  524 */       LOGGER.log(Level.FINE, "{0} resumed with Return Packet", getName()); 
/*  525 */     this.next = null;
/*  526 */     resume(resumePacket, forceSync);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Throwable throwable) {
/*  546 */     resume(throwable, this.packet, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Throwable throwable, @NotNull Packet packet) {
/*  568 */     resume(throwable, packet, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Throwable error, boolean forceSync) {
/*  585 */     resume(error, this.packet, forceSync);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resume(@NotNull Throwable error, @NotNull Packet packet, boolean forceSync) {
/*  604 */     if (isTraceEnabled())
/*  605 */       LOGGER.log(Level.FINE, "{0} resumed with Return Throwable", getName()); 
/*  606 */     this.next = null;
/*  607 */     this.throwable = error;
/*  608 */     resume(packet, forceSync);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancel(boolean mayInterrupt) {
/*  619 */     this.isCanceled = true;
/*  620 */     if (mayInterrupt)
/*      */     {
/*  622 */       synchronized (this) {
/*  623 */         if (this.currentThread != null) {
/*  624 */           this.currentThread.interrupt();
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface Listener
/*      */   {
/*      */     void fiberSuspended(Fiber param1Fiber);
/*      */     
/*      */     void fiberResumed(Fiber param1Fiber);
/*      */   }
/*      */   
/*      */   private boolean suspend(Holder<Boolean> isRequireUnlock, Runnable onExitRunnable) {
/*  638 */     if (isTraceEnabled()) {
/*  639 */       LOGGER.log(Level.FINE, "{0} suspending. Will have suspendedCount={1}", new Object[] { getName(), Integer.valueOf(this.suspendedCount + 1) });
/*  640 */       if (this.suspendedCount > 0) {
/*  641 */         LOGGER.log(Level.FINE, "WARNING - {0} suspended more than resumed. Will require more than one resume to actually resume this fiber.", getName());
/*      */       }
/*      */     } 
/*      */     
/*  645 */     List<Listener> listeners = getCurrentListeners();
/*  646 */     if (++this.suspendedCount == 1) {
/*  647 */       this.isInsideSuspendCallbacks = true;
/*      */       try {
/*  649 */         for (Listener listener : listeners) {
/*      */           try {
/*  651 */             listener.fiberSuspended(this);
/*  652 */           } catch (Throwable e) {
/*  653 */             if (isTraceEnabled())
/*  654 */               LOGGER.log(Level.FINE, "Listener {0} threw exception: {1}", new Object[] { listener, e.getMessage() }); 
/*      */           } 
/*      */         } 
/*      */       } finally {
/*  658 */         this.isInsideSuspendCallbacks = false;
/*      */       } 
/*      */     } 
/*      */     
/*  662 */     if (this.suspendedCount <= 0) {
/*      */       
/*  664 */       for (Listener listener : listeners) {
/*      */         try {
/*  666 */           listener.fiberResumed(this);
/*  667 */         } catch (Throwable e) {
/*  668 */           if (isTraceEnabled()) {
/*  669 */             LOGGER.log(Level.FINE, "Listener {0} threw exception: {1}", new Object[] { listener, e.getMessage() });
/*      */           }
/*      */         } 
/*      */       } 
/*  673 */     } else if (onExitRunnable != null) {
/*      */       
/*  675 */       if (!this.synchronous) {
/*      */         
/*  677 */         synchronized (this) {
/*      */ 
/*      */           
/*  680 */           this.currentThread = null;
/*      */         } 
/*  682 */         this.lock.unlock();
/*  683 */         assert !this.lock.isHeldByCurrentThread();
/*  684 */         isRequireUnlock.value = (T)Boolean.FALSE;
/*      */         
/*      */         try {
/*  687 */           onExitRunnable.run();
/*  688 */         } catch (Throwable t) {
/*  689 */           throw new OnExitRunnableException(t);
/*      */         } 
/*      */         
/*  692 */         return true;
/*      */       } 
/*      */ 
/*      */       
/*  696 */       if (isTraceEnabled())
/*  697 */         LOGGER.fine("onExitRunnable used with synchronous Fiber execution -- not exiting current thread"); 
/*  698 */       onExitRunnable.run();
/*      */     } 
/*      */ 
/*      */     
/*  702 */     return false;
/*      */   }
/*      */   
/*      */   public static interface CompletionCallback {
/*      */     void onCompletion(@NotNull Packet param1Packet);
/*      */     
/*      */     void onCompletion(@NotNull Throwable param1Throwable); }
/*      */   
/*      */   private static final class OnExitRunnableException extends RuntimeException { public OnExitRunnableException(Throwable target) {
/*  711 */       super((Throwable)null);
/*  712 */       this.target = target;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Throwable target; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addInterceptor(@NotNull FiberContextSwitchInterceptor interceptor) {
/*  736 */     if (this.interceptors == null) {
/*  737 */       this.interceptors = new ArrayList<FiberContextSwitchInterceptor>();
/*      */     } else {
/*  739 */       List<FiberContextSwitchInterceptor> l = new ArrayList<FiberContextSwitchInterceptor>();
/*  740 */       l.addAll(this.interceptors);
/*  741 */       this.interceptors = l;
/*      */     } 
/*  743 */     this.interceptors.add(interceptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean removeInterceptor(@NotNull FiberContextSwitchInterceptor interceptor) {
/*  770 */     if (this.interceptors != null) {
/*  771 */       boolean result = this.interceptors.remove(interceptor);
/*  772 */       if (this.interceptors.isEmpty()) {
/*  773 */         this.interceptors = null;
/*      */       } else {
/*  775 */         List<FiberContextSwitchInterceptor> l = new ArrayList<FiberContextSwitchInterceptor>();
/*  776 */         l.addAll(this.interceptors);
/*  777 */         this.interceptors = l;
/*      */       } 
/*  779 */       return result;
/*      */     } 
/*  781 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ClassLoader getContextClassLoader() {
/*  790 */     return this.contextClassLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassLoader setContextClassLoader(@Nullable ClassLoader contextClassLoader) {
/*  797 */     ClassLoader r = this.contextClassLoader;
/*  798 */     this.contextClassLoader = contextClassLoader;
/*  799 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void run() {
/*  809 */     Container old = ContainerResolver.getDefault().enterContainer(this.owner.getContainer());
/*      */     try {
/*  811 */       assert !this.synchronous;
/*      */       
/*  813 */       if (!doRun()) {
/*  814 */         if (this.startedSync && this.suspendedCount == 0 && (this.next != null || this.contsSize > 0)) {
/*      */ 
/*      */ 
/*      */           
/*  818 */           this.startedSync = false;
/*      */           
/*  820 */           dumpFiberContext("restarting (async) after startSync");
/*  821 */           this.owner.addRunnable(this);
/*      */         } else {
/*  823 */           completionCheck();
/*      */         } 
/*      */       }
/*      */     } finally {
/*  827 */       ContainerResolver.getDefault().exitContainer(old);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public Packet runSync(@NotNull Tube tubeline, @NotNull Packet request) {
/*  860 */     this.lock.lock();
/*      */     
/*      */     try {
/*  863 */       Tube[] oldCont = this.conts;
/*  864 */       int oldContSize = this.contsSize;
/*  865 */       boolean oldSynchronous = this.synchronous;
/*  866 */       Tube oldNext = this.next;
/*      */       
/*  868 */       if (oldContSize > 0) {
/*  869 */         this.conts = new Tube[16];
/*  870 */         this.contsSize = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  906 */       this.lock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void completionCheck() {
/*  911 */     this.lock.lock();
/*      */     
/*      */     try {
/*  914 */       if (!this.isCanceled && this.contsSize == 0 && this.suspendedCount == 0) {
/*  915 */         if (isTraceEnabled())
/*  916 */           LOGGER.log(Level.FINE, "{0} completed", getName()); 
/*  917 */         clearListeners();
/*  918 */         this.condition.signalAll();
/*  919 */         if (this.completionCallback != null)
/*  920 */           if (this.throwable != null)
/*  921 */           { if (this.isDeliverThrowableInPacket) {
/*  922 */               this.packet.addSatellite((PropertySet)new ThrowableContainerPropertySet(this.throwable));
/*  923 */               this.completionCallback.onCompletion(this.packet);
/*      */             } else {
/*  925 */               this.completionCallback.onCompletion(this.throwable);
/*      */             }  }
/*  927 */           else { this.completionCallback.onCompletion(this.packet); }
/*      */            
/*      */       } 
/*      */     } finally {
/*  931 */       this.lock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class InterceptorHandler
/*      */     implements FiberContextSwitchInterceptor.Work<Tube, Tube>
/*      */   {
/*      */     private final Holder<Boolean> isUnlockRequired;
/*      */ 
/*      */     
/*      */     private final List<FiberContextSwitchInterceptor> ints;
/*      */     
/*      */     private int idx;
/*      */ 
/*      */     
/*      */     public InterceptorHandler(Holder<Boolean> isUnlockRequired, List<FiberContextSwitchInterceptor> ints) {
/*  949 */       this.isUnlockRequired = isUnlockRequired;
/*  950 */       this.ints = ints;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Tube invoke(Tube next) {
/*  957 */       this.idx = 0;
/*  958 */       return execute(next);
/*      */     }
/*      */ 
/*      */     
/*      */     public Tube execute(Tube next) {
/*  963 */       if (this.idx == this.ints.size()) {
/*  964 */         Fiber.this.next = next;
/*  965 */         if (Fiber.this.__doRun(this.isUnlockRequired, this.ints))
/*  966 */           return (Tube)Fiber.PLACEHOLDER; 
/*      */       } else {
/*  968 */         FiberContextSwitchInterceptor interceptor = this.ints.get(this.idx++);
/*  969 */         return interceptor.<Tube, Tube>execute(Fiber.this, next, this);
/*      */       } 
/*  971 */       return Fiber.this.next;
/*      */     }
/*      */   }
/*      */   
/*  975 */   private static final PlaceholderTube PLACEHOLDER = new PlaceholderTube();
/*      */   
/*      */   private static class PlaceholderTube extends AbstractTubeImpl {
/*      */     private PlaceholderTube() {}
/*      */     
/*      */     public NextAction processRequest(Packet request) {
/*  981 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public NextAction processResponse(Packet response) {
/*  986 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public NextAction processException(Throwable t) {
/*  991 */       return doThrow(t);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void preDestroy() {}
/*      */ 
/*      */     
/*      */     public PlaceholderTube copy(TubeCloner cloner) {
/* 1000 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean doRun() {
/* 1009 */     dumpFiberContext("running");
/*      */     
/* 1011 */     if (serializeExecution) {
/* 1012 */       serializedExecutionLock.lock();
/*      */       try {
/* 1014 */         return _doRun(this.next);
/*      */       } finally {
/* 1016 */         serializedExecutionLock.unlock();
/*      */       } 
/*      */     } 
/* 1019 */     return _doRun(this.next);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _doRun(Tube next) {
/* 1025 */     Holder<Boolean> isRequireUnlock = new Holder<Boolean>(Boolean.TRUE);
/* 1026 */     this.lock.lock();
/*      */     try {
/*      */       List<FiberContextSwitchInterceptor> ints;
/*      */       ClassLoader old;
/* 1030 */       synchronized (this) {
/* 1031 */         ints = this.interceptors;
/*      */ 
/*      */ 
/*      */         
/* 1035 */         this.currentThread = Thread.currentThread();
/* 1036 */         if (isTraceEnabled()) {
/* 1037 */           LOGGER.log(Level.FINE, "Thread entering _doRun(): {0}", this.currentThread);
/*      */         }
/*      */         
/* 1040 */         old = this.currentThread.getContextClassLoader();
/* 1041 */         this.currentThread.setContextClassLoader(this.contextClassLoader);
/*      */       } 
/*      */       
/*      */       try {
/* 1045 */         boolean needsToReenter = false;
/*      */         
/*      */         do {
/* 1048 */           if (ints == null) {
/* 1049 */             this.next = next;
/* 1050 */             if (__doRun(isRequireUnlock, ints)) {
/* 1051 */               return true;
/*      */             }
/*      */           } else {
/* 1054 */             next = (new InterceptorHandler(isRequireUnlock, ints)).invoke(next);
/* 1055 */             if (next == PLACEHOLDER) {
/* 1056 */               return true;
/*      */             }
/*      */           } 
/*      */           
/* 1060 */           synchronized (this) {
/* 1061 */             needsToReenter = (ints != this.interceptors);
/* 1062 */             if (needsToReenter)
/* 1063 */               ints = this.interceptors; 
/*      */           } 
/* 1065 */         } while (needsToReenter);
/* 1066 */       } catch (OnExitRunnableException o) {
/*      */ 
/*      */         
/* 1069 */         Throwable t = o.target;
/* 1070 */         if (t instanceof WebServiceException)
/* 1071 */           throw (WebServiceException)t; 
/* 1072 */         throw new WebServiceException(t);
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/* 1077 */         Thread thread = Thread.currentThread();
/* 1078 */         thread.setContextClassLoader(old);
/* 1079 */         if (isTraceEnabled()) {
/* 1080 */           LOGGER.log(Level.FINE, "Thread leaving _doRun(): {0}", thread);
/*      */         }
/*      */       } 
/*      */       
/* 1084 */       return false;
/*      */     } finally {
/* 1086 */       if (((Boolean)isRequireUnlock.value).booleanValue()) {
/* 1087 */         synchronized (this) {
/* 1088 */           this.currentThread = null;
/*      */         } 
/* 1090 */         this.lock.unlock();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean __doRun(Holder<Boolean> isRequireUnlock, List<FiberContextSwitchInterceptor> originalInterceptors) {
/* 1101 */     assert this.lock.isHeldByCurrentThread();
/*      */     
/* 1103 */     Fiber old = CURRENT_FIBER.get();
/* 1104 */     CURRENT_FIBER.set(this);
/*      */ 
/*      */     
/* 1107 */     boolean traceEnabled = LOGGER.isLoggable(Level.FINER);
/*      */     
/*      */     try {
/* 1110 */       boolean abortResponse = false;
/* 1111 */       while (isReady(originalInterceptors)) {
/* 1112 */         if (this.isCanceled) {
/* 1113 */           this.next = null;
/* 1114 */           this.throwable = null;
/* 1115 */           this.contsSize = 0;
/*      */           
/*      */           break;
/*      */         } 
/*      */         try {
/*      */           NextAction na;
/*      */           Tube last;
/* 1122 */           if (this.throwable != null) {
/* 1123 */             if (this.contsSize == 0 || abortResponse) {
/* 1124 */               this.contsSize = 0;
/*      */               
/* 1126 */               return false;
/*      */             } 
/* 1128 */             last = popCont();
/* 1129 */             if (traceEnabled)
/* 1130 */               LOGGER.log(Level.FINER, "{0} {1}.processException({2})", new Object[] { getName(), last, this.throwable }); 
/* 1131 */             na = last.processException(this.throwable);
/*      */           }
/* 1133 */           else if (this.next != null) {
/* 1134 */             if (traceEnabled)
/* 1135 */               LOGGER.log(Level.FINER, "{0} {1}.processRequest({2})", new Object[] { getName(), this.next, (this.packet != null) ? ("Packet@" + Integer.toHexString(this.packet.hashCode())) : "null" }); 
/* 1136 */             na = this.next.processRequest(this.packet);
/* 1137 */             last = this.next;
/*      */           } else {
/* 1139 */             if (this.contsSize == 0 || abortResponse) {
/*      */               
/* 1141 */               this.contsSize = 0;
/* 1142 */               return false;
/*      */             } 
/* 1144 */             last = popCont();
/* 1145 */             if (traceEnabled)
/* 1146 */               LOGGER.log(Level.FINER, "{0} {1}.processResponse({2})", new Object[] { getName(), last, (this.packet != null) ? ("Packet@" + Integer.toHexString(this.packet.hashCode())) : "null" }); 
/* 1147 */             na = last.processResponse(this.packet);
/*      */           } 
/*      */ 
/*      */           
/* 1151 */           if (traceEnabled) {
/* 1152 */             LOGGER.log(Level.FINER, "{0} {1} returned with {2}", new Object[] { getName(), last, na });
/*      */           }
/*      */ 
/*      */           
/* 1156 */           if (na.kind != 4) {
/*      */             
/* 1158 */             if (na.kind != 3 && na.kind != 5)
/*      */             {
/* 1160 */               this.packet = na.packet; } 
/* 1161 */             this.throwable = na.throwable;
/*      */           } 
/*      */           
/* 1164 */           switch (na.kind) {
/*      */             case 0:
/*      */             case 7:
/* 1167 */               pushCont(last);
/*      */             
/*      */             case 1:
/* 1170 */               this.next = na.next;
/* 1171 */               if (na.kind == 7 && this.startedSync)
/*      */               {
/*      */                 
/* 1174 */                 return false;
/*      */               }
/*      */               break;
/*      */             case 5:
/*      */             case 6:
/* 1179 */               abortResponse = true;
/* 1180 */               if (isTraceEnabled()) {
/* 1181 */                 LOGGER.log(Level.FINE, "Fiber {0} is aborting a response due to exception: {1}", new Object[] { this, na.throwable });
/*      */               }
/*      */             case 2:
/*      */             case 3:
/* 1185 */               this.next = null;
/*      */               break;
/*      */             case 4:
/* 1188 */               if (this.next != null)
/*      */               {
/*      */                 
/* 1191 */                 pushCont(last);
/*      */               }
/* 1193 */               this.next = na.next;
/* 1194 */               if (suspend(isRequireUnlock, na.onExitRunnable))
/* 1195 */                 return true; 
/*      */               break;
/*      */             default:
/* 1198 */               throw new AssertionError();
/*      */           } 
/* 1200 */         } catch (RuntimeException t) {
/* 1201 */           if (traceEnabled)
/* 1202 */             LOGGER.log(Level.FINER, getName() + " Caught " + t + ". Start stack unwinding", t); 
/* 1203 */           this.throwable = t;
/* 1204 */         } catch (Error t) {
/* 1205 */           if (traceEnabled)
/* 1206 */             LOGGER.log(Level.FINER, getName() + " Caught " + t + ". Start stack unwinding", t); 
/* 1207 */           this.throwable = t;
/*      */         } 
/*      */         
/* 1210 */         dumpFiberContext("After tube execution");
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 1217 */       CURRENT_FIBER.set(old);
/*      */     } 
/*      */     
/* 1220 */     return false;
/*      */   }
/*      */   
/*      */   private void pushCont(Tube tube) {
/* 1224 */     this.conts[this.contsSize++] = tube;
/*      */ 
/*      */     
/* 1227 */     int len = this.conts.length;
/* 1228 */     if (this.contsSize == len) {
/* 1229 */       Tube[] newBuf = new Tube[len * 2];
/* 1230 */       System.arraycopy(this.conts, 0, newBuf, 0, len);
/* 1231 */       this.conts = newBuf;
/*      */     } 
/*      */   }
/*      */   
/*      */   private Tube popCont() {
/* 1236 */     return this.conts[--this.contsSize];
/*      */   }
/*      */   
/*      */   private Tube peekCont() {
/* 1240 */     int index = this.contsSize - 1;
/* 1241 */     if (index >= 0 && index < this.conts.length) {
/* 1242 */       return this.conts[index];
/*      */     }
/* 1244 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetCont(Tube[] conts, int contsSize) {
/* 1253 */     this.conts = conts;
/* 1254 */     this.contsSize = contsSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isReady(List<FiberContextSwitchInterceptor> originalInterceptors) {
/* 1261 */     if (this.synchronous) {
/* 1262 */       while (this.suspendedCount == 1) {
/*      */         try {
/* 1264 */           if (isTraceEnabled()) {
/* 1265 */             LOGGER.log(Level.FINE, "{0} is blocking thread {1}", new Object[] { getName(), Thread.currentThread().getName() });
/*      */           }
/* 1267 */           this.condition.await();
/* 1268 */         } catch (InterruptedException e) {
/*      */ 
/*      */ 
/*      */           
/* 1272 */           this.interrupted = true;
/*      */         } 
/*      */       } 
/* 1275 */       synchronized (this) {
/* 1276 */         return (this.interceptors == originalInterceptors);
/*      */       } 
/*      */     } 
/*      */     
/* 1280 */     if (this.suspendedCount > 0)
/* 1281 */       return false; 
/* 1282 */     synchronized (this) {
/* 1283 */       return (this.interceptors == originalInterceptors);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private String getName() {
/* 1289 */     return "engine-" + this.owner.id + "fiber-" + this.id;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1294 */     return getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Packet getPacket() {
/* 1306 */     return this.packet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletionCallback getCompletionCallback() {
/* 1315 */     return this.completionCallback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCompletionCallback(CompletionCallback completionCallback) {
/* 1324 */     this.completionCallback = completionCallback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSynchronous() {
/* 1350 */     return (current()).synchronous;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStartedSync() {
/* 1363 */     return this.startedSync;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NotNull
/*      */   public static Fiber current() {
/* 1376 */     Fiber fiber = CURRENT_FIBER.get();
/* 1377 */     if (fiber == null)
/* 1378 */       throw new IllegalStateException("Can be only used from fibers"); 
/* 1379 */     return fiber;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Fiber getCurrentIfSet() {
/* 1386 */     return CURRENT_FIBER.get();
/*      */   }
/*      */   
/* 1389 */   private static final ThreadLocal<Fiber> CURRENT_FIBER = new ThreadLocal<Fiber>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1394 */   private static final AtomicInteger iotaGen = new AtomicInteger();
/*      */   
/*      */   private static boolean isTraceEnabled() {
/* 1397 */     return LOGGER.isLoggable(Level.FINE);
/*      */   }
/*      */   
/* 1400 */   private static final Logger LOGGER = Logger.getLogger(Fiber.class.getName());
/*      */ 
/*      */   
/* 1403 */   private static final ReentrantLock serializedExecutionLock = new ReentrantLock();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1409 */   public static volatile boolean serializeExecution = Boolean.getBoolean(Fiber.class.getName() + ".serialize"); private final Set<Component> components;
/*      */   
/* 1411 */   Fiber(Engine engine) { this.components = new CopyOnWriteArraySet<Component>(); this.owner = engine;
/*      */     this.id = iotaGen.incrementAndGet();
/*      */     if (isTraceEnabled())
/*      */       LOGGER.log(Level.FINE, "{0} created", getName()); 
/* 1415 */     this.contextClassLoader = Thread.currentThread().getContextClassLoader(); } public <S> S getSPI(Class<S> spiType) { for (Component c : this.components) {
/* 1416 */       S spi = (S)c.getSPI(spiType);
/* 1417 */       if (spi != null) {
/* 1418 */         return spi;
/*      */       }
/*      */     } 
/* 1421 */     return null; }
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Component> getComponents() {
/* 1426 */     return this.components;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\Fiber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */