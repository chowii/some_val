package com.sentia.android.base.vis.search

/**
 * Created by mariolopez on 27/12/17.
 */
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.switchMap
import com.github.salomonbrys.kodein.instance
import com.sentia.android.base.vis.base.BaseViewModel
import com.sentia.android.base.vis.data.InspectionRepository
import com.sentia.android.base.vis.data.room.entity.Inspection
import com.sentia.android.base.vis.upload.Uploader
import com.sentia.android.base.vis.util.Resource

class SearchViewModel : BaseViewModel() {

    override val repository by kodein.instance<InspectionRepository>()
    private val searchInput: MutableLiveData<String> = MutableLiveData()
    private val uploader: Uploader by kodein.instance()

    internal val searchResult = switchMap(searchInput) {
        //this is lazy load so it caches the result on rotation
        repository.doSearch(it)
    }

    fun search(searchWord: String?) {
        searchInput.value = searchWord
    }

    fun loadInspections(): LiveData<Resource<List<Inspection>>?> = repository.getInspections()

    fun sync(inspectionToUpload: Inspection? = null) = uploader.sync(inspectionToUpload)
}


