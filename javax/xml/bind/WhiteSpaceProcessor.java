/*     */ package javax.xml.bind;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class WhiteSpaceProcessor
/*     */ {
/*     */   public static String replace(String text) {
/*  69 */     return replace(text).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSequence replace(CharSequence text) {
/*  76 */     int i = text.length() - 1;
/*     */ 
/*     */     
/*  79 */     while (i >= 0 && !isWhiteSpaceExceptSpace(text.charAt(i))) {
/*  80 */       i--;
/*     */     }
/*  82 */     if (i < 0)
/*     */     {
/*  84 */       return text;
/*     */     }
/*     */ 
/*     */     
/*  88 */     StringBuilder buf = new StringBuilder(text);
/*     */     
/*  90 */     buf.setCharAt(i--, ' ');
/*  91 */     for (; i >= 0; i--) {
/*  92 */       if (isWhiteSpaceExceptSpace(buf.charAt(i)))
/*  93 */         buf.setCharAt(i, ' '); 
/*     */     } 
/*  95 */     return new String(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSequence trim(CharSequence text) {
/* 103 */     int len = text.length();
/* 104 */     int start = 0;
/*     */     
/* 106 */     while (start < len && isWhiteSpace(text.charAt(start))) {
/* 107 */       start++;
/*     */     }
/* 109 */     int end = len - 1;
/*     */     
/* 111 */     while (end > start && isWhiteSpace(text.charAt(end))) {
/* 112 */       end--;
/*     */     }
/* 114 */     if (start == 0 && end == len - 1) {
/* 115 */       return text;
/*     */     }
/* 117 */     return text.subSequence(start, end + 1);
/*     */   }
/*     */   
/*     */   public static String collapse(String text) {
/* 121 */     return collapse(text).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSequence collapse(CharSequence text) {
/* 130 */     int len = text.length();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     int s = 0;
/* 136 */     while (s < len && 
/* 137 */       !isWhiteSpace(text.charAt(s)))
/*     */     {
/* 139 */       s++;
/*     */     }
/* 141 */     if (s == len)
/*     */     {
/* 143 */       return text;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 148 */     StringBuilder result = new StringBuilder(len);
/*     */     
/* 150 */     if (s != 0) {
/* 151 */       for (int j = 0; j < s; j++)
/* 152 */         result.append(text.charAt(j)); 
/* 153 */       result.append(' ');
/*     */     } 
/*     */     
/* 156 */     boolean inStripMode = true;
/* 157 */     for (int i = s + 1; i < len; i++) {
/* 158 */       char ch = text.charAt(i);
/* 159 */       boolean b = isWhiteSpace(ch);
/* 160 */       if (!inStripMode || !b) {
/*     */ 
/*     */         
/* 163 */         inStripMode = b;
/* 164 */         if (inStripMode) {
/* 165 */           result.append(' ');
/*     */         } else {
/* 167 */           result.append(ch);
/*     */         } 
/*     */       } 
/*     */     } 
/* 171 */     len = result.length();
/* 172 */     if (len > 0 && result.charAt(len - 1) == ' ') {
/* 173 */       result.setLength(len - 1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 178 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean isWhiteSpace(CharSequence s) {
/* 185 */     for (int i = s.length() - 1; i >= 0; i--) {
/* 186 */       if (!isWhiteSpace(s.charAt(i)))
/* 187 */         return false; 
/* 188 */     }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean isWhiteSpace(char ch) {
/* 195 */     if (ch > ' ') return false;
/*     */ 
/*     */     
/* 198 */     return (ch == '\t' || ch == '\n' || ch == '\r' || ch == ' ');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final boolean isWhiteSpaceExceptSpace(char ch) {
/* 208 */     if (ch >= ' ') return false;
/*     */ 
/*     */     
/* 211 */     return (ch == '\t' || ch == '\n' || ch == '\r');
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\WhiteSpaceProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */