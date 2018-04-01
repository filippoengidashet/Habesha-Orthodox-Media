package org.dalol.habeshaorthodoxmedia.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import android.graphics.drawable.Drawable
import android.graphics.PorterDuff
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.widget.Toast

/**
 * @author Filippo Engidashet [filippo.eng@gmail.com]
 * @version 1.0.0
 * @since Wednesday, 21/03/2018 at 16:49.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        onActivityCreated(savedInstanceState)
    }

    @CallSuper
    protected open fun onActivityCreated(savedInstanceState: Bundle?) {
    }

    inline fun <reified T : Activity> start() = startActivity(Intent(this, T::class.java))

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun showHome() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    protected fun showHome(resId: Int) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            showHome()
            actionBar.setHomeAsUpIndicator(resId)
        }
    }

    protected fun showHome(resId: Int, colorTint: Int) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            showHome()
            val drawable = ContextCompat.getDrawable(this, resId)
            tintDrawable(drawable, colorTint)
            actionBar.setHomeAsUpIndicator(drawable)
        }
    }

    protected fun tintDrawable(drawable: Drawable?, colorTint: Int) {
        val color = ContextCompat.getColor(this, colorTint)
        val wrappedDrawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(wrappedDrawable, color)
        DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SRC_IN)
    }

    protected fun showHome(drawable: Drawable) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            showHome()
            actionBar.setHomeAsUpIndicator(drawable)
        }
    }

    abstract fun getContentView(): Int
}