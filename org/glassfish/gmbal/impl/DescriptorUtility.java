/*    */ package org.glassfish.gmbal.impl;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.SortedMap;
/*    */ import java.util.TreeMap;
/*    */ import javax.management.Descriptor;
/*    */ import javax.management.modelmbean.DescriptorSupport;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DescriptorUtility
/*    */ {
/* 53 */   public static final Descriptor EMPTY_DESCRIPTOR = makeDescriptor(new HashMap<String, Object>());
/*    */ 
/*    */   
/*    */   public static Descriptor makeDescriptor(Map<String, ?> fields) {
/* 57 */     if (fields == null) {
/* 58 */       throw Exceptions.self.nullMap();
/*    */     }
/* 60 */     SortedMap<String, Object> map = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
/*    */     
/* 62 */     for (Map.Entry<String, ?> entry : fields.entrySet()) {
/* 63 */       String name = entry.getKey();
/* 64 */       if (name == null || name.equals("")) {
/* 65 */         throw Exceptions.self.badFieldName();
/*    */       }
/* 67 */       if (map.containsKey(name)) {
/* 68 */         throw Exceptions.self.duplicateFieldName(name);
/*    */       }
/* 70 */       map.put(name, entry.getValue());
/*    */     } 
/* 72 */     int size = map.size();
/* 73 */     String[] names = (String[])map.keySet().toArray((Object[])new String[size]);
/* 74 */     Object[] values = map.values().toArray(new Object[size]);
/* 75 */     return new DescriptorSupport(names, values);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Descriptor union(Descriptor... descriptors) {
/* 81 */     Map<String, Object> map = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
/*    */     
/* 83 */     for (Descriptor d : descriptors) {
/* 84 */       if (d != null) {
/* 85 */         String[] names = d.getFieldNames();
/* 86 */         for (String n : names) {
/* 87 */           Object v = d.getFieldValue(n);
/* 88 */           map.put(n, v);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 93 */     return makeDescriptor(map);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\DescriptorUtility.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */