/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.Transform;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class TransformImpl extends TransformTypeImpl implements Transform, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return Transform.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "Transform";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "Transform");
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
/*  58 */     return Transform.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\fsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\016q\000~\000\023psq\000~\000\000q\000~\000\023p\000sr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004ppsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\022\001q\000~\000\031sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\001L\000\003nc2q\000~\000\001xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\034sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\001L\000\003nc2q\000~\000\001xq\000~\000\034sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIt\000\022Ljava/lang/String;xq\000~\000\034t\000\000sq\000~\000\"t\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\"t\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000q\000~\000\023p\000sq\000~\000\016ppsq\000~\000\020q\000~\000\023psq\000~\000\026q\000~\000\023pq\000~\000\031q\000~\000\037sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\032q\000~\000/sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000#L\000\fnamespaceURIq\000~\000#xq\000~\000\034t\000?com.sun.xml.wss.saml.internal.saml11.jaxb10.TransformType.XPathq\000~\000)q\000~\000/sq\000~\000\026ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000#L\000\btypeNameq\000~\000#L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\023psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000#L\000\fnamespaceURIq\000~\000#xpq\000~\000?q\000~\000>sq\000~\0000t\000\tAlgorithmq\000~\000%sq\000~\000\016ppsq\000~\000\026q\000~\000\023psq\000~\0004ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0009q\000~\000>t\000\005QNameq\000~\000Bq\000~\000Dsq\000~\000Eq\000~\000Nq\000~\000>sq\000~\0000t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000/sq\000~\0000t\000\tTransformq\000~\000'sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\t\001pq\000~\000\017q\000~\000+q\000~\000\024q\000~\000,q\000~\000\tq\000~\000Iq\000~\000\rq\000~\000\nq\000~\000\021x");
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
/* 115 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final TransformImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 124 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 128 */       this(context);
/* 129 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 133 */       return TransformImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 142 */       switch (this.state) {
/*     */         case 1:
/* 144 */           attIdx = this.context.getAttribute("", "Algorithm");
/* 145 */           if (attIdx >= 0) {
/* 146 */             this.context.consumeAttribute(attIdx);
/* 147 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 152 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 155 */           if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 156 */             this.context.pushAttributes(__atts, true);
/* 157 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 162 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 173 */       switch (this.state) {
/*     */         case 1:
/* 175 */           attIdx = this.context.getAttribute("", "Algorithm");
/* 176 */           if (attIdx >= 0) {
/* 177 */             this.context.consumeAttribute(attIdx);
/* 178 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 183 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 186 */           if ("Transform" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 187 */             this.context.popAttributes();
/* 188 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 193 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 204 */       switch (this.state) {
/*     */         case 1:
/* 206 */           if ("Algorithm" == ___local && "" == ___uri) {
/* 207 */             TransformImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new TransformTypeImpl.Unmarshaller(TransformImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 212 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 215 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 226 */       switch (this.state) {
/*     */         case 1:
/* 228 */           attIdx = this.context.getAttribute("", "Algorithm");
/* 229 */           if (attIdx >= 0) {
/* 230 */             this.context.consumeAttribute(attIdx);
/* 231 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 236 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 239 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 251 */         switch (this.state) {
/*     */           case 1:
/* 253 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 254 */             if (attIdx >= 0) {
/* 255 */               this.context.consumeAttribute(attIdx);
/* 256 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 261 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 264 */       } catch (RuntimeException e) {
/* 265 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\TransformImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */