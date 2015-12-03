package edu.purdue.carlso70.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.*;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView display = (TextView) findViewById(R.id.display);
        CalculatorView view = new CalculatorView(display);
        final Calculator c = new Calculator(view);


        for (int i = 0; i < 10; i++) {
            final int anonymous = i;
            int resId = getResources().getIdentifier("button"+i,"id",getPackageName());
            Button button = (Button) findViewById(resId);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.inputDigit((char)(48 + anonymous));
                }
            });
        }

        Button equals = (Button) findViewById(R.id.equals);
        equals.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                c.equal();
            }
        });

        Button divide = (Button) findViewById(R.id.divide);
        divide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                c.operator('/');
            }
        });

        Button multiply = (Button) findViewById(R.id.multiply);
        multiply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                c.operator('*');
            }
        });

        Button subtract = (Button) findViewById(R.id.subtract);
        subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                c.operator('-');
            }
        });

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                c.operator('+');
            }
        });

        Button dot = (Button) findViewById(R.id.decimal);
        dot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                c.dot();
            }
        });

        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                c.delete();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
