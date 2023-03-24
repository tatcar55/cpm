/*    */ package com.google.zxing.client.j2se;
/*    */ 
/*    */ import com.beust.jcommander.Parameter;
/*    */ import com.beust.jcommander.validators.PositiveInteger;
/*    */ import com.google.zxing.BarcodeFormat;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class EncoderConfig
/*    */ {
/*    */   static final String DEFAULT_OUTPUT_FILE_BASE = "out";
/*    */   @Parameter(names = {"--barcode_format"}, description = "Format to encode, from BarcodeFormat class. Not all formats are supported")
/* 29 */   BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;
/*    */ 
/*    */   
/*    */   @Parameter(names = {"--image_format"}, description = "image output format, such as PNG, JPG, GIF")
/* 33 */   String imageFormat = "PNG";
/*    */ 
/*    */   
/*    */   @Parameter(names = {"--output"}, description = " File to write to. Defaults to out.png")
/* 37 */   String outputFileBase = "out";
/*    */ 
/*    */   
/*    */   @Parameter(names = {"--width"}, description = "Image width", validateWith = PositiveInteger.class)
/* 41 */   int width = 300;
/*    */ 
/*    */ 
/*    */   
/*    */   @Parameter(names = {"--height"}, description = "Image height", validateWith = PositiveInteger.class)
/* 46 */   int height = 300;
/*    */   @Parameter(names = {"--help"}, description = "Prints this help message", help = true)
/*    */   boolean help;
/*    */   @Parameter(description = "(Text to encode)", required = true)
/*    */   List<String> contents;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\j2se\EncoderConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */