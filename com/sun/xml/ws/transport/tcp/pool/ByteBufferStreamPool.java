/*    */ package com.sun.xml.ws.transport.tcp.pool;
/*    */ 
/*    */ import com.sun.xml.ws.util.Pool;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ByteBufferStreamPool<T extends LifeCycle>
/*    */ {
/* 52 */   private final Pool<T> pool = new Pool<T>() { protected T create() {
/*    */         LifeCycle lifeCycle;
/* 54 */         T member = null;
/*    */         try {
/* 56 */           lifeCycle = (LifeCycle)ByteBufferStreamPool.this.create(memberClass);
/* 57 */         } catch (Exception e) {}
/*    */ 
/*    */         
/* 60 */         return (T)lifeCycle;
/*    */       } }
/*    */   ;
/*    */ 
/*    */   
/*    */   private T create(Class<T> memberClass) throws InstantiationException, IllegalAccessException {
/* 66 */     return memberClass.newInstance();
/*    */   }
/*    */   
/*    */   public T take() {
/* 70 */     LifeCycle lifeCycle = (LifeCycle)this.pool.take();
/* 71 */     lifeCycle.activate();
/* 72 */     return (T)lifeCycle;
/*    */   }
/*    */   
/*    */   public void release(T member) {
/* 76 */     member.passivate();
/* 77 */     this.pool.recycle(member);
/*    */   }
/*    */   
/*    */   public ByteBufferStreamPool(final Class<T> memberClass) {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\pool\ByteBufferStreamPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */