/*    */ package javax.xml.bind;
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
/*    */ 
/*    */ 
/*    */ class Messages
/*    */ {
/*    */   static final String PROVIDER_NOT_FOUND = "ContextFinder.ProviderNotFound";
/*    */   static final String COULD_NOT_INSTANTIATE = "ContextFinder.CouldNotInstantiate";
/*    */   static final String CANT_FIND_PROPERTIES_FILE = "ContextFinder.CantFindPropertiesFile";
/*    */   static final String CANT_MIX_PROVIDERS = "ContextFinder.CantMixProviders";
/*    */   static final String MISSING_PROPERTY = "ContextFinder.MissingProperty";
/*    */   static final String NO_PACKAGE_IN_CONTEXTPATH = "ContextFinder.NoPackageInContextPath";
/*    */   static final String NAME_VALUE = "PropertyException.NameValue";
/*    */   static final String CONVERTER_MUST_NOT_BE_NULL = "DatatypeConverter.ConverterMustNotBeNull";
/*    */   static final String ILLEGAL_CAST = "JAXBContext.IllegalCast";
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\Messages.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */