/*     */ package org.glassfish.gmbal.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanInfo;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.modelmbean.ModelMBeanInfoSupport;
/*     */ import org.glassfish.gmbal.AMXClient;
/*     */ import org.glassfish.gmbal.AMXMBeanInterface;
/*     */ import org.glassfish.gmbal.generic.Algorithms;
/*     */ import org.glassfish.gmbal.generic.UnaryFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AMXImpl
/*     */   implements AMXMBeanInterface
/*     */ {
/*     */   private MBeanImpl mbean;
/*     */   
/*     */   public AMXImpl(MBeanImpl mb) {
/*  68 */     this.mbean = mb;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  72 */     return this.mbean.name();
/*     */   }
/*     */   public Map<String, ?> getMeta() {
/*     */     Descriptor desc;
/*  76 */     MBeanInfo mbi = this.mbean.getMBeanInfo();
/*  77 */     ModelMBeanInfoSupport mmbi = (ModelMBeanInfoSupport)mbi;
/*     */     
/*     */     try {
/*  80 */       desc = mmbi.getMBeanDescriptor();
/*  81 */     } catch (MBeanException ex) {
/*  82 */       throw Exceptions.self.excForGetMeta(ex);
/*     */     } 
/*  84 */     Map<String, Object> result = new HashMap<String, Object>();
/*  85 */     for (String key : desc.getFieldNames()) {
/*  86 */       result.put(key, desc.getFieldValue(key));
/*     */     }
/*  88 */     return result;
/*     */   }
/*     */   
/*     */   public AMXMBeanInterface getParent() {
/*  92 */     MBeanImpl parent = this.mbean.parent();
/*  93 */     if (parent != null) {
/*  94 */       return parent.<AMXMBeanInterface>facet(AMXMBeanInterface.class, false);
/*     */     }
/*  96 */     ManagedObjectManagerInternal mom = this.mbean.skeleton().mom();
/*  97 */     ObjectName rpn = mom.getRootParentName();
/*  98 */     if (rpn == null) {
/*  99 */       return null;
/*     */     }
/* 101 */     return (AMXMBeanInterface)new AMXClient(mom.getMBeanServer(), rpn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AMXMBeanInterface[] getChildren() {
/* 107 */     List<AMXMBeanInterface> children = getContained(this.mbean.children().keySet());
/* 108 */     return children.<AMXMBeanInterface>toArray(new AMXMBeanInterface[children.size()]);
/*     */   }
/*     */   
/* 111 */   private static UnaryFunction<MBeanImpl, AMXMBeanInterface> extract = new UnaryFunction<MBeanImpl, AMXMBeanInterface>()
/*     */     {
/*     */       public AMXMBeanInterface evaluate(MBeanImpl mb)
/*     */       {
/* 115 */         return mb.<AMXMBeanInterface>facet(AMXMBeanInterface.class, false);
/*     */       }
/*     */     };
/*     */   
/*     */   private List<AMXMBeanInterface> getContained(Set<String> types) {
/* 120 */     List<AMXMBeanInterface> result = new ArrayList<AMXMBeanInterface>();
/* 121 */     for (String str : types) {
/* 122 */       result.addAll(Arrays.asList(getContained(str)));
/*     */     }
/* 124 */     return result;
/*     */   }
/*     */   
/*     */   private AMXMBeanInterface[] getContained(String type) {
/* 128 */     Collection<AMXMBeanInterface> children = Algorithms.map(this.mbean.children().get(type), extract).values();
/*     */     
/* 130 */     return children.<AMXMBeanInterface>toArray(new AMXMBeanInterface[children.size()]);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\AMXImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */