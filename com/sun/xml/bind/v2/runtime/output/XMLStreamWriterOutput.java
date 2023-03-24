/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class XMLStreamWriterOutput
/*     */   extends XmlOutputAbstractImpl
/*     */ {
/*     */   private final XMLStreamWriter out;
/*     */   
/*     */   public static XmlOutput create(XMLStreamWriter out, JAXBContextImpl context) {
/*  73 */     Class<?> writerClass = out.getClass();
/*  74 */     if (writerClass == FI_STAX_WRITER_CLASS) {
/*     */       try {
/*  76 */         return FI_OUTPUT_CTOR.newInstance(new Object[] { out, context });
/*  77 */       } catch (Exception e) {}
/*     */     }
/*     */     
/*  80 */     if (STAXEX_WRITER_CLASS != null && STAXEX_WRITER_CLASS.isAssignableFrom(writerClass)) {
/*     */       try {
/*  82 */         return STAXEX_OUTPUT_CTOR.newInstance(new Object[] { out });
/*  83 */       } catch (Exception e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  88 */     return new XMLStreamWriterOutput(out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   protected final char[] buf = new char[256];
/*     */   
/*     */   protected XMLStreamWriterOutput(XMLStreamWriter out) {
/*  97 */     this.out = out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws IOException, SAXException, XMLStreamException {
/* 103 */     super.startDocument(serializer, fragment, nsUriIndex2prefixIndex, nsContext);
/* 104 */     if (!fragment) {
/* 105 */       this.out.writeStartDocument();
/*     */     }
/*     */   }
/*     */   
/*     */   public void endDocument(boolean fragment) throws IOException, SAXException, XMLStreamException {
/* 110 */     if (!fragment) {
/* 111 */       this.out.writeEndDocument();
/* 112 */       this.out.flush();
/*     */     } 
/* 114 */     super.endDocument(fragment);
/*     */   }
/*     */   
/*     */   public void beginStartTag(int prefix, String localName) throws IOException, XMLStreamException {
/* 118 */     this.out.writeStartElement(this.nsContext.getPrefix(prefix), localName, this.nsContext.getNamespaceURI(prefix));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     NamespaceContextImpl.Element nse = this.nsContext.getCurrent();
/* 124 */     if (nse.count() > 0)
/* 125 */       for (int i = nse.count() - 1; i >= 0; i--) {
/* 126 */         String uri = nse.getNsUri(i);
/* 127 */         if (uri.length() != 0 || nse.getBase() != 1)
/*     */         {
/* 129 */           this.out.writeNamespace(nse.getPrefix(i), uri);
/*     */         }
/*     */       }  
/*     */   }
/*     */   
/*     */   public void attribute(int prefix, String localName, String value) throws IOException, XMLStreamException {
/* 135 */     if (prefix == -1) {
/* 136 */       this.out.writeAttribute(localName, value);
/*     */     } else {
/* 138 */       this.out.writeAttribute(this.nsContext.getPrefix(prefix), this.nsContext.getNamespaceURI(prefix), localName, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endStartTag() throws IOException, SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void endTag(int prefix, String localName) throws IOException, SAXException, XMLStreamException {
/* 149 */     this.out.writeEndElement();
/*     */   }
/*     */   
/*     */   public void text(String value, boolean needsSeparatingWhitespace) throws IOException, SAXException, XMLStreamException {
/* 153 */     if (needsSeparatingWhitespace)
/* 154 */       this.out.writeCharacters(" "); 
/* 155 */     this.out.writeCharacters(value);
/*     */   }
/*     */   
/*     */   public void text(Pcdata value, boolean needsSeparatingWhitespace) throws IOException, SAXException, XMLStreamException {
/* 159 */     if (needsSeparatingWhitespace) {
/* 160 */       this.out.writeCharacters(" ");
/*     */     }
/* 162 */     int len = value.length();
/* 163 */     if (len < this.buf.length) {
/* 164 */       value.writeTo(this.buf, 0);
/* 165 */       this.out.writeCharacters(this.buf, 0, len);
/*     */     } else {
/* 167 */       this.out.writeCharacters(value.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   private static final Class FI_STAX_WRITER_CLASS = initFIStAXWriterClass();
/* 176 */   private static final Constructor<? extends XmlOutput> FI_OUTPUT_CTOR = initFastInfosetOutputClass();
/*     */   
/*     */   private static Class initFIStAXWriterClass() {
/*     */     try {
/* 180 */       ClassLoader loader = getClassLoader();
/* 181 */       Class<?> llfisw = Class.forName("org.jvnet.fastinfoset.stax.LowLevelFastInfosetStreamWriter", true, loader);
/* 182 */       Class<?> sds = loader.loadClass("com.sun.xml.fastinfoset.stax.StAXDocumentSerializer");
/*     */       
/* 184 */       if (llfisw.isAssignableFrom(sds)) {
/* 185 */         return sds;
/*     */       }
/* 187 */       return null;
/* 188 */     } catch (Throwable e) {
/* 189 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Constructor<? extends XmlOutput> initFastInfosetOutputClass() {
/*     */     try {
/* 195 */       if (FI_STAX_WRITER_CLASS == null)
/* 196 */         return null; 
/* 197 */       ClassLoader loader = getClassLoader();
/* 198 */       Class<?> c = Class.forName("com.sun.xml.bind.v2.runtime.output.FastInfosetStreamWriterOutput", true, loader);
/* 199 */       return (Constructor)c.getConstructor(new Class[] { FI_STAX_WRITER_CLASS, JAXBContextImpl.class });
/* 200 */     } catch (Throwable e) {
/* 201 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   private static final Class STAXEX_WRITER_CLASS = initStAXExWriterClass();
/* 209 */   private static final Constructor<? extends XmlOutput> STAXEX_OUTPUT_CTOR = initStAXExOutputClass();
/*     */   
/*     */   private static Class initStAXExWriterClass() {
/*     */     try {
/* 213 */       ClassLoader loader = getClassLoader();
/* 214 */       return Class.forName("org.jvnet.staxex.XMLStreamWriterEx", true, loader);
/* 215 */     } catch (Throwable e) {
/* 216 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Constructor<? extends XmlOutput> initStAXExOutputClass() {
/*     */     try {
/* 222 */       ClassLoader loader = getClassLoader();
/* 223 */       Class<?> c = Class.forName("com.sun.xml.bind.v2.runtime.output.StAXExStreamWriterOutput", true, loader);
/* 224 */       return (Constructor)c.getConstructor(new Class[] { STAXEX_WRITER_CLASS });
/* 225 */     } catch (Throwable e) {
/* 226 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ClassLoader getClassLoader() {
/* 231 */     ClassLoader cl = SecureLoader.getClassClassLoader(UnmarshallerImpl.class);
/* 232 */     if (cl == null) {
/* 233 */       cl = SecureLoader.getContextClassLoader();
/*     */     }
/* 235 */     return cl;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\XMLStreamWriterOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */