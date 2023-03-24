package com.sun.xml.registry.uddi;

public class FuturesRequestManager implements Runnable {
  private JAXRCommand jaxrCommand;
  
  static void invokeCommand(JAXRCommand paramJAXRCommand) {
    FuturesRequestManager futuresRequestManager = new FuturesRequestManager(paramJAXRCommand);
    (new Thread(futuresRequestManager)).start();
  }
  
  private FuturesRequestManager(JAXRCommand paramJAXRCommand) {
    this.jaxrCommand = paramJAXRCommand;
  }
  
  public void run() {
    try {
      this.jaxrCommand.execute();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\FuturesRequestManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */