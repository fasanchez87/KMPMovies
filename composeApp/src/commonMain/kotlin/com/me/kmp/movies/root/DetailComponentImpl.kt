package com.me.kmp.movies.root

import androidx.lifecycle.ViewModelStoreOwner

class DetailComponentImpl(
    override val viewModelStoreOwner: ViewModelStoreOwner,
    private val onFinished: () -> Unit,
    override val movieId: Int
) : DetailComponent {

    override fun onBack() = onFinished()
}
