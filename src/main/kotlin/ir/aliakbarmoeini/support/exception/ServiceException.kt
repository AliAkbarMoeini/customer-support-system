package ir.aliakbarmoeini.support.exception

import org.springframework.http.HttpStatus

class ServiceException(message: String, val status: HttpStatus) : RuntimeException(message)