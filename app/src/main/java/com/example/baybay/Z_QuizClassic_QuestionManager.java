package com.example.baybay;

import java.util.ArrayList;
import java.util.List;

public class Z_QuizClassic_QuestionManager {
    private final List<Question> quiz_questionList;


    public Z_QuizClassic_QuestionManager() {
        quiz_questionList = new ArrayList<>();
        initializeQuestions();
    }

    private void initializeQuestions() {
        //B
        List<String> choices1 = new ArrayList<>();
        choices1.add("b");
        choices1.add("l");
        choices1.add("h");
        choices1.add("y");
        quiz_questionList.add(new Question(R.drawable.quiz_b, choices1, 0));

        List<String> choices2 = new ArrayList<>();
        choices2.add("ya");
        choices2.add("sa");
        choices2.add("ba");
        choices2.add("ka");
        quiz_questionList.add(new Question(R.drawable.quiz_ba, choices2, 2));

        List<String> choices3 = new ArrayList<>();
        choices3.add("re");
        choices3.add("be");
        choices3.add("pe");
        choices3.add("he");
        quiz_questionList.add(new Question(R.drawable.quiz_be, choices3, 1));

        List<String> choices4 = new ArrayList<>();
        choices4.add("ri");
        choices4.add("di");
        choices4.add("hi");
        choices4.add("bi");
        quiz_questionList.add(new Question(R.drawable.quiz_bi, choices4, 3));

        List<String> choices5 = new ArrayList<>();
        choices5.add("bo");
        choices5.add("so");
        choices5.add("no");
        choices5.add("ro");
        quiz_questionList.add(new Question(R.drawable.quiz_bo, choices5, 0));

        List<String> choices6 = new ArrayList<>();
        choices6.add("du");
        choices6.add("mu");
        choices6.add("bu");
        choices6.add("tu");
        quiz_questionList.add(new Question(R.drawable.quiz_bu, choices6, 2));

        //A
        List<String> choices7 = new ArrayList<>();
        choices7.add("t");
        choices7.add("l");
        choices7.add("a");
        choices7.add("b");
        quiz_questionList.add(new Question(R.drawable.quiz_a, choices7, 2));

        // K
        List<String> choices8 = new ArrayList<>();
        choices8.add("e");
        choices8.add("y");
        choices8.add("k");
        choices8.add("w");
        quiz_questionList.add(new Question(R.drawable.quiz_k, choices8, 2));

        List<String> choices9 = new ArrayList<>();
        choices9.add("ye");
        choices9.add("ka");
        choices9.add("ta");
        choices9.add("pa");
        quiz_questionList.add(new Question(R.drawable.quiz_ka, choices9, 1));

        List<String> choices10 = new ArrayList<>();
        choices10.add("ke");
        choices10.add("he");
        choices10.add("de");
        choices10.add("se");
        quiz_questionList.add(new Question(R.drawable.quiz_ke, choices10, 0));

        List<String> choices11 = new ArrayList<>();
        choices11.add("pi");
        choices11.add("ki");
        choices11.add("bi");
        choices11.add("wi");
        quiz_questionList.add(new Question(R.drawable.quiz_ki, choices11, 1));

        List<String> choices12 = new ArrayList<>();
        choices12.add("wo");
        choices12.add("mo");
        choices12.add("ko");
        choices12.add("go");
        quiz_questionList.add(new Question(R.drawable.quiz_ko, choices12, 2));

        List<String> choices13 = new ArrayList<>();
        choices13.add("gu");
        choices13.add("ku");
        choices13.add("yu");
        choices13.add("nu");
        quiz_questionList.add(new Question(R.drawable.quiz_ku, choices13, 1));

// D
        List<String> choices14 = new ArrayList<>();
        choices14.add("r");
        choices14.add("t");
        choices14.add("y");
        choices14.add("d");
        quiz_questionList.add(new Question(R.drawable.quiz_d, choices14, 3));

        List<String> choices15 = new ArrayList<>();
        choices15.add("da");
        choices15.add("ka");
        choices15.add("sa");
        choices15.add("ra");
        quiz_questionList.add(new Question(R.drawable.quiz_da, choices15, 0));

        List<String> choices16 = new ArrayList<>();
        choices16.add("de");
        choices16.add("he");
        choices16.add("ye");
        choices16.add("we");
        quiz_questionList.add(new Question(R.drawable.quiz_de, choices16, 0));

        List<String> choices17 = new ArrayList<>();
        choices17.add("pi");
        choices17.add("di");
        choices17.add("li");
        choices17.add("ri");
        quiz_questionList.add(new Question(R.drawable.quiz_di, choices17, 1));

        List<String> choices18 = new ArrayList<>();
        choices18.add("so");
        choices18.add("ro");
        choices18.add("yo");
        choices18.add("do");
        quiz_questionList.add(new Question(R.drawable.quiz_do, choices18, 3));

        List<String> choices19 = new ArrayList<>();
        choices19.add("mu");
        choices19.add("du");
        choices19.add("pu");
        choices19.add("tu");
        quiz_questionList.add(new Question(R.drawable.quiz_du, choices19, 1));

// E
        List<String> choices20 = new ArrayList<>();
        choices20.add("b");
        choices20.add("y");
        choices20.add("e");
        choices20.add("p");
        quiz_questionList.add(new Question(R.drawable.quiz_e, choices20, 2));

// G
        List<String> choices21 = new ArrayList<>();
        choices21.add("g");
        choices21.add("s");
        choices21.add("n");
        choices21.add("a");
        quiz_questionList.add(new Question(R.drawable.quiz_g, choices21, 0));

        List<String> choices22 = new ArrayList<>();
        choices22.add("da");
        choices22.add("ga");
        choices22.add("sa");
        choices22.add("ba");
        quiz_questionList.add(new Question(R.drawable.quiz_ga, choices22, 1));

        List<String> choices23 = new ArrayList<>();
        choices23.add("ne");
        choices23.add("we");
        choices23.add("re");
        choices23.add("ge");
        quiz_questionList.add(new Question(R.drawable.quiz_ge, choices23, 3));

        List<String> choices24 = new ArrayList<>();
        choices24.add("yi");
        choices24.add("gi");
        choices24.add("mi");
        choices24.add("si");
        quiz_questionList.add(new Question(R.drawable.quiz_gi, choices24, 1));

        List<String> choices25 = new ArrayList<>();
        choices25.add("go");
        choices25.add("to");
        choices25.add("do");
        choices25.add("wo");
        quiz_questionList.add(new Question(R.drawable.quiz_go, choices25, 0));

        List<String> choices26 = new ArrayList<>();
        choices26.add("su");
        choices26.add("ru");
        choices26.add("wu");
        choices26.add("gu");
        quiz_questionList.add(new Question(R.drawable.quiz_gu, choices26, 3));

// H
        List<String> choices27 = new ArrayList<>();
        choices27.add("l");
        choices27.add("p");
        choices27.add("h");
        choices27.add("o");
        quiz_questionList.add(new Question(R.drawable.quiz_h, choices27, 2));

        List<String> choices28 = new ArrayList<>();
        choices28.add("ga");
        choices28.add("sa");
        choices28.add("ha");
        choices28.add("ka");
        quiz_questionList.add(new Question(R.drawable.quiz_ha, choices28, 2));

        List<String> choices29 = new ArrayList<>();
        choices29.add("te");
        choices29.add("me");
        choices29.add("ye");
        choices29.add("he");
        quiz_questionList.add(new Question(R.drawable.quiz_he, choices29, 3));

        List<String> choices30 = new ArrayList<>();
        choices30.add("gi");
        choices30.add("hi");
        choices30.add("si");
        choices30.add("wi");
        quiz_questionList.add(new Question(R.drawable.quiz_hi, choices30, 1));

        List<String> choices31 = new ArrayList<>();
        choices31.add("ho");
        choices31.add("wo");
        choices31.add("po");
        choices31.add("do");
        quiz_questionList.add(new Question(R.drawable.quiz_ho, choices31, 0));

        List<String> choices32 = new ArrayList<>();
        choices32.add("bu");
        choices32.add("hu");
        choices32.add("su");
        choices32.add("wu");
        quiz_questionList.add(new Question(R.drawable.quiz_hu, choices32, 1));

// I
        List<String> choices33 = new ArrayList<>();
        choices33.add("a");
        choices33.add("e");
        choices33.add("d");
        choices33.add("i");
        quiz_questionList.add(new Question(R.drawable.quiz_i, choices33, 3));

// L
        List<String> choices34 = new ArrayList<>();
        choices34.add("l"); //0
        choices34.add("m"); //1
        choices34.add("n"); //2
        choices34.add("o"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_l, choices34, 0));

        List<String> choices35 = new ArrayList<>();
        choices35.add("ga"); //0
        choices35.add("la"); //1
        choices35.add("ha"); //2
        choices35.add("na"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_la, choices35, 1));

        List<String> choices36 = new ArrayList<>();
        choices36.add("he"); //0
        choices36.add("ne"); //1
        choices36.add("le"); //2
        choices36.add("me"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_le, choices36, 2));

        List<String> choices37 = new ArrayList<>();
        choices37.add("ni"); //0
        choices37.add("mi"); //1
        choices37.add("ki"); //2
        choices37.add("li"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_li, choices37, 3));

        List<String> choices38 = new ArrayList<>();
        choices38.add("lo"); //0
        choices38.add("go"); //1
        choices38.add("ko"); //2
        choices38.add("mo"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_lo, choices38, 0));


        List<String> choices39 = new ArrayList<>();
        choices39.add("ku"); //0
        choices39.add("nu"); //1
        choices39.add("lu"); //2
        choices39.add("mu"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_lu, choices39, 2));

// M
        List<String> choices40 = new ArrayList<>();
        choices40.add("m"); //0
        choices40.add("j"); //1
        choices40.add("g"); //2
        choices40.add("p"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_m, choices40, 0));

        List<String> choices41 = new ArrayList<>();
        choices41.add("ga"); //0
        choices41.add("ma"); //1
        choices41.add("ha"); //2
        choices41.add("la"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ma, choices41, 1));

        List<String> choices42 = new ArrayList<>();
        choices42.add("re"); //0
        choices42.add("we"); //1
        choices42.add("me"); //2
        choices42.add("be"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_me, choices42, 2));

        List<String> choices43 = new ArrayList<>();
        choices43.add("ni"); //0
        choices43.add("li"); //1
        choices43.add("ki"); //2
        choices43.add("mi"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_mi, choices43, 3));

        List<String> choices44 = new ArrayList<>();
        choices44.add("mo"); //0
        choices44.add("ho"); //1
        choices44.add("lo"); //2
        choices44.add("go"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_mo, choices44, 0));


        List<String> choices45 = new ArrayList<>();
        choices45.add("yu"); //0
        choices45.add("hu"); //1
        choices45.add("mu"); //2
        choices45.add("ku"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_mu, choices45, 2));

// N
        List<String> choices46 = new ArrayList<>();
        choices46.add("n"); //0
        choices46.add("i"); //1
        choices46.add("y"); //2
        choices46.add("t"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_n, choices46, 0));

        List<String> choices47 = new ArrayList<>();
        choices47.add("ta"); //0
        choices47.add("na"); //1
        choices47.add("ra"); //2
        choices47.add("ha"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_na, choices47, 1));

        List<String> choices48 = new ArrayList<>();
        choices48.add("ke"); //0
        choices48.add("me"); //1
        choices48.add("ne"); //2
        choices48.add("be"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ne, choices48, 2));

        List<String> choices49 = new ArrayList<>();
        choices49.add("pi"); //0
        choices49.add("ki"); //1
        choices49.add("mi"); //2
        choices49.add("ni"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ni, choices49, 3));

        List<String> choices50 = new ArrayList<>();
        choices50.add("no"); //0
        choices50.add("ko"); //1
        choices50.add("do"); //2
        choices50.add("lo"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_no, choices50, 0));


        List<String> choices51 = new ArrayList<>();
        choices51.add("mu"); //0
        choices51.add("pu"); //1
        choices51.add("nu"); //2
        choices51.add("ku"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_nu, choices51, 2));

// NG
        List<String> choices52 = new ArrayList<>();
        choices52.add("ng"); //0
        choices52.add("n"); //1
        choices52.add("g"); //2
        choices52.add("k"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ng, choices52, 0));

        List<String> choices53 = new ArrayList<>();
        choices53.add("ba"); //0
        choices53.add("nga"); //1
        choices53.add("ha"); //2
        choices53.add("la"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_nga, choices53, 1));

        List<String> choices54 = new ArrayList<>();
        choices54.add("ge"); //0
        choices54.add("ne"); //1
        choices54.add("nge"); //2
        choices54.add("de"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_nge, choices54, 2));

        List<String> choices55 = new ArrayList<>();
        choices55.add("ni"); //0
        choices55.add("ri"); //1
        choices55.add("si"); //2
        choices55.add("ngi"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ngi, choices55, 3));

        List<String> choices56 = new ArrayList<>();
        choices56.add("ngo"); //0
        choices56.add("so"); //1
        choices56.add("lo"); //2
        choices56.add("po"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ngo, choices56, 0));


        List<String> choices57 = new ArrayList<>();
        choices57.add("ku"); //0
        choices57.add("gu"); //1
        choices57.add("ngu"); //2
        choices57.add("hu"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ngu, choices57, 2));

// O
        List<String> choices58 = new ArrayList<>();
        choices58.add("o"); //0
        choices58.add("k"); //1
        choices58.add("l"); //2
        choices58.add("m"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_o, choices58, 0));

// P
        List<String> choices59 = new ArrayList<>();
        choices59.add("p"); //0
        choices59.add("k"); //1
        choices59.add("l"); //2
        choices59.add("m"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_p, choices59, 0));

        List<String> choices60 = new ArrayList<>();
        choices60.add("ma"); //0
        choices60.add("pa"); //1
        choices60.add("la"); //2
        choices60.add("ha"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_pa, choices60, 1));

        List<String> choices61 = new ArrayList<>();
        choices61.add("me"); //0
        choices61.add("le"); //1
        choices61.add("pe"); //2
        choices61.add("ne"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_pe, choices61, 2));

        List<String> choices62 = new ArrayList<>();
        choices62.add("bi"); //0
        choices62.add("ki"); //1
        choices62.add("mi"); //2
        choices62.add("pi"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_pi, choices62, 3));

        List<String> choices63 = new ArrayList<>();
        choices63.add("po"); //0
        choices63.add("ko"); //1
        choices63.add("lo"); //2
        choices63.add("bo"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_po, choices63, 0));

        List<String> choices64 = new ArrayList<>();
        choices64.add("ru"); //0
        choices64.add("gu"); //1
        choices64.add("pu"); //2
        choices64.add("nu"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_pu, choices64, 2));

        // R
        List<String> choices65 = new ArrayList<>();
        choices65.add("l"); //0
        choices65.add("g"); //1
        choices65.add("r"); //2
        choices65.add("s"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_r, choices65, 2));

        List<String> choices66 = new ArrayList<>();
        choices66.add("wa"); //0
        choices66.add("ra"); //1
        choices66.add("ha"); //2
        choices66.add("da"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ra, choices66, 1));

        List<String> choices67 = new ArrayList<>();
        choices67.add("pe"); //0
        choices67.add("he"); //1
        choices67.add("be"); //2
        choices67.add("re"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_re, choices67, 3));

        List<String> choices68 = new ArrayList<>();
        choices68.add("gi"); //0
        choices68.add("ti"); //1
        choices68.add("ri"); //2
        choices68.add("wi"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ri, choices68, 2));

        List<String> choices69 = new ArrayList<>();
        choices69.add("ro"); //0
        choices69.add("go"); //1
        choices69.add("no"); //2
        choices69.add("wo"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ro, choices69, 0));

        List<String> choices70 = new ArrayList<>();
        choices70.add("bu"); //0
        choices70.add("su"); //1
        choices70.add("ru"); //2
        choices70.add("pu"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ru, choices70, 2));

// S
        List<String> choices71 = new ArrayList<>();
        choices71.add("w"); //0
        choices71.add("e"); //1
        choices71.add("r"); //2
        choices71.add("s"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_s, choices71, 3));

        List<String> choices72 = new ArrayList<>();
        choices72.add("ga"); //0
        choices72.add("sa"); //1
        choices72.add("ha"); //2
        choices72.add("ka"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_sa, choices72, 1));

        List<String> choices73 = new ArrayList<>();
        choices73.add("se"); //0
        choices73.add("we"); //1
        choices73.add("te"); //2
        choices73.add("ye"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_se, choices73, 0));

        List<String> choices74 = new ArrayList<>();
        choices74.add("gi"); //0
        choices74.add("hi"); //1
        choices74.add("si"); //2
        choices74.add("ki"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_si, choices74, 2));

        List<String> choices75 = new ArrayList<>();
        choices75.add("so"); //0
        choices75.add("bo"); //1
        choices75.add("no"); //2
        choices75.add("mo"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_so, choices75, 0));

        List<String> choices76 = new ArrayList<>();
        choices76.add("pu"); //0
        choices76.add("su"); //1
        choices76.add("yu"); //2
        choices76.add("wu"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_su, choices76, 1));

// T
        List<String> choices77 = new ArrayList<>();
        choices77.add("s"); //0
        choices77.add("g"); //1
        choices77.add("e"); //2
        choices77.add("t"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_t, choices77, 3));

        List<String> choices78 = new ArrayList<>();
        choices78.add("sa"); //0
        choices78.add("ta"); //1
        choices78.add("da"); //2
        choices78.add("ha"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ta, choices78, 1));

        List<String> choices79 = new ArrayList<>();
        choices79.add("he"); //0
        choices79.add("ke"); //1
        choices79.add("le"); //2
        choices79.add("te"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_te, choices79, 3));

        List<String> choices80 = new ArrayList<>();
        choices80.add("ti"); //0
        choices80.add("ri"); //1
        choices80.add("pi"); //2
        choices80.add("bi"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ti, choices80, 0));

        List<String> choices81 = new ArrayList<>();
        choices81.add("go"); //0
        choices81.add("so"); //1
        choices81.add("to"); //2
        choices81.add("wo"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_to, choices81, 2));

        List<String> choices82 = new ArrayList<>();
        choices82.add("tu"); //0
        choices82.add("mu"); //1
        choices82.add("pu"); //2
        choices82.add("yu"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_tu, choices82, 0));

// U
        List<String> choices83 = new ArrayList<>();
        choices83.add("h"); //0
        choices83.add("l"); //1
        choices83.add("u"); //2
        choices83.add("b"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_u, choices83, 2));

// W
        List<String> choices84 = new ArrayList<>();
        choices84.add("w"); //0
        choices84.add("g"); //1
        choices84.add("d"); //2
        choices84.add("k"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_w, choices84, 0));

        List<String> choices85 = new ArrayList<>();
        choices85.add("pa"); //0
        choices85.add("wa"); //1
        choices85.add("sa"); //2
        choices85.add("da"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_wa, choices85, 1));

        List<String> choices86 = new ArrayList<>();
        choices86.add("ge"); //0
        choices86.add("ye"); //1
        choices86.add("we"); //2
        choices86.add("ne"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_we, choices86, 2));

        List<String> choices87 = new ArrayList<>();
        choices87.add("mi"); //0
        choices87.add("di"); //1
        choices87.add("ri"); //2
        choices87.add("wi"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_wi, choices87, 3));

        List<String> choices88 = new ArrayList<>();
        choices88.add("ko"); //0
        choices88.add("so"); //1
        choices88.add("wo"); //2
        choices88.add("ro"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_wo, choices88, 2));

        List<String> choices89 = new ArrayList<>();
        choices89.add("wu"); //0
        choices89.add("yu"); //1
        choices89.add("pu"); //2
        choices89.add("du"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_wu, choices89, 0));

// Y
        List<String> choices90 = new ArrayList<>();
        choices90.add("k"); //0
        choices90.add("s"); //1
        choices90.add("t"); //2
        choices90.add("y"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_y, choices90, 3));

        List<String> choices91 = new ArrayList<>();
        choices91.add("ya"); //0
        choices91.add("ka"); //1
        choices91.add("ha"); //2
        choices91.add("ba"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ya, choices91, 0));

        List<String> choices92 = new ArrayList<>();
        choices92.add("te"); //0
        choices92.add("ye"); //1
        choices92.add("ke"); //2
        choices92.add("se"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_ye, choices92, 1));

        List<String> choices93 = new ArrayList<>();
        choices93.add("li"); //0
        choices93.add("ri"); //1
        choices93.add("yi"); //2
        choices93.add("pi"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_yi, choices93, 2));

        List<String> choices94 = new ArrayList<>();
        choices94.add("yo"); //0
        choices94.add("go"); //1
        choices94.add("bo"); //2
        choices94.add("ro"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_yo, choices94, 0));

        List<String> choices95 = new ArrayList<>();
        choices95.add("hu"); //0
        choices95.add("lu"); //1
        choices95.add("ru"); //2
        choices95.add("yu"); //3
        quiz_questionList.add(new Question(R.drawable.quiz_yu, choices95, 3));

        // Add more questions here...
    }

    public List<Question> getQuiz_questionList() {
        return quiz_questionList;
    }

    public static class Question {
        private final int questionImage;
        private final List<String> choices;
        private final int answerIndex;

        public Question(int questionImage, List<String> choices, int answerIndex) {
            this.questionImage = questionImage;
            this.choices = choices;
            this.answerIndex = answerIndex;
        }

        public int getQuestionImage() {
            return questionImage;
        }

        public List<String> getChoices() {
            return choices;
        }

        public int getAnswerIndex() {
            return answerIndex;
        }
    }
}
