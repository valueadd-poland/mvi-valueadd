package pl.valueadd.mvi.fragment.base

import pl.valueadd.mvi.activity.IBaseActivity

interface IBaseFragment {

    /**
     * Return parent fragment.
     * @throws IllegalArgumentException when given class does not belong to back stack.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : IBaseFragment> getParentFragment(fragmentClass: Class<T>): T

    /**
     * Return activity of the fragment.
     * @throws IllegalArgumentException when given class does not host current fragment.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : IBaseActivity> getActivity(activityClass: Class<T>): T
}