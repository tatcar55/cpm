/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyInfoType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class KeyInfoTypeImpl implements KeyInfoType, JAXBObject, UnmarshallableObject, XMLSerializable, IdentifiableObject, ValidatableObject {
/*     */   protected ListImpl _Content;
/*  16 */   public static final Class version = JAXBVersion.class; protected String _Id; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyNameImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyValueImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$RetrievalMethodImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$PGPDataImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$MgmtDataImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return KeyInfoType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getContent() {
/*  24 */     if (this._Content == null) {
/*  25 */       this._Content = new ListImpl(new ArrayList());
/*     */     }
/*  27 */     return this._Content;
/*     */   }
/*     */   
/*     */   public List getContent() {
/*  31 */     return (List)_getContent();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  35 */     return this._Id;
/*     */   }
/*     */   
/*     */   public void setId(String value) {
/*  39 */     this._Id = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  43 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  49 */     int idx1 = 0;
/*  50 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/*  51 */     while (idx1 != len1) {
/*     */       
/*  53 */       Object o = this._Content.get(idx1);
/*  54 */       if (o instanceof JAXBObject) {
/*  55 */         context.childAsBody((JAXBObject)this._Content.get(idx1++), "Content"); continue;
/*     */       } 
/*  57 */       if (o instanceof String) {
/*     */         try {
/*  59 */           context.text((String)this._Content.get(idx1++), "Content");
/*  60 */         } catch (Exception e) {
/*  61 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/*  64 */       if (o instanceof Object) {
/*  65 */         context.childAsBody((JAXBObject)this._Content.get(idx1++), "Content"); continue;
/*     */       } 
/*  67 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  78 */     int idx1 = 0;
/*  79 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/*  80 */     if (this._Id != null) {
/*  81 */       context.startAttribute("", "Id");
/*     */       try {
/*  83 */         context.text(context.onID(this, this._Id), "Id");
/*  84 */       } catch (Exception e) {
/*  85 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  87 */       context.endAttribute();
/*     */     } 
/*  89 */     while (idx1 != len1) {
/*     */       
/*  91 */       Object o = this._Content.get(idx1);
/*  92 */       if (o instanceof JAXBObject) {
/*  93 */         context.childAsAttributes((JAXBObject)this._Content.get(idx1++), "Content"); continue;
/*     */       } 
/*  95 */       if (o instanceof String) {
/*     */         try {
/*  97 */           idx1++;
/*  98 */         } catch (Exception e) {
/*  99 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 102 */       if (o instanceof Object) {
/* 103 */         idx1++; continue;
/*     */       } 
/* 105 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 116 */     int idx1 = 0;
/* 117 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/* 118 */     while (idx1 != len1) {
/*     */       
/* 120 */       Object o = this._Content.get(idx1);
/* 121 */       if (o instanceof JAXBObject) {
/* 122 */         context.childAsURIs((JAXBObject)this._Content.get(idx1++), "Content"); continue;
/*     */       } 
/* 124 */       if (o instanceof String) {
/*     */         try {
/* 126 */           idx1++;
/* 127 */         } catch (Exception e) {
/* 128 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 131 */       if (o instanceof Object) {
/* 132 */         idx1++; continue;
/*     */       } 
/* 134 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String ____jaxb____getId() {
/* 143 */     return this._Id;
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 147 */     return KeyInfoType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 151 */     if (schemaFragment == null) {
/* 152 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\007ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsq\000~\000\013ppsq\000~\000\013ppsq\000~\000\013ppsq\000~\000\013ppsq\000~\000\013ppsq\000~\000\013ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\013ppsq\000~\000\tsr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\024xq\000~\000\003q\000~\000\032psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\031\001q\000~\000\036sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\037q\000~\000$sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000&xq\000~\000!t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyNamet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\023pp\000sq\000~\000\013ppsq\000~\000\tq\000~\000\032psq\000~\000\033q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\0004com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyValueq\000~\000)sq\000~\000\023pp\000sq\000~\000\013ppsq\000~\000\tq\000~\000\032psq\000~\000\033q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\000;com.sun.xml.wss.saml.internal.saml11.jaxb10.RetrievalMethodq\000~\000)sq\000~\000\023pp\000sq\000~\000\013ppsq\000~\000\tq\000~\000\032psq\000~\000\033q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\0004com.sun.xml.wss.saml.internal.saml11.jaxb10.X509Dataq\000~\000)sq\000~\000\023pp\000sq\000~\000\013ppsq\000~\000\tq\000~\000\032psq\000~\000\033q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\0003com.sun.xml.wss.saml.internal.saml11.jaxb10.PGPDataq\000~\000)sq\000~\000\023pp\000sq\000~\000\013ppsq\000~\000\tq\000~\000\032psq\000~\000\033q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\0004com.sun.xml.wss.saml.internal.saml11.jaxb10.SPKIDataq\000~\000)sq\000~\000\023pp\000sq\000~\000\013ppsq\000~\000\tq\000~\000\032psq\000~\000\033q\000~\000\032pq\000~\000\036q\000~\000\"q\000~\000$sq\000~\000%t\0004com.sun.xml.wss.saml.internal.saml11.jaxb10.MgmtDataq\000~\000)sq\000~\000\023pp\000sq\000~\000\033ppq\000~\000\036sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\024L\000\003nc2q\000~\000\024xq\000~\000!q\000~\000\"sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\024L\000\003nc2q\000~\000\024xq\000~\000!sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIq\000~\000&xq\000~\000!t\000\000sq\000~\000Tt\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000Tq\000~\000)sq\000~\000\013ppsq\000~\000\033q\000~\000\032psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000&L\000\btypeNameq\000~\000&L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\002IDsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\000sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\032psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000&L\000\fnamespaceURIq\000~\000&xpq\000~\000jq\000~\000isq\000~\000%t\000\002Idq\000~\000Vq\000~\000$sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\031\001pq\000~\000\016q\000~\000\nq\000~\000\027q\000~\000+q\000~\0001q\000~\000\021q\000~\0007q\000~\000=q\000~\000Cq\000~\000\030q\000~\000,q\000~\0002q\000~\0008q\000~\000>q\000~\000Dq\000~\000Jq\000~\000Iq\000~\000\bq\000~\000\017q\000~\000Zq\000~\000\022q\000~\000\005q\000~\000\020q\000~\000\rq\000~\000\fx");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final KeyInfoTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 226 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 230 */       this(context);
/* 231 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 235 */       return KeyInfoTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 244 */         switch (this.state) {
/*     */           case 0:
/* 246 */             attIdx = this.context.getAttribute("", "Id");
/* 247 */             if (attIdx >= 0) {
/* 248 */               String v = this.context.eatAttribute(attIdx);
/* 249 */               this.state = 3;
/* 250 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 253 */             this.state = 3;
/*     */             continue;
/*     */           case 3:
/* 256 */             if ("KeyName" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 257 */               KeyInfoTypeImpl.this._getContent().add(spawnChildFromEnterElement((KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyNameImpl == null) ? (KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyNameImpl = KeyInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyNameImpl")) : KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyNameImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 260 */             if ("KeyValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 261 */               KeyInfoTypeImpl.this._getContent().add(spawnChildFromEnterElement((KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyValueImpl == null) ? (KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyValueImpl = KeyInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyValueImpl")) : KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$KeyValueImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 264 */             if ("RetrievalMethod" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 265 */               KeyInfoTypeImpl.this._getContent().add(spawnChildFromEnterElement((KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$RetrievalMethodImpl == null) ? (KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$RetrievalMethodImpl = KeyInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.RetrievalMethodImpl")) : KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$RetrievalMethodImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 268 */             if ("X509Data" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 269 */               KeyInfoTypeImpl.this._getContent().add(spawnChildFromEnterElement((KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataImpl == null) ? (KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataImpl = KeyInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataImpl")) : KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$X509DataImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 272 */             if ("PGPData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 273 */               KeyInfoTypeImpl.this._getContent().add(spawnChildFromEnterElement((KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$PGPDataImpl == null) ? (KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$PGPDataImpl = KeyInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.PGPDataImpl")) : KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$PGPDataImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 276 */             if ("SPKIData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 277 */               KeyInfoTypeImpl.this._getContent().add(spawnChildFromEnterElement((KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataImpl == null) ? (KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataImpl = KeyInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SPKIDataImpl")) : KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$SPKIDataImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 280 */             if ("MgmtData" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 281 */               KeyInfoTypeImpl.this._getContent().add(spawnChildFromEnterElement((KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$MgmtDataImpl == null) ? (KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$MgmtDataImpl = KeyInfoTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.MgmtDataImpl")) : KeyInfoTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$MgmtDataImpl, 3, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 284 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 285 */               Object co = spawnWildcard(3, ___uri, ___local, ___qname, __atts);
/* 286 */               if (co != null) {
/* 287 */                 KeyInfoTypeImpl.this._getContent().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 291 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*     */         }  break;
/*     */       } 
/* 294 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 303 */         KeyInfoTypeImpl.this._Id = this.context.addToIdTable(WhiteSpaceProcessor.collapse(value));
/* 304 */       } catch (Exception e) {
/* 305 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 315 */         switch (this.state) {
/*     */           case 0:
/* 317 */             attIdx = this.context.getAttribute("", "Id");
/* 318 */             if (attIdx >= 0) {
/* 319 */               String v = this.context.eatAttribute(attIdx);
/* 320 */               this.state = 3;
/* 321 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 324 */             this.state = 3;
/*     */             continue;
/*     */           case 3:
/* 327 */             revertToParentFromLeaveElement(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 330 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 341 */         switch (this.state) {
/*     */           case 0:
/* 343 */             if ("Id" == ___local && "" == ___uri) {
/* 344 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 347 */             this.state = 3;
/*     */             continue;
/*     */           case 3:
/* 350 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 353 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 364 */         switch (this.state) {
/*     */           case 2:
/* 366 */             if ("Id" == ___local && "" == ___uri) {
/* 367 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 372 */             attIdx = this.context.getAttribute("", "Id");
/* 373 */             if (attIdx >= 0) {
/* 374 */               String v = this.context.eatAttribute(attIdx);
/* 375 */               this.state = 3;
/* 376 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 379 */             this.state = 3;
/*     */             continue;
/*     */           case 3:
/* 382 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 385 */       super.leaveAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleText(String value) throws SAXException {
/*     */       try {
/*     */         while (true) {
/*     */           int attIdx;
/* 397 */           switch (this.state) {
/*     */             case 0:
/* 399 */               attIdx = this.context.getAttribute("", "Id");
/* 400 */               if (attIdx >= 0) {
/* 401 */                 String v = this.context.eatAttribute(attIdx);
/* 402 */                 this.state = 3;
/* 403 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 406 */               this.state = 3;
/*     */               continue;
/*     */             case 1:
/* 409 */               this.state = 2;
/* 410 */               eatText1(value);
/*     */               return;
/*     */             case 3:
/* 413 */               this.state = 3;
/* 414 */               eatText2(value); return;
/*     */           }  break;
/*     */         } 
/* 417 */       } catch (RuntimeException e) {
/* 418 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 428 */         KeyInfoTypeImpl.this._getContent().add(value);
/* 429 */       } catch (Exception e) {
/* 430 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\KeyInfoTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */