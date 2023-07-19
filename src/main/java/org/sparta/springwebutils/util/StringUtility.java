/**
 * Sparta Software Co.
 * 2020
 */
package org.sparta.springwebutils.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

// @formatter:off
 /** 
 * 
 * String Utility methods.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 * 
 * History: 
 *    Jul 10, 2020 - Daniel Conde Diehl
 *  
 */
// @formatter:on
public abstract class StringUtility {
    
    /**
     * Trim all String properties in the method by reflection.
     * 
     * @param obj object to be property trimmed
     * @throws Exception in case anything happens in the process
     */
    public static void trimAllStringProperties(Object obj) throws Exception {
        if (obj == null) {
            return;
        }
        PropertyDescriptor[] pds = Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors();
        
        for (PropertyDescriptor pd : pds) {
            if (pd.getPropertyType().equals(String.class)) {
                final Method readMethod = pd.getReadMethod();
                final Method writeMethod = pd.getWriteMethod();
                
                if (readMethod == null || writeMethod == null) {
                    throw new NoSuchMethodException("Getter/Setter for the property "+ pd.getDisplayName() + " does not exist");
                }
                
                try {
                    final String val = (String) readMethod.invoke(obj);
                    writeMethod.invoke(obj, StringUtils.trim(val));
                } catch (IllegalAccessException e) {
                    boolean isReadAccessible  = readMethod.isAccessible();
                    boolean isWriteAccessible = writeMethod.isAccessible(); 
                    readMethod.setAccessible(true);
                    writeMethod.setAccessible(true);

                    final String val = (String) readMethod.invoke(obj);
                    writeMethod.invoke(obj, StringUtils.trim(val));

                    readMethod.setAccessible(isReadAccessible);
                    writeMethod.setAccessible(isWriteAccessible);
                }
            }
        }
        
    }
}
