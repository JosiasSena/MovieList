package com.josiassena.movielist.app_helpers.listener

import android.text.Editable
import android.text.TextWatcher
import com.mancj.materialsearchbar.MaterialSearchBar
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * @author Josias Sena
 */
class SearchObservable {

    companion object {
        private var subject = PublishSubject.create<String>()

        fun fromView(materialSearchBar: MaterialSearchBar): Observable<String> {
            materialSearchBar
                    .setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
                        override fun onButtonClicked(buttonCode: Int) {
                        }

                        override fun onSearchStateChanged(stateChanged: Boolean) {
                            if (!stateChanged) {
                                subject.onNext("")
                            }
                        }

                        override fun onSearchConfirmed(query: CharSequence?) {
                            subject.onNext(query.toString())
                        }
                    })

            materialSearchBar.addTextChangeListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    subject.onNext("$s")
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

            return subject
        }
    }

}