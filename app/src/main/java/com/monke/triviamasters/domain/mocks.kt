package com.monke.triviamasters.domain

import com.monke.triviamasters.domain.models.Player
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