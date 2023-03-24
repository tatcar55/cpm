package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnexpectedObjectException;
import javax.xml.registry.infomodel.InternationalString;
import javax.xml.registry.infomodel.LocalizedString;

public class InternationalStringImpl implements InternationalString, Serializable {
  static String DEFAULT_CHARSET = "UTF-8";
  
  private HashMap strings = new HashMap<Object, Object>();
  
  public InternationalStringImpl() {}
  
  public InternationalStringImpl(LocalizedString paramLocalizedString) throws JAXRException {
    this();
    String str = makeKey(paramLocalizedString.getCharsetName(), paramLocalizedString.getLocale());
    this.strings.put(str, paramLocalizedString);
  }
  
  public InternationalStringImpl(Locale paramLocale, String paramString) {
    this();
    String str = makeKey(DEFAULT_CHARSET, paramLocale);
    this.strings.put(str, new LocalizedStringImpl(paramLocale, paramString));
  }
  
  public InternationalStringImpl(String paramString) {
    this(Locale.getDefault(), paramString);
  }
  
  public String getValue() throws JAXRException {
    String str = getValue(Locale.getDefault());
    if (str == null)
      str = getValue(new Locale(Locale.getDefault().getLanguage(), "")); 
    return str;
  }
  
  public String getValue(Locale paramLocale) throws JAXRException {
    if (paramLocale == null)
      return null; 
    String str = makeKey(DEFAULT_CHARSET, paramLocale);
    LocalizedString localizedString = (LocalizedString)this.strings.get(str);
    return (localizedString == null) ? null : localizedString.getValue();
  }
  
  public void setValue(String paramString) throws JAXRException {
    setValue(Locale.getDefault(), paramString);
  }
  
  public void setValue(Locale paramLocale, String paramString) throws JAXRException {
    String str = makeKey(DEFAULT_CHARSET, paramLocale);
    this.strings.put(str, new LocalizedStringImpl(paramLocale, paramString));
  }
  
  public void addLocalizedString(LocalizedString paramLocalizedString) throws JAXRException {
    if (paramLocalizedString == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("InternationalStringImpl:LocalizedString_cannot_be_null")); 
    String str = makeKey(paramLocalizedString.getCharsetName(), paramLocalizedString.getLocale());
    this.strings.put(str, paramLocalizedString);
  }
  
  public void addLocalizedStrings(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    Iterator<LocalizedString> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext())
        addLocalizedString(iterator.next()); 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("InternationalStringImpl:Objects_in_collection_must_be_LocalizedStrings"), classCastException);
    } 
  }
  
  public void removeLocalizedString(LocalizedString paramLocalizedString) throws JAXRException {
    if (paramLocalizedString == null)
      return; 
    String str = makeKey(paramLocalizedString.getCharsetName(), paramLocalizedString.getLocale());
    this.strings.remove(str);
  }
  
  public void removeLocalizedStrings(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    Iterator<LocalizedString> iterator = paramCollection.iterator();
    while (iterator.hasNext()) {
      try {
        removeLocalizedString(iterator.next());
      } catch (ClassCastException classCastException) {
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("InternationalStringImpl:Objects_in_collection_must_be_LocalizedStrings"), classCastException);
      } 
    } 
  }
  
  public LocalizedString getLocalizedString(Locale paramLocale, String paramString) throws JAXRException {
    String str = makeKey(paramString, paramLocale);
    return (LocalizedString)this.strings.get(str);
  }
  
  public Collection getLocalizedStrings() throws JAXRException {
    return new ArrayList(this.strings.values());
  }
  
  private String makeKey(String paramString, Locale paramLocale) {
    return new String(paramString + paramLocale.toString());
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\InternationalStringImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */