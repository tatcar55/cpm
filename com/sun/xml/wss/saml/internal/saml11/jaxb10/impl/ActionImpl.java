/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.msv.verifier.regexp.REDocumentDeclaration;
/*     */ import com.sun.xml.bind.RIElement;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.Action;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.AbstractUnmarshallingEventHandlerImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ActionImpl extends ActionTypeImpl implements Action, RIElement, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  16 */   public static final Class version = JAXBVersion.class;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  20 */     return Action.class;
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getNamespaceURI() {
/*  24 */     return "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   }
/*     */   
/*     */   public String ____jaxb_ri____getLocalName() {
/*  28 */     return "Action";
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  32 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  38 */     context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Action");
/*  39 */     super.serializeURIs(context);
/*  40 */     context.endNamespaceDecls();
/*  41 */     super.serializeAttributes(context);
/*  42 */     context.endAttributes();
/*  43 */     super.serializeBody(context);
/*  44 */     context.endElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/*  58 */     return Action.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/*  62 */     if (schemaFragment == null) {
/*  63 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\003xppp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\003L\000\004exp2q\000~\000\003xq\000~\000\004ppsq\000~\000\007ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\003L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\004sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000#com.sun.msv.datatype.xsd.StringType\000\000\000\000\000\000\000\001\002\000\001Z\000\risAlwaysValidxr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\025L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006stringsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xp\001sr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004q\000~\000\020psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\025L\000\fnamespaceURIq\000~\000\025xpq\000~\000\031q\000~\000\030sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\bppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\003L\000\tnameClassq\000~\000\001xq\000~\000\004q\000~\000\020psq\000~\000\013ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\022q\000~\000\030t\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\033q\000~\000\036sq\000~\000\037q\000~\000(q\000~\000\030sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\025L\000\fnamespaceURIq\000~\000\025xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\tNamespacet\000\000sr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\004sq\000~\000\017\001q\000~\0002sq\000~\000!ppsq\000~\000#q\000~\000\020psq\000~\000\013ppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\022q\000~\000\030t\000\005QNameq\000~\000*q\000~\000\036sq\000~\000\037q\000~\0009q\000~\000\030sq\000~\000,t\000\004typet\000)http://www.w3.org/2001/XMLSchema-instanceq\000~\0002sq\000~\000,t\000\006Actiont\000%urn:oasis:names:tc:SAML:1.0:assertionsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\004\001pq\000~\000\"q\000~\0004q\000~\000\tq\000~\000\nx");
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
/* 105 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final ActionImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 114 */       super(context, "----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 118 */       this(context);
/* 119 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 123 */       return ActionImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       int attIdx;
/* 132 */       switch (this.state) {
/*     */         case 1:
/* 134 */           attIdx = this.context.getAttribute("", "Namespace");
/* 135 */           if (attIdx >= 0) {
/* 136 */             this.context.consumeAttribute(attIdx);
/* 137 */             this.context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 142 */           if ("Action" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 143 */             this.context.pushAttributes(__atts, true);
/* 144 */             this.state = 1;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 149 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */       } 
/* 152 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       int attIdx;
/* 163 */       switch (this.state) {
/*     */         case 2:
/* 165 */           if ("Action" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 166 */             this.context.popAttributes();
/* 167 */             this.state = 3;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 1:
/* 172 */           attIdx = this.context.getAttribute("", "Namespace");
/* 173 */           if (attIdx >= 0) {
/* 174 */             this.context.consumeAttribute(attIdx);
/* 175 */             this.context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 180 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 183 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 194 */       switch (this.state) {
/*     */         case 1:
/* 196 */           if ("Namespace" == ___local && "" == ___uri) {
/* 197 */             ActionImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ActionTypeImpl.Unmarshaller(ActionImpl.this, this.context), 2, ___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 202 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 205 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       int attIdx;
/* 216 */       switch (this.state) {
/*     */         case 1:
/* 218 */           attIdx = this.context.getAttribute("", "Namespace");
/* 219 */           if (attIdx >= 0) {
/* 220 */             this.context.consumeAttribute(attIdx);
/* 221 */             this.context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 3:
/* 226 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 229 */       super.leaveAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleText(String value) throws SAXException {
/*     */       try {
/*     */         int attIdx;
/* 241 */         switch (this.state) {
/*     */           case 1:
/* 243 */             attIdx = this.context.getAttribute("", "Namespace");
/* 244 */             if (attIdx >= 0) {
/* 245 */               this.context.consumeAttribute(attIdx);
/* 246 */               this.context.getCurrentHandler().text(value);
/*     */               return;
/*     */             } 
/* 249 */             ActionImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new ActionTypeImpl.Unmarshaller(ActionImpl.this, this.context), 2, value);
/*     */             return;
/*     */           case 3:
/* 252 */             revertToParentFromText(value);
/*     */             return;
/*     */         } 
/* 255 */       } catch (RuntimeException e) {
/* 256 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\ActionImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */