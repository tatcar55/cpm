/*    */ package com.sun.xml.rpc.encoding;
/*    */ 
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface InternalEncodingConstants
/*    */ {
/*    */   public static final String JAXRPC_URI = "http://java.sun.com/jax-rpc-ri/internal";
/* 40 */   public static final QName QNAME_TYPE_IMAGE = new QName("http://java.sun.com/jax-rpc-ri/internal", "image");
/* 41 */   public static final QName QNAME_TYPE_MIME_MULTIPART = new QName("http://java.sun.com/jax-rpc-ri/internal", "multipart");
/* 42 */   public static final QName QNAME_TYPE_SOURCE = new QName("http://java.sun.com/jax-rpc-ri/internal", "text_xml");
/* 43 */   public static final QName QNAME_TYPE_DATA_HANDLER = new QName("http://java.sun.com/jax-rpc-ri/internal", "datahandler");
/*    */ 
/*    */   
/* 46 */   public static final QName ARRAY_ELEMENT_NAME = new QName("item");
/*    */ 
/*    */   
/* 49 */   public static final QName COLLECTION_ELEMENT_NAME = new QName("item");
/* 50 */   public static final QName QNAME_TYPE_COLLECTION = new QName("http://java.sun.com/jax-rpc-ri/internal", "collection");
/* 51 */   public static final QName QNAME_TYPE_LIST = new QName("http://java.sun.com/jax-rpc-ri/internal", "list");
/* 52 */   public static final QName QNAME_TYPE_SET = new QName("http://java.sun.com/jax-rpc-ri/internal", "set");
/* 53 */   public static final QName QNAME_TYPE_ARRAY_LIST = new QName("http://java.sun.com/jax-rpc-ri/internal", "arrayList");
/* 54 */   public static final QName QNAME_TYPE_VECTOR = new QName("http://java.sun.com/jax-rpc-ri/internal", "vector");
/* 55 */   public static final QName QNAME_TYPE_STACK = new QName("http://java.sun.com/jax-rpc-ri/internal", "stack");
/* 56 */   public static final QName QNAME_TYPE_LINKED_LIST = new QName("http://java.sun.com/jax-rpc-ri/internal", "linkedList");
/* 57 */   public static final QName QNAME_TYPE_HASH_SET = new QName("http://java.sun.com/jax-rpc-ri/internal", "hashSet");
/* 58 */   public static final QName QNAME_TYPE_TREE_SET = new QName("http://java.sun.com/jax-rpc-ri/internal", "treeSet");
/*    */ 
/*    */   
/* 61 */   public static final QName JAX_RPC_MAP_ENTRY_KEY_NAME = new QName("key");
/* 62 */   public static final QName JAX_RPC_MAP_ENTRY_VALUE_NAME = new QName("value");
/* 63 */   public static final QName QNAME_TYPE_MAP = new QName("http://java.sun.com/jax-rpc-ri/internal", "map");
/* 64 */   public static final QName QNAME_TYPE_JAX_RPC_MAP_ENTRY = new QName("http://java.sun.com/jax-rpc-ri/internal", "mapEntry");
/* 65 */   public static final QName QNAME_TYPE_HASH_MAP = new QName("http://java.sun.com/jax-rpc-ri/internal", "hashMap");
/* 66 */   public static final QName QNAME_TYPE_TREE_MAP = new QName("http://java.sun.com/jax-rpc-ri/internal", "treeMap");
/* 67 */   public static final QName QNAME_TYPE_HASHTABLE = new QName("http://java.sun.com/jax-rpc-ri/internal", "hashtable");
/* 68 */   public static final QName QNAME_TYPE_PROPERTIES = new QName("http://java.sun.com/jax-rpc-ri/internal", "properties");
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\InternalEncodingConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */