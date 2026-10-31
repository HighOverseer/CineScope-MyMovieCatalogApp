package com.fajar.cinescope.core.ui.model

import android.content.Context
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

sealed class UIText : Parcelable {

    @IgnoredOnParcel
    private val mapToGetValueLambda = hashMapOf(
        Dynamic::class to { _: Context -> (this as Dynamic).text },
        Static::class to { ctx: Context -> ctx.getString((this as Static).stringResId) },
        StaticWithArgs::class to { ctx: Context ->
            ctx.getString(
                (this as StaticWithArgs).stringResId,
                *this.args.toTypedArray()
            )
        }
    )


    fun getValue(context: Context): String {
        val getValueLambda = mapToGetValueLambda[this::class]
        return getValueLambda?.invoke(context) ?: ""
    }

    @Composable
    fun getValue(): String {
        val getValueLambda = mapToGetValueLambda[this::class]
        val context = LocalContext.current
        return getValueLambda?.invoke(context) ?: ""
    }

    @Parcelize
    data class Dynamic(
        val text: String
    ) : UIText()

    @Parcelize
    data class Static(
        val stringResId: Int
    ) : UIText()

    @Parcelize
    data class StaticWithArgs(
        val stringResId: Int,
        val args: List<String>
    ) : UIText()
}