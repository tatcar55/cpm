/*     */ package com.google.zxing.client.j2se;
/*     */ 
/*     */ import com.beust.jcommander.Parameter;
/*     */ import com.beust.jcommander.validators.PositiveInteger;
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DecoderConfig
/*     */ {
/*     */   @Parameter(names = {"--try_harder"}, description = "Use the TRY_HARDER hint, default is normal mode")
/*     */   boolean tryHarder;
/*     */   @Parameter(names = {"--pure_barcode"}, description = "Input image is a pure monochrome barcode image, not a photo")
/*     */   boolean pureBarcode;
/*     */   @Parameter(names = {"--products_only"}, description = "Only decode the UPC and EAN families of barcodes")
/*     */   boolean productsOnly;
/*     */   @Parameter(names = {"--dump_results"}, description = "Write the decoded contents to input.txt")
/*     */   boolean dumpResults;
/*     */   @Parameter(names = {"--dump_black_point"}, description = "Compare black point algorithms with dump as input.mono.png")
/*     */   boolean dumpBlackPoint;
/*     */   @Parameter(names = {"--multi"}, description = "Scans image for multiple barcodes")
/*     */   boolean multi;
/*     */   @Parameter(names = {"--brief"}, description = "Only output one line per file, omitting the contents")
/*     */   boolean brief;
/*     */   @Parameter(names = {"--recursive"}, description = "Descend into subdirectories")
/*     */   boolean recursive;
/*     */   @Parameter(names = {"--crop"}, description = " Only examine cropped region of input image(s)", arity = 4, validateWith = PositiveInteger.class)
/*     */   List<Integer> crop;
/*     */   @Parameter(names = {"--possible_formats"}, description = "Formats to decode, where format is any value in BarcodeFormat", variableArity = true)
/*     */   List<BarcodeFormat> possibleFormats;
/*     */   @Parameter(names = {"--help"}, description = "Prints this help message", help = true)
/*     */   boolean help;
/*     */   @Parameter(description = "(URIs to decode)", required = true, variableArity = true)
/*     */   List<URI> inputPaths;
/*     */   
/*     */   Map<DecodeHintType, ?> buildHints() {
/*  86 */     List<BarcodeFormat> finalPossibleFormats = this.possibleFormats;
/*  87 */     if (finalPossibleFormats == null || finalPossibleFormats.isEmpty()) {
/*  88 */       finalPossibleFormats = new ArrayList<>();
/*  89 */       finalPossibleFormats.addAll(Arrays.asList(new BarcodeFormat[] { BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       if (!this.productsOnly) {
/*  98 */         finalPossibleFormats.addAll(Arrays.asList(new BarcodeFormat[] { BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.QR_CODE, BarcodeFormat.DATA_MATRIX, BarcodeFormat.AZTEC, BarcodeFormat.PDF_417, BarcodeFormat.CODABAR, BarcodeFormat.MAXICODE }));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
/* 114 */     hints.put(DecodeHintType.POSSIBLE_FORMATS, finalPossibleFormats);
/* 115 */     if (this.tryHarder) {
/* 116 */       hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
/*     */     }
/* 118 */     if (this.pureBarcode) {
/* 119 */       hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
/*     */     }
/* 121 */     return Collections.unmodifiableMap(hints);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\j2se\DecoderConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */