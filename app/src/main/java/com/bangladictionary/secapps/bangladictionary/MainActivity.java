package com.bangladictionary.secapps.bangladictionary;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView searchText;
    TextView translationText,synonymsText,antonymsTest,similaWordText;
    CardView translationCardView,synonymsCardView,antonymsCardView,similarWordCardView;
    String word,selectedWord;
    Button btnSearch;

    ArrayAdapter<String> adapter;
    List<String> allWords;
    boolean firstClick=true;

    DatabaseAccess databaseAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchText = (AutoCompleteTextView) findViewById(R.id.searchAutocomplete);
        translationText = (TextView) findViewById(R.id.translationText);
        synonymsText = (TextView) findViewById(R.id.tvSynonims);
        antonymsTest = (TextView) findViewById(R.id.tvAntonyms);
        similaWordText =(TextView) findViewById(R.id.tvSimilarWord);

        translationCardView  = (CardView) findViewById(R.id.translation_card_view);
        synonymsCardView = (CardView) findViewById(R.id.synonims_card_view);
        antonymsCardView = (CardView) findViewById(R.id.antonyms_card_view);
        similarWordCardView = (CardView) findViewById(R.id.similarWord_card_view);
        btnSearch=(Button)findViewById(R.id.btnSearch);

        databaseAccess = DatabaseAccess.getInstance(this);
        searchText.addTextChangedListener(textWatcher);

        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                firstClick=false;
                Log.e("size",""+allWords.size());
                Log.d("position", "" + position);
                btnSearch.performClick();
//                translationCardView.setVisibility(view.VISIBLE);
//                selectedWord = parent.getItemAtPosition(position).toString();
//                selectedWord = adapter.getItem(position-1);
//                Log.e("Selected Word", selectedWord);
//                Toast.makeText(getApplicationContext(), selectedWord, Toast.LENGTH_SHORT).show();
//                databaseAccess.open();
//                final String translatedWord = databaseAccess.getMeaning(selectedWord);
//                databaseAccess.close();
//                translationText.setText(translatedWord);
                hideSoftKeyboard(view);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translationCardView.setVisibility(v.VISIBLE);
                synonymsCardView.setVisibility(v.VISIBLE);
                antonymsCardView.setVisibility(v.VISIBLE);
                similarWordCardView.setVisibility(v.VISIBLE);

                selectedWord = searchText.getText().toString();
                Log.e("Selected Word", selectedWord);
                databaseAccess.open();
                final String translatedWord = databaseAccess.getMeaning(selectedWord);
                final String synonyms = databaseAccess.getSynonimId(selectedWord);
                final String antonyms = databaseAccess.getAntonymsID(selectedWord);
                final String similar_word = databaseAccess.getSimilarWordId(selectedWord);
                databaseAccess.close();
                translationText.setText(translatedWord);
                //synonymsText.setText(synonyms);
                Log.e("synonyms", synonyms);

                getSynonyms(synonyms);
                getAntonyms(antonyms);
                getSimilarWord(similar_word);

                hideKeyboard(v);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            firstClick=true;

        }

        @Override
        public void afterTextChanged(Editable editable) {

            if (searchText.getText().toString().length() > 0 && firstClick) {
                Log.e("word", searchText.getText().toString());
                wordSequence(searchText.getText().toString());
            }
        }
    };


    public void wordSequence(String word)
    {
        databaseAccess.open();
        allWords=new ArrayList<>();
        allWords = databaseAccess.getWords(word);
        databaseAccess.close();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, allWords);
        searchText.setAdapter(adapter);
        Log.e("size", "" + allWords.size());
        //searchText.showDropDown();
    }

    public void getSynonyms(String synonyms)
    {
        StringTokenizer st = new StringTokenizer(synonyms,",");

        String total_synonyms="";

        while (st.hasMoreElements()) {
            String s;
            s = st.nextToken();

            databaseAccess.open();
            String token = databaseAccess.getSynonymWord(s);
            databaseAccess.close();

            if(token==null)
                break;

            total_synonyms+=token+", ";
            Log.e("token",s);
        }
        Log.e("token_m", total_synonyms);

        synonymsText.setText(total_synonyms);
    }

    public void getAntonyms(String antonyms)
    {
        StringTokenizer st = new StringTokenizer(antonyms,",");

        String total_antonyms="";

        while (st.hasMoreElements()) {
            String s;
            s = st.nextToken();

            databaseAccess.open();
            String token = databaseAccess.getSynonymWord(s);
            databaseAccess.close();

            if(token==null)
                break;

            total_antonyms+=token+", ";
            Log.e("token",s);
        }
        Log.e("token_m", total_antonyms);

        antonymsTest.setText(total_antonyms);
    }

    public void getSimilarWord(String similarWord)
    {
        StringTokenizer st = new StringTokenizer(similarWord,",");

        String total_similarWord="";

        while (st.hasMoreElements()) {
            String s;
            s = st.nextToken();

            databaseAccess.open();
            String token = databaseAccess.getSynonymWord(s);
            databaseAccess.close();

            if(token==null)
                break;

            total_similarWord+=token+", ";
            Log.e("token",s);
        }
        Log.e("token_m", total_similarWord);

        similaWordText.setText(total_similarWord);
    }

    public void hideKeyboard(View view) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void hideSoftKeyboard(View view) {
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
