/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.Reference;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ReferenceImpl extends ReferenceTypeImpl implements Reference, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return Reference.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "Reference";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "Reference");
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
/*  58 */     return Reference.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsq\000~\000\017sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\000q\000~\000\023p\000sq\000~\000\017ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004q\000~\000\023psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\023psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\022\001q\000~\000\034sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\035q\000~\000\"sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000$xq\000~\000\037t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.Transformst\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000q\000~\000\023p\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\017ppsq\000~\000\026q\000~\000\023psq\000~\000\031q\000~\000\023pq\000~\000\034q\000~\000 q\000~\000\"sq\000~\000#t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformsTypeq\000~\000'sq\000~\000\017ppsq\000~\000\031q\000~\000\023psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000$L\000\btypeNameq\000~\000$L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\023psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000$L\000\fnamespaceURIq\000~\000$xpq\000~\000=q\000~\000<sq\000~\000#t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\"sq\000~\000#t\000\nTransformst\000\"http://www.w3.org/2000/09/xmldsig#q\000~\000\"sq\000~\000\017ppsq\000~\000\000pp\000sq\000~\000\017ppsq\000~\000\026q\000~\000\023psq\000~\000\031q\000~\000\023pq\000~\000\034q\000~\000 q\000~\000\"sq\000~\000#t\0008com.sun.xml.wss.saml.internal.saml11.jaxb10.DigestMethodq\000~\000'sq\000~\000\000pp\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\017ppsq\000~\000\026q\000~\000\023psq\000~\000\031q\000~\000\023pq\000~\000\034q\000~\000 q\000~\000\"sq\000~\000#t\000<com.sun.xml.wss.saml.internal.saml11.jaxb10.DigestMethodTypeq\000~\000'sq\000~\000\017ppsq\000~\000\031q\000~\000\023pq\000~\0005q\000~\000Eq\000~\000\"sq\000~\000#t\000\fDigestMethodq\000~\000Jsq\000~\000\000pp\000sq\000~\000\007ppsq\000~\0002ppsr\000'com.sun.msv.datatype.xsd.FinalComponent\000\000\000\000\000\000\000\001\002\000\001I\000\nfinalValuexr\000\036com.sun.msv.datatype.xsd.Proxy\000\000\000\000\000\000\000\001\002\000\001L\000\bbaseTypet\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\000~\0009q\000~\000Jt\000\017DigestValueTypeq\000~\000@sr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xq\000~\0007q\000~\000<t\000\fbase64Binaryq\000~\000@\000\000\000\000q\000~\000Bsq\000~\000Cq\000~\000iq\000~\000Jsq\000~\000\017ppsq\000~\000\031q\000~\000\023pq\000~\0005q\000~\000Eq\000~\000\"sq\000~\000#t\000\013DigestValueq\000~\000Jsq\000~\000\017ppsq\000~\000\031q\000~\000\023psq\000~\0002ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0007q\000~\000<t\000\002IDq\000~\000@\000q\000~\000Bsq\000~\000Cq\000~\000wq\000~\000<sq\000~\000#t\000\002Idt\000\000q\000~\000\"sq\000~\000\017ppsq\000~\000\031q\000~\000\023psq\000~\0002ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0007q\000~\000<t\000\006anyURIq\000~\000@q\000~\000Bsq\000~\000Cq\000~\000q\000~\000<sq\000~\000#t\000\004Typeq\000~\000{q\000~\000\"sq\000~\000\017ppsq\000~\000\031q\000~\000\023pq\000~\000~sq\000~\000#t\000\003URIq\000~\000{q\000~\000\"sq\000~\000\017ppsq\000~\000\031q\000~\000\023pq\000~\0005q\000~\000Eq\000~\000\"sq\000~\000#t\000\tReferenceq\000~\000Jsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\033\001pq\000~\000\tq\000~\000)q\000~\000Sq\000~\000\016q\000~\000\025q\000~\000+q\000~\000Mq\000~\000Uq\000~\000\030q\000~\000,q\000~\000Nq\000~\000Vq\000~\000oq\000~\000\rq\000~\000|q\000~\000_q\000~\0000q\000~\000Zq\000~\000kq\000~\000\nq\000~\000q\000~\000\020q\000~\000\021q\000~\000Kq\000~\000q\000~\000\013q\000~\000\fx");
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
/* 134 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final ReferenceImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 143 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 147 */       this(context);
/* 148 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 152 */       return ReferenceImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 161 */       switch (this.state) {
/*     */         case 0:
/* 163 */           if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 164 */             this.context.pushAttributes(__atts, false);
/* 165 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 170 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 1:
/* 173 */           attIdx = this.context.getAttribute("", "Id");
/* 174 */           if (attIdx >= 0) {
/* 175 */             this.context.consumeAttribute(attIdx);
/* 176 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 179 */           attIdx = this.context.getAttribute("", "Type");
/* 180 */           if (attIdx >= 0) {
/* 181 */             this.context.consumeAttribute(attIdx);
/* 182 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 185 */           attIdx = this.context.getAttribute("", "URI");
/* 186 */           if (attIdx >= 0) {
/* 187 */             this.context.consumeAttribute(attIdx);
/* 188 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 191 */           if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 192 */             ReferenceImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ReferenceTypeImpl.Unmarshaller(ReferenceImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 195 */           if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 196 */             ReferenceImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ReferenceTypeImpl.Unmarshaller(ReferenceImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 199 */           if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 200 */             ReferenceImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ReferenceTypeImpl.Unmarshaller(ReferenceImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 203 */           if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 204 */             ReferenceImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ReferenceTypeImpl.Unmarshaller(ReferenceImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 209 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 220 */       switch (this.state) {
/*     */         case 3:
/* 222 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 225 */           if ("Reference" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 226 */             this.context.popAttributes();
/* 227 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 232 */           attIdx = this.context.getAttribute("", "Id");
/* 233 */           if (attIdx >= 0) {
/* 234 */             this.context.consumeAttribute(attIdx);
/* 235 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 238 */           attIdx = this.context.getAttribute("", "Type");
/* 239 */           if (attIdx >= 0) {
/* 240 */             this.context.consumeAttribute(attIdx);
/* 241 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 244 */           attIdx = this.context.getAttribute("", "URI");
/* 245 */           if (attIdx >= 0) {
/* 246 */             this.context.consumeAttribute(attIdx);
/* 247 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 252 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 263 */       switch (this.state) {
/*     */         case 3:
/* 265 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 268 */           if ("Id" == ___local && "" == ___uri) {
/* 269 */             ReferenceImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ReferenceTypeImpl.Unmarshaller(ReferenceImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 272 */           if ("Type" == ___local && "" == ___uri) {
/* 273 */             ReferenceImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ReferenceTypeImpl.Unmarshaller(ReferenceImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 276 */           if ("URI" == ___local && "" == ___uri) {
/* 277 */             ReferenceImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ReferenceTypeImpl.Unmarshaller(ReferenceImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 282 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 293 */       switch (this.state) {
/*     */         case 3:
/* 295 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 298 */           attIdx = this.context.getAttribute("", "Id");
/* 299 */           if (attIdx >= 0) {
/* 300 */             this.context.consumeAttribute(attIdx);
/* 301 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 304 */           attIdx = this.context.getAttribute("", "Type");
/* 305 */           if (attIdx >= 0) {
/* 306 */             this.context.consumeAttribute(attIdx);
/* 307 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 310 */           attIdx = this.context.getAttribute("", "URI");
/* 311 */           if (attIdx >= 0) {
/* 312 */             this.context.consumeAttribute(attIdx);
/* 313 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 318 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 330 */         switch (this.state) {
/*     */           case 3:
/* 332 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/* 335 */             attIdx = this.context.getAttribute("", "Id");
/* 336 */             if (attIdx >= 0) {
/* 337 */               this.context.consumeAttribute(attIdx);
/* 338 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 341 */             attIdx = this.context.getAttribute("", "Type");
/* 342 */             if (attIdx >= 0) {
/* 343 */               this.context.consumeAttribute(attIdx);
/* 344 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 347 */             attIdx = this.context.getAttribute("", "URI");
/* 348 */             if (attIdx >= 0) {
/* 349 */               this.context.consumeAttribute(attIdx);
/* 350 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */         } 
/* 355 */       } catch (RuntimeException e) {
/* 356 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\ReferenceImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */