package com.example.gordonramsdd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class EditInventory extends Activity {

    private String message;
    private String message2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory);

        Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        message = extras.getString("Inventory Name");
        textView = (TextView) findViewById(R.id.inventory_name);
        textView.setTextSize(20);
        if (message.equals("Grocery List")) {
            textView.setText(message);
        }
        else {
            textView.setText("Inventory: " + message);
        }
        message2 = extras.getString("Ingredient");
        textView = (TextView) findViewById(R.id.ingredient);
        textView.setTextSize(18);
        textView.setText(message2);

        Button button = (Button)findViewById(R.id.delete_ingredient);
        if (message.equals("Grocery List")) {
            Log.i("tag1", "GOT HERE");
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), GroceryList.class);
                    intent.putExtra(MainActivity.EXTRA_MESSAGE, extras.getString("Inventory Name"));

                    ArrayList<String> ingredient_file = new ArrayList<String>();
                    FileInputStream fis;

                    try {
                        fis = openFileInput("grocery_list.txt");

                        if (fis != null) {
                            InputStreamReader isr = new InputStreamReader(fis);
                            BufferedReader br = new BufferedReader(isr);

                            String line = "";

                            do {
                                line = br.readLine();
                                if (line == null)
                                    continue;
                                String[] splitting = line.split("\\|");
                                String line2 = "";
                                for (int i = 0; i < splitting.length - 1; i++) {
                                    line2 += splitting[i];
                                    line2 += "       ";
                                }
                                line2 += splitting[splitting.length-1];
                                if (line2.equals(message2))
                                    continue;
                                ingredient_file.add(line);
                            } while (line != null);
                        }
                        fis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    };

                    FileOutputStream fos;
                    try {
                        fos = openFileOutput("grocery_list.txt", Context.MODE_PRIVATE);
                        OutputStreamWriter osw = new OutputStreamWriter(fos);
                        for(int i = 0; i < ingredient_file.size(); i++) {
                            osw.write(ingredient_file.get(i));
                            osw.write("\n");
                            osw.flush();
                        }
                        osw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    startActivity(intent);
                }
            });
        }
        else {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ShowInventory.class);
                    intent.putExtra(MainActivity.EXTRA_MESSAGE, extras.getString("Inventory Name"));

                    ArrayList<String> ingredient_file = new ArrayList<String>();
                    FileInputStream fis;

                    try {
                        fis = openFileInput(extras.getString("Inventory Name") + "_Inventory.txt");

                        if (fis != null) {
                            InputStreamReader isr = new InputStreamReader(fis);
                            BufferedReader br = new BufferedReader(isr);

                            String line = "";

                            do {
                                line = br.readLine();
                                if (line == null)
                                    continue;
                                String[] splitting = line.split("\\|");
                                String line2 = "";
                                for (int i = 0; i < splitting.length - 1; i++) {
                                    line2 += splitting[i];
                                    line2 += "       ";
                                }
                                line2 += splitting[splitting.length - 1];
                                if (line2.equals(message2))
                                    continue;
                                ingredient_file.add(line);
                            } while (line != null);
                        }
                        fis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ;

                    FileOutputStream fos;
                    try {
                        fos = openFileOutput(extras.getString("Inventory Name") + "_Inventory.txt", Context.MODE_PRIVATE);
                        OutputStreamWriter osw = new OutputStreamWriter(fos);
                        for (int i = 0; i < ingredient_file.size(); i++) {
                            osw.write(ingredient_file.get(i));
                            osw.write("\n");
                            osw.flush();
                        }
                        osw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    startActivity(intent);
                }
            });
        }

        Button button_edit = (Button)findViewById(R.id.edit_ingredient);
        button_edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditIngredient.class);
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
            }
        });
    }
}
