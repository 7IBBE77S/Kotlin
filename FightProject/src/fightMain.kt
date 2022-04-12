import java.io.File
import kotlin.random.Random
import kotlin.system.exitProcess
/************************************************************
 *  Name:         
 *  Date:         10/21/20
 *  Assignment:
 *  Class Number: 
 *  Description:
 ***********************************************************/
fun main() {
    Start()
}

class Start {
    private lateinit var fighter1File: File
    private lateinit var fighter2File: File

    private var character1String = "Load Character 1"
    private var character2String = "Load Character 2"

    init {
        displayPrompt()
    }

    private fun displayPrompt() {
        var fighter1: Fighter? = null
        var fighter2: Fighter? = null
        while (true) {
            println("1. $character1String\n2. $character2String\n3. Fight\n4. Quit")
            print("Choose an option:")
            val input = try {
                readLine()!!.toInt()
            } catch (e: NumberFormatException) {
                println("\nInvalid option! Try again.")
                continue
            }
            when (input) {
                1 -> {
                    fighter1File = filePrompt()?: fighter1File
                    fighter1 = loadAndValidateFighter(fighter1File)
                    if (fighter1 != null) character1String = "Character 1: ${fighter1.name}"
                }
                2 ->  {
                    fighter2File = filePrompt()?: fighter2File
                    fighter2 = loadAndValidateFighter(fighter2File)
                    if (fighter2 != null) character2String = "Character 2: ${fighter2.name}"
                }
                3 -> {
                    if (fighter1 == null || fighter2 == null) {
                        println("Not all characters have been loaded yet!")
                        continue
                    }
                    this.beginFight(fighter1, fighter2)
                }
                4 -> exitProcess(0)
                else -> {
                    println("\nOption out of bounds! Try again.")
                    continue
                }
            }
            println()
        }
    }

    private fun filePrompt(): File? {
        print("Enter name of character file:")
        val file = File("src/${readLine()!!}")
        if (!file.exists()) {
            println("'${file.name}' file not found!")
            return null
        }
        return file
    }

    private fun loadAndValidateFighter(file: File): Fighter? {
        val lines = file.readLines()
        val fighterParts = lines[0].split(',')
        val weaponParts = lines[1].split(',')
        val armorParts = lines[2].split(',')
        try {
            val fighterBuilder = Fighter(fighterParts[0].trim(), fighterParts[1].trim(), fighterParts[2].trim().toInt(), fighterParts[3].trim().toInt(), fighterParts[4].trim().toInt(),
                Weapon(weaponParts[0].trim(), weaponParts[1].trim().toInt()),
                Armor(armorParts[0].trim(), armorParts[1].trim().toInt()))
            return if (!fighterBuilder.isValid()) {
                println("${fighterBuilder.name} is a cheater! Couldn't load data.")
                null
            } else {
                println(fighterBuilder)
                fighterBuilder
            }
        } catch (e: NumberFormatException) {
            println("File '${file.name}' is corrupt!")
        } catch (e: IndexOutOfBoundsException) {
            println("File '${file.name}' is corrupt!")
        }
        return null
    }

    private fun beginFight(fighter1: Fighter, fighter2: Fighter) {
        val firstRollFighter1 = Dice(fighter1.agility).roll()
        val firstRollFighter2 = Dice(fighter2.agility).roll()
        var fighter1Turn = firstRollFighter1 < firstRollFighter2
        val initialTurnState = fighter1Turn
        if (fighter1Turn) println("${fighter1.name.capitalize()} won the dice roll! $firstRollFighter1 to $firstRollFighter2.")
        else println("${fighter2.name.capitalize()} won the dice roll! $firstRollFighter2 to $firstRollFighter1.")
        println()
        while (fighter1.currentHealth > 0 && fighter2.currentHealth > 0) {
            if (fighter1Turn) attack(fighter1, fighter2)
            else attack(fighter2, fighter1)
            fighter1Turn = !fighter1Turn
            if (fighter1Turn == initialTurnState && fighter1.currentHealth > 0 && fighter2.currentHealth > 0) {
                println("Press enter key to continue..")
                readLine()
            }
        }
        if (fighter1.currentHealth <= 0) println("${fighter2.name.capitalize()} wins!")
        else println("${fighter1.name.capitalize()} wins!")
        println("------------------")
        println(fighter1.getCurrentStatus())
        println(fighter2.getCurrentStatus())
        println("------------------")
        fighter1.reviveFighter()
        fighter2.reviveFighter()
    }

    private fun attack(attacker: Fighter, target: Fighter) {
        val attackerName = attacker.name.capitalize()
        println("$attackerName fights with the ${attacker.weapon}:")
        if (Dice(10).roll() < target.agility) {
            val damage = calculateDamage(attacker, target)
            if (damage == 0) {
                println(String.format("%-10s%-10s", "", "Hit: Missed!"))
                return
            }
            target.reduceHealth(damage)
            if (damage == target.currentHealth) return
            println(target.getCurrentStatus())
        } else println(String.format("%-10s%-10s", "", "Hit: Missed!"))
        println()
    }

    private fun calculateDamage(attacker: Fighter, target: Fighter): Int {
        val rawDamage = (attacker.strength * (1.0 / Dice(4).roll()) + attacker.weapon.damage / Dice(8).roll()).toInt()
        println(String.format("%-10s%-10s", "", "Hit: $rawDamage"))
        val armorReduction = target.armor.protection / Dice(15).roll()
        var deductedDamage = (rawDamage - armorReduction)
        if (deductedDamage < 0) deductedDamage = 0
        else {
            println("${target.name.capitalize()}'s armor protected against $armorReduction damage.")
            println("${attacker.name.capitalize()}'s damage is reduced to $deductedDamage.")
        }
        if (deductedDamage > target.currentHealth) deductedDamage = target.currentHealth
        return deductedDamage
    }
}


class Dice(
    private val sidesOfDice: Int,
) {
    fun roll(): Int {
        return Random.nextInt(1, sidesOfDice + 1)
    }
}

class Fighter(
    val name: String,
    private val race: String,
    private val baseHealth: Int,
    val strength: Int,
    val agility: Int,
    var weapon: Weapon,
    var armor: Armor
) {
    var currentHealth = baseHealth

    override fun toString(): String {
        return "$name is a(n) $race using ${armor.name} with $armor defense and fights with the ${weapon.name}:\n" + String.format("%-10s%-10s", "", "Damage: ${weapon.damage}")
    }

    fun getCurrentStatus(): String {
        if (currentHealth < 0) currentHealth = 0
        return "${this.name.capitalize()} has ${this.currentHealth} of ${this.baseHealth} health left!"
    }

    fun reviveFighter() {
        currentHealth = baseHealth
    }


    fun reduceHealth(amount: Int) {
        currentHealth -= amount
    }

    fun isValid(): Boolean {
        if (name.isNotBlank() && race.isNotBlank() && baseHealth > 0 && baseHealth <= 100 && strength <= 50 && agility <= 10) return true
        return false
    }
}

open class Item(val name: String)

class Weapon(name: String, val damage: Int) : Item(name) {
    override fun toString(): String {
        return name.capitalize()
    }
}

class Armor(name: String, val protection: Int) : Item(name) {
    override fun toString(): String {
        return protection.toString()
    }
}