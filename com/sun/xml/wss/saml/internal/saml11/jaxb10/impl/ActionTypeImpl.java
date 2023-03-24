/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.ActionType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ActionTypeImpl implements ActionType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected String _Value;
/*  16 */   public static final Class version = JAXBVersion.class; protected String _Namespace;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return ActionType.class;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  24 */     return this._Value;
/*     */   }
/*     */   
/*     */   public void setValue(String value) {
/*  28 */     this._Value = value;
/*     */   }
/*     */   
/*     */   public String getNamespace() {
/*  32 */     return this._Namespace;
/*     */   }
/*     */   
/*     */   public void setNamespace(String value) {
/*  36 */     this._Namespace = value;
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
/*  47 */       context.text(this._Value, "Value");
/*  48 */     } catch (Exception e) {
/*  49 */       Util.handlePrintConversionException(this, e, context);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  56 */     if (this._Namespace != null) {
/*  57 */       context.startAttribute("", "Namespace");
/*     */       try {
/*  59 */         context.text(this._Namespace, "Namespace");
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
/*     */   public Class getPrimaryInterface() {
/*  73 */     return ActionType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  77 */     if (schemaFragment == null) {
/*  78 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\020L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\013psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\020L\000\fnamespaceURIq\000~\000\020xpq\000~\000\024q\000~\000\023sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xq\000~\000\003q\000~\000\013psq\000~\000\006ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\rq\000~\000\023t\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\026q\000~\000\031sq\000~\000\032q\000~\000$q\000~\000\023sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\020L\000\fnamespaceURIq\000~\000\020xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\tNamespacet\000\000sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\n\001q\000~\000.sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\002\001pq\000~\000\035q\000~\000\005x");
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
/* 113 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final ActionTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 122 */       super(context, "-----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 126 */       this(context);
/* 127 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 131 */       return ActionTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 140 */         switch (this.state) {
/*     */           case 4:
/* 142 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           case 0:
/* 145 */             attIdx = this.context.getAttribute("", "Namespace");
/* 146 */             if (attIdx >= 0) {
/* 147 */               String v = this.context.eatAttribute(attIdx);
/* 148 */               this.state = 3;
/* 149 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 152 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 155 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 164 */         ActionTypeImpl.this._Namespace = WhiteSpaceProcessor.collapse(value);
/* 165 */       } catch (Exception e) {
/* 166 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 176 */         switch (this.state) {
/*     */           case 4:
/* 178 */             revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 181 */             attIdx = this.context.getAttribute("", "Namespace");
/* 182 */             if (attIdx >= 0) {
/* 183 */               String v = this.context.eatAttribute(attIdx);
/* 184 */               this.state = 3;
/* 185 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 188 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 191 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 202 */         switch (this.state) {
/*     */           case 4:
/* 204 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 207 */             if ("Namespace" == ___local && "" == ___uri) {
/* 208 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 211 */             this.state = 3; continue;
/*     */         }  break;
/*     */       } 
/* 214 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 225 */         switch (this.state) {
/*     */           case 4:
/* 227 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           case 0:
/* 230 */             attIdx = this.context.getAttribute("", "Namespace");
/* 231 */             if (attIdx >= 0) {
/* 232 */               String v = this.context.eatAttribute(attIdx);
/* 233 */               this.state = 3;
/* 234 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 237 */             this.state = 3;
/*     */             continue;
/*     */           case 2:
/* 240 */             if ("Namespace" == ___local && "" == ___uri) {
/* 241 */               this.state = 3; return;
/*     */             }  break;
/*     */           default:
/*     */             break;
/*     */         } 
/* 246 */         super.leaveAttribute(___uri, ___local, ___qname); return; }  super.leaveAttribute(___uri, ___local, ___qname);
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
/* 258 */           switch (this.state) {
/*     */             case 4:
/* 260 */               revertToParentFromText(value);
/*     */               return;
/*     */             case 3:
/* 263 */               this.state = 4;
/* 264 */               eatText2(value);
/*     */               return;
/*     */             case 0:
/* 267 */               attIdx = this.context.getAttribute("", "Namespace");
/* 268 */               if (attIdx >= 0) {
/* 269 */                 String v = this.context.eatAttribute(attIdx);
/* 270 */                 this.state = 3;
/* 271 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 274 */               this.state = 3;
/*     */               continue;
/*     */             case 1:
/* 277 */               this.state = 2;
/* 278 */               eatText1(value); return;
/*     */           }  break;
/*     */         } 
/* 281 */       } catch (RuntimeException e) {
/* 282 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 292 */         ActionTypeImpl.this._Value = value;
/* 293 */       } catch (Exception e) {
/* 294 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\ActionTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */