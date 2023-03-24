/*    */ package com.google.zxing.client.j2se;
/*    */ 
/*    */ import com.beust.jcommander.JCommander;
/*    */ import com.google.zxing.MultiFormatWriter;
/*    */ import com.google.zxing.common.BitMatrix;
/*    */ import java.nio.file.Paths;
/*    */ import java.util.Locale;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CommandLineEncoder
/*    */ {
/*    */   public static void main(String[] args) throws Exception {
/* 37 */     EncoderConfig config = new EncoderConfig();
/* 38 */     JCommander jCommander = new JCommander(config, args);
/* 39 */     jCommander.setProgramName(CommandLineEncoder.class.getSimpleName());
/* 40 */     if (config.help) {
/* 41 */       jCommander.usage();
/*    */       
/*    */       return;
/*    */     } 
/* 45 */     String outFileString = config.outputFileBase;
/* 46 */     if ("out".equals(outFileString)) {
/* 47 */       outFileString = outFileString + '.' + config.imageFormat.toLowerCase(Locale.ENGLISH);
/*    */     }
/*    */     
/* 50 */     BitMatrix matrix = (new MultiFormatWriter()).encode(config.contents
/* 51 */         .get(0), config.barcodeFormat, config.width, config.height);
/* 52 */     MatrixToImageWriter.writeToPath(matrix, config.imageFormat, Paths.get(outFileString, new String[0]));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\j2se\CommandLineEncoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */