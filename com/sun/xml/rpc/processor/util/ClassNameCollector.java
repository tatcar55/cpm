/*     */ package com.sun.xml.rpc.processor.util;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.ExtendedModelVisitor;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttachmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralIDType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralListType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralTypeVisitor;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCRequestOrderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPListType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPTypeVisitor;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPUnorderedStructureType;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class ClassNameCollector
/*     */   extends ExtendedModelVisitor
/*     */   implements SOAPTypeVisitor, LiteralTypeVisitor
/*     */ {
/*     */   private Set _allClassNames;
/*     */   private Set _exceptions;
/*     */   private Set _wsdlBindingNames;
/*     */   private Set _conflictingClassNames;
/*     */   private Set _visitedTypes;
/*     */   private Set _visitedFaults;
/*     */   
/*     */   public void process(Model model) {
/*     */     try {
/*  92 */       this._allClassNames = new HashSet();
/*  93 */       this._exceptions = new HashSet();
/*  94 */       this._wsdlBindingNames = new HashSet();
/*  95 */       this._conflictingClassNames = new HashSet();
/*  96 */       this._visitedTypes = new HashSet();
/*  97 */       this._visitedFaults = new HashSet();
/*  98 */       visit(model);
/*  99 */     } catch (Exception e) {
/*     */     
/*     */     } finally {
/* 102 */       this._allClassNames = null;
/* 103 */       this._exceptions = null;
/* 104 */       this._visitedTypes = null;
/* 105 */       this._visitedFaults = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set getConflictingClassNames() {
/* 110 */     return this._conflictingClassNames;
/*     */   }
/*     */   
/*     */   protected void postVisit(Model model) throws Exception {
/* 114 */     for (Iterator<AbstractType> iter = model.getExtraTypes(); iter.hasNext();) {
/* 115 */       visitType(iter.next());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void preVisit(Service service) throws Exception {
/* 120 */     registerClassName(service.getJavaInterface().getName());
/*     */     
/* 122 */     registerClassName(service.getJavaInterface().getImpl());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preVisit(Port port) throws Exception {
/* 128 */     QName wsdlBindingName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*     */     
/* 130 */     if (!this._wsdlBindingNames.contains(wsdlBindingName))
/*     */     {
/*     */       
/* 133 */       registerClassName(port.getJavaInterface().getName());
/*     */     }
/* 135 */     registerClassName((String)port.getProperty("com.sun.xml.rpc.processor.model.StubClassName"));
/*     */     
/* 137 */     registerClassName((String)port.getProperty("com.sun.xml.rpc.processor.model.TieClassName"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postVisit(Port port) throws Exception {
/* 142 */     QName wsdlBindingName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*     */     
/* 144 */     if (!this._wsdlBindingNames.contains(wsdlBindingName)) {
/* 145 */       this._wsdlBindingNames.add(wsdlBindingName);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean shouldVisit(Port port) {
/* 150 */     QName wsdlBindingName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*     */     
/* 152 */     return !this._wsdlBindingNames.contains(wsdlBindingName);
/*     */   }
/*     */   
/*     */   protected void preVisit(Fault fault) throws Exception {
/* 156 */     if (!this._exceptions.contains(fault.getJavaException())) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 161 */       this._exceptions.add(fault.getJavaException());
/*     */       
/* 163 */       registerClassName(fault.getJavaException().getName());
/*     */       
/* 165 */       if (fault.getParentFault() != null) {
/* 166 */         preVisit(fault.getParentFault());
/*     */       }
/* 168 */       Iterator<Fault> iter = fault.getSubfaults();
/* 169 */       while (iter != null && iter.hasNext()) {
/*     */         
/* 171 */         Fault subfault = iter.next();
/* 172 */         preVisit(subfault);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void visitBodyBlock(Block block) throws Exception {
/* 178 */     visitBlock(block);
/*     */   }
/*     */   
/*     */   protected void visitHeaderBlock(Block block) throws Exception {
/* 182 */     visitBlock(block);
/*     */   }
/*     */   
/*     */   protected void visitFaultBlock(Block block) throws Exception {
/* 186 */     AbstractType type = block.getType();
/* 187 */     if (type instanceof SOAPStructureType) {
/* 188 */       Iterator<SOAPStructureMember> iter = ((SOAPStructureType)type).getMembers();
/* 189 */       while (iter.hasNext()) {
/*     */         
/* 191 */         SOAPStructureMember member = iter.next();
/* 192 */         visitType(member.getType());
/*     */       } 
/* 194 */     } else if (type instanceof SOAPArrayType) {
/* 195 */       visitType(((SOAPArrayType)type).getElementType());
/* 196 */     } else if (type instanceof LiteralStructuredType) {
/* 197 */       Iterator<LiteralAttributeMember> iterator = ((LiteralStructuredType)type).getAttributeMembers();
/* 198 */       while (iterator.hasNext()) {
/* 199 */         LiteralAttributeMember attribute = iterator.next();
/*     */         
/* 201 */         visitType(attribute.getType());
/*     */       } 
/* 203 */       Iterator<LiteralElementMember> iter = ((LiteralStructuredType)type).getElementMembers();
/* 204 */       while (iter.hasNext()) {
/* 205 */         LiteralElementMember element = iter.next();
/*     */         
/* 207 */         visitType(element.getType());
/*     */       } 
/* 209 */     } else if (type instanceof LiteralArrayType) {
/* 210 */       visitType(((LiteralArrayType)type).getElementType());
/* 211 */     } else if (type instanceof LiteralArrayWrapperType) {
/* 212 */       visitType(((LiteralArrayWrapperType)type).getElementMember().getType());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void visitBlock(Block block) throws Exception {
/* 217 */     visitType(block.getType());
/*     */   }
/*     */   
/*     */   protected void visit(Parameter parameter) throws Exception {
/* 221 */     visitType(parameter.getType());
/*     */   }
/*     */   
/*     */   private void visitType(AbstractType type) throws Exception {
/* 225 */     if (type != null) {
/* 226 */       if (type.isLiteralType()) {
/* 227 */         visitType((LiteralType)type);
/* 228 */       } else if (type.isSOAPType()) {
/* 229 */         visitType((SOAPType)type);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void visitType(LiteralType type) throws Exception {
/* 235 */     type.accept(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(LiteralSimpleType type) throws Exception {}
/*     */   
/*     */   public void visit(LiteralSequenceType type) throws Exception {
/* 242 */     visitLiteralStructuredType((LiteralStructuredType)type);
/*     */   }
/*     */   
/*     */   public void visit(LiteralAllType type) throws Exception {
/* 246 */     visitLiteralStructuredType((LiteralStructuredType)type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void visitLiteralStructuredType(LiteralStructuredType type) throws Exception {
/* 252 */     boolean alreadySeen = this._visitedTypes.contains(type);
/* 253 */     if (!alreadySeen) {
/* 254 */       this._visitedTypes.add(type);
/* 255 */       registerClassName(type.getJavaType().getName());
/* 256 */       for (Iterator<LiteralAttributeMember> iterator = type.getAttributeMembers(); iterator.hasNext(); ) {
/* 257 */         LiteralAttributeMember attribute = iterator.next();
/*     */         
/* 259 */         visitType(attribute.getType());
/*     */       } 
/* 261 */       for (Iterator<LiteralElementMember> iter = type.getElementMembers(); iter.hasNext(); ) {
/* 262 */         LiteralElementMember element = iter.next();
/*     */         
/* 264 */         visitType(element.getType());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visit(LiteralArrayType type) throws Exception {
/* 270 */     visitType(type.getElementType());
/*     */   }
/*     */   
/*     */   public void visit(LiteralArrayWrapperType type) throws Exception {
/* 274 */     boolean alreadySeen = this._visitedTypes.contains(type);
/* 275 */     if (!alreadySeen) {
/* 276 */       this._visitedTypes.add(type);
/* 277 */       registerClassName(type.getJavaType().getName());
/* 278 */       visitType(type.getElementMember().getType());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(LiteralFragmentType type) throws Exception {}
/*     */ 
/*     */   
/*     */   public void visit(LiteralListType type) throws Exception {}
/*     */ 
/*     */   
/*     */   public void visit(SOAPListType type) throws Exception {}
/*     */ 
/*     */   
/*     */   public void visit(LiteralIDType type) throws Exception {}
/*     */ 
/*     */   
/*     */   public void visit(LiteralEnumerationType type) throws Exception {
/* 296 */     boolean alreadySeen = this._visitedTypes.contains(type);
/* 297 */     if (!alreadySeen) {
/* 298 */       this._visitedTypes.add(type);
/* 299 */       registerClassName(type.getJavaType().getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void visitType(SOAPType type) throws Exception {
/* 304 */     type.accept(this);
/*     */   }
/*     */   
/*     */   public void visit(SOAPArrayType type) throws Exception {
/* 308 */     visitType(type.getElementType());
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(SOAPCustomType type) throws Exception {}
/*     */   
/*     */   public void visit(SOAPEnumerationType type) throws Exception {
/* 315 */     visitType(type.getBaseType());
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(SOAPSimpleType type) throws Exception {}
/*     */ 
/*     */   
/*     */   public void visit(SOAPAnyType type) throws Exception {}
/*     */   
/*     */   public void visit(SOAPOrderedStructureType type) throws Exception {
/* 325 */     visitSOAPStructureType((SOAPStructureType)type);
/*     */   }
/*     */   
/*     */   public void visit(SOAPUnorderedStructureType type) throws Exception {
/* 329 */     visitSOAPStructureType((SOAPStructureType)type);
/*     */   }
/*     */   
/*     */   public void visit(RPCRequestOrderedStructureType type) throws Exception {
/* 333 */     visitSOAPStructureType((SOAPStructureType)type);
/*     */   }
/*     */   
/*     */   public void visit(RPCRequestUnorderedStructureType type) throws Exception {
/* 337 */     visitSOAPStructureType((SOAPStructureType)type);
/*     */   }
/*     */   
/*     */   public void visit(RPCResponseStructureType type) throws Exception {
/* 341 */     visitSOAPStructureType((SOAPStructureType)type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void visitSOAPStructureType(SOAPStructureType type) throws Exception {
/* 347 */     boolean alreadySeen = this._visitedTypes.contains(type);
/* 348 */     if (!alreadySeen) {
/* 349 */       this._visitedTypes.add(type);
/*     */       
/* 351 */       if (this._exceptions.contains(type.getJavaType())) {
/*     */         return;
/*     */       }
/*     */       
/* 355 */       registerClassName(type.getJavaType().getName());
/* 356 */       for (Iterator<SOAPStructureMember> iterator = type.getMembers(); iterator.hasNext(); ) {
/* 357 */         SOAPStructureMember member = iterator.next();
/* 358 */         visitType(member.getType());
/*     */       } 
/* 360 */       Iterator<SOAPStructureType> iter = type.getSubtypes();
/* 361 */       while (iter != null && iter.hasNext()) {
/*     */         
/* 363 */         SOAPStructureType subType = iter.next();
/* 364 */         visitType((SOAPType)subType);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerClassName(String name) {
/* 370 */     if (name == null || name.equals("")) {
/*     */       return;
/*     */     }
/* 373 */     if (this._allClassNames.contains(name)) {
/* 374 */       this._conflictingClassNames.add(name);
/*     */     } else {
/* 376 */       this._allClassNames.add(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visit(LiteralAttachmentType type) throws Exception {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\ClassNameCollector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */