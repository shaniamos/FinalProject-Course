package com.example.myprocedure;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsService;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.SingleTop;

@Parent
@SingleTop
@Layout(R.layout.question_big)


public class QuestionBig {
    @View(R.id.big_question_text_view)
    public TextView bigQuestionTv;
    @View(R.id.arrow_big_question)
    public ImageView arrowBigQuestion;
    public String question;

    public QuestionBig(String question) {
        this.question = question;

    }

    @Resolve
    public void onResolved(){
        bigQuestionTv.setText(question);
    }
    @Expand
    public void onExpand(){
        arrowBigQuestion.setImageResource(R.drawable.ic_arrow_up);
    }
    @Collapse
    public void onCollapse(){
        arrowBigQuestion.setImageResource(R.drawable.ic_arrow_down);
    }
}
