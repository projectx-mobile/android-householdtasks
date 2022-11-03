package com.projectx.common.domain.use_case

import com.projectx.common.presentation.model.Faq

class GetFaqListUseCase : BaseUseCase<List<Faq>, Unit>() {
    override suspend fun run(params: Unit): List<Faq> {
        val answer = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        return listOf(
            Faq("Вопрос первый", answer),
            Faq("Вопрос второй", answer),
            Faq("Вопрос третий", answer),
            Faq("Вопрос четвертый", answer),
            Faq("Вопрос первый", answer),
            Faq("Вопрос второй", answer),
            Faq("Вопрос третий", answer),
            Faq("Вопрос четвертый", answer),
            Faq("Вопрос четвертый", answer),
            Faq("Вопрос первый", answer),
            Faq("Вопрос второй", answer),
            Faq("Вопрос третий", answer),
            Faq("Вопрос четвертый", answer)
        )
    }
}