/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CollectionSerializerWriter
/*     */   extends SerializerWriterBase
/*     */   implements GeneratorConstants
/*     */ {
/*     */   private String serializerMemberName;
/*     */   private CollectionInfo collectionInfo;
/*     */   private SOAPType dataType;
/*     */   
/*     */   public CollectionSerializerWriter(SOAPType type, Names names) {
/*  56 */     super((AbstractType)type, names);
/*  57 */     this.dataType = type;
/*     */     
/*  59 */     this.collectionInfo = (CollectionInfo)collectionTypes.get(type.getName());
/*  60 */     String serializerName = this.collectionInfo.serializerName;
/*     */ 
/*     */ 
/*     */     
/*  64 */     this.serializerMemberName = names.getClassMemberName(serializerName, (AbstractType)type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createSerializer(IndentingWriter p, StringBuffer typeName, String serName, boolean encodeTypes, boolean multiRefEncoding, String typeMapping) throws IOException {
/*  75 */     SOAPSimpleType type = (SOAPSimpleType)this.type;
/*  76 */     String nillable = type.isNillable() ? "NULLABLE" : "NOT_NULLABLE";
/*  77 */     String referenceable = type.isReferenceable() ? "REFERENCEABLE" : "NOT_REFERENCEABLE";
/*     */ 
/*     */ 
/*     */     
/*  81 */     String multiRef = (multiRefEncoding && type.isReferenceable()) ? "SERIALIZE_AS_REF" : "DONT_SERIALIZE_AS_REF";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     String encodeType = encodeTypes ? "ENCODE_TYPE" : "DONT_ENCODE_TYPE";
/*     */ 
/*     */     
/*  89 */     declareType(p, typeName, type.getName(), false, false);
/*  90 */     if (type.getName().equals(QNAME_TYPE_COLLECTION) || type.getName().equals(QNAME_TYPE_LIST) || type.getName().equals(QNAME_TYPE_SET) || type.getName().equals(QNAME_TYPE_MAP) || type.getName().equals(QNAME_TYPE_JAX_RPC_MAP_ENTRY)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  95 */       p.plnI(serializerName() + " " + serName + " = new " + this.collectionInfo.serializerName + "(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       p.pln(encodeType + ", " + nillable + ", " + getEncodingStyleString() + ");");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       p.pO();
/* 112 */     } else if (type.getName().equals(QNAME_TYPE_HASH_MAP) || type.getName().equals(QNAME_TYPE_TREE_MAP) || type.getName().equals(QNAME_TYPE_HASHTABLE) || type.getName().equals(QNAME_TYPE_PROPERTIES)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       StringBuffer elemName = new StringBuffer("elemName");
/* 118 */       declareType(p, elemName, COLLECTION_ELEMENT_NAME, false, false);
/* 119 */       p.plnI(serializerName() + " " + serName + " = new " + this.collectionInfo.serializerName + "(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       p.pln(this.collectionInfo.collectionClassName + ".class, " + encodeType + ", " + nillable + ", " + getEncodingStyleString() + ", " + getSOAPVersionString() + ");");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       p.pO();
/*     */     } else {
/* 141 */       StringBuffer elemName = new StringBuffer("elemName");
/* 142 */       declareType(p, elemName, COLLECTION_ELEMENT_NAME, false, false);
/* 143 */       StringBuffer elemType = new StringBuffer("elemType");
/* 144 */       declareType(p, elemType, this.collectionInfo.elementTypeName, false, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       p.plnI(serializerName() + " " + serName + " = new " + this.collectionInfo.serializerName + "(" + typeName + ",");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       p.pln(this.collectionInfo.collectionClassName + ".class, " + encodeType + ", " + nillable + ", " + getEncodingStyleString() + ", ");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 168 */       p.pln(elemName + ", " + elemType + ", " + "java.lang.Object" + ".class, " + getSOAPVersionString() + ");");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 177 */       p.pO();
/*     */     } 
/* 179 */     if (type.isReferenceable()) {
/* 180 */       p.plnI(serName + " = new " + "ReferenceableSerializerImpl" + "(" + multiRef + ", " + serName + ", " + getSOAPVersionString() + ");");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       p.pO();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void declareSerializer(IndentingWriter p, boolean isStatic, boolean isFinal) throws IOException {
/* 200 */     String modifier = getPrivateModifier(isStatic, isFinal);
/* 201 */     p.pln(modifier + serializerName() + " " + serializerMemberName() + ";");
/*     */   }
/*     */   
/*     */   public String serializerMemberName() {
/* 205 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   public String deserializerMemberName() {
/* 209 */     return getPrefix((AbstractType)this.dataType) + "_" + this.serializerMemberName;
/*     */   }
/*     */   
/*     */   public static boolean handlesType(AbstractType type) {
/* 213 */     return collectionTypes.containsKey(type.getName());
/*     */   }
/*     */   
/*     */   protected String getPrivateModifier(boolean isStatic, boolean isFinal) {
/* 217 */     return "private " + getModifier(isStatic, isFinal);
/*     */   }
/*     */   
/*     */   public AbstractType getBaseElementType() {
/* 221 */     SOAPType elemType = ((SOAPArrayType)this.type).getElementType();
/* 222 */     while (elemType instanceof SOAPArrayType) {
/* 223 */       elemType = ((SOAPArrayType)elemType).getElementType();
/*     */     }
/* 225 */     return (AbstractType)elemType;
/*     */   }
/*     */   
/* 228 */   private static final Map collectionTypes = new HashMap<Object, Object>();
/*     */ 
/*     */   
/* 231 */   public static final CollectionInfo COLLECTION_INFO = new CollectionInfo("java.util.Collection", "CollectionInterfaceSerializer", SchemaConstants.QNAME_TYPE_URTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   public static final CollectionInfo LIST_INFO = new CollectionInfo("java.util.List", "CollectionInterfaceSerializer", SchemaConstants.QNAME_TYPE_URTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 241 */   public static final CollectionInfo SET_INFO = new CollectionInfo("java.util.Set", "CollectionInterfaceSerializer", SchemaConstants.QNAME_TYPE_URTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 246 */   public static final CollectionInfo VECTOR_INFO = new CollectionInfo("java.util.Vector", "CollectionSerializer", SchemaConstants.QNAME_TYPE_URTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 251 */   public static final CollectionInfo STACK_INFO = new CollectionInfo("java.util.Stack", "CollectionSerializer", SchemaConstants.QNAME_TYPE_URTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   public static final CollectionInfo LINKED_LIST_INFO = new CollectionInfo("java.util.LinkedList", "CollectionSerializer", SchemaConstants.QNAME_TYPE_URTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public static final CollectionInfo ARRAY_LIST_INFO = new CollectionInfo("java.util.ArrayList", "CollectionSerializer", SchemaConstants.QNAME_TYPE_URTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 266 */   public static final CollectionInfo HASH_SET_INFO = new CollectionInfo("java.util.HashSet", "CollectionSerializer", SchemaConstants.QNAME_TYPE_URTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 271 */   public static final CollectionInfo TREE_SET_INFO = new CollectionInfo("java.util.TreeSet", "CollectionSerializer", SchemaConstants.QNAME_TYPE_URTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 277 */   public static final CollectionInfo MAP_INFO = new CollectionInfo("java.util.Map", "MapInterfaceSerializer", SchemaConstants.QNAME_TYPE_URTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 282 */   public static final CollectionInfo HASH_MAP_INFO = new CollectionInfo("java.util.HashMap", "MapSerializer", null);
/*     */   
/* 284 */   public static final CollectionInfo TREE_MAP_INFO = new CollectionInfo("java.util.TreeMap", "MapSerializer", null);
/*     */   
/* 286 */   public static final CollectionInfo HASHTABLE_INFO = new CollectionInfo("java.util.Hashtable", "MapSerializer", null);
/*     */   
/* 288 */   public static final CollectionInfo PROPERTIES_INFO = new CollectionInfo("java.util.Properties", "MapSerializer", null);
/*     */   
/* 290 */   public static final CollectionInfo JAX_RPC_MAP_ENTRY_INFO = new CollectionInfo("com.sun.xml.rpc.encoding.soap.JAXRpcMapEntry", "JAXRpcMapEntrySerializer", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 298 */     collectionTypes.put(QNAME_TYPE_COLLECTION, COLLECTION_INFO);
/* 299 */     collectionTypes.put(QNAME_TYPE_LIST, LIST_INFO);
/* 300 */     collectionTypes.put(QNAME_TYPE_SET, SET_INFO);
/* 301 */     collectionTypes.put(QNAME_TYPE_VECTOR, VECTOR_INFO);
/* 302 */     collectionTypes.put(QNAME_TYPE_STACK, STACK_INFO);
/* 303 */     collectionTypes.put(QNAME_TYPE_LINKED_LIST, LINKED_LIST_INFO);
/* 304 */     collectionTypes.put(QNAME_TYPE_ARRAY_LIST, ARRAY_LIST_INFO);
/* 305 */     collectionTypes.put(QNAME_TYPE_HASH_SET, HASH_SET_INFO);
/* 306 */     collectionTypes.put(QNAME_TYPE_TREE_SET, TREE_SET_INFO);
/*     */     
/* 308 */     collectionTypes.put(QNAME_TYPE_MAP, MAP_INFO);
/* 309 */     collectionTypes.put(QNAME_TYPE_HASH_MAP, HASH_MAP_INFO);
/* 310 */     collectionTypes.put(QNAME_TYPE_TREE_MAP, TREE_MAP_INFO);
/* 311 */     collectionTypes.put(QNAME_TYPE_HASHTABLE, HASHTABLE_INFO);
/* 312 */     collectionTypes.put(QNAME_TYPE_PROPERTIES, PROPERTIES_INFO);
/* 313 */     collectionTypes.put(QNAME_TYPE_JAX_RPC_MAP_ENTRY, JAX_RPC_MAP_ENTRY_INFO);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class CollectionInfo
/*     */   {
/*     */     public String collectionClassName;
/*     */     
/*     */     public String serializerName;
/*     */     
/*     */     public QName elementTypeName;
/*     */ 
/*     */     
/*     */     CollectionInfo(String collectionClassName, String serializerName, QName elementTypeName) {
/* 327 */       this.collectionClassName = collectionClassName;
/* 328 */       this.serializerName = serializerName;
/* 329 */       this.elementTypeName = elementTypeName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\CollectionSerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */