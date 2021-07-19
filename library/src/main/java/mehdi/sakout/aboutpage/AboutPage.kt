package mehdi.sakout.aboutpage

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import mehdi.sakout.aboutpage.AboutPage

/**
 * The main class of this library with many predefined methods to add Elements for common items in
 * an About page. This class creates a [android.view.View] that can be passed as the root view
 * in {link Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)} or passed to the [android.app.Activity.setContentView]
 * in an activity's {link android.app.Activity#onCreate(Bundle)} (Bundle)} method
 * Основной класс этой библиотеки со множеством предопределенных методов для добавления элементов для общих элементов на
 * странице о программе. Этот класс создает {link android.view.Представление}, которое может быть передано как корневое представление
 * в {фрагмент ссылки#onCreateView(LayoutInflater, ViewGroup, Пакет)} или передается в {ссылка android.app.Действие#setContentView(Просмотр)}
 * в методе действия {ссылка android.app.Действие#onCreate(пакет)} (Пакет)}
 *
 *
 * To create a custom item in the about page, pass an instance of [mehdi.sakout.aboutpage.Element]
 * to the [AboutPage.addItem] method.
 * Чтобы создать пользовательский элемент на странице "О программе", передайте экземпляр страницы {link mehdi.saki.about.Элемент}
 * к методу {ссылка о странице#addItem(Элемент)}.
 *
 * @see Element
 */
class AboutPage @JvmOverloads constructor(
    context: Context?, @StyleRes style: Int = AboutPageUtils.resolveResIdAttr(
        context!!, R.attr.aboutStyle, R.style.about_About
    )
) {
    private val mContext: Context
    private val mInflater: LayoutInflater
    private val mView: View
    private var mDescription: CharSequence? = null
    private var mImage = 0
    private var mIsRTL = false
    private var mCustomFont: Typeface? = null

    // Java Конструктор 2
    constructor(context: Context?, forceEnableDarkMode: Boolean) : this(
        context,
        if (forceEnableDarkMode) R.style.about_AboutBase_Dark else R.style.about_AboutBase_Light
    ) {
    }
    // Дальше описаны Методы этого класса AboutPage
    /**
     * Provide a valid path to a font here to use another font for the text inside this AboutPage
     * Укажите здесь допустимый путь к шрифту, чтобы использовать другой шрифт для текста на этой странице о программе
     *
     * @param path
     * @return this AboutPage instance for builder pattern support
     */
    fun setCustomFont(path: String?): AboutPage {
        //TODO: check if file exists
        mCustomFont = Typeface.createFromAsset(mContext.assets, path)
        return this
    }

    /**
     * Provide a typeface to use as custom font
     *
     * @param typeface
     * @return this AboutPage instance for builder pattern support
     */
    fun setCustomFont(typeface: Typeface?): AboutPage {
        mCustomFont = typeface
        return this
    }
    /**
     * Add a predefined Element that opens the users default email client with a new email to the
     * email address passed as parameter
     *
     * @param email the email address to send to
     * @param title the title string to display on this item
     * @return this AboutPage instance for builder pattern support
     */
    /**
     * Convenience method for [AboutPage.addEmail] but with
     * a predefined title string
     *
     * @param email the email address to send to
     * @return this AboutPage instance for builder pattern support
     */
    @JvmOverloads
    fun addEmail(
        email: String,
        title: String? = mContext.getString(R.string.about_contact_us)
    ): AboutPage {
        val emailElement = Element()
        emailElement.title = title
        emailElement.iconDrawable = R.drawable.about_icon_email
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailElement.intent = intent
        addItem(emailElement)
        return this
    }
    /**
     * Add a predefined Element that the opens Facebook app with a deep link to the specified user id
     * If the Facebook application is not installed this will open a web page instead.
     *
     * @param id    the id of the Facebook user to display in the Facebook app
     * @param title the title to display on this item
     * @return this AboutPage instance for builder pattern support
     */
    /**
     * Convenience method for [AboutPage.addFacebook] but with
     * a predefined title string
     *
     * @param id the facebook id to display
     * @return this AboutPage instance for builder pattern support
     */
    @JvmOverloads
    fun addFacebook(
        id: String,
        title: String? = mContext.getString(R.string.about_facebook)
    ): AboutPage {
        val facebookElement = Element()
        facebookElement.title = title
        facebookElement.iconDrawable = R.drawable.about_icon_facebook
        facebookElement.iconTint = R.color.about_facebook_color
        facebookElement.value = id
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        if (AboutPageUtils.isAppInstalled(mContext, "com.facebook.katana")) {
            intent.setPackage("com.facebook.katana")
            var versionCode = 0
            try {
                versionCode =
                    mContext.packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            if (versionCode >= 3002850) {
                val uri = Uri.parse("fb://facewebmodal/f?href=http://m.facebook.com/$id")
                intent.data = uri
            } else {
                val uri = Uri.parse("fb://page/$id")
                intent.data = uri
            }
        } else {
            intent.data = Uri.parse("http://m.facebook.com/$id")
        }
        facebookElement.intent = intent
        addItem(facebookElement)
        return this
    }
    /**
     * Add a predefined Element that the opens the Twitter app with a deep link to the specified user id
     * If the Twitter application is not installed this will open a web page instead.
     *
     * @param id    the id of the Twitter user to display in the Twitter app
     * @param title the title to display on this item
     * @return this AboutPage instance for builder pattern support
     */
    /**
     * Convenience method for [AboutPage.addTwitter] but with
     * a predefined title string
     *
     * @param id the Twitter id to display
     * @return this AboutPage instance for builder pattern support
     */
    @JvmOverloads
    fun addTwitter(
        id: String?,
        title: String? = mContext.getString(R.string.about_twitter)
    ): AboutPage {
        val twitterElement = Element()
        twitterElement.title = title
        twitterElement.iconDrawable = R.drawable.about_icon_twitter
        twitterElement.iconTint = R.color.about_twitter_color
        twitterElement.value = id
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        if (AboutPageUtils.isAppInstalled(mContext, "com.twitter.android")) {
            intent.setPackage("com.twitter.android")
            intent.data = Uri.parse(String.format("twitter://user?screen_name=%s", id))
        } else {
            intent.data = Uri.parse(
                String.format(
                    "http://twitter.com/intent/user?screen_name=%s",
                    id
                )
            )
        }
        twitterElement.intent = intent
        addItem(twitterElement)
        return this
    }
    /**
     * Add a predefined Element that the opens the PlayStore app with a deep link to the
     * specified app application id.
     *
     * @param id    the package id of the app to display
     * @param title the title to display on this item
     * @return this AboutPage instance for builder pattern support
     */
    /**
     * Convenience method for [AboutPage.addPlayStore] but with
     * a predefined title string
     *
     * @param id the package id of the app to display
     * @return this AboutPage instance for builder pattern support
     */
    @JvmOverloads
    fun addPlayStore(
        id: String,
        title: String? = mContext.getString(R.string.about_play_store)
    ): AboutPage {
        val playStoreElement = Element()
        playStoreElement.title = title
        playStoreElement.iconDrawable = R.drawable.about_icon_google_play
        playStoreElement.iconTint = R.color.about_play_store_color
        playStoreElement.value = id
        val uri = Uri.parse("https://play.google.com/store/apps/details?id=$id")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        playStoreElement.intent = goToMarket
        addItem(playStoreElement)
        return this
    }
    /**
     * Add a predefined Element that the opens the Youtube app with a deep link to the
     * specified channel id.
     *
     *
     * If the Youtube app is not installed this will open the Youtube web page instead.
     *
     * @param id    the id of the channel to deep link to
     * @param title the title to display on this item
     * @return this AboutPage instance for builder pattern support
     */
    /**
     * Convenience method for [AboutPage.addYoutube] but with
     * a predefined title string
     *
     * @param id the id of the channel to deep link to
     * @return this AboutPage instance for builder pattern support
     */
    @JvmOverloads
    fun addYoutube(
        id: String?,
        title: String? = mContext.getString(R.string.about_youtube)
    ): AboutPage {
        val youtubeElement = Element()
        youtubeElement.title = title
        youtubeElement.iconDrawable = R.drawable.about_icon_youtube
        youtubeElement.iconTint = R.color.about_youtube_color
        youtubeElement.value = id
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(String.format("http://youtube.com/channel/%s", id))
        if (AboutPageUtils.isAppInstalled(mContext, "com.google.android.youtube")) {
            intent.setPackage("com.google.android.youtube")
        }
        youtubeElement.intent = intent
        addItem(youtubeElement)
        return this
    }
    /**
     * Add a predefined Element that the opens the Instagram app with a deep link to the
     * specified user id.
     *
     *
     * If the Instagram app is not installed this will open the Intagram web page instead.
     *
     * @param id    the user id to deep link to
     * @param title the title to display on this item
     * @return this AboutPage instance for builder pattern support
     */
    /**
     * Convenience method for [AboutPage.addInstagram] (String, String)} but with
     * a predefined title string
     *
     * @param id the id of the instagram user to deep link to
     * @return this AboutPage instance for builder pattern support
     */
    @JvmOverloads
    fun addInstagram(
        id: String,
        title: String? = mContext.getString(R.string.about_instagram)
    ): AboutPage {
        val instagramElement = Element()
        instagramElement.title = title
        instagramElement.iconDrawable = R.drawable.about_icon_instagram
        instagramElement.iconTint = R.color.about_instagram_color
        instagramElement.value = id
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse("http://instagram.com/_u/$id")
        if (AboutPageUtils.isAppInstalled(mContext, "com.instagram.android")) {
            intent.setPackage("com.instagram.android")
        }
        instagramElement.intent = intent
        addItem(instagramElement)
        return this
    }
    /**
     * Add a predefined Element that the opens the a browser and displays the specified GitHub
     * users profile page.
     *
     * @param id    the GitHub user to link to
     * @param title the title to display on this item
     * @return this AboutPage instance for builder pattern support
     */
    /**
     * Convenience method for [AboutPage.addGitHub] but with
     * a predefined title string
     *
     * @param id the id of the GitHub user to display
     * @return this AboutPage instance for builder pattern support
     */
    @JvmOverloads
    fun addGitHub(
        id: String?,
        title: String? = mContext.getString(R.string.about_github)
    ): AboutPage {
        val gitHubElement = Element()
        gitHubElement.title = title
        gitHubElement.iconDrawable = R.drawable.about_icon_github
        gitHubElement.iconTint = R.color.about_github_color
        gitHubElement.value = id
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse(String.format("https://github.com/%s", id))
        gitHubElement.intent = intent
        addItem(gitHubElement)
        return this
    }
    /**
     * Add a predefined Element that the opens a browser and loads the specified URL
     *
     * @param url   the URL to open in a browser
     * @param title the title to display on this item
     * @return this AboutPage instance for builder pattern support
     */
    /**
     * Convenience method for [AboutPage.addWebsite] but with
     * a predefined title string
     *
     * @param url the URL to open in a browser
     * @return this AboutPage instance for builder pattern support
     */
    @JvmOverloads
    fun addWebsite(
        url: String,
        title: String? = mContext.getString(R.string.about_website)
    ): AboutPage {
        var url = url
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://$url"
        }
        val websiteElement = Element()
        websiteElement.title = title
        websiteElement.iconDrawable = R.drawable.about_icon_link
        websiteElement.value = url
        val uri = Uri.parse(url)
        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        websiteElement.intent = browserIntent
        addItem(websiteElement)
        return this
    }

    /**
     * Add a custom [Element] to this AboutPage
     *
     * @param element
     * @return this AboutPage instance for builder pattern support
     * @see Element
     */
    fun addItem(element: Element): AboutPage {
        val wrapper = mView.findViewById<LinearLayout>(R.id.about_providers)
        wrapper.addView(createItem(element))
        wrapper.addView(
            separator,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                mContext.resources.getDimensionPixelSize(R.dimen.about_separator_height)
            )
        )
        return this
    }

    /**
     * Set the header image to display in this AboutPage
     *
     * @param resource the resource id of the image to display
     * @return this AboutPage instance for builder pattern support
     */
    fun setImage(@DrawableRes resource: Int): AboutPage {
        mImage = resource
        return this
    }

    /**
     * Add a new group that will display a header in this AboutPage
     *
     *
     * A header will be displayed in the order it was added. For e.g:
     *
     *
     * `
     * new AboutPage(this)
     * .addItem(firstItem)
     * .addGroup("Header")
     * .addItem(secondItem)
     * .create();
    ` *
     *
     *
     * Will display the following
     * [First item]
     * [Header]
     * [Second item]
     *
     * @param name the title for this group
     * @return this AboutPage instance for builder pattern support
     */
    fun addGroup(name: String?): AboutPage {
        val textView = TextView(mContext)
        textView.text = name
        TextViewCompat.setTextAppearance(
            textView,
            AboutPageUtils.resolveResIdAttr(
                mContext,
                R.attr.aboutGroupTextAppearance,
                R.style.about_groupTextAppearance
            )
        )
        val textParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        if (mCustomFont != null) {
            textView.typeface = mCustomFont
        }
        val padding = mContext.resources.getDimensionPixelSize(R.dimen.about_group_text_padding)
        textView.setPadding(padding, padding, padding, padding)
        if (mIsRTL) {
            textView.gravity = Gravity.END or Gravity.CENTER_VERTICAL
            textParams.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        } else {
            textView.gravity = Gravity.START or Gravity.CENTER_VERTICAL
            textParams.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        }
        textView.layoutParams = textParams
        (mView.findViewById<View>(R.id.about_providers) as LinearLayout).addView(textView)
        return this
    }

    /**
     * Turn on the RTL mode.
     *
     * @param value
     * @return this AboutPage instance for builder pattern support
     */
    fun isRTL(value: Boolean): AboutPage {
        mIsRTL = value
        return this
    }

    fun setDescription(description: CharSequence?): AboutPage {
        mDescription = description
        return this
    }

    /**
     * Create and inflate this AboutPage. After this method is called the AboutPage
     * cannot be customized any more.
     *
     * @return the inflated [View] of this AboutPage
     */
    fun create(): View {
        val description = mView.findViewById<TextView>(R.id.description)
        val image = mView.findViewById<ImageView>(R.id.image)
        if (mImage > 0) {
            image.setImageResource(mImage)
        }
        if (!TextUtils.isEmpty(mDescription)) {
            description.text = mDescription
        }
        if (mCustomFont != null) {
            description.typeface = mCustomFont
        }
        return mView
    }

    private fun createItem(element: Element): View {
        val wrapper = LinearLayout(mContext)
        wrapper.orientation = LinearLayout.HORIZONTAL
        wrapper.isClickable = true
        if (element.onClickListener != null) {
            wrapper.setOnClickListener(element.onClickListener)
        } else if (element.intent != null) {
            wrapper.setOnClickListener {
                try {
                    mContext.startActivity(element.intent)
                } catch (e: ActivityNotFoundException) {
                    Log.e(LOG_TAG, "failed to launch intent for '" + element.title + "' element", e)
                }
            }
        }
        wrapper.setBackgroundResource(
            AboutPageUtils.resolveResIdAttr(
                mContext,
                R.attr.selectableItemBackground,
                android.R.color.transparent
            )
        )
        val padding = mContext.resources.getDimensionPixelSize(R.dimen.about_text_padding)
        wrapper.setPadding(padding, padding, padding, padding)
        val wrapperParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        wrapper.layoutParams = wrapperParams
        val textView = TextView(mContext)
        TextViewCompat.setTextAppearance(
            textView,
            AboutPageUtils.resolveResIdAttr(
                mContext,
                R.attr.aboutElementTextAppearance,
                R.style.about_elementTextAppearance
            )
        )
        val textParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = textParams
        if (mCustomFont != null) {
            textView.typeface = mCustomFont
        }
        var iconView: ImageView? = null
        if (element.iconDrawable != null) {
            iconView = ImageView(mContext)
            val size = mContext.resources.getDimensionPixelSize(R.dimen.about_icon_size)
            val iconParams = LinearLayout.LayoutParams(size, size)
            iconView.layoutParams = iconParams
            val iconPadding = mContext.resources.getDimensionPixelSize(R.dimen.about_icon_padding)
            iconView.setPadding(iconPadding, 0, iconPadding, 0)
            if (Build.VERSION.SDK_INT < 21) {
                val drawable: Drawable? = VectorDrawableCompat.create(
                    iconView.resources,
                    element.iconDrawable!!,
                    iconView.context.theme
                )
                iconView.setImageDrawable(drawable)
            } else {
                iconView.setImageResource(element.iconDrawable!!)
            }
            var wrappedDrawable = DrawableCompat.wrap(iconView.drawable)
            wrappedDrawable = wrappedDrawable.mutate()
            val isNightModeEnabled = AboutPageUtils.isNightModeEnabled(mContext)
            val iconColor = AboutPageUtils.resolveColorAttr(mContext, R.attr.aboutElementIconTint)
            if (!element.skipTint) {
                if (element.autoApplyIconTint) {
                    if (isNightModeEnabled) {
                        if (element.iconNightTint != null) {
                            DrawableCompat.setTint(
                                wrappedDrawable,
                                ContextCompat.getColor(mContext, element.iconNightTint!!)
                            )
                        } else {
                            DrawableCompat.setTint(wrappedDrawable, iconColor)
                        }
                    } else {
                        if (element.iconTint != null) {
                            DrawableCompat.setTint(
                                wrappedDrawable,
                                ContextCompat.getColor(mContext, element.iconTint!!)
                            )
                        } else {
                            DrawableCompat.setTint(wrappedDrawable, iconColor)
                        }
                    }
                } else if (element.iconTint != null) {
                    DrawableCompat.setTint(
                        wrappedDrawable,
                        ContextCompat.getColor(mContext, element.iconTint!!)
                    )
                } else if (isNightModeEnabled) {
                    DrawableCompat.setTint(wrappedDrawable, iconColor)
                }
            }
        } else {
            val iconPadding = mContext.resources.getDimensionPixelSize(R.dimen.about_icon_padding)
            textView.setPadding(iconPadding, iconPadding, iconPadding, iconPadding)
        }
        textView.text = element.title
        if (mIsRTL) {
            val gravity:Int = if (element.gravity != null) element.gravity!! else Gravity.END
            wrapper.gravity = gravity or Gravity.CENTER_VERTICAL
            textParams.gravity = gravity or Gravity.CENTER_VERTICAL
            wrapper.addView(textView)
            if (element.iconDrawable != null) {
                wrapper.addView(iconView)
            }
        } else {
            val gravity:Int = if (element.gravity != null) element.gravity!! else Gravity.START
            wrapper.gravity = gravity or Gravity.CENTER_VERTICAL
            textParams.gravity = gravity or Gravity.CENTER_VERTICAL
            if (element.iconDrawable != null) {
                wrapper.addView(iconView)
            }
            wrapper.addView(textView)
        }
        return wrapper
    }

    private val separator: View
        private get() = mInflater.inflate(R.layout.about_page_separator, null)

    companion object {
        private val LOG_TAG = AboutPage::class.java.simpleName
    }
    // Java Конструктор 3
    /**
     * The AboutPage requires a context to perform it's functions. Give it a context associated to an
     * Activity or a Fragment. To avoid memory leaks, don't pass a
     * Страница "О программе" требует контекста для выполнения своих функций. Дайте ему контекст, связанный с действием
     * или фрагментом. Чтобы избежать утечек памяти, не передавайте
     * [Context.getApplicationContext()][android.content.Context.getApplicationContext] here.
     *
     * @param context
     */
    // Java Конструктор 1
    init {
        mContext = ContextThemeWrapper(context, style)
        mInflater = LayoutInflater.from(mContext)
        mView = mInflater.inflate(R.layout.about_page, null)
    }
}