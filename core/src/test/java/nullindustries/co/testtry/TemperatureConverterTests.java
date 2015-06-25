package nullindustries.co.testtry;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by danielgomez22 on 6/24/15.
 */
public class TemperatureConverterTests {

    private static final String ERROR_MESSAGE_BELOW_ZERO_FMT = "Invalid temperature: %.2f%c below absolute zero";

    static Map<Double, Double> conversionTable;

    static {
        conversionTable = new HashMap<Double, Double>();
        // initialize (celsius, fahrenheit) pairs
        conversionTable.put(0.0, 32.0);
        conversionTable.put(100.0, 212.0);
        conversionTable.put(-1.0, 30.20);
        conversionTable.put(-100.0, -148.0);
        conversionTable.put(32.0, 89.60);
        conversionTable.put(-40.0, -40.0);
        conversionTable.put(-273.0, -459.40);
    }

    public TemperatureConverterTests() {
    }

    @Test
    public void testFahrenheitToCelsius() throws Exception {
        for (double knownCelsius : conversionTable.keySet()) {
            double knownFahrenheit = conversionTable.get(knownCelsius);

            double resultCelsius =
                    TemperatureConverter.fahrenheitToCelsius(knownFahrenheit);

            double delta = Math.abs(resultCelsius - knownCelsius);
            String msg = knownFahrenheit + "F -> " + knownCelsius + "C" + "but is " + resultCelsius;
            assertTrue(msg, delta < 0.0001);
        }

    }

    @Test
    public void testCelsiusToFahrenheit() {
        for (double knownCelsius : conversionTable.keySet()) {
            double knownFahrenheit = conversionTable.get(knownCelsius);

            double resultFahrenheit =
                    TemperatureConverter.celsiusToFahrenheit(knownCelsius);

            double delta = Math.abs(resultFahrenheit - knownFahrenheit);
            String msg = knownCelsius + "C -> " + knownFahrenheit + "F"
                    + " but is " + resultFahrenheit;
            assertTrue(msg, delta < 0.0001);
        }
    }

    @Test(expected = InvalidTemperatureException.class)
    public void testExceptionForLessThanAbsoluteZeroF() {
        TemperatureConverter.fahrenheitToCelsius(TemperatureConverter.ABSOLUTE_ZERO_F - 1);
    }

    @Test(expected = InvalidTemperatureException.class)
    public void testExceptionForLessThanAbsoluteZeroC() {
        TemperatureConverter.celsiusToFahrenheit(TemperatureConverter.ABSOLUTE_ZERO_C - 1);
    }

}
