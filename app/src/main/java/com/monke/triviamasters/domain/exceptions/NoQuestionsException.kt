package com.monke.triviamasters.domain.exceptions

import java.lang.Exception

class NoQuestionsException: Exception() {

    override val message: String?
        get() = "Sorry, but we can't create game with these settings"
}