package xyz.r2turntrue.demo

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import xyz.r2turntrue.chzzk4j.chat.ChatEventListener
import xyz.r2turntrue.chzzk4j.chat.ChatMessage

class PluginChatListener(val plugin: ChzzkDemoPlugin) : ChatEventListener {
    override fun onConnect() {
        println("Connected to chat! requesting recent chats..")
        chat.requestRecentChat(50)
    }

    private fun spawnEntitiesWithName(entityType: EntityType, name: Component) {
        plugin.server.onlinePlayers.forEach { player ->
            val entity = player.world.spawnEntity(player.location, entityType)
            entity.isCustomNameVisible = true
            entity.customName(name)
        }
    }

    override fun onChat(msg: ChatMessage) {
        plugin.server.scheduler.runTask(plugin, Runnable {
            val entityName = Component.text("<[치지직] ${msg.profile.nickname}> ${msg.content}", NamedTextColor.GREEN)
            Bukkit.broadcast(entityName)
            spawnEntitiesWithName(EntityType.WOLF, entityName)
        })
    }

    override fun onDonationChat(msg: ChatMessage) {
        plugin.server.scheduler.runTask(plugin, Runnable {
            val entityName = Component.text("<[도네이션] ${msg.profile.nickname}> ${msg.content} [${msg.extras.payAmount}원]", NamedTextColor.GOLD, TextDecoration.BOLD)
            plugin.server.showTitle(
                Title.title(
                    Component.text("${msg.profile.nickname}님이 ${msg.extras.payAmount}원 후원!", NamedTextColor.GOLD),
                    Component.text(msg.content, NamedTextColor.GOLD)
                )
            )
            Bukkit.broadcast(entityName)
            spawnEntitiesWithName(EntityType.IRON_GOLEM, entityName)
        })
    }
}