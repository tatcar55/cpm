/*     */ package com.sun.xml.rpc.sp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ContentModelState
/*     */ {
/*     */   private ContentModel model;
/*     */   private boolean sawOne;
/*     */   private ContentModelState next;
/*     */   
/*     */   ContentModelState(ContentModel model) {
/*  54 */     this(model, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ContentModelState(Object content, ContentModelState next) {
/*  62 */     this.model = (ContentModel)content;
/*  63 */     this.next = next;
/*  64 */     this.sawOne = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean terminate() {
/*     */     ContentModel m;
/*  73 */     switch (this.model.type) {
/*     */       case '+':
/*  75 */         if (!this.sawOne && !this.model.empty()) {
/*  76 */           return false;
/*     */         }
/*     */       case '*':
/*     */       case '?':
/*  80 */         return (this.next == null || this.next.terminate());
/*     */       
/*     */       case '|':
/*  83 */         return (this.model.empty() && (this.next == null || this.next.terminate()));
/*     */ 
/*     */       
/*     */       case ',':
/*  87 */         for (m = this.model; m != null && m.empty(); m = m.next);
/*     */         
/*  89 */         if (m != null)
/*  90 */           return false; 
/*  91 */         return (this.next == null || this.next.terminate());
/*     */       
/*     */       case '\000':
/*  94 */         return false;
/*     */     } 
/*     */     
/*  97 */     throw new InternalError();
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
/*     */   ContentModelState advance(String token) throws EndOfInputException {
/*     */     ContentModel m;
/* 110 */     switch (this.model.type) {
/*     */       case '*':
/*     */       case '+':
/* 113 */         if (this.model.first(token)) {
/* 114 */           this.sawOne = true;
/* 115 */           if (this.model.content instanceof String)
/* 116 */             return this; 
/* 117 */           return (new ContentModelState(this.model.content, this)).advance(token);
/*     */         } 
/*     */         
/* 120 */         if ((this.model.type == '*' || this.sawOne) && this.next != null) {
/* 121 */           return this.next.advance(token);
/*     */         }
/*     */         break;
/*     */       case '?':
/* 125 */         if (this.model.first(token)) {
/* 126 */           if (this.model.content instanceof String)
/* 127 */             return this.next; 
/* 128 */           return (new ContentModelState(this.model.content, this.next)).advance(token);
/*     */         } 
/*     */         
/* 131 */         if (this.next != null) {
/* 132 */           return this.next.advance(token);
/*     */         }
/*     */         break;
/*     */       case '|':
/* 136 */         for (m = this.model; m != null; m = m.next) {
/* 137 */           if (m.content instanceof String) {
/* 138 */             if (token == m.content) {
/* 139 */               return this.next;
/*     */             }
/*     */           }
/* 142 */           else if (((ContentModel)m.content).first(token)) {
/* 143 */             return (new ContentModelState(m.content, this.next)).advance(token);
/*     */           } 
/*     */         } 
/* 146 */         if (this.model.empty() && this.next != null) {
/* 147 */           return this.next.advance(token);
/*     */         }
/*     */         break;
/*     */       case ',':
/* 151 */         if (this.model.first(token)) {
/*     */           ContentModelState nextState;
/*     */           
/* 154 */           if (this.model.type == '\000')
/* 155 */             return this.next; 
/* 156 */           if (this.model.next == null) {
/* 157 */             nextState = new ContentModelState(this.model.content, this.next);
/*     */           } else {
/* 159 */             nextState = new ContentModelState(this.model.content, this);
/* 160 */             this.model = this.model.next;
/*     */           } 
/* 162 */           return nextState.advance(token);
/* 163 */         }  if (this.model.empty() && this.next != null) {
/* 164 */           return this.next.advance(token);
/*     */         }
/*     */         break;
/*     */       
/*     */       case '\000':
/* 169 */         if (this.model.content == token) {
/* 170 */           return this.next;
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 176 */     throw new EndOfInputException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\ContentModelState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */