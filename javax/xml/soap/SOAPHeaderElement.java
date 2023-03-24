package javax.xml.soap;

public interface SOAPHeaderElement extends SOAPElement {
  void setActor(String paramString);
  
  void setRole(String paramString) throws SOAPException;
  
  String getActor();
  
  String getRole();
  
  void setMustUnderstand(boolean paramBoolean);
  
  boolean getMustUnderstand();
  
  void setRelay(boolean paramBoolean) throws SOAPException;
  
  boolean getRelay();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\soap\SOAPHeaderElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */