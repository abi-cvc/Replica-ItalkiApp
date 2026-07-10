package com.italkiclone.app.data.fake

import com.italkiclone.app.data.model.ChatConversation
import com.italkiclone.app.data.model.ClassSession
import com.italkiclone.app.data.model.ClassStatus
import com.italkiclone.app.data.model.LanguageCompetency
import com.italkiclone.app.data.model.Student
import com.italkiclone.app.data.model.StudentRelation
import com.italkiclone.app.data.model.Teacher
import kotlin.random.Random

/**
 * Genera datasets simulados extensos y deterministas (misma semilla -> mismos datos)
 * para poder estresar el scroll de las listas sin depender de red/backend.
 */
object FakeDataGenerator {

    private val firstNames = listOf(
        "Carol", "Fedoua", "Camille", "David", "Angie", "Bridget", "Jamie", "Wally",
        "Haru", "Robert", "Francisco", "Kira", "Blume", "Andrew", "Artur", "Callum",
        "Charnelle", "Amirreza", "Montita", "Anahí", "Frédéric", "Sei", "Yuna", "Kanaa",
        "Erica", "John", "Bruno", "Maria", "Atsuhiro", "Joni", "Aaron", "Sofía",
        "Diego", "Valentina", "Mateo", "Lucía", "Nicolás", "Isabella", "Samuel", "Emma",
    )

    private val lastNames = listOf(
        "Velasquez", "Estrin", "Fonseca", "Preston", "Oguri", "Kirk", "Gonzalez", "Cando",
        "Burns", "Hunt", "Priedītis", "Zimin", "Jones", "P.", "B.", "Cárdenas", "Rojas",
        "Vargas", "Torres", "Salazar",
    )

    private val countries = listOf(
        "🇪🇨" to "Ecuador", "🇺🇸" to "Estados Unidos", "🇫🇷" to "Francia", "🇬🇧" to "Reino Unido",
        "🇯🇵" to "Japón", "🇧🇷" to "Brasil", "🇵🇱" to "Polonia", "🇷🇺" to "Rusia",
        "🇨🇴" to "Colombia", "🇲🇽" to "México", "🇩🇪" to "Alemania", "🇮🇹" to "Italia",
    )

    private val languages = listOf(
        "Inglés", "Español", "Francés", "Portugués", "Alemán", "Italiano", "Japonés",
        "Chino (mandarín)", "Coreano", "Ruso", "Polaco",
    )

    private val teacherBios = listOf(
        "Licensed English Teacher | TESOL Certified | 10+ Years Experience",
        "Practica español con una nativa / Free Conversation",
        "Educator with a practical approach who draws inspiration from human connections",
        "Clases conversacionales personalizadas a tus metas, con enfoque en fluidez",
        "Tutor de la comunidad, apasionado por la cultura y los idiomas",
        "Profesional en pedagogía con experiencia en preparación de exámenes",
    )

    private val interestPool = listOf(
        "Tecnología", "Películas", "Comida", "Viajes", "Finanzas", "Música", "Deportes", "Lectura",
    )

    private val classTitles = listOf(
        "Clase de Inglés", "Clase de Español", "Español clases", "Free Conversation",
        "Business English", "Preparación DELE", "Práctica de conversación", "Clase de Francés",
    )

    private fun randomFullName(random: Random) =
        "${firstNames[random.nextInt(firstNames.size)]} ${lastNames[random.nextInt(lastNames.size)]}"

    fun generateTeachers(count: Int = 60, seed: Int = 1): List<Teacher> {
        val random = Random(seed)
        return List(count) { index ->
            val (flag, _) = countries[random.nextInt(countries.size)]
            val isProfessional = random.nextBoolean()
            val hasDiscount = random.nextInt(100) < 35
            Teacher(
                id = "teacher_$index",
                name = randomFullName(random),
                countryFlagEmoji = flag,
                isProfessional = isProfessional,
                teachingLanguage = languages[random.nextInt(languages.size)],
                nativeLanguage = languages[random.nextInt(languages.size)],
                bio = teacherBios[random.nextInt(teacherBios.size)],
                pricePerHour = (5..35).random(random).toDouble() + 0.0,
                discountPercent = if (hasDiscount) listOf(10, 15, 20, 28).random(random) else null,
                rating = listOf(4.6, 4.7, 4.8, 4.9, 5.0).random(random),
                totalClasses = random.nextInt(0, 500),
                isNew = random.nextInt(100) < 15,
                photoSeed = "teacher-$index",
                interestTags = interestPool.shuffled(random).take(3),
            )
        }
    }

    fun generateClasses(count: Int = 400, seed: Int = 2): List<ClassSession> {
        val random = Random(seed)
        val statusWeights = buildList {
            addAll(List(9) { ClassStatus.COMPLETED })
            addAll(List(2) { ClassStatus.CANCELLED })
            add(ClassStatus.UPCOMING)
            add(ClassStatus.ACTION_REQUIRED)
            add(ClassStatus.WAITING)
        }
        val days = listOf("lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo")
        val months = listOf(
            "enero", "febrero", "marzo", "abril", "mayo", "junio",
            "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre",
        )
        val durations = listOf(30, 45, 60, 90, 105, 120)
        return List(count) { index ->
            val day = days[random.nextInt(days.size)]
            val month = months[random.nextInt(months.size)]
            val dateLabel = "$day, ${random.nextInt(1, 28)} de $month de ${2024 + random.nextInt(0, 3)}, " +
                "${random.nextInt(6, 21)}:${if (random.nextBoolean()) "00" else "30"}"
            ClassSession(
                id = "class_$index",
                participantName = randomFullName(random),
                classTitle = classTitles[random.nextInt(classTitles.size)],
                subject = languages[random.nextInt(languages.size)],
                dateTimeLabel = dateLabel,
                durationMinutes = durations[random.nextInt(durations.size)],
                status = statusWeights[random.nextInt(statusWeights.size)],
            )
        }
    }

    private val profileTagPool = listOf(
        "Adulto", "Adolescente", "Vivir en el extranjero", "Viaje de negocios",
        "Preparación de examen", "Uso académico",
    )

    fun generateStudents(count: Int = 40, seed: Int = 3): List<Student> {
        val random = Random(seed)
        val months = listOf(
            "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sept", "oct", "nov", "dic",
        )
        return List(count) { index ->
            val (flag, _) = countries[random.nextInt(countries.size)]
            val name = randomFullName(random)
            val relation = if (index < count * 6 / 10) StudentRelation.CURRENT else StudentRelation.POTENTIAL

            if (relation == StudentRelation.CURRENT) {
                Student(
                    id = "student_$index",
                    name = name,
                    countryFlagEmoji = flag,
                    relation = relation,
                    languagesSpoken = languages.shuffled(random).take(random.nextInt(1, 4)),
                    lastClassLabel = "${random.nextInt(1, 28)} ${months[random.nextInt(months.size)]} " +
                        "${2024 + random.nextInt(0, 3)}, ${random.nextInt(6, 21)}:${if (random.nextBoolean()) "00" else "30"}",
                    totalLessons = random.nextInt(1, 60),
                )
            } else {
                val nativeLanguage = languages[random.nextInt(languages.size)]
                val learningLanguage = languages.filter { it != nativeLanguage }.random(random)
                Student(
                    id = "student_$index",
                    name = name,
                    countryFlagEmoji = flag,
                    relation = relation,
                    possibleLearningLanguage = learningLanguage,
                    possibleLanguageLevel = random.nextInt(1, 5),
                    profileTags = profileTagPool.shuffled(random).take(random.nextInt(1, 3)),
                    languageCompetencies = listOf(
                        LanguageCompetency(nativeLanguage, isNative = true),
                        LanguageCompetency(learningLanguage, isNative = false, proficiencyLevel = random.nextInt(1, 5)),
                    ),
                )
            }
        }
    }

    fun generateChats(count: Int = 30, seed: Int = 4): List<ChatConversation> {
        val random = Random(seed)
        val messages = listOf(
            "Carol Velasquez compartió un...", "Gracias, ya se devolvieron los...", "en nuevo año",
            "Rana de cristal del sol", "hello can we use goog...", "Hi, I'm Haru. I'm lo...",
            "empalagoso", "Expedia", "¿Nos vemos mañana?", "Perfecto, gracias por la clase",
        )
        val months = listOf(
            "enero", "febrero", "marzo", "abril", "mayo", "junio",
            "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre",
        )
        return List(count) { index ->
            ChatConversation(
                id = "chat_$index",
                senderName = randomFullName(random),
                lastMessage = messages[random.nextInt(messages.size)],
                dateLabel = "${random.nextInt(1, 28)} de ${months[random.nextInt(months.size)]}" +
                    if (random.nextBoolean()) " de ${2024 + random.nextInt(0, 3)}" else "",
                isUnread = random.nextInt(100) < 20,
            )
        }
    }

    private fun IntRange.random(random: Random) = random.nextInt(first, last + 1)
}
