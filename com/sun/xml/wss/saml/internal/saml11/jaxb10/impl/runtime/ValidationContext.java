/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.ProxyGroup;
/*     */ import com.sun.xml.bind.serializer.AbortSerializationException;
/*     */ import com.sun.xml.bind.validator.Messages;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.helpers.NotIdentifiableEventImpl;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
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
/*     */ 
/*     */ 
/*     */ class ValidationContext
/*     */ {
/*     */   final DefaultJAXBContextImpl jaxbContext;
/*     */   private final IdentityHashSet validatedObjects;
/*     */   private final NamespaceContextImpl nsContext;
/*     */   private final boolean validateID;
/*     */   private final HashSet IDs;
/*     */   private final HashMap IDREFs;
/*     */   private final ValidationEventHandler eventHandler;
/*     */   
/*     */   public void validate(ValidatableObject vo) throws SAXException {
/*     */     if (this.validatedObjects.add(ProxyGroup.unwrap(vo))) {
/*     */       MSVValidator.validate(this.jaxbContext, this, vo);
/*     */     } else {
/*     */       reportEvent(vo, Messages.format("ValidationContext.CycleDetected"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public NamespaceContextImpl getNamespaceContext() {
/*     */     return this.nsContext;
/*     */   }
/*     */   
/*     */   ValidationContext(DefaultJAXBContextImpl _context, ValidationEventHandler _eventHandler, boolean validateID) {
/*  66 */     this.validatedObjects = new IdentityHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     this.nsContext = new NamespaceContextImpl(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     this.IDs = new HashSet();
/* 104 */     this.IDREFs = new HashMap();
/*     */     this.jaxbContext = _context;
/*     */     this.eventHandler = _eventHandler;
/*     */     this.validateID = validateID; } public String onID(XMLSerializable owner, String value) throws SAXException {
/* 108 */     if (!this.validateID) return value;
/*     */     
/* 110 */     if (!this.IDs.add(value))
/*     */     {
/*     */ 
/*     */       
/* 114 */       reportEvent(this.jaxbContext.getGrammarInfo().castToValidatableObject(owner), Messages.format("ValidationContext.DuplicateId", value));
/*     */     }
/*     */ 
/*     */     
/* 118 */     return value;
/*     */   }
/*     */   public String onIDREF(XMLSerializable referer, String value) throws SAXException {
/* 121 */     if (!this.validateID) return value;
/*     */     
/* 123 */     if (this.IDs.contains(value)) {
/* 124 */       return value;
/*     */     }
/*     */     
/* 127 */     this.IDREFs.put(value, referer);
/*     */     
/* 129 */     return value;
/*     */   }
/*     */   
/*     */   protected void reconcileIDs() throws SAXException {
/* 133 */     if (!this.validateID)
/*     */       return; 
/* 135 */     for (Iterator itr = this.IDREFs.entrySet().iterator(); itr.hasNext(); ) {
/* 136 */       Map.Entry e = itr.next();
/*     */       
/* 138 */       if (this.IDs.contains(e.getKey())) {
/*     */         continue;
/*     */       }
/*     */       
/* 142 */       ValidatableObject source = (ValidatableObject)e.getValue();
/* 143 */       reportEvent(source, new NotIdentifiableEventImpl(1, Messages.format("ValidationContext.IdNotFound", e.getKey()), new ValidationEventLocatorImpl(source)));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     this.IDREFs.clear();
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
/*     */   public void reportEvent(ValidatableObject source, String formattedMessage) throws AbortSerializationException {
/* 166 */     reportEvent(source, new ValidationEventImpl(1, formattedMessage, new ValidationEventLocatorImpl(source)));
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
/*     */   public void reportEvent(ValidatableObject source, Exception nestedException) throws AbortSerializationException {
/* 178 */     reportEvent(source, new ValidationEventImpl(1, nestedException.toString(), new ValidationEventLocatorImpl(source), nestedException));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reportEvent(ValidatableObject source, ValidationEvent event) throws AbortSerializationException {
/*     */     boolean bool;
/*     */     try {
/* 190 */       bool = this.eventHandler.handleEvent(event);
/* 191 */     } catch (RuntimeException re) {
/*     */ 
/*     */       
/* 194 */       bool = false;
/*     */     } 
/*     */     
/* 197 */     if (!bool)
/*     */     {
/* 199 */       throw new AbortSerializationException(event.getMessage());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\ValidationContext.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */