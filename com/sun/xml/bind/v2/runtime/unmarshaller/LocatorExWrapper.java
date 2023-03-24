/*    */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import javax.xml.bind.ValidationEventLocator;
/*    */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*    */ import org.xml.sax.Locator;
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
/*    */ class LocatorExWrapper
/*    */   implements LocatorEx
/*    */ {
/*    */   private final Locator locator;
/*    */   
/*    */   public LocatorExWrapper(Locator locator) {
/* 57 */     this.locator = locator;
/*    */   }
/*    */   
/*    */   public ValidationEventLocator getLocation() {
/* 61 */     return new ValidationEventLocatorImpl(this.locator);
/*    */   }
/*    */   
/*    */   public String getPublicId() {
/* 65 */     return this.locator.getPublicId();
/*    */   }
/*    */   
/*    */   public String getSystemId() {
/* 69 */     return this.locator.getSystemId();
/*    */   }
/*    */   
/*    */   public int getLineNumber() {
/* 73 */     return this.locator.getLineNumber();
/*    */   }
/*    */   
/*    */   public int getColumnNumber() {
/* 77 */     return this.locator.getColumnNumber();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\LocatorExWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */