/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.NameIdentifierType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class NameIdentifierTypeImpl implements NameIdentifierType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _Value;
/*  17 */   public static final Class version = JAXBVersion.class; protected String _NameQualifier; protected String _Format;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return NameIdentifierType.class;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  25 */     return this._Value;
/*     */   }
/*     */   
/*     */   public void setValue(String value) {
/*  29 */     this._Value = value;
/*     */   }
/*     */   
/*     */   public String getNameQualifier() {
/*  33 */     return this._NameQualifier;
/*     */   }
/*     */   
/*     */   public void setNameQualifier(String value) {
/*  37 */     this._NameQualifier = value;
/*     */   }
/*     */   
/*     */   public String getFormat() {
/*  41 */     return this._Format;
/*     */   }
/*     */   
/*     */   public void setFormat(String value) {
/*  45 */     this._Format = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  49 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*     */     try {
/*  56 */       context.text(this._Value, "Value");
/*  57 */     } catch (Exception e) {
/*  58 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  65 */     if (this._Format != null) {
/*  66 */       context.startAttribute("", "Format");
/*     */       try {
/*  68 */         context.text(this._Format, "Format");
/*  69 */       } catch (Exception e) {
/*  70 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  72 */       context.endAttribute();
/*     */     } 
/*  74 */     if (this._NameQualifier != null) {
/*  75 */       context.startAttribute("", "NameQualifier");
/*     */       try {
/*  77 */         context.text(this._NameQualifier, "NameQualifier");
/*  78 */       } catch (Exception e) {
/*  79 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  81 */       context.endAttribute();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  91 */     return NameIdentifierType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  95 */     if (schemaFragment == null) {
/*  96 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\021L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\fpsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\021L\000\fnamespaceURIq\000~\000\021xpq\000~\000\025q\000~\000\024sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xq\000~\000\003q\000~\000\fpsq\000~\000\007ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\016q\000~\000\024t\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\027q\000~\000\032sq\000~\000\033q\000~\000%q\000~\000\024sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\021L\000\fnamespaceURIq\000~\000\021xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\006Formatt\000\000sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\013\001q\000~\000/sq\000~\000\035ppsq\000~\000\037q\000~\000\fpq\000~\000\nsq\000~\000)t\000\rNameQualifierq\000~\000-q\000~\000/sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\004\001pq\000~\000\005q\000~\0001q\000~\000\036q\000~\000\006x");
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
/* 132 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final NameIdentifierTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 141 */       super(context, "--------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 145 */       this(context);
/* 146 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 150 */       return NameIdentifierTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 159 */         switch (this.state) {
/*     */           case 0:
/* 161 */             attIdx = this.context.getAttribute("", "Format");
/* 162 */             if (attIdx >= 0) {
/* 163 */               String v = this.context.eatAttribute(attIdx);
/* 164 */               this.state = 3;
/* 165 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 168 */             this.state = 3;
/*     */             continue;
/*     */           case 3:
/* 171 */             attIdx = this.context.getAttribute("", "NameQualifier");
/* 172 */             if (attIdx >= 0) {
/* 173 */               String v = this.context.eatAttribute(attIdx);
/* 174 */               this.state = 6;
/* 175 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 178 */             this.state = 6;
/*     */             continue;
/*     */           case 7:
/* 181 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*     */         }  break;
/*     */       } 
/* 184 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 193 */         NameIdentifierTypeImpl.this._Format = WhiteSpaceProcessor.collapse(value);
/* 194 */       } catch (Exception e) {
/* 195 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 203 */         NameIdentifierTypeImpl.this._NameQualifier = value;
/* 204 */       } catch (Exception e) {
/* 205 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 215 */         switch (this.state) {
/*     */           case 0:
/* 217 */             attIdx = this.context.getAttribute("", "Format");
/* 218 */             if (attIdx >= 0) {
/* 219 */               String v = this.context.eatAttribute(attIdx);
/* 220 */               this.state = 3;
/* 221 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 224 */             this.state = 3;
/*     */             continue;
/*     */           case 3:
/* 227 */             attIdx = this.context.getAttribute("", "NameQualifier");
/* 228 */             if (attIdx >= 0) {
/* 229 */               String v = this.context.eatAttribute(attIdx);
/* 230 */               this.state = 6;
/* 231 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 234 */             this.state = 6;
/*     */             continue;
/*     */           case 7:
/* 237 */             revertToParentFromLeaveElement(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 240 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 251 */         switch (this.state) {
/*     */           case 0:
/* 253 */             if ("Format" == ___local && "" == ___uri) {
/* 254 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 257 */             this.state = 3;
/*     */             continue;
/*     */           case 3:
/* 260 */             if ("NameQualifier" == ___local && "" == ___uri) {
/* 261 */               this.state = 4;
/*     */               return;
/*     */             } 
/* 264 */             this.state = 6;
/*     */             continue;
/*     */           case 7:
/* 267 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 270 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 281 */         switch (this.state) {
/*     */           case 5:
/* 283 */             if ("NameQualifier" == ___local && "" == ___uri) {
/* 284 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 289 */             attIdx = this.context.getAttribute("", "Format");
/* 290 */             if (attIdx >= 0) {
/* 291 */               String v = this.context.eatAttribute(attIdx);
/* 292 */               this.state = 3;
/* 293 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 296 */             this.state = 3;
/*     */             continue;
/*     */           case 2:
/* 299 */             if ("Format" == ___local && "" == ___uri) {
/* 300 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 305 */             attIdx = this.context.getAttribute("", "NameQualifier");
/* 306 */             if (attIdx >= 0) {
/* 307 */               String v = this.context.eatAttribute(attIdx);
/* 308 */               this.state = 6;
/* 309 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 312 */             this.state = 6;
/*     */             continue;
/*     */           case 7:
/* 315 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 318 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 330 */           switch (this.state) {
/*     */             case 0:
/* 332 */               attIdx = this.context.getAttribute("", "Format");
/* 333 */               if (attIdx >= 0) {
/* 334 */                 String v = this.context.eatAttribute(attIdx);
/* 335 */                 this.state = 3;
/* 336 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 339 */               this.state = 3;
/*     */               continue;
/*     */             case 1:
/* 342 */               this.state = 2;
/* 343 */               eatText1(value);
/*     */               return;
/*     */             case 3:
/* 346 */               attIdx = this.context.getAttribute("", "NameQualifier");
/* 347 */               if (attIdx >= 0) {
/* 348 */                 String v = this.context.eatAttribute(attIdx);
/* 349 */                 this.state = 6;
/* 350 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/* 353 */               this.state = 6;
/*     */               continue;
/*     */             case 7:
/* 356 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 6:
/* 359 */               this.state = 7;
/* 360 */               eatText3(value);
/*     */               return;
/*     */             case 4:
/* 363 */               this.state = 5;
/* 364 */               eatText2(value); return;
/*     */           }  break;
/*     */         } 
/* 367 */       } catch (RuntimeException e) {
/* 368 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText3(String value) throws SAXException {
/*     */       try {
/* 378 */         NameIdentifierTypeImpl.this._Value = value;
/* 379 */       } catch (Exception e) {
/* 380 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\NameIdentifierTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */