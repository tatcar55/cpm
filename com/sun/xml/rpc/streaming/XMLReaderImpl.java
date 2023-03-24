/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import com.sun.xml.rpc.sp.AttributesEx;
/*     */ import com.sun.xml.rpc.sp.ParseException;
/*     */ import com.sun.xml.rpc.sp.Parser2;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLReaderImpl
/*     */   extends XMLReaderBase
/*     */ {
/*     */   private int _state;
/*     */   private QName _name;
/*     */   private InputStream _stream;
/*     */   private AttributesAdapter _attributeAdapter;
/*     */   private ElementIdStack _elementIds;
/*     */   private int _elementId;
/*     */   private Parser2 _parser;
/*     */   private static final int DOC_END = -1;
/*     */   private static final int DOC_START = -2;
/*     */   private static final int EMPTY = -3;
/*     */   private static final int EXCEPTION = -4;
/*     */   
/*     */   public XMLReaderImpl(InputSource source) {
/*  55 */     this(source, false);
/*     */   }
/*     */   
/*     */   public XMLReaderImpl(InputSource source, boolean rejectDTDs) {
/*  59 */     this._state = 0;
/*     */     
/*  61 */     this._stream = source.getByteStream();
/*     */     
/*  63 */     this._parser = new Parser2(this._stream, true, true, rejectDTDs);
/*  64 */     this._elementIds = new ElementIdStack();
/*  65 */     this._attributeAdapter = new AttributesAdapter();
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     try {
/*  70 */       this._state = 5;
/*     */       
/*  72 */       this._stream.close();
/*  73 */     } catch (IOException e) {
/*  74 */       throw new XMLReaderException("xmlreader.ioException", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getState() {
/*  81 */     return this._state;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  85 */     if (this._name == null) {
/*  86 */       this._name = new QName(getURI(), getLocalName());
/*     */     }
/*  88 */     return this._name;
/*     */   }
/*     */   
/*     */   public String getURI() {
/*  92 */     return this._parser.getCurURI();
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/*  96 */     return this._parser.getCurName();
/*     */   }
/*     */   
/*     */   public Attributes getAttributes() {
/* 100 */     this._attributeAdapter.setTarget(this._parser.getAttributes());
/* 101 */     return this._attributeAdapter;
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 105 */     return this._parser.getCurValue();
/*     */   }
/*     */   
/*     */   public int getElementId() {
/* 109 */     return this._elementId;
/*     */   }
/*     */   
/*     */   public int getLineNumber() {
/* 113 */     return this._parser.getLineNumber();
/*     */   }
/*     */   
/*     */   public String getURI(String prefix) {
/* 117 */     return this._parser.getNamespaceSupport().getURI(prefix);
/*     */   }
/*     */   
/*     */   public Iterator getPrefixes() {
/* 121 */     return this._parser.getNamespaceSupport().getPrefixes();
/*     */   }
/*     */   
/*     */   public int next() {
/* 125 */     if (this._state == 5) {
/* 126 */       return 5;
/*     */     }
/*     */     
/* 129 */     this._name = null;
/*     */     
/*     */     try {
/* 132 */       this._state = this._parser.parse();
/* 133 */       if (this._state == -1) {
/* 134 */         this._state = 5;
/*     */       }
/* 136 */     } catch (ParseException e) {
/* 137 */       throw new XMLReaderException("xmlreader.parseException", new LocalizableExceptionAdapter(e));
/*     */     
/*     */     }
/* 140 */     catch (IOException e) {
/* 141 */       throw new XMLReaderException("xmlreader.ioException", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 146 */     switch (this._state) {
/*     */       case 1:
/* 148 */         this._elementId = this._elementIds.pushNext();
/*     */       
/*     */       case 2:
/* 151 */         this._elementId = this._elementIds.pop();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/* 163 */         return this._state;
/*     */     } 
/*     */     throw new XMLReaderException("xmlreader.illegalStateEncountered", Integer.toString(this._state));
/*     */   } public XMLReader recordElement() {
/* 167 */     return new RecordedXMLReader(this, this._parser.getNamespaceSupport());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void skipElement(int elementId) {
/* 173 */     while (this._state != 5 && (this._state != 2 || this._elementId != elementId))
/*     */     {
/* 175 */       next();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class AttributesAdapter
/*     */     implements Attributes
/*     */   {
/*     */     private AttributesEx _attr;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static final String XMLNS_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTarget(AttributesEx attr) {
/* 197 */       this._attr = attr;
/*     */     }
/*     */     
/*     */     public int getLength() {
/* 201 */       return this._attr.getLength();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isNamespaceDeclaration(int index) {
/* 207 */       return (this._attr.getURI(index) == "http://www.w3.org/2000/xmlns/");
/*     */     }
/*     */     
/*     */     public QName getName(int index) {
/* 211 */       return new QName(getURI(index), getLocalName(index));
/*     */     }
/*     */     
/*     */     public String getURI(int index) {
/* 215 */       return this._attr.getURI(index);
/*     */     }
/*     */     
/*     */     public String getLocalName(int index) {
/* 219 */       return this._attr.getLocalName(index);
/*     */     }
/*     */     
/*     */     public String getPrefix(int index) {
/* 223 */       String qname = this._attr.getQName(index);
/* 224 */       if (qname == null) {
/* 225 */         return null;
/*     */       }
/* 227 */       return XmlUtil.getPrefix(qname);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getValue(int index) {
/* 232 */       return this._attr.getValue(index);
/*     */     }
/*     */     
/*     */     public int getIndex(QName name) {
/* 236 */       return this._attr.getIndex(name.getNamespaceURI(), name.getLocalPart());
/*     */     }
/*     */     
/*     */     public int getIndex(String uri, String localName) {
/* 240 */       return this._attr.getIndex(uri, localName);
/*     */     }
/*     */     
/*     */     public int getIndex(String localName) {
/* 244 */       return this._attr.getIndex(localName);
/*     */     }
/*     */     
/*     */     public String getValue(QName name) {
/* 248 */       return this._attr.getValue(name.getNamespaceURI(), name.getLocalPart());
/*     */     }
/*     */     
/*     */     public String getValue(String uri, String localName) {
/* 252 */       return this._attr.getValue(uri, localName);
/*     */     }
/*     */     
/*     */     public String getValue(String localName) {
/* 256 */       return this._attr.getValue(localName);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 260 */       StringBuffer attributes = new StringBuffer();
/* 261 */       for (int i = 0; i < getLength(); i++) {
/* 262 */         if (i != 0) {
/* 263 */           attributes.append("\n");
/*     */         }
/* 265 */         attributes.append(getURI(i) + ":" + getLocalName(i) + " = " + getValue(i));
/*     */       } 
/*     */       
/* 268 */       return attributes.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\XMLReaderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */