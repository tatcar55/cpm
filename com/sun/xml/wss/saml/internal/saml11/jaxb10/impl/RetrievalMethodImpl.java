/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.RetrievalMethod;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class RetrievalMethodImpl extends RetrievalMethodTypeImpl implements RetrievalMethod, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return RetrievalMethod.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "RetrievalMethod";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod");
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
/*  58 */     return RetrievalMethod.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsq\000~\000\fsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\000q\000~\000\020p\000sq\000~\000\fppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004q\000~\000\020psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\020psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\017\001q\000~\000\031sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\032q\000~\000\037sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000!xq\000~\000\034t\0006com.sun.xml.wss.saml.internal.saml11.jaxb10.Transformst\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000q\000~\000\020p\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\fppsq\000~\000\023q\000~\000\020psq\000~\000\026q\000~\000\020pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\000:com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformsTypeq\000~\000$sq\000~\000\fppsq\000~\000\026q\000~\000\020psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000!L\000\btypeNameq\000~\000!L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\020psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000!L\000\fnamespaceURIq\000~\000!xpq\000~\000:q\000~\0009sq\000~\000 t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\037sq\000~\000 t\000\nTransformst\000\"http://www.w3.org/2000/09/xmldsig#q\000~\000\037sq\000~\000\fppsq\000~\000\026q\000~\000\020psq\000~\000/ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0004q\000~\0009t\000\006anyURIq\000~\000=q\000~\000?sq\000~\000@q\000~\000Mq\000~\0009sq\000~\000 t\000\004Typet\000\000q\000~\000\037sq\000~\000\fppsq\000~\000\026q\000~\000\020pq\000~\000Jsq\000~\000 t\000\003URIq\000~\000Qq\000~\000\037sq\000~\000\fppsq\000~\000\026q\000~\000\020pq\000~\0002q\000~\000Bq\000~\000\037sq\000~\000 t\000\017RetrievalMethodq\000~\000Gsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\016\001pq\000~\000\nq\000~\000&q\000~\000\022q\000~\000(q\000~\000\025q\000~\000)q\000~\000Rq\000~\000\013q\000~\000\tq\000~\000-q\000~\000Vq\000~\000Hq\000~\000\rq\000~\000\016x");
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
/* 114 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final RetrievalMethodImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 123 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 127 */       this(context);
/* 128 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 132 */       return RetrievalMethodImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 141 */       switch (this.state) {
/*     */         case 1:
/* 143 */           attIdx = this.context.getAttribute("", "Type");
/* 144 */           if (attIdx >= 0) {
/* 145 */             this.context.consumeAttribute(attIdx);
/* 146 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 149 */           attIdx = this.context.getAttribute("", "URI");
/* 150 */           if (attIdx >= 0) {
/* 151 */             this.context.consumeAttribute(attIdx);
/* 152 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 155 */           if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 156 */             RetrievalMethodImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new RetrievalMethodTypeImpl.Unmarshaller(RetrievalMethodImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 159 */           if ("Transforms" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 160 */             RetrievalMethodImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new RetrievalMethodTypeImpl.Unmarshaller(RetrievalMethodImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 163 */           RetrievalMethodImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new RetrievalMethodTypeImpl.Unmarshaller(RetrievalMethodImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 166 */           if ("RetrievalMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 167 */             this.context.pushAttributes(__atts, false);
/* 168 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 173 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/* 176 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 187 */       switch (this.state) {
/*     */         case 1:
/* 189 */           attIdx = this.context.getAttribute("", "Type");
/* 190 */           if (attIdx >= 0) {
/* 191 */             this.context.consumeAttribute(attIdx);
/* 192 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 195 */           attIdx = this.context.getAttribute("", "URI");
/* 196 */           if (attIdx >= 0) {
/* 197 */             this.context.consumeAttribute(attIdx);
/* 198 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 201 */           RetrievalMethodImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new RetrievalMethodTypeImpl.Unmarshaller(RetrievalMethodImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 204 */           if ("RetrievalMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 205 */             this.context.popAttributes();
/* 206 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 211 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 214 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 225 */       switch (this.state) {
/*     */         case 1:
/* 227 */           if ("Type" == ___local && "" == ___uri) {
/* 228 */             RetrievalMethodImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new RetrievalMethodTypeImpl.Unmarshaller(RetrievalMethodImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 231 */           if ("URI" == ___local && "" == ___uri) {
/* 232 */             RetrievalMethodImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new RetrievalMethodTypeImpl.Unmarshaller(RetrievalMethodImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 235 */           RetrievalMethodImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new RetrievalMethodTypeImpl.Unmarshaller(RetrievalMethodImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 3:
/* 238 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 241 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 252 */       switch (this.state) {
/*     */         case 1:
/* 254 */           attIdx = this.context.getAttribute("", "Type");
/* 255 */           if (attIdx >= 0) {
/* 256 */             this.context.consumeAttribute(attIdx);
/* 257 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 260 */           attIdx = this.context.getAttribute("", "URI");
/* 261 */           if (attIdx >= 0) {
/* 262 */             this.context.consumeAttribute(attIdx);
/* 263 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 266 */           RetrievalMethodImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new RetrievalMethodTypeImpl.Unmarshaller(RetrievalMethodImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 3:
/* 269 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 272 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 284 */         switch (this.state) {
/*     */           case 1:
/* 286 */             attIdx = this.context.getAttribute("", "Type");
/* 287 */             if (attIdx >= 0) {
/* 288 */               this.context.consumeAttribute(attIdx);
/* 289 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 292 */             attIdx = this.context.getAttribute("", "URI");
/* 293 */             if (attIdx >= 0) {
/* 294 */               this.context.consumeAttribute(attIdx);
/* 295 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 298 */             RetrievalMethodImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new RetrievalMethodTypeImpl.Unmarshaller(RetrievalMethodImpl.this, this.context), 2, value);
/*     */             return;
/*     */           case 3:
/* 301 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 304 */       } catch (RuntimeException e) {
/* 305 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\RetrievalMethodImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */