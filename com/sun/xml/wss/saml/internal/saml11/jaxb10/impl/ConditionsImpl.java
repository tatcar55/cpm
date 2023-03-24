/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.Conditions;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ConditionsImpl extends ConditionsTypeImpl implements Conditions, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return Conditions.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "Conditions";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions");
/*  39 */     super.serializeURIs(context);
/*  40 */     context.endNamespaceDecls();
/*  41 */     super.serializeAttributes(context);
/*  42 */     context.endAttributes();
/*  43 */     super.serializeBody(context);
/*  44 */     context.endElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  58 */     return Conditions.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\fq\000~\000\022psq\000~\000\fq\000~\000\022psq\000~\000\000q\000~\000\022p\000sq\000~\000\fppsq\000~\000\016q\000~\000\022psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\022psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\021\001q\000~\000\033sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\034q\000~\000!sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000#xq\000~\000\036t\000Hcom.sun.xml.wss.saml.internal.saml11.jaxb10.AudienceRestrictionConditiont\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000q\000~\000\022p\000sq\000~\000\fppsq\000~\000\016q\000~\000\022psq\000~\000\030q\000~\000\022pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\000?com.sun.xml.wss.saml.internal.saml11.jaxb10.DoNotCacheConditionq\000~\000&sq\000~\000\000q\000~\000\022p\000sq\000~\000\fppsq\000~\000\016q\000~\000\022psq\000~\000\030q\000~\000\022pq\000~\000\033q\000~\000\037q\000~\000!sq\000~\000\"t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Conditionq\000~\000&q\000~\000!sq\000~\000\fppsq\000~\000\030q\000~\000\022psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000%com.sun.msv.datatype.xsd.DateTimeType\000\000\000\000\000\000\000\001\002\000\000xr\000)com.sun.msv.datatype.xsd.DateTimeBaseType\024W\032@3¥´å\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000#L\000\btypeNameq\000~\000#L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\bdateTimesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\022psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000#L\000\fnamespaceURIq\000~\000#xpq\000~\000Aq\000~\000@sq\000~\000\"t\000\tNotBeforet\000\000q\000~\000!sq\000~\000\fppsq\000~\000\030q\000~\000\022pq\000~\0008sq\000~\000\"t\000\fNotOnOrAfterq\000~\000Kq\000~\000!sq\000~\000\fppsq\000~\000\030q\000~\000\022psq\000~\0005ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000;q\000~\000@t\000\005QNameq\000~\000Dq\000~\000Fsq\000~\000Gq\000~\000Uq\000~\000@sq\000~\000\"t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000!sq\000~\000\"t\000\nConditionst\000%urn:oasis:names:tc:SAML:1.0:assertionsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\020\001pq\000~\000\026q\000~\000(q\000~\000.q\000~\000\023q\000~\000\027q\000~\000)q\000~\000/q\000~\000\rq\000~\000\013q\000~\000\tq\000~\000Lq\000~\000\nq\000~\000Pq\000~\000\020q\000~\000\024q\000~\0003x");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final ConditionsImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 126 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 130 */       this(context);
/* 131 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 135 */       return ConditionsImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 144 */       switch (this.state) {
/*     */         case 0:
/* 146 */           if ("Conditions" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 147 */             this.context.pushAttributes(__atts, false);
/* 148 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 153 */           attIdx = this.context.getAttribute("", "NotBefore");
/* 154 */           if (attIdx >= 0) {
/* 155 */             this.context.consumeAttribute(attIdx);
/* 156 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 159 */           attIdx = this.context.getAttribute("", "NotOnOrAfter");
/* 160 */           if (attIdx >= 0) {
/* 161 */             this.context.consumeAttribute(attIdx);
/* 162 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 165 */           if ("AudienceRestrictionCondition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 166 */             ConditionsImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ConditionsTypeImpl.Unmarshaller(ConditionsImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 169 */           if ("DoNotCacheCondition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 170 */             ConditionsImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ConditionsTypeImpl.Unmarshaller(ConditionsImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 173 */           if ("Condition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 174 */             ConditionsImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ConditionsTypeImpl.Unmarshaller(ConditionsImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 177 */           ConditionsImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ConditionsTypeImpl.Unmarshaller(ConditionsImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 3:
/* 180 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/* 183 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       int attIdx;
/* 194 */       switch (this.state) {
/*     */         case 2:
/* 196 */           if ("Conditions" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 197 */             this.context.popAttributes();
/* 198 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 203 */           attIdx = this.context.getAttribute("", "NotBefore");
/* 204 */           if (attIdx >= 0) {
/* 205 */             this.context.consumeAttribute(attIdx);
/* 206 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 209 */           attIdx = this.context.getAttribute("", "NotOnOrAfter");
/* 210 */           if (attIdx >= 0) {
/* 211 */             this.context.consumeAttribute(attIdx);
/* 212 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 215 */           ConditionsImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new ConditionsTypeImpl.Unmarshaller(ConditionsImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 3:
/* 218 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 221 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 232 */       switch (this.state) {
/*     */         case 1:
/* 234 */           if ("NotBefore" == ___local && "" == ___uri) {
/* 235 */             ConditionsImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ConditionsTypeImpl.Unmarshaller(ConditionsImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 238 */           if ("NotOnOrAfter" == ___local && "" == ___uri) {
/* 239 */             ConditionsImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ConditionsTypeImpl.Unmarshaller(ConditionsImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 242 */           ConditionsImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ConditionsTypeImpl.Unmarshaller(ConditionsImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 3:
/* 245 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 248 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       int attIdx;
/* 259 */       switch (this.state) {
/*     */         case 1:
/* 261 */           attIdx = this.context.getAttribute("", "NotBefore");
/* 262 */           if (attIdx >= 0) {
/* 263 */             this.context.consumeAttribute(attIdx);
/* 264 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 267 */           attIdx = this.context.getAttribute("", "NotOnOrAfter");
/* 268 */           if (attIdx >= 0) {
/* 269 */             this.context.consumeAttribute(attIdx);
/* 270 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 273 */           ConditionsImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new ConditionsTypeImpl.Unmarshaller(ConditionsImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 3:
/* 276 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 279 */       super.leaveAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleText(String value) throws SAXException {
/*     */       try {
/*     */         int attIdx;
/* 291 */         switch (this.state) {
/*     */           case 1:
/* 293 */             attIdx = this.context.getAttribute("", "NotBefore");
/* 294 */             if (attIdx >= 0) {
/* 295 */               this.context.consumeAttribute(attIdx);
/* 296 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 299 */             attIdx = this.context.getAttribute("", "NotOnOrAfter");
/* 300 */             if (attIdx >= 0) {
/* 301 */               this.context.consumeAttribute(attIdx);
/* 302 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 305 */             ConditionsImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new ConditionsTypeImpl.Unmarshaller(ConditionsImpl.this, this.context), 2, value);
/*     */             return;
/*     */           case 3:
/* 308 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 311 */       } catch (RuntimeException e) {
/* 312 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\ConditionsImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */