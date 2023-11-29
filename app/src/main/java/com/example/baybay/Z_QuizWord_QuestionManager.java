package com.example.baybay;

import java.util.ArrayList;
import java.util.List;

public class Z_QuizWord_QuestionManager {

    private final List<Question> quizword_questionList;

    public Z_QuizWord_QuestionManager() {
        quizword_questionList = new ArrayList<>();
        initializeQuestions();
    }

    private void initializeQuestions() {

        //araw
        List<String> choices1 = new ArrayList<>();
        choices1.add("anak");
        choices1.add("apoy");
        choices1.add("araw");
        choices1.add("anim");
        quizword_questionList.add(new Question(R.drawable.quizword_araw, choices1, 2));

        //bata
        List<String> choices2 = new ArrayList<>();
        choices2.add("bata");
        choices2.add("buto");
        choices2.add("biro");
        choices2.add("bibe");
        quizword_questionList.add(new Question(R.drawable.quizword_bata, choices2, 0));

        //kuya
        List<String> choices3 = new ArrayList<>();
        choices3.add("kapa");
        choices3.add("kuya");
        choices3.add("keso");
        choices3.add("kama");
        quizword_questionList.add(new Question(R.drawable.quizword_kuya, choices3, 1));

        //dilaw
        List<String> choices4 = new ArrayList<>();
        choices4.add("dilag");
        choices4.add("dahon");
        choices4.add("durog");
        choices4.add("dilaw");
        quizword_questionList.add(new Question(R.drawable.quizword_dilaw, choices4, 3));

        //elisi
        List<String> choices5 = new ArrayList<>();
        choices5.add("edad");
        choices5.add("elisi");
        choices5.add("eden");
        choices5.add("edisyon");
        quizword_questionList.add(new Question(R.drawable.quizword_elisi, choices5, 1));

        //gulo
        List<String> choices6 = new ArrayList<>();
        choices6.add("gulo");
        choices6.add("galit");
        choices6.add("ganda");
        choices6.add("gawa");
        quizword_questionList.add(new Question(R.drawable.quizword_gulo, choices6, 0));

        //hayop
        List<String> choices7 = new ArrayList<>();
        choices7.add("hipan");
        choices7.add("hirap");
        choices7.add("hayop");
        choices7.add("husga");
        quizword_questionList.add(new Question(R.drawable.quizword_hayop, choices7, 2));

        //ilaw
        List<String> choices8 = new ArrayList<>();
        choices8.add("inip");
        choices8.add("isip");
        choices8.add("isda");
        choices8.add("ilaw");
        quizword_questionList.add(new Question(R.drawable.quizword_ilaw, choices8, 3));

        //lakad
        List<String> choices9 = new ArrayList<>();
        choices9.add("lipad");
        choices9.add("lusot");
        choices9.add("lapis");
        choices9.add("lakad");
        quizword_questionList.add(new Question(R.drawable.quizword_lakad, choices9, 3));

        //manok
        List<String> choices10 = new ArrayList<>();
        choices10.add("minsan");
        choices10.add("meron");
        choices10.add("manok");
        choices10.add("mundo");
        quizword_questionList.add(new Question(R.drawable.quizword_manok, choices10, 2));

        //nanay
        List<String> choice11 = new ArrayList<>();
        choice11.add("nanay");
        choice11.add("nobena");
        choice11.add("ninang");
        choice11.add("nawala");
        quizword_questionList.add(new Question(R.drawable.quizword_nanay, choice11, 0));

        //ngiti
        List<String> choices12 = new ArrayList<>();
        choices12.add("ngayon");
        choices12.add("ngiti");
        choices12.add("ngunit");
        choices12.add("ngilo");
        quizword_questionList.add(new Question(R.drawable.quizword_ngiti, choices12, 1));

        //oras
        List<String> choices13 = new ArrayList<>();
        choices13.add("oras");
        choices13.add("okra");
        choices13.add("okasyon");
        choices13.add("opera");
        quizword_questionList.add(new Question(R.drawable.quizword_oras, choices13, 0));

        //pito
        List<String> choices14 = new ArrayList<>();
        choices14.add("pinto");
        choices14.add("plato");
        choices14.add("pito");
        choices14.add("isara");
        quizword_questionList.add(new Question(R.drawable.quizword_pito, choices14, 2));

        //rosas
        List<String> choices15 = new ArrayList<>();
        choices15.add("rason");
        choices15.add("rinig");
        choices15.add("ritaso");
        choices15.add("rosas");
        quizword_questionList.add(new Question(R.drawable.quizword_rosas, choices15, 3));

        //sabaw
        List<String> choices16 = new ArrayList<>();
        choices16.add("sisiw");
        choices16.add("suman");
        choices16.add("sabaw");
        choices16.add("sipag");
        quizword_questionList.add(new Question(R.drawable.quizword_sabaw, choices16, 2));

        //takbo
        List<String> choices17 = new ArrayList<>();
        choices17.add("talon");
        choices17.add("tula");
        choices17.add("tinda");
        choices17.add("takbo");
        quizword_questionList.add(new Question(R.drawable.quizword_takbo, choices17, 3));

        //usa
        List<String> choices18 = new ArrayList<>();
        choices18.add("uhaw");
        choices18.add("ukit");
        choices18.add("usa");
        choices18.add("umaga");
        quizword_questionList.add(new Question(R.drawable.quizword_usa, choices18, 2));

        //walo
        List<String> choices19 = new ArrayList<>();
        choices19.add("walo");
        choices19.add("wika");
        choices19.add("wakas");
        choices19.add("wala");
        quizword_questionList.add(new Question(R.drawable.quizword_walo, choices19, 0));

        //yelo
        List<String> choices20 = new ArrayList<>();
        choices20.add("yelo");
        choices20.add("yero");
        choices20.add("yakap");
        choices20.add("yema");
        quizword_questionList.add(new Question(R.drawable.quizword_yelo, choices20, 0));

        //aso
        List<String> choices21 = new ArrayList<>();
        choices21.add("aso");
        choices21.add("abo");
        choices21.add("ako");
        choices21.add("apo");
        quizword_questionList.add(new Question(R.drawable.quizword_aso, choices21, 0));

        //buwan
        List<String> choices22 = new ArrayList<>();
        choices22.add("buwan");
        choices22.add("bukal");
        choices22.add("basag");
        choices22.add("bitag");
        quizword_questionList.add(new Question(R.drawable.quizword_buwan, choices22, 0));

        //kaliwa
        List<String> choices23 = new ArrayList<>();
        choices23.add("kasal");
        choices23.add("kaliwa");
        choices23.add("kumot");
        choices23.add("kislap");
        quizword_questionList.add(new Question(R.drawable.quizword_kaliwa, choices23, 1));

        //damit
        List<String> choices24 = new ArrayList<>();
        choices24.add("duhat");
        choices24.add("daliri");
        choices24.add("dikta");
        choices24.add("damit");
        quizword_questionList.add(new Question(R.drawable.quizword_damit, choices24, 3));

        //ekis
        List<String> choices25 = new ArrayList<>();
        choices25.add("estero");
        choices25.add("epekto");
        choices25.add("ekis");
        choices25.add("epiko");
        quizword_questionList.add(new Question(R.drawable.quizword_ekis, choices25, 2));

        //gamit
        List<String> choices26 = new ArrayList<>();
        choices26.add("gupit");
        choices26.add("guhit");
        choices26.add("galaw");
        choices26.add("gamit");
        quizword_questionList.add(new Question(R.drawable.quizword_gamit, choices26, 3));

        //hamon
        List<String> choices27 = new ArrayList<>();
        choices27.add("huli");
        choices27.add("hindi");
        choices27.add("hamon");
        choices27.add("hipon");
        quizword_questionList.add(new Question(R.drawable.quizword_hamon, choices27, 2));

        //ilog
        List<String> choices28 = new ArrayList<>();
        choices28.add("ilog");
        choices28.add("iwan");
        choices28.add("ikaw");
        choices28.add("itlog");
        quizword_questionList.add(new Question(R.drawable.quizword_ilog, choices28, 0));

        //likas
        List<String> choices29 = new ArrayList<>();
        choices29.add("lupa");
        choices29.add("likas");
        choices29.add("laso");
        choices29.add("lugar");
        quizword_questionList.add(new Question(R.drawable.quizword_likas, choices29, 1));

        //mesa
        List<String> choices30 = new ArrayList<>();
        choices30.add("mesa");
        choices30.add("moral");
        choices30.add("munti");
        choices30.add("mali");
        quizword_questionList.add(new Question(R.drawable.quizword_mesa, choices30, 0));

        //niyog
        List<String> choices31 = new ArrayList<>();
        choices31.add("nyebe");
        choices31.add("nais");
        choices31.add("niyog");
        choices31.add("noon");
        quizword_questionList.add(new Question(R.drawable.quizword_niyog, choices31, 2));

        //nguso
        List<String> choices32 = new ArrayList<>();
        choices32.add("ngaipib");
        choices32.add("nguya");
        choices32.add("ngisi");
        choices32.add("nguso");
        quizword_questionList.add(new Question(R.drawable.quizword_nguso, choices32, 3));

        //oso
        List<String> choices33 = new ArrayList<>();
        choices33.add("ostya");
        choices33.add("opisina");
        choices33.add("opal");
        choices33.add("oso");
        quizword_questionList.add(new Question(R.drawable.quizword_oso, choices33, 3));

        //pato
        List<String> choices34 = new ArrayList<>();
        choices34.add("puro");
        choices34.add("puno");
        choices34.add("pato");
        choices34.add("pala");
        quizword_questionList.add(new Question(R.drawable.quizword_pato, choices34, 2));

        //relo
        List<String> choices35 = new ArrayList<>();
        choices35.add("relo");
        choices35.add("radyo");
        choices35.add("regalo");
        choices35.add("reyna");
        quizword_questionList.add(new Question(R.drawable.quizword_relo, choices35, 0));

        //sopas
        List<String> choices36 = new ArrayList<>();
        choices36.add("sulit");
        choices36.add("sopas");
        choices36.add("sapa");
        choices36.add("sikat");
        quizword_questionList.add(new Question(R.drawable.quizword_sopas, choices36, 1));

        //tulay
        List<String> choices37 = new ArrayList<>();
        choices37.add("talim");
        choices37.add("tila");
        choices37.add("tupa");
        choices37.add("tulay");
        quizword_questionList.add(new Question(R.drawable.quizword_tulay, choices37, 3));

        //una
        List<String> choices38 = new ArrayList<>();
        choices38.add("upa");
        choices38.add("una");
        choices38.add("uwi");
        choices38.add("ube");
        quizword_questionList.add(new Question(R.drawable.quizword_una, choices38, 1));

        //wasto
        List<String> choices39 = new ArrayList<>();
        choices39.add("wasto");
        choices39.add("wakas");
        choices39.add("wagi");
        choices39.add("walo");
        quizword_questionList.add(new Question(R.drawable.quizword_wasto, choices39, 0));

        //yaman
        List<String> choices40 = new ArrayList<>();
        choices40.add("yugto");
        choices40.add("yakap");
        choices40.add("yaman");
        choices40.add("yanig");
        quizword_questionList.add(new Question(R.drawable.quizword_yaman, choices40, 2));


    }

    public List<Question> getQuizword_questionList() {
        return quizword_questionList;
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
