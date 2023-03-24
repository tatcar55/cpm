/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectLocality;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SubjectLocalityImpl extends SubjectLocalityTypeImpl implements SubjectLocality, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return SubjectLocality.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "SubjectLocality";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality");
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
/*  58 */     return SubjectLocality.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004q\000~\000\020psr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\031L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\020psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\031L\000\fnamespaceURIq\000~\000\031xpq\000~\000\035q\000~\000\034sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\031L\000\fnamespaceURIq\000~\000\031xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\nDNSAddresst\000\000sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\017\001q\000~\000+sq\000~\000\013ppsq\000~\000\rq\000~\000\020pq\000~\000\024sq\000~\000%t\000\tIPAddressq\000~\000)q\000~\000+sq\000~\000\013ppsq\000~\000\rq\000~\000\020psq\000~\000\021ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\026q\000~\000\034t\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\037q\000~\000\"sq\000~\000#q\000~\0006q\000~\000\034sq\000~\000%t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000+sq\000~\000%t\000\017SubjectLocalityt\000%urn:oasis:names:tc:SAML:1.0:assertionsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\005\001pq\000~\000\nq\000~\000\tq\000~\0001q\000~\000\fq\000~\000-x");
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
/* 105 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SubjectLocalityImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 114 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 118 */       this(context);
/* 119 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 123 */       return SubjectLocalityImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 132 */       switch (this.state) {
/*     */         case 3:
/* 134 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 1:
/* 137 */           attIdx = this.context.getAttribute("", "DNSAddress");
/* 138 */           if (attIdx >= 0) {
/* 139 */             this.context.consumeAttribute(attIdx);
/* 140 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 143 */           attIdx = this.context.getAttribute("", "IPAddress");
/* 144 */           if (attIdx >= 0) {
/* 145 */             this.context.consumeAttribute(attIdx);
/* 146 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 149 */           SubjectLocalityImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new SubjectLocalityTypeImpl.Unmarshaller(SubjectLocalityImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 152 */           if ("SubjectLocality" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 153 */             this.context.pushAttributes(__atts, false);
/* 154 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 159 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 170 */       switch (this.state) {
/*     */         case 3:
/* 172 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 175 */           attIdx = this.context.getAttribute("", "DNSAddress");
/* 176 */           if (attIdx >= 0) {
/* 177 */             this.context.consumeAttribute(attIdx);
/* 178 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 181 */           attIdx = this.context.getAttribute("", "IPAddress");
/* 182 */           if (attIdx >= 0) {
/* 183 */             this.context.consumeAttribute(attIdx);
/* 184 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 187 */           SubjectLocalityImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new SubjectLocalityTypeImpl.Unmarshaller(SubjectLocalityImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 190 */           if ("SubjectLocality" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 191 */             this.context.popAttributes();
/* 192 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 197 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 208 */       switch (this.state) {
/*     */         case 3:
/* 210 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 213 */           if ("DNSAddress" == ___local && "" == ___uri) {
/* 214 */             SubjectLocalityImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new SubjectLocalityTypeImpl.Unmarshaller(SubjectLocalityImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 217 */           if ("IPAddress" == ___local && "" == ___uri) {
/* 218 */             SubjectLocalityImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new SubjectLocalityTypeImpl.Unmarshaller(SubjectLocalityImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 221 */           SubjectLocalityImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new SubjectLocalityTypeImpl.Unmarshaller(SubjectLocalityImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 224 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 235 */       switch (this.state) {
/*     */         case 3:
/* 237 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 240 */           attIdx = this.context.getAttribute("", "DNSAddress");
/* 241 */           if (attIdx >= 0) {
/* 242 */             this.context.consumeAttribute(attIdx);
/* 243 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 246 */           attIdx = this.context.getAttribute("", "IPAddress");
/* 247 */           if (attIdx >= 0) {
/* 248 */             this.context.consumeAttribute(attIdx);
/* 249 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/* 252 */           SubjectLocalityImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new SubjectLocalityTypeImpl.Unmarshaller(SubjectLocalityImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 255 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 267 */         switch (this.state) {
/*     */           case 3:
/* 269 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/* 272 */             attIdx = this.context.getAttribute("", "DNSAddress");
/* 273 */             if (attIdx >= 0) {
/* 274 */               this.context.consumeAttribute(attIdx);
/* 275 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 278 */             attIdx = this.context.getAttribute("", "IPAddress");
/* 279 */             if (attIdx >= 0) {
/* 280 */               this.context.consumeAttribute(attIdx);
/* 281 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 284 */             SubjectLocalityImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new SubjectLocalityTypeImpl.Unmarshaller(SubjectLocalityImpl.this, this.context), 2, value);
/*     */             return;
/*     */         } 
/* 287 */       } catch (RuntimeException e) {
/* 288 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SubjectLocalityImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */