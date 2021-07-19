package mehdi.sakout.aboutpage.sample

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.*

// https://github.com/medyo/android-about-page
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adsElement = Element()
        adsElement.title = "Advertise with us"
        val aboutPage = AboutPage(this)
            .isRTL(false)
            .setImage(R.drawable.dummy_image)
            .addItem(Element().setTitle("Version 6.2"))
            .addItem(adsElement)
            .addGroup("Connect with us")
            .addEmail("elmehdi.sakout@gmail.com")
            .addWebsite("https://mehdisakout.com/")
            .addFacebook("the.medy")
            .addTwitter("medyo80")
            .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
            .addPlayStore("com.ideashower.readitlater.pro")
            .addInstagram("medyo80")
            .addGitHub("medyo")
            .addItem(copyRightsElement)
            .create()
        setContentView(aboutPage)
    }

    private val copyRightsElement: Element
        get() {
            val copyRightsElement = Element()
            val copyrights =
                String.format(getString(R.string.copy_right), Calendar.getInstance()[Calendar.YEAR])
            copyRightsElement.title = copyrights
            copyRightsElement.iconDrawable = R.drawable.about_icon_copy_right
            copyRightsElement.autoApplyIconTint = true
            copyRightsElement.iconTint = mehdi.sakout.aboutpage.R.color.about_item_icon_color
            copyRightsElement.iconNightTint = android.R.color.white
            copyRightsElement.gravity = Gravity.CENTER
            copyRightsElement.onClickListener = View.OnClickListener {
                Toast.makeText(this@MainActivity, copyrights, Toast.LENGTH_SHORT ).show()
            }
            return copyRightsElement
        }
}