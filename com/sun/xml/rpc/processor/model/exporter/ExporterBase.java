/*     */ package com.sun.xml.rpc.processor.model.exporter;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDateTimeCalendarEncoder;
/*     */ import com.sun.xml.rpc.processor.util.PrettyPrintingXMLWriterFactoryImpl;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.PrefixFactory;
/*     */ import com.sun.xml.rpc.streaming.PrefixFactoryImpl;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterException;
/*     */ import com.sun.xml.rpc.util.IdentityMap;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import java.util.zip.GZIPOutputStream;
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
/*     */ public abstract class ExporterBase
/*     */ {
/*     */   protected OutputStream out;
/*     */   protected XMLWriter writer;
/*     */   protected Map obj2id;
/*     */   protected Map immutableObj2id;
/*     */   protected int nextId;
/*     */   protected Set obj2serialize;
/*     */   protected Stack obj2serializeStack;
/*     */   protected Set immediateClasses;
/*     */   protected Set immutableClasses;
/*     */   
/*     */   public ExporterBase(OutputStream s) {
/*  65 */     this.out = s;
/*     */     try {
/*  67 */       this.writer = (new PrettyPrintingXMLWriterFactoryImpl()).createXMLWriter(new GZIPOutputStream(s));
/*     */     }
/*  69 */     catch (IOException e) {
/*  70 */       throw new XMLWriterException("xmlwriter.ioException", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */     
/*  73 */     this.writer.setPrefixFactory((PrefixFactory)new PrefixFactoryImpl("ns"));
/*     */   }
/*     */   
/*     */   protected void initialize() {
/*  77 */     this.obj2id = (Map)new IdentityMap();
/*  78 */     this.immutableObj2id = new HashMap<Object, Object>();
/*  79 */     this.obj2serialize = new HashSet();
/*  80 */     this.obj2serializeStack = new Stack();
/*  81 */     this.nextId = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     this.immutableClasses = new HashSet();
/*  91 */     this.immutableClasses.add(Boolean.class);
/*  92 */     this.immutableClasses.add(Integer.class);
/*  93 */     this.immutableClasses.add(Short.class);
/*  94 */     this.immutableClasses.add(Long.class);
/*  95 */     this.immutableClasses.add(Float.class);
/*  96 */     this.immutableClasses.add(Double.class);
/*  97 */     this.immutableClasses.add(Byte.class);
/*  98 */     this.immutableClasses.add(String.class);
/*  99 */     this.immutableClasses.add(BigDecimal.class);
/* 100 */     this.immutableClasses.add(BigInteger.class);
/* 101 */     this.immutableClasses.add(QName.class);
/* 102 */     this.immutableClasses.add(SOAPStyle.class);
/*     */     
/* 104 */     this.immutableClasses.add(SOAPUse.class);
/* 105 */     this.immutableClasses.add(SOAPVersion.class);
/*     */ 
/*     */     
/* 108 */     this.immutableClasses.add(URI.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     this.immediateClasses = new HashSet();
/* 120 */     this.immediateClasses.addAll(this.immutableClasses);
/*     */     try {
/* 122 */       this.immediateClasses.add(Class.forName("[I"));
/* 123 */       this.immediateClasses.add(Class.forName("[B"));
/* 124 */       this.immediateClasses.add(Class.forName("[Ljava.lang.String;"));
/* 125 */     } catch (ClassNotFoundException e) {}
/*     */ 
/*     */     
/* 128 */     this.immediateClasses.add(ArrayList.class);
/* 129 */     this.immediateClasses.add(HashSet.class);
/* 130 */     this.immediateClasses.add(HashMap.class);
/* 131 */     this.immediateClasses.add(GregorianCalendar.class);
/*     */   }
/*     */   
/*     */   protected void internalDoExport(Object root) {
/* 135 */     initialize();
/* 136 */     this.writer.startElement(getContainerName());
/*     */ 
/*     */     
/* 139 */     if (getVersion() != null) {
/* 140 */       int[] version = VersionUtil.getCanonicalVersion(getVersion());
/* 141 */       this.writer.writeAttribute("version", version[0] + "." + version[1] + "." + version[2] + "." + version[3]);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     int id = getId(root);
/* 152 */     while (!this.obj2serializeStack.empty()) {
/* 153 */       Object obj = this.obj2serializeStack.pop();
/* 154 */       this.obj2serialize.remove(obj);
/* 155 */       visit(obj);
/*     */     } 
/* 157 */     this.writer.endElement();
/* 158 */     this.writer.close();
/*     */   }
/*     */   
/*     */   protected void visit(Object obj) {
/* 162 */     if (obj == null) {
/*     */       return;
/*     */     }
/* 165 */     failUnsupportedClass(obj.getClass());
/*     */   }
/*     */   
/*     */   protected boolean isImmediate(Object obj) {
/* 169 */     if (obj == null) {
/* 170 */       return true;
/*     */     }
/* 172 */     return this.immediateClasses.contains(obj.getClass());
/*     */   }
/*     */   
/*     */   protected boolean isImmutable(Object obj) {
/* 176 */     if (obj == null) {
/* 177 */       return true;
/*     */     }
/* 179 */     return this.immutableClasses.contains(obj.getClass());
/*     */   }
/*     */   
/*     */   protected int getId(Object obj) {
/* 183 */     if (obj == null) {
/* 184 */       return 0;
/*     */     }
/* 186 */     Integer id = (Integer)this.obj2id.get(obj);
/* 187 */     if (id != null) {
/* 188 */       return id.intValue();
/*     */     }
/* 190 */     boolean immutable = isImmutable(obj);
/* 191 */     if (immutable) {
/* 192 */       id = (Integer)this.immutableObj2id.get(obj);
/* 193 */       if (id != null) {
/* 194 */         return id.intValue();
/*     */       }
/*     */     } 
/* 197 */     id = newId();
/* 198 */     this.obj2id.put(obj, id);
/* 199 */     if (immutable) {
/* 200 */       this.immutableObj2id.put(obj, id);
/*     */     }
/* 202 */     if (isImmediate(obj)) {
/* 203 */       defineImmediate(obj, id);
/*     */     } else {
/* 205 */       define(obj, id);
/*     */     } 
/* 207 */     return id.intValue();
/*     */   }
/*     */   
/*     */   protected void defineImmediate(Object obj, Integer id) {
/* 211 */     String value = getImmediateObjectValue(obj);
/* 212 */     this.writer.startElement(getDefineImmediateObjectName());
/* 213 */     this.writer.writeAttribute("id", id.toString());
/* 214 */     this.writer.writeAttribute("type", obj.getClass().getName());
/* 215 */     this.writer.writeAttribute("value", value);
/* 216 */     this.writer.endElement();
/*     */   }
/*     */   
/*     */   protected String getImmediateObjectValue(Object obj) {
/* 220 */     if (obj instanceof String)
/* 221 */       return (String)obj; 
/* 222 */     if (obj instanceof QName)
/* 223 */       return obj.toString(); 
/* 224 */     if (obj instanceof Boolean || obj instanceof Short || obj instanceof Integer || obj instanceof Long || obj instanceof Float || obj instanceof Double || obj instanceof Byte || obj instanceof BigDecimal || obj instanceof BigInteger || obj instanceof URI)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 235 */       return obj.toString(); } 
/* 236 */     if (obj instanceof SOAPStyle)
/* 237 */       return (obj == SOAPStyle.RPC) ? "rpc" : "document"; 
/* 238 */     if (obj instanceof SOAPUse)
/* 239 */       return (obj == SOAPUse.ENCODED) ? "encoded" : "literal"; 
/* 240 */     if (obj instanceof SOAPVersion)
/* 241 */       return obj.toString(); 
/* 242 */     if (obj instanceof int[]) {
/* 243 */       int[] a = (int[])obj;
/* 244 */       StringBuffer sb = new StringBuffer();
/* 245 */       for (int i = 0; i < a.length; i++) {
/* 246 */         if (i > 0) {
/* 247 */           sb.append(' ');
/*     */         }
/* 249 */         sb.append(Integer.toString(a[i]));
/*     */       } 
/* 251 */       return sb.toString();
/* 252 */     }  if (obj instanceof byte[]) {
/* 253 */       byte[] a = (byte[])obj;
/* 254 */       StringBuffer sb = new StringBuffer();
/* 255 */       for (int i = 0; i < a.length; i++) {
/* 256 */         if (i > 0) {
/* 257 */           sb.append(' ');
/*     */         }
/* 259 */         sb.append(Byte.toString(a[i]));
/*     */       } 
/* 261 */       return sb.toString();
/* 262 */     }  if (obj instanceof String[]) {
/* 263 */       String[] a = (String[])obj;
/* 264 */       StringBuffer sb = new StringBuffer();
/* 265 */       for (int i = 0; i < a.length; i++) {
/* 266 */         if (i > 0) {
/* 267 */           sb.append(' ');
/*     */         }
/* 269 */         sb.append(a[i]);
/*     */       } 
/* 271 */       return sb.toString();
/* 272 */     }  if (obj instanceof ArrayList) {
/* 273 */       ArrayList a = (ArrayList)obj;
/* 274 */       StringBuffer sb = new StringBuffer();
/* 275 */       boolean first = true;
/* 276 */       for (Iterator iter = a.iterator(); iter.hasNext(); ) {
/* 277 */         Object element = iter.next();
/* 278 */         if (!first) {
/* 279 */           sb.append(' ');
/*     */         }
/* 281 */         sb.append(Integer.toString(getId(element)));
/* 282 */         first = false;
/*     */       } 
/* 284 */       return sb.toString();
/* 285 */     }  if (obj instanceof HashSet) {
/* 286 */       HashSet s = (HashSet)obj;
/* 287 */       StringBuffer sb = new StringBuffer();
/* 288 */       boolean first = true;
/* 289 */       for (Iterator iter = s.iterator(); iter.hasNext(); ) {
/* 290 */         Object element = iter.next();
/* 291 */         if (!first) {
/* 292 */           sb.append(' ');
/*     */         }
/* 294 */         sb.append(Integer.toString(getId(element)));
/* 295 */         first = false;
/*     */       } 
/* 297 */       return sb.toString();
/* 298 */     }  if (obj instanceof HashMap) {
/* 299 */       HashMap m = (HashMap)obj;
/* 300 */       StringBuffer sb = new StringBuffer();
/* 301 */       boolean first = true;
/* 302 */       for (Iterator<Map.Entry> iter = m.entrySet().iterator(); iter.hasNext(); ) {
/* 303 */         Map.Entry entry = iter.next();
/* 304 */         if (!first) {
/* 305 */           sb.append(' ');
/*     */         }
/* 307 */         sb.append(Integer.toString(getId(entry.getKey())));
/* 308 */         sb.append(' ');
/* 309 */         sb.append(Integer.toString(getId(entry.getValue())));
/* 310 */         first = false;
/*     */       } 
/* 312 */       return sb.toString();
/* 313 */     }  if (obj instanceof GregorianCalendar)
/*     */       try {
/* 315 */         return XSDDateTimeCalendarEncoder.getInstance().objectToString(obj, null);
/*     */       }
/* 317 */       catch (Exception e) {
/* 318 */         failUnsupportedClass(obj.getClass());
/*     */         
/* 320 */         return "UNKOWN";
/*     */       }  
/* 322 */     failUnsupportedClass(obj.getClass());
/*     */ 
/*     */     
/* 325 */     return "UNKOWN";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void define(Object obj, Integer id) {
/* 330 */     this.writer.startElement(getDefineObjectName());
/* 331 */     this.writer.writeAttribute("id", id.toString());
/* 332 */     this.writer.writeAttribute("type", obj.getClass().getName());
/* 333 */     this.writer.endElement();
/* 334 */     this.obj2serialize.add(obj);
/* 335 */     this.obj2serializeStack.push(obj);
/*     */   }
/*     */   
/*     */   protected void property(String name, Object subject, Object object) {
/* 339 */     int sid = getId(subject);
/* 340 */     int oid = getId(object);
/* 341 */     this.writer.startElement(getPropertyName());
/* 342 */     this.writer.writeAttribute("name", name);
/* 343 */     this.writer.writeAttribute("subject", Integer.toString(sid));
/* 344 */     this.writer.writeAttribute("value", Integer.toString(oid));
/* 345 */     this.writer.endElement();
/*     */   }
/*     */   
/*     */   protected Integer newId() {
/* 349 */     return new Integer(this.nextId++);
/*     */   }
/*     */   
/*     */   protected abstract QName getContainerName();
/*     */   
/*     */   protected String getVersion() {
/* 355 */     return null;
/*     */   }
/*     */   
/*     */   protected QName getDefineObjectName() {
/* 359 */     return DEF_OBJ_NAME;
/*     */   }
/*     */   
/*     */   protected QName getDefineImmediateObjectName() {
/* 363 */     return DEF_IMM_OBJ_NAME;
/*     */   }
/*     */   
/*     */   protected QName getPropertyName() {
/* 367 */     return PROP_NAME;
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
/* 383 */   protected static final QName DEF_OBJ_NAME = new QName("object");
/* 384 */   protected static final QName DEF_IMM_OBJ_NAME = new QName("iobject");
/* 385 */   protected static final QName PROP_NAME = new QName("property");
/*     */   protected static final String ATTR_VERSION = "version";
/*     */   protected static final String ATTR_ID = "id";
/*     */   protected static final String ATTR_NAME = "name";
/*     */   protected static final String ATTR_TYPE = "type";
/*     */   protected static final String ATTR_VALUE = "value";
/*     */   protected static final String ATTR_SUBJECT = "subject";
/*     */   
/*     */   protected abstract void failUnsupportedClass(Class paramClass);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\exporter\ExporterBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */