/*     */ package com.sun.xml.ws.xmlfilter;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.xmlfilter.localization.LocalizationMessages;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
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
/*     */ public final class Invocation
/*     */ {
/*  62 */   private static final Logger LOGGER = Logger.getLogger(Invocation.class);
/*     */ 
/*     */   
/*     */   private final Method method;
/*     */ 
/*     */   
/*     */   private final Object[] arguments;
/*     */ 
/*     */   
/*     */   private String argsString;
/*     */ 
/*     */   
/*     */   private final XmlStreamWriterMethodType methodType;
/*     */ 
/*     */   
/*     */   private final boolean returnsVoid;
/*     */ 
/*     */   
/*     */   public static Invocation createInvocation(Method method, Object[] args) {
/*     */     Object[] arguments;
/*  82 */     XmlStreamWriterMethodType methodType = XmlStreamWriterMethodType.getMethodType(method.getName());
/*     */     
/*  84 */     if (methodType == XmlStreamWriterMethodType.WRITE_CHARACTERS && args.length == 3) {
/*  85 */       Integer start = (Integer)args[1];
/*  86 */       Integer length = (Integer)args[2];
/*  87 */       char[] charArrayCopy = new char[length.intValue()];
/*  88 */       System.arraycopy(args[0], start.intValue(), charArrayCopy, 0, length.intValue());
/*     */       
/*  90 */       arguments = new Object[3];
/*  91 */       arguments[0] = charArrayCopy;
/*  92 */       arguments[1] = Integer.valueOf(0);
/*  93 */       arguments[2] = length;
/*     */     } else {
/*  95 */       arguments = args;
/*     */     } 
/*     */     
/*  98 */     return new Invocation(method, methodType, arguments);
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
/*     */   public static void executeBatch(XMLStreamWriter target, Queue<Invocation> batch) throws InvocationProcessingException {
/* 117 */     for (Invocation invocation : batch) {
/* 118 */       if (!invocation.returnsVoid) {
/* 119 */         throw (InvocationProcessingException)LOGGER.logSevereException(new InvocationProcessingException("Cannot batch-execute invocation with non-void return type: '" + invocation.getMethodName() + "'"));
/*     */       }
/*     */     } 
/*     */     
/* 123 */     while (!batch.isEmpty()) {
/* 124 */       ((Invocation)batch.poll()).execute(target);
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
/*     */   private Invocation(Method method, XmlStreamWriterMethodType type, Object[] args) {
/* 140 */     this.method = method;
/* 141 */     this.arguments = args;
/* 142 */     this.methodType = type;
/* 143 */     this.returnsVoid = void.class.isAssignableFrom(method.getReturnType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMethodName() {
/* 152 */     return this.method.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XmlStreamWriterMethodType getMethodType() {
/* 162 */     return this.methodType;
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
/*     */   public Object getArgument(int index) throws ArrayIndexOutOfBoundsException {
/* 177 */     if (this.arguments == null) {
/* 178 */       throw (ArrayIndexOutOfBoundsException)LOGGER.logSevereException(new ArrayIndexOutOfBoundsException(LocalizationMessages.XMLF_5019_NO_ARGUMENTS_IN_INVOCATION(toString())));
/*     */     }
/* 180 */     return this.arguments[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArgumentsCount() {
/* 190 */     return (this.arguments == null) ? 0 : this.arguments.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object execute(XMLStreamWriter target) throws InvocationProcessingException {
/*     */     try {
/* 201 */       return this.method.invoke(target, this.arguments);
/* 202 */     } catch (IllegalArgumentException e) {
/* 203 */       throw (InvocationProcessingException)LOGGER.logSevereException(new InvocationProcessingException(this, e));
/* 204 */     } catch (InvocationTargetException e) {
/* 205 */       throw (InvocationProcessingException)LOGGER.logSevereException(new InvocationProcessingException(this, e.getCause()));
/* 206 */     } catch (IllegalAccessException e) {
/* 207 */       throw (InvocationProcessingException)LOGGER.logSevereException(new InvocationProcessingException(this, e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 218 */     StringBuffer retValue = new StringBuffer(30);
/* 219 */     retValue.append("invocation { method='").append(this.method.getName()).append("', args=").append(argsToString());
/* 220 */     retValue.append('}');
/*     */     
/* 222 */     return retValue.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String argsToString() {
/* 233 */     if (this.argsString == null) {
/* 234 */       List<Object> argList = null;
/* 235 */       if (this.arguments != null && this.arguments.length > 0) {
/* 236 */         if (this.arguments.length == 3 && "writeCharacters".equals(this.method.getName())) {
/* 237 */           argList = new ArrayList(3);
/* 238 */           argList.add(new String((char[])this.arguments[0]));
/* 239 */           argList.add(this.arguments[1]);
/* 240 */           argList.add(this.arguments[2]);
/*     */         } else {
/* 242 */           argList = Arrays.asList(this.arguments);
/*     */         } 
/*     */       }
/* 245 */       this.argsString = (argList == null) ? "no arguments" : argList.toString();
/*     */     } 
/*     */     
/* 248 */     return this.argsString;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\xmlfilter\Invocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */