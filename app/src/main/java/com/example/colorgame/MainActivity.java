package com.example.colorgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int[] colors = new int[]{R.color.red, R.color.blue, R.color.yellow, R.color.green};
    View[] views;
    int scoreCount = 0, randomNumber;
    Boolean firstTime = true;
    Boolean[] clicked = new Boolean[]{false, false, false, false};

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.view0)
    View view0;

    @BindView(R.id.view1)
    View view1;

    @BindView(R.id.view2)
    View view2;

    @BindView(R.id.view3)
    View view3;

    @BindView(R.id.textViewScore)
    TextView textViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        textViewScore.setText(getResources().getText(R.string.score_0));
        views = new View[]{view0, view1, view2, view3};

        for (int i = 0; i < 4; i++)
            views[i].setBackgroundColor(getResources().getColor(colors[i]));

        for (int i = 0; i < 4; i++) {
            views[i].setOnClickListener(this);
        }

        button.setOnClickListener(view -> NewTimer());

        //NewTimer();
    }

    private void conditions() {
        randomNumber = new Random().nextInt(4);

        for (int i = 0; i < 4; i++)
            views[i].setBackgroundColor(getResources().getColor(colors[i]));

        views[randomNumber].setBackgroundColor(getResources().getColor(R.color.grey));

        for (int i = 0; i < 4; i++)
            clicked[i] = false;
    }

    void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to play again ???");
        builder.setMessage("Game Over\n Your score is: " + scoreCount);
        builder.setNegativeButton("No", (DialogInterface dialogInterface, int i) -> {
            finish();
        });
        builder.setPositiveButton("OK", (DialogInterface dialogInterface, int i) -> {

        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        scoreCount = 0;
        textViewScore.setText("Score: " + scoreCount);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void NewTimer() {
        firstTime = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (firstTime || (clicked[0] || clicked[1] || clicked[2] || clicked[3])) {
                    runOnUiThread(() -> {
                        conditions();
                        firstTime = false;
                    });
                } else {
                    runOnUiThread(() -> {
                        showAlert();
                        timer.cancel();
                    });
                }
            }
        }, 0L, 1000);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == views[randomNumber].getId()) {
            if (!clicked[randomNumber]) {
                scoreCount++;
                textViewScore.setText("Score: " + scoreCount);
                clicked[randomNumber] = true;
            }/* else {
                showAlert();
            }*/
        } else {
            showAlert();
        }
    }

}
