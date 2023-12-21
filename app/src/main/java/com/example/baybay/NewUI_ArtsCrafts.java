package com.example.baybay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

public class NewUI_ArtsCrafts extends AppCompatActivity {

    private ImageButton ImgbtnArtsCratsExit;
    private NestedScrollView NsvMain;
    private ImageView ImgviewPens, ImgviewShirtsPants, ImgviewBags, ImgviewStickers, ImgviewAccessories, ImgviewCalligraphy, ImgviewTattoos, ImgviewEngraving, ImgviewSignsBrands;
    private ImageButton ImgbtnTagPen, ImgbtnTagShirtsPants, ImgbtnTagBags, ImgbtnTagStickers, ImgbtnTagAccessories, ImgbtnTagCalligraphy, ImgbtnTagTattoos, ImgbtnTagEngraving, ImgbtnTagSignsBrands;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // No Actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Do not sleep when the app is open
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_new_ui_arts_crafts);

        // Fullscreen beyond punch hole camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        // Set the gradient background color
        int singleColor = Color.parseColor("#FCF4E7");

        // Create the custom GradientDrawable
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{singleColor, singleColor});

        // Set the gradient heights
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setGradientCenter(0, 0);
        gradientDrawable.setBounds(0, 0, getWindow().getDecorView().getWidth(), getWindow().getDecorView().getHeight());

        // Set the custom GradientDrawable as the window background
        getWindow().setBackgroundDrawable(gradientDrawable);




        ImgbtnArtsCratsExit = findViewById(R.id.imgbtn_artscrafts_exit);
        ImgbtnArtsCratsExit.setOnClickListener(v -> {
            ClickSoundEffect();
            finish();
        });

        NsvMain = findViewById(R.id.ncv_main);

        ImgviewAccessories = findViewById(R.id.imageViewAccessories);
        ImgbtnTagAccessories = findViewById(R.id.imgbtn_artscrafts_tagaccessories);
        ImgbtnTagAccessories.setOnClickListener(v -> {
            NsvMain.smoothScrollTo(0, (int) ImgviewAccessories.getY(), 700);
        });



        ImageSlider ImgSliderPen = findViewById(R.id.slider_pen);
        List<SlideModel> slideModelsPen = new ArrayList<>();
        //slideModelsPen.add(new SlideModel(R.drawable.newui_trivia_board));
        slideModelsPen.add(new SlideModel("https://down-ph.img.susercontent.com/file/ph-11134207-7qul9-lk83fvi648do05"));
        slideModelsPen.add(new SlideModel("https://scontent.fmnl4-6.fna.fbcdn.net/v/t1.6435-9/152870297_452379642840143_6168932033046385146_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=dd63ad&_nc_eui2=AeH3xy6H2w5nMahjeAWp3mo2yeehT7zoKI_J56FPvOgojyj0HcMa7CpYtLY_-Qro-KnI4svoKZOPAckwaskWTOeI&_nc_ohc=-u-duVFFfIEAX_56BmV&_nc_ht=scontent.fmnl4-6.fna&oh=00_AfDHM8l9SjGgGBUgFpvNuywJH-0ChN93zpoi0AiHjhK2-g&oe=65AB5EE4"));
        slideModelsPen.add(new SlideModel("https://media.karousell.com/media/photos/products/2023/4/30/baybayin_ballpen_1682819470_1b877956.jpg"));
        ImgSliderPen.setImageList(slideModelsPen,true);

        ImageSlider ImgSliderShirtsPants = findViewById(R.id.slider_shirts);
        List<SlideModel> slideModelsShirtsPants = new ArrayList<>();
        //slideModelsShirtsPants.add(new SlideModel(R.drawable.newui_trivia_board));
        slideModelsShirtsPants.add(new SlideModel("https://down-ph.img.susercontent.com/file/ph-11134207-7r990-llz7t1tmy3n59f"));
        slideModelsShirtsPants.add(new SlideModel("https://cdn.shopify.com/s/files/1/0038/2382/9090/files/BLACKCREWNECK2.jpg?v=1700714272&width=533"));
        slideModelsShirtsPants.add(new SlideModel("https://scontent.fmnl8-3.fna.fbcdn.net/v/t1.6435-9/76728939_2666399476769697_7376293180519481344_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=dd63ad&_nc_eui2=AeGJzrWbH3uP-hP1HCeZ0aP2urvlqXprKpK6u-WpemsqkntrMRJrWKFYaqHBO67Ri2UtIdAREYDqNnqJvhRqZQyb&_nc_ohc=oQvfGmMR2jcAX_UvZQQ&_nc_ht=scontent.fmnl8-3.fna&oh=00_AfDIhIArcRYEmwwcz7AcBZqkwWRWnDwh-62fHHzGSlFZWw&oe=65AB6A82"));
        slideModelsShirtsPants.add(new SlideModel("https://down-ph.img.susercontent.com/file/612ad46cee0d14ef45acce17c27fb55d"));
        slideModelsShirtsPants.add(new SlideModel("https://scontent.fmnl8-2.fna.fbcdn.net/v/t1.6435-9/77265463_2666399333436378_4880344662402924544_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=dd63ad&_nc_eui2=AeEZCV3vImj-SedkPt5emmsuJhM_p50mxDomEz-nnSbEOkfZAYYqGrCC9qYG7XOOX_ab9uRmehZB7z7JsInO_V_o&_nc_ohc=H8WDLtfgYvMAX-Y5mUF&_nc_ht=scontent.fmnl8-2.fna&oh=00_AfCeKPGcDyHLEp2lbZcYrWlVLUVMmWifisKu0aKnUEKx6w&oe=65AB65C6"));
        ImgSliderShirtsPants.setImageList(slideModelsShirtsPants,true);

        ImageSlider ImgSliderBags = findViewById(R.id.slider_bags);
        List<SlideModel> slideModelsBags = new ArrayList<>();
        //slideModelsBags.add(new SlideModel(R.drawable.newui_trivia_board));
        slideModelsBags.add(new SlideModel("https://down-ph.img.susercontent.com/file/1c70b7a94217167b209c7d4ee35f9e2a"));
        slideModelsBags.add(new SlideModel("https://down-ph.img.susercontent.com/file/ph-11134207-7r98v-ll7872x9d48z5b"));
        slideModelsBags.add(new SlideModel("https://down-ph.img.susercontent.com/file/632a7e68791e6d4340abc0edb311fe78"));
        slideModelsBags.add(new SlideModel("https://www.wearlegazy.com/cdn/shop/files/Bayong2.0-Karamelo_1.jpg?crop=center&height=533&v=1696313154&width=800"));
        slideModelsBags.add(new SlideModel("https://www.merchiful.com/cdn/shop/products/1_d94fa8c0-442b-44c1-bfb4-8435240c6d23.jpg?v=1661933105"));
        ImgSliderBags.setImageList(slideModelsBags,true);

        ImageSlider ImgSliderStickers = findViewById(R.id.slider_stickers);
        List<SlideModel> slideModelsStickers = new ArrayList<>();
        //slideModelsStickers.add(new SlideModel(R.drawable.newui_trivia_board));

        slideModelsStickers.add(new SlideModel("https://down-ph.img.susercontent.com/file/bfec2b0f6e373b3bd356c68c1d48efe0"));
        slideModelsStickers.add(new SlideModel("https://down-ph.img.susercontent.com/file/020910927d8eb7cb52f70d5f73b5e328"));
        slideModelsStickers.add(new SlideModel("https://down-ph.img.susercontent.com/file/ph-11134201-7qul2-lkj1abovtfeme9"));
        slideModelsStickers.add(new SlideModel("https://down-ph.img.susercontent.com/file/e6a400fe257a5e90cb1856c83027b5b1"));
        slideModelsStickers.add(new SlideModel("https://www.sarapnow.com/cdn/shop/products/mie-makes-art-collectibles-mie-makes-mahal-kita-baybayin-sticker-i-love-you-sticker-tagalog-baybayin-alibata-filipino-philippines-laptop-sticker-hydroflask-waterbottle-30020876533847.jpg?v=1675678739&width=2000"));
        ImgSliderStickers.setImageList(slideModelsStickers,true);

        ImageSlider ImgSliderAccessories = findViewById(R.id.slider_accessories);
        List<SlideModel> slideModelsAccessories = new ArrayList<>();
        //slideModelsAccessories.add(new SlideModel(R.drawable.newui_trivia_board));
        slideModelsAccessories.add(new SlideModel("https://down-ph.img.susercontent.com/file/e9868c68965addfc6a9e2a9e42345c59"));
        slideModelsAccessories.add(new SlideModel("https://down-ph.img.susercontent.com/file/503d1df3f6d4cb350d350edd1ea59015"));
        slideModelsAccessories.add(new SlideModel("https://down-ph.img.susercontent.com/file/1d89367552fb1bc9a449c32da43f18da"));
        slideModelsAccessories.add(new SlideModel("https://down-ph.img.susercontent.com/file/d45d084929e07d19963943f437c84170"));
        slideModelsAccessories.add(new SlideModel("https://down-ph.img.susercontent.com/file/ph-11134201-7r98o-lkwd4mbvl6ps33"));
        ImgSliderAccessories.setImageList(slideModelsAccessories,true);

        ImageSlider ImgSliderCalligraphy = findViewById(R.id.slider_calligraphy);
        List<SlideModel> slideModelsCalligraphy = new ArrayList<>();
        //slideModelsCalligraphy.add(new SlideModel(R.drawable.newui_trivia_board));
        slideModelsCalligraphy.add(new SlideModel("https://imagevars.gulfnews.com/2019/07/31/190731-baybayin_16c46bc5266_large.jpg"));
        slideModelsCalligraphy.add(new SlideModel("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/af6e2759-ff89-40e7-9dc2-77b804057cde/dbo91h7-310666c3-52fb-4932-afe3-e65a6e244f99.jpg/v1/fill/w_900,h_623,q_75,strp/filipino_baybayin_calligraphy_by_architect_gillesania_dbo91h7-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NjIzIiwicGF0aCI6IlwvZlwvYWY2ZTI3NTktZmY4OS00MGU3LTlkYzItNzdiODA0MDU3Y2RlXC9kYm85MWg3LTMxMDY2NmMzLTUyZmItNDkzMi1hZmUzLWU2NWE2ZTI0NGY5OS5qcGciLCJ3aWR0aCI6Ijw9OTAwIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmltYWdlLm9wZXJhdGlvbnMiXX0.7OVonehpZ62IM_bOFkqUWJJgDadWmVogEhHlurtVWew"));
        slideModelsCalligraphy.add(new SlideModel("https://images.sbs.com.au/dims4/default/de6145e/2147483647/strip/true/crop/3264x1836+0+38/resize/1280x720!/quality/90/?url=http%3A%2F%2Fsbs-au-brightspot.s3.amazonaws.com%2Fdrupal%2Fyourlanguage%2Fpublic%2Fbaybayin1_1.jpg"));
        slideModelsCalligraphy.add(new SlideModel("https://www.baybayin.com/uploads/3/1/2/8/3128279/3903701_orig.jpg"));
        slideModelsCalligraphy.add(new SlideModel("https://64.media.tumblr.com/9b9455dd3f32c902eaf8fe11b9b0c570/tumblr_mlsn29B75H1qzeh9bo1_640.jpg"));
        ImgSliderCalligraphy.setImageList(slideModelsCalligraphy,true);

        ImageSlider ImgSliderTattos = findViewById(R.id.slider_tattoos);
        List<SlideModel> slideModelsTattoos = new ArrayList<>();
        //slideModelsTattoos.add(new SlideModel(R.drawable.newui_trivia_board));
        slideModelsTattoos.add(new SlideModel("https://images.summitmedia-digital.com/preview/images/2022/04/25/272935461_115494947555402_4623700061303765088_n-(1).jpg"));
        slideModelsTattoos.add(new SlideModel("https://i.pinimg.com/originals/f8/e6/9a/f8e69aa27a93d05b9566d0616905f5eb.jpg"));
        slideModelsTattoos.add(new SlideModel("https://images.summitmedia-digital.com/preview/images/2022/04/25/219154860_943788786400606_3944048259026654728_n-(1).jpg"));
        slideModelsTattoos.add(new SlideModel("https://sa.kapamilya.com/absnews/abscbnnews/media/ancx/style/2021/02/3roel.jpg"));
        slideModelsTattoos.add(new SlideModel("https://images.summitmedia-digital.com/preview/images/2022/04/25/140774981_850965592413618_2387209040066158877_n-(1).jpg"));
        ImgSliderTattos.setImageList(slideModelsTattoos,true);

        ImageSlider ImgSliderEngraving = findViewById(R.id.slider_engraving);
        List<SlideModel> slideModelsEngraving = new ArrayList<>();
        //slideModelsEngraving.add(new SlideModel(R.drawable.newui_trivia_board));
        slideModelsEngraving.add(new SlideModel("https://i.etsystatic.com/40106744/r/il/493daf/4539009521/il_300x300.4539009521_fqxc.jpg"));
        slideModelsEngraving.add(new SlideModel("https://i.etsystatic.com/16232105/r/il/6a8eb1/4415865742/il_570xN.4415865742_1oq6.jpg"));
        slideModelsEngraving.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGDTCyT4k-lYtbU6NE9zIHza4uxZC_V_T34g&usqp=CAU"));
        slideModelsEngraving.add(new SlideModel("https://obs.line-scdn.net/0hlDtP8A1aM2tJKRsBLUlMPHN_MAR6RSBoLR9idBZHbV8wGCE6fU16XmUhZQxsGnQ1Jx90D2ktKFo3HHw1dEx6/w644"));
        slideModelsEngraving.add(new SlideModel("https://64.media.tumblr.com/8f5e914da42439cf484edf70ab083df4/tumblr_n6iix9pIWm1s6239co1_640.jpg"));
        ImgSliderEngraving.setImageList(slideModelsEngraving,true);

        ImageSlider ImgSliderSignsBrands = findViewById(R.id.slider_signsbrands);
        List<SlideModel> slideModelsSignsBrands = new ArrayList<>();
        //slideModelsSignsBrands.add(new SlideModel(R.drawable.newui_trivia_board));
        slideModelsSignsBrands.add(new SlideModel("https://scontent.fmnl8-2.fna.fbcdn.net/v/t39.30808-6/307123911_391146399889370_701788119659488263_n.png?_nc_cat=107&ccb=1-7&_nc_sid=783fdb&_nc_eui2=AeGoIY4OnVIM4j1e33cq7uK2HPdy82Lbq4Ec93LzYturgftjE2MYSH3wgLJ_vYYrQKfmxTNfVTj9xlnOpcX2imFX&_nc_ohc=cbOuws64CPcAX-ZCBPV&_nc_ht=scontent.fmnl8-2.fna&oh=00_AfA-xDgwcdtrUfPRBiIQfTPkbHhqWJjFqjoWlFhlgdJ8-A&oe=6588E072"));
        slideModelsSignsBrands.add(new SlideModel("https://images.summitmedia-digital.com/esquiremagph/images/2018/04/27/BAYBAYIN-LOGOS_cover_APR2018.jpg"));
        slideModelsSignsBrands.add(new SlideModel("https://scontent.fmnl8-2.fna.fbcdn.net/v/t1.6435-9/139730593_793319777920719_8822489586794100063_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=7f8c78&_nc_eui2=AeGupsxSmR-63zNClPK81T-q6G5Va_2Z8r3oblVr_ZnyveUmr-gIicw3CUJ1B81EXV_D7j_FAY84RA_IfyYUvbkd&_nc_ohc=I3G1uG11gWEAX_DnhuK&_nc_ht=scontent.fmnl8-2.fna&oh=00_AfAGcoGWANDT77wk_sYp0vjzZX9qGUulOg32Pumtpl6tKg&oe=65AB5B8F"));
        slideModelsSignsBrands.add(new SlideModel("https://scontent.fmnl8-2.fna.fbcdn.net/v/t1.6435-9/139354249_792617861324244_2801931658923800392_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=7f8c78&_nc_eui2=AeE4t812RMZAfG9uvh_1xdJj4X_Nxm_BNfLhf83Gb8E18pNYx0OkWBrkJtTxi4pEkM_9qq-XBnA_-1wk5SQY9dIl&_nc_ohc=ORqacaSIpAYAX8e2xbx&_nc_ht=scontent.fmnl8-2.fna&oh=00_AfC6EXyki4-EEcTicTv1usevDq6-sH5TBkaqqbenZFkhWw&oe=65AB7266"));
        slideModelsSignsBrands.add(new SlideModel("https://scontent.fmnl8-3.fna.fbcdn.net/v/t1.6435-9/101007634_636670963585602_1686671375541993472_n.png?_nc_cat=104&ccb=1-7&_nc_sid=7f8c78&_nc_eui2=AeH3kga--6TMsHrvgTbD2Y96YF8SyJoK-yhgXxLImgr7KO1ivrzdeZn7BubKqfx73uSKW0a3T2fInzuN-W9tG0RP&_nc_ohc=J4v1_B5hr-wAX8wiUov&_nc_ht=scontent.fmnl8-3.fna&oh=00_AfCzlj2PPjassJgVi-Yg2-A-WrMOShzZxpjRfF6RydjetA&oe=65AB4334"));
        slideModelsSignsBrands.add(new SlideModel("https://scontent.fmnl8-1.fna.fbcdn.net/v/t1.6435-9/89316375_586388408613858_6043951057799741440_n.png?_nc_cat=108&ccb=1-7&_nc_sid=7f8c78&_nc_eui2=AeHXxZ2SXPS51p-KBUCSYGpRSDFn6wSlO2ZIMWfrBKU7ZmB8XACUp2zDuj4gkRsiHgzJjOQwia0jXiJLQ1LnCoyQ&_nc_ohc=LoBpo-euRicAX8OVCHX&_nc_ht=scontent.fmnl8-1.fna&oh=00_AfAp6dyAuYBZexeXkh_sDkgPWZ2kqD6PRZv8umE4Fp9bBw&oe=65AB6035"));
        slideModelsSignsBrands.add(new SlideModel("https://scontent.fmnl8-3.fna.fbcdn.net/v/t1.6435-9/55849440_378574346061933_1030878041795985408_n.png?_nc_cat=102&ccb=1-7&_nc_sid=7f8c78&_nc_eui2=AeEtqavk0q9yWN-rk0A7dC8ufJZ3M7tu-ol8lnczu276ieQz2Htwee37J714voLs08pkZGsEVKAwH6jVcFP_68Me&_nc_ohc=gogpeVCMuBUAX_1ryv4&_nc_ht=scontent.fmnl8-3.fna&oh=00_AfAq7cM1jl2u7O9FPH0nQFWv7GZ0gzP7KbuXf11Q6zC8LQ&oe=65AB435C"));
        slideModelsSignsBrands.add(new SlideModel("https://scontent.fmnl8-1.fna.fbcdn.net/v/t1.6435-9/51794298_358801374705897_6375106294885384192_n.png?_nc_cat=108&ccb=1-7&_nc_sid=7f8c78&_nc_eui2=AeElrxzKkoi3mzwmdEBLRoqYuRn89Xkd1m25Gfz1eR3WbbWqW8v3zx9O2uadqTGa8ZEVA_QC1BXw75d-nwu1__Ro&_nc_ohc=G-3SXCn8JDwAX-4GChG&_nc_ht=scontent.fmnl8-1.fna&oh=00_AfC0QrsMaNXo2oDINEes0FVivsEGKK2qkgtnw9nEWlSbXg&oe=65AB52E4"));
        slideModelsSignsBrands.add(new SlideModel("https://scontent.fmnl8-1.fna.fbcdn.net/v/t1.6435-9/50282465_349042802348421_7232460820335034368_n.png?_nc_cat=100&ccb=1-7&_nc_sid=7f8c78&_nc_eui2=AeHfJx0gc_lVscf9PYB_HCqxNU9Hq2dVjiM1T0erZ1WOI0j_M-45gPj0kqjZ7uKZdstQSLouQCbp7twk9jx5AvTS&_nc_ohc=434ASQeGXG4AX9GF5GX&_nc_ht=scontent.fmnl8-1.fna&oh=00_AfCIU33yR9-IHSvRwhrHmPF2zEfns92ts-F7jvP0jA3_kw&oe=65AB6134"));
        slideModelsSignsBrands.add(new SlideModel("https://scontent.fmnl8-1.fna.fbcdn.net/v/t1.6435-9/49612671_344780042774697_2430893846779920384_n.png?_nc_cat=110&ccb=1-7&_nc_sid=7f8c78&_nc_eui2=AeE9gXsDLflH7S999vgVWq4BHDstJHXvh50cOy0kde-HnXWFh7hrGlUzVCLssgdlJbtsIQEvpkYjs1FrNaXfuOOi&_nc_ohc=k9us1iG9uP4AX-_ELtj&_nc_ht=scontent.fmnl8-1.fna&oh=00_AfB8ckqdQnCXa_MRLiPY8cQBiANNHmQ-r039jlreYSufrg&oe=65AB4221"));
        slideModelsSignsBrands.add(new SlideModel("https://scontent.fmnl8-2.fna.fbcdn.net/v/t1.6435-9/37568704_259738964612139_2111720928499990528_n.png?_nc_cat=109&ccb=1-7&_nc_sid=7f8c78&_nc_eui2=AeHETWfNUiAivI8r4pg4_ko3Yh7ZhbyQYb9iHtmFvJBhv8Q9OdzbTU70JDb-qoFqnUoJj0I6VP9hxmX1yhEY7vwA&_nc_ohc=4J-Ey8nx8wwAX-5__WW&_nc_ht=scontent.fmnl8-2.fna&oh=00_AfAxaDtyztA6jXB6u3MPLYPy-jku3143uzJYk5UATcQwpg&oe=65AB5FF7"));
        ImgSliderSignsBrands.setImageList(slideModelsSignsBrands,true);

//        ImgSliderPen.startSliding(3000); // with new period
//        ImgSliderPen.stopSliding();

    }

    void ClickSoundEffect() {
        boolean[] sfxPass = Z_SoundManager.isSoundFx;
        if (sfxPass.length > 0 && sfxPass[0]) {
            Z_SoundManager soundManager = new Z_SoundManager();
            soundManager.RegButtonClickSound(this);
        }
    }


}