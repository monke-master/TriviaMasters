package com.monke.triviamasters.domain.exceptions

class IncorrectPasswordException: Exception() {

    override val message: String?
        get() = "Incorrect password"
}