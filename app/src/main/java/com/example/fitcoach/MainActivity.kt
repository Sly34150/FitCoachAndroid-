package com.example.fitcoach

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import java.util.Locale

 data class Exercise(val name: String, val detail: String, val seconds: Int, val reps: String)

class MainActivity : Activity(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private var ttsReady = false
    private var timer: CountDownTimer? = null
    private var remaining = 30
    private var index = 0
    private var stretching = false
    private lateinit var title: TextView
    private lateinit var instruction: TextView
    private lateinit var timerText: TextView
    private lateinit var illustration: ExerciseIllustration
    private lateinit var exercises: List<Exercise>

    private val gymExercises = listOf(
        Exercise("Presse pectorale machine", "Régle le siège. Garde les poignées à hauteur de poitrine. Pousse sans verrouiller les coudes, puis reviens lentement. 2 séries de 10 à 12 répétitions.", 45, "10–12 répétitions"),
        Exercise("Tirage vertical", "Prends la barre un peu plus large que les épaules. Tire vers le haut de la poitrine en gardant le buste stable, puis remonte contrôlé. 2 séries de 10 à 12.", 45, "10–12 répétitions"),
        Exercise("Presse à jambes", "Place les pieds à largeur d'épaules. Descends sans décoller le bassin, puis pousse avec les pieds. Ne verrouille pas les genoux. 2 séries de 12.", 45, "12 répétitions"),
        Exercise("Développé épaules haltères", "Assis, haltères à hauteur des épaules. Pousse vers le haut sans cambrer, puis redescends lentement. Choisis une charge légère. 2 séries de 8 à 10.", 45, "8–10 répétitions"),
        Exercise("Gainage avant-bras", "Coudes sous les épaules, corps aligné. Contracte les abdos et respire normalement. Maintiens 20 à 30 secondes.", 30, "20–30 secondes")
    )
    private val homeExercises = listOf(
        Exercise("Squats au poids du corps", "Pieds à largeur d'épaules. Recule les hanches comme pour t'asseoir, garde les genoux dans l'axe des pieds, puis remonte. 2 séries de 8 à 12.", 40, "8–12 répétitions"),
        Exercise("Pompes inclinées", "Mains sur un support stable. Corps aligné, descends la poitrine vers le support puis repousse. 2 séries de 6 à 10.", 40, "6–10 répétitions"),
        Exercise("Pont fessier", "Allongé sur le dos, pieds au sol. Pousse dans les talons et monte le bassin sans cambrer, puis redescends. 2 séries de 12.", 40, "12 répétitions"),
        Exercise("Bird-dog", "À quatre pattes, tends doucement bras droit et jambe gauche. Garde le bassin stable, alterne. 8 de chaque côté.", 40, "8 par côté"),
        Exercise("Gainage avant-bras", "Coudes sous les épaules, corps aligné. Contracte les abdos et respire. Maintiens 20 à 30 secondes.", 30, "20–30 secondes")
    )
    private val stretches = listOf(
        Exercise("Étirement des pectoraux", "Place l'avant-bras contre un mur ou un encadrement. Tourne doucement le buste jusqu'à sentir l'étirement devant l'épaule. Sans douleur.", 30, "30 secondes par côté"),
        Exercise("Étirement des épaules", "Amène un bras devant la poitrine et rapproche-le doucement avec l'autre bras. Garde l'épaule basse.", 30, "30 secondes par côté"),
        Exercise("Étirement du dos", "À quatre pattes, pousse les hanches vers les talons et allonge les bras devant toi. Respire lentement.", 40, "40 secondes"),
        Exercise("Étirement des quadriceps", "Debout près d'un appui, attrape la cheville et rapproche le talon des fesses. Garde les genoux proches.", 30, "30 secondes par côté"),
        Exercise("Étirement des mollets", "Mains au mur, une jambe derrière, talon au sol. Plie légèrement la jambe avant et avance le bassin.", 30, "30 secondes par côté")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tts = TextToSpeech(this, this)
        val days = findViewById<Spinner>(R.id.daysSpinner)
        val duration = findViewById<Spinner>(R.id.durationSpinner)
        val location = findViewById<Spinner>(R.id.locationSpinner)
        days.adapter = adapter(listOf("2 jours", "3 jours", "4 jours", "5 jours", "6 jours"))
        duration.adapter = adapter(listOf("20 minutes", "30 minutes", "40 minutes", "45 minutes", "60 minutes"))
        location.adapter = adapter(listOf("Salle de sport", "Maison sans matériel", "Mixte"))
        val plan = findViewById<TextView>(R.id.planText)
        title = findViewById(R.id.exerciseTitle)
        instruction = findViewById(R.id.instructionText)
        timerText = findViewById(R.id.timerText)
        illustration = findViewById(R.id.illustration)
        exercises = gymExercises
        showExercise()

        findViewById<Button>(R.id.generateButton).setOnClickListener {
            val n = days.selectedItemPosition + 2
            val mins = listOf(20,30,40,45,60)[duration.selectedItemPosition]
            val place = location.selectedItem.toString()
            val lines = (1..n).map { d ->
                val label = when {
                    place == "Maison sans matériel" -> if (d % 2 == 1) "Renforcement au poids du corps + abdos" else "Mobilité, cardio doux et gainage"
                    place == "Mixte" -> if (d % 2 == 1) "Salle : haut du corps + abdos" else "Maison : jambes, cardio doux ou étirements"
                    else -> when ((d-1)%3) { 0 -> "Salle : haut du corps + abdos"; 1 -> "Salle : jambes + cardio doux"; else -> "Salle : full body + mobilité" }
                }
                "Jour $d — $label ($mins min)"
            }
            plan.text = "Ton planning débutant\n\n${lines.joinToString("\n\n")}\n\nGarde au moins un jour de récupération entre les séances sollicitant les mêmes muscles."
            say("Programme créé. $n jours par semaine, séances de $mins minutes. Tu peux commencer quand tu es prêt.")
        }
        findViewById<Button>(R.id.startButton).setOnClickListener { startTimer() }
        findViewById<Button>(R.id.nextButton).setOnClickListener { timer?.cancel(); index = (index + 1) % exercises.size; showExercise(); say("Exercice suivant. ${exercises[index].name}. ${exercises[index].detail}") }
        findViewById<Button>(R.id.stretchButton).setOnClickListener {
            stretching = !stretching
            exercises = if (stretching) stretches else if (location.selectedItemPosition == 1) homeExercises else gymExercises
            index = 0
            showExercise()
            say(if (stretching) "Séance d'étirements à la maison. ${exercises[0].name}. ${exercises[0].detail}" else "Retour aux exercices de renforcement. ${exercises[0].name}")
        }
    }

    private fun adapter(items: List<String>) = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

    private fun showExercise() {
        val e = exercises[index]
        title.text = "${index + 1}/${exercises.size} • ${e.name}"
        instruction.text = "${e.detail}\n\n${e.reps}"
        remaining = e.seconds
        timerText.text = format(remaining)
        illustration.setExercise(index, stretching)
    }

    private fun startTimer() {
        timer?.cancel()
        val e = exercises[index]
        say("On commence : ${e.name}. ${e.detail}")
        remaining = e.seconds
        timer = object : CountDownTimer(e.seconds * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                remaining = (millisUntilFinished / 1000L).toInt()
                timerText.text = format(remaining)
                if (remaining == 10 || remaining == 5 || remaining == 3) say("$remaining")
            }
            override fun onFinish() {
                timerText.text = "00:00"
                say("Temps terminé. Prends une pause, puis appuie sur suivant.")
                Toast.makeText(this@MainActivity, "Temps terminé", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun format(s: Int) = String.format(Locale.getDefault(), "%02d:%02d", s / 60, s % 60)
    private fun say(text: String) { if (ttsReady) tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "fitcoach") }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.FRANCE)
            ttsReady = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
        }
    }
    override fun onDestroy() { timer?.cancel(); if (::tts.isInitialized) { tts.stop(); tts.shutdown() }; super.onDestroy() }
}
