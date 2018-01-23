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
import com.sentia.android.base.vis.data.room.entity.Image
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.databinding.FragmentPhotosBinding
import com.sentia.android.base.vis.sentialibrary.ImageSelector
import com.sentia.android.base.vis.util.KEY_INSPECTION_ID
import com.sentia.android.base.vis.util.Resource
import com.sentia.android.base.vis.util.Resource.Status.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_photos.*
import org.jetbrains.anko.error


/**
 * Created by mariolopez on 10/1/18.
 */
class PhotosFragment : EvaluationBaseFragment() {

    private lateinit var binding: FragmentPhotosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        val photosAdapter = PhotosAdapter()
        inspectionViewModel?.currentInspection
                ?.observe(this, Observer<Resource<Inspection>?> {
                    it?.data?.images?.let {
                        photosAdapter.setData(it)
                    }
                })
        inspectionViewModel?.imageObservable?.subscribeBy(
                onNext = { resource ->
                    when (resource.status) {
                        SUCCESS -> srl_loading.isRefreshing = false
                        LOADING -> srl_loading.isRefreshing = true
                        ERROR -> srl_loading.isRefreshing = false
                    }
                },
                onError = {
                    error { "Error during compressing image" }
                })
        subscribeOnItemClick(photosAdapter)

        with(rv_photos) {
            setHasFixedSize(true)
            adapter = photosAdapter
            layoutManager = GridLayoutManager(this.context, ITEMS_IN_LINE)
        }
        srl_loading.isEnabled = false
    }

    private fun subscribeOnItemClick(photosAdapter: PhotosAdapter) {
        var selectedImage: Image? = null

        val imageClickObservable: Observable<String> = photosAdapter.itemViewClickObservable
                .flatMap { image ->
                    selectedImage = image
                    val imageSelector = ImageSelector(activity!!)
                    lifecycle.addObserver(imageSelector)
                    imageSelector.selectImage()
                }
                .flatMap { inspectionViewModel?.compressAndDecodeImage(it, context!!) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        Observable.combineLatest(
                imageClickObservable,
                inspectionDbObs,
                BiFunction { base64: String, inspection: Inspection ->
                    Pair(inspection, inspection.copyWithList().apply {
                        this.images.find { it.id == selectedImage?.id }?.attachmentB64 = base64
                    })

                })
                .subscribeBy(
                        onNext = { (old, new) ->
                            inspectionViewModel?.saveTempChanges(old) {
                                it.images.clear()
                                it.images.addAll(new.images)
                                photosAdapter.setData(it.images)
                            }
                        },
                        onError = {
                            error { it.message }
                            subscribeOnItemClick(photosAdapter)
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