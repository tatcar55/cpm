/*    */ package com.sun.xml.ws.rx.rm.runtime.delivery;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.rx.rm.runtime.RmConfiguration;
/*    */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
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
/*    */ public final class DeliveryQueueBuilder
/*    */ {
/*    */   @NotNull
/*    */   private final RmConfiguration configuration;
/*    */   @NotNull
/*    */   private final Postman postman;
/*    */   @NotNull
/*    */   private final Postman.Callback deliveryCallback;
/*    */   private Sequence sequence;
/*    */   
/*    */   public static DeliveryQueueBuilder getBuilder(@NotNull RmConfiguration configuration, @NotNull Postman postman, @NotNull Postman.Callback deliveryCallback) {
/* 61 */     return new DeliveryQueueBuilder(configuration, postman, deliveryCallback);
/*    */   }
/*    */   
/*    */   private DeliveryQueueBuilder(@NotNull RmConfiguration configuration, @NotNull Postman postman, @NotNull Postman.Callback deliveryCallback) {
/* 65 */     assert configuration != null;
/* 66 */     assert postman != null;
/* 67 */     assert deliveryCallback != null;
/*    */     
/* 69 */     this.configuration = configuration;
/* 70 */     this.postman = postman;
/* 71 */     this.deliveryCallback = deliveryCallback;
/*    */   }
/*    */   
/*    */   public void sequence(Sequence sequence) {
/* 75 */     this.sequence = sequence;
/*    */   }
/*    */   
/*    */   public DeliveryQueue build() {
/* 79 */     if (this.configuration.getRmFeature().isOrderedDeliveryEnabled()) {
/* 80 */       return new InOrderDeliveryQueue(this.postman, this.deliveryCallback, this.sequence, this.configuration.getRmFeature().getDestinationBufferQuota());
/*    */     }
/* 82 */     return new SimpleDeliveryQueue(this.postman, this.deliveryCallback);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\delivery\DeliveryQueueBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */