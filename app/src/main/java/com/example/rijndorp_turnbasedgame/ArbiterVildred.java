package com.example.rijndorp_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

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
    int crit;
    //Monster Stats
    String monsName = "Enott the Red Nose Reindeer";
    int monsterHP = 3000;
    int monsterMP = 400;
    int monsterMinDamage = 40;
    int monsterMaxDamage = 55;
    //Game Turn
    int turnNumber = 1;

    boolean disabledstatus = false;
    int statuscounter = 0;
    int buttoncounter = 0;


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
        txtHeroDPS.setText(String.valueOf(heroMinDamage) + " ~ " + String.valueOf(heroMaxDamage));
        txtMonsDPS.setText(String.valueOf(monsterMinDamage) + " ~ " + String.valueOf(monsterMaxDamage));
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
    }
    private void normalHit() {
        if (sfxPlayer == null) {
            sfxPlayer = new MediaPlayer();
            sfxPlayer = MediaPlayer.create(this, R.raw.normalhitsfx);
            sfxPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
            sfxPlayer.start();
        }
    }
    private void criticalHit() {
        if (sfxPlayer == null) {
            sfxPlayer = new MediaPlayer();
            sfxPlayer = MediaPlayer.create(this, R.raw.crithitsfx);
            sfxPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
            sfxPlayer.start();
        }
    }
    private void stopPlayer() {
        if (sfxPlayer != null) {
            sfxPlayer.release();
            sfxPlayer = null;
        }
    }
    @Override
    public void onClick(View v) {

        Random randomizer = new Random();
        Random critRandomizer = new Random();
        int herodps = randomizer.nextInt(heroMaxDamage - heroMinDamage) + heroMinDamage;
        int monsdps = randomizer.nextInt(monsterMaxDamage - monsterMinDamage) + monsterMinDamage;
        int crit = critRandomizer.nextInt(100);
        int crit2 = critRandomizer.nextInt(100);

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
        if(turnNumber % 2 != 1){//if it is enemy's turn, disable button
            skill1.setEnabled(false);
        }
        else if(turnNumber%2 == 1){
            skill1.setEnabled(true);
        }

        if(buttoncounter>0){
            skill1.setEnabled(false);
            // buttoncounter--;
        }
        else if(buttoncounter==0){
            skill1.setEnabled(true);
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
        switch(v.getId()) {
            case R.id.btnSkill1:
                monsterHP = monsterHP - 250;
                //this is the code to set the value of the hp bar
                hpBar.setProgress((int) heroHpPercent);
                turnNumber++;
                txtMonsHP.setText(String.valueOf(monsterHP));
                btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                txtLog.setText("Our Hero "+String.valueOf(heroName) +" used stun!. It dealt "+String.valueOf(250) + " damage to the enemy. The enemy is stunned for 4 turns");

                disabledstatus = true;
                statuscounter = 4;

                if(monsterHP < 0){ //even
                    txtLog.setText("Our Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy. The player is victorious!");
                    heroHP = 1500;
                    monsterHP = 3000;
                    turnNumber= 1;
                    btnNextTurn.setText("Reset Game");
                }
                buttoncounter=12;


                break;
            case R.id.btnNxtTurn:
                //
                if(turnNumber % 2 == 1){ //odd
                    if (heroCrit == 1) {
                        monsterHP = monsterHP - herodps*2;
                        txtLog.setText("Our Hero "+String.valueOf(heroName) +" dealt  "+String.valueOf(herodps*2) + " critical damage to the enemy.");
                        criticalHit();
                    }
                    else {
                        monsterHP = monsterHP - herodps;
                        txtLog.setText("Our Hero " + String.valueOf(heroName) + " dealt " + String.valueOf(herodps) + " damage to the enemy.");
                        normalHit();
                    }
                    //this is the code to set the value of the hp bar
                    hpBar.setProgress((int) heroHpPercent);
                    turnNumber++;
                    txtMonsHP.setText(String.valueOf(monsterHP));
                    btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                    if(monsterHP < 0){ //even
                        txtLog.setText("Our Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy. The player is victorious!");
                        heroHP = 1500;
                        monsterHP = 3000;
                        turnNumber= 1;
                        buttoncounter=0;
                        btnNextTurn.setText("Reset Game");
                    }

                    // if(statuscounter>0){ //if the enemy is still stunned, reduce the stun for 1 turn
                    // statuscounter--;
                    // if(statuscounter==0){
                    //   disabledstatus=false;
                    // }
                    //  }
                    buttoncounter--;
                }
                else if(turnNumber%2 != 1){ //even

                    if(disabledstatus==true){
                        txtLog.setText("The enemy is still stunned for "+String.valueOf(statuscounter)+ "turns");
                        statuscounter--;
                        turnNumber++;
                        btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");
                        if(statuscounter==0){
                            disabledstatus=false;
                        }
                    }
                    else{
                        if (monsCrit == 1) {
                            heroHP = heroHP - monsdps*10;
                            txtLog.setText("The monster "+String.valueOf(monsName)+" dealt "+String.valueOf(monsdps*10)+ " critical damage to the enemy.");
                            criticalHit();
                        }
                        else {
                            monsterHP = monsterHP - herodps;
                            txtLog.setText("The monster "+String.valueOf(monsName)+" dealt "+String.valueOf(monsdps)+ " damage to the enemy.");
                            normalHit();
                        }
                        heroHP = heroHP - monsdps;
                        //this is the code to set the value of the hp bar
                        hpBar.setProgress((int) heroHpPercent);
                        turnNumber++;
                        txtHeroHP.setText(String.valueOf(heroHP));
                        btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                        if(heroHP < 0){
                            txtLog.setText("The monster "+String.valueOf(monsName)+" dealt "+String.valueOf(monsdps)+ " damage to the enemy. Game Over");
                            heroHP = 1500;
                            monsterHP = 3000;
                            turnNumber= 1;
                            buttoncounter=0;
                            btnNextTurn.setText("Reset Game");
                        }
                    }
                    // buttoncounter--;
                }
                break;
        }
    }
}