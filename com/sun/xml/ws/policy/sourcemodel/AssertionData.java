/*     */ package com.sun.xml.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyConstants;
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ public final class AssertionData
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4416256070795526315L;
/*  68 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(AssertionData.class);
/*     */ 
/*     */   
/*     */   private final QName name;
/*     */ 
/*     */   
/*     */   private final String value;
/*     */ 
/*     */   
/*     */   private final Map<QName, String> attributes;
/*     */ 
/*     */   
/*     */   private ModelNode.Type type;
/*     */ 
/*     */   
/*     */   private boolean optional;
/*     */   
/*     */   private boolean ignorable;
/*     */ 
/*     */   
/*     */   public static AssertionData createAssertionData(QName name) throws IllegalArgumentException {
/*  89 */     return new AssertionData(name, null, null, ModelNode.Type.ASSERTION, false, false);
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
/*     */   public static AssertionData createAssertionParameterData(QName name) throws IllegalArgumentException {
/* 103 */     return new AssertionData(name, null, null, ModelNode.Type.ASSERTION_PARAMETER_NODE, false, false);
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
/*     */   public static AssertionData createAssertionData(QName name, String value, Map<QName, String> attributes, boolean optional, boolean ignorable) throws IllegalArgumentException {
/* 121 */     return new AssertionData(name, value, attributes, ModelNode.Type.ASSERTION, optional, ignorable);
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
/*     */   public static AssertionData createAssertionParameterData(QName name, String value, Map<QName, String> attributes) throws IllegalArgumentException {
/* 137 */     return new AssertionData(name, value, attributes, ModelNode.Type.ASSERTION_PARAMETER_NODE, false, false);
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
/*     */   AssertionData(QName name, String value, Map<QName, String> attributes, ModelNode.Type type, boolean optional, boolean ignorable) throws IllegalArgumentException {
/* 158 */     this.name = name;
/* 159 */     this.value = value;
/* 160 */     this.optional = optional;
/* 161 */     this.ignorable = ignorable;
/*     */     
/* 163 */     this.attributes = new HashMap<QName, String>();
/* 164 */     if (attributes != null && !attributes.isEmpty()) {
/* 165 */       this.attributes.putAll(attributes);
/*     */     }
/* 167 */     setModelNodeType(type);
/*     */   }
/*     */   
/*     */   private void setModelNodeType(ModelNode.Type type) throws IllegalArgumentException {
/* 171 */     if (type == ModelNode.Type.ASSERTION || type == ModelNode.Type.ASSERTION_PARAMETER_NODE) {
/* 172 */       this.type = type;
/*     */     } else {
/* 174 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0074_CANNOT_CREATE_ASSERTION_BAD_TYPE(type, ModelNode.Type.ASSERTION, ModelNode.Type.ASSERTION_PARAMETER_NODE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AssertionData(AssertionData data) {
/* 185 */     this.name = data.name;
/* 186 */     this.value = data.value;
/* 187 */     this.attributes = new HashMap<QName, String>();
/* 188 */     if (!data.attributes.isEmpty()) {
/* 189 */       this.attributes.putAll(data.attributes);
/*     */     }
/* 191 */     this.type = data.type;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AssertionData clone() throws CloneNotSupportedException {
/* 196 */     return (AssertionData)super.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsAttribute(QName name) {
/* 206 */     synchronized (this.attributes) {
/* 207 */       return this.attributes.containsKey(name);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 214 */     if (this == obj) {
/* 215 */       return true;
/*     */     }
/*     */     
/* 218 */     if (!(obj instanceof AssertionData)) {
/* 219 */       return false;
/*     */     }
/*     */     
/* 222 */     boolean result = true;
/* 223 */     AssertionData that = (AssertionData)obj;
/*     */     
/* 225 */     result = (result && this.name.equals(that.name));
/* 226 */     result = (result && ((this.value == null) ? (that.value == null) : this.value.equals(that.value)));
/* 227 */     synchronized (this.attributes) {
/* 228 */       result = (result && this.attributes.equals(that.attributes));
/*     */     } 
/*     */     
/* 231 */     return result;
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
/*     */   public String getAttributeValue(QName name) {
/* 244 */     synchronized (this.attributes) {
/* 245 */       return this.attributes.get(name);
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
/*     */   public Map<QName, String> getAttributes() {
/* 260 */     synchronized (this.attributes) {
/* 261 */       return new HashMap<QName, String>(this.attributes);
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
/*     */   public Set<Map.Entry<QName, String>> getAttributesSet() {
/* 277 */     synchronized (this.attributes) {
/* 278 */       return new HashSet<Map.Entry<QName, String>>(this.attributes.entrySet());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/* 289 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 299 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 308 */     int result = 17;
/*     */     
/* 310 */     result = 37 * result + this.name.hashCode();
/* 311 */     result = 37 * result + ((this.value == null) ? 0 : this.value.hashCode());
/* 312 */     synchronized (this.attributes) {
/* 313 */       result = 37 * result + this.attributes.hashCode();
/*     */     } 
/* 315 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPrivateAttributeSet() {
/* 326 */     return "private".equals(getAttributeValue(PolicyConstants.VISIBILITY_ATTRIBUTE));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String removeAttribute(QName name) {
/* 336 */     synchronized (this.attributes) {
/* 337 */       return this.attributes.remove(name);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(QName name, String value) {
/* 348 */     synchronized (this.attributes) {
/* 349 */       this.attributes.put(name, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOptionalAttribute(boolean value) {
/* 359 */     this.optional = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOptionalAttributeSet() {
/* 368 */     return this.optional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIgnorableAttribute(boolean value) {
/* 377 */     this.ignorable = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIgnorableAttributeSet() {
/* 386 */     return this.ignorable;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 391 */     return toString(0, new StringBuffer()).toString();
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
/* 402 */     String indent = PolicyUtils.Text.createIndent(indentLevel);
/* 403 */     String innerIndent = PolicyUtils.Text.createIndent(indentLevel + 1);
/* 404 */     String innerDoubleIndent = PolicyUtils.Text.createIndent(indentLevel + 2);
/*     */     
/* 406 */     buffer.append(indent);
/* 407 */     if (this.type == ModelNode.Type.ASSERTION) {
/* 408 */       buffer.append("assertion data {");
/*     */     } else {
/* 410 */       buffer.append("assertion parameter data {");
/*     */     } 
/* 412 */     buffer.append(PolicyUtils.Text.NEW_LINE);
/*     */     
/* 414 */     buffer.append(innerIndent).append("namespace = '").append(this.name.getNamespaceURI()).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 415 */     buffer.append(innerIndent).append("prefix = '").append(this.name.getPrefix()).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 416 */     buffer.append(innerIndent).append("local name = '").append(this.name.getLocalPart()).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 417 */     buffer.append(innerIndent).append("value = '").append(this.value).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 418 */     buffer.append(innerIndent).append("optional = '").append(this.optional).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 419 */     buffer.append(innerIndent).append("ignorable = '").append(this.ignorable).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 420 */     synchronized (this.attributes) {
/* 421 */       if (this.attributes.isEmpty()) {
/* 422 */         buffer.append(innerIndent).append("no attributes");
/*     */       } else {
/*     */         
/* 425 */         buffer.append(innerIndent).append("attributes {").append(PolicyUtils.Text.NEW_LINE);
/* 426 */         for (Map.Entry<QName, String> entry : this.attributes.entrySet()) {
/* 427 */           QName aName = entry.getKey();
/* 428 */           buffer.append(innerDoubleIndent).append("name = '").append(aName.getNamespaceURI()).append(':').append(aName.getLocalPart());
/* 429 */           buffer.append("', value = '").append(entry.getValue()).append('\'').append(PolicyUtils.Text.NEW_LINE);
/*     */         } 
/* 431 */         buffer.append(innerIndent).append('}');
/*     */       } 
/*     */     } 
/*     */     
/* 435 */     buffer.append(PolicyUtils.Text.NEW_LINE).append(indent).append('}');
/*     */     
/* 437 */     return buffer;
/*     */   }
/*     */   
/*     */   public ModelNode.Type getNodeType() {
/* 441 */     return this.type;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\AssertionData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */