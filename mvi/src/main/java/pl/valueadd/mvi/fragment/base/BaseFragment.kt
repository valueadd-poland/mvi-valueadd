package pl.valueadd.mvi.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import pl.valueadd.mvi.activity.IBaseActivity

abstract class BaseFragment<Binding : ViewBinding> :
    Fragment(), IBaseFragment {

    protected abstract val bindingInflater: FragmentBindingInflater<Binding>

    protected var binding: Binding? = null
        private set

    protected val requireBinding: Binding
        get() = requireNotNull(binding)

    /* Life cycle */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding!!.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : IBaseFragment> getParentFragment(fragmentClass: Class<T>): T {
        if (parentFragment == null || parentFragment?.javaClass != fragmentClass) {
            throw IllegalArgumentException("Parent fragment at given class does not belong to back stack.")
        }
        return parentFragment as T
    }

    /**
     * Return activity of the fragment.
     * @throws IllegalArgumentException when given class does not host current fragment.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : IBaseActivity> getActivity(activityClass: Class<T>): T {
        if (activity == null || activity?.javaClass != activityClass) {
            throw IllegalArgumentException("Activity at given class does not host current fragment.")
        }
        return activity as T
    }
}

typealias FragmentBindingInflater<Binding> = (
    inflater: LayoutInflater,
    container: ViewGroup?,
    attachToParent: Boolean
) -> Binding