package com.monke.triviamasters.domain.exceptions

class RepositoryException(
    override val message: String,
    val code: Int
): Throwable() {



}