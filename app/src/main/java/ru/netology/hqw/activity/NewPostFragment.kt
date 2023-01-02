package ru.netology.hqw.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.hqw.databinding.FragmentNewPostBinding
import ru.netology.hqw.utils.StringArg
import ru.netology.hqw.viewModel.PostViewModel

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container,false)
        val viewModel by viewModels<PostViewModel>(ownerProducer = :: requireParentFragment)
        arguments?.textArg?.let(binding.edit::setText)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isNotBlank()){
                viewModel.changeContentAndSave(text) }
            findNavController().navigateUp()
        }
        return binding.root
    }

    companion object{
        var Bundle.textArg by StringArg
    }
}