/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignaturePropertyType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SignaturePropertyTypeImpl implements SignaturePropertyType, JAXBObject, UnmarshallableObject, XMLSerializable, IdentifiableObject, ValidatableObject {
/*     */   protected ListImpl _Content;
/*  17 */   public static final Class version = JAXBVersion.class; protected String _Target; protected String _Id;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return SignaturePropertyType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getContent() {
/*  25 */     if (this._Content == null) {
/*  26 */       this._Content = new ListImpl(new ArrayList());
/*     */     }
/*  28 */     return this._Content;
/*     */   }
/*     */   
/*     */   public List getContent() {
/*  32 */     return (List)_getContent();
/*     */   }
/*     */   
/*     */   public String getTarget() {
/*  36 */     return this._Target;
/*     */   }
/*     */   
/*     */   public void setTarget(String value) {
/*  40 */     this._Target = value;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  44 */     return this._Id;
/*     */   }
/*     */   
/*     */   public void setId(String value) {
/*  48 */     this._Id = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  52 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  58 */     int idx1 = 0;
/*  59 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/*  60 */     while (idx1 != len1) {
/*     */       
/*  62 */       Object o = this._Content.get(idx1);
/*  63 */       if (o instanceof String) {
/*     */         try {
/*  65 */           context.text((String)this._Content.get(idx1++), "Content");
/*  66 */         } catch (Exception e) {
/*  67 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/*  70 */       if (o instanceof Object) {
/*  71 */         context.childAsBody((JAXBObject)this._Content.get(idx1++), "Content"); continue;
/*     */       } 
/*  73 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  83 */     int idx1 = 0;
/*  84 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/*  85 */     if (this._Id != null) {
/*  86 */       context.startAttribute("", "Id");
/*     */       try {
/*  88 */         context.text(context.onID(this, this._Id), "Id");
/*  89 */       } catch (Exception e) {
/*  90 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  92 */       context.endAttribute();
/*     */     } 
/*  94 */     context.startAttribute("", "Target");
/*     */     try {
/*  96 */       context.text(this._Target, "Target");
/*  97 */     } catch (Exception e) {
/*  98 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/* 100 */     context.endAttribute();
/* 101 */     while (idx1 != len1) {
/*     */       
/* 103 */       Object o = this._Content.get(idx1);
/* 104 */       if (o instanceof String) {
/*     */         try {
/* 106 */           idx1++;
/* 107 */         } catch (Exception e) {
/* 108 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 111 */       if (o instanceof Object) {
/* 112 */         idx1++; continue;
/*     */       } 
/* 114 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/* 124 */     int idx1 = 0;
/* 125 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/* 126 */     while (idx1 != len1) {
/*     */       
/* 128 */       Object o = this._Content.get(idx1);
/* 129 */       if (o instanceof String) {
/*     */         try {
/* 131 */           idx1++;
/* 132 */         } catch (Exception e) {
/* 133 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 136 */       if (o instanceof Object) {
/* 137 */         idx1++; continue;
/*     */       } 
/* 139 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String ____jaxb____getId() {
/* 147 */     return this._Id;
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 151 */     return SignaturePropertyType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 155 */     if (schemaFragment == null) {
/* 156 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\rxq\000~\000\003ppsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\001q\000~\000\023sr\000'com.sun.msv.grammar.DifferenceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\rL\000\003nc2q\000~\000\rxr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\027sr\000#com.sun.msv.grammar.ChoiceNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\003nc1q\000~\000\rL\000\003nc2q\000~\000\rxq\000~\000\027sr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIt\000\022Ljava/lang/String;xq\000~\000\027t\000\000sq\000~\000\035t\000\"http://www.w3.org/2000/09/xmldsig#sq\000~\000\035t\000+http://java.sun.com/jaxb/xjc/dummy-elementssr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsq\000~\000\020sq\000~\000\024\000psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\036L\000\btypeNameq\000~\000\036L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\002IDsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\000sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000(psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\036L\000\fnamespaceURIq\000~\000\036xpq\000~\0007q\000~\0006sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\036L\000\fnamespaceURIq\000~\000\036xq\000~\000\027t\000\002Idq\000~\000 sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\025q\000~\000Csq\000~\000\020ppsq\000~\000)ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\0001q\000~\0006t\000\006anyURIq\000~\000:q\000~\000<sq\000~\000=q\000~\000Hq\000~\0006sq\000~\000?t\000\006Targetq\000~\000 sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\005\001pq\000~\000\013q\000~\000\006q\000~\000\tq\000~\000\005q\000~\000&x");
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
/* 207 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SignaturePropertyTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 216 */       super(context, "-------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 220 */       this(context);
/* 221 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 225 */       return SignaturePropertyTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 234 */         switch (this.state) {
/*     */           case 6:
/* 236 */             if ("" != ___uri && "http://www.w3.org/2000/09/xmldsig#" != ___uri) {
/* 237 */               Object co = spawnWildcard(6, ___uri, ___local, ___qname, __atts);
/* 238 */               if (co != null) {
/* 239 */                 SignaturePropertyTypeImpl.this._getContent().add(co);
/*     */               }
/*     */               return;
/*     */             } 
/* 243 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 3:
/* 246 */             attIdx = this.context.getAttribute("", "Target");
/* 247 */             if (attIdx >= 0) {
/* 248 */               String v = this.context.eatAttribute(attIdx);
/* 249 */               this.state = 6;
/* 250 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 255 */             attIdx = this.context.getAttribute("", "Id");
/* 256 */             if (attIdx >= 0) {
/* 257 */               String v = this.context.eatAttribute(attIdx);
/* 258 */               this.state = 3;
/* 259 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 262 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 265 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 274 */         SignaturePropertyTypeImpl.this._Target = WhiteSpaceProcessor.collapse(value);
/* 275 */       } catch (Exception e) {
/* 276 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 284 */         SignaturePropertyTypeImpl.this._Id = this.context.addToIdTable(WhiteSpaceProcessor.collapse(value));
/* 285 */       } catch (Exception e) {
/* 286 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 296 */         switch (this.state) {
/*     */           case 6:
/* 298 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 301 */             attIdx = this.context.getAttribute("", "Target");
/* 302 */             if (attIdx >= 0) {
/* 303 */               String v = this.context.eatAttribute(attIdx);
/* 304 */               this.state = 6;
/* 305 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 310 */             attIdx = this.context.getAttribute("", "Id");
/* 311 */             if (attIdx >= 0) {
/* 312 */               String v = this.context.eatAttribute(attIdx);
/* 313 */               this.state = 3;
/* 314 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 317 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 320 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 331 */         switch (this.state) {
/*     */           case 6:
/* 333 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 3:
/* 336 */             if ("Target" == ___local && "" == ___uri) {
/* 337 */               this.state = 4;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 342 */             if ("Id" == ___local && "" == ___uri) {
/* 343 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 346 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 349 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 360 */         switch (this.state) {
/*     */           case 6:
/* 362 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 2:
/* 365 */             if ("Id" == ___local && "" == ___uri) {
/* 366 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 371 */             if ("Target" == ___local && "" == ___uri) {
/* 372 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 377 */             attIdx = this.context.getAttribute("", "Target");
/* 378 */             if (attIdx >= 0) {
/* 379 */               String v = this.context.eatAttribute(attIdx);
/* 380 */               this.state = 6;
/* 381 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 386 */             attIdx = this.context.getAttribute("", "Id");
/* 387 */             if (attIdx >= 0) {
/* 388 */               String v = this.context.eatAttribute(attIdx);
/* 389 */               this.state = 3;
/* 390 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 393 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 396 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 408 */           switch (this.state) {
/*     */             case 6:
/* 410 */               this.state = 6;
/* 411 */               eatText3(value);
/*     */               return;
/*     */             case 4:
/* 414 */               this.state = 5;
/* 415 */               eatText1(value);
/*     */               return;
/*     */             case 3:
/* 418 */               attIdx = this.context.getAttribute("", "Target");
/* 419 */               if (attIdx >= 0) {
/* 420 */                 String v = this.context.eatAttribute(attIdx);
/* 421 */                 this.state = 6;
/* 422 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 0:
/* 427 */               attIdx = this.context.getAttribute("", "Id");
/* 428 */               if (attIdx >= 0) {
/* 429 */                 String v = this.context.eatAttribute(attIdx);
/* 430 */                 this.state = 3;
/* 431 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/* 434 */               this.state = 3;
/*     */               continue;
/*     */             case 1:
/* 437 */               this.state = 2;
/* 438 */               eatText2(value); return;
/*     */           }  break;
/*     */         } 
/* 441 */       } catch (RuntimeException e) {
/* 442 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText3(String value) throws SAXException {
/*     */       try {
/* 452 */         SignaturePropertyTypeImpl.this._getContent().add(value);
/* 453 */       } catch (Exception e) {
/* 454 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SignaturePropertyTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */