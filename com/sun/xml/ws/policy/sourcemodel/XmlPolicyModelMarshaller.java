/*     */ package com.sun.xml.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.txw2.TXW;
/*     */ import com.sun.xml.txw2.TypedXmlWriter;
/*     */ import com.sun.xml.txw2.output.StaxSerializer;
/*     */ import com.sun.xml.txw2.output.XmlSerializer;
/*     */ import com.sun.xml.ws.policy.PolicyConstants;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XmlPolicyModelMarshaller
/*     */   extends PolicyModelMarshaller
/*     */ {
/*  62 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(XmlPolicyModelMarshaller.class);
/*     */   
/*     */   private final boolean marshallInvisible;
/*     */ 
/*     */   
/*     */   XmlPolicyModelMarshaller(boolean marshallInvisible) {
/*  68 */     this.marshallInvisible = marshallInvisible;
/*     */   }
/*     */   
/*     */   public void marshal(PolicySourceModel model, Object storage) throws PolicyException {
/*  72 */     if (storage instanceof StaxSerializer) {
/*  73 */       marshal(model, (StaxSerializer)storage);
/*  74 */     } else if (storage instanceof TypedXmlWriter) {
/*  75 */       marshal(model, (TypedXmlWriter)storage);
/*  76 */     } else if (storage instanceof XMLStreamWriter) {
/*  77 */       marshal(model, (XMLStreamWriter)storage);
/*     */     } else {
/*  79 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0022_STORAGE_TYPE_NOT_SUPPORTED(storage.getClass().getName())));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void marshal(Collection<PolicySourceModel> models, Object storage) throws PolicyException {
/*  84 */     for (PolicySourceModel model : models) {
/*  85 */       marshal(model, storage);
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
/*     */   private void marshal(PolicySourceModel model, StaxSerializer writer) throws PolicyException {
/*  97 */     TypedXmlWriter policy = TXW.create(model.getNamespaceVersion().asQName(XmlToken.Policy), TypedXmlWriter.class, (XmlSerializer)writer);
/*     */     
/*  99 */     marshalDefaultPrefixes(model, policy);
/* 100 */     marshalPolicyAttributes(model, policy);
/* 101 */     marshal(model.getNamespaceVersion(), model.getRootNode(), policy);
/* 102 */     policy.commit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshal(PolicySourceModel model, TypedXmlWriter writer) throws PolicyException {
/* 113 */     TypedXmlWriter policy = writer._element(model.getNamespaceVersion().asQName(XmlToken.Policy), TypedXmlWriter.class);
/*     */     
/* 115 */     marshalDefaultPrefixes(model, policy);
/* 116 */     marshalPolicyAttributes(model, policy);
/* 117 */     marshal(model.getNamespaceVersion(), model.getRootNode(), policy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void marshal(PolicySourceModel model, XMLStreamWriter writer) throws PolicyException {
/* 128 */     StaxSerializer serializer = new StaxSerializer(writer);
/* 129 */     TypedXmlWriter policy = TXW.create(model.getNamespaceVersion().asQName(XmlToken.Policy), TypedXmlWriter.class, (XmlSerializer)serializer);
/*     */     
/* 131 */     marshalDefaultPrefixes(model, policy);
/* 132 */     marshalPolicyAttributes(model, policy);
/* 133 */     marshal(model.getNamespaceVersion(), model.getRootNode(), policy);
/* 134 */     policy.commit();
/* 135 */     serializer.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void marshalPolicyAttributes(PolicySourceModel model, TypedXmlWriter writer) {
/* 145 */     String policyId = model.getPolicyId();
/* 146 */     if (policyId != null) {
/* 147 */       writer._attribute(PolicyConstants.WSU_ID, policyId);
/*     */     }
/*     */     
/* 150 */     String policyName = model.getPolicyName();
/* 151 */     if (policyName != null) {
/* 152 */       writer._attribute(model.getNamespaceVersion().asQName(XmlToken.Name), policyName);
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
/*     */   private void marshal(NamespaceVersion nsVersion, ModelNode rootNode, TypedXmlWriter writer) {
/* 164 */     for (ModelNode node : rootNode) {
/* 165 */       AssertionData data = node.getNodeData();
/* 166 */       if (this.marshallInvisible || data == null || !data.isPrivateAttributeSet()) {
/* 167 */         TypedXmlWriter child = null;
/* 168 */         if (data == null) {
/* 169 */           child = writer._element(nsVersion.asQName(node.getType().getXmlToken()), TypedXmlWriter.class);
/*     */         } else {
/* 171 */           child = writer._element(data.getName(), TypedXmlWriter.class);
/* 172 */           String value = data.getValue();
/* 173 */           if (value != null) {
/* 174 */             child._pcdata(value);
/*     */           }
/* 176 */           if (data.isOptionalAttributeSet()) {
/* 177 */             child._attribute(nsVersion.asQName(XmlToken.Optional), Boolean.TRUE);
/*     */           }
/* 179 */           if (data.isIgnorableAttributeSet()) {
/* 180 */             child._attribute(nsVersion.asQName(XmlToken.Ignorable), Boolean.TRUE);
/*     */           }
/* 182 */           for (Map.Entry<QName, String> entry : data.getAttributesSet()) {
/* 183 */             child._attribute(entry.getKey(), entry.getValue());
/*     */           }
/*     */         } 
/* 186 */         marshal(nsVersion, node, child);
/*     */       } 
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
/*     */   private void marshalDefaultPrefixes(PolicySourceModel model, TypedXmlWriter writer) throws PolicyException {
/* 199 */     Map<String, String> nsMap = model.getNamespaceToPrefixMapping();
/* 200 */     if (!this.marshallInvisible && nsMap.containsKey("http://java.sun.com/xml/ns/wsit/policy")) {
/* 201 */       nsMap.remove("http://java.sun.com/xml/ns/wsit/policy");
/*     */     }
/* 203 */     for (Map.Entry<String, String> nsMappingEntry : nsMap.entrySet())
/* 204 */       writer._namespace(nsMappingEntry.getKey(), nsMappingEntry.getValue()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\XmlPolicyModelMarshaller.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */