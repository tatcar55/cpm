/*    */ package com.google.zxing.multi.qrcode.detector;
/*    */ 
/*    */ import com.google.zxing.DecodeHintType;
/*    */ import com.google.zxing.NotFoundException;
/*    */ import com.google.zxing.ReaderException;
/*    */ import com.google.zxing.ResultPointCallback;
/*    */ import com.google.zxing.common.BitMatrix;
/*    */ import com.google.zxing.common.DetectorResult;
/*    */ import com.google.zxing.qrcode.detector.Detector;
/*    */ import com.google.zxing.qrcode.detector.FinderPatternInfo;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public final class MultiDetector
/*    */   extends Detector
/*    */ {
/* 41 */   private static final DetectorResult[] EMPTY_DETECTOR_RESULTS = new DetectorResult[0];
/*    */   
/*    */   public MultiDetector(BitMatrix image) {
/* 44 */     super(image);
/*    */   }
/*    */   
/*    */   public DetectorResult[] detectMulti(Map<DecodeHintType, ?> hints) throws NotFoundException {
/* 48 */     BitMatrix image = getImage();
/*    */     
/* 50 */     ResultPointCallback resultPointCallback = (hints == null) ? null : (ResultPointCallback)hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
/* 51 */     MultiFinderPatternFinder finder = new MultiFinderPatternFinder(image, resultPointCallback);
/* 52 */     FinderPatternInfo[] infos = finder.findMulti(hints);
/*    */     
/* 54 */     if (infos.length == 0) {
/* 55 */       throw NotFoundException.getNotFoundInstance();
/*    */     }
/*    */     
/* 58 */     List<DetectorResult> result = new ArrayList<>();
/* 59 */     for (FinderPatternInfo info : infos) {
/*    */       try {
/* 61 */         result.add(processFinderPatternInfo(info));
/* 62 */       } catch (ReaderException readerException) {}
/*    */     } 
/*    */ 
/*    */     
/* 66 */     if (result.isEmpty()) {
/* 67 */       return EMPTY_DETECTOR_RESULTS;
/*    */     }
/* 69 */     return result.<DetectorResult>toArray(new DetectorResult[result.size()]);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\multi\qrcode\detector\MultiDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */