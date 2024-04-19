package com.example.laba4_2_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class GameSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        final EditText numberOfPlayersEditText = findViewById(R.id.numberOfPlayersEditText);
        final EditText skipLimitEditText = findViewById(R.id.skipLimitEditText);
        final EditText spinLimitEditText = findViewById(R.id.spinLimitEditText);

        Button startGameButton = findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfPlayers = Integer.parseInt(numberOfPlayersEditText.getText().toString());
                int skipLimit = Integer.parseInt(skipLimitEditText.getText().toString());
                int spinLimit = Integer.parseInt(spinLimitEditText.getText().toString());

                // Создаем объект настроек игры
                GameSettingsModel gameSettings = new GameSettingsModel(numberOfPlayers, skipLimit, spinLimit);

                // Передаем объект настроек в активность GameActivity
                Intent intent = new Intent(GameSettingsActivity.this, GameActivity.class);
                intent.putExtra("GameSettings", gameSettings);
                startActivity(intent);
            }
        });
    }
}
