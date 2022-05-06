package src.Statistics.Expirimental_StatisticsAnnotation;


import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
@Documented
public @interface ValueGuard  {
    /**
     * Value name to guard.
     * @return value name.
     */
    String[] valueNames();
    
    /**
     * Accessor for the values.
     * @return accessor.
     */
    String[] valueAccessor();
    
}
