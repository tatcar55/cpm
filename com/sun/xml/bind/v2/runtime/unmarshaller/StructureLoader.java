/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.api.AccessorException;
/*     */ import com.sun.xml.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.bind.v2.runtime.ClassBeanInfoImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.bind.v2.runtime.property.AttributeProperty;
/*     */ import com.sun.xml.bind.v2.runtime.property.Property;
/*     */ import com.sun.xml.bind.v2.runtime.property.StructureLoaderBuilder;
/*     */ import com.sun.xml.bind.v2.runtime.property.UnmarshallerChain;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import com.sun.xml.bind.v2.util.QNameMap;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StructureLoader
/*     */   extends Loader
/*     */ {
/*  82 */   private final QNameMap<ChildLoader> childUnmarshallers = new QNameMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChildLoader catchAll;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChildLoader textHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QNameMap<TransducedAccessor> attUnmarshallers;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Accessor<Object, Map<QName, String>> attCatchAll;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JaxBeanInfo beanInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int frameSize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureLoader(ClassBeanInfoImpl beanInfo) {
/* 118 */     super(true);
/* 119 */     this.beanInfo = (JaxBeanInfo)beanInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(JAXBContextImpl context, ClassBeanInfoImpl beanInfo, Accessor<?, Map<QName, String>> attWildcard) {
/* 130 */     UnmarshallerChain chain = new UnmarshallerChain(context);
/* 131 */     for (ClassBeanInfoImpl bi = beanInfo; bi != null; bi = bi.superClazz) {
/* 132 */       for (int i = bi.properties.length - 1; i >= 0; i--) {
/* 133 */         AttributeProperty ap; Property p = bi.properties[i];
/*     */         
/* 135 */         switch (p.getKind()) {
/*     */           case ATTRIBUTE:
/* 137 */             if (this.attUnmarshallers == null)
/* 138 */               this.attUnmarshallers = new QNameMap(); 
/* 139 */             ap = (AttributeProperty)p;
/* 140 */             this.attUnmarshallers.put(ap.attName.toQName(), ap.xacc);
/*     */             break;
/*     */           case ELEMENT:
/*     */           case REFERENCE:
/*     */           case MAP:
/*     */           case VALUE:
/* 146 */             p.buildChildElementUnmarshallers(chain, this.childUnmarshallers);
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 152 */     this.frameSize = chain.getScopeSize();
/*     */     
/* 154 */     this.textHandler = (ChildLoader)this.childUnmarshallers.get(StructureLoaderBuilder.TEXT_HANDLER);
/* 155 */     this.catchAll = (ChildLoader)this.childUnmarshallers.get(StructureLoaderBuilder.CATCH_ALL);
/*     */     
/* 157 */     if (attWildcard != null) {
/* 158 */       this.attCatchAll = (Accessor)attWildcard;
/*     */ 
/*     */       
/* 161 */       if (this.attUnmarshallers == null)
/* 162 */         this.attUnmarshallers = EMPTY; 
/*     */     } else {
/* 164 */       this.attCatchAll = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 170 */     UnmarshallingContext context = state.getContext();
/*     */ 
/*     */ 
/*     */     
/* 174 */     assert !this.beanInfo.isImmutable();
/*     */ 
/*     */     
/* 177 */     Object child = context.getInnerPeer();
/*     */     
/* 179 */     if (child != null && this.beanInfo.jaxbType != child.getClass()) {
/* 180 */       child = null;
/*     */     }
/* 182 */     if (child != null) {
/* 183 */       this.beanInfo.reset(child, context);
/*     */     }
/* 185 */     if (child == null) {
/* 186 */       child = context.createInstance(this.beanInfo);
/*     */     }
/* 188 */     context.recordInnerPeer(child);
/*     */     
/* 190 */     state.target = child;
/*     */     
/* 192 */     fireBeforeUnmarshal(this.beanInfo, child, state);
/*     */ 
/*     */     
/* 195 */     context.startScope(this.frameSize);
/*     */     
/* 197 */     if (this.attUnmarshallers != null) {
/* 198 */       Attributes atts = ea.atts;
/* 199 */       for (int i = 0; i < atts.getLength(); i++) {
/* 200 */         String auri = atts.getURI(i);
/*     */         
/* 202 */         String alocal = atts.getLocalName(i);
/* 203 */         if ("".equals(alocal)) {
/* 204 */           alocal = atts.getQName(i);
/*     */         }
/* 206 */         String avalue = atts.getValue(i);
/* 207 */         TransducedAccessor xacc = (TransducedAccessor)this.attUnmarshallers.get(auri, alocal);
/*     */         try {
/* 209 */           if (xacc != null) {
/* 210 */             xacc.parse(child, avalue);
/* 211 */           } else if (this.attCatchAll != null) {
/* 212 */             String qname = atts.getQName(i);
/* 213 */             if (!atts.getURI(i).equals("http://www.w3.org/2001/XMLSchema-instance"))
/*     */             { String prefix;
/* 215 */               Object o = state.target;
/* 216 */               Map<QName, String> map = (Map<QName, String>)this.attCatchAll.get(o);
/* 217 */               if (map == null) {
/*     */ 
/*     */ 
/*     */                 
/* 221 */                 if (this.attCatchAll.valueType.isAssignableFrom(HashMap.class)) {
/* 222 */                   map = new HashMap<QName, String>();
/*     */                 }
/*     */                 else {
/*     */                   
/* 226 */                   context.handleError(Messages.UNABLE_TO_CREATE_MAP.format(new Object[] { this.attCatchAll.valueType }));
/*     */                   return;
/*     */                 } 
/* 229 */                 this.attCatchAll.set(o, map);
/*     */               } 
/*     */ 
/*     */               
/* 233 */               int idx = qname.indexOf(':');
/* 234 */               if (idx < 0) { prefix = ""; }
/* 235 */               else { prefix = qname.substring(0, idx); }
/*     */               
/* 237 */               map.put(new QName(auri, alocal, prefix), avalue); } 
/*     */           } 
/* 239 */         } catch (AccessorException e) {
/* 240 */           handleGenericException((Exception)e, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void childElement(UnmarshallingContext.State state, TagName arg) throws SAXException {
/* 248 */     ChildLoader child = (ChildLoader)this.childUnmarshallers.get(arg.uri, arg.local);
/* 249 */     if (child == null) {
/* 250 */       if (this.beanInfo != null && this.beanInfo.getTypeNames() != null) {
/* 251 */         Iterator<QName> typeNamesIt = this.beanInfo.getTypeNames().iterator();
/* 252 */         QName parentQName = null;
/* 253 */         if (typeNamesIt != null && typeNamesIt.hasNext() && this.catchAll == null) {
/* 254 */           parentQName = typeNamesIt.next();
/* 255 */           String parentUri = parentQName.getNamespaceURI();
/* 256 */           child = (ChildLoader)this.childUnmarshallers.get(parentUri, arg.local);
/*     */         } 
/*     */       } 
/* 259 */       if (child == null) {
/* 260 */         child = this.catchAll;
/* 261 */         if (child == null) {
/* 262 */           super.childElement(state, arg);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 268 */     state.loader = child.loader;
/* 269 */     state.receiver = child.receiver;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedChildElements() {
/* 274 */     return this.childUnmarshallers.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedAttributes() {
/* 279 */     return this.attUnmarshallers.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/* 284 */     if (this.textHandler != null) {
/* 285 */       this.textHandler.loader.text(state, text);
/*     */     }
/*     */   }
/*     */   
/*     */   public void leaveElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 290 */     state.getContext().endScope(this.frameSize);
/* 291 */     fireAfterUnmarshal(this.beanInfo, state.target, state.prev);
/*     */   }
/*     */   
/* 294 */   private static final QNameMap<TransducedAccessor> EMPTY = new QNameMap();
/*     */   
/*     */   public JaxBeanInfo getBeanInfo() {
/* 297 */     return this.beanInfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\StructureLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */