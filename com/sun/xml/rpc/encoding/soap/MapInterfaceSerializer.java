/*     */ package com.sun.xml.rpc.encoding.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.CombinedSerializer;
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.Initializable;
/*     */ import com.sun.xml.rpc.encoding.InterfaceSerializerBase;
/*     */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializationException;
/*     */ import com.sun.xml.rpc.encoding.SerializerCallback;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.TreeMap;
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
/*     */ public class MapInterfaceSerializer
/*     */   extends InterfaceSerializerBase
/*     */   implements Initializable, InternalEncodingConstants
/*     */ {
/*     */   private CombinedSerializer hashMapSerializer;
/*     */   private CombinedSerializer treeMapSerializer;
/*     */   private CombinedSerializer hashtableSerializer;
/*     */   private CombinedSerializer propertiesSerializer;
/*  59 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  63 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapInterfaceSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  73 */     this(type, encodeType, isNullable, encodingStyle, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapInterfaceSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, SOAPVersion ver) {
/*  83 */     super(type, encodeType, isNullable, encodingStyle);
/*  84 */     init(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/*  90 */     this.hashMapSerializer = (CombinedSerializer)registry.getSerializer(this.encodingStyle, HashMap.class, QNAME_TYPE_HASH_MAP);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     this.hashMapSerializer = this.hashMapSerializer.getInnermostSerializer();
/*  96 */     this.treeMapSerializer = (CombinedSerializer)registry.getSerializer(this.encodingStyle, TreeMap.class, QNAME_TYPE_TREE_MAP);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     this.treeMapSerializer = this.treeMapSerializer.getInnermostSerializer();
/* 102 */     this.hashtableSerializer = (CombinedSerializer)registry.getSerializer(this.encodingStyle, Hashtable.class, QNAME_TYPE_HASHTABLE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     this.hashtableSerializer = this.hashtableSerializer.getInnermostSerializer();
/* 108 */     this.propertiesSerializer = (CombinedSerializer)registry.getSerializer(this.encodingStyle, Properties.class, QNAME_TYPE_PROPERTIES);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     this.propertiesSerializer = this.propertiesSerializer.getInnermostSerializer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object doDeserialize(QName name, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 122 */     QName elementType = getType(reader);
/* 123 */     if (elementType.equals(QNAME_TYPE_MAP) || elementType.equals(QNAME_TYPE_HASH_MAP))
/*     */     {
/* 125 */       return this.hashMapSerializer.deserialize(name, reader, context); } 
/* 126 */     if (elementType.equals(QNAME_TYPE_TREE_MAP))
/* 127 */       return this.treeMapSerializer.deserialize(name, reader, context); 
/* 128 */     if (elementType.equals(QNAME_TYPE_HASHTABLE))
/* 129 */       return this.hashtableSerializer.deserialize(name, reader, context); 
/* 130 */     if (elementType.equals(QNAME_TYPE_PROPERTIES)) {
/* 131 */       return this.propertiesSerializer.deserialize(name, reader, context);
/*     */     }
/* 133 */     throw new DeserializationException("soap.unexpectedElementType", new Object[] { "", elementType.toString() });
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
/*     */   public void doSerializeInstance(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 146 */     if (obj instanceof HashMap) {
/* 147 */       this.hashMapSerializer.serialize(obj, name, callback, writer, context);
/* 148 */     } else if (obj instanceof Properties) {
/* 149 */       this.propertiesSerializer.serialize(obj, name, callback, writer, context);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 155 */     else if (obj instanceof Hashtable) {
/* 156 */       this.hashtableSerializer.serialize(obj, name, callback, writer, context);
/* 157 */     } else if (obj instanceof TreeMap) {
/* 158 */       this.treeMapSerializer.serialize(obj, name, callback, writer, context);
/* 159 */     } else if (obj instanceof java.util.Map) {
/* 160 */       this.hashMapSerializer.serialize(obj, name, callback, writer, context);
/*     */     } else {
/* 162 */       throw new SerializationException("soap.cannot.serialize.type", obj.getClass().getName());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\MapInterfaceSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */