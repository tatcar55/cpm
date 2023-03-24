/*     */ package com.sun.istack.logging;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Logger
/*     */ {
/*     */   private static final String WS_LOGGING_SUBSYSTEM_NAME_ROOT = "com.sun.metro";
/*     */   private static final String ROOT_WS_PACKAGE = "com.sun.xml.ws.";
/*  63 */   private static final Level METHOD_CALL_LEVEL_VALUE = Level.FINEST;
/*     */ 
/*     */   
/*     */   private final String componentClassName;
/*     */   
/*     */   private final java.util.logging.Logger logger;
/*     */ 
/*     */   
/*     */   protected Logger(String systemLoggerName, String componentName) {
/*  72 */     this.componentClassName = "[" + componentName + "] ";
/*  73 */     this.logger = java.util.logging.Logger.getLogger(systemLoggerName);
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
/*     */   @NotNull
/*     */   public static Logger getLogger(@NotNull Class<?> componentClass) {
/*  92 */     return new Logger(getSystemLoggerName(componentClass), componentClass.getName());
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
/*     */   @NotNull
/*     */   public static Logger getLogger(@NotNull String customLoggerName, @NotNull Class<?> componentClass) {
/* 112 */     return new Logger(customLoggerName, componentClass.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String getSystemLoggerName(@NotNull Class<?> componentClass) {
/* 121 */     StringBuilder sb = new StringBuilder(componentClass.getPackage().getName());
/* 122 */     int lastIndexOfWsPackage = sb.lastIndexOf("com.sun.xml.ws.");
/* 123 */     if (lastIndexOfWsPackage > -1) {
/* 124 */       sb.replace(0, lastIndexOfWsPackage + "com.sun.xml.ws.".length(), "");
/*     */       
/* 126 */       StringTokenizer st = new StringTokenizer(sb.toString(), ".");
/* 127 */       sb = (new StringBuilder("com.sun.metro")).append(".");
/* 128 */       if (st.hasMoreTokens()) {
/* 129 */         String token = st.nextToken();
/* 130 */         if ("api".equals(token)) {
/* 131 */           token = st.nextToken();
/*     */         }
/* 133 */         sb.append(token);
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public void log(Level level, String message) {
/* 141 */     if (!this.logger.isLoggable(level)) {
/*     */       return;
/*     */     }
/* 144 */     this.logger.logp(level, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void log(Level level, String message, Object param1) {
/* 148 */     if (!this.logger.isLoggable(level)) {
/*     */       return;
/*     */     }
/* 151 */     this.logger.logp(level, this.componentClassName, getCallerMethodName(), message, param1);
/*     */   }
/*     */   
/*     */   public void log(Level level, String message, Object[] params) {
/* 155 */     if (!this.logger.isLoggable(level)) {
/*     */       return;
/*     */     }
/* 158 */     this.logger.logp(level, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void log(Level level, String message, Throwable thrown) {
/* 162 */     if (!this.logger.isLoggable(level)) {
/*     */       return;
/*     */     }
/* 165 */     this.logger.logp(level, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void finest(String message) {
/* 169 */     if (!this.logger.isLoggable(Level.FINEST)) {
/*     */       return;
/*     */     }
/* 172 */     this.logger.logp(Level.FINEST, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void finest(String message, Object[] params) {
/* 176 */     if (!this.logger.isLoggable(Level.FINEST)) {
/*     */       return;
/*     */     }
/* 179 */     this.logger.logp(Level.FINEST, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void finest(String message, Throwable thrown) {
/* 183 */     if (!this.logger.isLoggable(Level.FINEST)) {
/*     */       return;
/*     */     }
/* 186 */     this.logger.logp(Level.FINEST, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void finer(String message) {
/* 190 */     if (!this.logger.isLoggable(Level.FINER)) {
/*     */       return;
/*     */     }
/* 193 */     this.logger.logp(Level.FINER, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void finer(String message, Object[] params) {
/* 197 */     if (!this.logger.isLoggable(Level.FINER)) {
/*     */       return;
/*     */     }
/* 200 */     this.logger.logp(Level.FINER, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void finer(String message, Throwable thrown) {
/* 204 */     if (!this.logger.isLoggable(Level.FINER)) {
/*     */       return;
/*     */     }
/* 207 */     this.logger.logp(Level.FINER, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void fine(String message) {
/* 211 */     if (!this.logger.isLoggable(Level.FINE)) {
/*     */       return;
/*     */     }
/* 214 */     this.logger.logp(Level.FINE, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void fine(String message, Throwable thrown) {
/* 218 */     if (!this.logger.isLoggable(Level.FINE)) {
/*     */       return;
/*     */     }
/* 221 */     this.logger.logp(Level.FINE, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void info(String message) {
/* 225 */     if (!this.logger.isLoggable(Level.INFO)) {
/*     */       return;
/*     */     }
/* 228 */     this.logger.logp(Level.INFO, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void info(String message, Object[] params) {
/* 232 */     if (!this.logger.isLoggable(Level.INFO)) {
/*     */       return;
/*     */     }
/* 235 */     this.logger.logp(Level.INFO, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void info(String message, Throwable thrown) {
/* 239 */     if (!this.logger.isLoggable(Level.INFO)) {
/*     */       return;
/*     */     }
/* 242 */     this.logger.logp(Level.INFO, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void config(String message) {
/* 246 */     if (!this.logger.isLoggable(Level.CONFIG)) {
/*     */       return;
/*     */     }
/* 249 */     this.logger.logp(Level.CONFIG, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void config(String message, Object[] params) {
/* 253 */     if (!this.logger.isLoggable(Level.CONFIG)) {
/*     */       return;
/*     */     }
/* 256 */     this.logger.logp(Level.CONFIG, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void config(String message, Throwable thrown) {
/* 260 */     if (!this.logger.isLoggable(Level.CONFIG)) {
/*     */       return;
/*     */     }
/* 263 */     this.logger.logp(Level.CONFIG, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void warning(String message) {
/* 267 */     if (!this.logger.isLoggable(Level.WARNING)) {
/*     */       return;
/*     */     }
/* 270 */     this.logger.logp(Level.WARNING, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void warning(String message, Object[] params) {
/* 274 */     if (!this.logger.isLoggable(Level.WARNING)) {
/*     */       return;
/*     */     }
/* 277 */     this.logger.logp(Level.WARNING, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void warning(String message, Throwable thrown) {
/* 281 */     if (!this.logger.isLoggable(Level.WARNING)) {
/*     */       return;
/*     */     }
/* 284 */     this.logger.logp(Level.WARNING, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void severe(String message) {
/* 288 */     if (!this.logger.isLoggable(Level.SEVERE)) {
/*     */       return;
/*     */     }
/* 291 */     this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void severe(String message, Object[] params) {
/* 295 */     if (!this.logger.isLoggable(Level.SEVERE)) {
/*     */       return;
/*     */     }
/* 298 */     this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void severe(String message, Throwable thrown) {
/* 302 */     if (!this.logger.isLoggable(Level.SEVERE)) {
/*     */       return;
/*     */     }
/* 305 */     this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public boolean isMethodCallLoggable() {
/* 309 */     return this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE);
/*     */   }
/*     */   
/*     */   public boolean isLoggable(Level level) {
/* 313 */     return this.logger.isLoggable(level);
/*     */   }
/*     */   
/*     */   public void setLevel(Level level) {
/* 317 */     this.logger.setLevel(level);
/*     */   }
/*     */   
/*     */   public void entering() {
/* 321 */     if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
/*     */       return;
/*     */     }
/*     */     
/* 325 */     this.logger.entering(this.componentClassName, getCallerMethodName());
/*     */   }
/*     */   
/*     */   public void entering(Object... parameters) {
/* 329 */     if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
/*     */       return;
/*     */     }
/*     */     
/* 333 */     this.logger.entering(this.componentClassName, getCallerMethodName(), parameters);
/*     */   }
/*     */   
/*     */   public void exiting() {
/* 337 */     if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
/*     */       return;
/*     */     }
/* 340 */     this.logger.exiting(this.componentClassName, getCallerMethodName());
/*     */   }
/*     */   
/*     */   public void exiting(Object result) {
/* 344 */     if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
/*     */       return;
/*     */     }
/* 347 */     this.logger.exiting(this.componentClassName, getCallerMethodName(), result);
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
/*     */   public <T extends Throwable> T logSevereException(T exception, Throwable cause) {
/* 366 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 367 */       if (cause == null) {
/* 368 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } else {
/* 370 */         exception.initCause(cause);
/* 371 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage(), cause);
/*     */       } 
/*     */     }
/*     */     
/* 375 */     return exception;
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
/*     */   public <T extends Throwable> T logSevereException(T exception, boolean logCause) {
/* 397 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 398 */       if (logCause && exception.getCause() != null) {
/* 399 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
/*     */       } else {
/* 401 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 405 */     return exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Throwable> T logSevereException(T exception) {
/* 412 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 413 */       if (exception.getCause() == null) {
/* 414 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } else {
/* 416 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
/*     */       } 
/*     */     }
/*     */     
/* 420 */     return exception;
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
/*     */   public <T extends Throwable> T logException(T exception, Throwable cause, Level level) {
/* 440 */     if (this.logger.isLoggable(level)) {
/* 441 */       if (cause == null) {
/* 442 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } else {
/* 444 */         exception.initCause(cause);
/* 445 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage(), cause);
/*     */       } 
/*     */     }
/*     */     
/* 449 */     return exception;
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
/*     */   public <T extends Throwable> T logException(T exception, boolean logCause, Level level) {
/* 472 */     if (this.logger.isLoggable(level)) {
/* 473 */       if (logCause && exception.getCause() != null) {
/* 474 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
/*     */       } else {
/* 476 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 480 */     return exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Throwable> T logException(T exception, Level level) {
/* 488 */     if (this.logger.isLoggable(level)) {
/* 489 */       if (exception.getCause() == null) {
/* 490 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } else {
/* 492 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
/*     */       } 
/*     */     }
/*     */     
/* 496 */     return exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getCallerMethodName() {
/* 506 */     return getStackMethodName(5);
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
/*     */   private static String getStackMethodName(int methodIndexInStack) {
/*     */     String methodName;
/* 520 */     StackTraceElement[] stack = Thread.currentThread().getStackTrace();
/* 521 */     if (stack.length > methodIndexInStack + 1) {
/* 522 */       methodName = stack[methodIndexInStack].getMethodName();
/*     */     } else {
/* 524 */       methodName = "UNKNOWN METHOD";
/*     */     } 
/*     */     
/* 527 */     return methodName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\logging\Logger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */