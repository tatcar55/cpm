/*     */ package com.sun.xml.rpc.processor.model;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorUtil;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public class Fault
/*     */   extends ModelObject
/*     */ {
/*     */   private String name;
/*     */   private Block block;
/*     */   private JavaException javaException;
/*     */   private Fault parentFault;
/*     */   
/*     */   public Fault() {}
/*     */   
/*     */   public Fault(String name) {
/*  48 */     this.name = name;
/*  49 */     this.parentFault = null;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  53 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String s) {
/*  57 */     this.name = s;
/*     */   }
/*     */   
/*     */   public Block getBlock() {
/*  61 */     return this.block;
/*     */   }
/*     */   
/*     */   public void setBlock(Block b) {
/*  65 */     this.block = b;
/*     */   }
/*     */   
/*     */   public JavaException getJavaException() {
/*  69 */     return this.javaException;
/*     */   }
/*     */   
/*     */   public void setJavaException(JavaException e) {
/*  73 */     this.javaException = e;
/*     */   }
/*     */   
/*     */   public void accept(ModelVisitor visitor) throws Exception {
/*  77 */     visitor.visit(this);
/*     */   }
/*     */   
/*     */   public Fault getParentFault() {
/*  81 */     return this.parentFault;
/*     */   }
/*     */   
/*     */   public void setParentFault(Fault parentFault) {
/*  85 */     if (this.parentFault != null && parentFault != null && !this.parentFault.equals(parentFault))
/*     */     {
/*     */ 
/*     */       
/*  89 */       throw new ModelException("model.parent.fault.already.set", new Object[] { getName().toString(), this.parentFault.getName().toString(), parentFault.getName().toString() });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.parentFault = parentFault;
/*     */   }
/*     */   
/*     */   public void addSubfault(Fault fault) {
/* 100 */     this.subfaults.add(fault);
/* 101 */     fault.setParentFault(this);
/*     */   }
/*     */   
/*     */   public Iterator getSubfaults() {
/* 105 */     if (this.subfaults.size() == 0) {
/* 106 */       return null;
/*     */     }
/* 108 */     return this.subfaults.iterator();
/*     */   }
/*     */   
/*     */   public Iterator getSortedSubfaults() {
/* 112 */     Set sortedFaults = new TreeSet((Comparator<?>)new GeneratorUtil.FaultComparator());
/* 113 */     sortedFaults.addAll(this.subfaults);
/* 114 */     return sortedFaults.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getSubfaultsSet() {
/* 119 */     return this.subfaults;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSubfaultsSet(Set s) {
/* 124 */     this.subfaults = s;
/*     */   }
/*     */   
/*     */   public Iterator getAllFaults() {
/* 128 */     Set allFaults = getAllFaultsSet();
/* 129 */     if (allFaults.size() == 0) {
/* 130 */       return null;
/*     */     }
/* 132 */     return allFaults.iterator();
/*     */   }
/*     */   
/*     */   public Set getAllFaultsSet() {
/* 136 */     Set transSet = new HashSet();
/* 137 */     Iterator<Fault> iter = this.subfaults.iterator();
/* 138 */     while (iter.hasNext()) {
/* 139 */       transSet.addAll(((Fault)iter.next()).getAllFaultsSet());
/*     */     }
/* 141 */     transSet.addAll(this.subfaults);
/* 142 */     return transSet;
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/* 146 */     return this.elementName;
/*     */   }
/*     */   
/*     */   public void setElementName(QName elementName) {
/* 150 */     this.elementName = elementName;
/*     */   }
/*     */   
/*     */   public String getJavaMemberName() {
/* 154 */     return this.javaMemberName;
/*     */   }
/*     */   
/*     */   public void setJavaMemberName(String javaMemberName) {
/* 158 */     this.javaMemberName = javaMemberName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   private Set subfaults = new HashSet();
/* 167 */   private QName elementName = null;
/* 168 */   private String javaMemberName = null;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\Fault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */