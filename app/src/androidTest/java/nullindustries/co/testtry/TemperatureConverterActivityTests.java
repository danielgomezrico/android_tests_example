package nullindustries.co.testtry;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.test.ViewAsserts.assertLeftAligned;
import static android.test.ViewAsserts.assertOnScreen;
import static android.test.ViewAsserts.assertRightAligned;

/**
 * Created by danielgomez22 on 6/24/15.
 */
public class TemperatureConverterActivityTests extends ActivityInstrumentationTestCase2<TemperatureConverterActivity> {

    private TemperatureConverterActivity activity;
    private EditNumber celsiusInput;
    private EditNumber fahrenheitInput;
    private TextView celsiusLabel;
    private TextView fahrenheitLabel;

    public TemperatureConverterActivityTests() {
        this("TemperatureConverterActivityTests");
    }

    public TemperatureConverterActivityTests(String name) {
        super(TemperatureConverterActivity.class);
        setName(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        activity = getActivity();

        celsiusInput = (EditNumber) activity.findViewById(R.id.converter_celsius_input);
        fahrenheitInput = (EditNumber) activity.findViewById(R.id.converter_fahrenheit_input);

        celsiusLabel = (TextView) activity.findViewById(R.id.converter_celsius_label);
        fahrenheitLabel = (TextView) activity.findViewById(R.id.converter_fahrenheit_label);
    }

    private float getFloatPixelSize(int dimensionResourceId) {
        return getActivity().getResources()
                .getDimensionPixelSize(dimensionResourceId);
    }

    private int getIntPixelSize(int dimensionResourceId) {
        return (int) getFloatPixelSize(dimensionResourceId);
    }


    public final void testHasInputFields() {
        assertNotNull(celsiusInput);
        assertNotNull(fahrenheitInput);
    }

    public void testFieldsShouldStartEmpty() {
        assertEquals("", celsiusInput.getText().toString());
        assertEquals("", fahrenheitInput.getText().toString());
    }

    public void testFieldsOnScreen() {
        View origin = activity.getWindow().getDecorView();

        assertOnScreen(origin, celsiusInput);
        assertOnScreen(origin, fahrenheitInput);
    }

    public void testAlignment() {
        assertLeftAligned(celsiusLabel, celsiusInput);
        assertLeftAligned(fahrenheitLabel, fahrenheitInput);
        assertLeftAligned(celsiusInput, fahrenheitInput);
        assertRightAligned(celsiusInput, fahrenheitInput);
    }


    public void testCelciusInputFieldCoversEntireScreen() {
        ViewGroup.LayoutParams lp;
        int expected = ViewGroup.LayoutParams.MATCH_PARENT;
        lp = celsiusInput.getLayoutParams();
        assertEquals("celsiusInput layout width is not MATCH_PARENT", expected, lp.width);
    }

    public void testFahrenheitInputFieldCoversEntireScreen() {
        ViewGroup.LayoutParams lp;
        int expected = ViewGroup.LayoutParams.MATCH_PARENT;
        lp = fahrenheitInput.getLayoutParams();
        assertEquals("fahrenheitInput layout width is not MATCH_PARENT", expected, lp.width);
    }

    public void testFontSizes() {
        float pixelSize = getFloatPixelSize(R.dimen.label_text_size);

        assertEquals(pixelSize, celsiusLabel.getTextSize());
        assertEquals(pixelSize, fahrenheitLabel.getTextSize());
    }


    public void testFahrenheitInputMargins() {
        LinearLayout.LayoutParams lp =
                (LinearLayout.LayoutParams) fahrenheitInput.getLayoutParams();

        assertEquals(getIntPixelSize(R.dimen.margin), lp.leftMargin);
        assertEquals(getIntPixelSize(R.dimen.margin), lp.rightMargin);
    }

    public void testCelsiusInputJustification() {
        int expectedGravity = Gravity.END | Gravity.CENTER_VERTICAL;
        int actual = celsiusInput.getGravity();
        String errorMessage = String.format("Expected 0x%02x but was 0x%02x", expectedGravity, actual);
        assertEquals(errorMessage, expectedGravity, actual);
    }

    public void testFahrenheitInputJustification() {
        int expectedGravity = Gravity.END | Gravity.CENTER_VERTICAL;
        int actual = fahrenheitInput.getGravity();
        String errorMessage = String.format(
                "Expected 0x%02x but was 0x%02x", expectedGravity, actual);
        assertEquals(errorMessage, expectedGravity, actual);
    }

    public void testVirtualKeyboardSpaceReserved() {
        int expected = getIntPixelSize(R.dimen.keyboard_space);
        int actual = fahrenheitInput.getBottom();
        String errorMessage = "Space not reserved, expected " + expected + " actual " + actual;
        assertTrue(errorMessage, actual <= expected);
    }


    @UiThreadTest
    public void testFahrenheitToCelsiusConversion() {
        celsiusInput.clear();
        fahrenheitInput.clear();
        fahrenheitInput.requestFocus();
        fahrenheitInput.setText("32.5");
        celsiusInput.requestFocus();
        double f = 32.5;
        double expectedC = TemperatureConverter.fahrenheitToCelsius(f);
        double actualC = celsiusInput.getNumber();
        double delta = Math.abs(expectedC - actualC);
        String msg = f + "F -> " + expectedC + "C but was " + actualC + "C (delta " + delta + ")";
        assertTrue(msg, delta < 0.005);
    }

    public void testInputFilter() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                celsiusInput.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();

        sendKeys("MINUS 1 PERIOD 2 PERIOD 3 PERIOD 4");
        double number = celsiusInput.getNumber();

        String msg = "-1.2.3.4 should be filtered to -1.234 " + "but is " + number;
        assertEquals(msg, -1.234d, number);
    }

}
