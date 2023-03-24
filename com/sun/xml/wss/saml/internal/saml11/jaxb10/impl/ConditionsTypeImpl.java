/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.datatype.xsd.DateTimeType;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.ConditionsType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.Calendar;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class ConditionsTypeImpl implements ConditionsType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*     */   protected Calendar _NotOnOrAfter;
/*  17 */   public static final Class version = JAXBVersion.class; protected Calendar _NotBefore; protected ListImpl _AudienceRestrictionConditionOrDoNotCacheConditionOrCondition; private static Grammar schemaFragment; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AudienceRestrictionConditionImpl; static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DoNotCacheConditionImpl;
/*     */   static Class class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionImpl;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  21 */     return ConditionsType.class;
/*     */   }
/*     */   
/*     */   public Calendar getNotOnOrAfter() {
/*  25 */     return this._NotOnOrAfter;
/*     */   }
/*     */   
/*     */   public void setNotOnOrAfter(Calendar value) {
/*  29 */     this._NotOnOrAfter = value;
/*     */   }
/*     */   
/*     */   public Calendar getNotBefore() {
/*  33 */     return this._NotBefore;
/*     */   }
/*     */   
/*     */   public void setNotBefore(Calendar value) {
/*  37 */     this._NotBefore = value;
/*     */   }
/*     */   
/*     */   protected ListImpl _getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition() {
/*  41 */     if (this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition == null) {
/*  42 */       this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition = new ListImpl(new ArrayList());
/*     */     }
/*  44 */     return this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition;
/*     */   }
/*     */   
/*     */   public List getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition() {
/*  48 */     return (List)_getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition();
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  52 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  58 */     int idx3 = 0;
/*  59 */     int len3 = (this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition == null) ? 0 : this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition.size();
/*  60 */     while (idx3 != len3) {
/*  61 */       context.childAsBody((JAXBObject)this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition.get(idx3++), "AudienceRestrictionConditionOrDoNotCacheConditionOrCondition");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  68 */     int idx3 = 0;
/*  69 */     int len3 = (this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition == null) ? 0 : this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition.size();
/*  70 */     if (this._NotBefore != null) {
/*  71 */       context.startAttribute("", "NotBefore");
/*     */       try {
/*  73 */         context.text(DateTimeType.theInstance.serializeJavaObject(this._NotBefore, null), "NotBefore");
/*  74 */       } catch (Exception e) {
/*  75 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  77 */       context.endAttribute();
/*     */     } 
/*  79 */     if (this._NotOnOrAfter != null) {
/*  80 */       context.startAttribute("", "NotOnOrAfter");
/*     */       try {
/*  82 */         context.text(DateTimeType.theInstance.serializeJavaObject(this._NotOnOrAfter, null), "NotOnOrAfter");
/*  83 */       } catch (Exception e) {
/*  84 */         Util.handlePrintConversionException(this, e, context);
/*     */       } 
/*  86 */       context.endAttribute();
/*     */     } 
/*  88 */     while (idx3 != len3) {
/*  89 */       context.childAsAttributes((JAXBObject)this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition.get(idx3++), "AudienceRestrictionConditionOrDoNotCacheConditionOrCondition");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  96 */     int idx3 = 0;
/*  97 */     int len3 = (this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition == null) ? 0 : this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition.size();
/*  98 */     while (idx3 != len3) {
/*  99 */       context.childAsURIs((JAXBObject)this._AudienceRestrictionConditionOrDoNotCacheConditionOrCondition.get(idx3++), "AudienceRestrictionConditionOrDoNotCacheConditionOrCondition");
/*     */     }
/*     */   }
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 104 */     return ConditionsType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 108 */     if (schemaFragment == null) {
/* 109 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\037com.sun.msv.grammar.SequenceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1t\000 Lcom/sun/msv/grammar/Expression;L\000\004exp2q\000~\000\002xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsq\000~\000\000ppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expq\000~\000\002xq\000~\000\003sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psq\000~\000\007q\000~\000\rpsq\000~\000\007q\000~\000\rpsr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\rp\000sq\000~\000\007ppsq\000~\000\tq\000~\000\rpsr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\021xq\000~\000\003q\000~\000\rpsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\f\001q\000~\000\031sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\032q\000~\000\037sr\000#com.sun.msv.grammar.SimpleNameClass\000\000\000\000\000\000\000\001\002\000\002L\000\tlocalNamet\000\022Ljava/lang/String;L\000\fnamespaceURIq\000~\000!xq\000~\000\034t\000Hcom.sun.xml.wss.saml.internal.saml11.jaxb10.AudienceRestrictionConditiont\000+http://java.sun.com/jaxb/xjc/dummy-elementssq\000~\000\020q\000~\000\rp\000sq\000~\000\007ppsq\000~\000\tq\000~\000\rpsq\000~\000\026q\000~\000\rpq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\000?com.sun.xml.wss.saml.internal.saml11.jaxb10.DoNotCacheConditionq\000~\000$sq\000~\000\020q\000~\000\rp\000sq\000~\000\007ppsq\000~\000\tq\000~\000\rpsq\000~\000\026q\000~\000\rpq\000~\000\031q\000~\000\035q\000~\000\037sq\000~\000 t\0005com.sun.xml.wss.saml.internal.saml11.jaxb10.Conditionq\000~\000$q\000~\000\037sq\000~\000\007ppsq\000~\000\026q\000~\000\rpsr\000\033com.sun.msv.grammar.DataExp\000\000\000\000\000\000\000\001\002\000\003L\000\002dtt\000\037Lorg/relaxng/datatype/Datatype;L\000\006exceptq\000~\000\002L\000\004namet\000\035Lcom/sun/msv/util/StringPair;xq\000~\000\003ppsr\000%com.sun.msv.datatype.xsd.DateTimeType\000\000\000\000\000\000\000\001\002\000\000xr\000)com.sun.msv.datatype.xsd.DateTimeBaseType\024W\032@3¥´å\002\000\000xr\000*com.sun.msv.datatype.xsd.BuiltinAtomicType\000\000\000\000\000\000\000\001\002\000\000xr\000%com.sun.msv.datatype.xsd.ConcreteType\000\000\000\000\000\000\000\001\002\000\000xr\000'com.sun.msv.datatype.xsd.XSDatatypeImpl\000\000\000\000\000\000\000\001\002\000\003L\000\fnamespaceUriq\000~\000!L\000\btypeNameq\000~\000!L\000\nwhiteSpacet\000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\000 http://www.w3.org/2001/XMLSchemat\000\bdateTimesr\0005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\000\000\000\000\000\000\000\001\002\000\000xr\000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\000\000\000\000\000\000\000\001\002\000\000xpsr\0000com.sun.msv.grammar.Expression$NullSetExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\rpsr\000\033com.sun.msv.util.StringPairÐt\036jB \002\000\002L\000\tlocalNameq\000~\000!L\000\fnamespaceURIq\000~\000!xpq\000~\000?q\000~\000>sq\000~\000 t\000\tNotBeforet\000\000q\000~\000\037sq\000~\000\007ppsq\000~\000\026q\000~\000\rpq\000~\0006sq\000~\000 t\000\fNotOnOrAfterq\000~\000Iq\000~\000\037sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\016\001pq\000~\000\024q\000~\000&q\000~\000,q\000~\000\016q\000~\000\025q\000~\000'q\000~\000-q\000~\000\bq\000~\000\006q\000~\000Jq\000~\000\005q\000~\000\013q\000~\000\017q\000~\0001x");
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
/* 159 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final ConditionsTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 168 */       super(context, "--------");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 172 */       this(context);
/* 173 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 177 */       return ConditionsTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 186 */         switch (this.state) {
/*     */           case 6:
/* 188 */             if ("AudienceRestrictionCondition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 189 */               ConditionsTypeImpl.this._getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition().add(spawnChildFromEnterElement((ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AudienceRestrictionConditionImpl == null) ? (ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AudienceRestrictionConditionImpl = ConditionsTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AudienceRestrictionConditionImpl")) : ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AudienceRestrictionConditionImpl, 7, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 192 */             if ("DoNotCacheCondition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 193 */               ConditionsTypeImpl.this._getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition().add(spawnChildFromEnterElement((ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DoNotCacheConditionImpl == null) ? (ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DoNotCacheConditionImpl = ConditionsTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DoNotCacheConditionImpl")) : ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DoNotCacheConditionImpl, 7, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 196 */             if ("Condition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 197 */               ConditionsTypeImpl.this._getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition().add(spawnChildFromEnterElement((ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionImpl == null) ? (ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionImpl = ConditionsTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionImpl")) : ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionImpl, 7, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 200 */             this.state = 7;
/*     */             continue;
/*     */           case 3:
/* 203 */             attIdx = this.context.getAttribute("", "NotOnOrAfter");
/* 204 */             if (attIdx >= 0) {
/* 205 */               String v = this.context.eatAttribute(attIdx);
/* 206 */               this.state = 6;
/* 207 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 210 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 213 */             attIdx = this.context.getAttribute("", "NotBefore");
/* 214 */             if (attIdx >= 0) {
/* 215 */               String v = this.context.eatAttribute(attIdx);
/* 216 */               this.state = 3;
/* 217 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 220 */             this.state = 3;
/*     */             continue;
/*     */           case 7:
/* 223 */             if ("AudienceRestrictionCondition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 224 */               ConditionsTypeImpl.this._getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition().add(spawnChildFromEnterElement((ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AudienceRestrictionConditionImpl == null) ? (ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AudienceRestrictionConditionImpl = ConditionsTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AudienceRestrictionConditionImpl")) : ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$AudienceRestrictionConditionImpl, 7, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 227 */             if ("DoNotCacheCondition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 228 */               ConditionsTypeImpl.this._getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition().add(spawnChildFromEnterElement((ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DoNotCacheConditionImpl == null) ? (ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DoNotCacheConditionImpl = ConditionsTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DoNotCacheConditionImpl")) : ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$DoNotCacheConditionImpl, 7, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 231 */             if ("Condition" == ___local && "urn:oasis:names:tc:SAML:1.0:assertion" == ___uri) {
/* 232 */               ConditionsTypeImpl.this._getAudienceRestrictionConditionOrDoNotCacheConditionOrCondition().add(spawnChildFromEnterElement((ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionImpl == null) ? (ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionImpl = ConditionsTypeImpl.class$("com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionImpl")) : ConditionsTypeImpl.class$com$sun$xml$wss$saml$internal$saml11$jaxb10$impl$ConditionImpl, 7, ___uri, ___local, ___qname, __atts));
/*     */               return;
/*     */             } 
/* 235 */             revertToParentFromEnterElement(___uri, ___local, ___qname, __atts); return;
/*     */         }  break;
/*     */       } 
/* 238 */       super.enterElement(___uri, ___local, ___qname, __atts);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 247 */         ConditionsTypeImpl.this._NotOnOrAfter = (Calendar)DateTimeType.theInstance.createJavaObject(WhiteSpaceProcessor.collapse(value), null);
/* 248 */       } catch (Exception e) {
/* 249 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText2(String value) throws SAXException {
/*     */       try {
/* 257 */         ConditionsTypeImpl.this._NotBefore = (Calendar)DateTimeType.theInstance.createJavaObject(WhiteSpaceProcessor.collapse(value), null);
/* 258 */       } catch (Exception e) {
/* 259 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void leaveElement(String ___uri, String ___local, String ___qname) throws SAXException {
/*     */       while (true) {
/*     */         int attIdx;
/* 269 */         switch (this.state) {
/*     */           case 6:
/* 271 */             this.state = 7;
/*     */             continue;
/*     */           case 3:
/* 274 */             attIdx = this.context.getAttribute("", "NotOnOrAfter");
/* 275 */             if (attIdx >= 0) {
/* 276 */               String v = this.context.eatAttribute(attIdx);
/* 277 */               this.state = 6;
/* 278 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 281 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 284 */             attIdx = this.context.getAttribute("", "NotBefore");
/* 285 */             if (attIdx >= 0) {
/* 286 */               String v = this.context.eatAttribute(attIdx);
/* 287 */               this.state = 3;
/* 288 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 291 */             this.state = 3;
/*     */             continue;
/*     */           case 7:
/* 294 */             revertToParentFromLeaveElement(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 297 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 308 */         switch (this.state) {
/*     */           case 6:
/* 310 */             this.state = 7;
/*     */             continue;
/*     */           case 3:
/* 313 */             if ("NotOnOrAfter" == ___local && "" == ___uri) {
/* 314 */               this.state = 4;
/*     */               return;
/*     */             } 
/* 317 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 320 */             if ("NotBefore" == ___local && "" == ___uri) {
/* 321 */               this.state = 1;
/*     */               return;
/*     */             } 
/* 324 */             this.state = 3;
/*     */             continue;
/*     */           case 7:
/* 327 */             revertToParentFromEnterAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 330 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 341 */         switch (this.state) {
/*     */           case 6:
/* 343 */             this.state = 7;
/*     */             continue;
/*     */           case 5:
/* 346 */             if ("NotOnOrAfter" == ___local && "" == ___uri) {
/* 347 */               this.state = 6;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 2:
/* 352 */             if ("NotBefore" == ___local && "" == ___uri) {
/* 353 */               this.state = 3;
/*     */               return;
/*     */             } 
/*     */             break;
/*     */           case 3:
/* 358 */             attIdx = this.context.getAttribute("", "NotOnOrAfter");
/* 359 */             if (attIdx >= 0) {
/* 360 */               String v = this.context.eatAttribute(attIdx);
/* 361 */               this.state = 6;
/* 362 */               eatText1(v);
/*     */               continue;
/*     */             } 
/* 365 */             this.state = 6;
/*     */             continue;
/*     */           case 0:
/* 368 */             attIdx = this.context.getAttribute("", "NotBefore");
/* 369 */             if (attIdx >= 0) {
/* 370 */               String v = this.context.eatAttribute(attIdx);
/* 371 */               this.state = 3;
/* 372 */               eatText2(v);
/*     */               continue;
/*     */             } 
/* 375 */             this.state = 3;
/*     */             continue;
/*     */           case 7:
/* 378 */             revertToParentFromLeaveAttribute(___uri, ___local, ___qname); return;
/*     */         }  break;
/*     */       } 
/* 381 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 393 */           switch (this.state) {
/*     */             case 4:
/* 395 */               this.state = 5;
/* 396 */               eatText1(value);
/*     */               return;
/*     */             case 6:
/* 399 */               this.state = 7;
/*     */               continue;
/*     */             case 1:
/* 402 */               this.state = 2;
/* 403 */               eatText2(value);
/*     */               return;
/*     */             case 3:
/* 406 */               attIdx = this.context.getAttribute("", "NotOnOrAfter");
/* 407 */               if (attIdx >= 0) {
/* 408 */                 String v = this.context.eatAttribute(attIdx);
/* 409 */                 this.state = 6;
/* 410 */                 eatText1(v);
/*     */                 continue;
/*     */               } 
/* 413 */               this.state = 6;
/*     */               continue;
/*     */             case 0:
/* 416 */               attIdx = this.context.getAttribute("", "NotBefore");
/* 417 */               if (attIdx >= 0) {
/* 418 */                 String v = this.context.eatAttribute(attIdx);
/* 419 */                 this.state = 3;
/* 420 */                 eatText2(v);
/*     */                 continue;
/*     */               } 
/* 423 */               this.state = 3;
/*     */               continue;
/*     */             case 7:
/* 426 */               revertToParentFromText(value); return;
/*     */           }  break;
/*     */         } 
/* 429 */       } catch (RuntimeException e) {
/* 430 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\ConditionsTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */