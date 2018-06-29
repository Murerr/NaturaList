package com.murer.rudy.naturalist

interface HomeFragmentModel {

    companion object {
        fun newInstance(): HomeFragmentModelImpl =
                HomeFragmentModelImpl(
                        // add param here
                )

    }
    // add functions here
}
// add data class here

class HomeFragmentModelImpl(
    // add Managers here
) : HomeFragmentModel {


}