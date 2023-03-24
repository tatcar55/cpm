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
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Stack;
/*     */ import java.util.TreeSet;
/*     */ import java.util.Vector;
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
/*     */ public class CollectionInterfaceSerializer
/*     */   extends InterfaceSerializerBase
/*     */   implements Initializable, InternalEncodingConstants
/*     */ {
/*     */   private CombinedSerializer vectorSerializer;
/*     */   private CombinedSerializer stackSerializer;
/*     */   private CombinedSerializer linkedListSerializer;
/*     */   private CombinedSerializer arrayListSerializer;
/*     */   private CombinedSerializer hashSetSerializer;
/*     */   private CombinedSerializer treeSetSerializer;
/*  66 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  70 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionInterfaceSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  80 */     this(type, encodeType, isNullable, encodingStyle, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CollectionInterfaceSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, SOAPVersion ver) {
/*  90 */     super(type, encodeType, isNullable, encodingStyle);
/*  91 */     init(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/*  97 */     this.vectorSerializer = (CombinedSerializer)registry.getSerializer(this.encodingStyle, Vector.class, QNAME_TYPE_VECTOR);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     this.vectorSerializer = this.vectorSerializer.getInnermostSerializer();
/* 103 */     this.stackSerializer = (CombinedSerializer)registry.getSerializer(this.encodingStyle, Stack.class, QNAME_TYPE_STACK);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     this.stackSerializer = this.stackSerializer.getInnermostSerializer();
/* 109 */     this.linkedListSerializer = (CombinedSerializer)registry.getSerializer(this.encodingStyle, LinkedList.class, QNAME_TYPE_LINKED_LIST);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     this.linkedListSerializer = this.linkedListSerializer.getInnermostSerializer();
/* 115 */     this.arrayListSerializer = (CombinedSerializer)registry.getSerializer(this.encodingStyle, ArrayList.class, QNAME_TYPE_ARRAY_LIST);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     this.arrayListSerializer = this.arrayListSerializer.getInnermostSerializer();
/* 121 */     this.hashSetSerializer = (CombinedSerializer)registry.getSerializer(this.encodingStyle, HashSet.class, QNAME_TYPE_HASH_SET);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.hashSetSerializer = this.hashSetSerializer.getInnermostSerializer();
/* 127 */     this.treeSetSerializer = (CombinedSerializer)registry.getSerializer(this.encodingStyle, TreeSet.class, QNAME_TYPE_TREE_SET);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     this.treeSetSerializer = this.treeSetSerializer.getInnermostSerializer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object doDeserialize(QName name, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 141 */     QName elementType = getType(reader);
/* 142 */     if (elementType.equals(QNAME_TYPE_COLLECTION) || elementType.equals(QNAME_TYPE_LIST) || elementType.equals(QNAME_TYPE_ARRAY_LIST))
/*     */     {
/*     */       
/* 145 */       return this.arrayListSerializer.deserialize(name, reader, context); } 
/* 146 */     if (elementType.equals(QNAME_TYPE_LINKED_LIST))
/* 147 */       return this.linkedListSerializer.deserialize(name, reader, context); 
/* 148 */     if (elementType.equals(QNAME_TYPE_VECTOR))
/* 149 */       return this.vectorSerializer.deserialize(name, reader, context); 
/* 150 */     if (elementType.equals(QNAME_TYPE_STACK))
/* 151 */       return this.stackSerializer.deserialize(name, reader, context); 
/* 152 */     if (elementType.equals(QNAME_TYPE_SET) || elementType.equals(QNAME_TYPE_HASH_SET))
/*     */     {
/*     */       
/* 155 */       return this.hashSetSerializer.deserialize(name, reader, context); } 
/* 156 */     if (elementType.equals(QNAME_TYPE_TREE_SET)) {
/* 157 */       return this.treeSetSerializer.deserialize(name, reader, context);
/*     */     }
/* 159 */     throw new DeserializationException("soap.unexpectedElementType", new Object[] { "", elementType.toString() });
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
/* 172 */     if (obj instanceof Stack) {
/* 173 */       this.stackSerializer.serialize(obj, name, callback, writer, context);
/* 174 */     } else if (obj instanceof Vector) {
/* 175 */       this.vectorSerializer.serialize(obj, name, callback, writer, context);
/* 176 */     } else if (obj instanceof ArrayList) {
/* 177 */       this.arrayListSerializer.serialize(obj, name, callback, writer, context);
/* 178 */     } else if (obj instanceof LinkedList) {
/* 179 */       this.linkedListSerializer.serialize(obj, name, callback, writer, context);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 185 */     else if (obj instanceof HashSet) {
/* 186 */       this.hashSetSerializer.serialize(obj, name, callback, writer, context);
/* 187 */     } else if (obj instanceof TreeSet) {
/* 188 */       this.treeSetSerializer.serialize(obj, name, callback, writer, context);
/* 189 */     } else if (obj instanceof java.util.Set) {
/* 190 */       this.hashSetSerializer.serialize(obj, name, callback, writer, context);
/* 191 */     } else if (obj instanceof java.util.List || obj instanceof java.util.Collection) {
/* 192 */       this.arrayListSerializer.serialize(obj, name, callback, writer, context);
/*     */     } else {
/* 194 */       throw new SerializationException("soap.cannot.serialize.type", obj.getClass().getName());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\CollectionInterfaceSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */