/*     */ package com.sun.xml.ws.policy.privateutil;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PolicyUtils
/*     */ {
/*     */   public static class Commons
/*     */   {
/*     */     public static String getStackMethodName(int methodIndexInStack) {
/*     */       String methodName;
/*  82 */       StackTraceElement[] stack = Thread.currentThread().getStackTrace();
/*  83 */       if (stack.length > methodIndexInStack + 1) {
/*  84 */         methodName = stack[methodIndexInStack].getMethodName();
/*     */       } else {
/*  86 */         methodName = "UNKNOWN METHOD";
/*     */       } 
/*     */       
/*  89 */       return methodName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static String getCallerMethodName() {
/*  99 */       String result = getStackMethodName(5);
/* 100 */       if (result.equals("invoke0"))
/*     */       {
/* 102 */         result = getStackMethodName(4);
/*     */       }
/* 104 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class IO {
/* 109 */     private static final PolicyLogger LOGGER = PolicyLogger.getLogger(IO.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static void closeResource(Closeable resource) {
/* 119 */       if (resource != null) {
/*     */         try {
/* 121 */           resource.close();
/* 122 */         } catch (IOException e) {
/* 123 */           LOGGER.warning(LocalizationMessages.WSP_0023_UNEXPECTED_ERROR_WHILE_CLOSING_RESOURCE(resource.toString()), e);
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static void closeResource(XMLStreamReader reader) {
/* 136 */       if (reader != null) {
/*     */         try {
/* 138 */           reader.close();
/* 139 */         } catch (XMLStreamException e) {
/* 140 */           LOGGER.warning(LocalizationMessages.WSP_0023_UNEXPECTED_ERROR_WHILE_CLOSING_RESOURCE(reader.toString()), e);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Text
/*     */   {
/* 154 */     public static final String NEW_LINE = System.getProperty("line.separator");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static String createIndent(int indentLevel) {
/* 164 */       char[] charData = new char[indentLevel * 4];
/* 165 */       Arrays.fill(charData, ' ');
/* 166 */       return String.valueOf(charData);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Comparison
/*     */   {
/* 178 */     public static final Comparator<QName> QNAME_COMPARATOR = new Comparator<QName>() {
/*     */         public int compare(QName qn1, QName qn2) {
/* 180 */           if (qn1 == qn2 || qn1.equals(qn2)) {
/* 181 */             return 0;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 186 */           int result = qn1.getNamespaceURI().compareTo(qn2.getNamespaceURI());
/* 187 */           if (result != 0) {
/* 188 */             return result;
/*     */           }
/*     */           
/* 191 */           return qn1.getLocalPart().compareTo(qn2.getLocalPart());
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static int compareBoolean(boolean b1, boolean b2) {
/* 201 */       int i1 = b1 ? 1 : 0;
/* 202 */       int i2 = b2 ? 1 : 0;
/*     */       
/* 204 */       return i1 - i2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static int compareNullableStrings(String s1, String s2) {
/* 213 */       return (s1 == null) ? ((s2 == null) ? 0 : -1) : ((s2 == null) ? 1 : s1.compareTo(s2));
/*     */     }
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
/*     */   public static class Collections
/*     */   {
/*     */     public static <E, T extends Collection<? extends E>, U extends Collection<? extends E>> Collection<Collection<E>> combine(U initialBase, Collection<T> options, boolean ignoreEmptyOption) {
/* 228 */       List<Collection<E>> combinations = null;
/* 229 */       if (options == null || options.isEmpty()) {
/*     */         
/* 231 */         if (initialBase != null) {
/* 232 */           combinations = new ArrayList<Collection<E>>(1);
/* 233 */           combinations.add(new ArrayList<E>((Collection<? extends E>)initialBase));
/*     */         } 
/* 235 */         return combinations;
/*     */       } 
/*     */ 
/*     */       
/* 239 */       Collection<E> base = new LinkedList<E>();
/* 240 */       if (initialBase != null && !initialBase.isEmpty()) {
/* 241 */         base.addAll((Collection<? extends E>)initialBase);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 251 */       int finalCombinationsSize = 1;
/* 252 */       Queue<T> optionProcessingQueue = new LinkedList<T>();
/* 253 */       for (Collection<? extends E> collection : options) {
/* 254 */         int optionSize = collection.size();
/*     */         
/* 256 */         if (optionSize == 0) {
/* 257 */           if (!ignoreEmptyOption)
/* 258 */             return null;  continue;
/*     */         } 
/* 260 */         if (optionSize == 1) {
/* 261 */           base.addAll(collection); continue;
/*     */         } 
/* 263 */         optionProcessingQueue.offer((T)collection);
/* 264 */         finalCombinationsSize *= optionSize;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 269 */       combinations = new ArrayList<Collection<E>>(finalCombinationsSize);
/* 270 */       combinations.add(base);
/* 271 */       if (finalCombinationsSize > 1) {
/*     */         Collection collection;
/* 273 */         while ((collection = (Collection)optionProcessingQueue.poll()) != null) {
/* 274 */           int actualSemiCombinationCollectionSize = combinations.size();
/* 275 */           int newSemiCombinationCollectionSize = actualSemiCombinationCollectionSize * collection.size();
/*     */           
/* 277 */           int semiCombinationIndex = 0;
/* 278 */           for (E optionElement : collection) {
/* 279 */             for (int i = 0; i < actualSemiCombinationCollectionSize; i++) {
/* 280 */               Collection<E> semiCombination = combinations.get(semiCombinationIndex);
/*     */               
/* 282 */               if (semiCombinationIndex + actualSemiCombinationCollectionSize < newSemiCombinationCollectionSize)
/*     */               {
/* 284 */                 combinations.add(new LinkedList<E>(semiCombination));
/*     */               }
/*     */               
/* 287 */               semiCombination.add(optionElement);
/* 288 */               semiCombinationIndex++;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 293 */       return combinations;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Reflection
/*     */   {
/* 301 */     private static final PolicyLogger LOGGER = PolicyLogger.getLogger(Reflection.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static <T> T invoke(Object target, String methodName, Class<T> resultClass, Object... parameters) throws RuntimePolicyUtilsException {
/*     */       Class[] parameterTypes;
/* 309 */       if (parameters != null && parameters.length > 0) {
/* 310 */         parameterTypes = new Class[parameters.length];
/* 311 */         int i = 0;
/* 312 */         for (Object parameter : parameters) {
/* 313 */           parameterTypes[i++] = parameter.getClass();
/*     */         }
/*     */       } else {
/* 316 */         parameterTypes = null;
/*     */       } 
/*     */       
/* 319 */       return invoke(target, methodName, resultClass, parameters, parameterTypes);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static <T> T invoke(Object target, String methodName, Class<T> resultClass, Object[] parameters, Class[] parameterTypes) throws RuntimePolicyUtilsException {
/*     */       try {
/* 328 */         Method method = target.getClass().getMethod(methodName, parameterTypes);
/* 329 */         Object result = method.invoke(target, parameters);
/*     */         
/* 331 */         return resultClass.cast(result);
/* 332 */       } catch (IllegalArgumentException e) {
/* 333 */         throw (RuntimePolicyUtilsException)LOGGER.logSevereException(new RuntimePolicyUtilsException(createExceptionMessage(target, parameters, methodName), e));
/* 334 */       } catch (InvocationTargetException e) {
/* 335 */         throw (RuntimePolicyUtilsException)LOGGER.logSevereException(new RuntimePolicyUtilsException(createExceptionMessage(target, parameters, methodName), e));
/* 336 */       } catch (IllegalAccessException e) {
/* 337 */         throw (RuntimePolicyUtilsException)LOGGER.logSevereException(new RuntimePolicyUtilsException(createExceptionMessage(target, parameters, methodName), e.getCause()));
/* 338 */       } catch (SecurityException e) {
/* 339 */         throw (RuntimePolicyUtilsException)LOGGER.logSevereException(new RuntimePolicyUtilsException(createExceptionMessage(target, parameters, methodName), e));
/* 340 */       } catch (NoSuchMethodException e) {
/* 341 */         throw (RuntimePolicyUtilsException)LOGGER.logSevereException(new RuntimePolicyUtilsException(createExceptionMessage(target, parameters, methodName), e));
/*     */       } 
/*     */     }
/*     */     
/*     */     private static String createExceptionMessage(Object target, Object[] parameters, String methodName) {
/* 346 */       return LocalizationMessages.WSP_0061_METHOD_INVOCATION_FAILED(target.getClass().getName(), methodName, (parameters == null) ? null : Arrays.<Object>asList(parameters).toString());
/*     */     }
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
/*     */   public static class ConfigFile
/*     */   {
/*     */     public static String generateFullName(String configFileIdentifier) throws PolicyException {
/* 364 */       if (configFileIdentifier != null) {
/* 365 */         StringBuffer buffer = new StringBuffer("wsit-");
/* 366 */         buffer.append(configFileIdentifier).append(".xml");
/* 367 */         return buffer.toString();
/*     */       } 
/* 369 */       throw new PolicyException(LocalizationMessages.WSP_0080_IMPLEMENTATION_EXPECTED_NOT_NULL());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static URL loadFromContext(String configFileName, Object context) {
/* 383 */       return PolicyUtils.Reflection.<URL>invoke(context, "getResource", URL.class, new Object[] { configFileName });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static URL loadFromClasspath(String configFileName) {
/* 395 */       ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 396 */       if (cl == null) {
/* 397 */         return ClassLoader.getSystemResource(configFileName);
/*     */       }
/* 399 */       return cl.getResource(configFileName);
/*     */     }
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
/*     */   public static class ServiceProvider
/*     */   {
/*     */     public static <T> T[] load(Class<T> serviceClass, ClassLoader loader) {
/* 434 */       return ServiceFinder.<T>find(serviceClass, loader).toArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static <T> T[] load(Class<T> serviceClass) {
/* 455 */       return ServiceFinder.<T>find(serviceClass).toArray();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Rfc2396
/*     */   {
/* 461 */     private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyUtils.Reflection.class);
/*     */ 
/*     */     
/*     */     public static String unquote(String quoted) {
/* 465 */       if (null == quoted) {
/* 466 */         return null;
/*     */       }
/* 468 */       byte[] unquoted = new byte[quoted.length()];
/* 469 */       int newLength = 0;
/*     */ 
/*     */       
/* 472 */       for (int i = 0; i < quoted.length(); i++) {
/* 473 */         char c = quoted.charAt(i);
/* 474 */         if ('%' == c) {
/* 475 */           if (i + 2 >= quoted.length()) {
/* 476 */             throw (RuntimePolicyUtilsException)LOGGER.logSevereException(new RuntimePolicyUtilsException(LocalizationMessages.WSP_0079_ERROR_WHILE_RFC_2396_UNESCAPING(quoted)), false);
/*     */           }
/* 478 */           int hi = Character.digit(quoted.charAt(++i), 16);
/* 479 */           int lo = Character.digit(quoted.charAt(++i), 16);
/* 480 */           if (0 > hi || 0 > lo) {
/* 481 */             throw (RuntimePolicyUtilsException)LOGGER.logSevereException(new RuntimePolicyUtilsException(LocalizationMessages.WSP_0079_ERROR_WHILE_RFC_2396_UNESCAPING(quoted)), false);
/*     */           }
/* 483 */           unquoted[newLength++] = (byte)(hi * 16 + lo);
/*     */         } else {
/* 485 */           unquoted[newLength++] = (byte)c;
/*     */         } 
/*     */       } 
/*     */       try {
/* 489 */         return new String(unquoted, 0, newLength, "utf-8");
/* 490 */       } catch (UnsupportedEncodingException uee) {
/* 491 */         throw (RuntimePolicyUtilsException)LOGGER.logSevereException(new RuntimePolicyUtilsException(LocalizationMessages.WSP_0079_ERROR_WHILE_RFC_2396_UNESCAPING(quoted), uee));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\privateutil\PolicyUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */