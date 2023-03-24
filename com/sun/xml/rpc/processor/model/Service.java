/*     */ package com.sun.xml.rpc.processor.model;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*     */ import com.sun.xml.rpc.spi.model.JavaInterface;
/*     */ import com.sun.xml.rpc.spi.model.Service;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class Service
/*     */   extends ModelObject
/*     */   implements Service
/*     */ {
/*     */   private QName name;
/*     */   
/*     */   public Service() {}
/*     */   
/*     */   public Service(QName name, JavaInterface javaInterface) {
/*  49 */     this.name = name;
/*  50 */     this.javaInterface = javaInterface;
/*     */   }
/*     */   
/*     */   public QName getName() {
/*  54 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(QName n) {
/*  58 */     this.name = n;
/*     */   }
/*     */   
/*     */   public void addPort(Port port) {
/*  62 */     if (this.portsByName.containsKey(port.getName())) {
/*  63 */       throw new ModelException("model.uniqueness");
/*     */     }
/*  65 */     this.ports.add(port);
/*  66 */     this.portsByName.put(port.getName(), port);
/*     */   }
/*     */   
/*     */   public Iterator getPorts() {
/*  70 */     return this.ports.iterator();
/*     */   }
/*     */   
/*     */   public Port getPortByName(QName n) {
/*  74 */     if (this.portsByName.size() != this.ports.size()) {
/*  75 */       initializePortsByName();
/*     */     }
/*  77 */     return (Port)this.portsByName.get(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public List getPortsList() {
/*  82 */     return this.ports;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPortsList(List m) {
/*  87 */     this.ports = m;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializePortsByName() {
/*  92 */     this.portsByName = new HashMap<Object, Object>();
/*  93 */     if (this.ports != null) {
/*  94 */       for (Iterator<Port> iter = this.ports.iterator(); iter.hasNext(); ) {
/*  95 */         Port port = iter.next();
/*  96 */         if (port.getName() != null && this.portsByName.containsKey(port.getName()))
/*     */         {
/*     */           
/*  99 */           throw new ModelException("model.uniqueness");
/*     */         }
/* 101 */         this.portsByName.put(port.getName(), port);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public JavaInterface getJavaIntf() {
/* 107 */     return (JavaInterface)getJavaInterface();
/*     */   }
/*     */   
/*     */   public JavaInterface getJavaInterface() {
/* 111 */     return this.javaInterface;
/*     */   }
/*     */   
/*     */   public void setJavaInterface(JavaInterface i) {
/* 115 */     this.javaInterface = i;
/*     */   }
/*     */   
/*     */   public void accept(ModelVisitor visitor) throws Exception {
/* 119 */     visitor.visit(this);
/*     */   }
/*     */ 
/*     */   
/* 123 */   private List ports = new ArrayList();
/* 124 */   private Map portsByName = new HashMap<Object, Object>();
/*     */   private JavaInterface javaInterface;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\Service.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */