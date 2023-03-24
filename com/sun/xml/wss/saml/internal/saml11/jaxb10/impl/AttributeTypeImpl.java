/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AttributeTypeImpl extends AttributeDesignatorTypeImpl implements AttributeType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  17 */   public static final Class version = JAXBVersion.class; protected ListImpl _AttributeValue; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeValueImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return AttributeType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getAttributeValue() {
/*  25 */     if (this._AttributeValue == null) {
/*  26 */       this._AttributeValue = new ListImpl(new ArrayList());
/*     */     }
/*  28 */     return this._AttributeValue;
/*     */   }
/*     */   
/*     */   public List getAttributeValue() {
/*  32 */     return (List)_getAttributeValue();
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
/*  43 */     int len1 = (this._AttributeValue == null) ? 0 : this._AttributeValue.size();
/*  44 */     super.serializeBody(context);
/*  45 */     while (idx1 != len1) {
/*  46 */       if (this._AttributeValue.get(idx1) instanceof javax.xml.bind.Element) {
/*  47 */         context.childAsBody((JAXBObject)this._AttributeValue.get(idx1++), "AttributeValue"); continue;
/*     */       } 
/*  49 */       context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeValue");
/*  50 */       int idx_0 = idx1;
/*  51 */       context.childAsURIs((JAXBObject)this._AttributeValue.get(idx_0++), "AttributeValue");
/*  52 */       context.endNamespaceDecls();
/*  53 */       int idx_1 = idx1;
/*  54 */       context.childAsAttributes((JAXBObject)this._AttributeValue.get(idx_1++), "AttributeValue");
/*  55 */       context.endAttributes();
/*  56 */       context.childAsBody((JAXBObject)this._AttributeValue.get(idx1++), "AttributeValue");
/*  57 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  65 */     int idx1 = 0;
/*  66 */     int len1 = (this._AttributeValue == null) ? 0 : this._AttributeValue.size();
/*  67 */     super.serializeAttributes(context);
/*  68 */     while (idx1 != len1) {
/*  69 */       if (this._AttributeValue.get(idx1) instanceof javax.xml.bind.Element) {
/*  70 */         context.childAsAttributes((JAXBObject)this._AttributeValue.get(idx1++), "AttributeValue"); continue;
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
/*  81 */     int len1 = (this._AttributeValue == null) ? 0 : this._AttributeValue.size();
/*  82 */     super.serializeURIs(context);
/*  83 */     while (idx1 != len1) {
/*  84 */       if (this._AttributeValue.get(idx1) instanceof javax.xml.bind.Element) {
/*  85 */         context.childAsURIs((JAXBObject)this._AttributeValue.get(idx1++), "AttributeValue"); continue;
/*     */       } 
/*  87 */       idx1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  93 */     return AttributeType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  97 */     if (schemaFragment == null) {
/*  98 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\nppsq\000~\000\007sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\rxq\000~\000\003q\000~\000\023psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\022\001q\000~\000\027sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\030q\000~\000\035sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\037xq\000~\000\032t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeValuet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\fpp\000sq\000~\000\000ppsq\000~\000\fpp\000sq\000~\000\nppsq\000~\000\007q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.AnyTypeq\000~\000\"sq\000~\000\nppsq\000~\000\024q\000~\000\023psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\037L\000\btypeNameq\000~\000\037L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\023psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\037L\000\fnamespaceURIq\000~\000\037xpq\000~\0008q\000~\0007sq\000~\000\036t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\035sq\000~\000\036t\000\016AttributeValuet\000%urn:oasis:names:tc:SAML:1.0:assertionsq\000~\000\024ppsq\000~\000-q\000~\000\023psr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0002q\000~\0007t\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xq\000~\000:\001q\000~\000=sq\000~\000>q\000~\000Jq\000~\0007sq\000~\000\036t\000\rAttributeNamet\000\000sq\000~\000\024ppsq\000~\000-ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0002q\000~\0007t\000\006anyURIq\000~\000;q\000~\000=sq\000~\000>q\000~\000Uq\000~\0007sq\000~\000\036t\000\022AttributeNamespaceq\000~\000Psr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\n\001pq\000~\000\006q\000~\000$q\000~\000\020q\000~\000&q\000~\000\021q\000~\000'q\000~\000\005q\000~\000\tq\000~\000+q\000~\000\013x");
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
/*     */     private final AttributeTypeImpl this$0;
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
/* 168 */       return AttributeTypeImpl.this;
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
/* 179 */           attIdx = this.context.getAttribute("", "AttributeName");
/* 180 */           if (attIdx >= 0) {
/* 181 */             this.context.consumeAttribute(attIdx);
/* 182 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 2:
/* 188 */           AttributeTypeImpl.this._getAttributeValue().add(spawnChildFromEnterElement((AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl == null) ? (AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl = AttributeTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl")) : AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */           return;
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 194 */           if ("AttributeValue" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 195 */             AttributeTypeImpl.this._getAttributeValue().add(spawnChildFromEnterElement((AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeValueImpl == null) ? (AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeValueImpl = AttributeTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeValueImpl")) : AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeValueImpl, 4, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 198 */           if ("AttributeValue" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 199 */             this.context.pushAttributes(__atts, true);
/* 200 */             this.state = 2;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 205 */           if ("AttributeValue" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 206 */             AttributeTypeImpl.this._getAttributeValue().add(spawnChildFromEnterElement((AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeValueImpl == null) ? (AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeValueImpl = AttributeTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeValueImpl")) : AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AttributeValueImpl, 4, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 209 */           if ("AttributeValue" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 210 */             this.context.pushAttributes(__atts, true);
/* 211 */             this.state = 2;
/*     */             return;
/*     */           } 
/* 214 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/* 217 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 228 */       switch (this.state) {
/*     */         case 0:
/* 230 */           attIdx = this.context.getAttribute("", "AttributeName");
/* 231 */           if (attIdx >= 0) {
/* 232 */             this.context.consumeAttribute(attIdx);
/* 233 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 238 */           AttributeTypeImpl.this._getAttributeValue().add(spawnChildFromLeaveElement((AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl == null) ? (AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl = AttributeTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl")) : AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl, 3, ___uri, ___local, ___qname));
/*     */           return;
/*     */         case 3:
/* 241 */           if ("AttributeValue" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 242 */             this.context.popAttributes();
/* 243 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 4:
/* 248 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 251 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 262 */       switch (this.state) {
/*     */         case 0:
/* 264 */           if ("AttributeName" == ___local && "" == ___uri) {
/* 265 */             AttributeTypeImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new AttributeDesignatorTypeImpl.Unmarshaller(AttributeTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 270 */           AttributeTypeImpl.this._getAttributeValue().add(spawnChildFromEnterAttribute((AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl == null) ? (AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl = AttributeTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl")) : AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl, 3, ___uri, ___local, ___qname));
/*     */           return;
/*     */         case 4:
/* 273 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 276 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 287 */       switch (this.state) {
/*     */         case 0:
/* 289 */           attIdx = this.context.getAttribute("", "AttributeName");
/* 290 */           if (attIdx >= 0) {
/* 291 */             this.context.consumeAttribute(attIdx);
/* 292 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 297 */           AttributeTypeImpl.this._getAttributeValue().add(spawnChildFromLeaveAttribute((AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl == null) ? (AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl = AttributeTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl")) : AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl, 3, ___uri, ___local, ___qname));
/*     */           return;
/*     */         case 4:
/* 300 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 303 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 315 */         switch (this.state) {
/*     */           case 0:
/* 317 */             attIdx = this.context.getAttribute("", "AttributeName");
/* 318 */             if (attIdx >= 0) {
/* 319 */               this.context.consumeAttribute(attIdx);
/* 320 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 325 */             AttributeTypeImpl.this._getAttributeValue().add(spawnChildFromText((AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl == null) ? (AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl = AttributeTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl")) : AttributeTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AnyTypeImpl, 3, value));
/*     */             return;
/*     */           case 4:
/* 328 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 331 */       } catch (RuntimeException e) {
/* 332 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AttributeTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */