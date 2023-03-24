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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ContentModel
/*     */ {
/*     */   public char type;
/*     */   public Object content;
/*     */   public ContentModel next;
/*  72 */   private SimpleHashtable cache = new SimpleHashtable();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentModel(String element) {
/*  78 */     this.type = Character.MIN_VALUE;
/*  79 */     this.content = element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentModel(char type, ContentModel content) {
/*  87 */     this.type = type;
/*  88 */     this.content = content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean empty() {
/*     */     ContentModel m;
/*  98 */     switch (this.type) {
/*     */       case '*':
/*     */       case '?':
/* 101 */         return true;
/*     */       
/*     */       case '\000':
/*     */       case '+':
/* 105 */         return false;
/*     */       
/*     */       case '|':
/* 108 */         if (this.content instanceof ContentModel && ((ContentModel)this.content).empty())
/*     */         {
/* 110 */           return true;
/*     */         }
/* 112 */         m = this.next;
/* 113 */         for (; m != null; 
/* 114 */           m = m.next) {
/* 115 */           if (m.empty())
/* 116 */             return true; 
/*     */         } 
/* 118 */         return false;
/*     */       
/*     */       case ',':
/* 121 */         if (this.content instanceof ContentModel) {
/* 122 */           if (!((ContentModel)this.content).empty()) {
/* 123 */             return false;
/*     */           }
/*     */         } else {
/* 126 */           return false;
/*     */         } 
/* 128 */         m = this.next;
/* 129 */         for (; m != null; 
/* 130 */           m = m.next) {
/* 131 */           if (!m.empty())
/* 132 */             return false; 
/*     */         } 
/* 134 */         return true;
/*     */     } 
/*     */     
/* 137 */     throw new InternalError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean first(String token) {
/*     */     boolean retval;
/* 146 */     Boolean b = (Boolean)this.cache.get(token);
/*     */ 
/*     */     
/* 149 */     if (b != null) {
/* 150 */       return b.booleanValue();
/*     */     }
/*     */     
/* 153 */     switch (this.type) {
/*     */       case '\000':
/*     */       case '*':
/*     */       case '+':
/*     */       case '?':
/* 158 */         if (this.content instanceof String) {
/* 159 */           boolean bool = (this.content == token); break;
/*     */         } 
/* 161 */         retval = ((ContentModel)this.content).first(token);
/*     */         break;
/*     */       
/*     */       case ',':
/* 165 */         if (this.content instanceof String) {
/* 166 */           retval = (this.content == token); break;
/* 167 */         }  if (((ContentModel)this.content).first(token)) {
/* 168 */           retval = true; break;
/* 169 */         }  if (!((ContentModel)this.content).empty()) {
/* 170 */           retval = false; break;
/* 171 */         }  if (this.next != null) {
/* 172 */           retval = this.next.first(token); break;
/*     */         } 
/* 174 */         retval = false;
/*     */         break;
/*     */       
/*     */       case '|':
/* 178 */         if (this.content instanceof String && this.content == token) {
/* 179 */           retval = true; break;
/* 180 */         }  if (((ContentModel)this.content).first(token)) {
/* 181 */           retval = true; break;
/* 182 */         }  if (this.next != null) {
/* 183 */           retval = this.next.first(token); break;
/*     */         } 
/* 185 */         retval = false;
/*     */         break;
/*     */       
/*     */       default:
/* 189 */         throw new InternalError();
/*     */     } 
/*     */ 
/*     */     
/* 193 */     if (retval) {
/* 194 */       this.cache.put(token, Boolean.TRUE);
/*     */     } else {
/* 196 */       this.cache.put(token, Boolean.FALSE);
/*     */     } 
/* 198 */     return retval;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\ContentModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */