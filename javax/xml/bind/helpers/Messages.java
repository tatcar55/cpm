/*    */ package javax.xml.bind.helpers;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.ResourceBundle;
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
/*    */ class Messages
/*    */ {
/*    */   static final String INPUTSTREAM_NOT_NULL = "AbstractUnmarshallerImpl.ISNotNull";
/*    */   static final String MUST_BE_BOOLEAN = "AbstractMarshallerImpl.MustBeBoolean";
/*    */   static final String MUST_BE_STRING = "AbstractMarshallerImpl.MustBeString";
/*    */   static final String SEVERITY_MESSAGE = "DefaultValidationEventHandler.SeverityMessage";
/*    */   static final String LOCATION_UNAVAILABLE = "DefaultValidationEventHandler.LocationUnavailable";
/*    */   static final String UNRECOGNIZED_SEVERITY = "DefaultValidationEventHandler.UnrecognizedSeverity";
/*    */   static final String WARNING = "DefaultValidationEventHandler.Warning";
/*    */   static final String ERROR = "DefaultValidationEventHandler.Error";
/*    */   static final String FATAL_ERROR = "DefaultValidationEventHandler.FatalError";
/*    */   static final String ILLEGAL_SEVERITY = "ValidationEventImpl.IllegalSeverity";
/*    */   static final String MUST_NOT_BE_NULL = "Shared.MustNotBeNull";
/*    */   
/*    */   static String format(String property) {
/* 52 */     return format(property, (Object[])null);
/*    */   }
/*    */   
/*    */   static String format(String property, Object arg1) {
/* 56 */     return format(property, new Object[] { arg1 });
/*    */   }
/*    */   
/*    */   static String format(String property, Object arg1, Object arg2) {
/* 60 */     return format(property, new Object[] { arg1, arg2 });
/*    */   }
/*    */   
/*    */   static String format(String property, Object arg1, Object arg2, Object arg3) {
/* 64 */     return format(property, new Object[] { arg1, arg2, arg3 });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static String format(String property, Object[] args) {
/* 71 */     String text = ResourceBundle.getBundle(Messages.class.getName()).getString(property);
/* 72 */     return MessageFormat.format(text, args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\helpers\Messages.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */