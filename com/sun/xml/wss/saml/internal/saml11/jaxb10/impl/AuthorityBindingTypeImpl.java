/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorityBindingType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AuthorityBindingTypeImpl implements AuthorityBindingType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _Binding;
/*  17 */   public static final Class version = JAXBVersion.class; protected QName _AuthorityKind; protected String _Location;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return AuthorityBindingType.class;
/*     */   }
/*     */   
/*     */   public String getBinding() {
/*  25 */     return this._Binding;
/*     */   }
/*     */   
/*     */   public void setBinding(String value) {
/*  29 */     this._Binding = value;
/*     */   }
/*     */   
/*     */   public QName getAuthorityKind() {
/*  33 */     return this._AuthorityKind;
/*     */   }
/*     */   
/*     */   public void setAuthorityKind(QName value) {
/*  37 */     this._AuthorityKind = value;
/*     */   }
/*     */   
/*     */   public String getLocation() {
/*  41 */     return this._Location;
/*     */   }
/*     */   
/*     */   public void setLocation(String value) {
/*  45 */     this._Location = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  49 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  60 */     context.startAttribute("", "AuthorityKind");
/*     */     try {
/*  62 */       context.text(DatatypeConverter.printQName(this._AuthorityKind, (NamespaceContext)context.getNamespaceContext()), "AuthorityKind");
/*  63 */     } catch (Exception e) {
/*  64 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  66 */     context.endAttribute();
/*  67 */     context.startAttribute("", "Binding");
/*     */     try {
/*  69 */       context.text(this._Binding, "Binding");
/*  70 */     } catch (Exception e) {
/*  71 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  73 */     context.endAttribute();
/*  74 */     context.startAttribute("", "Location");
/*     */     try {
/*  76 */       context.text(this._Location, "Location");
/*  77 */     } catch (Exception e) {
/*  78 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  80 */     context.endAttribute();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*     */     try {
/*  87 */       context.getNamespaceContext().declareNamespace(this._AuthorityKind.getNamespaceURI(), this._AuthorityKind.getPrefix(), false);
/*  88 */     } catch (Exception e) {
/*  89 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  94 */     return AuthorityBindingType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  98 */     if (schemaFragment == null) {
/*  99 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xq\000~\000\003ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\022L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\005QNamesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\022L\000\fnamespaceURIq\000~\000\022xpq\000~\000\026q\000~\000\025sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\022L\000\fnamespaceURIq\000~\000\022xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\rAuthorityKindt\000\000sq\000~\000\007ppsq\000~\000\nppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\017q\000~\000\025t\000\006anyURIq\000~\000\031q\000~\000\033sq\000~\000\036q\000~\000)q\000~\000\025sq\000~\000 t\000\007Bindingq\000~\000$sq\000~\000\007ppq\000~\000&sq\000~\000 t\000\bLocationq\000~\000$sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\006q\000~\000\005x");
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
/* 132 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AuthorityBindingTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 141 */       super(context, "----------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 145 */       this(context);
/* 146 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 150 */       return AuthorityBindingTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 159 */         switch (this.state) {
/*     */           case 3:
/* 161 */             attIdx = this.context.getAttribute("", "Binding");
/* 162 */             if (attIdx >= 0) {
/* 163 */               String v = this.context.eatAttribute(attIdx);
/* 164 */               this.state = 6;
/* 165 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 9:
/* 170 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 6:
/* 173 */             attIdx = this.context.getAttribute("", "Location");
/* 174 */             if (attIdx >= 0) {
/* 175 */               String v = this.context.eatAttribute(attIdx);
/* 176 */               this.state = 9;
/* 177 */               eatText2(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 182 */             attIdx = this.context.getAttribute("", "AuthorityKind");
/* 183 */             if (attIdx >= 0) {
/* 184 */               String v = this.context.eatAttribute(attIdx);
/* 185 */               this.state = 3;
/* 186 */               eatText3(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 191 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 200 */         AuthorityBindingTypeImpl.this._Binding = WhiteSpaceProcessor.collapse(value);
/* 201 */       } catch (Exception e) {
/* 202 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 210 */         AuthorityBindingTypeImpl.this._Location = WhiteSpaceProcessor.collapse(value);
/* 211 */       } catch (Exception e) {
/* 212 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText3(String value) throws SAXException {
/*     */       try {
/* 220 */         AuthorityBindingTypeImpl.this._AuthorityKind = DatatypeConverter.parseQName(WhiteSpaceProcessor.collapse(WhiteSpaceProcessor.collapse(value)), (NamespaceContext)this.context);
/* 221 */       } catch (Exception e) {
/* 222 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 232 */         switch (this.state) {
/*     */           case 3:
/* 234 */             attIdx = this.context.getAttribute("", "Binding");
/* 235 */             if (attIdx >= 0) {
/* 236 */               String v = this.context.eatAttribute(attIdx);
/* 237 */               this.state = 6;
/* 238 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 9:
/* 243 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 6:
/* 246 */             attIdx = this.context.getAttribute("", "Location");
/* 247 */             if (attIdx >= 0) {
/* 248 */               String v = this.context.eatAttribute(attIdx);
/* 249 */               this.state = 9;
/* 250 */               eatText2(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 255 */             attIdx = this.context.getAttribute("", "AuthorityKind");
/* 256 */             if (attIdx >= 0) {
/* 257 */               String v = this.context.eatAttribute(attIdx);
/* 258 */               this.state = 3;
/* 259 */               eatText3(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 264 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 275 */       switch (this.state) {
/*     */         case 3:
/* 277 */           if ("Binding" == ___local && "" == ___uri) {
/* 278 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 9:
/* 283 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 6:
/* 286 */           if ("Location" == ___local && "" == ___uri) {
/* 287 */             this.state = 7;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 292 */           if ("AuthorityKind" == ___local && "" == ___uri) {
/* 293 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 298 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 309 */         switch (this.state) {
/*     */           case 3:
/* 311 */             attIdx = this.context.getAttribute("", "Binding");
/* 312 */             if (attIdx >= 0) {
/* 313 */               String v = this.context.eatAttribute(attIdx);
/* 314 */               this.state = 6;
/* 315 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 320 */             if ("Binding" == ___local && "" == ___uri) {
/* 321 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 8:
/* 326 */             if ("Location" == ___local && "" == ___uri) {
/* 327 */               this.state = 9;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 9:
/* 332 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 6:
/* 335 */             attIdx = this.context.getAttribute("", "Location");
/* 336 */             if (attIdx >= 0) {
/* 337 */               String v = this.context.eatAttribute(attIdx);
/* 338 */               this.state = 9;
/* 339 */               eatText2(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 344 */             if ("AuthorityKind" == ___local && "" == ___uri) {
/* 345 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 350 */             attIdx = this.context.getAttribute("", "AuthorityKind");
/* 351 */             if (attIdx >= 0) {
/* 352 */               String v = this.context.eatAttribute(attIdx);
/* 353 */               this.state = 3;
/* 354 */               eatText3(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 359 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 371 */           switch (this.state) {
/*     */             case 4:
/* 373 */               this.state = 5;
/* 374 */               eatText1(value);
/*     */               return;
/*     */             case 3:
/* 377 */               attIdx = this.context.getAttribute("", "Binding");
/* 378 */               if (attIdx >= 0) {
/* 379 */                 String v = this.context.eatAttribute(attIdx);
/* 380 */                 this.state = 6;
/* 381 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 1:
/* 386 */               this.state = 2;
/* 387 */               eatText3(value);
/*     */               return;
/*     */             case 9:
/* 390 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 6:
/* 393 */               attIdx = this.context.getAttribute("", "Location");
/* 394 */               if (attIdx >= 0) {
/* 395 */                 String v = this.context.eatAttribute(attIdx);
/* 396 */                 this.state = 9;
/* 397 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 0:
/* 402 */               attIdx = this.context.getAttribute("", "AuthorityKind");
/* 403 */               if (attIdx >= 0) {
/* 404 */                 String v = this.context.eatAttribute(attIdx);
/* 405 */                 this.state = 3;
/* 406 */                 eatText3(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 7:
/* 411 */               this.state = 8;
/* 412 */               eatText2(value); return;
/*     */           }  break;
/*     */         } 
/* 415 */       } catch (RuntimeException e) {
/* 416 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AuthorityBindingTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */