/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.msv.grammar.Grammar;
/*     */ import com.sun.xml.bind.DatatypeConverterImpl;
/*     */ import com.sun.xml.bind.Messages;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.PropertyException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.Validator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultJAXBContextImpl
/*     */   extends JAXBContext
/*     */ {
/*  36 */   private GrammarInfo gi = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Grammar grammar;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultJAXBContextImpl(String contextPath, ClassLoader classLoader) throws JAXBException {
/*  47 */     this(GrammarInfoFacade.createGrammarInfoFacade(contextPath, classLoader));
/*     */ 
/*     */     
/*  50 */     DatatypeConverter.setDatatypeConverter(DatatypeConverterImpl.theInstance);
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
/*     */   public GrammarInfo getGrammarInfo() {
/*  63 */     return this.gi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultJAXBContextImpl(GrammarInfo gi) {
/*  71 */     this.grammar = null;
/*     */     this.gi = gi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Grammar getGrammar() throws JAXBException {
/*  81 */     if (this.grammar == null)
/*  82 */       this.grammar = this.gi.getGrammar(); 
/*  83 */     return this.grammar;
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
/*     */   public Marshaller createMarshaller() throws JAXBException {
/*  96 */     return new MarshallerImpl(this);
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
/*     */   public Unmarshaller createUnmarshaller() throws JAXBException {
/* 108 */     return new UnmarshallerImpl(this, this.gi);
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
/*     */   public Validator createValidator() throws JAXBException {
/* 120 */     return new ValidatorImpl(this);
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
/*     */   public Object newInstance(Class javaContentInterface) throws JAXBException {
/* 135 */     if (javaContentInterface == null) {
/* 136 */       throw new JAXBException(Messages.format("DefaultJAXBContextImpl.CINotNull"));
/*     */     }
/*     */     
/*     */     try {
/* 140 */       Class c = this.gi.getDefaultImplementation(javaContentInterface);
/* 141 */       if (c == null) {
/* 142 */         throw new JAXBException(Messages.format("ImplementationRegistry.MissingInterface", javaContentInterface));
/*     */       }
/*     */       
/* 145 */       return c.newInstance();
/* 146 */     } catch (Exception e) {
/* 147 */       throw new JAXBException(e);
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
/* 158 */     throw new PropertyException(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws PropertyException {
/* 168 */     throw new PropertyException(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\DefaultJAXBContextImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */