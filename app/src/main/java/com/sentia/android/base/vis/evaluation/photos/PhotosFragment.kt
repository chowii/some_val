package com.sentia.android.base.vis.evaluation.photos

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sentia.android.base.vis.R
import com.sentia.android.base.vis.base.EvaluationBaseFragment
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.databinding.FragmentPhotosBinding
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.Resource
import kotlinx.android.synthetic.main.fragment_photos.*

/**
 * Created by mariolopez on 10/1/18.
 */
class PhotosFragment : EvaluationBaseFragment() {
    private lateinit var binding: FragmentPhotosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        val photosAdapter = PhotosAdapter(context)
        with(rv_photos) {
            setHasFixedSize(true)
            adapter = photosAdapter
            layoutManager = GridLayoutManager(this.context, ITEMS_IN_LINE)
        }
        inspectionViewModel?.currentInspection
                ?.observe(this, Observer<Resource<Inspection>?> {
                    it?.data?.images?.let {
                        photosAdapter.setData(it)
                    }
                })
    }

    companion object {
        private const val ITEMS_IN_LINE = 4

        fun newInstance(vehicleId: Long) = PhotosFragment().apply {
            arguments = Bundle().apply {
                putLong(KEY_INSPECTION_ID, vehicleId)
            }
        }
    }
}