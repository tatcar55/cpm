/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.bind.validator.SchemaDeserializer;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AudienceRestrictionConditionType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.ValidatableObject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AudienceRestrictionConditionTypeImpl extends ConditionAbstractTypeImpl implements AudienceRestrictionConditionType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  17 */   public static final Class version = JAXBVersion.class; protected ListImpl _Audience;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return AudienceRestrictionConditionType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getAudience() {
/*  25 */     if (this._Audience == null) {
/*  26 */       this._Audience = new ListImpl(new ArrayList());
/*     */     }
/*  28 */     return this._Audience;
/*     */   }
/*     */   
/*     */   public List getAudience() {
/*  32 */     return (List)_getAudience();
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  36 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  42 */     int idx1 = 0;
/*  43 */     int len1 = (this._Audience == null) ? 0 : this._Audience.size();
/*  44 */     super.serializeBody(context);
/*  45 */     while (idx1 != len1) {
/*  46 */       context.startElement("urn:oasis:names:tc:SAML:1.0:assertion", "Audience");
/*  47 */       int idx_0 = idx1;
/*     */       try {
/*  49 */         idx_0++;
/*  50 */       } catch (Exception e) {
/*  51 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  53 */       context.endNamespaceDecls();
/*  54 */       int idx_1 = idx1;
/*     */       try {
/*  56 */         idx_1++;
/*  57 */       } catch (Exception e) {
/*  58 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  60 */       context.endAttributes();
/*     */       try {
/*  62 */         context.text((String)this._Audience.get(idx1++), "Audience");
/*  63 */       } catch (Exception e) {
/*  64 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  66 */       context.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  73 */     int idx1 = 0;
/*  74 */     int len1 = (this._Audience == null) ? 0 : this._Audience.size();
/*  75 */     super.serializeAttributes(context);
/*  76 */     while (idx1 != len1) {
/*     */       try {
/*  78 */         idx1++;
/*  79 */       } catch (Exception e) {
/*  80 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  88 */     int idx1 = 0;
/*  89 */     int len1 = (this._Audience == null) ? 0 : this._Audience.size();
/*  90 */     super.serializeURIs(context);
/*  91 */     while (idx1 != len1) {
/*     */       try {
/*  93 */         idx1++;
/*  94 */       } catch (Exception e) {
/*  95 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 101 */     return AudienceRestrictionConditionType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 105 */     if (schemaFragment == null) {
/* 106 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003pp\000sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\002L\000\004exp2q\000~\000\002xq\000~\000\003ppsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000#com.sun.msv.datatype.xsd.AnyURIType\000\000\000\000\000\000\000\001\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUrit\000\022Ljava/lang/String;L\000\btypeNameq\000~\000\025L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\006anyURIsr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000\025L\000\fnamespaceURIq\000~\000\025xpq\000~\000\031q\000~\000\030sr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\013ppsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\007xq\000~\000\003q\000~\000 psq\000~\000\rppsr\000\"com.sun.msv.datatype.xsd.QnameType\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\022q\000~\000\030t\000\005QNameq\000~\000\034q\000~\000\036sq\000~\000!q\000~\000*q\000~\000\030sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNameq\000~\000\025L\000\fnamespaceURIq\000~\000\025xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpt\000\004typet\000)http://www.w3.org/2001/XMLSchema-instancesr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\037\001q\000~\0002sq\000~\000,t\000\bAudiencet\000%urn:oasis:names:tc:SAML:1.0:assertionsr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\003\001pq\000~\000\005q\000~\000\fq\000~\000$x");
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
/* 146 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AudienceRestrictionConditionTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 155 */       super(context, "-----");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 159 */       this(context);
/* 160 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 164 */       return AudienceRestrictionConditionTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/* 173 */       switch (this.state) {
/*     */         case 0:
/* 175 */           AudienceRestrictionConditionTypeImpl.this.getClass(); spawnHandlerFromEnterElement((UnmarshallingEventHandler)new ConditionAbstractTypeImpl.Unmarshaller(AudienceRestrictionConditionTypeImpl.this, this.context), 1, ___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 4:
/* 178 */           if ("Audience" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 179 */             this.context.pushAttributes(__atts, true);
/* 180 */             this.state = 2;
/*     */             return;
/*     */           } 
/* 183 */           revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
/*     */           return;
/*     */         case 1:
/* 186 */           if ("Audience" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 187 */             this.context.pushAttributes(__atts, true);
/* 188 */             this.state = 2;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/* 193 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/* 204 */       switch (this.state) {
/*     */         case 3:
/* 206 */           if ("Audience" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 207 */             this.context.popAttributes();
/* 208 */             this.state = 4;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case 0:
/* 213 */           AudienceRestrictionConditionTypeImpl.this.getClass(); spawnHandlerFromLeaveElement((UnmarshallingEventHandler)new ConditionAbstractTypeImpl.Unmarshaller(AudienceRestrictionConditionTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 4:
/* 216 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 219 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 230 */       switch (this.state) {
/*     */         case 0:
/* 232 */           AudienceRestrictionConditionTypeImpl.this.getClass(); spawnHandlerFromEnterAttribute((UnmarshallingEventHandler)new ConditionAbstractTypeImpl.Unmarshaller(AudienceRestrictionConditionTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 4:
/* 235 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 238 */       super.enterAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveAttribute(String ___uri, String ___local, String ___qname) throws SAXException {
/* 249 */       switch (this.state) {
/*     */         case 0:
/* 251 */           AudienceRestrictionConditionTypeImpl.this.getClass(); spawnHandlerFromLeaveAttribute((UnmarshallingEventHandler)new ConditionAbstractTypeImpl.Unmarshaller(AudienceRestrictionConditionTypeImpl.this, this.context), 1, ___uri, ___local, ___qname);
/*     */           return;
/*     */         case 4:
/* 254 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 257 */       super.leaveAttribute(___uri, ___local, ___qname);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handleText(String value) throws SAXException {
/*     */       try {
/* 269 */         switch (this.state) {
/*     */           case 2:
/* 271 */             this.state = 3;
/* 272 */             eatText1(value);
/*     */             return;
/*     */           case 0:
/* 275 */             AudienceRestrictionConditionTypeImpl.this.getClass(); spawnHandlerFromText((UnmarshallingEventHandler)new ConditionAbstractTypeImpl.Unmarshaller(AudienceRestrictionConditionTypeImpl.this, this.context), 1, value);
/*     */             return;
/*     */           case 4:
/* 278 */             revertToParentFromText(value);
/*     */             return;
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
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 292 */         AudienceRestrictionConditionTypeImpl.this._getAudience().add(WhiteSpaceProcessor.collapse(value));
/* 293 */       } catch (Exception e) {
/* 294 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AudienceRestrictionConditionTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */