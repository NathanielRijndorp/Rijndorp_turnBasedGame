package com.example.rijndorp_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ObtainCharacter extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_obtain_character);
        getSupportActionBar().hide();

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomCharacter();
            }
        });
        TextView textview = findViewById(R.id.button);
        setTextView(textview,
                ResourcesCompat.getColor(getResources(), R.color.rainbow1, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow2, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow3, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow4, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow5, null),
                ResourcesCompat.getColor(getResources(), R.color.rainbow6, null)
        );
    }

    private void setTextView(TextView textView, int... color){
        TextPaint paint = textView.getPaint();
        float width = paint.measureText(textView.getText().toString());

        Shader shader = new LinearGradient(0, 0, width, textView.getTextSize(), color, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(shader);
        textView.setTextColor(color[0]);
    }

    public void randomCharacter(){
        Intent intentArray[] = {new Intent(this, ArbiterVildred.class), new Intent(this, ArbiterVildred.class),
                new Intent(this, ArbiterVildred.class), new Intent(this, ArbiterVildred.class)};

        int random = (int) (Math.random()*4);
        Intent intent2 = intentArray[random];
        startActivity(intent2);
    }
}