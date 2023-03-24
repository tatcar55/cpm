/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SubjectLocalityType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SubjectLocalityTypeImpl implements SubjectLocalityType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _DNSAddress;
/*  16 */   public static final Class version = JAXBVersion.class; protected String _IPAddress;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return SubjectLocalityType.class;
/*     */   }
/*     */   
/*     */   public String getDNSAddress() {
/*  24 */     return this._DNSAddress;
/*     */   }
/*     */   
/*     */   public void setDNSAddress(String value) {
/*  28 */     this._DNSAddress = value;
/*     */   }
/*     */   
/*     */   public String getIPAddress() {
/*  32 */     return this._IPAddress;
/*     */   }
/*     */   
/*     */   public void setIPAddress(String value) {
/*  36 */     this._IPAddress = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  40 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
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
/*  51 */     if (this._DNSAddress != null) {
/*  52 */       context.startAttribute("", "DNSAddress");
/*     */       try {
/*  54 */         context.text(this._DNSAddress, "DNSAddress");
/*  55 */       } catch (Exception e) {
/*  56 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  58 */       context.endAttribute();
/*     */     } 
/*  60 */     if (this._IPAddress != null) {
/*  61 */       context.startAttribute("", "IPAddress");
/*     */       try {
/*  63 */         context.text(this._IPAddress, "IPAddress");
/*  64 */       } catch (Exception e) {
/*  65 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  67 */       context.endAttribute();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  77 */     return SubjectLocalityType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  81 */     if (schemaFragment == null) {
/*  82 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003q\000~\000\fpsr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\025L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\fpsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\025L\000\fnamespaceURIq\000~\000\025xpq\000~\000\031q\000~\000\030sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\025L\000\fnamespaceURIq\000~\000\025xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\nDNSAddresst\000\000sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\013\001q\000~\000'sq\000~\000\006ppsq\000~\000\bq\000~\000\fpq\000~\000\020sq\000~\000!t\000\tIPAddressq\000~\000%q\000~\000'sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\003\001pq\000~\000\005q\000~\000\007q\000~\000)x");
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
/* 115 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SubjectLocalityTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 124 */       super(context, "-------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 128 */       this(context);
/* 129 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 133 */       return SubjectLocalityTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 142 */         switch (this.state) {
/*     */           case 3:
/* 144 */             attIdx = this.context.getAttribute("", "IPAddress");
/* 145 */             if (attIdx >= 0) {
/* 146 */               String v = this.context.eatAttribute(attIdx);
/* 147 */               this.state = 6;
/* 148 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 151 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 154 */             attIdx = this.context.getAttribute("", "DNSAddress");
/* 155 */             if (attIdx >= 0) {
/* 156 */               String v = this.context.eatAttribute(attIdx);
/* 157 */               this.state = 3;
/* 158 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 161 */             this.state = 3;
/*     */             continue;
/*     */           case 6:
/* 164 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*     */         }  break;
/*     */       } 
/* 167 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 176 */         SubjectLocalityTypeImpl.this._IPAddress = value;
/* 177 */       } catch (Exception e) {
/* 178 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 186 */         SubjectLocalityTypeImpl.this._DNSAddress = value;
/* 187 */       } catch (Exception e) {
/* 188 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 198 */         switch (this.state) {
/*     */           case 3:
/* 200 */             attIdx = this.context.getAttribute("", "IPAddress");
/* 201 */             if (attIdx >= 0) {
/* 202 */               String v = this.context.eatAttribute(attIdx);
/* 203 */               this.state = 6;
/* 204 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 207 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 210 */             attIdx = this.context.getAttribute("", "DNSAddress");
/* 211 */             if (attIdx >= 0) {
/* 212 */               String v = this.context.eatAttribute(attIdx);
/* 213 */               this.state = 3;
/* 214 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 217 */             this.state = 3;
/*     */             continue;
/*     */           case 6:
/* 220 */             revertToParentFromLeaveElement(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 223 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 234 */         switch (this.state) {
/*     */           case 3:
/* 236 */             if ("IPAddress" == ___local && "" == ___uri) {
/* 237 */               this.state = 4;
/*     */               return;
/*     */             } 
/* 240 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 243 */             if ("DNSAddress" == ___local && "" == ___uri) {
/* 244 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 247 */             this.state = 3;
/*     */             continue;
/*     */           case 6:
/* 250 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 253 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 264 */         switch (this.state) {
/*     */           case 3:
/* 266 */             attIdx = this.context.getAttribute("", "IPAddress");
/* 267 */             if (attIdx >= 0) {
/* 268 */               String v = this.context.eatAttribute(attIdx);
/* 269 */               this.state = 6;
/* 270 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 273 */             this.state = 6;
/*     */             continue;
/*     */           case 5:
/* 276 */             if ("IPAddress" == ___local && "" == ___uri) {
/* 277 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 0:
/* 282 */             attIdx = this.context.getAttribute("", "DNSAddress");
/* 283 */             if (attIdx >= 0) {
/* 284 */               String v = this.context.eatAttribute(attIdx);
/* 285 */               this.state = 3;
/* 286 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 289 */             this.state = 3;
/*     */             continue;
/*     */           case 2:
/* 292 */             if ("DNSAddress" == ___local && "" == ___uri) {
/* 293 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 298 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 301 */         }  super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
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
/* 313 */           switch (this.state) {
/*     */             case 3:
/* 315 */               attIdx = this.context.getAttribute("", "IPAddress");
/* 316 */               if (attIdx >= 0) {
/* 317 */                 String v = this.context.eatAttribute(attIdx);
/* 318 */                 this.state = 6;
/* 319 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 322 */               this.state = 6;
/*     */               continue;
/*     */             case 0:
/* 325 */               attIdx = this.context.getAttribute("", "DNSAddress");
/* 326 */               if (attIdx >= 0) {
/* 327 */                 String v = this.context.eatAttribute(attIdx);
/* 328 */                 this.state = 3;
/* 329 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/* 332 */               this.state = 3;
/*     */               continue;
/*     */             case 4:
/* 335 */               this.state = 5;
/* 336 */               eatText1(value);
/*     */               return;
/*     */             case 6:
/* 339 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 1:
/* 342 */               this.state = 2;
/* 343 */               eatText2(value); return;
/*     */           }  break;
/*     */         } 
/* 346 */       } catch (RuntimeException e) {
/* 347 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SubjectLocalityTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */