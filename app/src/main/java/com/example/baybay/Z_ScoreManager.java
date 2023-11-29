package com.example.baybay;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Z_ScoreManager {
    private static final String PREF_NAME = "MyPrefs";
    private static final String QUIZ_SCORE_LIST_KEY = "quiz_score_list";
    private static final String MATCH_SCORE_LIST_KEY = "match_score_list";
    private static final String SPELL_SCORE_LIST_KEY = "spell_score_list";

    private static Z_ScoreManager instance;
    private SharedPreferences sharedPreferences;
    private List<Integer> Quizscorelist;
    private List<Integer> Matchscorelist;
    private List<Integer> Spellscorelist;

    private Z_ScoreManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Quizscorelist = loadScoreList(QUIZ_SCORE_LIST_KEY);
        Matchscorelist = loadScoreList(MATCH_SCORE_LIST_KEY);
        Spellscorelist = loadScoreList(SPELL_SCORE_LIST_KEY);
    }

    public static Z_ScoreManager getInstance(Context context) {
        if (instance == null) {
            instance = new Z_ScoreManager(context);
        }
        return instance;
    }

    public List<Integer> getQuizScoreList() {
        return Quizscorelist;
    }

    public List<Integer> getMatchScoreList() {
        return Matchscorelist;
    }
    public List<Integer> getSpellScoreList() {
        return Spellscorelist;
    }

    public void addItemToQuizScoreList(int newItem) {
        Quizscorelist.add(newItem);
        saveScoreList(QUIZ_SCORE_LIST_KEY, Quizscorelist);
    }

    public void addItemToMatchScoreList(int newItem) {
        Matchscorelist.add(newItem);
        saveScoreList(MATCH_SCORE_LIST_KEY, Matchscorelist);
    }

    public void addItemToSpellScoreList(int newItem) {
        Spellscorelist.add(newItem);
        saveScoreList(SPELL_SCORE_LIST_KEY, Spellscorelist);
    }

    private void saveScoreList(String key, List<Integer> scoreList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, TextUtils.join(",", scoreList));
        editor.apply(); // Use apply() instead of commit()
    }

    private List<Integer> loadScoreList(String key) {
        String scoreListString = sharedPreferences.getString(key, "");
        List<Integer> loadedList = new ArrayList<>();
        if (!scoreListString.isEmpty()) {
            String[] scoreArray = scoreListString.split(",");
            for (String score : scoreArray) {
                loadedList.add(Integer.parseInt(score));
            }
        }
        return loadedList;
    }

    public void clearQuizScoreList() {
        Quizscorelist.clear();
        saveScoreList(QUIZ_SCORE_LIST_KEY, Quizscorelist);
    }


    public void clearMatchScoreList() {
        Matchscorelist.clear();
        saveScoreList(MATCH_SCORE_LIST_KEY, Matchscorelist);
    }

    public void clearSpellScoreList() {
        Spellscorelist.clear();
        saveScoreList(SPELL_SCORE_LIST_KEY, Spellscorelist);
    }

}
