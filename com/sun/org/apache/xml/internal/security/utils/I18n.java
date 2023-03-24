package com.sun.org.apache.xml.internal.security.utils;

import com.sun.org.apache.xml.internal.security.Init;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
  public static final String NOT_INITIALIZED_MSG = "You must initialize the xml-security library correctly before you use it. Call the static method \"com.sun.org.apache.xml.internal.security.Init.init();\" to do that before you use any functionality from that library.";
  
  static String defaultLanguageCode;
  
  static String defaultCountryCode;
  
  static ResourceBundle resourceBundle;
  
  static boolean alreadyInitialized = false;
  
  static String _languageCode = null;
  
  static String _countryCode = null;
  
  public static String translate(String paramString, Object[] paramArrayOfObject) {
    return getExceptionMessage(paramString, paramArrayOfObject);
  }
  
  public static String translate(String paramString) {
    return getExceptionMessage(paramString);
  }
  
  public static String getExceptionMessage(String paramString) {
    try {
      return resourceBundle.getString(paramString);
    } catch (Throwable throwable) {
      return Init.isInitialized() ? ("No message with ID \"" + paramString + "\" found in resource bundle \"" + "com/sun/org/apache/xml/internal/security/resource/xmlsecurity" + "\"") : "You must initialize the xml-security library correctly before you use it. Call the static method \"com.sun.org.apache.xml.internal.security.Init.init();\" to do that before you use any functionality from that library.";
    } 
  }
  
  public static String getExceptionMessage(String paramString, Exception paramException) {
    try {
      Object[] arrayOfObject = { paramException.getMessage() };
      return MessageFormat.format(resourceBundle.getString(paramString), arrayOfObject);
    } catch (Throwable throwable) {
      return Init.isInitialized() ? ("No message with ID \"" + paramString + "\" found in resource bundle \"" + "com/sun/org/apache/xml/internal/security/resource/xmlsecurity" + "\". Original Exception was a " + paramException.getClass().getName() + " and message " + paramException.getMessage()) : "You must initialize the xml-security library correctly before you use it. Call the static method \"com.sun.org.apache.xml.internal.security.Init.init();\" to do that before you use any functionality from that library.";
    } 
  }
  
  public static String getExceptionMessage(String paramString, Object[] paramArrayOfObject) {
    try {
      return MessageFormat.format(resourceBundle.getString(paramString), paramArrayOfObject);
    } catch (Throwable throwable) {
      return Init.isInitialized() ? ("No message with ID \"" + paramString + "\" found in resource bundle \"" + "com/sun/org/apache/xml/internal/security/resource/xmlsecurity" + "\"") : "You must initialize the xml-security library correctly before you use it. Call the static method \"com.sun.org.apache.xml.internal.security.Init.init();\" to do that before you use any functionality from that library.";
    } 
  }
  
  public static void init(String paramString1, String paramString2) {
    defaultLanguageCode = paramString1;
    if (defaultLanguageCode == null)
      defaultLanguageCode = Locale.getDefault().getLanguage(); 
    defaultCountryCode = paramString2;
    if (defaultCountryCode == null)
      defaultCountryCode = Locale.getDefault().getCountry(); 
    initLocale(defaultLanguageCode, defaultCountryCode);
  }
  
  public static void initLocale(String paramString1, String paramString2) {
    if (alreadyInitialized && paramString1.equals(_languageCode) && paramString2.equals(_countryCode))
      return; 
    if (paramString1 != null && paramString2 != null && paramString1.length() > 0 && paramString2.length() > 0) {
      _languageCode = paramString1;
      _countryCode = paramString2;
    } else {
      _countryCode = defaultCountryCode;
      _languageCode = defaultLanguageCode;
    } 
    resourceBundle = ResourceBundle.getBundle("com/sun/org/apache/xml/internal/security/resource/xmlsecurity", new Locale(_languageCode, _countryCode));
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\I18n.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */