package com.monke.triviamasters.ui.modesFeature

sealed class Mode(
    val title: String,
    val description: String,
    val screenNavigation: () -> Unit
) {
    class SearchCategories(
        title: String,
        description: String,
        screenNavigation: () -> Unit) : Mode(title, description, screenNavigation)

    class FullyRandom(
        title: String,
        description: String,
        screenNavigation: () -> Unit) : Mode(title, description, screenNavigation)

    class StrengthTest(
        title: String,
        description: String,
        screenNavigation: () -> Unit) : Mode(title, description, screenNavigation)

    class OwnGame(
        title: String,
        description: String,
        screenNavigation: () -> Unit) : Mode(title, description, screenNavigation)
}