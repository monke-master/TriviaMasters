package com.monke.triviamasters.utils

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

fun formatDate(date: Long): String = SimpleDateFormat("dd-MM-yyyy").format(date)
