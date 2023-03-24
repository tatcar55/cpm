/*     */ package org.sqlite.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OSInfo
/*     */ {
/*  41 */   private static HashMap<String, String> archMapping = new HashMap<String, String>();
/*     */   
/*     */   public static final String X86 = "x86";
/*     */   
/*     */   public static final String X86_64 = "x86_64";
/*     */   public static final String IA64_32 = "ia64_32";
/*     */   public static final String IA64 = "ia64";
/*     */   public static final String PPC = "ppc";
/*     */   public static final String PPC64 = "ppc64";
/*     */   
/*     */   static {
/*  52 */     archMapping.put("x86", "x86");
/*  53 */     archMapping.put("i386", "x86");
/*  54 */     archMapping.put("i486", "x86");
/*  55 */     archMapping.put("i586", "x86");
/*  56 */     archMapping.put("i686", "x86");
/*  57 */     archMapping.put("pentium", "x86");
/*     */ 
/*     */     
/*  60 */     archMapping.put("x86_64", "x86_64");
/*  61 */     archMapping.put("amd64", "x86_64");
/*  62 */     archMapping.put("em64t", "x86_64");
/*  63 */     archMapping.put("universal", "x86_64");
/*     */ 
/*     */     
/*  66 */     archMapping.put("ia64", "ia64");
/*  67 */     archMapping.put("ia64w", "ia64");
/*     */ 
/*     */     
/*  70 */     archMapping.put("ia64_32", "ia64_32");
/*  71 */     archMapping.put("ia64n", "ia64_32");
/*     */ 
/*     */     
/*  74 */     archMapping.put("ppc", "ppc");
/*  75 */     archMapping.put("power", "ppc");
/*  76 */     archMapping.put("powerpc", "ppc");
/*  77 */     archMapping.put("power_pc", "ppc");
/*  78 */     archMapping.put("power_rs", "ppc");
/*     */ 
/*     */     
/*  81 */     archMapping.put("ppc64", "ppc64");
/*  82 */     archMapping.put("power64", "ppc64");
/*  83 */     archMapping.put("powerpc64", "ppc64");
/*  84 */     archMapping.put("power_pc64", "ppc64");
/*  85 */     archMapping.put("power_rs64", "ppc64");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*  90 */     if (args.length >= 1) {
/*  91 */       if ("--os".equals(args[0])) {
/*  92 */         System.out.print(getOSName());
/*     */         return;
/*     */       } 
/*  95 */       if ("--arch".equals(args[0])) {
/*  96 */         System.out.print(getArchName());
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 101 */     System.out.print(getNativeLibFolderPathForCurrentOS());
/*     */   }
/*     */   
/*     */   public static String getNativeLibFolderPathForCurrentOS() {
/* 105 */     return getOSName() + "/" + getArchName();
/*     */   }
/*     */   
/*     */   public static String getOSName() {
/* 109 */     return translateOSNameToFolderName(System.getProperty("os.name"));
/*     */   }
/*     */   
/*     */   public static boolean isAndroid() {
/* 113 */     return System.getProperty("java.runtime.name", "").toLowerCase().contains("android");
/*     */   }
/*     */   
/*     */   static String getHardwareName() {
/*     */     try {
/* 118 */       Process p = Runtime.getRuntime().exec("uname -m");
/* 119 */       p.waitFor();
/*     */       
/* 121 */       InputStream in = p.getInputStream();
/*     */       try {
/* 123 */         int readLen = 0;
/* 124 */         ByteArrayOutputStream b = new ByteArrayOutputStream();
/* 125 */         byte[] buf = new byte[32];
/* 126 */         while ((readLen = in.read(buf, 0, buf.length)) >= 0) {
/* 127 */           b.write(buf, 0, readLen);
/*     */         }
/* 129 */         return b.toString();
/*     */       } finally {
/*     */         
/* 132 */         if (in != null) {
/* 133 */           in.close();
/*     */         }
/*     */       }
/*     */     
/* 137 */     } catch (Throwable e) {
/* 138 */       System.err.println("Error while running uname -m: " + e.getMessage());
/* 139 */       return "unknown";
/*     */     } 
/*     */   }
/*     */   
/*     */   static String resolveArmArchType() {
/* 144 */     if (System.getProperty("os.name").contains("Linux")) {
/* 145 */       String armType = getHardwareName();
/*     */       
/* 147 */       if (armType.startsWith("armv6"))
/*     */       {
/* 149 */         return "armv6";
/*     */       }
/* 151 */       if (armType.startsWith("armv7"))
/*     */       {
/* 153 */         return "armv7";
/*     */       }
/* 155 */       if (armType.startsWith("armv5"))
/*     */       {
/* 157 */         return "arm";
/*     */       }
/* 159 */       if (armType.equals("aarch64"))
/*     */       {
/* 161 */         return "arm64";
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 166 */       String abi = System.getProperty("sun.arch.abi");
/* 167 */       if (abi != null && abi.startsWith("gnueabihf")) {
/* 168 */         return "armv7";
/*     */       }
/*     */ 
/*     */       
/* 172 */       String javaHome = System.getProperty("java.home");
/*     */       
/*     */       try {
/* 175 */         int exitCode = Runtime.getRuntime().exec("which readelf").waitFor();
/* 176 */         if (exitCode == 0) {
/* 177 */           String[] cmdarray = { "/bin/sh", "-c", "find '" + javaHome + "' -name 'libjvm.so' | head -1 | xargs readelf -A | grep 'Tag_ABI_VFP_args: VFP registers'" };
/*     */ 
/*     */           
/* 180 */           exitCode = Runtime.getRuntime().exec(cmdarray).waitFor();
/* 181 */           if (exitCode == 0) {
/* 182 */             return "armv7";
/*     */           }
/*     */         } else {
/* 185 */           System.err.println("WARNING! readelf not found. Cannot check if running on an armhf system, armel architecture will be presumed.");
/*     */         }
/*     */       
/*     */       }
/* 189 */       catch (IOException iOException) {
/*     */ 
/*     */       
/* 192 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 197 */     return "arm";
/*     */   }
/*     */   
/*     */   public static String getArchName() {
/* 201 */     String osArch = System.getProperty("os.arch");
/*     */     
/* 203 */     if (isAndroid()) {
/* 204 */       return "android-arm";
/*     */     }
/*     */     
/* 207 */     if (osArch.startsWith("arm")) {
/* 208 */       osArch = resolveArmArchType();
/*     */     } else {
/*     */       
/* 211 */       String lc = osArch.toLowerCase(Locale.US);
/* 212 */       if (archMapping.containsKey(lc))
/* 213 */         return archMapping.get(lc); 
/*     */     } 
/* 215 */     return translateArchNameToFolderName(osArch);
/*     */   }
/*     */   
/*     */   static String translateOSNameToFolderName(String osName) {
/* 219 */     if (osName.contains("Windows")) {
/* 220 */       return "Windows";
/*     */     }
/* 222 */     if (osName.contains("Mac") || osName.contains("Darwin")) {
/* 223 */       return "Mac";
/*     */     }
/* 225 */     if (osName.contains("Linux")) {
/* 226 */       return "Linux";
/*     */     }
/* 228 */     if (osName.contains("AIX")) {
/* 229 */       return "AIX";
/*     */     }
/*     */ 
/*     */     
/* 233 */     return osName.replaceAll("\\W", "");
/*     */   }
/*     */ 
/*     */   
/*     */   static String translateArchNameToFolderName(String archName) {
/* 238 */     return archName.replaceAll("\\W", "");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\sqlit\\util\OSInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */