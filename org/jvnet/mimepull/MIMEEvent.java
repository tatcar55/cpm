/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MIMEEvent
/*     */ {
/*     */   enum EVENT_TYPE
/*     */   {
/*  50 */     START_MESSAGE, START_PART, HEADERS, CONTENT, END_PART, END_MESSAGE;
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
/*  71 */   static final StartMessage START_MESSAGE = new StartMessage();
/*  72 */   static final StartPart START_PART = new StartPart();
/*  73 */   static final EndPart END_PART = new EndPart(); abstract EVENT_TYPE getEventType();
/*  74 */   static final EndMessage END_MESSAGE = new EndMessage();
/*     */   
/*     */   static final class StartMessage extends MIMEEvent {
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/*  78 */       return MIMEEvent.EVENT_TYPE.START_MESSAGE;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class StartPart extends MIMEEvent {
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/*  84 */       return MIMEEvent.EVENT_TYPE.START_PART;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class EndPart extends MIMEEvent {
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/*  90 */       return MIMEEvent.EVENT_TYPE.END_PART;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Headers extends MIMEEvent {
/*     */     InternetHeaders ih;
/*     */     
/*     */     Headers(InternetHeaders ih) {
/*  98 */       this.ih = ih;
/*     */     }
/*     */     
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/* 102 */       return MIMEEvent.EVENT_TYPE.HEADERS;
/*     */     }
/*     */     
/*     */     InternetHeaders getHeaders() {
/* 106 */       return this.ih;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class Content extends MIMEEvent {
/*     */     private final ByteBuffer buf;
/*     */     
/*     */     Content(ByteBuffer buf) {
/* 114 */       this.buf = buf;
/*     */     }
/*     */     
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/* 118 */       return MIMEEvent.EVENT_TYPE.CONTENT;
/*     */     }
/*     */     
/*     */     ByteBuffer getData() {
/* 122 */       return this.buf;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class EndMessage extends MIMEEvent {
/*     */     MIMEEvent.EVENT_TYPE getEventType() {
/* 128 */       return MIMEEvent.EVENT_TYPE.END_MESSAGE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\MIMEEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */