/*     */ package com.sun.xml.rpc.processor.generator.nodes;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.ExtendedModelVisitor;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Request;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
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
/*     */ import com.sun.xml.rpc.processor.util.ClassNameCollector;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class TypeVisitor
/*     */   extends ExtendedModelVisitor
/*     */   implements SOAPTypeVisitor, LiteralTypeVisitor
/*     */ {
/*     */   private Configuration config;
/*     */   private Set _visitedComplexTypes;
/*     */   private Set _visitedSimpleTypes;
/*     */   private Set _visitedFaults;
/*     */   private Map _visitedNSPackages;
/*     */   private boolean _trace;
/*     */   
/*     */   public TypeVisitor(Configuration config) {
/* 551 */     this._trace = false;
/*     */     this.config = config;
/*     */     this._visitedComplexTypes = new HashSet();
/*     */     this._visitedSimpleTypes = new HashSet();
/*     */     this._visitedFaults = new HashSet();
/*     */     this._visitedNSPackages = new HashMap<Object, Object>();
/*     */   }
/*     */   
/*     */   public Set getComplexTypes() {
/*     */     return this._visitedComplexTypes;
/*     */   }
/*     */   
/*     */   public Set getSimpleTypes() {
/*     */     return this._visitedSimpleTypes;
/*     */   }
/*     */   
/*     */   public Set getFaults() {
/*     */     return this._visitedFaults;
/*     */   }
/*     */   
/*     */   public Map getNamespacePackages() {
/*     */     return this._visitedNSPackages;
/*     */   }
/*     */   
/*     */   protected void preVisit(Model model) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("preVisit: model"); 
/*     */     ClassNameCollector collector = new ClassNameCollector();
/*     */     collector.process(model);
/*     */     Set names = collector.getConflictingClassNames();
/*     */     if (!names.isEmpty())
/*     */       throw new MappingException("j2ee.nameCollision", new Object[] { names.toString() }); 
/*     */   }
/*     */   
/*     */   protected void postVisit(Model model) throws Exception {
/*     */     processTypes(model);
/*     */   }
/*     */   
/*     */   protected void processTypes(Model model) throws Exception {
/*     */     for (Iterator<AbstractType> iter = model.getExtraTypes(); iter.hasNext(); ) {
/*     */       AbstractType extraType = iter.next();
/*     */       if (extraType.isLiteralType()) {
/*     */         describe((LiteralType)extraType);
/*     */         continue;
/*     */       } 
/*     */       if (extraType.isSOAPType())
/*     */         describe((SOAPType)extraType); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void preVisit(Service service) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("preVisit server " + service.getName()); 
/*     */   }
/*     */   
/*     */   protected void postVisit(Service service) throws Exception {}
/*     */   
/*     */   protected void preVisit(Port port) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("preVisit port:" + port.getName()); 
/*     */     processNamespacePackages(port);
/*     */   }
/*     */   
/*     */   protected void postVisit(Port port) throws Exception {}
/*     */   
/*     */   protected void preVisit(Operation operation) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("preVisit operation:" + operation.getName()); 
/*     */   }
/*     */   
/*     */   protected void postVisit(Operation operation) throws Exception {}
/*     */   
/*     */   protected void preVisit(Request request) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("preVisit Request"); 
/*     */   }
/*     */   
/*     */   protected void postVisit(Request request) throws Exception {}
/*     */   
/*     */   protected void preVisit(Response response) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("preVisit Response"); 
/*     */   }
/*     */   
/*     */   protected void postVisit(Response response) throws Exception {}
/*     */   
/*     */   protected void preVisit(Fault fault) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("preVisit Fault:" + fault.getName() + "; type = " + fault.getClass().getName()); 
/*     */     boolean alreadySeen = this._visitedFaults.contains(fault);
/*     */     if (alreadySeen)
/*     */       return; 
/*     */     this._visitedFaults.add(fault);
/*     */   }
/*     */   
/*     */   protected void postVisit(Fault fault) throws Exception {}
/*     */   
/*     */   protected void visitBodyBlock(Block block) throws Exception {
/*     */     if (block.getType().isLiteralType()) {
/*     */       describe((LiteralType)block.getType());
/*     */     } else if (block.getType().isSOAPType()) {
/*     */       describe((SOAPType)block.getType());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void visitHeaderBlock(Block block) throws Exception {
/*     */     if (block.getType().isLiteralType()) {
/*     */       describe((LiteralType)block.getType());
/*     */     } else if (block.getType().isSOAPType()) {
/*     */       describe((SOAPType)block.getType());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void visitFaultBlock(Block block) throws Exception {
/*     */     if (block.getType().isLiteralType()) {
/*     */       describe((LiteralType)block.getType());
/*     */     } else if (block.getType().isSOAPType()) {
/*     */       describe((SOAPType)block.getType());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void visit(Parameter parameter) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit Parameter:" + parameter.getName()); 
/*     */     if (parameter.getType().isLiteralType()) {
/*     */       describe((LiteralType)parameter.getType());
/*     */     } else if (parameter.getType().isSOAPType()) {
/*     */       describe((SOAPType)parameter.getType());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void describe(LiteralType type) throws Exception {
/*     */     processNamespacePackages((AbstractType)type);
/*     */     type.accept(this);
/*     */   }
/*     */   
/*     */   public void visit(LiteralSimpleType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit LiteralSimpleType:" + type.getName()); 
/*     */   }
/*     */   
/*     */   public void visit(LiteralSequenceType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit LiteralSequenceType:" + type.getName()); 
/*     */     visitLiteralStructuredType((LiteralStructuredType)type, "LITERAL-SEQUENCE-TYPE ", true);
/*     */   }
/*     */   
/*     */   public void visit(LiteralAllType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit LiteralAllType:" + type.getName()); 
/*     */     visitLiteralStructuredType((LiteralStructuredType)type, "LITERAL-ALL-TYPE ", true);
/*     */   }
/*     */   
/*     */   public void visit(LiteralEnumerationType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit LiteralEnumerationType " + type.getName()); 
/*     */     if (!this._visitedSimpleTypes.contains(type)) {
/*     */       this._visitedSimpleTypes.add(type);
/*     */       describe(type.getBaseType());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visit(LiteralListType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit LiteralListType " + type.getName()); 
/*     */   }
/*     */   
/*     */   public void visit(LiteralIDType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit LiteralIDType " + type.getName()); 
/*     */   }
/*     */   
/*     */   public void visit(LiteralArrayWrapperType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit LiteralArrayWrapperType" + type.getName() + " javatype = " + type.getJavaType().getName()); 
/*     */   }
/*     */   
/*     */   private void visitLiteralStructuredType(LiteralStructuredType type, String header, boolean detailed) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit LiteralStructuredType:" + type.getName()); 
/*     */     boolean alreadySeen = this._visitedComplexTypes.contains(type);
/*     */     if (alreadySeen)
/*     */       return; 
/*     */     this._visitedComplexTypes.add(type);
/*     */     if (detailed) {
/*     */       processAttributeMembers(type);
/*     */       processElementMembers(type);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processAttributeMembers(LiteralStructuredType type) throws Exception {
/*     */     for (Iterator<LiteralAttributeMember> iter = type.getAttributeMembers(); iter.hasNext(); ) {
/*     */       LiteralAttributeMember attribute = iter.next();
/*     */       writeAttributeMember(attribute);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void writeAttributeMember(LiteralAttributeMember attribute) throws Exception {
/*     */     describe(attribute.getType());
/*     */   }
/*     */   
/*     */   protected void processElementMembers(LiteralStructuredType type) throws Exception {
/*     */     for (Iterator<LiteralElementMember> iter = type.getElementMembers(); iter.hasNext(); ) {
/*     */       LiteralElementMember element = iter.next();
/*     */       writeElementMember(element);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void writeElementMember(LiteralElementMember element) throws Exception {
/*     */     describe(element.getType());
/*     */   }
/*     */   
/*     */   public void visit(LiteralArrayType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit LiteralArrayType:" + type.getName()); 
/*     */     describe(type.getElementType());
/*     */   }
/*     */   
/*     */   public void visit(LiteralFragmentType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit LiteralFragmentType:" + type.getName()); 
/*     */   }
/*     */   
/*     */   protected void describe(SOAPType type) throws Exception {
/*     */     processNamespacePackages((AbstractType)type);
/*     */     type.accept(this);
/*     */   }
/*     */   
/*     */   public void visit(SOAPArrayType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit SOAPArrayType:" + type.getName()); 
/*     */     describe(type.getElementType());
/*     */   }
/*     */   
/*     */   public void visit(SOAPCustomType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit SOAPCustomType:" + type.getName()); 
/*     */   }
/*     */   
/*     */   public void visit(SOAPEnumerationType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit SOAPEnumerationType:" + type.getName()); 
/*     */     if (!this._visitedSimpleTypes.contains(type)) {
/*     */       this._visitedSimpleTypes.add(type);
/*     */       describe(type.getBaseType());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visit(SOAPSimpleType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit SOAPSimpleType:" + type.getName()); 
/*     */   }
/*     */   
/*     */   public void visit(SOAPAnyType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit SOAPAnyType:" + type.getName()); 
/*     */   }
/*     */   
/*     */   public void visit(SOAPOrderedStructureType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit SOAPStructuredType:" + type.getName()); 
/*     */     visitSOAPStructureType((SOAPStructureType)type, "SOAP-ORDERED-STRUCTURE-TYPE", true);
/*     */   }
/*     */   
/*     */   public void visit(SOAPUnorderedStructureType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit SOAPUnorderedStructuredType:" + type.getName()); 
/*     */     visitSOAPStructureType((SOAPStructureType)type, "SOAP-UNORDERED-STRUCTURE-TYPE", true);
/*     */   }
/*     */   
/*     */   public void visit(SOAPListType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit SOAPUnorderedStructuredType:" + type.getName()); 
/*     */   }
/*     */   
/*     */   public void visit(RPCRequestOrderedStructureType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit RPCRequestOrderedStructureType:" + type.getName()); 
/*     */     visitSOAPStructureType((SOAPStructureType)type, "RPC-REQUEST-ORDERED-STRUCTURE-TYPE", false);
/*     */   }
/*     */   
/*     */   public void visit(RPCRequestUnorderedStructureType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit RPCRequestUnorderedStructureType:" + type.getName()); 
/*     */     visitSOAPStructureType((SOAPStructureType)type, "RPC-REQUEST-UNORDERED-STRUCTURE-TYPE", false);
/*     */   }
/*     */   
/*     */   public void visit(RPCResponseStructureType type) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit RPCResponseStructureType:" + type.getName()); 
/*     */     visitSOAPStructureType((SOAPStructureType)type, "RPC-RESPONSE-STRUCTURE-TYPE", false);
/*     */   }
/*     */   
/*     */   private void visitSOAPStructureType(SOAPStructureType type, String header, boolean detailed) throws Exception {
/*     */     if (this._trace)
/*     */       System.out.println("visit SOAPStructureType:" + type.getName()); 
/*     */     boolean alreadySeen = this._visitedComplexTypes.contains(type);
/*     */     if (alreadySeen)
/*     */       return; 
/*     */     this._visitedComplexTypes.add(type);
/*     */     if (detailed) {
/*     */       if (type.getParentType() != null)
/*     */         describe((SOAPType)type.getParentType()); 
/*     */       processMembers(type);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void processMembers(SOAPStructureType type) throws Exception {
/*     */     for (Iterator<SOAPStructureMember> iter = type.getMembers(); iter.hasNext(); ) {
/*     */       SOAPStructureMember member = iter.next();
/*     */       writeMember(member);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void writeMember(SOAPStructureMember member) throws Exception {
/*     */     describe(member.getType());
/*     */   }
/*     */   
/*     */   private void processNamespacePackages(AbstractType type) {
/*     */     if (!(type instanceof RPCRequestOrderedStructureType) && !(type instanceof RPCRequestOrderedStructureType) && !(type instanceof RPCResponseStructureType) && (type.getName() == null || this._visitedNSPackages.keySet().contains(type.getName().getNamespaceURI())))
/*     */       return; 
/*     */     String uri = type.getName().getNamespaceURI();
/*     */     if ("http://www.w3.org/2001/XMLSchema".equals(uri) || "http://schemas.xmlsoap.org/soap/encoding/".equals(uri) || "http://www.w3.org/2002/06/soap-encoding".equals(uri) || "".equals(uri))
/*     */       return; 
/*     */     String javaType = type.getJavaType().getName();
/*     */     String packageName = javaType.substring(0, javaType.lastIndexOf("."));
/*     */     if (this._trace)
/*     */       System.out.println("Namespace=" + uri + "; package=" + packageName); 
/*     */     this._visitedNSPackages.put(uri, packageName);
/*     */   }
/*     */   
/*     */   private void processNamespacePackages(Port port) {
/*     */     QName bindingQName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName");
/*     */     String uri = bindingQName.getNamespaceURI();
/*     */     String packageName = (String)this._visitedNSPackages.get(uri);
/*     */     if (packageName == null) {
/*     */       ProcessorEnvironment env = (ProcessorEnvironment)this.config.getEnvironment();
/*     */       JavaInterface intf = port.getJavaInterface();
/*     */       String className = env.getNames().customJavaTypeClassName(intf);
/*     */       packageName = className.substring(0, className.lastIndexOf("."));
/*     */       this._visitedNSPackages.put(uri, packageName);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visit(LiteralAttachmentType type) throws Exception {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\nodes\TypeVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */