package javanet.staxutils;

public interface Indentation {
  public static final String DEFAULT_INDENT = "  ";
  
  public static final String NORMAL_END_OF_LINE = "\n";
  
  void setIndent(String paramString);
  
  String getIndent();
  
  void setNewLine(String paramString);
  
  String getNewLine();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\Indentation.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */