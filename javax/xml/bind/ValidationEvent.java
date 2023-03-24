package javax.xml.bind;

public interface ValidationEvent {
  public static final int WARNING = 0;
  
  public static final int ERROR = 1;
  
  public static final int FATAL_ERROR = 2;
  
  int getSeverity();
  
  String getMessage();
  
  Throwable getLinkedException();
  
  ValidationEventLocator getLocator();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\bind\ValidationEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */