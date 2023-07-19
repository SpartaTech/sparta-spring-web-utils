/*
 * Sparta Software Co.
 * 2020
 */
package org.sparta.springwebutils.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


// @formatter:off

/**
 * Unit Tests for StringUtility.
 *
 * @author Daniel Conde Diehl - Sparta Technology
 * <p>
 * History:
 * - Jul 10, 2020 - Daniel Conde Diehl
 * - Oct 13, 2021 - Daniel Conde Diehl - Upgrading to Junit Jupiter
 */
// @formatter:on
public class StringUtilityTest extends StringUtility {

    @Test
    public void testTrimAllStringPropertiesNullObject() throws Exception {
        StringUtility.trimAllStringProperties(null);
    }
    
    @Test
    public void testTrimAllStringPropertiesNoProperties() throws Exception {
        final Object obj = new Object();
        
        StringUtility.trimAllStringProperties(obj);
    }
    
    @Test
    public void testTrimAllStringPropertiesNoGetters() {
        final NoGetters obj = new NoGetters();
        obj.setField1("123    ");
        
        assertThrows(NoSuchMethodException.class, () -> StringUtility.trimAllStringProperties(obj));
    }
    
    @Test
    public void testTrimAllStringPropertiesNoSetters() {
        final NoSetters obj = new NoSetters();
        assertThrows(NoSuchMethodException.class, () -> StringUtility.trimAllStringProperties(obj));
    }
    
    @Test
    public void testTrimAllStringPropertiesNoStrings() throws Exception {
        final NoStrings obj = new NoStrings();
        
        StringUtility.trimAllStringProperties(obj);
    }
    
    @Test
    public void testTrimAllStringPropertiesWithStrings() throws Exception {
        final WithStrings obj = new WithStrings();
        obj.setField1("dasdass   ");
        obj.setField2("   321321   dd ");
        
        StringUtility.trimAllStringProperties(obj);
        
        assertEquals("dasdass", obj.getField1());
        assertEquals("321321   dd", obj.getField2());
    }
    
    @Test
    public void testTrimAllStringPropertiesWithStringsPublic() throws Exception {
        final PublicWithStrings obj = new PublicWithStrings();
        obj.setField1("dasdass   ");
        obj.setField2("   321321   dd ");
        
        StringUtility.trimAllStringProperties(obj);
        
        assertEquals("dasdass", obj.getField1());
        assertEquals("321321   dd", obj.getField2());
    }
    
    static class NoGetters {
        @SuppressWarnings("unused")
        private String field1;

        /**
         * @param field1 the field1 to set
         */
        public void setField1(String field1) {
            this.field1 = field1;
        }
    }
    
    static class NoSetters {
        @SuppressWarnings("unused")
        private String field1;

        /**
         * @return the field1
         */
        public String getField1() {
            return field1;
        }
    }
    
    @SuppressWarnings("unused")
    static class NoStrings {
        private Integer field1;
        private List<Double> field2;
    }
    
    static class WithStrings {
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
    
    public static class PublicWithStrings {
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
