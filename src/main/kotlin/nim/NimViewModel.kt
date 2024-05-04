package nim

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nim.Player.PLAYER_ONE
import java.time.Instant
import kotlin.random.Random

class NimViewModel(val computation: NimComputation = NimComputationImpl()) {
    val stacks: SnapshotStateList<Int> = mutableStateListOf(10, 20, 30)
    var _player: MutableStateFlow<Player> = MutableStateFlow(PLAYER_ONE)
    val player = _player.asStateFlow()
    var _level: MutableStateFlow<Level?> = MutableStateFlow(null)
    val level = _level.asStateFlow()

    fun initGame(level: Level, stacksCount: Int, candiesCount: Int) {
        stacks.clear()
        stacks.addAll(generateStacks(stacksCount, candiesCount))
    }

    private fun generateStacks(stacksCount: Int, candiesCount: Int): List<Int> {
        val random = Random(Instant.now().toEpochMilli())
        val tempStacks =
            (1..stacksCount).map { random.nextInt(candiesCount / stacksCount / 2, candiesCount / stacksCount) }
        println(tempStacks)
        val sum = tempStacks.sum()
        println(sum)
        return tempStacks
            .map { it + (candiesCount - sum) / stacksCount }
            .mapIndexed { i, stack ->
                if (i < sum % stacksCount) stack + 1 else stack
            }
    }
}