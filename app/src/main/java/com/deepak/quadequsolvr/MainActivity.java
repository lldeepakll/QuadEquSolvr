package com.deepak.quadequsolvr;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.co_eff_text)
    TextView co_eff_text;

    @BindView(R.id.equationTextView)
    TextView equationTextView;

    @BindView(R.id.enterEquationTextView)
    TextView enterEquationTextView;

    @BindView(R.id.x1Answer)
    TextView x1Answer;

    @BindView(R.id.x2Answer)
    TextView x2Answer;

    @BindView(R.id.x1Value)
    TextView x1Value;

    @BindView(R.id.x2Value)
    TextView x2Value;

    @BindView(R.id.isNegB_TextView)
    TextView isNegB_TextView;

    @BindView(R.id.answerTextView)
    TextView answerTextView;

    @BindView(R.id.isNegC_TextView)
    TextView isNegC_TextView;

    @BindView(R.id.co_eff_for_a)
    EditText coeffA;

    @BindView(R.id.co_eff_for_b)
    EditText coeffB;

    @BindView(R.id.co_eff_for_c)
    EditText coeffC;

    @BindView(R.id.cb_neg_b)
    CheckBox cb_neg_b;

    @BindView(R.id.cb_neg_c)
    CheckBox cb_neg_c;

    private String squareSymbol = "\u00B2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // bind the view using butterknife
        ButterKnife.bind(this);

        ShimmerFrameLayout shimmerContainer = (ShimmerFrameLayout) findViewById(R.id.shimmer_title);
        shimmerContainer.startShimmerAnimation();

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Watchword_thin_demo.otf");
        Typeface custom_font_II = Typeface.createFromAsset(getAssets(), "fonts/Stika_Font.otf");
        co_eff_text.setTypeface(custom_font);
        equationTextView.setTypeface(custom_font);
        enterEquationTextView.setTypeface(custom_font);
        isNegB_TextView.setTypeface(custom_font);
        isNegC_TextView.setTypeface(custom_font);
        answerTextView.setTypeface(custom_font_II);
        x1Answer.setTypeface(custom_font);
        x2Answer.setTypeface(custom_font);

        answerTextView.setVisibility(View.GONE);
        x1Answer.setVisibility(View.GONE);
        x2Answer.setVisibility(View.GONE);
        x1Value.setVisibility(View.GONE);
        x2Value.setVisibility(View.GONE);


    }

    @OnClick(R.id.solveEquation)
    public void onSolveEquation(View view){

        if(coeffA.getText().toString().equals("")||coeffB.getText().toString().equals("")||coeffC.getText().toString().equals("")) {
            Toast.makeText(this, "Please Enter the coeffs", Toast.LENGTH_SHORT).show();
        }else {


            int a = Integer.parseInt(coeffA.getText().toString());
            int b = Integer.parseInt(coeffB.getText().toString());
            int c = Integer.parseInt(coeffC.getText().toString());

//            if(cb_neg_b.isChecked()){
//                b *= -1;
//            }
//            if(cb_neg_c.isChecked()){
//                c *= -1;
//            }

            double a_doub = Integer.parseInt(coeffA.getText().toString());
            double b_doub = Integer.parseInt(coeffB.getText().toString());
            double c_doub = Integer.parseInt(coeffC.getText().toString());

            if(cb_neg_b.isChecked()){
                b_doub *= -1;
            }
            if(cb_neg_c.isChecked()){
                c_doub *= -1;
            }

                Log.e("TAG", "A = " + a);
                Log.e("TAG", "B = " + b);
                Log.e("TAG", "C = " + c);

                Log.e("TAG", "A = " + a_doub);
                Log.e("TAG", "B = " + b_doub);
                Log.e("TAG", "C = " + c_doub);

            answerTextView.setVisibility(View.VISIBLE);
            x1Answer.setVisibility(View.VISIBLE);
            x2Answer.setVisibility(View.VISIBLE);
            x1Value.setVisibility(View.VISIBLE);
            x2Value.setVisibility(View.VISIBLE);

            double x1 = (-b_doub + Math.sqrt(Math.pow(b_doub, 2) - (4 * a_doub * c_doub))) / (2 * a_doub);
            double x2 = (-b_doub - Math.sqrt(Math.pow(b_doub, 2) - (4 * a_doub * c_doub))) / (2 * a_doub);

            if (Double.isNaN(x1) || Double.isNaN(x2)){

                String eq = "" + a + "x" + squareSymbol + "+" + b + "x+" + c + "=0";
                enterEquationTextView.setText(eq);

                x1Value.setText("NaN");
                x2Value.setText("NaN");

            }else {

                Double truncatedX1 = BigDecimal.valueOf(x1)
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue();
                Double truncatedX2 = BigDecimal.valueOf(x2)
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue();

                Log.e("TAG", "" + truncatedX1);
                Log.e("TAG", "" + truncatedX2);

                if (a == 1) {

                    String eq = "x" + squareSymbol + "+" + b + "x+" + c + "=0";
                    enterEquationTextView.setText(eq);

                    x1Value.setText("" + truncatedX1);
                    x2Value.setText("" + truncatedX2);

                } else {

                    String eq = "" + a + "x" + squareSymbol + "+" + b + "x+" + c + "=0";
                    enterEquationTextView.setText(eq);

                    x1Value.setText("" + truncatedX1);
                    x2Value.setText("" + truncatedX2);
                }
            }
        }

        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

//    @OnClick(R.id.ok)
//    public void onButtonClick(View view) {
//
//        double a = Integer.parseInt(first.getText().toString());
//        double b = Integer.parseInt(sec.getText().toString());
//        double c = Integer.parseInt(third.getText().toString());
//
//
//        Log.e("TAG",a+"");
//        Log.e("TAG",b+"");
//        Log.e("TAG",c+"");
//
//        double ans = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
//        double ans2 = (-b - Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
//
//        Log.e("TAG",ans+"");
//        Log.e("TAG",ans2+"");
//
//        if (Double.isNaN(ans) || Double.isNaN(ans2))
//        {
//            lblTitle.setText("Answer contains imaginary numbers");
//        } else {
//
//            lblTitle.setText(ans+","+ans2);
//
//        }
//
//
//    }
}
