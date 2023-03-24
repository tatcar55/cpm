/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPConstants;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.RmiUtils;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPNamespaceConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.soap.SOAPWSDLConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import java.io.IOException;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeneratorUtil
/*     */ {
/*  57 */   private static QName QNAME_SOAP_FAULT = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String MUST_UNDERSTAND_FAULT_MESSAGE_STRING = "SOAP must understand error";
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static SOAPNamespaceConstants soapNamespaceConstants = null;
/*     */   
/*  68 */   private static SOAPWSDLConstants soapWSDLConstants = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected GeneratorUtil() {
/*  80 */     init(SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   protected GeneratorUtil(SOAPVersion ver) {
/*  84 */     init(ver);
/*     */   }
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  88 */     soapNamespaceConstants = SOAPConstantsFactory.getSOAPNamespaceConstants(ver);
/*     */     
/*  90 */     soapWSDLConstants = SOAPConstantsFactory.getSOAPWSDLConstants(ver);
/*  91 */     soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getQNameConstant(QName name) {
/*  97 */     return (String)typeMap.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeNewQName(IndentingWriter p, QName name) throws IOException {
/* 102 */     String qnameConstant = getQNameConstant(name);
/* 103 */     if (qnameConstant != null) {
/* 104 */       p.p(qnameConstant);
/*     */     } else {
/* 106 */       p.p("new QName(\"" + name.getNamespaceURI() + "\", \"" + name.getLocalPart() + "\")");
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
/*     */   public static void writeBlockQNameDeclaration(IndentingWriter p, Operation operation, Block block, Names names) throws IOException {
/* 121 */     String qname = names.getBlockQNameName(operation, block);
/* 122 */     p.p("private static final javax.xml.namespace.QName ");
/* 123 */     p.p(qname + " = ");
/* 124 */     writeNewQName(p, block.getName());
/* 125 */     p.pln(";");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeQNameDeclaration(IndentingWriter p, QName name, Names names) throws IOException {
/* 133 */     String qname = names.getQNameName(name);
/* 134 */     p.p("private static final javax.xml.namespace.QName ");
/* 135 */     p.p(qname + " = ");
/* 136 */     writeNewQName(p, name);
/* 137 */     p.pln(";");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeQNameTypeDeclaration(IndentingWriter p, QName name, Names names) throws IOException {
/* 145 */     String qname = names.getTypeQName(name);
/* 146 */     p.p("private static final javax.xml.namespace.QName ");
/* 147 */     p.p(qname + " = ");
/* 148 */     writeNewQName(p, name);
/* 149 */     p.pln(";");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean classExists(ProcessorEnvironment env, String className) {
/*     */     try {
/* 157 */       String name = RmiUtils.getLoadableClassName(className, env.getClassLoader());
/*     */       
/* 159 */       return true;
/* 160 */     } catch (ClassNotFoundException ce) {
/*     */       
/* 162 */       return false;
/*     */     } 
/*     */   }
/* 165 */   public static Hashtable ht = null; private static Map typeMap;
/*     */   
/*     */   static {
/* 168 */     ht = new Hashtable<Object, Object>();
/* 169 */     ht.put("int", "Integer.TYPE");
/* 170 */     ht.put("boolean", "Boolean.TYPE");
/* 171 */     ht.put("char", "Character.TYPE");
/* 172 */     ht.put("byte", "Byte.TYPE");
/* 173 */     ht.put("short", "Short.TYPE");
/* 174 */     ht.put("long", "Long.TYPE");
/* 175 */     ht.put("float", "Float.TYPE");
/* 176 */     ht.put("double", "Double.TYPE");
/* 177 */     ht.put("void", "Void.TYPE");
/*     */ 
/*     */     
/* 180 */     ht.put("int[]", "I");
/* 181 */     ht.put("boolean[]", "Z");
/* 182 */     ht.put("char[]", "C");
/* 183 */     ht.put("byte[]", "B");
/* 184 */     ht.put("short[]", "S");
/* 185 */     ht.put("long[]", "J");
/* 186 */     ht.put("float[]", "F");
/* 187 */     ht.put("double[]", "D");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     typeMap = new HashMap<Object, Object>();
/*     */     
/* 195 */     typeMap.put(SchemaConstants.QNAME_TYPE_STRING, "SchemaConstants.QNAME_TYPE_STRING");
/*     */ 
/*     */     
/* 198 */     typeMap.put(SchemaConstants.QNAME_TYPE_NORMALIZED_STRING, "SchemaConstants.QNAME_TYPE_NORMALIZED_STRING");
/*     */ 
/*     */     
/* 201 */     typeMap.put(SchemaConstants.QNAME_TYPE_TOKEN, "SchemaConstants.QNAME_TYPE_TOKEN");
/*     */ 
/*     */     
/* 204 */     typeMap.put(SchemaConstants.QNAME_TYPE_BYTE, "SchemaConstants.QNAME_TYPE_BYTE");
/*     */ 
/*     */     
/* 207 */     typeMap.put(SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE, "SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE");
/*     */ 
/*     */     
/* 210 */     typeMap.put(SchemaConstants.QNAME_TYPE_BASE64_BINARY, "SchemaConstants.QNAME_TYPE_BASE64_BINARY");
/*     */ 
/*     */     
/* 213 */     typeMap.put(SchemaConstants.QNAME_TYPE_HEX_BINARY, "SchemaConstants.QNAME_TYPE_HEX_BINARY");
/*     */ 
/*     */     
/* 216 */     typeMap.put(SchemaConstants.QNAME_TYPE_INTEGER, "SchemaConstants.QNAME_TYPE_INTEGER");
/*     */ 
/*     */     
/* 219 */     typeMap.put(SchemaConstants.QNAME_TYPE_POSITIVE_INTEGER, "SchemaConstants.QNAME_TYPE_POSITIVE_INTEGER");
/*     */ 
/*     */     
/* 222 */     typeMap.put(SchemaConstants.QNAME_TYPE_NEGATIVE_INTEGER, "SchemaConstants.QNAME_TYPE_NEGATIVE_INTEGER");
/*     */ 
/*     */     
/* 225 */     typeMap.put(SchemaConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER, "SchemaConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER");
/*     */ 
/*     */     
/* 228 */     typeMap.put(SchemaConstants.QNAME_TYPE_NON_POSITIVE_INTEGER, "SchemaConstants.QNAME_TYPE_NON_POSITIVE_INTEGER");
/*     */ 
/*     */     
/* 231 */     typeMap.put(SchemaConstants.QNAME_TYPE_INT, "SchemaConstants.QNAME_TYPE_INT");
/*     */ 
/*     */     
/* 234 */     typeMap.put(SchemaConstants.QNAME_TYPE_UNSIGNED_INT, "SchemaConstants.QNAME_TYPE_UNSIGNED_INT");
/*     */ 
/*     */     
/* 237 */     typeMap.put(SchemaConstants.QNAME_TYPE_LONG, "SchemaConstants.QNAME_TYPE_LONG");
/*     */ 
/*     */     
/* 240 */     typeMap.put(SchemaConstants.QNAME_TYPE_UNSIGNED_LONG, "SchemaConstants.QNAME_TYPE_UNSIGNED_LONG");
/*     */ 
/*     */     
/* 243 */     typeMap.put(SchemaConstants.QNAME_TYPE_SHORT, "SchemaConstants.QNAME_TYPE_SHORT");
/*     */ 
/*     */     
/* 246 */     typeMap.put(SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT, "SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT");
/*     */ 
/*     */     
/* 249 */     typeMap.put(SchemaConstants.QNAME_TYPE_DECIMAL, "SchemaConstants.QNAME_TYPE_DECIMAL");
/*     */ 
/*     */     
/* 252 */     typeMap.put(SchemaConstants.QNAME_TYPE_FLOAT, "SchemaConstants.QNAME_TYPE_FLOAT");
/*     */ 
/*     */     
/* 255 */     typeMap.put(SchemaConstants.QNAME_TYPE_DOUBLE, "SchemaConstants.QNAME_TYPE_DOUBLE");
/*     */ 
/*     */     
/* 258 */     typeMap.put(SchemaConstants.QNAME_TYPE_BOOLEAN, "SchemaConstants.QNAME_TYPE_BOOLEAN");
/*     */ 
/*     */     
/* 261 */     typeMap.put(SchemaConstants.QNAME_TYPE_TIME, "SchemaConstants.QNAME_TYPE_TIME");
/*     */ 
/*     */     
/* 264 */     typeMap.put(SchemaConstants.QNAME_TYPE_DATE_TIME, "SchemaConstants.QNAME_TYPE_DATE_TIME");
/*     */ 
/*     */     
/* 267 */     typeMap.put(SchemaConstants.QNAME_TYPE_DURATION, "SchemaConstants.QNAME_TYPE_DURATION");
/*     */ 
/*     */     
/* 270 */     typeMap.put(SchemaConstants.QNAME_TYPE_DATE, "SchemaConstants.QNAME_TYPE_DATE");
/*     */ 
/*     */     
/* 273 */     typeMap.put(SchemaConstants.QNAME_TYPE_G_MONTH, "SchemaConstants.QNAME_TYPE_G_MONTH");
/*     */ 
/*     */     
/* 276 */     typeMap.put(SchemaConstants.QNAME_TYPE_G_YEAR, "SchemaConstants.QNAME_TYPE_G_YEAR");
/*     */ 
/*     */     
/* 279 */     typeMap.put(SchemaConstants.QNAME_TYPE_G_YEAR_MONTH, "SchemaConstants.QNAME_TYPE_G_YEAR_MONTH");
/*     */ 
/*     */     
/* 282 */     typeMap.put(SchemaConstants.QNAME_TYPE_G_DAY, "SchemaConstants.QNAME_TYPE_G_DAY");
/*     */ 
/*     */     
/* 285 */     typeMap.put(SchemaConstants.QNAME_TYPE_G_MONTH_DAY, "SchemaConstants.QNAME_TYPE_G_MONTH_DAY");
/*     */ 
/*     */     
/* 288 */     typeMap.put(SchemaConstants.QNAME_TYPE_NAME, "SchemaConstants.QNAME_TYPE_NAME");
/*     */ 
/*     */     
/* 291 */     typeMap.put(SchemaConstants.QNAME_TYPE_QNAME, "SchemaConstants.QNAME_TYPE_QNAME");
/*     */ 
/*     */     
/* 294 */     typeMap.put(SchemaConstants.QNAME_TYPE_NCNAME, "SchemaConstants.QNAME_TYPE_NCNAME");
/*     */ 
/*     */     
/* 297 */     typeMap.put(SchemaConstants.QNAME_TYPE_ANY_URI, "SchemaConstants.QNAME_TYPE_ANY_URI");
/*     */ 
/*     */     
/* 300 */     typeMap.put(SchemaConstants.QNAME_TYPE_ID, "SchemaConstants.QNAME_TYPE_ID");
/*     */ 
/*     */     
/* 303 */     typeMap.put(SchemaConstants.QNAME_TYPE_IDREF, "SchemaConstants.QNAME_TYPE_IDREF");
/*     */ 
/*     */     
/* 306 */     typeMap.put(SchemaConstants.QNAME_TYPE_IDREFS, "SchemaConstants.QNAME_TYPE_IDREFS");
/*     */ 
/*     */     
/* 309 */     typeMap.put(SchemaConstants.QNAME_TYPE_ENTITY, "SchemaConstants.QNAME_TYPE_ENTITY");
/*     */ 
/*     */     
/* 312 */     typeMap.put(SchemaConstants.QNAME_TYPE_ENTITIES, "SchemaConstants.QNAME_TYPE_ENTITIES");
/*     */ 
/*     */     
/* 315 */     typeMap.put(SchemaConstants.QNAME_TYPE_NOTATION, "SchemaConstants.QNAME_TYPE_NOTATION");
/*     */ 
/*     */     
/* 318 */     typeMap.put(SchemaConstants.QNAME_TYPE_NMTOKEN, "SchemaConstants.QNAME_TYPE_NMTOKEN");
/*     */ 
/*     */     
/* 321 */     typeMap.put(SchemaConstants.QNAME_TYPE_NMTOKENS, "SchemaConstants.QNAME_TYPE_NMTOKENS");
/*     */ 
/*     */ 
/*     */     
/* 325 */     typeMap.put(SchemaConstants.QNAME_LIST, "SchemaConstants.QNAME_LIST");
/*     */ 
/*     */     
/* 328 */     typeMap.put(SchemaConstants.QNAME_TYPE_URTYPE, "SchemaConstants.QNAME_TYPE_URTYPE");
/*     */ 
/*     */     
/* 331 */     typeMap.put(SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE, "SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE");
/*     */ 
/*     */ 
/*     */     
/* 335 */     typeMap.put(SOAPConstants.QNAME_TYPE_STRING, "SOAPConstants.QNAME_TYPE_STRING");
/*     */ 
/*     */     
/* 338 */     typeMap.put(SOAPConstants.QNAME_TYPE_NORMALIZED_STRING, "SOAPConstants.QNAME_TYPE_NORMALIZED_STRING");
/*     */ 
/*     */     
/* 341 */     typeMap.put(SOAPConstants.QNAME_TYPE_TOKEN, "SOAPConstants.QNAME_TYPE_TOKEN");
/*     */ 
/*     */     
/* 344 */     typeMap.put(SOAPConstants.QNAME_TYPE_BYTE, "SOAPConstants.QNAME_TYPE_BYTE");
/*     */ 
/*     */     
/* 347 */     typeMap.put(SOAPConstants.QNAME_TYPE_UNSIGNED_BYTE, "SOAPConstants.QNAME_TYPE_UNSIGNED_BYTE");
/*     */ 
/*     */     
/* 350 */     typeMap.put(SOAPConstants.QNAME_TYPE_BASE64_BINARY, "SOAPConstants.QNAME_TYPE_BASE64_BINARY");
/*     */ 
/*     */     
/* 353 */     typeMap.put(SOAPConstants.QNAME_TYPE_BASE64, "SOAPConstants.QNAME_TYPE_BASE64");
/*     */ 
/*     */     
/* 356 */     typeMap.put(SOAPConstants.QNAME_TYPE_HEX_BINARY, "SOAPConstants.QNAME_TYPE_HEX_BINARY");
/*     */ 
/*     */     
/* 359 */     typeMap.put(SOAPConstants.QNAME_TYPE_INTEGER, "SOAPConstants.QNAME_TYPE_INTEGER");
/*     */ 
/*     */     
/* 362 */     typeMap.put(SOAPConstants.QNAME_TYPE_POSITIVE_INTEGER, "SOAPConstants.QNAME_TYPE_POSITIVE_INTEGER");
/*     */ 
/*     */     
/* 365 */     typeMap.put(SOAPConstants.QNAME_TYPE_NEGATIVE_INTEGER, "SOAPConstants.QNAME_TYPE_NEGATIVE_INTEGER");
/*     */ 
/*     */     
/* 368 */     typeMap.put(SOAPConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER, "SOAPConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER");
/*     */ 
/*     */     
/* 371 */     typeMap.put(SOAPConstants.QNAME_TYPE_NON_POSITIVE_INTEGER, "SOAPConstants.QNAME_TYPE_NON_POSITIVE_INTEGER");
/*     */ 
/*     */     
/* 374 */     typeMap.put(SOAPConstants.QNAME_TYPE_INT, "SOAPConstants.QNAME_TYPE_INT");
/*     */ 
/*     */     
/* 377 */     typeMap.put(SOAPConstants.QNAME_TYPE_UNSIGNED_INT, "SOAPConstants.QNAME_TYPE_UNSIGNED_INT");
/*     */ 
/*     */     
/* 380 */     typeMap.put(SOAPConstants.QNAME_TYPE_LONG, "SOAPConstants.QNAME_TYPE_LONG");
/*     */ 
/*     */     
/* 383 */     typeMap.put(SOAPConstants.QNAME_TYPE_UNSIGNED_LONG, "SOAPConstants.QNAME_TYPE_UNSIGNED_LONG");
/*     */ 
/*     */     
/* 386 */     typeMap.put(SOAPConstants.QNAME_TYPE_SHORT, "SOAPConstants.QNAME_TYPE_SHORT");
/*     */ 
/*     */     
/* 389 */     typeMap.put(SOAPConstants.QNAME_TYPE_UNSIGNED_SHORT, "SOAPConstants.QNAME_TYPE_UNSIGNED_SHORT");
/*     */ 
/*     */     
/* 392 */     typeMap.put(SOAPConstants.QNAME_TYPE_DECIMAL, "SOAPConstants.QNAME_TYPE_DECIMAL");
/*     */ 
/*     */     
/* 395 */     typeMap.put(SOAPConstants.QNAME_TYPE_FLOAT, "SOAPConstants.QNAME_TYPE_FLOAT");
/*     */ 
/*     */     
/* 398 */     typeMap.put(SOAPConstants.QNAME_TYPE_DOUBLE, "SOAPConstants.QNAME_TYPE_DOUBLE");
/*     */ 
/*     */     
/* 401 */     typeMap.put(SOAPConstants.QNAME_TYPE_BOOLEAN, "SOAPConstants.QNAME_TYPE_BOOLEAN");
/*     */ 
/*     */     
/* 404 */     typeMap.put(SOAPConstants.QNAME_TYPE_TIME, "SOAPConstants.QNAME_TYPE_TIME");
/*     */ 
/*     */     
/* 407 */     typeMap.put(SOAPConstants.QNAME_TYPE_DATE_TIME, "SOAPConstants.QNAME_TYPE_DATE_TIME");
/*     */ 
/*     */     
/* 410 */     typeMap.put(SOAPConstants.QNAME_TYPE_DURATION, "SOAPConstants.QNAME_TYPE_DURATION");
/*     */ 
/*     */     
/* 413 */     typeMap.put(SOAPConstants.QNAME_TYPE_DATE, "SOAPConstants.QNAME_TYPE_DATE");
/*     */ 
/*     */     
/* 416 */     typeMap.put(SOAPConstants.QNAME_TYPE_G_MONTH, "SOAPConstants.QNAME_TYPE_G_MONTH");
/*     */ 
/*     */     
/* 419 */     typeMap.put(SOAPConstants.QNAME_TYPE_G_YEAR, "SOAPConstants.QNAME_TYPE_G_YEAR");
/*     */ 
/*     */     
/* 422 */     typeMap.put(SOAPConstants.QNAME_TYPE_G_YEAR_MONTH, "SOAPConstants.QNAME_TYPE_G_YEAR_MONTH");
/*     */ 
/*     */     
/* 425 */     typeMap.put(SOAPConstants.QNAME_TYPE_G_DAY, "SOAPConstants.QNAME_TYPE_G_DAY");
/*     */ 
/*     */     
/* 428 */     typeMap.put(SOAPConstants.QNAME_TYPE_G_MONTH_DAY, "SOAPConstants.QNAME_TYPE_G_MONTH_DAY");
/*     */ 
/*     */     
/* 431 */     typeMap.put(SOAPConstants.QNAME_TYPE_NAME, "SOAPConstants.QNAME_TYPE_NAME");
/*     */ 
/*     */     
/* 434 */     typeMap.put(SOAPConstants.QNAME_TYPE_QNAME, "SOAPConstants.QNAME_TYPE_QNAME");
/*     */ 
/*     */     
/* 437 */     typeMap.put(SOAPConstants.QNAME_TYPE_NCNAME, "SOAPConstants.QNAME_TYPE_NCNAME");
/*     */ 
/*     */     
/* 440 */     typeMap.put(SOAPConstants.QNAME_TYPE_ANY_URI, "SOAPConstants.QNAME_TYPE_ANY_URI");
/*     */ 
/*     */     
/* 443 */     typeMap.put(SOAPConstants.QNAME_TYPE_ID, "SOAPConstants.QNAME_TYPE_ID");
/* 444 */     typeMap.put(SOAPConstants.QNAME_TYPE_IDREF, "SOAPConstants.QNAME_TYPE_IDREF");
/*     */ 
/*     */     
/* 447 */     typeMap.put(SOAPConstants.QNAME_TYPE_IDREFS, "SOAPConstants.QNAME_TYPE_IDREFS");
/*     */ 
/*     */     
/* 450 */     typeMap.put(SOAPConstants.QNAME_TYPE_ENTITY, "SOAPConstants.QNAME_TYPE_ENTITY");
/*     */ 
/*     */     
/* 453 */     typeMap.put(SOAPConstants.QNAME_TYPE_ENTITIES, "SOAPConstants.QNAME_TYPE_ENTITIES");
/*     */ 
/*     */     
/* 456 */     typeMap.put(SOAPConstants.QNAME_TYPE_NOTATION, "SOAPConstants.QNAME_TYPE_NOTATION");
/*     */ 
/*     */     
/* 459 */     typeMap.put(SOAPConstants.QNAME_TYPE_NMTOKEN, "SOAPConstants.QNAME_TYPE_NMTOKEN");
/*     */ 
/*     */     
/* 462 */     typeMap.put(SOAPConstants.QNAME_TYPE_NMTOKENS, "SOAPConstants.QNAME_TYPE_NMTOKENS");
/*     */ 
/*     */     
/* 465 */     typeMap.put(SOAPConstants.QNAME_MUSTUNDERSTAND, "SOAPConstants.QNAME_MUSTUNDERSTAND");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 470 */     typeMap.put(SOAP12Constants.QNAME_TYPE_STRING, "SOAP12Constants.QNAME_TYPE_STRING");
/*     */ 
/*     */     
/* 473 */     typeMap.put(SOAP12Constants.QNAME_TYPE_NORMALIZED_STRING, "SOAP12Constants.QNAME_TYPE_NORMALIZED_STRING");
/*     */ 
/*     */     
/* 476 */     typeMap.put(SOAP12Constants.QNAME_TYPE_TOKEN, "SOAP12Constants.QNAME_TYPE_TOKEN");
/*     */ 
/*     */     
/* 479 */     typeMap.put(SOAP12Constants.QNAME_TYPE_BYTE, "SOAP12Constants.QNAME_TYPE_BYTE");
/*     */ 
/*     */     
/* 482 */     typeMap.put(SOAP12Constants.QNAME_TYPE_UNSIGNED_BYTE, "SOAP12Constants.QNAME_TYPE_UNSIGNED_BYTE");
/*     */ 
/*     */     
/* 485 */     typeMap.put(SOAP12Constants.QNAME_TYPE_BASE64_BINARY, "SOAP12Constants.QNAME_TYPE_BASE64_BINARY");
/*     */ 
/*     */     
/* 488 */     typeMap.put(SOAP12Constants.QNAME_TYPE_BASE64, "SOAP12Constants.QNAME_TYPE_BASE64");
/*     */ 
/*     */     
/* 491 */     typeMap.put(SOAP12Constants.QNAME_TYPE_HEX_BINARY, "SOAP12Constants.QNAME_TYPE_HEX_BINARY");
/*     */ 
/*     */     
/* 494 */     typeMap.put(SOAP12Constants.QNAME_TYPE_INTEGER, "SOAP12Constants.QNAME_TYPE_INTEGER");
/*     */ 
/*     */     
/* 497 */     typeMap.put(SOAP12Constants.QNAME_TYPE_POSITIVE_INTEGER, "SOAP12Constants.QNAME_TYPE_POSITIVE_INTEGER");
/*     */ 
/*     */     
/* 500 */     typeMap.put(SOAP12Constants.QNAME_TYPE_NEGATIVE_INTEGER, "SOAP12Constants.QNAME_TYPE_NEGATIVE_INTEGER");
/*     */ 
/*     */     
/* 503 */     typeMap.put(SOAP12Constants.QNAME_TYPE_NON_NEGATIVE_INTEGER, "SOAP12Constants.QNAME_TYPE_NON_NEGATIVE_INTEGER");
/*     */ 
/*     */     
/* 506 */     typeMap.put(SOAP12Constants.QNAME_TYPE_NON_POSITIVE_INTEGER, "SOAP12Constants.QNAME_TYPE_NON_POSITIVE_INTEGER");
/*     */ 
/*     */     
/* 509 */     typeMap.put(SOAP12Constants.QNAME_TYPE_INT, "SOAP12Constants.QNAME_TYPE_INT");
/*     */ 
/*     */     
/* 512 */     typeMap.put(SOAP12Constants.QNAME_TYPE_UNSIGNED_INT, "SOAP12Constants.QNAME_TYPE_UNSIGNED_INT");
/*     */ 
/*     */     
/* 515 */     typeMap.put(SOAP12Constants.QNAME_TYPE_LONG, "SOAP12Constants.QNAME_TYPE_LONG");
/*     */ 
/*     */     
/* 518 */     typeMap.put(SOAP12Constants.QNAME_TYPE_UNSIGNED_LONG, "SOAP12Constants.QNAME_TYPE_UNSIGNED_LONG");
/*     */ 
/*     */     
/* 521 */     typeMap.put(SOAP12Constants.QNAME_TYPE_SHORT, "SOAP12Constants.QNAME_TYPE_SHORT");
/*     */ 
/*     */     
/* 524 */     typeMap.put(SOAP12Constants.QNAME_TYPE_UNSIGNED_SHORT, "SOAP12Constants.QNAME_TYPE_UNSIGNED_SHORT");
/*     */ 
/*     */     
/* 527 */     typeMap.put(SOAP12Constants.QNAME_TYPE_DECIMAL, "SOAP12Constants.QNAME_TYPE_DECIMAL");
/*     */ 
/*     */     
/* 530 */     typeMap.put(SOAP12Constants.QNAME_TYPE_FLOAT, "SOAP12Constants.QNAME_TYPE_FLOAT");
/*     */ 
/*     */     
/* 533 */     typeMap.put(SOAP12Constants.QNAME_TYPE_DOUBLE, "SOAP12Constants.QNAME_TYPE_DOUBLE");
/*     */ 
/*     */     
/* 536 */     typeMap.put(SOAP12Constants.QNAME_TYPE_BOOLEAN, "SOAP12Constants.QNAME_TYPE_BOOLEAN");
/*     */ 
/*     */     
/* 539 */     typeMap.put(SOAP12Constants.QNAME_TYPE_TIME, "SOAP12Constants.QNAME_TYPE_TIME");
/*     */ 
/*     */     
/* 542 */     typeMap.put(SOAP12Constants.QNAME_TYPE_DATE_TIME, "SOAP12Constants.QNAME_TYPE_DATE_TIME");
/*     */ 
/*     */     
/* 545 */     typeMap.put(SOAP12Constants.QNAME_TYPE_DURATION, "SOAP12Constants.QNAME_TYPE_DURATION");
/*     */ 
/*     */     
/* 548 */     typeMap.put(SOAP12Constants.QNAME_TYPE_DATE, "SOAP12Constants.QNAME_TYPE_DATE");
/*     */ 
/*     */     
/* 551 */     typeMap.put(SOAP12Constants.QNAME_TYPE_G_MONTH, "SOAP12Constants.QNAME_TYPE_G_MONTH");
/*     */ 
/*     */     
/* 554 */     typeMap.put(SOAP12Constants.QNAME_TYPE_G_YEAR, "SOAP12Constants.QNAME_TYPE_G_YEAR");
/*     */ 
/*     */     
/* 557 */     typeMap.put(SOAP12Constants.QNAME_TYPE_G_YEAR_MONTH, "SOAP12Constants.QNAME_TYPE_G_YEAR_MONTH");
/*     */ 
/*     */     
/* 560 */     typeMap.put(SOAP12Constants.QNAME_TYPE_G_DAY, "SOAP12Constants.QNAME_TYPE_G_DAY");
/*     */ 
/*     */     
/* 563 */     typeMap.put(SOAP12Constants.QNAME_TYPE_G_MONTH_DAY, "SOAP12Constants.QNAME_TYPE_G_MONTH_DAY");
/*     */ 
/*     */     
/* 566 */     typeMap.put(SOAP12Constants.QNAME_TYPE_NAME, "SOAP12Constants.QNAME_TYPE_NAME");
/*     */ 
/*     */     
/* 569 */     typeMap.put(SOAP12Constants.QNAME_TYPE_QNAME, "SOAP12Constants.QNAME_TYPE_QNAME");
/*     */ 
/*     */     
/* 572 */     typeMap.put(SOAP12Constants.QNAME_TYPE_NCNAME, "SOAP12Constants.QNAME_TYPE_NCNAME");
/*     */ 
/*     */     
/* 575 */     typeMap.put(SOAP12Constants.QNAME_TYPE_ANY_URI, "SOAP12Constants.QNAME_TYPE_ANY_URI");
/*     */ 
/*     */     
/* 578 */     typeMap.put(SOAP12Constants.QNAME_TYPE_ID, "SOAP12Constants.QNAME_TYPE_ID");
/*     */ 
/*     */     
/* 581 */     typeMap.put(SOAP12Constants.QNAME_TYPE_IDREF, "SOAP12Constants.QNAME_TYPE_IDREF");
/*     */ 
/*     */     
/* 584 */     typeMap.put(SOAP12Constants.QNAME_TYPE_IDREFS, "SOAP12Constants.QNAME_TYPE_IDREFS");
/*     */ 
/*     */     
/* 587 */     typeMap.put(SOAP12Constants.QNAME_TYPE_ENTITY, "SOAP12Constants.QNAME_TYPE_ENTITY");
/*     */ 
/*     */     
/* 590 */     typeMap.put(SOAP12Constants.QNAME_TYPE_ENTITIES, "SOAP12Constants.QNAME_TYPE_ENTITIES");
/*     */ 
/*     */     
/* 593 */     typeMap.put(SOAP12Constants.QNAME_TYPE_NOTATION, "SOAP12Constants.QNAME_TYPE_NOTATION");
/*     */ 
/*     */     
/* 596 */     typeMap.put(SOAP12Constants.QNAME_TYPE_NMTOKEN, "SOAP12Constants.QNAME_TYPE_NMTOKEN");
/*     */ 
/*     */     
/* 599 */     typeMap.put(SOAP12Constants.QNAME_TYPE_NMTOKENS, "SOAP12Constants.QNAME_TYPE_NMTOKENS");
/*     */ 
/*     */     
/* 602 */     typeMap.put(SOAP12Constants.QNAME_MUSTUNDERSTAND, "SOAP12Constants.QNAME_MUSTUNDERSTAND");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 607 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_IMAGE, "InternalEncodingConstants.QNAME_TYPE_IMAGE");
/*     */ 
/*     */     
/* 610 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_MIME_MULTIPART, "InternalEncodingConstants.QNAME_TYPE_MIME_MULTIPART");
/*     */ 
/*     */     
/* 613 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_SOURCE, "InternalEncodingConstants.QNAME_TYPE_SOURCE");
/*     */ 
/*     */     
/* 616 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_DATA_HANDLER, "InternalEncodingConstants.QNAME_TYPE_DATA_HANDLER");
/*     */ 
/*     */     
/* 619 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_COLLECTION, "InternalEncodingConstants.QNAME_TYPE_COLLECTION");
/*     */ 
/*     */     
/* 622 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_LIST, "InternalEncodingConstants.QNAME_TYPE_LIST");
/*     */ 
/*     */     
/* 625 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_SET, "InternalEncodingConstants.QNAME_TYPE_SET");
/*     */ 
/*     */     
/* 628 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_ARRAY_LIST, "InternalEncodingConstants.QNAME_TYPE_ARRAY_LIST");
/*     */ 
/*     */     
/* 631 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_VECTOR, "InternalEncodingConstants.QNAME_TYPE_VECTOR");
/*     */ 
/*     */     
/* 634 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_STACK, "InternalEncodingConstants.QNAME_TYPE_STACK");
/*     */ 
/*     */     
/* 637 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_LINKED_LIST, "InternalEncodingConstants.QNAME_TYPE_LINKED_LIST");
/*     */ 
/*     */     
/* 640 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_HASH_SET, "InternalEncodingConstants.QNAME_TYPE_HASH_SET");
/*     */ 
/*     */     
/* 643 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_TREE_SET, "InternalEncodingConstants.QNAME_TYPE_TREE_SET");
/*     */ 
/*     */     
/* 646 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_MAP, "InternalEncodingConstants.QNAME_TYPE_MAP");
/*     */ 
/*     */     
/* 649 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_JAX_RPC_MAP_ENTRY, "InternalEncodingConstants.QNAME_TYPE_JAX_RPC_MAP_ENTRY");
/*     */ 
/*     */     
/* 652 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_HASH_MAP, "InternalEncodingConstants.QNAME_TYPE_HASH_MAP");
/*     */ 
/*     */     
/* 655 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_TREE_MAP, "InternalEncodingConstants.QNAME_TYPE_TREE_MAP");
/*     */ 
/*     */     
/* 658 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_HASHTABLE, "InternalEncodingConstants.QNAME_TYPE_HASHTABLE");
/*     */ 
/*     */     
/* 661 */     typeMap.put(InternalEncodingConstants.QNAME_TYPE_PROPERTIES, "InternalEncodingConstants.QNAME_TYPE_PROPERTIES");
/*     */   }
/*     */   
/*     */   public static class FaultComparator
/*     */     implements Comparator {
/*     */     private boolean sortName = false;
/*     */     
/*     */     public FaultComparator() {}
/*     */     
/*     */     public FaultComparator(boolean sortName) {
/* 671 */       this.sortName = sortName;
/*     */     }
/*     */     
/*     */     public int compare(Object o1, Object o2) {
/* 675 */       if (this.sortName) {
/* 676 */         QName name1 = ((Fault)o1).getBlock().getName();
/* 677 */         QName name2 = ((Fault)o2).getBlock().getName();
/*     */         
/* 679 */         if (!name1.equals(name2)) {
/* 680 */           return name1.toString().compareTo(name2.toString());
/*     */         }
/*     */       } 
/* 683 */       JavaException javaException1 = ((Fault)o1).getJavaException();
/* 684 */       JavaException javaException2 = ((Fault)o2).getJavaException();
/* 685 */       int result = sort((JavaStructureType)javaException1, (JavaStructureType)javaException2);
/* 686 */       return result;
/*     */     }
/*     */     
/*     */     protected int sort(JavaStructureType type1, JavaStructureType type2) {
/* 690 */       if (type1.getName().equals(type2.getName())) {
/* 691 */         return 0;
/*     */       }
/*     */       
/* 694 */       JavaStructureType superType = type1.getSuperclass();
/* 695 */       while (superType != null) {
/* 696 */         if (superType.equals(type2)) {
/* 697 */           return -1;
/*     */         }
/* 699 */         superType = superType.getSuperclass();
/*     */       } 
/* 701 */       superType = type2.getSuperclass();
/* 702 */       while (superType != null) {
/* 703 */         if (superType.equals(type1)) {
/* 704 */           return 1;
/*     */         }
/* 706 */         superType = superType.getSuperclass();
/*     */       } 
/* 708 */       if (type1.getSubclasses() == null && type2.getSubclasses() != null)
/* 709 */         return -1; 
/* 710 */       if (type1.getSubclasses() != null && type2.getSubclasses() == null)
/* 711 */         return 1; 
/* 712 */       if (type1.getSuperclass() != null && type2.getSuperclass() == null)
/*     */       {
/* 714 */         return 1;
/*     */       }
/* 716 */       if (type1.getSuperclass() == null && type2.getSuperclass() != null)
/*     */       {
/* 718 */         return -1;
/*     */       }
/* 720 */       return type1.getName().compareTo(type2.getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SubclassComparator
/*     */     implements Comparator
/*     */   {
/*     */     public int compare(Object o1, Object o2) {
/* 729 */       JavaStructureType type1 = (JavaStructureType)o1;
/* 730 */       JavaStructureType type2 = (JavaStructureType)o2;
/* 731 */       return sort(type1, type2);
/*     */     }
/*     */     
/*     */     protected int sort(JavaStructureType type1, JavaStructureType type2) {
/* 735 */       JavaStructureType parent = type1;
/* 736 */       while (parent.getSuperclass() != null) {
/* 737 */         parent = parent.getSuperclass();
/* 738 */         if (parent.equals(type2))
/* 739 */           return -1; 
/*     */       } 
/* 741 */       parent = type2;
/* 742 */       while (parent.getSuperclass() != null) {
/* 743 */         parent = parent.getSuperclass();
/* 744 */         if (parent.equals(type1))
/* 745 */           return 1; 
/*     */       } 
/* 747 */       return type1.getName().compareTo(type2.getName());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\GeneratorUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */