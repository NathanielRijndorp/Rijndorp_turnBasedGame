package com.example.rijndorp_turnbasedgame;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

@SuppressLint("SetTextI18n")
public class ArbiterVildred extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer sfxPlayer;
    Button button;
    TextView txtHeroName, txtMonsName, txtHeroHP, txtMonsHP, txtHeroMP, txtMonsMP, txtHeroDPS, txtMonsDPS, txtLog;
    Button btnNextTurn;
    ImageButton skill1, skill2, skill3, skill4;

    //Hero Stats
    String heroName = "Arbiter Vildred";
    int heroHP = 1500;
    int heroMP = 1000;
    int heroMinDamage = 100;
    int heroMaxDamage = 150;
    //HP Bar
    double heroHpPercent;
    public ProgressBar hpBar;
    int monsCrit;
    int heroCrit;
    //Monster Stats
    String monsName = "Enott the Red Nose Reindeer";
    int monsterHP = 3000;
    int monsterMP = 400;
    int monsterMinDamage = 40;
    int monsterMaxDamage = 222;
    //Game Turn
    int turnNumber = 1;

    boolean disabledstatus = false;
    int statuscounter = 0;
    int buttoncounter1 = 0;
    int buttoncounter2 = 0;
    int buttoncounter3 = 0;
    int buttoncounter4 = 0;
    int burnCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.arbiter_pvp);
        getSupportActionBar().hide();

        //XML ids for text and button
        txtHeroName = findViewById(R.id.txtHeroName);
        txtMonsName = findViewById(R.id.txtMonsName);
        txtHeroHP = findViewById(R.id.txtHeroHP);
        txtMonsHP = findViewById(R.id.txtMonsHP);
        txtHeroMP = findViewById(R.id.txtHeroMP);
        txtMonsMP = findViewById(R.id.txtMonsMP);
        btnNextTurn = findViewById(R.id.btnNxtTurn);
        txtHeroDPS = findViewById(R.id.txtHeroDPS);
        txtMonsDPS = findViewById(R.id.txtMonsDPS);
        //Combat Log XML
        txtLog = findViewById(R.id.txtCombatLog);
        //Hero XML
        txtHeroName.setText(heroName);
        txtHeroHP.setText(String.valueOf(heroHP));
        txtHeroMP.setText(String.valueOf(heroMP));
        //Mons. XML
        txtMonsName.setText(monsName);
        txtMonsHP.setText(String.valueOf(monsterHP));
        txtMonsMP.setText(String.valueOf(monsterMP));
        //DPS XML
        txtHeroDPS.setText(heroMinDamage + " ~ " + heroMaxDamage);
        txtMonsDPS.setText(monsterMinDamage + " ~ " + monsterMaxDamage);
        //Skill XML
        skill1 = findViewById(R.id.btnSkill1);
        skill2 = findViewById(R.id.btnSkill2);
        skill3 = findViewById(R.id.btnSkill3);
        skill4 = findViewById(R.id.btnSkill4);
        //these two codes are new
        hpBar = findViewById(R.id.hpBar);
        hpBar.setMax(100);


        //button onClick Listener
        btnNextTurn.setOnClickListener(this);
        skill1.setOnClickListener(this);
        skill2.setOnClickListener(this);
        skill3.setOnClickListener(this);
        skill4.setOnClickListener(this);
    }
    private void normalHit() {
        if (sfxPlayer == null) {
            sfxPlayer = new MediaPlayer();
            sfxPlayer = MediaPlayer.create(this, R.raw.normalhitsfx);
            sfxPlayer.setOnCompletionListener(mediaPlayer -> stopPlayer());
            sfxPlayer.start();
        }
    }
    private void criticalHit() {
        if (sfxPlayer == null) {
            sfxPlayer = new MediaPlayer();
            sfxPlayer = MediaPlayer.create(this, R.raw.crithitsfx);
            sfxPlayer.setOnCompletionListener(mediaPlayer -> stopPlayer());
            sfxPlayer.start();
        }
    }
    private void stopPlayer() {
        if (sfxPlayer != null) {
            sfxPlayer.release();
            sfxPlayer = null;
        }
    }
    private void resetStats() {
        heroHP = 1500;
        heroMP = 1000;
        monsterHP = 3000;
        turnNumber= 1;
        burnCounter = 0;
        buttoncounter1 = 0;
        buttoncounter2 = 0;
        buttoncounter3 = 0;
        buttoncounter4 = 0;
        btnNextTurn.setText("Reset Game");
    }
    private void setSkill4() {
        if (heroMP > 0) {
            txtLog.setText("Arbiter Vildred "  + " has released all his mana! It dealt " + ((heroMP * 2)-666) + " damage to the enemy. Mana Void!");
            buttoncounter4 = 999;
            monsterHP -= ((heroMP*2)-666);
            //this is the code to set the value of the hp bar
            hpBar.setProgress((int) heroHpPercent);
            turnNumber++;
            txtMonsHP.setText(String.valueOf(monsterHP));
            btnNextTurn.setText("Next Turn (" + turnNumber + ")");
            heroMP = 0;
        }
        else {
            txtLog.setText("Mana insufficient");
        }
        if(monsterHP < 0){ //even
            txtLog.setText("Arbiter Vildred " +" dealt "+ heroMP * 2 + " damage to the enemy. The player is victorious!");
            resetStats();
        }
    }
    private void setSkill3(int herodps) {
        if (heroMP > 200) {
            buttoncounter3 = 10;
            heroMP -= 200;
            burnCounter = 3;
            //this is the code to set the value of the hp bar
            hpBar.setProgress((int) heroHpPercent);
            turnNumber++;
            txtMonsHP.setText(String.valueOf(monsterHP));
            btnNextTurn.setText("Next Turn (" + turnNumber + ")");
            txtLog.setText("Arbiter Vildred " + " burned the enemy! You will be dealing a continous damage of 150 every turn for 3 turns!");
        }
        else {
            txtLog.setText("Mana insufficient");
        }
        if(monsterHP < 0){ //even
            txtLog.setText("Arbiter Vildred " +" dealt "+ herodps + " damage to the enemy. The player is victorious!");
            resetStats();
        }
    }
    private void setSkill2(int herodps) {
        if (heroMP > 0) {
            buttoncounter2 = 5;
            Random randomizer = new Random();
            heroHP += randomizer.nextInt(666) + 666;
            heroMP = heroMP/2;
            //this is the code to set the value of the hp bar
            hpBar.setProgress((int) heroHpPercent);
            turnNumber++;
            txtMonsHP.setText(String.valueOf(monsterHP));
            btnNextTurn.setText("Next Turn (" + turnNumber + ")");
            txtLog.setText("Arbiter Vildred has used forbidden magic to heal himself!");
        }
        else {
            txtLog.setText("Mana insufficient");
        }
        if(monsterHP < 0){ //even
            txtLog.setText("Arbiter Vildred " +" dealt "+ herodps + " damage to the enemy. The player is victorious!");
            resetStats();
        }
    }
    private void setSkill1(int herodps) {
        if (heroMP > 100) {
            buttoncounter1 = 8;
            heroMP -= 100;
            monsterHP -= 250;
            //this is the code to set the value of the hp bar
            hpBar.setProgress((int) heroHpPercent);
            turnNumber++;
            txtMonsHP.setText(String.valueOf(monsterHP));
            btnNextTurn.setText("Next Turn (" + turnNumber + ")");

            txtLog.setText("Arbiter Vildred " + " used stun!. It dealt " + 250 + " damage to the enemy. The enemy is stunned for 4 turns");
        }
        else {
            txtLog.setText("Mana insufficient");
        }
        if(monsterHP < 0){ //even
            txtLog.setText("Arbiter Vildred " +" dealt "+ (herodps) + " damage to the enemy. The player is victorious!");
            resetStats();
        }
    }
    @Override
    public void onClick(View v) {
        Random randomizer = new Random();
        Random critRandomizer = new Random();
        int herodps = randomizer.nextInt(heroMaxDamage - heroMinDamage) + heroMinDamage;
        int monsdps = randomizer.nextInt(monsterMaxDamage - monsterMinDamage) + monsterMinDamage;
        //crit checkers, two were placed to differenciate odds
        int crit = critRandomizer.nextInt(100);
        int crit2 = critRandomizer.nextInt(100);
        //crit condition
        if (crit <=10) {
            heroCrit++;
        }
        else {
            heroCrit = 0;
        }
        if (crit2 <=2) {
            monsCrit++;
        }
        else {
            monsCrit = 0;
        }
        if(turnNumber % 2 == 1){//if it is enemy's turn, disable button
            skill1.setEnabled(false);
            skill2.setEnabled(false);
            skill3.setEnabled(false);
            skill4.setEnabled(false);
        }
        else if (turnNumber %2 == 0) {//if it is your turn, enable button
            if(buttoncounter1 >0){
                skill1.setEnabled(false);
                // buttoncounter1--;
            }
            else if(buttoncounter1 ==0 ){
                skill1.setEnabled(true);
            }
            else {
                txtLog.setText("Not your turn");
            }
            if(buttoncounter2 >0){
                skill2.setEnabled(false);
                // buttoncounter2--;
            }
            else if(buttoncounter2 ==0 ){
                skill2.setEnabled(true);
            }
            else {
                txtLog.setText("Not your turn");
            }
            if(buttoncounter3 >0){
                skill3.setEnabled(false);
                // buttoncounter--;
            }
            else if(buttoncounter3 ==0 ){
                skill3.setEnabled(true);
            }
            else {
                txtLog.setText("Not your turn");
            }
            if(buttoncounter4 >0 ){
                skill4.setEnabled(false);
                // buttoncounter--;
            }
            else if(buttoncounter4 ==0 ){
                skill4.setEnabled(true);
            }
        }
        if (heroHP > 1501) {
            heroHP = 1500;
        }

        // this is the formula to get the health percentage
        // you can change 1500 to a variable for the max health
        heroHpPercent = heroHP * 100 / 1500;
        // ito yung para mag change ang color ng health bar
        if ((int) heroHpPercent > 75 && (int) heroHpPercent <= 100 ) {
            hpBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.fullhp)));// for the r.color thingy kay go to colors and make some color
        } else if ((int) heroHpPercent >= 50 && (int) heroHpPercent <= 75 ) {
            hpBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.seventyhp)));
        }
        else if ((int) heroHpPercent >= 25 && (int) heroHpPercent <= 50 ) {
            hpBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.fiftyhp)));
        }
        else if ((int) heroHpPercent >= 10 && (int) heroHpPercent <= 25 ) {
            hpBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.twentyfivehp)));
        }
        else {
            hpBar.setProgressTintList(ColorStateList.valueOf((getResources().getColor(R.color.lowhp))));
        }
        if (turnNumber %2 != 1 && burnCounter > 0) {
                Log.d(TAG, "beforeBurn: " + monsterHP);
                monsterHP -= 150;
                Log.d(TAG, "afterBurn: " + monsterHP);
                burnCounter -= 1;
        }
        switch(v.getId()) {
            case R.id.btnSkill4:
                setSkill4();
                break;
            case R.id.btnSkill3:
                setSkill3(herodps);
                break;
            case R.id.btnSkill2:
                setSkill2(herodps);
                break;
            case R.id.btnSkill1:
                setSkill1(herodps);
                break;
            case R.id.btnNxtTurn:
                //
                if(turnNumber % 2 == 1){ //oddturnNumber % 2 == 1
                    if (heroCrit == 1) {
                        monsterHP -= (herodps*2);
                        txtLog.setText("Arbiter Vildred " +" dealt  "+ (herodps*2) + " critical damage to the enemy.");
                        criticalHit();
                    }
                    else {
                        monsterHP -= herodps;
                        txtLog.setText("Arbiter Vildred " + " dealt " + (herodps) + " damage to the enemy.");
                        normalHit();
                    }
                    //this is the code to set the value of the hp bar
                    hpBar.setProgress((int) heroHpPercent);
                    turnNumber++;
                    txtMonsHP.setText(String.valueOf(monsterHP));
                    btnNextTurn.setText("Next Turn ("+ turnNumber +")");

                    if(monsterHP < 0){ //even
                        txtLog.setText("Arbiter Vildred " +" dealt "+ (herodps) + " damage to the enemy. The player is victorious!");
                        resetStats();
                    }

                    // if(statuscounter>0){ //if the enemy is still stunned, reduce the stun for 1 turn
                    // statuscounter--;
                    // if(statuscounter==0){
                    //   disabledstatus=false;
                    // }
                    //  }
                    buttoncounter1--;

                    break;
                }
                else if(turnNumber %2 != 1){ //even
                    if (burnCounter > 0) {
                        Toast.makeText(this, "Enemy is still burning!", Toast.LENGTH_SHORT).show();
                    }
                    if(disabledstatus==true){
                        txtLog.setText("The enemy is still stunned for "+ statuscounter + "turns");
                        statuscounter--;
                        turnNumber++;
                        btnNextTurn.setText("Next Turn ("+ turnNumber +")");
                        if(statuscounter==0){
                            disabledstatus=false;
                        }
                    }
                    else{
                        if (monsCrit == 1) {
                            heroHP = heroHP - monsdps*10;
                            txtLog.setText("The monster "+ monsName +" dealt "+ monsdps * 10 + " critical damage to the enemy.");
                            criticalHit();
                            hpBar.setProgress((int) heroHpPercent);
                        }
                        else {
                            heroHP -= monsdps;
                            txtLog.setText("The monster "+ monsName +" dealt "+ monsdps + " damage to the enemy.");
                            normalHit();
                            hpBar.setProgress((int) heroHpPercent);
                        }
                        //this is the code to set the value of the hp bar
                        hpBar.setProgress((int) heroHpPercent);
                        turnNumber++;
                        txtHeroHP.setText(String.valueOf(heroHP));
                        btnNextTurn.setText("Next Turn ("+ turnNumber +")");

                        if(heroHP < 0){
                            txtLog.setText("The monster "+ monsName +" dealt "+ monsdps + " damage to the enemy. Game Over");
                            resetStats();
                        }
                    }
                    // buttoncounter--;
                }
                break;

        }
    }
}