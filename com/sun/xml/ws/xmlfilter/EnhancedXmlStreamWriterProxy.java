/*     */ package com.sun.xml.ws.xmlfilter;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.xmlfilter.localization.LocalizationMessages;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EnhancedXmlStreamWriterProxy
/*     */   implements InvocationHandler
/*     */ {
/*  65 */   private static final Logger LOGGER = Logger.getLogger(EnhancedXmlStreamWriterProxy.class);
/*     */   
/*  67 */   private static final Class<?>[] PROXIED_INTERFACES = new Class[] { XMLStreamWriter.class };
/*     */   private static final Method hashCodeMethod;
/*     */   private static final Method equalsMethod;
/*     */   private static final Method toStringMethod;
/*     */   private final InvocationProcessor invocationProcessor;
/*     */   
/*     */   static {
/*     */     try {
/*  75 */       hashCodeMethod = Object.class.getMethod("hashCode", new Class[0]);
/*  76 */       equalsMethod = Object.class.getMethod("equals", new Class[] { Object.class });
/*  77 */       toStringMethod = Object.class.getMethod("toString", new Class[0]);
/*  78 */     } catch (NoSuchMethodException e) {
/*  79 */       throw (NoSuchMethodError)LOGGER.logSevereException(new NoSuchMethodError(e.getMessage()), e);
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
/*     */   public static XMLStreamWriter createProxy(XMLStreamWriter writer, InvocationProcessorFactory processorFactory) throws XMLStreamException {
/* 100 */     LOGGER.entering();
/*     */     
/* 102 */     XMLStreamWriter proxy = null;
/*     */     try {
/* 104 */       proxy = (XMLStreamWriter)Proxy.newProxyInstance(writer.getClass().getClassLoader(), PROXIED_INTERFACES, new EnhancedXmlStreamWriterProxy(writer, processorFactory));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 109 */       return proxy;
/*     */     } finally {
/* 111 */       LOGGER.exiting(proxy);
/*     */     } 
/*     */   }
/*     */   
/*     */   private EnhancedXmlStreamWriterProxy(XMLStreamWriter writer, InvocationProcessorFactory processorFactory) throws XMLStreamException {
/* 116 */     this.invocationProcessor = processorFactory.createInvocationProcessor(writer);
/*     */   }
/*     */   
/*     */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 120 */     if (LOGGER.isMethodCallLoggable()) {
/* 121 */       LOGGER.entering(new Object[] { method, args });
/*     */     }
/*     */     
/* 124 */     Object result = null;
/*     */     try {
/* 126 */       Class<?> declaringClass = method.getDeclaringClass();
/* 127 */       if (declaringClass == Object.class) {
/* 128 */         return handleObjectMethodCall(proxy, method, args);
/*     */       }
/* 130 */       Invocation invocation = Invocation.createInvocation(method, args);
/* 131 */       result = this.invocationProcessor.process(invocation);
/* 132 */       return result;
/*     */     } finally {
/*     */       
/* 135 */       LOGGER.exiting(result);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Object handleObjectMethodCall(Object proxy, Method method, Object[] args) {
/* 140 */     if (method.equals(hashCodeMethod))
/* 141 */       return Integer.valueOf(System.identityHashCode(proxy)); 
/* 142 */     if (method.equals(equalsMethod))
/* 143 */       return (proxy == args[0]) ? Boolean.TRUE : Boolean.FALSE; 
/* 144 */     if (method.equals(toStringMethod)) {
/* 145 */       return proxy.getClass().getName() + '@' + Integer.toHexString(proxy.hashCode());
/*     */     }
/* 147 */     throw (InternalError)LOGGER.logSevereException(new InternalError(LocalizationMessages.XMLF_5002_UNEXPECTED_OBJECT_METHOD(method)));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\xmlfilter\EnhancedXmlStreamWriterProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */