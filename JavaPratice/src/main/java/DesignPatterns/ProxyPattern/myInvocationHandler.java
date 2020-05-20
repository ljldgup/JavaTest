package DesignPatterns.ProxyPattern;

import java.lang.reflect.*;
public class myInvocationHandler implements InvocationHandler {
	private Object obj;

	public myInvocationHandler(Object obj) {
		// TODO Auto-generated constructor stub
		this.obj = obj;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] arg2) throws Throwable {
		// TODO Auto-generated method stub
		try {
			if(!method.getName().endsWith("Rate")) {

				return method.invoke(obj, arg2);
			}else {
				throw new IllegalAccessException();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(method.getName() + " block by proxy");
		}
		return null;
	}
	
}
