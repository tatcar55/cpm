/*    */ package javax.xml.bind.annotation.adapters;
/*    */ 
/*    */ import javax.xml.bind.DatatypeConverter;
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
/*    */ public final class HexBinaryAdapter
/*    */   extends XmlAdapter<String, byte[]>
/*    */ {
/*    */   public byte[] unmarshal(String s) {
/* 56 */     if (s == null) return null; 
/* 57 */     return DatatypeConverter.parseHexBinary(s);
/*    */   }
/*    */   
/*    */   public String marshal(byte[] bytes) {
/* 61 */     if (bytes == null) return null; 
/* 62 */     return DatatypeConverter.printHexBinary(bytes);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\annotation\adapters\HexBinaryAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */