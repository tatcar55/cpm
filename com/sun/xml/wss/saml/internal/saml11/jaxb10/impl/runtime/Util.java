/*    */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*    */ 
/*    */ import com.sun.xml.bind.Messages;
/*    */ import com.sun.xml.bind.serializer.AbortSerializationException;
/*    */ import com.sun.xml.bind.util.ValidationEventLocatorExImpl;
/*    */ import javax.xml.bind.ValidationEvent;
/*    */ import javax.xml.bind.ValidationEventLocator;
/*    */ import javax.xml.bind.helpers.PrintConversionEventImpl;
/*    */ import javax.xml.bind.helpers.ValidationEventImpl;
/*    */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*    */ import org.xml.sax.SAXException;
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
/*    */ public class Util
/*    */ {
/*    */   public static void handlePrintConversionException(Object caller, Exception e, XMLSerializer serializer) throws SAXException {
/* 33 */     if (e instanceof SAXException)
/*    */     {
/*    */ 
/*    */       
/* 37 */       throw (SAXException)e;
/*    */     }
/* 39 */     String message = e.getMessage();
/* 40 */     if (message == null) {
/* 41 */       message = e.toString();
/*    */     }
/*    */     
/* 44 */     ValidationEvent ve = new PrintConversionEventImpl(1, message, new ValidationEventLocatorImpl(caller), e);
/*    */ 
/*    */     
/* 47 */     serializer.reportError(ve);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void handleTypeMismatchError(XMLSerializer serializer, Object parentObject, String fieldName, Object childObject) throws AbortSerializationException {
/* 56 */     ValidationEvent ve = new ValidationEventImpl(1, Messages.format("Util.TypeMismatch", getUserFriendlyTypeName(parentObject), fieldName, getUserFriendlyTypeName(childObject)), (ValidationEventLocator)new ValidationEventLocatorExImpl(parentObject, fieldName));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 64 */     serializer.reportError(ve);
/*    */   }
/*    */   
/*    */   private static String getUserFriendlyTypeName(Object o) {
/* 68 */     if (o instanceof ValidatableObject) {
/* 69 */       return ((ValidatableObject)o).getPrimaryInterface().getName();
/*    */     }
/* 71 */     return o.getClass().getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\Util.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */