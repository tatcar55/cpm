package com.sun.xml.ws.server.sei;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Packet;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Invoker {
  public abstract Object invoke(@NotNull Packet paramPacket, @NotNull Method paramMethod, @NotNull Object... paramVarArgs) throws InvocationTargetException, IllegalAccessException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\sei\Invoker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */