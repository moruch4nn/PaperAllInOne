package dev.mr3n.paperallinone.commands

import dev.jorel.commandapi.CommandAPI
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style

@Throws(CommandSyntaxException::class)
fun CommandSender.failCommand(vararg components: Pair<TextColor, String>): Nothing {
    if(this is Player) { this.playSound(this, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f) }
    this.sendMessage(NamedTextColor.RED to "[!] ",*components)
    throw CommandAPI.failWithString(null)
}
fun CommandSender.successCommand(vararg components: Pair<TextColor, String>) {
    if(this is Player) { this.playSound(this, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f) }
    this.sendMessage(*components)
}

/**
 * プレイヤーに色付きメッセージを送信
 */
fun CommandSender.sendMessage(vararg msgList: Pair<TextColor,String>) {
    val msg = Component.text()
    msgList.forEach { msg.append(Component.text(it.second, Style.style(it.first))) }
    this.sendMessage(msg)
}