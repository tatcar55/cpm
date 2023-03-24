/*    */ package javanet.staxutils.events;
/*    */ 
/*    */ import javax.xml.stream.Location;
/*    */ import javax.xml.stream.events.ProcessingInstruction;
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
/*    */ public class ProcessingInstructionEvent
/*    */   extends AbstractXMLEvent
/*    */   implements ProcessingInstruction
/*    */ {
/*    */   protected String target;
/*    */   protected String data;
/*    */   
/*    */   public ProcessingInstructionEvent(String target, String data) {
/* 57 */     this.target = target;
/* 58 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ProcessingInstructionEvent(String target, String data, Location location) {
/* 65 */     super(location);
/* 66 */     this.target = target;
/* 67 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ProcessingInstructionEvent(ProcessingInstruction that) {
/* 73 */     super(that);
/* 74 */     this.target = that.getTarget();
/* 75 */     this.data = that.getData();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventType() {
/* 84 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTarget() {
/* 90 */     return this.target;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getData() {
/* 96 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\ProcessingInstructionEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */