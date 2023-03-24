/*     */ package com.google.zxing.client.j2se;
/*     */ 
/*     */ import com.beust.jcommander.JCommander;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CommandLineRunner
/*     */ {
/*     */   public static void main(String[] args) throws Exception {
/*  51 */     DecoderConfig config = new DecoderConfig();
/*  52 */     JCommander jCommander = new JCommander(config, args);
/*  53 */     jCommander.setProgramName(CommandLineRunner.class.getSimpleName());
/*  54 */     if (config.help) {
/*  55 */       jCommander.usage();
/*     */       
/*     */       return;
/*     */     } 
/*  59 */     List<URI> inputs = config.inputPaths;
/*     */     do {
/*  61 */       inputs = retainValid(expand(inputs), config.recursive);
/*  62 */     } while (config.recursive && isExpandable(inputs));
/*     */     
/*  64 */     int numInputs = inputs.size();
/*  65 */     if (numInputs == 0) {
/*  66 */       jCommander.usage();
/*     */       
/*     */       return;
/*     */     } 
/*  70 */     Queue<URI> syncInputs = new ConcurrentLinkedQueue<>(inputs);
/*  71 */     int numThreads = Math.min(numInputs, Runtime.getRuntime().availableProcessors());
/*  72 */     int successful = 0;
/*  73 */     if (numThreads > 1) {
/*  74 */       ExecutorService executor = Executors.newFixedThreadPool(numThreads);
/*  75 */       Collection<Future<Integer>> futures = new ArrayList<>(numThreads);
/*  76 */       for (int x = 0; x < numThreads; x++) {
/*  77 */         futures.add(executor.submit(new DecodeWorker(config, syncInputs)));
/*     */       }
/*  79 */       executor.shutdown();
/*  80 */       for (Future<Integer> future : futures) {
/*  81 */         successful += ((Integer)future.get()).intValue();
/*     */       }
/*     */     } else {
/*  84 */       successful += (new DecodeWorker(config, syncInputs)).call().intValue();
/*     */     } 
/*     */     
/*  87 */     if (!config.brief && numInputs > 1) {
/*  88 */       System.out.println("\nDecoded " + successful + " files out of " + numInputs + " successfully (" + (successful * 100 / numInputs) + "%)\n");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<URI> expand(List<URI> inputs) throws IOException, URISyntaxException {
/*  94 */     List<URI> expanded = new ArrayList<>();
/*  95 */     for (URI input : inputs) {
/*  96 */       if (isFileOrDir(input)) {
/*  97 */         Path inputPath = Paths.get(input);
/*  98 */         if (Files.isDirectory(inputPath, new java.nio.file.LinkOption[0])) {
/*  99 */           try (DirectoryStream<Path> childPaths = Files.newDirectoryStream(inputPath)) {
/* 100 */             for (Path childPath : childPaths)
/* 101 */               expanded.add(childPath.toUri()); 
/*     */           } 
/*     */           continue;
/*     */         } 
/* 105 */         expanded.add(input);
/*     */         continue;
/*     */       } 
/* 108 */       expanded.add(input);
/*     */     } 
/*     */     
/* 111 */     for (int i = 0; i < expanded.size(); i++) {
/* 112 */       URI input = expanded.get(i);
/* 113 */       if (input.getScheme() == null) {
/* 114 */         expanded.set(i, new URI("file", input.getSchemeSpecificPart(), input.getFragment()));
/*     */       }
/*     */     } 
/* 117 */     return expanded;
/*     */   }
/*     */   
/*     */   private static List<URI> retainValid(List<URI> inputs, boolean recursive) {
/* 121 */     List<URI> retained = new ArrayList<>();
/* 122 */     for (URI input : inputs) {
/*     */       boolean retain;
/* 124 */       if (isFileOrDir(input)) {
/* 125 */         Path inputPath = Paths.get(input);
/*     */ 
/*     */         
/* 128 */         retain = (!inputPath.getFileName().toString().startsWith(".") && (recursive || !Files.isDirectory(inputPath, new java.nio.file.LinkOption[0])));
/*     */       } else {
/* 130 */         retain = true;
/*     */       } 
/* 132 */       if (retain) {
/* 133 */         retained.add(input);
/*     */       }
/*     */     } 
/* 136 */     return retained;
/*     */   }
/*     */   
/*     */   private static boolean isExpandable(List<URI> inputs) {
/* 140 */     for (URI input : inputs) {
/* 141 */       if (isFileOrDir(input) && Files.isDirectory(Paths.get(input), new java.nio.file.LinkOption[0])) {
/* 142 */         return true;
/*     */       }
/*     */     } 
/* 145 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isFileOrDir(URI uri) {
/* 149 */     return "file".equals(uri.getScheme());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\j2se\CommandLineRunner.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */