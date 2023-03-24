/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectStatementAbstractType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SubjectStatementAbstractTypeImpl extends StatementAbstractTypeImpl implements SubjectStatementAbstractType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  17 */   public static final Class version = JAXBVersion.class; protected SubjectType _Subject; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return SubjectStatementAbstractType.class;
/*     */   }
/*     */   
/*     */   public SubjectType getSubject() {
/*  25 */     return this._Subject;
/*     */   }
/*     */   
/*     */   public void setSubject(SubjectType value) {
/*  29 */     this._Subject = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  33 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  39 */     super.serializeBody(context);
/*  40 */     if (this._Subject instanceof javax.xml.bind.Element) {
/*  41 */       context.childAsBody((JAXBObject)this._Subject, "Subject");
/*     */     } else {
/*  43 */       context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Subject");
/*  44 */       context.childAsURIs((JAXBObject)this._Subject, "Subject");
/*  45 */       context.endNamespaceDecls();
/*  46 */       context.childAsAttributes((JAXBObject)this._Subject, "Subject");
/*  47 */       context.endAttributes();
/*  48 */       context.childAsBody((JAXBObject)this._Subject, "Subject");
/*  49 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  56 */     super.serializeAttributes(context);
/*  57 */     if (this._Subject instanceof javax.xml.bind.Element) {
/*  58 */       context.childAsAttributes((JAXBObject)this._Subject, "Subject");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  65 */     super.serializeURIs(context);
/*  66 */     if (this._Subject instanceof javax.xml.bind.Element) {
/*  67 */       context.childAsURIs((JAXBObject)this._Subject, "Subject");
/*     */     }
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  72 */     return SubjectStatementAbstractType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  76 */     if (schemaFragment == null) {
/*  77 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\000ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\007xq\000~\000\003q\000~\000\017psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\016\001q\000~\000\023sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\024q\000~\000\031sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\033xq\000~\000\026t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.Subjectt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\006pp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsq\000~\000\006pp\000sq\000~\000\000ppsq\000~\000\013q\000~\000\017psq\000~\000\020q\000~\000\017pq\000~\000\023q\000~\000\027q\000~\000\031sq\000~\000\032t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectTypeq\000~\000\036sq\000~\000\000ppsq\000~\000\020q\000~\000\017psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\033L\000\btypeNameq\000~\000\033L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\017psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\033L\000\fnamespaceURIq\000~\000\033xpq\000~\0005q\000~\0004sq\000~\000\032t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\031sq\000~\000\032t\000\007Subjectt\000%urn:oasis:names:tc:SAML:1.0:assertionsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\007\001pq\000~\000!q\000~\000\nq\000~\000#q\000~\000\rq\000~\000$q\000~\000(q\000~\000\005x");
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
/* 122 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SubjectStatementAbstractTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 131 */       super(context, "-----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 135 */       this(context);
/* 136 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 140 */       return SubjectStatementAbstractTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 149 */       switch (this.state) {
/*     */         case 2:
/* 151 */           if ("NameIdentifier" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 152 */             SubjectStatementAbstractTypeImpl.this._Subject = (SubjectTypeImpl)spawnChildFromEnterElement((SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl == null) ? (SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl = SubjectStatementAbstractTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectTypeImpl")) : SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl, 3, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 155 */           if ("NameIdentifier" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 156 */             SubjectStatementAbstractTypeImpl.this._Subject = (SubjectTypeImpl)spawnChildFromEnterElement((SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl == null) ? (SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl = SubjectStatementAbstractTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectTypeImpl")) : SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl, 3, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 159 */           if ("SubjectConfirmation" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 160 */             SubjectStatementAbstractTypeImpl.this._Subject = (SubjectTypeImpl)spawnChildFromEnterElement((SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl == null) ? (SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl = SubjectStatementAbstractTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectTypeImpl")) : SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl, 3, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 163 */           if ("SubjectConfirmation" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 164 */             SubjectStatementAbstractTypeImpl.this._Subject = (SubjectTypeImpl)spawnChildFromEnterElement((SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl == null) ? (SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl = SubjectStatementAbstractTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectTypeImpl")) : SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectTypeImpl, 3, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 169 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 172 */           SubjectStatementAbstractTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new StatementAbstractTypeImpl.Unmarshaller(SubjectStatementAbstractTypeImpl.this, this.context), 1, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 1:
/* 175 */           if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 176 */             SubjectStatementAbstractTypeImpl.this._Subject = (SubjectImpl)spawnChildFromEnterElement((SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectImpl == null) ? (SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectImpl = SubjectStatementAbstractTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectImpl")) : SubjectStatementAbstractTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SubjectImpl, 4, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 179 */           if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 180 */             this.context.pushAttributes(__atts, false);
/* 181 */             this.state = 2;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 186 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 197 */       switch (this.state) {
/*     */         case 4:
/* 199 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 3:
/* 202 */           if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 203 */             this.context.popAttributes();
/* 204 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 209 */           SubjectStatementAbstractTypeImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new StatementAbstractTypeImpl.Unmarshaller(SubjectStatementAbstractTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 212 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 223 */       switch (this.state) {
/*     */         case 4:
/* 225 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 0:
/* 228 */           SubjectStatementAbstractTypeImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new StatementAbstractTypeImpl.Unmarshaller(SubjectStatementAbstractTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 231 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 242 */       switch (this.state) {
/*     */         case 4:
/* 244 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 0:
/* 247 */           SubjectStatementAbstractTypeImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new StatementAbstractTypeImpl.Unmarshaller(SubjectStatementAbstractTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 250 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 262 */         switch (this.state) {
/*     */           case 4:
/* 264 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 0:
/* 267 */             SubjectStatementAbstractTypeImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new StatementAbstractTypeImpl.Unmarshaller(SubjectStatementAbstractTypeImpl.this, this.context), 1, value);
/*     */             return;
/*     */         } 
/* 270 */       } catch (RuntimeException e) {
/* 271 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SubjectStatementAbstractTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */