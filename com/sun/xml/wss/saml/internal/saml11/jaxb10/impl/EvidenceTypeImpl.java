/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.EvidenceType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class EvidenceTypeImpl implements EvidenceType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   public static final Class version = JAXBVersion.class; protected ListImpl _AssertionIDReferenceOrAssertion; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  19 */     return EvidenceType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getAssertionIDReferenceOrAssertion() {
/*  23 */     if (this._AssertionIDReferenceOrAssertion == null) {
/*  24 */       this._AssertionIDReferenceOrAssertion = new ListImpl(new ArrayList());
/*     */     }
/*  26 */     return this._AssertionIDReferenceOrAssertion;
/*     */   }
/*     */   
/*     */   public List getAssertionIDReferenceOrAssertion() {
/*  30 */     return (List)_getAssertionIDReferenceOrAssertion();
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
/*  41 */     int len1 = (this._AssertionIDReferenceOrAssertion == null) ? 0 : this._AssertionIDReferenceOrAssertion.size();
/*  42 */     while (idx1 != len1) {
/*  43 */       context.childAsBody((JAXBObject)this._AssertionIDReferenceOrAssertion.get(idx1++), "AssertionIDReferenceOrAssertion");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  50 */     int idx1 = 0;
/*  51 */     int len1 = (this._AssertionIDReferenceOrAssertion == null) ? 0 : this._AssertionIDReferenceOrAssertion.size();
/*  52 */     while (idx1 != len1) {
/*  53 */       context.childAsAttributes((JAXBObject)this._AssertionIDReferenceOrAssertion.get(idx1++), "AssertionIDReferenceOrAssertion");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  60 */     int idx1 = 0;
/*  61 */     int len1 = (this._AssertionIDReferenceOrAssertion == null) ? 0 : this._AssertionIDReferenceOrAssertion.size();
/*  62 */     while (idx1 != len1) {
/*  63 */       context.childAsURIs((JAXBObject)this._AssertionIDReferenceOrAssertion.get(idx1++), "AssertionIDReferenceOrAssertion");
/*     */     }
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  68 */     return EvidenceType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  72 */     if (schemaFragment == null) {
/*  73 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\002L\000\004exp2q\000~\000\002xq\000~\000\003ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\006ppsq\000~\000\000sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\nxq\000~\000\003q\000~\000\020psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\017\001q\000~\000\024sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\025q\000~\000\032sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\034xq\000~\000\027t\000@com.sun.xml.wss.saml.internal.saml11.jaxb10.AssertionIDReferencet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\tpp\000sq\000~\000\006ppsq\000~\000\000q\000~\000\020psq\000~\000\021q\000~\000\020pq\000~\000\024q\000~\000\030q\000~\000\032sq\000~\000\033t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Assertionq\000~\000\037sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\006\001pq\000~\000\rq\000~\000!q\000~\000\016q\000~\000\"q\000~\000\005q\000~\000\bx");
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
/* 102 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final EvidenceTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 111 */       super(context, "--");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 115 */       this(context);
/* 116 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 120 */       return EvidenceTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 129 */       switch (this.state) {
/*     */         case 1:
/* 131 */           if ("AssertionIDReference" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 132 */             EvidenceTypeImpl.this._getAssertionIDReferenceOrAssertion().add(spawnChildFromEnterElement((EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl == null) ? (EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl = EvidenceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionIDReferenceImpl")) : EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl, 1, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 135 */           if ("Assertion" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 136 */             EvidenceTypeImpl.this._getAssertionIDReferenceOrAssertion().add(spawnChildFromEnterElement((EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl == null) ? (EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl = EvidenceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionImpl")) : EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl, 1, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 139 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 0:
/* 142 */           if ("AssertionIDReference" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 143 */             EvidenceTypeImpl.this._getAssertionIDReferenceOrAssertion().add(spawnChildFromEnterElement((EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl == null) ? (EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl = EvidenceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionIDReferenceImpl")) : EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl, 1, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 146 */           if ("Assertion" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 147 */             EvidenceTypeImpl.this._getAssertionIDReferenceOrAssertion().add(spawnChildFromEnterElement((EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl == null) ? (EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl = EvidenceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionImpl")) : EvidenceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl, 1, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 152 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 163 */       switch (this.state) {
/*     */         case 1:
/* 165 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 168 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 179 */       switch (this.state) {
/*     */         case 1:
/* 181 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 184 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 195 */       switch (this.state) {
/*     */         case 1:
/* 197 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 200 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 212 */         switch (this.state) {
/*     */           case 1:
/* 214 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 217 */       } catch (RuntimeException e) {
/* 218 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\EvidenceTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */