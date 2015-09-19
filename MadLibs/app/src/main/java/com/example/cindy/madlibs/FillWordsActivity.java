package com.example.cindy.madlibs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.util.Random;

public class FillWordsActivity extends Activity {

    Story story;
    private TextView words_left;
    private TextView word_type;
    private EditText word_hint;
    private String type;

    private Intent toStoryActivity;
    private InputStream stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_words);



        Random rand = new Random();
        int  n = rand.nextInt(4) + 1;
        //4 is the maximum and the 1 is our minimum

        switch (n) {
            case 1: stream = this.getResources().openRawResource(R.raw.madlib2_university);
                break;
            case 2: stream = this.getResources().openRawResource(R.raw.madlib1_tarzan);
                break;
            case 3: stream = this.getResources().openRawResource(R.raw.madlib3_clothes);
                break;
            case 4: stream = this.getResources().openRawResource(R.raw.madlib4_dance);
                break;
            default: stream = this.getResources().openRawResource(R.raw.madlib0_simple);
                break;
        }

        story = new Story();
        story.read(stream);


        int count = story.getPlaceholderRemainingCount();
        type = story.getNextPlaceholder();

        words_left = (TextView) findViewById(R.id.words_left);
        words_left.setText(count + " word(s) left");

        word_type = (TextView) findViewById(R.id.word_type);
        word_type.setText("please type a/an " + type);

        word_hint = (EditText) findViewById(R.id.word_hint);
        word_hint.setHint(type);

        toStoryActivity = new Intent(this, StoryActivity.class);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fill_words, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fillWordActivity(View view) {

        String word = String.valueOf(word_hint.getText());
        story.fillInPlaceholder(word.toUpperCase());

        if(!story.isFilledIn()) {
            int count = story.getPlaceholderRemainingCount();
            words_left.setText(count + " word(s) left");

            type = story.getNextPlaceholder();
            word_type.setText("please type a/an " + type);
            word_hint.setHint(type);
            word_hint.setText("");
        } else {
            String text_story = story.toString();
            toStoryActivity.putExtra("story", text_story);
            startActivity(toStoryActivity);
        }

    }
}
