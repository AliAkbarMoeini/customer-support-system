package ir.aliakbarmoeini.support.exception

import org.springframework.validation.BindingResult

class RequestBodyException(val binding: BindingResult) : RuntimeException()