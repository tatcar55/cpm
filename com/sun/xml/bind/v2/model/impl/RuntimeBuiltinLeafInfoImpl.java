/*      */ package com.sun.xml.bind.v2.model.impl;
/*      */ 
/*      */ import com.sun.istack.ByteArrayDataSource;
/*      */ import com.sun.xml.bind.DatatypeConverterImpl;
/*      */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*      */ import com.sun.xml.bind.api.AccessorException;
/*      */ import com.sun.xml.bind.v2.TODO;
/*      */ import com.sun.xml.bind.v2.model.runtime.RuntimeBuiltinLeafInfo;
/*      */ import com.sun.xml.bind.v2.runtime.Name;
/*      */ import com.sun.xml.bind.v2.runtime.Transducer;
/*      */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*      */ import com.sun.xml.bind.v2.runtime.output.Pcdata;
/*      */ import com.sun.xml.bind.v2.runtime.unmarshaller.Base64Data;
/*      */ import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*      */ import com.sun.xml.bind.v2.util.ByteArrayOutputStreamEx;
/*      */ import com.sun.xml.bind.v2.util.DataSourceSource;
/*      */ import java.awt.Component;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.MediaTracker;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Type;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.UUID;
/*      */ import javax.activation.DataHandler;
/*      */ import javax.activation.DataSource;
/*      */ import javax.activation.MimeType;
/*      */ import javax.activation.MimeTypeParseException;
/*      */ import javax.imageio.ImageIO;
/*      */ import javax.imageio.ImageWriter;
/*      */ import javax.imageio.stream.ImageOutputStream;
/*      */ import javax.xml.bind.MarshalException;
/*      */ import javax.xml.bind.helpers.ValidationEventImpl;
/*      */ import javax.xml.datatype.DatatypeConfigurationException;
/*      */ import javax.xml.datatype.DatatypeConstants;
/*      */ import javax.xml.datatype.DatatypeFactory;
/*      */ import javax.xml.datatype.Duration;
/*      */ import javax.xml.datatype.XMLGregorianCalendar;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class RuntimeBuiltinLeafInfoImpl<T>
/*      */   extends BuiltinLeafInfoImpl<Type, Class>
/*      */   implements RuntimeBuiltinLeafInfo, Transducer<T>
/*      */ {
/*      */   private RuntimeBuiltinLeafInfoImpl(Class type, QName... typeNames) {
/*  124 */     super(type, typeNames);
/*  125 */     LEAVES.put(type, this);
/*      */   }
/*      */   
/*      */   public final Class getClazz() {
/*  129 */     return (Class)getType();
/*      */   }
/*      */ 
/*      */   
/*      */   public final Transducer getTransducer() {
/*  134 */     return this;
/*      */   }
/*      */   
/*      */   public boolean useNamespace() {
/*  138 */     return false;
/*      */   }
/*      */   
/*      */   public final boolean isDefault() {
/*  142 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void declareNamespace(T o, XMLSerializer w) throws AccessorException {}
/*      */   
/*      */   public QName getTypeName(T instance) {
/*  149 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class StringImpl<T>
/*      */     extends RuntimeBuiltinLeafInfoImpl<T>
/*      */   {
/*      */     protected StringImpl(Class type, QName... typeNames) {
/*  157 */       super(type, typeNames);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void writeText(XMLSerializer w, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/*  163 */       w.text(print(o), fieldName);
/*      */     }
/*      */     
/*      */     public void writeLeafElement(XMLSerializer w, Name tagName, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/*  167 */       w.leafElement(tagName, print(o), fieldName);
/*      */     }
/*      */     
/*      */     public abstract String print(T param1T) throws AccessorException;
/*      */   }
/*      */   
/*      */   private static abstract class PcdataImpl<T>
/*      */     extends RuntimeBuiltinLeafInfoImpl<T> {
/*      */     protected PcdataImpl(Class type, QName... typeNames) {
/*  176 */       super(type, typeNames);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public final void writeText(XMLSerializer w, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/*  182 */       w.text(print(o), fieldName);
/*      */     }
/*      */     
/*      */     public final void writeLeafElement(XMLSerializer w, Name tagName, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/*  186 */       w.leafElement(tagName, print(o), fieldName);
/*      */     }
/*      */ 
/*      */     
/*      */     public abstract Pcdata print(T param1T) throws AccessorException;
/*      */   }
/*      */ 
/*      */   
/*  194 */   public static final Map<Type, RuntimeBuiltinLeafInfoImpl<?>> LEAVES = new HashMap<Type, RuntimeBuiltinLeafInfoImpl<?>>(); public static final RuntimeBuiltinLeafInfoImpl<String> STRING;
/*      */   
/*      */   private static QName createXS(String typeName) {
/*  197 */     return new QName("http://www.w3.org/2001/XMLSchema", typeName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String DATE = "date";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final List<RuntimeBuiltinLeafInfoImpl<?>> builtinBeanInfos;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String MAP_ANYURI_TO_URI = "mapAnyUriToUri";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  217 */     (new QName[10])[0] = createXS("string"); (new QName[10])[1] = createXS("anySimpleType"); (new QName[10])[2] = createXS("normalizedString"); (new QName[10])[3] = createXS("anyURI"); (new QName[10])[4] = createXS("token"); (new QName[10])[5] = createXS("language"); (new QName[10])[6] = createXS("Name"); (new QName[10])[7] = createXS("NCName"); (new QName[10])[8] = createXS("NMTOKEN"); (new QName[10])[9] = createXS("ENTITY"); (new QName[9])[0] = createXS("string"); (new QName[9])[1] = createXS("anySimpleType"); (new QName[9])[2] = createXS("normalizedString"); (new QName[9])[3] = createXS("token"); (new QName[9])[4] = createXS("language"); (new QName[9])[5] = createXS("Name"); (new QName[9])[6] = createXS("NCName"); (new QName[9])[7] = createXS("NMTOKEN"); (new QName[9])[8] = createXS("ENTITY"); QName[] qnames = (System.getProperty("mapAnyUriToUri") == null) ? new QName[10] : new QName[9];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  240 */     STRING = new StringImplImpl(String.class, qnames);
/*      */     
/*  242 */     ArrayList<RuntimeBuiltinLeafInfoImpl<?>> secondaryList = new ArrayList<RuntimeBuiltinLeafInfoImpl<?>>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  260 */     secondaryList.add(new StringImpl<Character>(Character.class, new QName[] { createXS("unsignedShort") })
/*      */         {
/*      */           public Character parse(CharSequence text)
/*      */           {
/*  264 */             return Character.valueOf((char)DatatypeConverterImpl._parseInt(text));
/*      */           }
/*      */           public String print(Character v) {
/*  267 */             return Integer.toString(v.charValue());
/*      */           }
/*      */         });
/*  270 */     secondaryList.add(new StringImpl<Calendar>(Calendar.class, new QName[] { DatatypeConstants.DATETIME })
/*      */         {
/*      */           public Calendar parse(CharSequence text) {
/*  273 */             return DatatypeConverterImpl._parseDateTime(text.toString());
/*      */           }
/*      */           public String print(Calendar v) {
/*  276 */             return DatatypeConverterImpl._printDateTime(v);
/*      */           }
/*      */         });
/*  279 */     secondaryList.add(new StringImpl<GregorianCalendar>(GregorianCalendar.class, new QName[] { DatatypeConstants.DATETIME })
/*      */         {
/*      */           public GregorianCalendar parse(CharSequence text) {
/*  282 */             return DatatypeConverterImpl._parseDateTime(text.toString());
/*      */           }
/*      */           public String print(GregorianCalendar v) {
/*  285 */             return DatatypeConverterImpl._printDateTime(v);
/*      */           }
/*      */         });
/*  288 */     secondaryList.add(new StringImpl<Date>(Date.class, new QName[] { DatatypeConstants.DATETIME })
/*      */         {
/*      */           public Date parse(CharSequence text) {
/*  291 */             return DatatypeConverterImpl._parseDateTime(text.toString()).getTime();
/*      */           }
/*      */           public String print(Date v) {
/*  294 */             XMLSerializer xs = XMLSerializer.getInstance();
/*  295 */             QName type = xs.getSchemaType();
/*  296 */             GregorianCalendar cal = new GregorianCalendar(0, 0, 0);
/*  297 */             cal.setTime(v);
/*  298 */             if (type != null && "http://www.w3.org/2001/XMLSchema".equals(type.getNamespaceURI()) && "date".equals(type.getLocalPart()))
/*      */             {
/*  300 */               return DatatypeConverterImpl._printDate(cal);
/*      */             }
/*  302 */             return DatatypeConverterImpl._printDateTime(cal);
/*      */           }
/*      */         });
/*      */     
/*  306 */     secondaryList.add(new StringImpl<File>(File.class, new QName[] { createXS("string") })
/*      */         {
/*      */           public File parse(CharSequence text) {
/*  309 */             return new File(WhiteSpaceProcessor.trim(text).toString());
/*      */           }
/*      */           public String print(File v) {
/*  312 */             return v.getPath();
/*      */           }
/*      */         });
/*  315 */     secondaryList.add(new StringImpl<URL>(URL.class, new QName[] { createXS("anyURI") })
/*      */         {
/*      */           public URL parse(CharSequence text) throws SAXException {
/*  318 */             TODO.checkSpec("JSR222 Issue #42");
/*      */             try {
/*  320 */               return new URL(WhiteSpaceProcessor.trim(text).toString());
/*  321 */             } catch (MalformedURLException e) {
/*  322 */               UnmarshallingContext.getInstance().handleError(e);
/*  323 */               return null;
/*      */             } 
/*      */           }
/*      */           public String print(URL v) {
/*  327 */             return v.toExternalForm();
/*      */           }
/*      */         });
/*  330 */     if (System.getProperty("mapAnyUriToUri") == null) {
/*  331 */       secondaryList.add(new StringImpl<URI>(URI.class, new QName[] { createXS("string") })
/*      */           {
/*      */             public URI parse(CharSequence text) throws SAXException {
/*      */               try {
/*  335 */                 return new URI(text.toString());
/*  336 */               } catch (URISyntaxException e) {
/*  337 */                 UnmarshallingContext.getInstance().handleError(e);
/*  338 */                 return null;
/*      */               } 
/*      */             }
/*      */             
/*      */             public String print(URI v) {
/*  343 */               return v.toString();
/*      */             }
/*      */           });
/*      */     }
/*  347 */     secondaryList.add(new StringImpl<Class>(Class.class, new QName[] { createXS("string") })
/*      */         {
/*      */           public Class parse(CharSequence text) throws SAXException {
/*  350 */             TODO.checkSpec("JSR222 Issue #42");
/*      */             try {
/*  352 */               String name = WhiteSpaceProcessor.trim(text).toString();
/*  353 */               ClassLoader cl = (UnmarshallingContext.getInstance()).classLoader;
/*  354 */               if (cl == null) {
/*  355 */                 cl = Thread.currentThread().getContextClassLoader();
/*      */               }
/*  357 */               if (cl != null) {
/*  358 */                 return cl.loadClass(name);
/*      */               }
/*  360 */               return Class.forName(name);
/*  361 */             } catch (ClassNotFoundException e) {
/*  362 */               UnmarshallingContext.getInstance().handleError(e);
/*  363 */               return null;
/*      */             } 
/*      */           }
/*      */           public String print(Class v) {
/*  367 */             return v.getName();
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  375 */     secondaryList.add(new PcdataImpl<Image>(Image.class, new QName[] { createXS("base64Binary") })
/*      */         {
/*      */           public Image parse(CharSequence text) throws SAXException {
/*      */             try {
/*      */               InputStream is;
/*  380 */               if (text instanceof Base64Data) {
/*  381 */                 is = ((Base64Data)text).getInputStream();
/*      */               } else {
/*  383 */                 is = new ByteArrayInputStream(RuntimeBuiltinLeafInfoImpl.decodeBase64(text));
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               try {
/*  390 */                 return ImageIO.read(is);
/*      */               } finally {
/*  392 */                 is.close();
/*      */               } 
/*  394 */             } catch (IOException e) {
/*  395 */               UnmarshallingContext.getInstance().handleError(e);
/*  396 */               return null;
/*      */             } 
/*      */           }
/*      */           
/*      */           private BufferedImage convertToBufferedImage(Image image) throws IOException {
/*  401 */             if (image instanceof BufferedImage) {
/*  402 */               return (BufferedImage)image;
/*      */             }
/*      */             
/*  405 */             MediaTracker tracker = new MediaTracker(new Component() {  });
/*  406 */             tracker.addImage(image, 0);
/*      */             try {
/*  408 */               tracker.waitForAll();
/*  409 */             } catch (InterruptedException e) {
/*  410 */               throw new IOException(e.getMessage());
/*      */             } 
/*  412 */             BufferedImage bufImage = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  417 */             Graphics g = bufImage.createGraphics();
/*  418 */             g.drawImage(image, 0, 0, null);
/*  419 */             return bufImage;
/*      */           }
/*      */ 
/*      */           
/*      */           public Base64Data print(Image v) {
/*  424 */             ByteArrayOutputStreamEx imageData = new ByteArrayOutputStreamEx();
/*  425 */             XMLSerializer xs = XMLSerializer.getInstance();
/*      */             
/*  427 */             String mimeType = xs.getXMIMEContentType();
/*  428 */             if (mimeType == null || mimeType.startsWith("image/*"))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  434 */               mimeType = "image/png";
/*      */             }
/*      */             try {
/*  437 */               Iterator<ImageWriter> itr = ImageIO.getImageWritersByMIMEType(mimeType);
/*  438 */               if (itr.hasNext()) {
/*  439 */                 ImageWriter w = itr.next();
/*  440 */                 ImageOutputStream os = ImageIO.createImageOutputStream(imageData);
/*  441 */                 w.setOutput(os);
/*  442 */                 w.write(convertToBufferedImage(v));
/*  443 */                 os.close();
/*  444 */                 w.dispose();
/*      */               } else {
/*      */                 
/*  447 */                 xs.handleEvent(new ValidationEventImpl(1, Messages.NO_IMAGE_WRITER.format(new Object[] { mimeType }, ), xs.getCurrentLocation(null)));
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  452 */                 throw new RuntimeException("no encoder for MIME type " + mimeType);
/*      */               } 
/*  454 */             } catch (IOException e) {
/*  455 */               xs.handleError(e);
/*      */               
/*  457 */               throw new RuntimeException(e);
/*      */             } 
/*  459 */             Base64Data bd = new Base64Data();
/*  460 */             imageData.set(bd, mimeType);
/*  461 */             return bd;
/*      */           }
/*      */         });
/*  464 */     secondaryList.add(new PcdataImpl<DataHandler>(DataHandler.class, new QName[] { createXS("base64Binary") })
/*      */         {
/*      */           public DataHandler parse(CharSequence text) {
/*  467 */             if (text instanceof Base64Data) {
/*  468 */               return ((Base64Data)text).getDataHandler();
/*      */             }
/*  470 */             return new DataHandler((DataSource)new ByteArrayDataSource(RuntimeBuiltinLeafInfoImpl.decodeBase64(text), UnmarshallingContext.getInstance().getXMIMEContentType()));
/*      */           }
/*      */ 
/*      */           
/*      */           public Base64Data print(DataHandler v) {
/*  475 */             Base64Data bd = new Base64Data();
/*  476 */             bd.set(v);
/*  477 */             return bd;
/*      */           }
/*      */         });
/*  480 */     secondaryList.add(new PcdataImpl<Source>(Source.class, new QName[] { createXS("base64Binary") })
/*      */         {
/*      */           public Source parse(CharSequence text) throws SAXException {
/*      */             try {
/*  484 */               if (text instanceof Base64Data) {
/*  485 */                 return (Source)new DataSourceSource(((Base64Data)text).getDataHandler());
/*      */               }
/*  487 */               return (Source)new DataSourceSource((DataSource)new ByteArrayDataSource(RuntimeBuiltinLeafInfoImpl.decodeBase64(text), UnmarshallingContext.getInstance().getXMIMEContentType()));
/*      */             }
/*  489 */             catch (MimeTypeParseException e) {
/*  490 */               UnmarshallingContext.getInstance().handleError(e);
/*  491 */               return null;
/*      */             } 
/*      */           }
/*      */           
/*      */           public Base64Data print(Source v) {
/*  496 */             XMLSerializer xs = XMLSerializer.getInstance();
/*  497 */             Base64Data bd = new Base64Data();
/*      */             
/*  499 */             String contentType = xs.getXMIMEContentType();
/*  500 */             MimeType mt = null;
/*  501 */             if (contentType != null) {
/*      */               try {
/*  503 */                 mt = new MimeType(contentType);
/*  504 */               } catch (MimeTypeParseException e) {
/*  505 */                 xs.handleError(e);
/*      */               } 
/*      */             }
/*      */             
/*  509 */             if (v instanceof DataSourceSource) {
/*      */ 
/*      */               
/*  512 */               DataSource ds = ((DataSourceSource)v).getDataSource();
/*      */               
/*  514 */               String dsct = ds.getContentType();
/*  515 */               if (dsct != null && (contentType == null || contentType.equals(dsct))) {
/*  516 */                 bd.set(new DataHandler(ds));
/*  517 */                 return bd;
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  524 */             String charset = null;
/*  525 */             if (mt != null)
/*  526 */               charset = mt.getParameter("charset"); 
/*  527 */             if (charset == null) {
/*  528 */               charset = "UTF-8";
/*      */             }
/*      */             try {
/*  531 */               ByteArrayOutputStreamEx baos = new ByteArrayOutputStreamEx();
/*  532 */               Transformer tr = xs.getIdentityTransformer();
/*  533 */               String defaultEncoding = tr.getOutputProperty("encoding");
/*  534 */               tr.setOutputProperty("encoding", charset);
/*  535 */               tr.transform(v, new StreamResult(new OutputStreamWriter((OutputStream)baos, charset)));
/*  536 */               tr.setOutputProperty("encoding", defaultEncoding);
/*  537 */               baos.set(bd, "application/xml; charset=" + charset);
/*  538 */               return bd;
/*  539 */             } catch (TransformerException e) {
/*      */               
/*  541 */               xs.handleError(e);
/*  542 */             } catch (UnsupportedEncodingException e) {
/*  543 */               xs.handleError(e);
/*      */             } 
/*      */ 
/*      */             
/*  547 */             bd.set(new byte[0], "application/xml");
/*  548 */             return bd;
/*      */           }
/*      */         });
/*  551 */     secondaryList.add(new StringImpl<XMLGregorianCalendar>(XMLGregorianCalendar.class, new QName[] { createXS("anySimpleType"), DatatypeConstants.DATE, DatatypeConstants.DATETIME, DatatypeConstants.TIME, DatatypeConstants.GMONTH, DatatypeConstants.GDAY, DatatypeConstants.GYEAR, DatatypeConstants.GYEARMONTH, DatatypeConstants.GMONTHDAY })
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public String print(XMLGregorianCalendar cal)
/*      */           {
/*  564 */             XMLSerializer xs = XMLSerializer.getInstance();
/*      */             
/*  566 */             QName type = xs.getSchemaType();
/*  567 */             if (type != null) {
/*      */               try {
/*  569 */                 RuntimeBuiltinLeafInfoImpl.checkXmlGregorianCalendarFieldRef(type, cal);
/*  570 */                 String format = (String)RuntimeBuiltinLeafInfoImpl.xmlGregorianCalendarFormatString.get(type);
/*  571 */                 if (format != null) {
/*  572 */                   return format(format, cal);
/*      */                 }
/*  574 */               } catch (MarshalException e) {
/*      */                 
/*  576 */                 xs.handleEvent(new ValidationEventImpl(0, e.getMessage(), xs.getCurrentLocation(null)));
/*      */                 
/*  578 */                 return "";
/*      */               } 
/*      */             }
/*  581 */             return cal.toXMLFormat();
/*      */           }
/*      */           
/*      */           public XMLGregorianCalendar parse(CharSequence lexical) throws SAXException {
/*      */             try {
/*  586 */               return RuntimeBuiltinLeafInfoImpl.datatypeFactory.newXMLGregorianCalendar(lexical.toString().trim());
/*  587 */             } catch (Exception e) {
/*  588 */               UnmarshallingContext.getInstance().handleError(e);
/*  589 */               return null;
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           private String format(String format, XMLGregorianCalendar value) {
/*  595 */             StringBuilder buf = new StringBuilder();
/*  596 */             int fidx = 0, flen = format.length();
/*      */             
/*  598 */             while (fidx < flen) {
/*  599 */               int offset; char fch = format.charAt(fidx++);
/*  600 */               if (fch != '%') {
/*  601 */                 buf.append(fch);
/*      */                 
/*      */                 continue;
/*      */               } 
/*  605 */               switch (format.charAt(fidx++)) {
/*      */                 case 'Y':
/*  607 */                   printNumber(buf, value.getEonAndYear(), 4);
/*      */                   continue;
/*      */                 case 'M':
/*  610 */                   printNumber(buf, value.getMonth(), 2);
/*      */                   continue;
/*      */                 case 'D':
/*  613 */                   printNumber(buf, value.getDay(), 2);
/*      */                   continue;
/*      */                 case 'h':
/*  616 */                   printNumber(buf, value.getHour(), 2);
/*      */                   continue;
/*      */                 case 'm':
/*  619 */                   printNumber(buf, value.getMinute(), 2);
/*      */                   continue;
/*      */                 case 's':
/*  622 */                   printNumber(buf, value.getSecond(), 2);
/*  623 */                   if (value.getFractionalSecond() != null) {
/*  624 */                     String frac = value.getFractionalSecond().toPlainString();
/*      */                     
/*  626 */                     buf.append(frac.substring(1, frac.length()));
/*      */                   } 
/*      */                   continue;
/*      */                 case 'z':
/*  630 */                   offset = value.getTimezone();
/*  631 */                   if (offset == 0) {
/*  632 */                     buf.append('Z'); continue;
/*  633 */                   }  if (offset != Integer.MIN_VALUE) {
/*  634 */                     if (offset < 0) {
/*  635 */                       buf.append('-');
/*  636 */                       offset *= -1;
/*      */                     } else {
/*  638 */                       buf.append('+');
/*      */                     } 
/*  640 */                     printNumber(buf, offset / 60, 2);
/*  641 */                     buf.append(':');
/*  642 */                     printNumber(buf, offset % 60, 2);
/*      */                   } 
/*      */                   continue;
/*      */               } 
/*  646 */               throw new InternalError();
/*      */             } 
/*      */ 
/*      */             
/*  650 */             return buf.toString();
/*      */           }
/*      */           private void printNumber(StringBuilder out, BigInteger number, int nDigits) {
/*  653 */             String s = number.toString();
/*  654 */             for (int i = s.length(); i < nDigits; i++)
/*  655 */               out.append('0'); 
/*  656 */             out.append(s);
/*      */           }
/*      */           private void printNumber(StringBuilder out, int number, int nDigits) {
/*  659 */             String s = String.valueOf(number);
/*  660 */             for (int i = s.length(); i < nDigits; i++)
/*  661 */               out.append('0'); 
/*  662 */             out.append(s);
/*      */           }
/*      */           
/*      */           public QName getTypeName(XMLGregorianCalendar cal) {
/*  666 */             return cal.getXMLSchemaType();
/*      */           }
/*      */         });
/*      */     
/*  670 */     ArrayList<RuntimeBuiltinLeafInfoImpl<?>> primaryList = new ArrayList<RuntimeBuiltinLeafInfoImpl<?>>();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  675 */     primaryList.add(STRING);
/*  676 */     primaryList.add(new StringImpl<Boolean>(Boolean.class, new QName[] { createXS("boolean") })
/*      */         {
/*      */           public Boolean parse(CharSequence text)
/*      */           {
/*  680 */             return DatatypeConverterImpl._parseBoolean(text);
/*      */           }
/*      */           
/*      */           public String print(Boolean v) {
/*  684 */             return v.toString();
/*      */           }
/*      */         });
/*  687 */     primaryList.add(new PcdataImpl<byte[]>(byte[].class, new QName[] { createXS("base64Binary"), createXS("hexBinary") })
/*      */         {
/*      */           
/*      */           public byte[] parse(CharSequence text)
/*      */           {
/*  692 */             return RuntimeBuiltinLeafInfoImpl.decodeBase64(text);
/*      */           }
/*      */           
/*      */           public Base64Data print(byte[] v) {
/*  696 */             XMLSerializer w = XMLSerializer.getInstance();
/*  697 */             Base64Data bd = new Base64Data();
/*  698 */             String mimeType = w.getXMIMEContentType();
/*  699 */             bd.set(v, mimeType);
/*  700 */             return bd;
/*      */           }
/*      */         });
/*  703 */     primaryList.add(new StringImpl<Byte>(Byte.class, new QName[] { createXS("byte") })
/*      */         {
/*      */           public Byte parse(CharSequence text)
/*      */           {
/*  707 */             return Byte.valueOf(DatatypeConverterImpl._parseByte(text));
/*      */           }
/*      */           
/*      */           public String print(Byte v) {
/*  711 */             return DatatypeConverterImpl._printByte(v.byteValue());
/*      */           }
/*      */         });
/*  714 */     primaryList.add(new StringImpl<Short>(Short.class, new QName[] { createXS("short"), createXS("unsignedByte") })
/*      */         {
/*      */           
/*      */           public Short parse(CharSequence text)
/*      */           {
/*  719 */             return Short.valueOf(DatatypeConverterImpl._parseShort(text));
/*      */           }
/*      */           
/*      */           public String print(Short v) {
/*  723 */             return DatatypeConverterImpl._printShort(v.shortValue());
/*      */           }
/*      */         });
/*  726 */     primaryList.add(new StringImpl<Integer>(Integer.class, new QName[] { createXS("int"), createXS("unsignedShort") })
/*      */         {
/*      */           
/*      */           public Integer parse(CharSequence text)
/*      */           {
/*  731 */             return Integer.valueOf(DatatypeConverterImpl._parseInt(text));
/*      */           }
/*      */           
/*      */           public String print(Integer v) {
/*  735 */             return DatatypeConverterImpl._printInt(v.intValue());
/*      */           }
/*      */         });
/*  738 */     primaryList.add(new StringImpl<Long>(Long.class, new QName[] { createXS("long"), createXS("unsignedInt") })
/*      */         {
/*      */ 
/*      */           
/*      */           public Long parse(CharSequence text)
/*      */           {
/*  744 */             return Long.valueOf(DatatypeConverterImpl._parseLong(text));
/*      */           }
/*      */           
/*      */           public String print(Long v) {
/*  748 */             return DatatypeConverterImpl._printLong(v.longValue());
/*      */           }
/*      */         });
/*  751 */     primaryList.add(new StringImpl<Float>(Float.class, new QName[] { createXS("float") })
/*      */         {
/*      */           
/*      */           public Float parse(CharSequence text)
/*      */           {
/*  756 */             return Float.valueOf(DatatypeConverterImpl._parseFloat(text.toString()));
/*      */           }
/*      */           
/*      */           public String print(Float v) {
/*  760 */             return DatatypeConverterImpl._printFloat(v.floatValue());
/*      */           }
/*      */         });
/*  763 */     primaryList.add(new StringImpl<Double>(Double.class, new QName[] { createXS("double") })
/*      */         {
/*      */           
/*      */           public Double parse(CharSequence text)
/*      */           {
/*  768 */             return Double.valueOf(DatatypeConverterImpl._parseDouble(text));
/*      */           }
/*      */           
/*      */           public String print(Double v) {
/*  772 */             return DatatypeConverterImpl._printDouble(v.doubleValue());
/*      */           }
/*      */         });
/*  775 */     primaryList.add(new StringImpl<BigInteger>(BigInteger.class, new QName[] { createXS("integer"), createXS("positiveInteger"), createXS("negativeInteger"), createXS("nonPositiveInteger"), createXS("nonNegativeInteger"), createXS("unsignedLong") })
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public BigInteger parse(CharSequence text)
/*      */           {
/*  785 */             return DatatypeConverterImpl._parseInteger(text);
/*      */           }
/*      */           
/*      */           public String print(BigInteger v) {
/*  789 */             return DatatypeConverterImpl._printInteger(v);
/*      */           }
/*      */         });
/*  792 */     primaryList.add(new StringImpl<BigDecimal>(BigDecimal.class, new QName[] { createXS("decimal") })
/*      */         {
/*      */           
/*      */           public BigDecimal parse(CharSequence text)
/*      */           {
/*  797 */             return DatatypeConverterImpl._parseDecimal(text.toString());
/*      */           }
/*      */           
/*      */           public String print(BigDecimal v) {
/*  801 */             return DatatypeConverterImpl._printDecimal(v);
/*      */           }
/*      */         });
/*  804 */     primaryList.add(new StringImpl<QName>(QName.class, new QName[] { createXS("QName") })
/*      */         {
/*      */           
/*      */           public QName parse(CharSequence text) throws SAXException
/*      */           {
/*      */             try {
/*  810 */               return DatatypeConverterImpl._parseQName(text.toString(), (NamespaceContext)UnmarshallingContext.getInstance());
/*  811 */             } catch (IllegalArgumentException e) {
/*  812 */               UnmarshallingContext.getInstance().handleError(e);
/*  813 */               return null;
/*      */             } 
/*      */           }
/*      */           
/*      */           public String print(QName v) {
/*  818 */             return DatatypeConverterImpl._printQName(v, (NamespaceContext)XMLSerializer.getInstance().getNamespaceContext());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean useNamespace() {
/*  823 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public void declareNamespace(QName v, XMLSerializer w) {
/*  828 */             w.getNamespaceContext().declareNamespace(v.getNamespaceURI(), v.getPrefix(), false);
/*      */           }
/*      */         });
/*  831 */     if (System.getProperty("mapAnyUriToUri") != null) {
/*  832 */       primaryList.add(new StringImpl<URI>(URI.class, new QName[] { createXS("anyURI") })
/*      */           {
/*      */             public URI parse(CharSequence text) throws SAXException {
/*      */               try {
/*  836 */                 return new URI(text.toString());
/*  837 */               } catch (URISyntaxException e) {
/*  838 */                 UnmarshallingContext.getInstance().handleError(e);
/*  839 */                 return null;
/*      */               } 
/*      */             }
/*      */             
/*      */             public String print(URI v) {
/*  844 */               return v.toString();
/*      */             }
/*      */           });
/*      */     }
/*  848 */     primaryList.add(new StringImpl<Duration>(Duration.class, new QName[] { createXS("duration") })
/*      */         {
/*      */           public String print(Duration duration) {
/*  851 */             return duration.toString();
/*      */           }
/*      */           
/*      */           public Duration parse(CharSequence lexical) {
/*  855 */             TODO.checkSpec("JSR222 Issue #42");
/*  856 */             return RuntimeBuiltinLeafInfoImpl.datatypeFactory.newDuration(lexical.toString());
/*      */           }
/*      */         });
/*  859 */     primaryList.add(new StringImpl<Void>(Void.class, new QName[0])
/*      */         {
/*      */ 
/*      */           
/*      */           public String print(Void value)
/*      */           {
/*  865 */             return "";
/*      */           }
/*      */           
/*      */           public Void parse(CharSequence lexical) {
/*  869 */             return null;
/*      */           }
/*      */         });
/*      */     
/*  873 */     List<RuntimeBuiltinLeafInfoImpl<?>> l = new ArrayList<RuntimeBuiltinLeafInfoImpl<?>>(secondaryList.size() + primaryList.size() + 1);
/*  874 */     l.addAll(secondaryList);
/*      */ 
/*      */     
/*      */     try {
/*  878 */       l.add(new UUIDImpl());
/*  879 */     } catch (LinkageError e) {}
/*      */ 
/*      */ 
/*      */     
/*  883 */     l.addAll(primaryList);
/*      */     
/*  885 */     builtinBeanInfos = Collections.unmodifiableList(l);
/*      */   }
/*      */   
/*      */   private static byte[] decodeBase64(CharSequence text) {
/*  889 */     if (text instanceof Base64Data) {
/*  890 */       Base64Data base64Data = (Base64Data)text;
/*  891 */       return base64Data.getExact();
/*      */     } 
/*  893 */     return DatatypeConverterImpl._parseBase64Binary(text.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  902 */   private static final DatatypeFactory datatypeFactory = init();
/*      */   
/*      */   private static DatatypeFactory init() {
/*      */     try {
/*  906 */       return DatatypeFactory.newInstance();
/*  907 */     } catch (DatatypeConfigurationException e) {
/*  908 */       throw new Error(Messages.FAILED_TO_INITIALE_DATATYPE_FACTORY.format(new Object[0]), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkXmlGregorianCalendarFieldRef(QName type, XMLGregorianCalendar cal) throws MarshalException {
/*  914 */     StringBuilder buf = new StringBuilder();
/*  915 */     int bitField = ((Integer)xmlGregorianCalendarFieldRef.get(type)).intValue();
/*  916 */     int l = 1;
/*  917 */     int pos = 0;
/*  918 */     while (bitField != 0) {
/*  919 */       int bit = bitField & 0x1;
/*  920 */       bitField >>>= 4;
/*  921 */       pos++;
/*      */       
/*  923 */       if (bit == 1) {
/*  924 */         switch (pos) {
/*      */           case 1:
/*  926 */             if (cal.getSecond() == Integer.MIN_VALUE) {
/*  927 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_SEC);
/*      */             }
/*      */           
/*      */           case 2:
/*  931 */             if (cal.getMinute() == Integer.MIN_VALUE) {
/*  932 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_MIN);
/*      */             }
/*      */           
/*      */           case 3:
/*  936 */             if (cal.getHour() == Integer.MIN_VALUE) {
/*  937 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_HR);
/*      */             }
/*      */           
/*      */           case 4:
/*  941 */             if (cal.getDay() == Integer.MIN_VALUE) {
/*  942 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_DAY);
/*      */             }
/*      */           
/*      */           case 5:
/*  946 */             if (cal.getMonth() == Integer.MIN_VALUE) {
/*  947 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_MONTH);
/*      */             }
/*      */           
/*      */           case 6:
/*  951 */             if (cal.getYear() == Integer.MIN_VALUE) {
/*  952 */               buf.append("  ").append(Messages.XMLGREGORIANCALENDAR_YEAR);
/*      */             }
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */     } 
/*  960 */     if (buf.length() > 0) {
/*  961 */       throw new MarshalException(Messages.XMLGREGORIANCALENDAR_INVALID.format(new Object[] { type.getLocalPart() }) + buf.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  970 */   private static final Map<QName, String> xmlGregorianCalendarFormatString = new HashMap<QName, String>();
/*      */   
/*      */   static {
/*  973 */     Map<QName, String> m = xmlGregorianCalendarFormatString;
/*      */     
/*  975 */     m.put(DatatypeConstants.DATETIME, "%Y-%M-%DT%h:%m:%s%z");
/*  976 */     m.put(DatatypeConstants.DATE, "%Y-%M-%D%z");
/*  977 */     m.put(DatatypeConstants.TIME, "%h:%m:%s%z");
/*  978 */     m.put(DatatypeConstants.GMONTH, "--%M--%z");
/*  979 */     m.put(DatatypeConstants.GDAY, "---%D%z");
/*  980 */     m.put(DatatypeConstants.GYEAR, "%Y%z");
/*  981 */     m.put(DatatypeConstants.GYEARMONTH, "%Y-%M%z");
/*  982 */     m.put(DatatypeConstants.GMONTHDAY, "--%M-%D%z");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  995 */   private static final Map<QName, Integer> xmlGregorianCalendarFieldRef = new HashMap<QName, Integer>();
/*      */   
/*      */   static {
/*  998 */     Map<QName, Integer> f = xmlGregorianCalendarFieldRef;
/*  999 */     f.put(DatatypeConstants.DATETIME, Integer.valueOf(17895697));
/* 1000 */     f.put(DatatypeConstants.DATE, Integer.valueOf(17895424));
/* 1001 */     f.put(DatatypeConstants.TIME, Integer.valueOf(16777489));
/* 1002 */     f.put(DatatypeConstants.GDAY, Integer.valueOf(16781312));
/* 1003 */     f.put(DatatypeConstants.GMONTH, Integer.valueOf(16842752));
/* 1004 */     f.put(DatatypeConstants.GYEAR, Integer.valueOf(17825792));
/* 1005 */     f.put(DatatypeConstants.GYEARMONTH, Integer.valueOf(17891328));
/* 1006 */     f.put(DatatypeConstants.GMONTHDAY, Integer.valueOf(16846848));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class UUIDImpl
/*      */     extends StringImpl<UUID>
/*      */   {
/*      */     public UUIDImpl() {
/* 1016 */       super(UUID.class, new QName[] { RuntimeBuiltinLeafInfoImpl.access$500("string") });
/*      */     }
/*      */     
/*      */     public UUID parse(CharSequence text) throws SAXException {
/* 1020 */       TODO.checkSpec("JSR222 Issue #42");
/*      */       try {
/* 1022 */         return UUID.fromString(WhiteSpaceProcessor.trim(text).toString());
/* 1023 */       } catch (IllegalArgumentException e) {
/* 1024 */         UnmarshallingContext.getInstance().handleError(e);
/* 1025 */         return null;
/*      */       } 
/*      */     }
/*      */     
/*      */     public String print(UUID v) {
/* 1030 */       return v.toString();
/*      */     }
/*      */   }
/*      */   
/*      */   private static class StringImplImpl
/*      */     extends StringImpl<String> {
/*      */     public StringImplImpl(Class type, QName[] typeNames) {
/* 1037 */       super(type, typeNames);
/*      */     }
/*      */     
/*      */     public String parse(CharSequence text) {
/* 1041 */       return text.toString();
/*      */     }
/*      */     
/*      */     public String print(String s) {
/* 1045 */       return s;
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeText(XMLSerializer w, String o, String fieldName) throws IOException, SAXException, XMLStreamException {
/* 1050 */       w.text(o, fieldName);
/*      */     }
/*      */ 
/*      */     
/*      */     public final void writeLeafElement(XMLSerializer w, Name tagName, String o, String fieldName) throws IOException, SAXException, XMLStreamException {
/* 1055 */       w.leafElement(tagName, o, fieldName);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeBuiltinLeafInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */