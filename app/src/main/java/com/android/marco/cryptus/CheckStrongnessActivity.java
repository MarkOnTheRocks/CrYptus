package com.android.marco.cryptus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by Marco Mancuso on 10/09/2016.
 */
public class CheckStrongnessActivity extends Activity {

    public class MyPasswordTransformationMethod extends PasswordTransformationMethod {

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {

            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source;
            }

            public char charAt(int index) {
                char c = 7;
                return c;
            }

            public int length() {
                return mSource.length();
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    };

    private TextView mtextView;
    private EditText editText;
    private TextView result;
    private Switch mswitch;
    private Button mbutton;
    String current;


    @Override
    public void onCreate (Bundle onSavedInstace) {
        super.onCreate(onSavedInstace);
        setContentView(R.layout.activity_strongness);
        mtextView = (TextView) findViewById(R.id.textViewStr);
        editText = (EditText) findViewById(R.id.editText);
        result = (TextView) findViewById(R.id.textresult);
        mbutton = (Button) findViewById(R.id.buttonch);
        mtextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        mswitch = (Switch) findViewById(R.id.switch1);
        mswitch.setChecked(false);
        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);;
                }
                else {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if(editText.getText().toString().equals("")) {
                    Toast.makeText(CheckStrongnessActivity.this, "Please fill the space with a non-empty sequence", Toast.LENGTH_SHORT).show();
                }
                else {
                    int res = checkStrong(editText.getText().toString());
                    switch (res) {
                        case 0:
                            result.setText("Very weak");
                            result.setTextColor(Color.parseColor("#820e08"));
                            break;

                        case 1:
                            result.setText("Weak");
                            result.setTextColor(Color.parseColor("#f63228"));
                            break;

                        case 2:
                            result.setText("Medium");
                            result.setTextColor(Color.parseColor("#d37c0d"));
                            break;

                        case 3:
                            result.setText("Strong");
                            result.setTextColor(Color.parseColor("#9fd30d"));
                            break;

                        default:
                            result.setText("Very Strong");
                            result.setTextColor(Color.parseColor("#0dd313"));
                    }
                }
            }
        });
    }

    public static int checkStrong(String ref) {
        if(ref.contains("ABC")|| ref.contains("abc")) {
            return 0;
        }
        if(ref.contains("123")) {
            return 0;
        }
        if(ref.contains("qwerty") || ref.contains("QWERTY")) {
            return 0;
        }

        int length = ref.length();
        int lowercase = 0;
        int uppercase = 0;
        int symbols = 0;
        int digits = 0;
        int lettersonly = 0;
        int numbersonly = 0;
        int nsymbolsonly = 0;
        int requirements = 0;
        int bonus = 0;

        if(length > 6) {
            requirements+=1;
        }

        for(int i = 0; i<length; i++) {
            char c = ref.charAt(i);
            int p = (int) c;
            if(c>=65 && c<= 90) {
                uppercase+=1;
            }
            else if(c>=97 && c<= 122) {
                lowercase+=1;
            }

            else if(c>=48 && c<= 57) {
                digits+=1;
            }

            else if((c>=33 && c<= 46) || (c>=58 && c<=64) || (c==96) || (c>=123 && c<=126)) {
                symbols+=1;
            }
        }

        if(uppercase > 0) {
            requirements+=1;
        }


        if(lowercase > 0) {
            requirements+=1;
        }


        if(digits > 0) {
            requirements+=1;
        }


        for(int i = 1; i<length-1; i++) {
            if (Character.isDigit(ref.charAt(i)))
                bonus+=1;
        }

        if(symbols > 0) {
            requirements+=1;
        }

        if(lowercase + uppercase == ref.length()) {
            lettersonly = 1;
        }

        if(digits == ref.length()) {
            numbersonly = 1;
        }

        if(symbols == ref.length()) {
            nsymbolsonly = 4;
        }



        int total = (length * 4) + ((length - uppercase) * 2)
                + ((length - lowercase) * 2) + (digits * 4) + (symbols * 8)
                + (bonus * 2) + (requirements * 2) - (lettersonly * length*2)
                - (numbersonly * length*3) - (uppercase * 2) - (lowercase * 2) + nsymbolsonly*2;
        System.out.println(total);

        return total/40;

    }





}
