/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.encoding.SOAPBindingCodec;
/*     */ import com.sun.xml.ws.encoding.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.encoding.XMLHTTPBindingCodec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Codecs
/*     */ {
/*     */   @NotNull
/*     */   public static SOAPBindingCodec createSOAPBindingCodec(WSFeatureList feature) {
/*  73 */     return (SOAPBindingCodec)new SOAPBindingCodec(feature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Codec createXMLCodec(WSFeatureList feature) {
/*  83 */     return (Codec)new XMLHTTPBindingCodec(feature);
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
/*     */   @NotNull
/*     */   public static SOAPBindingCodec createSOAPBindingCodec(WSBinding binding, StreamSOAPCodec xmlEnvelopeCodec) {
/* 104 */     return (SOAPBindingCodec)new SOAPBindingCodec(binding.getFeatures(), xmlEnvelopeCodec);
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
/*     */   @NotNull
/*     */   public static StreamSOAPCodec createSOAPEnvelopeXmlCodec(@NotNull SOAPVersion version) {
/* 117 */     return (StreamSOAPCodec)StreamSOAPCodec.create(version);
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
/*     */   @NotNull
/*     */   public static StreamSOAPCodec createSOAPEnvelopeXmlCodec(@NotNull WSBinding binding) {
/* 132 */     return (StreamSOAPCodec)StreamSOAPCodec.create(binding);
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
/*     */   @NotNull
/*     */   public static StreamSOAPCodec createSOAPEnvelopeXmlCodec(@NotNull WSFeatureList features) {
/* 145 */     return (StreamSOAPCodec)StreamSOAPCodec.create(features);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\Codecs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */