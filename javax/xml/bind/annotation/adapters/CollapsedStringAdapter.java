/*     */ package javax.xml.bind.annotation.adapters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CollapsedStringAdapter
/*     */   extends XmlAdapter<String, String>
/*     */ {
/*     */   public String unmarshal(String text) {
/*  62 */     if (text == null) return null;
/*     */     
/*  64 */     int len = text.length();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     int s = 0;
/*  70 */     while (s < len && 
/*  71 */       !isWhiteSpace(text.charAt(s)))
/*     */     {
/*  73 */       s++;
/*     */     }
/*  75 */     if (s == len)
/*     */     {
/*  77 */       return text;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  82 */     StringBuffer result = new StringBuffer(len);
/*     */     
/*  84 */     if (s != 0) {
/*  85 */       for (int j = 0; j < s; j++)
/*  86 */         result.append(text.charAt(j)); 
/*  87 */       result.append(' ');
/*     */     } 
/*     */     
/*  90 */     boolean inStripMode = true;
/*  91 */     for (int i = s + 1; i < len; i++) {
/*  92 */       char ch = text.charAt(i);
/*  93 */       boolean b = isWhiteSpace(ch);
/*  94 */       if (!inStripMode || !b) {
/*     */ 
/*     */         
/*  97 */         inStripMode = b;
/*  98 */         if (inStripMode) {
/*  99 */           result.append(' ');
/*     */         } else {
/* 101 */           result.append(ch);
/*     */         } 
/*     */       } 
/*     */     } 
/* 105 */     len = result.length();
/* 106 */     if (len > 0 && result.charAt(len - 1) == ' ') {
/* 107 */       result.setLength(len - 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 112 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String marshal(String s) {
/* 121 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isWhiteSpace(char ch) {
/* 129 */     if (ch > ' ') return false;
/*     */ 
/*     */     
/* 132 */     return (ch == '\t' || ch == '\n' || ch == '\r' || ch == ' ');
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\annotation\adapters\CollapsedStringAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */