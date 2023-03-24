/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformsType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class TransformsTypeImpl implements TransformsType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   public static final Class version = JAXBVersion.class; protected ListImpl _Transform; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformTypeImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  19 */     return TransformsType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getTransform() {
/*  23 */     if (this._Transform == null) {
/*  24 */       this._Transform = new ListImpl(new ArrayList());
/*     */     }
/*  26 */     return this._Transform;
/*     */   }
/*     */   
/*     */   public List getTransform() {
/*  30 */     return (List)_getTransform();
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  34 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  40 */     int idx1 = 0;
/*  41 */     int len1 = (this._Transform == null) ? 0 : this._Transform.size();
/*  42 */     while (idx1 != len1) {
/*  43 */       if (this._Transform.get(idx1) instanceof javax.xml.bind.Element) {
/*  44 */         context.childAsBody((JAXBObject)this._Transform.get(idx1++), "Transform"); continue;
/*     */       } 
/*  46 */       context.startElement("http://www.w3.org/2000/09/xmldsig#", "Transform");
/*  47 */       int idx_0 = idx1;
/*  48 */       context.childAsURIs((JAXBObject)this._Transform.get(idx_0++), "Transform");
/*  49 */       context.endNamespaceDecls();
/*  50 */       int idx_1 = idx1;
/*  51 */       context.childAsAttributes((JAXBObject)this._Transform.get(idx_1++), "Transform");
/*  52 */       context.endAttributes();
/*  53 */       context.childAsBody((JAXBObject)this._Transform.get(idx1++), "Transform");
/*  54 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  62 */     int idx1 = 0;
/*  63 */     int len1 = (this._Transform == null) ? 0 : this._Transform.size();
/*  64 */     while (idx1 != len1) {
/*  65 */       if (this._Transform.get(idx1) instanceof javax.xml.bind.Element) {
/*  66 */         context.childAsAttributes((JAXBObject)this._Transform.get(idx1++), "Transform"); continue;
/*     */       } 
/*  68 */       idx1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  76 */     int idx1 = 0;
/*  77 */     int len1 = (this._Transform == null) ? 0 : this._Transform.size();
/*  78 */     while (idx1 != len1) {
/*  79 */       if (this._Transform.get(idx1) instanceof javax.xml.bind.Element) {
/*  80 */         context.childAsURIs((JAXBObject)this._Transform.get(idx1++), "Transform"); continue;
/*     */       } 
/*  82 */       idx1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  88 */     return TransformsType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  92 */     if (schemaFragment == null) {
/*  93 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\002L\000\004exp2q\000~\000\002xq\000~\000\003ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\006ppsq\000~\000\000sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\nxq\000~\000\003q\000~\000\020psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\017\001q\000~\000\024sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\025q\000~\000\032sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\034xq\000~\000\027t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Transformt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\tpp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\007ppsq\000~\000\tpp\000sq\000~\000\006ppsq\000~\000\000q\000~\000\020psq\000~\000\021q\000~\000\020pq\000~\000\024q\000~\000\030q\000~\000\032sq\000~\000\033t\0009com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformTypeq\000~\000\037sq\000~\000\006ppsq\000~\000\021q\000~\000\020psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\034L\000\btypeNameq\000~\000\034L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\020psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\034L\000\fnamespaceURIq\000~\000\034xpq\000~\0006q\000~\0005sq\000~\000\033t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\032sq\000~\000\033t\000\tTransformt\000\"http://www.w3.org/2000/09/xmldsig#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\b\001pq\000~\000\"q\000~\000\rq\000~\000$q\000~\000\016q\000~\000%q\000~\000\005q\000~\000)q\000~\000\bx");
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
/* 139 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final TransformsTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 148 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 152 */       this(context);
/* 153 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 157 */       return TransformsTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 166 */       switch (this.state) {
/*     */         case 0:
/* 168 */           if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 169 */             TransformsTypeImpl.this._getTransform().add(spawnChildFromEnterElement((TransformsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformImpl == null) ? (TransformsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformImpl = TransformsTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformImpl")) : TransformsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 172 */           if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 173 */             this.context.pushAttributes(__atts, true);
/* 174 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 179 */           if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 180 */             TransformsTypeImpl.this._getTransform().add(spawnChildFromEnterElement((TransformsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformImpl == null) ? (TransformsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformImpl = TransformsTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformImpl")) : TransformsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 183 */           if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 184 */             this.context.pushAttributes(__atts, true);
/* 185 */             this.state = 1;
/*     */             return;
/*     */           } 
/* 188 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 1:
/* 191 */           attIdx = this.context.getAttribute("", "Algorithm");
/* 192 */           if (attIdx >= 0) {
/* 193 */             this.context.consumeAttribute(attIdx);
/* 194 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 199 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 210 */       switch (this.state) {
/*     */         case 2:
/* 212 */           if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 213 */             this.context.popAttributes();
/* 214 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 219 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 222 */           attIdx = this.context.getAttribute("", "Algorithm");
/* 223 */           if (attIdx >= 0) {
/* 224 */             this.context.consumeAttribute(attIdx);
/* 225 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 230 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 241 */       switch (this.state) {
/*     */         case 3:
/* 243 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 246 */           if ("Algorithm" == ___local && "" == ___uri) {
/* 247 */             TransformsTypeImpl.this._getTransform().add(spawnChildFromEnterAttribute((TransformsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformTypeImpl == null) ? (TransformsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformTypeImpl = TransformsTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformTypeImpl")) : TransformsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$TransformTypeImpl, 2, ___uri, ___local, ___qname));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 252 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 263 */       switch (this.state) {
/*     */         case 3:
/* 265 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 268 */           attIdx = this.context.getAttribute("", "Algorithm");
/* 269 */           if (attIdx >= 0) {
/* 270 */             this.context.consumeAttribute(attIdx);
/* 271 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 276 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 288 */         switch (this.state) {
/*     */           case 3:
/* 290 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/* 293 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 294 */             if (attIdx >= 0) {
/* 295 */               this.context.consumeAttribute(attIdx);
/* 296 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */         } 
/* 301 */       } catch (RuntimeException e) {
/* 302 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\TransformsTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */