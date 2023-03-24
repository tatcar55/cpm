/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl;
/*     */ import com.sun.msv.verifier.DocumentDeclaration;
/*     */ import com.sun.xml.bind.JAXBObject;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AnyType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingContext;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.UnmarshallingEventHandler;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.Util;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.XMLSerializer;
/*     */ import java.util.List;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class AnyTypeImpl implements AnyType, JAXBObject, UnmarshallableObject, XMLSerializable, ValidatableObject {
/*  15 */   public static final Class version = JAXBVersion.class; protected ListImpl _Content;
/*     */   private static Grammar schemaFragment;
/*     */   
/*     */   private static final Class PRIMARY_INTERFACE_CLASS() {
/*  19 */     return AnyType.class;
/*     */   }
/*     */   
/*     */   protected ListImpl _getContent() {
/*  23 */     if (this._Content == null) {
/*  24 */       this._Content = new ListImpl(new ArrayList());
/*     */     }
/*  26 */     return this._Content;
/*     */   }
/*     */   
/*     */   public List getContent() {
/*  30 */     return (List)_getContent();
/*     */   }
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(UnmarshallingContext context) {
/*  34 */     return (UnmarshallingEventHandler)new Unmarshaller(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(XMLSerializer context) throws SAXException {
/*  40 */     int idx1 = 0;
/*  41 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/*  42 */     while (idx1 != len1) {
/*     */       
/*  44 */       Object o = this._Content.get(idx1);
/*  45 */       if (o instanceof String) {
/*     */         try {
/*  47 */           context.text((String)this._Content.get(idx1++), "Content");
/*  48 */         } catch (Exception e) {
/*  49 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/*  52 */       if (o instanceof Object) {
/*  53 */         context.childAsBody((JAXBObject)this._Content.get(idx1++), "Content"); continue;
/*     */       } 
/*  55 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(XMLSerializer context) throws SAXException {
/*  65 */     int idx1 = 0;
/*  66 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/*  67 */     while (idx1 != len1) {
/*     */       
/*  69 */       Object o = this._Content.get(idx1);
/*  70 */       if (o instanceof String) {
/*     */         try {
/*  72 */           idx1++;
/*  73 */         } catch (Exception e) {
/*  74 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/*  77 */       if (o instanceof Object) {
/*  78 */         idx1++; continue;
/*     */       } 
/*  80 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(XMLSerializer context) throws SAXException {
/*  90 */     int idx1 = 0;
/*  91 */     int len1 = (this._Content == null) ? 0 : this._Content.size();
/*  92 */     while (idx1 != len1) {
/*     */       
/*  94 */       Object o = this._Content.get(idx1);
/*  95 */       if (o instanceof String) {
/*     */         try {
/*  97 */           idx1++;
/*  98 */         } catch (Exception e) {
/*  99 */           Util.handlePrintConversionException(this, e, context);
/*     */         }  continue;
/*     */       } 
/* 102 */       if (o instanceof Object) {
/* 103 */         idx1++; continue;
/*     */       } 
/* 105 */       Util.handleTypeMismatchError(context, this, "Content", o);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPrimaryInterface() {
/* 113 */     return AnyType.class;
/*     */   }
/*     */   
/*     */   public DocumentDeclaration createRawValidator() {
/* 117 */     if (schemaFragment == null) {
/* 118 */       schemaFragment = SchemaDeserializer.deserialize("¬í\000\005sr\000\034com.sun.msv.grammar.MixedExp\000\000\000\000\000\000\000\001\002\000\000xr\000\034com.sun.msv.grammar.UnaryExp\000\000\000\000\000\000\000\001\002\000\001L\000\003expt\000 Lcom/sun/msv/grammar/Expression;xr\000\036com.sun.msv.grammar.Expressionø\030èN5~O\002\000\002L\000\023epsilonReducibilityt\000\023Ljava/lang/Boolean;L\000\013expandedExpq\000~\000\002xpppsr\000\035com.sun.msv.grammar.ChoiceExp\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.BinaryExp\000\000\000\000\000\000\000\001\002\000\002L\000\004exp1q\000~\000\002L\000\004exp2q\000~\000\002xq\000~\000\003ppsr\000 com.sun.msv.grammar.OneOrMoreExp\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\001sr\000\021java.lang.BooleanÍ rÕúî\002\000\001Z\000\005valuexp\000psr\000'com.sun.msv.grammar.trex.ElementPattern\000\000\000\000\000\000\000\001\002\000\001L\000\tnameClasst\000\037Lcom/sun/msv/grammar/NameClass;xr\000\036com.sun.msv.grammar.ElementExp\000\000\000\000\000\000\000\001\002\000\002Z\000\032ignoreUndeclaredAttributesL\000\fcontentModelq\000~\000\002xq\000~\000\003q\000~\000\fp\000sr\000 com.sun.msv.grammar.AttributeExp\000\000\000\000\000\000\000\001\002\000\002L\000\003expq\000~\000\002L\000\tnameClassq\000~\000\016xq\000~\000\003ppsr\0002com.sun.msv.grammar.Expression$AnyStringExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003sq\000~\000\013\001q\000~\000\024sr\000 com.sun.msv.grammar.AnyNameClass\000\000\000\000\000\000\000\001\002\000\000xr\000\035com.sun.msv.grammar.NameClass\000\000\000\000\000\000\000\001\002\000\000xpsr\000&com.sun.msv.grammar.NamespaceNameClass\000\000\000\000\000\000\000\001\002\000\001L\000\fnamespaceURIt\000\022Ljava/lang/String;xq\000~\000\027t\000+http://java.sun.com/jaxb/xjc/dummy-elementssr\0000com.sun.msv.grammar.Expression$EpsilonExpression\000\000\000\000\000\000\000\001\002\000\000xq\000~\000\003q\000~\000\025q\000~\000\036sr\000\"com.sun.msv.grammar.ExpressionPool\000\000\000\000\000\000\000\001\002\000\001L\000\bexpTablet\000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\000-com.sun.msv.grammar.ExpressionPool$ClosedHash×jÐNïèí\034\003\000\003I\000\005countB\000\rstreamVersionL\000\006parentt\000$Lcom/sun/msv/grammar/ExpressionPool;xp\000\000\000\003\001pq\000~\000\nq\000~\000\bq\000~\000\005x");
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
/* 144 */     return (DocumentDeclaration)new REDocumentDeclaration(schemaFragment);
/*     */   }
/*     */   
/*     */   public class Unmarshaller
/*     */     extends AbstractUnmarshallingEventHandlerImpl
/*     */   {
/*     */     private final AnyTypeImpl this$0;
/*     */     
/*     */     public Unmarshaller(UnmarshallingContext context) {
/* 153 */       super(context, "-");
/*     */     }
/*     */     
/*     */     protected Unmarshaller(UnmarshallingContext context, int startState) {
/* 157 */       this(context);
/* 158 */       this.state = startState;
/*     */     }
/*     */     
/*     */     public Object owner() {
/* 162 */       return AnyTypeImpl.this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void enterElement(String ___uri, String ___local, String ___qname, Attributes __atts) throws SAXException {
/*     */       Object co;
/* 171 */       switch (this.state) {
/*     */         
/*     */         case 0:
/* 174 */           co = spawnWildcard(0, ___uri, ___local, ___qname, __atts);
/* 175 */           if (co != null) {
/* 176 */             AnyTypeImpl.this._getContent().add(co);
/*     */           }
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 183 */       super.enterElement(___uri, ___local, ___qname, __atts);
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
/* 194 */       switch (this.state) {
/*     */         case 0:
/* 196 */           revertToParentFromLeaveElement(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 199 */       super.leaveElement(___uri, ___local, ___qname);
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
/* 210 */       switch (this.state) {
/*     */         case 0:
/* 212 */           revertToParentFromEnterAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 215 */       super.enterAttribute(___uri, ___local, ___qname);
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
/* 226 */       switch (this.state) {
/*     */         case 0:
/* 228 */           revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
/*     */           return;
/*     */       } 
/* 231 */       super.leaveAttribute(___uri, ___local, ___qname);
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
/* 243 */         switch (this.state) {
/*     */           case 0:
/* 245 */             this.state = 0;
/* 246 */             eatText1(value);
/*     */             return;
/*     */         } 
/* 249 */       } catch (RuntimeException e) {
/* 250 */         handleUnexpectedTextException(value, e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void eatText1(String value) throws SAXException {
/*     */       try {
/* 260 */         AnyTypeImpl.this._getContent().add(value);
/* 261 */       } catch (Exception e) {
/* 262 */         handleParseConversionException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\AnyTypeImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */