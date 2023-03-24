/*     */ package com.sun.xml.ws.api.streaming;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.streaming.XMLReaderException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XMLStreamReaderFactory
/*     */ {
/*  76 */   private static final Logger LOGGER = Logger.getLogger(XMLStreamReaderFactory.class.getName());
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static volatile XMLStreamReaderFactory theInstance;
/*     */ 
/*     */   
/*     */   static {
/*  84 */     XMLInputFactory xif = getXMLInputFactory();
/*  85 */     XMLStreamReaderFactory f = null;
/*     */ 
/*     */ 
/*     */     
/*  89 */     if (!getProperty(XMLStreamReaderFactory.class.getName() + ".noPool").booleanValue()) {
/*  90 */       f = Zephyr.newInstance(xif);
/*     */     }
/*     */     
/*  93 */     if (f == null)
/*     */     {
/*  95 */       if (xif.getClass().getName().equals("com.ctc.wstx.stax.WstxInputFactory")) {
/*  96 */         f = new Woodstox(xif);
/*     */       }
/*     */     }
/*     */     
/* 100 */     if (f == null) {
/* 101 */       f = new Default();
/*     */     }
/*     */     
/* 104 */     theInstance = f;
/* 105 */     LOGGER.log(Level.FINE, "XMLStreamReaderFactory instance is = {0}", theInstance);
/*     */   }
/*     */   
/*     */   private static XMLInputFactory getXMLInputFactory() {
/* 109 */     XMLInputFactory xif = null;
/* 110 */     if (getProperty(XMLStreamReaderFactory.class.getName() + ".woodstox").booleanValue()) {
/*     */       try {
/* 112 */         xif = (XMLInputFactory)Class.forName("com.ctc.wstx.stax.WstxInputFactory").newInstance();
/* 113 */       } catch (Exception e) {}
/*     */     }
/*     */ 
/*     */     
/* 117 */     if (xif == null) {
/* 118 */       xif = XMLInputFactory.newInstance();
/*     */     }
/* 120 */     xif.setProperty("javax.xml.stream.isNamespaceAware", Boolean.valueOf(true));
/* 121 */     xif.setProperty("javax.xml.stream.supportDTD", Boolean.valueOf(false));
/* 122 */     xif.setProperty("javax.xml.stream.isCoalescing", Boolean.valueOf(true));
/* 123 */     return xif;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void set(XMLStreamReaderFactory f) {
/* 132 */     if (f == null) {
/* 133 */       throw new IllegalArgumentException();
/*     */     }
/* 135 */     theInstance = f;
/*     */   }
/*     */   
/*     */   public static XMLStreamReaderFactory get() {
/* 139 */     return theInstance;
/*     */   }
/*     */ 
/*     */   
/*     */   public static XMLStreamReader create(InputSource source, boolean rejectDTDs) {
/*     */     try {
/* 145 */       if (source.getCharacterStream() != null) {
/* 146 */         return get().doCreate(source.getSystemId(), source.getCharacterStream(), rejectDTDs);
/*     */       }
/*     */ 
/*     */       
/* 150 */       if (source.getByteStream() != null) {
/* 151 */         return get().doCreate(source.getSystemId(), source.getByteStream(), rejectDTDs);
/*     */       }
/*     */ 
/*     */       
/* 155 */       return get().doCreate(source.getSystemId(), (new URL(source.getSystemId())).openStream(), rejectDTDs);
/* 156 */     } catch (IOException e) {
/* 157 */       throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static XMLStreamReader create(@Nullable String systemId, InputStream in, boolean rejectDTDs) {
/* 162 */     return get().doCreate(systemId, in, rejectDTDs);
/*     */   }
/*     */   
/*     */   public static XMLStreamReader create(@Nullable String systemId, InputStream in, @Nullable String encoding, boolean rejectDTDs) {
/* 166 */     return (encoding == null) ? create(systemId, in, rejectDTDs) : get().doCreate(systemId, in, encoding, rejectDTDs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamReader create(@Nullable String systemId, Reader reader, boolean rejectDTDs) {
/* 172 */     return get().doCreate(systemId, reader, rejectDTDs);
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
/*     */   public static void recycle(XMLStreamReader r) {
/* 199 */     get().doRecycle(r);
/* 200 */     if (r instanceof RecycleAware) {
/* 201 */       ((RecycleAware)r).onRecycled();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLStreamReader doCreate(String systemId, InputStream in, @NotNull String encoding, boolean rejectDTDs) {
/*     */     Reader reader;
/*     */     try {
/* 212 */       reader = new InputStreamReader(in, encoding);
/* 213 */     } catch (UnsupportedEncodingException ue) {
/* 214 */       throw new XMLReaderException("stax.cantCreate", new Object[] { ue });
/*     */     } 
/* 216 */     return doCreate(systemId, reader, rejectDTDs);
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
/*     */   private static final class Zephyr
/*     */     extends XMLStreamReaderFactory
/*     */   {
/*     */     private final XMLInputFactory xif;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 241 */     private final ThreadLocal<XMLStreamReader> pool = new ThreadLocal<XMLStreamReader>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Method setInputSourceMethod;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Method resetMethod;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class zephyrClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public static XMLStreamReaderFactory newInstance(XMLInputFactory xif) {
/*     */       try {
/* 266 */         Class<?> clazz = xif.createXMLStreamReader(new StringReader("<foo/>")).getClass();
/*     */ 
/*     */         
/* 269 */         if (!clazz.getName().startsWith("com.sun.xml.stream."))
/* 270 */           return null; 
/* 271 */         return new Zephyr(xif, clazz);
/* 272 */       } catch (NoSuchMethodException e) {
/* 273 */         return null;
/* 274 */       } catch (XMLStreamException e) {
/* 275 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Zephyr(XMLInputFactory xif, Class clazz) throws NoSuchMethodException {
/* 280 */       this.zephyrClass = clazz;
/* 281 */       this.setInputSourceMethod = clazz.getMethod("setInputSource", new Class[] { InputSource.class });
/* 282 */       this.resetMethod = clazz.getMethod("reset", new Class[0]);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 287 */         xif.setProperty("reuse-instance", Boolean.valueOf(false));
/* 288 */       } catch (IllegalArgumentException e) {}
/*     */ 
/*     */       
/* 291 */       this.xif = xif;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private XMLStreamReader fetch() {
/* 298 */       XMLStreamReader sr = this.pool.get();
/* 299 */       if (sr == null) return null; 
/* 300 */       this.pool.set(null);
/* 301 */       return sr;
/*     */     }
/*     */     
/*     */     public void doRecycle(XMLStreamReader r) {
/* 305 */       if (this.zephyrClass.isInstance(r))
/* 306 */         this.pool.set(r); 
/*     */     }
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, InputStream in, boolean rejectDTDs) {
/*     */       try {
/* 311 */         XMLStreamReader xsr = fetch();
/* 312 */         if (xsr == null) {
/* 313 */           return this.xif.createXMLStreamReader(systemId, in);
/*     */         }
/*     */         
/* 316 */         InputSource is = new InputSource(systemId);
/* 317 */         is.setByteStream(in);
/* 318 */         reuse(xsr, is);
/* 319 */         return xsr;
/* 320 */       } catch (IllegalAccessException e) {
/* 321 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/* 322 */       } catch (InvocationTargetException e) {
/* 323 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/* 324 */       } catch (XMLStreamException e) {
/* 325 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, Reader in, boolean rejectDTDs) {
/*     */       try {
/* 331 */         XMLStreamReader xsr = fetch();
/* 332 */         if (xsr == null) {
/* 333 */           return this.xif.createXMLStreamReader(systemId, in);
/*     */         }
/*     */         
/* 336 */         InputSource is = new InputSource(systemId);
/* 337 */         is.setCharacterStream(in);
/* 338 */         reuse(xsr, is);
/* 339 */         return xsr;
/* 340 */       } catch (IllegalAccessException e) {
/* 341 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/* 342 */       } catch (InvocationTargetException e) {
/* 343 */         Throwable cause = e.getCause();
/* 344 */         if (cause == null) {
/* 345 */           cause = e;
/*     */         }
/* 347 */         throw new XMLReaderException("stax.cantCreate", new Object[] { cause });
/* 348 */       } catch (XMLStreamException e) {
/* 349 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */     
/*     */     private void reuse(XMLStreamReader xsr, InputSource in) throws IllegalAccessException, InvocationTargetException {
/* 354 */       this.resetMethod.invoke(xsr, new Object[0]);
/* 355 */       this.setInputSourceMethod.invoke(xsr, new Object[] { in });
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
/*     */   public static final class Default
/*     */     extends XMLStreamReaderFactory
/*     */   {
/* 371 */     private final ThreadLocal<XMLInputFactory> xif = new ThreadLocal<XMLInputFactory>()
/*     */       {
/*     */         public XMLInputFactory initialValue() {
/* 374 */           return XMLStreamReaderFactory.getXMLInputFactory();
/*     */         }
/*     */       };
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, InputStream in, boolean rejectDTDs) {
/*     */       try {
/* 380 */         return ((XMLInputFactory)this.xif.get()).createXMLStreamReader(systemId, in);
/* 381 */       } catch (XMLStreamException e) {
/* 382 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, Reader in, boolean rejectDTDs) {
/*     */       try {
/* 388 */         return ((XMLInputFactory)this.xif.get()).createXMLStreamReader(systemId, in);
/* 389 */       } catch (XMLStreamException e) {
/* 390 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void doRecycle(XMLStreamReader r) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NoLock
/*     */     extends XMLStreamReaderFactory
/*     */   {
/*     */     private final XMLInputFactory xif;
/*     */ 
/*     */ 
/*     */     
/*     */     public NoLock(XMLInputFactory xif) {
/* 410 */       this.xif = xif;
/*     */     }
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, InputStream in, boolean rejectDTDs) {
/*     */       try {
/* 415 */         return this.xif.createXMLStreamReader(systemId, in);
/* 416 */       } catch (XMLStreamException e) {
/* 417 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, Reader in, boolean rejectDTDs) {
/*     */       try {
/* 423 */         return this.xif.createXMLStreamReader(systemId, in);
/* 424 */       } catch (XMLStreamException e) {
/* 425 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void doRecycle(XMLStreamReader r) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Woodstox
/*     */     extends NoLock
/*     */   {
/*     */     public Woodstox(XMLInputFactory xif) {
/* 440 */       super(xif);
/* 441 */       xif.setProperty("org.codehaus.stax2.internNsUris", Boolean.valueOf(true));
/*     */     }
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, InputStream in, boolean rejectDTDs) {
/* 445 */       return super.doCreate(systemId, in, rejectDTDs);
/*     */     }
/*     */     
/*     */     public XMLStreamReader doCreate(String systemId, Reader in, boolean rejectDTDs) {
/* 449 */       return super.doCreate(systemId, in, rejectDTDs);
/*     */     }
/*     */   }
/*     */   
/*     */   private static Boolean getProperty(final String prop) {
/* 454 */     return AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run() {
/* 457 */             String value = System.getProperty(prop);
/* 458 */             return (value != null) ? Boolean.valueOf(value) : Boolean.FALSE;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public abstract XMLStreamReader doCreate(String paramString, InputStream paramInputStream, boolean paramBoolean);
/*     */   
/*     */   public abstract XMLStreamReader doCreate(String paramString, Reader paramReader, boolean paramBoolean);
/*     */   
/*     */   public abstract void doRecycle(XMLStreamReader paramXMLStreamReader);
/*     */   
/*     */   public static interface RecycleAware {
/*     */     void onRecycled();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\streaming\XMLStreamReaderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */