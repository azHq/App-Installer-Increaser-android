package com.example.asus.appinstaller;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class Speak {

    TextToSpeech text;
    Context context;

    public Speak(Context context){

        this.context=context;

        text = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status != TextToSpeech.ERROR) {

                    text.setLanguage(Locale.getDefault());
                }

            }
        });

    }

    public void toSpeak(String s){

        text.speak(s, TextToSpeech.QUEUE_FLUSH,null);

    }
}
