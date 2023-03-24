/*     */ package com.sun.xml.ws.api.policy;
/*     */ 
/*     */ import com.sun.xml.ws.addressing.policy.AddressingPrefixMapper;
/*     */ import com.sun.xml.ws.config.management.policy.ManagementPrefixMapper;
/*     */ import com.sun.xml.ws.encoding.policy.EncodingPrefixMapper;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.ws.policy.spi.PrefixMapper;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SourceModel
/*     */   extends PolicySourceModel
/*     */ {
/*  61 */   private static final PrefixMapper[] JAXWS_PREFIX_MAPPERS = new PrefixMapper[] { (PrefixMapper)new AddressingPrefixMapper(), (PrefixMapper)new EncodingPrefixMapper(), (PrefixMapper)new ManagementPrefixMapper() };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SourceModel(NamespaceVersion nsVersion) {
/*  77 */     this(nsVersion, null, null);
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
/*     */   private SourceModel(NamespaceVersion nsVersion, String policyId, String policyName) {
/*  89 */     super(nsVersion, policyId, policyName, Arrays.asList(JAXWS_PREFIX_MAPPERS));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PolicySourceModel createSourceModel(NamespaceVersion nsVersion) {
/*  99 */     return new SourceModel(nsVersion);
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
/*     */   public static PolicySourceModel createSourceModel(NamespaceVersion nsVersion, String policyId, String policyName) {
/* 112 */     return new SourceModel(nsVersion, policyId, policyName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\policy\SourceModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */