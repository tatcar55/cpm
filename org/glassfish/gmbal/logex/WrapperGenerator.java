/*     */ package org.glassfish.gmbal.logex;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.logging.Formatter;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.Logger;
/*     */ import org.glassfish.gmbal.generic.OperationTracer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperGenerator
/*     */ {
/*     */   private static int findAnnotatedParameter(Annotation[][] pannos, Class<? extends Annotation> cls) {
/* 103 */     for (int ctr1 = 0; ctr1 < pannos.length; ctr1++) {
/* 104 */       Annotation[] annos = pannos[ctr1];
/* 105 */       for (int ctr2 = 0; ctr2 < annos.length; ctr2++) {
/* 106 */         Annotation anno = annos[ctr2];
/* 107 */         if (cls.isInstance(anno)) {
/* 108 */           return ctr1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     return -1;
/*     */   }
/*     */   
/*     */   private static Object[] getWithSkip(Object[] args, int skip) {
/* 117 */     if (skip >= 0) {
/* 118 */       Object[] result = new Object[args.length - 1];
/* 119 */       int rindex = 0;
/* 120 */       for (int ctr = 0; ctr < args.length; ctr++) {
/* 121 */         if (ctr != skip) {
/* 122 */           result[rindex++] = args[ctr];
/*     */         }
/*     */       } 
/* 125 */       return result;
/*     */     } 
/* 127 */     return args;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceBundle getResourceBundle(String name) {
/* 132 */     ResourceBundle rb = null;
/*     */     
/* 134 */     if (name.length() > 0) {
/*     */       try {
/* 136 */         rb = ResourceBundle.getBundle(name);
/* 137 */       } catch (Exception exc) {
/* 138 */         Logger.getLogger("org.glassfish.gmbal.logex").fine("Could not load resource bundle");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 143 */     return rb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getMessage(ResourceBundle rb, Logger logger, Method method, int numParams, String idPrefix, int logId) {
/* 150 */     String msg = getTranslatedMessage(rb, logger, method);
/*     */     
/* 152 */     if (msg == null) {
/* 153 */       Message message = method.<Message>getAnnotation(Message.class);
/* 154 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 156 */       sb.append(idPrefix);
/* 157 */       sb.append(logId);
/* 158 */       sb.append(": ");
/*     */       
/* 160 */       if (message == null) {
/* 161 */         sb.append(method.getName());
/* 162 */         sb.append(' ');
/* 163 */         for (int ctr = 0; ctr < numParams; ctr++) {
/* 164 */           if (ctr > 0) {
/* 165 */             sb.append(", ");
/*     */           }
/*     */           
/* 168 */           sb.append("arg");
/* 169 */           sb.append(ctr);
/* 170 */           sb.append("={" + ctr + "}");
/*     */         } 
/*     */       } else {
/* 173 */         sb.append(message.value());
/*     */       } 
/*     */       
/* 176 */       msg = sb.toString();
/*     */     } 
/*     */     
/* 179 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void inferCaller(LogRecord lrec) {
/* 186 */     StackTraceElement[] stack = (new Throwable()).getStackTrace();
/* 187 */     StackTraceElement frame = null;
/* 188 */     String wcname = "$Proxy";
/* 189 */     String baseName = WrapperGenerator.class.getName();
/* 190 */     String nestedName = WrapperGenerator.class.getName() + "$1";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     int ix = 0;
/* 196 */     while (ix < stack.length) {
/* 197 */       frame = stack[ix];
/* 198 */       String cname = frame.getClassName();
/* 199 */       if (!cname.contains(wcname) && !cname.equals(baseName) && !cname.equals(nestedName)) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 204 */       ix++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 209 */     if (ix < stack.length) {
/* 210 */       lrec.setSourceClassName(frame.getClassName());
/* 211 */       lrec.setSourceMethodName(frame.getMethodName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Exception makeException(ReturnType rtype, boolean forceStackTrace, Class<?> rclass, Throwable cause, String msg) {
/* 218 */     Exception exc = null;
/*     */     
/*     */     try {
/* 221 */       if (rtype == ReturnType.NULL) {
/* 222 */         if (forceStackTrace) {
/* 223 */           exc = new RuntimeException("StackTrace");
/*     */         }
/* 225 */       } else if (rtype == ReturnType.EXCEPTION) {
/* 226 */         Constructor<?> cons = rclass.getConstructor(new Class[] { String.class });
/* 227 */         exc = (Exception)cons.newInstance(new Object[] { msg });
/*     */       } 
/* 229 */     } catch (InstantiationException ex) {
/* 230 */       exc = new RuntimeException("No <init>(String) constructor available in " + rclass + ": " + ex);
/*     */     }
/* 232 */     catch (IllegalAccessException ex) {
/* 233 */       exc = new RuntimeException("No <init>(String) constructor available in " + rclass + ": " + ex);
/*     */     }
/* 235 */     catch (IllegalArgumentException ex) {
/* 236 */       exc = new RuntimeException("No <init>(String) constructor available in " + rclass + ": " + ex);
/*     */     }
/* 238 */     catch (InvocationTargetException ex) {
/* 239 */       exc = new RuntimeException("No <init>(String) constructor available in " + rclass + ": " + ex);
/*     */     }
/* 241 */     catch (NoSuchMethodException ex) {
/* 242 */       exc = new RuntimeException("No <init>(String) constructor available in " + rclass + ": " + ex);
/*     */     }
/* 244 */     catch (SecurityException ex) {
/* 245 */       exc = new RuntimeException("No <init>(String) constructor available in " + rclass + ": " + ex);
/*     */     } 
/*     */ 
/*     */     
/* 249 */     if (exc != null && cause != null) {
/* 250 */       exc.initCause(cause);
/*     */     }
/*     */     
/* 253 */     return exc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getTranslatedMessage(ResourceBundle rb, Logger logger, Method method) {
/* 260 */     String result = null;
/*     */     
/* 262 */     if (rb != null) {
/*     */       try {
/* 264 */         String key = logger.getName() + "." + method.getName();
/* 265 */         result = rb.getString(key);
/* 266 */       } catch (Exception exc) {
/* 267 */         Logger.getLogger("org.glassfish.gmbal.logex").fine("Could not find translated message in bundle " + rb + " for logger " + logger + " and method " + method.getName());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String handleMessageOnly(ResourceBundle rb, Method method, Logger logger, Object[] messageParams) {
/* 284 */     String result, msg = ((Message)method.<Message>getAnnotation(Message.class)).value();
/* 285 */     String transMsg = getTranslatedMessage(rb, logger, method);
/* 286 */     if (transMsg == null) {
/* 287 */       transMsg = msg;
/*     */     }
/*     */ 
/*     */     
/* 291 */     if (transMsg.indexOf("{0") >= 0) {
/* 292 */       result = MessageFormat.format(transMsg, messageParams);
/*     */     } else {
/* 294 */       result = transMsg;
/*     */     } 
/*     */     
/* 297 */     return result;
/*     */   }
/*     */   
/* 300 */   private enum ReturnType { EXCEPTION, STRING, NULL; }
/*     */   
/*     */   private static ReturnType classifyReturnType(Method method) {
/* 303 */     Class<?> rtype = method.getReturnType();
/* 304 */     if (Exception.class.isAssignableFrom(rtype))
/* 305 */       return ReturnType.EXCEPTION; 
/* 306 */     if (rtype.equals(String.class))
/* 307 */       return ReturnType.STRING; 
/* 308 */     if (rtype.equals(void.class)) {
/* 309 */       return ReturnType.NULL;
/*     */     }
/* 311 */     throw new RuntimeException("Method " + method + " has an illegal return type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static LogRecord makeLogRecord(Level level, String key, Object[] args, boolean forceStackTrace, Logger logger) {
/* 318 */     LogRecord result = new LogRecord(level, key);
/* 319 */     if (args != null && args.length > 0) {
/* 320 */       result.setParameters(args);
/*     */     }
/*     */     
/* 323 */     result.setLoggerName(logger.getName());
/* 324 */     result.setResourceBundle(logger.getResourceBundle());
/* 325 */     if (forceStackTrace || level != Level.INFO) {
/* 326 */       inferCaller(result);
/*     */     }
/*     */     
/* 329 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ShortFormatter
/*     */     extends Formatter
/*     */   {
/*     */     public String format(LogRecord record) {
/* 338 */       StringBuilder sb = new StringBuilder();
/* 339 */       sb.append(record.getLevel().getLocalizedName());
/* 340 */       sb.append(": ");
/* 341 */       String message = formatMessage(record);
/* 342 */       sb.append(message);
/* 343 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/* 347 */   private static final ShortFormatter formatter = new ShortFormatter();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object handleFullLogging(ResourceBundle rb, Log log, Method method, Logger logger, String idPrefix, Object[] messageParams, Throwable cause) {
/* 353 */     Level level = log.level().getLevel();
/* 354 */     ReturnType rtype = classifyReturnType(method);
/* 355 */     boolean forceStackTrace = method.isAnnotationPresent((Class)StackTrace.class);
/*     */     
/* 357 */     int len = (messageParams == null) ? 0 : messageParams.length;
/* 358 */     String msgString = getMessage(rb, logger, method, len, idPrefix, log.id());
/*     */     
/* 360 */     LogRecord lrec = makeLogRecord(level, msgString, messageParams, forceStackTrace, logger);
/*     */     
/* 362 */     String message = formatter.format(lrec);
/*     */     
/* 364 */     Exception exc = makeException(rtype, forceStackTrace, method.getReturnType(), cause, message);
/*     */ 
/*     */     
/* 367 */     if (exc != null && (
/* 368 */       forceStackTrace || level != Level.INFO)) {
/* 369 */       lrec.setThrown(exc);
/*     */     }
/*     */ 
/*     */     
/* 373 */     if (logger.isLoggable(level)) {
/* 374 */       String context = OperationTracer.getAsString();
/* 375 */       String newMsg = msgString;
/* 376 */       if (context.length() > 0) {
/* 377 */         newMsg = newMsg + "\nCONTEXT:" + context;
/* 378 */         lrec.setMessage(newMsg);
/*     */       } 
/* 380 */       logger.log(lrec);
/*     */     } 
/*     */     
/* 383 */     switch (rtype) { case EXCEPTION:
/* 384 */         return exc;
/* 385 */       case STRING: return message; }
/* 386 */      return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getLoggerName(Class<?> cls) {
/* 391 */     ExceptionWrapper ew = cls.<ExceptionWrapper>getAnnotation(ExceptionWrapper.class);
/* 392 */     String str = ew.loggerName();
/* 393 */     if (str.length() == 0) {
/* 394 */       str = cls.getPackage().getName();
/*     */     }
/* 396 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> T makeWrapper(final Class<T> cls) {
/* 401 */     if (!cls.isInterface()) {
/* 402 */       throw new IllegalArgumentException("Class " + cls + "is not an interface");
/*     */     }
/*     */ 
/*     */     
/* 406 */     ExceptionWrapper ew = cls.<ExceptionWrapper>getAnnotation(ExceptionWrapper.class);
/* 407 */     final String idPrefix = ew.idPrefix();
/* 408 */     final String name = getLoggerName(cls);
/* 409 */     final ResourceBundle rb = getResourceBundle(ew.resourceBundle());
/* 410 */     InvocationHandler inh = new InvocationHandler()
/*     */       {
/*     */         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
/*     */         {
/* 414 */           Annotation[][] pannos = method.getParameterAnnotations();
/* 415 */           int chainIndex = WrapperGenerator.findAnnotatedParameter(pannos, (Class)Chain.class);
/*     */           
/* 417 */           Throwable cause = null;
/* 418 */           Object[] messageParams = WrapperGenerator.getWithSkip(args, chainIndex);
/* 419 */           if (chainIndex >= 0) {
/* 420 */             cause = (Throwable)args[chainIndex];
/*     */           }
/*     */           
/* 423 */           Logger logger = Logger.getLogger(name);
/* 424 */           Class<?> rtype = method.getReturnType();
/* 425 */           Log log = method.<Log>getAnnotation(Log.class);
/*     */           
/* 427 */           if (log == null) {
/* 428 */             if (!rtype.equals(String.class)) {
/* 429 */               throw new IllegalArgumentException("No @Log annotation present on " + cls.getName() + "." + method.getName());
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 434 */             return WrapperGenerator.handleMessageOnly(rb, method, logger, messageParams);
/*     */           } 
/*     */           
/* 437 */           return WrapperGenerator.handleFullLogging(rb, log, method, logger, idPrefix, messageParams, cause);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 444 */     ClassLoader loader = cls.getClassLoader();
/* 445 */     Class[] classes = { cls };
/* 446 */     return (T)Proxy.newProxyInstance(loader, classes, inh);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\logex\WrapperGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */