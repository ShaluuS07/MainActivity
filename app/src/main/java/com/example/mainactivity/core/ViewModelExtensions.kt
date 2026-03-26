package com.example.mainactivity.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

/**
 * Ergonomic shorthand for `viewModels { viewModelFactory { ... } }`.
 *
 * **Do not** use the old CaratLane-style `ViewModelProviders.of(this).get(T::class.java)` — it is
 * **deprecated**; use [androidx.fragment.app.viewModels] (this helper builds on it).
 *
 * **Do not** wrap in `by lazy { }` — [viewModels] is already lazy and tied to the fragment
 * lifecycle; double-lazy adds confusion and can break lifecycle expectations.
 *
 * For ViewModels with constructor args (repository, nav args, etc.), pass them inside the lambda
 * (runs when the ViewModel is first resolved, not at property declaration time).
 */
inline fun <reified VM : ViewModel> Fragment.viewModelsOf(
    crossinline factory: () -> VM,
) = viewModels<VM> { viewModelFactory(factory) }
