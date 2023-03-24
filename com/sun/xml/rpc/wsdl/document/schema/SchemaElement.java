/*     */ package com.sun.xml.rpc.wsdl.document.schema;
/*     */ 
/*     */ import com.sun.xml.rpc.util.NullIterator;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchemaElement
/*     */ {
/*     */   private String _nsURI;
/*     */   private String _localName;
/*     */   private List _children;
/*     */   private List _attributes;
/*     */   private Map _nsPrefixes;
/*     */   private SchemaElement _parent;
/*     */   private QName _qname;
/*     */   private Schema _schema;
/*     */   private static final String NEW_NS_PREFIX_BASE = "ns";
/*     */   
/*     */   public SchemaElement() {}
/*     */   
/*     */   public SchemaElement(String localName) {
/*  51 */     this._localName = localName;
/*     */   }
/*     */   
/*     */   public SchemaElement(QName name) {
/*  55 */     this._qname = name;
/*  56 */     this._localName = name.getLocalPart();
/*  57 */     this._nsURI = name.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/*  61 */     return this._nsURI;
/*     */   }
/*     */   
/*     */   public void setNamespaceURI(String s) {
/*  65 */     this._nsURI = s;
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/*  69 */     return this._localName;
/*     */   }
/*     */   
/*     */   public void setLocalName(String s) {
/*  73 */     this._localName = s;
/*     */   }
/*     */   
/*     */   public QName getQName() {
/*  77 */     if (this._qname == null) {
/*  78 */       this._qname = new QName(this._nsURI, this._localName);
/*     */     }
/*  80 */     return this._qname;
/*     */   }
/*     */   
/*     */   public SchemaElement getParent() {
/*  84 */     return this._parent;
/*     */   }
/*     */   
/*     */   public void setParent(SchemaElement e) {
/*  88 */     this._parent = e;
/*     */   }
/*     */   
/*     */   public SchemaElement getRoot() {
/*  92 */     return (this._parent == null) ? this : this._parent.getRoot();
/*     */   }
/*     */   
/*     */   public Schema getSchema() {
/*  96 */     return (this._parent == null) ? this._schema : this._parent.getSchema();
/*     */   }
/*     */   
/*     */   public void setSchema(Schema s) {
/* 100 */     this._schema = s;
/*     */   }
/*     */   
/*     */   public void addChild(SchemaElement e) {
/* 104 */     if (this._children == null) {
/* 105 */       this._children = new ArrayList();
/*     */     }
/*     */     
/* 108 */     this._children.add(e);
/* 109 */     e.setParent(this);
/*     */   }
/*     */   
/*     */   public void insertChildAtTop(SchemaElement e) {
/* 113 */     if (this._children == null) {
/* 114 */       this._children = new ArrayList();
/*     */     }
/*     */     
/* 117 */     this._children.add(0, e);
/* 118 */     e.setParent(this);
/*     */   }
/*     */   
/*     */   public Iterator children() {
/* 122 */     if (this._children == null) {
/* 123 */       return (Iterator)NullIterator.getInstance();
/*     */     }
/* 125 */     return this._children.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAttribute(SchemaAttribute a) {
/* 130 */     if (this._attributes == null) {
/* 131 */       this._attributes = new ArrayList();
/*     */     }
/*     */     
/* 134 */     this._attributes.add(a);
/* 135 */     a.setParent(this);
/* 136 */     a.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAttribute(String name, String value) {
/* 141 */     SchemaAttribute attr = new SchemaAttribute();
/* 142 */     attr.setLocalName(name);
/* 143 */     attr.setValue(value);
/* 144 */     addAttribute(attr);
/*     */   }
/*     */   
/*     */   public void addAttribute(String name, QName value) {
/* 148 */     SchemaAttribute attr = new SchemaAttribute();
/* 149 */     attr.setLocalName(name);
/* 150 */     attr.setValue(value);
/* 151 */     addAttribute(attr);
/*     */   }
/*     */   
/*     */   public Iterator attributes() {
/* 155 */     if (this._attributes == null) {
/* 156 */       return (Iterator)NullIterator.getInstance();
/*     */     }
/* 158 */     return this._attributes.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public SchemaAttribute getAttribute(String localName) {
/* 163 */     if (this._attributes != null) {
/* 164 */       for (Iterator<SchemaAttribute> iter = this._attributes.iterator(); iter.hasNext(); ) {
/* 165 */         SchemaAttribute attr = iter.next();
/* 166 */         if (localName.equals(attr.getLocalName())) {
/* 167 */           return attr;
/*     */         }
/*     */       } 
/*     */     }
/* 171 */     return null;
/*     */   }
/*     */   
/*     */   public String getValueOfMandatoryAttribute(String localName) {
/* 175 */     SchemaAttribute attr = getAttribute(localName);
/* 176 */     if (attr == null) {
/* 177 */       throw new ValidationException("validation.missingRequiredAttribute", new Object[] { localName, this._localName });
/*     */     }
/*     */ 
/*     */     
/* 181 */     return attr.getValue();
/*     */   }
/*     */   
/*     */   public String getValueOfAttributeOrNull(String localName) {
/* 185 */     SchemaAttribute attr = getAttribute(localName);
/* 186 */     if (attr == null) {
/* 187 */       return null;
/*     */     }
/* 189 */     return attr.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getValueOfBooleanAttributeOrDefault(String localName, boolean defaultValue) {
/* 196 */     String stringValue = getValueOfAttributeOrNull(localName);
/* 197 */     if (stringValue == null) {
/* 198 */       return defaultValue;
/*     */     }
/* 200 */     if (stringValue.equals("true") || stringValue.equals("1"))
/* 201 */       return true; 
/* 202 */     if (stringValue.equals("false") || stringValue.equals("0")) {
/* 203 */       return false;
/*     */     }
/* 205 */     throw new ValidationException("validation.invalidAttributeValue", new Object[] { localName, stringValue });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValueOfIntegerAttributeOrDefault(String localName, int defaultValue) {
/* 214 */     String stringValue = getValueOfAttributeOrNull(localName);
/* 215 */     if (stringValue == null) {
/* 216 */       return defaultValue;
/*     */     }
/*     */     try {
/* 219 */       return Integer.parseInt(stringValue);
/* 220 */     } catch (NumberFormatException e) {
/* 221 */       throw new ValidationException("validation.invalidAttributeValue", new Object[] { localName, stringValue });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getValueOfQNameAttributeOrNull(String localName) {
/* 228 */     String stringValue = getValueOfAttributeOrNull(localName);
/* 229 */     if (stringValue == null) {
/* 230 */       return null;
/*     */     }
/* 232 */     String prefix = XmlUtil.getPrefix(stringValue);
/* 233 */     String uri = (prefix == null) ? getURIForPrefix("") : getURIForPrefix(prefix);
/*     */     
/* 235 */     if (uri == null) {
/* 236 */       throw new ValidationException("validation.invalidAttributeValue", new Object[] { localName, stringValue });
/*     */     }
/*     */ 
/*     */     
/* 240 */     return new QName(uri, XmlUtil.getLocalPart(stringValue));
/*     */   }
/*     */   
/*     */   public void addPrefix(String prefix, String uri) {
/* 244 */     if (this._nsPrefixes == null) {
/* 245 */       this._nsPrefixes = new HashMap<Object, Object>();
/*     */     }
/*     */     
/* 248 */     this._nsPrefixes.put(prefix, uri);
/*     */   }
/*     */   
/*     */   public String getURIForPrefix(String prefix) {
/* 252 */     if (this._nsPrefixes != null) {
/* 253 */       String result = (String)this._nsPrefixes.get(prefix);
/* 254 */       if (result != null)
/* 255 */         return result; 
/*     */     } 
/* 257 */     if (this._parent != null) {
/* 258 */       return this._parent.getURIForPrefix(prefix);
/*     */     }
/* 260 */     if (this._schema != null) {
/* 261 */       return this._schema.getURIForPrefix(prefix);
/*     */     }
/*     */     
/* 264 */     return null;
/*     */   }
/*     */   
/*     */   public boolean declaresPrefixes() {
/* 268 */     return (this._nsPrefixes != null);
/*     */   }
/*     */   
/*     */   public Iterator prefixes() {
/* 272 */     if (this._nsPrefixes == null) {
/* 273 */       return (Iterator)NullIterator.getInstance();
/*     */     }
/* 275 */     return this._nsPrefixes.keySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName asQName(String s) {
/* 280 */     String prefix = XmlUtil.getPrefix(s);
/* 281 */     if (prefix == null) {
/* 282 */       prefix = "";
/*     */     }
/* 284 */     String uri = getURIForPrefix(prefix);
/* 285 */     if (uri == null) {
/* 286 */       throw new ValidationException("validation.invalidPrefix", prefix);
/*     */     }
/* 288 */     String localPart = XmlUtil.getLocalPart(s);
/* 289 */     return new QName(uri, localPart);
/*     */   }
/*     */   
/*     */   public String asString(QName name) {
/* 293 */     if (name.getNamespaceURI().equals("")) {
/* 294 */       return name.getLocalPart();
/*     */     }
/*     */     
/* 297 */     for (Iterator<String> iter = prefixes(); iter.hasNext(); ) {
/* 298 */       String str1 = iter.next();
/* 299 */       String uri = getURIForPrefix(str1);
/* 300 */       if (uri.equals(name.getNamespaceURI())) {
/* 301 */         if (str1.equals("")) {
/* 302 */           return name.getLocalPart();
/*     */         }
/* 304 */         return str1 + ":" + name.getLocalPart();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 310 */     if (this._parent != null) {
/* 311 */       return this._parent.asString(name);
/*     */     }
/* 313 */     if (this._schema != null) {
/* 314 */       String result = this._schema.asString(name);
/* 315 */       if (result != null) {
/* 316 */         return result;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 321 */     String prefix = getNewPrefix();
/* 322 */     addPrefix(prefix, name.getNamespaceURI());
/* 323 */     return asString(name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getNewPrefix() {
/* 328 */     String base = "ns";
/* 329 */     int count = 2;
/* 330 */     String prefix = null;
/* 331 */     for (boolean needNewOne = true; needNewOne; count++) {
/* 332 */       prefix = base + Integer.toString(count);
/* 333 */       needNewOne = (getURIForPrefix(prefix) != null);
/*     */     } 
/* 335 */     return prefix;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\schema\SchemaElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */