package xyz.r2turntrue.demo

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import xyz.r2turntrue.chzzk4j.chat.*
import java.lang.Exception

class PluginChatListener(val plugin: ChzzkDemoPlugin) : ChatEventListener {
    override fun onConnect(chat: ChzzkChat, isReconnecting: Boolean) {
        println("Connected to chat! requesting recent chats..")
        chat.requestRecentChat(50)
    }

    override fun onError(ex: Exception) {
        ex.printStackTrace()
    }


    override fun onChat(msg: ChatMessage) {
        plugin.server.scheduler.runTask(plugin, Runnable {
            val nickname = msg.profile?.nickname ?: "익명"

            val entityName = Component.text("<[치지직] $nickname> ${msg.content}", NamedTextColor.GREEN)
            Bukkit.broadcast(entityName)
        })
    }

    override fun onDonationChat(msg: DonationMessage) {
        plugin.server.scheduler.runTask(plugin, Runnable {
            val nickname = msg.profile?.nickname ?: "익명"

            plugin.server.showTitle(
                Title.title(
                    Component.text("${nickname}님이 ${msg.payAmount}원 후원!", NamedTextColor.GOLD),
                    Component.text(msg.content, NamedTextColor.GOLD)
                )
            )
            Bukkit.broadcast(Component
                .text(
                    "<[도네이션] $nickname> ${msg.content} [${msg.payAmount}원]",
                    NamedTextColor.GOLD,
                    TextDecoration.BOLD))
        })
    }

    override fun onSubscriptionChat(msg: SubscriptionMessage) {
        plugin.server.scheduler.runTask(plugin, Runnable {
            val nickname = msg.profile?.nickname ?: "익명"

            plugin.server.showTitle(
                Title.title(
                    Component.text("${nickname}님이 ${msg.subscriptionMonth}개월 구독!", NamedTextColor.GOLD),
                    Component.text(msg.subscriptionTierName, NamedTextColor.GOLD)
                )
            )
            Bukkit.broadcast(Component
                .text(
                    "<[구독] $nickname> ${msg.subscriptionTierName}를 ${msg.subscriptionMonth}개월 구독!",
                    NamedTextColor.GOLD,
                    TextDecoration.BOLD))
        })
    }
}