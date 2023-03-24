/*     */ package com.sun.xml.messaging.saaj.packaging.mime.internet;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ParameterList
/*     */ {
/*     */   private final HashMap list;
/*     */   
/*     */   public ParameterList() {
/*  68 */     this.list = new HashMap<Object, Object>();
/*     */   }
/*     */   
/*     */   private ParameterList(HashMap m) {
/*  72 */     this.list = m;
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
/*     */   public ParameterList(String s) throws ParseException {
/*  86 */     HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     this.list = new HashMap<Object, Object>();
/*     */     while (true) {
/*  93 */       HeaderTokenizer.Token tk = h.next();
/*  94 */       int type = tk.getType();
/*     */       
/*  96 */       if (type == -4) {
/*     */         return;
/*     */       }
/*  99 */       if ((char)type == ';') {
/*     */         
/* 101 */         tk = h.next();
/*     */         
/* 103 */         if (tk.getType() == -4) {
/*     */           return;
/*     */         }
/* 106 */         if (tk.getType() != -1)
/* 107 */           throw new ParseException(); 
/* 108 */         String name = tk.getValue().toLowerCase();
/*     */ 
/*     */         
/* 111 */         tk = h.next();
/* 112 */         if ((char)tk.getType() != '=') {
/* 113 */           throw new ParseException();
/*     */         }
/*     */         
/* 116 */         tk = h.next();
/* 117 */         type = tk.getType();
/*     */         
/* 119 */         if (type != -1 && type != -2)
/*     */         {
/* 121 */           throw new ParseException();
/*     */         }
/* 123 */         this.list.put(name, tk.getValue()); continue;
/*     */       }  break;
/* 125 */     }  throw new ParseException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 135 */     return this.list.size();
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
/*     */   public String get(String name) {
/* 148 */     return (String)this.list.get(name.trim().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String name, String value) {
/* 159 */     this.list.put(name.trim().toLowerCase(), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(String name) {
/* 169 */     this.list.remove(name.trim().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getNames() {
/* 179 */     return this.list.keySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 190 */     return toString(0);
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
/*     */   public String toString(int used) {
/* 208 */     StringBuffer sb = new StringBuffer();
/* 209 */     Iterator<Map.Entry> itr = this.list.entrySet().iterator();
/*     */     
/* 211 */     while (itr.hasNext()) {
/* 212 */       Map.Entry e = itr.next();
/* 213 */       String name = (String)e.getKey();
/* 214 */       String value = quote((String)e.getValue());
/* 215 */       sb.append("; ");
/* 216 */       used += 2;
/* 217 */       int len = name.length() + value.length() + 1;
/* 218 */       if (used + len > 76) {
/* 219 */         sb.append("\r\n\t");
/* 220 */         used = 8;
/*     */       } 
/* 222 */       sb.append(name).append('=');
/* 223 */       used += name.length() + 1;
/* 224 */       if (used + value.length() > 76) {
/*     */         
/* 226 */         String s = MimeUtility.fold(used, value);
/* 227 */         sb.append(s);
/* 228 */         int lastlf = s.lastIndexOf('\n');
/* 229 */         if (lastlf >= 0) {
/* 230 */           used += s.length() - lastlf - 1; continue;
/*     */         } 
/* 232 */         used += s.length(); continue;
/*     */       } 
/* 234 */       sb.append(value);
/* 235 */       used += value.length();
/*     */     } 
/*     */ 
/*     */     
/* 239 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private String quote(String value) {
/* 244 */     if ("".equals(value))
/* 245 */       return "\"\""; 
/* 246 */     return MimeUtility.quote(value, "()<>@,;:\\\"\t []/?=");
/*     */   }
/*     */   
/*     */   public ParameterList copy() {
/* 250 */     return new ParameterList((HashMap)this.list.clone());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\internet\ParameterList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */