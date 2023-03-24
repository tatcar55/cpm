/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.DigestMethodType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class DigestMethodTypeImpl implements DigestMethodType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _Algorithm;
/*  16 */   public static final Class version = JAXBVersion.class; protected ListImpl _Content;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return DigestMethodType.class;
/*     */   }
/*     */   
/*     */   public String getAlgorithm() {
/*  24 */     return this._Algorithm;
/*     */   }
/*     */   
/*     */   public void setAlgorithm(String value) {
/*  28 */     this._Algorithm = value;
/*     */   }
/*     */   
/*     */   protected ListImpl _getContent() {
/*  32 */     if (this._Content == null) {
/*  33 */       this._Content = new ListImpl(new ArrayList());
/*     */     }
/*  35 */     return this._Content;
/*     */   }
/*     */   
/*     */   public List getContent() {
/*  39 */     return (List)_getContent();
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  43 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  49 */     int idx2 = 0;
/*  50 */     int len2 = (this._Content == null) ? 0 : this._Content.size();
/*  51 */     while (idx2 != len2) {
/*     */       
/*  53 */       Object o = this._Content.get(idx2);
/*  54 */       if (o instanceof String) {
/*     */         try {
/*  56 */           context.text((String)this._Content.get(idx2++), "Content");
/*  57 */         } catch (Exception e) {
/*  58 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/*  61 */       if (o instanceof Object) {
/*  62 */         context.childAsBody((JAXBObject)this._Content.get(idx2++), "Content"); continue;
/*     */       } 
/*  64 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  74 */     int idx2 = 0;
/*  75 */     int len2 = (this._Content == null) ? 0 : this._Content.size();
/*  76 */     context.startAttribute("", "Algorithm");
/*     */     try {
/*  78 */       context.text(this._Algorithm, "Algorithm");
/*  79 */     } catch (Exception e) {
/*  80 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  82 */     context.endAttribute();
/*  83 */     while (idx2 != len2) {
/*     */       
/*  85 */       Object o = this._Content.get(idx2);
/*  86 */       if (o instanceof String) {
/*     */         try {
/*  88 */           idx2++;
/*  89 */         } catch (Exception e) {
/*  90 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/*  93 */       if (o instanceof Object) {
/*  94 */         idx2++; continue;
/*     */       } 
/*  96 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 106 */     int idx2 = 0;
/* 107 */     int len2 = (this._Content == null) ? 0 : this._Content.size();
/* 108 */     while (idx2 != len2) {
/*     */       
/* 110 */       Object o = this._Content.get(idx2);
/* 111 */       if (o instanceof String) {
/*     */         try {
/* 113 */           idx2++;
/* 114 */         } catch (Exception e) {
/* 115 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 118 */       if (o instanceof Object) {
/* 119 */         idx2++; continue;
/*     */       } 
/* 121 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 129 */     return DigestMethodType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 133 */     if (schemaFragment == null) {
/* 134 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\007sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\016p\000sr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\020xq\000~\000\003ppsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\r\001q\000~\000\026sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\020L\000\003nc2q\000~\000\020xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\031sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\020L\000\003nc2q\000~\000\020xq\000~\000\031sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIt\000\022Ljava/lang/String;xq\000~\000\031t\000\000sq\000~\000\037t\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\037t\000+http://java.sun.com/jaxb/xjc/dummy-elementssr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\027q\000~\000(sq\000~\000\023ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000 L\000\btypeNameq\000~\000 L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\016psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xpq\000~\0005q\000~\0004sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000 L\000\fnamespaceURIq\000~\000 xq\000~\000\031t\000\tAlgorithmq\000~\000\"sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\004\001pq\000~\000\nq\000~\000\fq\000~\000\bq\000~\000\005x");
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
/* 180 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final DigestMethodTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 189 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 193 */       this(context);
/* 194 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 198 */       return DigestMethodTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 207 */         switch (this.state) {
/*     */           case 3:
/* 209 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 210 */               Object co = spawnWildcard(3, ___uri, ___local, ___qname, __atts);
/* 211 */               if (co != null) {
/* 212 */                 DigestMethodTypeImpl.this._getContent().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 216 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 0:
/* 219 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 220 */             if (attIdx >= 0) {
/* 221 */               String v = this.context.eatAttribute(attIdx);
/* 222 */               this.state = 3;
/* 223 */               eatText1(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 228 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 237 */         DigestMethodTypeImpl.this._Algorithm = WhiteSpaceProcessor.collapse(value);
/* 238 */       } catch (Exception e) {
/* 239 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 249 */         switch (this.state) {
/*     */           case 3:
/* 251 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 254 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 255 */             if (attIdx >= 0) {
/* 256 */               String v = this.context.eatAttribute(attIdx);
/* 257 */               this.state = 3;
/* 258 */               eatText1(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 263 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 274 */       switch (this.state) {
/*     */         case 3:
/* 276 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 0:
/* 279 */           if ("Algorithm" == ___local && "" == ___uri) {
/* 280 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 285 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true)
/*     */       { int attIdx;
/* 296 */         switch (this.state) {
/*     */           case 3:
/* 298 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 301 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 302 */             if (attIdx >= 0) {
/* 303 */               String v = this.context.eatAttribute(attIdx);
/* 304 */               this.state = 3;
/* 305 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 310 */             if ("Algorithm" == ___local && "" == ___uri) {
/* 311 */               this.state = 3; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 316 */         super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
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
/* 328 */           switch (this.state) {
/*     */             case 3:
/* 330 */               this.state = 3;
/* 331 */               eatText2(value);
/*     */               return;
/*     */             case 1:
/* 334 */               this.state = 2;
/* 335 */               eatText1(value);
/*     */               return;
/*     */             case 0:
/* 338 */               attIdx = this.context.getAttribute("", "Algorithm");
/* 339 */               if (attIdx >= 0) {
/* 340 */                 String v = this.context.eatAttribute(attIdx);
/* 341 */                 this.state = 3;
/* 342 */                 eatText1(v); continue;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         } 
/* 347 */       } catch (RuntimeException e) {
/* 348 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 358 */         DigestMethodTypeImpl.this._getContent().add(value);
/* 359 */       } catch (Exception e) {
/* 360 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\DigestMethodTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */