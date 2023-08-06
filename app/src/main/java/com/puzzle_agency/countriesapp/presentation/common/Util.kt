package com.puzzle_agency.countriesapp.presentation.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}