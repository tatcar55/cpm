/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AdviceType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AdviceTypeImpl implements AdviceType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   public static final Class version = JAXBVersion.class; protected ListImpl _AssertionIDReferenceOrAssertionOrAny; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  19 */     return AdviceType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getAssertionIDReferenceOrAssertionOrAny() {
/*  23 */     if (this._AssertionIDReferenceOrAssertionOrAny == null) {
/*  24 */       this._AssertionIDReferenceOrAssertionOrAny = new ListImpl(new ArrayList());
/*     */     }
/*  26 */     return this._AssertionIDReferenceOrAssertionOrAny;
/*     */   }
/*     */   
/*     */   public List getAssertionIDReferenceOrAssertionOrAny() {
/*  30 */     return (List)_getAssertionIDReferenceOrAssertionOrAny();
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
/*  41 */     int len1 = (this._AssertionIDReferenceOrAssertionOrAny == null) ? 0 : this._AssertionIDReferenceOrAssertionOrAny.size();
/*  42 */     while (idx1 != len1) {
/*     */       
/*  44 */       Object o = this._AssertionIDReferenceOrAssertionOrAny.get(idx1);
/*  45 */       if (o instanceof JAXBObject) {
/*  46 */         context.childAsBody((JAXBObject)this._AssertionIDReferenceOrAssertionOrAny.get(idx1++), "AssertionIDReferenceOrAssertionOrAny"); continue;
/*     */       } 
/*  48 */       if (o instanceof Object) {
/*  49 */         context.childAsBody((JAXBObject)this._AssertionIDReferenceOrAssertionOrAny.get(idx1++), "AssertionIDReferenceOrAssertionOrAny"); continue;
/*     */       } 
/*  51 */       Util.handleTypeMismatchError(context, this, "AssertionIDReferenceOrAssertionOrAny", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  61 */     int idx1 = 0;
/*  62 */     int len1 = (this._AssertionIDReferenceOrAssertionOrAny == null) ? 0 : this._AssertionIDReferenceOrAssertionOrAny.size();
/*  63 */     while (idx1 != len1) {
/*     */       
/*  65 */       Object o = this._AssertionIDReferenceOrAssertionOrAny.get(idx1);
/*  66 */       if (o instanceof JAXBObject) {
/*  67 */         context.childAsAttributes((JAXBObject)this._AssertionIDReferenceOrAssertionOrAny.get(idx1++), "AssertionIDReferenceOrAssertionOrAny"); continue;
/*     */       } 
/*  69 */       if (o instanceof Object) {
/*  70 */         idx1++; continue;
/*     */       } 
/*  72 */       Util.handleTypeMismatchError(context, this, "AssertionIDReferenceOrAssertionOrAny", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  82 */     int idx1 = 0;
/*  83 */     int len1 = (this._AssertionIDReferenceOrAssertionOrAny == null) ? 0 : this._AssertionIDReferenceOrAssertionOrAny.size();
/*  84 */     while (idx1 != len1) {
/*     */       
/*  86 */       Object o = this._AssertionIDReferenceOrAssertionOrAny.get(idx1);
/*  87 */       if (o instanceof JAXBObject) {
/*  88 */         context.childAsURIs((JAXBObject)this._AssertionIDReferenceOrAssertionOrAny.get(idx1++), "AssertionIDReferenceOrAssertionOrAny"); continue;
/*     */       } 
/*  90 */       if (o instanceof Object) {
/*  91 */         idx1++; continue;
/*     */       } 
/*  93 */       Util.handleTypeMismatchError(context, this, "AssertionIDReferenceOrAssertionOrAny", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 101 */     return AdviceType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 105 */     if (schemaFragment == null) {
/* 106 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\000q\000~\000\npsq\000~\000\000q\000~\000\npsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\np\000sq\000~\000\000ppsq\000~\000\006q\000~\000\npsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\016xq\000~\000\003q\000~\000\npsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\t\001q\000~\000\026sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\027q\000~\000\034sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\036xq\000~\000\031t\000@com.sun.xml.wss.saml.internal.saml11.jaxb10.AssertionIDReferencet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\rq\000~\000\np\000sq\000~\000\000ppsq\000~\000\006q\000~\000\npsq\000~\000\023q\000~\000\npq\000~\000\026q\000~\000\032q\000~\000\034sq\000~\000\035t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Assertionq\000~\000!sq\000~\000\rq\000~\000\np\000sq\000~\000\023ppq\000~\000\026sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\016L\000\003nc2q\000~\000\016xq\000~\000\031q\000~\000\032sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\016L\000\003nc2q\000~\000\016xq\000~\000\031sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIq\000~\000\036xq\000~\000\031t\000\000sq\000~\000.t\000%urn:oasis:names:tc:SAML:1.0:assertionsq\000~\000.q\000~\000!q\000~\000\034sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\b\001pq\000~\000\013q\000~\000\005q\000~\000\021q\000~\000#q\000~\000\022q\000~\000$q\000~\000\bq\000~\000\fx");
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
/* 141 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AdviceTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 150 */       super(context, "--");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 154 */       this(context);
/* 155 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 159 */       return AdviceTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/* 168 */         switch (this.state) {
/*     */           case 1:
/* 170 */             if ("AssertionIDReference" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 171 */               AdviceTypeImpl.this._getAssertionIDReferenceOrAssertionOrAny().add(spawnChildFromEnterElement((AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl == null) ? (AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl = AdviceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionIDReferenceImpl")) : AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl, 1, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 174 */             if ("Assertion" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 175 */               AdviceTypeImpl.this._getAssertionIDReferenceOrAssertionOrAny().add(spawnChildFromEnterElement((AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl == null) ? (AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl = AdviceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionImpl")) : AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl, 1, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 178 */             if ("" != ___uri && "urn:oasis:names:tc:SAML:1.0:assertion" != ___uri) {
/* 179 */               Object co = spawnWildcard(1, ___uri, ___local, ___qname, __atts);
/* 180 */               if (co != null) {
/* 181 */                 AdviceTypeImpl.this._getAssertionIDReferenceOrAssertionOrAny().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 185 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 0:
/* 188 */             if ("AssertionIDReference" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 189 */               AdviceTypeImpl.this._getAssertionIDReferenceOrAssertionOrAny().add(spawnChildFromEnterElement((AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl == null) ? (AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl = AdviceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionIDReferenceImpl")) : AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionIDReferenceImpl, 1, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 192 */             if ("Assertion" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 193 */               AdviceTypeImpl.this._getAssertionIDReferenceOrAssertionOrAny().add(spawnChildFromEnterElement((AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl == null) ? (AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl = AdviceTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionImpl")) : AdviceTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AssertionImpl, 1, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 196 */             if ("" != ___uri && "urn:oasis:names:tc:SAML:1.0:assertion" != ___uri) {
/* 197 */               Object co = spawnWildcard(1, ___uri, ___local, ___qname, __atts);
/* 198 */               if (co != null) {
/* 199 */                 AdviceTypeImpl.this._getAssertionIDReferenceOrAssertionOrAny().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 203 */             this.state = 1; continue;
/*     */         }  break;
/*     */       } 
/* 206 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/* 217 */         switch (this.state) {
/*     */           case 1:
/* 219 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 222 */             this.state = 1; continue;
/*     */         }  break;
/*     */       } 
/* 225 */       super.leaveElement(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/* 236 */         switch (this.state) {
/*     */           case 1:
/* 238 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 241 */             this.state = 1; continue;
/*     */         }  break;
/*     */       } 
/* 244 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/* 255 */         switch (this.state) {
/*     */           case 1:
/* 257 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 260 */             this.state = 1; continue;
/*     */         }  break;
/*     */       } 
/* 263 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/*     */         while (true) {
/* 275 */           switch (this.state) {
/*     */             case 1:
/* 277 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 0:
/* 280 */               this.state = 1; continue;
/*     */           }  break;
/*     */         } 
/* 283 */       } catch (RuntimeException e) {
/* 284 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AdviceTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */