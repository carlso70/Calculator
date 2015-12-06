package edu.purdue.carlso70.calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Display;
import android.widget.TextView;
import android.view.View.*;


/**
 * Created by panth_000 on 12/2/2015.
 */
public class CalculatorView implements CalculatorViewInterface {

    private TextView display;
    private Context context;

    public CalculatorView(TextView display) {
        this.display = display;
        this.context = context;
        display("");
    }

    public void display(String val) {
        display.setTextColor(Color.BLACK);
        display.setText(val);
    }

    public void invalid() {
        display.setTextColor(Color.RED);
        System.out.println("\"invalid\" method called");
    }

}
