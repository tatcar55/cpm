/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AssertionIDReference;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AssertionIDReferenceImpl implements AssertionIDReference, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   public static final Class version = JAXBVersion.class;
/*     */   protected String _Value;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   public AssertionIDReferenceImpl() {}
/*     */   
/*     */   public AssertionIDReferenceImpl(String value) {
/*  22 */     this._Value = value;
/*     */   }
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  26 */     return AssertionIDReference.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  30 */     return "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  34 */     return "AssertionIDReference";
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  38 */     return this._Value;
/*     */   }
/*     */   
/*     */   public void setValue(String value) {
/*  42 */     this._Value = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  46 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  52 */     context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
/*  53 */     context.endNamespaceDecls();
/*  54 */     context.endAttributes();
/*     */     try {
/*  56 */       context.text(this._Value, "Value");
/*  57 */     } catch (Exception e) {
/*  58 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  60 */     context.endElement();
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
/*  74 */     return AssertionIDReference.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  78 */     if (schemaFragment == null) {
/*  79 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\024L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006NCNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\000sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\024L\000\fnamespaceURIq\000~\000\024xpq\000~\000\030q\000~\000\027sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\037psq\000~\000\nppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\021q\000~\000\027t\000\005QNameq\000~\000\033q\000~\000\035sq\000~\000 q\000~\000)q\000~\000\027sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\024L\000\fnamespaceURIq\000~\000\024xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\036\001q\000~\0001sq\000~\000+t\000\024AssertionIDReferencet\000%urn:oasis:names:tc:SAML:1.0:assertionsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\tq\000~\000#x");
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
/* 119 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AssertionIDReferenceImpl this$0;
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
/* 137 */       return AssertionIDReferenceImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 146 */       switch (this.state) {
/*     */         case 0:
/* 148 */           if ("AssertionIDReference" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 149 */             this.context.pushAttributes(__atts, true);
/* 150 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 155 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/* 158 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 169 */       switch (this.state) {
/*     */         case 2:
/* 171 */           if ("AssertionIDReference" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 172 */             this.context.popAttributes();
/* 173 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 178 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 181 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 192 */       switch (this.state) {
/*     */         case 3:
/* 194 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 197 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 208 */       switch (this.state) {
/*     */         case 3:
/* 210 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 213 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 225 */         switch (this.state) {
/*     */           case 1:
/* 227 */             this.state = 2;
/* 228 */             eatText1(value);
/*     */             return;
/*     */           case 3:
/* 231 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 234 */       } catch (RuntimeException e) {
/* 235 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 245 */         AssertionIDReferenceImpl.this._Value = WhiteSpaceProcessor.collapse(value);
/* 246 */       } catch (Exception e) {
/* 247 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AssertionIDReferenceImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */