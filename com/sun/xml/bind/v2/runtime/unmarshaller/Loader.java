/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public abstract class Loader
/*     */ {
/*     */   protected boolean expectText;
/*     */   
/*     */   protected Loader(boolean expectText) {
/*  64 */     this.expectText = expectText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Loader() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 105 */     reportUnexpectedChildElement(ea, true);
/* 106 */     state.loader = Discarder.INSTANCE;
/* 107 */     state.receiver = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void reportUnexpectedChildElement(TagName ea, boolean canRecover) throws SAXException {
/* 112 */     if (canRecover && !(UnmarshallingContext.getInstance()).parent.hasEventHandler()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 117 */     if (ea.uri != ea.uri.intern() || ea.local != ea.local.intern()) {
/* 118 */       reportError(Messages.UNINTERNED_STRINGS.format(new Object[0]), canRecover);
/*     */     } else {
/* 120 */       reportError(Messages.UNEXPECTED_ELEMENT.format(new Object[] { ea.uri, ea.local, computeExpectedElements() }, ), canRecover);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedChildElements() {
/* 127 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedAttributes() {
/* 134 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/* 146 */     text = text.toString().replace('\r', ' ').replace('\n', ' ').replace('\t', ' ').trim();
/* 147 */     reportError(Messages.UNEXPECTED_TEXT.format(new Object[] { text }, ), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean expectText() {
/* 155 */     return this.expectText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void leaveElement(UnmarshallingContext.State state, TagName ea) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String computeExpectedElements() {
/* 185 */     StringBuilder r = new StringBuilder();
/*     */     
/* 187 */     for (QName n : getExpectedChildElements()) {
/* 188 */       if (r.length() != 0) r.append(','); 
/* 189 */       r.append("<{").append(n.getNamespaceURI()).append('}').append(n.getLocalPart()).append('>');
/*     */     } 
/* 191 */     if (r.length() == 0) {
/* 192 */       return "(none)";
/*     */     }
/*     */     
/* 195 */     return r.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void fireBeforeUnmarshal(JaxBeanInfo beanInfo, Object child, UnmarshallingContext.State state) throws SAXException {
/* 205 */     if (beanInfo.lookForLifecycleMethods()) {
/* 206 */       UnmarshallingContext context = state.getContext();
/* 207 */       Unmarshaller.Listener listener = context.parent.getListener();
/* 208 */       if (beanInfo.hasBeforeUnmarshalMethod()) {
/* 209 */         beanInfo.invokeBeforeUnmarshalMethod(context.parent, child, state.prev.target);
/*     */       }
/* 211 */       if (listener != null) {
/* 212 */         listener.beforeUnmarshal(child, state.prev.target);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void fireAfterUnmarshal(JaxBeanInfo beanInfo, Object child, UnmarshallingContext.State state) throws SAXException {
/* 225 */     if (beanInfo.lookForLifecycleMethods()) {
/* 226 */       UnmarshallingContext context = state.getContext();
/* 227 */       Unmarshaller.Listener listener = context.parent.getListener();
/* 228 */       if (beanInfo.hasAfterUnmarshalMethod()) {
/* 229 */         beanInfo.invokeAfterUnmarshalMethod(context.parent, child, state.target);
/*     */       }
/* 231 */       if (listener != null) {
/* 232 */         listener.afterUnmarshal(child, state.target);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void handleGenericException(Exception e) throws SAXException {
/* 241 */     handleGenericException(e, false);
/*     */   }
/*     */   
/*     */   public static void handleGenericException(Exception e, boolean canRecover) throws SAXException {
/* 245 */     reportError(e.getMessage(), e, canRecover);
/*     */   }
/*     */   
/*     */   public static void handleGenericError(Error e) throws SAXException {
/* 249 */     reportError(e.getMessage(), false);
/*     */   }
/*     */   
/*     */   protected static void reportError(String msg, boolean canRecover) throws SAXException {
/* 253 */     reportError(msg, null, canRecover);
/*     */   }
/*     */   
/*     */   public static void reportError(String msg, Exception nested, boolean canRecover) throws SAXException {
/* 257 */     UnmarshallingContext context = UnmarshallingContext.getInstance();
/* 258 */     context.handleEvent(new ValidationEventImpl(canRecover ? 1 : 2, msg, context.getLocator().getLocation(), nested), canRecover);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void handleParseConversionException(UnmarshallingContext.State state, Exception e) throws SAXException {
/* 271 */     state.getContext().handleError(e);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\Loader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */