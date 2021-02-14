package pl.valueadd.mvi.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<Binding : ViewBinding> :
    AppCompatActivity(),
    IBaseActivity {

    protected abstract val bindingInflater: ActivityBindingInflater<Binding>

    protected var binding: Binding? = null
        private set

    protected val requireBinding: Binding
        get() = requireNotNull(binding)


    /* Life cycle methods */

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding!!.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delegate.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }
}

typealias ActivityBindingInflater<Binding> = (layoutInflater: LayoutInflater) -> Binding
