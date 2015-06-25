package nullindustries.co.testtry;

import android.test.AndroidTestCase;
import android.test.UiThreadTest;

import static org.assertj.android.api.Assertions.assertThat;

/**
 * Created by danielgomez22 on 6/24/15.
 */
public class EditNumberTests extends AndroidTestCase {

    private EditNumber editNumber;

    public EditNumberTests() {
        this("EditNumberTests");
    }

    public EditNumberTests(String name) {
        setName(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        editNumber = new EditNumber(mContext);
        editNumber.setFocusable(true);
    }

    @UiThreadTest
    public void testClear() {
        String value = "123.45";

        editNumber.setText(value);
        editNumber.clear();

        assertThat(editNumber).isEmpty();
    }

    public void testSetNumber() {
        editNumber.setNumber(123.45);
        assertThat(editNumber).hasTextString("123.45");
//        assertTrue("123.45".equals(String.valueOf(editNumber.getNumber())));
    }
}

