/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfo;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class KeyInfoImpl extends KeyInfoTypeImpl implements KeyInfo, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return KeyInfo.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "KeyInfo";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
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
/*  58 */     return KeyInfo.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\fppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsq\000~\000\020ppsq\000~\000\020ppsq\000~\000\020ppsq\000~\000\020ppsq\000~\000\020ppsq\000~\000\020ppsq\000~\000\000pp\000sq\000~\000\020ppsq\000~\000\016sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\034psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\033\001q\000~\000 sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000!q\000~\000&sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000(xq\000~\000#t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyNamet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000pp\000sq\000~\000\020ppsq\000~\000\016q\000~\000\034psq\000~\000\035q\000~\000\034pq\000~\000 q\000~\000$q\000~\000&sq\000~\000't\0004com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyValueq\000~\000+sq\000~\000\000pp\000sq\000~\000\020ppsq\000~\000\016q\000~\000\034psq\000~\000\035q\000~\000\034pq\000~\000 q\000~\000$q\000~\000&sq\000~\000't\000;com.sun.xml.wss.saml.internal.saml11.jaxb10.RetrievalMethodq\000~\000+sq\000~\000\000pp\000sq\000~\000\020ppsq\000~\000\016q\000~\000\034psq\000~\000\035q\000~\000\034pq\000~\000 q\000~\000$q\000~\000&sq\000~\000't\0004com.sun.xml.wss.saml.internal.saml11.jaxb10.X509Dataq\000~\000+sq\000~\000\000pp\000sq\000~\000\020ppsq\000~\000\016q\000~\000\034psq\000~\000\035q\000~\000\034pq\000~\000 q\000~\000$q\000~\000&sq\000~\000't\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.PGPDataq\000~\000+sq\000~\000\000pp\000sq\000~\000\020ppsq\000~\000\016q\000~\000\034psq\000~\000\035q\000~\000\034pq\000~\000 q\000~\000$q\000~\000&sq\000~\000't\0004com.sun.xml.wss.saml.internal.saml11.jaxb10.SPKIDataq\000~\000+sq\000~\000\000pp\000sq\000~\000\020ppsq\000~\000\016q\000~\000\034psq\000~\000\035q\000~\000\034pq\000~\000 q\000~\000$q\000~\000&sq\000~\000't\0004com.sun.xml.wss.saml.internal.saml11.jaxb10.MgmtDataq\000~\000+sq\000~\000\000pp\000sq\000~\000\035ppq\000~\000 sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\001L\000\003nc2q\000~\000\001xq\000~\000#q\000~\000$sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\001L\000\003nc2q\000~\000\001xq\000~\000#sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIq\000~\000(xq\000~\000#t\000\000sq\000~\000Vt\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000Vq\000~\000+sq\000~\000\020ppsq\000~\000\035q\000~\000\034psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000(L\000\btypeNameq\000~\000(L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\002IDsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\000sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\034psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000(L\000\fnamespaceURIq\000~\000(xpq\000~\000lq\000~\000ksq\000~\000't\000\002Idq\000~\000Xq\000~\000&sq\000~\000\020ppsq\000~\000\035q\000~\000\034psq\000~\000^ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000fq\000~\000kt\000\005QNameq\000~\000oq\000~\000qsq\000~\000rq\000~\000{q\000~\000ksq\000~\000't\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000&sq\000~\000't\000\007KeyInfoq\000~\000Zsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\033\001pq\000~\000\023q\000~\000\017q\000~\000\031q\000~\000-q\000~\0003q\000~\000\026q\000~\0009q\000~\000?q\000~\000Eq\000~\000\032q\000~\000.q\000~\0004q\000~\000:q\000~\000@q\000~\000Fq\000~\000Lq\000~\000Kq\000~\000\rq\000~\000\024q\000~\000\\q\000~\000vq\000~\000\tq\000~\000\027q\000~\000\nq\000~\000\025q\000~\000\022q\000~\000\021x");
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
/* 132 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final KeyInfoImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 141 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 145 */       this(context);
/* 146 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 150 */       return KeyInfoImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 159 */       switch (this.state) {
/*     */         case 3:
/* 161 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 164 */           if ("KeyInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 165 */             this.context.pushAttributes(__atts, true);
/* 166 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 171 */           attIdx = this.context.getAttribute("", "Id");
/* 172 */           if (attIdx >= 0) {
/* 173 */             this.context.consumeAttribute(attIdx);
/* 174 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 177 */           if ("KeyName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 178 */             KeyInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 181 */           if ("KeyValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 182 */             KeyInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 185 */           if ("RetrievalMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 186 */             KeyInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 189 */           if ("X509Data" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 190 */             KeyInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 193 */           if ("PGPData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 194 */             KeyInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 197 */           if ("SPKIData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 198 */             KeyInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 201 */           if ("MgmtData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 202 */             KeyInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 205 */           if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 206 */             KeyInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 209 */           KeyInfoImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/* 212 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 223 */       switch (this.state) {
/*     */         case 3:
/* 225 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 228 */           if ("KeyInfo" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 229 */             this.context.popAttributes();
/* 230 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 235 */           attIdx = this.context.getAttribute("", "Id");
/* 236 */           if (attIdx >= 0) {
/* 237 */             this.context.consumeAttribute(attIdx);
/* 238 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 241 */           KeyInfoImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 244 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 255 */       switch (this.state) {
/*     */         case 3:
/* 257 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 260 */           if ("Id" == ___local && "" == ___uri) {
/* 261 */             KeyInfoImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 264 */           KeyInfoImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 267 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 278 */       switch (this.state) {
/*     */         case 3:
/* 280 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 283 */           attIdx = this.context.getAttribute("", "Id");
/* 284 */           if (attIdx >= 0) {
/* 285 */             this.context.consumeAttribute(attIdx);
/* 286 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 289 */           KeyInfoImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 292 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 304 */         switch (this.state) {
/*     */           case 3:
/* 306 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/* 309 */             attIdx = this.context.getAttribute("", "Id");
/* 310 */             if (attIdx >= 0) {
/* 311 */               this.context.consumeAttribute(attIdx);
/* 312 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 315 */             KeyInfoImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new KeyInfoTypeImpl.Unmarshaller(KeyInfoImpl.this, this.context), 2, value);
/*     */             return;
/*     */         } 
/* 318 */       } catch (RuntimeException e) {
/* 319 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\KeyInfoImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */