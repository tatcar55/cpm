/*     */ package com.sun.xml.ws.util.exception;
/*     */ 
/*     */ import com.sun.istack.localization.Localizable;
/*     */ import com.sun.istack.localization.LocalizableMessage;
/*     */ import com.sun.istack.localization.LocalizableMessageFactory;
/*     */ import com.sun.istack.localization.Localizer;
/*     */ import com.sun.istack.localization.NullLocalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JAXWSExceptionBase
/*     */   extends WebServiceException
/*     */   implements Localizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Localizable msg;
/*     */   
/*     */   protected JAXWSExceptionBase(String key, Object... args) {
/*  73 */     super(findNestedException(args));
/*  74 */     this.msg = (Localizable)new LocalizableMessage(getDefaultResourceBundleName(), key, args);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JAXWSExceptionBase(String message) {
/*  79 */     this((Localizable)new NullLocalizable(message));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXWSExceptionBase(Throwable throwable) {
/*  86 */     this((Localizable)new NullLocalizable(throwable.toString()), throwable);
/*     */   }
/*     */   
/*     */   protected JAXWSExceptionBase(Localizable msg) {
/*  90 */     this.msg = msg;
/*     */   }
/*     */   
/*     */   protected JAXWSExceptionBase(Localizable msg, Throwable cause) {
/*  94 */     super(cause);
/*  95 */     this.msg = msg;
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
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 108 */     out.defaultWriteObject();
/*     */     
/* 110 */     out.writeObject(this.msg.getResourceBundleName());
/* 111 */     out.writeObject(this.msg.getKey());
/* 112 */     Object[] args = this.msg.getArguments();
/* 113 */     if (args == null) {
/* 114 */       out.writeInt(-1);
/*     */       return;
/*     */     } 
/* 117 */     out.writeInt(args.length);
/*     */     
/* 119 */     for (int i = 0; i < args.length; i++) {
/* 120 */       if (args[i] == null || args[i] instanceof java.io.Serializable) {
/* 121 */         out.writeObject(args[i]);
/*     */       } else {
/* 123 */         out.writeObject(args[i].toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/*     */     Object[] args;
/* 130 */     in.defaultReadObject();
/*     */     
/* 132 */     String resourceBundleName = (String)in.readObject();
/* 133 */     String key = (String)in.readObject();
/* 134 */     int len = in.readInt();
/* 135 */     if (len == -1) {
/* 136 */       args = null;
/*     */     } else {
/* 138 */       args = new Object[len];
/* 139 */       for (int i = 0; i < args.length; i++) {
/* 140 */         args[i] = in.readObject();
/*     */       }
/*     */     } 
/* 143 */     this.msg = (new LocalizableMessageFactory(resourceBundleName)).getMessage(key, args);
/*     */   }
/*     */   
/*     */   private static Throwable findNestedException(Object[] args) {
/* 147 */     if (args == null) {
/* 148 */       return null;
/*     */     }
/* 150 */     for (Object o : args) {
/* 151 */       if (o instanceof Throwable)
/* 152 */         return (Throwable)o; 
/* 153 */     }  return null;
/*     */   }
/*     */   
/*     */   public String getMessage() {
/* 157 */     Localizer localizer = new Localizer();
/* 158 */     return localizer.localize(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getDefaultResourceBundleName();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getKey() {
/* 171 */     return this.msg.getKey();
/*     */   }
/*     */   
/*     */   public final Object[] getArguments() {
/* 175 */     return this.msg.getArguments();
/*     */   }
/*     */   
/*     */   public final String getResourceBundleName() {
/* 179 */     return this.msg.getResourceBundleName();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\exception\JAXWSExceptionBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */