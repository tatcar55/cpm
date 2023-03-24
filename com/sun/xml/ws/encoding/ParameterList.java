/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ParameterList
/*     */ {
/*     */   private final Map<String, String> list;
/*     */   
/*     */   ParameterList(String s) {
/*  70 */     HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     this.list = new HashMap<String, String>();
/*     */     while (true) {
/*  77 */       HeaderTokenizer.Token tk = h.next();
/*  78 */       int type = tk.getType();
/*     */       
/*  80 */       if (type == -4) {
/*     */         return;
/*     */       }
/*  83 */       if ((char)type == ';') {
/*     */         
/*  85 */         tk = h.next();
/*     */         
/*  87 */         if (tk.getType() == -4) {
/*     */           return;
/*     */         }
/*  90 */         if (tk.getType() != -1)
/*  91 */           throw new WebServiceException(); 
/*  92 */         String name = tk.getValue().toLowerCase();
/*     */ 
/*     */         
/*  95 */         tk = h.next();
/*  96 */         if ((char)tk.getType() != '=') {
/*  97 */           throw new WebServiceException();
/*     */         }
/*     */         
/* 100 */         tk = h.next();
/* 101 */         type = tk.getType();
/*     */         
/* 103 */         if (type != -1 && type != -2)
/*     */         {
/* 105 */           throw new WebServiceException();
/*     */         }
/* 107 */         this.list.put(name, tk.getValue()); continue;
/*     */       }  break;
/* 109 */     }  throw new WebServiceException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int size() {
/* 119 */     return this.list.size();
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
/*     */   String get(String name) {
/* 132 */     return this.list.get(name.trim().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Iterator<String> getNames() {
/* 143 */     return this.list.keySet().iterator();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\ParameterList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */