package com.josiassena.movielist.app_helpers.listener

import android.text.Editable
import android.text.TextWatcher
import com.mancj.materialsearchbar.MaterialSearchBar
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author Josias Sena
 */
object SearchObservable : MaterialSearchBar.OnSearchActionListener, TextWatcher {

    private var subject = PublishSubject.create<String>()

    fun fromView(searchBar: MaterialSearchBar?): Observable<String> {
        searchBar?.let {
            it.setOnSearchActionListener(this)
            it.addTextChangeListener(this)
        }

        return subject
    }

    override fun onButtonClicked(buttonCode: Int) {
    }

    override fun onSearchStateChanged(stateChanged: Boolean) {
        if (!stateChanged) {
            subject.onNext("")
        }
    }

    override fun onSearchConfirmed(query: CharSequence?) = subject.onNext(query.toString())

    override fun afterTextChanged(s: Editable?) = subject.onNext("$s")

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}