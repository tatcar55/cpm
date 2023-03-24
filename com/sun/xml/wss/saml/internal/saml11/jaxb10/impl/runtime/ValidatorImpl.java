/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.bind.validator.Messages;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.ValidationException;
/*     */ import javax.xml.bind.Validator;
/*     */ import javax.xml.bind.helpers.DefaultValidationEventHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValidatorImpl
/*     */   implements Validator
/*     */ {
/*  41 */   private ValidationEventHandler eventHandler = new DefaultValidationEventHandler();
/*     */ 
/*     */   
/*     */   final DefaultJAXBContextImpl jaxbContext;
/*     */ 
/*     */   
/*     */   public ValidatorImpl(DefaultJAXBContextImpl c) {
/*  48 */     DatatypeConverter.setDatatypeConverter(DatatypeConverterImpl.theInstance);
/*     */     
/*  50 */     this.jaxbContext = c;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EventInterceptor
/*     */     implements ValidationEventHandler
/*     */   {
/*     */     private boolean hadError;
/*     */     
/*     */     private final ValidationEventHandler next;
/*     */     
/*     */     EventInterceptor(ValidationEventHandler _next) {
/*  62 */       this.hadError = false;
/*  63 */       this.next = _next; } public boolean hadError() { return this.hadError; }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean handleEvent(ValidationEvent e) {
/*     */       boolean result;
/*  69 */       this.hadError = true;
/*     */       
/*  71 */       if (this.next != null) {
/*     */         
/*     */         try {
/*  74 */           result = this.next.handleEvent(e);
/*  75 */         } catch (RuntimeException re) {
/*     */ 
/*     */           
/*  78 */           result = false;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  83 */         result = false;
/*     */       } 
/*  85 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean validateRoot(Object o) throws ValidationException {
/*  90 */     if (o == null) {
/*  91 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "rootObj"));
/*     */     }
/*     */ 
/*     */     
/*  95 */     return validate(o, true);
/*     */   }
/*     */   
/*     */   public boolean validate(Object o) throws ValidationException {
/*  99 */     if (o == null) {
/* 100 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "subrootObj"));
/*     */     }
/*     */ 
/*     */     
/* 104 */     return validate(o, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validate(Object o, boolean validateId) throws ValidationException {
/*     */     try {
/* 113 */       ValidatableObject vo = this.jaxbContext.getGrammarInfo().castToValidatableObject(o);
/*     */       
/* 115 */       if (vo == null) {
/* 116 */         throw new ValidationException(Messages.format("Validator.NotValidatable"));
/*     */       }
/*     */       
/* 119 */       EventInterceptor ei = new EventInterceptor(this.eventHandler);
/* 120 */       ValidationContext context = new ValidationContext(this.jaxbContext, ei, validateId);
/* 121 */       context.validate(vo);
/* 122 */       context.reconcileIDs();
/*     */       
/* 124 */       return !ei.hadError();
/* 125 */     } catch (SAXException e) {
/*     */       
/* 127 */       Exception nested = e.getException();
/* 128 */       if (nested != null) {
/* 129 */         throw new ValidationException(nested);
/*     */       }
/* 131 */       throw new ValidationException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidationEventHandler getEventHandler() {
/* 138 */     return this.eventHandler;
/*     */   }
/*     */   
/*     */   public void setEventHandler(ValidationEventHandler handler) {
/* 142 */     if (handler == null) {
/* 143 */       this.eventHandler = new DefaultValidationEventHandler();
/*     */     } else {
/* 145 */       this.eventHandler = handler;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws PropertyException {
/* 156 */     if (name == null) {
/* 157 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "name"));
/*     */     }
/*     */ 
/*     */     
/* 161 */     throw new PropertyException(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws PropertyException {
/* 171 */     if (name == null) {
/* 172 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "name"));
/*     */     }
/*     */ 
/*     */     
/* 176 */     throw new PropertyException(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\ValidatorImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */