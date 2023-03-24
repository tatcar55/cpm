/*    */ package com.sun.xml.rpc.util.exception;
/*    */ 
/*    */ import com.sun.xml.rpc.util.localization.Localizable;
/*    */ import com.sun.xml.rpc.util.localization.Localizer;
/*    */ import com.sun.xml.rpc.util.localization.NullLocalizable;
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalizableExceptionAdapter
/*    */   extends Exception
/*    */   implements Localizable
/*    */ {
/*    */   protected Localizable localizablePart;
/*    */   protected Throwable nestedException;
/*    */   
/*    */   public LocalizableExceptionAdapter(Throwable nestedException) {
/* 48 */     this.nestedException = nestedException;
/* 49 */     if (nestedException instanceof Localizable) {
/* 50 */       this.localizablePart = (Localizable)nestedException;
/*    */     } else {
/* 52 */       this.localizablePart = (Localizable)new NullLocalizable(nestedException.toString());
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 57 */     return this.localizablePart.getKey();
/*    */   }
/*    */   
/*    */   public Object[] getArguments() {
/* 61 */     return this.localizablePart.getArguments();
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 65 */     return this.localizablePart.getResourceBundleName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return this.nestedException.toString();
/*    */   }
/*    */   
/*    */   public String getLocalizedMessage() {
/* 74 */     if (this.nestedException == this.localizablePart) {
/* 75 */       Localizer localizer = new Localizer();
/* 76 */       return localizer.localize(this.localizablePart);
/*    */     } 
/* 78 */     return this.nestedException.getLocalizedMessage();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 83 */     return getLocalizedMessage();
/*    */   }
/*    */   
/*    */   public Throwable getNestedException() {
/* 87 */     return this.nestedException;
/*    */   }
/*    */   
/*    */   public void printStackTrace() {
/* 91 */     this.nestedException.printStackTrace();
/*    */   }
/*    */   
/*    */   public void printStackTrace(PrintStream s) {
/* 95 */     this.nestedException.printStackTrace(s);
/*    */   }
/*    */   
/*    */   public void printStackTrace(PrintWriter s) {
/* 99 */     this.nestedException.printStackTrace(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\exception\LocalizableExceptionAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */