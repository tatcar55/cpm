/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AttributeStatementTypeImpl extends SubjectStatementAbstractTypeImpl implements AttributeStatementType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  17 */   public static final Class version = JAXBVersion.class; protected ListImpl _Attribute; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeTypeImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return AttributeStatementType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getAttribute() {
/*  25 */     if (this._Attribute == null) {
/*  26 */       this._Attribute = new ListImpl(new ArrayList());
/*     */     }
/*  28 */     return this._Attribute;
/*     */   }
/*     */   
/*     */   public List getAttribute() {
/*  32 */     return (List)_getAttribute();
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  36 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  42 */     int idx1 = 0;
/*  43 */     int len1 = (this._Attribute == null) ? 0 : this._Attribute.size();
/*  44 */     super.serializeBody(context);
/*  45 */     while (idx1 != len1) {
/*  46 */       if (this._Attribute.get(idx1) instanceof javax.xml.bind.Element) {
/*  47 */         context.childAsBody((JAXBObject)this._Attribute.get(idx1++), "Attribute"); continue;
/*     */       } 
/*  49 */       context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Attribute");
/*  50 */       int idx_0 = idx1;
/*  51 */       context.childAsURIs((JAXBObject)this._Attribute.get(idx_0++), "Attribute");
/*  52 */       context.endNamespaceDecls();
/*  53 */       int idx_1 = idx1;
/*  54 */       context.childAsAttributes((JAXBObject)this._Attribute.get(idx_1++), "Attribute");
/*  55 */       context.endAttributes();
/*  56 */       context.childAsBody((JAXBObject)this._Attribute.get(idx1++), "Attribute");
/*  57 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  65 */     int idx1 = 0;
/*  66 */     int len1 = (this._Attribute == null) ? 0 : this._Attribute.size();
/*  67 */     super.serializeAttributes(context);
/*  68 */     while (idx1 != len1) {
/*  69 */       if (this._Attribute.get(idx1) instanceof javax.xml.bind.Element) {
/*  70 */         context.childAsAttributes((JAXBObject)this._Attribute.get(idx1++), "Attribute"); continue;
/*     */       } 
/*  72 */       idx1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  80 */     int idx1 = 0;
/*  81 */     int len1 = (this._Attribute == null) ? 0 : this._Attribute.size();
/*  82 */     super.serializeURIs(context);
/*  83 */     while (idx1 != len1) {
/*  84 */       if (this._Attribute.get(idx1) instanceof javax.xml.bind.Element) {
/*  85 */         context.childAsURIs((JAXBObject)this._Attribute.get(idx1++), "Attribute"); continue;
/*     */       } 
/*  87 */       idx1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  93 */     return AttributeStatementType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  97 */     if (schemaFragment == null) {
/*  98 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\006ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\txq\000~\000\003q\000~\000\021psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\020\001q\000~\000\025sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\026q\000~\000\033sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\035xq\000~\000\030t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.Subjectt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\bpp\000sq\000~\000\000ppsq\000~\000\bpp\000sq\000~\000\006ppsq\000~\000\rq\000~\000\021psq\000~\000\022q\000~\000\021pq\000~\000\025q\000~\000\031q\000~\000\033sq\000~\000\034t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectTypeq\000~\000 sq\000~\000\006ppsq\000~\000\022q\000~\000\021psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\035L\000\btypeNameq\000~\000\035L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\021psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\035L\000\fnamespaceURIq\000~\000\035xpq\000~\0006q\000~\0005sq\000~\000\034t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\033sq\000~\000\034t\000\007Subjectt\000%urn:oasis:names:tc:SAML:1.0:assertionsq\000~\000\rppsq\000~\000\006ppsq\000~\000\bpp\000sq\000~\000\006ppsq\000~\000\rq\000~\000\021psq\000~\000\022q\000~\000\021pq\000~\000\025q\000~\000\031q\000~\000\033sq\000~\000\034t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Attributeq\000~\000 sq\000~\000\bpp\000sq\000~\000\000ppsq\000~\000\bpp\000sq\000~\000\006ppsq\000~\000\rq\000~\000\021psq\000~\000\022q\000~\000\021pq\000~\000\025q\000~\000\031q\000~\000\033sq\000~\000\034t\0009com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeTypeq\000~\000 sq\000~\000\006ppsq\000~\000\022q\000~\000\021pq\000~\000.q\000~\000>q\000~\000\033sq\000~\000\034t\000\tAttributeq\000~\000Csr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\020\001pq\000~\000\"q\000~\000Mq\000~\000\fq\000~\000$q\000~\000Gq\000~\000Oq\000~\000\017q\000~\000%q\000~\000Hq\000~\000Pq\000~\000Dq\000~\000)q\000~\000Tq\000~\000\007q\000~\000Eq\000~\000\005x");
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
/* 150 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AttributeStatementTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 159 */       super(context, "-----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 163 */       this(context);
/* 164 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 168 */       return AttributeStatementTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 177 */       switch (this.state) {
/*     */         case 0:
/* 179 */           if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 180 */             AttributeStatementTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AttributeStatementTypeImpl.this, this.context), 1, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 183 */           if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 184 */             AttributeStatementTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AttributeStatementTypeImpl.this, this.context), 1, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 187 */           AttributeStatementTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AttributeStatementTypeImpl.this, this.context), 1, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 2:
/* 190 */           attIdx = this.context.getAttribute("", "AttributeName");
/* 191 */           if (attIdx >= 0) {
/* 192 */             this.context.consumeAttribute(attIdx);
/* 193 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 198 */           if ("Attribute" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 199 */             AttributeStatementTypeImpl.this._getAttribute().add(spawnChildFromEnterElement((AttributeStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeImpl == null) ? (AttributeStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeImpl = AttributeStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeImpl")) : AttributeStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeImpl, 4, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 202 */           if ("Attribute" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 203 */             this.context.pushAttributes(__atts, false);
/* 204 */             this.state = 2;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 209 */           if ("Attribute" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 210 */             AttributeStatementTypeImpl.this._getAttribute().add(spawnChildFromEnterElement((AttributeStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeImpl == null) ? (AttributeStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeImpl = AttributeStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeImpl")) : AttributeStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeImpl, 4, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 213 */           if ("Attribute" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 214 */             this.context.pushAttributes(__atts, false);
/* 215 */             this.state = 2;
/*     */             return;
/*     */           } 
/* 218 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/* 221 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 232 */       switch (this.state) {
/*     */         case 0:
/* 234 */           AttributeStatementTypeImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AttributeStatementTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 237 */           attIdx = this.context.getAttribute("", "AttributeName");
/* 238 */           if (attIdx >= 0) {
/* 239 */             this.context.consumeAttribute(attIdx);
/* 240 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 245 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 3:
/* 248 */           if ("Attribute" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 249 */             this.context.popAttributes();
/* 250 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 255 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 266 */       switch (this.state) {
/*     */         case 0:
/* 268 */           AttributeStatementTypeImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AttributeStatementTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 271 */           if ("AttributeName" == ___local && "" == ___uri) {
/* 272 */             AttributeStatementTypeImpl.this._getAttribute().add(spawnChildFromEnterAttribute((AttributeStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeTypeImpl == null) ? (AttributeStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeTypeImpl = AttributeStatementTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeTypeImpl")) : AttributeStatementTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeTypeImpl, 3, ___uri, ___local, ___qname));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 277 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 280 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 291 */       switch (this.state) {
/*     */         case 0:
/* 293 */           AttributeStatementTypeImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AttributeStatementTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 296 */           attIdx = this.context.getAttribute("", "AttributeName");
/* 297 */           if (attIdx >= 0) {
/* 298 */             this.context.consumeAttribute(attIdx);
/* 299 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 304 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 307 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 319 */         switch (this.state) {
/*     */           case 0:
/* 321 */             AttributeStatementTypeImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new SubjectStatementAbstractTypeImpl.Unmarshaller(AttributeStatementTypeImpl.this, this.context), 1, value);
/*     */             return;
/*     */           case 2:
/* 324 */             attIdx = this.context.getAttribute("", "AttributeName");
/* 325 */             if (attIdx >= 0) {
/* 326 */               this.context.consumeAttribute(attIdx);
/* 327 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 332 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 335 */       } catch (RuntimeException e) {
/* 336 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AttributeStatementTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */