/*     */ package com.sun.xml.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ModelNode
/*     */   implements Iterable<ModelNode>, Cloneable
/*     */ {
/*     */   private LinkedList<ModelNode> children;
/*     */   private Collection<ModelNode> unmodifiableViewOnContent;
/*     */   private final Type type;
/*     */   private ModelNode parentNode;
/*     */   private PolicySourceModel parentModel;
/*     */   private PolicyReferenceData referenceData;
/*  61 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(ModelNode.class);
/*     */   private PolicySourceModel referencedModel;
/*     */   private AssertionData nodeData;
/*     */   
/*     */   public enum Type
/*     */   {
/*  67 */     POLICY((String)XmlToken.Policy),
/*  68 */     ALL((String)XmlToken.All),
/*  69 */     EXACTLY_ONE((String)XmlToken.ExactlyOne),
/*  70 */     POLICY_REFERENCE((String)XmlToken.PolicyReference),
/*  71 */     ASSERTION((String)XmlToken.UNKNOWN),
/*  72 */     ASSERTION_PARAMETER_NODE((String)XmlToken.UNKNOWN);
/*     */     
/*     */     private XmlToken token;
/*     */     
/*     */     Type(XmlToken token) {
/*  77 */       this.token = token;
/*     */     }
/*     */     
/*     */     public XmlToken getXmlToken() {
/*  81 */       return this.token;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isChildTypeSupported(Type childType) {
/*  92 */       switch (this) {
/*     */         case POLICY:
/*     */         case ALL:
/*     */         case EXACTLY_ONE:
/*  96 */           switch (childType) {
/*     */             case ASSERTION_PARAMETER_NODE:
/*  98 */               return false;
/*     */           } 
/* 100 */           return true;
/*     */         
/*     */         case POLICY_REFERENCE:
/* 103 */           return false;
/*     */         case ASSERTION:
/* 105 */           switch (childType) {
/*     */             case ASSERTION_PARAMETER_NODE:
/*     */             case POLICY:
/*     */             case POLICY_REFERENCE:
/* 109 */               return true;
/*     */           } 
/* 111 */           return false;
/*     */         
/*     */         case ASSERTION_PARAMETER_NODE:
/* 114 */           switch (childType) {
/*     */             case ASSERTION_PARAMETER_NODE:
/* 116 */               return true;
/*     */           } 
/* 118 */           return false;
/*     */       } 
/*     */       
/* 121 */       throw (IllegalStateException)ModelNode.LOGGER.logSevereException(new IllegalStateException(LocalizationMessages.WSP_0060_POLICY_ELEMENT_TYPE_UNKNOWN(this)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ModelNode createRootPolicyNode(PolicySourceModel model) throws IllegalArgumentException {
/* 151 */     if (model == null) {
/* 152 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0039_POLICY_SRC_MODEL_INPUT_PARAMETER_MUST_NOT_BE_NULL()));
/*     */     }
/* 154 */     return new ModelNode(Type.POLICY, model);
/*     */   }
/*     */   
/*     */   private ModelNode(Type type, PolicySourceModel parentModel) {
/* 158 */     this.type = type;
/* 159 */     this.parentModel = parentModel;
/* 160 */     this.children = new LinkedList<ModelNode>();
/* 161 */     this.unmodifiableViewOnContent = Collections.unmodifiableCollection(this.children);
/*     */   }
/*     */   
/*     */   private ModelNode(Type type, PolicySourceModel parentModel, AssertionData data) {
/* 165 */     this(type, parentModel);
/*     */     
/* 167 */     this.nodeData = data;
/*     */   }
/*     */   
/*     */   private ModelNode(PolicySourceModel parentModel, PolicyReferenceData data) {
/* 171 */     this(Type.POLICY_REFERENCE, parentModel);
/*     */     
/* 173 */     this.referenceData = data;
/*     */   }
/*     */   
/*     */   private void checkCreateChildOperationSupportForType(Type type) throws UnsupportedOperationException {
/* 177 */     if (!this.type.isChildTypeSupported(type)) {
/* 178 */       throw (UnsupportedOperationException)LOGGER.logSevereException(new UnsupportedOperationException(LocalizationMessages.WSP_0073_CREATE_CHILD_NODE_OPERATION_NOT_SUPPORTED(type, this.type)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildPolicyNode() {
/* 189 */     checkCreateChildOperationSupportForType(Type.POLICY);
/*     */     
/* 191 */     ModelNode node = new ModelNode(Type.POLICY, this.parentModel);
/* 192 */     addChild(node);
/*     */     
/* 194 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildAllNode() {
/* 204 */     checkCreateChildOperationSupportForType(Type.ALL);
/*     */     
/* 206 */     ModelNode node = new ModelNode(Type.ALL, this.parentModel);
/* 207 */     addChild(node);
/*     */     
/* 209 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildExactlyOneNode() {
/* 219 */     checkCreateChildOperationSupportForType(Type.EXACTLY_ONE);
/*     */     
/* 221 */     ModelNode node = new ModelNode(Type.EXACTLY_ONE, this.parentModel);
/* 222 */     addChild(node);
/*     */     
/* 224 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildAssertionNode() {
/* 234 */     checkCreateChildOperationSupportForType(Type.ASSERTION);
/*     */     
/* 236 */     ModelNode node = new ModelNode(Type.ASSERTION, this.parentModel);
/* 237 */     addChild(node);
/*     */     
/* 239 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildAssertionNode(AssertionData nodeData) {
/* 250 */     checkCreateChildOperationSupportForType(Type.ASSERTION);
/*     */     
/* 252 */     ModelNode node = new ModelNode(Type.ASSERTION, this.parentModel, nodeData);
/* 253 */     addChild(node);
/*     */     
/* 255 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode createChildAssertionParameterNode() {
/* 265 */     checkCreateChildOperationSupportForType(Type.ASSERTION_PARAMETER_NODE);
/*     */     
/* 267 */     ModelNode node = new ModelNode(Type.ASSERTION_PARAMETER_NODE, this.parentModel);
/* 268 */     addChild(node);
/*     */     
/* 270 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ModelNode createChildAssertionParameterNode(AssertionData nodeData) {
/* 281 */     checkCreateChildOperationSupportForType(Type.ASSERTION_PARAMETER_NODE);
/*     */     
/* 283 */     ModelNode node = new ModelNode(Type.ASSERTION_PARAMETER_NODE, this.parentModel, nodeData);
/* 284 */     addChild(node);
/*     */     
/* 286 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ModelNode createChildPolicyReferenceNode(PolicyReferenceData referenceData) {
/* 297 */     checkCreateChildOperationSupportForType(Type.POLICY_REFERENCE);
/*     */     
/* 299 */     ModelNode node = new ModelNode(this.parentModel, referenceData);
/* 300 */     this.parentModel.addNewPolicyReference(node);
/* 301 */     addChild(node);
/*     */     
/* 303 */     return node;
/*     */   }
/*     */   
/*     */   Collection<ModelNode> getChildren() {
/* 307 */     return this.unmodifiableViewOnContent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setParentModel(PolicySourceModel model) throws IllegalAccessException {
/* 319 */     if (this.parentNode != null) {
/* 320 */       throw (IllegalAccessException)LOGGER.logSevereException(new IllegalAccessException(LocalizationMessages.WSP_0049_PARENT_MODEL_CAN_NOT_BE_CHANGED()));
/*     */     }
/*     */     
/* 323 */     updateParentModelReference(model);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateParentModelReference(PolicySourceModel model) {
/* 332 */     this.parentModel = model;
/*     */     
/* 334 */     for (ModelNode child : this.children) {
/* 335 */       child.updateParentModelReference(model);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicySourceModel getParentModel() {
/* 345 */     return this.parentModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 354 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode getParentNode() {
/* 363 */     return this.parentNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssertionData getNodeData() {
/* 374 */     return this.nodeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PolicyReferenceData getPolicyReferenceData() {
/* 385 */     return this.referenceData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssertionData setOrReplaceNodeData(AssertionData newData) {
/* 402 */     if (!isDomainSpecific()) {
/* 403 */       throw (UnsupportedOperationException)LOGGER.logSevereException(new UnsupportedOperationException(LocalizationMessages.WSP_0051_OPERATION_NOT_SUPPORTED_FOR_THIS_BUT_ASSERTION_RELATED_NODE_TYPE(this.type)));
/*     */     }
/*     */     
/* 406 */     AssertionData oldData = this.nodeData;
/* 407 */     this.nodeData = newData;
/*     */     
/* 409 */     return oldData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isDomainSpecific() {
/* 420 */     return (this.type == Type.ASSERTION || this.type == Type.ASSERTION_PARAMETER_NODE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean addChild(ModelNode child) {
/* 434 */     this.children.add(child);
/* 435 */     child.parentNode = this;
/*     */     
/* 437 */     return true;
/*     */   }
/*     */   
/*     */   void setReferencedModel(PolicySourceModel model) {
/* 441 */     if (this.type != Type.POLICY_REFERENCE) {
/* 442 */       throw (UnsupportedOperationException)LOGGER.logSevereException(new UnsupportedOperationException(LocalizationMessages.WSP_0050_OPERATION_NOT_SUPPORTED_FOR_THIS_BUT_POLICY_REFERENCE_NODE_TYPE(this.type)));
/*     */     }
/*     */     
/* 445 */     this.referencedModel = model;
/*     */   }
/*     */   
/*     */   PolicySourceModel getReferencedModel() {
/* 449 */     return this.referencedModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int childrenSize() {
/* 459 */     return this.children.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChildren() {
/* 468 */     return !this.children.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<ModelNode> iterator() {
/* 477 */     return this.children.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 490 */     if (this == obj) {
/* 491 */       return true;
/*     */     }
/*     */     
/* 494 */     if (!(obj instanceof ModelNode)) {
/* 495 */       return false;
/*     */     }
/*     */     
/* 498 */     boolean result = true;
/* 499 */     ModelNode that = (ModelNode)obj;
/*     */     
/* 501 */     result = (result && this.type.equals(that.type));
/*     */     
/* 503 */     result = (result && ((this.nodeData == null) ? (that.nodeData == null) : this.nodeData.equals(that.nodeData)));
/* 504 */     result = (result && ((this.children == null) ? (that.children == null) : this.children.equals(that.children)));
/*     */     
/* 506 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 514 */     int result = 17;
/*     */     
/* 516 */     result = 37 * result + this.type.hashCode();
/* 517 */     result = 37 * result + ((this.parentNode == null) ? 0 : this.parentNode.hashCode());
/* 518 */     result = 37 * result + ((this.nodeData == null) ? 0 : this.nodeData.hashCode());
/* 519 */     result = 37 * result + this.children.hashCode();
/*     */     
/* 521 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 532 */     return toString(0, new StringBuffer()).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer toString(int indentLevel, StringBuffer buffer) {
/* 543 */     String indent = PolicyUtils.Text.createIndent(indentLevel);
/* 544 */     String innerIndent = PolicyUtils.Text.createIndent(indentLevel + 1);
/*     */     
/* 546 */     buffer.append(indent).append(this.type).append(" {").append(PolicyUtils.Text.NEW_LINE);
/* 547 */     if (this.type == Type.ASSERTION) {
/* 548 */       if (this.nodeData == null) {
/* 549 */         buffer.append(innerIndent).append("no assertion data set");
/*     */       } else {
/* 551 */         this.nodeData.toString(indentLevel + 1, buffer);
/*     */       } 
/* 553 */       buffer.append(PolicyUtils.Text.NEW_LINE);
/* 554 */     } else if (this.type == Type.POLICY_REFERENCE) {
/* 555 */       if (this.referenceData == null) {
/* 556 */         buffer.append(innerIndent).append("no policy reference data set");
/*     */       } else {
/* 558 */         this.referenceData.toString(indentLevel + 1, buffer);
/*     */       } 
/* 560 */       buffer.append(PolicyUtils.Text.NEW_LINE);
/* 561 */     } else if (this.type == Type.ASSERTION_PARAMETER_NODE) {
/* 562 */       if (this.nodeData == null) {
/* 563 */         buffer.append(innerIndent).append("no parameter data set");
/*     */       } else {
/*     */         
/* 566 */         this.nodeData.toString(indentLevel + 1, buffer);
/*     */       } 
/* 568 */       buffer.append(PolicyUtils.Text.NEW_LINE);
/*     */     } 
/*     */     
/* 571 */     if (this.children.size() > 0) {
/* 572 */       for (ModelNode child : this.children) {
/* 573 */         child.toString(indentLevel + 1, buffer).append(PolicyUtils.Text.NEW_LINE);
/*     */       }
/*     */     } else {
/* 576 */       buffer.append(innerIndent).append("no child nodes").append(PolicyUtils.Text.NEW_LINE);
/*     */     } 
/*     */     
/* 579 */     buffer.append(indent).append('}');
/* 580 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelNode clone() throws CloneNotSupportedException {
/* 585 */     ModelNode clone = (ModelNode)super.clone();
/*     */     
/* 587 */     if (this.nodeData != null) {
/* 588 */       clone.nodeData = this.nodeData.clone();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 593 */     if (this.referencedModel != null) {
/* 594 */       clone.referencedModel = this.referencedModel.clone();
/*     */     }
/*     */ 
/*     */     
/* 598 */     clone.children = new LinkedList<ModelNode>();
/* 599 */     clone.unmodifiableViewOnContent = Collections.unmodifiableCollection(clone.children);
/*     */     
/* 601 */     for (ModelNode thisChild : this.children) {
/* 602 */       clone.addChild(thisChild.clone());
/*     */     }
/*     */     
/* 605 */     return clone;
/*     */   }
/*     */   
/*     */   PolicyReferenceData getReferenceData() {
/* 609 */     return this.referenceData;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\ModelNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */