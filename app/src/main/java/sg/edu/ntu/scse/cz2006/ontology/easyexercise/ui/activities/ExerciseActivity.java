package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Location;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.util.SportsImageMatcher;

/**
 * The activity class for staying in an exercise in the checking in task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class ExerciseActivity extends AppCompatActivity {
    private Date startDate;
    private Location location;
    private Sport sport;
    private TextView timerText;
    private Button checkOutButton;
    private Timer timer;
    private Double time = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initButton();
        startTimer();
    }

    private Location getLocation() {
        return (Location) getIntent().getSerializableExtra("ChosenLocation");
    }

    private Sport getSport() {
        return (Sport) getIntent().getSerializableExtra("ChosenSport");
    }

    /**
     * Start timer and change the text for timer accordingly.
     *
     * @author Ruan Donglin
     */
    private void startTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    time++;
                    timerText.setText(getTimerText());
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);
        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        @SuppressLint("DefaultLocale")
        String format = String.format("%02d : %02d : %02d", hours, minutes, seconds);
        return format;
    }

    private void initView() {
        setContentView(R.layout.activity_exercise);
        startDate = new Date();
        location = getLocation();
        sport = getSport();
        ImageView sportView = findViewById(R.id.imageView3);
        timerText = findViewById(R.id.timerText);
        checkOutButton = findViewById(R.id.check_out_button);
        sportView.setImageResource(SportsImageMatcher.getImage(sport));
        timer = new Timer();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initButton() {
        checkOutButton.setOnClickListener(view -> {
            Intent checkInIntent = new Intent(ExerciseActivity.this, CheckOutActivity.class);
            checkInIntent.putExtra("LocationExercise", location);
            checkInIntent.putExtra("SportExercise", sport);
            checkInIntent.putExtra("StartDate", startDate);
            checkInIntent.putExtra("timeDuration", getTimerText());
            checkInIntent.putExtra("EndDate", new Date());
            startActivity(checkInIntent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        moveTaskToBack(true);
        Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
