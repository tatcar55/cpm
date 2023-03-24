/*     */ package com.sun.xml.rpc.sp;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
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
/*     */ public abstract class MessageCatalog
/*     */ {
/*     */   private String bundleName;
/*     */   private Map cache;
/*     */   
/*     */   protected MessageCatalog(Class packageMember) {
/* 154 */     this(packageMember, "Messages");
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MessageCatalog(Class packageMember, String bundle) {
/* 441 */     this.cache = new HashMap<Object, Object>(5);
/*     */     this.bundleName = packageMember.getName();
/*     */     int index = this.bundleName.lastIndexOf('.');
/*     */     if (index == -1) {
/*     */       this.bundleName = "";
/*     */     } else {
/*     */       this.bundleName = this.bundleName.substring(0, index) + ".";
/*     */     } 
/*     */     this.bundleName += "resources." + bundle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage(Locale locale, String messageId) {
/*     */     if (locale == null) {
/*     */       locale = Locale.getDefault();
/*     */     }
/*     */     try {
/*     */       ResourceBundle bundle = ResourceBundle.getBundle(this.bundleName, locale);
/*     */       return bundle.getString(messageId);
/*     */     } catch (MissingResourceException e) {
/*     */       return packagePrefix(messageId);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocaleSupported(String localeName) {
/* 470 */     Boolean value = (Boolean)this.cache.get(localeName);
/*     */     
/* 472 */     if (value != null) {
/* 473 */       return value.booleanValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 480 */     ClassLoader loader = null;
/*     */     
/*     */     while (true) {
/* 483 */       String name = this.bundleName + "_" + localeName;
/*     */ 
/*     */       
/*     */       try {
/* 487 */         Class.forName(name);
/* 488 */         this.cache.put(localeName, Boolean.TRUE);
/* 489 */         return true;
/* 490 */       } catch (Exception e) {
/*     */         InputStream in;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 496 */         if (loader == null) {
/* 497 */           loader = getClass().getClassLoader();
/*     */         }
/* 499 */         name = name.replace('.', '/');
/* 500 */         name = name + ".properties";
/* 501 */         if (loader == null) {
/* 502 */           in = ClassLoader.getSystemResourceAsStream(name);
/*     */         } else {
/* 504 */           in = loader.getResourceAsStream(name);
/* 505 */         }  if (in != null) {
/* 506 */           this.cache.put(localeName, Boolean.TRUE);
/* 507 */           return true;
/*     */         } 
/*     */         
/* 510 */         int index = localeName.indexOf('_');
/*     */         
/* 512 */         if (index > 0) {
/* 513 */           localeName = localeName.substring(0, index);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 521 */     this.cache.put(localeName, Boolean.FALSE);
/* 522 */     return false;
/*     */   }
/*     */   
/*     */   private String packagePrefix(String messageId) {
/*     */     String temp = getClass().getName();
/*     */     int index = temp.lastIndexOf('.');
/*     */     if (index == -1) {
/*     */       temp = "";
/*     */     } else {
/*     */       temp = temp.substring(0, index);
/*     */     } 
/*     */     return temp + '/' + messageId;
/*     */   }
/*     */   
/*     */   public String getMessage(Locale locale, String messageId, Object[] parameters) {
/*     */     MessageFormat format;
/*     */     if (parameters == null)
/*     */       return getMessage(locale, messageId); 
/*     */     for (int i = 0; i < parameters.length; i++) {
/*     */       if (!(parameters[i] instanceof String) && !(parameters[i] instanceof Number) && !(parameters[i] instanceof java.util.Date))
/*     */         if (parameters[i] == null) {
/*     */           parameters[i] = "(null)";
/*     */         } else {
/*     */           parameters[i] = parameters[i].toString();
/*     */         }  
/*     */     } 
/*     */     if (locale == null)
/*     */       locale = Locale.getDefault(); 
/*     */     try {
/*     */       ResourceBundle bundle = ResourceBundle.getBundle(this.bundleName, locale);
/*     */       format = new MessageFormat(bundle.getString(messageId));
/*     */     } catch (MissingResourceException e) {
/*     */       String retval = packagePrefix(messageId);
/*     */       for (int j = 0; j < parameters.length; j++) {
/*     */         retval = retval + ' ';
/*     */         retval = retval + parameters[j];
/*     */       } 
/*     */       return retval;
/*     */     } 
/*     */     format.setLocale(locale);
/*     */     StringBuffer result = new StringBuffer();
/*     */     result = format.format(parameters, result, new FieldPosition(0));
/*     */     return result.toString();
/*     */   }
/*     */   
/*     */   public Locale chooseLocale(String[] languages) {
/*     */     if ((languages = canonicalize(languages)) != null)
/*     */       for (int i = 0; i < languages.length; i++) {
/*     */         if (isLocaleSupported(languages[i]))
/*     */           return getLocale(languages[i]); 
/*     */       }  
/*     */     return null;
/*     */   }
/*     */   
/*     */   private String[] canonicalize(String[] languages) {
/*     */     boolean didClone = false;
/*     */     int trimCount = 0;
/*     */     if (languages == null)
/*     */       return languages; 
/*     */     for (int i = 0; i < languages.length; i++) {
/*     */       String lang = languages[i];
/*     */       int len = lang.length();
/*     */       if (len != 2 && len != 5) {
/*     */         if (!didClone) {
/*     */           languages = (String[])languages.clone();
/*     */           didClone = true;
/*     */         } 
/*     */         languages[i] = null;
/*     */         trimCount++;
/*     */       } else if (len == 2) {
/*     */         lang = lang.toLowerCase();
/*     */         if (lang != languages[i]) {
/*     */           if (!didClone) {
/*     */             languages = (String[])languages.clone();
/*     */             didClone = true;
/*     */           } 
/*     */           languages[i] = lang;
/*     */         } 
/*     */       } else {
/*     */         char[] buf = new char[5];
/*     */         buf[0] = Character.toLowerCase(lang.charAt(0));
/*     */         buf[1] = Character.toLowerCase(lang.charAt(1));
/*     */         buf[2] = '_';
/*     */         buf[3] = Character.toUpperCase(lang.charAt(3));
/*     */         buf[4] = Character.toUpperCase(lang.charAt(4));
/*     */         if (!didClone) {
/*     */           languages = (String[])languages.clone();
/*     */           didClone = true;
/*     */         } 
/*     */         languages[i] = new String(buf);
/*     */       } 
/*     */     } 
/*     */     if (trimCount != 0) {
/*     */       String[] temp = new String[languages.length - trimCount];
/*     */       for (int j = 0; j < temp.length; j++) {
/*     */         while (languages[j + trimCount] == null)
/*     */           trimCount++; 
/*     */         temp[j] = languages[j + trimCount];
/*     */       } 
/*     */       languages = temp;
/*     */     } 
/*     */     return languages;
/*     */   }
/*     */   
/*     */   private Locale getLocale(String localeName) {
/*     */     String language, country;
/*     */     int index = localeName.indexOf('_');
/*     */     if (index == -1) {
/*     */       if (localeName.equals("de"))
/*     */         return Locale.GERMAN; 
/*     */       if (localeName.equals("en"))
/*     */         return Locale.ENGLISH; 
/*     */       if (localeName.equals("fr"))
/*     */         return Locale.FRENCH; 
/*     */       if (localeName.equals("it"))
/*     */         return Locale.ITALIAN; 
/*     */       if (localeName.equals("ja"))
/*     */         return Locale.JAPANESE; 
/*     */       if (localeName.equals("ko"))
/*     */         return Locale.KOREAN; 
/*     */       if (localeName.equals("zh"))
/*     */         return Locale.CHINESE; 
/*     */       language = localeName;
/*     */       country = "";
/*     */     } else {
/*     */       if (localeName.equals("zh_CN"))
/*     */         return Locale.SIMPLIFIED_CHINESE; 
/*     */       if (localeName.equals("zh_TW"))
/*     */         return Locale.TRADITIONAL_CHINESE; 
/*     */       language = localeName.substring(0, index);
/*     */       country = localeName.substring(index + 1);
/*     */     } 
/*     */     return new Locale(language, country);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\MessageCatalog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */