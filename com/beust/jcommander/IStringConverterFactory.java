package com.beust.jcommander;

public interface IStringConverterFactory {
  <T> Class<? extends IStringConverter<T>> getConverter(Class<T> paramClass);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\beust\jcommander\IStringConverterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */