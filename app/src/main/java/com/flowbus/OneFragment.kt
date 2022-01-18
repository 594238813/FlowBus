package com.flowbus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.flowbuslib.postEvent

class OneFragment : Fragment() {

    companion object{
        val instance: OneFragment by lazy  {
            OneFragment() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_one,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn = view.findViewById<Button>(R.id.btn_fragment)

        btn.setOnClickListener {
            postEvent(ChangeUIEvent("fragment msg"))
        }

    }
}