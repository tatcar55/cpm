/*    */ package com.beust.jcommander.converters;
/*    */ 
/*    */ import com.beust.jcommander.ParameterException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class URLConverter
/*    */   extends BaseConverter<URL>
/*    */ {
/*    */   public URLConverter(String optionName) {
/* 34 */     super(optionName);
/*    */   }
/*    */   
/*    */   public URL convert(String value) {
/*    */     try {
/* 39 */       return new URL(value);
/* 40 */     } catch (MalformedURLException e) {
/* 41 */       throw new ParameterException(getErrorString(value, "a RFC 2396 and RFC 2732 compliant URL"));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\beust\jcommander\converters\URLConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */