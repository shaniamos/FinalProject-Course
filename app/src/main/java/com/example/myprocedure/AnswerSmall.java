package com.example.myprocedure;


import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Layout(R.layout.answer_small)

public class AnswerSmall {
    @View(R.id.answer_small_text_view)
    public TextView answerSmallTv;
    public String answer;

    public AnswerSmall(String answer) {
        this.answer = answer;
    }

    @Resolve
    public void onResolved(){
        answerSmallTv.setText(answer);
    }
}
