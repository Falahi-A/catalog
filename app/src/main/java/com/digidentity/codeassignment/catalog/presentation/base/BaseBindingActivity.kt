package com.digidentity.codeassignment.catalog.presentation.base

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.digidentity.codeassignment.catalog.R
import com.digidentity.codeassignment.catalog.databinding.ActivityBaseBinding
import com.google.android.material.snackbar.Snackbar


/**
 *  A parent for every activity that wanna implement viewBinding.
 *
 */
abstract class
BaseBindingActivity<VB : ViewBinding> : AppCompatActivity() {


    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    private lateinit var baseViewBinding: ActivityBaseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewBinding = ActivityBaseBinding.inflate(layoutInflater)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

    }

    /**
     *  BaseContainer will hold view of subclasses
     */

    override fun setContentView(layoutResID: Int) {
        super.setContentView(baseViewBinding.root)
        baseViewBinding.containerBase.removeAllViews()
        baseViewBinding.containerBase.addView(layoutInflater.inflate(layoutResID, null))
        initView()
    }


    override fun setContentView(view: View?) {
        super.setContentView(baseViewBinding.root)
        baseViewBinding.containerBase.removeAllViews()
        baseViewBinding.containerBase.addView(view)
        initView()
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(baseViewBinding.root, params)
        baseViewBinding.containerBase.removeAllViews()
        baseViewBinding.containerBase.addView(view)
        initView()
    }

    abstract fun initView()


    fun displayProgress(isDisplayed: Boolean) {
        baseViewBinding.progressBarBase.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun displayMessage(
        message: String,
        duration: Int = Snackbar.LENGTH_LONG,
        onClickListener: View.OnClickListener? = null,
        action: String = getString(R.string.retry)
    ) {
        val snackBar = Snackbar.make(baseViewBinding.root, message, duration)
            .setAction(action, onClickListener)
        // Make snackBar to be shown on top of screen
        val view: View = snackBar.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params

        snackBar.show()
    }


    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
        }
    }
}