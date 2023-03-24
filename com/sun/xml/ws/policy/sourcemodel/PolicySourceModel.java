/*     */ package com.sun.xml.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.ws.policy.spi.PrefixMapper;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
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
/*     */ public class PolicySourceModel
/*     */   implements Cloneable
/*     */ {
/*  71 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicySourceModel.class);
/*     */   
/*  73 */   private static final Map<String, String> DEFAULT_NAMESPACE_TO_PREFIX = new HashMap<String, String>();
/*     */   static {
/*  75 */     PrefixMapper[] prefixMappers = (PrefixMapper[])PolicyUtils.ServiceProvider.load(PrefixMapper.class);
/*  76 */     if (prefixMappers != null) {
/*  77 */       for (PrefixMapper mapper : prefixMappers) {
/*  78 */         DEFAULT_NAMESPACE_TO_PREFIX.putAll(mapper.getPrefixMap());
/*     */       }
/*     */     }
/*     */     
/*  82 */     for (NamespaceVersion version : NamespaceVersion.values()) {
/*  83 */       DEFAULT_NAMESPACE_TO_PREFIX.put(version.toString(), version.getDefaultNamespacePrefix());
/*     */     }
/*  85 */     DEFAULT_NAMESPACE_TO_PREFIX.put("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu");
/*     */     
/*  87 */     DEFAULT_NAMESPACE_TO_PREFIX.put("http://java.sun.com/xml/ns/wsit/policy", "sunwsp");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  92 */   private final Map<String, String> namespaceToPrefix = new HashMap<String, String>(DEFAULT_NAMESPACE_TO_PREFIX);
/*     */   
/*     */   private ModelNode rootNode;
/*     */   
/*     */   private final String policyId;
/*     */   private final String policyName;
/*     */   private final NamespaceVersion nsVersion;
/*  99 */   private final List<ModelNode> references = new LinkedList<ModelNode>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean expanded = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PolicySourceModel createPolicySourceModel(NamespaceVersion nsVersion) {
/* 113 */     return new PolicySourceModel(nsVersion);
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
/*     */   public static PolicySourceModel createPolicySourceModel(NamespaceVersion nsVersion, String policyId, String policyName) {
/* 128 */     return new PolicySourceModel(nsVersion, policyId, policyName);
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
/*     */   private PolicySourceModel(NamespaceVersion nsVersion) {
/* 140 */     this(nsVersion, null, null);
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
/*     */   private PolicySourceModel(NamespaceVersion nsVersion, String policyId, String policyName) {
/* 152 */     this(nsVersion, policyId, policyName, null);
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
/*     */   protected PolicySourceModel(NamespaceVersion nsVersion, String policyId, String policyName, Collection<PrefixMapper> prefixMappers) {
/* 168 */     this.rootNode = ModelNode.createRootPolicyNode(this);
/* 169 */     this.nsVersion = nsVersion;
/* 170 */     this.policyId = policyId;
/* 171 */     this.policyName = policyName;
/* 172 */     if (prefixMappers != null) {
/* 173 */       for (PrefixMapper prefixMapper : prefixMappers) {
/* 174 */         this.namespaceToPrefix.putAll(prefixMapper.getPrefixMap());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelNode getRootNode() {
/* 185 */     return this.rootNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPolicyName() {
/* 194 */     return this.policyName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPolicyId() {
/* 203 */     return this.policyId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceVersion getNamespaceVersion() {
/* 212 */     return this.nsVersion;
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
/*     */   Map<String, String> getNamespaceToPrefixMapping() throws PolicyException {
/* 225 */     Map<String, String> nsToPrefixMap = new HashMap<String, String>();
/*     */     
/* 227 */     Collection<String> namespaces = getUsedNamespaces();
/* 228 */     for (String namespace : namespaces) {
/* 229 */       String prefix = getDefaultPrefix(namespace);
/* 230 */       if (prefix != null) {
/* 231 */         nsToPrefixMap.put(namespace, prefix);
/*     */       }
/*     */     } 
/*     */     
/* 235 */     return nsToPrefixMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 246 */     if (this == obj) {
/* 247 */       return true;
/*     */     }
/*     */     
/* 250 */     if (!(obj instanceof PolicySourceModel)) {
/* 251 */       return false;
/*     */     }
/*     */     
/* 254 */     boolean result = true;
/* 255 */     PolicySourceModel that = (PolicySourceModel)obj;
/*     */     
/* 257 */     result = (result && ((this.policyId == null) ? (that.policyId == null) : this.policyId.equals(that.policyId)));
/* 258 */     result = (result && ((this.policyName == null) ? (that.policyName == null) : this.policyName.equals(that.policyName)));
/* 259 */     result = (result && this.rootNode.equals(that.rootNode));
/*     */     
/* 261 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 269 */     int result = 17;
/*     */     
/* 271 */     result = 37 * result + ((this.policyId == null) ? 0 : this.policyId.hashCode());
/* 272 */     result = 37 * result + ((this.policyName == null) ? 0 : this.policyName.hashCode());
/* 273 */     result = 37 * result + this.rootNode.hashCode();
/*     */     
/* 275 */     return result;
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
/* 286 */     String innerIndent = PolicyUtils.Text.createIndent(1);
/* 287 */     StringBuffer buffer = new StringBuffer(60);
/*     */     
/* 289 */     buffer.append("Policy source model {").append(PolicyUtils.Text.NEW_LINE);
/* 290 */     buffer.append(innerIndent).append("policy id = '").append(this.policyId).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 291 */     buffer.append(innerIndent).append("policy name = '").append(this.policyName).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 292 */     this.rootNode.toString(1, buffer).append(PolicyUtils.Text.NEW_LINE).append('}');
/*     */     
/* 294 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected PolicySourceModel clone() throws CloneNotSupportedException {
/* 299 */     PolicySourceModel clone = (PolicySourceModel)super.clone();
/*     */     
/* 301 */     clone.rootNode = this.rootNode.clone();
/*     */     try {
/* 303 */       clone.rootNode.setParentModel(clone);
/* 304 */     } catch (IllegalAccessException e) {
/* 305 */       throw (CloneNotSupportedException)LOGGER.logSevereException(new CloneNotSupportedException(LocalizationMessages.WSP_0013_UNABLE_TO_SET_PARENT_MODEL_ON_ROOT()), e);
/*     */     } 
/*     */     
/* 308 */     return clone;
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
/*     */   public boolean containsPolicyReferences() {
/* 320 */     return !this.references.isEmpty();
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
/*     */   private boolean isExpanded() {
/* 337 */     return (this.references.isEmpty() || this.expanded);
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
/*     */   public synchronized void expand(PolicySourceModelContext context) throws PolicyException {
/* 355 */     if (!isExpanded()) {
/* 356 */       for (ModelNode reference : this.references) {
/* 357 */         PolicySourceModel referencedModel; PolicyReferenceData refData = reference.getPolicyReferenceData();
/* 358 */         String digest = refData.getDigest();
/*     */         
/* 360 */         if (digest == null) {
/* 361 */           referencedModel = context.retrieveModel(refData.getReferencedModelUri());
/*     */         } else {
/* 363 */           referencedModel = context.retrieveModel(refData.getReferencedModelUri(), refData.getDigestAlgorithmUri(), digest);
/*     */         } 
/*     */         
/* 366 */         reference.setReferencedModel(referencedModel);
/*     */       } 
/* 368 */       this.expanded = true;
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
/*     */   void addNewPolicyReference(ModelNode node) {
/* 381 */     if (node.getType() != ModelNode.Type.POLICY_REFERENCE) {
/* 382 */       throw new IllegalArgumentException(LocalizationMessages.WSP_0042_POLICY_REFERENCE_NODE_EXPECTED_INSTEAD_OF(node.getType()));
/*     */     }
/*     */     
/* 385 */     this.references.add(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection<String> getUsedNamespaces() throws PolicyException {
/* 396 */     Set<String> namespaces = new HashSet<String>();
/* 397 */     namespaces.add(getNamespaceVersion().toString());
/*     */     
/* 399 */     if (this.policyId != null) {
/* 400 */       namespaces.add("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */     }
/*     */     
/* 403 */     Queue<ModelNode> nodesToBeProcessed = new LinkedList<ModelNode>();
/* 404 */     nodesToBeProcessed.add(this.rootNode);
/*     */     
/*     */     ModelNode processedNode;
/* 407 */     while ((processedNode = nodesToBeProcessed.poll()) != null) {
/* 408 */       for (ModelNode child : processedNode.getChildren()) {
/* 409 */         if (child.hasChildren() && 
/* 410 */           !nodesToBeProcessed.offer(child)) {
/* 411 */           throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0081_UNABLE_TO_INSERT_CHILD(nodesToBeProcessed, child)));
/*     */         }
/*     */ 
/*     */         
/* 415 */         if (child.isDomainSpecific()) {
/* 416 */           AssertionData nodeData = child.getNodeData();
/* 417 */           namespaces.add(nodeData.getName().getNamespaceURI());
/* 418 */           if (nodeData.isPrivateAttributeSet()) {
/* 419 */             namespaces.add("http://java.sun.com/xml/ns/wsit/policy");
/*     */           }
/*     */           
/* 422 */           for (Map.Entry<QName, String> attribute : nodeData.getAttributesSet()) {
/* 423 */             namespaces.add(((QName)attribute.getKey()).getNamespaceURI());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 429 */     return namespaces;
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
/*     */   private String getDefaultPrefix(String namespace) {
/* 441 */     return this.namespaceToPrefix.get(namespace);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\PolicySourceModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */