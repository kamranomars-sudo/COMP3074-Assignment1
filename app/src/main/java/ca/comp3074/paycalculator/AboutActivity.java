package ca.comp3074.paycalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state); setTitle("About");
        if (getActionBar() != null) getActionBar().setDisplayHomeAsUpEnabled(true);
        TextView details = new TextView(this);
        int pad = (int)(24 * getResources().getDisplayMetrics().density);
        details.setPadding(pad, pad, pad, pad); details.setTextSize(22);
        details.setText("Pay Calculator\n\n" + getString(ca.comp3074.paycalculator.R.string.student_name)
                + "\nStudent ID: " + getString(ca.comp3074.paycalculator.R.string.student_id)
                + "\n\nCOMP3074 - Assignment 1");
        android.widget.ScrollView scroll = new android.widget.ScrollView(this);
        scroll.addView(details);
        setContentView(scroll);
        ScreenInsets.apply(this, scroll);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Return to existing MainActivity; never create another instance.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
