package src.Statistics.Expirimental_StatisticsAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class AnnotationTest {
    private static class value{
        
        public value(int arg) {
            this.arg = arg;
        }
        
        @ValueGuard(valueNames = "arg" , valueAccessor = "getArg")
        private int arg;
    
        public void setArg(int arg) {
            this.arg = arg;
        }
    
        public int getArg() {
            return this.arg;
        }
    }
    
    public static void main(String[] args) {
        value v = new value(1);
        
        new value(1);
        Class<value> demoClassObj = value.class;
        readAnnotationOn(demoClassObj);
        
    }
    
    static void readAnnotationOn(AnnotatedElement element)
    {
        try
        {
            System.out.println("\n Finding annotations on " + element.getClass().getName());
            Annotation[] annotations = element.getAnnotations();
            for (Annotation annotation : annotations)
            {
            
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
}

