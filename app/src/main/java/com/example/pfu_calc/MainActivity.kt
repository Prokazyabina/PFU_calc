package com.example.pfu_calc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // 1. Объявляем все переменные для View-элементов
    private lateinit var fatInput: EditText
    private lateinit var carbsInput: EditText
    private lateinit var proteinInput: EditText
    private lateinit var pfuResult: TextView
    private lateinit var stretchTimeResult: TextView
    private lateinit var calculateButton: Button
    private lateinit var saveButton: Button
    private lateinit var historyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2. Инициализируем все View-элементы
        initializeViews()

        // 3. Настраиваем обработчики кликов
        setupClickListeners()
    }

    private fun initializeViews() {
        // Поля ввода
        fatInput = findViewById(R.id.fatInput)
        carbsInput = findViewById(R.id.carbsInput)
        proteinInput = findViewById(R.id.proteinInput)

        // Поля вывода результатов
        pfuResult = findViewById(R.id.pfuResult)
        stretchTimeResult = findViewById(R.id.stretchTimeResult)

        // Кнопки
        calculateButton = findViewById(R.id.Calculate)
        saveButton = findViewById(R.id.saveButton)
        historyButton = findViewById(R.id.historyButton)
    }

    private fun setupClickListeners() {
        calculateButton.setOnClickListener {
            calculateAndDisplayResults()
        }

        saveButton.setOnClickListener {
            Toast.makeText(this, "Сохранено!", Toast.LENGTH_SHORT).show()
        }

        historyButton.setOnClickListener {
            Toast.makeText(this, "История", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateAndDisplayResults() {
        try {
            // Получаем значения из полей ввода
            val fat = fatInput.text.toString().toDouble()
            val carbs = carbsInput.text.toString().toDouble()
            val protein = proteinInput.text.toString().toDouble()

            // Вычисляем результаты
            val pfu = calculatePFU(fat, carbs, protein)
            val stretchTime = calculateStretchTime(pfu)

            // Отображаем результаты
            displayResults(pfu, stretchTime)

        } catch (e: NumberFormatException) {
            showToast("Пожалуйста, введите корректные числа")
        } catch (e: Exception) {
            showToast("Произошла ошибка: ${e.localizedMessage}")
        }
    }

    private fun calculatePFU(fat: Double, carbs: Double, protein: Double): Int {
        val caloriesFromFat = fat * 9
        val caloriesFromCarbs = carbs * 4
        val caloriesFromProtein = protein * 4
        return ((caloriesFromFat + caloriesFromCarbs + caloriesFromProtein) / 100).toInt()
    }

    private fun calculateStretchTime(pfu: Int): Int {
        return when {
            pfu == 1 -> 3
            pfu == 2 -> 4
            pfu == 3 -> 5
            pfu >= 4 -> 5 + (pfu - 4) * 2
            else -> 0
        }
    }

    private fun displayResults(pfu: Int, stretchTime: Int) {
        pfuResult.text = "Количество БЖЕ: $pfu"
        stretchTimeResult.text = "Время для растяжки: $stretchTime часов"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}