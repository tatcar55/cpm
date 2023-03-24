/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthenticationStatement;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AuthenticationStatementImpl extends AuthenticationStatementTypeImpl implements AuthenticationStatement, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return AuthenticationStatement.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "AuthenticationStatement";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement");
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
/*  58 */     return AuthenticationStatement.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsq\000~\000\000pp\000sq\000~\000\016ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\026psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\025\001q\000~\000\032sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\033q\000~\000 sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\"xq\000~\000\035t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.Subjectt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000pp\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectTypeq\000~\000%sq\000~\000\016ppsq\000~\000\027q\000~\000\026psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\"L\000\btypeNameq\000~\000\"L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\026psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\"L\000\fnamespaceURIq\000~\000\"xpq\000~\000;q\000~\000:sq\000~\000!t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000 sq\000~\000!t\000\007Subjectt\000%urn:oasis:names:tc:SAML:1.0:assertionsq\000~\000\016ppsq\000~\000\016q\000~\000\026psq\000~\000\000q\000~\000\026p\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000;com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectLocalityq\000~\000%sq\000~\000\000q\000~\000\026p\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000?com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectLocalityTypeq\000~\000%sq\000~\000\016ppsq\000~\000\027q\000~\000\026pq\000~\0003q\000~\000Cq\000~\000 sq\000~\000!t\000\017SubjectLocalityq\000~\000Hq\000~\000 sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\016q\000~\000\026psq\000~\000\000q\000~\000\026p\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000<com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorityBindingq\000~\000%sq\000~\000\000q\000~\000\026p\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000@com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorityBindingTypeq\000~\000%sq\000~\000\016ppsq\000~\000\027q\000~\000\026pq\000~\0003q\000~\000Cq\000~\000 sq\000~\000!t\000\020AuthorityBindingq\000~\000Hq\000~\000 sq\000~\000\027ppsq\000~\0000ppsr\000%com.sun.msv.datatype.xsd.DateTimeType\000\000\000\000\000\000\000\001\002\000\000xr\000)com.sun.msv.datatype.xsd.DateTimeBaseType\024W\032@3¥´å\002\000\000xq\000~\0005q\000~\000:t\000\bdateTimeq\000~\000>q\000~\000@sq\000~\000Aq\000~\000wq\000~\000:sq\000~\000!t\000\025AuthenticationInstantt\000\000sq\000~\000\027ppsq\000~\0000ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0005q\000~\000:t\000\006anyURIq\000~\000>q\000~\000@sq\000~\000Aq\000~\000q\000~\000:sq\000~\000!t\000\024AuthenticationMethodq\000~\000{sq\000~\000\016ppsq\000~\000\027q\000~\000\026pq\000~\0003q\000~\000Cq\000~\000 sq\000~\000!t\000\027AuthenticationStatementq\000~\000Hsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\036\001pq\000~\000\fq\000~\000]q\000~\000\nq\000~\000'q\000~\000Rq\000~\000gq\000~\000\rq\000~\000\021q\000~\000)q\000~\000Lq\000~\000Tq\000~\000aq\000~\000iq\000~\000\024q\000~\000*q\000~\000Mq\000~\000Uq\000~\000bq\000~\000jq\000~\000^q\000~\000\013q\000~\000.q\000~\000Yq\000~\000nq\000~\000q\000~\000Iq\000~\000\tq\000~\000\017q\000~\000Jq\000~\000_x");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AuthenticationStatementImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 140 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 144 */       this(context);
/* 145 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 149 */       return AuthenticationStatementImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 158 */       switch (this.state) {
/*     */         case 3:
/* 160 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 163 */           if ("AuthenticationStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 164 */             this.context.pushAttributes(__atts, false);
/* 165 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 170 */           attIdx = this.context.getAttribute("", "AuthenticationInstant");
/* 171 */           if (attIdx >= 0) {
/* 172 */             this.context.consumeAttribute(attIdx);
/* 173 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 178 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 189 */       switch (this.state) {
/*     */         case 2:
/* 191 */           if ("AuthenticationStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 192 */             this.context.popAttributes();
/* 193 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 198 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 201 */           attIdx = this.context.getAttribute("", "AuthenticationInstant");
/* 202 */           if (attIdx >= 0) {
/* 203 */             this.context.consumeAttribute(attIdx);
/* 204 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 209 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 220 */       switch (this.state) {
/*     */         case 3:
/* 222 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 225 */           if ("AuthenticationInstant" == ___local && "" == ___uri) {
/* 226 */             AuthenticationStatementImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new AuthenticationStatementTypeImpl.Unmarshaller(AuthenticationStatementImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
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
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       int attIdx;
/* 242 */       switch (this.state) {
/*     */         case 3:
/* 244 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 247 */           attIdx = this.context.getAttribute("", "AuthenticationInstant");
/* 248 */           if (attIdx >= 0) {
/* 249 */             this.context.consumeAttribute(attIdx);
/* 250 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 255 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 267 */         switch (this.state) {
/*     */           case 3:
/* 269 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/* 272 */             attIdx = this.context.getAttribute("", "AuthenticationInstant");
/* 273 */             if (attIdx >= 0) {
/* 274 */               this.context.consumeAttribute(attIdx);
/* 275 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */         } 
/* 280 */       } catch (RuntimeException e) {
/* 281 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AuthenticationStatementImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */