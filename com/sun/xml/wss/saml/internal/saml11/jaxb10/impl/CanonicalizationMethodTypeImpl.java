/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.CanonicalizationMethodType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class CanonicalizationMethodTypeImpl implements CanonicalizationMethodType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _Algorithm;
/*  16 */   public static final Class version = JAXBVersion.class; protected ListImpl _Content;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return CanonicalizationMethodType.class;
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
/* 129 */     return CanonicalizationMethodType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 133 */     if (schemaFragment == null) {
/* 134 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\007sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\016p\000sr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\020xq\000~\000\003ppsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\r\001q\000~\000\026sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIt\000\022Ljava/lang/String;xq\000~\000\031t\000+http://java.sun.com/jaxb/xjc/dummy-elementssr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\027q\000~\000 sq\000~\000\023ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000\034L\000\btypeNameq\000~\000\034L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\016psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\034L\000\fnamespaceURIq\000~\000\034xpq\000~\000-q\000~\000,sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\034L\000\fnamespaceURIq\000~\000\034xq\000~\000\031t\000\tAlgorithmt\000\000sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\004\001pq\000~\000\005q\000~\000\fq\000~\000\nq\000~\000\bx");
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
/* 176 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final CanonicalizationMethodTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 185 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 189 */       this(context);
/* 190 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 194 */       return CanonicalizationMethodTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/*     */         Object co;
/* 203 */         switch (this.state) {
/*     */           
/*     */           case 3:
/* 206 */             co = spawnWildcard(3, ___uri, ___local, ___qname, __atts);
/* 207 */             if (co != null) {
/* 208 */               CanonicalizationMethodTypeImpl.this._getContent().add(co);
/*     */             }
/*     */             return;
/*     */ 
/*     */ 
/*     */           
/*     */           case 0:
/* 215 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 216 */             if (attIdx >= 0) {
/* 217 */               String v = this.context.eatAttribute(attIdx);
/* 218 */               this.state = 3;
/* 219 */               eatText1(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 224 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 233 */         CanonicalizationMethodTypeImpl.this._Algorithm = WhiteSpaceProcessor.collapse(value);
/* 234 */       } catch (Exception e) {
/* 235 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 245 */         switch (this.state) {
/*     */           case 3:
/* 247 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 250 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 251 */             if (attIdx >= 0) {
/* 252 */               String v = this.context.eatAttribute(attIdx);
/* 253 */               this.state = 3;
/* 254 */               eatText1(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 259 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 270 */       switch (this.state) {
/*     */         case 3:
/* 272 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 0:
/* 275 */           if ("Algorithm" == ___local && "" == ___uri) {
/* 276 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 281 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 292 */         switch (this.state) {
/*     */           case 3:
/* 294 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 2:
/* 297 */             if ("Algorithm" == ___local && "" == ___uri) {
/* 298 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 303 */             attIdx = this.context.getAttribute("", "Algorithm");
/* 304 */             if (attIdx >= 0) {
/* 305 */               String v = this.context.eatAttribute(attIdx);
/* 306 */               this.state = 3;
/* 307 */               eatText1(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 312 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 324 */           switch (this.state) {
/*     */             case 1:
/* 326 */               this.state = 2;
/* 327 */               eatText1(value);
/*     */               return;
/*     */             case 3:
/* 330 */               this.state = 3;
/* 331 */               eatText2(value);
/*     */               return;
/*     */             case 0:
/* 334 */               attIdx = this.context.getAttribute("", "Algorithm");
/* 335 */               if (attIdx >= 0) {
/* 336 */                 String v = this.context.eatAttribute(attIdx);
/* 337 */                 this.state = 3;
/* 338 */                 eatText1(v); continue;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         } 
/* 343 */       } catch (RuntimeException e) {
/* 344 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 354 */         CanonicalizationMethodTypeImpl.this._getContent().add(value);
/* 355 */       } catch (Exception e) {
/* 356 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\CanonicalizationMethodTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */