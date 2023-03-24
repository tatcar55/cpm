/*     */ package javax.xml.bind;
/*     */ 
/*     */ import java.beans.Introspector;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JAXB
/*     */ {
/*     */   private static volatile WeakReference<Cache> cache;
/*     */   
/*     */   private static final class Cache
/*     */   {
/*     */     final Class type;
/*     */     final JAXBContext context;
/*     */     
/*     */     public Cache(Class type) throws JAXBException {
/* 126 */       this.type = type;
/* 127 */       this.context = JAXBContext.newInstance(new Class[] { type });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> JAXBContext getContext(Class<T> type) throws JAXBException {
/* 146 */     WeakReference<Cache> c = cache;
/* 147 */     if (c != null) {
/* 148 */       Cache cache = c.get();
/* 149 */       if (cache != null && cache.type == type) {
/* 150 */         return cache.context;
/*     */       }
/*     */     } 
/*     */     
/* 154 */     Cache d = new Cache(type);
/* 155 */     cache = new WeakReference<Cache>(d);
/*     */     
/* 157 */     return d.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T unmarshal(File xml, Class<T> type) {
/*     */     try {
/* 168 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(new StreamSource(xml), type);
/* 169 */       return item.getValue();
/* 170 */     } catch (JAXBException e) {
/* 171 */       throw new DataBindingException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T unmarshal(URL xml, Class<T> type) {
/*     */     try {
/* 183 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 184 */       return item.getValue();
/* 185 */     } catch (JAXBException e) {
/* 186 */       throw new DataBindingException(e);
/* 187 */     } catch (IOException e) {
/* 188 */       throw new DataBindingException(e);
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
/*     */   public static <T> T unmarshal(URI xml, Class<T> type) {
/*     */     try {
/* 201 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 202 */       return item.getValue();
/* 203 */     } catch (JAXBException e) {
/* 204 */       throw new DataBindingException(e);
/* 205 */     } catch (IOException e) {
/* 206 */       throw new DataBindingException(e);
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
/*     */   
/*     */   public static <T> T unmarshal(String xml, Class<T> type) {
/*     */     try {
/* 220 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 221 */       return item.getValue();
/* 222 */     } catch (JAXBException e) {
/* 223 */       throw new DataBindingException(e);
/* 224 */     } catch (IOException e) {
/* 225 */       throw new DataBindingException(e);
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
/*     */   public static <T> T unmarshal(InputStream xml, Class<T> type) {
/*     */     try {
/* 238 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 239 */       return item.getValue();
/* 240 */     } catch (JAXBException e) {
/* 241 */       throw new DataBindingException(e);
/* 242 */     } catch (IOException e) {
/* 243 */       throw new DataBindingException(e);
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
/*     */   
/*     */   public static <T> T unmarshal(Reader xml, Class<T> type) {
/*     */     try {
/* 257 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 258 */       return item.getValue();
/* 259 */     } catch (JAXBException e) {
/* 260 */       throw new DataBindingException(e);
/* 261 */     } catch (IOException e) {
/* 262 */       throw new DataBindingException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T unmarshal(Source xml, Class<T> type) {
/*     */     try {
/* 274 */       JAXBElement<T> item = getContext(type).createUnmarshaller().unmarshal(toSource(xml), type);
/* 275 */       return item.getValue();
/* 276 */     } catch (JAXBException e) {
/* 277 */       throw new DataBindingException(e);
/* 278 */     } catch (IOException e) {
/* 279 */       throw new DataBindingException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Source toSource(Object xml) throws IOException {
/* 290 */     if (xml == null) {
/* 291 */       throw new IllegalArgumentException("no XML is given");
/*     */     }
/* 293 */     if (xml instanceof String) {
/*     */       try {
/* 295 */         xml = new URI((String)xml);
/* 296 */       } catch (URISyntaxException e) {
/* 297 */         xml = new File((String)xml);
/*     */       } 
/*     */     }
/* 300 */     if (xml instanceof File) {
/* 301 */       File file = (File)xml;
/* 302 */       return new StreamSource(file);
/*     */     } 
/* 304 */     if (xml instanceof URI) {
/* 305 */       URI uri = (URI)xml;
/* 306 */       xml = uri.toURL();
/*     */     } 
/* 308 */     if (xml instanceof URL) {
/* 309 */       URL url = (URL)xml;
/* 310 */       return new StreamSource(url.toExternalForm());
/*     */     } 
/* 312 */     if (xml instanceof InputStream) {
/* 313 */       InputStream in = (InputStream)xml;
/* 314 */       return new StreamSource(in);
/*     */     } 
/* 316 */     if (xml instanceof Reader) {
/* 317 */       Reader r = (Reader)xml;
/* 318 */       return new StreamSource(r);
/*     */     } 
/* 320 */     if (xml instanceof Source) {
/* 321 */       return (Source)xml;
/*     */     }
/* 323 */     throw new IllegalArgumentException("I don't understand how to handle " + xml.getClass());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void marshal(Object jaxbObject, File xml) {
/* 347 */     _marshal(jaxbObject, xml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void marshal(Object jaxbObject, URL xml) {
/* 374 */     _marshal(jaxbObject, xml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void marshal(Object jaxbObject, URI xml) {
/* 398 */     _marshal(jaxbObject, xml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void marshal(Object jaxbObject, String xml) {
/* 423 */     _marshal(jaxbObject, xml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void marshal(Object jaxbObject, OutputStream xml) {
/* 447 */     _marshal(jaxbObject, xml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void marshal(Object jaxbObject, Writer xml) {
/* 471 */     _marshal(jaxbObject, xml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void marshal(Object jaxbObject, Result xml) {
/* 494 */     _marshal(jaxbObject, xml);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void _marshal(Object jaxbObject, Object xml) {
/*     */     try {
/*     */       JAXBContext context;
/* 573 */       if (jaxbObject instanceof JAXBElement) {
/* 574 */         context = getContext(((JAXBElement)jaxbObject).getDeclaredType());
/*     */       } else {
/* 576 */         Class<?> clazz = jaxbObject.getClass();
/* 577 */         XmlRootElement r = clazz.<XmlRootElement>getAnnotation(XmlRootElement.class);
/* 578 */         context = getContext(clazz);
/* 579 */         if (r == null)
/*     */         {
/* 581 */           jaxbObject = new JAXBElement(new QName(inferName(clazz)), clazz, jaxbObject);
/*     */         }
/*     */       } 
/*     */       
/* 585 */       Marshaller m = context.createMarshaller();
/* 586 */       m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
/* 587 */       m.marshal(jaxbObject, toResult(xml));
/* 588 */     } catch (JAXBException e) {
/* 589 */       throw new DataBindingException(e);
/* 590 */     } catch (IOException e) {
/* 591 */       throw new DataBindingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String inferName(Class clazz) {
/* 596 */     return Introspector.decapitalize(clazz.getSimpleName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Result toResult(Object xml) throws IOException {
/* 604 */     if (xml == null) {
/* 605 */       throw new IllegalArgumentException("no XML is given");
/*     */     }
/* 607 */     if (xml instanceof String) {
/*     */       try {
/* 609 */         xml = new URI((String)xml);
/* 610 */       } catch (URISyntaxException e) {
/* 611 */         xml = new File((String)xml);
/*     */       } 
/*     */     }
/* 614 */     if (xml instanceof File) {
/* 615 */       File file = (File)xml;
/* 616 */       return new StreamResult(file);
/*     */     } 
/* 618 */     if (xml instanceof URI) {
/* 619 */       URI uri = (URI)xml;
/* 620 */       xml = uri.toURL();
/*     */     } 
/* 622 */     if (xml instanceof URL) {
/* 623 */       URL url = (URL)xml;
/* 624 */       URLConnection con = url.openConnection();
/* 625 */       con.setDoOutput(true);
/* 626 */       con.setDoInput(false);
/* 627 */       con.connect();
/* 628 */       return new StreamResult(con.getOutputStream());
/*     */     } 
/* 630 */     if (xml instanceof OutputStream) {
/* 631 */       OutputStream os = (OutputStream)xml;
/* 632 */       return new StreamResult(os);
/*     */     } 
/* 634 */     if (xml instanceof Writer) {
/* 635 */       Writer w = (Writer)xml;
/* 636 */       return new StreamResult(w);
/*     */     } 
/* 638 */     if (xml instanceof Result) {
/* 639 */       return (Result)xml;
/*     */     }
/* 641 */     throw new IllegalArgumentException("I don't understand how to handle " + xml.getClass());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\JAXB.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */