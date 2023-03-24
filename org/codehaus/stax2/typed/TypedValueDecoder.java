package org.codehaus.stax2.typed;

public abstract class TypedValueDecoder {
  public abstract void decode(String paramString) throws IllegalArgumentException;
  
  public abstract void decode(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IllegalArgumentException;
  
  public abstract void handleEmptyValue() throws IllegalArgumentException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\codehaus\stax2\typed\TypedValueDecoder.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */