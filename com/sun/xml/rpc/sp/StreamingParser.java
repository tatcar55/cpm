/*     */ package com.sun.xml.rpc.sp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StreamingParser
/*     */ {
/*     */   public static final int START = 0;
/*     */   public static final int END = 1;
/*     */   public static final int ATTR = 2;
/*     */   public static final int CHARS = 3;
/*     */   public static final int IWS = 4;
/*     */   public static final int PI = 5;
/*     */   
/*     */   public abstract int parse() throws ParseException, IOException;
/*     */   
/*     */   public abstract int state();
/*     */   
/*     */   public abstract String name();
/*     */   
/*     */   public abstract String value();
/*     */   
/*     */   public abstract String uriString();
/*     */   
/*     */   public abstract int line();
/*     */   
/*     */   public abstract int column();
/*     */   
/*     */   public abstract String publicId();
/*     */   
/*     */   public abstract String systemId();
/*     */   
/*     */   public abstract boolean isValidating();
/*     */   
/*     */   public abstract boolean isCoalescing();
/*     */   
/*     */   public abstract boolean isNamespaceAware();
/*     */   
/*     */   private static void quote(StringBuffer sb, String s, int max) {
/* 267 */     boolean needDots = false;
/* 268 */     int limit = Math.min(s.length(), max);
/* 269 */     if (limit > max - 3) {
/* 270 */       needDots = true;
/* 271 */       limit = max - 3;
/*     */     } 
/* 273 */     sb.append('"');
/* 274 */     for (int i = 0; i < limit; i++) {
/* 275 */       char c = s.charAt(i);
/* 276 */       if (c < ' ' || c > '~') {
/* 277 */         if (c <= 'ÿ') {
/* 278 */           if (c == '\n') {
/* 279 */             sb.append("\\n");
/*     */           
/*     */           }
/* 282 */           else if (c == '\r') {
/* 283 */             sb.append("\\r");
/*     */           } else {
/*     */             
/* 286 */             sb.append("\\x");
/* 287 */             if (c < '\020')
/* 288 */               sb.append('0'); 
/* 289 */             sb.append(Integer.toHexString(c));
/*     */           }
/*     */         
/* 292 */         } else if (c == '"') {
/* 293 */           sb.append("\\\"");
/*     */         } else {
/*     */           
/* 296 */           sb.append("\\u");
/* 297 */           String n = Integer.toHexString(c);
/* 298 */           for (int j = n.length(); j < 4; j++)
/* 299 */             sb.append('0'); 
/* 300 */           sb.append(n);
/*     */         } 
/*     */       } else {
/* 303 */         sb.append(c);
/*     */       } 
/* 305 */     }  if (needDots)
/* 306 */       sb.append("..."); 
/* 307 */     sb.append('"');
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String describe(int state, String name, String value, boolean articleNeeded) {
/* 338 */     StringBuffer sb = new StringBuffer();
/* 339 */     switch (state) {
/*     */       case 0:
/* 341 */         if (articleNeeded)
/* 342 */           sb.append("a "); 
/* 343 */         sb.append("start tag");
/* 344 */         if (name != null) {
/* 345 */           sb.append(" for a \"" + name + "\" element");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 397 */         return sb.toString();case 1: if (articleNeeded) sb.append("an ");  sb.append("end tag"); if (name != null) sb.append(" for a \"" + name + "\" element");  return sb.toString();case 2: if (name == null) { if (articleNeeded) sb.append("an ");  sb.append("attribute"); } else { if (articleNeeded) sb.append("the ");  sb.append("attribute \"" + name + "\""); if (value != null) sb.append(" with value \"" + value + "\"");  }  return sb.toString();case 3: if (articleNeeded) sb.append("some ");  sb.append("character data"); if (value != null) { sb.append(": "); quote(sb, value, 40); }  return sb.toString();case 4: if (articleNeeded) sb.append("some ");  sb.append("ignorable whitespace"); return sb.toString();case 5: if (articleNeeded) sb.append("a ");  sb.append("processing instruction"); if (name != null) sb.append(" with target \"" + name + "\"");  return sb.toString();case -1: if (articleNeeded) sb.append("the ");  sb.append("end of the document"); return sb.toString();
/*     */     } 
/*     */     throw new InternalError("Unknown parser state");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String describe(boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 413 */     StringBuffer sb = new StringBuffer("[StreamingParser");
/* 414 */     if (systemId() != null)
/* 415 */       sb.append(" " + systemId()); 
/* 416 */     sb.append(": " + describe(false));
/* 417 */     sb.append("]");
/* 418 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\StreamingParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */