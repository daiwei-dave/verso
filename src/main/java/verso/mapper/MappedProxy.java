package verso.mapper;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import verso.session.Session;

public class MappedProxy<T> implements InvocationHandler {

    private Session session;
    private final Map<Method, MappedMethod> CACHE = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clazz, Session session) {
        MappedProxy<T> proxy = new MappedProxy<>();
        proxy.session = session;
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, proxy);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
    {
        MappedMethod mappedMethod = CACHE.get(method);
        if (mappedMethod == null) {
            CACHE.put(method, mappedMethod = new MappedMethod(method));
        }
        return mappedMethod.invoke(args, session);
    }
}
