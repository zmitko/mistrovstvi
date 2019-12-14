package com.example.ukol2_pokemon_typechart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static String DEF_TYPE = "Type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Integer[][] types = {
                {1,1,1,1,1,5,1,0,5,1,1,1,1,1,1,1,1,1}, //NORMAL     0
                {2,1,5,5,1,2,5,0,2,1,1,1,1,5,2,1,2,5}, //FIGHT      1
                {1,2,1,1,1,5,2,1,5,1,1,2,5,1,1,1,1,1}, //FLYING     2
                {1,1,1,5,5,5,1,5,0,1,1,2,1,1,1,1,1,2}, //POISON     3
                {1,1,0,2,1,2,5,1,2,2,1,5,2,1,1,1,1,1}, //GROUND     4
                {1,5,2,1,5,1,2,1,5,2,1,1,1,1,2,1,1,1}, //ROCK       5
                {1,5,5,5,1,1,1,5,5,5,1,2,1,2,1,1,2,5}, //BUG        6
                {0,1,1,1,1,1,1,2,1,1,1,1,1,2,1,1,5,1}, //GHOST      7
                {1,1,1,1,1,2,1,1,5,5,5,1,5,1,2,1,1,2}, //STEEL      8
                {1,1,1,1,1,5,2,1,2,5,5,2,1,1,2,5,1,1}, //FIRE       9
                {1,1,1,1,2,2,1,1,1,2,5,5,1,1,1,5,1,1}, //WATER      10
                {1,1,5,5,2,2,5,1,5,5,2,5,1,1,1,5,1,1}, //GRASS      11
                {1,1,2,1,0,1,1,1,1,1,2,5,5,1,1,5,1,1}, //ELECTRIC   12
                {1,2,1,2,1,1,1,1,5,1,1,1,1,5,1,1,0,1}, //PSYCHIC    13
                {1,1,2,1,2,1,1,1,5,5,5,2,1,1,5,2,1,1}, //ICE        14
                {1,1,1,1,1,1,1,1,5,1,1,1,1,1,1,2,1,0}, //DRAGON     15
                {1,5,1,1,1,1,1,2,1,1,1,1,1,2,1,1,5,5}, //DARK       16
                {1,2,1,5,1,1,1,1,5,5,1,1,1,1,1,2,2,1} //FAIRY       17
        };
        final Integer test = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DEF_TYPE, 20);
        editor.commit();

        final TextView defType = (TextView) findViewById(R.id.type_text);
        defType.setText("No type selected");

        final TextView effect = (TextView) findViewById(R.id.effect);
        effect.setText(String.valueOf(""));

        final Button normalButton = (Button) findViewById(R.id.normal);
        final Button fightButton = (Button) findViewById(R.id.fight);
        final Button flyingButton = (Button) findViewById(R.id.flying);
        final Button poisonButton = (Button) findViewById(R.id.poison);
        final Button groundButton = (Button) findViewById(R.id.ground);
        final Button rockButton = (Button) findViewById(R.id.rock);
        final Button bugButton = (Button) findViewById(R.id.bug);
        final Button ghostButton = (Button) findViewById(R.id.ghost);
        final Button steelButton = (Button) findViewById(R.id.steel);
        final Button fireButton = (Button) findViewById(R.id.fire);
        final Button waterButton = (Button) findViewById(R.id.water);
        final Button grassButton = (Button) findViewById(R.id.grass);
        final Button electricButton = (Button) findViewById(R.id.electric);
        final Button psychicButton = (Button) findViewById(R.id.psychic);
        final Button iceButton = (Button) findViewById(R.id.ice);
        final Button dragonButton = (Button) findViewById(R.id.dragon);
        final Button darkButton = (Button) findViewById(R.id.dark);
        final Button fairyButton = (Button) findViewById(R.id.fairy);

        //NORMAL
        normalButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 0);
                    editor.commit();
                    defType.setText("Normal");
                }
                else {
                    switch (types[0][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //FIGHTING
        fightButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 1);
                    editor.commit();
                    defType.setText("Fighting");
                }
                else {
                    switch (types[1][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //FLYING
        flyingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 2);
                    editor.commit();
                    defType.setText("Flying");
                }
                else {
                    switch (types[2][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //POISON
        poisonButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 3);
                    editor.commit();
                    defType.setText("Poison");
                }
                else {
                    switch (types[3][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //GROUND
        groundButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 4);
                    editor.commit();
                    defType.setText("Ground");
                }
                else {
                    switch (types[4][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //ROCK
        rockButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 5);
                    editor.commit();
                    defType.setText("Rock");
                }
                else {
                    switch (types[5][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //BUG
        bugButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 6);
                    editor.commit();
                    defType.setText("Bug");
                }
                else {
                    switch (types[6][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //GHOST
        ghostButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 7);
                    editor.commit();
                    defType.setText("Ghost");
                }
                else {
                    switch (types[7][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //STEEL
        steelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 8);
                    editor.commit();
                    defType.setText("STEEL");
                }
                else {
                    switch (types[8][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //FIRE
        fireButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 9);
                    editor.commit();
                    defType.setText("Fire");
                }
                else {
                    switch (types[9][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //WATER
        waterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 10);
                    editor.commit();
                    defType.setText("Water");
                }
                else {
                    switch (types[10][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //GRASS
        grassButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 11);
                    editor.commit();
                    defType.setText("Grass");
                }
                else {
                    switch (types[11][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //ELECTRIC
        electricButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 12);
                    editor.commit();
                    defType.setText("Electric");
                }
                else {
                    switch (types[12][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //PSYCHIC
        psychicButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 13);
                    editor.commit();
                    defType.setText("Psychic");
                }
                else {
                    switch (types[13][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //ICE
        iceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 14);
                    editor.commit();
                    defType.setText("Ice");
                }
                else {
                    switch (types[14][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //DRAGON
        dragonButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 15);
                    editor.commit();
                    defType.setText("Dragon");
                }
                else {
                    switch (types[15][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //DARK
        darkButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 16);
                    editor.commit();
                    defType.setText("Dark");
                }
                else {
                    switch (types[16][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        //FAIRY
        fairyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (sharedPreferences.getInt(DEF_TYPE, 20) == 20)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(DEF_TYPE, 17);
                    editor.commit();
                    defType.setText("Fairy");
                }
                else {
                    switch (types[17][sharedPreferences.getInt(DEF_TYPE, 20)]) {
                        case 0:effect.setText("Attack will have no effect"); break;
                        case 1: effect.setText("Attack will have regular effect"); break;
                        case 2: effect.setText("Attack will be super effective"); break;
                        case 5: effect.setText("Attack won't be effective"); break;
                        default: effect.setText("Error occurred"); break;
                    }
                }
            }
        });

        final Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(DEF_TYPE, 20);
                editor.commit();
                defType.setText("No type selected");
                effect.setText("");
            }
        });
    }
}

