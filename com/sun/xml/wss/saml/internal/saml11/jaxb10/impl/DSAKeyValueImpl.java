/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.DSAKeyValue;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class DSAKeyValueImpl extends DSAKeyValueTypeImpl implements DSAKeyValue, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return DSAKeyValue.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "http://www.w3.org/2000/09/xmldsig#";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "DSAKeyValue";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue");
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
/*  58 */     return DSAKeyValue.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsq\000~\000\007sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\000q\000~\000\022p\000sq\000~\000\007ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000'com.sun.msv.datatype.xsd.FinalComponent\000\000\000\000\000\000\000\001\002\000\001I\000\nfinalValuexr\000\036com.sun.msv.datatype.xsd.Proxy\000\000\000\000\000\000\000\001\002\000\001L\000\bbaseTypet\000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\035L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000\"http://www.w3.org/2000/09/xmldsig#t\000\fCryptoBinarysr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\034t\000 http://www.w3.org/2001/XMLSchemat\000\fbase64Binaryq\000~\000$\000\000\000\000sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\022psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\035L\000\fnamespaceURIq\000~\000\035xpq\000~\000+q\000~\000 sq\000~\000\016ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\022psq\000~\000\025ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000'q\000~\000*t\000\005QNameq\000~\000$q\000~\000-sq\000~\000.q\000~\0006q\000~\000*sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\035L\000\fnamespaceURIq\000~\000\035xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\021\001q\000~\000>sq\000~\0008t\000\001Pq\000~\000 sq\000~\000\000pp\000sq\000~\000\007ppq\000~\000\030sq\000~\000\016ppsq\000~\0001q\000~\000\022pq\000~\0003q\000~\000:q\000~\000>sq\000~\0008t\000\001Qq\000~\000 q\000~\000>sq\000~\000\016ppsq\000~\000\000q\000~\000\022p\000sq\000~\000\007ppq\000~\000\030sq\000~\000\016ppsq\000~\0001q\000~\000\022pq\000~\0003q\000~\000:q\000~\000>sq\000~\0008t\000\001Gq\000~\000 q\000~\000>sq\000~\000\000pp\000sq\000~\000\007ppq\000~\000\030sq\000~\000\016ppsq\000~\0001q\000~\000\022pq\000~\0003q\000~\000:q\000~\000>sq\000~\0008t\000\001Yq\000~\000 sq\000~\000\016ppsq\000~\000\000q\000~\000\022p\000sq\000~\000\007ppq\000~\000\030sq\000~\000\016ppsq\000~\0001q\000~\000\022pq\000~\0003q\000~\000:q\000~\000>sq\000~\0008t\000\001Jq\000~\000 q\000~\000>sq\000~\000\016ppsq\000~\000\007q\000~\000\022psq\000~\000\000q\000~\000\022p\000sq\000~\000\007ppq\000~\000\030sq\000~\000\016ppsq\000~\0001q\000~\000\022pq\000~\0003q\000~\000:q\000~\000>sq\000~\0008t\000\004Seedq\000~\000 sq\000~\000\000pp\000sq\000~\000\007ppq\000~\000\030sq\000~\000\016ppsq\000~\0001q\000~\000\022pq\000~\0003q\000~\000:q\000~\000>sq\000~\0008t\000\013PgenCounterq\000~\000 q\000~\000>sq\000~\000\016ppsq\000~\0001q\000~\000\022pq\000~\0003q\000~\000:q\000~\000>sq\000~\0008t\000\013DSAKeyValueq\000~\000 sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\032\001pq\000~\000\013q\000~\000\nq\000~\000\024q\000~\000Cq\000~\000Jq\000~\000\rq\000~\000Pq\000~\000Wq\000~\000_q\000~\000\017q\000~\000eq\000~\000\\q\000~\000\fq\000~\000\020q\000~\000]q\000~\0000q\000~\000Dq\000~\000Kq\000~\000Qq\000~\000Xq\000~\000`q\000~\000fq\000~\000Hq\000~\000Uq\000~\000jq\000~\000\tx");
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
/* 118 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final DSAKeyValueImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 127 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 131 */       this(context);
/* 132 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 136 */       return DSAKeyValueImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 145 */       switch (this.state) {
/*     */         case 3:
/* 147 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 150 */           if ("DSAKeyValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 151 */             this.context.pushAttributes(__atts, false);
/* 152 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 157 */           if ("P" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 158 */             DSAKeyValueImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new DSAKeyValueTypeImpl.Unmarshaller(DSAKeyValueImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 161 */           if ("G" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 162 */             DSAKeyValueImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new DSAKeyValueTypeImpl.Unmarshaller(DSAKeyValueImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 165 */           if ("Y" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 166 */             DSAKeyValueImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new DSAKeyValueTypeImpl.Unmarshaller(DSAKeyValueImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 171 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/* 182 */       switch (this.state) {
/*     */         case 2:
/* 184 */           if ("DSAKeyValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 185 */             this.context.popAttributes();
/* 186 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 191 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 194 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 205 */       switch (this.state) {
/*     */         case 3:
/* 207 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 210 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 221 */       switch (this.state) {
/*     */         case 3:
/* 223 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 226 */       super.leaveAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleText(String value) throws SAXException {
/*     */       try {
/* 238 */         switch (this.state) {
/*     */           case 3:
/* 240 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 243 */       } catch (RuntimeException e) {
/* 244 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\DSAKeyValueImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */