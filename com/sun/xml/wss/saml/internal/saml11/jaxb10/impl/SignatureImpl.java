/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.Signature;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SignatureImpl extends SignatureTypeImpl implements Signature, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return Signature.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "Signature";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "Signature");
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
/*  58 */     return Signature.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsq\000~\000\000pp\000sq\000~\000\016ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\026psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\025\001q\000~\000\032sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\033q\000~\000 sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\"xq\000~\000\035t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.SignedInfot\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000pp\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.SignedInfoTypeq\000~\000%sq\000~\000\016ppsq\000~\000\027q\000~\000\026psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\"L\000\btypeNameq\000~\000\"L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\026psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\"L\000\fnamespaceURIq\000~\000\"xpq\000~\000;q\000~\000:sq\000~\000!t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000 sq\000~\000!t\000\nSignedInfot\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\016ppsq\000~\000\000pp\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureValueq\000~\000%sq\000~\000\000pp\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\000>com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureValueTypeq\000~\000%sq\000~\000\016ppsq\000~\000\027q\000~\000\026pq\000~\0003q\000~\000Cq\000~\000 sq\000~\000!t\000\016SignatureValueq\000~\000Hsq\000~\000\016ppsq\000~\000\016q\000~\000\026psq\000~\000\000q\000~\000\026p\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfoq\000~\000%sq\000~\000\000q\000~\000\026p\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfoTypeq\000~\000%sq\000~\000\016ppsq\000~\000\027q\000~\000\026pq\000~\0003q\000~\000Cq\000~\000 sq\000~\000!t\000\007KeyInfoq\000~\000Hq\000~\000 sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\016q\000~\000\026psq\000~\000\000q\000~\000\026p\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\0002com.sun.xml.wss.saml.internal.saml11.jaxb10.Objectq\000~\000%sq\000~\000\000q\000~\000\026p\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\016ppsq\000~\000\022q\000~\000\026psq\000~\000\027q\000~\000\026pq\000~\000\032q\000~\000\036q\000~\000 sq\000~\000!t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.ObjectTypeq\000~\000%sq\000~\000\016ppsq\000~\000\027q\000~\000\026pq\000~\0003q\000~\000Cq\000~\000 sq\000~\000!t\000\006Objectq\000~\000Hq\000~\000 sq\000~\000\016ppsq\000~\000\027q\000~\000\026psq\000~\0000ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0005q\000~\000:t\000\002IDq\000~\000>\000q\000~\000@sq\000~\000Aq\000~\000q\000~\000:sq\000~\000!t\000\002Idt\000\000q\000~\000 sq\000~\000\016ppsq\000~\000\027q\000~\000\026pq\000~\0003q\000~\000Cq\000~\000 sq\000~\000!t\000\tSignatureq\000~\000Hsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000&\001pq\000~\000\fq\000~\000pq\000~\000\rq\000~\000'q\000~\000Qq\000~\000eq\000~\000zq\000~\000\021q\000~\000)q\000~\000Kq\000~\000Sq\000~\000_q\000~\000gq\000~\000tq\000~\000\024q\000~\000*q\000~\000Lq\000~\000Tq\000~\000`q\000~\000hq\000~\000uq\000~\000}q\000~\000|q\000~\000\tq\000~\000qq\000~\000.q\000~\000Xq\000~\000lq\000~\000q\000~\000q\000~\000\nq\000~\000q\000~\000\\q\000~\000\017q\000~\000Iq\000~\000]q\000~\000rq\000~\000\013x");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SignatureImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 145 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 149 */       this(context);
/* 150 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 154 */       return SignatureImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 163 */       switch (this.state) {
/*     */         case 0:
/* 165 */           if ("Signature" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 166 */             this.context.pushAttributes(__atts, false);
/* 167 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 172 */           attIdx = this.context.getAttribute("", "Id");
/* 173 */           if (attIdx >= 0) {
/* 174 */             this.context.consumeAttribute(attIdx);
/* 175 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 178 */           if ("SignedInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 179 */             SignatureImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SignatureTypeImpl.Unmarshaller(SignatureImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 182 */           if ("SignedInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 183 */             SignatureImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SignatureTypeImpl.Unmarshaller(SignatureImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 188 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/* 191 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 202 */       switch (this.state) {
/*     */         case 2:
/* 204 */           if ("Signature" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 205 */             this.context.popAttributes();
/* 206 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 211 */           attIdx = this.context.getAttribute("", "Id");
/* 212 */           if (attIdx >= 0) {
/* 213 */             this.context.consumeAttribute(attIdx);
/* 214 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 219 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 222 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 233 */       switch (this.state) {
/*     */         case 1:
/* 235 */           if ("Id" == ___local && "" == ___uri) {
/* 236 */             SignatureImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new SignatureTypeImpl.Unmarshaller(SignatureImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 241 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 244 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 255 */       switch (this.state) {
/*     */         case 1:
/* 257 */           attIdx = this.context.getAttribute("", "Id");
/* 258 */           if (attIdx >= 0) {
/* 259 */             this.context.consumeAttribute(attIdx);
/* 260 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 265 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 268 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 280 */         switch (this.state) {
/*     */           case 1:
/* 282 */             attIdx = this.context.getAttribute("", "Id");
/* 283 */             if (attIdx >= 0) {
/* 284 */               this.context.consumeAttribute(attIdx);
/* 285 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 290 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 293 */       } catch (RuntimeException e) {
/* 294 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SignatureImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */