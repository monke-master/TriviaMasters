package com.monke.triviamasters.domain

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.models.Player
import com.monke.triviamasters.domain.models.Question
import com.monke.triviamasters.domain.models.Statistics
import com.monke.triviamasters.domain.models.User
import java.util.Calendar

val mockedPlayer = Player(
    username = "Алексей Пропастин",
    startedPlayingDate = Calendar.getInstance().timeInMillis,
    statistics = Statistics()
)

val mockedUser = User(
    id = "124",
    player = mockedPlayer,
    email = "propastin@donetsk.org",
    password = "Platonova",
    registrationDate = Calendar.getInstance().timeInMillis
)

val mockedCategories =  listOf(
    Category(1, "Ukraine"),
    Category(2, "Russia"),
    Category(3, "Cars"),
    Category(4, "Books"),
    Category(5, "Philosophy"),
    Category(6, "Computers"),
    Category(7, "USA"),
    Category(8, "Rome"),
    Category(9, "Ancient Greece"),
    Category(10, "Guitars"),
    Category(11, "Android"),
    Category(12, "Games"),
    Category(13, "Travel"),
    Category(14, "Geeks"),
    Category(15, "Wagner"),
    Category(16, "Biber and Dolik"),
)

val mockedQuestions = listOf(
    Question(
        id = 1,
        question = "Russia's capital",
        category = Category(2, "Russia"),
        price = 200,
        answer = "Moscow"
    ),
    Question(
        id = 2,
        question = "Russia's second capital",
        category = Category(2, "Russia"),
        price = 400,
        answer = "Saint's Petersburg"
    ),
    Question(
        id = 3,
        question = "USA capital",
        category = Category(7, "USA"),
        price = 100,
        answer = "Washington"
    ),
    Question(
        id = 4,
        question = "States amount",
        category = Category(7, "USA"),
        price = 500,
        answer = "50"
    ),
    Question(
        id = 5,
        question = "First philosopher",
        category = Category(9, "Ancient Greece"),
        price = 700,
        answer = "Phales from Milet"
    ),
    Question(
        id = 6,
        question = "Place, where spartans have been attacked by Persians",
        category = Category(9, "Ancient Greece"),
        price = 600,
        answer = "Fermopiles"
    ),

)