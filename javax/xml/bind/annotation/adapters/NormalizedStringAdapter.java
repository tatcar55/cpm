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
/*     */ public final class NormalizedStringAdapter
/*     */   extends XmlAdapter<String, String>
/*     */ {
/*     */   public String unmarshal(String text) {
/*  61 */     if (text == null) return null;
/*     */     
/*  63 */     int i = text.length() - 1;
/*     */ 
/*     */     
/*  66 */     while (i >= 0 && !isWhiteSpaceExceptSpace(text.charAt(i))) {
/*  67 */       i--;
/*     */     }
/*  69 */     if (i < 0)
/*     */     {
/*  71 */       return text;
/*     */     }
/*     */ 
/*     */     
/*  75 */     char[] buf = text.toCharArray();
/*     */     
/*  77 */     buf[i--] = ' ';
/*  78 */     for (; i >= 0; i--) {
/*  79 */       if (isWhiteSpaceExceptSpace(buf[i]))
/*  80 */         buf[i] = ' '; 
/*     */     } 
/*  82 */     return new String(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String marshal(String s) {
/*  91 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isWhiteSpaceExceptSpace(char ch) {
/* 102 */     if (ch >= ' ') return false;
/*     */ 
/*     */     
/* 105 */     return (ch == '\t' || ch == '\n' || ch == '\r');
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\annotation\adapters\NormalizedStringAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */