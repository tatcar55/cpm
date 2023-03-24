/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Event
/*     */ {
/*  36 */   public int state = 6;
/*     */   
/*     */   public String name;
/*     */   public String value;
/*     */   public String uri;
/*     */   public int line;
/*     */   
/*     */   public Event() {}
/*     */   
/*     */   public Event(int s, String n, String v, String u) {
/*  46 */     this(s, n, v, u, -1);
/*     */   }
/*     */   
/*     */   public Event(int s, String n, String v, String u, int i) {
/*  50 */     this.state = s;
/*  51 */     this.name = n;
/*  52 */     this.value = v;
/*  53 */     this.uri = u;
/*  54 */     this.line = i;
/*     */   }
/*     */   
/*     */   public Event(Event e) {
/*  58 */     from(e);
/*     */   }
/*     */   
/*     */   public void from(Event e) {
/*  62 */     this.state = e.state;
/*  63 */     this.name = e.name;
/*  64 */     this.value = e.value;
/*  65 */     this.uri = e.uri;
/*  66 */     this.line = e.line;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  71 */     return "Event(" + getStateName() + ", " + this.name + ", " + this.value + ", " + this.uri + ", " + this.line + ")";
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
/*     */   protected String getStateName() {
/*  85 */     switch (this.state) {
/*     */       case 0:
/*  87 */         return "start";
/*     */       case 1:
/*  89 */         return "end";
/*     */       case 2:
/*  91 */         return "attr";
/*     */       case 3:
/*  93 */         return "chars";
/*     */       case 4:
/*  95 */         return "iws";
/*     */       case 5:
/*  97 */         return "pi";
/*     */       case 6:
/*  99 */         return "at_end";
/*     */     } 
/* 101 */     return "unknown";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\Event.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */