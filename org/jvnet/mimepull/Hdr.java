/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Hdr
/*     */   implements Header
/*     */ {
/*     */   String name;
/*     */   String line;
/*     */   
/*     */   Hdr(String l) {
/* 202 */     int i = l.indexOf(':');
/* 203 */     if (i < 0) {
/*     */       
/* 205 */       this.name = l.trim();
/*     */     } else {
/* 207 */       this.name = l.substring(0, i).trim();
/*     */     } 
/* 209 */     this.line = l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Hdr(String n, String v) {
/* 216 */     this.name = n;
/* 217 */     this.line = n + ": " + v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 225 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 233 */     int j, i = this.line.indexOf(':');
/* 234 */     if (i < 0) {
/* 235 */       return this.line;
/*     */     }
/*     */ 
/*     */     
/* 239 */     if (this.name.equalsIgnoreCase("Content-Description")) {
/*     */ 
/*     */       
/* 242 */       for (j = i + 1; j < this.line.length(); j++) {
/* 243 */         char c = this.line.charAt(j);
/* 244 */         if (c != '\t' && c != '\r' && c != '\n') {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 250 */       for (j = i + 1; j < this.line.length(); j++) {
/* 251 */         char c = this.line.charAt(j);
/* 252 */         if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 257 */     return this.line.substring(j);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\Hdr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */