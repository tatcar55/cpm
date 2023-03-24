/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AttributeStatementImpl extends AttributeStatementTypeImpl implements AttributeStatement, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return AttributeStatement.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "AttributeStatement";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement");
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
/*  58 */     return AttributeStatement.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsq\000~\000\000pp\000sq\000~\000\013ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\003xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\023psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\022\001q\000~\000\027sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\030q\000~\000\035sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\037xq\000~\000\032t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.Subjectt\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\000pp\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\013ppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectTypeq\000~\000\"sq\000~\000\013ppsq\000~\000\024q\000~\000\023psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\037L\000\btypeNameq\000~\000\037L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\023psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\037L\000\fnamespaceURIq\000~\000\037xpq\000~\0008q\000~\0007sq\000~\000\036t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\000\035sq\000~\000\036t\000\007Subjectt\000%urn:oasis:names:tc:SAML:1.0:assertionsq\000~\000\017ppsq\000~\000\013ppsq\000~\000\000pp\000sq\000~\000\013ppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Attributeq\000~\000\"sq\000~\000\000pp\000sq\000~\000\007ppsq\000~\000\000pp\000sq\000~\000\013ppsq\000~\000\017q\000~\000\023psq\000~\000\024q\000~\000\023pq\000~\000\027q\000~\000\033q\000~\000\035sq\000~\000\036t\0009com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeTypeq\000~\000\"sq\000~\000\013ppsq\000~\000\024q\000~\000\023pq\000~\0000q\000~\000@q\000~\000\035sq\000~\000\036t\000\tAttributeq\000~\000Esq\000~\000\013ppsq\000~\000\024q\000~\000\023pq\000~\0000q\000~\000@q\000~\000\035sq\000~\000\036t\000\022AttributeStatementq\000~\000Esr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\022\001pq\000~\000$q\000~\000Oq\000~\000\016q\000~\000&q\000~\000Iq\000~\000Qq\000~\000\021q\000~\000'q\000~\000Jq\000~\000Rq\000~\000\tq\000~\000Fq\000~\000+q\000~\000Vq\000~\000Zq\000~\000\fq\000~\000Gq\000~\000\nx");
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
/* 117 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AttributeStatementImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 126 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 130 */       this(context);
/* 131 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 135 */       return AttributeStatementImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 144 */       switch (this.state) {
/*     */         case 1:
/* 146 */           if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 147 */             AttributeStatementImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new AttributeStatementTypeImpl.Unmarshaller(AttributeStatementImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 150 */           if ("Subject" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 151 */             AttributeStatementImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new AttributeStatementTypeImpl.Unmarshaller(AttributeStatementImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/* 154 */           AttributeStatementImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new AttributeStatementTypeImpl.Unmarshaller(AttributeStatementImpl.this, this.context), 2, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 3:
/* 157 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 160 */           if ("AttributeStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 161 */             this.context.pushAttributes(__atts, false);
/* 162 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 167 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 178 */       switch (this.state) {
/*     */         case 2:
/* 180 */           if ("AttributeStatement" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 181 */             this.context.popAttributes();
/* 182 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 187 */           AttributeStatementImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new AttributeStatementTypeImpl.Unmarshaller(AttributeStatementImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 3:
/* 190 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
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
/* 206 */           AttributeStatementImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new AttributeStatementTypeImpl.Unmarshaller(AttributeStatementImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 3:
/* 209 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 212 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 223 */       switch (this.state) {
/*     */         case 1:
/* 225 */           AttributeStatementImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new AttributeStatementTypeImpl.Unmarshaller(AttributeStatementImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 3:
/* 228 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 231 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 243 */         switch (this.state) {
/*     */           case 1:
/* 245 */             AttributeStatementImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new AttributeStatementTypeImpl.Unmarshaller(AttributeStatementImpl.this, this.context), 2, value);
/*     */             return;
/*     */           case 3:
/* 248 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 251 */       } catch (RuntimeException e) {
/* 252 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AttributeStatementImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */