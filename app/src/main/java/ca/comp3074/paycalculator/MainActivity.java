package ca.comp3074.paycalculator;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends Activity {
    private final EditText[] inputs = new EditText[3];
    private final TextView[] outputs = new TextView[4];
    private final String[] labels = {"Regular pay", "Overtime pay", "Total pay", "Tax (on regular pay)"};
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setTitle("Pay Calculator");
        ScrollView scroll = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = (int)(20 * getResources().getDisplayMetrics().density);
        layout.setPadding(pad, pad, pad, pad);
        scroll.addView(layout);
        TextView intro = new TextView(this);
        intro.setText("Enter your hours, hourly rate, and tax percentage. Overtime begins after 40 hours at 1.5 times your rate.");
        intro.setTextSize(18); layout.addView(intro);
        String[] names = {"Hours worked", "Hourly rate ($)", "Tax rate (%) - e.g. 20 for 20%"};
        for (int i = 0; i < inputs.length; i++) {
            TextView label = new TextView(this); label.setText(names[i]); layout.addView(label);
            inputs[i] = new EditText(this); inputs[i].setId(100+i);
            inputs[i].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            inputs[i].setSingleLine(true); inputs[i].setHint(i == 2 ? "20" : "0");
            layout.addView(inputs[i]);
        }
        Button calculate = new Button(this); calculate.setText("Calculate pay");
        calculate.setOnClickListener(v -> calculate()); layout.addView(calculate);
        for (int i = 0; i < outputs.length; i++) {
            outputs[i] = new TextView(this); outputs[i].setTextSize(20);
            outputs[i].setPadding(0, pad/2, 0, pad/2);
            outputs[i].setText(labels[i] + ": --"); layout.addView(outputs[i]);
        }
        Button about = new Button(this); about.setText("About");
        about.setOnClickListener(v -> startActivity(new Intent(this, AboutActivity.class)));
        layout.addView(about); setContentView(scroll);
        ScreenInsets.apply(this, scroll);
        // Android restores input text automatically through stable view IDs.
        if (state != null && state.containsKey("result0")) {
            for (int i=0; i<outputs.length; i++) outputs[i].setText(state.getString("result"+i));
        }
    }
    private void calculate() {
        BigDecimal[] values = new BigDecimal[3];
        boolean valid = true;
        for (int i = 0; i < inputs.length; i++) {
            inputs[i].setError(null);
            try {
                String text = inputs[i].getText().toString().trim();
                if (text.isEmpty()) throw new NumberFormatException();
                values[i] = new BigDecimal(text);
                if (values[i].signum() < 0 || (i == 2 && values[i].compareTo(new BigDecimal("100")) > 0))
                    throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                inputs[i].setError(i == 2 ? "Enter a percentage from 0 to 100" : "Enter a nonnegative number");
                if (valid) inputs[i].requestFocus(); valid = false;
            }
        }
        if (!valid) {
            for (int i=0; i<outputs.length; i++) outputs[i].setText(labels[i] + ": --");
            return;
        }
        BigDecimal[] result = PayCalculator.calculate(values[0], values[1], values[2]);
        for (int i=0; i<outputs.length; i++) outputs[i].setText(labels[i] + ": $" + result[i].setScale(2, RoundingMode.HALF_UP).toPlainString());
    }
    @Override protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        for (int i=0; i<outputs.length; i++) state.putString("result"+i, outputs[i].getText().toString());
    }
}
