package nullindustries.co.testtry;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by danielgomez22 on 6/24/15.
 */
public class EditNumber extends EditText {

    private double number;
    private static final String DEFAULT_FORMAT = "%.2f";

    public EditNumber(Context context) {
        super(context);
        init();
    }

    public EditNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditNumber(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // DigistKeyListener.getInstance(true, true)
        // returns an instance that accepts digits, sign and decimal point
        InputFilter[] filters = new InputFilter[]{DigitsKeyListener.getInstance(true, true)};
        setFilters(filters);
    }

    public void clear() {
        setText("");
    }

    public double getNumber() {
        String number = getText().toString();
        if (TextUtils.isEmpty(number)) {
            return 0D;
        }
        return Double.valueOf(number);
    }

    public void setNumber(double number) {
        super.setText(String.format(DEFAULT_FORMAT, number));
    }
}
