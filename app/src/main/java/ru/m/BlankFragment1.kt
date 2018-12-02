package ru.m

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_blank_fragment1.*


class BlankFragment1 : Fragment() {
    private lateinit var mModel: RecyclerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mModel = ViewModelProviders.of(this).get(RecyclerViewModel::class.java)

        val mObserver = Observer<List<String>> {
            recycler.layoutManager = LinearLayoutManager(this.context)
            recycler.adapter = RecylcerAdapter(mModel.getList().value!!, this.context)
        }
        mModel.getList().observe(this, mObserver)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button1.setOnClickListener {
            mModel.update()

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(ru.m.R.layout.fragment_blank_fragment1, container, false)
    }
}
