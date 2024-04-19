package com.example.laba4_2_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity {

    private ListView playersListView;
    private ArrayAdapter<String> playersAdapter;
    private List<String> playersList;
    private int currentPlayerIndex = 0;
    private int bulletsLeft;
    private int skipLeft;
    private int spinLeft;
    private boolean[] revolverChamber = new boolean[6]; // Массив для представления револьвера

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playersListView = findViewById(R.id.playersListView);
        playersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        playersListView.setAdapter(playersAdapter);

        // Получаем объект настроек игры из Intent
        GameSettingsModel gameSettings = (GameSettingsModel) getIntent().getSerializableExtra("GameSettings");
        int numberOfPlayers = gameSettings.getNumberOfPlayers();

        playersList = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            playersList.add("Игрок " + i);
        }
        playersAdapter.addAll(playersList);

        // Инициализируем переменные для игры
        bulletsLeft = 1;
        skipLeft = gameSettings.getSkipLimit();
        spinLeft = gameSettings.getSpinLimit();

        // Инициализируем револьвер
        initializeRevolverChamber();

        // Находим кнопки на экране
        Button triggerButton = findViewById(R.id.triggerButton);
        Button skipButton = findViewById(R.id.skipButton);
        Button spinButton = findViewById(R.id.spinButton);

        // Устанавливаем обработчики событий для кнопок
        triggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullTrigger();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipTurn();
            }
        });

        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinChamber();
            }
        });
    }

    // Метод для инициализации револьвера
    private void initializeRevolverChamber() {
        // Заполняем все ячейки пустыми
        Arrays.fill(revolverChamber, false);
        // Размещаем пулю в случайной ячейке
        int bulletIndex = new Random().nextInt(6);
        revolverChamber[bulletIndex] = true;
    }

    private void pullTrigger() {
        // Получаем результат из выбранной ячейки револьвера
        boolean isBullet = revolverChamber[currentPlayerIndex];
        if (isBullet) {
            // Если пуля найдена, игрок выбывает из игры
            Toast.makeText(this, playersList.get(currentPlayerIndex) + " выбывает из игры!", Toast.LENGTH_SHORT).show();
            playersList.remove(currentPlayerIndex);
            playersAdapter.notifyDataSetChanged();
            // Если остался только один игрок, он объявляется победителем
            if (playersList.size() == 1) {
                Toast.makeText(this, playersList.get(0) + " победил в игре!", Toast.LENGTH_LONG).show();
                finish();
            }
            nextPlayer(); // Обновляем currentPlayerIndex после выбывания игрока
        } else {
            // Если пуля не найдена, игрок выживает
            Toast.makeText(this, "Вы выжили!", Toast.LENGTH_SHORT).show();
            nextPlayer(); // Переходим к следующему игроку
        }
    }


    // Метод для пропуска хода
    private void skipTurn() {
        // Проверяем, есть ли у текущего игрока оставшиеся пропуски
        if (skipLeft > 0) {
            // Уменьшаем количество доступных пропусков
            skipLeft--;
            // Переходим к следующему игроку
            nextPlayer();
        } else {
            // Если пропуски закончились, текущему игроку нужно выстрелить
            pullTrigger();
        }
    }

    // Метод для прокрутки барабана
    private void spinChamber() {
        // Проверяем, есть ли у текущего игрока оставшиеся прокрутки
        if (spinLeft > 0) {
            // Уменьшаем количество доступных прокруток
            spinLeft--;
            // Сбрасываем патрон
            bulletsLeft = 1;
            // Сообщаем игрокам о прокрутке барабана
            Toast.makeText(this, "Барабан прокручен!", Toast.LENGTH_SHORT).show();
        } else {
            // Если прокрутки закончились, но у игрока остались пропуски,
            // то предоставляем ему выбор: пропустить ход или выстрелить
            if (skipLeft > 0) {
                // Если у игрока остались пропуски, позволяем ему пропустить ход
                skipTurn();
            } else {
                // Если и пропуски закончились, приходится стрелять
                pullTrigger();
            }
        }
    }

    // Метод для перехода к следующему игроку
    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playersList.size();
        Toast.makeText(this, "Следующий игрок: " + playersList.get(currentPlayerIndex), Toast.LENGTH_SHORT).show();
    }

}
