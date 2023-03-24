/*     */ package com.sun.xml.rpc.wsdl.framework;
/*     */ 
/*     */ import com.sun.xml.rpc.sp.NamespaceSupport;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParserContext
/*     */ {
/*     */   private static final String PREFIX_XMLNS = "xmlns";
/*     */   private boolean _followImports;
/*     */   private AbstractDocument _document;
/*     */   private NamespaceSupport _nsSupport;
/*     */   private ArrayList _listeners;
/*     */   private WSDLLocation _wsdlLocation;
/*     */   
/*     */   public ParserContext(AbstractDocument doc, ArrayList listeners) {
/*  51 */     this._document = doc;
/*  52 */     this._listeners = listeners;
/*  53 */     this._nsSupport = new NamespaceSupport();
/*  54 */     this._wsdlLocation = new WSDLLocation();
/*     */   }
/*     */   
/*     */   public AbstractDocument getDocument() {
/*  58 */     return this._document;
/*     */   }
/*     */   
/*     */   public boolean getFollowImports() {
/*  62 */     return this._followImports;
/*     */   }
/*     */   
/*     */   public void setFollowImports(boolean b) {
/*  66 */     this._followImports = b;
/*     */   }
/*     */   
/*     */   public void push() {
/*  70 */     this._nsSupport.pushContext();
/*     */   }
/*     */   
/*     */   public void pop() {
/*  74 */     this._nsSupport.popContext();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/*  78 */     return this._nsSupport.getURI(prefix);
/*     */   }
/*     */   
/*     */   public Iterator getPrefixes() {
/*  82 */     return this._nsSupport.getPrefixes();
/*     */   }
/*     */   
/*     */   public String getDefaultNamespaceURI() {
/*  86 */     return getNamespaceURI("");
/*     */   }
/*     */   
/*     */   public void registerNamespaces(Element e) {
/*  90 */     for (Iterator<Attr> iter = XmlUtil.getAllAttributes(e); iter.hasNext(); ) {
/*  91 */       Attr a = iter.next();
/*  92 */       if (a.getName().equals("xmlns")) {
/*     */         
/*  94 */         this._nsSupport.declarePrefix("", a.getValue()); continue;
/*     */       } 
/*  96 */       String prefix = XmlUtil.getPrefix(a.getName());
/*  97 */       if (prefix != null && prefix.equals("xmlns")) {
/*  98 */         String nsPrefix = XmlUtil.getLocalPart(a.getName());
/*  99 */         String uri = a.getValue();
/* 100 */         this._nsSupport.declarePrefix(nsPrefix, uri);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public QName translateQualifiedName(String s) {
/* 107 */     if (s == null) {
/* 108 */       return null;
/*     */     }
/* 110 */     String prefix = XmlUtil.getPrefix(s);
/* 111 */     String uri = null;
/*     */     
/* 113 */     if (prefix == null) {
/* 114 */       uri = getDefaultNamespaceURI();
/*     */     } else {
/* 116 */       uri = getNamespaceURI(prefix);
/* 117 */       if (uri == null) {
/* 118 */         throw new ParseException("parsing.unknownNamespacePrefix", prefix);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 124 */     return new QName(uri, XmlUtil.getLocalPart(s));
/*     */   }
/*     */   
/*     */   public void fireIgnoringExtension(QName name, QName parent) {
/* 128 */     List _targets = null;
/*     */     
/* 130 */     synchronized (this) {
/* 131 */       if (this._listeners != null) {
/* 132 */         _targets = (List)this._listeners.clone();
/*     */       }
/*     */     } 
/*     */     
/* 136 */     if (_targets != null) {
/* 137 */       for (Iterator<ParserListener> iter = _targets.iterator(); iter.hasNext(); ) {
/* 138 */         ParserListener l = iter.next();
/* 139 */         l.ignoringExtension(name, parent);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireDoneParsingEntity(QName element, Entity entity) {
/* 145 */     List _targets = null;
/*     */     
/* 147 */     synchronized (this) {
/* 148 */       if (this._listeners != null) {
/* 149 */         _targets = (List)this._listeners.clone();
/*     */       }
/*     */     } 
/*     */     
/* 153 */     if (_targets != null) {
/* 154 */       for (Iterator<ParserListener> iter = _targets.iterator(); iter.hasNext(); ) {
/* 155 */         ParserListener l = iter.next();
/* 156 */         l.doneParsingEntity(element, entity);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushWSDLLocation() {
/* 164 */     this._wsdlLocation.push();
/*     */   }
/*     */   
/*     */   public void popWSDLLocation() {
/* 168 */     this._wsdlLocation.pop();
/*     */   }
/*     */   
/*     */   public void setWSDLLocation(String loc) {
/* 172 */     this._wsdlLocation.setLocation(loc);
/*     */   }
/*     */   
/*     */   public String getWSDLLocation() {
/* 176 */     return this._wsdlLocation.getLocation();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\ParserContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */