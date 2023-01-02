package ru.netology.hqw.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.hqw.R
import ru.netology.hqw.activity.NewPostFragment.Companion.textArg
import ru.netology.hqw.adapter.PostAdapter
import ru.netology.hqw.databinding.FragmentFeedBinding
import ru.netology.hqw.dto.Post
import ru.netology.hqw.listeners.OnInteractionListeners
import ru.netology.hqw.viewModel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = FragmentFeedBinding.inflate(inflater,container, false)

        val viewModel by viewModels<PostViewModel>(ownerProducer = :: requireParentFragment)
        val adapter = PostAdapter(object : OnInteractionListeners {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onReply(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val replyIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(replyIntent)
            }

           override fun onYoutube(post: Post) {
                if (post.video != null){
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(post.video)
                    }
                   startActivity(intent)
                }
            }

            override fun postLink(post: Post){
                findNavController().navigate(
                    R.id.action_feedFragment_to_postFragment, Bundle().apply {
                        textArg = post.id.toString()
                    }
                )
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }


        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
            findNavController()
                .navigate(R.id.action_feedFragment_to_newPostFragment, Bundle().apply {
                    textArg = post.content
                })
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        return binding.root
    }
}

