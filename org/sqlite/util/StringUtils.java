/*    */ package org.sqlite.util;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class StringUtils {
/*    */   public static String join(List<String> list, String separator) {
/*  7 */     StringBuilder sb = new StringBuilder();
/*  8 */     boolean first = true;
/*  9 */     for (String item : list) {
/* 10 */       if (first) {
/* 11 */         first = false;
/*    */       } else {
/* 13 */         sb.append(separator);
/*    */       } 
/* 15 */       sb.append(item);
/*    */     } 
/* 17 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlit\\util\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */