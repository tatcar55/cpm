/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.DigestMethod;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class DigestMethodImpl extends DigestMethodTypeImpl implements DigestMethod, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return DigestMethod.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "DigestMethod";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
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
/*  58 */     return DigestMethod.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\fsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\000q\000~\000\023p\000sr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004ppsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\022\001q\000~\000\030sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\001L\000\003nc2q\000~\000\001xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\033sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\001L\000\003nc2q\000~\000\001xq\000~\000\033sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIt\000\022Ljava/lang/String;xq\000~\000\033t\000\000sq\000~\000!t\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000!t\000+http://java.sun.com/jaxb/xjc/dummy-elementssr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\031q\000~\000*sq\000~\000\025ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\"L\000\btypeNameq\000~\000\"L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\023psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\"L\000\fnamespaceURIq\000~\000\"xpq\000~\0007q\000~\0006sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\"L\000\fnamespaceURIq\000~\000\"xq\000~\000\033t\000\tAlgorithmq\000~\000$sq\000~\000\016ppsq\000~\000\025q\000~\000\023psq\000~\000,ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0001q\000~\0006t\000\005QNameq\000~\000:q\000~\000<sq\000~\000=q\000~\000Gq\000~\0006sq\000~\000?t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000*sq\000~\000?t\000\fDigestMethodq\000~\000&sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\006\001pq\000~\000\017q\000~\000\021q\000~\000\rq\000~\000Bq\000~\000\tq\000~\000\nx");
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
/* 113 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final DigestMethodImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 122 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 126 */       this(context);
/* 127 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 131 */       return DigestMethodImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 140 */       switch (this.state) {
/*     */         case 3:
/* 142 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 1:
/* 145 */           attIdx = this.context.getAttribute("", "Algorithm");
/* 146 */           if (attIdx >= 0) {
/* 147 */             this.context.consumeAttribute(attIdx);
/* 148 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 153 */           if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 154 */             this.context.pushAttributes(__atts, true);
/* 155 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 160 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 171 */       switch (this.state) {
/*     */         case 3:
/* 173 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 176 */           if ("DigestMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 177 */             this.context.popAttributes();
/* 178 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 183 */           attIdx = this.context.getAttribute("", "Algorithm");
/* 184 */           if (attIdx >= 0) {
/* 185 */             this.context.consumeAttribute(attIdx);
/* 186 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 191 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 202 */       switch (this.state) {
/*     */         case 3:
/* 204 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 207 */           if ("Algorithm" == ___local && "" == ___uri) {
/* 208 */             DigestMethodImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new DigestMethodTypeImpl.Unmarshaller(DigestMethodImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 213 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 224 */       switch (this.state) {
/*     */         case 3:
/* 226 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 229 */           attIdx = this.context.getAttribute("", "Algorithm");
/* 230 */           if (attIdx >= 0) {
/* 231 */             this.context.consumeAttribute(attIdx);
/* 232 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 237 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 249 */         switch (this.state) {
/*     */           case 3:
/* 251 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/* 254 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 255 */             if (attIdx >= 0) {
/* 256 */               this.context.consumeAttribute(attIdx);
/* 257 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */         } 
/* 262 */       } catch (RuntimeException e) {
/* 263 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\DigestMethodImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */