package jsprit.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TypedMap {
	
	public static interface AbstractKey<K> {
		
	    Class<K> getType();
	}
	
	private Map<AbstractKey<?>, Object> map = new HashMap<AbstractKey<?>, Object>();

	public <T> T get(AbstractKey<T> key) {
		if(map.get(key) == null) return null;
        return key.getType().cast(map.get(key));
    }

    public <T> T put(AbstractKey<T> key, T value) {
        return key.getType().cast(map.put(key, key.getType().cast(value)));
    }
    
    public Set<AbstractKey<?>> keySet(){
    	return map.keySet();
    }
	
}
