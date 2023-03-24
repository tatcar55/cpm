/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeDesignatorType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AttributeDesignatorTypeImpl implements AttributeDesignatorType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _AttributeName;
/*  16 */   public static final Class version = JAXBVersion.class; protected String _AttributeNamespace;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return AttributeDesignatorType.class;
/*     */   }
/*     */   
/*     */   public String getAttributeName() {
/*  24 */     return this._AttributeName;
/*     */   }
/*     */   
/*     */   public void setAttributeName(String value) {
/*  28 */     this._AttributeName = value;
/*     */   }
/*     */   
/*     */   public String getAttributeNamespace() {
/*  32 */     return this._AttributeNamespace;
/*     */   }
/*     */   
/*     */   public void setAttributeNamespace(String value) {
/*  36 */     this._AttributeNamespace = value;
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
/*  51 */     context.startAttribute("", "AttributeName");
/*     */     try {
/*  53 */       context.text(this._AttributeName, "AttributeName");
/*  54 */     } catch (Exception e) {
/*  55 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  57 */     context.endAttribute();
/*  58 */     context.startAttribute("", "AttributeNamespace");
/*     */     try {
/*  60 */       context.text(this._AttributeNamespace, "AttributeNamespace");
/*  61 */     } catch (Exception e) {
/*  62 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*  64 */     context.endAttribute();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  73 */     return AttributeDesignatorType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  77 */     if (schemaFragment == null) {
/*  78 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xq\000~\000\003ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\023L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\016psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xpq\000~\000\027q\000~\000\026sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\023L\000\fnamespaceURIq\000~\000\023xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\rAttributeNamet\000\000sq\000~\000\006ppsq\000~\000\tppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\020q\000~\000\026t\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\031q\000~\000\034sq\000~\000\035q\000~\000(q\000~\000\026sq\000~\000\037t\000\022AttributeNamespaceq\000~\000#sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\001\001pq\000~\000\005x");
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
/* 112 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AttributeDesignatorTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 121 */       super(context, "-------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 125 */       this(context);
/* 126 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 130 */       return AttributeDesignatorTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 139 */         switch (this.state) {
/*     */           case 3:
/* 141 */             attIdx = this.context.getAttribute("", "AttributeNamespace");
/* 142 */             if (attIdx >= 0) {
/* 143 */               String v = this.context.eatAttribute(attIdx);
/* 144 */               this.state = 6;
/* 145 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 150 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 0:
/* 153 */             attIdx = this.context.getAttribute("", "AttributeName");
/* 154 */             if (attIdx >= 0) {
/* 155 */               String v = this.context.eatAttribute(attIdx);
/* 156 */               this.state = 3;
/* 157 */               eatText2(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 162 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 171 */         AttributeDesignatorTypeImpl.this._AttributeNamespace = WhiteSpaceProcessor.collapse(value);
/* 172 */       } catch (Exception e) {
/* 173 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 181 */         AttributeDesignatorTypeImpl.this._AttributeName = value;
/* 182 */       } catch (Exception e) {
/* 183 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 193 */         switch (this.state) {
/*     */           case 3:
/* 195 */             attIdx = this.context.getAttribute("", "AttributeNamespace");
/* 196 */             if (attIdx >= 0) {
/* 197 */               String v = this.context.eatAttribute(attIdx);
/* 198 */               this.state = 6;
/* 199 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 204 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 207 */             attIdx = this.context.getAttribute("", "AttributeName");
/* 208 */             if (attIdx >= 0) {
/* 209 */               String v = this.context.eatAttribute(attIdx);
/* 210 */               this.state = 3;
/* 211 */               eatText2(v); continue;
/*     */             }  break;
/*     */         } 
/*     */         break;
/*     */       } 
/* 216 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 227 */       switch (this.state) {
/*     */         case 3:
/* 229 */           if ("AttributeNamespace" == ___local && "" == ___uri) {
/* 230 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 6:
/* 235 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */         case 0:
/* 238 */           if ("AttributeName" == ___local && "" == ___uri) {
/* 239 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 244 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 255 */         switch (this.state) {
/*     */           case 3:
/* 257 */             attIdx = this.context.getAttribute("", "AttributeNamespace");
/* 258 */             if (attIdx >= 0) {
/* 259 */               String v = this.context.eatAttribute(attIdx);
/* 260 */               this.state = 6;
/* 261 */               eatText1(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 6:
/* 266 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 269 */             attIdx = this.context.getAttribute("", "AttributeName");
/* 270 */             if (attIdx >= 0) {
/* 271 */               String v = this.context.eatAttribute(attIdx);
/* 272 */               this.state = 3;
/* 273 */               eatText2(v);
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           case 5:
/* 278 */             if ("AttributeNamespace" == ___local && "" == ___uri) {
/* 279 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 284 */             if ("AttributeName" == ___local && "" == ___uri) {
/* 285 */               this.state = 3; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 290 */         super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
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
/* 302 */           switch (this.state) {
/*     */             case 3:
/* 304 */               attIdx = this.context.getAttribute("", "AttributeNamespace");
/* 305 */               if (attIdx >= 0) {
/* 306 */                 String v = this.context.eatAttribute(attIdx);
/* 307 */                 this.state = 6;
/* 308 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 6:
/* 313 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 4:
/* 316 */               this.state = 5;
/* 317 */               eatText1(value);
/*     */               return;
/*     */             case 0:
/* 320 */               attIdx = this.context.getAttribute("", "AttributeName");
/* 321 */               if (attIdx >= 0) {
/* 322 */                 String v = this.context.eatAttribute(attIdx);
/* 323 */                 this.state = 3;
/* 324 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/*     */               break;
/*     */             case 1:
/* 329 */               this.state = 2;
/* 330 */               eatText2(value); return;
/*     */           }  break;
/*     */         } 
/* 333 */       } catch (RuntimeException e) {
/* 334 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AttributeDesignatorTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */