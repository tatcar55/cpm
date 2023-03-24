/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.DoNotCacheConditionType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class DoNotCacheConditionTypeImpl extends ConditionAbstractTypeImpl implements DoNotCacheConditionType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return DoNotCacheConditionType.class;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  24 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  30 */     super.serializeBody(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  36 */     super.serializeAttributes(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  42 */     super.serializeURIs(context);
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  46 */     return DoNotCacheConditionType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  50 */     if (schemaFragment == null) {
/*  51 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpt\000 Lcom/sun/msv/grammar/Expression;xpsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\001q\000~\000\004sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\000\001px");
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
/*  62 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final DoNotCacheConditionTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/*  71 */       super(context, "--");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/*  75 */       this(context);
/*  76 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/*  80 */       return DoNotCacheConditionTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*  89 */       switch (this.state) {
/*     */         case 1:
/*  91 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/*  94 */           DoNotCacheConditionTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ConditionAbstractTypeImpl.Unmarshaller(DoNotCacheConditionTypeImpl.this, this.context), 1, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/*  97 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/* 108 */       switch (this.state) {
/*     */         case 1:
/* 110 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 0:
/* 113 */           DoNotCacheConditionTypeImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new ConditionAbstractTypeImpl.Unmarshaller(DoNotCacheConditionTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 116 */       super.leaveElement(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 127 */       switch (this.state) {
/*     */         case 1:
/* 129 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 0:
/* 132 */           DoNotCacheConditionTypeImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ConditionAbstractTypeImpl.Unmarshaller(DoNotCacheConditionTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 135 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 146 */       switch (this.state) {
/*     */         case 1:
/* 148 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 0:
/* 151 */           DoNotCacheConditionTypeImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new ConditionAbstractTypeImpl.Unmarshaller(DoNotCacheConditionTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 154 */       super.leaveAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleText(String value) throws SAXException {
/*     */       try {
/* 166 */         switch (this.state) {
/*     */           case 1:
/* 168 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 0:
/* 171 */             DoNotCacheConditionTypeImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new ConditionAbstractTypeImpl.Unmarshaller(DoNotCacheConditionTypeImpl.this, this.context), 1, value);
/*     */             return;
/*     */         } 
/* 174 */       } catch (RuntimeException e) {
/* 175 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\DoNotCacheConditionTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */