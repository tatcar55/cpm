/*     */ package com.sun.xml.rpc.processor.util;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.ProcessorAction;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Request;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralTypeVisitor;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPTypeVisitor;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Comparator;
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
/*     */ public class CanonicalModelWriter
/*     */   extends ModelWriter
/*     */   implements ProcessorAction, SOAPTypeVisitor, LiteralTypeVisitor
/*     */ {
/*     */   public CanonicalModelWriter(IndentingWriter w) {
/*  74 */     super(w);
/*     */   }
/*     */   
/*     */   public CanonicalModelWriter(OutputStream out) {
/*  78 */     super(out);
/*     */   }
/*     */   
/*     */   public CanonicalModelWriter(File f) throws FileNotFoundException {
/*  82 */     this(new FileOutputStream(f));
/*     */   }
/*     */   
/*     */   public void visit(Model model) throws Exception {
/*  86 */     preVisit(model);
/*  87 */     Set<Service> sortedServices = new TreeSet(new GetNameComparator(Service.class));
/*  88 */     for (Iterator<Service> iterator1 = model.getServices(); iterator1.hasNext(); ) {
/*  89 */       Service service = iterator1.next();
/*  90 */       sortedServices.add(service);
/*     */     } 
/*  92 */     for (Iterator<Service> iter = sortedServices.iterator(); iter.hasNext(); ) {
/*  93 */       Service service = iter.next();
/*  94 */       preVisit(service);
/*  95 */       Set<Port> sortedPorts = new TreeSet(new GetNameComparator(Port.class));
/*  96 */       for (Iterator<Port> iterator2 = service.getPorts(); iterator2.hasNext(); ) {
/*  97 */         Port port = iterator2.next();
/*  98 */         sortedPorts.add(port);
/*     */       } 
/* 100 */       for (Iterator<Port> iter2 = sortedPorts.iterator(); iter2.hasNext(); ) {
/* 101 */         Port port = iter2.next();
/* 102 */         preVisit(port);
/* 103 */         Set<Operation> sortedOperations = new TreeSet(new GetNameComparator(Operation.class));
/*     */         
/* 105 */         for (Iterator<Operation> iterator3 = port.getOperations(); iterator3.hasNext(); ) {
/* 106 */           Operation operation = iterator3.next();
/* 107 */           sortedOperations.add(operation);
/*     */         } 
/* 109 */         Iterator<Operation> iter3 = sortedOperations.iterator();
/* 110 */         while (iter3.hasNext()) {
/*     */           
/* 112 */           Operation operation = iter3.next();
/* 113 */           preVisit(operation);
/* 114 */           Request request = operation.getRequest();
/* 115 */           if (request != null) {
/* 116 */             preVisit(request);
/* 117 */             Set<Block> sortedHeaderBlocks = new TreeSet(new GetNameComparator(Block.class));
/*     */             
/* 119 */             Iterator<Block> iterator6 = request.getHeaderBlocks();
/* 120 */             while (iterator6.hasNext()) {
/*     */               
/* 122 */               Block block = iterator6.next();
/* 123 */               sortedHeaderBlocks.add(block);
/*     */             } 
/* 125 */             Iterator<Block> iterator5 = sortedHeaderBlocks.iterator();
/* 126 */             while (iterator5.hasNext()) {
/*     */               
/* 128 */               Block block = iterator5.next();
/* 129 */               visitHeaderBlock(block);
/*     */             } 
/* 131 */             Set<Block> sortedBodyBlocks = new TreeSet(new GetNameComparator(Block.class));
/*     */             
/* 133 */             Iterator<Block> iterator8 = request.getBodyBlocks();
/* 134 */             while (iterator8.hasNext()) {
/*     */               
/* 136 */               Block block = iterator8.next();
/* 137 */               sortedBodyBlocks.add(block);
/*     */             } 
/* 139 */             Iterator<Block> iterator7 = sortedBodyBlocks.iterator();
/* 140 */             while (iterator7.hasNext()) {
/*     */               
/* 142 */               Block block = iterator7.next();
/* 143 */               visitBodyBlock(block);
/*     */             } 
/* 145 */             Set<Parameter> sortedParams = new TreeSet(new GetNameComparator(Parameter.class));
/*     */             
/* 147 */             Iterator<Parameter> iterator10 = request.getParameters();
/* 148 */             while (iterator10.hasNext()) {
/*     */               
/* 150 */               Parameter parameter = iterator10.next();
/* 151 */               sortedParams.add(parameter);
/*     */             } 
/* 153 */             Iterator<Parameter> iterator9 = sortedParams.iterator();
/* 154 */             while (iterator9.hasNext()) {
/*     */               
/* 156 */               Parameter parameter = iterator9.next();
/* 157 */               visit(parameter);
/*     */             } 
/* 159 */             postVisit(request);
/*     */           } 
/*     */           
/* 162 */           Response response = operation.getResponse();
/* 163 */           if (request != null) {
/* 164 */             preVisit(response);
/* 165 */             Set<Block> sortedHeaderBlocks = new TreeSet(new GetNameComparator(Block.class));
/*     */             
/* 167 */             Iterator<Block> iterator6 = response.getHeaderBlocks();
/* 168 */             while (iterator6.hasNext()) {
/*     */               
/* 170 */               Block block = iterator6.next();
/* 171 */               sortedHeaderBlocks.add(block);
/*     */             } 
/* 173 */             Iterator<Block> iterator5 = sortedHeaderBlocks.iterator();
/* 174 */             while (iterator5.hasNext()) {
/*     */               
/* 176 */               Block block = iterator5.next();
/* 177 */               visitHeaderBlock(block);
/*     */             } 
/* 179 */             Set<Block> sortedBodyBlocks = new TreeSet(new GetNameComparator(Block.class));
/*     */             
/* 181 */             Iterator<Block> iterator8 = response.getBodyBlocks();
/* 182 */             while (iterator8.hasNext()) {
/*     */               
/* 184 */               Block block = iterator8.next();
/* 185 */               sortedBodyBlocks.add(block);
/*     */             } 
/* 187 */             Iterator<Block> iterator7 = sortedBodyBlocks.iterator();
/* 188 */             while (iterator7.hasNext()) {
/*     */               
/* 190 */               Block block = iterator7.next();
/* 191 */               visitBodyBlock(block);
/*     */             } 
/* 193 */             Set<Parameter> sortedParams = new TreeSet(new GetNameComparator(Parameter.class));
/*     */             
/* 195 */             Iterator<Parameter> iterator10 = response.getParameters();
/* 196 */             while (iterator10.hasNext()) {
/*     */               
/* 198 */               Parameter parameter = iterator10.next();
/* 199 */               sortedParams.add(parameter);
/*     */             } 
/* 201 */             Iterator<Parameter> iterator9 = sortedParams.iterator();
/* 202 */             while (iterator9.hasNext()) {
/*     */               
/* 204 */               Parameter parameter = iterator9.next();
/* 205 */               visit(parameter);
/*     */             } 
/* 207 */             postVisit(response);
/*     */           } 
/*     */           
/* 210 */           Set<Fault> sortedFaults = new TreeSet(new GetNameComparator(Operation.class));
/*     */           
/* 212 */           Iterator<Fault> iterator4 = operation.getFaults();
/* 213 */           while (iterator4.hasNext()) {
/*     */             
/* 215 */             Fault fault = iterator4.next();
/* 216 */             sortedFaults.add(fault);
/*     */           } 
/* 218 */           Iterator<Fault> iter4 = sortedFaults.iterator();
/* 219 */           while (iter4.hasNext()) {
/*     */             
/* 221 */             Fault fault = iter4.next();
/* 222 */             preVisit(fault);
/* 223 */             visitFaultBlock(fault.getBlock());
/* 224 */             postVisit(fault);
/*     */           } 
/* 226 */           postVisit(operation);
/*     */         } 
/* 228 */         postVisit(port);
/*     */       } 
/* 230 */       postVisit(service);
/*     */     } 
/* 232 */     postVisit(model);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processTypes(Model model) throws Exception {
/* 237 */     Set<AbstractType> sortedTypes = new TreeSet(new GetNameComparator(AbstractType.class));
/*     */     
/* 239 */     for (Iterator<AbstractType> iterator1 = model.getExtraTypes(); iterator1.hasNext(); ) {
/* 240 */       AbstractType extraType = iterator1.next();
/* 241 */       sortedTypes.add(extraType);
/*     */     } 
/* 243 */     for (Iterator<AbstractType> iter = sortedTypes.iterator(); iter.hasNext(); ) {
/* 244 */       AbstractType extraType = iter.next();
/* 245 */       if (extraType.isLiteralType()) {
/* 246 */         describe((LiteralType)extraType); continue;
/* 247 */       }  if (extraType.isSOAPType()) {
/* 248 */         describe((SOAPType)extraType);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processAttributeMembers(LiteralStructuredType type) throws Exception {
/* 256 */     Set<LiteralAttributeMember> sortedAttMem = new TreeSet(new GetNameComparator(LiteralAttributeMember.class));
/*     */     
/* 258 */     for (Iterator<LiteralAttributeMember> iterator1 = type.getAttributeMembers(); iterator1.hasNext(); ) {
/* 259 */       LiteralAttributeMember attribute = iterator1.next();
/*     */       
/* 261 */       sortedAttMem.add(attribute);
/*     */     } 
/* 263 */     for (Iterator<LiteralAttributeMember> iter = sortedAttMem.iterator(); iter.hasNext(); ) {
/* 264 */       LiteralAttributeMember attribute = iter.next();
/*     */       
/* 266 */       writeAttributeMember(attribute);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processElementMembers(LiteralStructuredType type) throws Exception {
/* 273 */     Set<LiteralElementMember> sortedElemMem = new TreeSet(new GetNameComparator(LiteralElementMember.class));
/*     */     
/* 275 */     for (Iterator<LiteralElementMember> iterator1 = type.getElementMembers(); iterator1.hasNext(); ) {
/* 276 */       LiteralElementMember element = iterator1.next();
/* 277 */       sortedElemMem.add(element);
/*     */     } 
/* 279 */     for (Iterator<LiteralElementMember> iter = sortedElemMem.iterator(); iter.hasNext(); ) {
/* 280 */       LiteralElementMember element = iter.next();
/* 281 */       writeElementMember(element);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processMembers(SOAPStructureType type) throws Exception {
/* 286 */     Set<SOAPStructureMember> sortedMembers = new TreeSet(new GetNameComparator(SOAPStructureMember.class));
/*     */     
/* 288 */     for (Iterator<SOAPStructureMember> iterator1 = type.getMembers(); iterator1.hasNext(); ) {
/* 289 */       SOAPStructureMember member = iterator1.next();
/* 290 */       sortedMembers.add(member);
/*     */     } 
/* 292 */     for (Iterator<SOAPStructureMember> iter = sortedMembers.iterator(); iter.hasNext(); ) {
/* 293 */       SOAPStructureMember member = iter.next();
/* 294 */       writeMember(member);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int compareNames(Object o1, Object o2) {
/* 299 */     if (o1 instanceof QName) {
/* 300 */       return ((QName)o1).toString().compareTo(((QName)o2).toString());
/*     */     }
/* 302 */     return ((String)o1).compareTo((String)o2);
/*     */   }
/*     */   
/*     */   public static class GetNameComparator implements Comparator {
/* 306 */     private Method getNameMethod = null;
/*     */     
/*     */     public GetNameComparator(Class objClass) {
/*     */       try {
/* 310 */         Class[] argsClass = new Class[0];
/* 311 */         this.getNameMethod = objClass.getMethod("getName", argsClass);
/* 312 */       } catch (Exception e) {
/* 313 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int compare(Object o1, Object o2) {
/*     */       try {
/* 319 */         Object[] args = new Object[0];
/* 320 */         return CanonicalModelWriter.compareNames(this.getNameMethod.invoke(o1, args), this.getNameMethod.invoke(o2, args));
/*     */       }
/* 322 */       catch (Exception e) {
/* 323 */         e.printStackTrace();
/*     */         
/* 325 */         return 0;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\CanonicalModelWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */