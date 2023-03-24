/*     */ package javax.xml.bind.helpers;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.text.MessageFormat;
/*     */ import javax.xml.bind.ValidationEventLocator;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValidationEventLocatorImpl
/*     */   implements ValidationEventLocator
/*     */ {
/*     */   public ValidationEventLocatorImpl() {}
/*     */   
/*     */   public ValidationEventLocatorImpl(Locator loc) {
/*  88 */     if (loc == null) {
/*  89 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "loc"));
/*     */     }
/*     */ 
/*     */     
/*  93 */     this.url = toURL(loc.getSystemId());
/*  94 */     this.columnNumber = loc.getColumnNumber();
/*  95 */     this.lineNumber = loc.getLineNumber();
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
/*     */   public ValidationEventLocatorImpl(SAXParseException e) {
/* 111 */     if (e == null) {
/* 112 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "e"));
/*     */     }
/*     */ 
/*     */     
/* 116 */     this.url = toURL(e.getSystemId());
/* 117 */     this.columnNumber = e.getColumnNumber();
/* 118 */     this.lineNumber = e.getLineNumber();
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
/*     */   public ValidationEventLocatorImpl(Node _node) {
/* 132 */     if (_node == null) {
/* 133 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "_node"));
/*     */     }
/*     */ 
/*     */     
/* 137 */     this.node = _node;
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
/*     */   public ValidationEventLocatorImpl(Object _object) {
/* 151 */     if (_object == null) {
/* 152 */       throw new IllegalArgumentException(Messages.format("Shared.MustNotBeNull", "_object"));
/*     */     }
/*     */ 
/*     */     
/* 156 */     this.object = _object;
/*     */   }
/*     */ 
/*     */   
/*     */   private static URL toURL(String systemId) {
/*     */     try {
/* 162 */       return new URL(systemId);
/* 163 */     } catch (MalformedURLException e) {
/*     */       
/* 165 */       return null;
/*     */     } 
/*     */   }
/*     */   
/* 169 */   private URL url = null;
/* 170 */   private int offset = -1;
/* 171 */   private int lineNumber = -1;
/* 172 */   private int columnNumber = -1;
/* 173 */   private Object object = null;
/* 174 */   private Node node = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getURL() {
/* 181 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURL(URL _url) {
/* 190 */     this.url = _url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOffset() {
/* 197 */     return this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOffset(int _offset) {
/* 206 */     this.offset = _offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/* 213 */     return this.lineNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineNumber(int _lineNumber) {
/* 222 */     this.lineNumber = _lineNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnNumber() {
/* 229 */     return this.columnNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumnNumber(int _columnNumber) {
/* 238 */     this.columnNumber = _columnNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getObject() {
/* 245 */     return this.object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObject(Object _object) {
/* 254 */     this.object = _object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getNode() {
/* 261 */     return this.node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNode(Node _node) {
/* 270 */     this.node = _node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 280 */     return MessageFormat.format("[node={0},object={1},url={2},line={3},col={4},offset={5}]", new Object[] { getNode(), getObject(), getURL(), String.valueOf(getLineNumber()), String.valueOf(getColumnNumber()), String.valueOf(getOffset()) });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\helpers\ValidationEventLocatorImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */