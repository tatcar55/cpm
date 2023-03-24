/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import javax.xml.bind.annotation.DomHandler;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.sax.TransformerHandler;
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
/*     */ public class DomLoader<ResultT extends Result>
/*     */   extends Loader
/*     */ {
/*     */   private final DomHandler<?, ResultT> dom;
/*     */   
/*     */   private final class State
/*     */   {
/*  66 */     private TransformerHandler handler = null;
/*     */ 
/*     */     
/*     */     private final ResultT result;
/*     */ 
/*     */     
/*  72 */     int depth = 1;
/*     */     
/*     */     public State(UnmarshallingContext context) throws SAXException {
/*  75 */       this.handler = JAXBContextImpl.createTransformerHandler((context.getJAXBContext()).disableSecurityProcessing);
/*  76 */       this.result = (ResultT)DomLoader.this.dom.createUnmarshaller(context);
/*     */       
/*  78 */       this.handler.setResult((Result)this.result);
/*     */ 
/*     */       
/*     */       try {
/*  82 */         this.handler.setDocumentLocator(context.getLocator());
/*  83 */         this.handler.startDocument();
/*  84 */         declarePrefixes(context, context.getAllDeclaredPrefixes());
/*  85 */       } catch (SAXException e) {
/*  86 */         context.handleError(e);
/*  87 */         throw e;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object getElement() {
/*  92 */       return DomLoader.this.dom.getElement(this.result);
/*     */     }
/*     */     
/*     */     private void declarePrefixes(UnmarshallingContext context, String[] prefixes) throws SAXException {
/*  96 */       for (int i = prefixes.length - 1; i >= 0; i--) {
/*  97 */         String nsUri = context.getNamespaceURI(prefixes[i]);
/*  98 */         if (nsUri == null) throw new IllegalStateException("prefix '" + prefixes[i] + "' isn't bound"); 
/*  99 */         this.handler.startPrefixMapping(prefixes[i], nsUri);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void undeclarePrefixes(String[] prefixes) throws SAXException {
/* 104 */       for (int i = prefixes.length - 1; i >= 0; i--)
/* 105 */         this.handler.endPrefixMapping(prefixes[i]); 
/*     */     }
/*     */   }
/*     */   
/*     */   public DomLoader(DomHandler<?, ResultT> dom) {
/* 110 */     super(true);
/* 111 */     this.dom = dom;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 116 */     UnmarshallingContext context = state.getContext();
/* 117 */     if (state.target == null) {
/* 118 */       state.target = new State(context);
/*     */     }
/* 120 */     State s = (State)state.target;
/*     */     try {
/* 122 */       s.declarePrefixes(context, context.getNewlyDeclaredPrefixes());
/* 123 */       s.handler.startElement(ea.uri, ea.local, ea.getQname(), ea.atts);
/* 124 */     } catch (SAXException e) {
/* 125 */       context.handleError(e);
/* 126 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 132 */     state.loader = this;
/* 133 */     State s = (State)state.prev.target;
/* 134 */     s.depth++;
/* 135 */     state.target = s;
/*     */   }
/*     */ 
/*     */   
/*     */   public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/* 140 */     if (text.length() == 0)
/*     */       return; 
/*     */     try {
/* 143 */       State s = (State)state.target;
/* 144 */       s.handler.characters(text.toString().toCharArray(), 0, text.length());
/* 145 */     } catch (SAXException e) {
/* 146 */       state.getContext().handleError(e);
/* 147 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void leaveElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 153 */     State s = (State)state.target;
/* 154 */     UnmarshallingContext context = state.getContext();
/*     */     
/*     */     try {
/* 157 */       s.handler.endElement(ea.uri, ea.local, ea.getQname());
/* 158 */       s.undeclarePrefixes(context.getNewlyDeclaredPrefixes());
/* 159 */     } catch (SAXException e) {
/* 160 */       context.handleError(e);
/* 161 */       throw e;
/*     */     } 
/*     */     
/* 164 */     if (--s.depth == 0) {
/*     */       
/*     */       try {
/* 167 */         s.undeclarePrefixes(context.getAllDeclaredPrefixes());
/* 168 */         s.handler.endDocument();
/* 169 */       } catch (SAXException e) {
/* 170 */         context.handleError(e);
/* 171 */         throw e;
/*     */       } 
/*     */ 
/*     */       
/* 175 */       state.target = s.getElement();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\DomLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */