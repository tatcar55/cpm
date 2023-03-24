/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.KeyValueType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class KeyValueTypeImpl implements KeyValueType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   public static final Class version = JAXBVersion.class; protected ListImpl _Content; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DSAKeyValueImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$RSAKeyValueImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  19 */     return KeyValueType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getContent() {
/*  23 */     if (this._Content == null) {
/*  24 */       this._Content = new ListImpl(new ArrayList());
/*     */     }
/*  26 */     return this._Content;
/*     */   }
/*     */   
/*     */   public List getContent() {
/*  30 */     return (List)_getContent();
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
/*  41 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/*  42 */     while (idx1 != len1) {
/*     */       
/*  44 */       Object o = this._Content.get(idx1);
/*  45 */       if (o instanceof JAXBObject) {
/*  46 */         context.childAsBody((JAXBObject)this._Content.get(idx1++), "Content"); continue;
/*     */       } 
/*  48 */       if (o instanceof String) {
/*     */         try {
/*  50 */           context.text((String)this._Content.get(idx1++), "Content");
/*  51 */         } catch (Exception e) {
/*  52 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/*  55 */       if (o instanceof Object) {
/*  56 */         context.childAsBody((JAXBObject)this._Content.get(idx1++), "Content"); continue;
/*     */       } 
/*  58 */       Util.handleTypeMismatchError(context, this, "Content", o);
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
/*  69 */     int idx1 = 0;
/*  70 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/*  71 */     while (idx1 != len1) {
/*     */       
/*  73 */       Object o = this._Content.get(idx1);
/*  74 */       if (o instanceof JAXBObject) {
/*  75 */         context.childAsAttributes((JAXBObject)this._Content.get(idx1++), "Content"); continue;
/*     */       } 
/*  77 */       if (o instanceof String) {
/*     */         try {
/*  79 */           idx1++;
/*  80 */         } catch (Exception e) {
/*  81 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/*  84 */       if (o instanceof Object) {
/*  85 */         idx1++; continue;
/*     */       } 
/*  87 */       Util.handleTypeMismatchError(context, this, "Content", o);
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
/*  98 */     int idx1 = 0;
/*  99 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/* 100 */     while (idx1 != len1) {
/*     */       
/* 102 */       Object o = this._Content.get(idx1);
/* 103 */       if (o instanceof JAXBObject) {
/* 104 */         context.childAsURIs((JAXBObject)this._Content.get(idx1++), "Content"); continue;
/*     */       } 
/* 106 */       if (o instanceof String) {
/*     */         try {
/* 108 */           idx1++;
/* 109 */         } catch (Exception e) {
/* 110 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 113 */       if (o instanceof Object) {
/* 114 */         idx1++; continue;
/*     */       } 
/* 116 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 125 */     return KeyValueType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 129 */     if (schemaFragment == null) {
/* 130 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\002L\000\004exp2q\000~\000\002xq\000~\000\003ppsq\000~\000\006ppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sq\000~\000\006ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\013xq\000~\000\003q\000~\000\022psr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\021\001q\000~\000\026sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\027q\000~\000\034sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000\036xq\000~\000\031t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.DSAKeyValuet\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\npp\000sq\000~\000\006ppsq\000~\000\017q\000~\000\022psq\000~\000\023q\000~\000\022pq\000~\000\026q\000~\000\032q\000~\000\034sq\000~\000\035t\0007com.sun.xml.wss.saml.internal.saml11.jaxb10.RSAKeyValueq\000~\000!sq\000~\000\npp\000sq\000~\000\023ppq\000~\000\026sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\013L\000\003nc2q\000~\000\013xq\000~\000\031q\000~\000\032sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\013L\000\003nc2q\000~\000\013xq\000~\000\031sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIq\000~\000\036xq\000~\000\031t\000\000sq\000~\000.t\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000.q\000~\000!sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\007\001pq\000~\000\016q\000~\000#q\000~\000\005q\000~\000\020q\000~\000$q\000~\000\bq\000~\000\tx");
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
/* 165 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final KeyValueTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 174 */       super(context, "-");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 178 */       this(context);
/* 179 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 183 */       return KeyValueTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 192 */       switch (this.state) {
/*     */         case 0:
/* 194 */           if ("DSAKeyValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 195 */             KeyValueTypeImpl.this._getContent().add(spawnChildFromEnterElement((KeyValueTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DSAKeyValueImpl == null) ? (KeyValueTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DSAKeyValueImpl = KeyValueTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DSAKeyValueImpl")) : KeyValueTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DSAKeyValueImpl, 0, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 198 */           if ("RSAKeyValue" == ___local && "http://www.w3.org/2000/09/xmldsig#" == ___uri) {
/* 199 */             KeyValueTypeImpl.this._getContent().add(spawnChildFromEnterElement((KeyValueTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$RSAKeyValueImpl == null) ? (KeyValueTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$RSAKeyValueImpl = KeyValueTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.RSAKeyValueImpl")) : KeyValueTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$RSAKeyValueImpl, 0, ___uri, ___local, ___qname, __atts));
/*     */             return;
/*     */           } 
/* 202 */           if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 203 */             Object co = spawnWildcard(0, ___uri, ___local, ___qname, __atts);
/* 204 */             if (co != null) {
/* 205 */               KeyValueTypeImpl.this._getContent().add(co);
/*     */             }
/*     */             return;
/*     */           } 
/* 209 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/* 212 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 223 */       switch (this.state) {
/*     */         case 0:
/* 225 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 228 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 239 */       switch (this.state) {
/*     */         case 0:
/* 241 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
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
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 255 */       switch (this.state) {
/*     */         case 0:
/* 257 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 260 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 272 */         switch (this.state) {
/*     */           case 0:
/* 274 */             this.state = 0;
/* 275 */             eatText1(value);
/*     */             return;
/*     */         } 
/* 278 */       } catch (RuntimeException e) {
/* 279 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 289 */         KeyValueTypeImpl.this._getContent().add(value);
/* 290 */       } catch (Exception e) {
/* 291 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\KeyValueTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */