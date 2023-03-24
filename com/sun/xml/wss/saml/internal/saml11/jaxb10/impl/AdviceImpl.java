/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.Advice;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AdviceImpl extends AdviceTypeImpl implements Advice, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return Advice.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "Advice";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Advice");
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
/*  58 */     return Advice.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\nq\000~\000\020psq\000~\000\nq\000~\000\020psq\000~\000\000q\000~\000\020p\000sq\000~\000\nppsq\000~\000\fq\000~\000\020psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\020psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\017\001q\000~\000\031sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\032q\000~\000\037sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000!xq\000~\000\034t\000@com.sun.xml.wss.saml.internal.saml11.jaxb10.AssertionIDReferencet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000q\000~\000\020p\000sq\000~\000\nppsq\000~\000\fq\000~\000\020psq\000~\000\026q\000~\000\020pq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Assertionq\000~\000$sq\000~\000\000q\000~\000\020p\000sq\000~\000\026ppq\000~\000\031sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\001L\000\003nc2q\000~\000\001xq\000~\000\034q\000~\000\035sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\001L\000\003nc2q\000~\000\001xq\000~\000\034sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIq\000~\000!xq\000~\000\034t\000\000sq\000~\0001t\000%urn:oasis:names:tc:SAML:1.0:assertionsq\000~\0001q\000~\000$q\000~\000\037sq\000~\000\nppsq\000~\000\026q\000~\000\020psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000!L\000\btypeNameq\000~\000!L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\020psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000!L\000\fnamespaceURIq\000~\000!xpq\000~\000Dq\000~\000Csq\000~\000 t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\037sq\000~\000 t\000\006Adviceq\000~\0005sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\n\001pq\000~\000\021q\000~\000\013q\000~\000\024q\000~\000&q\000~\000\025q\000~\000'q\000~\000\tq\000~\000\016q\000~\0007q\000~\000\022x");
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
/*     */     private final AdviceImpl this$0;
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
/* 133 */       return AdviceImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 142 */       switch (this.state) {
/*     */         case 3:
/* 144 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 1:
/* 147 */           if ("AssertionIDReference" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 148 */             AdviceImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new AdviceTypeImpl.Unmarshaller(AdviceImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 151 */           if ("Assertion" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 152 */             AdviceImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new AdviceTypeImpl.Unmarshaller(AdviceImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 155 */           if ("" != ___uri && "urn:oasis:names:tc:SAML:1.0:assertion" != ___uri) {
/* 156 */             AdviceImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new AdviceTypeImpl.Unmarshaller(AdviceImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 159 */           AdviceImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new AdviceTypeImpl.Unmarshaller(AdviceImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 162 */           if ("Advice" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 163 */             this.context.pushAttributes(__atts, false);
/* 164 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 169 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 180 */       switch (this.state) {
/*     */         case 3:
/* 182 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 185 */           AdviceImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new AdviceTypeImpl.Unmarshaller(AdviceImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 2:
/* 188 */           if ("Advice" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 189 */             this.context.popAttributes();
/* 190 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 195 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 206 */       switch (this.state) {
/*     */         case 3:
/* 208 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 211 */           AdviceImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new AdviceTypeImpl.Unmarshaller(AdviceImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 214 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 225 */       switch (this.state) {
/*     */         case 3:
/* 227 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 1:
/* 230 */           AdviceImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new AdviceTypeImpl.Unmarshaller(AdviceImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 233 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 245 */         switch (this.state) {
/*     */           case 3:
/* 247 */             revertToParentFromText(value);
/*     */             return;
/*     */           case 1:
/* 250 */             AdviceImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new AdviceTypeImpl.Unmarshaller(AdviceImpl.this, this.context), 2, value);
/*     */             return;
/*     */         } 
/* 253 */       } catch (RuntimeException e) {
/* 254 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AdviceImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */