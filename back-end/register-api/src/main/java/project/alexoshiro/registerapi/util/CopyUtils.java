package project.alexoshiro.registerapi.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CopyUtils {
	
	private static String[] getNullPropertyNames(Object source) {
		BeanWrapper sourceBeanWrapper = new BeanWrapperImpl(source);
		PropertyDescriptor[] propertyDescriptors = sourceBeanWrapper.getPropertyDescriptors();
		Set<String> emptyPropertyNames = new HashSet<>();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String sourcePropertyName = propertyDescriptor.getName();
			Object sourceValue = sourceBeanWrapper.getPropertyValue(sourcePropertyName);
			if (sourceValue == null) {
				emptyPropertyNames.add(sourcePropertyName);
			}
		}
		String[] result = new String[emptyPropertyNames.size()];
		return emptyPropertyNames.toArray(result);
	}

	public static void copyNonNullProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}
}
