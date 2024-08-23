package com.me.kmp.movies.root

import androidx.lifecycle.ViewModelStoreOwner

interface DetailComponent {
    val viewModelStoreOwner: ViewModelStoreOwner
    val movieId: Int
    fun onBack()
}
