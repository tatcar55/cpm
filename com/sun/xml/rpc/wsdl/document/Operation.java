/*     */ package com.sun.xml.rpc.wsdl.document;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class Operation
/*     */   extends Entity
/*     */ {
/*     */   private Documentation _documentation;
/*     */   private String _name;
/*     */   private Input _input;
/*     */   private Output _output;
/*  46 */   private List _faults = new ArrayList();
/*     */   private OperationStyle _style;
/*     */   
/*     */   public String getName() {
/*  50 */     return this._name;
/*     */   }
/*     */   private String _parameterOrder; private String _uniqueKey;
/*     */   public void setName(String name) {
/*  54 */     this._name = name;
/*     */   }
/*     */   
/*     */   public String getUniqueKey() {
/*  58 */     if (this._uniqueKey == null) {
/*  59 */       StringBuffer sb = new StringBuffer();
/*  60 */       sb.append(this._name);
/*  61 */       sb.append(' ');
/*  62 */       if (this._input != null) {
/*  63 */         sb.append(this._input.getName());
/*     */       } else {
/*  65 */         sb.append(this._name);
/*  66 */         if (this._style == OperationStyle.REQUEST_RESPONSE) {
/*  67 */           sb.append("Request");
/*  68 */         } else if (this._style == OperationStyle.SOLICIT_RESPONSE) {
/*  69 */           sb.append("Response");
/*     */         } 
/*     */       } 
/*  72 */       sb.append(' ');
/*  73 */       if (this._output != null) {
/*  74 */         sb.append(this._output.getName());
/*     */       } else {
/*  76 */         sb.append(this._name);
/*  77 */         if (this._style == OperationStyle.SOLICIT_RESPONSE) {
/*  78 */           sb.append("Solicit");
/*  79 */         } else if (this._style == OperationStyle.REQUEST_RESPONSE) {
/*  80 */           sb.append("Response");
/*     */         } 
/*     */       } 
/*  83 */       this._uniqueKey = sb.toString();
/*     */     } 
/*     */     
/*  86 */     return this._uniqueKey;
/*     */   }
/*     */   
/*     */   public OperationStyle getStyle() {
/*  90 */     return this._style;
/*     */   }
/*     */   
/*     */   public void setStyle(OperationStyle s) {
/*  94 */     this._style = s;
/*     */   }
/*     */   
/*     */   public Input getInput() {
/*  98 */     return this._input;
/*     */   }
/*     */   
/*     */   public void setInput(Input i) {
/* 102 */     this._input = i;
/*     */   }
/*     */   
/*     */   public Output getOutput() {
/* 106 */     return this._output;
/*     */   }
/*     */   
/*     */   public void setOutput(Output o) {
/* 110 */     this._output = o;
/*     */   }
/*     */   
/*     */   public void addFault(Fault f) {
/* 114 */     this._faults.add(f);
/*     */   }
/*     */   
/*     */   public Iterator faults() {
/* 118 */     return this._faults.iterator();
/*     */   }
/*     */   
/*     */   public String getParameterOrder() {
/* 122 */     return this._parameterOrder;
/*     */   }
/*     */   
/*     */   public void setParameterOrder(String s) {
/* 126 */     this._parameterOrder = s;
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/* 130 */     return WSDLConstants.QNAME_OPERATION;
/*     */   }
/*     */   
/*     */   public Documentation getDocumentation() {
/* 134 */     return this._documentation;
/*     */   }
/*     */   
/*     */   public void setDocumentation(Documentation d) {
/* 138 */     this._documentation = d;
/*     */   }
/*     */   
/*     */   public void withAllSubEntitiesDo(EntityAction action) {
/* 142 */     super.withAllSubEntitiesDo(action);
/*     */     
/* 144 */     if (this._input != null) {
/* 145 */       action.perform(this._input);
/*     */     }
/* 147 */     if (this._output != null) {
/* 148 */       action.perform(this._output);
/*     */     }
/* 150 */     for (Iterator<Entity> iter = this._faults.iterator(); iter.hasNext();) {
/* 151 */       action.perform(iter.next());
/*     */     }
/*     */   }
/*     */   
/*     */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 156 */     visitor.preVisit(this);
/* 157 */     if (this._input != null) {
/* 158 */       this._input.accept(visitor);
/*     */     }
/* 160 */     if (this._output != null) {
/* 161 */       this._output.accept(visitor);
/*     */     }
/* 163 */     for (Iterator<Fault> iter = this._faults.iterator(); iter.hasNext();) {
/* 164 */       ((Fault)iter.next()).accept(visitor);
/*     */     }
/* 166 */     visitor.postVisit(this);
/*     */   }
/*     */   
/*     */   public void validateThis() {
/* 170 */     if (this._name == null) {
/* 171 */       failValidation("validation.missingRequiredAttribute", "name");
/*     */     }
/* 173 */     if (this._style == null) {
/* 174 */       failValidation("validation.missingRequiredProperty", "style");
/*     */     }
/*     */ 
/*     */     
/* 178 */     if (this._style == OperationStyle.ONE_WAY) {
/* 179 */       if (this._input == null) {
/* 180 */         failValidation("validation.missingRequiredSubEntity", "input");
/*     */       }
/* 182 */       if (this._output != null) {
/* 183 */         failValidation("validation.invalidSubEntity", "output");
/*     */       }
/* 185 */       if (this._faults != null && this._faults.size() != 0) {
/* 186 */         failValidation("validation.invalidSubEntity", "fault");
/*     */       }
/* 188 */       if (this._parameterOrder != null) {
/* 189 */         failValidation("validation.invalidAttribute", "parameterOrder");
/*     */       }
/* 191 */     } else if (this._style == OperationStyle.NOTIFICATION && 
/* 192 */       this._parameterOrder != null) {
/* 193 */       failValidation("validation.invalidAttribute", "parameterOrder");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\Operation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */