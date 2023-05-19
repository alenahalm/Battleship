package com.example.battleship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var table: TableLayout
    lateinit var button: Button

    private val rows = 10
    private val cols = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        table = findViewById(R.id.tableLayout)
        button = findViewById(R.id.generate)

        for (i in 0 until rows) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            for (j in 0 until cols) {
                val image = ImageView(this)
                image.setImageResource(R.drawable.square_white)
                tableRow.addView(image, j)
            }
            table.addView(tableRow)
        }

        button.setOnClickListener {
            generate()
        }

    }

    private fun generate() {
        var T = 4
        var x = 0
        var y = 0
        var direction = 0

        val field = Array(10) {IntArray(10) {0} }

        while (T != 0) {
            for (i in 0 until 5-T) {
                x = Random.nextInt(0, 10)
                y = Random.nextInt(0, 10)
                direction = Random.nextInt(0,2)

                while (!check(field, x, y, direction, T)){
                    x = Random.nextInt(0, 10)
                    y = Random.nextInt(0, 10)
                    direction = Random.nextInt(0,2)
                }
                fill(field, x, y, direction, T)
            }
            T -= 1
        }

        for (i in 0 until rows) {
            val tableRow = table.getChildAt(i) as TableRow
            for (j in 0 until cols) {
                val image = tableRow.getChildAt(j) as ImageView
                if (field[i][j] == 1) {
                    image.setImageResource(R.drawable.square_black)
                } else {
                    image.setImageResource(R.drawable.square_white)
                }
            }
        }
    }

    private fun check(field: Array<IntArray>, x: Int, y: Int, dir: Int, len: Int): Boolean {
        if (dir == 0) {
            if (x - 1 < 0 || x+len > 9 || y-1 < 0 || y+1 > 9) {
                return false
            }
            for (i in x-1 .. x+len) {
                for (j in y-1 .. y+1) {
                    if (field[i][j] == 1) {
                        return false
                    }
                }
            }
        } else {
            if (x-1 < 0 || x+1 > 9 || y-1 < 0 || y+len > 9) {
                return false
            }
            for (i in x-1 .. x+1) {
                for (j in y-1 .. y+len) {
                    if(field[i][j] == 1) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun fill(field: Array<IntArray>, x: Int, y: Int, dir: Int, len: Int) {
        if (dir == 0) {
            for (i in x until x+len) {
                field[i][y] = 1
            }
        }
        if (dir == 1) {
            for (j in y until y+len) {
                field[x][j] = 1
            }
        }
    }
}