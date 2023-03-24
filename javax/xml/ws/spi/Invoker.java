package javax.xml.ws.spi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.xml.ws.WebServiceContext;

public abstract class Invoker {
  public abstract void inject(WebServiceContext paramWebServiceContext) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
  
  public abstract Object invoke(Method paramMethod, Object... paramVarArgs) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\spi\Invoker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */