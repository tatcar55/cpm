package com.beust.jcommander;

public interface IValueValidator<T> {
  void validate(String paramString, T paramT) throws ParameterException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\beust\jcommander\IValueValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */