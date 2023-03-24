/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignedInfo;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SignedInfoImpl extends SignedInfoTypeImpl implements SignedInfo, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return SignedInfo.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "SignedInfo";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "SignedInfo");
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
/*  58 */     return SignedInfo.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsq\000~\000\000pp\000sq\000~\000\rppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\025psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\024\001q\000~\000\031sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\032q\000~\000\037sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000!xq\000~\000\034t\000Bcom.sun.xml.wss.saml.internal.saml11.jaxb10.CanonicalizationMethodt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000pp\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\rppsq\000~\000\021q\000~\000\025psq\000~\000\026q\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\000Fcom.sun.xml.wss.saml.internal.saml11.jaxb10.CanonicalizationMethodTypeq\000~\000$sq\000~\000\rppsq\000~\000\026q\000~\000\025psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000!L\000\btypeNameq\000~\000!L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\025psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000!L\000\fnamespaceURIq\000~\000!xpq\000~\000:q\000~\0009sq\000~\000 t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\037sq\000~\000 t\000\026CanonicalizationMethodt\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\rppsq\000~\000\000pp\000sq\000~\000\rppsq\000~\000\021q\000~\000\025psq\000~\000\026q\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\000;com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureMethodq\000~\000$sq\000~\000\000pp\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\rppsq\000~\000\021q\000~\000\025psq\000~\000\026q\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\000?com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureMethodTypeq\000~\000$sq\000~\000\rppsq\000~\000\026q\000~\000\025pq\000~\0002q\000~\000Bq\000~\000\037sq\000~\000 t\000\017SignatureMethodq\000~\000Gsq\000~\000\021ppsq\000~\000\rppsq\000~\000\000pp\000sq\000~\000\rppsq\000~\000\021q\000~\000\025psq\000~\000\026q\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Referenceq\000~\000$sq\000~\000\000pp\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\rppsq\000~\000\021q\000~\000\025psq\000~\000\026q\000~\000\025pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\0009com.sun.xml.wss.saml.internal.saml11.jaxb10.ReferenceTypeq\000~\000$sq\000~\000\rppsq\000~\000\026q\000~\000\025pq\000~\0002q\000~\000Bq\000~\000\037sq\000~\000 t\000\tReferenceq\000~\000Gsq\000~\000\rppsq\000~\000\026q\000~\000\025psq\000~\000/ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\0004q\000~\0009t\000\002IDq\000~\000=\000q\000~\000?sq\000~\000@q\000~\000wq\000~\0009sq\000~\000 t\000\002Idt\000\000q\000~\000\037sq\000~\000\rppsq\000~\000\026q\000~\000\025pq\000~\0002q\000~\000Bq\000~\000\037sq\000~\000 t\000\nSignedInfoq\000~\000Gsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\034\001pq\000~\000\fq\000~\000&q\000~\000Pq\000~\000dq\000~\000\020q\000~\000(q\000~\000Jq\000~\000Rq\000~\000^q\000~\000fq\000~\000\nq\000~\000\023q\000~\000)q\000~\000Kq\000~\000Sq\000~\000_q\000~\000gq\000~\000\tq\000~\000oq\000~\000[q\000~\000-q\000~\000Wq\000~\000kq\000~\000|q\000~\000\013q\000~\000\016q\000~\000Hq\000~\000\\x");
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
/* 129 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SignedInfoImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 138 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 142 */       this(context);
/* 143 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 147 */       return SignedInfoImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 156 */       switch (this.state) {
/*     */         case 1:
/* 158 */           attIdx = this.context.getAttribute("", "Id");
/* 159 */           if (attIdx >= 0) {
/* 160 */             this.context.consumeAttribute(attIdx);
/* 161 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 164 */           if ("CanonicalizationMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 165 */             SignedInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SignedInfoTypeImpl.Unmarshaller(SignedInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 168 */           if ("CanonicalizationMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 169 */             SignedInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SignedInfoTypeImpl.Unmarshaller(SignedInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 174 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 177 */           if ("SignedInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 178 */             this.context.pushAttributes(__atts, false);
/* 179 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 184 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 195 */       switch (this.state) {
/*     */         case 1:
/* 197 */           attIdx = this.context.getAttribute("", "Id");
/* 198 */           if (attIdx >= 0) {
/* 199 */             this.context.consumeAttribute(attIdx);
/* 200 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 2:
/* 205 */           if ("SignedInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 206 */             this.context.popAttributes();
/* 207 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 212 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 215 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 226 */       switch (this.state) {
/*     */         case 1:
/* 228 */           if ("Id" == ___local && "" == ___uri) {
/* 229 */             SignedInfoImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new SignedInfoTypeImpl.Unmarshaller(SignedInfoImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 234 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 237 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 248 */       switch (this.state) {
/*     */         case 1:
/* 250 */           attIdx = this.context.getAttribute("", "Id");
/* 251 */           if (attIdx >= 0) {
/* 252 */             this.context.consumeAttribute(attIdx);
/* 253 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 258 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 261 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 273 */         switch (this.state) {
/*     */           case 1:
/* 275 */             attIdx = this.context.getAttribute("", "Id");
/* 276 */             if (attIdx >= 0) {
/* 277 */               this.context.consumeAttribute(attIdx);
/* 278 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 283 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 286 */       } catch (RuntimeException e) {
/* 287 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SignedInfoImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */