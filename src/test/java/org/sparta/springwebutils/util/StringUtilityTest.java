/**
 * Sparta Software Co.
 * 2020
 */
package org.sparta.springwebutils.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;


// @formatter:off
 /** 
 * 
 * Unit Tests for StringUtility.
 * 
 * @author Daniel Conde Diehl - Sparta Technology
 * 
 * History: 
 *    Jul 10, 2020 - Daniel Conde Diehl
 *  
 */
// @formatter:on
public class StringUtilityTest extends StringUtility {

    @Test
    public void testTrimAllStringPropertiesNullObject() throws Exception {
        StringUtility.trimAllStringProperties(null);
    }
    
    @Test
    public void testTrimAllStringPropertiesNoProperties() throws Exception {
        Object obj = new Object() ;
        
        StringUtility.trimAllStringProperties(obj);
    }
    
    @Test(expected=NoSuchMethodException.class)
    public void testTrimAllStringPropertiesNoGetters() throws Exception {
        NoGetters obj = new NoGetters();
        obj.setField1("123    ");
        
        StringUtility.trimAllStringProperties(obj);
    }
    
    @Test(expected=NoSuchMethodException.class)
    public void testTrimAllStringPropertiesNoSetters() throws Exception {
        NoSetters obj = new NoSetters();
        
        StringUtility.trimAllStringProperties(obj);
    }
    
    @Test
    public void testTrimAllStringPropertiesNoStrings() throws Exception {
        NoStrings obj = new NoStrings();
        
        StringUtility.trimAllStringProperties(obj);
    }
    
    @Test
    public void testTrimAllStringPropertiesWithStrings() throws Exception {
        WithStrings obj = new WithStrings();
        obj.setField1("dasdass   ");
        obj.setField2("   321321   dd ");
        
        StringUtility.trimAllStringProperties(obj);
        
        Assert.assertEquals("dasdass", obj.getField1());
        Assert.assertEquals("321321   dd", obj.getField2());
    }
    
    @Test
    public void testTrimAllStringPropertiesWithStringsPublic() throws Exception {
        PublicWithStrings obj = new PublicWithStrings();
        obj.setField1("dasdass   ");
        obj.setField2("   321321   dd ");
        
        StringUtility.trimAllStringProperties(obj);
        
        Assert.assertEquals("dasdass", obj.getField1());
        Assert.assertEquals("321321   dd", obj.getField2());
    }
    
    class NoGetters {
        @SuppressWarnings("unused")
        private String field1;

        /**
         * @param field1 the field1 to set
         */
        public void setField1(String field1) {
            this.field1 = field1;
        }
    }
    
    class NoSetters {
        private String field1;

        /**
         * @return the field1
         */
        public String getField1() {
            return field1;
        }
    }
    
    @SuppressWarnings("unused")
    class NoStrings {
        private Integer field1;
        private List<Double> field2;
    }
    
    class WithStrings {
        private String field1;
        private String field2;
        /**
         * @return the field1
         */
        public String getField1() {
            return field1;
        }
        /**
         * @param field1 the field1 to set
         */
        public void setField1(String field1) {
            this.field1 = field1;
        }
        /**
         * @return the field2
         */
        public String getField2() {
            return field2;
        }
        /**
         * @param field2 the field2 to set
         */
        public void setField2(String field2) {
            this.field2 = field2;
        }
    }
    
    public class PublicWithStrings {
        private String field1;
        private String field2;
        /**
         * @return the field1
         */
        public String getField1() {
            return field1;
        }
        /**
         * @param field1 the field1 to set
         */
        public void setField1(String field1) {
            this.field1 = field1;
        }
        /**
         * @return the field2
         */
        public String getField2() {
            return field2;
        }
        /**
         * @param field2 the field2 to set
         */
        public void setField2(String field2) {
            this.field2 = field2;
        }
    }
}
