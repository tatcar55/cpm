/*     */ package com.sun.xml.rpc.wsdl.document;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.ExtensibilityHelper;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
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
/*     */ public class BindingOperation
/*     */   extends Entity
/*     */   implements Extensible
/*     */ {
/*     */   private String _uniqueKey;
/*     */   private OperationStyle _style;
/*     */   private BindingOutput _output;
/*     */   private BindingInput _input;
/*  49 */   private List _faults = new ArrayList();
/*  50 */   private ExtensibilityHelper _helper = new ExtensibilityHelper();
/*     */   private String _name;
/*     */   
/*     */   public String getName() {
/*  54 */     return this._name;
/*     */   }
/*     */   private Documentation _documentation;
/*     */   public void setName(String name) {
/*  58 */     this._name = name;
/*     */   }
/*     */   
/*     */   public String getUniqueKey() {
/*  62 */     if (this._uniqueKey == null) {
/*  63 */       StringBuffer sb = new StringBuffer();
/*  64 */       sb.append(this._name);
/*  65 */       sb.append(' ');
/*  66 */       if (this._input != null) {
/*  67 */         sb.append(this._input.getName());
/*     */       } else {
/*  69 */         sb.append(this._name);
/*  70 */         if (this._style == OperationStyle.REQUEST_RESPONSE) {
/*  71 */           sb.append("Request");
/*  72 */         } else if (this._style == OperationStyle.SOLICIT_RESPONSE) {
/*  73 */           sb.append("Response");
/*     */         } 
/*     */       } 
/*  76 */       sb.append(' ');
/*  77 */       if (this._output != null) {
/*  78 */         sb.append(this._output.getName());
/*     */       } else {
/*  80 */         sb.append(this._name);
/*  81 */         if (this._style == OperationStyle.SOLICIT_RESPONSE) {
/*  82 */           sb.append("Solicit");
/*  83 */         } else if (this._style == OperationStyle.REQUEST_RESPONSE) {
/*  84 */           sb.append("Response");
/*     */         } 
/*     */       } 
/*  87 */       this._uniqueKey = sb.toString();
/*     */     } 
/*     */     
/*  90 */     return this._uniqueKey;
/*     */   }
/*     */   
/*     */   public OperationStyle getStyle() {
/*  94 */     return this._style;
/*     */   }
/*     */   
/*     */   public void setStyle(OperationStyle s) {
/*  98 */     this._style = s;
/*     */   }
/*     */   
/*     */   public BindingInput getInput() {
/* 102 */     return this._input;
/*     */   }
/*     */   
/*     */   public void setInput(BindingInput i) {
/* 106 */     this._input = i;
/*     */   }
/*     */   
/*     */   public BindingOutput getOutput() {
/* 110 */     return this._output;
/*     */   }
/*     */   
/*     */   public void setOutput(BindingOutput o) {
/* 114 */     this._output = o;
/*     */   }
/*     */   
/*     */   public void addFault(BindingFault f) {
/* 118 */     this._faults.add(f);
/*     */   }
/*     */   
/*     */   public Iterator faults() {
/* 122 */     return this._faults.iterator();
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/* 126 */     return WSDLConstants.QNAME_OPERATION;
/*     */   }
/*     */   
/*     */   public Documentation getDocumentation() {
/* 130 */     return this._documentation;
/*     */   }
/*     */   
/*     */   public void setDocumentation(Documentation d) {
/* 134 */     this._documentation = d;
/*     */   }
/*     */   
/*     */   public void addExtension(Extension e) {
/* 138 */     this._helper.addExtension(e);
/*     */   }
/*     */   
/*     */   public Iterator extensions() {
/* 142 */     return this._helper.extensions();
/*     */   }
/*     */   
/*     */   public void withAllSubEntitiesDo(EntityAction action) {
/* 146 */     if (this._input != null) {
/* 147 */       action.perform(this._input);
/*     */     }
/* 149 */     if (this._output != null) {
/* 150 */       action.perform(this._output);
/*     */     }
/* 152 */     for (Iterator<Entity> iter = this._faults.iterator(); iter.hasNext();) {
/* 153 */       action.perform(iter.next());
/*     */     }
/* 155 */     this._helper.withAllSubEntitiesDo(action);
/*     */   }
/*     */   
/*     */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 159 */     visitor.preVisit(this);
/*     */     
/* 161 */     this._helper.accept(visitor);
/* 162 */     if (this._input != null) {
/* 163 */       this._input.accept(visitor);
/*     */     }
/* 165 */     if (this._output != null) {
/* 166 */       this._output.accept(visitor);
/*     */     }
/* 168 */     for (Iterator<BindingFault> iter = this._faults.iterator(); iter.hasNext();) {
/* 169 */       ((BindingFault)iter.next()).accept(visitor);
/*     */     }
/* 171 */     visitor.postVisit(this);
/*     */   }
/*     */   
/*     */   public void validateThis() {
/* 175 */     if (this._name == null) {
/* 176 */       failValidation("validation.missingRequiredAttribute", "name");
/*     */     }
/* 178 */     if (this._style == null) {
/* 179 */       failValidation("validation.missingRequiredProperty", "style");
/*     */     }
/*     */ 
/*     */     
/* 183 */     if (this._style == OperationStyle.ONE_WAY) {
/* 184 */       if (this._input == null) {
/* 185 */         failValidation("validation.missingRequiredSubEntity", "input");
/*     */       }
/* 187 */       if (this._output != null) {
/* 188 */         failValidation("validation.invalidSubEntity", "output");
/*     */       }
/* 190 */       if (this._faults != null && this._faults.size() != 0)
/* 191 */         failValidation("validation.invalidSubEntity", "fault"); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\BindingOperation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */