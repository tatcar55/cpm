/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.datatype.xsd.Base64BinaryType;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.SignatureValueType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class SignatureValueTypeImpl implements SignatureValueType, JAXBObject, UnmarshallableObject, XMLSerializable, IdentifiableObject, ValidatableObject {
/*     */   protected byte[] _Value;
/*  16 */   public static final Class version = JAXBVersion.class; protected String _Id;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return SignatureValueType.class;
/*     */   }
/*     */   
/*     */   public byte[] getValue() {
/*  24 */     return this._Value;
/*     */   }
/*     */   
/*     */   public void setValue(byte[] value) {
/*  28 */     this._Value = value;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  32 */     return this._Id;
/*     */   }
/*     */   
/*     */   public void setId(String value) {
/*  36 */     this._Id = value;
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  40 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*     */     try {
/*  47 */       context.text(Base64BinaryType.save(this._Value), "Value");
/*  48 */     } catch (Exception e) {
/*  49 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  56 */     if (this._Id != null) {
/*  57 */       context.startAttribute("", "Id");
/*     */       try {
/*  59 */         context.text(context.onID(this, this._Id), "Id");
/*  60 */       } catch (Exception e) {
/*  61 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  63 */       context.endAttribute();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */   
/*     */   public String ____jaxb____getId() {
/*  73 */     return this._Id;
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  77 */     return SignatureValueType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  81 */     if (schemaFragment == null) {
/*  82 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000)com.sun.msv.datatype.xsd.Base64BinaryType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.BinaryBaseType§Î\016^¯W\021\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\017L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\fbase64Binarysr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\017L\000\fnamespaceURIq\000~\000\017xpq\000~\000\023q\000~\000\022sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xq\000~\000\003q\000~\000\032psq\000~\000\006ppsr\000\037com.sun.msv.datatype.xsd.IDType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.NcnameType\000\000\000\000\000\000\000\001\002\000\000xr\000\"com.sun.msv.datatype.xsd.TokenType\000\000\000\000\000\000\000\001\002\000\000xr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxq\000~\000\fq\000~\000\022t\000\002IDq\000~\000\026\000q\000~\000\030sq\000~\000\033q\000~\000(q\000~\000\022sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\017L\000\fnamespaceURIq\000~\000\017xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\002Idt\000\000sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\031\001q\000~\0000sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\036q\000~\000\005x");
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
/* 119 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final SignatureValueTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 128 */       super(context, "-----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 132 */       this(context);
/* 133 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 137 */       return SignatureValueTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 146 */         switch (this.state) {
/*     */           case 0:
/* 148 */             attIdx = this.context.getAttribute("", "Id");
/* 149 */             if (attIdx >= 0) {
/* 150 */               String v = this.context.eatAttribute(attIdx);
/* 151 */               this.state = 3;
/* 152 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 155 */             this.state = 3;
/*     */             continue;
/*     */           case 4:
/* 158 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*     */         }  break;
/*     */       } 
/* 161 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 170 */         SignatureValueTypeImpl.this._Id = this.context.addToIdTable(WhiteSpaceProcessor.collapse(value));
/* 171 */       } catch (Exception e) {
/* 172 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 182 */         switch (this.state) {
/*     */           case 0:
/* 184 */             attIdx = this.context.getAttribute("", "Id");
/* 185 */             if (attIdx >= 0) {
/* 186 */               String v = this.context.eatAttribute(attIdx);
/* 187 */               this.state = 3;
/* 188 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 191 */             this.state = 3;
/*     */             continue;
/*     */           case 4:
/* 194 */             revertToParentFromLeaveElement(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 197 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 208 */         switch (this.state) {
/*     */           case 0:
/* 210 */             if ("Id" == ___local && "" == ___uri) {
/* 211 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 214 */             this.state = 3;
/*     */             continue;
/*     */           case 4:
/* 217 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 220 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 231 */         switch (this.state) {
/*     */           case 0:
/* 233 */             attIdx = this.context.getAttribute("", "Id");
/* 234 */             if (attIdx >= 0) {
/* 235 */               String v = this.context.eatAttribute(attIdx);
/* 236 */               this.state = 3;
/* 237 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 240 */             this.state = 3;
/*     */             continue;
/*     */           case 2:
/* 243 */             if ("Id" == ___local && "" == ___uri) {
/* 244 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 4:
/* 249 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname); return;
/*     */           default:
/*     */             break;
/* 252 */         }  super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
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
/* 264 */           switch (this.state) {
/*     */             case 0:
/* 266 */               attIdx = this.context.getAttribute("", "Id");
/* 267 */               if (attIdx >= 0) {
/* 268 */                 String v = this.context.eatAttribute(attIdx);
/* 269 */                 this.state = 3;
/* 270 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 273 */               this.state = 3;
/*     */               continue;
/*     */             case 3:
/* 276 */               this.state = 4;
/* 277 */               eatText2(value);
/*     */               return;
/*     */             case 4:
/* 280 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 1:
/* 283 */               this.state = 2;
/* 284 */               eatText1(value); return;
/*     */           }  break;
/*     */         } 
/* 287 */       } catch (RuntimeException e) {
/* 288 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 298 */         SignatureValueTypeImpl.this._Value = Base64BinaryType.load(WhiteSpaceProcessor.collapse(value));
/* 299 */       } catch (Exception e) {
/* 300 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\SignatureValueTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */