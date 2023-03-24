/*     */ package com.sun.xml.ws.api.streaming;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.encoding.HasEncoding;
/*     */ import com.sun.xml.ws.streaming.XMLReaderException;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamWriterFilter;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XMLStreamWriterFactory
/*     */ {
/*  73 */   private static final Logger LOGGER = Logger.getLogger(XMLStreamWriterFactory.class.getName());
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static volatile XMLStreamWriterFactory theInstance;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  82 */     XMLOutputFactory xof = null;
/*  83 */     if (Boolean.getBoolean(XMLStreamWriterFactory.class.getName() + ".woodstox")) {
/*     */       try {
/*  85 */         xof = (XMLOutputFactory)Class.forName("com.ctc.wstx.stax.WstxOutputFactory").newInstance();
/*  86 */       } catch (Exception e) {}
/*     */     }
/*     */ 
/*     */     
/*  90 */     if (xof == null) {
/*  91 */       xof = XMLOutputFactory.newInstance();
/*     */     }
/*     */     
/*  94 */     XMLStreamWriterFactory f = null;
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (!Boolean.getBoolean(XMLStreamWriterFactory.class.getName() + ".noPool"))
/*  99 */       f = Zephyr.newInstance(xof); 
/* 100 */     if (f == null)
/*     */     {
/* 102 */       if (xof.getClass().getName().equals("com.ctc.wstx.stax.WstxOutputFactory"))
/* 103 */         f = new NoLock(xof); 
/*     */     }
/* 105 */     if (f == null) {
/* 106 */       f = new Default(xof);
/*     */     }
/* 108 */     theInstance = f;
/* 109 */     LOGGER.fine("XMLStreamWriterFactory instance is = " + theInstance);
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
/*     */   public static void recycle(XMLStreamWriter r) {
/* 154 */     get().doRecycle(r);
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
/*     */   @NotNull
/*     */   public static XMLStreamWriterFactory get() {
/* 173 */     return theInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void set(@NotNull XMLStreamWriterFactory f) {
/* 184 */     if (f == null) throw new IllegalArgumentException(); 
/* 185 */     theInstance = f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamWriter create(OutputStream out) {
/* 192 */     return get().doCreate(out);
/*     */   }
/*     */   
/*     */   public static XMLStreamWriter create(OutputStream out, String encoding) {
/* 196 */     return get().doCreate(out, encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamWriter createXMLStreamWriter(OutputStream out) {
/* 204 */     return create(out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamWriter createXMLStreamWriter(OutputStream out, String encoding) {
/* 212 */     return create(out, encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamWriter createXMLStreamWriter(OutputStream out, String encoding, boolean declare) {
/* 220 */     return create(out, encoding);
/*     */   }
/*     */   
/*     */   public abstract XMLStreamWriter doCreate(OutputStream paramOutputStream);
/*     */   
/*     */   public abstract XMLStreamWriter doCreate(OutputStream paramOutputStream, String paramString);
/*     */   
/*     */   public abstract void doRecycle(XMLStreamWriter paramXMLStreamWriter);
/*     */   
/*     */   public static interface RecycleAware {
/*     */     void onRecycled(); }
/*     */   
/*     */   public static final class Default extends XMLStreamWriterFactory { private final XMLOutputFactory xof;
/*     */     
/*     */     public Default(XMLOutputFactory xof) {
/* 235 */       this.xof = xof;
/*     */     }
/*     */     
/*     */     public XMLStreamWriter doCreate(OutputStream out) {
/* 239 */       return doCreate(out, "UTF-8");
/*     */     }
/*     */     
/*     */     public synchronized XMLStreamWriter doCreate(OutputStream out, String encoding) {
/*     */       try {
/* 244 */         XMLStreamWriter writer = this.xof.createXMLStreamWriter(out, encoding);
/* 245 */         return (XMLStreamWriter)new XMLStreamWriterFactory.HasEncodingWriter(writer, encoding);
/* 246 */       } catch (XMLStreamException e) {
/* 247 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void doRecycle(XMLStreamWriter r) {} }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Zephyr
/*     */     extends XMLStreamWriterFactory
/*     */   {
/*     */     private final XMLOutputFactory xof;
/*     */ 
/*     */     
/* 264 */     private final ThreadLocal<XMLStreamWriter> pool = new ThreadLocal<XMLStreamWriter>();
/*     */     private final Method resetMethod;
/*     */     private final Method setOutputMethod;
/*     */     private final Class zephyrClass;
/*     */     
/*     */     public static XMLStreamWriterFactory newInstance(XMLOutputFactory xof) {
/*     */       try {
/* 271 */         Class<?> clazz = xof.createXMLStreamWriter(new StringWriter()).getClass();
/*     */         
/* 273 */         if (!clazz.getName().startsWith("com.sun.xml.stream.")) {
/* 274 */           return null;
/*     */         }
/* 276 */         return new Zephyr(xof, clazz);
/* 277 */       } catch (XMLStreamException e) {
/* 278 */         return null;
/* 279 */       } catch (NoSuchMethodException e) {
/* 280 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     private Zephyr(XMLOutputFactory xof, Class clazz) throws NoSuchMethodException {
/* 285 */       this.xof = xof;
/*     */       
/* 287 */       this.zephyrClass = clazz;
/* 288 */       this.setOutputMethod = clazz.getMethod("setOutput", new Class[] { StreamResult.class, String.class });
/* 289 */       this.resetMethod = clazz.getMethod("reset", new Class[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private XMLStreamWriter fetch() {
/* 296 */       XMLStreamWriter sr = this.pool.get();
/* 297 */       if (sr == null) return null; 
/* 298 */       this.pool.set(null);
/* 299 */       return sr;
/*     */     }
/*     */     
/*     */     public XMLStreamWriter doCreate(OutputStream out) {
/* 303 */       return doCreate(out, "UTF-8");
/*     */     }
/*     */     
/*     */     public XMLStreamWriter doCreate(OutputStream out, String encoding) {
/* 307 */       XMLStreamWriter xsw = fetch();
/* 308 */       if (xsw != null) {
/*     */         
/*     */         try {
/* 311 */           this.resetMethod.invoke(xsw, new Object[0]);
/* 312 */           this.setOutputMethod.invoke(xsw, new Object[] { new StreamResult(out), encoding });
/* 313 */         } catch (IllegalAccessException e) {
/* 314 */           throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/* 315 */         } catch (InvocationTargetException e) {
/* 316 */           throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/* 321 */           xsw = this.xof.createXMLStreamWriter(out, encoding);
/* 322 */         } catch (XMLStreamException e) {
/* 323 */           throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */         } 
/*     */       } 
/* 326 */       return (XMLStreamWriter)new XMLStreamWriterFactory.HasEncodingWriter(xsw, encoding);
/*     */     }
/*     */     
/*     */     public void doRecycle(XMLStreamWriter r) {
/* 330 */       if (r instanceof XMLStreamWriterFactory.HasEncodingWriter) {
/* 331 */         r = ((XMLStreamWriterFactory.HasEncodingWriter)r).getWriter();
/*     */       }
/* 333 */       if (this.zephyrClass.isInstance(r)) {
/*     */         
/*     */         try {
/* 336 */           r.close();
/* 337 */         } catch (XMLStreamException e) {
/* 338 */           throw new WebServiceException(e);
/*     */         } 
/* 340 */         this.pool.set(r);
/*     */       } 
/* 342 */       if (r instanceof XMLStreamWriterFactory.RecycleAware) {
/* 343 */         ((XMLStreamWriterFactory.RecycleAware)r).onRecycled();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class NoLock
/*     */     extends XMLStreamWriterFactory
/*     */   {
/*     */     private final XMLOutputFactory xof;
/*     */     
/*     */     public NoLock(XMLOutputFactory xof) {
/* 355 */       this.xof = xof;
/*     */     }
/*     */     
/*     */     public XMLStreamWriter doCreate(OutputStream out) {
/* 359 */       return doCreate(out, "utf-8");
/*     */     }
/*     */     
/*     */     public XMLStreamWriter doCreate(OutputStream out, String encoding) {
/*     */       try {
/* 364 */         XMLStreamWriter writer = this.xof.createXMLStreamWriter(out, encoding);
/* 365 */         return (XMLStreamWriter)new XMLStreamWriterFactory.HasEncodingWriter(writer, encoding);
/* 366 */       } catch (XMLStreamException e) {
/* 367 */         throw new XMLReaderException("stax.cantCreate", new Object[] { e });
/*     */       } 
/*     */     }
/*     */     
/*     */     public void doRecycle(XMLStreamWriter r) {}
/*     */   }
/*     */   
/*     */   private static class HasEncodingWriter
/*     */     extends XMLStreamWriterFilter
/*     */     implements HasEncoding
/*     */   {
/*     */     private final String encoding;
/*     */     
/*     */     HasEncodingWriter(XMLStreamWriter writer, String encoding) {
/* 381 */       super(writer);
/* 382 */       this.encoding = encoding;
/*     */     }
/*     */     
/*     */     public String getEncoding() {
/* 386 */       return this.encoding;
/*     */     }
/*     */     
/*     */     XMLStreamWriter getWriter() {
/* 390 */       return this.writer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\streaming\XMLStreamWriterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */