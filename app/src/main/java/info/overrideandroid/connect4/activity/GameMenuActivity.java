package info.overrideandroid.connect4.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.overrideandroid.connect4.R;
import info.overrideandroid.connect4.controller.GameMenuController;
import info.overrideandroid.connect4.rules.GameRules;
import info.overrideandroid.connect4.view.MenuView;

public class GameMenuActivity extends AppCompatActivity implements GameMenuController.MenuControllerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
        MenuView menuView = (MenuView) findViewById(R.id.menuView);
        GameMenuController gameMenuController =new GameMenuController(this, menuView);
        menuView.setListeners(gameMenuController);

    }

    @Override
    public void onPlay(@NonNull GameRules gameRules) {
        Intent gamePlayIntent = new Intent(this,GamePlayActivity.class);
        gamePlayIntent.putExtras(gameRules.exportTo(new Bundle()));
        startActivity(gamePlayIntent);
    }
}
