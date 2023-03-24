/*     */ package javax.xml.bind.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValidationEventCollector
/*     */   implements ValidationEventHandler
/*     */ {
/*  67 */   private final List<ValidationEvent> events = new ArrayList<ValidationEvent>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidationEvent[] getEvents() {
/*  78 */     return this.events.<ValidationEvent>toArray(new ValidationEvent[this.events.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  85 */     this.events.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasEvents() {
/*  96 */     return !this.events.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean handleEvent(ValidationEvent event) {
/* 100 */     this.events.add(event);
/*     */     
/* 102 */     boolean retVal = true;
/* 103 */     switch (event.getSeverity())
/*     */     { case 0:
/* 105 */         retVal = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 120 */         return retVal;case 1: retVal = true; return retVal;case 2: retVal = false; return retVal; }  _assert(false, Messages.format("ValidationEventCollector.UnrecognizedSeverity", Integer.valueOf(event.getSeverity()))); return retVal;
/*     */   }
/*     */   
/*     */   private static void _assert(boolean b, String msg) {
/* 124 */     if (!b)
/* 125 */       throw new InternalError(msg); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bin\\util\ValidationEventCollector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */