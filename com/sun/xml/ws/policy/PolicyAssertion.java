/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.sourcemodel.ModelNode;
/*     */ import java.util.Collection;
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
/*     */ public abstract class PolicyAssertion
/*     */ {
/*     */   private final AssertionData data;
/*     */   private AssertionSet parameters;
/*     */   private NestedPolicy nestedPolicy;
/*     */   
/*     */   protected PolicyAssertion() {
/*  70 */     this.data = AssertionData.createAssertionData(null);
/*  71 */     this.parameters = AssertionSet.createAssertionSet(null);
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
/*     */   @Deprecated
/*     */   protected PolicyAssertion(AssertionData assertionData, Collection<? extends PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/*  88 */     this.data = assertionData;
/*  89 */     if (nestedAlternative != null) {
/*  90 */       this.nestedPolicy = NestedPolicy.createNestedPolicy(nestedAlternative);
/*     */     }
/*     */     
/*  93 */     this.parameters = AssertionSet.createAssertionSet(assertionParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PolicyAssertion(AssertionData assertionData, Collection<? extends PolicyAssertion> assertionParameters) {
/* 103 */     if (assertionData == null) {
/* 104 */       this.data = AssertionData.createAssertionData(null);
/*     */     } else {
/* 106 */       this.data = assertionData;
/*     */     } 
/* 108 */     this.parameters = AssertionSet.createAssertionSet(assertionParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QName getName() {
/* 117 */     return this.data.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getValue() {
/* 126 */     return this.data.getValue();
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
/*     */   public boolean isOptional() {
/* 138 */     return this.data.isOptionalAttributeSet();
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
/*     */   public boolean isIgnorable() {
/* 150 */     return this.data.isIgnorableAttributeSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isPrivate() {
/* 159 */     return this.data.isPrivateAttributeSet();
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
/*     */   public final Set<Map.Entry<QName, String>> getAttributesSet() {
/* 173 */     return this.data.getAttributesSet();
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
/*     */   public final Map<QName, String> getAttributes() {
/* 186 */     return this.data.getAttributes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getAttributeValue(QName name) {
/* 196 */     return this.data.getAttributeValue(name);
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
/*     */   @Deprecated
/*     */   public final boolean hasNestedAssertions() {
/* 209 */     return !this.parameters.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasParameters() {
/* 218 */     return !this.parameters.isEmpty();
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
/*     */   @Deprecated
/*     */   public final Iterator<PolicyAssertion> getNestedAssertionsIterator() {
/* 231 */     return this.parameters.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Iterator<PolicyAssertion> getParametersIterator() {
/* 240 */     return this.parameters.iterator();
/*     */   }
/*     */   
/*     */   boolean isParameter() {
/* 244 */     return (this.data.getNodeType() == ModelNode.Type.ASSERTION_PARAMETER_NODE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNestedPolicy() {
/* 254 */     return (getNestedPolicy() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NestedPolicy getNestedPolicy() {
/* 264 */     return this.nestedPolicy;
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
/*     */   public <T extends PolicyAssertion> T getImplementation(Class<T> type) {
/* 276 */     if (type.isAssignableFrom(getClass())) {
/* 277 */       return type.cast(this);
/*     */     }
/*     */     
/* 280 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 289 */     return toString(0, new StringBuffer()).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StringBuffer toString(int indentLevel, StringBuffer buffer) {
/* 300 */     String indent = PolicyUtils.Text.createIndent(indentLevel);
/* 301 */     String innerIndent = PolicyUtils.Text.createIndent(indentLevel + 1);
/*     */     
/* 303 */     buffer.append(indent).append("Assertion[").append(getClass().getName()).append("] {").append(PolicyUtils.Text.NEW_LINE);
/* 304 */     this.data.toString(indentLevel + 1, buffer);
/* 305 */     buffer.append(PolicyUtils.Text.NEW_LINE);
/*     */     
/* 307 */     if (hasParameters()) {
/* 308 */       buffer.append(innerIndent).append("parameters {").append(PolicyUtils.Text.NEW_LINE);
/* 309 */       for (PolicyAssertion parameter : this.parameters) {
/* 310 */         parameter.toString(indentLevel + 2, buffer).append(PolicyUtils.Text.NEW_LINE);
/*     */       }
/* 312 */       buffer.append(innerIndent).append('}').append(PolicyUtils.Text.NEW_LINE);
/*     */     } else {
/* 314 */       buffer.append(innerIndent).append("no parameters").append(PolicyUtils.Text.NEW_LINE);
/*     */     } 
/*     */     
/* 317 */     if (hasNestedPolicy()) {
/* 318 */       getNestedPolicy().toString(indentLevel + 1, buffer).append(PolicyUtils.Text.NEW_LINE);
/*     */     } else {
/* 320 */       buffer.append(innerIndent).append("no nested policy").append(PolicyUtils.Text.NEW_LINE);
/*     */     } 
/*     */     
/* 323 */     buffer.append(indent).append('}');
/*     */     
/* 325 */     return buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isCompatibleWith(PolicyAssertion assertion, PolicyIntersector.CompatibilityMode mode) {
/* 336 */     boolean result = (this.data.getName().equals(assertion.data.getName()) && hasNestedPolicy() == assertion.hasNestedPolicy());
/*     */     
/* 338 */     if (result && hasNestedPolicy()) {
/* 339 */       result = getNestedPolicy().getAssertionSet().isCompatibleWith(assertion.getNestedPolicy().getAssertionSet(), mode);
/*     */     }
/*     */     
/* 342 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 350 */     if (this == obj) {
/* 351 */       return true;
/*     */     }
/*     */     
/* 354 */     if (!(obj instanceof PolicyAssertion)) {
/* 355 */       return false;
/*     */     }
/*     */     
/* 358 */     PolicyAssertion that = (PolicyAssertion)obj;
/* 359 */     boolean result = true;
/*     */     
/* 361 */     result = (result && this.data.equals(that.data));
/* 362 */     result = (result && this.parameters.equals(that.parameters));
/* 363 */     result = (result && ((getNestedPolicy() == null) ? (that.getNestedPolicy() == null) : getNestedPolicy().equals(that.getNestedPolicy())));
/*     */     
/* 365 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 373 */     int result = 17;
/*     */     
/* 375 */     result = 37 * result + this.data.hashCode();
/* 376 */     result = 37 * result + (hasParameters() ? 17 : 0);
/* 377 */     result = 37 * result + (hasNestedPolicy() ? 17 : 0);
/*     */     
/* 379 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\PolicyAssertion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */