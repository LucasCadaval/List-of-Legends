package br.uri.listoflegends.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import br.uri.listoflegends.models.ChampionModel
import br.uri.listoflegends.services.getImageFromUrl
import java.io.File
import java.io.FileOutputStream


fun share(context: Context, textToShare: String, bitmap: Bitmap?) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = if (bitmap != null) "image/*" else "text/plain"
        putExtra(Intent.EXTRA_TEXT, textToShare)

        bitmap?.let {
            val imageUri = getImageUriFromBitmap(context, it)
            putExtra(Intent.EXTRA_STREAM, imageUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    context.startActivity(Intent.createChooser(intent, "Share via"))
}
fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
    val cachePath = File(context.cacheDir, "images")
    cachePath.mkdirs()

    val file = File(cachePath, "shared_image.png")
    val fileOutputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
    fileOutputStream.close()

    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}

fun formatChampionForSharing(champion: ChampionModel): String {
    val gold = "🟡"
    val stats = champion.stats?.let { stats ->
        """
        
        - HP: ${stats.hp}
        - HP per Level: ${stats.hpperlevel}
        - MP: ${stats.mp}
        - MP per Level: ${stats.mpperlevel}
        - Move Speed: ${stats.movespeed}
        - Armor: ${stats.armor}
        - Armor per Level: ${stats.armorperlevel}
        - Spell Block: ${stats.spellblock}
        - Spell Block per Level: ${stats.spellblockperlevel}
        - Attack Range: ${stats.attackrange}
        - HP Regen: ${stats.hpregen}
        - HP Regen per Level: ${stats.hpregenperlevel}
        - MP Regen: ${stats.mpregen}
        - MP Regen per Level: ${stats.mpregenperlevel}
        - Critical Chance: ${stats.crit}%
        - Critical Chance per Level: ${stats.critperlevel}%
        - Attack Damage: ${stats.attackdamage}
        - Attack Damage per Level: ${stats.attackdamageperlevel}
        - Attack Speed: ${stats.attackspeed}
        - Attack Speed per Level: ${stats.attackspeedperlevel}
        """.trimIndent()
    } ?: "No stats available"

    return """
        🌟 ${champion.name} - ${champion.title ?: "No Title"}
        
        ${champion.description ?: "No description available"}
        
$gold Stats:
        $stats
    """.trimIndent()
}
