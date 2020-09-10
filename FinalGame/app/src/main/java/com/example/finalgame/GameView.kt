package com.example.finalgame

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout

class GameView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : GridLayout(context, attrs, defStyleAttr) {
    var size = 4
        set(size) {
            field = size
            removeAllViews()
            columnCount = size
            BlocksMap = Array(size) { Array(size) { Block(context, size) } }

            BlocksMap.deepForEach { addView(it, width / size, width / size) }
            startGame()
        }
    private lateinit var BlocksMap: Array<Array<Block>>
    private fun Array<Array<Block>>.deepForEach(action: (Block) -> Unit) = this.forEach { it.forEach { action(it) } }

    init {
        setBackgroundColor(Color.rgb(187, 173, 160)) /* standard silk background color */
        setOnTouchListener(object : View.OnTouchListener {
            private var startX = 0f
            private var startY = 0f
            private var offsetX = 0f
            private var offsetY = 0f

            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = motionEvent.x
                        startY = motionEvent.y
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        offsetX = motionEvent.x - startX
                        offsetY = motionEvent.y - startY
                        when {
                            (Math.abs(offsetX) > Math.abs(offsetY)) && offsetX < -5 -> swipeLeft()
                            (Math.abs(offsetX) > Math.abs(offsetY)) && offsetX > 5 -> swipeRight()
                            (Math.abs(offsetX) < Math.abs(offsetY)) && offsetY < -5 -> swipeUp()
                            (Math.abs(offsetX) < Math.abs(offsetY)) && offsetY > 5 -> swipeDown()
                        }
                    }
                }
                return true
            }
        })
        post { size = size }
    }

    fun startGame() {
        GameActivity.gameActivity!!.score = 0
        BlocksMap.deepForEach { it.num = 0 }
        repeat(2) { addBlock() }
    }

    private fun addBlock() {
        val emptyBlock = mutableListOf<Block>()
        BlocksMap.forEach { emptyBlock += it.filter { it.num <= 0 } } /*possible block*/
        emptyBlock[(Math.random() * emptyBlock.size).toInt()].num = if (Math.random() > 0.5) 2 else 4
        canMove()
    }

    private fun canMove() {
        for (x in 0 until 4) {
            for (y in 0 until 4) {
                val num = BlocksMap[x][y].num
                if (num <= 0 ||
                    x < 3 && num == BlocksMap[x + 1][y].num ||
                    y < 3 && num == BlocksMap[x][y + 1].num ) {
                    return
                }
            }
        } //check can move anymore

        val intent = Intent(context, SubmitActivity::class.java)
        context.startActivity(intent)
    }

    private fun checkMerge(b1: Block, b2: Block): Int {
        val num1 = b1.num
        val num2 = b2.num
        if (num1 <= 0) {
            b1.num = num2
            b2.num = 0
            return 1
        } else if (num1 == num2) {
            b1.num = num1 * 2
            b2.num = 0
            GameActivity.gameActivity!!.score += num1 * 2
            return 2
        }
        return 0
    }

    private fun swipeLeft() {
        var move = false
        for (x in 0 until 4) {
            var y = 0
            while (y < 4) {
                for (y1 in y + 1 until 4) {
                    if (BlocksMap[x][y1].num > 0) {
                        val check = checkMerge(BlocksMap[x][y], BlocksMap[x][y1])
                        if (move || check == 1 || check == 2){
                            move = true;
                            if (check == 1){
                                y -= 1;
                            }
                        }

                        //move = move || check == 1 || check == 2
                        //y -= if (check == 1) 1 else 0
                        break
                    }
                }
                y++

            }
        }
        if (move) addBlock()
    }

    private fun swipeRight() {
        var move = false
        for (x in 0 until size) {
            var y = size - 1
            while (y >= 0) {

                for (y1 in y - 1 downTo 0) {
                    if (BlocksMap[x][y1].num > 0) {

                        val check = checkMerge(BlocksMap[x][y], BlocksMap[x][y1])
                        move = move || check == 1 || check == 2
                        y += if (check == 1) 1 else 0

                        break
                    }
                }
                y--

            }
        }
        if (move) addBlock()
    }

    private fun swipeUp() {
        var move = false
        for (y in 0 until size) {
            var x = 0
            while (x < size) {

                for (x1 in x + 1 until size) {
                    if (BlocksMap[x1][y].num > 0) {
                        val check = checkMerge(BlocksMap[x][y], BlocksMap[x1][y])
                        move = move || check == 1 || check == 2
                        x -= if (check == 1) 1 else 0

                        break
                    }
                }
                x++
            }
        }
        if (move) addBlock()
    }

    private fun swipeDown() {
        var move = false
        for (y in 0 until size) {
            var x = size - 1
            while (x >= 0) {

                for (x1 in x - 1 downTo 0) {
                    if (BlocksMap[x1][y].num > 0) {
                        val check = checkMerge(BlocksMap[x][y], BlocksMap[x1][y])
                        move = move || check == 1 || check == 2
                        x += if (check == 1) 1 else 0
                        break
                    }
                }
                x--

            }
        }
        if (move) addBlock()
    }


}
