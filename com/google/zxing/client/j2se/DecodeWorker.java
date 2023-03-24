/*     */ package com.google.zxing.client.j2se;
/*     */ 
/*     */ import com.google.zxing.Binarizer;
/*     */ import com.google.zxing.BinaryBitmap;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.LuminanceSource;
/*     */ import com.google.zxing.MultiFormatReader;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.Reader;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.client.result.ParsedResult;
/*     */ import com.google.zxing.client.result.ResultParser;
/*     */ import com.google.zxing.common.BitArray;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.HybridBinarizer;
/*     */ import com.google.zxing.multi.GenericMultipleBarcodeReader;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DecodeWorker
/*     */   implements Callable<Integer>
/*     */ {
/*     */   private static final int RED = -65536;
/*     */   private static final int BLACK = -16777216;
/*     */   private static final int WHITE = -1;
/*     */   private final DecoderConfig config;
/*     */   private final Queue<URI> inputs;
/*     */   private final Map<DecodeHintType, ?> hints;
/*     */   
/*     */   DecodeWorker(DecoderConfig config, Queue<URI> inputs) {
/*  66 */     this.config = config;
/*  67 */     this.inputs = inputs;
/*  68 */     this.hints = config.buildHints();
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer call() throws IOException {
/*  73 */     int successful = 0; URI input;
/*  74 */     while ((input = this.inputs.poll()) != null) {
/*  75 */       Result[] results = decode(input, this.hints);
/*  76 */       if (results != null) {
/*  77 */         successful++;
/*  78 */         if (this.config.dumpResults) {
/*  79 */           dumpResult(input, results);
/*     */         }
/*     */       } 
/*     */     } 
/*  83 */     return Integer.valueOf(successful);
/*     */   }
/*     */   
/*     */   private static Path buildOutputPath(URI input, String suffix) throws IOException {
/*     */     Path outDir;
/*     */     String inputFileName;
/*  89 */     if ("file".equals(input.getScheme())) {
/*  90 */       Path inputPath = Paths.get(input);
/*  91 */       outDir = inputPath.getParent();
/*  92 */       inputFileName = inputPath.getFileName().toString();
/*     */     } else {
/*  94 */       outDir = Paths.get(".", new String[0]).toRealPath(new java.nio.file.LinkOption[0]);
/*  95 */       String[] pathElements = input.getPath().split("/");
/*  96 */       inputFileName = pathElements[pathElements.length - 1];
/*     */     } 
/*     */ 
/*     */     
/* 100 */     int pos = inputFileName.lastIndexOf('.');
/* 101 */     if (pos > 0) {
/* 102 */       inputFileName = inputFileName.substring(0, pos) + suffix;
/*     */     } else {
/* 104 */       inputFileName = inputFileName + suffix;
/*     */     } 
/*     */     
/* 107 */     return outDir.resolve(inputFileName);
/*     */   }
/*     */   
/*     */   private static void dumpResult(URI input, Result... results) throws IOException {
/* 111 */     Collection<String> resultTexts = new ArrayList<>();
/* 112 */     for (Result result : results) {
/* 113 */       resultTexts.add(result.getText());
/*     */     }
/* 115 */     Files.write(buildOutputPath(input, ".txt"), (Iterable)resultTexts, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]);
/*     */   } private Result[] decode(URI uri, Map<DecodeHintType, ?> hints) throws IOException {
/*     */     LuminanceSource source;
/*     */     Result[] results;
/* 119 */     BufferedImage image = ImageReader.readImage(uri);
/*     */ 
/*     */     
/* 122 */     if (this.config.crop == null) {
/* 123 */       source = new BufferedImageLuminanceSource(image);
/*     */     } else {
/* 125 */       List<Integer> crop = this.config.crop;
/*     */       
/* 127 */       source = new BufferedImageLuminanceSource(image, ((Integer)crop.get(0)).intValue(), ((Integer)crop.get(1)).intValue(), ((Integer)crop.get(2)).intValue(), ((Integer)crop.get(3)).intValue());
/*     */     } 
/*     */     
/* 130 */     BinaryBitmap bitmap = new BinaryBitmap((Binarizer)new HybridBinarizer(source));
/* 131 */     if (this.config.dumpBlackPoint) {
/* 132 */       dumpBlackPoint(uri, image, bitmap);
/*     */     }
/*     */     
/* 135 */     MultiFormatReader multiFormatReader = new MultiFormatReader();
/*     */     
/*     */     try {
/* 138 */       if (this.config.multi) {
/* 139 */         GenericMultipleBarcodeReader genericMultipleBarcodeReader = new GenericMultipleBarcodeReader((Reader)multiFormatReader);
/* 140 */         results = genericMultipleBarcodeReader.decodeMultiple(bitmap, hints);
/*     */       } else {
/* 142 */         results = new Result[] { multiFormatReader.decode(bitmap, hints) };
/*     */       } 
/* 144 */     } catch (NotFoundException ignored) {
/* 145 */       System.out.println(uri + ": No barcode found");
/* 146 */       return null;
/*     */     } 
/*     */     
/* 149 */     if (this.config.brief) {
/* 150 */       System.out.println(uri + ": Success");
/*     */     } else {
/* 152 */       for (Result result : results) {
/* 153 */         ParsedResult parsedResult = ResultParser.parseResult(result);
/* 154 */         System.out.println(uri + " (format: " + result
/* 155 */             .getBarcodeFormat() + ", type: " + parsedResult
/* 156 */             .getType() + "):\n" + "Raw result:\n" + result
/*     */             
/* 158 */             .getText() + "\n" + "Parsed result:\n" + parsedResult
/*     */             
/* 160 */             .getDisplayResult());
/* 161 */         System.out.println("Found " + (result.getResultPoints()).length + " result points.");
/* 162 */         for (int i = 0; i < (result.getResultPoints()).length; i++) {
/* 163 */           ResultPoint rp = result.getResultPoints()[i];
/* 164 */           System.out.println("  Point " + i + ": (" + rp.getX() + ',' + rp.getY() + ')');
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     return results;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void dumpBlackPoint(URI uri, BufferedImage image, BinaryBitmap bitmap) throws IOException {
/* 178 */     int width = bitmap.getWidth();
/* 179 */     int height = bitmap.getHeight();
/* 180 */     int stride = width * 3;
/* 181 */     int[] pixels = new int[stride * height];
/*     */ 
/*     */     
/* 184 */     int[] argb = new int[width];
/* 185 */     for (int y = 0; y < height; y++) {
/* 186 */       image.getRGB(0, y, width, 1, argb, 0, width);
/* 187 */       System.arraycopy(argb, 0, pixels, y * stride, width);
/*     */     } 
/*     */ 
/*     */     
/* 191 */     BitArray row = new BitArray(width); int i;
/* 192 */     for (i = 0; i < height; i++) {
/*     */       try {
/* 194 */         row = bitmap.getBlackRow(i, row);
/* 195 */       } catch (NotFoundException nfe) {
/*     */         
/* 197 */         int j = i * stride + width;
/* 198 */         Arrays.fill(pixels, j, j + width, -65536);
/*     */       } 
/*     */ 
/*     */       
/* 202 */       int offset = i * stride + width;
/* 203 */       for (int x = 0; x < width; x++) {
/* 204 */         pixels[offset + x] = row.get(x) ? -16777216 : -1;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 210 */       for (i = 0; i < height; i++) {
/* 211 */         BitMatrix matrix = bitmap.getBlackMatrix();
/* 212 */         int offset = i * stride + width * 2;
/* 213 */         for (int x = 0; x < width; x++) {
/* 214 */           pixels[offset + x] = matrix.get(x, i) ? -16777216 : -1;
/*     */         }
/*     */       } 
/* 217 */     } catch (NotFoundException notFoundException) {}
/*     */ 
/*     */ 
/*     */     
/* 221 */     writeResultImage(stride, height, pixels, uri, ".mono.png");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writeResultImage(int stride, int height, int[] pixels, URI input, String suffix) throws IOException {
/* 229 */     BufferedImage result = new BufferedImage(stride, height, 2);
/* 230 */     result.setRGB(0, 0, stride, height, pixels, 0, stride);
/* 231 */     Path imagePath = buildOutputPath(input, suffix);
/*     */     try {
/* 233 */       if (!ImageIO.write(result, "png", imagePath.toFile())) {
/* 234 */         System.err.println("Could not encode an image to " + imagePath);
/*     */       }
/* 236 */     } catch (IOException ignored) {
/* 237 */       System.err.println("Could not write to " + imagePath);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\j2se\DecodeWorker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */