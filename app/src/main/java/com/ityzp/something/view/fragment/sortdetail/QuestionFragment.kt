package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.QuestionContract
import com.ityzp.something.presenter.QuestionPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class QuestionFragment : MvpFragment<QuestionContract.questionView, QuestionPresenter>(),
    QuestionContract.questionView {
    override fun initPresenter(): QuestionPresenter {
        return QuestionPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_question

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getQuestion() {
    }

}