/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.Object;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ObjectImpl extends ObjectTypeImpl implements Object, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return Object.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "Object";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "Object");
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
/*  58 */     return Object.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\016sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\000q\000~\000\025p\000sr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004ppsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\024\001q\000~\000\032sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIt\000\022Ljava/lang/String;xq\000~\000\035t\000+http://java.sun.com/jaxb/xjc/dummy-elementssr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\033q\000~\000$sq\000~\000\020ppsq\000~\000\027q\000~\000\025psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000 L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\025psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\0002q\000~\0001sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xq\000~\000\035t\000\bEncodingt\000\000q\000~\000$sq\000~\000\020ppsq\000~\000\027q\000~\000\025psq\000~\000'ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\000,q\000~\0001t\000\002IDq\000~\0005\000q\000~\0007sq\000~\0008q\000~\000Fq\000~\0001sq\000~\000:t\000\002Idq\000~\000=q\000~\000$sq\000~\000\020ppsq\000~\000\027q\000~\000\025psq\000~\000'q\000~\000\025psq\000~\000Dq\000~\0001t\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xq\000~\0004\001q\000~\0007sq\000~\0008q\000~\000Nq\000~\0001sq\000~\000:t\000\bMimeTypeq\000~\000=q\000~\000$sq\000~\000\020ppsq\000~\000\027q\000~\000\025psq\000~\000'ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000,q\000~\0001t\000\005QNameq\000~\0005q\000~\0007sq\000~\0008q\000~\000Yq\000~\0001sq\000~\000:t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000$sq\000~\000:t\000\006Objectt\000\"http://www.w3.org/2000/09/xmldsig#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\013\001pq\000~\000Jq\000~\000\023q\000~\000\013q\000~\000\tq\000~\000\021q\000~\000%q\000~\000Tq\000~\000>q\000~\000\017q\000~\000\fq\000~\000\nx");
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
/* 119 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final ObjectImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 128 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 132 */       this(context);
/* 133 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 137 */       return ObjectImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 146 */       switch (this.state) {
/*     */         case 0:
/* 148 */           if ("Object" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 149 */             this.context.pushAttributes(__atts, true);
/* 150 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 155 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 1:
/* 158 */           attIdx = this.context.getAttribute("", "Encoding");
/* 159 */           if (attIdx >= 0) {
/* 160 */             this.context.consumeAttribute(attIdx);
/* 161 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 164 */           attIdx = this.context.getAttribute("", "Id");
/* 165 */           if (attIdx >= 0) {
/* 166 */             this.context.consumeAttribute(attIdx);
/* 167 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 170 */           attIdx = this.context.getAttribute("", "MimeType");
/* 171 */           if (attIdx >= 0) {
/* 172 */             this.context.consumeAttribute(attIdx);
/* 173 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             
/*     */             return;
/*     */           } 
/* 177 */           ObjectImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ObjectTypeImpl.Unmarshaller(ObjectImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
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
/* 196 */           if ("Object" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 197 */             this.context.popAttributes();
/* 198 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 203 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 206 */           attIdx = this.context.getAttribute("", "Encoding");
/* 207 */           if (attIdx >= 0) {
/* 208 */             this.context.consumeAttribute(attIdx);
/* 209 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 212 */           attIdx = this.context.getAttribute("", "Id");
/* 213 */           if (attIdx >= 0) {
/* 214 */             this.context.consumeAttribute(attIdx);
/* 215 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 218 */           attIdx = this.context.getAttribute("", "MimeType");
/* 219 */           if (attIdx >= 0) {
/* 220 */             this.context.consumeAttribute(attIdx);
/* 221 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 224 */           ObjectImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new ObjectTypeImpl.Unmarshaller(ObjectImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 227 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 238 */       switch (this.state) {
/*     */         case 3:
/* 240 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 243 */           if ("Encoding" == ___local && "" == ___uri) {
/* 244 */             ObjectImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ObjectTypeImpl.Unmarshaller(ObjectImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 247 */           if ("Id" == ___local && "" == ___uri) {
/* 248 */             ObjectImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ObjectTypeImpl.Unmarshaller(ObjectImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 251 */           if ("MimeType" == ___local && "" == ___uri) {
/* 252 */             ObjectImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ObjectTypeImpl.Unmarshaller(ObjectImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 255 */           ObjectImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ObjectTypeImpl.Unmarshaller(ObjectImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 258 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 269 */       switch (this.state) {
/*     */         case 3:
/* 271 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 274 */           attIdx = this.context.getAttribute("", "Encoding");
/* 275 */           if (attIdx >= 0) {
/* 276 */             this.context.consumeAttribute(attIdx);
/* 277 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 280 */           attIdx = this.context.getAttribute("", "Id");
/* 281 */           if (attIdx >= 0) {
/* 282 */             this.context.consumeAttribute(attIdx);
/* 283 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 286 */           attIdx = this.context.getAttribute("", "MimeType");
/* 287 */           if (attIdx >= 0) {
/* 288 */             this.context.consumeAttribute(attIdx);
/* 289 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 292 */           ObjectImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new ObjectTypeImpl.Unmarshaller(ObjectImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 295 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 307 */         switch (this.state) {
/*     */           case 3:
/* 309 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/* 312 */             attIdx = this.context.getAttribute("", "Encoding");
/* 313 */             if (attIdx >= 0) {
/* 314 */               this.context.consumeAttribute(attIdx);
/* 315 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 318 */             attIdx = this.context.getAttribute("", "Id");
/* 319 */             if (attIdx >= 0) {
/* 320 */               this.context.consumeAttribute(attIdx);
/* 321 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 324 */             attIdx = this.context.getAttribute("", "MimeType");
/* 325 */             if (attIdx >= 0) {
/* 326 */               this.context.consumeAttribute(attIdx);
/* 327 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 330 */             ObjectImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new ObjectTypeImpl.Unmarshaller(ObjectImpl.this, this.context), 2, value);
/*     */             return;
/*     */         } 
/* 333 */       } catch (RuntimeException e) {
/* 334 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\ObjectImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */