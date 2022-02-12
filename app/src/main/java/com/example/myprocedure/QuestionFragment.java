package com.example.myprocedure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

public class QuestionFragment extends Fragment {
    View view;
    ExpandablePlaceHolderView expandablePlaceHolderView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.questions_fragment,container,false);
        expandablePlaceHolderView = view.findViewById(R.id.expandble_list_view);

        expandablePlaceHolderView
                .addView(new QuestionBig(getResources().getString(R.string.question1)))
                .addView(new AnswerSmall(getResources().getString(R.string.answer1)))
                .addView(new QuestionBig(getResources().getString(R.string.question2)))
                .addView(new AnswerSmall(getResources().getString(R.string.answer2)))
                .addView(new QuestionBig(getResources().getString(R.string.question3)))
                .addView(new AnswerSmall(getResources().getString(R.string.answer3)))
                .addView(new QuestionBig(getResources().getString(R.string.question4)))
                .addView(new AnswerSmall(getResources().getString(R.string.answer4)))
                .addView(new QuestionBig(getResources().getString(R.string.question5)))
                .addView(new AnswerSmall(getResources().getString(R.string.answer5)))
                .addView(new QuestionBig(getResources().getString(R.string.question6)))
                .addView(new AnswerSmall(getResources().getString(R.string.answer6)));


        return view;
    }
}
