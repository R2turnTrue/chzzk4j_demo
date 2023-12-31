package xyz.r2turntrue.demo

import org.bukkit.plugin.java.JavaPlugin
import xyz.r2turntrue.chzzk4j.Chzzk
import xyz.r2turntrue.chzzk4j.ChzzkBuilder
import xyz.r2turntrue.chzzk4j.chat.ChzzkChat

lateinit var chzzk: Chzzk
lateinit var chat: ChzzkChat

class ChzzkDemoPlugin : JavaPlugin() {

    override fun onEnable() {
        chzzk = ChzzkBuilder().build()
        val channelId = config.getString("channelId")
        val channel = chzzk.getChannel(channelId)
        println("Trying connect to ${channel.channelName} (${channel.followerCount} followers)")
        chat = chzzk.chat()
        chat.addListener(PluginChatListener(this))
        saveDefaultConfig()
        chat.connectFromChannelId(channelId)
    }

    override fun onDisable() {
        chat.close()
    }
}