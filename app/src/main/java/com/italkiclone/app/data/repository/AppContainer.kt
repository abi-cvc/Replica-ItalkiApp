package com.italkiclone.app.data.repository

/**
 * Contenedor simple de dependencias. No usamos un framework de DI porque
 * el taller no lo requiere: basta con exponer instancias únicas de los
 * repositorios (interfaz en domain, implementación fake en data).
 */
object AppContainer {
    val teacherRepository: FakeTeacherRepository by lazy { FakeTeacherRepository() }
    val classRepository: FakeClassRepository by lazy { FakeClassRepository() }
    val studentRepository: FakeStudentRepository by lazy { FakeStudentRepository() }
    val chatRepository: FakeChatRepository by lazy { FakeChatRepository() }
}
