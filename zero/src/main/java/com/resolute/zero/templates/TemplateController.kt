package com.resolute.zero.templates

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController



@RestController
@CrossOrigin("*")
class TemplateController {
    fun getFormattedTemplate(arguments: Map<Int, String>, template: Templates): String {
        var formattedString = template.template
        for (i in 1..template.arguments.size) {
            val value = arguments[i]
            if (value != null) {
                formattedString = formattedString.replace("{{$i}}", value)
            }
        }
        return formattedString
    }

    @GetMapping("/list/templatesAndArguments") // Map this method to a GET request at "/api/v1/hello"
    fun getTemplates():  List<Map<String,Any>> {
        return enumValues<Templates>().map { template ->
            hashMapOf(
                "templateName" to template.templateName,
                "arguments" to template.arguments,
                "template" to template.template
            )
        }
    }
}